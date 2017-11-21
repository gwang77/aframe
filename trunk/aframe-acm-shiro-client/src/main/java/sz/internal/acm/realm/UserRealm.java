package sz.internal.acm.realm;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sz.internal.acm.ACMConfigData;
import sz.internal.acm.to.UserClientTO;

@Component
public class UserRealm extends AuthorizingRealm {
    private static final Logger logger = Logger.getLogger(UserRealm.class);

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return RealmUtil.queryForAuthorizationInfo(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        UserClientTO user = null;
        try {
            user = ACMConfigData.retrieveUserInfoFromDB(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (user == null || StringUtils.isEmpty(username)) {
            throw new UnknownAccountException();// no account
        }

        if ("Y".equals(user.getLocked())) {
            throw new LockedAccountException(); // account locked
        }

        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
    }

}
