package com.hebeitv.news.frame;

import java.util.regex.Pattern;

import android.app.Activity;
import android.text.TextUtils;

import com.hebeitv.news.utils.WindowUtil;

public class UserManager
{

    public static boolean checkUserMsg(Activity activity, String msg, String msgType)
    {
        if (TextUtils.isEmpty(msg))
        {
            WindowUtil.showToast(activity, msgType + "不能为空!");
            return false;
        }
        else if (msg.length() < 6)
        {
            WindowUtil.showToast(activity, msgType + "长度过短!");
            return false;
        }
        else if (msg.length() > 18)
        {
            WindowUtil.showToast(activity, msgType + "长度过长!");
            return false;
        }

        boolean match = Pattern.matches("[\\W]", msg);
        if (match)
        {
            WindowUtil.showToast(activity, msgType + "不符合规范!");
            return false;
        }
        return true;
    }

    public static boolean checkUserName(Activity activity, String msg)
    {
        return checkUserMsg(activity, msg, "用户名");
    }

    public static boolean checkPassword(Activity activity, String pass)
    {
        return checkUserMsg(activity, pass, "密码");
    }

    public static boolean userMsgSecurity(Activity activity, String msg, String pass)
    {
        return checkUserName(activity, msg) && checkPassword(activity, pass);
    }

    /**
     * 登录信息校验
     * 
     * @param activity
     * @param msg
     * @param pass
     * @return
     */
    public static boolean securityLoginMsg(Activity activity, String msg, String pass)
    {
        return userMsgSecurity(activity, msg, pass);
    }

    public static boolean securityRegistMsg(Activity activity, String msg, String pass, String phone, String passAgain)
    {
        boolean msgPass = userMsgSecurity(activity, msg, pass);
        if (msgPass)
        {
            if (TextUtils.isEmpty(phone))
            {
                WindowUtil.showToast(activity, "电话不能为空!");
            }
            else if (phone.length() != 11 || !TextUtils.isDigitsOnly(phone))
            {
                WindowUtil.showToast(activity, "请输入正确的电话号码!");

            }
            else if (TextUtils.isEmpty(passAgain))
            {
                WindowUtil.showToast(activity, "请再次输入密码!");

            }
            else if (!pass.equals(passAgain))
            {
                WindowUtil.showToast(activity, "两次密码输入不一致!");
            }
            else
            {
                return true;
            }
        }
        return false;
    }

}
