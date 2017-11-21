package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class ApplicationTO extends BaseTO {
	private String app_id;
	private String name;
	private String base_url;
	private String locked;
	private CodeTO locked_code;
	
	@JsonIgnore
	private String name_like;
	
	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase_url() {
		return base_url;
	}

	public void setBase_url(String base_url) {
		this.base_url = base_url;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public CodeTO getLocked_code() {
		return locked_code;
	}

	public void setLocked_code(CodeTO locked_code) {
		this.locked_code = locked_code;
	}

	public String getName_like() {
		return name_like;
	}

	public void setName_like(String name_like) {
		this.name_like = name_like;
	}
	
}
