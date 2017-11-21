package sz.internal.acm.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.acm.ACMConfigDataMgr;
import sz.internal.acm.ACMConstants;
import sz.internal.acm.mapper.UserMapper;
import sz.internal.acm.to.PermissionTO;
import sz.internal.acm.to.RoleTO;
import sz.internal.acm.to.UserRoleTO;
import sz.internal.acm.to.UserTO;
import sz.internal.acm.util.ACMSecurityUtil;
import sz.internal.common.SystemConfig;
import sz.internal.common.base.service.BaseService;
import sz.internal.common.base.to.BaseTO;
import sz.internal.common.component.code.CodeConstant;
import sz.internal.common.component.code.CodeMgr;
import sz.internal.common.component.code.to.CodeTO;
import sz.internal.common.exception.BusinessException;
import sz.internal.common.util.ValidationUtil;

import javax.annotation.Resource;
import java.util.*;

@Component
public class UserService extends BaseService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    @Resource(name = "sz.internal.acm.mapper.UserMapper")
    public void setMapper(UserMapper mapper) {
        super.setMapper(mapper);
    }

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;

    public void insertUserTO(UserTO userTO) throws Exception {
        prepareEncryptPassword(userTO);
        if (!StringUtils.isEmpty(userTO.getEmail())) {
            if (!ValidationUtil.isEmail(userTO.getEmail())) {
                throw new BusinessException("errors_email");
            }
        }
        checkDuplicateUser(userTO);
        if (StringUtils.isEmpty(userTO.getLocked())) {
            userTO.setLocked("N");
        }
        userTO.setRegister_date(new Date());
        insert(userTO);

        insertDefaultRole(userTO);
        ACMConfigDataMgr.clearCache();
    }

    public void checkDuplicateUser(UserTO userTO) throws Exception {
        UserTO userTOTmp = new UserTO();

        userTOTmp.setUsername(userTO.getUsername());
        List<UserTO> list = search(userTOTmp);

        if (list.size() >= 1) {
            if (userTO.getId() == null || !list.get(0).getId().equals(userTO.getId())) {
                throw new BusinessException("errors_user_exist");
            }
        }
    }

    private void insertDefaultRole(UserTO userTO) throws Exception {
        String roleName = userTO.getDefault_role();

        if (StringUtils.isEmpty(roleName)) {
            roleName = getDefaultRole(userTO);
        }
        if (StringUtils.isEmpty(roleName)) {
            return;
        }
        RoleTO roleTO = new RoleTO();
        String[] roles = roleName.split(",");
        for (String role : roles) {
            if (!StringUtils.hasText(role)) {
                continue;
            }
            String[] roleDefine = role.split(":");
            if (roleDefine.length > 1) {
                String appid = roleDefine[0];
                String rName = roleDefine[1];
                if (StringUtils.hasText(appid) && !"*".equals(appid)) {
                    roleTO.setApp_id(role);
                }
                if (!StringUtils.hasText(rName)) {
                    continue;
                }
                roleTO.setRole(rName.trim());
            } else {
                roleTO.setRole(roleDefine[0]);
            }
            List roleList = roleService.search(roleTO);
            for (int i = 0; roleList != null && i < roleList.size(); i++) {
                roleTO = (RoleTO) roleList.get(i);
                UserRoleTO userRoleTO = new UserRoleTO();
                userRoleTO.setUser_id(userTO.getId());
                userRoleTO.setRole_id(roleTO.getId());
                userRoleService.insert(userRoleTO);
            }
        }
    }

    private String getDefaultRole(UserTO userTO) {
        if (userTO == null) {
            return "";
        }
        String user_type = userTO.getUser_type();
        if (StringUtils.isEmpty(user_type)) {
            return "";
        }
        String key = user_type.startsWith("B") ? "acm.default.role" : "acm.default.role.register";
        if (StringUtils.isEmpty(user_type)) {
            return "";
        }
        return SystemConfig.getSystemConfigValue(key);
    }

    public void updateUserTO(UserTO userTO) throws Exception {
        if (!StringUtils.isEmpty(userTO.getEmail())) {
            if (!ValidationUtil.isEmail(userTO.getEmail())) {
                throw new BusinessException("errors_email");
            }
        }
        checkDuplicateUser(userTO);
        update(userTO);
        ACMConfigDataMgr.clearCache();
    }

    public void saveUserTO(UserTO userTO) throws Exception {
        if (StringUtils.isEmpty(userTO.getUsername())) {
            throw new BusinessException("errors_name_null");
        }
        if (!StringUtils.isEmpty(userTO.getEmail())) {
            if (!ValidationUtil.isEmail(userTO.getEmail())) {
                throw new BusinessException("errors_email");
            }
        }
        UserTO uTO = findUserTOByUsername(userTO.getUsername());
        if (uTO == null) {
            if (StringUtils.isEmpty(userTO.getLocked())) {
                userTO.setLocked("N");
            }
            userTO.setRegister_date(new Date());
            insert(userTO);
        }
        insertDefaultRole(userTO);
        ACMConfigDataMgr.clearCache();
    }

    public boolean changePassword(UserTO userTO) throws Exception {
        boolean flag = isMatchPassword(userTO.getUsername(), userTO.getPassword());
        if (!flag) {
            throw new BusinessException("errors_password");
        }
        UserTO newUserTO = findUserTOByUsername(userTO.getUsername());
        newUserTO.setPassword(userTO.getConfirm_password());
        prepareEncryptPassword(newUserTO);
        ((UserMapper) getMapper()).changePassword(newUserTO);
        return true;
    }

    public UserTO findUserTOByID(int userID) throws Exception {
        UserTO userTO = new UserTO();
        userTO.setId(userID);
        return (UserTO) find(userTO);
    }

    public UserTO findUserTOByUsername(String username) throws Exception {
        UserTO userTO = new UserTO();
        userTO.setUsername(username);
        List list = search(userTO);
        if (list == null || list.size() == 0) {
            return null;
        }
        return (UserTO) list.get(0);
    }

    public void prepareUserCode(UserTO userTO) throws Exception {
        if (userTO == null) {
            return;
        }
        CodeTO codeTO = CodeMgr.getCodeTO(ACMConstants.CODE_TYPE_USER_TYPE, userTO.getUser_type());
        userTO.setUser_type_code(codeTO);
        CodeTO codeTO2 = CodeMgr.getCodeTO(CodeConstant.CODE_TYPE_YES_NO, userTO.getLocked());
        userTO.setLocked_code(codeTO2);
    }

    private void prepareEncryptPassword(UserTO userTO) {
        if (userTO == null || StringUtils.isEmpty(userTO.getPassword())) {
            return;
        }
        String salt = ACMSecurityUtil.generateNewSalt();
        userTO.setSalt(salt);
        String encodedPassword = ACMSecurityUtil.encryptPassword(userTO.getPassword(), userTO.getCredentialsSalt());
        userTO.setPassword(encodedPassword);
    }

    public boolean isMatchPassword(String userName, String password) throws Exception {
        if (StringUtils.isEmpty(userName)) {
            logger.info("user name is empty");
            return false;
        }
        UserTO userTO = findUserTOByUsername(userName);
        if (userTO == null) {
            logger.info("user name is not existed");
            return false;
        }
        String encryptedPwd = ACMSecurityUtil.encryptPassword(password, userTO.getCredentialsSalt());
        return (encryptedPwd.equals(userTO.getPassword()));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //priv related
    public List<PermissionTO> getPermissionByUsername(String username, String app_id) throws Exception {
        List<PermissionTO> permissionList = new ArrayList<>();

        if (StringUtils.isEmpty(app_id)) {
            return permissionList;
        }

        UserTO userTO = findUserTOByUsername(username);
        if (userTO == null) {
            logger.info("user name is not existed");
            return new ArrayList<>();
        }
        int userID = userTO.getId();

        //User Roles' Permissions
        UserRoleTO userRoleTO = new UserRoleTO();
        userRoleTO.setUser_id(userID);
        userRoleTO.setApp_id(app_id);
        List<UserRoleTO> userRoleList = userRoleService.search(userRoleTO);
        for (UserRoleTO userRoleTOTmp : userRoleList) {
            List<PermissionTO> permissionTOList = roleService.getPermissionByRoleID(userRoleTOTmp.getRole_id());
            permissionList.addAll(permissionTOList);
        }

        //remove duplicate
        for (int i = permissionList.size() - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (permissionList.get(i).getId().equals(permissionList.get(j).getId())) {
                    permissionList.remove(i);
                    break;
                }
            }
        }

        return permissionList;
    }

    public Set findRoles(String userName, String app_id) throws Exception {
        Set<String> roleSet = new HashSet<String>();
        if (StringUtils.isEmpty(app_id)) {
            return roleSet;
        }

        UserTO userTO = findUserTOByUsername(userName);
        if (userTO == null) {
            return new HashSet();
        }
        int userID = userTO.getId();
        UserRoleTO userRoleTO = new UserRoleTO();
        userRoleTO.setUser_id(userID);
        userRoleTO.setApp_id(app_id);

        List<UserRoleTO> userRoleList = userRoleService.search(userRoleTO);
        for (UserRoleTO userRoleTOTmp : userRoleList) {
            RoleTO roleTO = new RoleTO();
            roleTO.setId(userRoleTOTmp.getRole_id());
            roleTO = (RoleTO) roleService.find(roleTO);
            roleSet.add(roleTO.getRole());
        }
        return roleSet;
    }

    public Set findPermissions(String userName, String app_id) throws Exception {
        Set<String> permissionSet = new HashSet<String>();
        if (StringUtils.isEmpty(app_id)) {
            return permissionSet;
        }

        List<PermissionTO> resourceList = getPermissionByUsername(userName, app_id);
        for (PermissionTO resourceTO : resourceList) {
            permissionSet.add(resourceTO.getPermission());
        }

        return permissionSet;
    }

    //assign priv
    public UserTO findUserTOWithPrivInfo(int userID, String app_id) throws Exception {
        UserTO userTO = new UserTO();
        userTO.setId(userID);
        userTO = (UserTO) find(userTO);
        if (StringUtils.isEmpty(app_id)) {
            return userTO;
        }
        List<RoleTO> roleTOList = roleService.findAssignedToUserRole(String.valueOf(userID), app_id);
        userTO.setRoleTOList(roleTOList);

        return userTO;
    }

    public void saveUserPriv(UserTO userTO) throws Exception {
        if (!validateSaveUserPriv(userTO)) {
            return;
        }
        List<RoleTO> roleTOList = userTO.getRoleTOList();

        int user_id = userTO.getId();
        String app_id = userTO.getApp_id();

        userRoleService.deleteByUserId(user_id, app_id);
        for (RoleTO roleTO : roleTOList) {
            UserRoleTO userRoleTO = new UserRoleTO();
            userRoleTO.setUser_id(user_id);
            userRoleTO.setRole_id(roleTO.getId());
            userRoleService.insert(userRoleTO);
        }
        ACMConfigDataMgr.clearCache();
    }

    private BaseTO getTOFromCache(BaseTO to, Map cache, BaseService service) throws Exception {
        if (cache.get(to.getId()) == null) {
            to = service.find(to);
            cache.put(to.getId(), to);
        }
        return (BaseTO) cache.get(to.getId());
    }

    private boolean validateSaveUserPriv(UserTO userTO) throws Exception {
        //check all priv items are in the same app_id
        Map<Integer, RoleTO> roleCache = new HashMap<Integer, RoleTO>();
        List<RoleTO> roleTOList = userTO.getRoleTOList();
        for (int i = 0; i < roleTOList.size() - 1; i++) {
            RoleTO itemTO = (RoleTO) getTOFromCache(roleTOList.get(i), roleCache, roleService);
            for (int j = i + 1; j < roleTOList.size(); j++) {
                RoleTO itemTO2 = (RoleTO) getTOFromCache(roleTOList.get(j), roleCache, roleService);
                if (!itemTO.getApp_id().equals(itemTO2.getApp_id())) {
                    throw new BusinessException("errors_role_app_difference");
                }
            }
        }
        return true;
    }

    public List<UserTO> findUserByUser_from() throws Exception {
        String user = getCurrUser();
        UserMapper mapper = (UserMapper) getMapper();
        return mapper.findUserByUser_from(user);
    }

    public List<UserTO> findUserNotUser_from() throws Exception {
        String user = getCurrUser();
        UserMapper mapper = (UserMapper) getMapper();
        return mapper.findUserNotUser_from(user);
    }

}
