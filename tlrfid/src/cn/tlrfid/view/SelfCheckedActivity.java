package cn.tlrfid.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.CurriculumVitaeBean;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.bean.ProjectRecoder;
import cn.tlrfid.bean.ScheduleProject;
import cn.tlrfid.bean.SecurityCheckEntry;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
import cn.tlrfid.bean.SelfProjectRecoder;
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
import cn.tlrfid.view.QualityCheckedActivity.MyScan;
import cn.tlrfid.view.control.PullToRefreshView;
import cn.tlrfid.view.control.PullToRefreshView.OnFooterRefreshListener;
import cn.tlrfid.view.control.PullToRefreshView.OnHeaderRefreshListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cetc7.UHFReader.UHFReaderClass;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SelfCheckedActivity extends BaseActivity implements OnItemClickListener, OnFooterRefreshListener,
		OnHeaderRefreshListener {
	protected static final String TAG = "SelfCheckedActivity";
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
	private CheckedAdapter adapter;
	private List<ScheduleProject> subProjectList;
	private ProjectsAdapter mProjectAdapter;
	private PopupWindow popupWindow;
	private MyAdapter mAdapter;
	@ViewById_Clickthis
	private Button date_select;
	
	private UHFReaderClass.OnEPCsListener listen;
	private Handler mHandler = null;
	
	private ArrayList<String> mEpcList;
	public List<SelfProjectRecoder> mItemList;
	@ViewById_Clickthis
	private Button search;
	@ViewById
	private PullToRefreshView mPullToRefreshView;
	
	private void initData() {
		mScan = new MyScan();
		list = new ArrayList<String>();
		adapter = new CheckedAdapter(getApplicationContext(), curriculum_vitae_query_gv_show,
				R.dimen.gridview_item_hight);
		curriculum_vitae_query_gv_show.setAdapter(adapter);
		
		title_bar_content.setText("待整改安全检查记录");
		
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
						mScan.setndCard(SelfCheckedActivity.this, mEpcList, ConstantValues.CURRICULUM_QUERY,
								ProjectCardBean.class);
					}
					break;
				}
				
			}
		};
		
		listen = new UHFReaderClass.OnEPCsListener() {
			// 对接收到EPC数据的处理
			// 由于onEPCsRecv是被封装在UHFReaderClass类的非UI线程调用的
			// 要实现把数据实时刷到界面，需通过Handle交回给UI线程处理。
			public void onEPCsRecv(ArrayList<String> EPCList) {
				mHandler.sendMessage(Message.obtain(mHandler, 0, EPCList));
			}
		};
		
		mAdapter = new MyAdapter(new ArrayList<ProjectCardBean>());
	}
	
	private MyScan mScan;
	
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
	
	@Override
	public void onBackPressed() {
		AlertDialogUtil.dissmissLoading();
		AnimateFirstDisplayListener.displayedImages.clear();
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
		mPullToRefreshView.setOnHeaderRefreshListener(this);
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
	
	private boolean isSearch = false;
	private int mCurrentPage = 2;
	private int mCurrentPageSearch = 1;
	
	private void searchCurriculum() {
		isSearch = true;
		mItemList.clear();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("projectCode", Config_values.PROJECT_CODE);
		hashMap.put("pageSize", "10");
		// selectTagCode="FD0000000000000000000011";
		hashMap.put("tagCode", selectTagCode);
		String name = curriculum_vitae_query_about.getText().toString().trim();
		List<ScheduleProject> list = currVitae == null ? new ArrayList<ScheduleProject>() : currVitae.projectNameList;
		
		for (ScheduleProject pro : list) {
			if (pro.name.equals(name)) {
				hashMap.put("scheduleId", String.valueOf(pro.scheduleId));
			}
		}
		// 09-18 21:49:35.955: D/SelfCheckedActivity(4062):
		// Url:http://182.92.76.78:8080/spms/service/queryNeedOverhaulSecurityCheckRecord.action
		// 09-18 22:29:14.250: D/SelfCheckedActivity(1769):
		// Url:http://192.168.1.105:8080/spms/service/queryNeedOverhaulSecurityCheckRecord.action
		// selectTagCodeFD0000000000000000000011 checkTime:
		
		//
		hashMap.put("checkTime", curriculum_vitae_query_et_select_date.getText().toString().trim());
		hashMap.put("page", "1");
		LogUtil.d(TAG, "Url:" + ConstantValues.CHECK_RECODE + "    selectTagCode" + selectTagCode + " checkTime:"
				+ curriculum_vitae_query_et_select_date.getText().toString().trim());
		HttpRequest request = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				LogUtil.d(TAG, "message:" + message);
				currVitae = StringUtils.parseSelfCurriculum(message);
				LogUtil.d(TAG, "currVitae:" + currVitae);
				if (currVitae.state != 1) {
					SystemNotification.showToast(SelfCheckedActivity.this, currVitae.errMessage);
					currVitae = null;
					return;
				}
				mItemList = currVitae.mItemList;
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(SelfCheckedActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
				AlertDialogUtil.dissmissLoading();
			}
		}, ConstantValues.CHECK_RECODE, hashMap);
		
		request.execute();
	}
	
	private String selectTagCode = "";
	
	private void showCardDialog() {
		View view = View.inflate(this, R.layout.card_num_show_dialog, null);
		new MyAlertDialogShow(this, view, mAdapter, "检查点列表", listen) {
			public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
				selectTagCode = (String) view.getTag(R.id.tag_search_tagCode);
				curriculum_vitae_query_card.setText(((TextView) view.findViewById(R.id.tv_str)).getText());
				logOutRFID();
			};
			
		}.show();
	}
	
	public class MyAdapter extends BaseAdapter {
		
		ArrayList<ProjectCardBean> mList;
		
		public MyAdapter(ArrayList<ProjectCardBean> mList) {
			this.mList = mList;
		}
		
		public void clearList() {
			mList.clear();
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mList == null ? 0 : mList.size();
		}
		
		public void addToList(ProjectCardBean pcb) {
			mList.add(pcb);
			notifyDataSetChanged();
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
				convertView = View.inflate(SelfCheckedActivity.this, R.layout.item_scan, null);
				holer.tv_str = (TextView) convertView.findViewById(R.id.tv_str);
				holer.ib_item_delete = (ImageButton) convertView.findViewById(R.id.ib_item_delete);
				convertView.setTag(holer);
			} else {
				holer = (Holder) convertView.getTag();
			}
			// ProjectCardBean
			convertView.setTag(R.id.tag_search_tagCode, mList.get(position).getTagCode());
			holer.tv_str.setText(mList.get(position).getName());
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
		//
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
		subProjectList = currVitae == null ? null : currVitae.projectNameList;
		
		ListView lv = new ListView(this);
		lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
		// 隐藏滚动条
		lv.setVerticalScrollBarEnabled(false);
		// 让listView没有分割线
		lv.setDividerHeight(0);
		lv.setDivider(null);
		lv.setOnItemClickListener(this);
		
		mProjectAdapter = new ProjectsAdapter();
		lv.setAdapter(mProjectAdapter);
		
		// SystemNotification.showPopupWindow(this, lv, curriculum_vitae_query_about,
		// curriculum_vitae_query_about.getWidth(), 200, 0, 0, this);
		
		popupWindow = new PopupWindow(lv, curriculum_vitae_query_about.getWidth(), 300);
		// 设置点击外部可以被关闭
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		// 设置popupWindow可以得到焦点
		popupWindow.setFocusable(true);
		
		popupWindow.showAsDropDown(curriculum_vitae_query_about, 0, 0); // 显示
	}
	
	class ProjectsAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return subProjectList == null ? 0 : subProjectList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return subProjectList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ProjectViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item_spinner_names, null);
				holder = new ProjectViewHolder();
				holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				holder.ibDelete = (ImageView) convertView.findViewById(R.id.ib_delete);
				convertView.setTag(holder);
			} else {
				holder = (ProjectViewHolder) convertView.getTag();
			}
			holder.tvName.setText(subProjectList.get(position).name);
			holder.ibDelete.setTag(position);
			holder.ibDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					subProjectList.remove(index);
					mProjectAdapter.notifyDataSetChanged();
					
					if (subProjectList.size() == 0) {
						popupWindow.dismiss();
					}
					
				}
			});
			return convertView;
		}
		
	}
	
	public class ProjectViewHolder extends BaseViewHolder {
		public TextView tvName;
		public ImageView ibDelete;
	}
	
	private List<String> getProjects() {
		List<String> mProjects = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			mProjects.add("土建工程");
		}
		return mProjects;
	}
	
	private class CheckedAdapter extends FrameAdapter {
		
		public CheckedAdapter(Context context, AbsListView alv) {
			super(context, alv, 0);
		}
		
		public CheckedAdapter(Context context, AbsListView alv, int resId) {
			super(context, alv, resId);
			
		}
		
		@Override
		public int getCount() {
			return mItemList == null ? 0 : mItemList.size();
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_curriculum_vitae_query);
			ViewHolder holder = (ViewHolder) convertView.getTag();
			SelfProjectRecoder recoder = mItemList.get(position);
			String time;
			if (recoder.checkTime != null && recoder.checkTime.length() > 10) {
				time = recoder.checkTime.substring(0, 10);
			} else {
				time = "未知";
			}
			holder.time.setText("时间：" + time);
			holder.time.setTextColor(Color.parseColor(recoder.color));
			if (StringUtils.isEmpty(recoder.checkPersonName)) {
				holder.name.setText("巡检人：未知");
			} else {
				holder.name.setText("巡检人：" + recoder.checkPersonName);
			}
			
			holder.name.setTextColor(Color.parseColor(recoder.color));
			
			if (StringUtils.isEmpty(recoder.scheduleName)) {
				holder.project_name.setText("未知");
			} else {
				holder.project_name.setText(recoder.scheduleName);
			}
			holder.project_name.setTextColor(Color.parseColor(recoder.color));
			
			ImageLoader.getInstance().displayImage(ConstantValues.CLIENT_URL_PRE + recoder.picPath,
					holder.item_curriculum_vitae_query_iv_show, diOptions, afdListener);
			
			return convertView;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
	}
	
	private static class ViewHolder extends BaseViewHolder {
		ImageView item_curriculum_vitae_query_iv_show;
		TextView item_curriculum_vitae_query_tv_show;
		TextView project_name;
		TextView name;
		TextView time;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent instanceof ListView) {
			curriculum_vitae_query_about.setText(subProjectList.get(position).name);
			popupWindow.dismiss();
		} else {
			getSelfData(mItemList.get(position).id, position);
		}
	}
	
	private void getSelfData(int id, final int position) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("recordId", id + "");
		HttpRequest request = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				if (!StringUtils.isEmpty(message)) {
					try {
						JSONObject obj = new JSONObject(message);
						int state = obj.getInt("state");
						if (state == 1) {
							org.json.JSONArray arr = obj.getJSONArray("entryList");
							SelfProjectRecoder recoder = mItemList.get(position);
							List<SecurityCheckEntry> parseArray = JSON.parseArray(arr.toString(),
									SecurityCheckEntry.class);
							for (SecurityCheckEntry s : parseArray) {
								s.result = 0;
							}
							recoder.mSecurityList = parseArray;
							startActivity(SelfNeedCheckedProjectActivity.class, "data", recoder);
							
						} else {
							SystemNotification.showToast(SelfCheckedActivity.this, obj.getString("errMessage"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				} else {
					SystemNotification.showToast(SelfCheckedActivity.this, "请求失败!");
				}
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(SelfCheckedActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
				AlertDialogUtil.dissmissLoading();
			}
		}, ConstantValues.ITEM_SELF_CURR, map);
		request.execute();
		
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		
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
	
	private AnimateFirstDisplayListener afdListener;
	private DisplayImageOptions diOptions;
	
	@Override
	public void init() {
		setListener();
		getData();
		
		afdListener = new AnimateFirstDisplayListener();
		
		diOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub).showImageOnFail(R.drawable.ic_stub).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(10)).build();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}
	
	private void getData() {
		Intent intent = getIntent();
		currVitae = (SelfCurriculumViateQueryBean) intent.getSerializableExtra("data");
		mItemList = currVitae == null ? new ArrayList<SelfProjectRecoder>() : currVitae.mItemList;
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.selfmanagerproject);
	}
	
	/**
	 * 显示时间选择器
	 */
	final Calendar mDate = Calendar.getInstance();
	private SelfCurriculumViateQueryBean currVitae;
	private AlertDialog dialog;
	
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
	public void onHeaderRefresh(PullToRefreshView view) {
		
		// 设置三个搜索框为空
		curriculum_vitae_query_card.setText("");
		curriculum_vitae_query_about.setText("");
		curriculum_vitae_query_et_select_date.setText("");
		
		isSearch = false;
		mCurrentPage = 2;
		mCurrentPageSearch = 1;
		
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "9");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				currVitae = StringUtils.parseSelfCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(SelfCheckedActivity.this, currVitae.errMessage);
					return;
				}
				mItemList.clear();
				mItemList = currVitae.mItemList;
				adapter.notifyDataSetChanged();
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void start() {
			}
			
			@Override
			public void error(String message) {
				mPullToRefreshView.onHeaderRefreshComplete();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.CHECK_RECODE, mHashMap);
		mRequest.execute();
		
	}
	
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		
		if (isSearch) {
			if (mItemList.size() == currVitae.totalCount) {
				SystemNotification.showToast(SelfCheckedActivity.this, "没有更多数据了...");
				mPullToRefreshView.onFooterRefreshComplete();
				return;
			}
			
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("projectCode", Config_values.PROJECT_CODE);
			hashMap.put("pageSize", "9");
			hashMap.put("tagCode", selectTagCode);
			String name = curriculum_vitae_query_about.getText().toString().trim();
			List<ScheduleProject> list = currVitae == null ? new ArrayList<ScheduleProject>()
					: currVitae.projectNameList;
			
			for (ScheduleProject pro : list) {
				if (pro.name.equals(name)) {
					hashMap.put("scheduleId", String.valueOf(pro.scheduleId));
				}
			}
			
			hashMap.put("checkTime", curriculum_vitae_query_et_select_date.getText().toString().trim());
			hashMap.put("page", mCurrentPageSearch + "");
			
			HttpRequest request = new HttpRequest(this, new HttpRequestCallBack() {
				
				@Override
				public void success(String message) {
					currVitae = StringUtils.parseSelfCurriculum(message);
					if (currVitae.state != 1) {
						SystemNotification.showToast(SelfCheckedActivity.this, currVitae.errMessage);
						return;
					}
					mItemList = currVitae.mItemList;
					adapter.notifyDataSetChanged();
					mPullToRefreshView.onFooterRefreshComplete();
					if (mItemList.size() == currVitae.totalCount) {
						// SystemNotification.showToast(CurriculumVitae_Query_Activity.this, "没有更多数据了...");
					} else {
						mCurrentPageSearch++;
					}
				}
				
				@Override
				public void start() {
				}
				
				@Override
				public void error(String message) {
				}
				
				@Override
				public void cancel() {
				}
			}, ConstantValues.CHECK_RECODE, hashMap);
			
			request.execute();
		} else {
			if (mItemList.size() == currVitae.totalCount) {
				SystemNotification.showToast(SelfCheckedActivity.this, "没有更多数据了...");
				mPullToRefreshView.onFooterRefreshComplete();
				return;
			}
			
			HashMap<String, String> mHashMap = new HashMap<String, String>();
			mHashMap.put("projectCode", Config_values.PROJECT_CODE);
			mHashMap.put("pageSize", "9");
			mHashMap.put("page", String.valueOf(mCurrentPage));
			HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
				
				@Override
				public void success(String message) {
					currVitae = StringUtils.parseSelfCurriculum(message);
					if (currVitae.state != 1) {
						SystemNotification.showToast(SelfCheckedActivity.this, currVitae.errMessage);
						return;
					}
					List<SelfProjectRecoder> list = currVitae.mItemList;
					mItemList.addAll(list);
					adapter.notifyDataSetChanged();
					mPullToRefreshView.onFooterRefreshComplete();
					if (mItemList.size() == currVitae.totalCount) {
						// SystemNotification.showToast(CurriculumVitae_Query_Activity.this, "没有更多数据了...");
					} else {
						mCurrentPage++;
					}
				}
				
				@Override
				public void start() {
				}
				
				@Override
				public void error(String message) {
					mPullToRefreshView.onFooterRefreshComplete();
				}
				
				@Override
				public void cancel() {
				}
			}, ConstantValues.CHECK_RECODE, mHashMap);
			mRequest.execute();
			
		}
		
	}
	
	// private class CurriculumVitaeAsync extends NetConnectionAsync<String, Void, F>(){
	//
	// }
}
