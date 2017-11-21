package sz.internal.acm.controller.json;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sz.internal.acm.ACMConstants;
import sz.internal.acm.service.RoleService;
import sz.internal.acm.service.UserService;
import sz.internal.acm.to.UserSearchTO;
import sz.internal.acm.to.UserTO;
import sz.internal.common.SpringContextHolder;
import sz.internal.common.SystemConfig;
import sz.internal.common.base.to.PageTO;

import java.util.List;

@RestController
@RequestMapping("/json/user")
public class UserJsonController {
    private UserService userService;

    private UserService getUserService() {
        if (userService == null) {
            userService = SpringContextHolder.getBean("userService");
        }
        return userService;
    }

    private RoleService roleService;

    private RoleService getRoleService() {
        if (roleService == null) {
            roleService = SpringContextHolder.getBean("userService");
        }
        return roleService;
    }

    @RequestMapping(value = "/changePassword")
    public Object changePassword(@RequestBody UserTO userTO) throws Exception {
        return getUserService().changePassword(userTO);
    }

    @RequestMapping(value = "/searchUser")
    public Object searchUser(@RequestBody UserTO userTO) throws Exception {
        List list = getUserService().search(userTO);
        for (Object toTmp : list) {
            prepareUserCode((UserTO) toTmp);
        }
        return list;
    }

    @RequestMapping("/searchUserPage")
    public Object searchUserPage(@RequestBody UserSearchTO searchTO) throws Exception {
        PageTO pageTO = searchTO.getPageTO();
        UserTO userTO = new UserTO();
        userTO.setUsername_like(searchTO.getUsername_like());
        userTO.setSortBy(searchTO.getSortBy());
        userTO.setSortType(searchTO.getSortType());
        pageTO = getUserService().searchPage(userTO, pageTO);
        List list = pageTO.getList();
        for (Object toTmp : list) {
            prepareUserCode((UserTO) toTmp);
        }
        return pageTO;
    }

    @RequestMapping("/findUser/{id}")
    public Object findUser(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        UserTO userTO = new UserTO();
        userTO.setId(uID);
        userTO = (UserTO) getUserService().find(userTO);
        prepareUserCode(userTO);
        return userTO;
    }

    @RequestMapping(value = "/findUserByName/{userName}")
    public Object findUserByName(@PathVariable String userName) throws Exception {
        UserTO userTO = getUserService().findUserTOByUsername(userName);
        if (userTO != null) {
            prepareUserCode(userTO);
            return userTO;
        }
        return "";
    }

    @RequestMapping(value = "/createUser")
    public Object createUser(@RequestBody UserTO userTO) throws Exception {
        userTO.setPassword(userTO.getConfirm_password());
        userTO.setUser_type(ACMConstants.USER_TYPE_PASSWORD);
        getUserService().insertUserTO(userTO);

        return userTO;
    }

    @RequestMapping(value = "/updateUser")
    public Object updateUser(@RequestBody UserTO userTO) throws Exception {
        getUserService().updateUserTO(userTO);

        return userTO;
    }

    @RequestMapping("/deleteUser/{id}")
    public Object deleteUser(@PathVariable String id) throws Exception {
        int uID = Integer.parseInt(id);
        UserTO userTO = new UserTO();
        userTO.setId(uID);

        getUserService().delete(userTO);

        return "";
    }

    private void prepareUserCode(UserTO userTO) throws Exception {
        getUserService().prepareUserCode(userTO);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //assign priv
    @RequestMapping("/findUserWithPriv/{id}/{app_id}")
    public Object findUserWithPrivInfo(@PathVariable String id, @PathVariable String app_id) throws Exception {
        int uID = Integer.parseInt(id);
        UserTO userTO = new UserTO();
        userTO.setId(uID);
        userTO = getUserService().findUserTOWithPrivInfo(uID, app_id);
        prepareUserCode(userTO);
        return userTO;
    }

    @RequestMapping("/userUnassignedRole/{id}/{app_id}")
    public Object userUnassignedRole(@PathVariable String id, @PathVariable String app_id) throws Exception {
        if (id.equals("0") || StringUtils.isEmpty(id)) {
            id = null;
        }
        return getRoleService().findUnassignedToUserRole(id, app_id);
    }

    @RequestMapping(value = "/saveUserPriv")
    public Object saveUserPriv(@RequestBody UserTO userTO) throws Exception {
        getUserService().saveUserPriv(userTO);

        return userTO;
    }

    @RequestMapping(value = "/findUserByUser_from")
    public Object findUserByUser_from() throws Exception {
        List list = getUserService().findUserByUser_from();
        return list;
    }

    @RequestMapping(value = "/findUserNotUser_from")
    public Object findUserNotUser_from() throws Exception {
        List list = getUserService().findUserNotUser_from();
        return list;
    }
}
