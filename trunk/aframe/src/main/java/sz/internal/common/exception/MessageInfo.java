package sz.internal.common.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * User: wanggang
 * Date: 12/18/15
 * Time: 5:02 PM
 */
public class MessageInfo {
    private int errorCode;
    private String message;
    private String[] arguments;

    public MessageInfo(String message) {
        this.errorCode = HttpServletResponse.SC_BAD_REQUEST;
        this.message = message;
    }

    public MessageInfo(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public MessageInfo(String message, String... arguments) {
        this.message = message;
        this.arguments = arguments;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }
}
