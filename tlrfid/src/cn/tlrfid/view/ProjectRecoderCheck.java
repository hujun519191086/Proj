package cn.tlrfid.view;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.ProjectRecoder;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ProjectRecoderCheck extends BaseActivity {
	
	private ProjectRecoder recoder;
	@ViewById
	private TextView projectr_name;
	@ViewById_Clickthis
	private ListView curruculumvitae_lv;
	
	private String[] names;
	@ViewById
	private SeekBar seekbar;
	@ViewById
	private TextView seekbar_tv;
	@ViewById
	private TextView tv_currentTime;
	@ViewById
	private TextView person;
	@ViewById
	private TextView message;
	
	private DisplayImageOptions options;
	
	@ViewById
	private ImageView curriculumvitae_iv;
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
		seekbar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		initData();
		initView();
		initImageLoaderOption();
	}
	
	private void initImageLoaderOption() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		imageLoader.displayImage(ConstantValues.CLIENT_URL_PRE + recoder.picPath, curriculumvitae_iv, options,
				animateFirstListener);
		// imageLoader.displayImage("http://192.168.1.101:8080/spms/service/loadPersonPic.action?id=2001",
		// curriculumvitae_iv, options,
		// animateFirstListener);
	}
	
	private void initView() {
		projectr_name.setText(recoder.scheduleName);
		seekbar.setProgress(recoder.finishRate);
		seekbar_tv.setText(String.valueOf(recoder.finishRate));
		tv_currentTime.setText(recoder.inspectionTime);
		person.setText(recoder.inspectionPersonName);
		message.setText(recoder.remark);
		
		MyAdapter adapter = new MyAdapter(this, curruculumvitae_lv, R.dimen.project_recoder_check_lv_item_hight);
		curruculumvitae_lv.setAdapter(adapter);
	}
	
	private void initData() {
		Intent intent = getIntent();
		recoder = (ProjectRecoder) intent.getSerializableExtra("data");
		names = recoder.person.split("\\,");
		setActionBarTitle(recoder.scheduleName);
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.curriculumvitae_check);
	}
	
	private class MyAdapter extends FrameAdapter {
		
		public MyAdapter(Context context, AbsListView alv, int resId) {
			super(context, alv, resId);
		}
		
		public MyAdapter(Context context, AbsListView alv) {
			super(context, alv, 0);
		}
		
		@Override
		public int getCount() {
			return names == null ? 0 : names.length;
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_project_recoder);
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.item_create_name.setText(names[position]);
			holder.item_create_style.setText("");
			return convertView;
		}
		
	}
	
	static class ViewHolder extends BaseViewHolder {
		TextView item_create_name;
		TextView item_create_style;
	}
	
	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		
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
