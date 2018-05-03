package com.hebeitv.news.view.login;

import java.util.ArrayList;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.LoginBean;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.UserManager;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.utils.WindowUtil;
import com.hebeitv.news.view.PersonCenterActivity;

public class LoginActivity extends FrameActivity
{

    @ResetControl(id = R.id.ed_login_registbtn, clickThis = true)
    private TextView tv_to_regist;
    @ResetControl(id = R.id.ed_login_loginbtn, clickThis = true)
    private TextView login;
    @ResetControl(id = R.id.ed_login_username)
    private EditText userName;
    @ResetControl(id = R.id.ed_login_password)
    private EditText password;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("用户登录", true, false);
        setCenterContent(R.layout.activity_login);
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
            case R.id.ed_login_registbtn:
                startActivity(RegistActivity.class);
                break;

            case R.id.ed_login_loginbtn:

                String m_user = userName.getText().toString();
                String m_pass = password.getText().toString();

                if (UserManager.securityLoginMsg(this, m_user, m_pass))
                {
                    v.setClickable(false);
                    IMessage message = getModule(IMessage.class);
                    message.sendLoginMessage(MessageId.MESSAGE_ID_LOGIN, m_user, m_pass, this);
                }
                else
                {

                    v.setClickable(true);
                }

                break;
            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        switch (messageId)
        {
            case MessageId.MESSAGE_ID_LOGIN:

                LoginBean lb = ((ArrayList<LoginBean>) bean).get(0);
                lb.success = true;
                ILogin loginModule = getModule(ILogin.class);
                loginModule.setLoginInfo(lb);
                startActivity(PersonCenterActivity.class);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        login.setClickable(true);
        WindowUtil.showToast(this, e.getMessage());
        ILogin loginModule = getModule(ILogin.class);
        loginModule.logout();
    }
}
