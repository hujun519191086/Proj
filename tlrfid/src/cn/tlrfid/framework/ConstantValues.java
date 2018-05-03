package cn.tlrfid.framework;

import cn.tlrfid.utils.LogUtil;

/**
 * @author Administrator
 * 
 */
public class ConstantValues {
	public static final String[] PROJECT_MANAGER_LEFT = new String[] { "工程信息", "一级网络计划", "横道图", "新增履历", "履历一览" }; // 工程管理
	public static final String[] SAEFE_LEFT = new String[] { "工程信息", "新建安全检查", "安全检查一览", "待整改安全检查记录" };// 安全管理
	public static final String[] QUALITY_MANAGER_LEFT = new String[] { "工程信息", "新建质量检查", "质量管理一览", "待整改质量检查记录" }; // 质量管理
	public static final String[] WAREHOUSE_MANAGER_LEFT = new String[] { "设备一览", "设备详情", "养护提醒" };// 仓库管理
	
	public static boolean rfid_state = false;
	public static final String ACTION_BAR_NAME = "action_bar";
	
	public static final String PAGE_TITLE_INTENT_EXTRA = "title_extra";// actionbar不同字的改变.
	public static final int CONNECTION_TIMEOUT = 10000;
	public static final String ERR_MESSAGE = "errMessage";
	public static final String STATUS = "state";
	public static final int STATUS_CODE_SUCCESS = 1;
	public static final int STATUS_CODE_FAULT = 0;
	
	public static String CLIENT_URL_PRE = "http://182.92.76.78:8080/spms";
	// public static String CLIENT_URL_PRE = "http://192.168.1.100:8080/spms";
	
	public static final int CONNECTION_READ_TIMEOUT = 5000;
	
	public static final String LOGIN_NAME = "loginName";
	public static final String LOGIN_PASSWORD = "password";
	private static final String TAG = "ConstantValues";
	public static String PROJECT_CODE = "projectCode";
	
	public static void initClientPre(String CLIENT_PRE) {
		CLIENT_URL_PRE = CLIENT_PRE;
		
		LOGIN_URL = CLIENT_URL_PRE + "/service/login.action";
		CURRICULUM_VITAE_QUERY = CLIENT_URL_PRE + "/service/queryProjectRecord.action";
		QUERY_CHECK_RECORD = CLIENT_URL_PRE + "/service/queryQualityCheckRecord.action";
		SELF_CURRICULUM_VITATE_QUERY = CLIENT_URL_PRE + "/service/querySecurityCheckRecord.action";
		LOAD_PROGRESSMANAGER = CLIENT_URL_PRE + "/service/loadProjectInfo.action";
		LOAD_PROGRESS_QUERY = CLIENT_URL_PRE + "/service/queryProjectSchedule.action";
		UP_LOAD_IMAGE = CLIENT_URL_PRE + "/service/upload.action";
		CURRICULUM_QUERY = CLIENT_URL_PRE + "/service/queryScheduleByTag.action?tagCode=";
		QUERYPERSON = CLIENT_URL_PRE + "/service/queryPersonByPersonTagId.action?personTagId=";
		QUERYPROJECT = CLIENT_URL_PRE + "/service/queryScheduleByTag.action?tagCode=";
		WRITE = CLIENT_URL_PRE + "/service/saveProjectRecord.action";
		
		SAFEITEMBYID = CLIENT_URL_PRE + "/service/querySecurityEntryBySchedule.action";
		QUALITYITEMBYID = CLIENT_URL_PRE + "/service/queryQualityEntryBySchedule.action";
		
		LogUtil.d(TAG, "CHECK_RECODE before :" + CHECK_RECODE);
		CHECK_RECODE = CLIENT_PRE + "/service/queryNeedOverhaulSecurityCheckRecord.action";
		LogUtil.d(TAG, "CHECK_RECODE after :" + CHECK_RECODE);
		QUALITY_NEED_CHECKED = CLIENT_URL_PRE + "/service/queryNeedOverhaulQualityCheckRecord.action";
		SAVE_SELF_CURR = CLIENT_URL_PRE + "/service/saveSecurityCheckRecord.action";
		SAVE_QUALITY_CURR = CLIENT_URL_PRE + "/service/saveQualityCheckRecord.action";
		MODFY_SELF_CURR = CLIENT_URL_PRE + "/service/saveSecurityOverhaulRecord.action";
		
		MODFY_QUALITY_CURR = CLIENT_URL_PRE + "/service/saveQualityOverhaul.action";
		ITEM_SELF_CURR = CLIENT_URL_PRE + "/service/queryNeedOverhaulSecurityEntry.action";
		ITEM_QUALITY_CURR = CLIENT_URL_PRE + "/service/queryNeedOverhaulQualityEntry.action";
		WAREHOUSE_MANAGER_URL = CLIENT_URL_PRE + "/service/queryDevice.action";
		MAINTENANCE_REMINDER = CLIENT_URL_PRE + "/service/queryNeedConserve.action";
	}
	
	public static String MAINTENANCE_REMINDER = CLIENT_URL_PRE + "/service/queryNeedConserve.action";
	public static String WAREHOUSE_MANAGER_URL = CLIENT_URL_PRE + "/service/queryDevice.action";
	public static String LOGIN_URL = CLIENT_URL_PRE + "/service/login.action";
	public static String CURRICULUM_VITAE_QUERY = CLIENT_URL_PRE + "/service/queryProjectRecord.action";
	public static String QUERY_CHECK_RECORD = CLIENT_URL_PRE + "/service/queryQualityCheckRecord.action";
	public static String SELF_CURRICULUM_VITATE_QUERY = CLIENT_URL_PRE + "/service/querySecurityCheckRecord.action";
	public static String LOAD_PROGRESSMANAGER = CLIENT_URL_PRE + "/service/loadProjectInfo.action";
	public static String LOAD_PROGRESS_QUERY = CLIENT_URL_PRE + "/service/queryProjectSchedule.action";
	public static String UP_LOAD_IMAGE = CLIENT_URL_PRE + "/service/upload.action";
	public static String CURRICULUM_QUERY = CLIENT_URL_PRE + "/service/queryScheduleByTag.action?tagCode=";
	public static String QUERYPERSON = CLIENT_URL_PRE + "/service/queryPersonByPersonTagId.action?personTagId=";
	public static String QUERYPROJECT = CLIENT_URL_PRE + "/service/queryScheduleByTag.action?tagCode=";
	public static String WRITE = CLIENT_URL_PRE + "/service/saveProjectRecord.action";
	
	// 根据项目id查找安全条目
	public static String SAFEITEMBYID = CLIENT_URL_PRE + "/service/querySecurityEntryBySchedule.action";
	// 待整改安全检查记录
	public static String CHECK_RECODE = CLIENT_URL_PRE + "/service/queryNeedOverhaulSecurityCheckRecord.action";
	
	// 根据项目id查找质量条目
	public static String QUALITYITEMBYID = CLIENT_URL_PRE + "/service/queryQualityEntryBySchedule.action";
	// 待整改质量检查记录
	public static String QUALITY_NEED_CHECKED = CLIENT_URL_PRE + "/service/queryNeedOverhaulQualityCheckRecord.action";
	
	public static String SAVE_SELF_CURR = CLIENT_URL_PRE + "/service/saveSecurityCheckRecord.action";
	public static String SAVE_QUALITY_CURR = CLIENT_URL_PRE + "/service/saveQualityCheckRecord.action";
	public static String MODFY_SELF_CURR = CLIENT_URL_PRE + "/service/saveSecurityOverhaulRecord.action";
	public static String MODFY_QUALITY_CURR = CLIENT_URL_PRE + "/service/saveQualityOverhaul.action";
	
	public static String ITEM_SELF_CURR = CLIENT_URL_PRE + "/service/queryNeedOverhaulSecurityEntry.action";
	public static String ITEM_QUALITY_CURR = CLIENT_URL_PRE + "/service/queryNeedOverhaulQualityEntry.action";
	
	public static void toAfterString() {
		
	}
}
