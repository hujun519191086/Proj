package com.qc188.com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 判断手机是否联网
 * 
 * @return
 */
public final class IsNetwork {
	public static boolean isNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		if (connectivityManager.getActiveNetworkInfo() == null) {
			return false;
		}
		return connectivityManager.getActiveNetworkInfo().isAvailable();
	}

	// 是否是wifi网络
	public static boolean isWIFINetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void connectNet(Context context, WebView webView, String url, RelativeLayout relativeLayout) {
		// webView.loadUrl(url);
		Log.e("stopnet", "start check");
		if (isNetwork(context)) {
			relativeLayout.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
			Log.e("stopnet", "start load");
			webView.loadUrl(url);
		} else {
			relativeLayout.setVisibility(View.VISIBLE);
			webView.setVisibility(View.GONE);
			Toast.makeText(context, "手机网络不通,请先打开网络连接！", Toast.LENGTH_LONG).show();
		}
		Log.e("stopnet", "end check");
	}

	/**
	 * 提示用户使用的网络
	 * 
	 * @return
	 */
	public static int currentNetwork(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = mgrConn.getActiveNetworkInfo();
		if (networkInfo != null) {
			if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) { // 是否是wifi
				return 1; //
			}
			if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {// 是否是3G网络
				return 2; //
			}
		}
		return 0;
	}

	/**
	 * 提示用户网络入口
	 * 
	 * @param webView
	 * @param url
	 */
	public static boolean currentConnectNet(Context context) {
		if (currentNetwork(context) == 1) {
			Toast.makeText(context, "您当前是wifi网络,舒心上网吧!", Toast.LENGTH_LONG).show();
			return false;
		} else if (currentNetwork(context) == 2) {
			Toast.makeText(context, "当前使用3G网络，将会耗费大量流量.使用wifi会给你带来更完美的体验", Toast.LENGTH_LONG).show();
		} else if (currentNetwork(context) == 0) {
			Toast.makeText(context, "手机网络不通,请先打开网络连接！", Toast.LENGTH_LONG).show();
		}
		return true;
	}
}