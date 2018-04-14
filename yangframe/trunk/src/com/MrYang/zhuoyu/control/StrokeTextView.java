package com.MrYang.zhuoyu.control;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class StrokeTextView extends TextView
{

    private boolean drawStroke = true;
    private int strokeColor = 0XFFFFFFFF;
    private int strokeWidth = 3;
    private boolean isBold = true;
    private boolean boldGetValue = false;

    public void setDrawStroke(boolean drawStroke)
    {
        this.drawStroke = drawStroke;
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setBold()
    {
        boldGetValue = true;
        isBold = true;
    }

    public void setText(CharSequence text, BufferType type)
    {

        super.setText(" " + text + " ", type);
    }

    private void init(Context context, AttributeSet attrs)
    {
        setIncludeFontPadding(false);

    }

    public StrokeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public StrokeTextView(Context context)
    {
        super(context);

        init(context);
    }

    private void init(Context context)
    {
    }

    public void setStrokeColor(int color)
    {
        strokeColor = color;
    }

    Paint m_TextPaint = getPaint();

    public void closeStrokePaint()
    {
        drawStroke = false;
    }

    public void openStrokePaint()
    {
        drawStroke = true;
    }

    public void setStrokeWidth(int strokeWidth)
    {
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        Log.d("textColor", "ondraw:" + strokeColor + "...text:" + getText().toString() + "...width:" + strokeWidth);
        if (drawStroke)
        {
            int currentTextColor = getCurrentTextColor();
            // super.setTextColor(Color.BLUE); //导致递归
            setTextColorUseReflection(strokeColor);
            m_TextPaint.setStrokeWidth(strokeWidth); // 描边宽度
            m_TextPaint.setStyle(Style.FILL_AND_STROKE); // 描边种类
            m_TextPaint.setFakeBoldText(true); // 外层text采用粗体
            super.onDraw(canvas);

            // 描内层，恢复原先的画笔

            // super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(currentTextColor);
            m_TextPaint.setStrokeWidth(0);
            m_TextPaint.setStyle(Style.FILL);

            if (boldGetValue)
            {
                m_TextPaint.setFakeBoldText(isBold);
            }
        }
        else
        {
            if (boldGetValue)
            {
                m_TextPaint.setFakeBoldText(isBold);
            }
        }
        super.onDraw(canvas);
    }

    // 使用反射设置值
    private void setTextColorUseReflection(int color)
    {
        Field textColorField;
        try
        {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        m_TextPaint.setColor(color);
    }
}
