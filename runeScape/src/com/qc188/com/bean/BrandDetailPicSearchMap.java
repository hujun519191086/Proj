package com.qc188.com.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.qc188.com.R;
import com.qc188.com.framwork.BaseBean;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.LogUtil;

public class BrandDetailPicSearchMap extends BaseBean
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private static final String TAG = "BrandDetailPicSearchMap";

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, String> typeMap = new HashMap<Integer, String>();

    private List<String> type;
    private List<String> colorName;
    private List<String> color;
    private List<String> carNameList;
    private List<String> carList;

    public int getSelectCardListPosition(String colorList)
    {
        for (int i = 0; i < carNameList.size(); i++)
        {
            if (carNameList.get(i).equals(colorList))
            {
                return i;
            }
        }
        return 0;
    }

    public int getSelectColorPosition(String color)
    {

        for (int i = 0; i < colorName.size(); i++)
        {
            if (colorName.get(i).equals(color))
            {
                return i;
            }
        }
        return 0;
    }

    public int getSelectTypePosition(String type)
    {

        for (int i = 0; i < typeMap.size(); i++)
        {
            if (typeName.get(i).equals(type))
            {
                return i;
            }
        }
        return 0;
    }

    private String typeUrl = "";
    private String colorUrl = "";
    public String carListUrl = "";

    private ArrayList<String> typeName;

    {
        // "全部类型","外观","座椅","中控","其他"

        typeMap.put(0, "全部位置");
        typeMap.put(1, "外观");
        typeMap.put(2, "座椅");
        typeMap.put(3, "中控");
        typeMap.put(4, "其他");
    }

    public void setSearchType(int id, String selectStr)
    {
        LogUtil.d(TAG, "selectType:" + typeUrl + "    color:" + colorUrl + "  carName:" + carListUrl);
        switch (id)
        {
        // 类型删选
            case R.id.tv_carType_pic_selectType: // type
            case R.id.tv_brandDetail_pic_selectType: // type
                for (int i = 1; i < typeMap.size(); i++)
                {
                    if (typeMap.get(i).equals(selectStr))
                    {
                        typeUrl = "&tutype=" + i;
                        LogUtil.d(TAG, "changeType:" + i);
                        return;
                    }
                }
                typeUrl = "";
                break;
            case R.id.tv_carType_pic_selectColor: // color
            case R.id.tv_brandDetail_pic_selectColor: // color
                for (int i = 1; i < colorName.size(); i++)
                {
                    if (colorName.get(i).equals(selectStr))
                    {
                        colorUrl = "&color=" + color.get(i);
                        LogUtil.d(TAG, "color:" + i);
                        return;
                    }
                }
                colorUrl = "";
                break;
            case R.id.tv_brandDetail_pic_selectLevel: // colorList
                for (int i = 1; i < carNameList.size(); i++)
                {
                    if (carNameList.get(i).equals(selectStr))
                    {
                        carListUrl = "&cid=" + carList.get(i);
                        LogUtil.d(TAG, "colorList:" + i);
                        return;
                    }
                }
                carListUrl = "";
                break;
        }

    }

    public List<String> getTypeName()
    {
        if (typeName == null)
        {
            typeName = new ArrayList<String>();
            for (int i = 0; i < type.size(); i++)
            {
                typeName.add(typeMap.get(Integer.valueOf(type.get(i))));
            }
        }
        return typeName;
    }

    public List<String> getType()
    {
        return type;
    }

    public void setType(List<String> type)
    {
        type.add(0, "0");
        this.type = type;
    }

    public List<String> getColorName()
    {
        return colorName;
    }

    public void setColorName(List<String> colorName)
    {
        colorName.add(0, "全部颜色");
        this.colorName = colorName;
    }

    // 12-08 01:55:22.105: D/BrandDetail_Pic(2423):
    // url:http://www.qc188.com/app/carphoto.asp?id=1313&cid=3987&page=1

    public List<String> getColor()
    {
        return color;
    }

    public void setColor(List<String> color)
    { // URL
        color.add(0, "0");
        this.color = color;
    }

    public List<String> getCarNameList()
    {
        return carNameList;
    }

    // 12-05 01:52:54.892: D/BrandDetail_Pic(2371):
    // url:http://www.qc188.com/app/carphoto.asp?id=1313&cid=3987&page=1

    public void setCarNameList(List<String> carNameList)
    {
        carNameList.add(0, "全部车型");
        this.carNameList = carNameList;
    }

    public List<String> getCarList()
    {
        return carList;
    }

    public void setCarList(List<String> carList)
    { // URL

        carList.add(0, "0");
        this.carList = carList;
    }

    public String getUrl(String id, int page)
    {
        return (TextUtils.isEmpty(carListUrl) ? ConstantValues.BRAND_DETAIL_PIC : ConstantValues.BRAND_CARlIST_DETAIL_PIC) + id + carListUrl + typeUrl + colorUrl + "&page=" + page;
    }

    public boolean isAllSearch()
    {
        return TextUtils.isEmpty(typeUrl) && TextUtils.isEmpty(colorUrl) && TextUtils.isEmpty(carListUrl);
    }
}
