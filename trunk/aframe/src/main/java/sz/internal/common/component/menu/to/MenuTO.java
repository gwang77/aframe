package sz.internal.common.component.menu.to;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.code.to.CodeTO;

public class MenuTO extends BaseTO {
	@NotBlank(message = "caption.null.error")
	private String caption;
	//@NotBlank(message = "url.null.error")
	private String url;
	//@NotBlank(message = "parent_id.null.error")
	private String parent_id;
	private List<MenuTO> list;
    private String permission;
    private String available;
    private CodeTO available_code;
    private String style;
    @NotBlank(message = "seqNo.null.error")
    private String seq_no;

	private int level;
	private String canDelete;

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


	public List<MenuTO> getList() {
		return list;
	}

	public void setList(List<MenuTO> list) {
		this.list = list;
	}

	public MenuTO(String caption, String url, String parent_id) {
		super();
		this.caption = caption;
		this.url = url;
		this.parent_id = parent_id;
	}

	public MenuTO() {
		super();
	}


	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public CodeTO getAvailable_code() {
		return available_code;
	}

	public void setAvailable_code(CodeTO available_code) {
		this.available_code = available_code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(String canDelete) {
		this.canDelete = canDelete;
	}
}
