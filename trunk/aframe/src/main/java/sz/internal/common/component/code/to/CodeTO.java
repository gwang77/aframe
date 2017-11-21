package sz.internal.common.component.code.to;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CodeTO implements Serializable {
    private String code_type_id;
    private String code_id;
    private String code_desc;
    private String code_seq;
    private Map<String, String> label = new HashMap();

    public String getCode_type_id() {
        return code_type_id;
    }

    public void setCode_type_id(String code_type_id) {
        this.code_type_id = code_type_id;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public String getCode_desc() {
        return code_desc;
    }

    public void setCode_desc(String code_desc) {
        this.code_desc = code_desc;
    }

    public String getCode_seq() {
        return code_seq;
    }

    public void setCode_seq(String code_seq) {
        this.code_seq = code_seq;
    }

    public Map<String, String> getLabel() {
        return label;
    }

    public void setLabel(Map<String, String> label) {
        this.label = label;
    }
}
