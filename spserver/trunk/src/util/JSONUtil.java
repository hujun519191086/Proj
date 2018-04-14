package util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

import control.bean.Person;

/**
 * 简单json工具
 * @author jieranyishen
 *
 */
public class JSONUtil {

	private static Gson gson = new Gson();

	public static String toJSON(Object bean) {

		
		if(bean instanceof Person)
		{
			Person temp = (Person) bean;
			temp.setVerificationCode("");//抹除验证码
		}
		String jsonValue =  gson.toJson(bean);
		return jsonValue;
	}

	public static <T>T fromJSON(Class<T> clazz ,String jsonStr) {

		return gson.fromJson(jsonStr, clazz);
	}
	public static <T>T fromJSON(Type clazz ,String jsonStr) {

		return gson.fromJson(jsonStr, clazz);
	}
}
