package com.hebeitv.news.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.view.person.MyReadActivity;

public class PersonCenterActivity extends FrameActivity
{
    @ResetControl(id = R.id.iv_personcenter_top_pic, drawable = R.drawable.person_upbg)
    private ImageView iv;

    @ResetControl(id = R.id.tv_myread_btn, clickThis = true)
    private TextView my_read;
    @ResetControl(id = R.id.tv_hotreg_btn, clickThis = true)
    private TextView hotreg;
    @ResetControl(id = R.id.tv_collection_btn, clickThis = true)
    private TextView collection;
    @ResetControl(id = R.id.tv_myUpload_btn, clickThis = true)
    private TextView myUpload;
    @ResetControl(id = R.id.tv_my_setting, clickThis = true)
    private TextView my_stting;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("个人中心", true, false);
        setCenterContent(R.layout.activity_personcenter);
    }

    @Override
    protected void init()
    {
        setCommonTopBack();
    }

    @Override
    public void onViewClick(View v)
    {
        IMessage message = getModule(IMessage.class);
        switch (v.getId())
        {
            case R.id.tv_my_setting:

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.tv_myUpload_btn:
                message.loadMyUploadMessage(MessageId.MESSAGE_ID_MY_UPLOAD, "" + 0, this);
                break;

            default:
                sendAboutMyMessage(v.getId(), message);
                break;
        }
    }

    private void sendAboutMyMessage(int id, IMessage message)
    {
        int messageId = MessageId.MESSAGE_ID_MY_READ;
        switch (id)
        {
            case R.id.tv_collection_btn:// 我的收藏shoucang
                messageId++;
            case R.id.tv_hotreg_btn:// 热门推荐push
                messageId++;
            case R.id.tv_myread_btn:// 我的阅读record
                LogInfomation.d(TAG, "messageId:" + messageId);
                message.loadMyReadMessage(messageId, "" + 0, this);
                break;
        }
    }

    public static final String ABOUT_MY_MESSAGE_KEY = "myMessageKey";
    public static final String ABOUT_MY_TITLE_KEY = "title";

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {

        switch (messageId)
        {
            case MessageId.MESSAGE_ID_MY_READ:
            case MessageId.MESSAGE_ID_HOTREG:
            case MessageId.MESSAGE_ID_COLLECTION:
            case MessageId.MESSAGE_ID_MY_UPLOAD:

                LogInfomation.d(TAG, "PersoncenterActivity:list size:" + ((ArrayList) bean));
                /**
                 * 我的阅读--A 热门推荐--A 我的上传-- 我的收藏--A
                 */
                startActivity(MyReadActivity.class, new SirPair<String, ArrayList>(ABOUT_MY_MESSAGE_KEY, (ArrayList) bean), new SirPair<String, Integer>(ABOUT_MY_TITLE_KEY, messageId));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == SettingActivity.LOGOUT_RESOULT)
        {
            finish();
        }
    }
}
