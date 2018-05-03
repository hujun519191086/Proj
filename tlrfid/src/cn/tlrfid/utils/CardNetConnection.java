package cn.tlrfid.utils;

import java.util.HashMap;
import java.util.List;

import android.util.Pair;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.view.async.SendCardAsync;

public abstract class CardNetConnection {
	
	private static final String TAG = "CardNetConnection";
	private int requestCount = 0;
	private int connectionUrlAsyncCount = 0;
	
	private boolean cardRequest = false;// 未在读卡
	
	public void setndCard(BaseActivity activity, List<String> cardIdList, String url, Class<? extends BaseBean> bbClazz) {
		LogUtil.d(TAG, "sendCard:" + cardIdList);
		if ((cardIdList == null || cardIdList.size() <= 0) && !cardRequest) {
			SystemNotification.showToast(activity, "未读取到卡信息!");
			return;
		}//09-03 23:57:55.518: D/UniversalEngineImpl(6432): url:http://182.92.76.78:8080/spms/service/queryScheduleByTag.action?tagCode=FA5873100001022013010094

		if (!cardRequest) {// 如果现在没有在读卡
			requestCount = 0;
			connectionUrlAsyncCount = 0;
			cardRequest = true;
		}
		
		for (int i = 0; i < cardIdList.size(); i++) {
			if (!ReadCardSche.getInstance().putInMap(cardIdList.get(i))) {
				new SendCardAsync(activity, this).execute(url, bbClazz, cardIdList.get(i));
				connectionUrlAsyncCount++;
				LogUtil.d(TAG, "send a new async:"+cardIdList.get(i));
				// } else {
				// requestCount++;
				// if (requestCount >= connectionUrlAsyncCount) {
				// onCardReadOver();
				// }
			}
		}
	}
	
	public void clearMap() {
		ReadCardSche.getInstance().clearMap();
	}
	
	/**
	 * 是否正在联网读取
	 * 
	 * @return true为正在读卡
	 */
	public boolean onCardRequest() {
		return cardRequest;
	}
	
	public final void onCardRead() {
		requestCount++;
		if (requestCount >= connectionUrlAsyncCount) {
			cardRequest = false;
			onCardReadOver(getRequestMap());
		}
	}
	
	/**
	 * list中. 一个item读取完毕之后的回调
	 * 
	 * @param key
	 * @param value
	 */
	public abstract void onCardReadOne(BaseBean value);
	
	/**
	 * 几个卡真正的读取完毕了!
	 */
	public abstract void
    onCardReadOver(HashMap<String, Pair<Boolean, String>> map);
	
	public HashMap<String, Pair<Boolean, String>> getRequestMap() {
		return ReadCardSche.getInstance().getMap();
	}
}
