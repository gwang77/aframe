package sz.internal.acm.shiro.filter;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import sz.internal.acm.util.ConfigPropertyUtils;
import sz.internal.acm.util.InnerAPISecurityUtils;
import sz.internal.acm.util.JsonUtils;
import sz.internal.acm.util.http.HTTPUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class InnerAPIFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String md5_key = ConfigPropertyUtils.getProperties("acm.api.md5.key");
        md5_key = md5_key == null ? "" : md5_key;

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String security_key = httpRequest.getHeader("securityKey");

        //get url and params
        String requestURI = getPathWithinApplication(request);

        Map<String, String> paramMap = new HashMap<String, String>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value = request.getParameter(name);
            if (!StringUtils.isEmpty(value)) {
                paramMap.put(name, value);
            }
        }
        Map postParams = getPostParams(httpRequest);
        if (postParams != null) {
            paramMap.putAll(postParams);
        }
        return InnerAPISecurityUtils.isValidMD5Info(requestURI, paramMap, md5_key, security_key);
    }

    private Map<String, String> getPostParams(HttpServletRequest httpRequest) {
        String params = httpRequest.getHeader("paramValues");
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        return (Map<String, String>) JsonUtils.readValue(params, HashMap.class);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HTTPUtil.responseInfo(WebUtils.toHttp(response), String.valueOf(HttpServletResponse.SC_FORBIDDEN), HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

}
