package cn.tlrfid.view;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.R.dimen;
import cn.tlrfid.view.adapter.ChangeMoodAdapter;

public class MainViewPagerActivity extends Activity implements OnPageChangeListener, OnClickListener {
	private ChangeMoodAdapter cmAdapter;
	private TextView mainviewpager_tv_progress;
	private TextView mainviewpager_tv_deviceManager;
	private TextView mainviewpager_tv_safety;
	private ArrayList<TextView> bottomText;
	private ArrayList<TextView> bottomTextTitle;
	private ViewPager mainviewpager_vp_changeMood;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_viewpager);
		mainviewpager_vp_changeMood = (ViewPager) findViewById(R.id.mainviewpager_vp_changeMood);
		cmAdapter = new ChangeMoodAdapter(getApplicationContext());
		mainviewpager_vp_changeMood.setAdapter(cmAdapter);
		mainviewpager_vp_changeMood.setCurrentItem(cmAdapter.getCurrentItem());
		mainviewpager_vp_changeMood.setOnPageChangeListener(this);
		initText();
	}
	
	private void initText() {
		bottomText = new ArrayList<TextView>();
		mainviewpager_tv_progress = (TextView) findViewById(R.id.mainviewpager_tv_progress);
		mainviewpager_tv_safety = (TextView) findViewById(R.id.mainviewpager_tv_safety);
		mainviewpager_tv_deviceManager = (TextView) findViewById(R.id.mainviewpager_tv_deviceManager);
		bottomText.add(mainviewpager_tv_progress);
		bottomText.add(mainviewpager_tv_safety);
		bottomText.add(mainviewpager_tv_deviceManager);
		mainviewpager_tv_progress.setOnClickListener(this);
		mainviewpager_tv_safety.setOnClickListener(this);
		mainviewpager_tv_deviceManager.setOnClickListener(this);
		
		bottomTextTitle = new ArrayList<TextView>();
		bottomTextTitle.add((TextView) findViewById(R.id.mainviewpater_tv_progress_title));
		bottomTextTitle.add((TextView) findViewById(R.id.mainviewpater_tv_safety_title));
		bottomTextTitle.add((TextView) findViewById(R.id.mainviewpater_tv_deviceManager_title));
		// bottomTextTitle.get(0).setVisibility(View.VISIBLE);
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	
	@Override
	public void onPageSelected(int arg0) {
		onPageChangeInit(cmAdapter.getPosition(arg0));
	}
	
	private void onPageChangeInit(int position) {
		Log.d("onScroll", "positionL" + position + "  bottomText:" + bottomText.size());
		for (int i = 0; i < bottomTextTitle.size(); i++) {
			if (position == i) {
				// bottomTextTitle.get(i).setVisibility(View.VISIBLE);
				
			} else {
				// bottomTextTitle.get(i).setVisibility(View.GONE);
			}
		}
		for (int i = 0; i < 3; i++) {
			TextView tempText = bottomText.get(i);
			Log.d("onScroll", "position:" + i + "   tempText:" + tempText);
			if (position == i) {
				// tempText.setTextColor(Color.WHITE);
				tempText.setTextSize(getResources().getDimensionPixelSize(dimen.textSize_36));
				tempText.setBackgroundColor(Color.parseColor("#0000ff"));
			} else {
				// tempText.setTextColor(getResources().getColor(R.color.mainviewpager_bottom_text_select_color));
				tempText.setTextSize(getResources().getDimensionPixelSize(dimen.textSize_30));
				tempText.setBackgroundColor(Color.parseColor("#00000000"));
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mainviewpager_tv_progress:
			setCurrentItem(0);
			break;
		case R.id.mainviewpager_tv_safety:
			setCurrentItem(1);
			break;
		case R.id.mainviewpager_tv_deviceManager:
			setCurrentItem(2);
			break;
		}
	}
	
	public void setCurrentItem(int position) {
		
		int currentPosition = mainviewpager_vp_changeMood.getCurrentItem();
		int nowCurrent = cmAdapter.getPosition(currentPosition);
		int offset = position - nowCurrent;
		Log.d("setCurrent", offset + "    currentPosition:" + currentPosition);
		for (int i = 0; i < Math.abs(offset); i++) {
			if (offset < 0) {
				mainviewpager_vp_changeMood.setCurrentItem(mainviewpager_vp_changeMood.getCurrentItem() - 1);
			} else {
				mainviewpager_vp_changeMood.setCurrentItem(mainviewpager_vp_changeMood.getCurrentItem() + 1);
			}
		}
	}
	
}
