package com.hebeitv.news.view.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.ImageLoadUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.android.volley.toolbox.NetworkImageView;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.MyReadBean;
import com.hebeitv.news.control.RefreshLayout;
import com.hebeitv.news.control.RefreshLayout.OnLoadListener;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.view.PersonCenterActivity;
import com.hebeitv.news.view.WebActivity;

public class MyReadActivity extends FrameActivity implements OnLoadListener, OnItemClickListener
{
    @ResetControl(id = R.id.lv_my_read)
    private ListView my_readList;
    @ResetControl(id = R.id.rl_my_read_content)
    private RefreshLayout rl_my_read_content;
    private TextView deleteView;
    private MyReadAdapter listAdapter;
    private int messageId;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {

        int message = getIntent().getIntExtra(PersonCenterActivity.ABOUT_MY_TITLE_KEY, MessageId.MESSAGE_ID_MY_READ);
        String title = (message == MessageId.MESSAGE_ID_MY_READ) ? "我的阅读" : ((message == MessageId.MESSAGE_ID_HOTREG) ? "热门推荐" : ((message == MessageId.MESSAGE_ID_COLLECTION) ? "我的收藏" : ((message == MessageId.MESSAGE_ID_MY_UPLOAD) ? "我的上传"
                : "")));
        includeTop(title, true, false, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        setCenterContent(R.layout.my_read);

    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init()
    {
        setCommonTopBack();
        deleteView = setCommonTopRightText("编辑");
        deleteView.setTag(true);
        deleteView.setOnClickListener(this);
        ArrayList<MyReadBean> beanList = (ArrayList<MyReadBean>) getIntent().getSerializableExtra(PersonCenterActivity.ABOUT_MY_MESSAGE_KEY);
        messageId = getIntent().getIntExtra(PersonCenterActivity.ABOUT_MY_TITLE_KEY, MessageId.MESSAGE_ID_MY_READ);
        listAdapter = new MyReadAdapter(beanList);
        my_readList.setAdapter(listAdapter);
        my_readList.setOnItemClickListener(this);

        rl_my_read_content.setOnLoadListener(this, null);
    }

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_ttile_rightText:

                boolean isEdit = (Boolean) v.getTag();

                listAdapter.deleteStatus(isEdit);
                listAdapter.notifyDataSetChanged();
                if (isEdit)
                {
                    deleteView.setText("删除");

                }
                else
                {
                    deleteView.setText("编辑");
                    HashMap<String, MyReadBean> list = listAdapter.getDeleteList();

                    if (list != null && list.size() > 0)
                    {

                        Set<String> sets = list.keySet();
                        Object[] keys = sets.toArray();
                        String deleteNewsIds = "";
                        for (int i = 0; i < keys.length; i++)
                        {
                            deleteNewsIds += keys[i] + ";";
                        }
                        deleteNewsIds = deleteNewsIds.subSequence(0, deleteNewsIds.length() - 1).toString();
                        IMessage message = getModule(IMessage.class);

                        message.deleteReadHistory(123, deleteNewsIds, this);
                    }
                    // 这里进行删除条目
                }
                v.setTag(!isEdit);
                break;

            default:
                break;
        }
    }

    private class MyReadAdapter extends FrameAdapter<MyReadBean> implements OnCheckedChangeListener
    {

        public MyReadAdapter(ArrayList<MyReadBean> list)
        {
            super(list, DensityUtil.dip2px(120));
        }

        private HashMap<String, MyReadBean> deleteList = new HashMap<String, MyReadBean>();

        public HashMap<String, MyReadBean> getDeleteList()
        {
            return deleteList;
        }

        public void removeDeleteList()
        {

            Collection<MyReadBean> collection = deleteList.values();
            Object[] deleteListBean = collection.toArray();
            for (int i = 0; i < deleteListBean.length; i++)
            {
                dataList.remove(deleteListBean[i]);
            }
            deleteList.clear();
            notifyDataSetChanged();
        }

        private boolean visibleDelete = false;

        public void deleteStatus(boolean visible)
        {
            visibleDelete = visible;
        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent)
        {
            convertView = setConvertView_Holder(convertView, R.layout.item_my_read);

            MyReadBean mrb = getItem(position);
            String url = (TextUtils.isEmpty(mrb.getPic()) ? mrb.getPrePath() : mrb.getPic());
            String title = mrb.getTitle();
            CheckBox deleteView = (CheckBox) convertView.findViewById(R.id.iv_myRead_left_point);
            deleteView.setVisibility(visibleDelete ? View.VISIBLE : View.INVISIBLE);
            deleteView.setChecked(false);
            deleteView.setTag(mrb);
            deleteView.setOnCheckedChangeListener(this);

            NetworkImageView niv = (NetworkImageView) convertView.findViewById(R.id.tv_myRead_pic);
            ImageLoadUtil.loadImage(getApplicationContext(), niv, url);

            TextView tv_myRead_title = (TextView) convertView.findViewById(R.id.tv_myRead_title);
            tv_myRead_title.setText(title);
            convertView.setTag(R.id.tag_first, TextUtils.isEmpty(mrb.getPic()) ? mrb.getvPath() : mrb.getPic());

            // TextView tv_myRead_second_title = (TextView) convertView.findViewById(R.id.tv_myRead_second_title);
            // tv_myRead_second_title.setText(mrb.get);//meiyou

            return convertView;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            MyReadBean bean = (MyReadBean) buttonView.getTag();
            String key = TextUtils.isEmpty(bean.getId()) ? bean.getvId() : bean.getId();

            if (isChecked)
            {
                deleteList.put(key, bean);
            }
            else
            {
                deleteList.remove(key);
            }
        }
    }

    /**
     * 删除掉已删除的东西.
     */
    @Override
    public void onSuccess(int messageId, String resultContent)
    {
        listAdapter.removeDeleteList();
        super.onSuccess(messageId, resultContent);
    }

    private int loadPage = 0;

    @SuppressWarnings("unchecked")
    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        LogInfomation.d(TAG, "Success bean:" + bean);
        loadPage++;
        listAdapter.addList((List<MyReadBean>) bean);
        rl_my_read_content.setLoading(false);
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        super.onFault(messageId, errorCode, headers, reslutContent, e);
        rl_my_read_content.setLoading(false);
    }

    @Override
    public void onLoad()
    {
        LogInfomation.d(TAG, "onLoad:" + (loadPage + 1) + "  messageId:" + messageId);
        getModule(IMessage.class).loadMyReadMessage(messageId, (loadPage + 1) + "", this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String pcb = (String) view.getTag(R.id.tag_first);
        startActivity(WebActivity.class, new SirPair<String, String>("url", pcb));
    }
}
