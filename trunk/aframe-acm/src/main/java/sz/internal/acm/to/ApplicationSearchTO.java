package sz.internal.acm.to;

import sz.internal.common.base.to.BaseSearchTO;

public class ApplicationSearchTO extends BaseSearchTO {
	private String name;
	private String name_like;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_like() {
		return name_like;
	}

	public void setName_like(String name_like) {
		this.name_like = name_like;
	}
	
}


