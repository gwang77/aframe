package sz.internal.common.component.seqno.service;

import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.to.BaseTO;

public class SeqNoUtil {

    public static int getNextID(BaseTO baseTO) throws Exception {
        SeqNoService seqNoService = SpringContextHolder.getBean("seqNoService");
        return seqNoService.getNextID(baseTO);
    }

    public static int getNextIDByKeyName(String key_name) throws Exception {
        return getNextIDByKeyName(key_name, "");
    }

    public static int getNextIDByKeyName(String key_name, String seq_type) throws Exception {
        SeqNoService seqNoService = SpringContextHolder.getBean("seqNoService");
        return seqNoService.getNextIDByKeyName(key_name, seq_type);
    }
}
