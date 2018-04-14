package control.db.personDB;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.UserUtil;
import util.WordUtil;
import control.bean.Person;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;

public class WebDB extends CommDB {


	public DataPair<DBErrorCode, Person> mailLogin(String mail, String pass) {

		// LOGIN_ERROR_PASS_ERROR,//密码错误
		// LOGIN_ERROR_NO_USER,//没有这个用户

		Person person = new Person();
		String sql = "select * from login where mail=? and password=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, mail);
		setString(ps, 2, pass);
		ResultSet rs = executeQuery(ps);
		boolean hasData = resultSet2Person(rs, person);

		close(ps, rs);
		if (hasData) {
			return new DataPair<DBErrorCode, Person>(DBErrorCode.SUCCESS,
					person);
		} else {
			return new DataPair<DBErrorCode, Person>(
					DBErrorCode.LOGIN_ERROR_NO_USER, null);
		}

	}
	public DataPair<DBErrorCode, Person> phoneLogin(String phoneNum, String pass) {
		
		// LOGIN_ERROR_PASS_ERROR,//密码错误
		// LOGIN_ERROR_NO_USER,//没有这个用户
		
		Person person = new Person();
		String sql = "select * from login where phonenum=? and password=?";
		
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, phoneNum);
		setString(ps, 2, pass);
		ResultSet rs = executeQuery(ps);
		boolean hasData = resultSet2Person(rs, person);
		
		close(ps, rs);
		if (hasData) {
			return new DataPair<DBErrorCode, Person>(DBErrorCode.SUCCESS,
					person);
		} else {
			return new DataPair<DBErrorCode, Person>(
					DBErrorCode.LOGIN_ERROR_NO_USER, null);
		}
		
	}
	public static void autoLogin(HttpServletRequest request,
			HttpServletResponse response, Person person, String pass) {
		String autoLogin = request.getParameter("autoLogin");

		if(WordUtil.isEmpty(autoLogin))
		{
			return;
		}
		//清除cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null ) {
			Cookie cookieMail = new Cookie("mail", null);
			Cookie cookiePhone = new Cookie("phone", null);
			Cookie cookiePass = new Cookie("pass", null);

			cookieMail.setMaxAge(0);
			cookiePhone.setMaxAge(0);
			cookiePass.setMaxAge(0);

			response.addCookie(cookieMail);
			response.addCookie(cookiePhone);
			response.addCookie(cookiePass);
		}
		// 自动登录
		if (autoLogin.equals("1")) {

			int date = 60 * 60 * 24 * 7 * 2;// 2周

			Cookie cookieMail = new Cookie("mail", person.getMailBox());
			Cookie cookiePhone = new Cookie("phone", person.getPhoneNum());
			Cookie cookiePass = new Cookie("pass", pass);

			cookieMail.setMaxAge(date);
			cookiePhone.setMaxAge(date);
			cookiePass.setMaxAge(date);

			response.addCookie(cookieMail);
			response.addCookie(cookiePhone);
			response.addCookie(cookiePass);

		} 
	}
	public boolean checkOldPassword(String pid,String oldPassword){
		String sql = "select password from login where pid = ?";
		
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		ResultSet rs = null;
		try {
			setString(ps, 1, pid);
			rs = executeQuery(ps);
			if(rs.next()){
				if(oldPassword.equals(rs.getString("password"))){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally{
			close(ps, rs);
		}
		return false;
	}
	public void updatePassword(PrintWriter out,String pid,String oldPassword,String newPassword){
		String sql = "update login set password = ? where pid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps,1,newPassword);
		setString(ps,2,pid);
		int updateResult = executeUpdate(ps);
		close(ps);
		if((updateResult>0)){
			UserUtil.sendNormalErrorMsg(out, EventIdList.SUCCESS);
			return;
		}
		else{
			UserUtil.sendNormalErrorMsg(out, EventIdList.RESPONSE_UPDATE_PASSWORD_ERROR);
			return;
			
		}
	
	}
	public boolean hasPhoneNum(String pid){
		String sql = "select phonenum from login where gid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps,1,pid);
		ResultSet rs = executeQuery(ps);
		try {
			if(rs.next()){
				if(!WordUtil.isEmpty(rs.getString("password"))){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally{
			close(ps, rs);
		}
		return false;
	}
	public boolean hasMail(String pid){
		String sql = "select mail from login where gid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps,1,pid);
		ResultSet rs = executeQuery(ps);
		try {
			if(rs.next()){
				if(!WordUtil.isEmpty(rs.getString("mail"))){
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		finally{
			close(ps, rs);
		}
		return false;
	}
	public DBErrorCode updateVerification(String verificationCode, String pid) {
		
		String sql = "UPDATE login set verificationCode=? WHERE pid=?";
		//
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, verificationCode);
		setString(ps, 2, pid);
		int updateCloum = executeUpdate(ps);

		close(ps);
		if (updateCloum > 0) {

			return DBErrorCode.SUCCESS;

		}
		return DBErrorCode.PHONENUM_VERIFICATION_INSERT_FAILT;
	}
	public String getPhoneNum(String pid){
		String sql = "select * from login where pid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps,1,pid);
		ResultSet rs = executeQuery(ps);
		try {
			if(rs.next()){
				String phoneNum = rs.getString("phonenum");
				return phoneNum;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		finally{
			close(ps);
		}
		return "";
	}
	public String getMail(String pid){
		String sql = "select * from login where pid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps,1,pid);
		ResultSet rs = executeQuery(ps);
		try {
			if(rs.next()){
				String mail = rs.getString("mail");
				return mail;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		finally{
			close(ps);
		}
		return "";
	}


}
