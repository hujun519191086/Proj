package com.qc188.com.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qc188.com.R;
import com.qc188.com.bean.BrandPicBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.util.AsyncImageTask;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;

public class BrandDetail_PicAdapter extends FrameAdapter {

	private List<BrandPicBean> picList;
	private Context context;
	private AsyncImageTask imageTask = new AsyncImageTask();

    public BrandDetail_PicAdapter(Context context)
    {
		this.context = context;
		setItemHeigth(DensityUtil.dip2px(70));
	}

	@Override
	public int getCount() {
		if (picList == null)
			return 0;
		return picList.size();
	}

	public void setList(List<BrandPicBean> picList) {
		this.picList = picList;
		notifyDataSetChanged();
	}

	public List<BrandPicBean> getList() {
		return picList;
	}

	public void addList(List<BrandPicBean> picList) {
		if (this.picList == null) {
			this.picList = picList;
		} else {
			this.picList.addAll(picList);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = View.inflate(context, R.layout.item_brand_detail_pic,
					null);
			setLayoutParams(convertView);
		}

		ImageView tempImageView = (ImageView) convertView
				.findViewById(R.id.iv_brand_detail_pic_item);

		ImageLoadUtil.loadImage(picList.get(position).getSmall_image_url(),
				tempImageView, ConstantValues.DEFAULT_DRAWABLE_A,
				DensityUtil.dip2px(40), DensityUtil.dip2px(40));
		// Drawable drawable = imageTask.loadImage(tempImageView,
		// picList.get(position).getSmall_image_url(),
		// DensityUtil.dip2px(30), DensityUtil.dip2px(30),
		// new ImageCallback() {
		// @Override
		// public void imageLoaded(Drawable image, Object id) {
		// if (image != null) {
		// ((ImageView) id).setImageDrawable(image);
		// }
		// }
		// });
		// if (drawable != null) {
		// tempImageView.setImageDrawable(drawable);// 13698409915
		// // 12-03 09:20:38.388: D/BrandDetail_Pic(1858):
		// //
		// url:http://www.qc188.com/app/photo.asp?id=1289&tutype=1&color=1B1C17&carList=0&page=1
		//
		// } else {
		// tempImageView.setImageResource(R.drawable.load_a);
		// }

        // 将list传给viewpager
		convertView.setTag(picList);
		return convertView;
	}
}
