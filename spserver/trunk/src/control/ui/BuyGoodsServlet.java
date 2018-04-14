package control.ui;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.tree.SingleIterator;

import control.inter.DBErrorCode;
import util.GoodsUtil;
import frame.SingleFactory;

public class BuyGoodsServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BuyGoodsServlet() {
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

		doPost(request, response);//TODO 要删除
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
		SingleFactory sf = SingleFactory.ins();
		long gid = Long.valueOf(request.getParameter("gid").toString());
		
		//todo 获取当前期号的方法和获取随机夺宝号码的方法
		int period = sf.getGoodsIns().getCurrentPeriod(gid);
		int buycount = Integer.valueOf(request.getParameter("buycount"));
		sf.getBuyHistoryIns().insertBuyHistory(gid, 770889226501394432l, System.currentTimeMillis(),buycount);
		DBErrorCode dbError = sf.getGoodsIns().changeGoodsData(gid,buycount);
		int pubStatus = (Integer)dbError.getTag();
		//参与人数到达总需人数
		if(pubStatus==2){
			sf.getBuyHistoryIns().insertPubHistory(gid,770889226501394432l,period);
			
			//重置号码池
			sf.getGoodsIns().updateCodeOrder(gid);
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
