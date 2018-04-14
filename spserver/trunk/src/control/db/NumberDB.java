package control.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import control.inter.DBErrorCode;
import frame.DBFrame;
import frame.SingleFactory;

public class NumberDB extends DBFrame {

	public DBErrorCode createNumber(String gid, int count) {
		String params = "ID Bigint(8) unsigned Primary key Auto_Increment,luckNumber varchar(10) Primary key,userId varchar(80),injonyTime timestamp,userName varchar(225),userIP varchar(20)";
		createTab(gid, SingleFactory.NUMBER_URL, params);
		Connection conn = null;

		// IFNULL(region_name,0) 如果是空 . 则用什么数据
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			String findCountSQL = "select count(luckNumber)cou from " + gid;
			PreparedStatement st = conn.prepareStatement(findCountSQL);

			ResultSet rs = st.executeQuery();
			int columnCount = 0;
			if (rs.next()) {
				columnCount = rs.getInt(1);
			}

			close(null, st, rs);
			if (columnCount > 0) {// 已经计算出号码了. 那就不需要计算.
				return DBErrorCode.NUMBER_CREATE_ERROR_NUMBER_IN_USE;
			}

			int luckNumberBase = gid.hashCode();
			String insertNumberSQL = "INSERT INTO " + gid
					+ "(luckNumber) VALUES(?)";
			for (int i = 0; i < count; i++) {
				st = conn.prepareStatement(insertNumberSQL);
				st.setString(1, luckNumberBase + i+"");
				st.executeUpdate();
				close(null, st, null);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();

			rollBack(conn);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					close(conn, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

		return DBErrorCode.SUCCESS;
	}
}
