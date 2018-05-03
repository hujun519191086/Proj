package com.hebeitv.news.module;

import java.util.Collection;
import java.util.HashMap;

import com.hebeitv.news.net.OnPostCallBack;

public class ModuleManager
{

    public static ModuleManager mmanager = new ModuleManager();

    private ModuleManager()
    {

    }

    public static ModuleManager getManager()
    {
        return mmanager;
    }

    private HashMap<Class<? extends Object>, IModule> moduleMap = new HashMap<Class<? extends Object>, IModule>();
    private HashMap<Class<? extends OnPostCallBack>, OnPostCallBack> callback = new HashMap<Class<? extends OnPostCallBack>, OnPostCallBack>();

    public <T extends IModule> void addModule(T module)
    {
        moduleMap.put(module.getClass().getInterfaces()[0], module);
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getModule(Class<T> clazz)
    {
        return (T) moduleMap.get(clazz);
    }

    public void addCallBack(OnPostCallBack callBack)
    {
        callback.put(callBack.getClass(), callBack);
    }

    public void removeCallBack(OnPostCallBack callBack)
    {
        callback.remove(callBack.getClass());
    }

    public OnPostCallBack getCallBack(Class<? extends OnPostCallBack> key)
    {
        return callback.get(key);
    }

    public OnPostCallBack[] getCallBack(Class<? extends OnPostCallBack>[] keys)
    {

        if (keys != null)
        {
            OnPostCallBack[] backclazzs = new OnPostCallBack[keys.length];
            for (int i = 0; i < keys.length; i++)
            {
                backclazzs[i] = getCallBack(keys[i]);
            }
            return backclazzs;
        }
        return null;
    }

    public Collection<OnPostCallBack> getAllCallBack()
    {
        return callback.values();
    }

}
