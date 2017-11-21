package sz.internal.acm.shiro.filter;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import sz.internal.acm.ACMConfigData;
import sz.internal.acm.LDAPUserUtil;
import sz.internal.acm.to.UserClientTO;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * User: wanggang
 * Date: 5/30/16
 * Time: 5:01 PM
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger logger = Logger.getLogger(LoginFormAuthenticationFilter.class);

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        doAfterLoginSuccess(token, subject, request, response);
        if ((request instanceof HttpServletRequest) && (!((HttpServletRequest) request).getHeader("accept").contains("application/json")
                && (((HttpServletRequest) request).getHeader("X-Requested-With") == null || !((HttpServletRequest) request).getHeader("X-Requested-With").contains("XMLHttpRequest")))) {
            super.onLoginSuccess(token, subject, request, response);
        }
        return true;
    }

    protected boolean doAfterLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        handleUserInfo(token);
        return true;
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && (WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD) || WebUtils.toHttp(request).getMethod().equalsIgnoreCase(GET_METHOD));
    }

    //if login user is ldap user, check DB, if not existed, insert to DB.
    private void handleUserInfo(AuthenticationToken token) throws Exception {
        String principal = (String) token.getPrincipal();
        Object credentials = token.getCredentials();

        if (StringUtils.isEmpty(principal)) {
            return;
        }

        UserClientTO user = null;
        try {
            user = ACMConfigData.retrieveUserInfoFromDB(principal);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (user == null) {
            UserClientTO userTO = LDAPUserUtil.getUserInfoFromLdap(principal, credentials);
            if (userTO != null) {
                ACMConfigData.saveUserInfoToDB(userTO);
            }
        }
    }

}
