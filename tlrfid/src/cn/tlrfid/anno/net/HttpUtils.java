package cn.tlrfid.anno.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.util.Log;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.LogUtil;

public class HttpUtils {
	
	private static final String TAG = "HttpClientUtils";
	
	// 08-26 15:39:27.833: D/HttpClientUtils(1848):
	// url:http://182.92.76.78:8080/spms/service/login.action?loginName=admin&password=123&projectCode=35kVBDZ
	
	public static String getJson_Asyn(String path) {
		try {
			LogUtil.d(TAG, "url:" + path);
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(ConstantValues.CONNECTION_TIMEOUT);
			conn.setReadTimeout(ConstantValues.CONNECTION_READ_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			Log.d(TAG, "conn");
			int response = conn.getResponseCode();
			Log.d(TAG, "response:" + response);
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
			return null;
		}
		return null;
	}
	
	/* 上传文件至Server的方法 */
	public void uploadFile(Context context, String actionUrl) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;");
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			// ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + newName + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			// FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			// while ((length = fStream.read(buffer)) != -1) {
			// /* 将资料写入DataOutputStream中 */
			// ds.write(buffer, 0, length);
			// }
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			// fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */
			AlertDialogUtil.getInstance().showLoading(context, "上传成功");
			/* 关闭DataOutputStream */
			ds.close();
		} catch (Exception e) {
			AlertDialogUtil.getInstance().showLoading(context, "上传失败");
		}
	}
	
}
