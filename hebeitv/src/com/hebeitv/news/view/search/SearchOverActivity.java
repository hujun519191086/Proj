package com.hebeitv.news.view.search;

import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.ImageLoadUtil;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.android.volley.toolbox.NetworkImageView;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.SearchBean;
import com.hebeitv.news.control.RefreshLayout;
import com.hebeitv.news.control.RefreshLayout.OnLoadListener;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.view.WebActivity;

public class SearchOverActivity extends FrameActivity implements OnLoadListener, OnItemClickListener
{
    @ResetControl(id = R.id.rl_refresh_search)
    private RefreshLayout refreshSearch;
    @ResetControl(id = R.id.lv_searchover)
    private ListView searchContent;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("搜索结果", true, false);
        setCenterContent(R.layout.search_over);

    }

    private FrameAdapter<SearchBean> frameSearchValueadapter;
    private String content;
    private int nowPage = 0;
    private boolean isReload = false;

    @Override
    protected void init()
    {
        refreshSearch.closeSwipRefresh();
        refreshSearch.setOnLoadListener(this, null);
        frameSearchValueadapter = new FrameAdapter<SearchBean>(DensityUtil.dip2px(125))
        {
            @Override
            public View getItemView(int position, View convertView, ViewGroup parent)
            {
                convertView = setConvertView_Holder(convertView, R.layout.main_list_item);
                ViewHolder vh = (ViewHolder) convertView.getTag();

                SearchBean sb = getItem(position);
                convertView.setTag(R.id.tag_second, sb);
                ImageLoadUtil.loadImage(getApplicationContext(), vh.getView(R.id.item_main_list_pic, NetworkImageView.class), sb.getPic());
                vh.getView(R.id.item_main_list_text, TextView.class).setText(sb.getTitle());
                return convertView;
            }
        };
        searchContent.setAdapter(frameSearchValueadapter);
        searchContent.setOnItemClickListener(this);
        content = getIntent().getStringExtra(SearchActivity.searchKey);
        getModule(IMessage.class).search(MessageId.MESSAGE_ID_SEARCH, "0", content, this);
        isReload = true;
    }

    @SuppressWarnings("unchecked")
    public <T> void onSuccess(int messageId, T bean)
    {

        if (isReload)
        {
            frameSearchValueadapter.setList(null);
            nowPage = 0;
        }
        else
        {
            nowPage++;
        }
        frameSearchValueadapter.addList((List<SearchBean>) bean);
        refreshSearch.setLoading(false);
        System.out.println(" search over bean:" + bean);
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        super.onFault(messageId, errorCode, headers, reslutContent, e);
        postDelay(new Runnable()
        {
            public void run()
            {
                finish();
            }
        }, 1000);
    }

    @Override
    public void onLoad()
    {
        isReload = (nowPage + 1 == 0);
        getModule(IMessage.class).search(MessageId.MESSAGE_ID_SEARCH, (nowPage + 1) + "", content, this);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        SearchBean sb = (SearchBean) view.getTag(R.id.tag_second);
        startActivity(WebActivity.class, new SirPair<String, String>("url", sb.getUrl()));
    }
}
