package com.MrYang.zhuoyu.view.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.ViewStub;

import com.MrYang.zhuoyu.handle.ViewStubProxy;
import com.MrYang.zhuoyu.utils.InitUtil;

/**
 * 这是控件成员变量优化策略. 传递进id,和drawable的int值,就能自动赋值赋背景. <br/>
 * 属性: id 控件id <br/>
 * 属性: drawable 控件背景<br/>
 * 
 * 
 * waring:如果是给{@link ViewStub}做的注解,那么成员变量不会被赋值,如果需要获取ViewStub,可以在{@link ViewStubProxy}类中调用{@link ViewStubProxy#inflate(int)}获取id对应的{@link ViewStub}
 * 
 * 
 * @author jieranyishen
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ResetControl
{
    int id();

    int[] drawable() default InitUtil.ERROR_VALUES;

    boolean isSrc() default false;

    boolean clickThis() default false;

    boolean drawableKeepPadding() default true;
}
