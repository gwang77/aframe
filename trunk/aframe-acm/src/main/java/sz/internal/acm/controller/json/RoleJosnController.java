package sz.internal.acm.controller.json;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.acm.service.PermissionService;
import sz.internal.acm.service.RoleService;
import sz.internal.acm.to.RoleSearchTO;
import sz.internal.acm.to.RoleTO;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.base.to.PageTO;

import java.util.List;

@RestController
@RequestMapping("/json/role")
public class RoleJosnController extends BaseController {
	private PermissionService permissionService;
	
	private PermissionService getPermissionService() {
        if (permissionService == null) {
        	permissionService = SpringContextHolder.getBean("permissionService");
        }
        return permissionService;
    }
	
	private RoleService roleService;
	
	private RoleService getRoleService() {
        if (roleService == null) {
        	roleService = SpringContextHolder.getBean("roleService");
        }
        return roleService;
    }
	 

    @RequestMapping(value = "/searchRole")
    public Object searchRole(@RequestBody RoleTO roleTO) throws Exception {
        return getRoleService().search(roleTO);
    }

    @RequestMapping("/searchRolePage")
    public Object searchRolePage(@RequestBody RoleSearchTO searchTO) throws Exception {
        PageTO pageTO = searchTO.getPageTO();
        RoleTO roleTO = new RoleTO();
        roleTO.setName_like(searchTO.getName_like());
        roleTO.setApp_id(searchTO.getApp_id());
        roleTO.setSortBy(searchTO.getSortBy());
        roleTO.setSortType(searchTO.getSortType());
        pageTO = getRoleService().searchPage(roleTO, pageTO);
        return pageTO;
    }

    @RequestMapping("/findRole/{id}")
    public Object findRole(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        RoleTO roleTO = new RoleTO();
        roleTO.setId(uID);
        roleTO = (RoleTO) getRoleService().findRole(roleTO);
        return roleTO;
    }

    @RequestMapping("/roleUnassignedPermission/{id}/{app_id}")
    public Object roleUnassignedPermission(@PathVariable String id, @PathVariable String app_id) throws Exception {
        if (id.equals("0")) {
            id = null;
        }
        List permissionList = getPermissionService().findUnassignedPermission(id, app_id);
        return permissionList;
    }

    @RequestMapping(value = "/createRole")
    public Object createRole(@RequestBody RoleTO roleTO) throws Exception {
        getRoleService().insertRole(roleTO);
        return roleTO;
    }

    @RequestMapping(value = "/updateRole")
    public Object updateRole(@RequestBody RoleTO roleTO) throws Exception {
        roleTO.setApp_id(roleTO.getApp_id());
        getRoleService().updateRole(roleTO);
        return roleTO;
    }

    @RequestMapping("/deleteRole/{id}")
    public Object deleteRole(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        RoleTO roleTO = new RoleTO();
        roleTO.setId(uID);
        getRoleService().deleteRole(roleTO);
        return "";
    }

}
