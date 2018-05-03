package com.qc188.com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.TypeBean;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;

public class CarTypeOptionAdapter extends BaseExpandableListAdapter
{
    private static final String TAG = "CarTypeOptionAdapter";
    private TypeBean tBean;
    private Context context;

    public CarTypeOptionAdapter(Context context)
    {
        this.context = context;
    }

    public void addData(TypeBean cBean)
    {
        this.tBean = cBean;

        leftGroupList = tBean.list;
    }

    @Override
    public int getGroupCount()
    {

        if (tBean != null)
        {
            if (leftGroupList != null)
            {
                LogUtil.d(TAG, "getGroupCount:" + leftGroupList.size());
                return leftGroupList.size();
            }
        }
        return 0;
    }

    public int getGroupHeight()
    {
        return groupItemHeigh;
    }

    public int getChildHeight()
    {
        return groupItemHeigh;
    }

    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> leftGroupList;

    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (tBean != null)
        {
            if (leftGroupList != null)
            {
                return leftGroupList.get(groupPosition).second.size();

            }

        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    private int groupItemHeigh = DensityUtil.dip2px(35);

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        TextView tv = null;
        if (convertView == null)
        {
            tv = new TextView(context);
            tv.setTextColor(0xFF858688);
            tv.setTextSize(15);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(0xFFDADBDD);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, groupItemHeigh);
            tv.setLayoutParams(params);
            tv.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    // TODO Auto-generated method stub

                }
            });
        }
        else
        {
            tv = (TextView) convertView;
        }
        String titleStr = "";
        if (leftGroupList != null)
        {
            titleStr = leftGroupList.get(groupPosition).first;
        }
        LogUtil.d(TAG, "title:" + titleStr + "  groupPosition:" + groupPosition);
        tv.setText(titleStr);
        return tv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        LogUtil.d(TAG, "getChildView:" + groupPosition + "  childPosition:" + childPosition + "isLastChild :" + isLastChild);
        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_car_type_option, null);
        }
        else
        {
        }

        TextView tv_compair_item_child_left = (TextView) convertView.findViewById(R.id.tv_carType_item_child_left);
        TextView tv_compair_item_child_right = (TextView) convertView.findViewById(R.id.tv_carType_item_child_right);

        if (leftGroupList != null)
        {
            Pair<String, String> leftPair = leftGroupList.get(groupPosition).second.get(childPosition);
            tv_compair_item_child_left.setText(leftPair.first);
            tv_compair_item_child_right.setText(leftPair.second);
        }
        else
        {
            tv_compair_item_child_left.setText("");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }
}
