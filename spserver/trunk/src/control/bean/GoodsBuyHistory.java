package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GoodsBuyHistory {
	private String tradeno;//订单号
	private String gid;// 物品id
	private String pid;//购买者id
	private int period;//期号
	private int onecCount;//一次多少钱
	private String buy_Time;//购买时间
	private int pub_status;//揭晓状态 0 等待揭晓， 1 已揭晓，2 正在进行
	private String nickname;//用户昵称
	private int buyCount;//购买人次
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
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getBuy_Time() {
		return buy_Time;
	}
	public String getTradeno() {
		return tradeno;
	}
	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
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
	public int getOnecCount() {
		return onecCount;
	}
	public void setOnecCount(int onecCount) {
		this.onecCount = onecCount;
	}
	public int getPub_status() {
		return pub_status;
	}
	public void setPub_status(int pub_status) {
		this.pub_status = pub_status;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setBuy_Time(Timestamp buy_Time) {
		if(buy_Time!=null)
		{
			this.buy_Time = String.valueOf(buy_Time.getTime());
		}
	}
	
	public void transformData(ResultSet rs) throws SQLException {
		setTradeno(rs.getString("tradeno"));
		setGid(rs.getString("gid"));
		setPid(rs.getString("pid"));
		setPeriod(rs.getInt("period"));
		setOnecCount(rs.getInt("onecCount"));
		setBuy_Time(rs.getTimestamp("buy_Time"));
		setPub_status(rs.getInt("pub_status"));
		setNickname(rs.getString("nickname"));
		setBuyCount(rs.getInt("buyCount"));
		setCodes(rs.getString("codes"));
		
	}
	
}
