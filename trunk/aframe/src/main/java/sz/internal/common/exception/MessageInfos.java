package sz.internal.common.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * User: wanggang
 * Date: 12/18/15
 * Time: 5:11 PM
 */
public class MessageInfos {
    private List<MessageInfo> messageInfoList = new ArrayList<MessageInfo>();

    public List getMessageInfoList() {
        return messageInfoList;
    }

    public void setMessageInfoList(List<MessageInfo> messageInfoList) {
        this.messageInfoList = messageInfoList;
    }

    public void addMessageInfo(MessageInfo messageInfo) {
        messageInfoList.add(messageInfo);
    }

    public int getMessageInfoCount() {
        return messageInfoList.size();
    }
}
