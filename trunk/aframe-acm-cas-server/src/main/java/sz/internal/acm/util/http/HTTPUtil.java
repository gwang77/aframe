package sz.internal.acm.util.http;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class HTTPUtil {
    private static final Logger logger = Logger.getLogger(HTTPUtil.class);

    private static HTTPExecute execute = new HTTPExecute();

    public static String get(String url) {
        return execute.get(url);
    }

    public static String get(String url, Map headerMap) {
        return execute.get(url, headerMap);
    }

    public static String post(String url, Map<String, String> params) {
        return execute.post(url, params);
    }

    public static String post(String url, Map<String, String> params, Map headerMap) {
        return execute.post(url, params, headerMap);
    }

    public static String put(String url, Map<String, String> params) {
        return execute.put(url, params);
    }

    public static String put(String url, Map<String, String> params, Map headerMap) {
        return execute.put(url, params, headerMap);
    }

    public static String delete(String url) {
        return execute.delete(url);
    }

    public static String delete(String url, Map headerMap) {
        return execute.delete(url, headerMap);
    }

    public static void responseInfo(HttpServletResponse response, String info) {
        responseInfo(response, info, 400);
    }

    public static void responseInfo(HttpServletResponse response, String info, int status) {
        try {
            response.setStatus(status);
            PrintWriter writer = response.getWriter();
            writer.write(info);
            writer.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
