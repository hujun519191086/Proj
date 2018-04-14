package com.MrYang.zhuoyu.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;

import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;

/**
 * 基类adapter 。 可提供很多简便方法 不需要复写getCount（如有特殊需要） 自动convertView。 调用目标方法即可
 * 
 * @author mryang
 * 
 * @param <T>
 *            list中的泛型
 */
public abstract class FrameAdapter<T> extends BaseAdapter
{
    private static final int ERROR_VALUE = InitUtil.ERROR_VALUES;

    private static final String TAG = "FrameAdapter";

    protected AbsListView m_absListView;

    private ItemViewList itemViewList = new ItemViewList();

    public FrameAdapter()
    {
        init();
    }

    /**
     * 可将list直接传入
     * 
     * @param lists
     */
    public FrameAdapter(List<T> lists)
    {
        this(lists, 0);
        init();
    }

    /**
     * 可将list直接传入,并且设置高度
     * 
     * @param lists
     */
    public FrameAdapter(List<T> lists, int itemHeight)
    {
        this.dataList = lists;
        this.itemHeight = itemHeight;
        init();
    }

    /**
     * 可将list直接传入,并且设置高度
     * 
     * @param lists
     */
    public FrameAdapter(List<T> lists, int itemHeight, AbsListView absListView)
    {
        this.dataList = lists;
        this.itemHeight = itemHeight;
        m_absListView = absListView;
        init();
    }

    protected void init()
    {

    }

    /**
     * 传递进listview,不设置adapter.
     * 
     * @param lists
     */
    public FrameAdapter(List<T> lists, AbsListView absListView)
    {
        this.dataList = lists;
        m_absListView = absListView;
        init();
    }

    /**
     * 赋值item的高度
     * 
     * @param itemHeight
     *            需要的高度， 直接成为px
     */
    public FrameAdapter(int itemHeight)
    {
        this.itemHeight = itemHeight;
        init();
    }

    /**
     * 在内部增加了对 <br/>
     * {@link com.MrYang.zhuoyu.adapter.FrameAdapter#setList(List)} <br/>
     * 中的list判断count <br/>
     * <br/>
     * 如果想要重新定义一下count建议使用 <br/>
     * {@link com.MrYang.zhuoyu.adapter.FrameAdapter#getCountInSelf()}<br/>
     * 方法.
     */
    @Override
    final public int getCount()
    {
        int count = 0;
        count = getCountInSelf();
        if (count == ERROR_VALUE)
        {
            return dataList == null ? 0 : dataList.size();
        }
        return count;
    }

    /**
     * 获取adapter的count.如果设置了list,只是使用list的size.. 那么就不需要复写这些方法.当然,复写也没错
     * 
     * @return
     */
    public int getCountInSelf()
    {
        return ERROR_VALUE;
    }

    /**
     * 获取设置的list的一条数据,如果想要此方法生效,请设置List<br/>
     * {@link #FrameAdapter(List)} <br/>
     * {@link #FrameAdapter(List, AbsListView)}<br/>
     * {@link FrameAdapter#setList(List)}<br/>
     * 任意一个方法.
     */
    @Override
    public T getItem(int position)
    {
        if (dataList != null && dataList.size() > position)
        {
            return dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    /**
     * 在本类中会被直接调用的类.可进行直接修改.
     */
    protected List<T> dataList;

    /**
     * 设置adapter中数据的list
     * 
     * @param list
     *            要替换的list
     */
    public void setList(List<T> list)
    {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public void addList(List<T> list)
    {
        if (dataList != null && list != null)
        {
            dataList.addAll(list);
        }
        else
        {
            dataList = list;
        }
        notifyDataSetChanged();
    }

    /**
     * 增加一个数据
     * 
     * @param obj
     *            数据
     */
    public void addToList(T obj)
    {
        dataList.add(obj);
    }

    /**
     * 增加一个数据到list的某一位置
     * 
     * @param obj
     *            数据
     * @param position
     *            位置
     */
    public void addToList(T obj, int position)
    {
        dataList.add(position, obj);
    }

    private int itemHeight = DensityUtil.dip2px(50);

    /**
     * 设置控件高度为传入参数
     * 
     * @param convertView
     *            需要设置高度的控件
     * @param itemHeight
     *            控件高度。直接以px影响
     */
    public void setLayoutParams(View convertView, int itemHeight)
    {
        AbsListView.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, itemHeight);
        convertView.setLayoutParams(params);
    }

    /**
     * 记录保存itemHeight
     * 
     * @param itemHeight
     *            需要保存的itemHeight数据， 以PX
     */
    public void setItemHeight(int itemHeight)
    {
        this.itemHeight = itemHeight;
    }

    /**
     * adapter中获取view的方法,不推荐复写这个方法.请使用{@link #getItemView(int, View, ViewGroup)} <b> </b>
     * 
     * <li>dadss</li>
     */
    @Override
    final public View getView(int position, View convertView, ViewGroup parent)
    {
        View tempView = getItemView(position, convertView, parent);
        itemViewList.put(position, tempView);
        return tempView;
    }

    /**
     * 获取当前选中的实例,有可能为空(position为-1的时候),要做类型判断
     * 
     * @param position
     *            位置.
     * @return 位置下的view
     */
    public View getItemView(int position)
    {
        return itemViewList.get(position);
    }

    /**
     * adapter中推荐使用的获取view的复写方法。
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    /**
     * 自动填充 view 将{@link com.MrYang.zhuoyu.handle.ViewHolder} 放在 view.setTag()里.
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
        if (convertView != null)
        {
            return convertView;
        }
        convertView = View.inflate(InitUtil.getInitUtil().context, layoutId, null);
        ViewHolder vHolder = new ViewHolder(convertView);
        if (setheigth)
        {
            setitemAbsParams(convertView);
        }
        convertView.setTag(vHolder);
        return convertView;
    }

    /**
     * 设置item的params高度. 高度数量如果为0,默认为30dp
     * 
     * @param view
     * @return
     */
    public View setitemAbsParams(View view)
    {

        LogInfomation.d(TAG, "itemheight:" + itemHeight);
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemHeight));
        return view;
    }

    /**
     * 有需要可以复写这个,计算listview的高度.
     * 
     * @return
     */
    public int getViewHeight()
    {
        return -1;
    }

    protected Context getContext()
    {
        return InitUtil.getInitUtil().context;
    }

    /**
     * 记录每个position对应的view实例
     * 
     * @author jieranyishen
     * 
     */
    private class ItemViewList
    {
        private HashMap<View, Integer> viewMap = new HashMap<View, Integer>();
        private SparseArray<View> itemView = new SparseArray<View>();

        public void put(int position, View view)
        {
            Integer arrayRawPosition = viewMap.get(view);
            if (arrayRawPosition != null)
            {
                itemView.remove(arrayRawPosition);
                viewMap.remove(view);
            }

            viewMap.put(view, position);
            itemView.append(position, view);
        }

        public View get(int position)
        {
            return itemView.get(position);
        }
    }
}
