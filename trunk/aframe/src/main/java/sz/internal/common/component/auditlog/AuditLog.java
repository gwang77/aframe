package sz.internal.common.component.auditlog;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.SystemConfig;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.auditlog.service.AuditLogService;
import sz.internal.common.component.auditlog.to.AuditLogTO;
import sz.internal.common.util.JsonUtil;
import sz.internal.common.util.SysConfigPropertyUtil;

public class AuditLog {
    private static final Logger LOGGER = Logger.getLogger(AuditLog.class);

    public static final String AUDIT_LOG_CREATE = "CREATE";
    public static final String AUDIT_LOG_UPDATE = "UPDATE";
    public static final String AUDIT_LOG_DELETE = "DELETE";

    private static AuditLogService auditLogService;

    private static Boolean allowAuditLog = null;
    private static String allow_tables = null;

    public static AuditLogService getAuditLogService() {
        if (auditLogService == null) {
            auditLogService = (AuditLogService) SpringContextHolder.getBean("sz.internal.common.component.auditlog.service.AuditLogService");
        }
        return auditLogService;
    }

    public static void auditCreate(BaseTO baseTO) throws Exception {
        audit(null, baseTO, AUDIT_LOG_CREATE);
    }

    public static void auditUpdate(BaseTO b_to, BaseTO a_to) throws Exception {
        audit(b_to, a_to, AUDIT_LOG_UPDATE);
    }

    public static void auditDelete(BaseTO baseTO) throws Exception {
        audit(baseTO, null, AUDIT_LOG_DELETE);
    }

    private static void audit(BaseTO b_to, BaseTO a_to, String opt) throws Exception {
        try {
            String class_name = b_to != null ? b_to.getClass().getName() : (a_to != null ? a_to.getClass().getName() : "");
            if (!isAllowAuditLog(class_name)) {
                return;
            }
            AuditLogTO auditLogTO = new AuditLogTO();
            auditLogTO.setClass_name(class_name);
            auditLogTO.setB_img(classToString(b_to));
            auditLogTO.setA_img(classToString(a_to));
            auditLogTO.setAction(opt);
            getAuditLogService().insert(auditLogTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static String classToString(BaseTO to) {
        if (to == null) {
            return "";
        }
        return JsonUtil.writeValueForAudit(to);
    }

    private static boolean isAllowAuditLog(String class_name) {
        if (allowAuditLog == null) {
            String allowAuditLogStr = SysConfigPropertyUtil.getProperties("auditlog.allow");
            if (!StringUtils.isEmpty(allowAuditLogStr)) {
                allowAuditLog = "Y".equalsIgnoreCase(allowAuditLogStr) || "YES".equalsIgnoreCase(allowAuditLogStr) || "TRUE".equalsIgnoreCase(allowAuditLogStr) || "T".equalsIgnoreCase(allowAuditLogStr);
            } else {
                allowAuditLog = SystemConfig.isAllowAuditLog();
            }
        }
        if (StringUtils.isEmpty(allow_tables)) {
            allow_tables = SysConfigPropertyUtil.getProperties("auditlog.tables");
        }

        return allowAuditLog && isMatchName(class_name);
    }

    private static boolean isMatchName(String class_name) {
        return !StringUtils.isEmpty(class_name) && (StringUtils.isEmpty(allow_tables) || allow_tables.contains("," + class_name + ","));
    }
}
