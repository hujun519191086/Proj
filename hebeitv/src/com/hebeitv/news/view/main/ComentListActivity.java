package com.hebeitv.news.view.main;

import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.ComnetListBean;
import com.hebeitv.news.control.RefreshLayout;
import com.hebeitv.news.control.RefreshLayout.OnLoadListener;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;

public class ComentListActivity extends FrameActivity
{
    @ResetControl(id = R.id.rl_coment_list_refreshView)
    private RefreshLayout rl;
    @ResetControl(id = R.id.rl_coment_list_contentList)
    private ListView contentList;

    private String nowPage = "-1";
    private boolean isReload = false;

    private String newsId;
    private ComentListAdapter clAdapter;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("评论", true, false, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        super.onCreateView(savedInstanceState);
        setCenterContent(R.layout.coment_list);
    }

    @Override
    protected void init()
    {
        newsId = (String) getIntent().getSerializableExtra(CenterDetailActivity.COMENT_LIST_DATA_KEY);
        reloadMessage();

        OnRefresh or = new OnRefresh();
        rl.setOnRefreshListener(or);
        rl.setOnLoadListener(or, null);
        clAdapter = new ComentListAdapter();
        contentList.setAdapter(clAdapter);
    }

    private class OnRefresh implements OnRefreshListener, OnLoadListener
    {

        public OnRefresh()
        {
        }

        @Override
        public void onRefresh()
        {
            reloadMessage();
        }

        @Override
        public void onLoad()
        {
            loadMoreMessage();

        }
    }

    public void reloadMessage()
    {
        isReload = true;
        getModule(IMessage.class).commentList(MessageId.MESSAGE_ID_COMENT_LIST, newsId, "0", this);
    }

    public void loadMoreMessage()
    {
        isReload = false;
        getModule(IMessage.class).commentList(MessageId.MESSAGE_ID_COMENT_LIST, newsId, Integer.valueOf(nowPage) + 1 + "", this);
    }

    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        if (isReload)
        {
            nowPage = "0";
        }
        else
        {
            nowPage = (Integer.valueOf(nowPage) + 1) + "";
        }

        clAdapter.addList((List<ComnetListBean>) bean);
        if (rl != null)
        {
            rl.setRefreshing(false);
            rl.setLoading(false);
        }
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        if (rl != null)
        {
            rl.setRefreshing(false);
            rl.setLoading(false);
        }
    }

    private class ComentListAdapter extends FrameAdapter<ComnetListBean>
    {

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent)
        {

            View contentView = View.inflate(getApplicationContext(), R.layout.item_comnet_list, null);
            TextView item_comnet_name = (TextView) contentView.findViewById(R.id.item_comnet_name);
            TextView item_comnet_time = (TextView) contentView.findViewById(R.id.item_comnet_time);
            TextView item_comnet_content = (TextView) contentView.findViewById(R.id.item_comnet_content);

            ComnetListBean clb = getItem(position);
            item_comnet_name.setText(clb.rName);
            item_comnet_time.setText(clb.time);
            item_comnet_content.setText(clb.comment);

            return contentView;
        }

    }
}
