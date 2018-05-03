package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class MyUpLoadBean
{
    @SerializedName("title")
    private String title;
    @SerializedName("userId")
    private String userId;
    @SerializedName("vId")
    private String vId;
    @SerializedName("vTime")
    private String vTime;
    @SerializedName("vPath")
    private String vPath;
    @Override
    public String toString()
    {
        return "MyUpLoadBean [title=" + title + ", userId=" + userId + ", vId=" + vId + ", vTime=" + vTime + ", vPath=" + vPath + "]";
    }
    
    
    
}
