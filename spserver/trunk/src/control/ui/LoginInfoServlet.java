package control.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.JSONUtil;
import util.MD5Util;
import util.UserUtil;
import util.WordUtil;
import control.bean.Person;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;
import frame.DataCenter;
import frame.SingleFactory;

@SuppressWarnings("serial")
public class LoginInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginInfoServlet() {
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
		doPost(request,response);
	}

	//

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
		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		Logger logger = LoggerFactory.getLogger(getClass());
		PrintWriter out = response.getWriter();
		String emailName = request.getParameter("emailName");
		String phoneNum = request.getParameter("phonenum");
		String passWorld = request.getParameter("pass");

		
		WebDB webDB = SingleFactory.ins().getWebDBIns();
		DataPair<DBErrorCode, Person> person = null;
		
		if(!WordUtil.isEmpty(phoneNum)){//手机登录
			person = webDB.phoneLogin(phoneNum, passWorld);
			if (UserUtil.checkDBErrorCode(out, person.first)) {
				
				// 检查手机号是否合法
				if (!UserUtil.phoneNumIsLegitimate(phoneNum)) {
					
					logger.info(" user login  fault,resion:"+EventIdList.RESPONSE_PHONENUM_IS_CANNOT_TO_USE);
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_PHONENUM_IS_CANNOT_TO_USE);
					return;
				}
				// 手机号都没有被使用, 肯定没注册. 发送错误码
				if (!UserUtil.phoneNumInUse(person)) {
					logger.info(" user login  fault,resion:"+EventIdList.RESPONSE_LOGIN_PASS_ERROR);
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_LOGIN_PASS_ERROR);
					return;
				}
				if (UserUtil.accountInLoginLock(person)) {// 检查账号是否被锁定
					
					logger.info(" user login  success,user name:"+phoneNum);
					WebDB.autoLogin(request, response, person.second, passWorld);
					// 登录成功 ,跳转网页
					
					String pid = person.second.getPid();
					String nickname = person.second.getNickname();
					String loginMD5 = MD5Util.MD5Encode(person.second.getPid()+System.currentTimeMillis()+phoneNum,"UTF-8");
					
					HttpSession session = request.getSession();
					session.setAttribute("pid", pid);
					session.setAttribute("nickname", nickname);
					session.setAttribute("loginMD5", loginMD5);
					
					DataCenter.ins().updateLoginMD5(pid, loginMD5);
					
					out.write(JSONUtil.toJSON(person.second));
					out.flush();
					out.close();
				} else {
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_LOGIN_ACCOUNT_BE_LOCK);
				}
				
			}

		}
		else if(!WordUtil.isEmpty(emailName)){//邮箱登陆
			
			person = webDB.mailLogin(emailName, passWorld);
			if (UserUtil.checkDBErrorCode(out, person.first)) {
				
				// 检查邮箱是否合法
				if (!UserUtil.mailIsLegitimate(emailName)) {
					
					logger.info(" user login  fault,resion:"+EventIdList.RESPONSE_MAIL_IS_CANNOT_TO_USE);
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_MAIL_IS_CANNOT_TO_USE);
					return;
				}
				// 邮箱都没有被使用, 肯定没注册. 发送错误码
				if (!UserUtil.mailInUse(person)) {
					logger.info(" user login  fault,resion:"+EventIdList.RESPONSE_LOGIN_PASS_ERROR);
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_LOGIN_PASS_ERROR);
					return;
				}
				if (UserUtil.accountInLoginLock(person)) {// 检查邮箱是否被锁定
					
					logger.info(" user login  success,user name:"+emailName);
					WebDB.autoLogin(request, response, person.second, passWorld);
					// 登录成功 ,跳转网页
					
					String pid = person.second.getPid();
					String nickname = person.second.getNickname();
					String loginMD5 = MD5Util.MD5Encode(person.second.getPid()+System.currentTimeMillis()+emailName,"UTF-8");
					
					HttpSession session = request.getSession();
					session.setAttribute("pid", pid);
					session.setAttribute("nickname", nickname);
					session.setAttribute("loginMD5", loginMD5);
					
					DataCenter.ins().updateLoginMD5(pid, loginMD5);
					
					out.write(JSONUtil.toJSON(person.second));
					out.flush();
					out.close();
				} else {
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_LOGIN_ACCOUNT_BE_LOCK);
				}
				
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
	}

}
