package cn.tlrfid.engine.impl;

import java.util.List;
import java.util.Map;

import android.text.TextUtils;
import cn.tlrfid.anno.net.HttpUtils;
import cn.tlrfid.bean.warehouse.AssetsCategoryBean;
import cn.tlrfid.bean.warehouse.DevicePagerBean;
import cn.tlrfid.engine.WarehouseEngine;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.LogUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class WarehouseEngineImpl implements WarehouseEngine {
	private static final String TAG = "WarehouseEngineImpl";
	private List<AssetsCategoryBean> acBeanList;
	private DevicePagerBean dpBean;
	
	@Override
	public String initConnectionJson(String url) {
		String json = HttpUtils.getJson_Asyn(url);
		LogUtil.d(TAG, "url:" + url + "  json:" + json);
		if (TextUtils.isEmpty(json)) {
			return "网络错误!";
		}
		
		Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		});
		int errorcode = Integer.valueOf((Integer) map.get(ConstantValues.STATUS));
		if (errorcode == ConstantValues.STATUS_CODE_FAULT) {
			return (String) map.get("errMessage");
		}
		Object obj = map.get("assetsCategoryList");
		LogUtil.d(TAG, "assetsCategoryList:" + obj);
		acBeanList = JSON.parseArray(JSON.toJSONString(obj), AssetsCategoryBean.class);
		obj = map.get("devicePage");
		LogUtil.d(TAG, "devicePage:" + obj);
		dpBean = JSON.parseObject(JSON.toJSONString(obj), DevicePagerBean.class);
		
		return null;
	}
	
	public List<AssetsCategoryBean> getAssetsList() {
		// LogUtil.d(TAG, "AssetsCategoryBean:" + acBeanList.toString());
		return acBeanList;
	}
	
	public DevicePagerBean getDevicePager() {
		// LogUtil.d(TAG, "DevicePagerBean:" + dpBean.toString());
		return dpBean;
	}
	
}
