package util;

import frame.Global_value;

public class SQLUtil {

	/**
	 * 购物车的db名称
	 * @param pid
	 * @return
	 */
	public static String getShoppingCartTableName(long pid)
	{
		return "购物车_"+(pid/Global_value.DB_NAME_SPLITE);
	}
	public static String getGoodsTableName(){
		
		return "goods_info";
	}
	public static String getGidGoodsTableName(long gid){
		return "goods_"+(gid%Global_value.TABLE_NAME_SPLITE);
	}
	public static String getCatGoodsTableName(String catelog){
		return catelog;
	}
	public static String getGoodsBuyHistoryTableName(long gid){
		
		String tabName = "g_"+(gid/Global_value.TABLE_NAME_SPLITE);
		return tabName;
	}
	public static String getPersonBuyHistoryTableName(long pid){
		
		String tabName = "p_"+(pid/Global_value.TABLE_NAME_SPLITE);
		return tabName;
	}
	public static String getPubHistoryTableName(long gid){
		String tabName = "pub_his_"+(gid/Global_value.TABLE_NAME_SPLITE);
		return tabName;
	}
	public static String getEventName(long gid,int period){
		String eventName = "event_" + gid + "_" + period;
		return eventName;
	}
	public static String getRencentPublishTableName(){
		return "recent_publish";
	}
	public static String getCodeOrderTableName(Long gid){
		return "code_order_"+gid;
	}
	public static String getTradeNoTableName(Long pid){
		String tabName = "tradeNo";
		return tabName;
	}
}
