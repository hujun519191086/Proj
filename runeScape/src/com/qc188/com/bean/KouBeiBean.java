package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

/**
 * 口碑
 * 
 * @author mryang
 * 
 */
public class KouBeiBean extends BaseBean {
	private int reputID;
	private String reputName;

	private String fromWhere;

	private String whereName;
	
	
	

	public String getWhereName() {
		return whereName;
	}

	public void setWhereName(String whereName) {
		this.whereName = whereName;
	}

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}

	public int getReputID() {
		return reputID;
	}

	public void setReputID(int reputID) {
		this.reputID = reputID;
	}

	public String getReputName() {
		return reputName;
	}

	public void setReputName(String reputName) {
		this.reputName = reputName;
	}

	@Override
	public String toString() {
		return "KouBeiBean [reputID=" + reputID + ", reputName=" + reputName + "]";
	}

}
