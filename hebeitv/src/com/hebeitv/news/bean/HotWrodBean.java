package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class HotWrodBean
{
    @SerializedName("hKey")
    public String hKey;
    @SerializedName("itemId")
    public String itemId;
    @SerializedName("url")
    public String url;
    @SerializedName("hId")
    public String hId;

    @Override
    public String toString()
    {
        return "HotWrodBean [hKey=" + hKey + ", itemId=" + itemId + ", url=" + url + ", hId=" + hId + "]";
    }

}
