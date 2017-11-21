package sz.internal.acm.util;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import sz.internal.acm.util.http.HTTPUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class SecurityHttpUtil {
    private static final Logger logger = Logger.getLogger(SecurityHttpUtil.class);

    public static String baseURL = ConfigPropertyUtils.getProperties("acm.api.base.url");

    public static String getResponse(String url) {
        String md5_key = ConfigPropertyUtils.getProperties("acm.api.md5.key");
        md5_key = md5_key == null ? "" : md5_key;

        String securityKey = prepareSecurityKey(url, md5_key);
        Map<String, String> map = new HashMap<String, String>();
        map.put("securityKey", securityKey);
        return HTTPUtil.get(url, map);
    }

    public static String post(String url, Map<String, String> paramMap) {
        String md5_key = ConfigPropertyUtils.getProperties("acm.api.md5.key");
        md5_key = md5_key == null ? "" : md5_key;

        String securityKey = prepareSecurityKey(url, md5_key, paramMap);
        Map<String, String> map = new HashMap<String, String>();
        map.put("securityKey", securityKey);
        map.put("paramValues", paramMap == null ? "" : JsonUtils.writeValue(paramMap));
        return HTTPUtil.post(url, paramMap, map);
    }

    private static String prepareSecurityKey(String url, String md5_key) {
        return prepareSecurityKey(url, md5_key, null);
    }

    private static String prepareSecurityKey(String url, String md5_key, Map<String, String> paramMap) {
        Map<String, String> map = new HashMap<String, String>();
        if (paramMap != null) {
            map.putAll(paramMap);
        }
        url = url.replace(baseURL, "");
        try {
            url = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
            logger.error("decode error");
        }
        int ind = url.indexOf("?");
        String url_content = url;
        String paramsStr = "";
        if (ind >= 0) {
            url_content = url.substring(0, ind);
            paramsStr = url.substring(ind + 1);
        }
        String[] params = paramsStr.split("&");
        for (String param : params) {
            String[] vals = param.split("=");
            if (vals.length >= 2 && !StringUtils.isEmpty(vals[1])) {
                map.put(vals[0], vals[1]);
            }
        }
        return InnerAPISecurityUtils.constructMD5Info(url_content, map, md5_key);
    }
}
