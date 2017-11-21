package sz.internal.common.util;

import org.apache.log4j.Logger;
import sz.internal.common.util.http.HTTPSUtil;
import sz.internal.common.util.http.HTTPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = Logger.getLogger(HttpUtil.class);

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

    public static String getRequestBasePath(HttpServletRequest request) {
        String reqUrl = request.getRequestURL().toString();
        String sevUrl = request.getServletPath();
        return reqUrl.substring(0, reqUrl.indexOf(sevUrl));
    }

    public static String getResponse(String url) {
        return get(url);
    }

    public static String post(String url, Map<String, String> params) {
        return HTTPUtil.post(url, params);
    }

    public static String put(String url, Map<String, String> params) {
        return HTTPUtil.put(url, params);
    }

    public static String get(String url) {
        return HTTPUtil.get(url);
    }

    public static String delete(String url) {
        return HTTPUtil.delete(url);
    }

    public static String getHTTPS(String url) {
        return HTTPSUtil.get(url);
    }

    public static void main(String[] args) {
        String url = "";
        String result = "";

        //get
        url = "http://localhost:8080/dwgame/api/game/startGame";
        result = get(url);
        System.out.println(result);

        //post
        url = "http://localhost:8080/gepc/user/api/saveUserInfo";
        String str = "{\"id\":11,\"version\":1,\"sortBy\":null,\"sortType\":null,\"totalSize\":null,\"username\":\"wanggang@ncsi.com.cn\",\"confirm_password\":null,\"user_type\":\"L\",\"realname\":\"Wang Gang  (NCSI SZ)\",\"staff_id\":\"1210818\",\"id_number\":null,\"sex\":null,\"birth_date_s\":null,\"tel\":\"(86)512-62886969-8302\",\"email\":\"wanggang@ncsi.com.cn\",\"locked\":\"N\"}";
        Map map = (Map) JsonUtil.readValue(str, HashMap.class);
        result = post(url, map);
        System.out.println(result);

//        //get
//        url = "http://localhost:8080/gepc/request/requestObject/123";
//        result = get(url);
//        System.out.println(result);
//        //delete
//        url = "http://localhost:8080/gepc/request/requestObject/123";
//        result = delete(url);
//        System.out.println(result);
//        //post
//        url = "http://localhost:8080/gepc/request/requestObject";
//        result = post(url, map);
//        System.out.println(result);
//        //put
//        url = "http://localhost:8080/gepc/request/requestObject";
//        result = put(url, map);
//        System.out.println(result);


        //getHTTPS
//        url = "https://ssodev.ncs.com.sg/sso/identity/attributes?subjectid=";
//        result = getHTTPS(url);
//        System.out.println(result);

    }

}
