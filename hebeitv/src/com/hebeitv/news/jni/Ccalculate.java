package com.hebeitv.news.jni;

import com.hebeitv.news.net.EventSender;

/**
 * 很多方法使用c计算, 加密
 * 
 * @author jieranyishen
 * 
 */
public class Ccalculate
{

    public static native String welcomeBackground();

    public static native int numberToDip(int number);

    public static native int numberToNumber(int number);

    public static native boolean sendMessage(EventSender sender);
}
