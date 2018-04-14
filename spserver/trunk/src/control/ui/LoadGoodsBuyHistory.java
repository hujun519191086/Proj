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
import control.bean.GoodsBuyHistory;
import control.bean.GoodsBuyHistoryList;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import frame.SingleFactory;

public class LoadGoodsBuyHistory extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadGoodsBuyHistory() {
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

		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		
		PrintWriter out = response.getWriter();
		String gid = request.getParameter("gid").toString();
		String period = request.getParameter("period").toString();
		
		DataPair<DBErrorCode, ArrayList<GoodsBuyHistory>> dp = SingleFactory
				.ins()
				.getBuyHistoryIns()
				.loadGoodsBuyHistory( Long.valueOf(gid),Integer.valueOf(period));

		if (UserUtil.checkDBErrorCode(out, dp.first)) {

			GoodsBuyHistoryList gl = new GoodsBuyHistoryList();
			gl.setGoods_buy_his_list(dp.second);
			out.write(JSONUtil.toJSON(gl));
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
