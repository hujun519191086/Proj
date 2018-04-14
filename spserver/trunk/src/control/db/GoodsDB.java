package control.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import util.GoodsUtil;
import util.SQLUtil;
import util.WordUtil;
import control.bean.GoodsItem;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.GOODS_ORDER;
import frame.DBFrame;
import frame.Global_value;
import frame.SingleFactory;

/**
 * 关于物品的数据库
 * 
 * @author Administrator
 * 
 */
public class GoodsDB extends DBFrame {


	/**
	 * 数据库设计字段 gid vchar <br>
	 * totalcount vchar(不过是long) <br>
	 * onecCount int <br>
	 * goods_name vchar <br>
	 * second_name vchar <br>
	 * open_time time <br>
	 * jiexiao_total_count int <br>
	 * 
	 * 
	 * @param request
	 * @return
	 */
	public DBErrorCode updateGoods(HttpServletRequest request) {
		// 位置,上传者的信息(物品id 要和上传者信息比对, 如果一致才能操作不一致不可以), 物品信息(如果正在进行中,
		// 不可以更改信息.break掉),图片.
		String province = request.getAttribute("province").toString();
		String city = request.getAttribute("city").toString();
		String district = request.getAttribute("district").toString();
		String beloneId = request.getAttribute("beloneId").toString();// YY
		String catelog = request.getAttribute("cat").toString();//car

		/*
		 * 创建物品主表及分类表
		 * 主表存储物品信息
		 * 分类表表名 一级分类名
		 * 显示分类下所有物品时 读取分类表
		 * */
		String params_main = "beloneId varchar(80), gid Bigint(8) NOT NULL unique primary key, totalCount int, onecCount int,needCount int,followCount int DEFAULT '0',progress int DEFAULT '0',goods_name varchar(225),second_name varchar(225),open_time timestamp,period int DEFAULT 1,county varchar(225),province varchar(225),city varchar(225),district varchar(225),location varchar(225),goods_img varchar(225),dis_imgs text,detail_imgs text,catelog varchar(225),status int DEFAULT 0,stock int,catelog_id int";
		
		String params_cat = "gid Bigint(8) NOT NULL unique primary key";
		String tabName = SQLUtil.getGoodsTableName();
		createTab(tabName, SingleFactory.GOODS_URL, params_main);
		
		
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			String queryGoods = "SELECT * from " + tabName + " where gid=?";
			String updateGoods = "UPDATE "
					+ tabName
					+ " set goods_name=?,totalCount=?,onecCount=?,needCount=?,second_name=?,open_time=?,goods_img=?,dis_imgs=?,detail_imgs=?,catelog=?,province=?,city=?,district=?,location=?,status=?,stock=?,catelog_id=? WHERE beloneId=? and gid=?";
			String insertGoods = "INSERT INTO "
					+ tabName
					+ " (goods_name,totalCount,onecCount,needCount,second_name,open_time,goods_img,dis_imgs,detail_imgs,catelog,province,city,district,location,status,stock,catelog_id,beloneId,gid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			

			long gid = Long.valueOf(request.getAttribute("gid").toString());
			DBErrorCode dbError = queryGoodsAndSendError(conn, queryGoods,
					gid, beloneId);
			
			if (dbError == DBErrorCode.SUCCESS) {

				// 这里才允许更改数据
				Object tag = DBErrorCode.SUCCESS.getTag();
				String tempSql = ((Boolean) DBErrorCode.SUCCESS.getTag()) ? updateGoods
						: insertGoods;
				PreparedStatement st = conn.prepareStatement(tempSql);

				String gName = request.getAttribute("gName").toString();
				st.setString(1, gName);

				int totalCount = (Integer) request.getAttribute("totalCount");
				st.setInt(2, totalCount);

				int onecCount = (Integer) request.getAttribute("onecCount");
				st.setInt(3, onecCount);
				
				
				int needCount = totalCount;
				st.setInt(4, needCount);// 现在需要人数.

				String second_name = request
						.getAttribute("second_name").toString();
				st.setString(5, second_name);


				long open_time = (Long) request.getAttribute("open_time");
				st.setTimestamp(6, new Timestamp(open_time));
				
				String goods_img = (String)request.getAttribute("goods_img");
				st.setString(7, goods_img);
				
				String dis_imgs = request.getAttribute("dis_imgs").toString();
				st.setString(8,dis_imgs);
				
				String detail_imgs = request.getAttribute("detail_imgs").toString();
				st.setString(9, detail_imgs);
				
				
				st.setString(10, catelog);
				
				st.setString(11,province);
				st.setString(12,city);
				st.setString(13,district);
				
				String location = province + city + district;
				st.setString(14, location);

				
				int status = (Integer)request.getAttribute("status");
				st.setInt(15, status);
				
				int stock = (Integer)request.getAttribute("stock");
				st.setInt(16, stock);
				
				int catelog_id = (Integer)request.getAttribute("catelog_id");
				st.setInt(17, catelog_id);
				
				st.setString(18, beloneId);
				st.setLong(19, gid);
				st.executeUpdate();
				
				
				//分类表
				String tabName_cat = SQLUtil.getCatGoodsTableName(catelog);
				createTab(tabName_cat, SingleFactory.GOODS_URL, params_cat);
				String insertGoods_cat = "INSERT INTO "
						+ tabName_cat
						+ " (gid) VALUES(?)";
				PreparedStatement st_cat = conn.prepareStatement(insertGoods_cat);
				
				st_cat.setLong(1, gid);
				
				st_cat.executeUpdate();
				
				//关闭本次
				close(null, st, null);
				close(null, st_cat,null);
				}
			
			conn.commit();
			return DBErrorCode.SUCCESS;

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

		return DBErrorCode.GOODS_INSERT_FAILT_CANNOT_CHANGE;
	}
	/**
	 * 更新夺宝号码顺序表
	 */
	public DBErrorCode updateCodeOrder(long gid){
		
		String params = "sequence int,code int";
		GoodsItem gItem = SingleFactory.ins().getGoodsIns().getGoodsItem(gid);
		
		String tabName = SQLUtil.getCodeOrderTableName(gid);
		createTab(tabName, SingleFactory.GOODS_URL, params);
		
		Connection conn = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			
			String dropCodes = "DELETE FROM " + tabName;
			String insertCodes = "INSERT INTO "
					+ tabName
					+ "(sequence,code) VALUES (?,?)";
			
			int totalCount = gItem.getTotalCount();
			
			//更新前清除数据
			PreparedStatement st_drop = conn.prepareStatement(dropCodes);
			st_drop.execute();
			
			PreparedStatement st_insert = conn.prepareStatement(insertCodes);
			
			
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			for(int i=1;i<=totalCount;i++){
				tempList.add(Global_value.BASE_CODE+i);
			}
			int sequence = 1;
			while(tempList.size()>0){
				if(tempList.size()==999){
					System.out.println(sequence);
				}
				
				st_insert.setInt(1, sequence);
				
				int index = (int)(Math.random()*tempList.size());
				int code = tempList.get(index);
				st_insert.setInt(2,code);
				
				st_insert.addBatch();
				//分批插入
				if(sequence % 1000 == 0){
					st_insert.executeBatch();
				}
				tempList.remove(index);
				sequence++;
			}
			st_insert.executeBatch();
			close(null, st_drop, null);
			close(null, st_insert,null);
			
			conn.commit();
			return DBErrorCode.SUCCESS;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack(conn);
		}
		finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					close(conn, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return DBErrorCode.CODE_ORDER_UPDATE_FAILT;
	}
	/**
	 * 降序获取人气的物品
	 * 
	 * @param locationDBName
	 * @return
	 */
	public DataPair<DBErrorCode, ArrayList<GoodsItem>> locationFollowGoods(
			String location, int page,int pageCount, int order,int catelog_id,long gid) {// TODO
		// 需要做的事情:如果没有物品,则获取其他数据库的数据

		
		String tabName = SQLUtil.getGoodsTableName();
		
		String orderBy = GOODS_ORDER.values()[Integer.valueOf(order)].columnName;
		String isTimeNeedWhere = " where open_time<now()";// 如果是时间降序.
		String isCatelog = "";
		String isLimit = "";
		int startIndex = 0;
		if(catelog_id != 0){
			isCatelog = " and catelog_id = " + catelog_id ;
		}
		//如果pageCount<0 默认读取全部商品
		if(pageCount>=0){
			isLimit = " limit ?,?";
			startIndex = page * pageCount;
		}
		//如果有有gid，查询gid对应物品
		String isGid = "";
		if(gid!=0){
			isGid = "and gid = "+gid;
		}
		ArrayList<GoodsItem> dataList = null;

		String sql = "select gid,totalCount,onecCount,needCount,followCount,progress,goods_name,second_name,open_time,period,goods_img,dis_imgs,detail_imgs,catelog,province,city,district,location,status,stock,catelog_id,now() as cur_time from "
				+ tabName
				+ isTimeNeedWhere
				+ isGid
				+ isCatelog
				+ " order by case when location=? then 0 else 1 end,"+orderBy+isLimit;
		
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		try {
			if(pageCount>=0){
				dp.second.setString(1, location);
				dp.second.setInt(2, startIndex);
				dp.second.setInt(3, pageCount);
			}
			else{
				dp.second.setString(1, location);
			}
			ResultSet rs = executeQuery(dp);
			dataList = resultSet2GoodsList_User(rs);
			
			close(null, dp.second,null);
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack(dp.first);
		}
		finally {
			if (dp.first != null) {
				try {
					dp.first.setAutoCommit(true);
					close(dp.first, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		
		

		if (dataList != null && dataList.size() > 0) {
			return new DataPair<DBErrorCode, ArrayList<GoodsItem>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<GoodsItem>>(
				DBErrorCode.LOAD_GOODS_FAILT_NO_GOODS, null);
	}
	
	/**
	 * 获取即将揭晓的物品
	 *
	 */
	public DataPair<DBErrorCode, ArrayList<GoodsItem>> locationSoonPublishGoods(
			String tabName, int page,int pageCount,String pid) {// TODO
		// 需要做的事情:如果没有物品,则获取其他数据库的数据

		int startIndex = 0;
		String isSoonPublish = " WHERE status = 2";
		String orderBy = GOODS_ORDER.PROGRESS.columnName;
		String isLimit = "";
		if(pageCount>=0){
			isLimit = " limit ?,?";
			startIndex = page*pageCount;
		}
		
		String sql = "select gid,totalCount,onecCount,needCount,followCount,progress,goods_name,second_name,open_time,period,goods_img,dis_imgs,detail_imgs,catelog,province,city,district,location,status,stock,catelog_id from "
				+ tabName
				+ isSoonPublish
				+ " order by "+orderBy+isLimit;// 人气分页:人气,降序_
		// 需要人数,升序

		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		
		try {
			if(pageCount>=0){				
				dp.second.setInt(1, startIndex);
				dp.second.setInt(2, startIndex+pageCount);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ResultSet rs = executeQuery(dp);
		ArrayList<GoodsItem> dataList = resultSet2GoodsList_User(rs);

		if (dataList != null && dataList.size() > 0) {
			return new DataPair<DBErrorCode, ArrayList<GoodsItem>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<GoodsItem>>(
				DBErrorCode.LOAD_GOODS_FAILT_NO_GOODS, null);
	}
	
	/**
	 * 查找物品并且发送错误信息
	 * 
	 * @return
	 */
	private DBErrorCode queryGoodsAndSendError(Connection conn,
			String querySQL, long gid, String beloneId) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(querySQL);

			st.setLong(1, gid);
			rs = st.executeQuery();
			GoodsItem gitem = new GoodsItem();
			boolean hasData = resultSet2GOodsItem_ALL(rs, gitem);
			DBErrorCode.SUCCESS.setTag(hasData);
			close(null, st, rs);// 关闭本次查询.
			if (hasData && !gitem.goodsCanChange()) {
				// 有数据, 但是数据超时了.
				// if (conn != null) {
				// try {
				// conn.rollback();// 回滚
				// } catch (SQLException e1) {
				// e1.printStackTrace();
				// }
				// }
				return DBErrorCode.GOODS_INSERT_FAILT_CANNOT_CHANGE;
			} else if (gitem != null && !WordUtil.isEmpty(gitem.getBeloneId())
					&& !gitem.getBeloneId().equals(beloneId)) {// 物品不是该人所有
				return DBErrorCode.GOODS_INSERT_FAILT_NOT_YOURS;
			} else {
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(null, st, rs);
		}

		return DBErrorCode.SUCCESS;
	}

	/**
	 * 获取用户使用的物品列表(某些列不会被找出来)
	 * 
	 * @param rs
	 * @return
	 */
	private ArrayList<GoodsItem> resultSet2GoodsList_User(ResultSet rs) {
		try {
			ArrayList<GoodsItem> goodsList = new ArrayList<GoodsItem>();

			while (rs.next()) {
				GoodsItem gitem = new GoodsItem();
				gitem.transformUserData(rs);
				goodsList.add(gitem);
			}

			return goodsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private boolean resultSet2GOodsItem_ALL(ResultSet rs, GoodsItem gitem) {
		try {
			if (rs.next()) {
				gitem.transformData(rs);
				return true;
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	

	public DBErrorCode changeGoodsData(Long gid,int buycount) {
		String tabName = SQLUtil.getGoodsTableName();
		//long gid = Long.valueOf( request.getAttribute("gid").toString());
		
		String queryGoods = "SELECT * from " + tabName + " where gid=?";
		String updateGoods = "INSERT INTO "
				+ tabName
				+ " (gid) VALUES(?) ON DUPLICATE KEY UPDATE needCount=needCount-?,followCount=followCount+?,progress=floor(followCount*100/totalCount)";
		String updateGoods_soon = "INSERT INTO "
				+ tabName
				+ " (gid) VALUES(?) ON DUPLICATE KEY UPDATE needCount=needCount-?,followCount=followCount+?,progress=floor(followCount*100/totalCount),status=2";
		String updateGoods_publish = "UPDATE "
				+ tabName
				+ " set needCount=totalCount,followCount=0,progress=0,period=period+1,stock=stock-1,status=1 WHERE gid=?";
		Connection conn = null;
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			DBErrorCode dbError = checkGoodsAndSendError(conn,queryGoods,gid,buycount);
			if(dbError == DBErrorCode.SUCCESS){
				String tempsql;
				int pubStatus = (Integer)dbError.getTag();
				switch (pubStatus){
				case 0:
					tempsql = updateGoods;
					break;
				case 1:
					tempsql = updateGoods_soon;
					break;
				case 2:
					tempsql = updateGoods_publish;
					break;
				default:
					tempsql = updateGoods;
				}
				PreparedStatement st = conn.prepareStatement(tempsql);
				if(pubStatus==2){
					st.setLong(1, gid);
				}
				else{
					
					st.setLong(1, gid);
					st.setInt(2, buycount);
					st.setInt(3, buycount);
				}
			
				st.executeUpdate();
					
				close(null, st, null);// 关闭本次.
				conn.commit();
				return DBErrorCode.SUCCESS;
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		} 
		finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					close(conn, null, null);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		//todo 
		return DBErrorCode.GOODS_UPDATE_FAILT;
	}
	private DBErrorCode checkGoodsAndSendError(Connection conn,String queryGoods,long gid,int buycount){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(queryGoods);
			st.setLong(1, gid);
			rs = st.executeQuery();
			GoodsItem gItem = new GoodsItem();
			int pubStabus = checkPublish(gItem,rs,buycount);
			DBErrorCode.SUCCESS.setTag(pubStabus);
			close(null, st, rs);// 关闭本次查询.
			if(gItem.getNeedCount()<gItem.getOnecCount()){
				return DBErrorCode.GOODS_UPDATE_FAILT_OUT_OF_RANGER;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			close(null, st, rs);
		}
		return DBErrorCode.SUCCESS;
	}
	private int checkPublish(GoodsItem gItem,ResultSet rs,int buycount){
		try {
			rs.next();
			gItem.transformData(rs);
			int followCount = gItem.getFollowCount();
			int totalCount = gItem.getTotalCount();
			if(totalCount <= (followCount+buycount)){
				return 2;//揭晓
			}
			else if(((followCount+buycount)*100/totalCount)>=Global_value.SOON_PUBLISH_PERCENT ){
				return 1;//即将揭晓
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	//获取单个物品对象
	public GoodsItem getGoodsItem(long gid){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String tabName = SQLUtil.getGoodsTableName();
		String sql = "SELECT * from "+tabName+" where gid=?";
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, gid);
			rs = st.executeQuery();
			rs.next();
			GoodsItem gItem = new GoodsItem();
			gItem.transformData(rs);
			close(null,st,rs);
			return gItem;
		}
		catch (SQLException e) {
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
		return null;
	}
	
	public int getCurrentPeriod(long gid){
		/**
		 * 获取当前期号
		 */
		GoodsItem gItem = SingleFactory.ins().getGoodsIns().getGoodsItem(gid);
		int period = 0;
		if(gItem != null){			
			period = gItem.getPeriod();
		}
		return period;
	}
	
	public String getCodes(long gid,int sequence,int buyCount){
		/**
		 * 获取夺宝号码，返回所有夺宝号码拼接的字符串  1000017_1000749__1000063_
		 */
		String code = "";
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String tabName = SQLUtil.getCodeOrderTableName(gid);
		String sql = "SELECT code FROM "+tabName+" WHERE sequence >? AND sequence <= ?+?";
		try{
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, sequence);
			st.setLong(2, sequence);
			st.setInt(3, buyCount);
			rs = st.executeQuery();
			while(rs.next()){
				code += rs.getInt("code") + "_";
			}
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
		return code;
	}
	
}
