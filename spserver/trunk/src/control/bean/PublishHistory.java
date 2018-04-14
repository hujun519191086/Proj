package control.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PublishHistory {
	private String gid;// 物品id
	private String pid;//幸运用户id
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	private int period;//期号
	private int pub_status;//揭晓状态 0 等待揭晓 1 已揭晓
	private int lucky_code;//幸运号码
	private String pub_time;//揭晓时间
	private String location;//地区名
	private String cur_time;//服务器当前时间
	private String beloneId;//专属者名字
	private int totalCount;//总人次
	private int notifyStatus;//中奖用户是否已通知
	public int getNotifyStatus() {
		return notifyStatus;
	}
	public void setNotifyStatus(int notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	private int onecCount;//一次多少钱
	private String goods_name;//物品名字
	private String second_name;//二级名称
	private String province;//省级
	private String city;//市级
	private String district;//县级
	private String goods_img;//图片路径
	private String catelog;//物品分类目录
	private int catelog_id;//分类id
	private String dis_imgs;// 展示图片列表
	private String nickname;//幸运用户昵称
	private int buyCount;//幸运用户本期购买人次
	public String getDis_imgs() {
		return dis_imgs;
	}
	public void setDis_imgs(String dis_imgs) {
		this.dis_imgs = dis_imgs;
	}
	public String getDetail_imgs() {
		return detail_imgs;
	}
	public void setDetail_imgs(String detail_imgs) {
		this.detail_imgs = detail_imgs;
	}
	private String detail_imgs;// 详情图片列表
	
	
	public String getBeloneId() {
		return beloneId;
	}
	public void setBeloneId(String beloneId) {
		this.beloneId = beloneId;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getOnecCount() {
		return onecCount;
	}
	public void setOnecCount(int onecCount) {
		this.onecCount = onecCount;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getSecond_name() {
		return second_name;
	}
	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	public String getCatelog() {
		return catelog;
	}
	public void setCatelog(String catelog) {
		this.catelog = catelog;
	}
	public int getCatelog_id() {
		return catelog_id;
	}
	public void setCatelog_id(int catelog_id) {
		this.catelog_id = catelog_id;
	}
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getPub_status() {
		return pub_status;
	}
	public void setPub_status(int pub_status) {
		this.pub_status = pub_status;
	}
	public int getLucky_code() {
		return lucky_code;
	}
	public void setLucky_code(int lucky_code) {
		this.lucky_code = lucky_code;
	}
	public String getPub_time() {
		return pub_time;
	}
	public void setPub_time(Timestamp pub_time) {
		if(pub_time!=null)
		{
			this.pub_time = String.valueOf(pub_time.getTime());
		}
	}
	public String getCur_time() {
		return cur_time;
	}

	public void setCur_time(Timestamp cur_time) {
		if(cur_time!=null)
		{
			this.cur_time = String.valueOf(cur_time.getTime());
		}
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void transformData(ResultSet rs) throws SQLException {
		setGid(rs.getString("gid"));
		setPeriod(rs.getInt("period"));
		setPub_time(rs.getTimestamp("pub_time"));
		setLocation(rs.getString("location"));
		setBeloneId(rs.getString("beloneId"));
		setTotalCount(rs.getInt("totalCount"));
		setOnecCount(rs.getInt("onecCount"));
		setGoods_name(rs.getString("goods_name"));
		setSecond_name(rs.getString("second_name"));
		setProvince(rs.getString("province"));
		setCity(rs.getString("city"));
		setDistrict(rs.getString("district"));
		setGoods_img(rs.getString("goods_img"));
		setCatelog(rs.getString("catelog"));
		setCatelog_id(rs.getInt("catelog_id"));
		setDis_imgs(rs.getString("dis_imgs"));
		setDetail_imgs(rs.getString("detail_imgs"));
		setNickname(rs.getString("nickname"));
		setBuyCount(rs.getInt("buyCount"));
		setLucky_code(rs.getInt("lucky_code"));
		setPub_status(rs.getInt("pub_status"));
		setPid(rs.getString("pid"));
		boolean hasNotifyStatus = false;
		try{
			if(rs.findColumn("notfyStatus")>0){
				setNotifyStatus(rs.getInt("notfyStatus"));
				hasNotifyStatus = true;
			}
		}
		catch(SQLException e){
			hasNotifyStatus = false;
		}
		if(hasNotifyStatus){
			setNotifyStatus(rs.getInt("notfyStatus"));
		}
		
		
		
		
		//判断是否要读取当前时间
		boolean hasCurTime = false;
		try{
			if(rs.findColumn("cur_time")>0){
				setCur_time(rs.getTimestamp("cur_time"));
				hasCurTime = true;
			}
		}
		catch(SQLException e){
			hasCurTime = false;
		}
		if(hasCurTime){
			setCur_time(rs.getTimestamp("cur_time"));
		}
	}
	
}
