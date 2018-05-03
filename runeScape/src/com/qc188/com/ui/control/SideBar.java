package com.qc188.com.ui.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qc188.com.interf.SideBarClickLisener;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.LogUtil;

public class SideBar extends LinearLayout {

	private static final String TAG = "SideBar";

	public static String[] word_number = { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public SideBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private int itemHeight = 0;

	private int controlHeight = 0;

	public void setWordCount(int wordCount) {
		LogUtil.d(TAG, "wordCount");
		this.controlHeight = wordCount;
		controlHeight = getMeasuredHeight() / wordCount;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	private void init() {
		setGravity(Gravity.CENTER_HORIZONTAL);

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					public void onGlobalLayout() {
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						itemHeight = getMeasuredHeight() / 26;
						for (int i = 0; i < word_number.length; i++) {
							TextView tv = new TextView(getContext());
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									-1, itemHeight);
							tv.setLayoutParams(params);
							// tv.setOnTouchListener(new MyTouch());
							tv.setId(i);
							tv.setText(word_number[i]);
							tv.setTextSize(15);
							tv.setTextColor(0xFF666666);
							addView(tv);
						}
						itemHeight = getMeasuredHeight() / 26
								+ DensityUtil.dip2px(2);
						setOnTouchListener(new ControlTouch());
					}
				});
	}

	private int lastTouch = -1;

	private class ControlTouch implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (lisener == null) {
				return true;
			}
			int p = (int) (event.getY() / controlHeight);
			if (p != lastTouch) {
				lisener.OnSide(v, p);
				lastTouch = p;

			}

			return true;
		}

	}

	private SideBarClickLisener lisener;

	public void setTouchListener(SideBarClickLisener lisener) {
		this.lisener = lisener;
	}

}
