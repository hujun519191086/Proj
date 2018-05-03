package cn.tlrfid.engine.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.tlrfid.anno.net.HttpUtils;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.LogUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

public class UniversalEngineImpl implements UniversalEngine {
	// 09-08 17:32:20.249: D/ProjectScheduleEngineImpl(1964):
	// url:http://182.92.76.78:8080/spms/service/queryProjectSchedule.action?projectCode=35kVBDZ
	
	private static final String TAG = "UniversalEngineImpl";
	private HashMap<String, Object> ignoreJson;
	
	@Override
	public <T> T getUrlBean(String url, Class<? extends BaseBean> clazz) {
		String json = HttpUtils.getJson_Asyn(url);
		LogUtil.d(TAG, "url:" + url + "   json:" + json);
		if (json == null) {
			return null;
		}
		return parseString(json, clazz);
	}
	
	public <T> T parseString(String json, Class<? extends BaseBean> clazz) {
		Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		});
		
		String infoTitle = InstanceFactory.getInfoTitle(clazz);
		LogUtil.d(TAG, "infoTitle:" + infoTitle + "      cnc" + map + "jsonIntType:" + map.get(ConstantValues.STATUS));
		final Object obj = map.get(infoTitle);
		
		int errorcode = Integer.valueOf((Integer) map.get(ConstantValues.STATUS));
		if (errorcode == ConstantValues.STATUS_CODE_FAULT) {
			return null;
		}
		
		if (ignoreJson == null) {
			ignoreJson = new HashMap<String, Object>();
		} else {
			ignoreJson.clear();
		}
		Set<String> sets = map.keySet();
		
		for (String set : sets) {
			if (!set.equals(infoTitle) && !set.equals(ConstantValues.STATUS)) {
				ignoreJson.put(set, map.get(set));
			}
		}
		LogUtil.d(TAG, "obj:" + obj);
		return getBean(obj, clazz);
	}
	
	public HashMap<String, Object> getIgnoreJson() {
		if (ignoreJson == null) {
			ignoreJson = new HashMap<String, Object>();
		}
		
		return ignoreJson;
	}
	
	public <T> T getBean(Object obj, Class<? extends BaseBean> clazz) {
		// if (json instanceof String) {
		// return parseString((String) json, clazz);
		// }
		
		T t = null;
		try {
			if (obj instanceof JSONArray) {
				t = (T) JSON.parseArray(JSON.toJSONString(obj), clazz);
			} else {
				t = (T) JSON.parseObject(JSON.toJSONString(obj), clazz);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
}
