package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class ProjectScheduleBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actualFinishTime;
	private String beginTime; // 项目开始时间
	private String endTime; // 项目预计时间
	private int finishRate;
	private int id;
	private String name; // 工程名称
	private int outOfDay; // 超出天数
	private Integer parentId;
	private int period;
	private int projectId;
	private String remark;
	private boolean state;
	private String tagCode;
	private int type;
	
	public int getOutOfDay() {
		return outOfDay;
	}
	
	public void setOutOfDay(int outOfDay) {
		this.outOfDay = outOfDay;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	public String getActualFinishTime() {
		return actualFinishTime;
	}
	
	public void setActualFinishTime(String actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public int getFinishRate() {
		return finishRate;
	}
	
	public void setFinishRate(int finishRate) {
		this.finishRate = finishRate;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public boolean isState() {
		return state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public String getTagCode() {
		return tagCode;
	}
	
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ProjectScheduleBean [actualFinishTime=" + actualFinishTime + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", finishRate=" + finishRate + ", id=" + id + ", name=" + name + ", parentId=" + parentId
				+ ", period=" + period + ", projectId=" + projectId + ", remark=" + remark + ", state=" + state
				+ ", tagCode=" + tagCode + ", type=" + type + "]";
	}
	
}
