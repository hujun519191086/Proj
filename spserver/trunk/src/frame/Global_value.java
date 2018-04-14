package frame;

import java.sql.Timestamp;

public class Global_value {

	// E:\my_eclipse_2014_wrok\.metadata\.me_tcat7\webapps\SPserver
	public static String REAL_PATH;

	// /E:/my_eclipse_2014_wrok/.metadata/.me_tcat7/webapps/SPserver/WEB-INF/classes/
	public static String CLASS_PATH;
	public static String CONFIG_FILE_NAME;// ="/config"
	public static String GOODS_FILE_NAME;// =CONFIG_FILE_NAME+"/goods_config/"

	// /SPServer
	public static String CONTEXT_PATH;
	
	public static final String LOG4J_PROPERTIES_PATH="log4j.properties";
	// public static String SERVER_ADDRESS;//暂时没想好怎么用

	public static final int CHECK_GOODS_FILE_TIME = 10 * 1000;// 10s检查一次是否是有修改的文件

	public static final int GOODS_DATA_IN_OPEN_PRE_TIME = 60 * 10 * 1000;// 10分钟之内不能更改内容

	public static final int TABLE_NAME_SPLITE = 1024;// 表名分组间隔数

	public static final int DB_NAME_SPLITE = 1024;//随你用哪个 到时候删掉一个
	
	public static final int SOON_PUBLISH_PERCENT = 70 ;//到达多少百分比 即将揭晓 
	
	public static final long PUBLISH_TIME_SPLITE = 5 * 60 *1000;//每隔10分钟揭晓一次
	
	public static final int BASE_CODE = 10000001;//夺宝号码基础号码
	
	public static final int EXPIRATION_DAYS = 3;//最新揭晓数据表中记录信息在揭晓后几天过期
	
	public static final String UPDATE_RECENT_PULISH_TIME = "00:00:00";//定时更新最新揭晓数据 每天几点执行
	
	public static final boolean IS_DEBUG = true;

}
