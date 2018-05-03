package cn.tlrfid.view.async;

import java.util.ArrayList;

import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.engine.ProjectScheduleEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.framework.SerializPair;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.ProgressQueryActivity;

public class LoadProjectScheduleAsync extends
		NetConnectionAsync<String, Void, SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>> {
	
	public LoadProjectScheduleAsync(BaseActivity activity, String dialogString, String projectInfoTitle) {
		super(activity, dialogString);
		this.projectInfoTitle = projectInfoTitle;
	}
	
	@Override
	protected SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> doInBackground(String... params) {
		
		ProjectScheduleEngine pse = InstanceFactory.getEngine(ProjectScheduleEngine.class);
		return pse.getProjectSchedule(params[0], ProjectScheduleBean.class);
		
	}
	
	private String projectInfoTitle;
	
	@Override
	protected void onNetResult(SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> result) {
		
		if (result != null) {
			result.first.setProjectTitle(projectInfoTitle);
			activity.startActivity(ProgressQueryActivity.class, "projectSchedule", result);
		} else {
			SystemNotification.showToast(activity.getApplicationContext(), "网络错误");
		}
	}
	
}
