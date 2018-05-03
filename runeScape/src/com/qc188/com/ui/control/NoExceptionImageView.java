package com.qc188.com.ui.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NoExceptionImageView extends ImageView {

	public NoExceptionImageView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	public NoExceptionImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public NoExceptionImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		setScaleType(ScaleType.FIT_XY);

	}

	protected void onDraw(android.graphics.Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception e) {
			System.out.println("trying to use a recycled bitmap");
		}
	}
}
