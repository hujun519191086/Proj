package com.MrYang.zhuoyu.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.MrYang.zhuoyu.enu.MustFinishAnnoEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE })
public @interface MustFinish
{
    /**
     * 备注
     * 
     * @return
     */
    String[] values();

    /**
     * 此备注起的作用
     * 
     * @return
     */
    MustFinishAnnoEnum mustFinishEnum() default MustFinishAnnoEnum.NOT_DO;

}
