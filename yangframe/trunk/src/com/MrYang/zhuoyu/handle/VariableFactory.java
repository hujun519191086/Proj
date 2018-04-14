package com.MrYang.zhuoyu.handle;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.utils.SystemNotification;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.MrYang.zhuoyu.view.anno.ResetControl;

/*
 * 
 * @author mryang
 * 
 */

@MustFinish(values = { "图片赋值,设置背景或者src" })
public class VariableFactory
{

    public static final String TAG = "VariableFactory";

    /**
     * 自动赋值工具.<br/>
     * 解析BaseActivity中的带有ViewById,ViewById_setClickThis,ResetControl注解的成员变量
     * 
     * @param bActivity
     * @param findoutView
     * @param context
     */

    public static void autoInjectAllField(BaseActivity bActivity, View findoutView)
    {
        Resources resource = bActivity.getResources();
        String packageName = bActivity.getPackageName();
        autoInjectAllField(bActivity, findoutView, resource, packageName);
    }

    public static void autoInjectAllField(Object obj, View findoutView, Resources resource, String packageName)
    {

        Field[] fields = obj.getClass().getDeclaredFields();

        try
        {
            for (int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                field.setAccessible(true);
                if (field.isAnnotationPresent(ResetControl.class))
                {
                    ResetControl reset = fields[i].getAnnotation(ResetControl.class);
                    putValues(reset, field, reset.id(), findoutView, obj, reset.clickThis());
                }
                fields[i].setAccessible(false);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void putValues(ResetControl resetAnno, Field field, int id, View v, Object obj, boolean setClick)
    {

        View injectedValue = v.findViewById(id);
        if (injectedValue == null)
        {
            throw new IllegalStateException("findViewById(" + id + ") gave null for " + field + ", can't inject");
        }

        if (injectedValue instanceof ViewStub)
        {
            LogInfomation.d(VariableFactory.class, "viewStub:" + id);
            ViewStubProxy.getProxy().putStub(obj, id, (ViewStub) injectedValue);
        }
        else
        {
            try
            {
                String fieldName = field.getType().getName();
                String viewName = injectedValue.getClass().getName();
                LogInfomation.i(TAG, "fieldType:" + fieldName + "    viewType:" + viewName);
                if (fieldName.equals(viewName))
                {
                    field.set(obj, injectedValue);
                }
                else
                {

                    String errorMsg = "  在类:" + obj + "赋值类型错误!!成员变量名字:" + field.getName() + "  成员变量类型:" + fieldName + "    找出的控件类型:" + viewName;
                    if (!InitUtil.FINAL)
                    {
                        SystemNotification.showToast(InitUtil.getInitUtil().context, "有成员变量赋值异常!看logCat");
                    }
                    throw new IllegalArgumentException(errorMsg);
                }
            }
            catch (IllegalAccessException e)
            {
                System.err.println("VariableFactory :赋值异常:" + obj.getClass().getSimpleName() + "  参数:" + field.getName());
            }

            if (setClick)
            {
                try
                {
                    injectedValue.setOnClickListener((android.view.View.OnClickListener) obj);
                }
                catch (ClassCastException cce)
                {
                    LogInfomation.w(VariableFactory.class, "ClassCastException:" + cce);
                }
            }
        }

        if (resetAnno != null)
        {
            setDrawable(obj.getClass(), injectedValue, resetAnno.drawable(), resetAnno.isSrc(), resetAnno.drawableKeepPadding());
        }
    }

    /**
     * 給控件设置背景.
     * 
     * @param bActivity
     * @param contentView
     * @param resId
     * @param isSrc
     * @param keeppadding
     */
    @SuppressWarnings("deprecation")
    private static void setDrawable(Class<? extends Object> bActivity, View contentView, int[] resId, boolean isSrc, boolean keeppadding)
    {
        /**
         * 如果是没有背景.
         */
        if (resId[0] == InitUtil.ERROR_VALUES)
        {
            return;
        }
        ArrayList<Integer> resInt = new ArrayList<Integer>();
        for (int i = 0; i < resId.length; i++)
        {
            resInt.add(Integer.valueOf(resId[i]));
        }
        Drawable drawable = ImageManager.getManger().getDrawable(bActivity, contentView, resInt.toArray(new Integer[0]));

        if (isSrc && contentView instanceof ImageView)
        {
            ImageView iv = (ImageView) contentView;
            iv.setImageDrawable(drawable);
        }
        else
        {
            contentView.setBackgroundDrawable(drawable);
        }

        if (keeppadding)
        {
            int left = contentView.getPaddingLeft();
            int top = contentView.getPaddingTop();
            int right = contentView.getPaddingRight();
            int bottom = contentView.getPaddingBottom();

            contentView.setPadding(left, top, right, bottom);
        }

    }

}
