package com.hebeitv.news.net;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.apache.http.Header;

import android.text.TextUtils;

import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.StringMap;
import com.hebeitv.news.frame.Global_hebei;
import com.hebeitv.news.utils.FakeUtil;
import com.hebeitv.news.utils.WindowUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class HTTPResponseAsync extends TextHttpResponseHandler
{
    public static final String TAG = "HTTPResponseAsync";

    private AsyncHttpClient mHttpClient = new AsyncHttpClient();
    private Gson mGson = new Gson();
    private EventSender sender;

    public HTTPResponseAsync()
    {
        mHttpClient.setTimeout(Global_hebei.NET_OUT_OF_TIME);
    }

    public void excutePost(EventSender sender)
    {
        this.sender = sender;
        LogInfomation.i(TAG, "post_url:" + sender.getServerName());

        if (FakeUtil.getValue(true, true, false))
        {
            WindowUtil.ins().showLoading();
        }
        mHttpClient.post(InitUtil.getInitUtil().context, sender.getServerName(), sender.getParams(), this);

    }

    @Override
    public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3)
    {
        WindowUtil.ins().dissmissLoading();

        LogInfomation.e(TAG, "onFailure:" + arg2 + "  Throwable:" + arg3);
        if (sender != null && arg3 instanceof Exception)
        {
            sender.callFault(arg0, arg1, arg2, new IllegalArgumentException("发送失败!服务器异常!"));
        }
    }

    /**
     * 好不容易拼接
     * 
     * 登录成功 "{ \"success\": \"true\",\"info\": \"这是info字段\",\"result\":{\"rName\":\"快了\",\"rPass\":\"qqqqqq\",\"rId\":\"27\"}}";
     * 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void onSuccess(int arg0, Header[] arg1, String content)
    {
        WindowUtil.ins().dissmissLoading();
        // {"result":
        // [{"pId":"p34","title":"图片6","pPath":"http:\/\/121.28.74.226:9090\/HebTVNewsMediaPlatform\/uploadImage\/21.png","userId":37,"type":"photo","pTime":"2015-12-09"},
        // {"pId":"p44","title":"","pPath":"http:\/\/121.28.74.226:9090\/HebTVNewsMediaPlatform\/uploadImage\/ewm.png","userId":37,"type":"photo","pTime":"2015-12-31"},
        // {"title":"dududu","prePath":"http:\/\/121.28.74.226:9090\/HebTVNewsMediaPlatform\/uploadImage\/hebtvTemp.png","userId":37,"vId":"v23","vTime":"2015-12-31","type":"vedio","vPath":"http:\/\/121.28.74.226:9090\/HebTVNewsMediaPlatform\/uploadVedio\/recording243430375.mp4"}],"success":true,"info":"已经是最后一页了"}
        if (content.equals("{}"))
        {
            return;
        }
        LogInfomation.d(TAG, "onSuccess:" + content);
        if (sender != null)
        {
            try
            {
                EventReceiver r = mGson.fromJson(content, EventReceiver.class);

                if (r != null && !TextUtils.isEmpty(r.success) && r.success.equals("true"))
                {

                    Object result = null;
                    Object requestBean = sender.getRequestBean();
                    try
                    {

                        if (r.result != null && requestBean != null)
                        {
                            if (requestBean instanceof Class)
                            {

                                requestBean = ((Class) requestBean).newInstance();
                            }

                            if (r.result instanceof StringMap)
                            {
                                result = analyticalStringMap(requestBean, (StringMap) r.result);
                            }
                            else if (r.result instanceof ArrayList)
                            {
                                ArrayList<StringMap> list = (ArrayList<StringMap>) r.result;

                                ArrayList tempResultList = null;

                                if (list.size() > 0)
                                {
                                    tempResultList = new ArrayList();
                                    for (int i = 0; i < list.size(); i++)
                                    {
                                        tempResultList.add(analyticalStringMap(requestBean.getClass().newInstance(), list.get(i)));
                                    }
                                }
                                result = tempResultList;

                            }
                        }
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                    sender.setRequestBean(result);
                    if (result == null)
                    {
                        content = r.info;
                    }
                    sender.callSuccess(content);
                }
                else
                {
                    sender.callFault(arg0, arg1, content, new NullPointerException(TextUtils.isEmpty(r.info) ? "解析失败!" : r.info));
                }

            }
            catch (JsonSyntaxException e)
            {
                sender.callFault(arg0, arg1, content, new JsonSyntaxException("JSON解析出错!"));
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                sender.callFault(arg0, arg1, content, new IllegalArgumentException("解析失败,bean字段传递错误!"));
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                sender.callFault(arg0, arg1, content, new IllegalAccessException("权限异常!"));
                e.printStackTrace();
            }
        }
    }

    private Object analyticalStringMap(Object bean, @SuppressWarnings("rawtypes") StringMap map) throws IllegalAccessException, IllegalArgumentException
    {

        if (map.size() > 0)
        {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                field.setAccessible(true);
                SerializedName name = field.getAnnotation(SerializedName.class);
                Object mapValue = null;
                if (name != null)
                {
                    mapValue = map.get(name.value());
                }
                else
                {
                    mapValue = map.get(field.getName());
                }
                if (mapValue != null)
                {
                    if (field.getType() == String.class)
                    {
                        field.set(bean, String.valueOf(mapValue));
                    }
                    else
                    {
                        field.set(bean, mapValue);
                    }

                }
                field.setAccessible(false);
            }
            sender.setRequestBean(bean);
        }
        else
        {
            return null;
        }
        return bean;
    }
}
