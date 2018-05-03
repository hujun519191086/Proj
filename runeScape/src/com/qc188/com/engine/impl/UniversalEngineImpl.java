package com.qc188.com.engine.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.framwork.BaseBean;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.ConstantValues.STATUS;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class UniversalEngineImpl implements UniversalEngine
{

    private static final String TAG = "UniversalEngineImpl";
    private HashMap<String, Object> ignoreJson;

    @Override
    public <T> T getUrlBean(String url, Class<?> clazz)
    {
        String json = HttpClientUtils.getJson(url);
        LogUtil.d(TAG, "url:" + url + "   json:" + json);
        if (json == null)
        {
            return null;
        }
        return parseString(json, clazz);
    }

    // 11-06 21:00:10.239: D/UniversalEngineImpl(7663):
    // url:http://www.qc188.com/app/photo.asp?id=1039&page=0 json:{"status": 0,
    // 12-03 03:25:30.704: D/BrandDetail_Pic(3355): url:http://www.qc188.com/app/photo.asp?id=1313&tutype=2&color=BFC1C7&carList=3986&page=0

    public <T> T parseString(String json, Class<?> clazz)
    {

        Object obj = getParseObj(json, clazz);
        return getBean(obj, clazz);
    }

    public Object getParseObj(String json, Class<?> clazz)
    {
        Map<String, Object> map = parseMap(json);
        if (map == null)
        {
            return null;
        }
        LogUtil.d(TAG, "cnc" + map + "jsonIntType:" + map.get(ConstantValues.STATUS));
        String infoTitle = InstanceFactory.getInfoTitle(clazz);
        final Object obj = map.get(infoTitle);

        int errorcode = Integer.valueOf((Integer) map.get(ConstantValues.STATUS));
        if (errorcode == STATUS.FAULT)
        {
            return null;
        }

        if (ignoreJson == null)
        {
            ignoreJson = new HashMap<String, Object>();
        }
        else
        {
            ignoreJson.clear();
        }
        Set<String> sets = map.keySet();

        for (String set : sets)
        {
            if (!set.equals(infoTitle) && !set.equals(ConstantValues.STATUS))
            {
                ignoreJson.put(set, map.get(set));
            }
        }
        LogUtil.d(TAG, "obj:" + obj);
        return obj;
    }

    private Map<String, Object> parsemap;

    public Map<String, Object> parseMap(String json)
    {
        if (parsemap == null) parsemap = JSON.parseObject(json, new TypeReference<Map<String, Object>>()
        {
        });
        return parsemap;
    }

    public HashMap<String, Object> getIgnoreJson()
    {
        if (ignoreJson == null)
        {
            ignoreJson = new HashMap<String, Object>();
        }

        return ignoreJson;
    }

    public <T> T parseString(String json, String infoTitle, Class<? extends BaseBean> clazz)
    {

        Map<String, Object> map = parseMap(json);

        LogUtil.d(TAG, "cnc" + map + "jsonIntType:" + map.get(ConstantValues.STATUS));
        final Object obj = map.get(infoTitle);
        LogUtil.d(TAG, "infoTitle:" + infoTitle + "   obj:" + obj);
        return getBean(obj, clazz);
    }

    public Map<String, Object> getParseMap()
    {
        return parsemap;
    }

    public <T> T getBean(Object json, Class<?> clazz)
    {
        T t = null;
        try
        {
            if (json instanceof JSONArray)
            {
                t = (T) JSON.parseArray(JSON.toJSONString(json), clazz);
            }
            else
            {
                t = (T) JSON.parseObject(JSON.toJSONString(json), clazz);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

}
