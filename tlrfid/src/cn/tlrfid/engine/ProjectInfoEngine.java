package cn.tlrfid.engine;

import cn.tlrfid.framework.BaseBean;

public interface ProjectInfoEngine {
	public <T> T getProjectInfo(String url, Class<? extends BaseBean> clazz);
	
}
