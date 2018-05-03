package com.qc188.com.util;

import java.io.IOException;
import java.util.Properties;

import com.qc188.com.framwork.BaseBean;
import com.qc188.com.ui.MainActivity;

public class InstanceFactory {
	private static final String TAG = "InstanceFactory";
	private static Properties p;
	private static Properties proUrlTitle;
	static {
		p = new Properties();
		proUrlTitle = new Properties();
		// 配置文件在class下，即Src下
		try {
			p.load(MainActivity.class.getClassLoader().getResourceAsStream("engine.properties"));
			proUrlTitle.load(ConstantValues.class.getClassLoader().getResourceAsStream("UrlTitle.properies"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取json的键
	 * 
	 * @param <T>
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> String getInfoTitle(Class<T> clazz) {
		String infoTitle = proUrlTitle.getProperty(clazz.getSimpleName());
		LogUtil.d(TAG, "infoTitle:" + infoTitle);
		return infoTitle;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstances(Class<T> clazz) {
		String name = clazz.getSimpleName();

		if (p != null) {
			try {
				return (T) Class.forName(p.getProperty(name)).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
