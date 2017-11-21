package sz.internal.common.component.code.to;

import sz.internal.common.base.to.BaseTO;


public class CodeIntTO extends BaseTO {
	private String code_id;
	private String code_seq;
	private String code_desc;
	private String codetype_id;
	private String status;
	private CodeTO status_code;
	private String locales;
	private CodeTO locales_code;
	private String editable;
	
	public String getCode_id() {
		return code_id;
	}

	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}

	public String getCode_seq() {
		return code_seq;
	}

	public void setCode_seq(String code_seq) {
		this.code_seq = code_seq;
	}

	public String getCode_desc() {
		return code_desc;
	}

	public void setCode_desc(String code_desc) {
		this.code_desc = code_desc;
	}

	public String getCodetype_id() {
		return codetype_id;
	}

	public void setCodetype_id(String codetype_id) {
		this.codetype_id = codetype_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	public CodeTO getStatus_code() {
		return status_code;
	}

	public void setStatus_code(CodeTO status_code) {
		this.status_code = status_code;
	}

	public CodeTO getLocales_code() {
		return locales_code;
	}

	public void setLocales_code(CodeTO locales_code) {
		this.locales_code = locales_code;
	}

	public String getLocales() {
		return locales;
	}

	public void setLocales(String locales) {
		this.locales =locales;
	}

	public String[] getPKFields() {
        return new String[]{"code_id","codetype_id"};
    }

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}
   
}
