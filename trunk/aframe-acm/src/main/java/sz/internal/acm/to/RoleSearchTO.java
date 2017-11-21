package sz.internal.acm.to;

import sz.internal.common.base.to.BaseSearchTO;

public class RoleSearchTO extends BaseSearchTO {
	
	private String name_like;
	
	private String app_id;
	
	public String getName_like() {
		return name_like;
	}

	public void setName_like(String name_like) {
		this.name_like = name_like;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
}


