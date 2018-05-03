package com.qc188.com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.util.AsyncImageTask;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.LogUtil;

public class SearchContentAdapter extends FrameAdapter {
	private static final String TAG = "SearchContentAdapter";
	private Context context;
	private ArrayList<SearchContentBean> result;
	private AsyncImageTask imageTask = new AsyncImageTask();

	public SearchContentAdapter(Context context, ArrayList<SearchContentBean> result, ListView lv) {
		this.context = context;
		setItemHeigth(DensityUtil.dip2px(65));
		this.result = result;
	}

	@Override
	public int getCount() {
		if (result == null) {
			return 0;
		}
		return result.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_search_content, null);
			setLayoutParams(convertView);
		}

		LogUtil.d(TAG, "getview  times:" + position);
		ImageView iv_searchContent_icon = (ImageView) convertView.findViewById(R.id.iv_searchContent_icon);
		TextView tv_searchContent_name = (TextView) convertView.findViewById(R.id.tv_searchContent_name);
		TextView tv_searchContent_sale = (TextView) convertView.findViewById(R.id.tv_searchContent_sale);
		SearchContentBean scb = result.get(position);
		tv_searchContent_name.setText(scb.getCar_name());
		tv_searchContent_sale.setText(scb.getCar_price());

		ImageLoadUtil.loadImageFromDefault(scb.getCar_imageUrl(), iv_searchContent_icon, ConstantValues.DEFAULT_DRAWABLE_A);
		// Drawable drawable = imageTask.loadImage(iv_searchContent_icon,
		// scb.getCar_imageUrl(), DensityUtil.dip2px(40),
		// DensityUtil.dip2px(40),
		// new ImageCallback() {
		// @Override
		// public void imageLoaded(Drawable image, Object id) {
		// if (image != null) {
		// ((ImageView) id).setImageDrawable(image);
		// // ImageView mainBg = (ImageView) lv
		// // .findViewWithTag(id);
		// // if (mainBg != null) {
		// // putBitmap(id + "", BitmapUtils
		// // .convertDrawable2BitmapByCanvas(image));
		// // mainBg.setImageDrawable(image);
		// // }
		// }
		// }
		// });
		// if (drawable != null) {
		// iv_searchContent_icon.setImageDrawable(drawable);
		// } else {
		// iv_searchContent_icon.setImageResource(ConstantValues.DEFAULT_DRAWABLE_A);
		// }

		// ImageLoadUtil.loadImageFromDefault(scb.getCar_imageUrl(),
		// iv_searchContent_icon, ConstantValues.DEFAULT_DRAWABLE_A);

		convertView.setTag(R.id.activity_tag, scb);
		return convertView;
	}

}
