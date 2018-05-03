package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class LoginBean
{
    @SerializedName("rName")
    public String rName;
    @SerializedName("rPass")
    public String rPass;
    @SerializedName("rId")
    public String rId;

    public boolean success;

    @Override
    public String toString()
    {
        return "LoginBean [rName=" + rName + ", rPass=" + rPass + ", rId=" + rId + "]";
    }

}
