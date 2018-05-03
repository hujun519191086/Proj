package com.qc188.com.engine;

import java.util.HashMap;
import java.util.Map;

import com.qc188.com.framwork.BaseBean;

public interface UniversalEngine {
    public <T> T getUrlBean(String url, Class<?> clazz);

    public <T> T parseString(String json, Class<?> clazz);

    public Object getParseObj(String json, Class<?> clazz);

	public <T> T parseString(String json, String infoTitle, Class<? extends BaseBean> clazz);

	public Map<String, Object> parseMap(String json);

    public <T> T getBean(Object json, Class<?> clazz);

	public HashMap<String, Object> getIgnoreJson();

	public Map<String, Object> getParseMap();
}
