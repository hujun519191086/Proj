package com.qc188.com.ui.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandDetailEngineBean;
import com.qc188.com.bean.CopyOfbrandDetailEngineItem;
import com.qc188.com.util.DensityUtil;

public class BrandDetailContentExp extends ExpandableListView {

	public BrandDetailContentExp(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public BrandDetailContentExp(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BrandDetailContentExp(Context context) {
		super(context);
	}

	public void addList(List<BrandDetailEngineBean> list) {
		if (list == null || list.size() < 1) {
			return;
		}
		List<BrandDetailEngineBean> onSaleList = new ArrayList<BrandDetailEngineBean>();
		List<BrandDetailEngineBean> stopSaleList = new ArrayList<BrandDetailEngineBean>();
		for (int i = 0; i < list.size(); i++) {

			if (CopyOfbrandDetailEngineItem.NOT_SALE == list.get(i).getList()
					.get(0).getStopsale()) {

				stopSaleList.add(list.get(i));
			} else {
				onSaleList.add(list.get(i));
			}
		}

		baseExpandable = new MyBaseExpanable(onSaleList, stopSaleList);
		setAdapter(baseExpandable);

		for (int i = 0; i < baseExpandable.getGroupCount(); i++) {
			expandGroup(i);
		}
	}

	private MyBaseExpanable baseExpandable;

	public void openStopSaleList() {
		baseExpandable.loadStopSale();
	}

	public boolean hasStopSale() {
		return baseExpandable.hasStopSale();
	}

	private class MyBaseExpanable extends BaseExpandableListAdapter {

		private List<BrandDetailEngineBean> onSaleList;
		private List<BrandDetailEngineBean> stopSaleList;

		public MyBaseExpanable(List<BrandDetailEngineBean> onSaleList,
				List<BrandDetailEngineBean> stopSaleList) {
			this.onSaleList = onSaleList;
			this.stopSaleList = stopSaleList;

		}

		public boolean hasStopSale() {
			return stopSaleList != null && stopSaleList.size() > 0;

		}

		public void loadStopSale() {
			onSaleList.addAll(onSaleList.size(), stopSaleList);
		}

		@Override
		public int getGroupCount() {
			return onSaleList.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return onSaleList.get(groupPosition).getList().size();
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
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.branddetail_content_group, null);
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});
			}

			TextView tv = (TextView) convertView;
			tv.setText(onSaleList.get(groupPosition).getTitle());
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getContext(),
						R.layout.item_brand_list_content, null);
				holder = new ViewHolder(convertView);
				setLayoutParams(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			CopyOfbrandDetailEngineItem coeItem = onSaleList.get(groupPosition)
					.getList().get(childPosition);
			holder.matchData(coeItem);
			setLayoutParams(convertView);
			return convertView;
		}

		private int itemHeight = DensityUtil.dip2px(85);

		public void setLayoutParams(View convertView) {
			AbsListView.LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, itemHeight);
			convertView.setLayoutParams(params);
		}

		private class ViewHolder {
			public ViewHolder(View view) {
				tv_brandDetail_content_name = (TextView) view
						.findViewById(R.id.tv_brandDetail_content_name);
				tv_brandDetail_content_sale = (TextView) view
						.findViewById(R.id.tv_brandDetail_content_sale);
				tv_brandDetail_content_detail = (TextView) view
						.findViewById(R.id.tv_brandDetail_content_detail);
				bt_brandDetail_content_ask = (Button) view
						.findViewById(R.id.bt_brandDetail_content_ask);
			}

			TextView tv_brandDetail_content_name;
			TextView tv_brandDetail_content_sale;
			TextView tv_brandDetail_content_detail;
			Button bt_brandDetail_content_ask;

			public void matchData(CopyOfbrandDetailEngineItem coeItem) {
				tv_brandDetail_content_name.setText(coeItem.getIntroduce());
				tv_brandDetail_content_sale.setText(coeItem.getSale());
				tv_brandDetail_content_detail.setText(coeItem.getDetail());
			}
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
}
