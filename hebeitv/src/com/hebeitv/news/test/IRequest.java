package com.hebeitv.news.test;

import java.lang.reflect.Type;

import android.content.Context;

import com.loopj.android.http.RequestParams;

/**
 * 
 * @Description: 网络访问的基本请求类
 * @ClassName: IRequest
 * @author: XM 
 * @date: 2015年11月6日 上午09:09:30
 * 
 */
public abstract class IRequest {
	public final static String SERVER_HOST = "http://101.73.102.59:9090/HebTVNewsMediaPlatform/com/hebtv/news";
	/**
	 * 不做解决类型
	 */
	public final static int RESPONSE_TYPE = 1000;
	/**
	 * 按对象解决
	 */
	public final static int RESPONSE_OBJECT_TYPE = 1001;

	private int mRequestId = 0;
	protected Context mContext;
	protected RequestParams mParams = new RequestParams();

	public final static int PAGE_SIZE = 10;

	public IRequest(Context context) {
		mContext = context;
//		mParams.put("deviceId", PhoneUtils.deviceUniqueId(mContext));
//		mParams.put("userId", PreManager.instance(mContext).getUserId());
	}

	public RequestParams getParams() {
		return mParams;
	}

	public String getPostData() {
		return null;
	}

	public Context getContext() {
		return mContext;
	}

	public int getRequestId() {
		return mRequestId;
	}

	public void setRequestId(int requestId) {
		mRequestId = requestId;
	}

	public abstract String getUrl();

	public abstract Type getParserType();

	public int getResponseType() {
		return RESPONSE_OBJECT_TYPE;
	}
}
