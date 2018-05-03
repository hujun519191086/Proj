package cn.tlrfid.framework;

import android.content.Context;
import android.os.AsyncTask;
import cn.tlrfid.utils.AlertDialogUtil;

public abstract class NetConnectionAsync<T, E, F> extends AsyncTask<T, E, F> {
	protected Context context;
	protected BaseActivity activity;
	private String dialogString;
	
	public NetConnectionAsync(BaseActivity activity) {
		this.activity = activity;
		context = activity.getApplicationContext();
	}
	
	public NetConnectionAsync(Context context, BaseActivity activity, String dialogString) {
		this.context = context;
		this.activity = activity;
		this.dialogString = dialogString;
	}
	
	public NetConnectionAsync(BaseActivity activity, String dialogString) {
		this.activity = activity;
		this.dialogString = dialogString;
		context = activity.getApplicationContext();
	}
	
	private boolean openLock = true;
	
	public NetConnectionAsync(BaseActivity activity, String dialogString, boolean openLock) {
		this.activity = activity;
		context = activity.getApplicationContext();
		this.dialogString = dialogString;
		this.openLock = openLock;
	}
	
	@Override
	protected final void onPreExecute() {
		if (openLock) {
			AlertDialogUtil.showLoading(activity, dialogString);
		}
	}
	
	protected void onPostExecute(F result) {
		if (openLock) {
			AlertDialogUtil.dissmissLoading();
		}
		onNetResult(result);
	}
	
	protected void onNetResult(F result) {
		
	}
	
}
