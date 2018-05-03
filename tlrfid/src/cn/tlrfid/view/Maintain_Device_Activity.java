package cn.tlrfid.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.warehouse.DeviceDetailBean;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.RFIDUtils;
import cn.tlrfid.utils.ReadCardSche;
import cn.tlrfid.utils.SystemNotification;

import com.cetc7.UHFReader.UHFReaderClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 设备维护
 * 
 * @author sk
 * 
 */
public class Maintain_Device_Activity extends BaseActivity {
	
	protected static final String TAG = "Maintain_Device_Activity";
	
	@ViewById_Clickthis
	private Button bt_camera;
	
	@ViewById_Clickthis
	private Button bt_scan_project;
	
	private ArrayList<String> mEpcList;
	
	public static List<String> list_code = new ArrayList();
	
	@ViewById
	private ImageView maintain_iv;
	
	@ViewById
	public static TextView tv_project;
	
	@ViewById
	public TextView tv_zichan_no; // 资产编号
	
	@ViewById
	public TextView tv_zichan_style; // 资产分类
	
	@ViewById
	public TextView tv_zichan_name; // 资产名称
	
	@ViewById
	public TextView tv_factory;// 厂家
	
	@ViewById
	public TextView tv_style_no; // 型号
	
	@ViewById
	public TextView tv_buy_end_time;// 购置期限
	
	@ViewById
	public TextView tv_use_end_time; // 使用期限
	
	@ViewById
	public TextView tv_maintain_style;// 养护类型
	
	@ViewById
	public TextView tv_maintain_period; // 养护周期
	
	@ViewById
	public TextView tv_maintain_last_time; // 上次养护时间
	
	@ViewById
	public TextView tv_maintain_next_time; // 下次养护时间
	
	@ViewById
	public TextView tv_person; // 责任人
	
	private Uri mUri;
	
	private String photo_url; // 图片路径
	
	private Handler mHandler = new Handler() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Log.e("asd", "1里面");
				mEpcList = (ArrayList<String>) msg.obj;
				if (mEpcList != null && mEpcList.size() > 0) {
					System.out.println(mEpcList.get(0));
					LogUtil.d(TAG, "mEpcList:" + mEpcList);
					RFIDUtils.getInstance(Maintain_Device_Activity.this).stopReadEPCs();
					// SystemNotification.showToast(getApplicationContext(), "已关闭");
					AlertDialogUtil.dissmissLoading();
					getData(mEpcList.get(mEpcList.size() - 1));
					
				} else {
					
					RFIDUtils.getInstance(Maintain_Device_Activity.this).stopReadEPCs();
					AlertDialogUtil.dissmissLoading();
					SystemNotification.showToast(getApplicationContext(), "数据库无此数据");
				}
				
				break;
			}
			
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.bt_scan_project:
			AlertDialogUtil.showLoading(Maintain_Device_Activity.this, "正在扫描...");
			ReadCardSche.getInstance().clearMap();
			
			if (!ConstantValues.rfid_state) {
				
				// 开始扫描
				RFIDUtils.getInstance(Maintain_Device_Activity.this).startReadEPCs(listen);
				ConstantValues.rfid_state = true;
				
			} else {
				
				RFIDUtils.getInstance(Maintain_Device_Activity.this).stopReadEPCs();
				RFIDUtils.getInstance(Maintain_Device_Activity.this).startReadEPCs(listen);
			}
			break;
		
		}
	}
	
	private void getData(final String tagcode) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// "http://192.168.1.101:8080/spms/service/queryByTagCode.action?tagCode=FE0000000000000000000044";
				UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
				final DeviceDetailBean ddb = ue.getUrlBean(ConstantValues.CLIENT_URL_PRE
						+ "/service/queryByTagCode.action?tagCode=" + tagcode, DeviceDetailBean.class);
				// http://192.168.1.101:8080/spms/service/queryByTagCode.action?tagCode=FE0000000000000000000005
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						if (ddb != null) {
							photo_url = ddb.getPicPath();
							
							tv_zichan_no.setText(ddb.getAssetsCode());
							
							tv_zichan_style.setText(ddb.getAssetCategoryName());
							
							tv_zichan_name.setText(ddb.getAssetNameName());
							
							tv_factory.setText(ddb.getFactoryName());
							
							tv_style_no.setText(ddb.getModelName());
							
							tv_buy_end_time.setText(ddb.getPurchaseDate());// 购置期限
							
							tv_use_end_time.setText(ddb.getUsefulLife()); // 使用期限
							
							tv_maintain_style.setText(ddb.getConserveTypeName());// 养护类型
							
							tv_maintain_period.setText(ddb.getConservePeriod()); // 养护周期
							
							tv_maintain_last_time.setText(ddb.getLastConserveDate()); // 上次养护时间
							
							tv_maintain_next_time.setText(ddb.getNextConserveDate()); // 下次养护时间
							
							if (ddb.getPersonName() == null) {
								tv_person.setText("");
							} else {
								tv_person.setText(Html.fromHtml("<u>" + ddb.getPersonName() + "</u>"));
							}
							initImageLoaderOption();
							
						} else {
							
							SystemNotification.showToast(getApplicationContext(), "数据库无此数据");
						}
					}
				});
				
			}
		}).start();
		
	}
	
	@Override
	public void init() {
		
		Intent intent = getIntent();
		String tagcode = intent.getStringExtra("assentCode");
		
		if (!TextUtils.isEmpty(tagcode)) {
			getData(tagcode);
		}
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.maintain_device);
		
		title_bar_content.setText("设备维护");
		
	}
	
	UHFReaderClass.OnEPCsListener listen = new UHFReaderClass.OnEPCsListener() {
		// 对接收到EPC数据的处理
		// 由于onEPCsRecv是被封装在UHFReaderClass类的非UI线程调用的
		// 要实现把数据实时刷到界面，需通过Handle交回给UI线程处理。
		public void onEPCsRecv(ArrayList<String> EPCList) {
			
			mHandler.sendMessage(Message.obtain(mHandler, 1, EPCList));
			
		}
	};
	
	@Override
	public void onBackPressed() {
		
		ConstantValues.rfid_state = false;
		super.onBackPressed();
	}
	
	@Override
	public void finish() {
		
		super.finish();
	}
	
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private void initImageLoaderOption() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		imageLoader.displayImage(ConstantValues.CLIENT_URL_PRE + photo_url, maintain_iv, options, animateFirstListener);
		
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
