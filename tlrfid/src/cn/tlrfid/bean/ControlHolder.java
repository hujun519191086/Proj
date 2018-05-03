package cn.tlrfid.bean;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.view.adapter.ProjectPersonAdapter;

public class ControlHolder {
	private static final String TAG = "ControlHolder";
	private TextView progressmanager_name;
	private TextView progressmanager_pdCode;
	private TextView progressmanager_address;
	private TextView progressmanager_construction;
	private TextView progressmanager_pm;
	private ListView progressmanager_personList;
	
	public void init(View view) {
		Field[] fields = this.getClass().getDeclaredFields();
		Resources res = view.getResources();
		for (Field field : fields) {
			int id = res.getIdentifier(field.getName(), "id", view.getContext().getPackageName());
			field.setAccessible(true);
			if (id != 0) {
				try {
					field.set(this, view.findViewById(id));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			field.setAccessible(false);
		}
	}
	
	public void preperData(Context context, ProjectInfoBean pib) {
		LogUtil.d(TAG, "pib:" + pib + "  progressmanager_name: " + progressmanager_name);
		
		progressmanager_name.setText(pib.getName());
		progressmanager_pdCode.setText(pib.getPdCode());
		progressmanager_address.setText(pib.getAddress());
		progressmanager_construction.setText(pib.getConstruction());
		progressmanager_pm.setText(pib.getPm());
		
		List<PersonBean> beanList = pib.getPersonList();
		progressmanager_personList.setAdapter(new ProjectPersonAdapter(context, progressmanager_personList, beanList));
	}
}
