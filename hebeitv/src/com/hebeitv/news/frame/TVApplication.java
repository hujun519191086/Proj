package com.hebeitv.news.frame;

import java.io.File;

import android.app.Application;
import android.os.Environment;

import com.MrYang.zhuoyu.utils.CommboUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.module.imp.FileModule;
import com.hebeitv.news.module.imp.LoginModule;
import com.hebeitv.news.module.imp.MessageModule;

public class TVApplication extends Application
{
    static
    {
        try
        {
            System.loadLibrary("calculate");
            System.out.println("load success!");

        }
        catch (Exception e)
        {
            System.out.println("load exception:" + e);
        }
    }

    @Override
    public void onCreate()
    {
        CommboUtil.setOutofTime(200);// 200毫秒之内只能点一次
        LogInfomation.pointToFile = true;
        LogInfomation.setLogFilePath(Environment.getExternalStorageDirectory() + File.separator + "hebeitv" + File.separator + "log");

        ModuleManager.getManager().addModule(new FileModule());
        ModuleManager.getManager().addModule(new LoginModule());
        ModuleManager.getManager().addModule(new MessageModule());

        InitUtil.FINAL = !Global_hebei.debug;
        if (Global_hebei.debug)
        {
        }
        else
        {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
        Global_hebei.videoFilePath = Environment.getExternalStorageDirectory() + File.separator + "hebeitv/video/";
        Global_hebei.logFilePath = Environment.getExternalStorageDirectory() + File.separator + "hebeitv/log/";
        LogInfomation.setLogFilePath(Global_hebei.logFilePath);

        super.onCreate();

    }

    @Override
    public void onTerminate()
    {

        super.onTerminate();
        InitUtil.getInitUtil().onApplicationExit();
    }

    private FrameActivity working_activity;

    public void setWorkingActivity(FrameActivity activity)
    {
        ActivityManager.ins().addActivity(activity);
        working_activity = activity;
    }

    public FrameActivity getWorkingActivity()
    {
        return working_activity;
    }
}
