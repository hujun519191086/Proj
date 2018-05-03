package com.qc188.com.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {

	public static Context context;

	public static int densityDpi;

	public static void setContext(Context context) {
		DensityUtil.context = context;
	}

	public static float getScale() {
		DisplayMetrics dm = getDisplayMetrics();
		final float scale = dm.density;
		return scale;
	}

	public static int dip2px(float dpValue) {

		return (int) (dpValue * getScale() + 0.5f);
	}

	public static int px2dip(float pxValue) {
		return (int) (pxValue / getScale() + 0.5f);
	}

	private static DisplayMetrics getDisplayMetrics() {
		if (DensityUtil.context == null) {
			throw new IllegalArgumentException("DensityUtil没有初始化context");
		}
		return context.getResources().getDisplayMetrics();
	}

	public static int getHeightPixels() {
		return getDisplayMetrics().heightPixels;
	}

	public static int getWidthPixels() {
		return getDisplayMetrics().widthPixels;
	}

	public static int getHeightWidthScale(int widthPixels, double d) {
		return (int) (widthPixels / d);

	}

}
