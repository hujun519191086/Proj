package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

/**
 * 车系首页每个条目中的详细信息
 * 
 * @author mryang
 * 
 */
public class CopyOfbrandDetailEngineItem extends BaseBean {

	/**
	 * [ { "introduce": "2015款30TFIS搜懂舒适型", "sale": "27.28万", "detail":
	 * "前置前驱6挡手动", "brandId": 1236123 }, { "introduce": "2015款30TFIS搜懂舒适型",
	 * "sale": "27.28万", "detail": "前置前驱6挡手动", "brandId": 1236123 } ]
	 */

	private String introduce;
	private String sale;
	private String detail;
	private int brandId;
	private int stopsale;

	public static final int ON_SALE = 0; // 在售
	public static final int NOT_SALE = 1;// 停產

	public int getStopsale() {
		return stopsale;
	}

	public void setStopsale(int stopsale) {
		this.stopsale = stopsale;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	@Override
	public String toString() {
		return "CopyOfbrandDetailEngineItem [introduce=" + introduce
				+ ", sale=" + sale + ", detail=" + detail + ", brandId="
				+ brandId + "]";
	}

}
