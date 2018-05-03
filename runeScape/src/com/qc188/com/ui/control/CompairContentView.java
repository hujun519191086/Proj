package com.qc188.com.ui.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.util.LogUtil;

public class CompairContentView implements OnClickListener
{
    private static final String TAG = "CompairContentView";
    private TableLayout contentView;
    private int selectColor = 0xFF323232;
    private View selectView = null;
    private int rowCount = 4;
    private OnClickListener onitemClick;
    private int[] compairIdList = new int[] { R.id.tv_compairContent_item_row1, R.id.tv_compairContent_item_row2, R.id.tv_compairContent_item_row3, R.id.tv_compairContent_item_row4 };

    private int[] use = null;

    public CompairContentView(TableLayout contentView, OnClickListener onitemClick, boolean compair)
    {

        if (compair)
        {
            use = compairIdList;
        }
        else
        {
            use = compairIdList;
        }
        this.contentView = contentView;
        this.onitemClick = onitemClick;
        for (int i = 1; i < rowCount + 1; i++)
        {

            View rowView = contentView.getChildAt(i - 1);
            // 01-10 20:43:36.586: D/CompairContentView(31835): tagName:外部配置 titleSelectMap:{座椅配置=3, 多媒体配置=4, 内部配置=2, 安全装备=1, 空调/冰箱=7, 灯光配置=5, 基本参数=0, 玻璃/后视镜=6}

            ArrayList<TextView> childViews = new ArrayList<TextView>();

            TextView cildView = (TextView) rowView.findViewById(use[0]);
            cildView.setOnClickListener(this);
            cildView.setBackgroundColor(0);
            childViews.add(cildView);
            cildView = (TextView) rowView.findViewById(use[1]);
            cildView.setOnClickListener(this);
            cildView.setBackgroundColor(0);
            childViews.add(cildView);
            cildView = (TextView) rowView.findViewById(use[2]);
            cildView.setOnClickListener(this);
            cildView.setBackgroundColor(0);
            childViews.add(cildView);
            cildView = (TextView) rowView.findViewById(use[3]);
            cildView.setOnClickListener(this);
            cildView.setBackgroundColor(0);
            childViews.add(cildView);

            Pair<View, ArrayList<TextView>> pair = new Pair<View, ArrayList<TextView>>(rowView, childViews);
            tabView.add(pair);
        }
    }

    ArrayList<Pair<View, ArrayList<TextView>>> tabView = new ArrayList<Pair<View, ArrayList<TextView>>>();

    HashMap<String, Integer> titleSelectMap;
    TreeMap<Integer, String> titleSelectTreeMap;

    public void matchData(HashMap<String, Integer> titleSelectMap)
    {
        this.titleSelectMap = titleSelectMap;
        titleSelectTreeMap = new TreeMap<Integer, String>();
        Set<String> sets = titleSelectMap.keySet();
        for (String set : sets)
        {
            titleSelectTreeMap.put(titleSelectMap.get(set), set);
        }

        int line = titleSelectMap.size() / rowCount + 1;
        for (int i = 0; i < tabView.size(); i++)
        {
            ArrayList<TextView> rowViews = tabView.get(i).second;
            if (i < line)
            {
                tabView.get(i).first.setVisibility(View.VISIBLE);
                for (int j = 0; j < rowViews.size(); j++)
                {
                    int mapPosition = (i * rowCount) + j;

                    LogUtil.d(TAG, "text:" + titleSelectTreeMap.get(mapPosition));
                    rowViews.get(j).setText(titleSelectTreeMap.get(mapPosition));
                }
            }
            else
            {
                tabView.get(i).first.setVisibility(View.GONE);
            }

        }
    }

    public void changBackgroud(String tagName)
    {

        if (titleSelectMap == null || !titleSelectMap.containsKey(tagName))
        {
            return;

        }
        LogUtil.d(TAG, "tagName:" + tagName + "  titleSelectMap:" + titleSelectMap);
        int position = titleSelectMap.get(tagName);

        int line = position / rowCount;
        int index = position % rowCount;
        View tempView = tabView.get(line).second.get(index);
        if (selectView != tempView)
        {
            if (selectView != null)
            {
                selectView.setBackgroundColor(0);
            }
            selectView = tempView;
            selectView.setBackgroundColor(selectColor);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (selectView != null && selectView == v)
        {

        }
        else
        {

            if (selectView != null)
            {
                selectView.setBackgroundColor(0);
            }

            if (onitemClick != null)
            {
                onitemClick.onClick(v);
            }
            selectView = v;
            selectView.setBackgroundColor(selectColor);
        }
    }

}
