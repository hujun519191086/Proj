package cn.tlrfid.view.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.QualityBean;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.StringUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class QualityListAdapter extends FrameAdapter {
	public List<QualityBean> mItemList;
	private AnimateFirstDisplayListener afdListener;
	private DisplayImageOptions diOptions;
	
	public QualityListAdapter(Context context, AbsListView alv, int resId, List<QualityBean> mItemList) {
		super(context, alv, resId);
		this.mItemList = mItemList;
		
		diOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		afdListener = new AnimateFirstDisplayListener();
	}
	
	@Override
	public int getCount() {
		return mItemList == null ? 0 : mItemList.size();
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		
		convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_curriculum_vitae_query);
		ViewHolder holder = (ViewHolder) convertView.getTag();
		QualityBean recoder = mItemList.get(position);
		holder.time.setText("时间：" + recoder.getCheckTime());
		if (StringUtils.isEmpty(recoder.getCheckPerson())) {
			holder.name.setText("名称：未知");	
		} else {
			holder.name.setText("巡检人：" + recoder.getCheckPerson());
		}
		if (StringUtils.isEmpty(recoder.getPerson())) {
			holder.project_name.setText("未知");
		} else {
			holder.project_name.setText(recoder.getPerson());
		}
		ImageLoader.getInstance().displayImage(ConstantValues.CLIENT_URL_PRE + recoder.getPicPath(),
				holder.item_curriculum_vitae_query_iv_show, diOptions, afdListener);
		
		ImageLoader.getInstance().displayImage(ConstantValues.CLIENT_URL_PRE + recoder.getPicPath(),
				holder.item_curriculum_vitae_query_iv_show);
		return convertView;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	private static class ViewHolder extends BaseViewHolder {
		ImageView item_curriculum_vitae_query_iv_show;
		TextView item_curriculum_vitae_query_tv_show;
		TextView project_name;
		TextView name;
		TextView time;
	}
	
	private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
		public final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
