package com.qc188.com.ui.manager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.qc188.com.util.DataPackageUtil;
import com.qc188.com.util.LogUtil;

public class CompairManager extends Observable {


	public static final CompairManager manager = new CompairManager();

	private static final String TAG = "CompairManager";

	public static CompairManager getManger() {
		return manager;
	}

	public final String COMPAIR_SHARED = "compair";

	private SharedPreferences sp;

	private int selectItemCount = 0;

	public boolean isFull(){
    	Map<String, ?>    allMap = sp.getAll();
    	if(allMap!=null){
			return allMap.size() > 8;
    	}
		return false;
    }

	public void initMap(Activity activity) {
		sp = activity.getSharedPreferences(COMPAIR_SHARED, Context.MODE_PRIVATE);
	}

	public void deleteSelect() {
		Map<String, ?> sharedMap = getSharedMap();

		Set<String> keySet = sharedMap.keySet();
		for (String set : keySet) {
			String value = (String) sharedMap.get(set);
			LogUtil.d(TAG, "key:" + set + "  value:" + value);
			if (value.split("_")[0].equals("true")) {
				sp.edit().remove(set).commit();
			}
		}
	}

	public void clearSelect() {

		Map<String, ?> sharedMap = getSharedMap();

		Set<String> keySet = sharedMap.keySet();
		for (String set : keySet) {
			String value = (String) sharedMap.get(set);
			LogUtil.d(TAG, "key:" + set + "  value:" + value);
			if (value.split("_")[0].equals("true")) {
				modifyCompair(set, value.split("_")[1], false);
			}
		}

	}

	public int getSelectCount() {

		selectItemCount = 0;
		if (!hasCompairList()) {
			return 0;
		}
		Map<String, ?> sharedMap = getSharedMap();
		String[] valueSet = (String[]) sharedMap.values().toArray();
		for (int i = 0; i < valueSet.length; i++) {
			if (valueSet[i].split("_")[0].equals("true")) {
				selectItemCount++;
			}
		}
		return selectItemCount;
	}

	public ArrayList<Pair<String, String>> getSelectId() {
		if (!hasCompairList()) {
			return null;
		}
		ArrayList<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
		Map<String, ?> sharedMap = getSharedMap();
		Set<String> keySet = sharedMap.keySet();
		for (String set : keySet) {
			String value = (String) sharedMap.get(set);
			LogUtil.d(TAG, "key:" + set + "  value:" + value);
			if (value.split("_")[0].equals("true")) {
				list.add(new Pair<String, String>(set, value.split("_")[1]));
			}
		}
		return list;
	}

	public Map<String, ?> getSharedMap() {
		return sp.getAll();
	}

	public boolean hasCompairList() {
		if (sp == null) {
			return false;
		}
		return sp.getAll().size() > 0;
	}

	public boolean putCompair(String key, String value, boolean checked) {
		LogUtil.d(TAG, "putCompair: key :" + key + "     value:" + checked + "_" + DataPackageUtil.getPackage().get("carName") + " " + value);
		if (bt_brandDetail_compairCount != null) {
			bt_brandDetail_compairCount.setVisibility(View.VISIBLE);
		}
		Map<String, ?> maps = sp.getAll();
		if (maps != null) {
			if (maps.size() > 8) {
				return false;
			}
		}
		if (bt_brandDetail_compairCount != null) {
			bt_brandDetail_compairCount.setText((maps.size() + 1) + "");
		}
		return sp.edit().putString(key, checked + "_" + DataPackageUtil.getPackage().get("carName") + " " + value).commit();
	}

	public boolean modifyCompair(String key, String value, boolean checked) {
		Map<String, ?> maps = sp.getAll();

		LogUtil.d(TAG, "key :" + key + "hasMsg:" + maps.get(key));
		if (maps.get(key) != null) {
			sp.edit().putString(key, checked + "_" + value).commit();
		}
		return false;
	}

	public boolean putCompair(String key, String value) {
		return putCompair(key, value, false);
	}

	public boolean inCompairMap(String key) {

		Map<String, ?> maps = getSharedMap();
		if (maps != null) {
			return maps.get(key) != null;
		}
		return false;
	}

	public void clear() {
		if (bt_brandDetail_compairCount != null) {
			bt_brandDetail_compairCount.setVisibility(View.GONE);
		}
		if (sp != null) {
			sp.edit().clear().commit();
		}
	}

	public void showCompairView() {

		LogUtil.d(TAG, "showCompairView:" + bt_brandDetail_compairCount + "   size:" + sp.getAll().size());
		if (bt_brandDetail_compairCount == null) {
			return;
		}
		if (hasCompairList()) {
			LogUtil.d(TAG, "hasCompairList");
			bt_brandDetail_compairCount.setVisibility(View.VISIBLE);
			bt_brandDetail_compairCount.setText(sp.getAll().size() + "");
		} else {
			hideCompairView();
		}
	}

	public void hideCompairView() {
		if (bt_brandDetail_compairCount == null) {
			return;
		}

		bt_brandDetail_compairCount.setVisibility(View.GONE);

	}

	private TextView bt_brandDetail_compairCount;

	public void setCountView(TextView bt_brandDetail_compairCount) {
		this.bt_brandDetail_compairCount = bt_brandDetail_compairCount;
	}
}
