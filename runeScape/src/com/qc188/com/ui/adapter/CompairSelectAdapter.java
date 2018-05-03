package com.qc188.com.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;

public class CompairSelectAdapter extends FrameAdapter implements Observer
{
    private static final String TAG = "CompairSelectAdapter";
    private List<Pair<String, String>> compairList = new ArrayList<Pair<String, String>>();

    public CompairSelectAdapter(Context context, ListView lv)
    {
        super(context);
        this.mListView = lv;
        setItemHeigth(DensityUtil.dip2px(50));
        matchList();
    }


    private ListView mListView;
    private RelativeLayout rl_compairSelect_nullMsg;

    public void setNummMsgRela(RelativeLayout rl_compairSelect_nullMsg)
    {
        this.rl_compairSelect_nullMsg = rl_compairSelect_nullMsg;
    }

    private int selectCount = 0;

    public void matchList()
    {
        Map<String, ?> compairMap = CompairManager.getManger().getSharedMap();
        selectCount = 0;
        if (compairMap != null)
        {
            compairList.clear();
            @SuppressWarnings("unchecked")
            Map<String, String> compairSet = (Map<String, String>) compairMap;
            Set<String> sets = compairSet.keySet();
            for (String set : sets)
            {
                String str = (String) compairMap.get(set);
                if (str.startsWith("true"))
                {
                    selectCount++;
                }
                compairList.add(new Pair<String, String>(set, (String) compairMap.get(set)));
            }

            if (itemClickRunable != null)
            {
                itemClickRunable.onItemClick(null, null, selectCount, 0);
            }
            notifyDataSetChanged();
        }
    }

    private boolean deleteModule = false;

    public void setDeleteModule()
    {
        deleteModule = true;
    }

    public void setCompairModule()
    {
        deleteModule = false;
    }

    @Override
    public int getCount()
    {
        if (compairList != null)
        {
            if (compairList.size() == 0)
            {
                if (rl_compairSelect_nullMsg != null)
                {
                    rl_compairSelect_nullMsg.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                }
            }
            else
            {
                if (rl_compairSelect_nullMsg != null)
                {
                    mListView.setVisibility(View.VISIBLE);
                    rl_compairSelect_nullMsg.setVisibility(View.GONE);
                }
            }
            return compairList.size();
        }

        if (rl_compairSelect_nullMsg != null)
        {
            mListView.setVisibility(View.GONE);
            rl_compairSelect_nullMsg.setVisibility(View.VISIBLE);
        }
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // #FFC7C7C7 cannot color
        // #FF8293A6 selectColor
        convertView = setConvertView_Holder(convertView, R.layout.item_compair_select_content);
        CheckBox cb_item_CompairSelect_checkBox = (CheckBox) convertView.findViewById(R.id.cb_item_CompairSelect_checkBox);
        TextView tv_item_CompairSelect_text = (TextView) convertView.findViewById(R.id.tv_item_CompairSelect_text);
        Pair<String, String> pair = compairList.get(position);
        String checked = pair.second.split("_")[0];
        String msg = pair.second.split("_")[1];
        LogUtil.d(TAG, "adapterGetView:" + checked + "    msg:" + msg + "    id:" + compairList.get(position).first);
        cb_item_CompairSelect_checkBox.setTag(position);
        cb_item_CompairSelect_checkBox.setTag(R.id.tag_first, msg);
        cb_item_CompairSelect_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                int position = (Integer) buttonView.getTag();
                String msg = (String) buttonView.getTag(R.id.tag_first);
                CompairManager.getManger().modifyCompair(compairList.get(position).first, msg, isChecked);
                matchList();

            }
        });

        Boolean isChecked = Boolean.valueOf(checked);
        cb_item_CompairSelect_checkBox.setChecked(isChecked);

        LogUtil.d(TAG, "isChecked:" + isChecked + "   selectCount:" + selectCount);
        if (!deleteModule && !isChecked && selectCount >= 2)
        {
            cb_item_CompairSelect_checkBox.setClickable(false);
            tv_item_CompairSelect_text.setTextColor(0xFFC7C7C7);
        }
        else
        {
            cb_item_CompairSelect_checkBox.setClickable(true);
            tv_item_CompairSelect_text.setTextColor(0xFF8293A6);

        }

        // if (isChecked)
        // {
        // cb_item_CompairSelect_checkBox.setClickable(false);
        // }

        tv_item_CompairSelect_text.setText(msg);

        return convertView;
    }

    private OnItemClickListener itemClickRunable;

    public void setOnSelectChangeRunnable(OnItemClickListener runable)
    {
        itemClickRunable = runable;
    }

	@Override
	public void update(Observable observable, Object data) {
		LogUtil.d(TAG, "update:CompairSelectAdapter");
		matchList();
	}

}
