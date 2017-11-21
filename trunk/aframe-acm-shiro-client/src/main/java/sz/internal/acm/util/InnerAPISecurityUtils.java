package sz.internal.acm.util;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.*;

public class InnerAPISecurityUtils {

    //md5(url=<url>&param1=<param1>&param2=<param2>...md5(key))
    //param sort by para name. both get and post will get all params.
    public static boolean isValidMD5Info(String url, Map map, String md5_key, String security_key) {
        String md5InfoStr = constructMD5Info(url, map, md5_key);
        return !isEmpty(md5InfoStr) && !isEmpty(security_key) && md5InfoStr.equals(security_key);
    }

    public static String constructMD5Info(Map map, String md5_key) {
        return constructMD5Info("", map, md5_key);
    }

    public static String constructMD5Info(String url, Map map, String md5_key) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(url)) {
            sb.append("url=").append(url);
        }
        if (map == null) {
            map = new HashMap();
        }

        ArrayList<String> keyList = new ArrayList<String>();
        keyList.addAll(map.keySet());
        Collections.sort(keyList);

        for (String item_key : keyList) {
            Object value = map.get(item_key);
            String val = value == null ? "" : String.valueOf(value);
            if (value instanceof Object[]) {
                val = Arrays.toString((Object[]) map.get(item_key));
            }
            if (!"md5Info".equals(item_key) && val != null && !"".equals(val)) {
                if (!isEmpty(sb.toString())) {
                    sb.append("&");
                }
                sb.append(item_key).append("=").append(val);
            }
        }
        if (!isEmpty(md5_key)) {
            sb.append(md5Encode(md5_key));
        }
        String md5Info_org = sb.toString();

        System.out.println("key:" + md5_key);
        System.out.println("md5Info:" + md5Info_org);

        return md5Encode(md5Info_org);
    }

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static String md5Encode(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bts = md5.digest(str.getBytes("utf-8"));

            return bytesToHex(bts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", "13912345678");
        map.put("khh", "");
        map.put("source", "aaa");
        map.put("review", "false");
        map.put("points", "10");
        map.put("taskName", "上传积分");
        map.put("taskId", "");
        boolean isvalid = isValidMD5Info("", map, "abc", constructMD5Info(map, "abc"));
        System.out.println(isvalid);
    }

}
