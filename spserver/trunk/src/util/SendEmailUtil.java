package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * 发邮件工具
 * 
 * @author jieranyishen
 * 
 */
public class SendEmailUtil {

	// 发送邮件
	public static String send(String targetEmail) {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.yeah.net");
		email.setAuthentication("jingpc@yeah.net", "741852963temp");

		try {
			email.addTo(targetEmail);//目标email
			
			String verificationCode = WordUtil.getEmailVerificationCode();
			email.setFrom("jingpc@yeah.net","海购");//把验证码当做账户的备注名.以后好找一点
			email.setSubject("欢迎您注册海购账户");
			email.setMsg("欢迎您注册海购账户,这是您的验证码:" + verificationCode);
			email.send();
			return verificationCode;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	
	/**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
	
	
}
