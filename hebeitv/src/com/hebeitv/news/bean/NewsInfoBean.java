package com.hebeitv.news.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class NewsInfoBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("pic")
    private String pic;
    @SerializedName("url")
    private String url;
    @SerializedName("totalComent")
    private String totalComment = "0";

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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

    public String getTotalComment()
    {
        return totalComment;
    }

    public void setTotalComment(String totalComment)
    {
        this.totalComment = totalComment;
    }

    @Override
    public String toString()
    {
        return "NewsInfoBean [id=" + id + ", title=" + title + ", pic=" + pic + ", url=" + url + ", totalComment=" + totalComment + "]";
    }

}
