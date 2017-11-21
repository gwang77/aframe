package sz.internal.auth.shiro.filter;

import org.apache.shiro.authc.AuthenticationToken;

public class OAuth2RealmToken implements AuthenticationToken {
    private String authCode;
    private String principal;
    private String type; //WX or QQ

    public OAuth2RealmToken(String authCode, String type) {
        this.authCode = authCode;
        this.type = type;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
