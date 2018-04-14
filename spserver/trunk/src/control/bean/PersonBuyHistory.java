package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import frame.SingleFactory;

public class PersonBuyHistory {
	private String gid;// 物品id
	private String pid;//购买者id
	private int period;//期号
	private int buyCount;//购买人次
	private int pub_status;//揭晓状态 0 等待揭晓， 1 已揭晓， 2 正在进行
	private String codes;//夺宝号
	
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public int getPub_status() {
		return pub_status;
	}
	public void setPub_status(int pub_status) {
		this.pub_status = pub_status;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}

	
	public void transformData(ResultSet rs) throws SQLException {
		setGid(rs.getString("gid"));
		setPid(rs.getString("pid"));
		setPeriod(rs.getInt("period"));
		setBuyCount(rs.getInt("buyCount"));
		setCodes(rs.getString("codes"));
		
		
		//获取物品状态 0 等待揭晓 1 已揭晓 2 正在进行
		long gid = Long.valueOf(rs.getString("gid"));
		int period = Integer.valueOf(rs.getInt("period"));
		int pub_status = checkStatus(gid,period);
		setPub_status(pub_status);
		
	}
	public int checkStatus(long gid,int period){
		/**
		 * 获取对应期号物品的状态
		 */
		
		GoodsItem gItem = SingleFactory.ins().getGoodsIns().getGoodsItem(gid);
		PublishHistory pubHis = SingleFactory.ins().getBuyHistoryIns().getPublishHistory(gid, period);
		if(gItem.getPeriod() == period){
			return 2;
		}
		else{
			return pubHis.getPub_status();
		}
		
	}
}
