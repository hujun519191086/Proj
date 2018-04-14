package com.MrYang.zhuoyu.handle;

import java.util.HashMap;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;

import com.MrYang.zhuoyu.utils.LogInfomation;


/**
 * 将ViewStub的方法进行proxy
 * 
 * @author jieranyishen
 * 
 */
public class ViewStubProxy
{
    private HashMap<Object, SparseArray<ViewStub>> stubMap = new HashMap<Object, SparseArray<ViewStub>>();
    
    private static ViewStubProxy vsProxy = new ViewStubProxy();
    private ViewStubProxy()
    {

    }

    public static ViewStubProxy getProxy()
    {
        return vsProxy;
    }

    /**
     * 将View从Stub解放出来.
     * 
     * @param stubId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T inflate(int stubId)
    {

        SparseArray<ViewStub> stubPair = stubMap.get(stubId);

        if (stubPair != null)
        {
            ViewStub tempVS = stubPair.get(stubId);
            if (tempVS != null && tempVS.getParent() != null)
            {
                stubMap.remove(stubId);
                return (T) tempVS.inflate();
            }
        }


        return null;
    }

    /**
     * 將Stub放进Map里面
     * 
     * @param stubId
     * @param stub
     */
    public void putStub(Object obj, int stubId, ViewStub stub)
    {
        SparseArray<ViewStub> stubPair = stubMap.get(stubId);

        if (stubPair == null)
        {
            stubPair = new SparseArray<ViewStub>();
        }

        stubPair.put(stubId, stub);
        stubMap.put(obj, stubPair);
        LogInfomation.printMap(this, stubMap);
    }
}
