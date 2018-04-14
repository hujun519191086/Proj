package control.bean;

import enumPKG.EventIdList;

/**
 * 所有发送到客户端的bean都需要继承这个
 * 
 * @author jieranyishen
 * 
 */
public class BaseBean {

	private String errorMsg;
	private int errorCode = EventIdList.SUCCESS.eventId;// 默认是成功

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
