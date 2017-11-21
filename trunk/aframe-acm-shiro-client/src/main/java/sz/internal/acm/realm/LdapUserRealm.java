package sz.internal.acm.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class LdapUserRealm extends JndiLdapRealm {
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return RealmUtil.queryForAuthorizationInfo(principals);
    }
}
