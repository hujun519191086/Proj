package com.qc188.com.engine;

import com.qc188.com.bean.MsgContentBean;

public interface MsgContentEngine {
	public MsgContentBean getContent_async(int msg_id, String url, int index);

	public MsgContentBean getADVContent_async(String url, int index);
}
