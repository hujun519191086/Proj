package cn.tlrfid.engine;

import java.util.List;

import cn.tlrfid.bean.warehouse.AssetsCategoryBean;
import cn.tlrfid.bean.warehouse.DevicePagerBean;

/**
 * 获取仓库管理数据
 * 
 * @author Administrator
 * 
 */
public interface WarehouseEngine {
	// 第一步:联网准备数据
	public String initConnectionJson(String url);
	
	// 第二步,获取assets. 主要是顶部条目数据
	public List<AssetsCategoryBean> getAssetsList();
	
	// 第三步, 获取内容item数据
	public DevicePagerBean getDevicePager();
}
