package sz.internal.common.component.statistic.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticTO extends BaseTO {
    private String item_key;
    private String item_desc;
    private String item_count_s;
    private String item_user_count_s;
    private String seq_no_s;

    public String getItem_key() {
        return item_key;
    }

    public void setItem_key(String item_key) {
        this.item_key = item_key;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    @JsonIgnore
    public Integer getItem_count() {
        if (item_count_s == null || "".equals(item_count_s)) {
            return null;
        }
        return new Integer(item_count_s);
    }

    public void setItem_count(Integer item_count) {
        if (item_count == null) {
            item_count_s = null;
            return;
        }
        item_count_s = String.valueOf(item_count);
    }

    public String getItem_count_s() {
        return item_count_s;
    }

    public void setItem_count_s(String item_count_s) {
        this.item_count_s = item_count_s;
    }

    @JsonIgnore
    public Integer getItem_user_count() {
        if (item_user_count_s == null || "".equals(item_user_count_s)) {
            return null;
        }
        return new Integer(item_user_count_s);
    }

    public void setItem_user_count(Integer item_user_count) {
        if (item_user_count == null) {
            item_user_count_s = null;
            return;
        }
        item_user_count_s = String.valueOf(item_user_count);
    }

    public String getItem_user_count_s() {
        return item_user_count_s;
    }

    public void setItem_user_count_s(String item_user_count_s) {
        this.item_user_count_s = item_user_count_s;
    }

    @JsonIgnore
    public Integer getSeq_no() {
        if (seq_no_s == null || "".equals(seq_no_s)) {
            return null;
        }
        return new Integer(seq_no_s);
    }

    public void setSeq_no(Integer seq_no) {
        if (seq_no == null) {
            seq_no_s = null;
            return;
        }
        seq_no_s = String.valueOf(seq_no);
    }

    public String getSeq_no_s() {
        return seq_no_s;
    }

    public void setSeq_no_s(String seq_no_s) {
        this.seq_no_s = seq_no_s;
    }

}


