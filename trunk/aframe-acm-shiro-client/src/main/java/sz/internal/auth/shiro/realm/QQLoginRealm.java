package sz.internal.auth.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import sz.internal.acm.util.ConfigPropertyUtils;
import sz.internal.auth.shiro.filter.OAuth2RealmToken;
import sz.internal.auth.util.QQOAuth2Util;
import sz.internal.auth.util.SNSUserInfo;

public class QQLoginRealm extends OAuth2Realm {
    private static String app_id = ConfigPropertyUtils.getProperties("qq.app.id");
    private static String security_id = ConfigPropertyUtils.getProperties("qq.app.secret");

    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2RealmToken && "QQ".equals(((OAuth2RealmToken) token).getType());
    }

    public String extractUsername(String code) {
        String app_id = "";
        String security_id = "";
        SNSUserInfo userInfo = QQOAuth2Util.getQQUserInfo(app_id, security_id, code, app_id);
        if (userInfo == null) {
            throw new AuthenticationException("get user info error!");
        }
        return userInfo.getOpenId();
    }
}
