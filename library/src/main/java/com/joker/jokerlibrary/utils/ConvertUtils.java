package com.joker.jokerlibrary.utils;

/**
 * Created by Joker on 2016/6/21.
 */
public class ConvertUtils
{
    public final static int convertToInt(Object value,int defaultValue)
    {
        if(value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Integer.parseInt(value.toString());
        }
        catch (Exception e)
        {
            try
            {
                return Double.valueOf(value.toString()).intValue();
            }
            catch (Exception e1)
            {
                return defaultValue;
            }
        }
    }

    public final static double convertToDouble(Object value,double defaultValue)
    {
        if(value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Double.parseDouble(value.toString());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    public final static long convertToLong(Object value,long defaultValue)
    {
        if(value == null || "".equals(value.toString().trim()))
        {
            return defaultValue;
        }
        try
        {
            return Long.parseLong(value.toString());
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }
}
