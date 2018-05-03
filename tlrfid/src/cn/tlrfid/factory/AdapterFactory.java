package cn.tlrfid.factory;

import java.lang.reflect.Field;

import android.content.Context;
import android.view.View;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.LogUtil;

public class AdapterFactory {
	private static final String TAG = "AdapterFactory";
	
	public static View putHolderToView(Context context, View convertView, BaseViewHolder holder) {
		Field[] fields = holder.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals("position")) {
				continue;
			}
			field.setAccessible(true);
			View view = convertView.findViewById(context.getResources().getIdentifier(field.getName(), "id",
					context.getPackageName()));
			LogUtil.d(TAG, "findViewById(field.getName()):" + view);
			if (view == null) {
				throw new IllegalStateException("findViewById(R.id." + field.getName() + ") gave null for "
						+ convertView + ", can't inject");
			}
			try {
				
				field.set(holder, view);
				
			} catch (IllegalAccessException e) {
				LogUtil.e(TAG, "IllegalAccessException:" + e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				LogUtil.e(TAG, "IllegalArgumentException:" + e);
				e.printStackTrace();
			}
			field.setAccessible(false);
		}
		
		convertView.setTag(holder);
		LogUtil.i(TAG, "not exception:" + convertView.getTag());
		return convertView;
	}
}
