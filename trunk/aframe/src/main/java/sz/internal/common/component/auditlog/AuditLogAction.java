package sz.internal.common.component.auditlog;

import org.apache.log4j.Logger;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.component.auditlog.service.AuditLogActionService;
import sz.internal.common.component.auditlog.to.AuditLogActionTO;
import sz.internal.common.component.statistic.StatisticUtil;

import java.sql.Timestamp;
import java.util.Date;

public class AuditLogAction {
    private static final Logger logger = Logger.getLogger(AuditLogAction.class);

    private static AuditLogActionService auditLogActionService;

    private static AuditLogActionService getAuditLogActionService() {
        if (auditLogActionService == null) {
            auditLogActionService = SpringContextHolder.getBean("auditLogActionService");
        }
        return auditLogActionService;
    }

    public static void auditAction(String user_name, String action, String action_desc) throws Exception {
        auditAction(user_name, action, action_desc, null);
    }

    public static void auditAction(String user_name, String action, String action_desc, String params) throws Exception {
        AuditLogActionTO actionTO = new AuditLogActionTO();
        actionTO.setUser_name(user_name);
        actionTO.setAction(action);
        actionTO.setParams(params);
        actionTO.setAudit_date(new Timestamp((new Date()).getTime()));
        getAuditLogActionService().insert(actionTO);

        int inc_count = 1;
        int inc_user_count = 1;
        AuditLogActionTO searchTO = new AuditLogActionTO();
        searchTO.setUser_name(user_name);
        searchTO.setAction(action);
        int count = getAuditLogActionService().searchCount(searchTO);
        if (count > 1) {
            inc_user_count = 0;
        }
        StatisticUtil.updateCount(action, action_desc, inc_count, inc_user_count);
    }

}
