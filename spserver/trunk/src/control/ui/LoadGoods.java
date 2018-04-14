package control.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.bean.GoodsItem;
import control.bean.GoodsList;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;
import enumPKG.GOODS_ORDER;
import frame.SingleFactory;
import util.GoodsUtil;
import util.JSONUtil;
import util.UserUtil;
import util.WordUtil;

public class LoadGoods extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4189803063375793452L;

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);// TODO 要删除
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		PrintWriter out = response.getWriter();

		String pid = request.getParameter("pid");
		String page = request.getParameter("page");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String loc = request.getParameter("loc");
		String order = request.getParameter("order");
		String catelog_id = request.getParameter("catelog_id");
		String pageCount = request.getParameter("pageCount");
		String gid = request.getParameter("gid");

	

		// TODO 要做消息限速.
		// TODO 如果pid 是0 ,代表是游客. 需要有100个随机pid进行消息限速.同时使用只能100个
//		if (SingleFactory.ins().getSpeedLimitUtil().inSpeedLimit(out, pid)) {
//			return;
//		}

		// 页码不给默认0
		if (WordUtil.isEmpty(page)) {
			page = "0";
		}
		
		// 每页数量不给默认读取全部
		if (WordUtil.isEmpty(pageCount)) {
			pageCount = "-1";
		}

		// 经纬度只要一个是空. 那都是0
		if (WordUtil.isEmpty(longitude) || WordUtil.isEmpty(latitude)) {
			longitude = "0.0";
			latitude = "0.0";
		}
		// 获取排序名字
		if (WordUtil.isEmpty(order)) {
			order = GOODS_ORDER.PROGRESS.value + "";
		}
		//分类默认为全部分类
		if (WordUtil.isEmpty(catelog_id)){
			catelog_id = "0";
		}
		if(WordUtil.isEmpty(gid)){
			gid = "0";
		}

		DataPair<DBErrorCode, ArrayList<GoodsItem>> dp = SingleFactory
				.ins()
				.getGoodsIns()
				.locationFollowGoods(loc, Integer.valueOf(page),Integer.valueOf(pageCount),
						Integer.valueOf(order),Integer.valueOf(catelog_id),Long.valueOf(gid));

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
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
