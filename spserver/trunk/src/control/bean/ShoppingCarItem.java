package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCarItem {
	private String pid;
	private String gid;
	private int buyCount;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	
	public String getPid(String pid){
		return pid;
	}
	
	public String getGid(String gid){
		return gid;
	}
	
	public int getBuyCount(int buyCount){
		return buyCount;
	}
	public void transformDataShoppingCart(ResultSet rs) throws SQLException{
		setPid(rs.getString("pid"));
		setGid(rs.getString("gid"));
		setBuyCount(rs.getInt("buyCount"));
		
	}
	
}
