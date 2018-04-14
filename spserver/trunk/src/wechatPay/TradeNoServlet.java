package wechatPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.bean.GoodsItem;
import control.bean.ShoppingCarItem;
import frame.SingleFactory;

public class TradeNoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TradeNoServlet() {
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

		doPost(request, response);
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
		//将订单信息保存到订单数据库并返回订单号
		//将tradeno,orderNo,pid,物品名称,（物品gid:参与人次）,总价格,购买时间存入List
		//tradeNo表示当前订单的状态，不对用户显示    orderNo 页面显示的订单号
		//物品名称使用secondname
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int tradeNo;
		String goodName;
		int onceCount;
		int buyCount;
		int totalCount = 0;
		String goodsName = "";
		String attach = "";
		String[] orderArray = new String[5];
		String gids = request.getParameter("gid").toString();
		Long pid = Long.valueOf(request.getParameter("pid").toString());
		String []gitem = gids.split(",");
		SingleFactory sf = SingleFactory.ins();
		GoodsItem goodItem;
		ShoppingCarItem shoppingCarItem;
		//生成orderNo
		Date now = new Date();
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNo = dataFormat.format(now).toString();
		int randomno = (int)((Math.random()*9+1)*100000);
		orderNo =  orderNo+randomno;
		
		for(int i = 0; i < gitem.length; i++){
			goodItem = sf.getGoodsIns().getGoodsItem(Long.parseLong(gitem[i]));
			goodsName = goodsName + goodItem.getGoods_name();
			onceCount = goodItem.getOnecCount();
			shoppingCarItem = sf.getShoppingCart().getShoppingCartItem(pid, Long.parseLong(gitem[i]));
			
			buyCount = shoppingCarItem.getBuyCount();
			
			attach = attach + gitem[i] + ":" + buyCount +",";
			totalCount = totalCount + onceCount * buyCount;
		}
		orderArray[0] = orderNo;
		orderArray[1] = pid.toString();
		orderArray[2] = goodsName;
		orderArray[3] = attach;
		orderArray[4] = String.valueOf(totalCount*100);//微信支付0.01元为一个单位
		System.out.println(orderArray[4]);
		tradeNo = sf.getTradeNodb().insertTradeNoDB(orderArray);
		System.out.println(tradeNo);
		out.print(tradeNo);
		out.flush();
		out.close();
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
