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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.CurriculumVitaeBean;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.utils.CardNetConnection;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.adapter.CreateAdapter;
import cn.tlrfid.view.adapter.ScanAdapter;
import cn.tlrfid.view.adapter.ScanIvAdapter;

import com.cetc7.UHFReader.UHFReaderClass;

/**
 * 新建履历
 * 
 * @author sk
 * 
 */
public class CurriculumVitae_Create_Activity extends BaseActivity implements
		OnSeekBarChangeListener {

	@ViewById
	private ImageView curriculumvitae_iv;
	@ViewById
	private ListView curruculumvitae_lv;

	@ViewById_Clickthis
	private Button bt_scan_check_person;

	@ViewById_Clickthis
	private Button bt_scan_construction_person;

	@ViewById_Clickthis
	private Button bt_scan_project;

	private String time;
	@ViewById
	private TextView tv_currentTime; // 当前日期

	@ViewById_Clickthis
	private Button bt_upload_photo;

	@ViewById
	private EditText et_remark; // 备注

	@ViewById
	private EditText et_progress; // 进度

	@ViewById
	public static TextView tv_project;

	@ViewById
	public static TextView tv_check_person;

	@ViewById
	private SeekBar sb;

	private ArrayList<String> mEpcList;

	@ViewById_Clickthis
	private Button bt_write_curriculumvitae;

	public static int state = 1;

	private String photo_url;

	public static CreateAdapter adapter;

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

				mScan.setndCard(CurriculumVitae_Create_Activity.this, mEpcList,
						ConstantValues.QUERYPROJECT, ProjectCardBean.class);

				break;
			case 2:
				Log.e("asd", "2里面");
				mEpcList = (ArrayList<String>) msg.obj;
				if (mEpcList != null && mEpcList.size() > 0) {
					System.out.println(mEpcList.get(0));
				}

				mScan.setndCard(CurriculumVitae_Create_Activity.this, mEpcList,
						ConstantValues.QUERYPERSON, PersonCardBean.class);

				break;
			case 3:
				Log.e("asd", "3里面");
				mEpcList = (ArrayList<String>) msg.obj;
				if (mEpcList != null && mEpcList.size() > 0) {
					System.out.println(mEpcList.get(0));
				}

				mScan.setndCard(CurriculumVitae_Create_Activity.this, mEpcList,
						ConstantValues.QUERYPERSON, PersonCardBean.class);

				break;
			}

		}
	};

	public void onCreateView() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd kk:mm:ss");

		time = dateFormat.format(new Date(System.currentTimeMillis()));

		setContentView(R.layout.curriculumvitae_create);

	};

	public void toOpen(View v) {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File appDir = new File(Environment.getExternalStorageDirectory()
				+ "/KengDieA");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
				+ "/KengDieA/", "kengDiePic"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
		try {
			cameraIntent.putExtra("return-data", true);
			startActivityForResult(cameraIntent, 168);
		} catch (Exception e) {
			e.printStackTrace();

		}

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
				bmp_selectedPhoto = BitmapFactory.decodeStream(cr
						.openInputStream(mUri));
				curriculumvitae_iv.setImageBitmap(bmp_selectedPhoto);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			SystemNotification.showToast(getApplicationContext(), "拍照成功！！！");

		}
	}

	@Override
	public void onClick(View v) {
		View view = View.inflate(this, R.layout.card_num_show_dialog, null);
		switch (v.getId()) {
		case R.id.bt_scan_project:
			state = 1;

			sAdapter = new ScanAdapter(this, list_project);

			Toast.makeText(getApplicationContext(), "1", 0).show();
			new MyAlertDialogShow(CurriculumVitae_Create_Activity.this, view,
					sAdapter, "巡检项目", listen).show();

			break;

		case R.id.bt_scan_construction_person:
			state = 2;
			Toast.makeText(getApplicationContext(), "2", 0).show();
			siAdapter = new ScanIvAdapter(this, list_person);

			new MyAlertDialogShow(CurriculumVitae_Create_Activity.this, view,
					siAdapter, "施工人员", listen)
			/*
			 * { public void onClickOut() { List<PersonCardBean> list =
			 * siAdapter.getListData(); ArrayList<PersonCardBean> checkedList =
			 * new ArrayList<PersonCardBean>(); for (int i = 0; i < list.size();
			 * i++) { PersonCardBean pcb = list.get(i); if (pcb.isChecked()) {
			 * checkedList.add(pcb); } } adapter.notifyList(checkedList); }; }
			 */
			.show();
			break;

		case R.id.bt_scan_check_person:
			state = 3;

			siAdapter_check = new ScanIvAdapter(this, list_check_person);

			new MyAlertDialogShow(CurriculumVitae_Create_Activity.this, view,
					siAdapter_check, "巡检人员", listen).show();
			break;

		case R.id.bt_upload_photo:
			AlertDialogUtil.showLoading(CurriculumVitae_Create_Activity.this,
					"正在上传...");

			new AsyncTask<String[], Integer, String>() {

				@Override
				protected String doInBackground(String[]... params) {
					try {
						photo_url = BitmapUtil.sendData(BitmapUtil
								.drawableToBitmap(curriculumvitae_iv
										.getDrawable()),
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
					// Toast.makeText(getApplicationContext(), "数据位空！！！",
					// 0).show();
					// return;
					// }
					//
					// System.out.println(result);
					if (!StringUtils.isEmpty(result)) {
						try {
							JSONObject json = new JSONObject(result);
							if (json.getInt("state") == 1) {
								photo_url = json.getString("filePath").replace(
										"\\/", "");
								SystemNotification.showToast(
										CurriculumVitae_Create_Activity.this,
										"上传成功！！！");
							} else {
								SystemNotification.showToast(
										CurriculumVitae_Create_Activity.this,
										json.getString("errMessage"));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					} else {
						SystemNotification
								.showToast(
										CurriculumVitae_Create_Activity.this,
										"上传失败！！！");
					}

					super.onPostExecute(result);
				}

			}.execute(new String[] {});
			break;

		case R.id.bt_write_curriculumvitae:

			// record .finishRate:完成度
			// record .inspectionPerson:巡检人ID
			// record .inspectionTime:巡检时间
			// record .person:施工人员,人员名称逗号分隔字符串
			// record .picPath:图片路径
			// record .Remark:备注
			// record .scheduleId:进度计划ID

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
				Toast.makeText(getApplicationContext(), "请先拍照上传照片", 0).show();
				return;
			}

			AlertDialogUtil.showLoading(CurriculumVitae_Create_Activity.this,
					"正在写入...");

			HashMap<String, String> map = new HashMap<String, String>();

			String record_person_id = "";
			for (PersonCardBean pc : siAdapter.list) {
				record_person_id += pc.getPersonId() + ",";
			}

			map.put("record.finishRate", sb.getProgress() + "");
			map.put("record.inspectionPerson",
					list_check_person.size() == 0 ? "" : siAdapter_check.list
							.get(siAdapter_check.list.size() - 1).getPersonId());
			map.put("record.inspectionTime", time == null ? "" : time);
			map.put("record.person", record_person_id == null ? ""
					: record_person_id);
			map.put("record.picPath", photo_url == null ? "" : photo_url);
			map.put("record.Remark", et_remark.getText().toString());
			map.put("record.scheduleId", list_project.size() == 0 ? ""
					: list_project.get(0).getId() + "");

			HttpRequest request = new HttpRequest(
					CurriculumVitae_Create_Activity.this,
					new HttpRequestCallBack() {

						@Override
						public void success(String message) {
							AlertDialogUtil.dissmissLoading();
							if (!StringUtils.isEmpty(message)) {
								try {
									JSONObject obj = new JSONObject(message);
									int state = obj.getInt("state");
									if (state == 1) {
										SystemNotification
												.showToast(
														CurriculumVitae_Create_Activity.this,
														"写入成功！！！");

										getProjectRecod();

									} else {
										SystemNotification
												.showToast(
														CurriculumVitae_Create_Activity.this,
														"写入失败！！！");
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
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
							Toast.makeText(getApplicationContext(), "写入错误！！！",
									0);
						}

						@Override
						public void cancel() {

						}
					}, ConstantValues.WRITE, map);

			request.execute();

			break;
		}

	}

	@Override
	public void init() {

		mScan = new myScan();

		list_check_person.clear();
		list_person.clear();
		list = list_person;

		adapter = new CreateAdapter(this, curruculumvitae_lv, list);

		curruculumvitae_lv.setAdapter(adapter);

		title_bar_content.setText("新增履历");

		tv_currentTime.setText(time);

		sb.setOnSeekBarChangeListener(this);

		et_progress.clearFocus();
		// 监听进度输入框，改变进度条
		et_progress.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				if (TextUtils.isEmpty(s.toString().trim())) {
					et_progress.setText(0 + "");
					return;
				}

				int progress = Integer.valueOf(s.toString());
				if (progress > 100) {

					et_progress.setText(100 + "");
					return;

				}

				if (progress < 0) {
					et_progress.setText(0 + "");
					return;
				}

				sb.setProgress(progress);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

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
			case 3:
				mHandler.sendMessage(Message.obtain(mHandler, 3, EPCList));
				break;
			}

		}
	};
	private ScanAdapter sAdapter;
	public static List<ProjectCardBean> list_project = new ArrayList();

	public static List<PersonCardBean> list_check_person = new ArrayList();
	private myScan mScan;
	public static ArrayList<PersonCardBean> list_person = new ArrayList<PersonCardBean>();;
	public static ScanIvAdapter siAdapter;
	public static ScanIvAdapter siAdapter_check;
	private ArrayList<PersonCardBean> list;
	private Uri mUri;

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

				tv_project.setText(list_project.get(list_project.size() - 1)
						.getName());
				break;
			case 2:

				Log.e("qweqw", ((PersonCardBean) value).toString());
				if (!list_person.contains(((PersonCardBean) value))) {
					list_person.add(((PersonCardBean) value));
				}

				Log.e("asd", list_person.toString());

				adapter.notifyDataSetChanged();
				siAdapter.notifyDataSetChanged();
				break;
			case 3:

				if (!list_check_person.contains(((PersonCardBean) value))) {
					list_check_person.add(((PersonCardBean) value));
				}

				Log.e("asd", list_check_person.toString());
				siAdapter_check.notifyDataSetChanged();

				tv_check_person.setText(list_check_person.get(
						list_check_person.size() - 1).getPersonName());
				break;
			}
		}
	}

	/**
	 * 进去一览前
	 */
	protected void getProjectRecod() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "50");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {

			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				CurriculumVitaeBean currVitae = StringUtils
						.parseCUrriculumInfo(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(
							CurriculumVitae_Create_Activity.this,
							currVitae.errMessage);
					return;
				}

				startActivity(CurriculumVitae_Query_Activity.class, "data",
						currVitae);
				finish();
			}

			@Override
			public void start() {
				AlertDialogUtil.showLoading(
						CurriculumVitae_Create_Activity.this, "加载中...");
			}

			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}

			@Override
			public void cancel() {
			}
		}, ConstantValues.CURRICULUM_VITAE_QUERY, mHashMap);
		mRequest.execute();

	}

	@Override
	public void finish() {
		mScan.clearMap();
		super.finish();
	}

	/*
	 * 监听seekbar
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onProgressChanged(android
	 * .widget.SeekBar, int, boolean)
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		System.out.println("当前进度" + progress + "%");
		et_progress.setText(progress + ""); // 改变进度

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		System.out.println("拖动开始");
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		System.out.println("拖动停止");
	}

}
