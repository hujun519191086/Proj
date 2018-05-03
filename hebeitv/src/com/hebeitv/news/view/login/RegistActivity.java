package com.hebeitv.news.view.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.UserManager;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;

/**
 * 注册
 * 
 * @author jieranyishen
 * 
 */
public class RegistActivity extends FrameActivity
{
    @ResetControl(id = R.id.ed_regist_username)
    private EditText userName;
    @ResetControl(id = R.id.ed_regist_phone)
    private EditText phone;
    @ResetControl(id = R.id.ed_regist_password)
    private EditText password;
    @ResetControl(id = R.id.ed_regist_repassword)
    private EditText rePassword;

    @ResetControl(id = R.id.regist_userName_image)
    private ImageView userName_image;
    @ResetControl(id = R.id.regist_phone_image)
    private ImageView phone_image;
    @ResetControl(id = R.id.regist_password_image)
    private ImageView password_image;
    @ResetControl(id = R.id.regist_repassword_image)
    private ImageView rePassword_image;
    @ResetControl(id = R.id.tv_regist_registbtn, clickThis = true)
    private TextView registBtn;

    @ResetControl(id = R.id.iv_regist_title, drawable = R.drawable.shangmiantu)
    private ImageView titleImage;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("用户注册", true, false);
        setCenterContent(R.layout.activity_regist);
    }

    @Override
    protected void init()
    {
        setCommonTopBack();
    }

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_regist_registbtn:// 注册
                String m_userName = userName.getText().toString();
                String m_phone = phone.getText().toString();
                String m_password = password.getText().toString();
                String m_rePassword = rePassword.getText().toString();

                if (UserManager.securityRegistMsg(this, m_userName, m_password, m_phone, m_rePassword))
                {
                    IMessage message = getModule(IMessage.class);
                    message.sendRegistMessage(MessageId.MESSAGE_ID_REGIST, m_userName, m_phone, m_password, this);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(int messageId, String resultContent)
    {
        super.onSuccess(messageId, resultContent);
        finish();
    }

}
