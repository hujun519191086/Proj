package com.qc188.com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.HomeItemBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.util.AsyncImageTask;
import com.qc188.com.util.AsyncImageTask.ImageCallback;
import com.qc188.com.util.BitmapUtils;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;

public class HomeAdapter extends FrameAdapter {

	private static final String TAG = "HomeAdapter";
	private ArrayList<HomeItemBean> list;
	private Context context;
	private AsyncImageTask imageTask;
	private ListView lv;

	public HomeAdapter(Context context, ArrayList<HomeItemBean> list,
			ListView lv) {
		this.list = list;
		this.context = context;
		setItemHeigth(DensityUtil.dip2px(80));
		imageTask = new AsyncImageTask();
		lv.setDivider(context.getResources().getDrawable(R.color.dividerColor));
		lv.setDividerHeight(2);
		this.lv = lv;

	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	private int type = 0;

	public void changeType(int type) {
		if (this.type != type) {

		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.home_listview_item,
					null);
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1,
					getItemHeight());
			convertView.setLayoutParams(params);
			convertView.setBackgroundColor(Color.WHITE);
		}
		ImageView iv_home_item_image = (ImageView) convertView
				.findViewById(R.id.iv_home_item_image);
		TextView tv_home_item_title = (TextView) convertView
				.findViewById(R.id.tv_home_item_title);
		TextView tv_home_item_time = (TextView) convertView
				.findViewById(R.id.tv_home_item_time);
		// TextView tv_home_item_comments = (TextView) convertView
		// .findViewById(R.id.tv_home_item_comments);
		iv_home_item_image.setTag(position);
		HomeItemBean hib = list.get(position);
		iv_home_item_image.setTag(R.id.tag_first, hib.getCar_imageUrl());
		Drawable drawable = imageTask.loadImage(position,
				hib.getCar_imageUrl(), DensityUtil.dip2px(30),
				DensityUtil.dip2px(30), new ImageCallback() {
					@Override
					public void imageLoaded(Drawable image, Object id) {
						if (image != null) {
							ImageView mainBg = (ImageView) lv
									.findViewWithTag(id);
							LogUtil.d(TAG, "imageView = " + mainBg + ",,,,id:"
									+ id + "....");
							if (mainBg != null) {

								putBitmap("" + id, BitmapUtils
										.convertDrawable2BitmapByCanvas(image));
								mainBg.setBackgroundDrawable(image);
							}
						}
					}
				});

		if (drawable != null) {
			iv_home_item_image.setBackgroundDrawable(drawable);
		} else {
			iv_home_item_image
					.setBackgroundResource(ConstantValues.DEFAULT_DRAWABLE_A);
		}
		tv_home_item_title.setText(hib.getMsg_title());
		tv_home_item_time.setText(hib.getTime());
		// tv_home_item_comments.setText(hib.getMsg_comments() + "条评论");
		convertView.setTag(hib.getMsg_id());
		return convertView;
	}
}
