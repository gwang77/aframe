package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class RolePermissionTO extends BaseTO {
	private String role_id_s;
	private String permission_id_s;

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
    public Integer getPermission_id() {
		if (permission_id_s == null || "".equals(permission_id_s)) {
			return null;
		}
		return new Integer(permission_id_s);
	}

	public void setPermission_id(Integer permission_id) {
		if (permission_id == null) {
			permission_id_s = null;
			return;
		}
		permission_id_s = String.valueOf(permission_id);
	}

    public String getPermission_id_s() {
		return permission_id_s;
	}

	public void setPermission_id_s(String permission_id_s) {
		this.permission_id_s = permission_id_s;
	}

}


