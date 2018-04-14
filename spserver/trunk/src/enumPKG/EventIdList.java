package enumPKG;

import javax.servlet.http.HttpServletRequest;

public enum EventIdList {
	
	UNKNOW_ERROR(99999,"未知错误"),
	
	
	REQUEST_REGIST_SEND_MAIL(10001,""),//发送邮件	验证码
	RESPONSE_REGIST_MAIL_FAILE_BEUSE(20002,"邮箱已被使用"),
	RESPONSE_MAIL_SEND_ERROR(20003,"验证码发送失败"),
	RESPONSE_MAIL_IS_EMPTY(20004,"邮箱为空!"),
	RESPONSE_MAIL_IS_CANNOT_TO_USE(20005,"请输入正确的邮箱格式!"),
	
	REQUEST_REGIST_SEND_MESSAGE(10401,""),//发送短信	验证码
	RESPONSE_REGIST_PHONENUM_FAILE_BEUSE(20402,"手机号已被使用"),
	RESPONSE_MESSAGE_SEND_ERROR(20403,"验证码发送失败"),
	RESPONSE_PHONENUM_IS_EMPTY(20404,"手机号为空!"),
	RESPONSE_PHONENUM_IS_CANNOT_TO_USE(20005,"请输入正确的手机号码!"),
	
	REQUEST_REGIST(10101,""),//注册账号
	RESPONSE_CHECK_EMAIL_IN_REGIST_SUCCESS(20101,""),// 成功
	RESPONSE_CHECK_EMAIL_IN_REGIST_BEUSE(20102,"邮箱被使用"), 
	RESPONSE_CHECK_EMAIL_IN_REGIST_SOSIMPLE(20103,"请输入正确的邮箱格式"),
	RESPONSE_PASS_TOO_EASE(20104,"密码过于简单"),
	RESPONSE_VER_CODE_ERROR(20105,"校验码验证失败"),
	RESPONSE_REGIST_ERROR(20106,"注册失败"),
	
	REQUEST_LOGIN(10201,""),//登录
	RESPONSE_LOGIN_PASS_ERROR(20201,"登录账号或者密码出错"),
//	RESPONSE_LOGIN_NO_MAIL(20202,"该邮箱没注册"),
	RESPONSE_LOGIN_ACCOUNT_BE_LOCK(20203,"该账号被锁定"),
	LOGIN_FAILT_NO_DEVICE_ID(20204,"没有上传手机的id"),
	
	LOAD_GOODS(10301,""),//加载物品消息
	LOAD_GOODS_FAILT_NO_GOODS(20301,"没有物品"),
	
	REQUEST_UPDATE_PASSWORD(10501,""),//修改密码
	RESPONSE_OLD_PASSWORD_ERROR(20501,"旧密码错误"),
	RESPONSE_UPDATE_PASSWORD_ERROR(20502,"修改密码失败"),
	
	REQUEST_UPDATE_PHONENUM_SENDVCODE(10601,""),//修改手机号 验证码
	
	REQUEST_UPDATE_MAIL(10701,""),//修改邮箱
	
	
	LOGIN_SESSION_ERROR(99996,"登录信息不匹配"),
	IN_SPEED_LIMIT(99997,"刷新动作太快,请稍后再重试"),
	SUCCESS(99998,"");//success

	
	public final int eventId;
	public final String errorMsg;
	private EventIdList(int eventId,String errorMsg)
	{
		this.eventId = eventId;
		this.errorMsg = errorMsg;
	}
	public static int getEvent(HttpServletRequest request) {
		String eventId = request.getParameter("eventId");
		return Integer.valueOf(eventId);
	}
}
