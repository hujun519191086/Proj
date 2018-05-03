package cn.tlrfid.bean;

import java.util.List;

import cn.tlrfid.framework.BaseBean;

public class ProjectInfoBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String address;
	private String construction;
	private int id;
	private String name;
	private String pdCode;
	private String pdName;
	private List<PersonBean> personList;
	private String phoneNo;
	private String pm;
	private String remark;
	private UserBean user;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getConstruction() {
		return construction;
	}
	
	public void setConstruction(String construction) {
		this.construction = construction;
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
	
	public String getPdCode() {
		return pdCode;
	}
	
	public void setPdCode(String pdCode) {
		this.pdCode = pdCode;
	}
	
	public String getPdName() {
		return pdName;
	}
	
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	
	public List<PersonBean> getPersonList() {
		return personList;
	}
	
	public void setPersonList(List<PersonBean> personList) {
		this.personList = personList;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getPm() {
		return pm;
	}
	
	public void setPm(String pm) {
		this.pm = pm;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public UserBean getUser() {
		return user;
	}
	
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "ProjectInfoBean [address=" + address + ", construction=" + construction + ", id=" + id + ", name="
				+ name + ", pdCode=" + pdCode + ", pdName=" + pdName + ", personList=" + personList + ", phoneNo="
				+ phoneNo + ", pm=" + pm + ", remark=" + remark + ", user=" + user + "]";
	}
	
}
