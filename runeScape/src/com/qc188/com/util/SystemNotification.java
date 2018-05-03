package com.qc188.com.util;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qc188.com.R;

public class SystemNotification {
	private SystemNotification() {
	}

	private static NotificationManager mNotificationManager;
	private static Toast sToast = null;
	private static PopupWindow pw;

	public static void showToast(Context context, String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		if (sToast == null) {
			sToast = new Toast(context);
			TextView tv = new TextView(context);
			tv.setText(msg);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setBackgroundResource(R.drawable.toast_background);
			tv.setTextColor(Color.WHITE);
			tv.setPadding(100, 50, 100, 50);
			sToast.setView(tv);
			sToast.setDuration(Toast.LENGTH_SHORT);
			sToast.setGravity(Gravity.CENTER, 0, 0);
		} else {
			TextView tv = (TextView) sToast.getView();
			tv.setText(msg);
		}
		showToastTime = System.currentTimeMillis();
		sToast.show();
	}

	public static void CancelToast() {
		if (sToast != null) {
			sToast.cancel();
		}
		LogUtil.i("SystemNotification", "CancelToast");
	}

	@SuppressWarnings("deprecation")
	public static void showPopupWindow(Context context, View contentView,
			final View dropView, int anim, int width, int height, int xoff,
			int yoff) {
		ViewGroup g = ((ViewGroup) contentView.getParent());
		if (g != null) {
			g.removeView(contentView);
		}

		pw = new PopupWindow(contentView, width, height);
		if (anim != 0) {
			pw.setAnimationStyle(anim);
		}

		if (contentView instanceof ListView) {
			((AbsListView) contentView)
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							if (dropView instanceof TextView) {
								((TextView) dropView).setText(((TextView) view)
										.getText().toString()
										.replaceAll("[^\\d+]", ""));
							}
							pw.dismiss();
						}
					});
		}
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.showAsDropDown(dropView, xoff, yoff);
	}

	public static boolean popIsShowing() {
		if (pw != null) {
			return pw.isShowing();
		}
		return false;
	}

	public static void showPopupWindow(Context context, View contentView,
			final View dropView, int width, int height, int xoff, int yoff) {
		showPopupWindow(context, contentView, dropView, 0, width, height, xoff,
				yoff);
	}

	/**
	 * 显示一个popwindow
	 * 
	 * @param context
	 * @param contentView
	 *            要显示的view
	 * @param dropView
	 *            在哪个view下面显示
	 * @param width
	 *            要显示的view的宽
	 * @param height
	 *            要显示的view的高
	 * @param xoff
	 *            x轴偏移量
	 * @param yoff
	 *            y轴偏移量
	 * @param listener
	 *            如果显示的是listview，设置监听
	 */
	@SuppressWarnings("deprecation")
	public static void showPopupWindow(Context context, View contentView,
			View dropView, int width, int height, int xoff, int yoff,
			final OnItemClickListener listener) {
		ViewGroup g = ((ViewGroup) contentView.getParent());
		if (g != null) {
			g.removeView(contentView);
		}

		pw = new PopupWindow(contentView, width, height);

		if (contentView instanceof ListView) {
			((AbsListView) contentView)
					.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							if (listener != null) {
								listener.onItemClick(parent, view, position, id);
							}
							pw.dismiss();
						}
					});
		}
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.showAsDropDown(dropView, xoff, yoff);
	}

	public static long showToastTime;

	public static boolean isToashShowing() {
		return System.currentTimeMillis() - showToastTime <= 2000;
	}

	public static void dismissPopupWindow() {
		if (pw != null && pw.isShowing()) {
			pw.dismiss();
		}
	}
}
