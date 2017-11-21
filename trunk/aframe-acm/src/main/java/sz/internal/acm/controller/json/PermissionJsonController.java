package sz.internal.acm.controller.json;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sz.internal.acm.service.PermissionService;
import sz.internal.acm.to.PermissionSearchTO;
import sz.internal.acm.to.PermissionTO;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.base.controller.BaseController;
import sz.internal.common.base.to.PageTO;

@RestController
@RequestMapping("/json/permission")
public class PermissionJsonController extends BaseController {
	
	private PermissionService permissionService;
	
	private PermissionService getPermissionService() {
        if (permissionService == null) {
        	permissionService = SpringContextHolder.getBean("permissionService");
        }
        return permissionService;
    }

    @RequestMapping(value = "/searchPermission")
    public Object searchPermission(@RequestBody PermissionTO permissionTO) throws Exception {
        return getPermissionService().search(permissionTO);
    }

    @RequestMapping("/searchPermissionPage")
    public Object searchPermissionPage(@RequestBody PermissionSearchTO searchTO) throws Exception {
        PageTO pageTO = searchTO.getPageTO();
        PermissionTO permissionTO = new PermissionTO();
        permissionTO.setName_like(searchTO.getName_like());
        permissionTO.setApp_id(searchTO.getApp_id());
        permissionTO.setSortBy(searchTO.getSortBy());
        permissionTO.setSortType(searchTO.getSortType());
        pageTO = getPermissionService().searchPage(permissionTO, pageTO);
        return pageTO;
    }

    @RequestMapping("/findPermission/{id}")
    public Object findPermission(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        PermissionTO permissionTO = new PermissionTO();
        permissionTO.setId(uID);
        permissionTO = (PermissionTO) getPermissionService().find(permissionTO);
        return permissionTO;
    }

    @RequestMapping(value = "/createPermission")
    public Object createPermission(@RequestBody PermissionTO permissionTO) throws Exception {
        getPermissionService().insertPermission(permissionTO);

        return permissionTO;
    }

    @RequestMapping(value = "/updatePermission")
    public Object updatePermission(@RequestBody PermissionTO permissionTO) throws Exception {
        getPermissionService().updatePermission(permissionTO);

        return permissionTO;
    }

    @RequestMapping("/deletePermission/{id}")
    public Object deletePermission(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        PermissionTO permissionTO = new PermissionTO();
        permissionTO.setId(uID);

        getPermissionService().delete(permissionTO);

        return "";
    }


}

