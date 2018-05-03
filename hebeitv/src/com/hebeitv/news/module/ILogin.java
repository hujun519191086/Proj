package com.hebeitv.news.module;

import com.hebeitv.news.bean.LoginBean;
import com.hebeitv.news.bean.LoginInfo;

public interface ILogin
{
    public void logout();

    public boolean isAlive();

    public void setLoginInfo(LoginBean lb);

    public void loadLoginStatus();

    public LoginInfo getLoginInfo();
}
