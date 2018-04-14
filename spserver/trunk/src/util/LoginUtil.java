package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import frame.DataCenter;

public class LoginUtil {
	public static boolean checkLogin(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		if(session != null){
			if(session.getAttribute("pid")!=null && session.getAttribute("loginMD5")!=null){
				
				String pid = session.getAttribute("pid").toString();
				String md5 = session.getAttribute("loginMD5").toString();
				if(DataCenter.ins().getLoginMD5(pid).equals(md5)){
					return true;
				}
			}
		}
		
		
		return false;
	}
}
