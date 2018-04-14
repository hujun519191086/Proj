package util;



public class SendMessageUtil {
	// 发送短信
		public static String send(String phoneNum) {
			String url = "http://222.73.117.169/msg/HttpBatchSendSM";// 应用地址
			String account = "N7334722";// 账号
			String pswd = "S93ZHkcCnA3d6e";// 密码
			String mobile = phoneNum;// 手机号码，多个号码使用","分割
			
			String verificationCode = WordUtil.getEmailVerificationCode();
			String msg = "【海淘云购】您的验证码为"+verificationCode+",请输入完成验证。为了您的账户安全，请勿将验证码告诉任何人。如非本人操作，可不必理会。";// 短信内容
			boolean needstatus = false;// 是否需要状态报告，需要true，不需要false
			String extno = null;// 扩展码
			
			try {
				String returnString = HttpSender.batchSend(url, account, pswd, mobile, msg, needstatus, extno);
				System.out.println(returnString);
				return verificationCode;
				// TODO 处理返回值,参见HTTP协议文档
			} catch (Exception e) {
				// TODO 处理异常
				e.printStackTrace();
			}
			
			return "";
		}
}
