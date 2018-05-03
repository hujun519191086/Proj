package cn.tlrfid.framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.factory.ValuesFactory;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.ProjectManageActivity;
import cn.tlrfid.view.QualityManagerActivity;
import cn.tlrfid.view.QualitySelfeActivity;
import cn.tlrfid.view.WarehouseManagerActivity;
import cn.tlrfid.view.adapter.LeftListAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends Activity implements OnClickListener {

	private static final String TAG = "BaseActivity";

	public LinearLayout base_title_layout;
	private ImageView title_bar_logo;
	protected TextView title_bar_content;

	private boolean isInMainActivity = false;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public void setActionBarTitle(String actionBarTitle) {
		title_bar_content.setText(actionBarTitle);
	}

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isInMainActivity = false;
		onCreateView();
		ValuesFactory.autoSetValues(this);
		DensityUtil.initAllValue(getApplicationContext(), this);
		init();
		SharedPreferences sp = getSharedPreferences("login_info", MODE_PRIVATE);
		String s = sp.getString("project_no", "35kVBDZ");
		Config_values.PROJECT_CODE = s;
		Config_values.initProjectCode(s);
		// ConstantValues.initClientPre(sp.getString("ip",
		// "http://192.168.1.105:8080/spms"));
		ConstantValues.initClientPre(sp.getString("ip", "http://182.92.76.78:8080/spms"));
	}

	public void startActivity(Class<? extends BaseActivity> clazz) {
		startActivity(clazz, null, null);
	}

	public void startActivity(Class<? extends BaseActivity> clazz, String key, Serializable values) {
		startActivity(clazz, null, key, values);
	}

	private HashMap<String, Serializable> intentMap;

	public void setIntentValues(String key, Serializable values) {
		if (intentMap == null) {
			intentMap = new HashMap<String, Serializable>();
		}

		intentMap.put(key, values);
	}

	public void startActivity(Class<? extends BaseActivity> clazz, String actionbarname, String key, Serializable values) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		if (key != null) {
			intent.putExtra(key, values);
		}

		if (!TextUtils.isEmpty(actionbarname)) {
			intent.putExtra(ConstantValues.ACTION_BAR_NAME, actionbarname);
		}

		if (intentMap != null && intentMap.size() > 0) {
			Set<String> set = intentMap.keySet();
			for (String s : set) {
				intent.putExtra(s, intentMap.get(s));
			}
		}
		intent.setClass(getApplicationContext(), clazz);
		startActivity(intent);
	}

	public void startActivity(Class<? extends BaseActivity> clazz, String actionbarname) {
		startActivity(clazz, actionbarname, null, null);
	}

	public void startActivity(Class<? extends BaseActivity> clazz, int... flag) {
		Intent intent = new Intent();
		for (int i = 0; i < flag.length; i++) {
			intent.addFlags(flag[i]);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.setClass(getApplicationContext(), clazz);
		startActivity(intent);
	}

	/**
	 * 在这方法内部,已经将全部有注解的成员变量赋值.
	 */
	public abstract void init();

	/**
	 * 负责setContentView
	 */
	public abstract void onCreateView();

	@Override
	public void setContentView(int layoutResID) {
		View main_back_view = getLayoutInflater().inflate(R.layout.main_back_view, null);
		FrameLayout base_fl_content = (FrameLayout) main_back_view.findViewById(R.id.base_fl_content);
		View.inflate(this, layoutResID, base_fl_content);
		super.setContentView(main_back_view);
		initTitleView();

	}

	@Override
	public void onBackPressed() {
		if (isInMainActivity) {
			if (SystemNotification.isToashShowing()) {
				super.onBackPressed();
			} else {
				SystemNotification.showToast(getApplicationContext(), "再次按返回键退出!");
			}
		} else {

			super.onBackPressed();
		}
	}

	private void initTitleView() {
		base_title_layout = (LinearLayout) findViewById(R.id.base_title_layout);
		title_bar_logo = (ImageView) findViewById(R.id.title_bar_logo);
		title_bar_content = (TextView) findViewById(R.id.title_bar_content);
	}

	/**
	 * 没有actionbar
	 * 
	 * @param layoutResID
	 */
	public void setContentViewNoActionBar(int layoutResID) {
		super.setContentView(layoutResID);
	}

	private ArrayList<Pair<View, TextView>> titleList;

	private ListView left_lv;

	private void changeTitleModule(int position) {
		isInMainActivity = true;
		if (titleList == null) {
			titleList = new ArrayList<Pair<View, TextView>>();
			View view = findViewById(R.id.header_tv_pro_text);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(ProjectManageActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION);
					finish();
				}
			});
			titleList.add(new Pair<View, TextView>(view, (TextView) findViewById(R.id.header_tv_pro_bottom_line)));

			view = findViewById(R.id.header_tv_selfe_text);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(QualitySelfeActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION);
					finish();
				}
			});
			titleList.add(new Pair<View, TextView>(view, (TextView) findViewById(R.id.header_tv_selfe_bottom_line)));

			view = findViewById(R.id.header_tv_hard_quert);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(QualityManagerActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION);
					finish();
				}
			});
			titleList.add(new Pair<View, TextView>(view, (TextView) findViewById(R.id.header_tv_quert_bottom_line)));

			view = findViewById(R.id.header_tv_hard_warehouse);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(WarehouseManagerActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION);
					finish();
				}
			});
			titleList.add(new Pair<View, TextView>(view, (TextView) findViewById(R.id.header_tv_warehouse_bottom_line)));

		}
		for (int i = 0; i < titleList.size(); i++) {
			if (i == position) {
				titleList.get(i).first.setClickable(false);
				titleList.get(i).second.setVisibility(View.VISIBLE);
			} else {
				titleList.get(i).first.setClickable(true);
				titleList.get(i).second.setVisibility(View.INVISIBLE);
			}
		}

	}

	/**
	 * 左边列表按钮选择
	 * 
	 * @param lle
	 * @param oiclitener
	 */
	public void setLeftListType(LeftListEnum lle, OnItemClickListener oiclitener) {

		left_lv = (ListView) findViewById(R.id.left_lv);
		LeftListAdapter aa = null;
		switch (lle) {
		case PROJECT_MANAGER:
			aa = new LeftListAdapter(getApplicationContext(), left_lv, ConstantValues.PROJECT_MANAGER_LEFT);
			break;
		case SAFE:
			aa = new LeftListAdapter(getApplicationContext(), left_lv, ConstantValues.SAEFE_LEFT);
			break;
		case QUALITY_MANAGER:
			aa = new LeftListAdapter(getApplicationContext(), left_lv, ConstantValues.QUALITY_MANAGER_LEFT);
			break;
		case WAREHOUSE:
			aa = new LeftListAdapter(getApplicationContext(), left_lv, ConstantValues.WAREHOUSE_MANAGER_LEFT);
			break;
		}
		overridePendingTransition(0, 0);
		changeTitleModule(lle.values);
		left_lv.setAdapter(aa);
		left_lv.setOnItemClickListener(oiclitener);

		left_lv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				left_lv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				left_lv.getChildAt(0).setBackgroundResource(R.color.projectmanager_left_list_select);
				((TextView) left_lv.getChildAt(0)).setTextColor(Color.WHITE);
			}
		});
	}

	public enum LeftListEnum {
		PROJECT_MANAGER(0), SAFE(1), QUALITY_MANAGER(2), WAREHOUSE(3);

		int values = 0;

		private LeftListEnum(int value) {
			values = value;
		}
	}

	@Override
	public void finish() {

		super.finish();
	}
}
