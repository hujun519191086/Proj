package control.bean;

import java.util.ArrayList;

public class GoodsBuyHistoryList extends BaseBean {

	private ArrayList<GoodsBuyHistory> goods_buy_his_list;

	public ArrayList<GoodsBuyHistory> getGoods_buy_his_list() {
		return goods_buy_his_list;
	}

	public void setGoods_buy_his_list(ArrayList<GoodsBuyHistory> goods_buy_his_list) {
		this.goods_buy_his_list = goods_buy_his_list;
	}
}
