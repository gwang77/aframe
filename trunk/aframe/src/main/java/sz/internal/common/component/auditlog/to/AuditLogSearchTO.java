package sz.internal.common.component.auditlog.to;

import sz.internal.common.base.to.BaseSearchTO;

public class AuditLogSearchTO extends BaseSearchTO {
	private String class_nameLike;
	private String actionLike;
	private String start_date;
	private String end_date;

	public String getClass_nameLike() {
		return class_nameLike;
	}

	public void setClass_nameLike(String class_nameLike) {
		this.class_nameLike = class_nameLike;
	}

	public String getActionLike() {
		return actionLike;
	}

	public void setActionLike(String actionLike) {
		this.actionLike = actionLike;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
