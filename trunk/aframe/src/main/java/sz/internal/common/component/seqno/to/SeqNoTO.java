package sz.internal.common.component.seqno.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;

public class SeqNoTO extends BaseTO {
	private String key_name;
    private String max_id_s;
	private String max_id_desc;

	public String getKey_name() {
		return key_name;
    }

	public void setKey_name(String key_name) {
		this.key_name = key_name;
    }

    @JsonIgnore
    public Integer getMax_id() {
        if (max_id_s == null || "".equals(max_id_s)) {
            return null;
        }
        return new Integer(max_id_s);
    }

    public void setMax_id(Integer max_id) {
        if (max_id == null) {
            max_id_s = null;
            return;
        }
        max_id_s = String.valueOf(max_id);
    }

    public String getMax_id_s() {
        return max_id_s;
    }

    public void setMax_id_s(String max_id_s) {
        this.max_id_s = max_id_s;
    }

    public String getMax_id_desc() {
        return max_id_desc;
    }

    public void setMax_id_desc(String max_id_desc) {
        this.max_id_desc = max_id_desc;
    }

    @Override
    public String[] getPKFields() {
        return new String[]{"key_name"};
    }
}


