package cn.tlrfid.factory;

import java.lang.reflect.Field;

import android.view.View;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.utils.LogUtil;

/**
 * 将每个activity的成员变量进行设置值
 * 
 * @author jieranyishen
 * 
 */
public class ValuesFactory {
	private static final String TAG = "ValuesFactory";
	
	public static void autoSetValues(BaseActivity baseActivity) {
		Field[] fileds = baseActivity.getClass().getDeclaredFields();
		LogUtil.d(TAG, "fileds:" + fileds);
		for (int i = 0; i < fileds.length; i++) {
			LogUtil.d(TAG, "fileds[i]" + fileds[i].getName());
			fileds[i].setAccessible(true);
			ViewById_Clickthis vbc = fileds[i].getAnnotation(ViewById_Clickthis.class);
			if (vbc != null) {
				setValues(baseActivity, fileds[i], true);
			} else {
				ViewById vbi = fileds[i].getAnnotation(ViewById.class);
				if (vbi != null) {
					setValues(baseActivity, fileds[i], false);
				}
			}
			fileds[i].setAccessible(false);
		}
	}
	
	private static void setValues(BaseActivity baseActivity, Field field, boolean setClick) {
		int id = baseActivity.getResources().getIdentifier(field.getName(), "id", baseActivity.getPackageName());
		if (id != 0) {
			try {
				View tempView = baseActivity.findViewById(id);
				field.set(baseActivity, tempView);
				if (setClick) {
					tempView.setOnClickListener(baseActivity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
