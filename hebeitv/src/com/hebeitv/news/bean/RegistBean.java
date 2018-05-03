package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class RegistBean
{
    @SerializedName("success")
    public boolean success;
    @SerializedName("info")
    public String info;
    @Override
    public String toString()
    {
        return "RegistBean [success=" + success + ", info=" + info + "]";
    }

}
