package cn.tlrfid.anno.net1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;

public class HttpUtil {
	public static HttpResult HttpPost(Context context, String httpUrl, HashMap<String, String> httpParams,
			int retryCount) {
		
		String errorResult = "";
		HttpURLConnection httpURLConnection = null;
		for (int i = 0; i < retryCount; i++) {
			try {
				StringBuffer stringBuffer = new StringBuffer();
				if (httpParams != null) {
					Iterator<String> iterator = httpParams.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						String value = httpParams.get(key);
						stringBuffer.append(key);
						stringBuffer.append("=");
						stringBuffer.append(URLEncoder.encode(value, "utf-8"));
						if (iterator.hasNext()) {
							stringBuffer.append("&");
						}
					}
				}
				
				byte[] entitydata = stringBuffer.toString().getBytes();
				URL url = new URL(httpUrl);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("POST");
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setConnectTimeout(15 * 1000);
				httpURLConnection.setReadTimeout(15 * 1000);
				httpURLConnection.setDoOutput(true);
				
				httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(entitydata.length)); // 传递数据的长据
				OutputStream outStream = httpURLConnection.getOutputStream();
				outStream.write(entitydata);
				// 把内存中的数据刷新输送给对方
				outStream.flush();
				outStream.close();
				
				int res = httpURLConnection.getResponseCode();
				if (res == HttpURLConnection.HTTP_OK) {
					InputStream in = httpURLConnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
					StringBuffer resultBuffer = new StringBuffer();
					String result = null;
					while ((result = bufferedReader.readLine()) != null) {
						resultBuffer.append(result);
					}
					return new HttpResult(true, resultBuffer.toString());
				}
			} catch (Exception e) {
			} finally {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return new HttpResult(false, errorResult);
	}
	
	public static HttpResult HttpGet(Context context, String httpUrl, HashMap<String, String> httpParams, int retryCount) {
		String errorResult = "";
		HttpURLConnection httpURLConnection = null;
		for (int i = 0; i < retryCount; i++) {
			try {
				StringBuffer stringBuffer = new StringBuffer();
				if (httpParams != null) {
					stringBuffer.append("?");
					Iterator<String> iterator = httpParams.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();
						String value = httpParams.get(key);
						stringBuffer.append(key);
						stringBuffer.append("=");
						stringBuffer.append(URLEncoder.encode(value, "utf-8"));
						if (iterator.hasNext()) {
							stringBuffer.append("&");
						}
					}
				}
				String urlString = httpUrl + stringBuffer.toString();
				URL url = new URL(urlString);
				
				System.out.println("url= " + url);
				httpURLConnection = (HttpURLConnection) url.openConnection();
				httpURLConnection.setRequestMethod("GET");
				// httpURLConnection.setDoOutput(true);
				httpURLConnection.setDoInput(true);
				httpURLConnection.setUseCaches(false);
				httpURLConnection.setConnectTimeout(15 * 1000);
				httpURLConnection.setReadTimeout(15 * 1000);
				httpURLConnection.connect();
				
				int res = httpURLConnection.getResponseCode();
				if (res == HttpURLConnection.HTTP_OK) {
					InputStream in = httpURLConnection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
					StringBuffer resultBuffer = new StringBuffer();
					String result = null;
					while ((result = bufferedReader.readLine()) != null) {
						resultBuffer.append(result);
					}
					return new HttpResult(true, resultBuffer.toString());
				}
			} catch (Exception e) {
			} finally {
				if (httpURLConnection != null) {
					httpURLConnection.disconnect();
				}
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return new HttpResult(false, errorResult);
	}
	
}
