package com.MrYang.zhuoyu.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.enu.MustFinishAnnoEnum;

/**
 * 自动设置值的工具
 * 
 * @author jieranyishen
 * 
 */

@MustFinish(mustFinishEnum = MustFinishAnnoEnum.NOT_DO, values = { "制作json孵化器,  xml孵化器." })
public class AutoSetValues
{

    /**
     * 设置单个值
     * 
     * @param t
     *            类或者基类.
     * @param fixValues
     *            要赋予的值.(给 integer和long使用)
     * @return 如果是Bean封装类,那么不需要接住此返回值, 如果是Boolean或者String诸如此类,需要接住返回值.保险起见,接住为好.
     */
    @SuppressWarnings("unchecked")
    public static <T> T setValues(Class<T> clazz, Integer fixValues)
    {
        T t = null;
        try
        {

            t = (T) clazz.getConstructors()[0].newInstance();
        }
        catch (InstantiationException e1)
        {
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1)
        {
            e1.printStackTrace();
        }
        catch (IllegalArgumentException e1)
        {
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1)
        {
            e1.printStackTrace();
        }
        if (fixValues == null)
        {
            fixValues = 0;
        }
        Random random = new Random();
        if (t instanceof String)
        {

            t = (T) ("machine:" + fixValues);
            return t;
        }
        else if (t instanceof Integer || t.getClass().getName().equals(int.class.getName()))
        {
            t = (T) (Integer.valueOf(random.nextInt() + fixValues));
            return t;
        }
        else if (t instanceof Long || t.getClass().getName().equals(long.class.getName()))
        {
            t = (T) Long.valueOf(random.nextLong() + fixValues);
            return t;
        }
        else if (t instanceof Boolean || t.getClass().getName().equals(boolean.class.getName()))
        {
            Boolean bool = random.nextBoolean();
            t = (T) bool;
            return t;
        }

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            try
            {
                if (field.getType().getName().equals(String.class.getName()))
                {
                    field.set(t, "machine:" + fixValues);
                }
                else if (field.getType().getName().equals(Integer.class.getName()) || field.getType().getSimpleName().equals(int.class.getSimpleName()))
                {
                    field.set(t, fixValues);
                }
                else if (field.getType().getName().equals(Long.class.getName()) || field.getType().getSimpleName().equals(long.class.getSimpleName()))
                {
                    field.set(t, Long.valueOf(fixValues));
                }
                else if (field.getType().getName().equals(Boolean.class.getName()) || field.getType().getSimpleName().equals(boolean.class.getSimpleName()))
                {
                    field.set(t, new Random().nextBoolean());
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return t;
    }

    /**
     * 获取数组
     * 
     * @param list
     *            该list, 可传入null.注意,如果传入null,则在返回的时候需要被重新赋值.
     * @param t
     *            list中泛型.
     * @param count
     *            多少条数据
     * @return 返回该list.
     */
    public static <T> List<T> setListValues(List<T> list, Class<T> t, int count)
    {
        return setListValues(list, t, count, 0);
    }

    /**
     * 获取数组
     * 
     * @param list
     *            该list, 可传入null.注意,如果传入null,则在返回的时候需要被重新赋值.
     * @param t
     *            list中泛型.
     * @param count
     *            多少条数据
     * @param fixValue
     *            修正值
     * @return 返回该list.
     */
    public static <T> List<T> setListValues(List<T> list, Class<T> t, int count, int fixValue)
    {

        if (list == null)
        {
            list = new ArrayList<T>();
        }
        for (int i = 0; i < count; i++)
        {

            // T nt = ((T) t.getConstructors()[0].newInstance());
            list.add(setValues(t, fixValue + i));
        }

        return list;
    }
}
