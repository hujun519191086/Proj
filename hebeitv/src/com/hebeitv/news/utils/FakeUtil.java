package com.hebeitv.news.utils;

import com.hebeitv.news.frame.Global_hebei;

/**
 * debug模拟
 * 
 * @author jieranyishen
 * 
 */
public class FakeUtil
{

    public static <T> T getValue(T realValue)
    {
        return realValue;
    }

    public static <T> T getValue(T realValue, T testValue)
    {
        return !Global_hebei.debug ? realValue : testValue;
    }

    public static <T> T getValue(boolean useReal, T realValue, T testValue)
    {
        return useReal ? realValue : testValue;
    }
}
