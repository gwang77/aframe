package sz.internal.acm.shiro.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CasExtFilter extends CasFilter {
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        Cookie cookie = new Cookie("auth-token","1");
        HttpServletResponse rs = (HttpServletResponse) response;
        rs.addCookie(cookie);
        issueSuccessRedirect(request, rs);
        return false;
    }
}
