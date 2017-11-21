package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class UserRoleTO extends BaseTO {
	private String user_id_s;
	private String role_id_s;
	private String app_id;

	@JsonIgnore
    public Integer getUser_id() {
		if (user_id_s == null || "".equals(user_id_s)) {
			return null;
		}
		return new Integer(user_id_s);
	}

	public void setUser_id(Integer user_id) {
		if (user_id == null) {
			user_id_s = null;
			return;
		}
		user_id_s = String.valueOf(user_id);
	}

    public String getUser_id_s() {
		return user_id_s;
	}

	public void setUser_id_s(String user_id_s) {
		this.user_id_s = user_id_s;
	}

	@JsonIgnore
    public Integer getRole_id() {
		if (role_id_s == null || "".equals(role_id_s)) {
			return null;
		}
		return new Integer(role_id_s);
	}

	public void setRole_id(Integer role_id) {
		if (role_id == null) {
			role_id_s = null;
			return;
		}
		role_id_s = String.valueOf(role_id);
	}

    public String getRole_id_s() {
		return role_id_s;
	}

	public void setRole_id_s(String role_id_s) {
		this.role_id_s = role_id_s;
	}

	@JsonIgnore
	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
}


