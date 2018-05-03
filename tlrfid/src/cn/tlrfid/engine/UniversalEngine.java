package cn.tlrfid.engine;

import java.util.HashMap;
import java.util.List;

import cn.tlrfid.framework.BaseBean;

public interface UniversalEngine {
	public <T> T getUrlBean(String url, Class<? extends BaseBean> clazz);
	
	public HashMap<String, Object> getIgnoreJson();
}
