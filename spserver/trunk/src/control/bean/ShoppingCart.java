package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCart {
	private String pid;
	private String gid;
	private int buyCount;//购买数量
	private String goods_img;//图片路径
	private String dis_imgs;//展示图片列表 
	private String goods_name;//物品名称
	private int totalCount;//总需
	private int needCount;//还需
	private int onecCount;//夺宝单价
	
	public String getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = String.valueOf(pid);
	}
	public String getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = String.valueOf(gid);
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	public String getDis_imgs() {
		return dis_imgs;
	}
	public void setDis_imgs(String dis_imgs) {
		this.dis_imgs = dis_imgs;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getNeedCount() {
		return needCount;
	}
	public void setNeedCount(int needCount) {
		this.needCount = needCount;
	}
	public int getOnecCount() {
		return onecCount;
	}
	public void setOnecCount(int onecCount) {
		this.onecCount = onecCount;
	}
	
	public void transformData(ResultSet rs) throws SQLException {
		setPid(rs.getLong("pid"));
		setGid(rs.getLong("gid"));
		setTotalCount(rs.getInt("totalCount"));
		setOnecCount(rs.getInt("onecCount"));
		setNeedCount(rs.getInt("needCount"));
		setBuyCount(rs.getInt("buyCount"));
		setGoods_img(rs.getString("goods_img"));
		setDis_imgs(rs.getString("dis_imgs"));
		setGoods_name(rs.getString("goods_name"));
	}
	
}
