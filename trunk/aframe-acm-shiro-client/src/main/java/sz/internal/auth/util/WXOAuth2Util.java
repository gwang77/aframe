package sz.internal.auth.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public class WXOAuth2Util {
    private static final Logger logger = LoggerFactory.getLogger(WXOAuth2Util.class);

    /**
     * 获取微信code的请求url.
     */
    public static String getOAuth2CodeUrl(String appId, String redirect_uri, String scope, String state) {
        // 拼接请求地址
        String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REDIRECT_URI", URLEncoder.encode(redirect_uri));
        requestUrl = requestUrl.replace("SCOPE", scope);
        requestUrl = requestUrl.replace("STATE", state);

        return requestUrl;
    }

    /**
     * 根据code获取网页授权凭证access_token.
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code      code
     * @return OAuth2Token
     */
    public static OAuth2LoginToken getOAuth2Token(String appId, String appSecret, String code) {
        OAuth2LoginToken wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);

        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        logger.info("获取网页授权凭证：" + jsonObject);
        if (null != jsonObject) {
            try {
                wat = new OAuth2LoginToken();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }

    /**
     * 通过网页授权获取用户信息.
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return SNSUserInfo
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        requestUrl = requestUrl.replace("OPENID", openId);

        // 通过网页授权获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.getString("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                logger.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }

    public static SNSUserInfo getWXUserInfo(String app_id, String app_secret, String code) {
        if (StringUtils.isEmpty(app_id) || StringUtils.isEmpty(app_secret) || StringUtils.isEmpty(code)) {
            logger.error("Any of param app_id, app_secret, code is empty.");
            return null;
        }
        OAuth2LoginToken token = WXOAuth2Util.getOAuth2Token(app_id, app_secret, code);

        if (token == null) {
            logger.error("Get token error.");
            return null;
        }

        SNSUserInfo userInfo = WXOAuth2Util.getSNSUserInfo(token.getAccessToken(), token.getOpenId());
        if (userInfo == null) {
            logger.error("Get User Info error.");
            return null;
        }
        return userInfo;
    }

    /**
     * 获取jsapi ticket.
     */
    public static String getJsapiTicket(String appId, String appSecret) {
        // 1. 获取access_token
        String access_token = "";
        String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
        getTokenUrl = getTokenUrl.replace("APPID", appId);
        getTokenUrl = getTokenUrl.replace("SECRET", appSecret);
        JSONObject tokenJosnObj = httpsRequest(getTokenUrl, "GET", null);
        logger.info("获取网页授权凭证：" + tokenJosnObj);
        if (null != tokenJosnObj) {
            try {
                access_token = tokenJosnObj.getString("access_token");
            } catch (Exception e) {
                int errorCode = tokenJosnObj.getInteger("errcode");
                String errorMsg = tokenJosnObj.getString("errmsg");
                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        // 2. 获取ticket
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
        JSONObject ticketJsonObj = httpsRequest(ticketUrl, "GET", null);
        if (null != ticketJsonObj) {
            try {
                return ticketJsonObj.getString("ticket");
            } catch (Exception e) {
                int errorCode = tokenJosnObj.getInteger("errcode");
                String errorMsg = tokenJosnObj.getString("errmsg");
                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        return "kgt8ON7yVITDhtdwci0qeTPi4uLRAN-5WPf5pygQ7_8OEmKM518hH7rc9QLKKqTeV2LY4kIoGCVbaXKf3oIwgw";
    }

    /**
     * 判断是否是微信打开.
     */
    public static boolean isWeiXinOpen(HttpServletRequest req) {
        boolean isWeiXinOpen = false;
        String ua = req.getHeader("user-agent").toLowerCase();
        if (ua.indexOf("micromessenger") > 0) {
            isWeiXinOpen = true;
        }

        return isWeiXinOpen;
    }

    /**
     * 发送https请求.
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return jsonObject;
    }
}
