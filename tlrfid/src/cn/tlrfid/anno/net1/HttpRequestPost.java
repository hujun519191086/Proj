package cn.tlrfid.anno.net1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author yin.xinya Http请求发送
 */
public class HttpRequestPost extends AsyncTask<Void, Void, String> {
	HttpURLConnection httpURLConnection;
	String httpUrl;
	HashMap<String, String> httpParams;
	HttpRequestCallBack callBack;
	boolean isSuccess;
	private Context context;
	int retryCount = 2;
	volatile boolean isRunning = false;
	
	public HttpRequestPost(Context context, HttpRequestCallBack callBack, String httpUrl,
			HashMap<String, String> httpParams) {
		this.context = context;
		this.callBack = callBack;
		this.httpUrl = httpUrl;
		this.httpParams = httpParams;
		isSuccess = false;
		System.out.println("urL:" + httpUrl);
	}
	
	protected void onPreExecute() {
		super.onPreExecute();
		isRunning = true;
		
		if (callBack == null) {
			cancel(true);
			return;
		}
		if (!NetworkDetector.isNetworkAvailable(context) && callBack instanceof Activity) { // 无可用网络
			cancel(true);
			return;
		}
		callBack.start();
	}
	
	@Override
	protected String doInBackground(Void... params) {
		// 请求两次服务器
		HttpResult ret = null;
		ret = HttpUtil.HttpPost(context, httpUrl, httpParams, 2);
		
		isSuccess = ret.isSuccess;
		
		// 成功返回从服务器获取的数据
		return ret.returnString;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			if (callBack != null) {
				if (isSuccess) {
					callBack.success(result);
				} else {
					callBack.error(result);
				}
			}
		} finally {
			isRunning = false;
		}
	}
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		if (callBack != null) {
			callBack.cancel();
		}
	}
	
	public interface HttpRequestCallBack {
		/**
		 * 开始发送请求 可以在这个函数弹出进度条
		 */
		void start();
		
		/**
		 * 请求正常处理 响应码为200
		 * 
		 * @param message
		 */
		void success(String message);
		
		/**
		 * 请求异常 响应码不为200或网络问题
		 * 
		 * @param message
		 */
		void error(String message);
		
		/**
		 * 手动取消
		 */
		void cancel();
		
	}
	
}
