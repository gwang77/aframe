package sz.internal.common.component.code.to;

import sz.internal.common.base.to.BaseTO;

public class CodeTypeTO extends BaseTO {
	private String codetype_id;
	private String codetype_table;
	private String codetype_desc;
	private String col_codetype_id;
	private String col_code_id;
	private String col_code_desc;

	public String getCodetype_id() {
		return codetype_id;
	}

	public void setCodetype_id(String codetype_id) {
		this.codetype_id = codetype_id;
	}

	public String getCodetype_desc() {
		return codetype_desc;
	}

	public void setCodetype_desc(String codetype_desc) {
		this.codetype_desc = codetype_desc;
	}

	public String getCol_codetype_id() {
		return col_codetype_id;
	}

	public void setCol_codetype_id(String col_codetype_id) {
		this.col_codetype_id = col_codetype_id;
	}

	public String getCol_code_id() {
		return col_code_id;
	}

	public void setCol_code_id(String col_code_id) {
		this.col_code_id = col_code_id;
	}

	public String getCol_code_desc() {
		return col_code_desc;
	}

	public void setCol_code_desc(String col_code_desc) {
		this.col_code_desc = col_code_desc;
	}

	public String getCodetype_table() {
		return codetype_table;
	}

	public void setCodetype_table(String codetype_table) {
		this.codetype_table = codetype_table;
	}

    public String[] getPKFields() {
        return new String[]{"codetype_id"};
    }

}
