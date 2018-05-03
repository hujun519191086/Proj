package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class ProjectRecoder extends BaseBean {
	
	public int finishRate;
	public int id;
	public String inspectionPerson;
	public String inspectionTime;
	public String person;
	public String picPath;
	public String remark;
	public int scheduleId;
	public String tagCode;
	public String inspectionPersonName;
	public String scheduleName;
	
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
	
	public String getInspectionPerson() {
		return inspectionPerson;
	}
	
	public void setInspectionPerson(String inspectionPerson) {
		this.inspectionPerson = inspectionPerson;
	}
	
	public String getInspectionTime() {
		return inspectionTime;
	}
	
	public void setInspectionTime(String inspectionTime) {
		this.inspectionTime = inspectionTime;
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
	
	public String getInspectionPersonName() {
		return inspectionPersonName;
	}
	
	public void setInspectionPersonName(String inspectionPersonName) {
		this.inspectionPersonName = inspectionPersonName;
	}
	
	public String getScheduleName() {
		return scheduleName;
	}
	
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
}
