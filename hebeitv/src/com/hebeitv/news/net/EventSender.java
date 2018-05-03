package com.hebeitv.news.net;

import java.io.File;
import java.util.HashMap;

import org.apache.http.Header;

import android.text.TextUtils;

import com.MrYang.zhuoyu.utils.LogInfomation;
import com.google.gson.Gson;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.utils.FakeUtil;
import com.loopj.android.http.RequestParams;

public class EventSender
{
    // http://wangbo890505.imwork.net:9090/HebTVNewsMediaPlatform
    // http://121.28.74.226:9090/HebTVNewsMediaPlatform
    public static String SERVER_NAME = FakeUtil.getValue("http://121.28.74.226:9090/HebTVNewsMediaPlatform/app");// TIPS 需要加一个/app?????
    private String actionName = "";
    private String jsonPostKey = "";
    private int m_messageId;

    private HashMap<String, Object> jsonParams = new HashMap<String, Object>();// json 型 post的使用
    private RequestParams normalPostParams = new RequestParams();// 正常post的使用

    private Object requestBean;

    private boolean alertLoading = true;

    private Object senderData; // 发送者要传递给接受者的数据

    private EventSender(String actionName, Object requestBean)
    {
        if (actionName != null)
        {
            this.actionName = actionName;
        }
        this.requestBean = requestBean;
    }

    public void setSenderData(Object senderData)
    {
        this.senderData = senderData;
    }

    public void setRequestBean(Object requestBean)
    {
        this.requestBean = requestBean;
    }

    public Object getRequestBean()
    {
        return requestBean;
    }

    public String getActionName()
    {
        return actionName;
    }

    public void setActionName(String actionName)
    {
        this.actionName = actionName;
    }

    public <T> void put(String key, T value)
    {
        jsonParams.put(key, value);
    }

    public <T> void putNormalParam(String key, T value)
    {
        normalPostParams.put(key, value);
    }

    public void replaceNormalPost(RequestParams rp)
    {
        normalPostParams = rp;
    }

    public void setJsonPostKey(String jsonPostKey)
    {
        this.jsonPostKey = jsonPostKey;
    }

    public RequestParams getParams()
    {
        if (jsonParams.size() <= 0 || TextUtils.isEmpty(jsonPostKey))
        {
            return normalPostParams;
        }
        RequestParams mParams = new RequestParams();
        Gson gson = new Gson();
        String json = gson.toJson(jsonParams);
        LogInfomation.i("EventSender", "json:" + json);
        mParams.put(jsonPostKey, json);
        return mParams;
    }

    public boolean isAlertLoading()
    {
        return alertLoading;
    }

    public void setAlertLoading(boolean alertLoading)
    {
        this.alertLoading = alertLoading;
    }

    public void setSERVER_NAME(String sERVER_NAME)
    {
        if (!TextUtils.isEmpty(sERVER_NAME))
        {
            SERVER_NAME = sERVER_NAME;
        }
    }

    public String getServerName()
    {
        return SERVER_NAME + File.separator + getActionName();
    }

    private OnPostCallBack[] m_callBack;

    public void setPostCallBack(int messageId, Class<? extends OnPostCallBack>... callBackClass)
    {
        if (callBackClass != null)
        {
            OnPostCallBack[] callBacks = new OnPostCallBack[callBackClass.length];
            for (int i = 0; i < callBackClass.length; i++)
            {
                callBacks[i] = ModuleManager.getManager().getCallBack(callBackClass[i]);
            }
            setPostCallBack(messageId, callBacks);
        }
    }

    public void setPostCallBack(int messageId, OnPostCallBack... callBacks)
    {
        this.m_messageId = messageId;
        m_callBack = callBacks;
    }

    public void callSuccess(String resultContent)
    {
        call(true, 0, null, resultContent, null);
    }

    private <T> void call(boolean success, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        if (m_callBack != null)
        {
            for (int i = 0; i < m_callBack.length; i++)
            {
                OnPostCallBack call = m_callBack[i];

                if (success)
                {
                    if (requestBean != null)
                    {
                        call.onSuccess(m_messageId, requestBean, senderData);
                    }
                    else
                    {
                        call.onSuccess(m_messageId, reslutContent, senderData);
                    }

                }
                else
                {
                    call.onFault(m_messageId, errorCode, headers, reslutContent, e);
                }
            }
        }
    }

    public void callFault(int errorCode, org.apache.http.Header[] headers, String reslutContent, Exception e)
    {
        call(false, errorCode, headers, reslutContent, e);
    }

    public static EventSender getSender(String actionName)
    {
        return new EventSender(actionName, null);
    }

    public static EventSender getSender(Object bean)
    {
        return new EventSender(null, bean);
    }

    public static EventSender getSender(String actionName, Object bean)
    {
        return new EventSender(actionName, bean);
    }

}
