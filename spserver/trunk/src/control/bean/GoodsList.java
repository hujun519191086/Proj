package control.bean;

import java.util.ArrayList;

public class GoodsList extends BaseBean{

	private ArrayList<GoodsItem> goods_list;//最大页码.

	public ArrayList<GoodsItem> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(ArrayList<GoodsItem> goods_list) {
		this.goods_list = goods_list;
	}
	
	
	
}
