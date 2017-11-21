package sz.internal.common.exception;

import java.util.List;

/**
 * User: wanggang
 * Date: 12/18/15
 * Time: 1:20 PM
 */
public class BusinessException extends Exception {
    private MessageInfos errorInfos = new MessageInfos();

    public MessageInfos getErrorInfos() {
        return errorInfos;
    }

    public void setErrorInfos(MessageInfos errorInfos) {
        this.errorInfos = errorInfos;
    }

    public BusinessException() {
    }

    public BusinessException(String message) {
        addErrorInfo(message);
    }

    public BusinessException(int errCode, String message) {
        addErrorInfo(new MessageInfo(errCode, message));
    }

    public BusinessException(String message, String... args) {
        addErrorInfo(message, args);
    }

    public boolean hasError() {
        return errorInfos.getMessageInfoCount() != 0;
    }

    public MessageInfo getMessageInfo() {
        List list = errorInfos.getMessageInfoList();
        if (list.size() > 0) {
            return (MessageInfo) list.get(0);
        }
        return null;
    }

    @Override
    public String getMessage() {
        List list = errorInfos.getMessageInfoList();
        if (list.size() > 0) {
            MessageInfo errorInfo = (MessageInfo) list.get(0);
            return errorInfo.getMessage();
        }
        return "";
    }

    public String[] getMessageArg() {
        List list = errorInfos.getMessageInfoList();
        if (list.size() > 0) {
            MessageInfo errorInfo = (MessageInfo) list.get(0);
            return errorInfo.getArguments();
        }
        return new String[0];
    }

    public void addErrorInfo(MessageInfo errorInfo) {
        errorInfos.addMessageInfo(errorInfo);
    }

    public void addErrorInfo(String message) {
        MessageInfo errorInfo = new MessageInfo(message);
        addErrorInfo(errorInfo);
    }

    public void addErrorInfo(String message, String... args) {
        MessageInfo errorInfo = new MessageInfo(message, args);
        addErrorInfo(errorInfo);
    }

}
