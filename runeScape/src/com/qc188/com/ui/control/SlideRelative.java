package com.qc188.com.ui.control;

import com.qc188.com.util.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SlideRelative extends RelativeLayout {

	private static final String TAG = "SlideRelative";

	public SlideRelative(Context context) {
		super(context);
	}

	public SlideRelative(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SlideRelative(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// RelativeLayout.LayoutParams params = new
		// RelativeLayout.LayoutParams(getLayoutParams());
		// params.leftMargin = -widthMeasureSpec;

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LogUtil.d(TAG, "touch");

				return false;
			}
		});
	}
	

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		LogUtil.d(TAG, "int");
		return false;
	}

}
