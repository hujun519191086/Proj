package com.qc188.com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * 日志信息 @ @
 */
public class LogUtil {

	/**
	 * 日志开关
	 */
	protected static final boolean LOG_OPEN_DEBUG = true;
	protected static final boolean LOG_OPEN_POINT = false;

	/**
	 * 日志类型开关，必须 LOG_OPEN_DEBUG = true的时候才能启作用
	 */
	protected static boolean logOpeni = true;
	protected static boolean logOpend = true;
	protected static boolean logOpenw = true;
	protected static boolean logOpene = true;

	/**
	 * 日志目录
	 */
	protected static final String PATH_ROOT = Environment
			.getExternalStorageDirectory() + "/projectName/";
	protected static final String PATH_LOG_INFO = PATH_ROOT + "info/";
	protected static final String PATH_LOG_WARNING = PATH_ROOT + "warning/";
	protected static final String PATH_LOG_ERROR = PATH_ROOT + "error/";

	public static void d(String TAG, String message) {
		if (TAG != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpend) {
				Log.d(TAG, message);
			}
			if (LOG_OPEN_POINT)
				point(PATH_LOG_INFO, TAG, message);
		}
	}

	public static void i(String TAG, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpeni) {
				Log.i(TAG, message);
			}
			if (LOG_OPEN_POINT)
				point(PATH_LOG_INFO, TAG, message);
		}

	}

	public static void w(String TAG, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpenw) {
				Log.w(TAG, message);
			}
			if (LOG_OPEN_POINT)
				point(PATH_LOG_WARNING, TAG, message);
		}

	}

	public static void e(String TAG, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpene) {
				Log.e(TAG, message);
			}
			if (LOG_OPEN_POINT)
				point(PATH_LOG_ERROR, TAG, message);
		}

	}

	public static void point(String path, String TAG, String msg) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("",
				Locale.SIMPLIFIED_CHINESE);
		dateFormat.applyPattern("yyyy");
		path = path + dateFormat.format(date) + "/";
		dateFormat.applyPattern("MM");
		path += dateFormat.format(date) + "/";
		dateFormat.applyPattern("dd");
		path += dateFormat.format(date) + ".log";
		dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]");
		String time = dateFormat.format(date);
		File file = new File(path);
		if (!file.exists())
			createDipPath(path);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(time + " " + TAG + " " + msg + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 根据文件路径 递归创建文件
	 * 
	 * @param file
	 */
	public static void createDipPath(String file) {
		String parentFile = file.substring(0, file.lastIndexOf("/"));
		File file1 = new File(file);
		File parent = new File(parentFile);
		if (!file1.exists()) {
			parent.mkdirs();
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static long depleteTime;

	/**
	 * 消费时间记录器
	 */
	public static void systemCurrentBegin() {
		depleteTime = System.currentTimeMillis();
	}

	/**
	 * 打印每次消耗时间长
	 * 
	 * @param TAG
	 */
	public static void systemCurrentLog(String TAG, String tagOffset) {

		if (depleteTime == 0) {
			systemCurrentBegin();
		}
		d(TAG,
				"tagOffset:" + tagOffset + "__depleteTime:"
						+ (System.currentTimeMillis() - depleteTime));
		systemCurrentBegin();
	}
}
