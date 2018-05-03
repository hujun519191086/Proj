package com.qc188.com.test;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//public class MainActivity extends Activity {
//	private WebView mWebView;
//	private boolean isOnPause = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		hardwareAccelerate();
//		initWebView();
//	}
//
//	// 硬件加速
//	private void hardwareAccelerate() {
//		if (this.getPhoneSDKInt() >= 14) {
//			getWindow().setFlags(0x1000000, 0x1000000);
//		}
//	}
//
//	// 设置WebView
//	private void initWebView() {
//		mWebView = (WebView) findViewById(R.id.webView);
//		mWebView.setVerticalScrollBarEnabled(false);
//		mWebView.setHorizontalScrollBarEnabled(false);
//		mWebView.getSettings().setSupportZoom(true);
//		mWebView.getSettings().setBuiltInZoomControls(true);
//		mWebView.getSettings().setJavaScriptEnabled(true);
//
//		mWebView.getSettings().setDomStorageEnabled(true);
//		// mWebView.getSettings().setPluginsEnabled(true);
//		mWebView.requestFocus();
//
//		// 以下两句和硬件加速有关
//		mWebView.getSettings().setPluginState(PluginState.ON);
//		mWebView.getSettings().setAllowFileAccess(true);
//
//		mWebView.getSettings().setUseWideViewPort(true);
//		mWebView.getSettings().setLoadWithOverviewMode(true);
//		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//
//		mWebView.loadUrl("http://www.sharejs.com/codes/java/7814");
//		mWebView.setWebViewClient(new TestWebViewClient());
//	}
//
//	/**
//	 * 当Activity执行onPause()时让WebView执行pause
//	 */
//	@Override
//	protected void onPause() {
//		super.onPause();
//		try {
//			if (mWebView != null) {
//				mWebView.getClass().getMethod("onPause")
//						.invoke(mWebView, (Object[]) null);
//				isOnPause = true;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 当Activity执行onResume()时让WebView执行resume
//	 */
//	@Override
//	protected void onResume() {
//		super.onResume();
//		try {
//			if (isOnPause) {
//				if (mWebView != null) {
//					mWebView.getClass().getMethod("onResume")
//							.invoke(mWebView, (Object[]) null);
//				}
//				isOnPause = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 该处的处理尤为重要: 应该在内置缩放控件消失以后,再执行mWebView.destroy() 否则报错WindowLeaked
//	 */
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (mWebView != null) {
//			mWebView.getSettings().setBuiltInZoomControls(true);
//			mWebView.setVisibility(View.GONE);
//			long delayTime = ViewConfiguration.getZoomControlsTimeout();
//			new Timer().schedule(new TimerTask() {
//				@Override
//				public void run() {
//					mWebView.destroy();
//					mWebView = null;
//				}
//			}, delayTime);
//
//		}
//		isOnPause = false;
//	}
//
//	private class TestWebViewClient extends WebViewClient {
//		@Override
//		public void onPageStarted(WebView view, String url, Bitmap favicon) {
//			super.onPageStarted(view, url, favicon);
//		}
//
//		@Override
//		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			view.loadUrl(url);
//			return true;
//		}
//
//		@Override
//		public void onPageFinished(WebView view, String url) {
//			super.onPageFinished(view, url);
//
//		}
//
//		@Override
//		public void onReceivedError(WebView view, int errorCode,
//				String description, String failingUrl) {
//			super.onReceivedError(view, errorCode, description, failingUrl);
//		}
//	}
//
//	public int getPhoneSDKInt() {
//		int version = 0;
//		try {
//			version = Integer.valueOf(android.os.Build.VERSION.SDK);
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//		return version;
//	}
// }