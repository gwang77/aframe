package sz.internal.auth.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import sz.internal.acm.util.ConfigPropertyUtils;
import sz.internal.auth.shiro.filter.OAuth2RealmToken;
import sz.internal.auth.util.SNSUserInfo;
import sz.internal.auth.util.WXOAuth2Util;

public class WXLoginRealm extends OAuth2Realm {
    private static String app_id = ConfigPropertyUtils.getProperties("wx.app.id");
    private static String security_id = ConfigPropertyUtils.getProperties("wx.app.secret");

    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2RealmToken && "WX".equals(((OAuth2RealmToken) token).getType());
    }

    public String extractUsername(String code) {
        SNSUserInfo userInfo = WXOAuth2Util.getWXUserInfo(app_id, security_id, code);
        if (userInfo == null) {
            throw new AuthenticationException("get user info error!");
        }
        return userInfo.getOpenId();
    }
}
