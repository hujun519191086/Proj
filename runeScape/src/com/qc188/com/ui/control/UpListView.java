package com.qc188.com.ui.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.ListView;
import android.widget.TextView;

public class UpListView extends ListView {

	public UpListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public UpListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public UpListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		TextView tv = new TextView(getContext());
		tv.setText("正在刷新");
		addFooterView(tv);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return super.onTouchEvent(ev);
	}
}
