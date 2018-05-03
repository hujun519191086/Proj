package cn.tlrfid.anno.net1;

public class HttpResult
{
	public boolean isSuccess = false;;
	public String returnString = null;

	public HttpResult(boolean isSuccess, String returnString)
	{
		this.isSuccess = isSuccess;
		this.returnString = returnString;
	}
}