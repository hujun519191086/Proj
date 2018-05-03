package com.qc188.com.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.qc188.com.R;
import com.qc188.com.framwork.BaseActivity;

public class DepreciateActivity extends BaseActivity {
    private WebView wv;
    private ProgressBar wv_club_loadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.club);
        setTitleContent("降价");
        wv = findById(R.id.wv_club_web);
        wv_club_loadProgress = findById(R.id.wv_club_loadProgress);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        wv.loadUrl("http://t.m.qc188.com/");//
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
