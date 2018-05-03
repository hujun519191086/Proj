package cn.tlrfid.engine.impl;

import java.util.Map;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.tlrfid.anno.net.HttpUtils;
import cn.tlrfid.engine.ProjectInfoEngine;
import cn.tlrfid.framework.BaseBean;

public class ProjectInfoEngineImpl implements ProjectInfoEngine {
	
	@Override
	public <T> T getProjectInfo(String url, Class<? extends BaseBean> clazz) {
		String json = HttpUtils.getJson_Asyn(url);
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		
		T t = null;
		try {
			
			Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
			});
			
			final Object obj = map.get("projectInfo");
			
			t = (T) JSON.parseObject(JSON.toJSONString(obj), clazz);
			System.out.println(t);
			return t;
		} catch (Exception e) {
			return null;
		}
	}
	
}
