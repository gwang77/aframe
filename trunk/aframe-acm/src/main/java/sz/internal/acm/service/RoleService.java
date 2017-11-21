package sz.internal.acm.service;

import org.springframework.stereotype.Component;
import sz.internal.acm.mapper.RoleMapper;
import sz.internal.acm.to.PermissionTO;
import sz.internal.acm.to.RolePermissionTO;
import sz.internal.acm.to.RoleTO;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.exception.BusinessException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleService extends BaseService {

    private RoleTO role;

    @Resource(name = "sz.internal.acm.mapper.RoleMapper")
    public void setMapper(RoleMapper mapper) {
        super.setMapper(mapper);
    }

    @Resource
    RolePermissionService rolePermissionService;

    @Resource
    PermissionService permissionService;

    public List<PermissionTO> getPermissionByRoleID(Integer roleID) throws Exception {
        List<PermissionTO> permissionList = new ArrayList<>();
        RolePermissionTO rolePermissionTO = new RolePermissionTO();
        rolePermissionTO.setRole_id(roleID);
        List<RolePermissionTO> list = rolePermissionService.search(rolePermissionTO);
        for (RolePermissionTO rolePermissionTOTmp : list) {
            PermissionTO permissionTO = new PermissionTO();
            permissionTO.setId(rolePermissionTOTmp.getPermission_id());
            permissionTO = (PermissionTO) permissionService.find(permissionTO);
            permissionList.add(permissionTO);
        }
        return permissionList;
    }

    public RoleTO findRole(RoleTO roleTO) throws Exception {
        role = (RoleTO) find(roleTO);
        List<PermissionTO> list = permissionService.findAssignedPermission(role.getId().toString());
        role.setPermissionList(list);
        return role;
    }

    public void insertRole(RoleTO roleTO) throws Exception {
        validate(roleTO);
        insert(roleTO);
        insertRolePermission(roleTO);
    }

    public void updateRole(RoleTO roleTO) throws Exception {
        validate(roleTO);
        update(roleTO);
        insertRolePermission(roleTO);
    }

    public Object deleteRole(RoleTO roleTO) throws Exception {
        if (roleTO == null || roleTO.getId() <= 0) {
            return "";
        }
        rolePermissionService.deleteByRoleId(roleTO.getId().toString());
        delete(roleTO);

        return "";
    }

    private void insertRolePermission(RoleTO roleTO) throws Exception {
        RolePermissionTO rolePermissionTO = new RolePermissionTO();
        List<PermissionTO> permissionList = roleTO.getPermissionList();

        if (permissionList.size() == 0) {
            throw new BusinessException("errors_permissionList_null");
        }
        rolePermissionTO.setRole_id(roleTO.getId());
        rolePermissionService.deleteByRoleId(rolePermissionTO.getRole_id_s());
        for (int i = 0; i < permissionList.size(); i++) {
            String permission_id = permissionList.get(i).getId().toString();
            rolePermissionTO.setPermission_id_s(permission_id);
            rolePermissionTO.setId(null);
            rolePermissionService.insert(rolePermissionTO);
        }
    }

    public void validate(RoleTO roleTO) throws Exception {
        String name = roleTO.getRole();
        String app_id = roleTO.getApp_id();
        if (name == null) {
            throw new BusinessException("errors_name_null");
        } else {
            if (name.length() > 100) {
                throw new BusinessException("errors_name_size");
            } else {
                checkDuplicate(roleTO);
            }
        }
        if (app_id == null) {
            throw new BusinessException("errors_app_id_null");
        } else {
            if (app_id.length() > 100) {
                throw new BusinessException("errors_app_id_size");
            }
        }
    }

    public void checkDuplicate(RoleTO roleTO) throws Exception {

        List<RoleTO> list = new ArrayList<RoleTO>();
        RoleTO roleTOTmp = new RoleTO();

        roleTOTmp.setRole(roleTO.getRole());
        roleTOTmp.setApp_id(roleTO.getApp_id());
        list = search(roleTOTmp);

        if (list.size() >= 1) {
            if (roleTO.getId() == null || !list.get(0).getId().equals(roleTO.getId())) {
                throw new BusinessException("errors_role_exist");
            }
        }
    }

    public List<RoleTO> findAssignedToUserRole(String user_id, String app_id) throws Exception {
        RoleMapper mapper = (RoleMapper) getMapper();
        return mapper.findAssignedToUser(user_id, app_id);
    }

    public List<RoleTO> findUnassignedToUserRole(String user_id, String app_id) throws Exception {
        RoleMapper mapper = (RoleMapper) getMapper();
        return mapper.findUnassignedToUser(user_id, app_id);
    }

}
