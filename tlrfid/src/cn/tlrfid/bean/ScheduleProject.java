package cn.tlrfid.bean;

import android.view.View;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;

public class ScheduleProject extends BaseBean {
	
	public int scheduleId;
	public String name;
	public String errMessage;
	public int state;
	@Override
	public String toString() {
		return "ScheduleProject [scheduleId=" + scheduleId + ", name=" + name + ", errMessage=" + errMessage
				+ ", state=" + state + "]";
	}
	
	
	
}
