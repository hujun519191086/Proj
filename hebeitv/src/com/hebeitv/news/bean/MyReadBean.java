package com.hebeitv.news.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class MyReadBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @SerializedName("pId")
    private String pid;
    @SerializedName("title")
    // 我的阅读和我的上传都用到
    private String title;// 我的阅读和我的上传都用到
    @SerializedName("pPath")
    private String pic;
    @SerializedName("url")
    private String url;

    // 视频的
    @SerializedName("userId")
    private String userId;
    @SerializedName("vId")
    private String vId;
    @SerializedName("vTime")
    private String vTime;
    @SerializedName("vPath")
    private String vPath;
    @SerializedName("prePath")
    private String prePath;

    public String getPrePath()
    {
        return prePath;
    }

    public void setPrePath(String prePath)
    {
        this.prePath = prePath;
    }

    public String getId()
    {
        return pid;
    }

    public void setId(String id)
    {
        this.pid = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getvId()
    {
        return vId;
    }

    public void setvId(String vId)
    {
        this.vId = vId;
    }

    public String getvTime()
    {
        return vTime;
    }

    public void setvTime(String vTime)
    {
        this.vTime = vTime;
    }

    public String getvPath()
    {
        return vPath;
    }

    public void setvPath(String vPath)
    {
        this.vPath = vPath;
    }

    @Override
    public String toString()
    {
        return "MyReadBean [id=" + pid + ", title=" + title + ", pic=" + pic + ", url=" + url + ", userId=" + userId + ", vId=" + vId + ", vTime=" + vTime + ", vPath=" + vPath + ", prePath=" + prePath + "]";
    }

}
