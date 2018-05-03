package com.qc188.com.ui.control;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class TouchButton extends Button {

	public TouchButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public TouchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public TouchButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {

	}

	private DrawerLayout mDrawerLayout;

	public void setDrawerLayout(DrawerLayout drawerLayout) {
		mDrawerLayout = drawerLayout;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mDrawerLayout != null) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
				break;

			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
				break;
			default:
				break;
			}

		}

		return super.dispatchTouchEvent(event);
	}

}
