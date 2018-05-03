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
import com.qc188.com.bean.CompairBean;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;

public class CompairAdapter extends BaseExpandableListAdapter
{
    private static final String TAG = "CompairAdapter";
    private CompairBean cBean;
    private Context context;

    public CompairAdapter(Context context)
    {
        this.context = context;
    }

    public void addData(CompairBean cBean)
    {
        this.cBean = cBean;
        toNormal();
    }

    public void clearLeft()
    {
        leftGroupList = null;
        cBean.leftList = null;
        cBean.differentLeft = null;
        notifyDataSetChanged();
    }

    public void clearRight()
    {
        rightGroupList = null;
        cBean.rightList = null;
        cBean.differentRight = null;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount()
    {

        if (cBean != null)
        {
            if (leftGroupList != null)
            {
                LogUtil.d(TAG, "getGroupCount:" + leftGroupList.size());
                return leftGroupList.size();
            }
            else if (rightGroupList != null)
            {
                LogUtil.d(TAG, "rightGroupList  getGroupCount:" + rightGroupList.size());
                return rightGroupList.size();
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

    /**
     * 显示不同
     */

    public void toDifferent()
    {

        if (cBean != null)
        {
            leftGroupList = cBean.differentLeft;
            rightGroupList = cBean.differentRight;
            notifyDataSetChanged();
        }
    }

    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> leftGroupList;
    public ArrayList<Pair<String, ArrayList<Pair<String, String>>>> rightGroupList;

    /**
     * 显示想痛痛
     */
    public void toNormal()
    {
        if (cBean != null)
        {
            leftGroupList = cBean.leftList;
            rightGroupList = cBean.rightList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (cBean != null)
        {
            if (leftGroupList != null)
            {
                return leftGroupList.get(groupPosition).second.size();

            }
            if (rightGroupList != null)
            {
                return rightGroupList.get(groupPosition).second.size();

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
            // tv.setOnTouchListener(new OnTouchListener()
            // {
            //
            // @Override
            // public boolean onTouch(View v, MotionEvent event)
            // {
            // return false;
            // }
            // });
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
        else if (rightGroupList != null)
        {

            titleStr = rightGroupList.get(groupPosition).first;
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
            convertView = View.inflate(context, R.layout.item_compair_child, null);
        }
        else
        {
        }

        TextView tv_compair_item_child_left = (TextView) convertView.findViewById(R.id.tv_compair_item_child_left);
        TextView tv_compair_item_child_middle = (TextView) convertView.findViewById(R.id.tv_compair_item_child_middle);
        TextView tv_compair_item_child_right = (TextView) convertView.findViewById(R.id.tv_compair_item_child_right);

        if (leftGroupList != null)
        {
            Pair<String, String> leftPair = leftGroupList.get(groupPosition).second.get(childPosition);
            tv_compair_item_child_left.setText(leftPair.second);
            tv_compair_item_child_middle.setText(leftPair.first);
        }
        else
        {
            tv_compair_item_child_left.setText("");
        }
        if (rightGroupList != null)
        {
            Pair<String, String> rightPair = rightGroupList.get(groupPosition).second.get(childPosition);
            tv_compair_item_child_middle.setText(rightPair.first);
            tv_compair_item_child_right.setText(rightPair.second);
        }
        else
        {
            tv_compair_item_child_right.setText("");

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}
