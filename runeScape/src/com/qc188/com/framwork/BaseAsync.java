package com.qc188.com.framwork;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.qc188.com.util.LogUtil;
import com.qc188.com.util.SystemLock;

public abstract class BaseAsync<T, K> extends AsyncTask<T, Void, K> {
	private static final String TAG = "BaseAsync";
	private Activity activity;
	private boolean mLock = true;

	public BaseAsync(Activity activity) {
		this.activity = activity;
	}

	public BaseAsync(Activity activity, boolean lock) {
		this.activity = activity;
		this.mLock = lock;
	}

	@Override
	final protected void onPreExecute() {

		if (mLock) {
			try {
				SystemLock.Lock(activity);
			} catch (Exception e) {
			}
		}
		onPre();
	}

	protected void onPre() {

	}

	private boolean checkedLink = true;

	public void setCheckedLink(boolean checkedLink) {
		this.checkedLink = checkedLink;
	}

	private String toastMsg = "网络连接错误....";

	public void setToastMsg(String toastMsg) {

		this.toastMsg = toastMsg;

	}

	@Override
	protected void onPostExecute(K result) {
		if (mLock) {
			SystemLock.UnLock();
		}
		if (result == null && checkedLink) {
			LogUtil.d(TAG, "toast:" + activity);
			Toast.makeText(activity, toastMsg, Toast.LENGTH_LONG).show();
		}
		onPost(result);
	}

	public abstract void clear();

	public abstract void onPost(K result);

}
