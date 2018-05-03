package com.qc188.com.engine;

import java.util.ArrayList;
import java.util.List;

import com.qc188.com.bean.HomeADVBean;
import com.qc188.com.bean.HomeItemBean;

public interface MainHomeEngine {
	public ArrayList<HomeItemBean> getHomeItemList_async();
	
	public List<HomeADVBean> getHomeAdv_Async();
	
	public ArrayList<HomeItemBean> getItemList_async(String url);
	
}
