package com.qc188.com.framwork;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qc188.com.R;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.ConstantValues.STATUS;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.SystemNotification;

public abstract class BaseActivity extends Activity
{

    private static final String TAG = "BaseActivity";
    private HashMap<String, WeakReference<Drawable>> weakHashmap;

    public void setTitleContent(int resid)
    {
        hideBackButton();
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) tvTitle.setText(resid);
    }

    /**
     * 开启一个简单封装好了的activity
     * 
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public void startActivity(Class<? extends BaseActivity> clazz)
    {
        startActivity(clazz, new Pair<String, Serializable>(null, null));
    }

	protected void out(int result) {
		if (result != -1) {
            SystemNotification.showToast(getApplicationContext(), STATUS.STATUS_STR_VALUES[result] + "  3秒后退出此界面。。。");
		} else {
            SystemNotification.showToast(getApplicationContext(), "数据异常,3秒后退出此界面。。。");
		}

		postDelay(new Runnable() {

			@Override
			public void run() {
				finish();
			}
		}, 3000);
	}

    /**
     * 开启一个简单封装好了的activity
     * 
     * @param clazz
     */
    public void startActivity(Class<? extends BaseActivity> clazz, Pair<String, ? extends Serializable>... pairs)
    {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), clazz);
        intent.putExtra(ConstantValues.FROM_FLAG, this.getClass().getSimpleName());

        if (pairs != null)
        {
            for (int i = 0; i < pairs.length; i++)
            {
                if (pairs[i].first != null && pairs[i].second != null) intent.putExtra(pairs[i].first, pairs[i].second);
            }
        }
        ImageLoader.getInstance().stop();
        startActivity(intent);
    }

    public void setTitleContent(String text)
    {
        hideBackButton();
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) tvTitle.setText(text);
    }

    public Drawable putDrawable(String key, Drawable drawable)
    {
        if (drawable == null)
        {
            return null;
        }
        if (weakHashmap == null)
        {
            weakHashmap = new HashMap<String, WeakReference<Drawable>>();
        }
        WeakReference<Drawable> weakDrawable = weakHashmap.get(key);
        if (weakDrawable == null)
        {
            weakDrawable = new WeakReference<Drawable>(drawable);
            weakHashmap.put(key, weakDrawable);

        }
        return weakDrawable.get();
    }

    public Drawable getWeakMapDrawable(String key)
    {
        if (weakHashmap == null)
        {
            weakHashmap = new HashMap<String, WeakReference<Drawable>>();
        }
        WeakReference<Drawable> weakDrawable = weakHashmap.get(key);
        if (weakDrawable == null)
        {
            return null;
        }
        return weakDrawable.get();
    }

    public String getMapKey(String position, String url)
    {
        return position + "," + url;
    }

    public void visibleBackButton()
    {
        findViewById(R.id.btnBack).setVisibility(View.VISIBLE);
        findViewById(R.id.btnBack).setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                onBackPressed();

            }
        });
    }

    protected boolean activityIsClose = false;

    private Handler handler;

    public void postDelay(Runnable r, long delayMillis)
    {
        getHanlder();
        handler.postDelayed(r, delayMillis);
    }

    public Handler getHanlder()
    {
        if (handler == null)
        {
            handler = new Handler();
        }
        return handler;
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
        View view = super.findViewById(id);
        return (T) view;
    }

    public void postDelay(Runnable r)
    {
        getHanlder();
        handler.post(r);
    }

    public void notifyData()
    {

    }

    @Override
    protected void onDestroy()
    {
        activityIsClose = true;
        LogUtil.d(TAG, "destroy view!!!" + getClass().getSimpleName());
        if (weakHashmap != null)
        {
            Set<String> keySet = weakHashmap.keySet();
            for (String set : keySet)
            {
                if (weakHashmap.get(set) != null)
                {
                    Drawable d = (BitmapDrawable) weakHashmap.get(set).get();
                    if (d != null)
                    {
                        Bitmap bd = ((BitmapDrawable) d).getBitmap();
                        if (!bd.isRecycled())
                        {
                            bd.recycle();
                        }
                    }
                }
            }
            weakHashmap.clear();
            weakHashmap = null;
            System.gc();
        }
        super.onDestroy();
    }

    public void hideBackButton()
    {
        findViewById(R.id.btnBack).setVisibility(View.GONE);
    }

}
