package com.qc188.com.framwork;

import java.io.Serializable;

/**
 * 主要是为了暴露Frameadapter调用gettag方法
 * 
 * @author jieranyishen
 * 
 */
public @SuppressWarnings("serial")
class BaseBean implements Serializable {
	public long getTag() {
		return 0;
	}
	
	public String getInTag() {
		return "";
	}
}
