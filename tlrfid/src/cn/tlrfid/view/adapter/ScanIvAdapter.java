package cn.tlrfid.view.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.view.CurriculumVitae_Create_Activity;
import cn.tlrfid.view.QualityCreateActivity;
import cn.tlrfid.view.SafetyCreateActivity;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ScanIvAdapter extends FrameAdapter {
	private Context mContext;
	
	public List<PersonCardBean> list;
	
	protected ImageLoader imageLoader;
	
	private DisplayImageOptions options;
	
	private ImageLoadingListener animateFirstListener;
	
	public ScanIvAdapter(Context context, List<PersonCardBean> list) {
		super(context, null, 0);
		this.mContext = context;
		imageLoader = ImageLoader.getInstance();
		
		animateFirstListener = new AnimateFirstDisplayListener();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		this.list = list;
		
		// list = AddDataMachine.add(PersonCardBean.class, 50);
	}
	
	public List<PersonCardBean> getListData() {
		return list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		
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
	
	@Override
	public View getItemView(final int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_scan_iv, null);
		
		String picPath = list.get(position).getPicPath();
		
		TextView tv_str = (TextView) view.findViewById(R.id.tv_str);
		
		tv_str.setText(list.get(position).getPersonName());
		
		ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
		
		imageLoader.displayImage(ConstantValues.CLIENT_URL_PRE + picPath, iv_photo, options, animateFirstListener);
		/*
		 * CheckBox ib_item_select = (CheckBox) view.findViewById(R.id.ib_item_select);
		 * ib_item_select.setVisibility(View.VISIBLE); ib_item_select.setTag(list.get(position));
		 * ib_item_select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { PersonCardBean pcb =
		 * (PersonCardBean) buttonView.getTag(); pcb.setChecked(isChecked); } });
		 */
		
		view.findViewById(R.id.ib_item_delete).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// list.remove(position);
				if (context instanceof CurriculumVitae_Create_Activity) {
					
					if (((CurriculumVitae_Create_Activity) context).state == 1) {
						((CurriculumVitae_Create_Activity) context).list_project.remove(position);
						((CurriculumVitae_Create_Activity) context).adapter.notifyDataSetChanged();
						
						if (((CurriculumVitae_Create_Activity) context).list_project.size() != 0) {
							((CurriculumVitae_Create_Activity) context).tv_project
									.setText(((CurriculumVitae_Create_Activity) context).list_project.get(
											((CurriculumVitae_Create_Activity) context).list_project.size() - 1)
											.getName());
						} else {
							((CurriculumVitae_Create_Activity) context).list_project.clear();
							((CurriculumVitae_Create_Activity) context).tv_project.setText("");
						}
						
					}
					
					if (((CurriculumVitae_Create_Activity) context).state == 2) {
						((CurriculumVitae_Create_Activity) context).list_person.remove(position);
						((CurriculumVitae_Create_Activity) context).adapter.notifyDataSetChanged();
						
						if (((CurriculumVitae_Create_Activity) context).list_person.size() == 0) {
							((CurriculumVitae_Create_Activity) context).list_person.clear();
						}
						
					}
					if (((CurriculumVitae_Create_Activity) context).state == 3) {
						((CurriculumVitae_Create_Activity) context).list_check_person.remove(position);
						((CurriculumVitae_Create_Activity) context).siAdapter_check.notifyDataSetChanged();
						
						if (((CurriculumVitae_Create_Activity) context).list_check_person.size() != 0) {
							((CurriculumVitae_Create_Activity) context).tv_check_person
									.setText(((CurriculumVitae_Create_Activity) context).list_check_person.get(
											((CurriculumVitae_Create_Activity) context).list_check_person.size() - 1)
											.getPersonName());
						} else {
							((CurriculumVitae_Create_Activity) context).list_check_person.clear();
							((CurriculumVitae_Create_Activity) context).tv_check_person.setText("");
						}
					}
					ScanIvAdapter.this.notifyDataSetChanged();
				}
				if (context instanceof QualityCreateActivity) {
					
					if (((QualityCreateActivity) context).state == 2) {
						((QualityCreateActivity) context).list_check_person.remove(position);
						((QualityCreateActivity) context).siAdapter_check.notifyDataSetChanged();
						if (((QualityCreateActivity) context).list_check_person.size() != 0) {
							((QualityCreateActivity) context).tv_check_person
									.setText(((QualityCreateActivity) context).list_check_person.get(
											((QualityCreateActivity) context).list_check_person.size() - 1)
											.getPersonName());
						} else {
							((QualityCreateActivity) context).list_check_person.clear();
							((QualityCreateActivity) context).tv_check_person.setText("");
						}
					}
					ScanIvAdapter.this.notifyDataSetChanged();
				}
				if (context instanceof SafetyCreateActivity) {
					
					if (((SafetyCreateActivity) context).state == 2) {
						((SafetyCreateActivity) context).list_check_person.remove(position);
						((SafetyCreateActivity) context).siAdapter_check.notifyDataSetChanged();
						if (((SafetyCreateActivity) context).list_check_person.size() != 0) {
							((SafetyCreateActivity) context).tv_check_person
									.setText(((SafetyCreateActivity) context).list_check_person.get(
											((SafetyCreateActivity) context).list_check_person.size() - 1)
											.getPersonName());
						} else {
							((SafetyCreateActivity) context).list_check_person.clear();
							((SafetyCreateActivity) context).tv_check_person.setText("");
						}
					}
					ScanIvAdapter.this.notifyDataSetChanged();
				}
			}
		});
		
		/*
		 * ImageButton ib_item_delete = (ImageButton) view.findViewById(R.id.ib_item_delete);
		 * ib_item_delete.setTag(position); ib_item_delete.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { int position = (Integer) v.getTag(); list.remove(position);
		 * CurriculumVitae_Create_Activity.list_person.remove(position); notifyDataSetChanged(); } });
		 */
		
		return view;
	}
}
