package cn.tlrfid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;
import android.text.format.Time;

public class DateFormatUtil {
	private static Time today;
	static {
		today = new Time();
		today.set(System.currentTimeMillis());
	}
	
	public static String getChatTime(long oldTime) {
		Time t = new Time();
		t.set(oldTime);
		String hourStr = String.valueOf(t.hour);
		hourStr = hourStr.length() > 1 ? hourStr : "0" + hourStr;
		
		String minuteStr = String.valueOf(t.minute);
		minuteStr = minuteStr.length() > 1 ? minuteStr : "0" + minuteStr;
		
		String minuteTime = hourStr + ":" + minuteStr;
		if (isToday(t.year, t.month, t.monthDay)) {
			return minuteTime;
		}
		if (isYesterday(t.year, t.month, t.monthDay)) {
			return "昨天  " + minuteTime;
		}
		return t.year + "-" + t.month + "-" + t.monthDay + "  " + minuteTime;
	}
	
	/**
	 * 传入毫秒,获取月-日 例子:10-5
	 * 
	 * @param millis
	 * @return
	 */
	public static String getMillisString(long millis) {
		Time t = new Time();
		t.set(millis);
		return t.year + "-" + t.month;
	}
	
	/**
	 * 是昨天吗
	 * 
	 * @param year
	 * @param moth
	 * @param mothDay
	 * @return
	 */
	public static boolean isYesterday(int year, int moth, int mothDay) {
		return today.year == year && today.month == moth && today.monthDay - 1 == mothDay;
	}
	
	/**
	 * 是今天?
	 * 
	 * @param year
	 * @param moth
	 * @param mothDay
	 * @return
	 */
	public static boolean isToday(int year, int moth, int mothDay) {
		return today.year == year && today.month == moth && today.monthDay == mothDay;
	}
	
}
