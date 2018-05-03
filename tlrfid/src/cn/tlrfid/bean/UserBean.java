package cn.tlrfid.bean;

public class UserBean {
	private String cellPhoneNo;
	private int effect;
	private String email;
	private String icn;
	private int id;
	private String loginName;
	private String name;
	private String password;
	private String phoneNo;
	private String qq;
	private String remark;
	
	public String getCellPhoneNo() {
		return cellPhoneNo;
	}
	
	public void setCellPhoneNo(String cellPhoneNo) {
		this.cellPhoneNo = cellPhoneNo;
	}
	
	public int getEffect() {
		return effect;
	}
	
	public void setEffect(int effect) {
		this.effect = effect;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIcn() {
		return icn;
	}
	
	public void setIcn(String icn) {
		this.icn = icn;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	
	@Override
	public String toString() {
		return "UserBean [cellPhoneNo=" + cellPhoneNo + ", effect=" + effect + ", email=" + email + ", icn=" + icn
				+ ", id=" + id + ", loginName=" + loginName + ", name=" + name + ", password=" + password
				+ ", phoneNo=" + phoneNo + ", qq=" + qq + ", remark=" + remark + "]";
	}
	
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getQq() {
		return qq;
	}
	
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
