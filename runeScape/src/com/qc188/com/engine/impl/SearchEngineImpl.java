package com.qc188.com.engine.impl;

import java.util.ArrayList;
import java.util.TreeMap;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.bean.SortBean;
import com.qc188.com.engine.SearchEngine;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class SearchEngineImpl implements SearchEngine {

	private static final String TAG = "SearchEngineImpl";

	@Override
	public ArrayList<SortBean> getSearchList_async() {
		String json = HttpClientUtils.getJson(ConstantValues.SORT_ITEM_URL);
		return getSortList(json);
	}

	public ArrayList<SortBean> getSortList(String json) {
		ArrayList<SortBean> beanList = new ArrayList<SortBean>();
		if (!TextUtils.isEmpty(json)) {
			Gson gson = new Gson();
			ConstantValues.homeUrlPreference.edit()
					.putString(ConstantValues.SORT_ITEM_URL, json).commit();
			try {
				TreeMap map = gson.fromJson(json, TreeMap.class);
				ArrayList list = (ArrayList) map.get("brandInfo");
				for (int i = 0; i < list.size(); i++) {
					LinkedTreeMap treeMap = (LinkedTreeMap) list.get(i);
					SortBean bean = new SortBean();
					bean.setBrand_id(((Double) treeMap.get("brand_id"))
							.intValue());

					// LogUtils.d(TAG,
					// treeMap.get("msg_id").getClass().getSimpleName());
					bean.setType_id(treeMap.get("type_id") + "");
					String str = (String) treeMap.get("car_brand");
					bean.setCar_brand(str);

					bean.setBrand_url(treeMap.get("brand_url") + "");
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

	public ArrayList<SearchContentBean> getSearchContent_Async(String brandId) {

		UniversalEngine ue = InstanceFactory
				.getInstances(UniversalEngine.class);

		LogUtil.d(TAG, "searchALlUrl:"
				+ (ConstantValues.SEARCH_CONTENT + brandId));
		return ue.getUrlBean(ConstantValues.SEARCH_CONTENT + brandId,
				SearchContentBean.class);

	}

	@Override
	public ArrayList<SearchContentBean> getSearchOnsale_Async(String brandId) {

		UniversalEngine ue = InstanceFactory
				.getInstances(UniversalEngine.class);

		LogUtil.d(TAG, "searchOnsaleUrl:"
				+ (ConstantValues.SEARCH_ON_SALE_CONTENT + brandId));
		return ue.getUrlBean(ConstantValues.SEARCH_ON_SALE_CONTENT + brandId,
				SearchContentBean.class);
	}
}
