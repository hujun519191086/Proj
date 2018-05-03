package com.qc188.com.ui.async;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import com.qc188.com.R;
import com.qc188.com.bean.MsgContentBean;
import com.qc188.com.engine.MsgContentEngine;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.ContentActivity;
import com.qc188.com.ui.MainActivity.AnimCommon;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class LoadContentAsync extends BaseAsync<String, MsgContentBean> {
	private static final String TAG = "LoadContentAsync";
	private Activity activity;
	
	public LoadContentAsync(Activity activity) {
		super(activity);
		this.activity = activity;
	}
	
	public void onPost(MsgContentBean result) {
		Intent intent = new Intent();
		intent.putExtra("where", fromTAG);
		intent.setClass(activity.getApplicationContext(), ContentActivity.class);
		AnimCommon.set(R.anim.froomright_in_translate, R.anim.state_translate);
		intent.putExtra(ConstantValues.MSG_CONTENT_BUNDLE, result);
		activity.startActivity(intent);
	}
	
	private MsgContentEngine mcEngine = InstanceFactory.getInstances(MsgContentEngine.class);
	private String fromTAG;
	
	@Override
	protected MsgContentBean doInBackground(String... params) {
		fromTAG = params[2];
		LogUtil.d(TAG, " index:" + params.length);
		return mcEngine.getContent_async(Double.valueOf(params[0]).intValue(), params[1] + "",
				Integer.valueOf(params[3]));
	}
	
	@Override
	public void clear() {
		
	}
}
