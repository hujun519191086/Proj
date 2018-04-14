package control.inter;

import java.util.List;

import control.bean.Person;

/**加密解密查询创建用户啊的接口
 * @author android
 *
 */
public interface WebInterface {
	public String enPwd(String pwd);// 加密
	public String dePwd(String pwd);// 解密

	public DBErrorCode updateVerificationToUser(String  mailBox,String verificationCode,Person person);//保存验证码
	public String getVerfication(String mailBox);//邮箱获取验证码
	public DBErrorCode  updatePerson(String  mailBox,String verificationCode,String pwd);// 创建用户 参数邮箱，昵称，加了密的密码
	
	public int getId4MailBox(String mailBox,String pwd);// 根据邮箱密码生成我们使用的ID
	public DBErrorCode queryInfo4Id(int id);// 根据我们的id查询用户的所有信息
	
	public DBErrorCode queryInfo4Mail_PWD(String mailBox,String enPwd);// 用户登录
	
	public DataPair<DBErrorCode,Person> queryInfo4Mail(String mailBox);//检查邮箱的状态.是否已经被注册
	
	
	public void updateDeviceId(int userId, String deviceId);//更新设备id
	
}
