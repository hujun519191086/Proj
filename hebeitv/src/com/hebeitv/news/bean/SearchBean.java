package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class SearchBean
{
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("zaojian")
    private String zaojian;
    @SerializedName("pic")
    private String pic;
    @SerializedName("url")
    private String url;

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

    public String getZaojian()
    {
        return zaojian;
    }

    public void setZaojian(String zaojian)
    {
        this.zaojian = zaojian;
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

    @Override
    public String toString()
    {
        return "SearchBean [id=" + id + ", title=" + title + ", zaojian=" + zaojian + ", pic=" + pic + ", url=" + url + "]";
    }

}
