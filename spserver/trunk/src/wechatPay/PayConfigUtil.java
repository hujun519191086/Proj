package wechatPay;

public class PayConfigUtil {
	public static String APP_ID = "wxda46c6839f37699e";
	public static String APP_SECRET = "338f8c3b8b5f9e79c188c16c85e0a1d0 ";
	public static String MCH_ID = "1387834402"	;
	public static String API_KEY = "E94CVGSP38KTIRM5L7NFXWH60BD2YAOQ";
	//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	public static String CREATE_IP = "192.168.0.108";
	//接收微信通知回调异步地址，用于完成支付后的逻辑
	public static String NOTIFY_URL = "http://www.91htyg.com/servlet/Wei_xinNotifyServlet";
	//微信统一下单API
	public static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";	

}
