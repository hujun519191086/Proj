package com.MrYang.zhuoyu.utils;

import java.util.ArrayList;
import java.util.HashSet;

import android.content.Context;

import com.MrYang.zhuoyu.handle.InstanceFactory;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 元初始化工具。在任何时候都可以启动初始化，但是初始化代码只会执行一次，被设计为单例 single
 * 
 * @author mryang
 * 
 */
public class InitUtil
{
    public static final InitUtil iUtils = new InitUtil();

    /**
     * 可用作全局错误码. 值为-1
     */
    public static final int ERROR_VALUES = -1;

    /**
     * 在BaseActivity中循环检测初始化是否成功的时间,可调整
     */
    public static int CHECK_COMPLEMENT_TIME = 200;

    /**
     * 是否是发布本框架的版本
     */
    public static final boolean FRAME_FINAL = false;

    /**
     * 是否是应用发布版本
     */
    public static boolean FINAL = false;

    /**
     * 获取是初始化对象
     * 
     * @return
     */
    public static InitUtil getInitUtil()
    {
        return iUtils;
    }

    public Context context;

    /**
     * 做全局初始化！！里面涉及到异步加载.不会阻碍主线程,但是建议在做操作之前最好加一个是否加载完成判断.<br/>
     * {@link InitUtil#initComplete() }
     * 
     * <br/>
     * <br/>
     * <br/>
     * 里面涉及到的打印之类的需要设置修改InitUtil.FINAL = true;
     * 
     * @param context
     */
    public InitUtil init(Context context)
    {
        if (this.context != null)
        {
            return this;
        }

        // 保存Context
        this.context = context;

        // 初始化ImageLoader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        // 初始化DensityUtil工具
        DensityUtil.initAllValue(context);
        // 初始化实例化工厂
        InstanceFactory.init(context);
        return this;
    }
    
    private HashSet<Class<? extends BaseActivity>> initHistory;

    /**
     * 初始化ui的list,同一个界面只会被执行一次!!!
     * 
     * @param initList
     * @param activityClass
     */
    public void initUIList(ArrayList<Runnable> initList, Class<? extends BaseActivity> activityClass)
    {
        int listSize = initList.size();

        if (listSize == 0)// 如果size是0的话,就不放进去.
        {
            return;
        }
        if (initHistory == null)
        {
            initHistory = new HashSet<Class<? extends BaseActivity>>();
        }
        if (initHistory.contains(activityClass))
        {
          return;
        }
        initHistory.add(activityClass);

        for (int i = 0; i < listSize; i++)
        {
            Runnable runnable = initList.get(i);
            if (runnable != null)
            {
                runnable.run();
            }
        }
    }

    /**
     * 框架型注解,标注框架内有注解的信息的TAG
     */
    public static final String MUSTFINISH_ANNO_TAG = "MustFinish";

    /**
     * 应用程序该调用的方法.
     */
    public void onApplicationExit()
    {
        BaseActivity.clearHangder();
        context = null;
    }

    /**
     * 是否初始化成功?
     * 
     * @return false是还没有成功
     */
    public boolean initComplete()
    {
        return InstanceFactory.InitComplete;
    }

}
