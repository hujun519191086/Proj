package control.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import util.SQLUtil;
import control.bean.GoodsItem;
import control.bean.ShoppingCarItem;
import control.bean.ShoppingCart;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import frame.DBFrame;
import frame.SingleFactory;

/**
 * 购物车：表名：人物id/Global_value.DB_NAME_SPLITE
 * 字段：人物id，物品id，购买数量
 * @author Mryang
 *
 */
public class ShoppingCartDB extends DBFrame{

	public DBErrorCode insert2ShoppingCart(HttpServletRequest request)
	{
		Long pid = Long.parseLong(request.getAttribute("pid").toString());
		Long gid = Long.parseLong(request.getAttribute("gid").toString());
		int buyCount = Integer.parseInt(request.getAttribute("buyCount").toString());
		
		String params = "pid BIGINT,gid BIGINT,buyCount int,primary key  (pid,gid)";
		String shoppingCartTableName =SQLUtil.getShoppingCartTableName(pid);
		createTab(shoppingCartTableName, SingleFactory.SHOPPING_CART_URL, params);
		
		String sql = "INSERT INTO "+shoppingCartTableName+"(pid,gid,buyCount)VALUES(?,?,?) ON DUPLICATE KEY UPDATE buyCount=buyCount+?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setLong(ps, 1,pid );
		setLong(ps, 2, gid);
		setInt(ps, 3,buyCount);
		setInt(ps, 4,buyCount);
		int updateResult = executeUpdate(ps);
	
		close(ps);
		if (updateResult > 0) {
			return DBErrorCode.SUCCESS;

		}
		return DBErrorCode.INSERT_SHOOPING_CART_FAILT;
	}
	
	public DBErrorCode addShoppingCount()
	{
		return DBErrorCode.SUCCESS;
	}
	
	public DBErrorCode DeleteShoppingCart(Long gid, Long pid){
		String shoppingCartTableName = SQLUtil.getShoppingCartTableName(pid);
		String sql = "DELETE FROM " + shoppingCartTableName + " WHERE pid = ? AND gid=?";
		DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
		setLong(ps, 1, pid);
		setLong(ps, 2, gid);
		int updateResult = executeUpdate(ps);
		close(ps);
		if(updateResult > 0){
			return DBErrorCode.SUCCESS;
		}else{
			return DBErrorCode.DELETE_SHOOPING_CART_FAILT;
		}
		
	}
	/*
	public int getBuyCount(Long pid, Long gid){
		String tabName_s = SQLUtil.getShoppingCartTableName(pid);
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT buyCount from" +tabName_s+ "where gid =?";
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, gid);
			rs = st.executeQuery();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	*/
	public ShoppingCarItem getShoppingCartItem(Long pid, Long gid){
		String tabName_s = SQLUtil.getShoppingCartTableName(pid);
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ShoppingCarItem shopItem = new ShoppingCarItem();
		String sql = "SELECT pid, gid, buyCount from "+tabName_s+ " where gid =? and pid=?";
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, gid);
			System.out.println(pid);
			System.out.println(gid);
			st.setLong(2, pid);
			rs = st.executeQuery();
			rs.next();
			
			shopItem.transformDataShoppingCart(rs);
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
		return shopItem;
		
	}
	

	
	public DataPair<DBErrorCode, ArrayList<ShoppingCart>> LoadShoppingCart(long pid){
		
		String tabName_g = SQLUtil.getGoodsTableName();//商品信息表
		String tabName_s = SQLUtil.getShoppingCartTableName(pid);//用户购物车记录表
		String sql = "select pid,g.gid,buyCount,goods_img,dis_imgs,goods_name,totalCount,needCount,onecCount from goods."+tabName_g+" g,"+tabName_s+" s where g.gid = s.gid";
		
		ArrayList<ShoppingCart> dataList = null;
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		
		ResultSet rs = executeQuery(dp);
		dataList = resultSet2ShoppingCartList(rs);
		
		close(null, dp.second,null);
		try {
			dp.first.setAutoCommit(true);
			close(dp.first, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (dataList != null && dataList.size() > 0) {
			return new DataPair<DBErrorCode, ArrayList<ShoppingCart>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<ShoppingCart>>(
				DBErrorCode.LOAD_SHOPPING_CART_FAILT, null);
		
	}
	private ArrayList<ShoppingCart> resultSet2ShoppingCartList(ResultSet rs){
		try{
			ArrayList<ShoppingCart> cartlist = new ArrayList<ShoppingCart>();
			while(rs.next()){
				ShoppingCart shoppingCart = new ShoppingCart();
				shoppingCart.transformData(rs);
				cartlist.add(shoppingCart);
			}
			return cartlist;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	} 
}
