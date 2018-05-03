package cn.tlrfid.engine.impl;

import java.util.ArrayList;
import java.util.HashMap;

import android.text.TextUtils;
import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.engine.ProjectScheduleEngine;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.SerializPair;
import cn.tlrfid.utils.LogUtil;

public class ProjectScheduleEngineImpl implements ProjectScheduleEngine {
	private static final String TAG = "ProjectScheduleEngineImpl";

	public SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> getProjectSchedule(
			String url, Class<? extends BaseBean> clazz) {
		LogUtil.d(TAG, "url:" + url);
		UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
		ArrayList<ProjectScheduleBean> list = ue.getUrlBean(url,
				ProjectScheduleBean.class);
		// 10-16 22:59:02.552: D/ProjectScheduleEngineImpl(1824):
		// url:http://182.92.76.78:8080/spms/service/queryProjectSchedule.action?projectCode=35kVBDZ

		if (list == null) {
			return null;
		}
		GanttMaxMinMothText gmmmb = getGanttMaxMinMothBean(ue);
		if (gmmmb == null) {
			return null;
		}
		return new SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>(
				gmmmb, list);
	}

	private GanttMaxMinMothText getGanttMaxMinMothBean(UniversalEngine ue) {
		HashMap<String, Object> ignoreList = ue.getIgnoreJson();
		LogUtil.d(TAG, "ignoreList:" + ignoreList);
		String first = (String) ignoreList.get("firstDate");
		String last = (String) ignoreList.get("lastDate");

		if (TextUtils.isEmpty(first) || TextUtils.isEmpty(last)) {
			return null;
		}
		return new GanttMaxMinMothText(first, last);
	}
}
