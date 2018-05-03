package com.qc188.com.framwork;

import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

public abstract class FrameAdapter extends BaseAdapter
{

    private WeakHashMap<String, Bitmap> map;
    private int itemHeigth;
    protected Context context;

    public FrameAdapter(Context context)
    {
        this.context = context;
        map = new WeakHashMap<String, Bitmap>();
    }

    public int getItemHeight()
    {
        return itemHeigth;
    }

    public void setItemHeigth(int itemHeigth)
    {
        this.itemHeigth = itemHeigth;
    }

    public FrameAdapter()
    {
        map = new WeakHashMap<String, Bitmap>();
    }

    public void putBitmap(String key, Bitmap bitmap)
    {
        map.put(key, bitmap);
    }

    @Override
    public abstract int getCount();

    public void clear()
    {
        // Set<Entry<String, Bitmap>> set = map.entrySet();
        // Iterator<Entry<String, Bitmap>> iterator = set.iterator();
        // while (iterator.hasNext()) {
        // Entry<String, Bitmap> entry = iterator.next();
        // entry.getValue().recycle();
        // }
        System.gc();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    /**
     * 自动填充 view 将viewholder放在 view里面.
     * 
     * @param convertView
     * @param holder
     * @param layoutId
     * @return
     */
    public View setConvertView_Holder(View convertView, int layoutId)
    {
        return setConvertView_Holder(convertView, layoutId, true);
    }

    /**
     * 免去viewholder的各种查找烦恼.
     * 
     * @param convertView
     *            参数内的convertView
     * @param holder
     *            定义的viewholder,继承自BaseViewHolder,成员变量必须为public
     * @param layoutId
     *            需要充入的布局int
     * @param setheigth
     *            是否设置高度为构造函数内传入的高度! false为不设置
     * @return 已处理完成的view
     */
    public View setConvertView_Holder(View convertView, int layoutId, boolean setheigth)
    {

        if (context == null)
        {
            throw new IllegalArgumentException("没有传递context");
        }
        if (convertView != null)
        {
            return convertView;
        }
        convertView = View.inflate(context, layoutId, null);
        if (setheigth)
        {
            setLayoutParams(convertView);
        }
        return convertView;
    }

    /**
     * 设置控件高度为传入参数
     * 
     * @param convertView
     *            需要设置高度的控件
     * @param itemHeight
     *            控件高度。直接以px影响
     */
    public void setLayoutParams(View convertView)
    {
        AbsListView.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, itemHeigth);
        convertView.setLayoutParams(params);
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
