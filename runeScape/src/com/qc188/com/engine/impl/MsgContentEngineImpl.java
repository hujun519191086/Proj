package com.qc188.com.engine.impl;

import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qc188.com.bean.MsgContentBean;
import com.qc188.com.engine.MsgContentEngine;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class MsgContentEngineImpl implements MsgContentEngine {

	private static final String TAG = "MsgContentEngineImpl";

	public MsgContentBean getContent_async(int msg_id, String url, int index) {
		LogUtil.d(
				TAG,
				"url:" + (url + msg_id + "&page_index=" + index) + "    url.contains(ConstantValues.HOME_PRAISE_URL_Content)?:"
						+ (url.contains(ConstantValues.HOME_PRAISE_URL_Content)));
		url = url + msg_id + "&page_index=" + index;

		UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
		return ue.getUrlBean(url, MsgContentBean.class);
	}

	public MsgContentBean getADVContent_async(String url, int index) {
		LogUtil.d(
				TAG,
				"url:" + ("&page_index=" + index) + "    url.contains(ConstantValues.HOME_PRAISE_URL_Content)?:"
						+ (url.contains(ConstantValues.HOME_PRAISE_URL_Content)));
		url += index;

		UniversalEngine ue = InstanceFactory.getInstances(UniversalEngine.class);
		return ue.getUrlBean(url, MsgContentBean.class);
	}

}
