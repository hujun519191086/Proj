package com.qc188.com.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qc188.com.R;
import com.qc188.com.bean.MsgContentBean;
import com.qc188.com.engine.MsgContentEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class ContentActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = "ContentActivity";
	private TextView tv_content_msg_title;
	private TextView tv_content_other;
	private boolean inAsync = false;

	// 12-04 09:43:32.599: D/ContentActivity(2057):
	// onPageStarted:http://m.qc188.com/NApi.asp?dir=pcsj&id=39752&page=11

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		init();
	}

	private WebView wv_content;

	@Override
	protected void onStop() {
		super.onStop();
		wv_content.clearHistory();
		wv_content.clearCache(false);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void init() {
		LogUtil.d(TAG, " inContent");
		fromTAG = getIntent().getStringExtra(ConstantValues.FROMEWHERE);

		if (fromTAG.equals(ConstantValues.FROM_ADV)) {

			advBean = (MsgContentBean) getIntent().getSerializableExtra(ConstantValues.TO_CONTENT_TAG);

			tagName = "广告";
		} else {
			msgID = getIntent().getStringExtra(ConstantValues.TO_CONTENT_TAG);
			tagName = getIntent().getStringExtra(ConstantValues.TITLE_NAME);
		}
		rl_progressLoad = (RelativeLayout) findViewById(R.id.rl_progressLoad);
		new LoadContentAsyncTask(this).execute(msgID, "0");
		sv_contnet_realContent = (ScrollView) findViewById(R.id.sv_contnet_realContent);
		Button bt_content_refresh = (Button) findViewById(R.id.bt_content_refresh); // 刷新按钮
		tv_content_msg_title = (TextView) findViewById(R.id.tv_content_msg_title);
		tv_content_other = (TextView) findViewById(R.id.tv_content_other);
		wv_content = (WebView) findViewById(R.id.wv_content);
		// wv_content.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wv_content.getSettings().setJavaScriptEnabled(true);
		wv_content.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				LogUtil.d(TAG, "onProgressChanged:" + newProgress);

				if (newProgress > 30 && newProgress < 35) {
					wv_content.scrollTo(0, 0);
					sv_contnet_realContent.scrollTo(0, 0);
				}

				super.onProgressChanged(view, newProgress);
			}
		});

		/**
		 * 中间翻页
		 */
		iv_content_back = (ImageView) findViewById(R.id.iv_content_back);
		iv_content_back.setOnClickListener(this);
		tv_content_nowTag = (TextView) findViewById(R.id.tv_content_nowTag);
		iv_content_next = (ImageView) findViewById(R.id.iv_content_next);
		iv_content_next.setOnClickListener(this);
		// ------------------------

		bt_content_refresh.setOnClickListener(this);

	}

	protected void onResume() {
		super.onResume();
		visibleBackButton();
		setTitleContent(tagName);
	}

	private void preperData(MsgContentBean result) {
		tv_content_msg_title.setText(result.getDeital_title());
		String other = "";
		if (TextUtils.isEmpty(result.getIs_self())) {

		} else {

			// other = result.getTime() + "  " + result.getFrom() + "  " +
			// (result.getIs_self().equals("true") ? "原创" : "摘抄") + "   " +
			// result.getName();
		}
		other = "";
		tv_content_other.setText(other);
		pageCount = result.getPage_count();
		nowPage = result.getIndex();
		LogUtil.d(TAG, "click" + result.getMsg_id());
		setPage(nowPage, true, result);
	}

	private int nowPage;
	private int pageCount;

	public void setPage(int now, boolean scorlltoTop, MsgContentBean result) {
		if (now < 0 || now >= (result.getPage_count() + 1)) {
			nowPage = 0;
			return;
		}
		if (result.getIndex() != now) {

			if (fromTAG.contains(ConstantValues.HOME_PRAISE_URL_Content)) {
				if (result.getIndex() != now) {
					String tag = getIntent().getStringExtra(ConstantValues.TO_CONTENT_TAG);
					new LoadContentAsyncTask(this).execute(tag, now + "");
					return;
				}
				tv_content_nowTag.setText((result.getIndex() + 1) + "/" + (result.getPage_count()));

				// wv_content.loadData("<html><head>test<a href=\"http://wap.bai.com\">中文</a></html>",
				// "text/html", "UTF-8");
				wv_content.loadUrl(result.getDetail_content());

				return;
			}
		}
		// wv_content.loadData("<html><head>test<a href=\"http://wap.bai.com\">中文</a></html>",
		// "text/html", "UTF-8");
		LogUtil.d(TAG, "webView:" + result.getDetail_content() + " result:" + result);
		wv_content.loadUrl(result.getDetail_content());
		tv_content_nowTag.setText((now + 1) + "/" + (result.getPage_count()));

	}

	@Override
	public void onClick(View v) {

		if (inAsync) {
			Toast.makeText(getApplicationContext(), "请等待本次数据加载完毕!", Toast.LENGTH_SHORT).show();
			return;
		}
		switch (v.getId()) {
		case R.id.bt_content_refresh:
			new LoadContentAsyncTask(this).execute(msgID, nowPage + "");
			break;

		case R.id.iv_content_next:
			if (nowPage < pageCount - 1) {
				new LoadContentAsyncTask(this).execute(msgID, (nowPage + 1) + "");
			}
			break;
		case R.id.iv_content_back:
			if (nowPage > 0) {
				new LoadContentAsyncTask(this).execute(msgID, "" + (nowPage - 1));
			}
			break;
		default:
			Toast.makeText(getApplicationContext(), "未知错误...", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private String fromTAG;
	private String tagName;
	private ImageView iv_content_back;
	private TextView tv_content_nowTag;
	private ImageView iv_content_next;

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private MsgContentEngine mcEngine = InstanceFactory.getInstances(MsgContentEngine.class);
	private RelativeLayout rl_progressLoad;
	private ScrollView sv_contnet_realContent;
	private String msgID;
	private MsgContentBean advBean;

	private class LoadContentAsyncTask extends BaseAsync<String, MsgContentBean> {

		public LoadContentAsyncTask(Activity activity) {
			super(activity, false);
		}

		@Override
		protected MsgContentBean doInBackground(String... params) {
			inAsync = true;
			LogUtil.d(TAG, " index:" + params.length + " params:" + params[0] + "  fromTAG:" + fromTAG + "  params[1]:" + params[1]);

			if (advBean == null) {
				return mcEngine.getContent_async(Double.valueOf(params[0]).intValue(), fromTAG, Integer.valueOf(params[1]));
			} else {
				advBean.setIndex(Integer.valueOf(params[1]));
				LogUtil.d(TAG, "advUrl:" + advBean.getDetail_content());
				return advBean;
			}
		}

		@Override
		protected void onPre() {
			rl_progressLoad.setVisibility(View.VISIBLE);
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPost(MsgContentBean result) {
			// TODO Auto-generated method stub
			inAsync = false;
			if (result == null) {
				Toast.makeText(getApplicationContext(), "加载失败!", Toast.LENGTH_LONG).show();
				finish();
				return;
			}
			rl_progressLoad.setVisibility(View.GONE);
			preperData(result);
			if (fromTAG.contains(ConstantValues.HOME_PRAISE_URL_Content)) {
			} else {
			}
		}
	}
}
