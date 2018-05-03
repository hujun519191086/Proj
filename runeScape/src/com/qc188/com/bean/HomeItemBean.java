package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

public class HomeItemBean extends BaseBean {
	private String car_imageUrl;//
	private String msg_title;//
	private String msg_comments;//
	private String msg_isHead;//
	private String time;
	private double msg_id;
	private String lastJson;
	
	public String getLastJson() {
		return lastJson;
	}
	
	public void setLastJson(String lastJson) {
		this.lastJson = lastJson;
	}
	
	@Override
	public String toString() {
		return "HomeItemBean [car_imageUrl=" + car_imageUrl + ", msg_title=" + msg_title + ", msg_comments="
				+ msg_comments + ", msg_isHead=" + msg_isHead + ", time=" + time + ", msg_id=" + msg_id + "]";
	}
	
	public double getMsg_id() {
		return msg_id;
	}
	
	public void setMsg_id(double msg_id) {
		this.msg_id = msg_id;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getCar_imageUrl() {
		return car_imageUrl;
	}
	
	public void setCar_imageUrl(String car_imageUrl) {
		this.car_imageUrl = car_imageUrl;
	}
	
	public String getMsg_title() {
		return msg_title;
	}
	
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	
	public String getMsg_comments() {
		return msg_comments;
	}
	
	public void setMsg_comments(String msg_comments) {
		this.msg_comments = msg_comments;
	}
	
	public String getMsg_isHead() {
		return msg_isHead;
	}
	
	public void setMsg_isHead(String msg_isHead) {
		this.msg_isHead = msg_isHead;
	}
	
}
