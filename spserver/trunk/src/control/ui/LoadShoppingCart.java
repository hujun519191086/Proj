package control.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import util.UserUtil;
import util.WordUtil;
import control.bean.PublishHistoryList;
import control.bean.ShoppingCart;
import control.bean.ShoppingCartList;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import frame.SingleFactory;

public class LoadShoppingCart extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadShoppingCart() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);// TODO 要删除
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		PrintWriter out = response.getWriter();
		
		String pid = request.getParameter("pid");
		
		if (WordUtil.isEmpty(pid)) {
			pid = "0";
		}
		
		DataPair<DBErrorCode, ArrayList<ShoppingCart>> dp = SingleFactory
				.ins()
				.getShoppingCart()
				.LoadShoppingCart(Long.valueOf(pid));
		
		if (UserUtil.checkDBErrorCode(out, dp.first)) {

			ShoppingCartList cartList = new ShoppingCartList();
			cartList.setCartList(dp.second);
			out.write(JSONUtil.toJSON(cartList));
			out.flush();
			out.close();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
