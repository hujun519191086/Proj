package control.bean;

import java.io.File;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import frame.Global_value;

public class GoodsItem {

	private String beloneId;// 专属者名字
	private String gid; // 唯一id
	private int totalCount;// 总共需要多少钱
	private int onecCount;// 一次多少钱
	private int needCount;// 还需购买人次
	private int followCount;// 关注人数
	private int progress;// 百分之几
	private String goods_name;// 物品名字
	private String second_name;// 二级名称
	private String open_time;// 开放时间
	private String goods_img;// 图片路径
	private String catelog;// 物品分类目录
	private String province;// 省级
	private String city;// 市级
	private String district;// 县级
	private String cur_time;// 当前时间
	private int catelog_id;// 分类id
	private String dis_imgs;// 展示图片列表
	private String detail_imgs;// 详情图片列表
	
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


	public int getCatelog_id() {
		return catelog_id;
	}

	public void setCatelog_id(int catelog_id) {
		this.catelog_id = catelog_id;
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

	private String locaiton;// 地理位置
	private int status;// 物品状态 0 未开放 1 正在进行 2 即将揭晓
	private int period;// 当前期号
	private int stock;// 库存

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCatelog() {
		return catelog;
	}

	public void setCatelog(String catelog) {
		this.catelog = catelog;
	}

	public String getLocaiton() {
		return locaiton;
	}

	public void setLocaiton(String locaiton) {
		this.locaiton = locaiton;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private ArrayList<String> goods_img_list;// 某个物品的图片集

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

	public String getGoods_img() {
		return goods_img;
	}

	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(Timestamp open_time) {
		if (open_time != null) {
			this.open_time = String.valueOf(open_time.getTime());
		}
	}

	public String getCur_time() {
		return cur_time;
	}

	public void setCur_time(Timestamp cur_time) {
		if (cur_time != null) {
			this.cur_time = String.valueOf(cur_time.getTime());
		}
	}

	public ArrayList<String> getGoods_img_list() {
		return goods_img_list;
	}

	// 初始化图片路径.
	public void initPic(String picPath) {
		this.goods_img_list = new ArrayList<String>();

		String realPath = picPath + getGid();

		File dir = new File(realPath);

		String[] list = dir.list();

		for (int i = 0; i < list.length; i++) {
			goods_img_list.add(realPath + list[i]);
		}
	}

	public String getGid() {
		return gid;
	}

	public void setGid(long gid) {
		this.gid = gid + "";
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

	public int getNeedCount() {
		return needCount;
	}

	public void setNeedCount(int haveCount) {
		this.needCount = haveCount;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public boolean goodsCanChange() {

		if (System.currentTimeMillis() > (Long.valueOf(getOpen_time()) - Global_value.GOODS_DATA_IN_OPEN_PRE_TIME)
				|| getNeedCount() != 0) {
			return false;// 到达时间,或者 有用户已经购买了. 则不让修改数据了.
		}

		return true;
	}

	public String getBeloneId() {
		return beloneId;
	}

	public void setBeloneId(String beloneId) {
		this.beloneId = beloneId;
	}

	public int getFollowCount() {
		return followCount;
	}

	public void setFollowCount(int followCount) {
		this.followCount = followCount;
	}

	@Override
	public String toString() {
		return "GoodsItem [beloneId=" + beloneId + ", gid=" + gid
				+ ", totalCount=" + totalCount + ", onecCount=" + onecCount
				+ ", needCount=" + needCount + ", followCount=" + followCount
				+ ", goods_name=" + goods_name + ", second_name=" + second_name
				+ ", open_time=" + open_time + ", goods_img_list="
				+ goods_img_list + "]";
	}

	public void transformUserData(ResultSet rs) throws SQLException {
		setGid(rs.getLong("gid"));
		setTotalCount(rs.getInt("totalCount"));
		setOnecCount(rs.getInt("onecCount"));
		setNeedCount(rs.getInt("needCount"));
		setFollowCount(rs.getInt("followCount"));
		setProgress(rs.getInt("progress"));
		setGoods_name(rs.getString("goods_name"));
		setSecond_name(rs.getString("second_name"));
		setOpen_time(rs.getTimestamp("open_time"));
		setGoods_img(rs.getString("goods_img"));
		setCatelog(rs.getString("catelog"));
		setLocaiton(rs.getString("location"));
		setStatus(rs.getInt("status"));
		setStock(rs.getInt("stock"));
		setPeriod(rs.getInt("period"));
		setProvince(rs.getString("province"));
		setCity(rs.getString("city"));
		setDistrict(rs.getString("district"));
		setCatelog_id(rs.getInt("catelog_id"));
		setDis_imgs(rs.getString("dis_imgs"));
		setDetail_imgs(rs.getString("detail_imgs"));

		// 判断是否要读取当前时间
		boolean hasCurTime = false;
		try {
			if (rs.findColumn("cur_time") > 0) {
				setCur_time(rs.getTimestamp("cur_time"));
				hasCurTime = true;
			}
		} catch (SQLException e) {
			hasCurTime = false;
		}
		if (hasCurTime) {
			setCur_time(rs.getTimestamp("cur_time"));
		}
	}

	public void transformData(ResultSet rs) throws SQLException {
		setBeloneId(rs.getString("beloneId"));
		setGid(rs.getLong("gid"));
		setTotalCount(rs.getInt("totalCount"));
		setOnecCount(rs.getInt("onecCount"));
		setNeedCount(rs.getInt("needCount"));
		setFollowCount(rs.getInt("followCount"));
		setProgress(rs.getInt("progress"));
		setGoods_name(rs.getString("goods_name"));
		setSecond_name(rs.getString("second_name"));
		setOpen_time(rs.getTimestamp("open_time"));
		setGoods_img(rs.getString("goods_img"));
		setCatelog(rs.getString("catelog"));
		setLocaiton(rs.getString("location"));
		setStatus(rs.getInt("status"));
		setStock(rs.getInt("stock"));
		setPeriod(rs.getInt("period"));
		setProvince(rs.getString("province"));
		setCity(rs.getString("city"));
		setDistrict(rs.getString("district"));
		setCatelog_id(rs.getInt("catelog_id"));
		setDis_imgs(rs.getString("dis_imgs"));
		setDetail_imgs(rs.getString("detail_imgs"));

		// 判断是否要读取当前时间
		boolean hasCurTime = false;
		try {
			if (rs.findColumn("cur_time") > 0) {
				setCur_time(rs.getTimestamp("cur_time"));
				hasCurTime = true;
			}
		} catch (SQLException e) {
			hasCurTime = false;
		}
		if (hasCurTime) {
			setCur_time(rs.getTimestamp("cur_time"));
		}
	}

}
