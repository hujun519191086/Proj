package com.hebeitv.news.view.main;

import java.util.ArrayList;

import org.apache.http.Header;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.ImageLoadUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.android.volley.toolbox.NetworkImageView;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.MVPBean;
import com.hebeitv.news.bean.NewsInfoBean;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.media.MediaActivity;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.utils.FakeUtil;
import com.hebeitv.news.view.PersonCenterActivity;
import com.hebeitv.news.view.adapter.MainCenterAdapter;
import com.hebeitv.news.view.login.LoginActivity;

/**
 * 中心栏目
 * 
 * @author jieranyishen
 * 
 */
public class CenterActivity extends FrameActivity implements OnPageChangeListener
{

    @ResetControl(id = R.id.ll_scrollBar)
    private LinearLayout titleScrollBar;

    @ResetControl(id = R.id.vp_center_content)
    private ViewPager centenePager;

    private Integer[] scrollBarNormalList = new Integer[] { R.drawable.jingxuan, R.drawable.lianbo, R.drawable.jinwanshijie, R.drawable.kanjinzhao, R.drawable.taiqianmuhou };;
    private Integer[] scrollBarPressList = new Integer[] { R.drawable.jingxuan_dianjihou, R.drawable.lianbo_dianjihou, R.drawable.jinwanshijie_dianjihou, R.drawable.kanjinzhao_dianjihou, R.drawable.taiqianmuhou_dianjihou };;
    private View[] titleScrollBarList;

    private Integer[] viewPagerItemViews = new Integer[] { R.layout.listview, R.layout.listview, R.layout.listview, R.layout.listview, R.layout.center_pager_item_5, R.layout.listview };

    private int selectItemPosition = -1;

    private SparseBooleanArray isReload = new SparseBooleanArray();

    private MainCenterAdapter mcAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("河北电视台新闻中心", true, true);
        includeBottom(0, CenterActivity.class, NativeShowingActivity.class, PaiKeCenterActivity.class);
        setCenterContent(R.layout.main_center);

    }

    @Override
    protected void init()
    {

        getModule(ILogin.class).loadLoginStatus();
        View personButton = setCommonPersonClick();
        setCommonTopSearch();
        initScrollBar();
        selectBar();
        mcAdapter = new MainCenterAdapter(this, viewPagerItemViews, titleScrollBarList.length, centenePager);
        centenePager.setAdapter(mcAdapter);
        centenePager.setOnPageChangeListener(this);
        pageStatus.append(MessageId.MESSAGE_ID_JING_XUAN, "-1");
        pageStatus.append(MessageId.MESSAGE_ID_JING_XUAN + 1, "-1");
        pageStatus.append(MessageId.MESSAGE_ID_JING_XUAN + 2, "-1");
        pageStatus.append(MessageId.MESSAGE_ID_JING_XUAN + 3, "-1");

        // 加载第一页数据
        reloadMessage(0);

        /**
         * 个人点击
         */
        personButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ILogin lm = getModule(ILogin.class);
                if (lm.isAlive())// 是否已经登陆
                {
                    startActivity(FakeUtil.getValue(PersonCenterActivity.class, MediaActivity.class));
                }
                else
                {
                    // TIPS 应该跳到登录界面
                    startActivity(LoginActivity.class);
                    // startActivity(LoginActivity.class);

                }
            }
        });
    }

    public void reloadMessage(int itemPageMessagePosition)
    {
        int messageId = itemPageMessagePosition + MessageId.MESSAGE_ID_JING_XUAN;
        isReload.append(messageId, true);
        LogInfomation.d(TAG, "reloadMessage:" + messageId + "  nowPage:" + pageStatus.get(messageId));
        getModule(IMessage.class).loadNewsListMessage(messageId, 0, this);
    }

    private ScrollView empView;

    public void LoadEmp(ScrollView empView)
    {

        this.empView = empView;
        LogInfomation.d(TAG, "LoadEmp");
        getModule(IMessage.class).showEMP(MessageId.MESSAGE_ID_LOAD_EMP, this);

    }

    public void loadMoreMessage(int itemPageMessagePosition)
    {
        int messageId = itemPageMessagePosition + MessageId.MESSAGE_ID_JING_XUAN;
        String nowPage = pageStatus.get(messageId);
        if (TextUtils.isEmpty(nowPage))
        {
            nowPage = "-1";
        }
        LogInfomation.d(TAG, "loadMoreMessage:" + messageId + "  nowPage:" + nowPage);

        isReload.append(messageId, false);
        getModule(IMessage.class).loadNewsListMessage(messageId, Integer.valueOf(nowPage) + 1, this);
    }

    private void initScrollBar()
    {
        titleScrollBarList = new View[scrollBarPressList.length];
        float scale = (float) 92 / (float) 56;

        int width = DensityUtil.dip2px(71);
        int height = (int) ((float) width / scale);

        HorizontalScrollView.LayoutParams p = new HorizontalScrollView.LayoutParams(-2, height);
        titleScrollBar.setLayoutParams(p);

        LogInfomation.d(TAG, "width:" + width + "  height:" + height);
        for (int i = 0; i < scrollBarNormalList.length; i++)
        {
            View view = new View(getApplicationContext());
            HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(width, -1);
            view.setLayoutParams(params);
            view.setTag(i);
            titleScrollBarList[i] = view;
            view.setOnClickListener(this);
            titleScrollBar.addView(view);

            view = new View(getApplicationContext());
            params = new HorizontalScrollView.LayoutParams(2, -1);
            view.setLayoutParams(params);
            view.setBackgroundColor(Color.rgb(0xD3, 0x5c, 0x32));
            titleScrollBar.addView(view);

        }

        selectItemPosition = 0;
    }

    /**
     * 选择
     */
    @SuppressWarnings("deprecation")
    private void selectBar()
    {
        for (int i = 0; i < titleScrollBarList.length; i++)
        {
            if (selectItemPosition == i)
            {
                centenePager.setCurrentItem(i);
                titleScrollBarList[i].setBackgroundDrawable(getDrawable(titleScrollBarList[i], scrollBarPressList[i]));
            }
            else
            {
                titleScrollBarList[i].setBackgroundDrawable(getDrawable(titleScrollBarList[i], scrollBarNormalList[i]));

            }
        }
    }

    @Override
    public void onViewClick(View v)
    {
        int id = v.getId();

        if (id == -1)// 沒有id
        {
            Object tag = v.getTag();

            if (tag != null)
            {
                if (tag instanceof Integer)// 並且tag是数字
                {
                    onPageSelected((Integer) tag);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0)
    {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {

    }

    private SparseArray<String> pageStatus = new SparseArray<String>();

    @Override
    public void onPageSelected(int arg0)
    {
        if (arg0 != selectItemPosition)
        {
            selectItemPosition = arg0;

            int messageId = MessageId.MESSAGE_ID_JING_XUAN + arg0;
            if (mcAdapter.needLoadMessage(messageId))
            {
                reloadMessage(arg0);
            }
        }
        else
        {
            LogInfomation.e(TAG, "selectItem:" + arg0);
        }
        selectBar();

    }

    public static final String centerDetailSerializDataKey = "centerDetailUrl";

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {

        switch (messageId)
        {
            case MessageId.MESSAGE_ID_LOAD_EMP:
                super.onFault(messageId, errorCode, headers, reslutContent, e);
                break;

            default:
                mcAdapter.goneRefresh(messageId - MessageId.MESSAGE_ID_JING_XUAN);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {

        if (messageId == MessageId.MESSAGE_ID_LOAD_EMP)
        {

        }
        else
        {
            if (isReload.get(messageId))
            {
                pageStatus.append(messageId, "0");
            }
            else
            {
                String page = pageStatus.get(messageId);
                pageStatus.append(messageId, (Integer.valueOf(page) + 1) + "");
            }

        }

        switch (messageId)
        {
            case MessageId.MESSAGE_ID_JING_XUAN:
            case MessageId.MESSAGE_ID_LIAN_BO:
            case MessageId.MESSAGE_ID_WAN_JIAN:
            case MessageId.MESSAGE_ID_JIN_ZHAO:
                mcAdapter.addData(messageId - MessageId.MESSAGE_ID_JING_XUAN, (ArrayList<NewsInfoBean>) bean, pageStatus.get(messageId).equals("0"));
                break;

            case MessageId.MESSAGE_ID_LOAD_EMP:

                int[] groupPosition = { 0, 2, 4 };
                ViewGroup vg = (ViewGroup) empView.findViewById(R.id.center_pager_goodJob);
                ViewGroup workSpace = (ViewGroup) empView.findViewById(R.id.center_pager_workSpace);

                ArrayList<MVPBean> mvpBeanlist = (ArrayList<MVPBean>) bean;
                for (int i = 0; i < groupPosition.length && i < mvpBeanlist.size(); i++)
                {
                    int position = groupPosition[i];
                    View childView = vg.getChildAt(position);// 员工
                    NetworkImageView icon = (NetworkImageView) childView.findViewById(R.id.item_goodJob_pic);
                    TextView name = (TextView) childView.findViewById(R.id.item_goodJob_name);

                    MVPBean mvpBean = mvpBeanlist.get(i);
                    ImageLoadUtil.loadImage(getApplicationContext(), icon, mvpBean.getsPic());
                    name.setText(mvpBean.getsName());

                    childView = workSpace.getChildAt(position);// 工作场景
                    icon = (NetworkImageView) childView.findViewById(R.id.item_work_space_img);
                    ImageLoadUtil.loadImage(getApplicationContext(), icon, mvpBean.getWorkScene());

                    if (!TextUtils.isEmpty(mvpBean.getWorkSplace()))
                    {

                        int aroundid = getResources().getIdentifier("iv_work_around" + (i + 1), "id", getPackageName());

                        icon = (NetworkImageView) empView.findViewById(aroundid);
                        icon.setVisibility(View.VISIBLE);
                        ImageLoadUtil.loadImage(getApplicationContext(), icon, mvpBean.getWorkSplace());
                    }
                }

                break;
            default:
                break;
        }
    }
}
