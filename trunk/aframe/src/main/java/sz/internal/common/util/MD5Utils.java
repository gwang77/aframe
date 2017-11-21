package sz.internal.common.util;

import java.security.MessageDigest;

public class MD5Utils {

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
        String str = "abc";
        System.out.println(md5Encode(str));
    }

}
