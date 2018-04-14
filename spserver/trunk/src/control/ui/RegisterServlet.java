package control.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.JSONUtil;
import util.MD5Util;
import util.SendEmailUtil;
import util.SendMessageUtil;
import util.UserUtil;
import util.WordUtil;
import control.bean.Person;
import control.db.personDB.WebDB;
import control.inter.DBErrorCode;
import control.inter.DataPair;
import enumPKG.EventIdList;
import frame.DataCenter;
import frame.SingleFactory;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public RegisterServlet() {
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
		doPost(request, response);
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

		// ios web
		Object fromWhere = request.getAttribute("from");

		if (fromWhere != null && fromWhere.toString().equals("ios")) {
			response.setContentType("text/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}

		PrintWriter out = response.getWriter();
		String phoneNum = request.getParameter("phoneNum");
		String emailName = request.getParameter("emailName");
		
		WebDB webDB = SingleFactory.ins().getWebDBIns();
		DataPair<DBErrorCode, Person> person = null;
		if(!WordUtil.isEmpty(phoneNum)){
			// 检查手机号是否合法
			if (!UserUtil.phoneNumIsLegitimate(phoneNum)) {
				UserUtil.sendNormalErrorMsg(out, EventIdList.RESPONSE_PHONENUM_IS_EMPTY);
				return;
			}
			
			person = webDB.queryInfo4PhoneNum(phoneNum);
			
			// 检查手机号是否被使用
			if (UserUtil.phoneNumInUse(person)) {
				UserUtil.sendNormalErrorMsg(out,
						EventIdList.RESPONSE_REGIST_PHONENUM_FAILE_BEUSE);
				return;
			}
		}
		else if(!WordUtil.isEmpty(emailName)){
			
			// 检查邮箱是否合法
			if (!UserUtil.mailIsLegitimate(emailName)) {
				UserUtil.sendNormalErrorMsg(out, EventIdList.RESPONSE_MAIL_IS_EMPTY);
				return;
			}
			
			person = webDB.queryInfo4Mail(emailName);
			
			// 检查邮箱是否被使用
			if (UserUtil.mailInUse(person)) {
				UserUtil.sendNormalErrorMsg(out,
						EventIdList.RESPONSE_REGIST_MAIL_FAILE_BEUSE);
				return;
			}
		}
		else{
			return;
		}

		// 通用数据库返回的code检查
		if (UserUtil.checkDBErrorCode(out, person.first)) {

			int event = EventIdList.getEvent(request);
			if (event == EventIdList.REQUEST_REGIST_SEND_MAIL.eventId) {// 发送邮箱验证码

				String vCode = SendEmailUtil.send(emailName);
				if (vCode.equals("")) { // 错误码: 邮箱发送失败!
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_MAIL_SEND_ERROR);
					return;
				}

				DBErrorCode de = webDB.updateMailVerificationToUser(emailName,
						vCode, person.second);
				if (UserUtil.checkDBErrorCode(out, de)) {
					UserUtil.sendNormalErrorMsg(out, EventIdList.SUCCESS);
				}

			} 
			else if(event == EventIdList.REQUEST_REGIST_SEND_MESSAGE.eventId){//手机验证码
				String vCode = SendMessageUtil.send(phoneNum);
				if (vCode.equals("")) { // 错误码: 短信验证码发送失败!
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_MESSAGE_SEND_ERROR);
					return;
				}

				DBErrorCode de = webDB.updatePhoneNumVerificationToUser(phoneNum,
						vCode, person.second);
				if (UserUtil.checkDBErrorCode(out, de)) {
					UserUtil.sendNormalErrorMsg(out, EventIdList.SUCCESS);
				}
			}
			else if (event == EventIdList.REQUEST_REGIST.eventId) {// 注册这个账号

				String passWorld = request.getParameter("pass");
				String verificationCode = request
						.getParameter("VerificationCode");

				// 檢查密码是否过于简单
				if (!UserUtil.checkPass(passWorld)) {
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_PASS_TOO_EASE);
					return;
				}

				// 检查 校验码是否成功
				if (!UserUtil.checkVerficationIsSuccess(verificationCode,
						person.second)) {
					UserUtil.sendNormalErrorMsg(out,
							EventIdList.RESPONSE_VER_CODE_ERROR);
					return;
				}
				

				
				if(!WordUtil.isEmpty(phoneNum)){
					// 真正插入密码数据
					DataPair<DBErrorCode, Person> dp = webDB.registPhoneNumPerson(phoneNum, passWorld,
							person.second);
					
					String pid = dp.second.getPid();
					String nickname = dp.second.getNickname();
					String loginMD5 = MD5Util.MD5Encode(dp.second.getPid()+System.currentTimeMillis()+phoneNum,"UTF-8");
					
					HttpSession session = request.getSession();
					session.setAttribute("pid", pid);
					session.setAttribute("nickname", nickname);
					session.setAttribute("loginMD5", loginMD5);
					
					DataCenter.ins().updateLoginMD5(pid, loginMD5);
					
					if (UserUtil.checkDBErrorCode(out, dp.first)) {
						Logger logger = LoggerFactory.getLogger(getClass());
						logger.info("regist user success,user name:"+phoneNum);
						
						out.write(JSONUtil.toJSON(dp.second));
						out.flush();
						out.close();
					}

				}
				else if(!WordUtil.isEmpty(emailName)){
					
					// 真正插入密码数据
					DataPair<DBErrorCode, Person> dp = webDB.registMailPerson(emailName, passWorld,
							person.second);
					
					String pid = dp.second.getPid();
					String nickname = dp.second.getNickname();
					String loginMD5 = MD5Util.MD5Encode(dp.second.getPid()+System.currentTimeMillis()+emailName,"UTF-8");
					
					HttpSession session = request.getSession();
					session.setAttribute("pid", pid);
					session.setAttribute("nickname", nickname);
					session.setAttribute("loginMD5", loginMD5);
					
					DataCenter.ins().updateLoginMD5(pid, loginMD5);
					
					if (UserUtil.checkDBErrorCode(out, dp.first)) {
						Logger logger = LoggerFactory.getLogger(getClass());
						logger.info("regist user success,user name:"+emailName);
						out.write(JSONUtil.toJSON(dp.second));
						out.flush();
						out.close();
					}
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
		// Put your code here
	}

}
