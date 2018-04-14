package com.MrYang.zhuoyu.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;

import com.MrYang.zhuoyu.utils.HttpUtils;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

/**
 * 统一的JSON解析Engine
 * 
 * @author jieranyishen
 * 
 */

public class UniversalJsonEngine
{
    private HashMap<String, Object> ignoreJson;
    private int status_error_value = InitUtil.ERROR_VALUES;
    private String statusJsonTitle = "";

    /**
     * 设置错误返回码的json鍵值對
     * 
     * @param statusTitle
     *            返回码的键
     * @param error_value
     *            返回码的值, 错误值,比如-1
     */
    public void setStatus_ErrorValue(String statusTitle, int error_value)
    {
        this.statusJsonTitle = statusTitle;
        this.status_error_value = error_value;
    }
    
    /**
     * 联网并且获取值.
     * 
     * @param url
     *            要获取数据的url
     * @param clazz
     *            要赋值的Bean.
     * @param infoTitle
     *            Json中对应的键.
     * @return 可以返回一个list
     */
    public <T> T getUrlBean(String url, @SuppressWarnings("rawtypes") Class clazz, String infoTitle)
    {
        String json = HttpUtils.getJson_Asyn(url);
        if (json == null)
        {
            return null;
        }
        return parseString(json, clazz, infoTitle);
    }

    public <T> T parseString(String json, @SuppressWarnings("rawtypes") Class clazz, String infoTitle)
    {
        Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>()
        {
        });

        final Object obj = map.get(infoTitle);

        if (TextUtils.isEmpty(statusJsonTitle))// 检查有没有设置返回码的检测
        {
            Object statusMapValue = map.get(statusJsonTitle);

            if (statusMapValue != null)
            {
                int errorcode = Integer.valueOf((Integer) statusMapValue);
                if (errorcode == status_error_value)
                {
                    return null;
                }
            }
        }// end

        getIgnoreJson(); // 初始化被忽略列表
        Set<String> sets = map.keySet();
        for (String set : sets)
        {
            if (!set.equals(infoTitle) && !set.equals(statusJsonTitle))
            {
                ignoreJson.put(set, map.get(set));
            }
        }
        return getBean(obj, clazz);
    }

    public HashMap<String, Object> getIgnoreJson()
    {
        if (ignoreJson == null)
        {
            ignoreJson = new HashMap<String, Object>();
        }
        else
        {
            ignoreJson.clear();
        }

        return ignoreJson;
    }

    /**
     * 獲取单个Bean. obj必须为json格式
     * 
     * @param obj
     *            内容json
     * @param clazz
     *            要賦值的类
     * @return 返回那个类,可以是list
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Object obj, @SuppressWarnings("rawtypes") Class clazz)
    {
        T t = null;
        try
        {
            if (obj instanceof JSONArray)
            {
                t = (T) JSON.parseArray(JSON.toJSONString(obj), clazz);
            }
            else
            {
                t = (T) JSON.parseObject(JSON.toJSONString(obj), clazz);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }

}
