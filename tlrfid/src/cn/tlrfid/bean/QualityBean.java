package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class QualityBean extends BaseBean {
	private String checkPerson;
	private String checkPersonName;
	private String checkTime;
	private int id;
	private boolean overLimit;
	private int overhaulLimit;
	private String person;
	private String picPath;
	private String remark;
	private int result;
	private String resultName;
	private int scheduleId;
	private String tagCode;
	
	public String getCheckPerson() {
		return checkPerson;
	}
	
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}
	
	public String getCheckPersonName() {
		return checkPersonName;
	}
	
	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}
	
	public String getCheckTime() {
		return checkTime;
	}
	
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isOverLimit() {
		return overLimit;
	}
	
	public void setOverLimit(boolean overLimit) {
		this.overLimit = overLimit;
	}
	
	public int getOverhaulLimit() {
		return overhaulLimit;
	}
	
	public void setOverhaulLimit(int overhaulLimit) {
		this.overhaulLimit = overhaulLimit;
	}
	
	public String getPerson() {
		return person;
	}
	
	public void setPerson(String person) {
		this.person = person;
	}
	
	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getResult() {
		return result;
	}
	
	public void setResult(int result) {
		this.result = result;
	}
	
	public String getResultName() {
		return resultName;
	}
	
	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
	
	public int getScheduleId() {
		return scheduleId;
	}
	
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public String getTagCode() {
		return tagCode;
	}
	
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	
}
