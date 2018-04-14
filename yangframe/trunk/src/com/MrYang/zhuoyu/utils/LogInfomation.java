package com.MrYang.zhuoyu.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import android.os.Environment;
import android.util.Log;

public class LogInfomation {

	public static final boolean LOG_OPEN_DEBUG = !InitUtil.FRAME_FINAL;
	public static final boolean LOG_OPEN = !InitUtil.FINAL;
	private static String MYLOG_PATH_SDCARD_DIR = Environment
			.getExternalStorageDirectory() + File.separator + "log";// 日志文件在sdcard中的路径
	private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
	private static SimpleDateFormat logfile = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.CHINA);
	private static SimpleDateFormat myLogSdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 日志的输出格式
	private static int SDCARD_LOG_FILE_SAVE_DAYS = 4;// sd卡中日志文件的最多保存天数
	public static boolean pointToFile = false;

	public static final String MEMORY_TAG = "memory";

	public static void showMemory(String TAG, String index) {
		if (LOG_OPEN) {
			Log.d(MEMORY_TAG, "in " + TAG + " ----" + index + ":"
					+ Runtime.getRuntime().totalMemory() / (1024 * 1024) + "."
					+ Runtime.getRuntime().totalMemory() % (1024 * 1024) + "M");
			Log.d(MEMORY_TAG, "in " + TAG + " ----" + index + ":" + "可用空间=="
					+ Runtime.getRuntime().freeMemory() / (1024 * 1024) + "."
					+ Runtime.getRuntime().freeMemory() % (1024 * 1024) + "M");

		}
	}

	public static void setLogFilePath(String path) {
		MYLOG_PATH_SDCARD_DIR = path;
	}

	/**
	 * 在写本框架的时候才允许被打印,如果不想打印,修改{@link InitUtil.FINAL} = true;
	 * 
	 * @param TAG
	 * @param message
	 */
	public static void d(Object obj, String message) {
		if (obj != null && message != null) {
			if (LOG_OPEN_DEBUG) {
				Log.d(obj.toString(), message);
				writeLogtoFile("Debug", obj.toString(), message);
			}
		}
	}

	private static boolean openLog() {
		return LOG_OPEN;
	}

	/**
	 * 打印给使用本框架的应用,如果不想打印,修改{@link InitUtil.FINAL} = true;
	 * 
	 * @param TAG
	 * @param message
	 */
	public static void i(Object TAG, String message) {
		if (message != null && message != null) {
			if (openLog()) {
				Log.i(TAG.toString(), message);
				writeLogtoFile("Info", TAG.toString(), message);
			}
		}
	}

	public static void w(Object TAG, String message) {
		if (message != null && message != null) {
			if (openLog()) {
				Log.w(TAG.toString(), message);
				writeLogtoFile("Waring", TAG.toString(), message);
			}
		}

	}

	public static void e(Object tag, String message) {
		if (message != null && message != null) {
			if (openLog()) {
				Log.e(tag.toString(), message);
				writeLogtoFile("Error", tag.toString(), message);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static void printMap(Object obj,
			Map<? extends Object, ? extends Object> map) {
		if (obj != null) {
			if (obj instanceof Class) {
				printMap((Class<Object>) obj, map);
			} else {
				printMap(obj.getClass().getName(), map);
			}
		}
	}

	public static void printMap(Class<? extends Object> TAG,
			Map<? extends Object, ? extends Object> map) {
		printMap(TAG.getName(), map);
	}

	/**
	 * 打印键值对存储map . 调试需要.
	 * 
	 * @param TAG
	 * @param map
	 */
	public static void printMap(String TAG,
			Map<? extends Object, ? extends Object> map) {
		if (!LOG_OPEN) {
			return;
		}
		Set<? extends Object> sets = map.keySet();
		i(TAG,
				"begin print map-------------------------------------------------");
		for (Object set : sets) {
			if (set == null) {
				continue;
			}
			String keys = set.toString();
			if (set instanceof Integer[]) {
				Integer[] tempInt = (Integer[]) set;
				keys = Arrays.toString(tempInt);
			}
			i(TAG, "map_Key:" + keys + "    value:" + map.get(set));
		}
		i(TAG,
				"end   print map-------------------------------------------------");
	}

	/**
	 * 打开日志文件并写入日志
	 * 
	 * @return
	 * **/
	private static void writeLogtoFile(String mylogtype, String tag, String text) {

		if (!pointToFile) {
			return;
		}

		// 新建或打开日志文件
		Date nowtime = new Date();
		String needWriteFiel = logfile.format(nowtime);
		String needWriteMessage = myLogSdf.format(nowtime) + " [" + mylogtype
				+ "] [" + tag + "] " + text;
		File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel
				+ MYLOGFILEName);
		try {
			File logRootFile = new File(MYLOG_PATH_SDCARD_DIR);
			if (!logRootFile.exists()) {
				logRootFile.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
			BufferedWriter bufWriter = new BufferedWriter(filerWriter);
			bufWriter.write(needWriteMessage);
			bufWriter.newLine();
			bufWriter.close();
			filerWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除制定的日志文件
	 * */
	public static void delFile() {// 删除日志文件
		String needDelFiel = logfile.format(getDateBefore());
		File file = new File(MYLOG_PATH_SDCARD_DIR, needDelFiel + MYLOGFILEName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
	 * */
	private static Date getDateBefore() {
		Date nowtime = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowtime);
		now.set(Calendar.DATE, now.get(Calendar.DATE)
				- SDCARD_LOG_FILE_SAVE_DAYS);
		return now.getTime();
	}

}
