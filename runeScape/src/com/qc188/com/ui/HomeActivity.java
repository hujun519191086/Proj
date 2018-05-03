package com.qc188.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qc188.com.R;
import com.qc188.com.bean.HomeItemBean;
import com.qc188.com.bean.MsgContentBean;
import com.qc188.com.engine.MainHomeEngine;
import com.qc188.com.engine.impl.MainHomeEngineImpl;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.MainActivity.AnimCommon;
import com.qc188.com.ui.adapter.HomeAdapter;
import com.qc188.com.ui.adapter.SliderAdapter;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.Utility;

@SuppressLint("UseSparseArrays")
public class HomeActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = "HomeActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		CompairManager.getManger().initMap(this);
		mhEngine = InstanceFactory.getInstances(MainHomeEngine.class);
		setTitleContent(R.string.mssage);
		findViews();
		init();
	}

	private ImageView cursor;
	private int offset = 0;
	private int bmpW;
	private ViewPager vp_home_vd;
	private int baseWidth = 0;
	private int margin = 0;
	private RelativeLayout rl_home_titleSelect;
	private RelativeLayout rl_maintitle_textbackground;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onResume() {

		super.onResume();
		LogUtil.d(TAG, "resumeReload:" + resumeReload);
		if (resumeReload) {
			selectTo(lastSelect);
		}
	}

	private ArrayList<TextView> textLists;

	private void findViews() {

		textLists = new ArrayList<TextView>();
		vp_home_vd = (ViewPager) findViewById(R.id.vp_home_adv);
		// 设置幻灯片比例 3:2
		LinearLayout.LayoutParams adv_params = new LinearLayout.LayoutParams(-1, DensityUtil.getWidthPixels() / 3 * 2);
		RelativeLayout rl_vp_home_adv_border = (RelativeLayout) findViewById(R.id.rl_vp_home_adv_border);
		rl_vp_home_adv_border.setLayoutParams(adv_params);

		rl_home_titleSelect = (RelativeLayout) findViewById(R.id.rl_home_titleSelect);
		rl_maintitle_textbackground = (RelativeLayout) findViewById(R.id.rl_maintitle_textbackground);
		sv_allSv = (ScrollView) findViewById(R.id.sv_allSv);

		rl_main_contentControl = (RelativeLayout) findViewById(R.id.rl_main_contentControl);

		ll_home_advIndex = (LinearLayout) findViewById(R.id.ll_home_advIndex);

		lv_newest_listview = (ListView) findViewById(R.id.lv_newest_listview);
		rl_home_clickRefresh = (RelativeLayout) findViewById(R.id.rl_home_clickRefresh);
		ImageView iv_home_clickRefresh = (ImageView) findViewById(R.id.iv_home_clickRefresh);

		// 四个字.
		tv_home_title1 = (TextView) findViewById(R.id.tv_home_title1);
		TextView tv_home_title2 = (TextView) findViewById(R.id.tv_home_title2);
		TextView tv_home_title3 = (TextView) findViewById(R.id.tv_home_title3);
		TextView tv_home_title4 = (TextView) findViewById(R.id.tv_home_title4);
		TextView tv_home_title5 = (TextView) findViewById(R.id.tv_home_title5);
		TextView tv_home_title6 = (TextView) findViewById(R.id.tv_home_title6);
		textLists.add(tv_home_title1);
		textLists.add(tv_home_title2);
		textLists.add(tv_home_title3);
		textLists.add(tv_home_title4);
		textLists.add(tv_home_title5);
		textLists.add(tv_home_title6);

		iv_home_clickRefresh.setOnClickListener(this);

		findViewById(R.id.tv_home_title2).setOnClickListener(this);
		findViewById(R.id.tv_home_title3).setOnClickListener(this);
		findViewById(R.id.tv_home_title4).setOnClickListener(this);
		findViewById(R.id.tv_home_title5).setOnClickListener(this);
		findViewById(R.id.tv_home_title6).setOnClickListener(this);
	}

	private void init() {

		ll_home_advIndex.removeAllViews();
		asyncList = new ArrayList<BaseAsync>();
		allViews = new HashMap<Integer, View>();
		tv_home_title1.setOnClickListener(this);
		tv_home_title1.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				rl_home_titleSelect.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				LayoutParams lp = new RelativeLayout.LayoutParams(tv_home_title1.getWidth(), tv_home_title1.getHeight());
				lp.leftMargin = tv_home_title1.getLeft();
				lp.rightMargin = tv_home_title1.getRight();
				lp.addRule(RelativeLayout.CENTER_VERTICAL);
				rl_maintitle_textbackground.setLayoutParams(lp);
				baseWidth = findViewById(R.id.tv_home_title1).getWidth();
				rl_home_titleSelect.setVisibility(View.VISIBLE);
				rl_maintitle_textbackground.setBackgroundResource(R.drawable.home_title_item_color);
				margin = tv_home_title1.getLeft() + 1;
			}
		});
		cursor = new ImageView(getApplicationContext());
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.page_item1).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 3 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);

		HomeListAsync ha = new HomeListAsync(HomeActivity.this);
		String json = ConstantValues.homeUrlPreference.getString(ConstantValues.HOME_ITEM_URL, "-1");
		ArrayList<HomeItemBean> list = MainHomeEngineImpl.getItemListFromString(json);
		ha.onPost(list);
		resumeReload = false;
		// 加载列表
		ha.execute();
		loadADV();
	}

	private void loadADV() {
		sliderAdapter = new SliderAdapter(getApplicationContext(), vp_home_vd, ll_home_advIndex) {
			public void onClick(View v) {
				MsgContentBean mcBean = ((SliderAdapter) vp_home_vd.getAdapter()).getMsgContentBean();
				if (mcBean == null) {
					return;
				}
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ContentActivity.class);
				intent.putExtra(ConstantValues.FROMEWHERE, ConstantValues.FROM_ADV);

				intent.putExtra(ConstantValues.TO_CONTENT_TAG, mcBean);
				AnimCommon.set(R.anim.froomright_in_translate, R.anim.state_translate);
				startActivity(intent);
			}
		};
		vp_home_vd.setAdapter(sliderAdapter);

		Timer timer = new Timer(false);
		timer.schedule(new TimerTask() {
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						if (sliderAdapter.getCount() != 0) {
							vp_home_vd.setCurrentItem((vp_home_vd.getCurrentItem() + 1) % sliderAdapter.getCount());
						}
					}
				});
			}
		}, 3000, 3000);
	}

	private int currentItem = 1;
	private int ll_state = 0;
	private TextView tv_home_title1;
	private RelativeLayout rl_main_contentControl;
	private LinearLayout ll_home_advIndex;
	private ListView lv_newest_listview;
	private RelativeLayout rl_home_clickRefresh;

	private ListView lv;
	private TextView tv;

	private HashMap<Integer, View> allViews;

	public void onClick(View v) {
		ImageLoader.getInstance().stop();
		if (lv == null) {
			lv = new ListView(getApplicationContext());
			tv = new TextView(getApplicationContext());
			tv.setBackgroundColor(Color.BLACK);
		}
		int position = 0;
		switch (v.getId()) {
		case R.id.tv_home_title1:
			if (0 != ll_state) {
				startTranslateAnimation(proIndex, 0, 0);
				position = 0;
				ll_state = 0;
				selectTo(position);
			}
			break;
		case R.id.tv_home_title2:
			if (baseWidth != ll_state) {
				startTranslateAnimation(proIndex, (baseWidth + margin), 1);
				position = 1;
				ll_state = baseWidth;
				selectTo(position);
			}
			break;
		case R.id.tv_home_title3:
			if (baseWidth * 2 != ll_state) {
				startTranslateAnimation(proIndex, (baseWidth + margin) * 2, 2);
				position = 2;
				ll_state = baseWidth * 2;
				selectTo(position);
			}
			break;
		case R.id.tv_home_title4:
			if (baseWidth * 3 != ll_state) {
				startTranslateAnimation(proIndex, (baseWidth + margin) * 3, 3);
				position = 3;
				ll_state = baseWidth * 3;
				selectTo(position);
			}
			break;
		case R.id.tv_home_title5:
			LogUtil.d(TAG, " click 5:" + ll_state + ",,,,,:" + baseWidth * 4);
			if (baseWidth * 4 != ll_state) {
				startTranslateAnimation(proIndex, (baseWidth + margin) * 4, 4);
				position = 4;
				ll_state = baseWidth * 4;
				selectTo(position);
			}
			break;

		case R.id.tv_home_title6:
			LogUtil.d(TAG, " click 5:" + ll_state + ",,,,,:" + baseWidth * 5);
			if (baseWidth * 5 != ll_state) {
				startTranslateAnimation(proIndex, (baseWidth + margin) * 5, 5);
				position = 5;
				ll_state = baseWidth * 5;
				selectTo(position);
			}
			break;
		case R.id.iv_home_clickRefresh:
			new HomeListAsync(HomeActivity.this).execute();
			hideRefresh();
			break;
		}

	}

	private int proIndex = 0;

	private void startTranslateAnimation(int fromX, int toX, final int position) {
		TranslateAnimation ta = new TranslateAnimation(fromX, toX, 0, 0);
		proIndex = toX;
		// ta.initialize(fromX, toX, 0, 0);
		ta.setDuration(200);
		ta.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				for (int i = 0; i < textLists.size(); i++) {
					if (position == i) {
						textLists.get(i).setTextColor(Color.WHITE);
					} else {
						textLists.get(i).setTextColor(Color.BLACK);
					}
				}
			}
		});
		ta.setFillAfter(true);
		rl_maintitle_textbackground.startAnimation(ta);
	}

	private int lastSelect = 0;
	private MainHomeEngine mhEngine;
	private ScrollView sv_allSv;
	private ArrayList<BaseAsync> asyncList;
	private SliderAdapter sliderAdapter;

	private boolean resumeReload = false;

	private void selectTo(int position) {
		resumeReload = false;
		View view = rl_main_contentControl.getChildAt(0);

		if (allViews.get(lastSelect) == null) {
			allViews.put(lastSelect, view);
		}
		View v = null;
		rl_main_contentControl.removeAllViews();
		view = allViews.get(position);
		if (view == null || view instanceof ListView) {

			ListView lv1 = new ListView(getApplicationContext());
			lv1.setDividerHeight(1);

			NewsAsync na = new NewsAsync(this, lv1);
			asyncList.add(na);
			String url = null;
			String urlContent = null;
			String url_from = null;
			switch (position) {
			case 1:
				url = ConstantValues.HOME_EVALUATION_URL; // 评测
				urlContent = ConstantValues.HOME_EVALUATION_URL_Content;
				url_from = ConstantValues.FROMEVALUATION + "#评测";
				break;
			case 2:
				url = ConstantValues.CAR_TYPE_URL; // 对比y,
				urlContent = ConstantValues.HOME_duibi_URL_Content;
				url_from = ConstantValues.FROMEduibi + "#对比";
				break;
			case 3:
				url = ConstantValues.HOME_GUID_URL; // 导购
				urlContent = ConstantValues.HOME_GUID_URL_Content;
				url_from = ConstantValues.FROMGUID + "#导购";
				break;

			case 4:
				url = ConstantValues.HOME_KNOWAGE_URL; // 知识
				urlContent = ConstantValues.HOME_KNOWAGE_URL_Content;
				url_from = ConstantValues.FROMKNOWAGE + "#知识";
				break;

			case 5:
				url = ConstantValues.HOME_PRAISE_URL; // 口碑
				urlContent = ConstantValues.HOME_PRAISE_URL_Content;
				url_from = ConstantValues.FROMPRAISE + "#口碑";
				break;
			}
			String json = ConstantValues.homeUrlPreference.getString(url, "-1");
			if (json != "-1") {
				ArrayList<HomeItemBean> list = MainHomeEngineImpl.getItemListFromString(json);
				LogUtil.d(TAG, "json in bendi:" + list + "    Json:" + json);
				na.onPost(list);
			}
			na.execute(url, urlContent, url_from);
			view = lv1;
		} else {
			HomeListAsync ha = new HomeListAsync(HomeActivity.this);
			String json = ConstantValues.homeUrlPreference.getString(ConstantValues.HOME_ITEM_URL, "-1");
			ArrayList<HomeItemBean> list = MainHomeEngineImpl.getItemListFromString(json);
			ha.onPost(list);
			// 加载列表
			ha.execute();
		}
		LogUtil.d(TAG, "addview");
		rl_main_contentControl.addView(view);
		lastSelect = position;

	}

	private class HomeListAsync extends BaseAsync<Void, ArrayList<HomeItemBean>> {
		private HomeAdapter ha;

		public HomeListAsync(Activity activity) {
			super(activity);
		}

		@Override
		protected ArrayList<HomeItemBean> doInBackground(Void... params) {
			MainHomeEngine mhe = InstanceFactory.getInstances(MainHomeEngine.class);
			return mhe.getHomeItemList_async();
		}

		@Override
		public void onPost(ArrayList<HomeItemBean> result) {
			if (result == null || result.size() < 0) {
				showRefresh();
				return;
			}
			resumeReload = false;
			hideRefresh();
			ha = new HomeAdapter(getApplicationContext(), result, lv_newest_listview);
			lv_newest_listview.setAdapter(ha);
			Utility.setListViewHeightBasedOnChildren(lv_newest_listview);
			lv_newest_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					LogUtil.d(TAG, "item click");
					startActivityToContent(String.valueOf(view.getTag()));
				}
			});
			sv_allSv.scrollTo(0, 0);
		}

		@Override
		public void clear() {
			if (ha != null) {
				ha.clear();
			}
		}
	}

	public void startActivityToContent(String msg_id) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), ContentActivity.class);
		intent.putExtra(ConstantValues.TO_CONTENT_TAG, msg_id);
		intent.putExtra(ConstantValues.FROMEWHERE, ConstantValues.FROMHOME);
		intent.putExtra(ConstantValues.TITLE_NAME, "新闻");
		AnimCommon.set(R.anim.froomright_in_translate, R.anim.state_translate);
		startActivity(intent);
	}

	public void showRefresh() {

		resumeReload = true;
		LogUtil.d(TAG, "showRefresh:resumeReload:" + resumeReload);
		rl_home_clickRefresh.setVisibility(View.VISIBLE);
		sv_allSv.setVisibility(View.GONE);
	}

	public void hideRefresh() {
		sv_allSv.setVisibility(View.VISIBLE);
		rl_home_clickRefresh.setVisibility(View.GONE);
	}

	public class NewsAsync extends BaseAsync<String, ArrayList<HomeItemBean>> {
		private ListView lv;
		private String url;
		private String from;
		private HomeAdapter haNews;

		public NewsAsync(Activity activity, ListView lv) {
			super(activity);
			this.lv = lv;
		}

		public void onPost(ArrayList<HomeItemBean> result) {
			LogUtil.d(TAG, "post:" + result);
			if (result != null && result.size() > 0) {
				hideRefresh();
				haNews = new HomeAdapter(getApplicationContext(), result, lv);
				lv.setAdapter(haNews);
				Utility.setListViewHeightBasedOnChildren(lv);
				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						LogUtil.d(TAG, "item click");
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(), ContentActivity.class);
						intent.putExtra(ConstantValues.TO_CONTENT_TAG, String.valueOf(view.getTag()));
						intent.putExtra(ConstantValues.FROMEWHERE, from);

						intent.putExtra(ConstantValues.TITLE_NAME, contentTitle);
						AnimCommon.set(R.anim.froomright_in_translate, R.anim.state_translate);
						LogUtil.d(TAG, "start activity");
						startActivity(intent);
						LogUtil.d(TAG, "start activity end");
						// new LoadContentAsync(HomeActivity.this).execute(
						// String.valueOf(view.getTag()), url, from, "0");
					}
				});
				sv_allSv.scrollTo(0, 0);
				resumeReload = false;
			} else {
				showRefresh();
			}
		}

		private String contentTitle = "";

		@Override
		protected ArrayList<HomeItemBean> doInBackground(String... params) {
			LogUtil.d(TAG, "正在联网获取!!!!!!!!!");
			MainHomeEngine mhe = InstanceFactory.getInstances(MainHomeEngine.class);
			url = params[1];
			from = params[2].split(ConstantValues.SPLICT)[0];
			contentTitle = params[2].split(ConstantValues.SPLICT)[1];
			return mhe.getItemList_async(params[0]);
		}

		@Override
		public void clear() {
			if (haNews != null) {
				haNews.clear();
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		for (int i = 0; i < asyncList.size(); i++) {
			asyncList.get(i).clear();
		}
	}
}
