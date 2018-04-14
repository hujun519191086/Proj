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
import control.bean.LoginInfo;
import control.bean.Person;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;
import frame.DataCenter;
import frame.SingleFactory;

public class LoadLoginInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoadLoginInfo() {
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
		HttpSession session = request.getSession();
		LoginInfo loginInfo = new LoginInfo();
		

		Logger logger = LoggerFactory.getLogger(getClass());
		WebDB webDB = SingleFactory.ins().getWebDBIns();
		DataPair<DBErrorCode, Person> person = null;
		
		if(session != null){
			if(session.getAttribute("nickname")!=null && session.getAttribute("pid")!=null){
				String nickname = session.getAttribute("nickname").toString();
				String pid = session.getAttribute("pid").toString();
				loginInfo.setNickname(nickname);
				loginInfo.setPid(pid);
						
			}
			else{
				Cookie[] cookies = request.getCookies();
				String mail = "";
				String phone = "";
				String pass = "";
				
				// 有cookie
				if (cookies != null ) {
					for(Cookie cookie : cookies){
						String cookieName = cookie.getName();
						if(cookieName.equals("mail")){
							mail = cookie.getValue();
							continue;
						}
						else if(cookieName.equals("phone")){
							phone = cookie.getValue();
							continue;
						}
						else if(cookieName.equals("pass")){
							pass = cookie.getValue();
							continue;
						}
					}
					if(!WordUtil.isEmpty(phone)&&!WordUtil.isEmpty(pass)){
						person = webDB.phoneLogin(phone, pass);
						if (UserUtil.checkDBErrorCode(out, person.first)) {
							
							// 检查手机号是否合法
							if (!UserUtil.phoneNumIsLegitimate(phone)) {
								
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
								
								logger.info(" user login  success,user name:"+phone);
								WebDB.autoLogin(request, response, person.second, pass);
								// 登录成功 ,跳转网页
								
								String pid = person.second.getPid();
								String nickname = person.second.getNickname();
								String loginMD5 = MD5Util.MD5Encode(person.second.getPid()+System.currentTimeMillis()+phone,"UTF-8");
								
								
								session.setAttribute("pid", pid);
								session.setAttribute("nickname", nickname);
								session.setAttribute("loginMD5", loginMD5);
								
								loginInfo.setNickname(nickname);
								loginInfo.setPid(pid);
								
								DataCenter.ins().updateLoginMD5(pid, loginMD5);
							} else {
								UserUtil.sendNormalErrorMsg(out,
										EventIdList.RESPONSE_LOGIN_ACCOUNT_BE_LOCK);
							}
							
						}
					}
					else if(!WordUtil.isEmpty(mail)&&!WordUtil.isEmpty(pass)){
						person = webDB.mailLogin(mail, pass);
						if (UserUtil.checkDBErrorCode(out, person.first)) {
							
							// 检查邮箱是否合法
							if (!UserUtil.mailIsLegitimate(mail)) {
								
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
								
								logger.info(" user login  success,user name:"+mail);
								WebDB.autoLogin(request, response, person.second, mail);
								// 登录成功 ,跳转网页
								
								String pid = person.second.getPid();
								String nickname = person.second.getNickname();
								String loginMD5 = MD5Util.MD5Encode(person.second.getPid()+System.currentTimeMillis()+mail,"UTF-8");
								
								
								session.setAttribute("pid", pid);
								session.setAttribute("nickname", nickname);
								session.setAttribute("loginMD5", loginMD5);
								
								loginInfo.setNickname(nickname);
								loginInfo.setPid(pid);
								
								DataCenter.ins().updateLoginMD5(pid, loginMD5);
								
							} else {
								UserUtil.sendNormalErrorMsg(out,
										EventIdList.RESPONSE_LOGIN_ACCOUNT_BE_LOCK);
							}
							
						}
					}
				}
				else{
					
				}
				
			}
		}
		out.write(JSONUtil.toJSON(loginInfo));
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
