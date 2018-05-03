package cn.tlrfid.view.async;

import cn.tlrfid.bean.ControlHolder;
import cn.tlrfid.bean.ProjectInfoBean;
import cn.tlrfid.engine.ProjectInfoEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.SystemNotification;

public class LoadProjectManagerAsync extends NetConnectionAsync<String, Void, ProjectInfoBean> {
	
	private static final String TAG = "LoadProjectManagerAsync";
	private ControlHolder cholder;
	
	public LoadProjectManagerAsync(BaseActivity activity, ControlHolder cholder) {
		super(activity, "正在加载工程管理信息");
		this.cholder = cholder;
	}
	
	@Override
	protected ProjectInfoBean doInBackground(String... params) {
		String url = ConstantValues.LOAD_PROGRESSMANAGER + "?" + params[0];
		
		for (int i = 1; i < params.length; i++) {
			url = url + "&" + params[i];
		}
		LogUtil.d(TAG, "url:" + url);
		ProjectInfoEngine pie = InstanceFactory.getEngine(ProjectInfoEngine.class);
		return pie.getProjectInfo(url, ProjectInfoBean.class);
	}
	
	@Override
	protected void onNetResult(ProjectInfoBean result) {
		if (result != null) {
			cholder.preperData(activity.getApplicationContext(), result);
			// pcInfo = pib.getName();
			onNetResultSuccess(result);
		} else {
			SystemNotification.showToast(activity.getApplicationContext(), "获取信息失败,请再试一次!");
		}
	}
	
	public void onNetResultSuccess(ProjectInfoBean result) {
		
	}
	
}