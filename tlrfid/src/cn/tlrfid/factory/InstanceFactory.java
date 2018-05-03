package cn.tlrfid.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.view.LoginActivity;

public class InstanceFactory {
	
	private static final String TAG = "InstanceFactory";
	
	/**
	 * 可读取任何配置文件. 只要传入响应文件名称. 默认根目录:src下 注意:传入值的时候必须注意路径!!!报错首先查看路径问题!
	 * 
	 * @param resourceName 文件名
	 * @return 可使用的Properties 空代表读取失败
	 */
	public static Properties getPropertiesFroResourceName(String resourceName) {
		Properties p = null;
		
		// 配置文件在class下，即Src下
		InputStream is = LoginActivity.class.getClassLoader().getResourceAsStream(resourceName);
		
		if (is != null) {
			try {
				p = new Properties();
				p.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
	private static Properties proEngine;
	private static Properties proUrlTitle;
	static {
		proEngine = new Properties();
		proUrlTitle = new Properties();
		// 配置文件在class下，即Src下
		try {
			proEngine.load(LoginActivity.class.getClassLoader().getResourceAsStream("Engine.properties"));
			proUrlTitle.load(LoginActivity.class.getClassLoader().getResourceAsStream("UrlTitle.properies"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取json的键
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getInfoTitle(Class<? extends BaseBean> clazz) {
		String infoTitle = proUrlTitle.getProperty(clazz.getSimpleName());
		LogUtil.d(TAG, "infoTitle:" + infoTitle);
		return infoTitle;
	}
	
	public static <T> T getEngine(Class<T> clazz) {
		if (proEngine != null) {
			try {
				String name = proEngine.getProperty(clazz.getSimpleName());
				LogUtil.d(TAG, name);
				return (T) Class.forName(name).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 创建任何实例.只能实例化在Engine.properties文件内配置过的类
	 * 
	 * @param interfaceName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstances(String interfaceName) {
		
		if (proEngine != null) {
			try {
				Constructor<T> c = (Constructor<T>) Class.forName(proEngine.getProperty(interfaceName))
						.getDeclaredConstructors()[0];
				c.setAccessible(true);
				T t = c.newInstance(new Object[0]);
				c.setAccessible(false);
				return t;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static <T> T getInstances(Class<T> clazz) {
		try {
			if (clazz == null) {
				return null;
			}
			Constructor<T> c = (Constructor<T>) clazz.getDeclaredConstructors()[0];
			c.setAccessible(true);
			T t = c.newInstance(null);
			c.setAccessible(false);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
