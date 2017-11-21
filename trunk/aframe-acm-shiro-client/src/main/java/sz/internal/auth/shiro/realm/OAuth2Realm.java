package sz.internal.auth.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import sz.internal.acm.realm.RealmUtil;
import sz.internal.auth.shiro.filter.OAuth2RealmToken;

public class OAuth2Realm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2RealmToken;//表示此Realm只支持OAuth2Token类型
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return RealmUtil.queryForAuthorizationInfo(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OAuth2RealmToken oAuth2RealmToken = (OAuth2RealmToken) token;
        String code = oAuth2RealmToken.getAuthCode();
        String username = extractUsername(code);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, code, getName());
        return authenticationInfo;
    }

    public String extractUsername(String code) {
        //get user name
        return "";
    }
}
