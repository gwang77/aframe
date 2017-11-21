package sz.internal.acm.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sz.internal.common.base.to.BaseTO;

import java.util.List;

public class RoleTO extends BaseTO {
    private String role;
    @JsonIgnore
    private String name_like;
    private String app_id;
    private String description;
    private String available;

    private List<PermissionTO> permissionList;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public List<PermissionTO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<PermissionTO> permissionList) {
        this.permissionList = permissionList;
    }
}


