package com.hebeitv.news.jni;

import android.app.Application;

import com.hebeitv.news.net.EventSender;
import com.hebeitv.news.net.OnPostCallBack;

/**
 * jni层的封装
 * 
 * @author jieranyishen
 * 
 */
public class JNISupport
{

    public static int getSplashResource(Application application)
    {
        return application.getResources().getIdentifier(Ccalculate.welcomeBackground(), "drawable", application.getPackageName());
    }

    public static boolean sendEvent(String postKey, EventSender ev, int eventId, OnPostCallBack... callBack)
    {
        ev.setJsonPostKey(postKey);
        ev.setPostCallBack(eventId, callBack);
        return Ccalculate.sendMessage(ev);
    }
}
