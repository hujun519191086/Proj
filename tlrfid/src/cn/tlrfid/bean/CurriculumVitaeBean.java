package cn.tlrfid.bean;

import java.util.List;

import cn.tlrfid.framework.BaseBean;

public class CurriculumVitaeBean extends BaseBean {
	
	/**
	 * 总页数
	 */
	public int totalPage;
	/**
	 * 总数量
	 */
	public int totalCount;
	
	public String errMessage;
	public int state;
	
	public List<ProjectRecoder> mItemList;
	
	public List<ScheduleProject> projectNameList;
	
}
