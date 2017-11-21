package sz.internal.common;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: wanggang
 * Date: 4/29/16
 * Time: 11:06 AM
 */
public class TableTOMap {
    private static final Logger logger = Logger.getLogger(TableTOMap.class);

    public static Map<String, String> TABLE_TO_MAP = null;
    public static Map<String, String> TO_TABLE_MAP = null;

    private static void initMap() {
        TABLE_TO_MAP = new HashMap<String, String>();
        TO_TABLE_MAP = new HashMap<String, String>();
        DefaultSqlSessionFactory bean = SpringContextHolder.getBean("sqlSessionFactory");
        try {
            Collection col = bean.getConfiguration().getMappedStatements();
            for (Object obj : col) {
                if (!(obj instanceof MappedStatement)) {
                    continue;
                }
                MappedStatement st = (MappedStatement) obj;
                String id = st.getId();
                if (!id.endsWith(".insert")) {
                    continue;
                }
                String paraClsName = st.getParameterMap().getType().getName();
                String sql = st.getBoundSql(new Object()).getSql();
                String tableName = sql.substring(sql.indexOf("tbl_"), sql.indexOf("("));
                tableName = tableName.trim();
                TABLE_TO_MAP.put(tableName, paraClsName);
                TO_TABLE_MAP.put(paraClsName, tableName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static String getTableByTOName(String toName) {
        if (StringUtils.isEmpty(toName)) {
            return "";
        }
        if (TO_TABLE_MAP == null) {
            initMap();
        }
        String tableName = TO_TABLE_MAP.get(toName);
        tableName = tableName == null ? "" : tableName;
        return tableName;
    }

    public static String getTONameByTable(String tableName) {
        if (TABLE_TO_MAP == null) {
            initMap();
        }
        String toName = TO_TABLE_MAP.get(tableName);
        toName = toName == null ? "" : toName;
        return toName;
    }
}
