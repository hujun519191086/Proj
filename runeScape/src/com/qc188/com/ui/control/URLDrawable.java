package com.qc188.com.ui.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;

import com.qc188.com.R;

public class URLDrawable extends BitmapDrawable {
	private static final String TAG = "URLDrawable";
	public Drawable drawable;
	private Context context;

	@SuppressWarnings("deprecation")
	public URLDrawable(Context context) {
		this.context = context;
		this.setBounds(getDefaultImageBounds(context));
		drawable = context.getResources().getDrawable(R.drawable.load);
		drawable.setBounds(getDefaultImageBounds(context));
	}

	@Override
	public void draw(Canvas canvas) {
		Log.d("test", "this=" + this.getBounds());
		if (drawable != null) {
			// drawable.setBounds(copyBounds());
			Log.d("test", "draw=" + drawable.getBounds());
			drawable.draw(canvas);
		}
	}

	public static Rect getDefaultImageBounds(Context context) {
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		double width = display.getWidth() / 3;
		double height = width / 3;
		Rect bounds = new Rect(0, 0, (int) width, (int) height);
		return bounds;
	}

	public static Rect getImageBounds(Context context, Drawable drawable) {
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		double width = display.getWidth() / 1.1;
		double scal = width / drawable.getIntrinsicWidth();
		double height = drawable.getIntrinsicHeight() * scal;
		Rect bounds = new Rect(0, 0, (int) width, (int) height);
		return bounds;
	}
}