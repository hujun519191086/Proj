package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradeNoItem {
	private  Long ID ;
	private String orderNo;
	private Long pid;
	private String goodsName;
	private String attach;
	private int totalCount;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public void transformData(ResultSet rs) throws SQLException {
		setOrderNo(rs.getString("orderNo"));
		setPid(rs.getLong("pid"));
		setGoodsName(rs.getString("goodsName"));
		setAttach(rs.getString("attach"));
		setTotalCount(rs.getInt("totalCount"));
		
	}
}
