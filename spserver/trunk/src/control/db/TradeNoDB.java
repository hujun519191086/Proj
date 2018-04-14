package control.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import control.bean.TradeNoItem;
import control.inter.DBErrorCode;
import frame.DBFrame;
import frame.SingleFactory;

public class TradeNoDB extends DBFrame{
	public int insertTradeNoDB(String[] orderArray){
		//将当前物品插入到订单库，返回自增长id
		//加入tradeno,orderno,物品gid,物品名称,参与人次,总价格,购买时间
		int Length = orderArray.length;
		String tableName = "tradeNo";
		for(int x =0; x<Length;x++){
			System.out.println(orderArray[x]);
		}
		ResultSet rs = null;
		int tradeno;
		String params = "ID Bigint(8) unsigned Primary key Auto_Increment, orderNo varchar(225), pid Bigint(8) NOT NULL ,goodsName varchar(225),attach varchar(225),totalCount int,buy_Time timestamp";
		createTab(tableName, SingleFactory.TRADENO_URL, params);
		Connection conn = null;
		try {
			String sql = "INSERT INTO " 
						+ tableName 
						+ "(orderNo, pid, goodsName, attach, totalCount) VALUES (?, ?, ?, ?, ?)";
			String sql1 = "SELECT last_insert)id();";
			conn = getConnection();
			conn.setAutoCommit(false);
			PreparedStatement st = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, orderArray[0]);
			st.setLong(2, Long.valueOf(orderArray[1]));
			st.setString(3, orderArray[2]);
			st.setString(4, orderArray[3]);
			st.setInt(5, Integer.valueOf(orderArray[4]));
			st.executeUpdate();
			System.out.println("sdsd");
			rs = st.getGeneratedKeys();
			rs.next();
			tradeno = rs.getInt(1);
			conn.commit();
			close(null,st,null);
			return tradeno;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(conn != null){
				try{
					conn.setAutoCommit(true);
					close(conn,null,null);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	return 0;
	}
	public DBErrorCode deleteTradeNoDB(){
		//根据订单创建时间删除
		return DBErrorCode.SUCCESS;
	}
	
	
	public int getTotalCount(Long tradeno){
		String tableName = "tradeno";
		String sql = "SELECT totalCount From "+tableName+ " where ID = ?";
		int totalCount;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, tradeno);			
			rs = st.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
			return totalCount;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(conn != null){
				try{
					conn.setAutoCommit(true);
					close(conn,null,null);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return 0;
		
	}
	
	
	public TradeNoItem getTradeNoItem(Long tradeno){
		String tableName = "tradeno";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		TradeNoItem tradeNoItem = new TradeNoItem();
		String sql = "SELECT orderNo, pid, goodsName, attach,totalCount from "+tableName+ " where id = ?";
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, tradeno);
			rs = st.executeQuery();
			rs.next();
			
			tradeNoItem.transformData(rs);
			close(null,st,rs);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					close(conn, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return tradeNoItem;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
