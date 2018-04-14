package control.bean;

import java.util.ArrayList;

public class ShoppingCartList extends BaseBean {
	private ArrayList<ShoppingCart> cartList;

	public ArrayList<ShoppingCart> getCartList() {
		return cartList;
	}

	public void setCartList(ArrayList<ShoppingCart> cartList) {
		this.cartList = cartList;
	}
	
}
