package com.hebeitv.news.view;

import java.io.File;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.MrYang.zhuoyu.utils.LogInfomation;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.media.MediaActivity;
import com.hebeitv.news.utils.DownloadUtil;
import com.hebeitv.news.utils.DownloadUtil.OnDownloadFinishCallBack;

public class WebActivity extends FrameActivity implements OnDownloadFinishCallBack
{

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("网页", true, false);
        setCenterContent(R.layout.web);
        String url = getIntent().getStringExtra("url");
        LogInfomation.d(TAG, "url:" + url);
        WebView wv_web = findById(R.id.wv_web);

        // // 设置WebView属性，能够执行Javascript脚本
        // wv_web.getSettings().setJavaScriptEnabled(true);
        // // wv_web.getSettings().setPluginsEnabled(true);
        // wv_web.getSettings().setPluginState(PluginState.ON);
        // wv_web.getSettings().setUseWideViewPort(true);
        //

        if (url.endsWith("mp4"))
        {
            String localUrl = DownloadUtil.download(url, this);
            if (!TextUtils.isEmpty(localUrl))
            {
                startActivity(MediaActivity.class);

            }
        }
        else
        {
            wv_web.loadUrl(url);
        }

    }

    // 01-03 13:08:10.473: D/com.hebeitv.news.view.WebActivity(5166): url:http://121.28.74.226:9090/HebTVNewsMediaPlatform/uploadVedio/recording243430375.mp4

    @Override
    protected void init()
    {
        setCommonTopBack();
    }

    @Override
    public void onFinish(File obj)
    {
        LogInfomation.d(TAG, "downLoadFinish:" + obj);
    }
}
