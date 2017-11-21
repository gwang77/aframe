package sz.internal.acm.cas;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.Principal;
import org.springframework.util.StringUtils;
import sz.internal.acm.util.ConfigPropertyUtils;
import sz.internal.acm.util.JsonUtils;

import javax.security.auth.login.FailedLoginException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class LoginAcceptUsersPasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private static final Logger logger = Logger.getLogger(LoginAcceptUsersPasswordAuthenticationHandler.class);

    private static String baseURL = ConfigPropertyUtils.getProperties("acm.api.base.url");
    private static String match_pwd_url = baseURL + "/api/user/matchPwd?userName=";

    public LoginAcceptUsersPasswordAuthenticationHandler() {
    }

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential) throws GeneralSecurityException, PreventedException {
        String username = credential.getUsername();
        String password = credential.getPassword();

        try {
            String url = match_pwd_url + URLEncoder.encode(username, "utf-8") + "&pwd=" + URLEncoder.encode(password, "utf-8");
            String json_str = HttpSecurity.getResponse(url);
            if (StringUtils.isEmpty(json_str)) {
                return null;
            }
            Map map = (Map) JsonUtils.readValue(json_str, HashMap.class);
            if (map == null) {
                return null;
            }
            String result = (String) map.get("result");
            if (!"true".equals(result)) {
                logger.info("login failed: username:" + username);
                throw new FailedLoginException();
            }
            logger.info("login success");

            Principal principal = this.principalFactory.createPrincipal(username);

            return createHandlerResult(credential, principal, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new FailedLoginException();
        }
    }
}