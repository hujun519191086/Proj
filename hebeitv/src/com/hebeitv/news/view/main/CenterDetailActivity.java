package com.hebeitv.news.view.main;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.ComnetListBean;
import com.hebeitv.news.bean.NewsInfoBean;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.utils.WindowUtil;
import com.hebeitv.news.view.login.LoginActivity;

/**
 * 详情
 * 
 * @author Administrator
 * 
 */
public class CenterDetailActivity extends FrameActivity
{
    @ResetControl(id = R.id.tv_center_detail_web)
    private WebView ww;
    @ResetControl(id = R.id.tv_center_detail_input)
    private EditText input;
    @ResetControl(id = R.id.tv_center_detail_totalComent,clickThis=true)
    private TextView coment;
    @ResetControl(id = R.id.tv_center_detail_shoucang,clickThis=true)
    private TextView shoucang;
    private NewsInfoBean newsInfoBean;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("", true, false, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        super.onCreateView(savedInstanceState);
        setCenterContent(R.layout.center_detail);

    }

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_center_detail_totalComent:
                startActivity(ComentListActivity.class,new SirPair<String, String>(COMENT_LIST_DATA_KEY, newsInfoBean.getId()));
                break;

            case R.id.tv_center_detail_shoucang:
                getModule(IMessage.class).collectionNews(newsInfoBean.getId(), this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void init()
    {
        setCommonTopBack();

        Intent intent = getIntent();
        newsInfoBean = (NewsInfoBean) intent.getSerializableExtra(CenterActivity.centerDetailSerializDataKey);
        LogInfomation.d(TAG, "webViewUrl:" + newsInfoBean);
        ww.loadUrl(newsInfoBean.getUrl());
        input.setFilters(new InputFilter[] { new InputFilter()
        {

            @Override
            public CharSequence filter(CharSequence arg0, int arg1, int arg2, Spanned arg3, int arg4, int arg5)
            {

                if (arg0.equals("\n"))
                {
                    sendInPut();
                }

                return null;
            }

        } });
        coment.setText(TextUtils.isEmpty(newsInfoBean.getTotalComment()) ? "0" : newsInfoBean.getTotalComment());
    }

    private void sendInPut()
    {

        ILogin login = getModule(ILogin.class);
        if (login.isAlive())
        {
            String inputCotent = input.getText().toString();
            if (TextUtils.isEmpty(inputCotent))
            {
                WindowUtil.showToast(this, "请输入评论内容!");
            }
            else
            {
                getModule(IMessage.class).commentNews(newsInfoBean.getId(), inputCotent, this);
                // TIPS 发送
            }
        }
        else
        {
            WindowUtil.showToast(this, "请先登录!");
            startActivity(LoginActivity.class);
        }

        input.setText("");
    }

    public static final String COMENT_LIST_DATA_KEY = "comentlistdatakey";

    @SuppressWarnings("unchecked")
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        switch (messageId)
        {
            case MessageId.MESSAGE_ID_COMENT_LIST:
                ArrayList<ComnetListBean> list = (ArrayList<ComnetListBean>) bean;
                startActivity(ComentListActivity.class, new SirPair<String, ArrayList<ComnetListBean>>(COMENT_LIST_DATA_KEY, list));
                break;

            default:
                break;
        }
    }
}
