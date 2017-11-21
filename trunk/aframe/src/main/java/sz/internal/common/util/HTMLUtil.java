package sz.internal.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * User: wanggang
 * Date: 1/14/16
 * Time: 2:16 PM
 */
public class HTMLUtil {

    public static String htmlEncode(String str) {
        String result = str.replaceAll("<", "&lt;");
        result = result.replaceAll(">", "&gt;");
        result = result.replaceAll(" ", "&nbsp;");
        result = result.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        result = result.replaceAll("\n", "<br>");
        return result;
    }

    public static String encodeBase64Str(String str) {
        if (str == null) {
            return "";
        }
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    public static String encodeBase64(byte[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        return new String(Base64.encodeBase64(strArr));
    }

    public static String decodeBase64Str(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return new String(Base64.decodeBase64(str));
    }

    public static byte[] decodeBase64(String str) {
        if (str == null || str.length() == 0) {
            return new byte[0];
        }
        return Base64.decodeBase64(str);
    }
}
