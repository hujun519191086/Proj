package com.hebeitv.news.bean;

import com.hebeitv.news.utils.FakeUtil;

public class LoginInfo
{

    public String accountNumber = "0";// 账号
    public String account_id = "0";// id
    public String account_pass = "0";// 密码

    public boolean inLogin = FakeUtil.getValue(false);// 是否处于登录状态
}
