package com.MrYang.zhuoyu.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.MrYang.zhuoyu.R;

public class SystemNotification
{
    protected static final String TAG = "SystemNotification";
    public static final String SYSTEMNOTIFY_EXTRA = "SystemNotification";

    private SystemNotification()
    {
    }

    // private static NotificationManager mNotificationManager;
    private static Toast sToast = null;
    private static PopupWindow pw;
    public static boolean sAllMsgsNotify = true;

    public static void showToast(Context context, String msg, boolean repeat)
    {
        if (TextUtils.isEmpty(msg))
        {
            return;
        }
        if (sToast == null)
        {
            sToast = new Toast(context);
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setBackgroundColor(R.drawable.toast_background);
            tv.setTextColor(Color.WHITE);
            int paddingLeft = DensityUtil.dip2px(80);
            int paddingTop = DensityUtil.dip2px(40);
            tv.setTextSize(14);
            tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            sToast.setView(tv);
            sToast.setDuration(Toast.LENGTH_SHORT);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        }
        TextView tv = (TextView) sToast.getView();
        if (repeat)
        {
            tv.setText(msg);
            sToast.show();
        }
        else
        {
            if (!tv.getText().toString().equals(msg))
            {
                tv.setText(msg);
                sToast.show();
            }
        }

    }

    public static void hideToast()
    {
        try
        {
            // 需要将前面代码中的obj变量变成类变量。这样在多个地方就都可以访问了
            Method method = sToast.getClass().getDeclaredMethod("hide");
            method.invoke(sToast);
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 弹出toast
     * 
     * @param msg
     *            土司内的信息
     */
    public static void showToast(Context context, String msg)
    {
        showToast(context, msg, true);
    }

    public static void cancelToast()
    {
        if (sToast != null)
        {
            sToast.cancel();
        }
    }

    @SuppressWarnings("deprecation")
    public static void showPopupWindow(Context context, View contentView, final View dropView, int anim, int width, int height, int xoff, int yoff)
    {
        ViewGroup g = ((ViewGroup) contentView.getParent());
        if (g != null)
        {
            g.removeView(contentView);
        }

        pw = new PopupWindow(contentView, width, height);
        if (anim != 0)
        {
            pw.setAnimationStyle(anim);
        }

        if (contentView instanceof ListView)
        {
            ((AbsListView) contentView).setOnItemClickListener(new OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if (dropView instanceof TextView)
                    {
                        ((TextView) dropView).setText(((TextView) view).getText().toString());
                    }
                    pw.dismiss();
                }
            });
        }
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setFocusable(true);
        // 设置允许在外点击消失
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(dropView, xoff, yoff);
    }

    public static boolean popIsShowing()
    {
        if (pw != null)
        {
            return pw.isShowing();
        }
        return false;
    }

    public static void showPopupWindow(Context context, View contentView, final View dropView, int width, int height, int xoff, int yoff)
    {
        showPopupWindow(context, contentView, dropView, width, height, xoff, yoff, null, null);
    }

    @SuppressWarnings("deprecation")
    public static void showPopupWindow(Context context, View contentView, View dropView, int width, int height, int xoff, int yoff, final OnItemClickListener listener, OnDismissListener dismiss_listener)
    {
        ViewGroup g = ((ViewGroup) contentView.getParent());
        if (g != null)
        {
            g.removeView(contentView);
        }

        pw = new PopupWindow(contentView, width, height);

        if (contentView instanceof ListView)
        {
            ((AbsListView) contentView).setOnItemClickListener(new OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if (listener != null)
                    {
                        listener.onItemClick(parent, view, position, id);
                    }
                    pw.dismiss();
                }
            });
        }
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setFocusable(true);
        // 设置允许在外点击消失
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(dropView, xoff, yoff);

        if (dismiss_listener != null)
        {
            pw.setOnDismissListener(dismiss_listener);
        }
    }

    public static void dismissPopupWindow()
    {
        if (pw != null && pw.isShowing())
        {
            pw.dismiss();
        }
    }
}
