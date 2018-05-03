package com.qc188.com.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.CompairBean;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.adapter.CompairAdapter;
import com.qc188.com.ui.control.CompairContentView;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.LogUtil;
import com.qc188.com.util.SystemNotification;

public class CompairActivity extends BaseActivity implements OnScrollListener, OnCheckedChangeListener, OnClickListener {
	private CompairContentView ccView;
	private ArrayList<Pair<String, String>> selectIdList;
	private static final String TAG = "CompairActivity";

	private CompairBean cBean;
	private ExpandableListView elv_compairContent;
	private TextView tv_compairContent_flower;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(getApplicationContext(), R.layout.compair, null);
		setContentView(view);
		setTitleContent("车型对比");
		visibleBackButton();

		selectIdList = CompairManager.getManger().getSelectId();
		if (selectIdList == null) {
			SystemNotification.showToast(getApplicationContext(), "没有添加对比车型!");
			return;
		}

		tw_compair_titleTab = findById(R.id.tw_compair_titleTab); // 顶部的tab
		ccView = new CompairContentView(tw_compair_titleTab, new TabItemClick(), true);
		// 01-22 09:49:04.283: D/UniversalEngineImpl(1818):
		// url:http://www.qc188.com/app/carv.asp?id=3985 json:{

		TextView tv_compairContent_leftTopName = findById(R.id.tv_compairContent_leftTopName);// 名字
		TextView tv_compairContent_rightTopName = findById(R.id.tv_compairContent_rightTopName); // 名字
		tv_compairContent_leftTopName.setText(selectIdList.get(0).second);
		tv_compairContent_rightTopName.setText(selectIdList.get(1).second);

		elv_compairContent = findById(R.id.elv_compairContent); // 滚动内容

		elv_compairContent.setDividerHeight(0);
		tv_compairContent_flower = findById(R.id.tv_compairContent_flower); // 滚动内容上面的灰条
		tw_compair_showTitleText = findById(R.id.tw_compair_showTitleText); // 顶部文字.
		tw_compair_showTitleText.setOnClickListener(this);

		cb_compairContent_showAll = findById(R.id.cb_compairContent_showAll);
		cb_compairContent_showdif = findById(R.id.cb_compairContent_showdif); // CheckBox

		findViewById(R.id.tv_compairContent_leftDelete).setOnClickListener(this);
		findViewById(R.id.tv_compairContent_rightDelete).setOnClickListener(this); // 删除按钮

		tv_compairContent_leftAddCar = findById(R.id.tv_compairContent_leftAddCar);
		tv_compairContent_rightAddCar = findById(R.id.tv_compairContent_rightAddCar);
		tv_compairContent_leftcar = findById(R.id.tv_compairContent_leftcar);
		tv_compairContent_rightcar = findById(R.id.tv_compairContent_rightcar);// 左右边

		cb_compairContent_showAll.setTag(cb_compairContent_showdif);
		cb_compairContent_showdif.setTag(cb_compairContent_showAll);

		cb_compairContent_showAll_txt = findById(R.id.cb_compairContent_showAll_txt);
		cb_compairContent_showdif_txt = findById(R.id.cb_compairContent_showdif_txt);

		cb_compairContent_showAll.setOnCheckedChangeListener(this);
		cb_compairContent_showdif.setOnCheckedChangeListener(this);
		cb_compairContent_showAll.setChecked(true);
		cb_compairContent_showdif.setChecked(false);

		elv_compairContent.setGroupIndicator(null);
		elv_compairContent.setOnScrollListener(this);
		new CompairMsgAysnc(this).execute(selectIdList.get(0).first, selectIdList.get(1).first);

		findById(R.id.tv_compairContent_inloading).setVisibility(View.VISIBLE);
		elv_compairContent.setVisibility(View.VISIBLE);
		tw_compair_showTitleText.setVisibility(View.VISIBLE);
		cb_compairContent_showAll.setChecked(true);
		cb_compairContent_showAll.setClickable(false);
		cb_compairContent_showdif.setClickable(true);
		different = false;
		((TextView) findById(R.id.tv_compairContent_inloading)).setText("正在加载...");
	}

	private CompairAdapter compairAdapter;
	private CompairAdapter compairDiffAdapter;

	private class CompairMsgAysnc extends BaseAsync<String, CompairBean> {

		public CompairMsgAysnc(Activity activity) {
			super(activity);
		}

		@Override
		public void clear() {

		}

		@Override
		public void onPost(CompairBean result) {
			cBean = result;
			// LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,
			// totalHeight);
			// elv_compairContent.setLayoutParams(p);
			elv_compairContent.setAdapter(compairAdapter);

			int groupCount = compairAdapter.getGroupCount();
			for (int i = 0; i < groupCount; i++) {
				elv_compairContent.expandGroup(i);
			}
			ccView.matchData(cBean.groupPositionMap);
		}

		@Override
		protected CompairBean doInBackground(String... params) {

			String json = HttpClientUtils.getJson("http://www.qc188.com/app/compareCar.asp?lid=" + params[0] + "&rid="
					+ params[1]);
			CompairBean cBean = new CompairBean();

			if (json == null) {
				return null;
			}
			cBean.matchData(json);
			compairAdapter = new CompairAdapter(getApplicationContext());
			compairAdapter.addData(cBean);
			compairDiffAdapter = new CompairAdapter(getApplicationContext());
			compairDiffAdapter.addData(cBean);
			compairDiffAdapter.toDifferent();
			compairAdapter.toNormal();
			return cBean;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	private TextView tw_compair_showTitleText;
	private TextView cb_compairContent_showAll_txt;
	private TextView cb_compairContent_showdif_txt;
	private TableLayout tw_compair_titleTab;
	private RelativeLayout tv_compairContent_leftcar;
	private RelativeLayout tv_compairContent_rightcar;
	private RelativeLayout tv_compairContent_leftAddCar;
	private RelativeLayout tv_compairContent_rightAddCar;
	private CheckBox cb_compairContent_showAll;
	private CheckBox cb_compairContent_showdif;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		LogUtil.d(TAG, "totalItemCount:" + totalItemCount);
		View childView = view.getChildAt(1);
		if (childView == null) {
			childView = view.getChildAt(0);

			if (childView != null) {
				ccView.changBackgroud(cBean.leftList.get(0).first);
				tv_compairContent_flower.setText(cBean.leftList.get(0).first);
			}
			return;
		}

		if (childView instanceof TextView) {
			changeConpairContentFlower(((TextView) childView).getText().toString(), -1);
			childView = view.getChildAt(0);
			RelativeLayout.LayoutParams params = (LayoutParams) tv_compairContent_flower.getLayoutParams();
			params.topMargin = childView.getTop();
			tv_compairContent_flower.setLayoutParams(params);
			LogUtil.d(TAG, "groupTop:" + childView.getTop());
		} else {
			childView = view.getChildAt(0);
			if (childView != null && childView instanceof TextView) {
				changeConpairContentFlower(((TextView) childView).getText().toString(), 0);
			}
			RelativeLayout.LayoutParams params = (LayoutParams) tv_compairContent_flower.getLayoutParams();
			params.topMargin = 0;
			tv_compairContent_flower.setLayoutParams(params);
			tv_compairContent_flower.setPadding(0, 0, 0, 0);

		}
	}

	private void changeConpairContentFlower(String text, int fix) {
		Integer position = cBean.groupPositionMap.get(text);
		text = cBean.getMiddleText(position + fix, different);
		ccView.changBackgroud(text);
		tw_compair_showTitleText.setText(text);
		tv_compairContent_flower.setText(text);
	}

	private boolean different = false;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			buttonView.setClickable(false);
			buttonView = (CompoundButton) buttonView.getTag();
			buttonView.setClickable(true);
			buttonView.setChecked(false);
		}
		switch (buttonView.getId()) {
		case R.id.cb_compairContent_showAll:
			if (isChecked) {
				LogUtil.d(TAG, "toDifferent");

				if (compairDiffAdapter == null) {
					return;
				}
				ccView.matchData(cBean.differentPositinMap);
				different = true;
				elv_compairContent.setAdapter(compairDiffAdapter);
				int groupCount = compairDiffAdapter.getGroupCount();
				for (int i = 0; i < groupCount; i++) {
					elv_compairContent.expandGroup(i);
				}
				cb_compairContent_showAll_txt.setTextColor(0xFFC2D2DA);
				cb_compairContent_showdif_txt.setTextColor(0xFF1562B8);
			} else {
				cb_compairContent_showAll_txt.setTextColor(0xFF1562B8);
				cb_compairContent_showdif_txt.setTextColor(0xFFC2D2DA);
			}
			break;
		case R.id.cb_compairContent_showdif:
			if (!isChecked) {
				cb_compairContent_showAll_txt.setTextColor(0xFFC2D2DA);
				cb_compairContent_showdif_txt.setTextColor(0xFF1562B8);
			} else {

				if (compairAdapter == null) {
					return;
				}
				LogUtil.d(TAG, "normal");
				different = false;
				ccView.matchData(cBean.groupPositionMap);

				elv_compairContent.setAdapter(compairAdapter);
				int groupCount = compairAdapter.getGroupCount();
				for (int i = 0; i < groupCount; i++) {
					elv_compairContent.expandGroup(i);
				}
				cb_compairContent_showAll_txt.setTextColor(0xFF1562B8);
				cb_compairContent_showdif_txt.setTextColor(0xFFC2D2DA);
			}

			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tw_compair_showTitleText:
			if (tw_compair_titleTab.getVisibility() == View.VISIBLE) {
				TranslateAnimation ta = new TranslateAnimation(0, 0, 0, -tw_compair_titleTab.getHeight());
				ta.setDuration(500);
				ta.setFillAfter(true);
				ta.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						tw_compair_titleTab.setVisibility(View.GONE);

					}
				});
				tw_compair_titleTab.startAnimation(ta);
			} else {
				TranslateAnimation ta = new TranslateAnimation(0, 0, -tw_compair_titleTab.getHeight(), 0);
				ta.setDuration(500);
				ta.setFillAfter(true);
				tw_compair_titleTab.setVisibility(View.VISIBLE);
				tw_compair_titleTab.startAnimation(ta);
			}
			break;
		case R.id.tv_compairContent_leftDelete:
			tv_compairContent_leftAddCar.setVisibility(View.VISIBLE);
			tv_compairContent_leftAddCar.setOnClickListener(this);
			tv_compairContent_leftcar.setVisibility(View.GONE);
			compairAdapter.clearLeft();
			compairDiffAdapter.clearLeft();
			checkLeftAndRightData();
			break;
		case R.id.tv_compairContent_rightDelete:
			tv_compairContent_rightAddCar.setVisibility(View.VISIBLE);
			tv_compairContent_rightAddCar.setOnClickListener(this);
			tv_compairContent_rightcar.setVisibility(View.GONE);
			compairAdapter.clearRight();
			compairDiffAdapter.clearRight();
			checkLeftAndRightData();
			break;

		case R.id.tv_compairContent_leftAddCar: // 添加左边车型

		case R.id.tv_compairContent_rightAddCar: // 添加右边车型
			startActivity(InCompairAddTypeActivity.class);
			break;
		}
	}

	// 如果左右都没有数据, 那么显示空
	private void checkLeftAndRightData() {
		if (compairAdapter.leftGroupList == null && compairAdapter.rightGroupList == null) {
			tv_compairContent_flower.setText("点击上方添加车型");
			tw_compair_showTitleText.setVisibility(View.GONE);
			tw_compair_titleTab.setVisibility(View.GONE);
			elv_compairContent.setVisibility(View.GONE);
			findById(R.id.tv_compairContent_inloading).setVisibility(View.INVISIBLE);
		}

		if (compairAdapter.leftGroupList == null || compairAdapter.rightGroupList == null) {
			cb_compairContent_showAll.setChecked(true);
			cb_compairContent_showdif.setChecked(false);
			cb_compairContent_showAll.setClickable(false);
			cb_compairContent_showdif.setClickable(false);
		} else {

			elv_compairContent.setVisibility(View.VISIBLE);
			tw_compair_showTitleText.setVisibility(View.VISIBLE);
			cb_compairContent_showAll.setChecked(true);
			cb_compairContent_showAll.setClickable(false);
			cb_compairContent_showdif.setClickable(true);

		}

	}

	private class TabItemClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			TextView tempView = (TextView) v;
			LogUtil.d(TAG, "TabItemClick:" + tempView.getText().toString());
			elv_compairContent.setSelectedGroup(cBean.groupPositionMap.get(tempView.getText().toString()));
		}
	}
}
