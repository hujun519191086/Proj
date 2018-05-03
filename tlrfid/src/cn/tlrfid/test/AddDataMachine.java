package cn.tlrfid.test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.text.TextUtils;

/**
 * 增加数据的机器人
 * 
 * @author Administrator
 * 
 */
public class AddDataMachine {
	
	/**
	 * 赋值一个数据
	 * 
	 * @param obj 要赋值的数据
	 * @param values 如果要赋值的成员变量有int值,期望的偏移量
	 * @return
	 */
	public static Object add(Object obj, String values) {
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object tempO = field.get(new Object());
				if (tempO instanceof String) {
					field.set(obj, "机器人" + values);
				} else if (tempO instanceof Integer) {
					if (TextUtils.isDigitsOnly(values)) {
						field.set(obj, Integer.valueOf(values) + 99);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		return obj;
	}
	
	/**
	 * 赋值一组数据
	 * 
	 * @param objList 一组数据
	 * @param count 需要赋值多少个
	 * @return
	 */
	public static <T> List<T> add(Class<T> objList, int count) {
		//09-07 07:43:30.742: D/LoginActivity(1824): logurl:http://182.92.76.78:8080/spms/service/login.action?loginName=admin&password=123&projectCode=35kVBDZ

		List<T> list = (List<T>) Arrays.asList(Array.newInstance(objList, count));
		for (int i = 0; i < count; i++) {
			add(list.get(i), i + "");
		}
		return list;
	}
}
