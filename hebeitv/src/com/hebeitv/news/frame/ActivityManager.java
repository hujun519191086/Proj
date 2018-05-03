package com.hebeitv.news.frame;

import java.util.ArrayList;

public class ActivityManager
{
    private static ActivityManager manager = new ActivityManager();

    public static ActivityManager ins()
    {
        return manager;
    }

    private ArrayList<FrameActivity> list = new ArrayList<FrameActivity>();

    public void addActivity(FrameActivity activity)
    {
        if (list.size() == 0 || (list.size() > 0 && !list.get(list.size() - 1).getClass().equals(activity.getClass())))
        {
            list.add(activity);
        }
    }

    public void removeTo(Class<? extends FrameActivity> clazz, boolean finsh)
    {
        for (int i = list.size() - 1; i >= 0; i--)
        {
            FrameActivity activity = list.get(i);
            if (activity.getClass().equals(clazz))
            {
                return;
            }
            else
            {
                list.remove(i);
                if (finsh)
                {
                    activity.finish();
                }
            }
        }
    }

    public void removeTo(Class<? extends FrameActivity> clazz)
    {

        removeTo(clazz, true);
    }

    public void removeClazz(Class<? extends FrameActivity> clazz)
    {
        for (int i = list.size() - 1; i >= 0; i--)
        {
            FrameActivity activity = list.get(i);
            if (activity.getClass().equals(clazz))
            {
                return;
            }
            else
            {
                list.remove(i);
            }
        }
    }
}
