package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class PaikeCenterBean
{
    @SerializedName("pId")
    private String pId;
    @SerializedName("title")
    private String title;
    @SerializedName("pPath")
    private String pPath;
    @SerializedName("userId")
    private String userId;
    @SerializedName("pTime")
    private String pTime;

    public String getpId()
    {
        return pId;
    }

    public void setpId(String pId)
    {
        this.pId = pId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getpPath()
    {
        return pPath;
    }

    public void setpPath(String pPath)
    {
        this.pPath = pPath;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getpTime()
    {
        return pTime;
    }

    public void setpTime(String pTime)
    {
        this.pTime = pTime;
    }

    @Override
    public String toString()
    {
        return "PaikeCenterBean [pId=" + pId + ", title=" + title + ", pPath=" + pPath + ", userId=" + userId + ", pTime=" + pTime + "]";
    }

}
