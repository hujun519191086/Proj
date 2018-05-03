package com.qc188.com.bean;

import com.qc188.com.framwork.BaseBean;

/**
 * 车系首页顶部信息
 * 
 * @author mryang
 * 
 */
public class BrandDetailMsgBean extends BaseBean
{

    /**
     * "image_url": "http: //url/abc.jpg", "level": "中型车", "engine": "1.8T2.0T3.0T", "transmission": "手动无级双离合", "guideSale": "27.8-30万"
     */

    private String image_url;
    private String level;
    private String engine;
    private String transmission;
    private String guideSale;

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getEngine()
    {
        return engine;
    }

    public void setEngine(String engine)
    {
        this.engine = engine;
    }

    public String getTransmission()
    {
        return transmission;
    }

    public void setTransmission(String transmission)
    {
        this.transmission = transmission;
    }

    public String getGuideSale()
    {
        return guideSale.equals("0-0万") ? "未上市" : guideSale;
    }

    public void setGuideSale(String guideSale)
    {
        this.guideSale = guideSale;
    }

    @Override
    public String toString()
    {
        return "brandDetailMsgBean [image_url=" + image_url + ", level=" + level + ", engine=" + engine + ", transmission=" + transmission + ", guideSale=" + guideSale + "]";
    }

}
