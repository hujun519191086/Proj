package com.qc188.com.util;

/**
 * 禁止多次点击
 * 
 * @author jieranyishen
 * 
 */
public class CommboUtil {
	private static final String TAG = "CommboUtil";
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {

		LogUtil.d(TAG, "isFast:" + lastClickTime);
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 600) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static boolean isFastEdit() {

		LogUtil.d(TAG, "isFast:" + lastClickTime);
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 30) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
