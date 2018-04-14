package com.MrYang.zhuoyu.view.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.enu.MustFinishAnnoEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@MustFinish(mustFinishEnum = MustFinishAnnoEnum.NOT_RUN, values = { "与图片资源优化一起制作." })
public @interface BackgroundStrs
{

}
