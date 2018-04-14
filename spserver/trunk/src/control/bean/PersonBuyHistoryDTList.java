package control.bean;

import java.util.ArrayList;

public class PersonBuyHistoryDTList extends BaseBean {
	private ArrayList<PersonBuyHistoryDT> person_buy_his_list;

	public ArrayList<PersonBuyHistoryDT> person_buy_his_list() {
		return person_buy_his_list;
	}

	public void setPerson_buy_his_list(ArrayList<PersonBuyHistoryDT> person_buy_his_list) {
		this.person_buy_his_list = person_buy_his_list;
	}
}
