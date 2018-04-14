package com.MrYang.zhuoyu.utils;

/**
 * 禁止多次点击
 * 
 * @author MR.yang
 * 
 */
public class CommboUtil {
	private static long lastClickTime;
	private static long outOfTime = 500;

	/**
	 * 检查与上次点击时间相隔有没有超过 outOfTime
	 * 
	 * @return 如果是true，则未超过outOfTime，理应拦截。 false反之
	 */
	public static boolean isFastDoubleClick() {

		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < outOfTime) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 设置 outOfTime 默认是500 毫秒
	 * 
	 * @param time
	 *            要设置的毫秒数
	 */
	public static void setOutofTime(long time) {
		outOfTime = time;
	}
}
