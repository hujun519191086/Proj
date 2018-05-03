package com.qc188.com.engine;

import java.util.List;

import com.qc188.com.bean.BrandDetailEngineBean;
import com.qc188.com.bean.BrandDetailMsgBean;

public interface BrandDetailEngine {
	public int getJsonToMatchBean(int brandid);

	public BrandDetailMsgBean getBrandDetailBean();

	public List<BrandDetailEngineBean> getBrandEngineBean();

}
