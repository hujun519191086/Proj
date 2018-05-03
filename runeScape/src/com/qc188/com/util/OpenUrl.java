package com.qc188.com.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * 打开url
 * 
 * @author jieranyishen
 * 
 */
public class OpenUrl
{

    private static final String TAG = "OpenUrl";

    public OpenUrl(Activity activity, String url)
    {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        LogUtil.d(TAG, "OpenUrl:" + url);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        activity.startActivity(intent);
    }
}
