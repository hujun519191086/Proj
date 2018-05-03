package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

public class MsgContentBean extends BaseBean {
	private int msg_id;
	private String deital_title;
	private String time;
	private String from;
	private String name;
	private String is_self;
	private int page_count;
	private int index;
	private String detail_content;

	private boolean isFromAdv = false;

	public boolean isFromAdv() {
		return isFromAdv;
	}

	// 12-05 11:29:10.999: D/BrandDetail_Pic(1836):
	// url:http://www.qc188.com/app/carphoto.asp?id=1313&cid=3987&page=1
	// 12-05 11:31:24.221: D/BrandDetail_Pic(1836):
	// url:http://www.qc188.com/app/carphoto.asp?id=1313&cid=3985&page=1

	public void setFromAdv(boolean isFromAdv) {
		this.isFromAdv = isFromAdv;
	}

	public String getDetail_content() {
		detail_content = (detail_content.endsWith("&page=") ? detail_content
				: detail_content.substring(0, detail_content.length() - 1));
		detail_content = (detail_content.endsWith("&page=") ? detail_content
				: detail_content.substring(0, detail_content.length() - 1));
		return detail_content + (index + 1);
	}

	public void setDetail_content(String detail_content) {
		if (isFromAdv) {
			this.detail_content = detail_content.substring(0,
					detail_content.length() - 1);
		} else {
			this.detail_content = detail_content;

		}
	}

	public int getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}

	public String getDeital_title() {
		return deital_title;
	}

	public void setDeital_title(String deital_title) {
		this.deital_title = deital_title;
	}

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

	@Override
	public String toString() {
		return "MsgContentBean [msg_id=" + msg_id + ", deital_title="
				+ deital_title + ", time=" + time + ", from=" + from
				+ ", name=" + name + ", is_self=" + is_self + ", page_count="
				+ page_count + ", index=" + index + ", detail_content="
				+ detail_content + "]";
	}

}
