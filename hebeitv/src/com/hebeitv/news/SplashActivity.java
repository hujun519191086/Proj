package com.hebeitv.news;

import android.os.Bundle;
import android.view.View;

import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.hebeitv.news.jni.JNISupport;
import com.hebeitv.news.utils.FakeUtil;
import com.hebeitv.news.view.main.CenterActivity;

public class SplashActivity extends BaseActivity
{

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        setView(R.layout.splash);
        View view = findById(R.id.splah_view);
        view.setBackgroundDrawable(ImageManager.getManger().getDrawable(this.getClass(), view, JNISupport.getSplashResource(getApplication())));

        postDelay(new Runnable()
        {

            @Override
            public void run()
            {
                startActivity(CenterActivity.class);
                finish();
            }
        }, FakeUtil.getValue(5000, 200));

    }

}
