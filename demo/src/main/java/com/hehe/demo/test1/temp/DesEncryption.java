package com.hehe.demo.test1.temp;

import com.google.common.base.Splitter;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Map;

/**
 * DES加密解密
 * 可以用于白名单接口加密措施(一般来说用于安全性不太高的接口，对于安全性要求高的接口，可以考虑使用oauth2)
 *
 * @author xieqinghe .
 * @date 2017/12/27 下午4:12
 * @email qinghe101@qq.com
 */
public class DesEncryption {

    /**
     * 默认密钥
     */
    private static String defaultSecretKey = "model_secret_key_a0021";

    /**
     * 加密器
     */
    private Cipher encryptCipher = null;

    /**
     * 解密器
     */
    private Cipher decryptCipher = null;

    public DesEncryption() throws Exception {
        this(defaultSecretKey);
    }

    /**
     * 加密解密使用的密钥
     */
    public DesEncryption(String secretKey) {
        Key key;
        try {
            key = getKey(secretKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
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

    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }


    /**
     * 用法实例
     */
    public static void main(String[] args) {
        try {
            String test="var1=0000016-171225181752169-fjy-W&var2=test";

            DesEncryption des = new DesEncryption();
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des.encrypt(test));
            System.out.println("解密后的字符：" + des.decrypt(des.encrypt(test)));

            System.out.println();
            DesEncryption des1 = new DesEncryption("uc:usercenter");
            System.out.println("加密前的字符：" + test);
            System.out.println("加密后的字符：" + des1.encrypt(test));
            System.out.println("解密后的字符：" + des1.decrypt(test));

            System.out.println();
            Map<String, String> map = Splitter.on("&").withKeyValueSeparator("=").split(des1.decrypt(des1.encrypt(test)));
            System.out.println("var1:" + map.get("var1"));
            System.out.println("var2:" + map.get("var2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
