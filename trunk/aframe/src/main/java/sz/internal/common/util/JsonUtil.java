package sz.internal.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import sz.internal.common.base.to.BaseTO;

import java.util.*;

/**
 * User: wanggang
 * Date: 1/7/16
 * Time: 11:11 AM
 */
public class JsonUtil {
    private static final Logger logger = Logger.getLogger(JsonUtil.class);

    private static final String json_ignore = ",totalSize,sortType,sortBy,";

    public static Object readValue(String jsonStr, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String writeValue(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "Json Write Value error:" + e.getMessage();
        }
    }

    public static String writeValueForAudit(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.convertValue(obj, HashMap.class);
            //remove List and BaseTO
            removeComplexObject(map);

            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            return "Json Write Value error:" + e.getMessage();
        }
    }

    private static void removeComplexObject(Map map) {
        List keyList = new ArrayList();
        Set set = map.entrySet();
        for (Object aSet : set) {
            Map.Entry entry = (Map.Entry) aSet;
            String key = (String) entry.getKey();
            if (json_ignore.contains("," + entry.getKey() + ",")) {
                keyList.add(key);
                continue;
            }
            Object val = entry.getValue();
            if (val == null
                    || val instanceof List
                    || val instanceof Map
                    || val instanceof BaseTO
                    || val instanceof byte[]
                    || val instanceof String[]) {
                keyList.add(key);
            }
        }
        for (Object key : keyList) {
            map.remove(key);
        }
    }

}
