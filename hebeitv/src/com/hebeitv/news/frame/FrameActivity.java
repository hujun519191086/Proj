package com.hebeitv.news.frame;

import org.apache.http.Header;

import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.hebeitv.news.R;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.net.OnPostCallBack;
import com.hebeitv.news.utils.WindowUtil;
import com.hebeitv.news.view.search.SearchActivity;

public class FrameActivity extends BaseActivity implements OnPostCallBack
{

    private TopAndBottomConfig TBConfig = new TopAndBottomConfig();

    @Override
    public void onAttachedToWindow()
    {
        ModuleManager.getManager().addCallBack(this);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow()
    {
        ModuleManager.getManager().removeCallBack(this);
        super.onDetachedFromWindow();
    }

    protected void includeBottom(int position, Class<? extends FrameActivity>... jumpList)
    {
        TBConfig.includeBottom = true;
        TBConfig.m_bottomPosition = position;
        TBConfig.clickJumpClass = jumpList;
    }

    @Override
    protected void onStart()
    {
        getTVApplication().setWorkingActivity(this);
        super.onStart();
    }

    public TVApplication getTVApplication()
    {
        return (TVApplication) getApplication();
    }

    /**
     * 顶部初始化
     * 
     * includeTop("", true, false); setCenterContent(R.layout.upload); 必须以这种形式调用
     * 
     * @param topStr
     * @param hasBack
     * @param hasRight
     * @return
     */
    protected Pair<Integer, Integer> includeTop(String topStr)
    {
        return includeTop(topStr, false, false);
    }

    /**
     * includeTop("", true, false); setCenterContent(R.layout.upload); 必须以这种形式调用
     * 
     * @param topStr
     * @param hasBack
     * @param hasRight
     * @return
     */
    protected Pair<Integer, Integer> includeTop(String topStr, boolean hasBack, boolean hasRight)
    {
        return includeTop(topStr, hasBack, hasRight, Gravity.CENTER);
    }

    /**
     * includeTop("", true, false); setCenterContent(R.layout.upload); 必须以这种形式调用
     * 
     * @param topStr
     * @param hasBack
     * @param hasRight
     * @param gravity
     * @return
     */
    protected Pair<Integer, Integer> includeTop(String topStr, boolean hasBack, boolean hasRight, int gravity)
    {
        TBConfig.includeTop = true;
        TBConfig.m_topStr = topStr;
        TBConfig.m_gravity = gravity;
        TBConfig.m_hasBack = hasBack;
        TBConfig.m_hasRight = hasRight;
        return new Pair<Integer, Integer>(R.id.iv_title_leftbtn, R.id.iv_title_rightbtn);
    }

    protected void setCommonTopBack()
    {
        ImageView iv_title_leftbtn = findById(R.id.iv_title_leftbtn);

        if (iv_title_leftbtn != null)
        {
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(23), -1);
            iv_title_leftbtn.setLayoutParams(llParams);
            iv_title_leftbtn.setScaleType(ScaleType.FIT_CENTER);
            iv_title_leftbtn.setImageDrawable(getDrawable(iv_title_leftbtn, R.drawable.fanhui));
            iv_title_leftbtn.setPadding(0, 0, DensityUtil.dip2px(12), 0);
            iv_title_leftbtn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    onBackPressed();
                }
            });
            iv_title_leftbtn.setVisibility(TBConfig.m_hasBack ? View.VISIBLE : View.GONE);
        }
    }

    protected View setCommonPersonClick()
    {
        ImageView iv_title_leftbtn = findById(R.id.iv_title_leftbtn);

        if (iv_title_leftbtn != null)
        {
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(31), -1);
            iv_title_leftbtn.setLayoutParams(llParams);
            iv_title_leftbtn.setScaleType(ScaleType.FIT_CENTER);
            iv_title_leftbtn.setImageDrawable(getDrawable(iv_title_leftbtn, R.drawable.geren));
            iv_title_leftbtn.setPadding(0, DensityUtil.dip2px(16), 0, DensityUtil.dip2px(11));
            iv_title_leftbtn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                }
            });
            iv_title_leftbtn.setVisibility(TBConfig.m_hasBack ? View.VISIBLE : View.GONE);
        }
        return iv_title_leftbtn;
    }

    protected void setCommonTopSearch()
    {
        ImageView iv_title_rightbtn = findById(R.id.iv_title_rightbtn);

        if (iv_title_rightbtn != null)
        {
            FrameLayout.LayoutParams llParams = new FrameLayout.LayoutParams(DensityUtil.dip2px(32), -1);
            iv_title_rightbtn.setLayoutParams(llParams);
            iv_title_rightbtn.setScaleType(ScaleType.FIT_CENTER);
            iv_title_rightbtn.setImageDrawable(getDrawable(iv_title_rightbtn, R.drawable.sousuo));
            iv_title_rightbtn.setPadding(DensityUtil.dip2px(10), 0, 0, 0);
            iv_title_rightbtn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    startActivity(SearchActivity.class);
                }
            });
            iv_title_rightbtn.setVisibility(TBConfig.m_hasRight ? View.VISIBLE : View.GONE);
        }
    }

    protected void setCommonTopPicture()
    {
        ImageView iv_title_rightbtn = findById(R.id.iv_title_rightbtn);

        if (iv_title_rightbtn != null)
        {
            FrameLayout.LayoutParams llParams = new FrameLayout.LayoutParams(DensityUtil.dip2px(31), -1);
            iv_title_rightbtn.setLayoutParams(llParams);
            iv_title_rightbtn.setScaleType(ScaleType.FIT_CENTER);
            iv_title_rightbtn.setImageDrawable(getDrawable(iv_title_rightbtn, R.drawable.xiangji));
            iv_title_rightbtn.setPadding(0, 0, 0, 0);
            iv_title_rightbtn.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    // TIPS 拍照

                    if (getModule(ILogin.class).isAlive())
                    {
                        WindowUtil.ins().alertTakephoto(FrameActivity.this);
                    }
                    else
                    {
                        WindowUtil.showToast(FrameActivity.this, "请先登录!");
                    }
                }
            });
            iv_title_rightbtn.setVisibility(TBConfig.m_hasRight ? View.VISIBLE : View.GONE);
        }
    }

    protected TextView setCommonTopRightText(String text)
    {
        TextView tv_ttile_rightText = findById(R.id.tv_ttile_rightText);

        ImageView iv_title_rightbtn = findById(R.id.iv_title_rightbtn);
        iv_title_rightbtn.setVisibility(View.GONE);
        tv_ttile_rightText.setText(text);
        return tv_ttile_rightText;
    }

    public void setCenterContent(int layoutResID)
    {
        getTVApplication().setWorkingActivity(this);
        setContent(View.inflate(getApplicationContext(), layoutResID, null), null);
    }

    private void setContent(View view, LayoutParams params)
    {
        View contentView = null;

        if (TBConfig.includeBottom || TBConfig.includeTop)// 有一个要包括.
        {
            contentView = View.inflate(getApplicationContext(), R.layout.include_top_bottom, null);
            FrameLayout fl_common_content = (FrameLayout) contentView.findViewById(R.id.fl_common_content);
            fl_common_content.addView(view);

        }
        else
        {
            contentView = view;
        }
        if (params == null)
        {

            setView(contentView);
        }
        else
        {
            setView(contentView, params);
        }

        TBConfig.initBottom(contentView);
        TBConfig.initTop(contentView);
    }

    public void setCenterContent(View view)
    {
        setContent(view, null);
    }

    public void setCenterContent(View view, LayoutParams params)
    {
        setContent(view, params);
    }

    private class TopAndBottomConfig
    {

        private int bottomSize = 3;

        private boolean includeBottom = false;
        private int m_bottomPosition = 0;
        private boolean includeTop = false;

        private String m_topStr;
        private boolean m_hasBack;
        private boolean m_hasRight;
        private int m_gravity;
        public View[] content_main = new View[bottomSize];

        public Class<? extends FrameActivity>[] clickJumpClass;

        public TopAndBottomConfig()
        {
            bottomImageNormalList = new int[] { R.drawable.zhongxinlanmu, R.drawable.bentaijiemu, R.drawable.paike };
            bottomImagePressList = new int[] { R.drawable.zhongxinlanmu_dianji, R.drawable.bentaijiemu_dianji, R.drawable.paike_dianji };
        }

        public int[] bottomImageNormalList;
        public int[] bottomImagePressList;

        public void initBottom(View contentView)
        {

            if (includeBottom)
            {
                View common_bottom = contentView.findViewById(R.id.common_bottom);
                common_bottom.setVisibility(View.VISIBLE);

                for (int i = 0; i < 3; i++)
                {
                    content_main[i] = common_bottom.findViewById(getResources().getIdentifier("content_main_" + i, "id", getPackageName()));
                }

                for (int i = 0; i < TBConfig.content_main.length; i++)
                {
                    View bottomView = TBConfig.content_main[i];
                    if (m_bottomPosition == i)
                    {
                        bottomView.setBackgroundDrawable(getDrawable(bottomView, bottomImagePressList[i]));
                    }
                    else
                    {
                        bottomView.setBackgroundDrawable(getDrawable(bottomView, bottomImageNormalList[i]));

                        bottomView.setTag(clickJumpClass[i]);
                        bottomView.setOnClickListener(new OnClickListener()
                        {

                            @SuppressWarnings("unchecked")
                            @Override
                            public void onClick(View v)
                            {
                                // setIntentFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity((Class<? extends FrameActivity>) v.getTag());
                                overridePendingTransition(0, 0);
                                finish();
                            }
                        });
                    }
                }
            }
        }

        public void initTop(View contentView)
        {
            if (includeTop)
            {
                View common_top = contentView.findViewById(R.id.common_top);
                common_top.setVisibility(View.VISIBLE);
                TextView tv = (TextView) common_top.findViewById(R.id.tv_title);
                tv.setText(m_topStr);
                tv.setGravity(m_gravity);
            }
        }
    }

    public Drawable getDrawable(View contentView, Integer... res)
    {
        return ImageManager.getManger().getDrawable(this.getClass(), contentView, res);
    }

    public <T extends Object> T getModule(Class<T> clazz)
    {
        return ModuleManager.getManager().getModule(clazz);
    }

    @Override
    public <T> void onSuccess(int messageId, T bean)
    {

    }

    @Override
    protected void onViewPreDestroy()
    {
        ActivityManager.ins().removeClazz(getClass());
    }

    @Override
    public void onSuccess(int messageId, String resultContent)
    {
        WindowUtil.showToast(((TVApplication) InitUtil.getInitUtil().context).getWorkingActivity(), resultContent);
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        WindowUtil.showToast(((TVApplication) InitUtil.getInitUtil().context).getWorkingActivity(), e.getMessage());
    }

    private Object senderMsg;

    @Override
    public void setSenderMsg(Object obj)
    {
        senderMsg = obj;

    }

    @Override
    public <T> T getSenderMsg()
    {
        return (T) senderMsg;
    }

    @Override
    public <T> void onSuccess(int messageId, T bean, Object data)
    {
        onSuccess(messageId, bean);
    }

    @Override
    public <T> void onSuccess(int messageId, String resultContent, Object data)
    {
        onSuccess(messageId, resultContent);
    }

}
