package control.db.personDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import control.bean.Person;
import control.inter.DBErrorCode;
import control.inter.DataPair;

public class IOSDB extends CommDB {

	// 累了 明天弄
	public DataPair<DBErrorCode, Person> login(String mail, String pass,
			String deviceIds, HttpServletResponse response) {

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
			// 插入deviceid
			return new DataPair<DBErrorCode, Person>(DBErrorCode.SUCCESS,
					person);
		} else {
			return new DataPair<DBErrorCode, Person>(
					DBErrorCode.LOGIN_ERROR_NO_USER, null);
		}

	}

	// 更新设备在线状态.
	public void updateDeviceId(String userId, String deviceId) {
		String sql = "UPDATE login set deviceid=?,logintime=? WHERE pid=?";

		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setString(ps, 1, deviceId);
		setString(ps, 2, System.currentTimeMillis() + "");
		setString(ps, 3, userId);
		int rs = executeUpdate(ps);
	}

}
