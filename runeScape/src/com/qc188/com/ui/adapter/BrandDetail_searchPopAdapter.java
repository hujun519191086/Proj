package com.qc188.com.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.util.DensityUtil;

public class BrandDetail_searchPopAdapter extends FrameAdapter {

	private static final String TAG = "BrandDetail_searchPopAdapter";
	private Context mContext;
	private List<String> contentList;

	public BrandDetail_searchPopAdapter(Context context, List<String> tempList) {
		mContext = context;
		contentList = tempList;
		setItemHeigth(DensityUtil.dip2px(45));
	}

	private int selectPosition = 0;

	public void setSlectPosition(int position) {
		selectPosition = position;
		// notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (contentList != null) {
			return contentList.size();
		}
		return 0;
	}

	private List<String> mColorList;

	public void setColorList(List<String> colorList) {
		mColorList = colorList;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			convertView = View.inflate(mContext, R.layout.item_branddetail_search, null);

			setLayoutParams(convertView);
			if (mColorList != null) {
				convertView.findViewById(R.id.iv_brandDetail_searchItemImage).setVisibility(View.VISIBLE);
			}
		}

		if (selectPosition == position) {
			convertView.findViewById(R.id.view_brandDetail_selectLeft).setVisibility(View.VISIBLE);
			convertView.setBackgroundColor(0xFFF2F2F2);
		} else {
			convertView.findViewById(R.id.view_brandDetail_selectLeft).setVisibility(View.INVISIBLE);
			convertView.setBackgroundColor(Color.WHITE);
		}

		// F2F2F2 selectColor
		TextView iv_brandDetail_searchItemText = (TextView) convertView.findViewById(R.id.iv_brandDetail_searchItemText);
		iv_brandDetail_searchItemText.setText(contentList.get(position));

		if (mColorList != null) {
			ImageView iv_brandDetail_searchItemImage = (ImageView) convertView.findViewById(R.id.iv_brandDetail_searchItemImage);

			if (position != 0) {

				// LogUtil.d(TAG, "toHexStr:" +
				// Integer.toHexString(Long.valueOf("FF" +
				// mColorList.get(position), 16).intValue()));

				iv_brandDetail_searchItemImage.setImageDrawable(new ColorDrawable(Long.valueOf("FF" + mColorList.get(position), 16).intValue()));
			}
		}
		return convertView;
	}
}
