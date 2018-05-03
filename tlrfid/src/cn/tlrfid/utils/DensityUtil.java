package cn.tlrfid.utils;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * 
 * @author jieranyishen
 * 
 */
public class DensityUtil {
	public static float scale = 1;
	public static final int ERROR_VALUE = -1;
	
	/**
	 * dip转化为px
	 */
	public static int dip2px(float dpValue) {
		return (int) (dpValue * scale + 0.5f);
	}
	
	public static int getDimensPx(Context context, int dimenId) {
		if (dimenId == 0) {
			return -1;
		}
		return context.getResources().getDimensionPixelSize(dimenId);
	}
	
	private static int statusBarHeight = ERROR_VALUE;
	
	/**
	 * 获取bar的高度
	 * 
	 * @param act
	 * @return
	 */
	public static int getStatusBarHeigth(Activity act) {
		if (statusBarHeight == ERROR_VALUE) {
			Rect frame = new Rect();
			act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			statusBarHeight = frame.top;
		}
		return statusBarHeight;
	}
	
	/**
	 * px转化为dp
	 */
	public static int px2dip(float pxValue) {
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 手机比例类
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		if (context == null) {
			return null;
		}
		return context.getResources().getDisplayMetrics();
	}
	
	public static int heightPixels = 0;
	public static int widthPixels = 0;
	public static int densityDpi = 0;
	
	public static int getResourceDimens(Context context, int resId) {
		return context.getResources().getDimensionPixelSize(resId);
	}
	
	private static boolean isInit = false;
	
	/**
	 * 初始化
	 * 
	 * @param context
	 * @param activity
	 */
	public static void initAllValue(Context context, Activity activity) {
		if (isInit) {
			return;
		}
		isInit = true;
		heightPixels = DensityUtil.getDisplayMetrics(context).heightPixels;
		widthPixels = DensityUtil.getDisplayMetrics(context).widthPixels;
		scale = context.getResources().getDisplayMetrics().density;
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		densityDpi = dm.densityDpi;
		statusBarHeigth = getStatusBarHeight(context);
	}
	
	private static int statusBarHeigth;
	
	public static int getStatusBarHeight() {
		return statusBarHeigth;
	}
	
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}
	
}
