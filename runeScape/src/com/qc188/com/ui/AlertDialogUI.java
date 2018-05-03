package com.qc188.com.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

// alert左边按钮的设置
public abstract class AlertDialogUI
{
    protected Object m_para;
    private String msgContent;
    private int resourceID = -1;

    public AlertDialogUI(Object para, String msgContent)
    {
        m_para = para;
        this.msgContent = msgContent;
    }

    public String negativeName()
    {
        return null;
    }

    public String positiveName()
    {
        return null;
    }

    public String msgContentTitle()
    {
        return null;
    }

    public AlertDialogUI(Object para, int resourceID)
    {
        m_para = para;
        this.resourceID = resourceID;
    }

    public boolean hideEditInput()
    {
        return false;
    }

    public ArrayList<View> igonreView()
    {
        return null;
    }

    public void onSoftInput()
    {

    }

    public View getOtherView()
    {
        return null;
    }

    /**
     * 左边按钮按下的时候的操作
     */
    public void negativeButton()
    {

    }

    /**
     * 是否将高度设置为包裹内容.(当内容高度过高,则进入默认高度.最高高度.)
     * 
     * @return
     */
    public boolean setMaxHeight()
    {
        return false;
    }

    /**
     * 是否显示左上角的图片
     * 
     * @return true为显示.
     */
    public boolean visibleLeftTopImage()
    {
        return false;
    }

    /**
     * 右边按钮按下的操作
     */
    public void positiveButton()
    {

    };

    /**
     * 设置标题内容
     * 
     * @return null 为不显示标题
     */
    public abstract String getTitle();

    /**
     * 设置文本内容.
     * 
     * @return null为不显示
     */
    public final String getMsg(Context context)
    {
        if (resourceID != -1)
        {
            return context.getResources().getString(resourceID);
        }
        return msgContent;
    }

    /**
     * 可否被取消
     */

    public boolean cancelAble()
    {
        return false;
    }

    public void hideChat()
    {

    }

    public void onAlertDialogDismiss()
    {
        // do nothing
    }
}
