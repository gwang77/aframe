package sz.internal.auth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class QQOAuth2Util {

    private static final Logger logger = LoggerFactory.getLogger(QQOAuth2Util.class);

    /**
     * 获取QQ code的请求url.
     */
    public static String getOAuth2CodeUrl(String appId, String redirect_uri, String scope, String state) {
        // 拼接请求地址
        String requestUrl = "https://graph.qq.com/oauth2.0/authorize"
                + "?client_id=" + appId
                + "&redirect_uri=" + URLEncoder.encode(redirect_uri)
                + "&response_type=code"
                + "&state=" + state
                + "&scope=" + scope;
        return requestUrl;
    }

    /**
     * 根据code获取网页授权凭证access_token.
     *
     * @param appId       QQ账号的唯一标识
     * @param appSecret   QQ账号的密钥(appKey)
     * @param code        code
     * @param redirectURL
     * @return OAuth2LoginToken
     */
    public static OAuth2LoginToken getOAuth2Token(String appId, String appSecret, String code, String redirectURL) {
        // 拼接请求地址
        String requestUrl = "https://graph.qq.com/oauth2.0/token"
                + "?client_id=" + appId
                + "&client_secret=" + appSecret
                + "&grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + URLEncoder.encode(redirectURL);
        String result = httpsRequest(requestUrl, "GET", null);
        if (result == null) {
            return null;
        }
        if (result.indexOf("\"error\"") != -1) {
            JSONObject jsonObject = JSON.parseObject(fmtJSONText(result));
            int errorCode = jsonObject.getInteger("error");
            String errorMsg = jsonObject.getString("error_description");
            logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            return null;
        }
        try {
            String[] as = result.split("&");
            OAuth2LoginToken wat = new OAuth2LoginToken();
            wat.setAccessToken(as[0].split("=")[1]);
            wat.setExpiresIn(Integer.parseInt(as[1].split("=")[1]));
            wat.setRefreshToken(as[2].split("=")[1]);
            setUserOpenId(wat);
            return wat;
        } catch (Exception e) {
            logger.error("获取网页授权凭证失败{}", e.getMessage());
            return null;
        }
    }

    private static void setUserOpenId(OAuth2LoginToken ot) throws Exception {
        String requestUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + ot.getAccessToken();
        String result = httpsRequest(requestUrl, "GET", null);
        if (result != null && result.startsWith("callback(")) {
            JSONObject o = JSON.parseObject(fmtJSONText(result));
            String openId = o.getString("openid");
            if (openId != null) {
                ot.setOpenId(openId);
                return;
            }
        }
        throw new Exception("Cannot Get User ID.");
    }

    /**
     * 通过网页授权获取用户信息.
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @param appId       QQ账号的唯一标识
     * @return SNSUserInfo
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId, String appId) {
        String requestUrl = "https://graph.qq.com/user/get_user_info"
                + "?openid=" + openId
                + "&oauth_consumer_key=" + appId
                + "&access_token=" + accessToken
                + "&format=json";
        String result = httpsRequest(requestUrl, "GET", null);
        if (result == null) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(result);
            int ret = jsonObject.getIntValue("ret");
            if (ret == 0) {
                SNSUserInfo ot = new SNSUserInfo();
                ot.setOpenId(openId);
                ot.setNickname(jsonObject.getString("nickname"));
                ot.setSex(getSex(jsonObject.getString("gender")));
                ot.setProvince(jsonObject.getString("province"));
                ot.setCity(jsonObject.getString("city"));
                ot.setHeadImgUrl(jsonObject.getString("figureurl_qq_2"));
                return ot;
            } else {
                int errorCode = jsonObject.getInteger("ret");
                String errorMsg = jsonObject.getString("msg");
                logger.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                return null;
            }
        } catch (Exception e) {
            logger.error("获取用户信息失败: {}.", e.getMessage());
            return null;
        }
    }

    public static SNSUserInfo getQQUserInfo(String app_id, String app_secret, String code, String redirect_url) {
        if (StringUtils.isEmpty(app_id) || StringUtils.isEmpty(app_secret) || StringUtils.isEmpty(code)) {
            logger.error("Any of param app_id, app_secret, code is empty.");
            return null;
        }
        OAuth2LoginToken token = QQOAuth2Util.getOAuth2Token(app_id, app_secret, code, redirect_url);

        if (token == null) {
            logger.error("Get token error.");
            return null;
        }

        SNSUserInfo userInfo = QQOAuth2Util.getSNSUserInfo(token.getAccessToken(), token.getOpenId(), app_id);
        if (userInfo == null) {
            logger.error("Get User Info error.");
            return null;
        }
        return userInfo;
    }

    protected static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        HttpsURLConnection httpsConn = null;
        InputStream inputStream = null;
        try {
            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
            sslcontext.init(null, new TrustManager[]{xtm}, null);
            URL url = new URL(requestUrl);
            httpsConn = (HttpsURLConnection) url.openConnection();
            httpsConn.setSSLSocketFactory(sslcontext.getSocketFactory());
            httpsConn.setDoOutput(true);
            httpsConn.setDoInput(true);
            httpsConn.setUseCaches(true);
            httpsConn.setRequestMethod(requestMethod);
            if (outputStr != null) {
                OutputStream outputStream = httpsConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            inputStream = httpsConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            int bufferSize = 100;
            char[] chars = new char[bufferSize];
            StringBuilder sb = new StringBuilder();
            for (int i = inputStreamReader.read(chars); i != -1; i = inputStreamReader.read(chars)) {
                sb.append(chars, 0, i);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("https Connection Exception：{}", e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
            if (httpsConn != null) {
                try {
                    httpsConn.disconnect();
                } catch (Exception e) {
                }
            }
        }
    }

    private static String fmtJSONText(String text) {
        text = text.substring(10);
        return text.substring(0, text.length() - 3);
    }

    private static int getSex(String value) {
        if (value == null) {
            return 0;
        } else if (value.equals("\u7537")) {
            return 1;
        } else if (value.equals("\u5973")) {
            return 2;
        } else {
            return 0;
        }
    }
}
