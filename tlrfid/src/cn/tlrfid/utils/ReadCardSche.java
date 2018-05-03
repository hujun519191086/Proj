package cn.tlrfid.utils;

import java.util.HashMap;

import android.util.Pair;

public class ReadCardSche {
	private static class GetThisInstance {
		private static final ReadCardSche rcs = new ReadCardSche();
	}
	
	private HashMap<String, Pair<Boolean, String>> cardMsgMap;
	
	public void clearMap() {
		if (cardMsgMap != null) {
			cardMsgMap.clear();
		}
	}
	
	/**
	 * 将读取到的信息放入.
	 * 
	 * @param idCard
	 * @return false的话就是可以联网. true代表已经联网完成
	 */
	public boolean putInMap(String idCard) {
		if (cardMsgMap == null) {
			cardMsgMap = new HashMap<String, Pair<Boolean, String>>();
		}
		
		Pair<Boolean, String> pair = cardMsgMap.get(idCard);
		if (pair != null) {
			return true;
		}
		cardMsgMap.put(idCard, new Pair<Boolean, String>(false, ""));
		return false;
	}
	
	public void changeKeyState(String idCard) {
		Pair<Boolean, String> pair = cardMsgMap.get(idCard);
		String second = pair.second;
		cardMsgMap.put(idCard, new Pair<Boolean, String>(true, second));
	}
	
	public HashMap<String, Pair<Boolean, String>> getMap() {
		return cardMsgMap;
	}
	
	public static ReadCardSche getInstance() {
		return GetThisInstance.rcs;
	}
	
	private ReadCardSche() {
	}
}
