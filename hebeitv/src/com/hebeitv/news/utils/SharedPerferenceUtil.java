package com.hebeitv.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.MrYang.zhuoyu.utils.InitUtil;
import com.hebeitv.news.bean.LoginInfo;

public class SharedPerferenceUtil
{

    private static SharedPreferences sp;

    public static final String[] textSize = { "small", "middle", "big" };

    public static String getNewsTextFontSize()
    {
        if (sp == null)
        {
            sp = InitUtil.getInitUtil().context.getSharedPreferences("hebeiTV", Context.MODE_PRIVATE);
        }
        return get("newsFont", "middle");
    }

    public static int getFontSizeIndex()
    {
        return get("fontIndex", 1);
    }

    public static void putNowUseFontSize(int size)
    {
        put("newsFont", textSize[size]);
        put("fontIndex", size);
    }

    public static void putOpenPushCheck(boolean checked)
    {
        put("openPush", checked);
    }

    public static boolean getOpenPushCheck()
    {
        return get("openPush", false);
    }

    public static void refreshLoginStatus(LoginInfo info)
    {
        put("accountNumber", info.accountNumber);
        put("account_id", info.account_id);
        put("account_pass", info.account_pass);
        put("inLogin", info.inLogin);
    }

    public static LoginInfo ReadLoginStatus()
    {
        LoginInfo info = new LoginInfo();

        info.accountNumber = get("accountNumber", info.accountNumber);
        info.account_id = get("account_id", info.account_id);
        info.account_pass = get("account_pass", info.account_pass);
        info.inLogin = get("inLogin", info.inLogin);

        return info;
    }

    private static SharedPreferences put(String key, Object value)
    {
        if (sp == null)
        {
            sp = InitUtil.getInitUtil().context.getSharedPreferences("hebeiTV", Context.MODE_PRIVATE);
        }

        if (value instanceof Boolean)
        {
            sp.edit().putBoolean(key, (Boolean) value).commit();
        }
        else if (value instanceof String)
        {
            sp.edit().putString(key, (String) value).commit();
        }
        else if (value instanceof Float)
        {
            sp.edit().putFloat(key, (Float) value).commit();
        }
        else if (value instanceof Float)
        {
            sp.edit().putInt(key, (Integer) value).commit();
        }
        else if (value instanceof Long)
        {
            sp.edit().putLong(key, (Long) value).commit();
        }
        return sp;
    }

    @SuppressWarnings("unchecked")
    private static <T> T get(String key, T defaultValue)
    {
        if (sp == null)
        {
            sp = InitUtil.getInitUtil().context.getSharedPreferences("hebeiTV", Context.MODE_PRIVATE);
        }

        if (defaultValue instanceof Boolean)
        {
            return (T) Boolean.valueOf(sp.getBoolean(key, (Boolean) defaultValue));
        }
        else if (defaultValue instanceof String)
        {
            return (T) sp.getString(key, (String) defaultValue);
        }
        else if (defaultValue instanceof Float)
        {
            return (T) Float.valueOf(sp.getFloat(key, (Float) defaultValue));
        }
        else if (defaultValue instanceof Integer)
        {
            return (T) Integer.valueOf(sp.getInt(key, (Integer) defaultValue));
        }
        else if (defaultValue instanceof Long)
        {
            return (T) Long.valueOf(sp.getLong(key, (Long) defaultValue));
        }
        return null;
    }
}
