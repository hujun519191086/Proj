package com.qc188.com.ui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qc188.com.R;
import com.qc188.com.bean.BrandPicBean;
import com.qc188.com.util.AsyncImageTask;
import com.qc188.com.util.BitmapUtils;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.LogUtil;

public class BrandDetail_PagerAdapter extends PagerAdapter {

	private static final String TAG = "BrandDetail_PagerAdapter";
	private List<BrandPicBean> bpbList;
	private Context context;
	private AsyncImageTask aitask;

	private int itemHeight = 0;

	public BrandDetail_PagerAdapter(Context context, List<BrandPicBean> bpbList) {
		this.bpbList = bpbList;
		this.context = context;
		aitask = new AsyncImageTask();
	}

	@Override
	public int getCount() {
		return bpbList.size();
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(arg0);
		BitmapUtils.recycleView(arg0);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public Object instantiateItem(View container, int position) {

		View view = View.inflate(context, R.layout.item_brand_detail_pager,
				null);
		ImageView iv_pager_picShow = (ImageView) view
				.findViewById(R.id.iv_pager_picShow);

		BrandPicBean bpb = bpbList.get(position);

		LogUtil.d(TAG, "brandDetail_pageUrl:" + bpb.getImage_url());
		ImageLoadUtil.loadImageFromDefault(bpb.getImage_url(),
				iv_pager_picShow, R.drawable.load_a);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				DensityUtil.getWidthPixels(), -1);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		iv_pager_picShow.setLayoutParams(params);

		((ViewPager) container).addView(view, 0);
		return view;

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
