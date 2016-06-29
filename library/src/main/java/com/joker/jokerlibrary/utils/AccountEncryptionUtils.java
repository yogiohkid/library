package com.joker.jokerlibrary.utils;

/**
 * Created by Joker on 2016/5/9.
 * 账号加密显示
 */
public class AccountEncryptionUtils
{
    public static String encryption(String account)
    {
        if (account.matches("^\\d{19}$"))
        {
            return account.substring(0, 4) + " ******** " + account.substring(15, 19);
        }
        if (account.matches("^\\d{16}$"))
        {
            return account.substring(0, 4) + " ******** " + account.substring(12, 16);
        }
        if (account.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"))
        {
            String[] s = account.split("@");
            if (s[0].length() > 4)
            {
                return s[0].substring(0, 2) + "****" + s[0].substring(s[0].length() - 2, s[0].length()) + "@" + s[1];
            } else
            {
                return s[0].substring(0, 1) + "****" + "@" + s[1];
            }
        }
        if (account.matches("^((13[0-9])|14[5,7]|15[0-9]|18[0-9]|17[6-8]|170)\\d{8}$"))
        {
            return account.substring(0, 3) + "****" + account.substring(8, 11);
        }
        return null;
    }
}
