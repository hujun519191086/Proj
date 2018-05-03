package com.qc188.com.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.qc188.com.bean.HomeADVBean;
import com.qc188.com.bean.HomeItemBean;
import com.qc188.com.engine.MainHomeEngine;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class MainHomeEngineImpl implements MainHomeEngine {

	private static final String TAG = "MainHomeEngineImpl";

	public ArrayList<HomeItemBean> getItemList_async(String url) {

		// url = "http://www.qc188.com/app/cwcsv.asp?msg_id=15555&page_index=0";

		// 09-04 16:59:01.231: D/MainHomeEngineImpl(7820):
		// url:http://www.qc188.com/app/pcsj.asp

		String json = HttpClientUtils.getJson(url);
		LogUtil.d(TAG, "url:" + url + "    json:" + json);
		UniversalEngine ue = InstanceFactory
				.getInstances(UniversalEngine.class);

		ConstantValues.homeUrlPreference.edit().putString(url, json).commit();
		// return ue.parseString(url, HomeItemBean.class);
		return ue.getUrlBean(url, HomeItemBean.class);
		// 09-04 16:46:53.701: D/MainHomeEngineImpl(6800):
		// url:http://www.qc188.com/app/ztdg.asp

		// return getItemListFromString(json);
	}

	public static ArrayList<HomeItemBean> getItemListFromString(String json) {

		ArrayList<HomeItemBean> beanList = new ArrayList<HomeItemBean>();
		if (!TextUtils.isEmpty(json)) {
			Gson gson = new Gson();
			try {
				TreeMap map = gson.fromJson(json, TreeMap.class);
				ArrayList list = (ArrayList) map.get("msg");
				for (int i = 0; i < list.size(); i++) {
					LinkedTreeMap treeMap = (LinkedTreeMap) list.get(i);
					HomeItemBean bean = new HomeItemBean();
					bean.setMsg_id((Double) treeMap.get("msg_id"));

					// LogUtils.d(TAG,
					// treeMap.get("msg_id").getClass().getSimpleName());
					bean.setMsg_title((String) treeMap.get("msg_title"));
					String str = (String) treeMap.get("msg_isHead");
					bean.setMsg_isHead(str);

					bean.setTime(treeMap.get("time") + "");
					bean.setMsg_comments(treeMap.get("msg_comments") + "");
					bean.setCar_imageUrl((String) treeMap.get("car_imageUrl"));

					beanList.add(bean);
				}
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
		return beanList;
	}

	/**
	 * 06-03 15:06:37.336: D/MainHomeEngineImpl(2967): {msg_id=15863.0,
	 * msg_title=推荐中高配车型 海马福美来M5购车导购, msg_isHead=true, time=2014-6-3 20:05:00,
	 * msg_comments=0,
	 * car_imageUrl=http://img.qc188.com/UserDocument/admin/20146358173.
	 * jpg}/.....LinkedTreeMap
	 */
	public ArrayList<HomeItemBean> getHomeItemList_async() {
		String json = HttpClientUtils.getJson(ConstantValues.HOME_ITEM_URL);
		ConstantValues.homeUrlPreference.edit().putString(
				ConstantValues.HOME_ITEM_URL, json);
		LogUtil.d(TAG, "home item:" + json);
		ArrayList<HomeItemBean> beanList = new ArrayList<HomeItemBean>();
		if (!TextUtils.isEmpty(json)) {
			Gson gson = new Gson();

			try {

				TreeMap map = gson.fromJson(json, TreeMap.class);

				ArrayList list = (ArrayList) map.get("msg");
				for (int i = 0; i < list.size(); i++) {
					LinkedTreeMap treeMap = (LinkedTreeMap) list.get(i);
					HomeItemBean bean = new HomeItemBean();
					bean.setMsg_id((Double) treeMap.get("msg_id"));

					// LogUtils.d(TAG,
					// treeMap.get("msg_id").getClass().getSimpleName());
					bean.setMsg_title((String) treeMap.get("msg_title"));
					String str = (String) treeMap.get("msg_isHead");
					bean.setMsg_isHead(str);

					bean.setTime(treeMap.get("time") + "");
					bean.setMsg_comments(treeMap.get("msg_comments") + "");
					bean.setCar_imageUrl((String) treeMap.get("car_imageUrl"));

					beanList.add(bean);

				}
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
		return beanList;
	}

	public List<HomeADVBean> getHomeAdv_Async() {
		String json = HttpClientUtils.getJson(ConstantValues.HOME_ADV_URL);
		
		//05-23 11:36:51.084: D/MainHomeEngineImpl(1557):  getHomeAdv_Async adv:____URL:http://www.qc188.com/app/adv.asp    joson:null

		LogUtil.d(TAG, " getHomeAdv_Async adv:____URL:"
				+ ConstantValues.HOME_ADV_URL + "    joson:" + json);
		if (TextUtils.isEmpty(json)) {
			return null;
		}

		Map<String, Object> map = HttpClientUtils.getJSONMap(json);

		LogUtil.d(TAG, "map:" + map);
		List<HomeADVBean> list = null;
		if (map != null) {
			list = JSON.parseArray(map.get("advUrl").toString(),
					HomeADVBean.class);
		}
		LogUtil.d(TAG, "json:" + list);
		return list;
	}
}
