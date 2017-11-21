package sz.internal.acm;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.acm.to.ResourceInfoTO;
import sz.internal.acm.to.UserClientTO;
import sz.internal.acm.util.JsonUtils;
import sz.internal.acm.util.SecurityHttpUtil;

import java.net.URLEncoder;
import java.util.*;

public class ACMConfigData {
    private static final Logger logger = Logger.getLogger(ACMConfigData.class);

    private static String baseURL = SecurityHttpUtil.baseURL;
    private static String roles_url = baseURL + "/api/user/retrieveUserRoles?userName=";
    private static String permissions_url = baseURL + "/api/user/retrieveUserPermissions?userName=";
    private static String resources_url = baseURL + "/api/user/retrieveUserResources?userName=";

    private static String retrieve_user_url = baseURL + "/api/user/retrieveUserInfo?userName=";
    private static String match_pwd_url = baseURL + "/api/user/matchPwd?userName=";
    private static String insert_user_url = baseURL + "/api/user/saveUserInfo";

    public static Set<String> getUserRolesByUserName(String userName) {
        String app_id = UserUtils.getCurrApp_id();
        String url = roles_url + userName + "&app_id=" + app_id;
        String json_str = SecurityHttpUtil.getResponse(url);
        return (Set<String>) JsonUtils.readValue(json_str, Set.class);
    }

    public static Set<String> getUserPermissionsByUserName(String userName) {
        String app_id = UserUtils.getCurrApp_id();
        String url = permissions_url + userName + "&app_id=" + app_id;
        String json_str = SecurityHttpUtil.getResponse(url);
        return (Set<String>) JsonUtils.readValue(json_str, Set.class);
    }

    public static List<ResourceInfoTO> getUserResourcesByUserName(String userName) {
        List<ResourceInfoTO> resourceList = new ArrayList<ResourceInfoTO>();
        String app_id = UserUtils.getCurrApp_id();
        String url = resources_url + userName + "&app_id=" + app_id;
        String json_str = SecurityHttpUtil.getResponse(url);
        List list = (List) JsonUtils.readValue(json_str, ArrayList.class);
        for (Object item : list) {
            Map map = (Map) item;
            ResourceInfoTO resourceTO = new ResourceInfoTO();
            resourceTO.setApp_id(String.valueOf(map.get("app_id")));
            resourceTO.setName(String.valueOf(map.get("name")));
            resourceTO.setPermission(String.valueOf(map.get("permission")));
            resourceTO.setRequest_method(String.valueOf(map.get("request_method")));
            resourceTO.setUrl(String.valueOf(map.get("url")));
            resourceList.add(resourceTO);
        }
        return resourceList;
    }

    //User related info
    public static UserClientTO retrieveUserInfoFromDB(String userName) {
        try {
            String url = retrieve_user_url + URLEncoder.encode(userName, "utf-8");
            String json_str = SecurityHttpUtil.getResponse(url);
            return prepareUserTOFromJson(json_str);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static UserClientTO prepareUserTOFromJson(String json_str) {
        if (StringUtils.isEmpty(json_str)) {
            return null;
        }
        Map map = (Map) JsonUtils.readValue(json_str, HashMap.class);
        if (map == null) {
            return null;
        }
        String id = String.valueOf(map.get("id"));
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        UserClientTO userTO = new UserClientTO();
        userTO.setId(id);
        userTO.setUsername((String) map.get("username"));
        userTO.setPassword((String) map.get("password"));
        userTO.setCredentialsSalt((String) map.get("credentialsSalt"));
        userTO.setEmail((String) map.get("email"));
        userTO.setRealname((String) map.get("realname"));
        userTO.setStaff_id((String) map.get("staff_id"));
        userTO.setTel((String) map.get("tel"));

        userTO.setUser_type((String) map.get("user_type"));
        userTO.setLocked((String) map.get("locked"));
        return userTO;
    }

    public static boolean isMatchPassword(String userName, String pwd) {
        try {
            String url = match_pwd_url + URLEncoder.encode(userName, "utf-8") + "&pwd=" + URLEncoder.encode(pwd, "utf-8");
            String json_str = SecurityHttpUtil.getResponse(url);
            if (StringUtils.isEmpty(json_str)) {
                return false;
            }
            Map map = (Map) JsonUtils.readValue(json_str, HashMap.class);
            if (map == null) {
                logger.debug("result map is null");
                return false;
            }
            String result = (String) map.get("result");
            if (!"true".equals(result)) {
                logger.info("login failed: username:" + userName);
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static void saveUserInfoToDB(UserClientTO userTO) throws Exception {
        String url = insert_user_url;
        String user_json = JsonUtils.writeValue(userTO);
        Map map = (Map) JsonUtils.readValue(user_json, HashMap.class);

        String json_str = SecurityHttpUtil.post(url, map);
        logger.info(json_str);
    }

    public static void main(String[] args) {
        Set set = getUserRolesByUserName("wanggang@ncsi.com.cn");
        Set set2 = getUserPermissionsByUserName("wanggang@ncsi.com.cn");
        List list = getUserResourcesByUserName("wanggang@ncsi.com.cn");
        Object obj = retrieveUserInfoFromDB("wanggang@ncsi.com.cn");
        Object obj2 = isMatchPassword("wanggang", "123");
        System.out.println(set);
        System.out.println(set2);
        System.out.println(list);
        System.out.println(obj);
        System.out.println(obj2);
    }
}
