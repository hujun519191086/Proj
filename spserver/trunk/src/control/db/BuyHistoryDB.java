package control.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import util.PublishUtil;
import util.SQLUtil;
import control.bean.GoodsBuyHistory;
import control.bean.GoodsItem;
import control.bean.Person;
import control.bean.PersonBuyHistory;
import control.bean.PersonBuyHistoryDT;
import control.bean.PublishHistory;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.GOODS_ORDER;
import frame.DBFrame;
import frame.Global_value;
import frame.SingleFactory;

public class BuyHistoryDB extends DBFrame {
	
	//该接口用于支付完成后回调函数中查询订单，判断当前订单是否有进行过处理,为true时表示不操作
	public boolean selectBuyHistory(Long pid, Long out_trade_no){
		String tabName_p = SQLUtil.getPersonBuyHistoryTableName(pid);
		
		
		
		String sql = "SELECT * from " + tabName_p + " where tradeno = ?";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int rowCount;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setLong(1, out_trade_no);			
			rs = st.executeQuery();
			rs.last();
			rowCount = rs.getRow();
			System.out.println(rowCount);
			close(null,st,rs);
			//返回true表示不执行操作
			if(rowCount > 0){				
				return true;				
			}else{
				return false;
			}			
		} catch (SQLException e) {
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
		return false;
	}
	
	
	public DBErrorCode insertBuyHistory(Long gid, Long pid, Long tradeno ,int buyCount){
		
		String tabName_g = SQLUtil.getGoodsBuyHistoryTableName(gid);
		String tabName_p = SQLUtil.getPersonBuyHistoryTableName(pid);
		String params_g = "ID Bigint(8) unsigned Primary key Auto_Increment, tradeno Bigint(8), gid Bigint(8) NOT NULL ,pid Bigint(8) NOT NULL ,period int,codes text,onecCount int,buy_Time timestamp,pub_status int DEFAULT 0,nickname varchar(225),buyCount int";
		String params_p = "ID Bigint(8) unsigned Primary key Auto_Increment, tradeno Bigint(8), gid Bigint(8) NOT NULL ,pid Bigint(8) NOT NULL ,period int,codes text,onecCount int,buy_Time timestamp,pub_status int DEFAULT 0,nickname varchar(225),buyCount int";
		createTab(tabName_g, SingleFactory.BUY_HISTORY_URL, params_g);
		createTab(tabName_p, SingleFactory.BUY_HISTORY_URL, params_p);
		
		Connection conn = null;
		try{
			/*
			 * 添加购买历史
			 * */
			conn = getConnection();
			conn.setAutoCommit(false);
			
			String insertBuyHistory_g = "INSERT INTO "
					+tabName_g
					+"(tradeno,gid,pid,period,codes,onecCount,buy_Time,nickname,buyCount) VALUES (?,?,?,?,?,?,?,?,?)" ;
			String insertBuyHistory_p = "INSERT INTO "
					+tabName_p
					+"(tradeno,gid,pid,period,codes,onecCount,buy_Time,nickname,buyCount) VALUES (?,?,?,?,?,?,?,?,?)" ;
//			String insertBuyHistory = "INSERT INTO g_0 (gid,number,oneCount,buy_Time,loc) VALUES (1,1,1,'2016-08-19 17:14:32','1')";
			String tempsql_g = insertBuyHistory_g;
			String tempsql_p = insertBuyHistory_p;
			PreparedStatement st_g = conn.prepareStatement(tempsql_g);
			PreparedStatement st_p = conn.prepareStatement(tempsql_p);
			
			//获取用户昵称
			WebDB webDB = SingleFactory.ins().getWebDBIns();
			DataPair<DBErrorCode, Person> person = webDB.queryInfo4Pid(Long.valueOf(pid));
			String nickname = "";
			if(person.first == DBErrorCode.SUCCESS ){
				
				nickname = person.second.getNickname();
			}
			//获取物品对象
			GoodsItem gItem = SingleFactory.ins().getGoodsIns().getGoodsItem(gid);
			//获取当前购买人数
			int followCount = gItem.getFollowCount();
				
			st_p.setLong(1, tradeno);
			st_g.setLong(1, tradeno);
			
			st_g.setLong(2, gid);
			st_p.setLong(2, gid);
			
			st_g.setLong(3, pid);
			st_p.setLong(3, pid);
			
			int period = gItem.getPeriod();
			st_g.setInt(4, period);
			st_p.setInt(4, period);
			
			String codes = SingleFactory.ins().getGoodsIns().getCodes(gid, followCount,buyCount).toString();
			st_g.setString(5, codes);
			st_p.setString(5, codes);
			
			int onecCount = gItem.getOnecCount();
			st_g.setInt(6,onecCount);
			st_p.setInt(6,onecCount);
			
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			st_g.setTimestamp(7, ts);
			st_p.setTimestamp(7, ts);
			
			st_g.setString(8, nickname);
			st_p.setString(8, nickname);
			
			st_g.setInt(9, buyCount);
			st_p.setInt(9, buyCount);
			
			
			
			
			st_g.executeUpdate();
			st_p.executeUpdate();
			
			close(null,st_g,null);
			close(null,st_p,null);
			
			conn.commit();
			return DBErrorCode.SUCCESS;
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		}
		finally{
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
		return DBErrorCode.BUY_HISTORY_INSERT_FAILT;
	}
	
	public DBErrorCode insertPubHistory(Long gid, Long pid ,int period){
		
		String location ;
		String tabName = SQLUtil.getPubHistoryTableName(gid);
		String params= "ID Bigint(8) unsigned Primary key Auto_Increment, gid Bigint(8) NOT NULL  ,pid Bigint(8),period int,lucky_code int,pub_status int DEFAULT 0,pub_time timestamp,location varchar(225),beloneId varchar(80),totalCount int,onecCount int,goods_name varchar(225),second_name varchar(225),province varchar(225),city varchar(225),district varchar(225),goods_img varchar(225),dis_imgs text,detail_imgs text,catelog varchar(225),catelog_id int,nickname varchar(225),buyCount int, notifyStatus int default 0";
		
		createTab(tabName, SingleFactory.BUY_HISTORY_URL, params);
		Connection conn = null;
		try{
			/*
			 * 添加往期历史
			 * */
			conn = getConnection();
			conn.setAutoCommit(false);
			
			String insertPubHistory = "INSERT INTO "
					+tabName
					+"(gid,period,pub_time,location,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
			
			String tempsql = insertPubHistory;
			PreparedStatement st = conn.prepareStatement(tempsql);
			
			GoodsItem gItem = SingleFactory.ins().getGoodsIns().getGoodsItem(gid);
			
			st.setLong(1, gid);
			
			st.setInt(2,period );
			
			Timestamp timestamp = PublishUtil.getPublishTime();
			st.setTimestamp(3, timestamp);
		    
			location = gItem.getLocaiton();
			st.setString(4, location);
			
			String beloneId = gItem.getBeloneId();
			st.setString(5, beloneId);
			
			int totalCount = gItem.getTotalCount();
			st.setInt(6,totalCount);
			
			int onecCount = gItem.getOnecCount();
			st.setInt(7, onecCount);
			
			String goods_name = gItem.getGoods_name();
			st.setString(8,goods_name);
			
			String second_name = gItem.getSecond_name();
			st.setString(9, second_name);
			
			String province = gItem.getProvince();
			st.setString(10, province);
			
			String city = gItem.getCity();
			st.setString(11, city);

			String district = gItem.getDistrict();
			st.setString(12, district);
			
			String goods_img = gItem.getGoods_img();
			st.setString(13, goods_img);
			
			String dis_imgs = gItem.getDis_imgs();
			st.setString(14, dis_imgs);
			
			String detail_imgs = gItem.getDetail_imgs();
			st.setString(15, detail_imgs);
			
			String catelog = gItem.getCatelog();
			st.setString(16, catelog);
			
			int catelog_id = gItem.getCatelog_id();
			st.setInt(17, catelog_id);
			
			
			st.executeUpdate();
			
			//存储到最新发布表
			insertRecentPublish(gid, period, pid, location, timestamp ,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id);
			
			
			//定时揭晓
			String createEvent = "CREATE EVENT IF NOT EXISTS "
					+ SQLUtil.getEventName(gid, period)
					+" ON SCHEDULE AT ?"
					+" ON COMPLETION NOT PRESERVE"
					+" DO CALL publish_procedure(?,?,?,?,?,?,?) ";
			PreparedStatement st_event = conn.prepareStatement(createEvent);
			st_event.setTimestamp(1, timestamp);
			
			st_event.setLong(2, gid);
			st_event.setInt(3, period);
			
			String pubHisTable = SQLUtil.getPubHistoryTableName(gid);
			st_event.setString(4, pubHisTable);
			
			String rctPubTable = SQLUtil.getRencentPublishTableName();
			st_event.setString(5, rctPubTable);
			
			String goodsTable = SQLUtil.getGoodsTableName();
			st_event.setString(6, goodsTable);
			
			String buyHisTable_g = SQLUtil.getGoodsBuyHistoryTableName(gid);
			st_event.setString(7, buyHisTable_g);
						
			st_event.setInt(8,Global_value.BASE_CODE);
			
			st_event.execute();
			
			
			close(null,st,null);
			close(null,st_event,null);
			
			conn.commit();
			return DBErrorCode.SUCCESS;
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		}
		finally{
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
		return DBErrorCode.PUB_HISTORY_INSERT_FAILT;
	}
	private DBErrorCode insertRecentPublish(long gid,int period,long pid,String location,Timestamp timestamp,String beloneId,int totalCount,int onecCount,String goods_name,String second_name,String province,String city,String district,String goods_img,String dis_imgs,String detail_imgs,String catelog,int catelog_id){
		
		
		String tabName = SQLUtil.getRencentPublishTableName();
		String params= "ID Bigint(8) unsigned Primary key Auto_Increment, gid Bigint(8) NOT NULL  ,pid Bigint(8),period int,lucky_code int,pub_status int DEFAULT 0,pub_time timestamp,location varchar(225),beloneId varchar(80),totalCount int,onecCount int,goods_name varchar(225),second_name varchar(225),province varchar(225),city varchar(225),district varchar(225),goods_img varchar(225),dis_imgs text,detail_imgs text,catelog varchar(225),catelog_id int,nickname varchar(225),buyCount int, notifyStatus int default 0";
		
		createTab(tabName, SingleFactory.BUY_HISTORY_URL, params);
		Connection conn = null;
		try{
			/*
			 * 添加最新发布
			 * */
			conn = getConnection();
			conn.setAutoCommit(false);
			
			String insertRecentPublish = "INSERT INTO "
					+tabName
					+"(gid,period,pub_time,location,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
			
			String tempsql = insertRecentPublish;
			PreparedStatement st = conn.prepareStatement(tempsql);
			
			st.setLong(1, gid);
			
			st.setInt(2,period );
			
			st.setTimestamp(3, timestamp);
		    
			st.setString(4, location);
			
			st.setString(5, beloneId);
			
			st.setInt(6,totalCount);
			
			st.setInt(7, onecCount);
			
			st.setString(8,goods_name);
			
			st.setString(9, second_name);
			
			st.setString(10, province);
			
			st.setString(11, city);

			st.setString(12, district);
			
			st.setString(13, goods_img);
			
			st.setString(14, dis_imgs);
			
			st.setString(15, detail_imgs);
			
			st.setString(16, catelog);
			
			st.setInt(17, catelog_id);
			
			st.executeUpdate();
			conn.commit();
			return DBErrorCode.SUCCESS;
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		}
		finally{
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
		return DBErrorCode.RECENT_PUBLISH_INSERT_FAILT;
	}
	/**
	 * 获取最新揭晓的商品
	 * 
	 * @param locationDBName
	 * @return
	 */
	public DataPair<DBErrorCode, ArrayList<PublishHistory>> locationRecentPublish(int page,int pageCount,long gid){
		
		int startIndex = 0;
		String tabName = SQLUtil.getRencentPublishTableName();
		String orderBy = GOODS_ORDER.PUBLISH_TIME.columnName;
		String isLimit = "";
		
		if(pageCount>=0){
			isLimit = "  limit ?,?";
			startIndex = page * pageCount;
		}
		
		String isGid = "";
		if(gid!=0){
			isGid = " where gid = "+gid;
		}
		
		ArrayList<PublishHistory> dataList = null;
		String sql = "select gid,pid,period,pub_time,pub_status,location,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id,nickname,lucky_code,buyCount,now() as cur_time from "
				+ tabName
				+ isGid
				+ " order by "+orderBy+isLimit;
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		try {
			if(pageCount>0){
				
				dp.second.setInt(1, startIndex);
				dp.second.setInt(2, startIndex + pageCount);
			}
			ResultSet rs = executeQuery(dp);
			dataList = resultSet2PublishHistoryList(rs);
			
			close(null, dp.second,null);
			
		} catch (SQLException e) {
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
			return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
				DBErrorCode.LOAD_PUBLISH_FAILT_NO_GOODS, null);
	}
	/**
	 * 获取往期历史揭晓
	 * 
	 * @param locationDBName
	 * @return
	 */
	public DataPair<DBErrorCode, ArrayList<PublishHistory>> loadPubHistory(long gid,int period){
		
		String tabName = SQLUtil.getPubHistoryTableName(gid);
		ArrayList<PublishHistory> dataList = null;
		String sql = "select gid,pid,period,pub_status,pub_time,location,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id,nickname,lucky_code,buyCount,now() as cur_time from "
				+ tabName
				+ " where gid=? and period=?;";
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		try {
			dp.second.setLong(1, gid);
			dp.second.setInt(2, period);
			
			ResultSet rs = executeQuery(dp);
			dataList = resultSet2PublishHistoryList(rs);
			
			close(null, dp.second,null);
			
		} catch (SQLException e) {
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
			return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
				DBErrorCode.LOAD_PUBLISH_FAILT_NO_GOODS, null);
	}
	/**
	**管理员读取中奖记录
	*/
public DataPair<DBErrorCode, ArrayList<PublishHistory>> loadPubInfo(int page,int pageCount){
		
		int startIndex = 0;
		String tabName = SQLUtil.getRencentPublishTableName();
		String orderBy = GOODS_ORDER.PUBLISH_TIME.columnName;
		String isLimit = "";
		
		if(pageCount>=0){
			isLimit = "  limit ?,?";
			startIndex = page * pageCount;
		}
		
		
		
		ArrayList<PublishHistory> dataList = null;
		String sql = "select gid,pid,period,pub_time,pub_status,location,beloneId,totalCount,onecCount,goods_name,second_name,province,city,district,goods_img,dis_imgs,detail_imgs,catelog,catelog_id,nickname,lucky_code,buyCount,notifyStatus,now() as cur_time from "
				+ tabName
				+" where pub_status = 1"
				+ " order by "+orderBy+isLimit;
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		try {
			if(pageCount>0){
				
				dp.second.setInt(1, startIndex);
				dp.second.setInt(2, startIndex + pageCount);
			}
			ResultSet rs = executeQuery(dp);
			dataList = resultSet2PublishHistoryList(rs);
			
			close(null, dp.second,null);
			
		} catch (SQLException e) {
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
			return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<PublishHistory>>(
				DBErrorCode.LOAD_PUBLISH_FAILT_NO_GOODS, null);
	}
	/**
	 * 修改已通知中奖记录 notifyStatus为1
	 */
public DBErrorCode updatePubInfo(Long pid, int period, int notifyStatus){
	String sql = "update recent_publish set notifyStatus=? where pid =? and period =?";
	DataPair<Connection, PreparedStatement> ps = getPreparedStatement(sql);
	
	setInt(ps,1,notifyStatus);
	setLong(ps,2,pid);
	setInt(ps,3,period);
	int updateCloum = executeUpdate(ps);
	
	close(ps);
	if (updateCloum > 0) {

		return DBErrorCode.SUCCESS;

	}
	return DBErrorCode.UPDATE_USER_INFO_FAILT;
}	
	/**
	 * 获取物品的参与记录
	 * 
	 * @param locationDBName
	 * @return
	 */
public DataPair<DBErrorCode, ArrayList<GoodsBuyHistory>> loadGoodsBuyHistory(long gid,int period){
		
		String tabName = SQLUtil.getGoodsBuyHistoryTableName(gid);
		ArrayList<GoodsBuyHistory> dataList = null;
		String sql = "select tradeno,gid,pid,period,onecCount,buy_Time,pub_status,nickname,buyCount,codes from "
				+ tabName
				+ " where gid=? and period=?"
				+ " order by buy_Time desc";
		DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
		try {
			dp.second.setLong(1, gid);
			dp.second.setInt(2, period);
			
			ResultSet rs = executeQuery(dp);
			dataList = resultSet2GoodsBuyHistoryList(rs);
			
			close(null, dp.second,null);
			
		} catch (SQLException e) {
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
			return new DataPair<DBErrorCode, ArrayList<GoodsBuyHistory>>(
					DBErrorCode.SUCCESS, dataList);
		}
		return new DataPair<DBErrorCode, ArrayList<GoodsBuyHistory>>(
				DBErrorCode.LOAD_GOODS_BUY_HISTORY_FAILT, null);
	}
/**
 * 获取用户的参与记录
 * 
 * @param locationDBName
 * @return
 */
public DataPair<DBErrorCode, ArrayList<PersonBuyHistory>> loadPersonBuyHistory(long pid,int merge){
	
	String tabName = SQLUtil.getPersonBuyHistoryTableName(pid);
	ArrayList<PersonBuyHistory> dataList = null;
	
	
	String sql = "";
	if(merge==1){
		sql = "select gid,pid,period,pub_status,sum(buyCount) buyCount,group_concat(codes separator '') codes from "
				+ tabName
				+ " where pid=? "
				+ " group by gid,period";
	}
	else{
		sql = "select gid,pid,period,pub_status,buyCount,codes from "
				+ tabName
				+ " where pid=? ";
	}
	DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
	try {
		dp.second.setLong(1, pid);
		
		ResultSet rs = executeQuery(dp);
		dataList = resultSet2PersonBuyHistoryList(rs);
		
		close(null, dp.second,null);
		
	} catch (SQLException e) {
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
		return new DataPair<DBErrorCode, ArrayList<PersonBuyHistory>>(
				DBErrorCode.SUCCESS, dataList);
	}
	return new DataPair<DBErrorCode, ArrayList<PersonBuyHistory>>(
			DBErrorCode.LOAD_PERSON_BUY_HISTORY_FAILT, null);
}

/**
 * 获取详细用户参与记录
 * 
 * @param locationDBName
 * @return
 */
public DataPair<DBErrorCode, ArrayList<PersonBuyHistoryDT>> loadPersonBuyHistoryDT(long pid,long gid,int period){
	
	String tabName = SQLUtil.getPersonBuyHistoryTableName(pid);
	ArrayList<PersonBuyHistoryDT> dataList = null;
	String isGid = "";
	if(gid!=0&&period!=0){
		isGid = " and gid=? and period=? ";
	}
	
	
	String sql = "select tradeno,gid,pid,period,code,onecCount,buy_time,nickname from "
			+ tabName
			+ " where pid=? "
			+ isGid
			+ " ";
	DataPair<Connection, PreparedStatement> dp = getPreparedStatement(sql);
	try {
		if(gid==0||period==0){
			
			dp.second.setLong(1, pid);
		}
		else{
			dp.second.setLong(1, pid);
			dp.second.setLong(2, gid);
			dp.second.setLong(3, period);
		}
		
		ResultSet rs = executeQuery(dp);
		dataList = resultSet2PersonBuyHistoryDTList(rs);
		
		close(null, dp.second,null);
		
	} catch (SQLException e) {
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
		return new DataPair<DBErrorCode, ArrayList<PersonBuyHistoryDT>>(
				DBErrorCode.SUCCESS, dataList);
	}
	return new DataPair<DBErrorCode, ArrayList<PersonBuyHistoryDT>>(
			DBErrorCode.LOAD_PERSON_BUY_HISTORY_FAILT, null);
}
	private ArrayList<PublishHistory> resultSet2PublishHistoryList(ResultSet rs){
		try{
			ArrayList<PublishHistory> pubList = new ArrayList<PublishHistory>();
			while(rs.next()){
				PublishHistory phistory = new PublishHistory();
				phistory.transformData(rs);
				pubList.add(phistory);
			}
			return pubList;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	} 
	private ArrayList<GoodsBuyHistory> resultSet2GoodsBuyHistoryList(ResultSet rs){
		try{
			ArrayList<GoodsBuyHistory> pubList = new ArrayList<GoodsBuyHistory>();
			while(rs.next()){
				GoodsBuyHistory goodsBuyHistory = new GoodsBuyHistory();
				goodsBuyHistory.transformData(rs);
				pubList.add(goodsBuyHistory);
			}
			return pubList;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	} 
	private ArrayList<PersonBuyHistory> resultSet2PersonBuyHistoryList(ResultSet rs){
		try{
			ArrayList<PersonBuyHistory> pubList = new ArrayList<PersonBuyHistory>();
			while(rs.next()){
				PersonBuyHistory personBuyHistory = new PersonBuyHistory();
				personBuyHistory.transformData(rs);
				pubList.add(personBuyHistory);
			}
			return pubList;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	} 
	private ArrayList<PersonBuyHistoryDT> resultSet2PersonBuyHistoryDTList(ResultSet rs){
		try{
			ArrayList<PersonBuyHistoryDT> pubList = new ArrayList<PersonBuyHistoryDT>();
			while(rs.next()){
				PersonBuyHistoryDT personBuyHistoryDT = new PersonBuyHistoryDT();
				personBuyHistoryDT.transformData(rs);
				pubList.add(personBuyHistoryDT);
			}
			return pubList;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	} 
	public void init(){
		createPublishProcedure();
		createUpdateRctPubEvent();
		
	}
	private DBErrorCode createPublishProcedure(){
		Connection conn = null;
		try{
			
			conn = getConnection();
			conn.setAutoCommit(false);
			
			/**
			 * 创建自动揭晓存储过程publish_procedure
			 * 完成商品的揭晓过程
			 * gid 商品id
			 * period 期号
			 * pubHisTable 往期历史表表名
			 * goodsTable 商品信息表
			 * buyHisTable_g 物品购买记录表
			 * baseCode 夺宝号码基础值 
			 */
			
			String drop_procedure = "DROP PROCEDURE IF EXISTS publish_procedure";
			PreparedStatement st_drop_procedure = conn.prepareStatement(drop_procedure);
			st_drop_procedure.execute();
			
			String publish_procedure = " CREATE PROCEDURE publish_procedure (gid bigint(8),period int,pubHisTable varchar(225),rctPubTable varchar(225),goodsTable varchar(225),buyHisTable_g varchar(225),baseCode int)"
					+" BEGIN"
					+" SET @sqlpubhis = CONCAT('UPDATE ',pubHisTable,' pt ,goods.',goodsTable,' gt SET pt.lucky_code = ',baseCode,' +FLOOR(RAND()*gt.totalCount+1),pub_status = 1 WHERE pt.gid=gt.gid AND pub_status = 0 AND pt.gid=',gid,' AND pt.period = ',period);"
					+" SET @sqlgetpid = CONCAT('UPDATE ',pubHisTable,' pt ,',buyHisTable_g, ' bt SET pt.pid = bt.pid,pt.nickname=bt.nickname,pt.buyCount=bt.buyCount WHERE pt.gid = bt.gid AND pt.period = bt.period AND bt.codes like CONCAT(\"%\",pt.lucky_code,\"%\") AND pt.gid = ',gid,' AND pt.period = ',period);"
					+" SET @sqlbuyhisg = CONCAT('UPDATE ',buyHisTable_g,' SET pub_status = 1 where gid = ',gid, ' AND period = ',period);"
					//+" SET @sqlbuycount = CONCAT('UPDATE ',pubHisTable,' SET buyCount = (SELECT a.buyCount FROM(SELECT COUNT(*) buyCount FROM  ',buyHisTable_g,' bt, ',pubHisTable,' pt WHERE bt.gid=pt.gid AND bt.period=pt.period AND bt.gid= ',gid,' AND bt.pid=pt.pid and bt.period= ',period,' )a) WHERE gid=',gid,' AND period=',period);"//用户购买人次
					+" SET @sqlrecpub = CONCAT('UPDATE ',rctPubTable,' rt ,',pubHisTable,' pt SET rt.lucky_code = pt.lucky_code,rt.pub_status = 1,rt.pid = pt.pid,rt.nickname=pt.nickname,rt.buyCount=pt.buyCount WHERE rt.gid=pt.gid AND rt.pub_status = 0 AND rt.period = pt.period');"
					+" PREPARE stmtpubhis FROM @sqlpubhis;"
					+" PREPARE stmtgetpid FROM @sqlgetpid;"
					+" PREPARE stmtbuyhisg FROM @sqlbuyhisg;"
					//+" PREPARE stmtbuycount FROM @sqlbuycount;"
					+" PREPARE stmtrecpub FROM @sqlrecpub;"
					+" EXECUTE stmtpubhis;"
					+" EXECUTE stmtgetpid;"
					+" EXECUTE stmtbuyhisg;"
					//+" EXECUTE stmtbuycount;"
					+" EXECUTE stmtrecpub;"
					+" DEALLOCATE PREPARE stmtpubhis;"
					+" DEALLOCATE PREPARE stmtgetpid;"
					+" DEALLOCATE PREPARE stmtbuyhisg;"
					//+" DEALLOCATE PREPARE stmtbuycount;"
					+" DEALLOCATE PREPARE stmtrecpub;"
					+" END"
					+" ";
			PreparedStatement st_procedure = conn.prepareStatement(publish_procedure);
			st_procedure.execute();
			
			close(null,st_drop_procedure,null);
			close(null,st_procedure,null);
			
			conn.commit();
			return DBErrorCode.SUCCESS;
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		}
		finally{
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
		return DBErrorCode.PUBLISH_PROCEDURE_CREATE_FAILT;
	}
	private DBErrorCode createUpdateRctPubEvent(){
		Connection conn = null;
		try{
			/**
			 *最新发布数据表自动更新
			 */
			conn = getConnection();
			conn.setAutoCommit(false);
			
			
			//过程函数
			
			String drop_procedure = "DROP PROCEDURE IF EXISTS recPub_procedure";
			PreparedStatement st_drop_procedure = conn.prepareStatement(drop_procedure);
			st_drop_procedure.execute();
			
			String rctPub_procedure = "CREATE PROCEDURE recPub_procedure(rctPubTable varchar(225),expiration_days int)"
					+ " BEGIN"
					+ " SET @sqlpubhis = CONCAT('DELETE FROM ',rctPubTable,'WHERE TO_DAYS(NOW())-TO_DAYS(pub_time) >= ',expiration_days);"
					+ " END"
					+ " ";
			
			PreparedStatement st_procedure = conn.prepareStatement(rctPub_procedure);
			st_procedure.execute();
			//定时事件
			String rctPub_event = "CREATE EVENT IF NOT EXISTS rctPub_event"
					+" ON SCHEDULE EVERY 1 DAY"
					+" STARTS ?"
					+" ON COMPLETION NOT PRESERVE"
					+" DO CALL recPub_procedure(?,?) ";
			
			
			
			PreparedStatement st_event = conn.prepareStatement(rctPub_event);
			
			String updateRctPubTime = Global_value.UPDATE_RECENT_PULISH_TIME;
			st_event.setString(1, "2016-08-05 "+updateRctPubTime);
			
			String rctPubTable = SQLUtil.getRencentPublishTableName();
			st_event.setString(2, rctPubTable);
			
			int expiration_days = Global_value.EXPIRATION_DAYS;
			st_event.setInt(3, expiration_days);
			st_event.execute();
			
			close(null,st_drop_procedure,null);
			close(null,st_procedure,null);
			close(null,st_event,null);
			
			conn.commit();
			return DBErrorCode.SUCCESS;
		}
		catch(SQLException e){
			e.printStackTrace();
			rollBack(conn);
		}
		finally{
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
		return DBErrorCode.UPDATE_RECENT_PUBLISH_EVENT_FAILT;
	}
	//获取往期揭晓历史对象
		public PublishHistory getPublishHistory(long gid,int period){
			Connection conn = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			String tabName = SQLUtil.getPubHistoryTableName(gid);
			String sql = "SELECT * from "+tabName+" where gid=? and period=?";
			try{
				conn = getConnection();
				conn.setAutoCommit(false);
				st = conn.prepareStatement(sql);
				st.setLong(1, gid);
				st.setInt(2,period);
				rs = st.executeQuery();
				rs.next();
				PublishHistory pubHis = new PublishHistory();
				pubHis.transformData(rs);
				close(null,st,rs);
				return pubHis;
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

}
