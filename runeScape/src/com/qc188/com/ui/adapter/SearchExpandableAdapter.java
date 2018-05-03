package com.qc188.com.ui.adapter;

import java.util.ArrayList;

import com.qc188.com.bean.SortBean;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class SearchExpandableAdapter extends BaseExpandableListAdapter {

	private ArrayList<SortBean> result;
	private String lastType = "";
	private int groupCount = 0;

	public SearchExpandableAdapter(ArrayList<SortBean> result) {
		this.result = result;

		for (int i = 0; i < result.size(); i++) {
			if (!lastType.equals(result.get(i).getType_id())) {
				lastType = result.get(i).getType_id();
				this.result.add(i, null);
				i++;
			}
		}
	}

	@Override
	public int getGroupCount() {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
