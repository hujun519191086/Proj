package com.MrYang.zhuoyu.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法键盘彈出和隱藏轉換
 * 
 * @author jieranyishen
 * 
 */
public class EditHideUtil
{

    public static Boolean positionIsInView(View view, int x, int y)
    {
        if (view != null)
        {
            int[] leftTop = { 0, 0 };
            view.getLocationInWindow(leftTop);

            int left = leftTop[0], top = leftTop[1], bottom = top + view.getMeasuredHeight(), right = left + view.getMeasuredWidth();
            if (x > left && x < right && y > top && y < bottom && view.getVisibility() == View.VISIBLE)
            {
                return true;
            }

        }
        return false;

    }

    public static Boolean hideInputMethod(Context context, View v)
    {
        if (v != null)
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && v != null)
            {
                return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        }
        return false;
    }

    public static void visibleInputMethod(Context context, View v)
    {
        if (v != null)
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
            {
                imm.showSoftInput(v, InputMethodManager.RESULT_SHOWN);
            }
        }
    }

    /**
     * 
     * @param context
     */
    public static void otherInputMethod(Context context)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
        {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
