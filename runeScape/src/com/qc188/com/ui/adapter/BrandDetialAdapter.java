package com.qc188.com.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.CopyOfbrandDetailEngineItem;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.OpenUrl;
import com.qc188.com.util.SystemNotification;

public class BrandDetialAdapter extends FrameAdapter implements OnClickListener {

	private static final String TAG = "BrandDetialAdapter";
	private List<CopyOfbrandDetailEngineItem> list;
	private Context context;
    private Activity activity;

    public BrandDetialAdapter(Context context, List<CopyOfbrandDetailEngineItem> list, Activity activity)
    {
		this.list = list;
		this.context = context;
        this.activity = activity;
		setItemHeigth(DensityUtil.dip2px(85));

	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private boolean isSelectType = false;

	public void selectToSelectType() {
		isSelectType = true;
		setItemHeigth(DensityUtil.dip2px(78));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CopyOfbrandDetailEngineItem coeItem = list.get(position);
		if (isSelectType) {
			if (convertView == null) {

				convertView = View.inflate(context, R.layout.item_add_car_type_select, null);
			}
			TextView tv_brandDetail_content_name = (TextView) convertView.findViewById(R.id.tv_brandDetail_content_name);
			TextView tv_brandDetail_content_detail = (TextView) convertView.findViewById(R.id.tv_brandDetail_content_detail);
			TextView tv_brandDetail_content_sale = (TextView) convertView.findViewById(R.id.tv_brandDetail_content_sale);

			tv_brandDetail_content_name.setText(coeItem.getIntroduce());
			tv_brandDetail_content_detail.setText(coeItem.getDetail());
			tv_brandDetail_content_sale.setText(coeItem.getSale());
			convertView.setTag(R.id.tag_first, coeItem.getBrandId() + "");
			convertView.setTag(R.id.tag_second, coeItem.getIntroduce());
		} else {

			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.item_brand_list_content, null);
				holder = new ViewHolder(convertView, this);
				setLayoutParams(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}


			convertView.setTag(R.id.tag_fourth, coeItem.getBrandId());
			convertView.setTag(R.id.tag_third, coeItem.getIntroduce() + "");
            holder.matchData(coeItem, activity);

			setLayoutParams(convertView);
		}
		return convertView;
	}

	private static class ViewHolder {
		private BrandDetialAdapter bdAdapter;

		public ViewHolder(View view, BrandDetialAdapter bdAdapter) {
			this.bdAdapter = bdAdapter;
			tv_brandDetail_content_name = (TextView) view.findViewById(R.id.tv_brandDetail_content_name);
			tv_brandDetail_content_sale = (TextView) view.findViewById(R.id.tv_brandDetail_content_sale);
			tv_brandDetail_content_detail = (TextView) view.findViewById(R.id.tv_brandDetail_content_detail);
			bt_brandDetail_content_compair = (Button) view.findViewById(R.id.bt_brandDetail_content_compair);
			bt_brandDetail_content_addOne = (ImageView) view.findViewById(R.id.bt_brandDetail_content_addOne);
            bt_brandDetail_content_ask = (Button) view.findViewById(R.id.bt_brandDetail_content_ask);
		}

		TextView tv_brandDetail_content_name;
		TextView tv_brandDetail_content_sale;
		TextView tv_brandDetail_content_detail;
		Button bt_brandDetail_content_compair;
		ImageView bt_brandDetail_content_addOne;

        Button bt_brandDetail_content_ask;

        public void matchData(CopyOfbrandDetailEngineItem coeItem, Activity activity)
        {
			tv_brandDetail_content_name.setText(coeItem.getIntroduce());
			tv_brandDetail_content_sale.setText(coeItem.getSale());
			tv_brandDetail_content_detail.setText(coeItem.getDetail());

			bt_brandDetail_content_compair.setTag(R.id.tag_third, coeItem.getIntroduce() + "");
			if (CompairManager.getManger().inCompairMap(coeItem.getBrandId() + "")) {
				bt_brandDetail_content_compair.setEnabled(false);
                bt_brandDetail_content_compair.setText("已添加");
				bt_brandDetail_content_compair.setTextColor(0xFF65617A);
			} else {
				bt_brandDetail_content_compair.setEnabled(true);
                bt_brandDetail_content_compair.setText("+ 对比");
				bt_brandDetail_content_compair.setTextColor(0xFF395899);
				bt_brandDetail_content_compair.setTag(coeItem);
				bt_brandDetail_content_compair.setTag(R.id.tag_first, bt_brandDetail_content_addOne);
				bt_brandDetail_content_compair.setTag(R.id.tag_second, coeItem.getBrandId() + "");
				bt_brandDetail_content_compair.setOnClickListener(bdAdapter);
			}
			
            LogUtil.d(TAG, "coeItem:" + coeItem);
            bt_brandDetail_content_ask.setOnClickListener(new onGroupPlugClick(activity, "http://t.m.qc188.com/s" + coeItem.getBrandId() + "" + "/"));
		}

        private class onGroupPlugClick implements OnClickListener
        {
            private Activity activity;
            private String url;

            private onGroupPlugClick(Activity activity, String url)
            {
                this.activity = activity;
                this.url = url;
            }

            @Override
            public void onClick(View v)
            {
                new OpenUrl(activity, url);
            }

        }
    }


	@Override
	public void onClick(View v) {
		View addOne = (View) v.getTag(R.id.tag_first);
		String id = (String) v.getTag(R.id.tag_second);
		String introduce = (String) v.getTag(R.id.tag_third);
		if (CompairManager.getManger().putCompair(id, introduce)) {

			AnimationSet as = new AnimationSet(false);
			AlphaAnimation aa = new AlphaAnimation(1, 0);
			aa.setDuration(500);
			aa.setRepeatMode(Animation.REVERSE);
			ScaleAnimation sa = new ScaleAnimation(0.8f, 2f, 0.8f, 2f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
			sa.setDuration(aa.getDuration());
			sa.setRepeatMode(Animation.RESTART);
			as.addAnimation(aa);
			as.addAnimation(sa);
			as.setAnimationListener(new AddOneAnimationListener(addOne));
			addOne.startAnimation(as);

			notifyDataSetChanged();
		} else {
            SystemNotification.showToast(context, "最多9个车型");
		}
	}

	private class AddOneAnimationListener implements AnimationListener {

		private View view;

		public AddOneAnimationListener(View view) {
			this.view = view;
		}

		@Override
		public void onAnimationStart(Animation animation) {
			view.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view.clearAnimation();
			view.setVisibility(View.GONE);

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

	}
}
