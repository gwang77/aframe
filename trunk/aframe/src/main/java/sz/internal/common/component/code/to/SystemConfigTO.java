package sz.internal.common.component.code.to;

import java.util.List;



public class SystemConfigTO {
	private String label;
	private String code_id;
	private String code_desc;
	private String value_codetype_id;
	private String editable;
	private List<SystemConfigTO> valueList;
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public String getValue_codetype_id() {
		return value_codetype_id;
	}

	public void setValue_codetype_id(String value_codetype_id) {
		this.value_codetype_id = value_codetype_id;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public List<SystemConfigTO> getValueList() {
		return valueList;
	}

	public void setValueList(List<SystemConfigTO> valueList) {
		this.valueList = valueList;
	}

	
	
}
