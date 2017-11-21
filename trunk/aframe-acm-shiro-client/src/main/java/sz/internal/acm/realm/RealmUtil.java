package sz.internal.acm.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import sz.internal.acm.ACMConfigData;

public class RealmUtil {
    private static final Logger logger = Logger.getLogger(LdapUserRealm.class);

    public static AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            authorizationInfo.setRoles(ACMConfigData.getUserRolesByUserName(username));
            authorizationInfo.setStringPermissions(ACMConfigData.getUserPermissionsByUserName(username));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return authorizationInfo;
    }
}
