package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class PersonCardBean extends BaseBean {
	private String personName;
	private String helmetTagId;
	private String personTagId;
	private boolean sex;
	private String jobNumber;
	private String personId;
	private String group;
	
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	private String picPath;
	
	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getPersonId() {
		return personId;
	}
	
	@Override
	public String getInTag() {
		return personTagId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getHelmetTagId() {
		return helmetTagId;
	}
	
	public void setHelmetTagId(String helmetTagId) {
		this.helmetTagId = helmetTagId;
	}
	
	public String getPersonTagId() {
		return personTagId;
	}
	
	public void setPersonTagId(String personTagId) {
		this.personTagId = personTagId;
	}
	
	public boolean isSex() {
		return sex;
	}
	
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	public String getJobNumber() {
		return jobNumber;
	}
	
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((helmetTagId == null) ? 0 : helmetTagId.hashCode());
		result = prime * result + ((jobNumber == null) ? 0 : jobNumber.hashCode());
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((personName == null) ? 0 : personName.hashCode());
		result = prime * result + ((personTagId == null) ? 0 : personTagId.hashCode());
		result = prime * result + (sex ? 1231 : 1237);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonCardBean other = (PersonCardBean) obj;
		if (helmetTagId == null) {
			if (other.helmetTagId != null)
				return false;
		} else if (!helmetTagId.equals(other.helmetTagId))
			return false;
		if (jobNumber == null) {
			if (other.jobNumber != null)
				return false;
		} else if (!jobNumber.equals(other.jobNumber))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (personName == null) {
			if (other.personName != null)
				return false;
		} else if (!personName.equals(other.personName))
			return false;
		if (personTagId == null) {
			if (other.personTagId != null)
				return false;
		} else if (!personTagId.equals(other.personTagId))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PersonCardBean [personName=" + personName + ", helmetTagId=" + helmetTagId + ", personTagId="
				+ personTagId + ", sex=" + sex + ", jobNumber=" + jobNumber + ", personId=" + personId + "]";
	}
	
}
