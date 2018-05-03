package com.hebeitv.news.module.imp;

import com.hebeitv.news.bean.LoginBean;
import com.hebeitv.news.bean.LoginInfo;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.IModule;
import com.hebeitv.news.utils.SharedPerferenceUtil;

public class LoginModule extends IModule implements ILogin
{

    private LoginInfo loginInfo = new LoginInfo();

    /**
     * 登出要调用的方法
     */
    public void logout()
    {
        loginInfo = new LoginInfo();
        SharedPerferenceUtil.refreshLoginStatus(loginInfo);
    }

    /**
     * 是否已经登陆????
     * 
     * @return
     */
    public boolean isAlive()
    {
        return loginInfo.inLogin;
    }

    public void loadLoginStatus()
    {
        loginInfo = SharedPerferenceUtil.ReadLoginStatus();
    }

    @Override
    public void setLoginInfo(LoginBean lb)
    {
        loginInfo.account_id = lb.rId;
        loginInfo.account_pass = lb.rPass;
        loginInfo.accountNumber = lb.rName;
        loginInfo.inLogin = lb.success;
        SharedPerferenceUtil.refreshLoginStatus(loginInfo);
    }

    public LoginInfo getLoginInfo()
    {
        return loginInfo;
    }
}
