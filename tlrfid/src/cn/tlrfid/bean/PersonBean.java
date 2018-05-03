package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class PersonBean extends BaseBean {
	private String companyId;
	
	private String groupId;
	private String helmetTagId;
	private String jobNumber;
	private String personId;
	private String personName;
	private String personTagId;
	private boolean sex;
	private String tradesId;
	private GroupBean group;
	
	public GroupBean getGroup() {
		return group;
	}
	
	public void setGroup(GroupBean group) {
		this.group = group;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getHelmetTagId() {
		return helmetTagId;
	}
	
	public void setHelmetTagId(String helmetTagId) {
		this.helmetTagId = helmetTagId;
	}
	
	public String getJobNumber() {
		return jobNumber;
	}
	
	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	public String getPersonId() {
		return personId;
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
	
	public String getTradesId() {
		return tradesId;
	}
	
	public void setTradesId(String tradesId) {
		this.tradesId = tradesId;
	}
	
	public class GroupBean extends BaseBean {
		private String groupName;
		private String id;
		
		public String getGroupName() {
			return groupName;
		}
		
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return "GroupBean [groupName=" + groupName + ", id=" + id + "]";
		}
		
	}
	
	@Override
	public String toString() {
		return "PersonBean [companyId=" + companyId + ", groupId=" + groupId + ", helmetTagId=" + helmetTagId
				+ ", jobNumber=" + jobNumber + ", personId=" + personId + ", personName=" + personName
				+ ", personTagId=" + personTagId + ", sex=" + sex + ", tradesId=" + tradesId + ", group=" + group + "]";
	}
	
}
