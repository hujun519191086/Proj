package util;

import java.sql.Timestamp;

import frame.Global_value;

public class PublishUtil {
	public static int getLuckyCode(long gid,int period){
		return 100035;
	}
	public static Timestamp getPublishTime(){
		long pTime = System.currentTimeMillis()+Global_value.PUBLISH_TIME_SPLITE-(System.currentTimeMillis()%Global_value.PUBLISH_TIME_SPLITE);
		Timestamp publish_time = new Timestamp(pTime);
		return publish_time;
	}
}
