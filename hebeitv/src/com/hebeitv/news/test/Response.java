package com.hebeitv.news.test;

import com.google.gson.annotations.SerializedName;


/**
 * 返回解析类
 * 
 * @Description:
 * @ClassName:	Response 
 * @author: XM 
 * @date: 2015年11月6日 上午09:09:30
 * 
 * @param <T>
 */
public class Response<T> {
    @SerializedName("success")
	public int abc;
//	@SerializedName("info")
//	public String info;
	public T result;
    @Override
    public String toString()
    {
        return "Response [abc=" + abc + ", result=" + result + "]";
    }
	
}
