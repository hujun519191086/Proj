package com.qc188.com.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.HomeADVBean;
import com.qc188.com.bean.MsgContentBean;
import com.qc188.com.engine.MainHomeEngine;
import com.qc188.com.ui.control.NoExceptionImageView;
import com.qc188.com.util.BitmapUtils;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class SliderAdapter extends PagerAdapter implements OnPageChangeListener, OnClickListener {

	private static final String TAG = "SliderAdapter";
	public List<HomeADVBean> mListViews;
	private Context context;
	private ViewPager vp;
	private MainHomeEngine homeEngine;
	private LinearLayout ll_home_advIndex;
	private WeakHashMap<String, Bitmap> map;

	public SliderAdapter(Context context, ViewPager vp, LinearLayout ll_home_advIndex) {
		this.context = context;
		map = new WeakHashMap<String, Bitmap>();
		this.vp = vp;
		vp.setOnPageChangeListener(this);
		this.ll_home_advIndex = ll_home_advIndex;
		homeEngine = InstanceFactory.getInstances(MainHomeEngine.class);
		new ADVAsync().execute();

	}

	public void putBitmap(String key, Bitmap bitmap) {
		map.put(key, bitmap);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(arg0);
		BitmapUtils.recycleView(arg0);
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		if (mListViews != null && mListViews.size() > 0) {
			return mListViews.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		final NoExceptionImageView iv = new NoExceptionImageView(context);
		final HomeADVBean bean = mListViews.get(arg1);

		ImageLoadUtil.loadImageFromDefault(bean.getImageUrl(), iv, R.drawable.load);

		iv.setOnClickListener(this);
		((ViewPager) arg0).addView(iv, 0);
		return iv;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	private ArrayList<TextView> circl_View;

	private TextView middleText;

	private class ADVAsync extends AsyncTask<Void, Void, List<HomeADVBean>> {

		private String TAG = "SliderAdapter";

		protected List<HomeADVBean> doInBackground(Void... params) {

			LogUtil.d(TAG, " getADV");
			return homeEngine.getHomeAdv_Async();
		}

		protected void onPostExecute(List<HomeADVBean> result) {
			LogUtil.d(TAG, "post:" + result);
			ll_home_advIndex.setVisibility(View.VISIBLE);
			if (result != null && result.size() > 0) {
				mListViews = result;
				circl_View = new ArrayList<TextView>();

				middleText = new TextView(context);
				middleText.setText("需要展示的信息");
				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, -1);
				p.weight = 1;
				middleText.setLayoutParams(p);
				middleText.setTextColor(Color.WHITE);
				middleText.setTextSize(17);
				middleText.setGravity(Gravity.CENTER_VERTICAL);
				ll_home_advIndex.addView(middleText, 0);

				for (int i = 0; i < getCount(); i++) {
					TextView tv = new TextView(context);// ll_home_advIndex
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5), DensityUtil.dip2px(5));
					params.setMargins(0, 0, DensityUtil.dip2px(5), 0);
					tv.setLayoutParams(params);
					tv.setBackgroundResource(R.drawable.whit_circel);
					circl_View.add(tv);
					ll_home_advIndex.addView(tv, i + 1);
				}
				notifyDataSetChanged();
				setSelectionCircel(0);
			} else {
				vp.setBackgroundResource(R.drawable.no_picture);
			}
		}
	}

	private void setSelectionCircel(int selection) {

		if (middleText != null) {
			currentHomeAdv = mListViews.get(selection);
			middleText.setText(mListViews.get(selection).getImageTitle());
		}
		for (int i = 0; i < circl_View.size(); i++) {
			circl_View.get(i).setBackgroundResource(R.drawable.whit_circel);
		}
		circl_View.get(selection).setBackgroundResource(R.drawable.black_circel);
	}

	@Override
	public String toString() {
		String url = currentHomeAdv.getDetail_content();

		return url;
	}

	public MsgContentBean getMsgContentBean() {
		String advUrl = toString();

		if (advUrl == null) {
			return null;
		}
		int index = advUrl.indexOf("id=");

		int endIndex = advUrl.indexOf("&", index);
		String msgID = advUrl.subSequence(index, endIndex).toString().split("=")[1];

		MsgContentBean mcBean = new MsgContentBean();
		mcBean.setFromAdv(true);
		mcBean.setDeital_title(currentHomeAdv.getImageTitle());
		mcBean.setDetail_content(advUrl);
		mcBean.setMsg_id(Integer.valueOf(msgID));
		mcBean.setTime(currentHomeAdv.getTime());
		mcBean.setFrom(currentHomeAdv.getFrom());
		mcBean.setName(currentHomeAdv.getName());
		mcBean.setIs_self(currentHomeAdv.getIs_self());
		mcBean.setPage_count(currentHomeAdv.getPage_count());
		mcBean.setIndex(currentHomeAdv.getIndex());

		return mcBean;
	}

	private HomeADVBean currentHomeAdv;

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setSelectionCircel(arg0);
	}

	@Override
	public void onClick(View v) {

	}
}
