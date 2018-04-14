package frame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.taglibs.standard.tag.el.sql.SetDataSourceTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.inter.DataPair;

public class DBFrame {

	protected DataSource dbSource;

	public void setDataSource(DataSource ds) {
		dbSource = ds;
	}

	/**
	 * 只是为了不出现try catch
	 * 
	 * @param ps
	 * @return
	 */
	public ResultSet executeQuery(DataPair<Connection, PreparedStatement> ps) {
		try {
			return ps.second.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 只是为了不出现try catch
	 * 
	 * @param sql
	 * @return
	 */
	public int executeUpdate(DataPair<Connection, PreparedStatement> ps) {
		try {

			return ps.second.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public DataPair<Connection, PreparedStatement> getPreparedStatement(
			String sql) {
		return getPreparedStatement(sql, true);

	}

	public DataPair<Connection, PreparedStatement> getPreparedStatement(
			String sql, boolean autoCommit) {
		try {
			Connection conn = getConnection();

			conn.setAutoCommit(autoCommit);
			return new DataPair<Connection, PreparedStatement>(conn,
					conn.prepareStatement(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public synchronized Connection getConnection() throws SQLException {
		return getConnection(dbSource);
	}

	public synchronized Connection getConnection(DataSource ds)
			throws SQLException {
		final Connection conn = ds.getConnection();

		Logger logger = LoggerFactory.getLogger(getClass());
		logger.info("get a DB connection,  db:"+ds);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return conn;
	}

	// 关闭连接的三个重载方法
	public void close(DataPair<Connection, PreparedStatement> conn_ps) {

		close(conn_ps.first, conn_ps.second, null);
	}

	public void close(DataPair<Connection, PreparedStatement> conn_ps,
			ResultSet rs) {

		close(conn_ps.first, conn_ps.second, rs);
	}

	public void close(Connection conn, PreparedStatement ps) {
		close(conn, ps, null);
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {

		// try {
		// if (rs != null && !rs.isClosed()) {
		// rs.close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// try {
		// if (ps != null && !ps.isClosed()) {
		// ps.close();
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 只是为了不在外面try catch
	 * 
	 * @param ps
	 * @param parameterIndex
	 * @param x
	 * @return
	 */
	public boolean setString(DataPair<Connection, PreparedStatement> ps,
			int parameterIndex, String x) {

		try {
			ps.second.setString(parameterIndex, x);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 只是为了不在外面try catch
	 * 
	 * @param ps
	 * @param parameterIndex
	 * @param x
	 * @return
	 */
	public boolean setLong(DataPair<Connection, PreparedStatement> ps,
			int parameterIndex, long x) {
		try {
			ps.second.setLong(parameterIndex, x);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 只是为了不在外面try catch
	 * 
	 * @param ps
	 * @param parameterIndex
	 * @param x
	 * @return
	 */
	public boolean setInt(DataPair<Connection, PreparedStatement> ps,
			int parameterIndex, int x) {
		try {
			ps.second.setInt(parameterIndex, x);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 通用创建表方法
	// 个人表里面增加字段:
	protected boolean createTab(String tabName, String dataBaseName,
			String params) {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = getConnection();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			String queryTable_is_ex = "CREATE TABLE IF NOT EXISTS "
					+ dataBaseName + "." + tabName + "(  " + params + ")";
			ps = connection.prepareStatement(queryTable_is_ex);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, ps);
		}

		return false;
	}

	public void rollBack(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();// 回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
