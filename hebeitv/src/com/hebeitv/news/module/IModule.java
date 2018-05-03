package com.hebeitv.news.module;

import com.hebeitv.news.net.OnPostCallBack;

public class IModule
{
    public final String TAG = getClass().getSimpleName();

    public OnPostCallBack getCallBack(Class<? extends OnPostCallBack> key)
    {
        return ModuleManager.getManager().getCallBack(key);
    }

}
