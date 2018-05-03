package com.hebeitv.news.view.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.ImageLoadUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.android.volley.toolbox.NetworkImageView;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.NewsInfoBean;
import com.hebeitv.news.control.RefreshLayout;
import com.hebeitv.news.control.RefreshLayout.OnLoadListener;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.view.main.CenterActivity;
import com.hebeitv.news.view.main.CenterDetailActivity;

public class MainCenterAdapter extends PagerAdapter implements OnItemClickListener
{
    private int count;
    private Class<? extends FrameActivity> beloneUiClazz;
    private CenterActivity mbeloneUi;
    private Integer[] pageItemViewResources;
    private View[] pageItemViews;
    private ViewPager absRefreshView;

    private SparseArray<RefreshLayout> refreshViewList = new SparseArray<RefreshLayout>();

    public MainCenterAdapter(CenterActivity beloneUi, Integer[] pageItemViewResources, int count, ViewPager rl)
    {
        this.count = count;
        this.beloneUiClazz = beloneUi.getClass();
        absRefreshView = rl;
        mbeloneUi = beloneUi;
        this.pageItemViewResources = pageItemViewResources;
        pageItemViews = new View[pageItemViewResources.length];

    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(pageItemViews[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = pageItemViews[position];
        if (itemView == null)
        {
            itemView = pageItemViews[position] = View.inflate(InitUtil.getInitUtil().context, pageItemViewResources[position], null);
            if (position < 4)
            {
                RefreshLayout srl = (RefreshLayout) itemView;

                addListData((ListView) srl.findViewById(R.id.id_listview), srl, position);
            }
            else if (position == 4)
            {
                mbeloneUi.LoadEmp((ScrollView) itemView);
            }
        }
        container.addView(itemView, 0);
        return itemView;
    }

    private SparseArray<ArrayList<NewsInfoBean>> m_dataList = new SparseArray<ArrayList<NewsInfoBean>>();
    private SparseArray<FrameAdapter<NewsInfoBean>> itemAdapterList = new SparseArray<FrameAdapter<NewsInfoBean>>();

    public void addData(int messageId, ArrayList<NewsInfoBean> dataList, boolean isAdd)
    {
        if (!isAdd)
        {
            m_dataList.get(messageId).clear();
        }
        goneRefresh(messageId);
        ArrayList<NewsInfoBean> list = m_dataList.get(messageId);
        if (list != null)
        {
            list.addAll(dataList);
        }
        else
        {
            m_dataList.put(messageId, dataList);
        }
        itemAdapterList.get(messageId).setList(m_dataList.get(messageId));
    }

    public void goneRefresh(int messageId)
    {
        RefreshLayout rl = refreshViewList.get(messageId);
        if (rl != null)
        {
            rl.setRefreshing(false);
            rl.setLoading(false);
        }
    }

    /**
     * 页面切换是否需要加载数据
     * 
     * @return
     */
    public boolean needLoadMessage(int messageId)
    {

        if (messageId == MessageId.MESSAGE_ID_LOAD_EMP)
        {
            return false;
        }
        ArrayList<NewsInfoBean> list = m_dataList.get(messageId - MessageId.MESSAGE_ID_JING_XUAN);
        return (list == null || list.size() == 0);
    }

    private void addListData(ListView lv, RefreshLayout srl, int position)
    {
        refreshViewList.append(position, srl);
        View headerView = View.inflate(InitUtil.getInitUtil().context, R.layout.center_title, null);
        lv.addHeaderView(headerView);
        OnRefresh or = new OnRefresh(mbeloneUi);
        srl.setOnRefreshListener(or);
        srl.setOnLoadListener(or, headerView);
        lv.setTag(position + MessageId.MESSAGE_ID_JING_XUAN);
        lv.setTag(R.id.tag_second, headerView);

        // ----------
        ArrayList<NewsInfoBean> newsInfoBeanList = m_dataList.get(position);
        if (newsInfoBeanList != null && newsInfoBeanList.size() > 0)
        {

            NewsInfoBean nib = newsInfoBeanList.get(0);
            headerView.setTag(R.id.tag_first, nib);

            TextView center_header_text = (TextView) headerView.findViewById(R.id.center_header_text);
            NetworkImageView center_header_image = (NetworkImageView) headerView.findViewById(R.id.center_header_image);
            center_header_text.setText(nib.getTitle());
            ImageLoadUtil.loadImage(InitUtil.getInitUtil().context, center_header_image, nib.getPic());
        }

        FrameAdapter<NewsInfoBean> fa = new FrameAdapter<NewsInfoBean>(newsInfoBeanList, DensityUtil.dip2px(125), lv)
        {

            @Override
            public void notifyDataSetChanged()
            {
                if (dataList != null && dataList.size() > 0)
                {
                    View headerView = (View) m_absListView.getTag(R.id.tag_second);
                    NewsInfoBean nib = getItem(0);
                    TextView center_header_text = (TextView) headerView.findViewById(R.id.center_header_text);
                    NetworkImageView center_header_image = (NetworkImageView) headerView.findViewById(R.id.center_header_image);
                    center_header_text.setText(nib.getTitle());
                    headerView.setTag(R.id.tag_first, nib);
                    ImageLoadUtil.loadImage(getContext(), center_header_image, nib.getPic());

                }
                super.notifyDataSetChanged();
            }

            public int getCountInSelf()
            {
                if (dataList != null && dataList.size() > 0)
                {
                    return dataList.size() - 1;
                }
                else
                {
                    return 0;
                }
            };

            @Override
            public View getItemView(int arg0, View arg1, ViewGroup arg2)
            {
                arg1 = setConvertView_Holder(arg1, R.layout.main_list_item);
                ViewHolder holder = (ViewHolder) arg1.getTag();

                NewsInfoBean newInfoBean = getItem(arg0 + 1);
                arg1.setTag(R.id.tag_first, newInfoBean);
                NetworkImageView niv = holder.getView(R.id.item_main_list_pic, NetworkImageView.class);
                ImageLoadUtil.loadImage(getContext(), niv, newInfoBean.getPic());
                holder.getView(R.id.item_main_list_text, TextView.class).setText(newInfoBean.getTitle());

                return arg1;
            }
        };
        itemAdapterList.append(position, fa);
        lv.setAdapter(fa);
        lv.setOnItemClickListener(this);
    }

    private class OnRefresh implements OnRefreshListener, OnLoadListener
    {
        CenterActivity uiname;

        public OnRefresh(CenterActivity uiname)
        {
            this.uiname = uiname;
        }

        @Override
        public void onRefresh()
        {
            uiname.reloadMessage(absRefreshView.getCurrentItem());
        }

        @Override
        public void onLoad()
        {
            uiname.loadMoreMessage(absRefreshView.getCurrentItem());
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {

        IMessage message = ModuleManager.getManager().getModule(IMessage.class);
        NewsInfoBean nib = (NewsInfoBean) arg1.getTag(R.id.tag_first);

        if (nib == null)
        {
            return;
        }
        LogInfomation.d("itemClick", "arg1:" + arg1.getId() + "   arg2:" + arg2 + "     arg3:" + arg3 + "   nib:" + nib);
        message.onNewsItemClickMessage(MessageId.MESSAGE_ID_NEW_DETAIL, nib, (Integer) arg0.getTag());

        mbeloneUi.startActivity(CenterDetailActivity.class, new SirPair<String, NewsInfoBean>(CenterActivity.centerDetailSerializDataKey, nib));

    }
}
