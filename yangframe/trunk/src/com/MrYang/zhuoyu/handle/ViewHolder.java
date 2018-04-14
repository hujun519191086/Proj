package com.MrYang.zhuoyu.handle;

import android.util.SparseArray;
import android.view.View;

import com.MrYang.zhuoyu.utils.LogInfomation;

/**
 * 可以为传入的id所对应的控件做缓存。这样就不必要将控件消耗在成员变量中<br/>
 * 建议：在使用viewHolder的时候，最多将getView出来的View存成局部变量而不将它作为成员变量！<br/>
 * <b>建议在adapter中使用而不是在activity或者fragmen中使用</b>
 * 
 * @author mryang
 * 
 */
public class ViewHolder
{
    private View rootView;

    /**
     * 将根部视图传入，以便获取根部视图中所有子控件
     * 
     * @param rootView
     *            根视图。
     */
    public ViewHolder(View rootView)
    {
        if (rootView == null)
        {
            throw new IllegalArgumentException("创建失败!传入的rootView为空!!");
        }
        this.rootView = rootView;
    }

    private SparseArray<View> views = new SparseArray<View>();

    /**
     * 查找id对应的view,可以使rootView为空
     * 
     * @param id
     *            控件的id
     * @return 查找出来的view
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id)
    {
        View findView = views.get(id);

        if (rootView == null)
        {
            return (T) findView;
        }
        if (findView == null)
        {
            findView = rootView.findViewById(id);
            setView(id, findView);
        }

        if (findView == null)
        {
            LogInfomation.e(this.getClass(), "参数异常，你传入的id＝" + id + "  不在此View：" + rootView + "   控件内！");
        }
        return (T) findView;
    }

    public <T extends View> T getView(int id, Class<T> clazz)
    {
        return getView(id);
    }
    public void setView(int id, View view)
    {
        views.append(id, view);
    }

    /**
     * 清理掉控件引用
     */
    public void clear()
    {
        views.clear();
        rootView = null;
    }
}
