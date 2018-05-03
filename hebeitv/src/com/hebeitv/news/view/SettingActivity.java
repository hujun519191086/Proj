package com.hebeitv.news.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.utils.SharedPerferenceUtil;

public class SettingActivity extends FrameActivity implements OnSeekBarChangeListener, OnCheckedChangeListener
{

    @ResetControl(id = R.id.sb_settingProgress)
    private SeekBar sb;
    @ResetControl(id = R.id.cb_openPush)
    private CheckBox openPush;
    @ResetControl(id = R.id.tv_logout, clickThis = true)
    private TextView logout;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("我的设置", true, false, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        setCenterContent(R.layout.setting);
    }

    @Override
    protected void init()
    {
        setCommonTopBack();

        sb.setOnSeekBarChangeListener(this);
        sb.setProgress(SharedPerferenceUtil.getFontSizeIndex());
        openPush.setOnCheckedChangeListener(this);
        openPush.setChecked(SharedPerferenceUtil.getOpenPushCheck());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        SharedPerferenceUtil.putNowUseFontSize(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        SharedPerferenceUtil.putOpenPushCheck(isChecked);
    }

    public static final int LOGOUT_RESOULT = 11;

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_logout:
                ILogin login = getModule(ILogin.class);
                login.logout();
                setResult(LOGOUT_RESOULT);
                finish();
                break;

            default:
                break;
        }
    }
}
