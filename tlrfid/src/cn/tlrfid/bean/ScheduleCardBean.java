package cn.tlrfid.bean;

import cn.tlrfid.framework.BaseBean;

public class ScheduleCardBean extends BaseBean {
	private String name;
	private String tagCode;
	
	public String getName() {
		return name;
	}
	
	@Override
	public String getInTag() {
		return tagCode;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTagCode() {
		return tagCode;
	}
	
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	
}
