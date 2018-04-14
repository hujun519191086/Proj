package wechatPay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.bean.TradeNoItem;
import control.inter.DBErrorCode;
import frame.DataCenter;
import frame.SingleFactory;

public class Weixin_notify {
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		InputStream inputStream;  
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s ;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while((s = in.readLine()) != null){
			sb.append(s);
		}
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());
		String output = "";
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while(it.hasNext()){
			String parameter = (String)it.next();
			String parameterValue = m.get(parameter);
			String v = "";
			if(null != parameterValue){
				v = parameterValue.trim();
				output = output+parameter+":"+v+"-------";
			}
			packageParams.put(parameter, v);
			 
		}
		String key = PayConfigUtil.API_KEY;
		//签名验证
		boolean ree = PayCommonUtil.isTenpaySign("UTF-8", packageParams, key);
		System.out.println(ree);
		output = output+ree;
		FileOutputStream fs = new FileOutputStream(new File("D:\\test.txt"));
		PrintStream p = new PrintStream(fs);
		p.println(output);
		p.close();
		String resXML = "";
			if("SUCCESS".equals((String)packageParams.get("result_code"))){
				/*
				 * 获取订单号用于对当前订单是否有过处理进行校验
				 */
				int buycount;
				int period;
				Long gid;
				Long pid;
				TradeNoItem tradeNoItem = new TradeNoItem();
				String out_trade_no = (String)packageParams.get("out_trade_no");
				Long tradenoo = Long.valueOf(out_trade_no);
				String attach;
				//获取打包信息，包含pid，gid,buycount，以gid:buycount,gid:buycount,..,pid的形式存储
				
				SingleFactory sf = SingleFactory.ins();	
				tradeNoItem = sf.getTradeNodb().getTradeNoItem(tradenoo);
				pid = tradeNoItem.getPid();
				attach = tradeNoItem.getAttach();
				String [] convertAttach = attach.split(":|,");
				int Length = convertAttach.length;
				boolean hasRecord = sf.getBuyHistoryIns().selectBuyHistory(pid, tradenoo);
				if(hasRecord || !DataCenter.ins().insertPayTime(tradenoo)){
					System.out.println("当前订单号已处理:"+out_trade_no);
				}else{
					for(int i = 0; i < Length/2; i+=2){
						gid = Long.valueOf(convertAttach[i]);
						period = sf.getGoodsIns().getGoodsItem(gid).getPeriod();
						//加入buytime
						buycount = Integer.parseInt(convertAttach[i+1]);
						sf.getBuyHistoryIns().insertBuyHistory(gid, pid,tradenoo,buycount);
						DBErrorCode dbError = sf.getGoodsIns().changeGoodsData(gid,buycount);
						int pubStatus = (Integer)dbError.getTag();
						//参与人数到达总需人数
						if(pubStatus==2){
							sf.getBuyHistoryIns().insertPubHistory(gid,tradenoo,period);
							
							//重置号码池
							sf.getGoodsIns().updateCodeOrder(gid);
						}
						Logger logger = LoggerFactory.getLogger(getClass());
						logger.debug("update database!");
						
					}					
				}
				resXML = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
			}else { 
				resXML = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}			
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXML.getBytes());
			out.flush();
			out.close();
			
			
		}
		
	}
	

