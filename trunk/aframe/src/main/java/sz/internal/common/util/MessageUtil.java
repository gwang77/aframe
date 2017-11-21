package sz.internal.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * User: wanggang
 * Date: 12/8/15
 * Time: 5:03 PM
 */
public class MessageUtil {

    public static String getLocaleMessage(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key);
    }

    public static String getLocaleMessage(String key, String... args) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key, args);
    }

    public static String[] getLocaleMessages(String[] keys) {
        if (keys == null || keys.length == 0) {
            return new String[0];
        }
        String[] msgs = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            msgs[i] = getLocaleMessage(keys[i]);
        }
        return msgs;
    }

}
