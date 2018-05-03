package com.hebeitv.news.control;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class RoundImageDrawable extends Drawable
{

    public String TAG = "RoundImageDrawable";
    private Paint mPaint;
    private Bitmap mBitmap;

    private RectF rectF = new RectF();
    protected RectF mBitmapRect;

    public RoundImageDrawable(Bitmap bitmap)
    {
        this(bitmap, 0);
    }

    private int round;
    private BitmapShader bitmapShader;

    public RoundImageDrawable(Bitmap bitmap, int round)
    {
        mBitmap = bitmap;
        bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
        this.round = round;

        mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom)
    {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    protected void onBoundsChange(Rect bounds)
    {
        super.onBoundsChange(bounds);

        rectF.set(0, 0, bounds.width(), bounds.height());

        Matrix shaderMatrix = new Matrix();
        shaderMatrix.setRectToRect(mBitmapRect, rectF, Matrix.ScaleToFit.FILL);
        bitmapShader.setLocalMatrix(shaderMatrix);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRoundRect(rectF, round, round, mPaint);
    }

    @Override
    public int getIntrinsicWidth()
    {
        return mBitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight()
    {
        return mBitmap.getHeight();
    }

    @Override
    public void setAlpha(int alpha)
    {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf)
    {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }

}
