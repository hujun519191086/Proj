package com.hebeitv.news.net;

import org.apache.http.Header;

public interface OnPostCallBack
{
    public <T> void onSuccess(int messageId, T bean);
    public <T> void onSuccess(int messageId, T bean,Object data);

    public void onSuccess(int messageId, String resultContent);
    public <T> void onSuccess(int messageId, String resultContent,Object data);

    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e);

    /**
     * 发送者要传递给接收者的内容保存
     */
    public void setSenderMsg(Object obj);

    public <T> T getSenderMsg();
}
