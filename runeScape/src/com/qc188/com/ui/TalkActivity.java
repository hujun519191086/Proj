package com.qc188.com.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.qc188.com.R;
import com.qc188.com.framwork.BaseActivity;

public class TalkActivity extends BaseActivity {
    private ProgressBar wv_club_loadProgress;
    private WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.club);
        setTitleContent("论坛");
        wv_club_loadProgress = findById(R.id.wv_club_loadProgress);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        wv = findById(R.id.wv_club_web);
        wv.loadUrl("http://club.qc188.com/");// m失效
        wv.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                wv.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                wv_club_loadProgress.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                wv_club_loadProgress.setVisibility(View.VISIBLE);
            }
        });
	}
}
