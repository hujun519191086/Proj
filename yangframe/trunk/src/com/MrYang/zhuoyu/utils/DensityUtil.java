package com.MrYang.zhuoyu.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.WindowManager;

public class DensityUtil
{
    public static float scale = 1;
    public static final int ERROR_VALUE = InitUtil.ERROR_VALUES;// 错误码

    public static SparseIntArray dip2PxValues;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue)
    {
        int px = dip2PxValues.get((int) dpValue, ERROR_VALUE);
        if (px == ERROR_VALUE)
        {
            px = (int) (dpValue * scale + 0.5f);
            dip2PxValues.put((int) dpValue, px);
        }
        return px;
    }

    private static int statusBarHeight = ERROR_VALUE;


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue)
    {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取显示信息的尺寸
     * 
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context)
    {
        if (context == null)
        {
            return null;
        }
        return context.getResources().getDisplayMetrics();
    }

    public static int heightPixels = 0;
    public static int widthPixels = 0;
    public static int densityDpi = 0;

    /**
     * 读取资源的dimens
     * 
     * @param context
     * @param resId
     *            资源id
     * @return 转换后的px
     */
    public static int getResourceDimens(int resId)
    {
        return InitUtil.getInitUtil().context.getResources().getDimensionPixelSize(resId);
    }

    /**
     * 初始化这个工具类的所有值,(手机给予应用的<b>宽,高,状态栏高度</b>)
     * 
     * 
     * @param context
     *            上下文
     */
    public static void initAllValue(Context context)
    {
        dip2PxValues = new SparseIntArray();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        densityDpi = dm.densityDpi;
        scale =  ((float)densityDpi)/((float)160.0);
        reSet(context);
    }

    /**
     * 重新测量手机的<b>宽,高,状态栏高度</b>
     * 
     * @param context
     *            上下文
     */
    public static void reSet(Context context)
    {
        heightPixels = DensityUtil.getDisplayMetrics(context).heightPixels;// 手机给予应用的高度
        widthPixels = DensityUtil.getDisplayMetrics(context).widthPixels; // 手机给予应用的宽度
        statusBarHeigth = getStatusBarHeight(context); // 状态栏高度
    }

    /**
     * 传入宽和比例,可计算出高度值.
     * 
     * @param widthPixels
     * @param d
     * @return
     */
    public static int getHeightWidthScale(int widthPixels, double d)
    {
        return (int) (widthPixels / d);
    }

    private static int statusBarHeigth;

    /**
     * 获取状态栏高度
     * 
     * @return
     */
    public static int getStatusBarHeight()
    {
        return statusBarHeigth;
    }

    /**
     * 重新获取状态栏高度
     * 
     * @param context
     * @return 顶条栏高度
     */
    private static int getStatusBarHeight(Context context)
    {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        statusBarHeight = 0;
        try
        {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResourceDimens(x);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

}
