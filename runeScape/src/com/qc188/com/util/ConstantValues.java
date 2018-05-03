package com.qc188.com.util;

import android.content.SharedPreferences;

import com.qc188.com.R;

public class ConstantValues
{
    public static final String STATUS = "status";
    public static final String FROM_FLAG = "fromFlag";
    public static final String URL_TITLE = "http://www.qc188.com";
    public static final String HOME_ITEM_URL = URL_TITLE + "/app/cwcs.asp"; // 主页最新连接
    public static final String SORT_ITEM_URL = URL_TITLE + "/app/brand.asp"; // 搜索链接
    public static final String HOME_ADV_URL = URL_TITLE + "/app/adv.asp"; // 广告条连接
    public static final String CONTENT_URL = URL_TITLE + "/app/cwcsv.asp?msg_id="; // 只要add进id就行
    public static final String SEARCH_CONTENT = URL_TITLE + "/app/xilie.asp?brand_id="; // search跳转到详细内容
    public static final String SEARCH_ON_SALE_CONTENT = URL_TITLE + "/app/xilie_onsale.asp?brand_id="; // search跳转到详细内容
    public static final String CAR_TYPE_URL = URL_TITLE + "/app/carv.asp?id="; // 车型详情.
    public static final String CAR_TYPE_OPTION_URL = URL_TITLE + "/app/option.asp?id="; // 车型中，参数
    public static final String CONTENT_IMAGE_FORWORD = "http://img.qc188.com"; // 详情中起头部分
    public static final String CONTENT_ = "http://img.qc188.com"; // 详情中起头部分
    public static final String MOBILE_URL = "http://m.qc188.com"; // 手机网页的网址
    public static final String MSG_CONTENT_BUNDLE = "result_msgContent"; // home跳转到Content的时候传递的key
    public static String FILE_PATH = "";
    public static String CACHE_PATH = "";
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int CONNECTION_READ_TIMEOUT = 15000;
    public static boolean NO_PIC = false;
    public static final String TO_CONTENT_TAG = "msgId";

    public static SharedPreferences homeUrlPreference;
    public static final String HOME_PREFERENCE_NAME = "home_preference";

    public static final String HOME_NEWS_URL = URL_TITLE + "/app/cwcs.asp"; // 为新闻
    public static final String HOME_EVALUATION_URL = URL_TITLE + "/app/pcsj.asp"; // 为评测
                                                                                  // 校验完成
    public static final String HOME_GUID_URL = URL_TITLE + "/app/ztdg.asp"; // 为导购
    public static final String HOME_KNOWAGE_URL = URL_TITLE + "/app/qczs.asp"; // 为知识
                                                                               // 校验完成
    public static final String HOME_PRAISE_URL = URL_TITLE + "/app/czsc.asp"; // 口碑
                                                                              // 校验完成
    public static final String HOME_duibi_URL = URL_TITLE + "/app/dbdg.asp"; // 对比

    // http://www.qc188.com/app/czscv.asp?msg_id=109630
    public static final String HOME_NEWS_URL_Content = URL_TITLE + "/app/cwcsv.asp?msg_id="; // 新闻内容
    public static final String HOME_EVALUATION_URL_Content = URL_TITLE + "/app/pcsjv.asp?msg_id="; // 为评测内容
    public static final String HOME_duibi_URL_Content = URL_TITLE + "/app/dbdgv.asp?msg_id="; // 对比内容
    public static final String HOME_GUID_URL_Content = URL_TITLE + "/app/ztdgv.asp?msg_id="; // 导购内容//ztdgv
    public static final String HOME_KNOWAGE_URL_Content = URL_TITLE + "/app/qczsv.asp?msg_id="; // 知识内容
    public static final String HOME_PRAISE_URL_Content = URL_TITLE + "/app/czscv.asp?msg_id="; // 口碑内容

    public static final String SPLICT = "#";
    public static final String FROMHOME = CONTENT_URL;
    public static final String FROM_ADV = HOME_ADV_URL; // 新闻
    public static final String FROMNEW = HOME_NEWS_URL_Content; // 新闻
    public static final String FROMGUID = HOME_GUID_URL_Content; // 导购
    public static final String FROMKNOWAGE = HOME_KNOWAGE_URL_Content; // 知识
    public static final String FROMPRAISE = HOME_PRAISE_URL_Content; // 口碑
    public static final String FROMEVALUATION = HOME_EVALUATION_URL_Content; // 评测
    public static final String FROMEduibi = HOME_duibi_URL_Content; // 对比
    public static final String FROMEWHERE = "fromeWhere";

    public static final String TITLE_NAME = "title";

    public static int DEFAULT_DRAWABLE = R.drawable.load;
    public static int DEFAULT_DRAWABLE_A = R.drawable.load_a;

    // 二期－－－－－
    public static final String BRAND_DETAIL = URL_TITLE + "/app/xiliev.asp?id=";// 详情界面
    public static final String BRAND_DETAIL_KOUBEI = URL_TITLE + "/app/owner.asp?id=";// 详情口碑
    public static final String BRAND_DETAIL_TESTING = URL_TITLE + "/app/testing.asp?id=";// 详情评测
    public static final String BRAND_DETAIL_COMPARE = URL_TITLE + "/app/compare.asp?id=";// 详情导购
    public static final String BRAND_DETAIL_PIC = URL_TITLE + "/app/photo.asp?id=";// 详情图片
    public static final String BRAND_CARlIST_DETAIL_PIC = URL_TITLE + "/app/carphoto.asp?id=";// 详情图片

    public static final int SEARCH_SELECT_COLOR = 0xFFF2F2F2; // 搜索选中颜色
    public static boolean LOCK = false;

    // end

    public static class STATUS
    {
        public static final int FAULT = -1; // 失败
        public static final int SUCCESS = 0; // 成功
        public static final int SEVER_ERROR = 1; // 服务器出错
        public static final int DO_SOMETHING_ERROR = 2; // 操作失败
        public static final int SERVER_BUSY = 3; // 服务器繁忙
        public static final int DATA_ERROR = 4; // 数据错误
        public static final int OTHER_ERROR = 5; // 其他错误

        public static final String[] STATUS_STR_VALUES = { "失败", "成功", "服务器出错", "操作失败", "服务器繁忙", "数据错误", "其他错误" };

        public static final int UNKONW_ERROR_INDEX = 6;
    }
}
