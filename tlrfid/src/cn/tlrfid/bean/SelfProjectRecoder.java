package cn.tlrfid.bean;

import java.util.List;

import cn.tlrfid.framework.BaseBean;

public class SelfProjectRecoder extends BaseBean {
	
	public String checkPerson;
	public String checkPersonName;
	public String checkTime;
	public String color;
	public List<SecurityCheckEntry> mSecurityList;
	public int id;
	public boolean overLimit;
	public int overhaulLimit;
	public String person;
	public String picPath;
	public String remark;
	public int result;
	public String resultName;
	public int scheduleId;
	public String scheduleName;
	public String tagCode;
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public List<SecurityCheckEntry> getmSecurityList() {
		return mSecurityList;
	}
	public void setmSecurityList(List<SecurityCheckEntry> mSecurityList) {
		this.mSecurityList = mSecurityList;
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
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getTagCode() {
		return tagCode;
	}
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	@Override
	public String toString() {
		return "SelfProjectRecoder [checkPerson=" + checkPerson + ", checkPersonName=" + checkPersonName
				+ ", checkTime=" + checkTime + ", color=" + color + ", mSecurityList=" + mSecurityList + ", id=" + id
				+ ", overLimit=" + overLimit + ", overhaulLimit=" + overhaulLimit + ", person=" + person + ", picPath="
				+ picPath + ", remark=" + remark + ", result=" + result + ", resultName=" + resultName
				+ ", scheduleId=" + scheduleId + ", scheduleName=" + scheduleName + ", tagCode=" + tagCode + "]";
	}
	
	
	
}
