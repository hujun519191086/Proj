package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;
import com.qc188.com.util.LogUtil;

/**
 * 详情图片列表
 * 
 * @author mryang
 * 
 */
public class BrandPicBean extends BaseBean {
	private static final String TAG = "BrandPicBean";
	private String type;
	private String color;
	private String image_url;
	private int imageId;
	private String introduce;

	private String small_image_url;

	public String getSmall_image_url() {
		return small_image_url;
	}

	public void setSmall_image_url(String small_image_url) {
		this.small_image_url = small_image_url;
	}

	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {

		LogUtil.d(TAG, "type:" + type);
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		LogUtil.d(TAG, "color:" + color);
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Override
	public String toString() {
		return "BrandPicBean [type=" + type + ", color=" + color
				+ ", image_url=" + image_url + ", imageId=" + imageId
				+ ", introduce=" + introduce + "]";
	}

}
