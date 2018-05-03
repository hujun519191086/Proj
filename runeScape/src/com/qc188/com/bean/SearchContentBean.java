package com.qc188.com.bean;

import android.text.TextUtils;

import com.qc188.com.framwork.BaseBean;

public class SearchContentBean extends BaseBean {
	private int car_id;
	private String car_name;
	private String car_price;
	private String car_imageUrl;
	private String chang;

	public String getChang() {
		return chang;
	}

	public void setChang(String chang) {
		this.chang = chang;
	}

	public int getCar_id() {
		return car_id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}

	public String getCar_name() {
		return car_name;
	}

	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}

	public String getCar_price() {
		return (car_price.equals("0-0") || TextUtils.isEmpty(car_price)) ? "未上市" : car_price + "万";
	}

	public void setCar_price(String car_price) {
		this.car_price = car_price;
	}

	public String getCar_imageUrl() {
		return car_imageUrl;
	}

	public void setCar_imageUrl(String car_imageUrl) {
		this.car_imageUrl = car_imageUrl;
	}

	@Override
	public String toString() {
		return "SearchContentBean [car_id=" + car_id + ", car_name=" + car_name + ", car_price=" + car_price + ", car_imageUrl=" + car_imageUrl + ", chang="
				+ chang + "]";
	}

}
