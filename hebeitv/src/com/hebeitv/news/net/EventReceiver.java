package com.hebeitv.news.net;

import com.google.gson.annotations.SerializedName;

/**
 * 返回解析类
 * 
 * @Description:
 * @ClassName: Response
 * @author: XM
 * @date: 2015年11月6日 上午09:09:30
 * 
 * @param <T>
 */
public class EventReceiver<T>
{
    @SerializedName("success")
    public String success;
    @SerializedName("info")
    public String info;
    @SerializedName("result")
    public T result;
    @Override
    public String toString()
    {
        return "EventReceiver [success=" + success + ", info=" + info + ", result=" + result + "]";
    }


}
