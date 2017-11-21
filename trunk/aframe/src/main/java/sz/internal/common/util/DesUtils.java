package sz.internal.common.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;

public class DesUtils {
    /**
     * default key
     */
    private static String strDefaultKey = "hello!23";

    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public DesUtils() throws Exception {
        this(strDefaultKey);
    }

    public DesUtils(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    public static void main(String[] args) {
        try {
            String test = "123456789ddddddddddddddd";
            DesUtils des = new DesUtils();
            System.out.println("before:" + test);
            System.out.println("after encrypt:" + des.encrypt(test));
            System.out.println("after decode:" + des.decrypt(des.encrypt(test)));

            System.out.println("after decode:" + des.decrypt("1c201407e4ec25c9482f788d6093363e"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
