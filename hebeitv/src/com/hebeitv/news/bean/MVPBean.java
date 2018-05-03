package com.hebeitv.news.bean;

import com.google.gson.annotations.SerializedName;

public class MVPBean
{
    @SerializedName("sId")
    private String sId;
    @SerializedName("sName")
    private String sName;
    @SerializedName("sPic")
    private String sPic;
    @SerializedName("workScene")
    private String workScene;
    @SerializedName("workSplace")
    private String workSplace;

    public String getsId()
    {
        return sId;
    }

    public void setsId(String sId)
    {
        this.sId = sId;
    }

    public String getsName()
    {
        return sName;
    }

    public void setsName(String sName)
    {
        this.sName = sName;
    }

    public String getsPic()
    {
        return sPic;
    }

    public void setsPic(String sPic)
    {
        this.sPic = sPic;
    }

    public String getWorkScene()
    {
        return workScene;
    }

    public void setWorkScene(String workScene)
    {
        this.workScene = workScene;
    }

    public String getWorkSplace()
    {
        return workSplace;
    }

    public void setWorkSplace(String workSplace)
    {
        this.workSplace = workSplace;
    }

    @Override
    public String toString()
    {
        return "MVPBean [sId=" + sId + ", sName=" + sName + ", sPic=" + sPic + ", workScene=" + workScene + ", workSplace=" + workSplace + "]";
    }

}
