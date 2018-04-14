package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 详细用户购买记录
 * 包含夺宝号码，订单号等详细信息
 * @author Administrator
 *
 */
public class PersonBuyHistoryDT {
	private String tradeno;//订单号
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getOnecCount() {
		return onecCount;
	}
	public void setOnecCount(int onecCount) {
		this.onecCount = onecCount;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	private String gid;// 物品id
	private String pid;//购买者id
	private int period;//期号
	private int code;//夺宝号码
	private int onecCount;//一次多少钱
	private String buy_Time;//购买时间
	private String nickname;//用户昵称
	
	public String getBuy_Time() {
		return buy_Time;
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
		setBuy_Time(rs.getTimestamp("buy_Time"));;
		setNickname(rs.getString("nickname"));
		setCode(rs.getInt("code"));
		
	}
}
