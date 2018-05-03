package com.qc188.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.widget.ProgressBar;

public class SystemLock {

	private static final String TAG = "SystemLock";
	private static AlertDialog dialog;
	private static Handler handler = new Handler();

	public static void Lock(Activity activity) {
		LogUtil.d(TAG, "activity:" + activity.getClass().getSimpleName());
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setView(new ProgressBar(activity));
		builder.setCancelable(false);
		dialog = builder.create();
		dialog.show();
		handler.postDelayed(new Runnable() {
			public void run() {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}
		}, ConstantValues.CONNECTION_READ_TIMEOUT);

		handler.postDelayed(new Runnable() {
			public void run() {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}
		}, ConstantValues.CONNECTION_READ_TIMEOUT * 2);

	}

	public static void UnLock() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}
}
