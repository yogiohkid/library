package com.joker.jokerlibrary.utils;

/**
 * Created by Joker on 2016/6/21.
 */
public class ConvertUtils
{
    public final static int convertToInt(Object value, int defaultValue)
    {
        if (value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value.toString());
        } catch (Exception e)
        {
            try
            {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1)
            {
                return defaultValue;
            }
        }
    }

    public final static double convertToDouble(Object value, double defaultValue)
    {
        if (value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Double.parseDouble(value.toString());
        } catch (Exception e)
        {
            return defaultValue;
        }
    }

    public final static long convertToLong(Object value, long defaultValue)
    {
        if (value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Long.parseLong(value.toString());
        } catch (Exception e)
        {
            return defaultValue;
        }
    }

    public final static String bytesToHexString(byte[] src)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0)
        {
            return null;
        }

        for (int i = 0; i < src.length; i++)
        {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2)
            {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public final static byte[] hexStringToBytes(String hexString)
    {
        if (hexString == null || hexString.equals(""))
        {
            return null;
        }

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++)
        {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private final static byte charToByte(char c)
    {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
