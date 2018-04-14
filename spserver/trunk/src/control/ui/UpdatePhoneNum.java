package control.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import util.SendMessageUtil;
import util.UserUtil;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import enumPKG.EventIdList;
import frame.SingleFactory;

public class UpdatePhoneNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdatePhoneNum() {
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
		String oldVCode = request.getParameter("oldVCode");
		String newPhoneNum = request.getParameter("newPhoneNum");
		String newVCode = request.getParameter("newVCode");
		String pid = request.getParameter("pid");
		PrintWriter out = response.getWriter();
		WebDB webDB = SingleFactory.ins().getWebDBIns();
		
		int event = EventIdList.getEvent(request);
		if (event == EventIdList.REQUEST_UPDATE_PHONENUM_SENDVCODE.eventId) {// 修改手机 原手机发送验证码
			String oldPhoneNum = webDB.getPhoneNum(pid);
			String vCode = SendMessageUtil.send(oldPhoneNum);
			if (vCode.equals("")) { // 错误码: 短信验证码发送失败!
				UserUtil.sendNormalErrorMsg(out,
						EventIdList.RESPONSE_MESSAGE_SEND_ERROR);
				return;
			}

			DBErrorCode de = webDB.updateVerification(vCode,pid);
			if (UserUtil.checkDBErrorCode(out, de)) {
				UserUtil.sendNormalErrorMsg(out, EventIdList.SUCCESS);
				return;
			}
		}
		
		out.print(JSONUtil.toJSON(EventIdList.SUCCESS));
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
