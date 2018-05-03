package cn.tlrfid.engine;

import java.util.ArrayList;

import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.SerializPair;

public interface ProjectScheduleEngine {
	/**
	 * 获取工程进度管理bean
	 * 
	 * @param url
	 * @param clazz
	 * @return
	 */
	public SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> getProjectSchedule(String url, Class<? extends BaseBean> clazz);
}
