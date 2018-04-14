package control.bean;

import java.util.ArrayList;

public class PersonBuyHistoryList extends BaseBean {
	private ArrayList<PersonBuyHistory> person_buy_his_list;

	public ArrayList<PersonBuyHistory> person_buy_his_list() {
		return person_buy_his_list;
	}

	public void setPerson_buy_his_list(ArrayList<PersonBuyHistory> person_buy_his_list) {
		this.person_buy_his_list = person_buy_his_list;
	}
}
