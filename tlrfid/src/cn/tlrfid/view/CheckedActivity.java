package cn.tlrfid.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.bean.PollingResult;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.bean.SafetyItemBean;
import cn.tlrfid.bean.SecurityCheckEntry;
import cn.tlrfid.bean.SelfProjectRecoder;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.view.adapter.PollingResultAdapter;
import cn.tlrfid.view.adapter.PollingResultAdapter.ChangeData;
import cn.tlrfid.view.adapter.ScanAdapter;
import cn.tlrfid.view.adapter.ScanIvAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 新建安全检查
 * 
 * @author sk
 * 
 */
public class CheckedActivity extends BaseActivity implements ChangeData {
	
	@ViewById
	private ListView polling_lv;
	
	private ArrayList<SafetyItemBean> list_item;
	
	private PollingResult pr;
	
	@ViewById_Clickthis
	private Button bt_camera;
	
	@ViewById_Clickthis
	private Button bt_scan_project;
	
	private ArrayList<String> mEpcList;
	
	private ScanIvAdapter siAdapter_check;
	
	private ScanAdapter sAdapter;
	
	private PollingResultAdapter adapter_item;
	
	@ViewById_Clickthis
	private Button bt_scan_person;
	
	private List<ProjectCardBean> list_project = new ArrayList();
	
	private List<PersonCardBean> list_check_person = new ArrayList();
	
	@ViewById
	private ImageView polling_iv;
	
	@ViewById
	private TextView tv_check_person;
	
	@ViewById
	private TextView tv_project;
	
	private SelfProjectRecoder recoder;
	
	@ViewById
	private EditText limit_time;
	@ViewById
	private EditText remark;
	@ViewById
	private TextView time;
	
	private List<SecurityCheckEntry> mSecurityList;
	
	@Override
	public void init() {
		initData();
		initView();
		initImageLoaderOption();
	}
	
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private void initImageLoaderOption() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		imageLoader.displayImage(ConstantValues.CLIENT_URL_PRE + recoder.picPath, polling_iv, options,
				animateFirstListener);
		
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
	
	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
	
	private void initView() {
		
		tv_project.setText(recoder.scheduleName);
		tv_check_person.setText(recoder.checkPersonName);
		limit_time.setText(recoder.overhaulLimit + "天");
		remark.setText(recoder.remark);
		time.setText(recoder.checkTime.replace("T", "  "));
	}
	
	private void initData() {
		recoder = (SelfProjectRecoder) getIntent().getSerializableExtra("data");
		if (recoder != null) {
			mSecurityList = recoder.mSecurityList;
		} else {
			recoder = new SelfProjectRecoder();
		}
		
		MyAdapter adapter = new MyAdapter(this, polling_lv, R.dimen.main_content_marginRight);
		polling_lv.setAdapter(adapter);
		
	}
	
	private class MyAdapter extends FrameAdapter {
		
		public MyAdapter(Context context, AbsListView alv, int resId) {
			super(context, alv, resId);
		}
		
		@Override
		public int getCount() {
			return mSecurityList == null ? 0 : mSecurityList.size();
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_polling_create);
			ViewHolder holder = (ViewHolder) convertView.getTag();
			SecurityCheckEntry checkEntry = mSecurityList.get(position);
			if (StringUtils.isEmpty(checkEntry.levelName)) {
				holder.item_polling_create_name.setText(checkEntry.title);
			} else {
				holder.item_polling_create_name.setText(checkEntry.title + "[" + checkEntry.levelName + "]");
			}
			
			if (checkEntry.result == 0) {
				// 不合格
				holder.item_polling_create_ismistake.setChecked(true);
				holder.item_polling_create_isqualify.setChecked(false);
			} else {
				// 合格
				holder.item_polling_create_ismistake.setChecked(false);
				holder.item_polling_create_isqualify.setChecked(true);
			}
			
			holder.item_polling_create_ismistake.setFocusable(false);
			holder.item_polling_create_isqualify.setFocusable(false);
			return convertView;
		}
		
	}
	
	static class ViewHolder extends BaseViewHolder {
		TextView item_polling_create_name;
		CheckBox item_polling_create_isqualify;
		CheckBox item_polling_create_ismistake;
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.check);
		
		title_bar_content.setText(getIntent().getStringExtra(ConstantValues.ACTION_BAR_NAME));
	}
	
	@Override
	public void change(ArrayList<SafetyItemBean> mList) {
		
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
}
