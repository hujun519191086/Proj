package com.qc188.com.bean;

public class SortBean {
	public int brand_id;
	public String type_id;
	public String car_brand;
	public String brand_url;

	public int getBrand_id() {
		return brand_id;
	}

	@Override
	public String toString() {
		return "SortBean [brand_id=" + brand_id + ", type_id=" + type_id + ", car_brand=" + car_brand + ", brand_url=" + brand_url + "]";
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public String getBrand_url() {
		return brand_url;
	}

	public void setBrand_url(String brand_url) {
		this.brand_url = brand_url;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

}
