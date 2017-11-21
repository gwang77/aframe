package sz.internal.common.util.http;

import java.util.Map;

public class HTTPUtil {
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

}
