package com.qc188.com.bean;

/**
 * 车型详情中的item
 * 
 * @author jieranyishen
 * 
 */
public class CarTypeItemBean
{
    private String addressName;
    private String sale;
    private String phone;
    private String sale_area;
    private String service_time;
    private String promotion;
    private String price;

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getAddressName()
    {
        return addressName;
    }

    public void setAddressName(String addressName)
    {
        this.addressName = addressName;
    }

    public String getSale()
    {
        return sale;
    }

    public void setSale(String sale)
    {
        this.sale = sale;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getSale_area()
    {
        return sale_area;
    }

    public void setSale_area(String sale_area)
    {
        this.sale_area = sale_area;
    }

    public String getService_time()
    {
        return service_time;
    }

    public void setService_time(String service_time)
    {
        this.service_time = service_time;
    }

    public String getPromotion()
    {
        return promotion;
    }

    public void setPromotion(String promotion)
    {
        this.promotion = promotion;
    }

    @Override
    public String toString()
    {
        return "CarTypeItemBean [addressName=" + addressName + ", sale=" + sale + ", phone=" + phone + ", sale_area=" + sale_area + ", service_time=" + service_time + ", promotion=" + promotion + "]";
    }

}
