package com.qc188.com.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.qc188.com.framwork.FrameAdapter;

public class Utility {
	private static final String TAG = "Utility";

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		if (listAdapter instanceof FrameAdapter) {
			totalHeight += ((FrameAdapter) listAdapter).getItemHeight()
					* listAdapter.getCount();
		} else {
			for (int i = 0; i < listAdapter.getCount(); i++) {
				// LogUtils.d(TAG, "height:" + listItem.getMeasuredHeight() +
				// "....."
				// + DensityUtil.dip2px(65));

				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}
		}

		LogUtil.d(TAG, "totalHeight:" + totalHeight);
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if (params == null) {
			params = new ViewGroup.LayoutParams(-1, 200);
		}
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount()));
		listView.setLayoutParams(params);

	}
}