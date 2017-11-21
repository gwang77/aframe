package sz.internal.common.util.http;

import java.util.HashMap;
import java.util.Map;

public class HTTPSUtil {
    private static HTTPExecute execute = new HTTPSExecute();

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

    public static void main(String[] args) {
        String url = "";
        String result = "";

        //get
//        url = "https://sso.ncs.com.sg/sso/identity/attributes?subjectid=AQIC5wM2LY4SfcxAH2fDHMPz0ah2tcMo2ev7SaJpr-lvck0.*AAJTSQACMDQAAlNLAAkzNzAwNjY1NDUAAlMxAAIwMg..*";
//        result = get(url);
//        System.out.println(result);

        //post
        url = "https://test.showcai.com.cn:41081/dwzq_api_common/points/adjustPoints";
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", "13912345678");
        map.put("khh", "");
        map.put("source", "疯狂K线");
        map.put("review", "false");
        map.put("points", "10");
        map.put("taskName", "上传积分");
        map.put("taskId", "");

        String s = post(url, map);
        System.out.println(s);
    }
}
