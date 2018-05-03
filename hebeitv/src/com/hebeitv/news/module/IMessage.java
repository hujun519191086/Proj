package com.hebeitv.news.module;

import java.util.ArrayList;
import java.util.HashSet;

import com.hebeitv.news.bean.NewsInfoBean;
import com.hebeitv.news.net.OnPostCallBack;

public interface IMessage
{
    public void sendTestMessage(int messageId, int weaid, Class<? extends OnPostCallBack>... callBackClass);

    public void sendTestMessage(int messageId, int weaid, OnPostCallBack... callBack);

    public void sendRegistMessage(int messageId, String userName, String phone, String password, OnPostCallBack... callBack);

    public void sendLoginMessage(int messageId, String userName, String password, OnPostCallBack... callBack);

    public void loadMyReadMessage(int messageId, String page, OnPostCallBack... callBack);

    public void loadMyUploadMessage(int messageId, String page, OnPostCallBack... callBack);

    public void loadNewsListMessage(int messageId, Integer page, OnPostCallBack... callBack);

    public void onNewsItemClickMessage(int messageId, NewsInfoBean news, int listMessageId);

    /**
     * 需要判断是否登录,登录后才能评论
     */
    public void commentNews(String newsId, String content, OnPostCallBack... callBack);

    public void commentList(int eventId, String newsId, String page, OnPostCallBack... callBack);

    public void collectionNews(String newsId, OnPostCallBack... backs);

    void uploadFile(String title, ArrayList<String> filePaths, OnPostCallBack... backs);

    void deleteReadHistory(int eventId, String deleteNewsIds, OnPostCallBack... callBack);// 删除阅读历史.

    void deleteMyUploadMessage(int eventId, HashSet<Integer> deleteNewsIds, OnPostCallBack... callBack);// 删除我的上传

    void showEMP(int messageid, OnPostCallBack... backs);// 重要员工

    void loadPaikeCenter(int messageId, String page, OnPostCallBack... backs);// 拍客中心

    void search(int messageId, String page, String want, OnPostCallBack... backs);// 搜索

    public void hotWord(int messageId, OnPostCallBack... backs);// 加载热词
}
