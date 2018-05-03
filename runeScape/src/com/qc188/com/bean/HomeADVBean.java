package com.qc188.com.bean;

public class HomeADVBean {
	private Integer adv_id;
	private String imageUrl;
	private String msg_id;
	private String imageTitle;
	private String time;
	private String from;
	private String name;
	private String is_self;
	private int page_count;
	private int index;
	private String detail_content;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIs_self() {
		return is_self;
	}

	public void setIs_self(String is_self) {
		this.is_self = is_self;
	}

	public int getPage_count() {
		return page_count;
	}

	public void setPage_count(int page_count) {
		this.page_count = page_count;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getDetail_content() {
		return detail_content;
	}

	public void setDetail_content(String detail_content) {
		this.detail_content = detail_content;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public Integer getAdv_id() {
		return adv_id;
	}

	public void setAdv_id(Integer adv_id) {
		this.adv_id = adv_id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	@Override
	public String toString() {
		return "HomeADVBean [adv_id=" + adv_id + ", imageUrl=" + imageUrl + ", msg_id=" + msg_id + ", imageTitle=" + imageTitle + ", time=" + time + ", from="
				+ from + ", name=" + name + ", is_self=" + is_self + ", page_count=" + page_count + ", index=" + index + ", detail_content=" + detail_content
				+ "]";
	}

}
