package cn.tlrfid.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.anno.net1.HttpRequestPost;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.bean.PollingResult;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.bean.SafetyItemBean;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
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
import cn.tlrfid.view.adapter.PollingResultAdapter;
import cn.tlrfid.view.adapter.PollingResultAdapter.ChangeData;
import cn.tlrfid.view.adapter.ScanAdapter;
import cn.tlrfid.view.adapter.ScanIvAdapter;

import com.alibaba.fastjson.JSON;
import com.cetc7.UHFReader.UHFReaderClass;

/**
 * 新建安全检查
 * 
 * @author sk
 * 
 */
public class SafetyCreateActivity extends BaseActivity implements ChangeData {
	
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
	
	public static List<ProjectCardBean> list_project = new ArrayList();
	
	public static List<PersonCardBean> list_check_person = new ArrayList();
	
	@ViewById
	private ImageView safety_iv;
	
	@ViewById
	public static TextView tv_check_person;
	
	@ViewById
	public static TextView tv_project;
	
	@ViewById
	private EditText et_remark;
	
	@ViewById
	private EditText et_overhaulLimit;
	
	@ViewById_Clickthis
	private Button bt_request_photo;
	
	public static int state = 1;
	@ViewById_Clickthis
	private Button bt_safe_create;
	
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
				
				mScan.setndCard(SafetyCreateActivity.this, mEpcList, ConstantValues.QUERYPROJECT, ProjectCardBean.class);
				
				break;
			case 2:
				Log.e("asd", "3里面");
				mEpcList = (ArrayList<String>) msg.obj;
				if (mEpcList != null && mEpcList.size() > 0) {
					System.out.println(mEpcList.get(0));
				}
				
				mScan.setndCard(SafetyCreateActivity.this, mEpcList, ConstantValues.QUERYPERSON, PersonCardBean.class);
				
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
			
			AlertDialogUtil.showLoading(SafetyCreateActivity.this, "正在上传...");
			
			new AsyncTask<String[], Integer, String>() {
				
				@Override
				protected String doInBackground(String[]... params) {
					try {
						photo_url = BitmapUtil.sendData(BitmapUtil.drawableToBitmap(safety_iv.getDrawable()),
								System.currentTimeMillis() + "");
					} catch (Exception e) {
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
								SystemNotification.showToast(SafetyCreateActivity.this, "上传成功！！！");
							} else {
								SystemNotification.showToast(SafetyCreateActivity.this, json.getString("errMessage"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					} else {
						SystemNotification.showToast(SafetyCreateActivity.this, "上传失败！！！");
					}
					
					super.onPostExecute(result);
				}
				
			}.execute(new String[] {});
			break;
		
		case R.id.bt_scan_project:
			state = 1;
			
			sAdapter = new ScanAdapter(this, list_project);
			
			new MyAlertDialogShow(SafetyCreateActivity.this, view, sAdapter, "巡检项目", listen).show();
			break;
		case R.id.bt_scan_person:
			
			state = 2;
			siAdapter_check = new ScanIvAdapter(this, list_check_person);
			
			new MyAlertDialogShow(SafetyCreateActivity.this, view, siAdapter_check, "巡检人员", listen).show();
			break;
		
		// 创建
		case R.id.bt_safe_create:
			if (list_check_person.size() == 0) {
				Toast.makeText(getApplicationContext(), "巡检人不能为空", 0).show();
				return;
			}
			
			if (TextUtils.isEmpty(list_check_person.get(0).getPersonId())) {
				Toast.makeText(getApplicationContext(), "巡检人不能为空", 0).show();
				return;
			}
			
			if (list_project.size() == 0) {
				Toast.makeText(getApplicationContext(), "进度计划不能为空", 0).show();
				return;
			}
			if (TextUtils.isEmpty(list_project.get(0).getId() + "")) {
				Toast.makeText(getApplicationContext(), "进度计划不能为空", 0).show();
				return;
				
			}
			
			if (TextUtils.isEmpty(photo_url)) {
				;
				Toast.makeText(getApplicationContext(), "请先拍照上传照片", 0).show();
				return;
			}
			
			AlertDialogUtil.showLoading(SafetyCreateActivity.this, "正在写入...");
			
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
			
			// 此为list集合
			
			for (int i = 0; i < adapter_item.mList.size(); i++) {
				map.put("entryList[" + i + "].entryId", adapter_item.mList.get(i).getId() + "");
				if (adapter_item.mList.get(i).isQualify()) {
					// 合格
					map.put("entryList[" + i + "].result", "1");
				} else {
					// 不合格
					map.put("entryList[" + i + "].result", "0");
				}
			}
			
			map.put("record.overhaulLimit", et_overhaulLimit.getText().toString().trim());
			
			map.put("record.checkPerson",
					list_check_person.size() == 0 ? "" : list_check_person.get(list_check_person.size() - 1)
							.getPersonId());
			map.put("record.checkTime", time == null ? "" : time);
			
			map.put("record.picPath", photo_url == null ? "" : photo_url);
			map.put("record.Remark", et_remark.getText().toString());
			map.put("record.scheduleId", list_project.size() == 0 ? "" : list_project.get(list_project.size() - 1)
					.getId() + "");
			
			// map.put("record.overhaulLimit", "3");
			// map.put("record.checkPerson", "1");
			// map.put("record.checkTime", time == null ? "" : time);
			// map.put("record.picPath", "");
			// map.put("record.remark", et_remark.getText().toString());
			// map.put("record.scheduleId", "1");
			// map.put("entryList[" + 0 + "].result", "1");
			// map.put("entryList[" + 0 + "].entryId", "1");
			// 还有两个list没存
			
			HttpRequestPost request = new HttpRequestPost(SafetyCreateActivity.this,
					new HttpRequestPost.HttpRequestCallBack() {
						
						@Override
						public void success(String message) {
							AlertDialogUtil.dissmissLoading();
							if (!StringUtils.isEmpty(message)) {
								try {
									JSONObject obj = new JSONObject(message);
									int state = obj.getInt("state");
									if (state == 1) {
										SystemNotification.showToast(SafetyCreateActivity.this, "写入成功！！！");
										getProjectRecod();
										
									} else {
										SystemNotification.showToast(SafetyCreateActivity.this, "写入失败！！！");
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
							Toast.makeText(getApplicationContext(), "写入错误！！！", 0);
						}
						
						@Override
						public void cancel() {
							
						}
					}, ConstantValues.SAVE_SELF_CURR, map);
			
			request.execute();
			
			break;
		}
	}
	
	@Override
	public void init() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		
		time = dateFormat.format(new Date(System.currentTimeMillis()));
		
		tv_time.setText(time);
		
		list_check_person.clear();
		mScan = new myScan();
		list_item = new ArrayList<SafetyItemBean>();
		
		adapter_item = new PollingResultAdapter(this, list_item);
		
		adapter_item.setDataChangeListener(this);
		polling_lv.setAdapter(adapter_item);
		
		// UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
		// List<SafetyItemBean> list = ue.getUrlBean(ConstantValues.SAFEITEMBYID + "?scheduleId=" + "2",
		// SafetyItemBean.class);
		
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.safety_create);
		
		title_bar_content.setText("新建安全检查");
		
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
	
	public static ScanIvAdapter siAdapter_check;
	
	private ScanAdapter sAdapter;
	
	private myScan mScan;
	
	private PollingResultAdapter adapter_item;
	
	private String time;
	
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
					HttpRequest request = new HttpRequest(SafetyCreateActivity.this, new HttpRequestCallBack() {
						
						@Override
						public void success(String message) {
							AlertDialogUtil.dissmissLoading();
							if (!StringUtils.isEmpty(message)) {
								try {
									JSONObject obj = new JSONObject(message);
									int state = obj.getInt("state");
									if (state == 1) {
										// SystemNotification.showToast(SafetyCreateActivity.this, "写入成功！！！");
										
										List<SafetyItemBean> array = JSON.parseArray(obj.getJSONArray("entryList")
												.toString(), SafetyItemBean.class);
										System.out.println(array.toString());
										list_item.clear();
										for (SafetyItemBean sib : array) {
											list_item.add(sib);
										}
										
										adapter_item.notifyDataSetChanged();
										
									} else {
										SystemNotification.showToast(SafetyCreateActivity.this, "写入失败！！！");
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
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "写入错误！！！", 0);
						}
						
						@Override
						public void cancel() {
							
						}
					}, ConstantValues.SAFEITEMBYID + "?scheduleId=" + id, null);
					
					request.execute();
					
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
	
	protected void getProjectRecod() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "50");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseSelfCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(SafetyCreateActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(SelfManagerProject.class, "data", currVitae);
				finish();
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(SafetyCreateActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.SELF_CURRICULUM_VITATE_QUERY, mHashMap);
		mRequest.execute();
		
	}
	
	@Override
	public void finish() {
		mScan.clearMap();
		super.finish();
	}
	
	@Override
	public void change(ArrayList<SafetyItemBean> mList) {
		list_item = mList;
		adapter_item.notifyDataSetChanged();
	}
}
