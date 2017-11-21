package sz.internal.common.component.seqno.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.common.TableTOMap;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.seqno.mapper.SeqNoMapper;
import sz.internal.common.component.seqno.to.SeqNoTO;
import sz.internal.common.util.DateUtil;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class SeqNoService extends BaseService {
    public static String SEQ_TYPE_DATE = "D";
    public static String SEQ_TYPE_MONTH = "M";
    public static String SEQ_TYPE_YEAR = "Y";

    @Resource(name = "sz.internal.common.component.seqno.mapper.SeqNoMapper")
    public void setMapper(SeqNoMapper mapper) {
        super.setMapper(mapper);
    }

    public void insert(BaseTO baseTO) throws Exception {
        checkInputNull(baseTO);
        getMapper().insert(baseTO);
    }

    public void update(BaseTO baseTO) throws Exception {
        checkInputIDNull(baseTO);
        getMapper().update(baseTO);
    }

    public int getNextID(BaseTO baseTO) throws Exception {
        String tableName = TableTOMap.getTableByTOName(baseTO.getClass().getName());
        return getNextIDByKeyName(tableName);
    }

    public synchronized int getNextIDByKeyName(String key_name) throws Exception {
        return getNextIDByKeyName(key_name, "");
    }

    public synchronized int getNextIDByKeyName(String key_name, String seq_type) throws Exception {
        int nextID = 1;
        SeqNoTO seqNoTO = new SeqNoTO();
        seqNoTO.setKey_name(key_name);
        seqNoTO = (SeqNoTO) find(seqNoTO);
        if (seqNoTO == null) {
            seqNoTO = new SeqNoTO();
            seqNoTO.setKey_name(key_name);
            seqNoTO.setMax_id(nextID);
            seqNoTO.setMax_id_desc(DateUtil.formatDate(new Date(), "yyyyMMdd"));
            insert(seqNoTO);
        } else {
            seqNoTO = getNextSeqTO(seqNoTO, seq_type);
            update(seqNoTO);
            nextID = seqNoTO.getMax_id();
        }
        return nextID;
    }

    private SeqNoTO getNextSeqTO(SeqNoTO seqNoTO, String seq_type) {
        Date currDt = new Date();
        String currDtStr = DateUtil.formatDate(currDt, "yyyyMMdd");

        if (StringUtils.isEmpty(seq_type) || StringUtils.isEmpty(seqNoTO.getMax_id_desc())) {
            seqNoTO.setMax_id(seqNoTO.getMax_id() + 1);
            seqNoTO.setMax_id_desc(currDtStr);
            return seqNoTO;
        }
        String max_id_desc_type = currDtStr;
        String max_id_desc = seqNoTO.getMax_id_desc();

        if (SEQ_TYPE_DATE.equals(seq_type)) {
            max_id_desc_type = currDtStr;
        }
        if (SEQ_TYPE_MONTH.equals(seq_type)) {
            max_id_desc_type = currDtStr.substring(0, 6);
            max_id_desc = max_id_desc.substring(0, 6);
        }
        if (SEQ_TYPE_YEAR.equals(seq_type)) {
            max_id_desc_type = currDtStr.substring(0, 4);
            max_id_desc = max_id_desc.substring(0, 4);
        }

        if (max_id_desc_type.equals(max_id_desc)) {
            seqNoTO.setMax_id(seqNoTO.getMax_id() + 1);
        } else {
            seqNoTO.setMax_id(1);
        }
        seqNoTO.setMax_id_desc(currDtStr);
        return seqNoTO;
    }
}