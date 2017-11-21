package sz.internal.common.base.controller;

import sz.internal.common.exception.MessageInfo;
import sz.internal.common.exception.MessageInfos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * User: wanggang
 * Date: 12/4/15
 * Time: 2:27 PM
 */
public class BaseController {
    public void addMessage(HttpServletRequest request, String messageKey) {
        MessageInfos messageInfos = getMessageInfo(request);
        messageInfos.addMessageInfo(new MessageInfo(messageKey));
    }

    public void addMessage(HttpServletRequest request, String messageKey, String... args) {
        MessageInfos messageInfos = getMessageInfo(request);
        messageInfos.addMessageInfo(new MessageInfo(messageKey, args));
    }

    public void addBusinessError(HttpServletRequest request, String messageKey) {
        MessageInfos messageInfos = getErrorInfo(request);
        messageInfos.addMessageInfo(new MessageInfo(messageKey));
    }

    public void addBusinessError(HttpServletRequest request, String messageKey, String... args) {
        MessageInfos messageInfos = getErrorInfo(request);
        messageInfos.addMessageInfo(new MessageInfo(messageKey, args));
    }

    private MessageInfos getMessageInfo(HttpServletRequest request) {
        return getMessageInfo(request, "business_messages");
    }

    private MessageInfos getErrorInfo(HttpServletRequest request) {
        return getMessageInfo(request, "business_errors");
    }

    private MessageInfos getMessageInfo(HttpServletRequest request, String messageID) {
        MessageInfos messageInfos = (MessageInfos) request.getAttribute("business_messages");
        if (messageInfos == null) {
            messageInfos = new MessageInfos();
            request.setAttribute("business_messages", messageInfos);
        }
        return messageInfos;
    }

    public Object downloadFile(byte[] content, HttpServletResponse response) throws Exception {
        OutputStream out = response.getOutputStream();
        try {
            out = response.getOutputStream();
            out.write(content);
            out.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return true;
    }
}
