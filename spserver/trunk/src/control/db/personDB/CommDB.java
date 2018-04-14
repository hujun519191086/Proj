package control.db.personDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;

import util.UserUtil;
import util.WordUtil;
import control.bean.Person;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import control.inter.PersonStatus;
import frame.DBFrame;

public class CommDB extends DBFrame {

	public String enPwd(String pwd) {
		return null;
	}

	public String dePwd(String pwd) {
		return null;
	}

	/**
	 * 从邮箱查询 用户信息
	 * 
	 * @param mailBox
	 * @return
	 */
	public DataPair<DBErrorCode, Person> queryInfo4Mail(String mailBox) {
		Person person = new Person();
		String sql = "select * from login where mail=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, mailBox);
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
	/**
	 * 从手机号查询 用户信息
	 * 
	 * @param mailBox
	 * @return
	 */
	public DataPair<DBErrorCode, Person> queryInfo4PhoneNum(String phoneNum) {
		Person person = new Person();
		String sql = "select * from login where phonenum=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, phoneNum);
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
	/**
	 * 根据用户id 获取用户信息
	 * 
	 */
	public DataPair<DBErrorCode,Person> queryInfo4Pid(long pid){
		Person person = new Person();
		String sql = "select * from login where pid=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setLong(ps, 1, pid);
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
	/**
	 * 修改用户信息
	 * 
	 */
	public DBErrorCode updateUserInfo(long pid,String nickname,String address){
		String sql = "update login set nickname=?,address=? where pid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		
		setString(ps,1,nickname);
		setString(ps,2,address);
		setLong(ps,3,pid);
		int updateCloum = executeUpdate(ps);
		
		close(ps);
		if (updateCloum > 0) {

			return DBErrorCode.SUCCESS;

		}
		return DBErrorCode.UPDATE_USER_INFO_FAILT;
	}
	/**
	 * 邮箱更新 验证码
	 * 
	 * @param mailBox
	 * @param verificationCode
	 * @param person
	 * @return
	 */
	public DBErrorCode updateMailVerificationToUser(String mailBox,
			String verificationCode, Person person) {

		String sql = "";// 插入:邮箱,验证码,用户状态
		if (person == null || WordUtil.isEmpty(person.getMailBox())) {
			sql = "INSERT INTO login (verificationCode,personstatus,mail) VALUES(?,?,?)";
		} else {
			sql = "UPDATE login set verificationCode=?,personstatus=? WHERE mail=?";
		}
		//
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, verificationCode);
		setInt(ps, 2, PersonStatus.NOT_EMAIL_VERIFICATION.value);
		setString(ps, 3, mailBox);
		int updateCloum = executeUpdate(ps);

		close(ps);
		if (updateCloum > 0) {

			return DBErrorCode.SUCCESS;

		}
		return DBErrorCode.MAIL_VERIFICATION_INSERT_FAILT;
	}
	/**
	 * 手机注册更新验证码
	 * 
	 * @param mailBox
	 * @param verificationCode
	 * @param person
	 * @return
	 */
	public DBErrorCode updatePhoneNumVerificationToUser(String phoneNum,
			String verificationCode, Person person) {

		String sql = "";// 插入:手机号,验证码,用户状态
		if (person == null || WordUtil.isEmpty(person.getPhoneNum())) {
			sql = "INSERT INTO login (verificationCode,personstatus,phonenum) VALUES(?,?,?)";
		} else {
			sql = "UPDATE login set verificationCode=?,personstatus=? WHERE phonenum=?";
		}
		//
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, verificationCode);
		setInt(ps, 2, PersonStatus.NOT_PHONENUM_VERIFICATION.value);
		setString(ps, 3, phoneNum);
		int updateCloum = executeUpdate(ps);

		close(ps);
		if (updateCloum > 0) {

			return DBErrorCode.SUCCESS;

		}
		return DBErrorCode.PHONENUM_VERIFICATION_INSERT_FAILT;
	}
	/**
	 * 点击注册按钮
	 * 
	 * @param mailBox
	 * @param pwd
	 * @param person
	 * @return
	 */
	public DataPair<DBErrorCode,Person> registMailPerson(String mailBox, String pwd,
			Person person) {

		// WHERE
		String sql = "";
		if (person.getMailBox() == null || person.getMailBox() == "") {
			return  new DataPair<DBErrorCode, Person>(DBErrorCode.LOGIN_ERROR_NO_USER,null);
		} else {
			sql = "UPDATE login set password=?,registtime=?,pid=?,personstatus=?,nickname=? WHERE mail=?";
		}
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, pwd);
		String time = System.currentTimeMillis() + "";
		setString(ps, 2, time);
		String pid = WordUtil.getId()+"";
		setString(ps, 3,pid);
		setInt(ps, 4, PersonStatus.ALL_CLEAR.value);
		//昵称默认为注册邮箱
		setString(ps,5,UserUtil.mailMissing(mailBox));
		setString(ps, 6, mailBox);
		int cloum = executeUpdate(ps);
		

		close(ps);
		if (cloum > 0) {

			person.setPid(pid);
			person.setNickname(UserUtil.mailMissing(mailBox));
			return new DataPair<DBErrorCode, Person>(DBErrorCode.SUCCESS,person);

		}
		return new DataPair<DBErrorCode, Person>(DBErrorCode.REGIST_FAILT,null);
	}
	/**
	 * 手机注册
	 * 
	 * @param mailBox
	 * @param pwd
	 * @param person
	 * @return
	 */
	public DataPair<DBErrorCode,Person> registPhoneNumPerson(String phoneNum, String pwd,
			Person person) {

		// WHERE
		String sql = "";
		if (person.getPhoneNum() == null || person.getPhoneNum() == "") {
			return  new DataPair<DBErrorCode, Person>(DBErrorCode.LOGIN_ERROR_NO_USER,null);
		} else {
			sql = "UPDATE login set password=?,registtime=?,pid=?,personstatus=?,nickname=? WHERE phonenum=?";
		}
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, pwd);
		String time = System.currentTimeMillis() + "";
		setString(ps, 2, time);
		String pid = WordUtil.getId()+"";
		setString(ps, 3,pid);
		setInt(ps, 4, PersonStatus.ALL_CLEAR.value);
		//昵称默认为注册邮箱
		setString(ps,5,UserUtil.phoneNumMissing(phoneNum));
		setString(ps, 6, phoneNum);
		int cloum = executeUpdate(ps);
		

		close(ps);
		if (cloum > 0) {

			person.setPid(pid);
			person.setNickname(UserUtil.phoneNumMissing(phoneNum));
			return new DataPair<DBErrorCode, Person>(DBErrorCode.SUCCESS,person);

		}
		return new DataPair<DBErrorCode, Person>(DBErrorCode.REGIST_FAILT,null);
	}
	/**
	 * pid查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public DataPair<DBErrorCode, Person> queryInfo4Pid(int id) {

		Person person = new Person();
		String sql = "select * from login where pid=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setInt(ps, 1, id);
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

	// 数据转化, ResultSet转成Person
	public boolean resultSet2Person(ResultSet rs, Person person) {
		try {
			if (rs.next()) {
				person.setMailBox(rs.getString("mail"));
				person.setLoginTime(rs.getLong("logintime"));
				person.setSex("m".equals(rs.getString("sex")));
				person.setWeight(rs.getFloat("weight"));
				person.setHigh(rs.getFloat("high"));
				String ts = rs.getString("registtime");
				person.setRegistTime(WordUtil.isEmpty(ts) ? 0 : Long
						.valueOf(ts));
				person.setVerificationCode(rs.getString("verificationCode"));
				person.setPersonId(rs.getInt("personid"));
				person.setCounty(rs.getString("county"));
				person.setNickname(rs.getString("nickname"));
				person.setPhoneNum(rs.getString("phonenum"));
				person.setDeviceId(rs.getString("deviceid"));
				person.setCity(rs.getString("city"));
				person.setProvince(rs.getString("province"));
				person.setPid(rs.getString("pid"));
				person.setPersonstatus(rs.getInt("personstatus"));
				person.setAddress(rs.getString("address"));

				return true;
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
