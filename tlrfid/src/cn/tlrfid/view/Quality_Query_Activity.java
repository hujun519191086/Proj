package cn.tlrfid.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.bean.ProjectRecoder;
import cn.tlrfid.bean.QualityBean;
import cn.tlrfid.bean.QualityRecordBean;
import cn.tlrfid.bean.ScheduleProject;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.CardNetConnection;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.adapter.QualityListAdapter;
import cn.tlrfid.view.control.PullToRefreshView;
import cn.tlrfid.view.control.PullToRefreshView.OnFooterRefreshListener;

import com.cetc7.UHFReader.UHFReaderClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class Quality_Query_Activity extends BaseActivity implements OnItemClickListener, OnFooterRefreshListener {
	@ViewById_Clickthis
	private Button curriculum_vitae_query_tv_click_to_scan;
	@ViewById
	private TextView curriculum_vitae_query_et_select_date;
	@ViewById
	private TextView curriculum_vitae_query_card;
	@ViewById
	private GridView curriculum_vitae_query_gv_show;
	@ViewById
	private EditText curriculum_vitae_query_about;
	@ViewById_Clickthis
	private ImageView curriculum_vitae_query_arrow;
	private List<String> list;
	private QualityListAdapter adapter;
	private List<ScheduleProject> subProjectList;
	private PopupWindow popupWindow;
	private MyAdapter mAdapter;
	@ViewById_Clickthis
	private Button date_select;
	
	private Handler mHandler = null;
	
	private ArrayList<String> mEpcList;
	public List<QualityBean> mItemList;
	@ViewById_Clickthis
	private Button search;
	@ViewById
	private PullToRefreshView mPullToRefreshView;
	
	class MyScan extends CardNetConnection {
		private static final String TAG = "MyScan";
		
		@Override
		public void onCardReadOver(HashMap<String, Pair<Boolean, String>> map) {
			LogUtil.d(TAG, "result:" + map);
		}
		
		@Override
		public void onCardReadOne(BaseBean value) {
			LogUtil.d(TAG, "values:" + value);
			mAdapter.addToList((ProjectCardBean) value);
		}
	}
	
	private void initData() {
		list = new ArrayList<String>();
		adapter = new QualityListAdapter(getApplicationContext(), curriculum_vitae_query_gv_show,
				R.dimen.gridview_item_hight, mItemList);
		curriculum_vitae_query_gv_show.setAdapter(adapter);
		
		title_bar_content.setText("质量管理一览");
		mScan = new MyScan();
		mHandler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					mEpcList = (ArrayList<String>) msg.obj;
					if (mEpcList != null && mEpcList.size() > 0) {
						SystemNotification.showToast(getApplicationContext(), mEpcList.toString());
						mScan.setndCard(Quality_Query_Activity.this, mEpcList, ConstantValues.CURRICULUM_QUERY,
								ProjectCardBean.class);
						
						// 09-04 00:11:27.707: D/UniversalEngineImpl(7941):
						// url:http://182.92.76.78:8080/spms/service/queryScheduleByTag.action?tagCode=FD0000000000000000000005
						
					}
					break;
				}
				
			}
		};
		
		listen2 = new UHFReaderClass.OnEPCsListener() {
			// 对接收到EPC数据的处理
			// 由于onEPCsRecv是被封装在UHFReaderClass类的非UI线程调用的
			// 要实现把数据实时刷到界面，需通过Handle交回给UI线程处理。
			public void onEPCsRecv(ArrayList<String> EPCList) {
				mHandler.sendMessage(Message.obtain(mHandler, 0, EPCList));
			}
		};
		
		mAdapter = new MyAdapter(new ArrayList<ProjectCardBean>());
	}
	
	@Override
	public void onBackPressed() {
		AlertDialogUtil.dissmissLoading();
		super.onBackPressed();
	}
	
	private void setListener() {
		curriculum_vitae_query_gv_show.setOnItemClickListener(this);
		curriculum_vitae_query_et_select_date.setOnClickListener(this);
		// curriculum_vitae_query_et_select_date.setKeyListener(null);
		// RFIDUtils.getInstance(this).setRFIDReadListener(new RFIDReadEngine() {
		//
		// @Override
		// public ArrayList<String> readRFID(ArrayList<String> mEpcList) {
		// SystemNotification.showToast(CurriculumVitae_Query_Activity.this, mEpcList.get(0));
		// curriculum_vitae_query_card.setText(mEpcList.get(0));
		// return null;
		// }
		//
		// @Override
		// public byte[] read6CUserData(byte[] mByte) {
		// return null;
		// }
		// });
		
		mPullToRefreshView.setOnFooterRefreshListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.curriculum_vitae_query_tv_click_to_scan:
			
			// 扫描设备
			// RFIDUtils.getInstance(CurriculumVitae_Query_Activity.this).readEPCs(listen);
			showCardDialog();
			break;
		case R.id.curriculum_vitae_query_arrow:
			// 弹出选择对话框
			showSelectNumberDialog();
			break;
		case R.id.curriculum_vitae_query_et_select_date:
			// SystemNotification.showToast(CurriculumVitae_Query_Activity.this, "巡检日期");
			showDatePick();
			break;
		case R.id.search:
			// 查询履历
			searchCurriculum();
			break;
		
		case R.id.date_select:
			// 日期选择
			dialog.dismiss();
			break;
		}
	}
	
	private void searchCurriculum() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("projectCode", Config_values.PROJECT_CODE);
		hashMap.put("pageSize", "50");
		
		hashMap.put("tagCode", "FD0000000000000000000005");
		String name = curriculum_vitae_query_about.getText().toString().trim();
		ArrayList<QualityBean> list = currVitae.getItems();
		
		for (QualityBean pro : list) {
			// if (pro.name.equals(name)) {
			// hashMap.put("scheduleId", String.valueOf(pro.scheduleId));
			// }
		}
		
		hashMap.put("inspectionTime", curriculum_vitae_query_et_select_date.getText().toString().trim());
		hashMap.put("page", "1");
		
		// HttpRequest request = new HttpRequest(this, new HttpRequestCallBack() {
		//
		// @Override
		// public void success(String message) {
		// AlertDialogUtil.dissmissLoading();
		// currVitae = StringUtils.parseCUrriculumInfo(message);
		// if (currVitae.state != 1) {
		// SystemNotification.showToast(Quality_Query_Activity.this, currVitae.errMessage);
		// return;
		// }
		// mItemList = currVitae.mItemList;
		// adapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void start() {
		// AlertDialogUtil.showLoading(Quality_Query_Activity.this, "加载中...");
		// }
		//
		// @Override
		// public void error(String message) {
		// AlertDialogUtil.dissmissLoading();
		// }
		//
		// @Override
		// public void cancel() {
		// AlertDialogUtil.dissmissLoading();
		// }
		// }, ConstantValues.QUERY_CHECK_RECORD, hashMap);
		//
		// request.execute();
	}
	
	private void showCardDialog() {
		View view = View.inflate(this, R.layout.card_num_show_dialog, null);
		new MyAlertDialogShow(this, view, mAdapter, "检查点列表", listen2).show();
	}
	
	public class MyAdapter extends BaseAdapter {
		
		ArrayList<ProjectCardBean> mList;
		
		public MyAdapter(ArrayList<ProjectCardBean> mList) {
			this.mList = mList;
		}
		
		public void addToList(ProjectCardBean pcb) {
			mList.add(pcb);
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mList == null ? 0 : mList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holer = null;
			if (convertView == null) {
				holer = new Holder();
				convertView = View.inflate(Quality_Query_Activity.this, R.layout.item_scan, null);
				holer.tv_str = (TextView) convertView.findViewById(R.id.tv_str);
				holer.ib_item_delete = (ImageButton) convertView.findViewById(R.id.ib_item_delete);
				convertView.setTag(holer);
			} else {
				holer = (Holder) convertView.getTag();
			}
			ProjectCardBean pcb = mList.get(position);
			holer.tv_str.setText(pcb.getName());
			holer.ib_item_delete.setTag(position);
			holer.ib_item_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					mList.remove(position);
					notifyDataSetChanged();
				}
			});
			return convertView;
		}
		
		// public void addEPCs(ArrayList<String> EPCs) {
		// for (int i = 0; i < EPCs.size(); i++) {
		// if (!mList.contains(EPCs.get(i))) {
		// mList.add(EPCs.get(i));
		// }
		// }
		// notifyDataSetChanged();
		// }
		
	}
	
	class Holder {
		TextView tv_str;
		ImageButton ib_item_delete;
	}
	
	private void showSelectNumberDialog() {
		// subProjectList = getProjects();
		// subProjectList = currVitae == null ? null : currVitae.projectNameList;
		//
		// ListView lv = new ListView(this);
		// lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
		// // 隐藏滚动条
		// lv.setVerticalScrollBarEnabled(false);
		// // 让listView没有分割线
		// lv.setDividerHeight(0);
		// lv.setDivider(null);
		// lv.setOnItemClickListener(this);
		//
		// mProjectAdapter = new ProjectsAdapter();
		// lv.setAdapter(mProjectAdapter);
		//
		// // SystemNotification.showPopupWindow(this, lv, curriculum_vitae_query_about,
		// // curriculum_vitae_query_about.getWidth(), 200, 0, 0, this);
		//
		// popupWindow = new PopupWindow(lv, curriculum_vitae_query_about.getWidth(), 300);
		// // 设置点击外部可以被关闭
		// popupWindow.setOutsideTouchable(true);
		// popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//
		// // 设置popupWindow可以得到焦点
		// popupWindow.setFocusable(true);
		//
		// popupWindow.showAsDropDown(curriculum_vitae_query_about, 0, 0); // 显示
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent instanceof ListView) {
			curriculum_vitae_query_about.setText(subProjectList.get(position).name);
			popupWindow.dismiss();
		} else {
			startActivity(ProjectRecoderCheck.class, "data", mItemList.get(position));
		}
	}
	
	@Override
	public void init() {
		setListener();
		getData();
		initData();
		
	}
	
	private void getData() {
		Intent intent = getIntent();
		currVitae = (QualityRecordBean) intent.getSerializableExtra("data");
		mItemList = currVitae == null ? new ArrayList<QualityBean>() : currVitae.getItems();
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.curriculum_vitae_query);
	}
	
	/**
	 * 显示时间选择器
	 */
	final Calendar mDate = Calendar.getInstance();
	private QualityRecordBean currVitae;
	private AlertDialog dialog;
	private UHFReaderClass.OnEPCsListener listen2;
	private MyScan mScan;
	
	public void showDatePick() {
		
		DatePickerDialog dataPick = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				mDate.set(year, monthOfYear, dayOfMonth);
				curriculum_vitae_query_et_select_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
			}
		}, mDate.get(Calendar.YEAR), mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH));
		dataPick.show();
		//
		// View view = View.inflate(this, R.layout.date_time_picker, null);
		// DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		// TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
		// timePicker.setIs24HourView(true);
		// AlertDialog.Builder build = new Builder(this);
		// build.setView(view);
		// dialog = build.create();
		// build.show();
	}
	
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 2000);
	}
	
	// private class CurriculumVitaeAsync extends NetConnectionAsync<String, Void, F>(){
	//
	// }
}
