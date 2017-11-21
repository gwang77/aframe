package sz.internal.acm.cas;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.LdapAuthenticationHandler;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.ldaptive.*;
import org.ldaptive.auth.Authenticator;
import org.springframework.util.StringUtils;
import sz.internal.acm.to.UserTO;
import sz.internal.acm.util.ConfigPropertyUtils;
import sz.internal.acm.util.JsonUtils;
import sz.internal.common.SpringContextHolder;

import javax.validation.constraints.NotNull;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class LoginAcceptUsersAuthenticationHandler extends LdapAuthenticationHandler {
    private static final Logger logger = Logger.getLogger(LoginAcceptUsersAuthenticationHandler.class);

    private static String baseURL = ConfigPropertyUtils.getProperties("acm.api.base.url");
    private static String retrieve_user_url = baseURL + "/api/user/retrieveUserInfo?userName=";
    private static String insert_user_url = baseURL + "/api/user/saveUserInfo";

    public LoginAcceptUsersAuthenticationHandler(@NotNull Authenticator authenticator) {
        super(authenticator);
    }

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential upc) throws GeneralSecurityException, PreventedException {
        HandlerResult result = super.authenticateUsernamePasswordInternal(upc);

        handleUserInfo(upc.getUsername(), upc.getPassword());

        return result;
    }

    //if login user is ldap user, check DB, if not existed, insert to DB.
    private static void handleUserInfo(String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            return;
        }
        try {
            UserTO user = retrieveUserInfoFromDB(userName);

            if (user == null) {
                UserTO userTO = retrieveUserInfoFromLDAP(userName);
                if (userTO != null) {
                    saveUserInfoToDB(userTO);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static UserTO retrieveUserInfoFromLDAP(String userName) throws Exception {
        ConnectionFactory connectionFactory = SpringContextHolder.getBean("pooledLdapConnectionFactory");
        SearchExecutor executor = new SearchExecutor();
        executor.setBaseDn("DC=ncs,DC=corp,DC=int-ads");
        SearchResult result = executor.search(connectionFactory, "(mail=" + userName + ")").getResult();
        LdapEntry entry = result.getEntry();
        return prepareUserTO(entry);
    }

    private static UserTO prepareUserTO(LdapEntry entry) throws Exception {
        if (entry == null) {
            return null;
        }
        UserTO userTO = new UserTO();

        String email = getAttributeValue(entry, "mail");
        String accountName = getAttributeValue(entry, "sAMAccountName");
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(accountName)) {
            return null;
        }
        userTO.setUsername(email);
        userTO.setEmail(email);
        userTO.setRealname(getAttributeValue(entry, "name"));
        userTO.setStaff_id(getAttributeValue(entry, "employeeID"));
        userTO.setTel(getAttributeValue(entry, "telephoneNumber"));

        userTO.setUser_type("L");
        userTO.setLocked("N");
        return userTO;
    }

    private static String getAttributeValue(LdapEntry entry, String attName) {
        LdapAttribute att = entry.getAttribute(attName);
        return att.getStringValue();
    }

    private static UserTO retrieveUserInfoFromDB(String userName) throws Exception {
        String url = retrieve_user_url + URLEncoder.encode(userName, "utf-8");
        String json_str = HttpSecurity.getResponse(url);
        return prepareUserTOFromJson(json_str);
    }

    private static UserTO prepareUserTOFromJson(String json_str) throws Exception {
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
        UserTO userTO = new UserTO();
        userTO.setId(id);
        userTO.setUsername((String) map.get("username"));
        userTO.setEmail((String) map.get("email"));
        userTO.setRealname((String) map.get("realname"));
        userTO.setStaff_id((String) map.get("staff_id"));
        userTO.setTel((String) map.get("tel"));

        userTO.setUser_type("L");
        userTO.setLocked("N");
        return userTO;
    }

    private static void saveUserInfoToDB(UserTO userTO) throws Exception {
        String url = insert_user_url;
        String user_json = JsonUtils.writeValue(userTO);
        Map map = (Map) JsonUtils.readValue(user_json, HashMap.class);

        String json_str = HttpSecurity.post(url, map);
        logger.info(json_str);
    }
}