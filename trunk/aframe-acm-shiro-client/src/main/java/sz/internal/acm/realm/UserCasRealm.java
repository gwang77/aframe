package sz.internal.acm.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

//CAS SSO Realm
public class UserCasRealm extends CasRealm {

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return RealmUtil.queryForAuthorizationInfo(principals);
    }

}
