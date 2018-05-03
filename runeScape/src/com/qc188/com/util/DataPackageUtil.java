package com.qc188.com.util;

import java.util.HashMap;
import java.util.Map;

public class DataPackageUtil
{
    private static final DataPackageUtil dpu = new DataPackageUtil();

	private String key = "carName";
    private DataPackageUtil()
    {

    }

    public static DataPackageUtil getPackage()
    {
        return dpu;
    }

    private Map<Object, Object> map = new HashMap<Object, Object>();

	public <K> void put(K value) {
		put(key, value);
	}
    public <T, K> void put(T key, K value)
    {
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T, K> K get(T key)
    {
        return (K) map.get(key);
    }
}
