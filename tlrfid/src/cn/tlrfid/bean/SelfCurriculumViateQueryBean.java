package cn.tlrfid.bean;

import java.util.ArrayList;
import java.util.List;

import cn.tlrfid.framework.BaseBean;

public class SelfCurriculumViateQueryBean extends BaseBean {
	
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
	
	public List<SelfProjectRecoder> mItemList;
	
	public List<ScheduleProject> projectNameList;

	@Override
	public String toString() {
		return "SelfCurriculumViateQueryBean [totalPage=" + totalPage + ", totalCount=" + totalCount + ", errMessage="
				+ errMessage + ", state=" + state + ", mItemList=" + mItemList + ", projectNameList=" + projectNameList
				+ "]";
	}
	
	
}
