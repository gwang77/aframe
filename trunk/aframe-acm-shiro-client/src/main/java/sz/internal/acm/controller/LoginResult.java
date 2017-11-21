package sz.internal.acm.controller;

import java.io.Serializable;

class LoginResult implements Serializable {
    static final String LOGIN_STATUS_SUCCESS = "0";
    static final String LOGIN_STATUS_FAILED = "1";

    private String status;
    private String errMsg;
    private String session_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
