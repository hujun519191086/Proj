package com.qc188.com.engine;

import java.util.ArrayList;

import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.bean.SortBean;

public interface SearchEngine {
	public ArrayList<SortBean> getSearchList_async();

	public ArrayList<SortBean> getSortList(String json);

	public ArrayList<SearchContentBean> getSearchContent_Async(String brandId);

	public ArrayList<SearchContentBean> getSearchOnsale_Async(String brandId);
}
