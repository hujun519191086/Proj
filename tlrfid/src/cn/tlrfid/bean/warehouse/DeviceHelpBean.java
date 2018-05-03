package cn.tlrfid.bean.warehouse;

import cn.tlrfid.framework.BaseBean;

public class DeviceHelpBean extends BaseBean {
	private String assetsCode;
	private String assetNameName;
	private String lastConserveDate;
	private String purchaseDate;
	private String picPath;
	private String model;
	private int id;
	private String conserveType;
	private String conservePeriod;
	private String assetCategoryName;
	private String usefulLife;
	private String modelName;
	private String nextConserveDate ;
	
	public String getNextConserveDate() {
		return nextConserveDate;
	}

	public void setNextConserveDate(String nextConserveDate) {
		this.nextConserveDate = nextConserveDate;
	}

	public String getAssetsCode() {
		return assetsCode;
	}
	
	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}
	
	public String getAssetNameName() {
		return assetNameName;
	}
	
	public void setAssetNameName(String assetNameName) {
		this.assetNameName = assetNameName;
	}
	
	public String getLastConserveDate() {
		return lastConserveDate;
	}
	
	public void setLastConserveDate(String lastConserveDate) {
		this.lastConserveDate = lastConserveDate;
	}
	
	public String getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getConserveType() {
		return conserveType;
	}
	
	public void setConserveType(String conserveType) {
		this.conserveType = conserveType;
	}
	
	public String getConservePeriod() {
		return conservePeriod;
	}
	
	public void setConservePeriod(String conservePeriod) {
		this.conservePeriod = conservePeriod;
	}
	
	public String getAssetCategoryName() {
		return assetCategoryName;
	}
	
	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}
	
	public String getUsefulLife() {
		return usefulLife;
	}
	
	public void setUsefulLife(String usefulLife) {
		this.usefulLife = usefulLife;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Override
	public String toString() {
		return "DeviceHelpBean [assetsCode=" + assetsCode + ", assetNameName=" + assetNameName + ", lastConserveDate="
				+ lastConserveDate + ", purchaseDate=" + purchaseDate + ", picPath=" + picPath + ", model=" + model
				+ ", id=" + id + ", conserveType=" + conserveType + ", conservePeriod=" + conservePeriod
				+ ", assetCategoryName=" + assetCategoryName + ", usefulLife=" + usefulLife + ", modelName="
				+ modelName + "]";
	}
	
}
