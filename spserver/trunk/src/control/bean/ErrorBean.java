package control.bean;

import enumPKG.EventIdList;

/**
 * 错误码返回
 * @author jieranyishen
 *
 */
public class ErrorBean extends BaseBean{

	public ErrorBean()
	{
		setErrorCode( EventIdList.UNKNOW_ERROR.eventId);
	}
}
