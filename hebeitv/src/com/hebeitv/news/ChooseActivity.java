package com.hebeitv.news;

import android.os.Bundle;

import com.hebeitv.news.frame.FrameActivity;

/**
 * 选择
 * 
 * @author jieranyishen
 * 
 */
public class ChooseActivity extends FrameActivity
{
    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        // startActivity(TestActivity.class);
        startActivity(SplashActivity.class);
        finish();
    }
}
