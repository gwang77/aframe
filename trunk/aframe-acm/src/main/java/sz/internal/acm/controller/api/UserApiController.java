package sz.internal.acm.controller.api;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sz.internal.acm.ACMConfigDataMgr;
import sz.internal.acm.Constants;
import sz.internal.acm.service.UserService;
import sz.internal.acm.to.UserTO;
import sz.internal.common.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private UserService userService;

    private UserService getUserService() {
        if (userService == null) {
            userService = SpringContextHolder.getBean("userService");
        }
        return userService;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //api
    @RequestMapping(value = "/retrieveUserInfo")
    public Object retrieveUserInfo(@RequestParam String userName) throws Exception {
        if (StringUtils.isEmpty(userName)) {
            return "";
        }
        return findUserByName(userName);
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

    private void prepareUserCode(UserTO userTO) throws Exception {
        getUserService().prepareUserCode(userTO);
    }

    @RequestMapping(value = "/matchPwd")
    public Object matchPwd(@RequestParam String userName, @RequestParam String pwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(pwd)) {
            return map;
        }
        boolean isMatch = getUserService().isMatchPassword(userName, pwd);
        map.put("result", isMatch ? "true" : "false");
        return map;
    }

    @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
    public Object saveUserInfo(@RequestBody UserTO userTO) throws Exception {
        if (userTO == null) {
            return "";
        }
        if (StringUtils.isEmpty(userTO.getUser_type())) {
            userTO.setUser_type(Constants.USER_TYPE_USER_PASSWORD);
        }
        getUserService().saveUserTO(userTO);
        return "";
    }

    @RequestMapping(value = "/retrieveUserRoles")
    public Object retrieveUserRoles(@RequestParam String userName, @RequestParam String app_id) throws Exception {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(app_id)) {
            return "";
        }
        return ACMConfigDataMgr.getUserRolesByUserName(userName, app_id);
    }

    @RequestMapping(value = "/retrieveUserPermissions")
    public Object retrieveUserPermissions(@RequestParam String userName, @RequestParam String app_id) throws Exception {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(app_id)) {
            return "";
        }
        return ACMConfigDataMgr.getUserPermissionsByUserName(userName, app_id);
    }

    @RequestMapping(value = "/retrieveUserResources")
    public Object retrieveUserResources(@RequestParam String userName, @RequestParam String app_id) throws Exception {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(app_id)) {
            return "";
        }
        return ACMConfigDataMgr.getUserResourcesByUserName(userName, app_id);
    }

}
