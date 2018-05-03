package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

public class PageInfoBean extends BaseBean {
	private int pageCount;
	private int index;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean hasNext() {
		return index < pageCount;
	}

}
