package com.MrYang.zhuoyu.handle;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.http.entity.mime.Mime_DatumLine;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.MrYang.zhuoyu.DatumLine;
import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.enu.MustFinishAnnoEnum;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.alibaba.fastjson.JSON_DatumLine;
import com.nostra13.universalimageloader.Universal_DatumLine;

import dalvik.system.DexFile;

/**
 * 实例化工厂, 本着解耦精神,现在只要传入Interface类,即可获取Implements类.(多实现则只有单实例.)
 * 
 * @author jieranyishen
 * 
 */
public class InstanceFactory
{

    private static final Class<InstanceFactory> TAG = InstanceFactory.class;

    public static Properties getPropertiesFroResourceName(String resourceName)
    {
        Properties p = null;

        InputStream is = BaseActivity.class.getClassLoader().getResourceAsStream(resourceName);

        if (is != null)
        {
            try
            {
                p = new Properties();
                p.load(is);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return p;
    }

    private static HashMap<String, Class<?>> implementMap;

    public static void printMap()
    {
        LogInfomation.printMap(TAG, implementMap);
    }

    /**
     * 初始化(建立继承或者实现的关系).是否成功
     */
    public static boolean InitComplete = false;

    /**
     * 
     * 做初始化, 主要是对所有的class进行继承和实现关系梳理.
     * 
     * @param context
     */
    public static void init(final Context context)
    {

        if (InitComplete)
        {
            return;
        }

        try
        {
            implementMap = new HashMap<String, Class<?>>();

            // 初始化忽略类列表.不需要循环的列表
            List<String> classIgnoreList = new ArrayList<String>();
            // 初始化忽略父类列表.不需要循环的列表
            List<String> superIgnoreList = new ArrayList<String>();

            superIgnoreList.add("java");
            superIgnoreList.add("android");
            superIgnoreList.add(DatumLine.class.getPackage().getName());
            superIgnoreList.add(Mime_DatumLine.class.getPackage().getName());
            superIgnoreList.add(JSON_DatumLine.class.getPackage().getName());
            superIgnoreList.add(Universal_DatumLine.class.getPackage().getName());

            String packageName = context.getPackageName();

            classIgnoreList.add(packageName + ".BuildConfig");
            classIgnoreList.add(packageName + ".R");

            String path = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
            // PrintInfomation.i(TAG, "sourceDir path:" + path);
            DexFile dexfile = new DexFile(path);
            Enumeration<String> entries = dexfile.entries();
            while (entries.hasMoreElements())
            {

                String name = entries.nextElement();

                if (name.startsWith(packageName)) // in this package
                {
                    Class<?> clazz = null;
                    try
                    {
                        // 需要注意的是, 在forName
                        // async之类的使用handler的时候无法进行init,会报ExceptionInInitializerError错误.
                        // 所以第二个参数为false, 就是不要初始化.避免造成错误.
                        clazz = Class.forName(name, false, InitUtil.class.getClassLoader());
                        LogInfomation.i(TAG, "putClassToMap:ClassName-->" + name + "  forName success!");
                    }
                    catch (ClassNotFoundException e)
                    {
                        LogInfomation.w(TAG, "putClassToMap:classForNameFail!");
                        e.printStackTrace();
                        continue;
                    }
                    if (clazz.isInterface())
                    {// 如果是接口
                        LogInfomation.w(TAG, "putClassToMap:class is interface, ignore!");
                        continue;
                    }
                    if (!checkClassIgnoreList(name, classIgnoreList))
                    {
                        LogInfomation.d(TAG, "allow check class:" + name);
                        putClassToMap(clazz, superIgnoreList);
                    }
                    // 检查class注解, 打印注解
                    checkFinishAnno(clazz);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        LogInfomation.i(TAG, "InstanceFactory$init:" + implementMap);
        InitComplete = true;
    }

    /**
     * 检查忽略类
     * 
     * @param name
     * @param classIgnoreList
     * @return true的话， 那就是包含
     */
    private static boolean checkClassIgnoreList(String name, List<String> classIgnoreList)
    {
        for (int i = 0; i < classIgnoreList.size(); i++)
        {
            if (name.startsWith(classIgnoreList.get(i)))
            {
                return true;
            }

        }
        return false;
    }

    /**
     * 
     * @param name
     * @param superIgnoreList
     * @return 返回true 就是包含忽略父类。
     */
    private static boolean checkSuperIgnoreList(String name, List<String> superIgnoreList)
    {
        for (int i = 0; i < superIgnoreList.size(); i++)
        {
            if (name.startsWith(superIgnoreList.get(i)))
            {
                return true;
            }

        }
        return false;
    }

    /**
     * 获取类信息,将类的接口和父类分解出来
     * 
     * @param classStr
     *            类的全名
     */
    private static void putClassToMap(Class<?> clazz, List<String> superIgnoreList)
    {

        Class<?> superClass = clazz.getSuperclass();
        putClassToMap(implementMap, superIgnoreList, clazz, superClass);
        // 获取本class的接口,处理接口的对应关系
        Class<?> classes[] = clazz.getInterfaces();
        for (int i = 0; i < classes.length; i++)
        {
            putClassToMap(implementMap, superIgnoreList, clazz, classes[i]);
        }
    }

    /**
     * 是不是匿名内部类
     * 
     * @param name
     *            类名
     * @return 如果是true, 代表这个类是匿名内部类. false反之
     */
    private static boolean isCryptonymInnerClass(String name)
    {

        String[] str = name.split("[$]");

        LogInfomation.i(TAG, "InstanceFactory#isCryptonymInnerClass:splitLength:" + str.length + "  str:" + Arrays.toString(str));

        for (int i = 0; i < str.length; i++)
        {
            if (TextUtils.isDigitsOnly(str[i]))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 将类和其对应的接口(或者父类)绑定
     * 
     * @param map
     *            要放入的map
     * @param clazz
     *            类
     * @param key
     *            接口名(或者父类)
     */
    private static void putClassToMap(HashMap<String, Class<?>> map, List<String> superIgnoreList, Class<?> valueClass, Class<?> keyClass)
    {
        if (keyClass != null && !checkSuperIgnoreList(keyClass.getName(), superIgnoreList) && !isCryptonymInnerClass(valueClass.getName())) // 保证父类是否是忽略列表,或者其本身是否是匿名内部类,如果不是,则不放入
        {
            LogInfomation.i(TAG, "putClassToMap:realPut:" + keyClass.getName() + "  class:" + valueClass);
            map.put(keyClass.getName(), valueClass);
        }
    }

    /**
     * 检查{@link com.MrYang.zhuoyu.anno.MustFinish }
     * 
     * @throws NameNotFoundException
     * @throws IOException
     * 
     * @throws FinishAnnoException
     */
    @MustFinish(mustFinishEnum = MustFinishAnnoEnum.FINISH, values = { "需要制作检查@MustFinish 注解的方法,全局检测,  获取所有的类全名, 并且实例化!!!不依赖逐个调用" })
    public static void checkFinishAnno(Class<?> clazz)
    {
        if (InitUtil.FRAME_FINAL)// 框架未发布,需要检查
        {
            return;
        }

        String clazzName = clazz.getName();
        // 检查类本身的注解,是否有
        checkAnno((MustFinish) clazz.getAnnotation(MustFinish.class), clazzName);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)// 检查类中的全局变量
        {
            field.setAccessible(true);
            checkAnno(field.getAnnotation(MustFinish.class), clazzName + "#field:" + field.getName());
            field.setAccessible(false);
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) // 检查类中的方法.
        {
            method.setAccessible(true);

            checkAnno(method.getAnnotation(MustFinish.class), clazzName + "#method:" + method.getName());

            method.setAccessible(false);
        }
    }

    /**
     * 检查@MustFinish注解的注解等级.做相应处理
     * 
     * @param finishAnno
     */
    private static void checkAnno(MustFinish finishAnno, String field_methodName)
    {
        if (finishAnno == null)
        {
            return;
        }
        finishAnno.mustFinishEnum().printStack(finishAnno, field_methodName);
    }

    /**
     * 本工厂自动读取工程包下的所有接口实现和父类继承的类.并存放.如果一个接口有多个实现,那么将只存一个(随机)实现
     * 
     * @param interfaceName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getImplInstance(String interfaceName)
    {
        try
        {
            return (T) getInstances(implementMap.get(interfaceName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个接口的实现(多实现,默认为最后一个)
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getImplInstance(Class<T> clazz)
    {

        return (T) getInstances(implementMap.get(clazz.getName()));
    }

    /**
     * 实例化本类 (仅仅是实例化本类,如果class是个接口,需要实例化实现,请调用 {@link InstanceFactory#getImplInstance(Class)})
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstances(Class<T> clazz)
    {
        try
        {
            if (clazz == null)
            {
                return null;
            }
            Constructor<T> c = (Constructor<T>) clazz.getDeclaredConstructors()[0];
            c.setAccessible(true);
            T t = c.newInstance(new Object[0]);
            c.setAccessible(false);
            return t;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
