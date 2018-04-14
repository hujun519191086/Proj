package com.MrYang.zhuoyu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class BitmapUtil
{

    /**
     * 压缩图片
     * 
     * @param oldbmp
     * @param w
     * @param h
     * @return
     */
    public static Bitmap compressBitmap(Bitmap oldbmp, int w, int h)
    {
        int width = oldbmp.getWidth();
        int height = oldbmp.getHeight();
        float sx = 1;
        float sy = 1;
        if (w > 0)
        {
            sx = ((float) w / width);

        }
        if (h > 0)
        {

            sy = ((float) h / height);
        }

        if (sx == 1 && sy == 1)
        {
            return oldbmp;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    /**
     * 清理view上的背景和src
     * 
     * @param view
     */
    @SuppressWarnings("deprecation")
    public static void clearView(View view)
    {
        if (view == null)
        {
            return;
        }
        view.destroyDrawingCache();
        view.clearAnimation();
        Drawable tempDrawable = view.getBackground();
        if (tempDrawable != null)
        {
            view.setBackgroundDrawable(null);
            BitmapUtil.recycleDrawable(tempDrawable);
            tempDrawable.setCallback(null);
            if (view instanceof ImageView)
            {
                ImageView iv = (ImageView) view;
                Drawable drawable = iv.getDrawable();
                iv.setImageDrawable(null);
                BitmapUtil.recycleDrawable(drawable);

            }

        }
    }

    @SuppressWarnings("deprecation")
    public static Drawable toGrayDrawable(Drawable drawable)
    {
        Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
        bmp = toGrayImage(bmp);
        return new BitmapDrawable(bmp);
    }

    @SuppressWarnings("deprecation")
    public static Drawable toGrayDrawable(Context context, int resId)
    {
        Bitmap bmp = readBitMap(context, resId);
        bmp = toGrayImage(bmp);
        return new BitmapDrawable(bmp);
    }

    @SuppressWarnings("deprecation")
    public static Drawable toGrayDrawable(Bitmap bmp)
    {
        bmp = toGrayImage(bmp);
        return new BitmapDrawable(bmp);
    }

    public static String getBitmapMemory(Bitmap image)
    {
        if (image == null)
        {
            return "0KB";
        }
        long i = image.getRowBytes() * image.getHeight();
        return i / 1024 + "KB";
    }

    public static String getDrawableMemory(Drawable d)
    {
        if (d == null)
        {
            return "0KB";
        }
        Bitmap bmp = null;
        if (d instanceof BitmapDrawable)
        {
            bmp = ((BitmapDrawable) d).getBitmap();
        }
        else
        {
            bmp = drawableToBitmap(d);
        }

        return getBitmapMemory(bmp);

    }

    public static Bitmap compressImage(Bitmap image)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100)
        {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    @SuppressWarnings("deprecation")
    public static Drawable cutDrawable(Drawable drawable)
    {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap tempMap = bd.getBitmap();

        Bitmap bmp = Bitmap.createBitmap(bd.getBitmap(), 0, 0, (int) (tempMap.getWidth() / 1.422), (int) (tempMap.getHeight() / 1.422));

        return new BitmapDrawable(bmp);
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable == null)
        {
            return null;
        }

        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        drawable.setBounds(0, 0, w, h);
        return bitmap;
    }

    /**
     * 将 Drawable标记为 recycle
     * 
     * @param drawable
     */
    public static void recycleDrawable(Drawable drawable)
    {

        if (drawable != null && drawable instanceof BitmapDrawable)
        {
            Bitmap tempMap = ((BitmapDrawable) drawable).getBitmap();

            if (!tempMap.isRecycled())
            {
                tempMap.recycle();
            }
        }
    }

    public static Bitmap toGrayImage(Bitmap bitmap)
    {
        try
        {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap grayImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(grayImg);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorMatrixFilter);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return grayImg;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 
     * 读取图片.
     */
    public static Bitmap readBitMap(Context context, int resId)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bmp = BitmapFactory.decodeStream(is, null, opt);
        return bmp;
    }
    /**
     * 
     * 读取图片.
     */
    public static Bitmap readBitMap(Context context, String path)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = 3;
        Bitmap bmp = BitmapFactory.decodeFile(path, opt);
        return bmp;
    }

    public static Bitmap BitmapreadBitMapForReal(Context context, int resId)
    {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }


    public static Bitmap readBitMap(Context context, int resId, int width, int height)
    {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bmp = BitmapFactory.decodeStream(is, null, opt);
        if (width <= 0 && height <= 0)
        {
            return bmp;
        }
        return compressBitmap(bmp, width, height);
    }

    public static Bitmap createImageThumbnail(String filePath)
    {
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        try
        {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        }
        catch (Exception e)
        {
        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8)
        {
            roundedSize = 1;
            while (roundedSize < initialSize)
            {
                roundedSize <<= 1;
            }
        }
        else
        {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound)
        {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1))
        {
            return 1;
        }
        else if (minSideLength == -1)
        {
            return lowerBound;
        }
        else
        {
            return upperBound;
        }
    }

}
