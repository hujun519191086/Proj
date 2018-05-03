package com.qc188.com.ui;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.IsNetwork;
import com.qc188.com.util.LogUtil;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnTabChangeListener {


	private static final String TAG = "MainActivity";
	int currentTab = 0;
	private Handler handler;

	private Class[] itemClazzs = { HomeActivity.class, TalkActivity.class, SearchActivity.class, DepreciateActivity.class, SettingActivity.class };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slpash);
		ConstantValues.homeUrlPreference = getSharedPreferences(ConstantValues.HOME_PREFERENCE_NAME, Context.MODE_PRIVATE);
		handler = new Handler();
		SharedPreferences sp = getSharedPreferences("prop", Context.MODE_PRIVATE);
		boolean isNoPic = sp.getBoolean("noPic", false);
		ConstantValues.NO_PIC = isNoPic;
		if (IsNetwork.currentConnectNet(getApplicationContext())) {
			if (!ConstantValues.NO_PIC) {
				showCloseDownUrlAlert();
				return;
			}
		} else {
		}
		toMain();
	}

	private void toMain() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						setContentView(R.layout.activity_main);
						init();
						setTabs();
					}
				});

			}
		}, 2000);
	}

	public void destroyData() {
		ConstantValues.homeUrlPreference = null;
		DensityUtil.context = null;
	}

	private void init() {
		DensityUtil.densityDpi = getResources().getDisplayMetrics().densityDpi;
		ConstantValues.FILE_PATH = getFilesDir().toString();
	}

	private void setTabs() {
		String[] bottom_list = getResources().getStringArray(R.array.tab_bottom_list);
		for (int i = 0; i < bottom_list.length; i++) {
			addTab(bottom_list[i], getResources().getIdentifier("tab_" + (i + 1), "drawable", getPackageName()), itemClazzs[i]);

		}
	}

	protected void onPause() {
		super.onPause();
		if (AnimCommon.in != 0 && AnimCommon.out != 0) {
			super.overridePendingTransition(AnimCommon.in, AnimCommon.out);
			AnimCommon.clear();
		}
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}


	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			showQuitAlert();
			return false;
		} else {
			return super.dispatchKeyEvent(event);

		}
	}

	@Override
	protected void onResume() {
		overridePendingTransition(R.anim.state_translate, R.anim.toright_out_translate);
		super.onResume();
	}

	public void setCurrentTab(int currentTab) {
		LogUtil.d(TAG, "currentTAB:" + currentTab + "....+" + getCurrentActivity());
		this.currentTab = currentTab;

	}

	private void showCloseDownUrlAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(R.string.notWifi)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						ConstantValues.NO_PIC = true;
					}
				}).setNegativeButton(android.R.string.no, null);
		AlertDialog diaLog = builder.create();
		diaLog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				toMain();
			}
		});
		diaLog.show();

	}

	private void showQuitAlert() {
		new AlertDialog.Builder(this).setMessage(R.string.info_quit).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		}).setNegativeButton(android.R.string.no, null).show();
	}

	public static class AnimCommon {

		public static int in = 0;
		public static int out = 0;

		public static void set(int enter, int out) {
			in = enter;
			AnimCommon.out = out;
		}

		public static void clear() {
			in = 0;
			out = 0;
		}
	}

	@Override
	public void onTabChanged(String tabId) {

	}

}