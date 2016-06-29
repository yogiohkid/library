package com.joker.jokerlibrary.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Joker on 2016/6/21.
 */
public class EncryptionUtils
{
    public static final String VIPARA = "0102030405060708";
    public static final String bm = "UTF-8";

    /**
     * 加密
     *
     * @param dataPassword 密钥
     * @param cleartext    原始数据
     * @return 加密后内容
     * @throws Exception
     */
    private static String aesEncrypt(String dataPassword, String cleartext)
            throws Exception
    {
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));

        return Base64Utils.encode(encryptedData);
    }

    /**
     * 解密
     *
     * @param dataPassword 密钥
     * @param encrypted    密文
     * @return 原始数据
     * @throws Exception
     */
    private static String aesDecrypt(String dataPassword, String encrypted)
            throws Exception
    {
        byte[] byteMi = Base64Utils.decode(encrypted);
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData, bm);
    }

    public static String md5Encrypt(String cleartext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(cleartext.getBytes());
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
