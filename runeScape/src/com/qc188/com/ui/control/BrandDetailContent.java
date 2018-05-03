package com.qc188.com.ui.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandDetailEngineBean;
import com.qc188.com.bean.CopyOfbrandDetailEngineItem;
import com.qc188.com.ui.adapter.BrandDetialAdapter;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.Utility;

public class BrandDetailContent extends LinearLayout {

	private static final String TAG = "BrandDetailContent";

	public BrandDetailContent(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BrandDetailContent(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
	}

	private List<Pair<ViewHolder, View>> contentList;

	private boolean stopSaleShowing = false;

	public boolean stopSaleShow() {
		return stopSaleShowing;
	}

	public boolean hasStopSale() {
		return !(stopSaleList == null || stopSaleList.size() < 1);
	}

    public void addList(List<BrandDetailEngineBean> list, Map<String, ?> map, Activity activity)
    {
        addList(list, map, false, activity);
	}

    public void addList(List<BrandDetailEngineBean> list, Map<String, ?> map, boolean addCarTypeModule, Activity activity)
    {
		if (list == null) {
			return;
		}

		LogUtil.d(TAG, "size:" + list.size() + "    list:" + list);
		int forSize = list.size();

		for (int i = 0; i < forSize; i++) {
			list.get(i).checkCompairList(map);
			List<CopyOfbrandDetailEngineItem> contentList = list.get(i).getList();
			if (contentList != null && contentList.get(0) != null && !stopSaleShowing
					&& CopyOfbrandDetailEngineItem.NOT_SALE == contentList.get(0).getStopsale()) {
				if (stopSaleList == null) {
					stopSaleList = new ArrayList<BrandDetailEngineBean>();
				}
				stopSaleList.add(list.get(i));
				continue;
			}

			// LogUtil.d(TAG, "addData:" +
			// list.get(i).getList().get(0).getStopsale());
            addData(list.get(i), addCarTypeModule, activity);
		}
	}

    public void openNotSaleList(Activity activity)
    {
		stopSaleShowing = true;
        addList(stopSaleList, null, false, activity);
	}

	private List<BrandDetailEngineBean> stopSaleList;

    public void addData(BrandDetailEngineBean bdeBean, Activity activity)
    {
        addData(bdeBean, false, activity);
	}

    public void addData(BrandDetailEngineBean bdeBean, boolean addCarTypeModule, Activity activity)
    {
		if (contentList == null) {
			contentList = new ArrayList<Pair<ViewHolder, View>>();
		}
		View addView = View.inflate(getContext(), R.layout.brand_list_content_item, null);

		LogUtil.d(TAG, "addView:" + addView);

		ViewHolder holder = new ViewHolder();
		holder.tv_brandDetail_contentTitle = (TextView) addView.findViewById(R.id.tv_brandDetail_contentTitle);
		holder.lv_brandDetail_contentList = (ListView) addView.findViewById(R.id.lv_brandDetail_contentList);

		contentList.add(new Pair<BrandDetailContent.ViewHolder, View>(holder, addView));
        matchData(holder, bdeBean, addCarTypeModule, activity);
		addView(addView);
	}

	private OnItemClickListener itemClick;

	public void setOnItemClickListener(OnItemClickListener itemClick) {
		this.itemClick = itemClick;
	}

	                            /**
     * 匹配数据
     * 
     * @param holder
     * @param bdeBean
     */
    private void matchData(ViewHolder holder, BrandDetailEngineBean bdeBean, boolean addCarTypeModule, Activity activity)
    {
		holder.tv_brandDetail_contentTitle.setText(bdeBean.getTitle());
        BrandDetialAdapter bdAdapter = new BrandDetialAdapter(getContext(), bdeBean.getList(), activity);
		if (addCarTypeModule) {
			bdAdapter.selectToSelectType();
		}
		holder.lv_brandDetail_contentList.setAdapter(bdAdapter);
		holder.lv_brandDetail_contentList.setOnItemClickListener(itemClick);
		Utility.setListViewHeightBasedOnChildren(holder.lv_brandDetail_contentList);
	}

	public void notifyData() {
		if (contentList != null) {

			for (int i = 0; i < contentList.size(); i++) {
				Pair<ViewHolder, View> pair = contentList.get(i);
				if (pair.first.lv_brandDetail_contentList.getAdapter() != null) {
					BaseAdapter ba = (BaseAdapter) pair.first.lv_brandDetail_contentList.getAdapter();
					ba.notifyDataSetChanged();
				}
			}
		}
	}

	private class ViewHolder {
		TextView tv_brandDetail_contentTitle;
		ListView lv_brandDetail_contentList;
	}

}
