package frame;

import java.util.TreeMap;

import util.WordUtil;

public class DataCenter {

	private static DataCenter dc = new DataCenter();
	
	private TreeMap<Long, Long> weixinPayTime =new TreeMap<Long, Long>();
	
	private TreeMap<String, String> loginMD5 = new TreeMap<String,String>();
	
	private DataCenter() {
	}
	
	public static DataCenter ins()
	{
		return dc;
	}
	
	
	/**
	 * 如果此订单操作为成功，保存订单号，再操作数据库。
	 * @param key  false为插入失败，不应该操作数据库
	 * @return
	 */
	public synchronized boolean insertPayTime(Long key)
	{
		if(key != null && key != 0)
		{
			if(weixinPayTime.containsKey(key))
			{
				return false;
			}
			weixinPayTime.put(key, System.currentTimeMillis());//这里需要操作：对value的检查。 超过一定时间给remove掉。  或者在特定时间remove掉一批
			return true;
		}
		
		return false;
		
	}
	public boolean updateLoginMD5(String pid,String md5){
		if(!WordUtil.isEmpty(pid))
		{
			
			loginMD5.put(pid, md5);
			return true;
		}
		
		return false;
	}
	public String getLoginMD5(String pid){
		return loginMD5.get(pid);
	}

}
