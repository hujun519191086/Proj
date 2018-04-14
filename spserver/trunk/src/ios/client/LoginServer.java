package ios.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONUtil;
import util.UserUtil;
import util.WordUtil;
import control.bean.Person;
import control.db.personDB.IOSDB;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;
import frame.SingleFactory;
import frame.ServletInitApplication;

public class LoginServer extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServer() {
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
		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// String emailName = request.getParameter("emailName");
		// String passWorld = request.getParameter("pass");
		// String deviceId = request.getParameter("deviceId");
		// String type = request.getParameter("sp_type");
		// WebDB.ins().login(emailName, passWorld, deviceId, response);
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
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String emailName = request.getParameter("emailName");
		String passWorld = request.getParameter("pass");
		String deviceId = request.getParameter("deviceId");

		if (WordUtil.isEmpty(deviceId)) {
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.LOGIN_FAILT_NO_DEVICE_ID);
			return;
		}

		IOSDB iosWeb = SingleFactory.ins().getIOSDBIns();
		DataPair<DBErrorCode, Person> person = iosWeb.login(emailName,
				passWorld, deviceId, response);

		if (UserUtil.checkDBErrorCode(out, person.first)) {

			// 邮箱都没有被使用, 肯定没注册. 发送错误码
			if (!UserUtil.mailInUse(person)) {

				UserUtil.sendNormalErrorMsg(out,
						EventIdList.RESPONSE_LOGIN_PASS_ERROR);
				return;
			}
			if (UserUtil.accountInLoginLock(person)) {// 检查邮箱是否被锁定

				iosWeb.updateDeviceId(person.second.getPid(), deviceId);// 更新IME
				// 登录成功 ,跳转网页
				out.write(JSONUtil.toJSON(person.second));
				out.flush();
				out.close();
			} else {
				UserUtil.sendNormalErrorMsg(out,
						EventIdList.RESPONSE_LOGIN_ACCOUNT_BE_LOCK);
			}

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
