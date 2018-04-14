package util;

import java.io.PrintWriter;
import java.util.Hashtable;

import enumPKG.EventIdList;

public class SpeedLimitUtil {

	private Hashtable<Long, Long> userLoadTime = new Hashtable<Long, Long>();

	private int visitorCount = 10;// 总共可以支持多少访客
	private int limitTime = 100000;// 限速1秒

	public SpeedLimitUtil() {
		for (long i = 0; i < visitorCount; i++) {
			userLoadTime.put(i, System.currentTimeMillis());
		}
	}

	public boolean inSpeedLimit(PrintWriter out, String pid) {
		long pidValue = Long.valueOf(pid);

		// 访客模式
		if (pidValue < visitorCount) {
			for (long i = 0; i < visitorCount; i++) {
				Long vTime = userLoadTime.get(i);

				if ((System.currentTimeMillis() - vTime) > limitTime) {

					return false;
				}
			}
			UserUtil.sendNormalErrorMsg(out,
					EventIdList.RESPONSE_MAIL_SEND_ERROR);
			return true;// 没找到可用访客位置. 不可访问.
		}

		Long time = userLoadTime.get(limitTime);
		if (time == null) {// 空的话, 加入限速
			userLoadTime.put(Long.valueOf(pid), System.currentTimeMillis());
			return false;
		}

		// 超过限速时间,取消限速
		if ((System.currentTimeMillis() - time) > limitTime) {
			userLoadTime.put(Long.valueOf(pid), System.currentTimeMillis());
			return false;
		}

		UserUtil.sendNormalErrorMsg(out, EventIdList.RESPONSE_MAIL_SEND_ERROR);
		return true;
	}
}
