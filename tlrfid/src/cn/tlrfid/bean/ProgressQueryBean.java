package cn.tlrfid.bean;

public class ProgressQueryBean {
	private String serialNumber;
	private String plan;
	private String planType;
	private String startTime;
	private String endTime;
	private String limitTime; // 计划工期
	private String completeProgress; // 完成天数
	private String details;
	private String projectName;
	private String finishTime; // 真正完成时间
	private String exceedTime;// 超过时间
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getExceedTime() {
		return exceedTime;
	}
	
	public void setExceedTime(String exceedTime) {
		this.exceedTime = exceedTime;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getPlan() {
		return plan;
	}
	
	public void setPlan(String plan) {
		this.plan = plan;
	}
	
	public String getPlanType() {
		return planType;
	}
	
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getLimitTime() {
		return limitTime;
	}
	
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	
	public String getCompleteProgress() {
		return completeProgress;
	}
	
	public void setCompleteProgress(String completeProgress) {
		this.completeProgress = completeProgress;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
}
