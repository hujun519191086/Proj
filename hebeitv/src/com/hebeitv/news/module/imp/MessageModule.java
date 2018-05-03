package com.hebeitv.news.module.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.text.format.DateFormat;
import android.util.SparseArray;

import com.MrYang.zhuoyu.utils.LogInfomation;
import com.hebeitv.news.bean.ComnetListBean;
import com.hebeitv.news.bean.HotWrodBean;
import com.hebeitv.news.bean.LoginBean;
import com.hebeitv.news.bean.MVPBean;
import com.hebeitv.news.bean.MyReadBean;
import com.hebeitv.news.bean.NewsInfoBean;
import com.hebeitv.news.bean.PaikeCenterBean;
import com.hebeitv.news.bean.SearchBean;
import com.hebeitv.news.demo.DemoResult;
import com.hebeitv.news.jni.Ccalculate;
import com.hebeitv.news.jni.JNISupport;
import com.hebeitv.news.module.ILogin;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.IModule;
import com.hebeitv.news.module.MessageId;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.net.EventSender;
import com.hebeitv.news.net.OnPostCallBack;
import com.hebeitv.news.view.upload.UploadPhotoActivity;
import com.loopj.android.http.RequestParams;

public class MessageModule extends IModule implements IMessage
{

    @Override
    public void sendTestMessage(int messageId, int weaid, Class<? extends OnPostCallBack>... callBackClass)
    {
        sendTestMessage(messageId, weaid, ModuleManager.getManager().getCallBack(callBackClass));
    }

    @Override
    public void sendTestMessage(int messageId, int weaid, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender(DemoResult.class);

        sender.put("app", "weather.today");
        sender.put("weaid", weaid + "");
        sender.put("appkey", "10003");
        sender.put("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4");
        sender.put("format", "json");

        sender.setPostCallBack(messageId, callBack);

        boolean sendSuccess = Ccalculate.sendMessage(sender);

        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    /*
     * (non-Javadoc)
     * @see com.hebeitv.news.module.IMessage#sendRegistMessage(int, java.lang.String, java.lang.String, java.lang.String, long) 验证通过
     */
    @Override
    public void sendRegistMessage(int messageId, String userName, String phone, String password, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("readerRegist.action");
        sender.put("rName", userName);
        sender.put("rPhone", phone);
        sender.put("rPass", password);
        sender.put("createTime", formatNowTime());
        sender.setPostCallBack(messageId, callBack);
        boolean sendSuccess = JNISupport.sendEvent("registInfos", sender, messageId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    /**
     * 验证通过
     */
    @Override
    public void sendLoginMessage(int messageId, String userName, String password, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("readerLogin.action", LoginBean.class);
        sender.put("rName", userName);
        sender.put("rPass", password);

        boolean sendSuccess = JNISupport.sendEvent("loginInfos", sender, messageId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    @Override
    public void loadMyReadMessage(int messageId, String page, OnPostCallBack... callBack)
    {

        // http://wangbo890505.imwork.net:9090/HebTVNewsMediaPlatform/app/myAll.action?paramInfos= {"rId":"1","limit":"10","page":"0","command":"record"}

        EventSender sender = EventSender.getSender("myAll.action", MyReadBean.class);
        sender.put("rId", getAccountId());
        sender.put("limit", 10 + "");
        sender.put("page", page + "");

        String command = (messageId == MessageId.MESSAGE_ID_MY_READ ? "record" : (messageId == MessageId.MESSAGE_ID_HOTREG ? "push" : "shoucang"));
        sender.put("command", command);

        boolean sendSuccess = JNISupport.sendEvent("paramInfos", sender, messageId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess + "  command:" + command);
        // {"recordList":[{"success":"false","info":"您暂时没有任何阅读记录"}]}
    }

    public String getAccountId()
    {
        return ModuleManager.getManager().getModule(ILogin.class).getLoginInfo().account_id;
    }

    @Override
    public void loadMyUploadMessage(int messageId, String page, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("myAll.action", MyReadBean.class);
        sender.put("rId", getAccountId());
        sender.put("limit", 10 + "");
        sender.put("page", page + "");

        String command = "myUpload";
        sender.put("command", command);

        boolean sendSuccess = JNISupport.sendEvent("paramInfos", sender, messageId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess + "  command:" + command);

    }

    // TIPS 待确认
    @Override
    public void deleteMyUploadMessage(int eventId, HashSet<Integer> deleteNewsIds, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("deleteMyUpload.action");

        String ids = "";
        Iterator<Integer> idsIterator = deleteNewsIds.iterator();
        while (idsIterator.hasNext())
        {
            Integer id = idsIterator.next();
            ids += id + ";";
        }
        sender.put("id", ids);
        sender.put("type", "vedio");

        boolean sendSuccess = JNISupport.sendEvent("//", sender, eventId, callBack);// TIPS 要確認
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    @Override
    public void loadNewsListMessage(int messageId, Integer page, OnPostCallBack... callBack)
    {
        SparseArray<String> newsMap = new SparseArray<String>();
        newsMap.put(MessageId.MESSAGE_ID_JING_XUAN, "jingxuan");
        newsMap.put(MessageId.MESSAGE_ID_LIAN_BO, "lianbo");
        newsMap.put(MessageId.MESSAGE_ID_WAN_JIAN, "wanjian");
        newsMap.put(MessageId.MESSAGE_ID_JIN_ZHAO, "zaojian");
        EventSender sender = EventSender.getSender("newsList.action", NewsInfoBean.class);
        sender.put("limit", 10 + "");
        sender.put("page", page + "");
        sender.put("type", newsMap.get(messageId));

        boolean sendSuccess = JNISupport.sendEvent("listInfos", sender, messageId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess + "  type:" + newsMap.get(messageId));
    }

    @Override
    public void onNewsItemClickMessage(int messageId, NewsInfoBean newsbean, int listMessageId)
    {

        SparseArray<String> newsMap = new SparseArray<String>();
        newsMap.put(MessageId.MESSAGE_ID_JING_XUAN, "jingxuan");
        newsMap.put(MessageId.MESSAGE_ID_LIAN_BO, "lianbo");
        newsMap.put(MessageId.MESSAGE_ID_WAN_JIAN, "wanjian");
        newsMap.put(MessageId.MESSAGE_ID_JIN_ZHAO, "zaojian");

        EventSender sender = EventSender.getSender("item.action", NewsInfoBean.class);

        sender.put("id", newsbean.getId());
        sender.put("rId", getAccountId());
        sender.put("type", newsMap.get(listMessageId));
        sender.put("command", "click");

        boolean sendSuccess = JNISupport.sendEvent("checkItemInfo", sender, messageId, null);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess + "  type:" + newsMap.get(messageId));
    }

    @Override
    public void commentNews(String newsId, String content, OnPostCallBack... callBack)
    {

        EventSender sender = EventSender.getSender("newsComment.action", NewsInfoBean.class);

        sender.put("id", newsId);
        sender.put("rId", getAccountId());
        sender.put("content", content);
        sender.put("time", formatNowTime());

        boolean sendSuccess = JNISupport.sendEvent("commentInfos", sender, 0, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    private CharSequence formatNowTime()
    {

        return DateFormat.format("yyyy-MM-dd", System.currentTimeMillis());
    }

    /**
     * 加载评论列表
     */
    @Override
    public void commentList(int eventId, String newsId, String page, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("commentList.action", ComnetListBean.class);

        sender.put("id", newsId);
        sender.put("page", page);
        sender.put("limit", 10);

        boolean sendSuccess = JNISupport.sendEvent("commentListInfo", sender, eventId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    /**
     * 刪除歷史閱讀
     * 
     * @param eventId
     * @param deleteNewsIds
     * @param callBack
     */
    @Override
    public void deleteReadHistory(int eventId, String deleteNewsIds, OnPostCallBack... callBack)
    {
        EventSender sender = EventSender.getSender("deleteFor.action");// 没有返回参数

        LogInfomation.d(TAG, "deleteNewsIds:" + deleteNewsIds);
        if (eventId == MessageId.MESSAGE_ID_MY_UPLOAD)
        {
            sender.put("id", deleteNewsIds);

        }
        else
        {
            sender.put("rId", getAccountId());
            sender.put("ids", deleteNewsIds);
            String command = (eventId == MessageId.MESSAGE_ID_MY_READ ? "record" : (eventId == MessageId.MESSAGE_ID_HOTREG ? "push" : (eventId == MessageId.MESSAGE_ID_COLLECTION ? "shoucang" : "myUpload")));
            sender.put("command", command);// push 推荐 ,shoucang收藏
        }

        // ArrayList<Integer> list = null;
        // Collection<Integer> valueSet = deleteNewsIds.values();
        //
        // Iterator<object> it = list.iterator();
        // while (it.hasNext()) {
        // if (valueSet.contains(it.next().newsId)) {//要和新闻id比对.
        // it.remove();
        // }
        // }

        boolean sendSuccess = JNISupport.sendEvent("deleteInfos", sender, eventId, callBack);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    /**
     * 收藏
     * 
     * @param newsId
     * @param backs
     */
    @Override
    public void collectionNews(String newsId, OnPostCallBack... backs)
    {
        EventSender sender = EventSender.getSender("itemCollection.action");// 没有返回参数
        sender.put("id", newsId);
        sender.put("rId", "" + getAccountId());
        sender.put("command", "shoucang");
        boolean sendSuccess = JNISupport.sendEvent("collectionParams", sender, 0, backs);
        LogInfomation.d(TAG, "sendSuccess:" + sendSuccess);
    }

    @Override
    public void uploadFile(String title, ArrayList<String> filePaths, OnPostCallBack... backs)
    {
        // HttpClient client = new DefaultHttpClient();// 获取client对象
        // HttpPost post = new HttpPost(URL + "?sign=" + mysign);// 使用post方式
        // post.setHeader("User-Agent", USER_AGENT);// 设置post消息头
        // StringEntity se = new StringEntity(postBody, "UTF-8");// 封装消息体
        // post.setEntity(se);// 把封装的消息体放入post方法中
        // HttpResponse response = client.execute(post);// 执行post请求

        filePaths.remove(UploadPhotoActivity.moreImageValue);// 去掉moreclick
        boolean isVideo = (filePaths.size() == 1 && filePaths.get(0).endsWith(".mp4"));

        EventSender sender = EventSender.getSender((isVideo ? "uploadVedio.action" : "uploadPic.action"));// 没有返回参数

        sender.put("uploadDate", formatNowTime());
        sender.put("title", title);
        sender.put("rId", getAccountId());
        sender.setJsonPostKey(isVideo ? "uploadVedioInfo" : "uploadPicInfo");

        RequestParams params = sender.getParams();

        try
        {
            for (int i = 0; i < filePaths.size(); i++)
            {
                String filePath = filePaths.get(i);

                String contentType = "";
                if (filePath.endsWith(".png"))
                {
                    contentType = "image/png";
                }
                else if (filePath.endsWith(".gif"))
                {
                    contentType = "image/gif";
                }
                else if (filePath.endsWith(".mp4"))
                {
                    contentType = "video/mp4";
                }
                else
                {
                    contentType = "image/jpeg";
                }
                params.put((isVideo ? "uploadVedio" : "uploadPic"), new File(filePath), contentType);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        sender.replaceNormalPost(params);
        JNISupport.sendEvent("", sender, 0, backs);
    }

    /**
     * 优秀员工
     */
    @Override
    public void showEMP(int messageid, OnPostCallBack... backs)
    {
        EventSender sender = EventSender.getSender("showEmp.action", MVPBean.class);
        JNISupport.sendEvent("", sender, messageid, backs);
    }

    @Override
    public void loadPaikeCenter(int messageId, String page, OnPostCallBack... backs)
    {
        EventSender sender = EventSender.getSender("uploadList.action", PaikeCenterBean.class);
        sender.put("limit", 10);
        sender.put("page", page);
        JNISupport.sendEvent("showList", sender, messageId, backs);
    }

    @Override
    public void search(int messageId, String page, String want, OnPostCallBack... backs)
    {
        EventSender sender = EventSender.getSender("searchItem.action", SearchBean.class);
        sender.put("limit", 10);
        sender.put("page", page);
        sender.put("want", want);
        JNISupport.sendEvent("searchInfos", sender, messageId, backs);
    }

    public void hotWord(int messageId, OnPostCallBack... backs)
    {
        EventSender sender = EventSender.getSender("searchItem.action", HotWrodBean.class);
        sender.put("time", formatNowTime());
        JNISupport.sendEvent("searchInfos", sender, messageId, backs);// TIPS 要确认
    }
}
