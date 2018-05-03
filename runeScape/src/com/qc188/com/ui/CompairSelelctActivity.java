package com.qc188.com.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.qc188.com.R;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.ui.adapter.CompairSelectAdapter;
import com.qc188.com.ui.manager.CompairManager;

public class CompairSelelctActivity extends BaseActivity implements OnClickListener {

	private Button bt_brandDetail_compair;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compair_select);

		setTitleContent("车型对比");
		visibleBackButton();
		findView();
	}


	@Override
	protected void onResume() {
		super.onResume();
		csadapter.matchList();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.back_activirty_in, R.anim.back_activity_out);
	}

	private LinearLayout rl_compairSelect_bottomContent;
	private Button compairSelect_delete;
	private Button compairSelect_beginCompair;
	private CompairSelectAdapter csadapter;
	private Button bt_compairSelect_clearAll;

	private void findView() {
		ListView lv_compairSelect_content = (ListView) findViewById(R.id.lv_compairSelect_content);
		rl_compairSelect_bottomContent = (LinearLayout) findViewById(R.id.rl_compairSelect_bottomContent);
		csadapter = new CompairSelectAdapter(getApplicationContext(), lv_compairSelect_content);
		csadapter.setNummMsgRela((RelativeLayout) findViewById(R.id.rl_compairSelect_nullMsg));
		compairSelect_delete = (Button) findViewById(R.id.compairSelect_delete);
		compairSelect_beginCompair = (Button) findViewById(R.id.compairSelect_beginCompair);
		bt_compairSelect_clearAll = (Button) findViewById(R.id.bt_compairSelect_clearAll);
		csadapter.setOnSelectChangeRunnable(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position <= 0) {
					compairSelect_delete.setEnabled(false);
					compairSelect_beginCompair.setEnabled(false);
				} else {
					compairSelect_delete.setEnabled(true);

					if (position >= 2) {
						compairSelect_beginCompair.setEnabled(true);
					} else {
						compairSelect_beginCompair.setEnabled(false);
					}
				}
			}
		});
		lv_compairSelect_content.setAdapter(csadapter);

		bt_brandDetail_compair = (Button) findViewById(R.id.bt_brandDetail_compair);
		bt_brandDetail_compair.setOnClickListener(this);
		bt_brandDetail_compair.setText("编辑");
		bt_brandDetail_compair.setVisibility(View.VISIBLE);
		bt_brandDetail_compair.setTag(false);

		compairSelect_delete.setOnClickListener(this);
		compairSelect_beginCompair.setOnClickListener(this);
		bt_compairSelect_clearAll.setOnClickListener(this);

		refreshFull();
	}

	private void refreshFull() {
		View addCompair = findById(R.id.compairSelect_addCompair);
		if (CompairManager.getManger().isFull()) {
			addCompair.setEnabled(false);
		} else {
			addCompair.setEnabled(true);
			addCompair.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		refreshFull();
		switch (v.getId()) {
		case R.id.bt_brandDetail_compair:
			boolean inEdit = (Boolean) v.getTag();
			CompairManager.getManger().clearSelect();
			csadapter.matchList();
			if (inEdit) {
				v.setTag(false);
				bt_brandDetail_compair.setText("编辑");
				csadapter.setCompairModule();
				TranslateAnimation ta = new TranslateAnimation(0, 0, rl_compairSelect_bottomContent.getHeight(), 0);
				ta.setDuration(300);
				ta.setFillAfter(true);
				ta.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						rl_compairSelect_bottomContent.clearAnimation();
					}
				});
				rl_compairSelect_bottomContent.startAnimation(ta);
				rl_compairSelect_bottomContent.setVisibility(View.VISIBLE);
			} else {
				v.setTag(true);
				bt_brandDetail_compair.setText("完成");
				csadapter.setDeleteModule();
				TranslateAnimation ta = new TranslateAnimation(0, 0, 0, rl_compairSelect_bottomContent.getHeight());
				ta.setDuration(200);
				ta.setFillAfter(true);
				ta.setAnimationListener(new AnimationListener() {
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						rl_compairSelect_bottomContent.clearAnimation();
						rl_compairSelect_bottomContent.setVisibility(View.GONE);
					}
				});
				rl_compairSelect_bottomContent.startAnimation(ta);
			}

			break;

		case R.id.compairSelect_delete:
			CompairManager.getManger().deleteSelect();
			csadapter.matchList();
			break;
		case R.id.compairSelect_beginCompair: // 开始对比
			startActivity(CompairActivity.class);
			break;
		case R.id.bt_compairSelect_clearAll:

			new AlertDialog.Builder(this).setMessage("将会清空列表中的所有车型,是否继续?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					CompairManager.getManger().clear();
					csadapter.matchList();
				}
			}).setNegativeButton(android.R.string.no, null).show();
			break;
		case R.id.compairSelect_addCompair: // 添加车型
			startActivity(SearchActivity.class);
			break;
		default:
			break;
		}
	}

}
