package util;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import control.bean.ErrorBean;
import control.bean.Person;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import control.inter.PersonStatus;
import enumPKG.EventIdList;

public class UserUtil {

	public static int PASSWORD_MIN_LENGTH = 6;
	private static Pattern mailRegex;
	private static Pattern phoneNumRegex;

	public static void CheckLoginStatus() {

	}

	public static void sendNormalErrorMsg(PrintWriter out) {
		ErrorBean eb = new ErrorBean();
		out.write(JSONUtil.toJSON(eb));
		out.flush();
		out.close();
	}

	public static void sendNormalErrorMsg(PrintWriter out, EventIdList code) {
		ErrorBean eb = new ErrorBean();
		eb.setErrorCode(code.eventId);
		eb.setErrorMsg(code.errorMsg);
		out.write(JSONUtil.toJSON(eb));
		out.flush();
		out.close();
	}

	public static boolean checkDBErrorCode(PrintWriter out, DBErrorCode code) {// 对数据库的任何操作,都需要对errorcode做检查
		switch (code) {
		case SUCCESS:
		case LOGIN_ERROR_NO_USER:
			return true;

		case REGIST_FAILT:
			UserUtil.sendNormalErrorMsg(out, EventIdList.RESPONSE_REGIST_ERROR);
			break;

		case MAIL_VERIFICATION_INSERT_FAILT:
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_MAIL_SEND_ERROR);
			break;

		case PASSWORD_ERROR:
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_LOGIN_PASS_ERROR);
			break;
		case GOODS_INSERT_FAILT_CANNOT_CHANGE:
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_LOGIN_PASS_ERROR);
			break;
		//
		// break;不能被使用, 因为没有这个用户不一定是错误的.
		default:
			UserUtil.sendNormalErrorMsg(out, EventIdList.UNKNOW_ERROR);
			break;
		}
		return false;
	}

	public static boolean checkPass(String pass) {
		pass = pass.trim();
		if (!pass.isEmpty() && pass.length() >= UserUtil.PASSWORD_MIN_LENGTH
				&& !UserUtil.isNumber(pass)) {

			return true;// 密码校验通过
		}

		return false;
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 检查邮箱是否在被正常注册的用户所使用.
	public static boolean mailInUse(DataPair<DBErrorCode, Person> person) {

		if (person.first != DBErrorCode.SUCCESS)// 数据库操作都不是成功了.那肯定不是用户
		{
			return false;
		} else if (person.first == DBErrorCode.SUCCESS
				&& person.second.getPersonstatus() == PersonStatus.NOT_EMAIL_VERIFICATION.value) {
			// 处在发送验证码.但是没有进行注册的阶段. 该邮箱是未使用的
			return false;
		} else if (person.first == DBErrorCode.SUCCESS
				&& person.second.getPersonstatus() == PersonStatus.NOT_USER.value) {
			// 处在发送验证码.但是用户状态是空. 该邮箱是未使用的
			return false;
		}

		// 其他状态的时候, 邮箱是被使用的.不能注册
		return true;
	}
	// 检查手机号是否在被正常注册的用户所使用.
		public static boolean phoneNumInUse(DataPair<DBErrorCode, Person> person) {

			if (person.first != DBErrorCode.SUCCESS)// 数据库操作都不是成功了.那肯定不是用户
			{
				return false;
			} else if (person.first == DBErrorCode.SUCCESS
					&& person.second.getPersonstatus() == PersonStatus.NOT_PHONENUM_VERIFICATION.value) {
				// 处在发送验证码.但是没有进行注册的阶段. 该邮箱是未使用的
				return false;
			} else if (person.first == DBErrorCode.SUCCESS
					&& person.second.getPersonstatus() == PersonStatus.NOT_USER.value) {
				// 处在发送验证码.但是用户状态是空. 该邮箱是未使用的
				return false;
			}

			// 其他状态的时候, 邮箱是被使用的.不能注册
			return true;
		}

	/**
	 * 检查该邮箱是否可以登录
	 * 
	 * @return
	 */
	public static boolean accountInLoginLock(DataPair<DBErrorCode, Person> person) {
		if (person.second.getPersonstatus() == PersonStatus.BE_LOCK.value) {// 用户被锁住
			return false;
		}
		return true;
	}

	public static boolean mailIsLegitimate(String mail) {
		if (WordUtil.isEmpty(mail)) {
			return false;// 空, 不合法
		}

		if (mailRegex == null) {// mark_yuan@yeah.net 匹配
			String check = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
			// String check =
			// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			mailRegex = Pattern.compile(check);
		}
		Matcher matcher = mailRegex.matcher(mail);
		boolean isMatched = matcher.matches();
		if (!isMatched) {// 邮箱正则不匹配,不合法
			return false;
		}

		return true;// 剩余, 合法
	}
	public static boolean phoneNumIsLegitimate(String phoneNum){
		if (WordUtil.isEmpty(phoneNum)) {
			return false;// 空, 不合法
		}
		if(phoneNumRegex == null){
			String check = "^1[34578]\\d{9}$";
			phoneNumRegex = Pattern.compile(check);
		}
		Matcher matcher = phoneNumRegex.matcher(phoneNum);
		boolean isMatched = matcher.matches();
		if (!isMatched) {// 邮箱正则不匹配,不合法
			return false;
		}
		return true;
	}

	public static boolean checkVerficationIsSuccess(String uploadV, Person dbV) {

		if (dbV!=null && !WordUtil.isEmpty(dbV.getVerificationCode()) && !WordUtil.isEmpty(uploadV)
				&& dbV.getVerificationCode().equals(uploadV)) {
			return true;// 两者不是空,并且相等.才是检查通过.不然发送错误码
		}
		return false;
	}
	public static String phoneNumMissing(String phoneNum){
		return phoneNum.replace(phoneNum.substring(phoneNum.length()-4),"****");
	}
	public static String mailMissing(String mail){
		return mail.replace(mail.substring(mail.lastIndexOf("@")-4, mail.lastIndexOf("@")), "****");
	}
}
