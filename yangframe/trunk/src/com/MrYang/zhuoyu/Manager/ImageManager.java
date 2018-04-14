package com.MrYang.zhuoyu.Manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.utils.LogInfomation;

public class ImageManager
{
    private TreeMap<Object, BitmapMsg> bitmapCache;
    private static final String TAG = "ImageManager";

    private ImageManager()
    {
        bitmapCache = new TreeMap<Object, BitmapMsg>(new Comparator<Object>()
        {

            @Override
            public int compare(Object lhs, Object rhs)
            {
                String lhstypeName = lhs.getClass().getName();
                String rhstypeName = rhs.getClass().getName();
                String intListtypeName = Integer[].class.getName();

                if (lhstypeName.equals(intListtypeName) && rhstypeName.equals(intListtypeName))
                {
                    Integer[] left = (Integer[]) lhs;
                    Integer[] right = (Integer[]) rhs;
                    int comSirPairValue = comSirPairInt(left.length, right.length);
                    if (comSirPairValue == 0)
                    {
                        for (int i = 0; i < left.length; i++)
                        {

                            comSirPairValue = comSirPairInt(left[i], right[i]);

                            if (comSirPairValue != 0)
                            {
                                return comSirPairValue;
                            }
                        }

                    }
                    return comSirPairValue;
                }
                else
                {
                    return comHash(lhs, rhs);
                }

            }

            public int comSirPairInt(int left, int right)
            {
                if (left < right)
                {
                    return -1;
                }
                if (left > right)
                {
                    return 1;
                }

                return 0;
            }

            public int comHash(Object left, Object right)
            {
                if (left.hashCode() > right.hashCode())
                {
                    return 1;
                }
                if (left.equals(right))
                {
                    return 0;
                }
                return -1;
            }
        });
    }

    private static ImageManager manager = new ImageManager();

    public static ImageManager getManger()
    {
        return manager;
    }

    /**
     * @param bActitivity
     * @return
     */
    public static Class<? extends Object> getBelongUIName(Class<? extends Object> bActitivity)
    {
        return bActitivity;
    }

    /**
     * 最好用{@link #getDrawable(Class, View, Integer)} 有contentView的方法. gc的时候不会轻易出现 exception
     * 
     * @param bActitivity
     * @param resId
     * @return
     */
    public Drawable getDrawable(Class<? extends Object> bActitivity, Integer... resId)
    {
        return getDrawable(bActitivity, null, resId);
    }

    /**
     * 获取图片,进行策略管理.
     * 
     * @param belongTag
     * @param resId
     * @param cornerRadius
     * @param margin
     * @return
     */
    public Drawable getDrawable(Class<? extends Object> bActitivity, View contentView, Integer... res)
    {

        BitmapMsg bmg = getBitmapMsg(bActitivity, contentView, res);
        return bmg.getDrawable(bActitivity, contentView);
    }

    public Bitmap getBitmap(Class<? extends Object> bActitivity, View contentView, String path)
    {
        BitmapMsg bmg = bitmapCache.get(path);
        if (bmg != null)
        {
            return bmg.getBitmap(bActitivity, contentView);
        }
        LogInfomation.d("getBitmapMsg", ":" + path + "   content:" + contentView.getId());
        return createMap(bActitivity, path).getBitmap(bActitivity, contentView);
    }

    private BitmapMsg getBitmapMsg(Class<? extends Object> bActitivity, View contentView, Integer... res)
    {
        BitmapMsg bmg = bitmapCache.get(res);
        if (bmg != null)
        {
            return bmg;
        }
        return createMap(bActitivity, res);
    }

    /**
     * 创建bitmap管理器
     * 
     * @param bActitivity
     * @param resId
     * @return
     */
    @MustFinish(values = { "现在的做法是将一个drawable为单位,多个bitmap存储.对于单个资源,还是会出现多次读取的现象,需要制作的是,多个资源,真正读取一次,并且制作成多个drawable" })
    public BitmapMsg createMap(Class<? extends Object> bActitivity, Integer... resId)
    {
        LogInfomation.d(TAG, "createMap:" + resId);
        BitmapMsg bitmapMsg = new BitmapMsg(bActitivity, resId);
        bitmapCache.put(resId, bitmapMsg);
        return bitmapMsg;
    }

    @MustFinish(values = { "现在的做法是将一个drawable为单位,多个bitmap存储.对于单个资源,还是会出现多次读取的现象,需要制作的是,多个资源,真正读取一次,并且制作成多个drawable" })
    public BitmapMsg createMap(Class<? extends Object> bActitivity, String path)
    {
        LogInfomation.d(TAG, "createMap:" + path);
        BitmapMsg bitmapMsg = new BitmapMsg(bActitivity, path);
        bitmapCache.put(path, bitmapMsg);
        return bitmapMsg;
    }

    /**
     * 删除drawable
     * 
     * @param bActitivity
     */
    public void destoryDrawable(Class<? extends Object> bActitivity)
    {
        if (bitmapCache != null)
        {
            LogInfomation.d(TAG, "beginDestoryMap:");
            LogInfomation.printMap(TAG, bitmapCache);
            Set<Object> sets = bitmapCache.keySet();

            ArrayList<Object> keyList = new ArrayList<Object>(sets);
            for (int i = 0; i < keyList.size(); i++)
            {
                Object set = keyList.get(i);
                BitmapMsg bm = bitmapCache.get(set);

                if (bm != null)
                {
                    bm.destoryDrawable(bActitivity);
                }
            }

            LogInfomation.d(TAG, "detstoryOver:");
            LogInfomation.printMap(TAG, bitmapCache);
            LogInfomation.d(TAG, "destoryEnd------------");
        }
    }

}
