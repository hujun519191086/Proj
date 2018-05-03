package cn.tlrfid.bean.warehouse;

import java.util.List;

public class DevicePagerBean {
	private List<DevicePagerItemBean> items;
	private int page;
	private int pageSize;
	private List<Integer> pages;
	private int totalCount;
	private int totalPage;
	
	@Override
	public String toString() {
		return "DevicePagerBean [items=" + items + ", page=" + page + ", pageSize=" + pageSize + ", pages=" + pages
				+ ", totalCount=" + totalCount + ", totalPage=" + totalPage + "]";
	}

	public List<DevicePagerItemBean> getItems() {
		return items;
	}
	
	public void setItems(List<DevicePagerItemBean> items) {
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
	
	public List<Integer> getPages() {
		return pages;
	}
	
	public void setPages(List<Integer> pages) {
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
