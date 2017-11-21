package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class PermissionTO extends BaseTO {
	private String name;
	private String app_id;
	private String url;
	private String request_method;
	private String permission;

	@JsonIgnore
	private String name_like;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequest_method() {
		return request_method;
	}

	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName_like() {
		return name_like;
	}

	public void setName_like(String name_like) {
		this.name_like = name_like;
	}
}


