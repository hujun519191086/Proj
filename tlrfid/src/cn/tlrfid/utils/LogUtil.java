package cn.tlrfid.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
	
	public static void d(String TAG, String message) {
		if (TAG != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpend) {
				Log.d(TAG, message);
			}
		}
		
	}
	
	public static void i(String TAG, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpeni) {
				Log.i(TAG, message);
			}
		}
		
	}
	
	public static void w(String TAG, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpenw) {
				Log.w(TAG, message);
			}
		}
		
	}
	
	public static void e(String tag, String message) {
		if (message != null && message != null) {
			if (LOG_OPEN_DEBUG && logOpene) {
				Log.e(tag, message);
			}
		}
		
	}
	
	public static void point(String path, String tag, String msg) {
		
		File file = new File(path);
		if (!file.exists())
			createDipPath(path);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
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
}
