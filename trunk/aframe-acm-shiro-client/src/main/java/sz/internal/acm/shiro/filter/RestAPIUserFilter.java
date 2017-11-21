package sz.internal.acm.shiro.filter;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import sz.internal.acm.util.http.HTTPUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class RestAPIUserFilter extends UserFilter {
    private static final Logger logger = Logger.getLogger(RestAPIUserFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        logger.error("URL access failed, please login first!");
        HTTPUtil.responseInfo(WebUtils.toHttp(servletResponse), String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
