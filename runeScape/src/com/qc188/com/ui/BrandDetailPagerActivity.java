package com.qc188.com.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandPicBean;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.ui.adapter.BrandDetail_PagerAdapter;
import com.qc188.com.util.SystemNotification;

public class BrandDetailPagerActivity extends BaseActivity {

	private ViewPager vp_brandDetail_pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand_detail_pic_pager);

		visibleBackButton();
		@SuppressWarnings("unchecked")
		ArrayList<BrandPicBean> bpbList = (ArrayList<BrandPicBean>) getIntent().getSerializableExtra("brandDetail");

		TextView tv_brandDetail_msg = (TextView) findViewById(R.id.tv_brandDetail_msg);

		int position = (Integer) getIntent().getSerializableExtra("position");

		vp_brandDetail_pager = (ViewPager) findViewById(R.id.vp_brandDetail_pager);

		if (bpbList != null) {

			tv_brandDetail_msg.setText(bpbList.get(0).getIntroduce());
			BrandDetail_PagerAdapter bdpadapter = new BrandDetail_PagerAdapter(getApplicationContext(), bpbList);
			vp_brandDetail_pager.setAdapter(bdpadapter);
			vp_brandDetail_pager.setCurrentItem(position, false);
		} else {
			SystemNotification.showToast(getApplicationContext(), "数据有误，3秒后退出该界面！");
			postDelay(new Runnable() {

				public void run() {
					finish();
				}
			}, 3000);
		}
	}
}
