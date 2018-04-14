package control.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import util.SQLUtil;
import util.UserUtil;
import util.WordUtil;
import control.bean.GoodsItem;
import control.bean.GoodsList;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.GOODS_ORDER;
import frame.SingleFactory;

public class LoadSoonPublish extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadSoonPublish() {
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

		doPost(request,response);
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
		// TODO 要做消息限速.
		// TODO 如果pid 是0 ,代表是游客. 需要有100个随机pid进行消息限速.同时使用只能100个
		PrintWriter out = response.getWriter();

		String pid = request.getParameter("pid");
		String page = request.getParameter("page");
		String pageCount = request.getParameter("pageCount");

		if (WordUtil.isEmpty(pid)) {
			pid = "0";
		}

		if (SingleFactory.ins().getSpeedLimitUtil().inSpeedLimit(out, pid)) {
			return;
		}

		// 页码不给默认0
		if (WordUtil.isEmpty(page)) {
			page = "0";
		}
		if (WordUtil.isEmpty(pageCount)){
			pageCount="-1";
		}

	

//		String db_name = GoodsUtil.getLoacalName(Double.valueOf(longitude),
//				Double.valueOf(latitude));
		String db_name = SQLUtil.getGoodsTableName();

		DataPair<DBErrorCode, ArrayList<GoodsItem>> dp = SingleFactory
				.ins()
				.getGoodsIns()
				.locationSoonPublishGoods(db_name, Integer.valueOf(page),Integer.valueOf(pageCount), pid);

		if (UserUtil.checkDBErrorCode(out, dp.first)) {

			GoodsList gl = new GoodsList();
			gl.setGoods_list(dp.second);
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
