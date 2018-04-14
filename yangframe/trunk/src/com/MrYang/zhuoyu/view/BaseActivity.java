package com.MrYang.zhuoyu.view;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.MrYang.zhuoyu.ProjectSetting;
import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.enu.MustFinishAnnoEnum;
import com.MrYang.zhuoyu.handle.VariableFactory;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.CommboUtil;
import com.MrYang.zhuoyu.utils.InitUtil;

/**
 * 调用时，希望使用一个类似FragmeActivity来继承此类。以方便和统一定制自己每个想要的activity类型
 * 
 * @author mryang
 * 
 */

@MustFinish(mustFinishEnum = MustFinishAnnoEnum.NOT_DO, values = { "增加viewHolder.  增加和holder和注解的搭配使用." })
public class BaseActivity extends Activity implements OnClickListener
{
    protected final String SIMPLE_TAG = getClass().getSimpleName();
    protected final String TAG = getClass().getName();
    
    /**
     * 如果想要调用此方法，请使用{@link #onCreateView(Bundle)} 方法！
     */
    @Override
    final protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ArrayList<Runnable> initList = new ArrayList<Runnable>();
        putInitMethodToInitUtil(initList);
        InitUtil.getInitUtil().init(getApplicationContext()).initUIList(initList, getClass());
        onCreateView(savedInstanceState);
        init();
    }

    /**
     * 将要执行init的代码放进这里面,只会被执行一次.
     * 
     * @param initList
     */
    protected void putInitMethodToInitUtil(ArrayList<Runnable> initList)
    {

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    private ViewHolder controlHolder;

    /**
     * 如果要找view，可以使用<br/>
     * {@link #findById(int)}
     */
    @Override
    public View findViewById(int id)
    {
        return super.findViewById(id);
    }

    /**
     * 建议在使用此方法的时候，不要将view存成成员变量
     * 
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findById(int id)
    {
        View view = controlHolder.getView(id);
        if (view == null)
        {
            view = super.findViewById(id);
            controlHolder.setView(id, view);
        }
        return (T) view;
    }

    // @Deprecated 由于技术问题，不确定这样设置ContentView必然为rootView，只是经过部分测试。如果出现find不到id的问题。或者其他的， 可以考虑使用{@link com.MrYang.zhuoyu.BaseActivity#setContentView(View view) }
    public void setView(int layoutResID)
    {
        View rootView = View.inflate(getApplicationContext(), layoutResID, null);
        setView(rootView);
        // View rootView = ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
    }

    public void setView(View view)
    {
        setView(view, null);
    }

    /**
     * 对注解做初始化.
     * 
     * @param view
     */
    private void initInSetContent(View view)
    {
        VariableFactory.autoInjectAllField(this, view);
    }

    public void setView(View view, LayoutParams params)
    {
        if (params == null)
        {
            super.setContentView(view);
        }
        else
        {
            super.setContentView(view, params);
        }
        setContentViewOverImmediately();
        initInSetContent(view);
        controlHolder = new ViewHolder(view);
    }

    /**
     * 在{@link BaseActivity#setContentView(View)}设置内容之后,{@link #initInSetContent(View)}之前调用.让允许添加动态view.
     */
    public void setContentViewOverImmediately()
    {

    }

    /**
     * 在工具做完初始化操作 之后马上就被调用的方法。调用顺序在<br/>
     * {@link #init() }之前
     * 
     * 
     * @param savedInstanceState
     */
    protected void onCreateView(Bundle savedInstanceState)
    {

    }

    /**
     * 在 {@link #onCreateView(Bundle) } 之后马上调用。
     */
    protected void init()
    {

    }

    /**
     * 开启一个简单封装好了的activity
     * 
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    final public void startActivity(Class<? extends BaseActivity> clazz)
    {
        startActivity(clazz, new SirPair<String, Serializable>(null, null));
    }

    private int[] intentFlags;

    /**
     * 设置intent跳转时所需要做的标记。 一般都是类似 single之类的标记
     * 
     * @param flags
     */
    public void setIntentFlags(int... flags)
    {
        intentFlags = flags;
    }

    /**
     * 给intent跳转时，标记上一个跳转界面的键。
     */
    public static String FROM_FLAG = Intent.ACTION_ATTACH_DATA;

    /**
     * 在做startactivity跳转的时候，会默认添加一个Extra，代表着新的界面是来自哪里 这个flag就是要传递的键， 默认是Intent.ACTION_ATTACH_DATA
     * 
     * @param flag
     *            在做界面跳转时，代表上一个界面的extra 的键
     */
    final public void setIntentFromFlag(String flag)
    {
        FROM_FLAG = flag;
    }

    /**
     * 更强大的startactivity跳转
     * 
     * @param clazz
     *            需要跳转的activity的class
     * @param pairs
     *            跳转时，需要传递的参数。如果不需要传递参数。请不要使用此方法！！
     */
    final public void startActivity(Class<? extends BaseActivity> clazz, SirPair<String, ? extends Serializable>... pairs)
    {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), clazz);
        intent.putExtra(FROM_FLAG, this.getClass().getName());
        intent.setFlags(ProjectSetting.ACTIVITY_JUMP_INTENT);
        if (pairs != null)
        {
            for (int i = 0; i < pairs.length; i++)
            {
                if (pairs[i].first != null && pairs[i].second != null)
                {
                    intent.putExtra(pairs[i].first, pairs[i].second);
                }
            }
        }

        if (intentFlags != null)
        {
            for (int i = 0; i < intentFlags.length; i++)
            {
                intent.setFlags(intentFlags[i]);
            }
        }

        startActivity(intent);
    }

    /**
     * 如果要调用这个方法，请使用<br/>
     * {@link #onViewPreDestroy()  }
     */
    @MustFinish(values = { "图片优化缓存处理" })
    @Override
    final protected void onDestroy()
    {
        onViewPreDestroy();
        super.onDestroy();
        onViewInDestroy();

        if (controlHolder != null)
        {
            controlHolder.clear();
            controlHolder = null;
        }

        ImageManager.getManger().destoryDrawable(this.getClass());
        // if (null == destoryViewList)
        // {
        // return;
        // }
        // for (int i = 0; i < destoryViewList.size(); i++)
        // {
        // View tempView = destoryViewList.get(i);
        // tempView.clearAnimation();
        // tempView.destroyDrawingCache();
        // Drawable drawable = tempView.getBackground();
        // tempView.setBackgroundDrawable(null);
        // BitmapUtil.recycleDrawable(drawable);
        // }
    }

    /**
     * 在onDestroy中调用的方法，需要的时候复写。<br/>
     * 注意,这个方法调用顺序在super.onDestroy()之前,如果需要调用super.onDestroy()之后的方法.请使用<br/>
     * {@link #onViewInDestroy()}方法
     */
    protected void onViewPreDestroy()
    {
    }

    /**
     * 这个方法调用顺序在super.onDestroy()之后.如果无特殊调用顺序要求,可以使用<br/>
     * {@link #onViewPreDestroy()}方法
     */
    protected void onViewInDestroy()
    {
    }

    /**
     * 点击回调,不支持使用此方法。因为一般屏幕支持多点触控，多个button可同时相应多个点击操作。在此方法内做了这种bug的拦截。<br/>
     * 如果要使用onClick请使用<br/>
     * {@link #onViewClick(View v)}
     * 
     * 
     */
    @Override
    public void onClick(View v)
    {
        if (CommboUtil.isFastDoubleClick())
        {
            return;
        }
        onViewClick(v);
    }

    /**
     * 推荐使用的onClick方法
     * 
     * @param v
     */
    public void onViewClick(View v)
    {
    }

    /**
     * 做一些线程同步操作
     */
    private static Handler handler;

    /**
     * 放入runnable，等待delayMillis毫秒后执行
     * 
     * @param r
     *            要执行的代码块
     * @param delayMillis
     *            再多少毫秒后执行
     */
    public void postDelay(Runnable r, long delayMillis)
    {
        getHanlder().postDelayed(r, delayMillis);
    }

    /**
     * 获取handler和初始化handler,非常不推荐使用handleMessage，如果要将代码推送到主线程，请使用 <br/>
     * {@link #postDelay(Runnable r)}<br/>
     * 或者<br/>
     * {@link #postDelay(Runnable r, long delayMillis)}
     * 
     * @return 返回线程同步handler
     * 
     */
    public static Handler getHanlder()
    {
        if (handler == null)
        {
            handler = new Handler();
        }
        return handler;
    }

    /**
     * 退出应用的时候该调用的代码
     */
    public static void clearHangder()
    {
        if (handler != null)
        {
            handler = null;
        }
    }

    /**
     * 马上再主线程中使用此代码快
     * 
     * @param r
     *            需要执行的代码块
     */
    public void postDelay(Runnable r)
    {
        getHanlder().post(r);
    }

}
