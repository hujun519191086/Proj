package cn.tlrfid.bean.warehouse;

import cn.tlrfid.framework.BaseBean;

/**
 * 设备详情
 * 
 * @author Administrator
 * 
 */
public class DeviceDetailBean extends BaseBean {
	
	private String assetCategoryName;
	private String assetNameName;
	private String assetsCode;
	private String conservePeriod;
	private String conserveTypeName;
	private String factoryName;
	private int id;
	private String lastConserveDate;
	private String modelName;
	private String personName;
	private String picPath;
	private String purchaseDate;
	private String usefulLife;
	private String nextConserveDate;
	private boolean writeCard;
	
	public String getAssetCategoryName() {
		return assetCategoryName;
	}
	
	public void setAssetCategoryName(String assetCategoryName) {
		this.assetCategoryName = assetCategoryName;
	}
	
	public String getAssetNameName() {
		return assetNameName;
	}
	
	public void setAssetNameName(String assetNameName) {
		this.assetNameName = assetNameName;
	}
	
	public String getAssetsCode() {
		return assetsCode;
	}
	
	public void setAssetsCode(String assetsCode) {
		this.assetsCode = assetsCode;
	}
	
	public String getConservePeriod() {
		return conservePeriod;
	}
	
	public void setConservePeriod(String conservePeriod) {
		this.conservePeriod = conservePeriod;
	}
	
	public String getConserveTypeName() {
		return conserveTypeName;
	}
	
	public void setConserveTypeName(String conserveTypeName) {
		this.conserveTypeName = conserveTypeName;
	}
	
	public String getFactoryName() {
		return factoryName;
	}
	
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastConserveDate() {
		return lastConserveDate;
	}
	
	public void setLastConserveDate(String lastConserveDate) {
		this.lastConserveDate = lastConserveDate;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public String getUsefulLife() {
		return usefulLife;
	}
	
	public void setUsefulLife(String usefulLife) {
		this.usefulLife = usefulLife;
	}
	
	public String getNextConserveDate() {
		return nextConserveDate;
	}
	
	public void setNextConserveDate(String nextConserveDate) {
		this.nextConserveDate = nextConserveDate;
	}
	
	public boolean isWriteCard() {
		return writeCard;
	}
	
	public void setWriteCard(boolean writeCard) {
		this.writeCard = writeCard;
	}
	
	@Override
	public String toString() {
		return "DeviceDetailBean [assetCategoryName=" + assetCategoryName + ", assetNameName=" + assetNameName
				+ ", assetsCode=" + assetsCode + ", conservePeriod=" + conservePeriod + ", conserveTypeName="
				+ conserveTypeName + ", factoryName=" + factoryName + ", id=" + id + ", lastConserveDate="
				+ lastConserveDate + ", modelName=" + modelName + ", personName=" + personName + ", picPath=" + picPath
				+ ", purchaseDate=" + purchaseDate + ", usefulLife=" + usefulLife + ", nextConserveDate="
				+ nextConserveDate + ", writeCard=" + writeCard + "]";
	}
	
}
