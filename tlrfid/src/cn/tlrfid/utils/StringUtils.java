package cn.tlrfid.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.util.JsonReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.tlrfid.bean.CurriculumVitaeBean;
import cn.tlrfid.bean.ProjectRecoder;
import cn.tlrfid.bean.ScheduleProject;
import cn.tlrfid.bean.SecurityCheckEntry;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
import cn.tlrfid.bean.SelfProjectRecoder;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.ConstantValues;

public class StringUtils {
	
	private static final String TAG = "StringUtils";
	
	/**
	 * 字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (null == string || "null".equalsIgnoreCase(string)) {
			return true;
		}
		if (0 == string.trim().length() || 0 == string.replace(" ", "").length()) {
			return true;
		}
		
		return false;
	}
	
	public static SelfCurriculumViateQueryBean parseSelfCurriculum(String message) {
		SelfCurriculumViateQueryBean selfCurriculum = new SelfCurriculumViateQueryBean();
		if (!isEmpty(message)) {
			try {
				JSONObject json = new JSONObject(message);
				selfCurriculum.state = json.getInt("state");
				if ((selfCurriculum.state) != 1) {
					selfCurriculum.errMessage = json.getString("errMessage");
				} else {
					JSONObject obj = json.getJSONObject("recordPage");
					selfCurriculum.totalCount = obj.getInt("totalCount");
					selfCurriculum.totalPage = obj.getInt("totalPage");
					JSONArray arr = obj.getJSONArray("items");
					selfCurriculum.mItemList = new ArrayList<SelfProjectRecoder>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject itemObj = arr.getJSONObject(i);
						SelfProjectRecoder recoder = JSON.parseObject(itemObj.toString(), SelfProjectRecoder.class);
						
						JSONArray arrInner = itemObj.getJSONArray("entryList");
						recoder.mSecurityList = new ArrayList<SecurityCheckEntry>();
						for (int j = 0; j < arrInner.length(); j++) {
							JSONObject itemObjInner = arrInner.getJSONObject(j);
							
							JSONObject contentObj = itemObjInner.getJSONObject("securityCheckEntry");
							
							
							SecurityCheckEntry entry = JSON
									.parseObject(contentObj.toString(), SecurityCheckEntry.class);
							entry.result = itemObjInner.getInt("result");
							recoder.mSecurityList.add(entry);
						}
						selfCurriculum.mItemList.add(recoder);
					}
					selfCurriculum.projectNameList = new ArrayList<ScheduleProject>();
					JSONArray nameObj = json.getJSONArray("scheduleList");
					for (int i = 0; i < nameObj.length(); i++) {
						ScheduleProject pro = new ScheduleProject();
						JSONObject itemObj = nameObj.getJSONObject(i);
						pro.scheduleId = itemObj.getInt("id");
						pro.name = itemObj.getString("name");
						selfCurriculum.projectNameList.add(pro);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return selfCurriculum;
	}
	
	public static SelfCurriculumViateQueryBean parseQualityCurriculum(String message) {
		SelfCurriculumViateQueryBean selfCurriculum = new SelfCurriculumViateQueryBean();
		if (!isEmpty(message)) {
			try {
				JSONObject json1 = new JSONObject(message);
				selfCurriculum.state = json1.getInt("state");
				if ((selfCurriculum.state) != 1) {
					selfCurriculum.errMessage = json1.getString("errMessage");
				} else {
					JSONObject obj = json1.getJSONObject("recordPage");
					selfCurriculum.totalCount = obj.getInt("totalCount");
					selfCurriculum.totalPage = obj.getInt("totalPage");
					JSONArray arr = obj.getJSONArray("items");
					selfCurriculum.mItemList = new ArrayList<SelfProjectRecoder>();
					for (int i = 0; i < arr.length(); i++) {
						// SelfProjectRecoder recoder = new SelfProjectRecoder();
						JSONObject itemObj = arr.getJSONObject(i);
						SelfProjectRecoder recoder = JSON.parseObject(itemObj.toString(), SelfProjectRecoder.class);
						
						JSONArray arrInner = itemObj.getJSONArray("entryList");
						recoder.mSecurityList = new ArrayList<SecurityCheckEntry>();
						for (int j = 0; j < arrInner.length(); j++) {
							// SecurityCheckEntry entry = new SecurityCheckEntry();
							JSONObject itemObjInner = arrInner.getJSONObject(j);
							// entry.result = itemObjInner.getInt("result");
							
							JSONObject contentObj = itemObjInner.getJSONObject("qualityCheckEntry");
							
							
							SecurityCheckEntry entry = JSON
									.parseObject(contentObj.toString(), SecurityCheckEntry.class);
							entry.result = itemObjInner.getInt("result");
							recoder.mSecurityList.add(entry);
							
						}
						selfCurriculum.mItemList.add(recoder);
					}
					selfCurriculum.projectNameList = new ArrayList<ScheduleProject>();
					JSONArray nameObj = json1.getJSONArray("scheduleList");
					for (int i = 0; i < nameObj.length(); i++) {
						ScheduleProject pro = new ScheduleProject();
						JSONObject itemObj = nameObj.getJSONObject(i);
						pro.scheduleId = itemObj.getInt("id");
						pro.name = itemObj.getString("name");
						selfCurriculum.projectNameList.add(pro);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return selfCurriculum;
	}
	
	public static CurriculumVitaeBean parseCUrriculumInfo(String message) {
		
		CurriculumVitaeBean currVitaeInfo = new CurriculumVitaeBean();
		if (!isEmpty(message)) {
			try {
				JSONObject json = new JSONObject(message);
				currVitaeInfo.state = json.getInt("state");
				if ((currVitaeInfo.state) != 1) {
					currVitaeInfo.errMessage = json.getString("errMessage");
				} else {
					JSONObject obj = json.getJSONObject("projectRecordPage");
					currVitaeInfo.totalCount = obj.getInt("totalCount");
					currVitaeInfo.totalPage = obj.getInt("totalPage");
					JSONArray arr = obj.getJSONArray("items");
					currVitaeInfo.mItemList = new ArrayList<ProjectRecoder>();
					for (int i = 0; i < arr.length(); i++) {
//						ProjectRecoder recoder = new ProjectRecoder();
						JSONObject itemObj = arr.getJSONObject(i);
//						recoder.finishRate = itemObj.getInt("finishRate");
//						recoder.id = itemObj.getInt("id");
//						recoder.inspectionPerson = itemObj.getString("inspectionPerson");
//						recoder.inspectionTime = itemObj.getString("inspectionTime");
//						recoder.person = itemObj.getString("person");
//						recoder.picPath = itemObj.getString("picPath");
//						recoder.remark = itemObj.getString("remark");
//						recoder.scheduleId = itemObj.getInt("scheduleId");
//						recoder.tagCode = itemObj.getString("tagCode");
//						recoder.inspectionPersonName = itemObj.getString("inspectionPersonName");
//						recoder.scheduleName = itemObj.getString("scheduleName");
						
						ProjectRecoder recoder=JSON.parseObject(itemObj.toString(),ProjectRecoder.class);
						
						currVitaeInfo.mItemList.add(recoder);
					}
					currVitaeInfo.projectNameList = new ArrayList<ScheduleProject>();
					JSONArray nameObj = json.getJSONArray("scheduleList");
					for (int i = 0; i < nameObj.length(); i++) {
						ScheduleProject pro = new ScheduleProject();
						JSONObject itemObj = nameObj.getJSONObject(i);
						pro.scheduleId = itemObj.getInt("id");
						pro.name = itemObj.getString("name");
						currVitaeInfo.projectNameList.add(pro);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return currVitaeInfo;
	}
	
	public static <T> T parseString(String json, Class<? extends BaseBean> clazz) {
		Map<String, Object> map = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
		});
		
		LogUtil.d(TAG, "cnc" + map + "jsonIntType:" + map.get(ConstantValues.STATUS));
		String infoTitle = InstanceFactory.getInfoTitle(clazz);
		final Object obj = map.get(infoTitle);
		
		int errorcode = Integer.valueOf((Integer) map.get(ConstantValues.STATUS));
		if (errorcode == ConstantValues.STATUS_CODE_FAULT) {
			return null;
		}
		
		if (ignoreJson == null) {
			ignoreJson = new HashMap<String, Object>();
		} else {
			ignoreJson.clear();
		}
		Set<String> sets = map.keySet();
		
		for (String set : sets) {
			if (!set.equals(infoTitle) && !set.equals(ConstantValues.STATUS)) {
				ignoreJson.put(set, map.get(set));
			}
		}
		LogUtil.d(TAG, "obj:" + obj);
		return getBean(obj, clazz);
	}
	
	public HashMap<String, Object> getIgnoreJson() {
		if (ignoreJson == null) {
			ignoreJson = new HashMap<String, Object>();
		}
		
		return ignoreJson;
	}
	
	private static HashMap<String, Object> ignoreJson;
	
	public static <T> T getBean(Object json, Class<? extends BaseBean> clazz) {
		
		T t = null;
		try {
			if (json instanceof JSONArray) {
				t = (T) JSON.parseArray(JSON.toJSONString(json), clazz);
			} else {
				t = (T) JSON.parseObject(JSON.toJSONString(json), clazz);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	private int parseInt(Integer inter) {
		
		if (inter == null) {
			return inter;
		}
		return 0;
	}
}
