package sz.internal.acm.to;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sz.internal.common.base.to.BaseTO;
import sz.internal.common.util.DateUtil;

public class DelegateTO extends BaseTO{

	private String user_from;
	private String user_to;
	private List<RoleTO> user_to_roleList;
	private String date_from_s;
	private String date_to_s;
	private List<UserTO> userList;
	
	public List<RoleTO> getUser_to_roleList() {
		return user_to_roleList;
	}
	public void setUser_to_roleList(List<RoleTO> user_to_roleList) {
		this.user_to_roleList = user_to_roleList;
	}
	public List<UserTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserTO> userList) {
		this.userList = userList;
	}
	public String getUser_from() {
		return user_from;
	}
	public void setUser_from(String user_from) {
		this.user_from = user_from;
	}
	public String getUser_to() {
		return user_to;
	}
	public void setUser_to(String user_to) {
		this.user_to = user_to;
	}
	
	@JsonIgnore
	public Date getDate_from() {
		if (date_from_s == null || "".equals(date_from_s)) {
            return null;
        }
        return DateUtil.parseDate(date_from_s);
	}
	public void setDate_from(Date date_from) {
		if (date_from == null) {
			date_from_s = null;
            return;
        }
		date_from_s = DateUtil.formatDate(date_from);
	}
	public String getDate_from_s() {
		return date_from_s;
	}
	public void setDate_from_s(String date_from_s) {
		this.date_from_s = date_from_s;
	}
	@JsonIgnore
	public Date getDate_to() {
		if (date_to_s == null || "".equals(date_to_s)) {
            return null;
        }
        return DateUtil.parseDate(date_to_s);
	}
	public void setDate_to(Date date_to) {
		if (date_to == null) {
			date_to_s = null;
            return;
        }
		date_to_s = DateUtil.formatDate(date_to);
	}
	
	public String getDate_to_s() {
		return date_to_s;
	}
	public void setDate_to_s(String date_to_s) {
		this.date_to_s = date_to_s;
	}
	
}
