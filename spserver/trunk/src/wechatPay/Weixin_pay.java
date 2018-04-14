package wechatPay;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Weixin_pay {
	public String weixin_pay(String out_trade_no, String total_fee,String body) throws Exception { 
		/*
		 * 打包上传信息
		 */		
	    String appid = PayConfigUtil.APP_ID;  // appid
	    String appsecret = PayConfigUtil.APP_SECRET; // appsecret
	    String mch_id = PayConfigUtil.MCH_ID; // 商业号
	    String key = PayConfigUtil.API_KEY; // key
	    String currTime = PayCommonUtil.getCurrTime();
	    String strTime = currTime.substring(8, currTime.length());
	    String strRandom = PayCommonUtil.buildRandom(4) + "";
	    String nonce_str = strTime + strRandom;	    
	   
	    // 获取发起电脑 ip
	    String spbill_create_ip = PayConfigUtil.CREATE_IP;
	    // 回调接口 
	    String notify_url = PayConfigUtil.NOTIFY_URL;
	    //微信支付方式，网页扫码采用NATIVE
	    String trade_type = "NATIVE";	    
	    SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
	    packageParams.put("appid", appid);
	    packageParams.put("mch_id", mch_id);
	    packageParams.put("nonce_str", nonce_str);
	    packageParams.put("body", body);
	    packageParams.put("out_trade_no", out_trade_no);
	    packageParams.put("total_fee", total_fee);
	    packageParams.put("spbill_create_ip", spbill_create_ip);
	    packageParams.put("notify_url", notify_url);
	    packageParams.put("trade_type", trade_type);
	    
	    String sign = PayCommonUtil.createSign("UTF-8", packageParams,key);
	    //通过sign进行签名每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，
	    //API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证  
	    packageParams.put("sign", sign);
	    //解析成xml
	    String requestXML = PayCommonUtil.getRequestXml(packageParams);
	    System.out.println("打印请求xml"+requestXML);

	    //传入要请求的api,以及打包加密好的数据
	    String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);	    
	    Map map = XMLUtil.doXMLParse(resXml);
	    //获取微信返回值  return_code 为success 成功
	    //返回return_msg 错误信息签名失败
	    String return_code = (String) map.get("return_code"); 
	    String prepay_id = (String) map.get("prepay_id");
	    String return_msg = (String) map.get("return_msg");
	    String urlCode = (String) map.get("code_url"); //返回的二维码参数
	    String error = (String) map.get("error_code");
	    System.out.println("同意下单接口生成的二维码："+urlCode);
	    System.out.println("打印成功信息"+return_code);
	    System.out.println("打印错误信息"+return_msg);
	    System.out.println(error);
		return urlCode;
	}
}

