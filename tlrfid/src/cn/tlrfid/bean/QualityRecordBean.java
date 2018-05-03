package cn.tlrfid.bean;

import java.util.ArrayList;

import cn.tlrfid.framework.BaseBean;

public class QualityRecordBean extends BaseBean {
	private ArrayList<QualityBean> items;
	private int page;
	private int pageSize;
	private String pages;
	private int totalCount;
	private int totalPage;
	
	public ArrayList<QualityBean> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<QualityBean> items) {
		this.items = items;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getPages() {
		return pages;
	}
	
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
