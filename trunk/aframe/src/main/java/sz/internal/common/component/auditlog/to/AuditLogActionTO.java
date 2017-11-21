package sz.internal.common.component.auditlog.to;

import sz.internal.common.base.to.BaseTO;

import java.sql.Timestamp;

public class AuditLogActionTO extends BaseTO {
    private String user_name;
    private String action;
    private Timestamp audit_date;
    private String params;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(Timestamp audit_date) {
        this.audit_date = audit_date;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}


