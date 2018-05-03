package com.qc188.com.engine.impl;

import java.util.List;
import java.util.Map;

import com.qc188.com.bean.BrandDetailEngineBean;
import com.qc188.com.bean.BrandDetailMsgBean;
import com.qc188.com.engine.BrandDetailEngine;
import com.qc188.com.engine.UniversalEngine;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.ConstantValues.STATUS;
import com.qc188.com.util.HttpClientUtils;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

public class BrandDetailEngineImpl implements BrandDetailEngine
{

    private static final String TAG = "BrandDetailEngineImpl";
    private BrandDetailMsgBean bdmBean;
    private List<BrandDetailEngineBean> bdeBean;

    @Override
    public int getJsonToMatchBean(int brandid)
    {
        String json = HttpClientUtils.getJson(ConstantValues.BRAND_DETAIL + brandid);

        LogUtil.d(TAG, "url:" + (ConstantValues.BRAND_DETAIL + brandid) + "       json:" + json);
        if (json == null)
        {
            return -1;
        }
        UniversalEngine bdEngine = InstanceFactory.getInstances(UniversalEngine.class);

        Map<String, Object> map = bdEngine.parseMap(json);

        int status = ((Integer) map.get(ConstantValues.STATUS));
        if (status == STATUS.SUCCESS)
        {
            bdmBean = bdEngine.parseString(json, "brandDetail", BrandDetailMsgBean.class);
            bdeBean = bdEngine.parseString(json, "engine", BrandDetailEngineBean.class);
        }

        return status;
    }

    @Override
    public BrandDetailMsgBean getBrandDetailBean()
    {
        return bdmBean;
    }

    @Override
    public List<BrandDetailEngineBean> getBrandEngineBean()
    {
        return bdeBean;
    }

}
