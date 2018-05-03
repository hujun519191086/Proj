package com.hebeitv.news.view.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.NativeShowingBean;
import com.hebeitv.news.frame.FrameActivity;

/**
 * 本台节目
 * 
 * @author jieranyishen
 * 
 */
public class NativeShowingActivity extends FrameActivity
{

    @ResetControl(id = R.id.native_showingList)
    private ListView showingList;

    private List<NativeShowingBean> beanList = new ArrayList<NativeShowingBean>();
    private Integer[] srcList = { R.drawable.jinrizixun, R.drawable.laosanrexian, R.drawable.minshengliuhaoxian, R.drawable.minsheng315 };
    private String[] strList = new String[] { "今日资讯", "老三热线", "民生6号线", "民生·315" };
    private String[] introductionList = new String[] { "2015年10月15日", "2015年10月15日", "2015年10月15日", "2015年10月15日" };

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("本台节目");
        includeBottom(1, CenterActivity.class, NativeShowingActivity.class, PaiKeCenterActivity.class);
        setCenterContent(R.layout.main_native_showing);
    }

    @Override
    protected void init()
    {
        View headSpace = new View(getApplicationContext());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, DensityUtil.dip2px(43));
        headSpace.setLayoutParams(params);
        showingList.addHeaderView(headSpace);

        // 加载list数据
        addListData();

        showingList.setAdapter(new MyAdapter(beanList));
    }

    private void addListData()
    {
        for (int i = 0; i < srcList.length; i++)
        {
            NativeShowingBean bean = new NativeShowingBean();
            bean.setImageSrc(srcList[i]);
            bean.setTitle(strList[i]);
            bean.setIntroduction(introductionList[i]);
            beanList.add(bean);
        }
    }

    private class MyAdapter extends FrameAdapter<NativeShowingBean>
    {

        public MyAdapter(List<NativeShowingBean> beanList)
        {
            super(beanList, DensityUtil.dip2px(71));
        }

        @Override
        public View getItemView(int arg0, View arg1, ViewGroup arg2)
        {
            arg1 = setConvertView_Holder(arg1, R.layout.native_showing_item);
            ViewHolder holder = (ViewHolder) arg1.getTag();
            ImageView   icon =   holder.getView(R.id.native_item_icon);
            TextView   title =   holder.getView(R.id.native_item_title);
            TextView   introduction =   holder.getView(R.id.native_item_introduction);
            NativeShowingBean   bean =   getItem(arg0);
            icon.setImageDrawable(getDrawable(icon, bean.getImageSrc()));
            title.setText(bean.getTitle());
            introduction.setText(bean.getIntroduction());
            return arg1;
        }

    }
}
