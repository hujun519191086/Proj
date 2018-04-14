package wechatPay;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import control.bean.GoodsItem;
import control.bean.ShoppingCarItem;
import control.bean.TradeNoItem;
import control.bean.UrlCode;
import control.inter.DBErrorCode;
import frame.SingleFactory;

public class weixinpayServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public weixinpayServlet() {
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
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		SingleFactory sf = SingleFactory.ins();
		
		int totalCount;
		String attach;
		TradeNoItem tradeNoItem = new TradeNoItem();
		
		Long tradeno = Long.valueOf(request.getParameter("tradeno"));
		tradeNoItem = sf.getTradeNodb().getTradeNoItem(tradeno);
		attach = tradeNoItem.getAttach();
		String [] attachConvert = attach.split(":|,");
		int Length = attachConvert.length;
		Long pid = Long.valueOf(request.getParameter("pid").toString());
		for(int i=0; i<Length/2;i++){
			sf.getShoppingCart().DeleteShoppingCart(Long.valueOf(attachConvert[i]), pid);
		}
		
		totalCount = sf.getTradeNodb().getTotalCount(tradeno);
		//sf.getShoppingCart().DeleteShoppingCart(gid, pid);
		String body = "海淘云购";
		Weixin_pay wp = new Weixin_pay();
		String xmlres = "";
		UrlCode urlCode = new UrlCode();
		try {
			//返回二维码信息
			xmlres = wp.weixin_pay(String.valueOf(tradeno), String.valueOf(totalCount), body);
			//设置跳转的二维码页面
			System.out.println(xmlres);
			urlCode.setUrlCode(xmlres);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		out.print(JSONUtil.toJSON(urlCode));
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
