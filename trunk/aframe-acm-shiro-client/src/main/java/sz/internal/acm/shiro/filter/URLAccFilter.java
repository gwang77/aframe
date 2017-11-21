package sz.internal.acm.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import sz.internal.acm.ACMConfigData;
import sz.internal.acm.to.ResourceInfoTO;
import sz.internal.acm.util.http.HTTPUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class URLAccFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        List<ResourceInfoTO> resourceList = ACMConfigData.getUserResourcesByUserName(username);
        String requestURI = getPathWithinApplication(request);

        String request_method = "";
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            request_method = httpRequest.getMethod();
        }
        for (ResourceInfoTO resourceTO : resourceList) {
            String url = resourceTO.getUrl();
            String method_priv = resourceTO.getRequest_method();
            if (pathsMatch(url, requestURI) && methodMatch(request_method, method_priv)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                HTTPUtil.responseInfo(WebUtils.toHttp(response), String.valueOf(HttpServletResponse.SC_FORBIDDEN), HttpServletResponse.SC_FORBIDDEN);
            }
        }
        return false;
    }

    protected boolean methodMatch(String req_method, String priv_method) {
        return StringUtils.isEmpty(req_method) || StringUtils.isEmpty(priv_method) || priv_method.equals("*") || req_method.equalsIgnoreCase(priv_method);
    }
}