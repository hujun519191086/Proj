package com.MrYang.zhuoyu.view.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelLayout extends RelativeLayout
{

    public LevelLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public LevelLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LevelLayout(Context context)
    {
        super(context);
    }

    /**
     * level从0开始
     * 
     * @param level
     */
    public void setView(int level)
    {
        RelativeLayout rl = this;
        if (level > 0)
        {
            for (int i = 0; i < level; i++)
            {
                RelativeLayout childRl = new RelativeLayout(getContext());
                rl.addView(childRl);
                rl = childRl;
            }
        }
        for (int i = 0; i < 50; i++)
        {
            TextView tv = new TextView(getContext());
            tv.setId(getContext().getResources().getIdentifier("tv_levle1_tv" + i, "id", getContext().getPackageName()));
            rl.addView(tv);
        }
    }
}
