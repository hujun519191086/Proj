package control.bean;

import control.inter.PersonStatus;

/**
 * 后期需要实现: 一个Person对应多个收货地址.
 * @author jieranyishen
 *
 */
public class Person extends BaseBean{
	private String phoneNum;// 电话号码
	private String nickname;//昵称 如果昵称是空 ,填写邮箱
	private String mail;//邮箱
	private String pid;// 唯一id
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private int personId;//  //当前登陆的id
	private String verificationCode;//验证码
	private long verificationOutTime;//过期时间
	private long  registTime; //注册时间
	private float high;// 身高
	private float weight;//体重
	private boolean sex;//false  女
	private long loginTime = 0 ;//登陆时间, 超过半小时没更新视为未登录
	private String deviceId = ""; //如果是手机登陆, 需要上传设备的id
	private String county;
	private String address;//地址
	
	private String province;
	private String city;//市
	private int personstatus  = PersonStatus.NOT_USER.value;//默认不是用户
	
	
	public int getPersonstatus() {
		return personstatus;
	}
	public void setPersonstatus(int personstatus) {
		if(personstatus==0)//数据库给的0. 设置成没有用户
		{
			 personstatus  = PersonStatus.NOT_USER.value;
		}
		this.personstatus = personstatus;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getMailBox() {
		return mail;
	}
	public void setMailBox(String mailBox) {
		this.mail = mailBox;
	}
	
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public long getVerificationOutTime() {
		return verificationOutTime;
	}
	public void setVerificationOutTime(long verificationOutTime) {
		this.verificationOutTime = verificationOutTime;
	}
	public long getRegistTime() {
		return registTime;
	}
	public void setRegistTime(long registTime) {
		this.registTime = registTime;
	}
	
}
