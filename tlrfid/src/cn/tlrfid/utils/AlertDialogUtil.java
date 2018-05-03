package cn.tlrfid.utils;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.tlrfid.framework.BaseActivity;

public class AlertDialogUtil {
	private Context context;

	private AlertDialogUtil(Context context) {
		this.context = context;
	}

	private static ProgressDialog loadProgress;

	public static void showLoading(final Context context, String text) {
		loadProgress = ProgressDialog.show(context, null, text);

	}

	public static void showLoading(final Context context, String text, final boolean openAutoHide) {
		loadProgress = ProgressDialog.show(context, null, text);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (openAutoHide && loadProgress.isShowing()) {
					dissmissLoading();
					SystemNotification.showToast(context, "请求超时...");
				}
			}
		}, 13000);

	}

	public static void dissmissLoading() {
		if (loadProgress != null && loadProgress.isShowing()) {
			loadProgress.dismiss();
		}
	}

	private static AlertDialogUtil instance;

	public static AlertDialogUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (AlertDialogUtil.class) {
				if (instance == null) {
					instance = new AlertDialogUtil(context);
				}
			}
		}
		return instance;
	}

	public static AlertDialogUtil getInstance() {
		if (instance == null) {
			throw new RuntimeException("没有初始化dialog");
		}
		return instance;
	}

	private AlertDialog dialog;

	public void alert(BaseActivity activity, String title, String content, String negative, DialogInterface.OnClickListener listener) {
		if (dialog != null) {
			dialog.cancel();
		}
		dialog = new AlertDialog.Builder(activity).setTitle(title).setMessage(content).setNegativeButton(negative, listener).create();
		dialog.show();
	}

	public void alert(Context context, String title, String content, String negative, DialogInterface.OnClickListener listener) {
		if (dialog != null) {
			dialog.cancel();
		}
		dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(content).setNegativeButton(negative, listener).create();
		dialog.show();
	}

	public void alert(Context context, String title, String content, String negative, DialogInterface.OnClickListener listener1, String neutral,
			DialogInterface.OnClickListener listener2) {
		if (dialog != null) {
			dialog.cancel();
		}
		dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(content).setNegativeButton(negative, listener1)
				.setNeutralButton(neutral, listener2).create();
		dialog.show();
	}

	public void alert(BaseActivity activity, String title, String content) {
		if (dialog != null) {
			dialog.cancel();
		}
		dialog = new AlertDialog.Builder(activity).setTitle(title).setMessage(content).create();
		dialog.show();
	}

	public void cancel() {

	}

}
