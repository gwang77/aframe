package sz.internal.common.component.auditlog.to;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import sz.internal.common.base.to.PageTO;
import sz.internal.common.util.DateUtil;

/**
 * 
 * @author yangdan
 *
 */

public class AuditLogTO {

	private Integer id;
	private String user_name;
	private String class_name;
	private String action;
	private Date audit_date;
	private String b_img;
	private String a_img;

	private String class_nameLike;
	private String actionLike;
	@JsonIgnore
	private Date start_date;
	@JsonIgnore
	private Date end_date;

	private String sortBy;
	private String sortType;
	private Integer totalSize;
	private boolean pagination;

	private PageTO pageTO = new PageTO();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public String getB_img() {
		return b_img;
	}

	public void setB_img(String b_img) {
		this.b_img = b_img;
	}

	public String getA_img() {
		return a_img;
	}

	public void setA_img(String a_img) {
		this.a_img = a_img;
	}

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
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
		if (start_date != null) {
			return sdf.format(start_date);
		}
		return null;
	}

	@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE)
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
		if (end_date != null) {
			return sdf.format(end_date);
		}
		return null;
	}

	@DateTimeFormat(pattern = DateUtil.DEFAULT_FORMAT_DATE)
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String convertSortBy(String sortBy) {
		return sortBy;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public PageTO getPageTO() {
		return pageTO;
	}

	public void setPageTO(PageTO pageTO) {
		this.pageTO = pageTO;
	}

	public String[] getPKFields() {
		return new String[] { "id" };
	}

}
