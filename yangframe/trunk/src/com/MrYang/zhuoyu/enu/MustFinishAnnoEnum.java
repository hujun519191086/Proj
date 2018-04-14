package com.MrYang.zhuoyu.enu;

import java.util.Arrays;

import com.MrYang.zhuoyu.anno.MustFinish;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;

/**
 * 给予{@link com.MrYang.zhuoyu.anno.MustFinish }注解制作的一个枚举.
 * 
 * @author jieranyishen
 * 
 */
public enum MustFinishAnnoEnum
{

    /**
     * 還沒有做
     */
    NOT_DO("代码未做"),

    /**
     * 已完成
     */
    FINISH("代码已做."),

    /**
     * 警告--做了一半
     */
    WARING("代码只做了一半."),

    /**
     * 不完成允许被运行!!
     */
    NOT_RUN("代码未完成,不能正常使用!");

    public String msg;

    MustFinishAnnoEnum(String msg)
    {
        this.msg = msg;
    }

    /**
     * 检查@MustFinish注解的注解等级.做相应处理
     * 
     * @param finishAnno
     *            注解
     * @param field_methodName
     *            位置信息
     */
    public void printStack(MustFinish finishAnno, String field_methodName)
    {
        String annoPre = "MustFinish注解内容:";
        String newLineSpace = String.format("%" + annoPre.length() + "s", "");
        String printStr = annoPre + Arrays.toString(finishAnno.values()) + "\n" + newLineSpace + "注解类型:" + finishAnno.mustFinishEnum().msg + "\n" + newLineSpace + "注解位置:" + field_methodName + "\n   ";
        switch (this)
        {
            case NOT_DO:
                LogInfomation.d(InitUtil.MUSTFINISH_ANNO_TAG, printStr);
                break;
            case FINISH:
                LogInfomation.i(InitUtil.MUSTFINISH_ANNO_TAG, printStr);
                break;
            case WARING:
                LogInfomation.w(InitUtil.MUSTFINISH_ANNO_TAG, printStr);
                break;
            case NOT_RUN:
                LogInfomation.e(InitUtil.MUSTFINISH_ANNO_TAG, printStr);
//                throw new FinishAnnoException(field_methodName);
        }

    }
}
