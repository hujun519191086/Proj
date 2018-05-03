package com.qc188.com.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class HttpClientUtils {
	private static final String TAG = "HttpClientUtils";

	public static String getJson(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(ConstantValues.CONNECTION_TIMEOUT);
			conn.setReadTimeout(ConstantValues.CONNECTION_READ_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			LogUtil.d(TAG, "conn");
			int response = conn.getResponseCode();
			LogUtil.d(TAG, "response:" + response);
			if (response == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				int len = 0;
				byte[] buffer = new byte[2048];

				InputStream in = conn.getInputStream();
				while ((len = in.read(buffer)) != -1) {
					bout.write(buffer, 0, len);
				}
				String result = new String(bout.toByteArray(), "utf-8");
				in.close();

				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.d(TAG, "e:" + e);
			return null;
		}
		return null;
	}

	public static Map<String, Object> getJSONMap(String json) {
		if (TextUtils.isEmpty(json)) {
			return null;
		}

		try {
			Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
			});

			Integer status = (Integer) map.get("status");
			if (status != 0) {
				return null;
			}
			return map;
		} catch (Exception e) {
			return null;
		}

	}
}
