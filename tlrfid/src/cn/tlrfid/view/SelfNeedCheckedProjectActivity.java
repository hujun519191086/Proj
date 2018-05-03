package cn.tlrfid.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequestPost;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.bean.PollingResult;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.bean.SafetyItemBean;
import cn.tlrfid.bean.SecurityCheckEntry;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
import cn.tlrfid.bean.SelfProjectRecoder;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.utils.CardNetConnection;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.adapter.ScanAdapter;
import cn.tlrfid.view.adapter.ScanIvAdapter;

import com.alibaba.fastjson.JSON;
import com.cetc7.UHFReader.UHFReaderClass;
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
public class SelfNeedCheckedProjectActivity extends BaseActivity {
	
	@ViewById
	private ListView polling_lv;
	
	private ArrayList<SafetyItemBean> list_item;
	
	private PollingResult pr;
	
	@ViewById_Clickthis
	private Button bt_camera;
	
	@ViewById_Clickthis
	private Button bt_scan_project;
	
	private ArrayList<String> mEpcList;
	
	@ViewById_Clickthis
	private Button bt_scan_person;
	
	private List<ProjectCardBean> list_project = new ArrayList();
	
	private List<PersonCardBean> list_check_person = new ArrayList();
	
	@ViewById
	private ImageView safety_iv;
	
	@ViewById
	private TextView tv_check_person;
	
	@ViewById
	private ImageView polling_iv;
	
	@ViewById
	private TextView tv_project;
	
	@ViewById
	private EditText et_remark;
	
	@ViewById
	private EditText et_overhaulLimit;
	
	@ViewById_Clickthis
	private Button bt_request_photo;
	@ViewById_Clickthis
	private Button bt_safe_create;
	
	private int state = 1;
	
	private Uri mUri;
	
	@ViewById
	private TextView tv_time;
	
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
				}
				
				mScan.setndCard(SelfNeedCheckedProjectActivity.this, mEpcList, ConstantValues.QUERYPROJECT,
						ProjectCardBean.class);
				
				break;
			case 2:
				Log.e("asd", "3里面");
				mEpcList = (ArrayList<String>) msg.obj;
				if (mEpcList != null && mEpcList.size() > 0) {
					System.out.println(mEpcList.get(0));
				}
				
				mScan.setndCard(SelfNeedCheckedProjectActivity.this, mEpcList, ConstantValues.QUERYPERSON,
						PersonCardBean.class);
				
				break;
			
			}
			
		}
	};
	
	@Override
	public void onClick(View v) {
		View view = View.inflate(this, R.layout.card_num_show_dialog, null);
		switch (v.getId()) {
		
		case R.id.bt_camera:
			
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			File appDir = new File(Environment.getExternalStorageDirectory() + "/KengDieA");
			if (!appDir.exists()) {
				appDir.mkdir();
			}
			mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/KengDieA/", "kengDiePic"
					+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
			cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
			try {
				cameraIntent.putExtra("return-data", true);
				startActivityForResult(cameraIntent, 168);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			break;
		
		case R.id.bt_request_photo:
			
			AlertDialogUtil.showLoading(SelfNeedCheckedProjectActivity.this, "正在上传...");
			
			new AsyncTask<String[], Integer, String>() {
				
				@Override
				protected String doInBackground(String[]... params) {
					try {
						photo_url = BitmapUtil.sendData(BitmapUtil.drawableToBitmap(safety_iv.getDrawable()),
								System.currentTimeMillis() + "");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return photo_url;
				}
				
				@Override
				protected void onPostExecute(String result) {
					AlertDialogUtil.dissmissLoading();
					
					// if (TextUtils.isEmpty(result)) {
					// Toast.makeText(getApplicationContext(), "数据位空！！！", 0).show();
					// return;
					// }
					//
					// System.out.println(result);
					if (!StringUtils.isEmpty(result)) {
						try {
							JSONObject json = new JSONObject(result);
							if (json.getInt("state") == 1) {
								photo_url = json.getString("filePath").replace("\\/", "");
								SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, "上传成功！！！");
							} else {
								SystemNotification.showToast(SelfNeedCheckedProjectActivity.this,
										json.getString("errMessage"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					} else {
						SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, "上传失败！！！");
					}
					
					super.onPostExecute(result);
				}
				
			}.execute(new String[] {});
			break;
		
		case R.id.bt_scan_project:
			state = 1;
			
			sAdapter = new ScanAdapter(this, list_project);
			
			new MyAlertDialogShow(SelfNeedCheckedProjectActivity.this, view, sAdapter, "巡检项目", listen).show();
			break;
		case R.id.bt_scan_person:
			
			state = 2;
			siAdapter_check = new ScanIvAdapter(this, list_check_person);
			
			new MyAlertDialogShow(SelfNeedCheckedProjectActivity.this, view, siAdapter_check, "巡检人员", listen).show();
			break;
		
		// 创建
		case R.id.bt_safe_create:
			
			// if (list_check_person.size() == 0) {
			//
			// Toast.makeText(getApplicationContext(), "巡检人不能为空", 0).show();
			//
			// return;
			//
			// }
			//
			//
			// if (list_project.size() == 0) {
			// Toast.makeText(getApplicationContext(), "进度计划不能为空", 0).show();
			// return;
			// }
			// if (TextUtils.isEmpty(list_project.get(0).getId() + "")) {
			// Toast.makeText(getApplicationContext(), "进度计划不能为空", 0).show();
			// return;
			//
			// }
			
			if (TextUtils.isEmpty(photo_url)) {
				Toast.makeText(getApplicationContext(), "请先拍照上传照片", 0).show();
				return;
			}
			
			AlertDialogUtil.showLoading(SelfNeedCheckedProjectActivity.this, "正在写入...");
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			// record . overhaulLimit:整改期限
			// record . checkPerson:巡检人ID
			// record .checkTime:巡检时间
			// record .picPath:图片路径
			// record .Remark:备注
			// record .scheduleId:进度计划ID
			// 质量检查条目数组:
			// entryList[i]. entryId:条目ID
			// entryList[i]. result:结果,合格1,不合格0
			// 其中i从0开始
			
			for (int i = 0; i < list_item.size(); i++) {
				map.put("checkResultList[" + i + "].entryId", list_item.get(i).getId() + "");
				if (list_item.get(i).isQualify()) {
					// 合格
					map.put("checkResultList[" + i + "].result", "1");
				} else {
					// 不合格
					map.put("checkResultList[" + i + "].result", "0");
				}
			}
			
			map.put("overhaul.checkPerson", list_check_person.size() == 0 ? recoder.checkPerson : list_check_person
					.get(list_check_person.size() - 1).getPersonId());
			map.put("overhaul.checkTime", time == null ? "" : time);
			
			map.put("overhaul.picPath", photo_url == null ? recoder.picPath : photo_url);
			map.put("overhaul.Remark", et_remark.getText().toString());
			map.put("overhaul.scheduleId",
					list_project.size() == 0 ? recoder.scheduleId + "" : list_project.get(list_project.size() - 1)
							.getId() + "");
			map.put("overhaul.recordId", recoder.id + "");
			
			// map.put("overhaul.checkPerson", "1");
			// map.put("overhaul.checkTime", time == null ? "" : time);
			// map.put("overhaul.picPath", "");
			// map.put("overhaul.remark", et_remark.getText().toString());
			// map.put("overhaul.scheduleId", "1");
			// map.put("overhaul.recordId",recoder.id+"");
			// map.put("checkResultList[" + 0 + "].result", "1");
			// map.put("checkResultList[" + 0 + "].entryId", "1");
			// 还有两个list没存
			
			HttpRequestPost request = new HttpRequestPost(SelfNeedCheckedProjectActivity.this,
					new HttpRequestPost.HttpRequestCallBack() {
						
						@Override
						public void success(String message) {
							AlertDialogUtil.dissmissLoading();
							if (!StringUtils.isEmpty(message)) {
								try {
									JSONObject obj = new JSONObject(message);
									int state = obj.getInt("state");
									if (state == 1) {
										SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, "写入成功！！！");
										
										getNeedCheckedRecode();
									} else {
										SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, "写入失败！！！");
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
						
						@Override
						public void start() {
							
						}
						
						@Override
						public void error(String message) {
							AlertDialogUtil.dissmissLoading();
							Toast.makeText(getApplicationContext(), "写入错误！！！", 0).show();
						}
						
						@Override
						public void cancel() {
							
						}
					}, ConstantValues.MODFY_SELF_CURR, map);
			
			request.execute();
			
			break;
		}
	}
	
	@Override
	public void init() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		
		time = dateFormat.format(new Date(System.currentTimeMillis()));
		
		tv_time.setText(time);
		
		mScan = new myScan();
		list_item = new ArrayList<SafetyItemBean>();
		
		adapter_item = new PollingResultAdapter();
		
		polling_lv.setAdapter(adapter_item);
		
		// UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
		// List<SafetyItemBean> list = ue.getUrlBean(ConstantValues.SAFEITEMBYID + "?scheduleId=" + "2",
		// SafetyItemBean.class);
		
		// HttpRequest request = new HttpRequest(SelfNeedCheckedProjectActivity.this, new HttpRequestCallBack() {
		//
		// @Override
		// public void success(String message) {
		// AlertDialogUtil.dissmissLoading();
		// if (!StringUtils.isEmpty(message)) {
		// try {
		// JSONObject obj = new JSONObject(message);
		// int state = obj.getInt("state");
		// if (state == 1) {
		// // SystemNotification.showToast(SafetyCreateActivity.this, "写入成功！！！");
		//
		// List<SafetyItemBean> array = JSON.parseArray(obj.getJSONArray("entryList").toString(),
		// SafetyItemBean.class);
		// System.out.println(array.toString());
		// list_item.clear();
		// for (SafetyItemBean sib : array) {
		// list_item.add(sib);
		// }
		//
		// adapter_item.notifyDataSetChanged();
		//
		// } else {
		// SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, "写入失败！！！");
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// }
		//
		// @Override
		// public void start() {
		//
		// }
		//
		// @Override
		// public void error(String message) {
		// AlertDialogUtil.dissmissLoading();
		// // TODO Auto-generated method stub
		// Toast.makeText(getApplicationContext(), "写入错误！！！", 0);
		// }
		//
		// @Override
		// public void cancel() {
		//
		// }
		// }, ConstantValues.SAFEITEMBYID + "?scheduleId=" + "2", null);
		//
		// request.execute();
		
		initData();
		
		initImageLoaderOption();
	}
	
	private void initData() {
		recoder = (SelfProjectRecoder) getIntent().getSerializableExtra("data");
		List<SecurityCheckEntry> mSecurityList;
		if (recoder != null) {
			mSecurityList = recoder.mSecurityList;
		} else {
			recoder = new SelfProjectRecoder();
			mSecurityList = new ArrayList<SecurityCheckEntry>();
		}
		
		photo_url = recoder.picPath;
		
		tv_project.setText(recoder.scheduleName);
		tv_check_person.setText(recoder.checkPersonName);
		limit_time.setText(recoder.overhaulLimit + "天");
		et_remark.setText(recoder.remark);
		
		for (SecurityCheckEntry ss : mSecurityList) {
			SafetyItemBean bean = new SafetyItemBean();
			bean.setCategory(ss.category);
			bean.setDescription(ss.description);
			bean.setId(ss.id);
			bean.setLevel(ss.level);
			bean.setLevelName(ss.levelName);
			if (ss.result == 0) {
				// 不合格
				bean.setMistake(true);
				bean.setQualify(false);
			} else if (ss.result == 1) {
				// 合格
				bean.setMistake(false);
				bean.setQualify(true);
			}
			bean.setTitle(ss.title);
			list_item.add(bean);
		}
		adapter_item.notifyDataSetChanged();
	}
	
	@ViewById
	private EditText limit_time;
	
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private void initImageLoaderOption() {
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.no_photo_background)
				.showImageForEmptyUri(R.drawable.no_photo_background).showImageOnFail(R.drawable.no_photo_background)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(10)).build();
		imageLoader.displayImage(ConstantValues.CLIENT_URL_PRE + recoder.picPath, safety_iv, options,
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
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.need_check);
		
		title_bar_content.setText("安全整改");
		
	}
	
	private Bitmap bmp_selectedPhoto;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// 返回是得到bitmap更改图片
		if (resultCode == Activity.RESULT_OK) {
			ContentResolver cr = this.getContentResolver();
			try {
				if (bmp_selectedPhoto != null)// 如果不释放的话，不断取图片，将会内存不够
					bmp_selectedPhoto.recycle();
				bmp_selectedPhoto = BitmapFactory.decodeStream(cr.openInputStream(mUri));
				safety_iv.setImageBitmap(bmp_selectedPhoto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			SystemNotification.showToast(getApplicationContext(), "拍照成功！！！");
			
		}
	}
	
	UHFReaderClass.OnEPCsListener listen = new UHFReaderClass.OnEPCsListener() {
		// 对接收到EPC数据的处理
		// 由于onEPCsRecv是被封装在UHFReaderClass类的非UI线程调用的
		// 要实现把数据实时刷到界面，需通过Handle交回给UI线程处理。
		public void onEPCsRecv(ArrayList<String> EPCList) {
			
			switch (state) {
			case 1:
				mHandler.sendMessage(Message.obtain(mHandler, 1, EPCList));
				break;
			case 2:
				mHandler.sendMessage(Message.obtain(mHandler, 2, EPCList));
				break;
			
			}
			
		}
	};
	
	private ScanIvAdapter siAdapter_check;
	
	private ScanAdapter sAdapter;
	
	private myScan mScan;
	
	private PollingResultAdapter adapter_item;
	
	private String time;
	
	private SelfProjectRecoder recoder;
	
	class myScan extends CardNetConnection {
		@Override
		public void onCardReadOver(HashMap<String, Pair<Boolean, String>> map) {
			
		}
		
		@Override
		public void onCardReadOne(BaseBean value) {
			
			switch (state) {
			case 1:
				if (!list_project.contains(((ProjectCardBean) value))) {
					list_project.add(((ProjectCardBean) value));
				}
				
				sAdapter.notifyDataSetChanged();
				
				String id = (list_project.get(list_project.size() - 1).getId()) + "";
				
				if (!TextUtils.isEmpty(id)) {
					UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
					List<SafetyItemBean> list = ue.getUrlBean(ConstantValues.SAFEITEMBYID + "?scheduleId=" + id,
							SafetyItemBean.class);
					
					System.out.println(list.toString());
					
					list_item.clear();
					for (SafetyItemBean sib : list) {
						list_item.add(sib);
					}
				}
				tv_project.setText(list_project.get(list_project.size() - 1).getName());
				break;
			
			case 2:
				
				if (!list_check_person.contains(((PersonCardBean) value))) {
					list_check_person.add(((PersonCardBean) value));
				}
				
				Log.e("asd", list_check_person.toString());
				siAdapter_check.notifyDataSetChanged();
				
				tv_check_person.setText(list_check_person.get(list_check_person.size() - 1).getPersonName());
				break;
			}
			
		}
	}
	
	@Override
	public void finish() {
		mScan.clearMap();
		super.finish();
	}
	
	public class PollingResultAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return list_item.size();
		}
		
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = View.inflate(SelfNeedCheckedProjectActivity.this, R.layout.item_polling_create, null);
			
			TextView item_polling_create_name = (TextView) view.findViewById(R.id.item_polling_create_name);
			
			final CheckBox item_polling_create_isqualify = (CheckBox) view
					.findViewById(R.id.item_polling_create_isqualify);
			
			final CheckBox item_polling_create_ismistake = (CheckBox) view
					.findViewById(R.id.item_polling_create_ismistake);
			
			if (TextUtils.isEmpty(list_item.get(position).getTitle())) {
				item_polling_create_name.setText(list_item.get(position).getTitle());
			} else {
				item_polling_create_name.setText(list_item.get(position).getTitle() + "["
						+ list_item.get(position).getLevelName() + "]");
			}
			item_polling_create_isqualify.setChecked(list_item.get(position).isQualify());
			item_polling_create_ismistake.setChecked(list_item.get(position).isMistake());
			
			item_polling_create_isqualify.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if (isChecked) {
						
						if (list_item.get(position).isMistake()) {
							list_item.get(position).setMistake(!isChecked);
							item_polling_create_ismistake.setChecked(!isChecked);
						}
					}
					list_item.get(position).setQualify(isChecked);
				}
			});
			
			item_polling_create_ismistake.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					
					if (isChecked) {
						
						if (list_item.get(position).isQualify()) {
							list_item.get(position).setQualify(!isChecked);
							item_polling_create_isqualify.setChecked(!isChecked);
						}
					}
					list_item.get(position).setMistake(isChecked);
					
				}
			});
			
			return view;
		}
		
	}
	
	protected void getNeedCheckedRecode() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "50");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseSelfCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(SelfNeedCheckedProjectActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(SelfCheckedActivity.class, "data", currVitae);
				finish();
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(SelfNeedCheckedProjectActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.CHECK_RECODE, mHashMap);
		mRequest.execute();
	}
}
