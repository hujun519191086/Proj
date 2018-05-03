package com.qc188.com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.LogUtil;

public class SearchContentExpandableAdapter extends BaseExpandableListAdapter {
	private static final String TAG = "SearchContentExpandableAdapter";
	private ArrayList<SearchContentBean> dataList;
	private ArrayList<ArrayList<SearchContentBean>> contentMap = new ArrayList<ArrayList<SearchContentBean>>();

	private Context mContext;

	public SearchContentExpandableAdapter(Context context, ArrayList<SearchContentBean> data) {
		dataList = data;
		mContext = context;
		matchData();
	}

	public void setList(ArrayList<SearchContentBean> data) {
		dataList = data;
		matchData();
	}

	public void clearList() {
		if (contentMap != null) {
			contentMap.clear();
		}
		if (dataList != null) {
			dataList.clear();
		}
		matchData();
	}

	private void matchData() {
		int lastChangePosition = 0;
		if (dataList == null || dataList.size() < 1) {
			contentMap.clear();
			notifyDataSetChanged();
			return;
		}
		String lastChange = dataList.get(0).getChang();
		for (int i = 0; i < dataList.size(); i++) {
			String chang = dataList.get(i).getChang();
			if (!TextUtils.isEmpty(chang) && !chang.equals(lastChange)) {
				ArrayList<SearchContentBean> itemSearchList = new ArrayList<SearchContentBean>();
				itemSearchList.addAll(dataList.subList(lastChangePosition, i));
				contentMap.add(itemSearchList);
				lastChangePosition = i;
				lastChange = dataList.get(i).getChang();
			}
		}
		ArrayList<SearchContentBean> itemSearchList = new ArrayList<SearchContentBean>();
		itemSearchList.addAll(dataList.subList(lastChangePosition, dataList.size()));
		contentMap.add(itemSearchList);
		notifyDataSetChanged();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_search_content, null);
			AbsListView.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(73));
			convertView.setLayoutParams(params);

		}

		if (contentMap.size() <= groupPosition) {
			return null;
		}
		SearchContentBean scb = contentMap.get(groupPosition).get(childPosition);
		LogUtil.d(TAG, "getview  times:" + childPosition);
		ImageView iv_searchContent_icon = (ImageView) convertView.findViewById(R.id.iv_searchContent_icon);
		TextView tv_searchContent_name = (TextView) convertView.findViewById(R.id.tv_searchContent_name);
		TextView tv_searchContent_sale = (TextView) convertView.findViewById(R.id.tv_searchContent_sale);
		tv_searchContent_name.setText(scb.getCar_name());
		tv_searchContent_sale.setText(scb.getCar_price());

		ImageLoadUtil.loadImageFromDefault(scb.getCar_imageUrl(), iv_searchContent_icon, ConstantValues.DEFAULT_DRAWABLE_A);

		convertView.setTag(R.id.activity_tag, scb);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return contentMap.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return contentMap.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.search_content_title, null);
			convertView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

				}
			});

		}

		TextView tv_searchContentList_title = (TextView) convertView.findViewById(R.id.tv_searchContentList_title);
		String change = contentMap.get(groupPosition).get(0).getChang();
		tv_searchContentList_title.setText(change);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
