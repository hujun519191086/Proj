package com.hebeitv.news.demo;

import com.google.gson.annotations.SerializedName;


public class DemoResult
{

    private String weaid;
    private String days;
    private String week;
    private String cityno;
    private String citynm;
    @SerializedName("cityid")
    private String cyaysdfiId;
    private String temperature;
    private String temperature_curr;
    private String humidity;
    private String weather;
    private String weather_icon;
    private String weather_icon1;
    private String wind;
    private String winp;
    private String temp_high;
    private String temp_low;
    private String temp_curr;
    private String humi_high;
    private String humi_low ;
    private String weatid ;
    private String weatid1 ;
    private String windid ;
    private String winpid ;
    @Override
    public String toString()
    {
        return "DemoResult [weaid=" + weaid + ", days=" + days + ", week=" + week + ", cityno=" + cityno + ", citynm=" + citynm + ", cityid=" + cyaysdfiId + ", temperature=" + temperature + ", temperature_curr=" + temperature_curr
                + ", humidity=" + humidity + ", weather=" + weather + ", weather_icon=" + weather_icon + ", weather_icon1=" + weather_icon1 + ", wind=" + wind + ", winp=" + winp + ", temp_high=" + temp_high + ", temp_low=" + temp_low
                + ", temp_curr=" + temp_curr + ", humi_high=" + humi_high + ", humi_low=" + humi_low + ", weatid=" + weatid + ", weatid1=" + weatid1 + ", windid=" + windid + ", winpid=" + winpid + "]";
    }
    
    
    
}
