package com.MrYang.zhuoyu.Manager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.MrYang.zhuoyu.utils.BitmapUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer.RoundedDrawable;

public class BitmapMsg
{
    private HashMap<String, View> useList;
    private ArrayList<Bitmap> bitmaps;
    private Integer[] resIds;
    public static final String TAG = BitmapMsg.class.getSimpleName();
    private boolean usePath;
    private String imagePath;

    public BitmapMsg(Class<? extends Object> bActitivity, Integer... resId)
    {
        usePath = false;
        resIds = resId;
        useList = new HashMap<String, View>();
    }

    public BitmapMsg(Class<? extends Object> bActitivity, String path)
    {
        usePath = true;
        imagePath = path;
        useList = new HashMap<String, View>();
    }

    private void createBitmaps()
    {
        if (bitmaps == null)
        {
            bitmaps = new ArrayList<Bitmap>();
            LogInfomation.d(TAG, "createBitMap!!!");
            if (usePath && !TextUtils.isEmpty(imagePath))
            {
                bitmaps.add(BitmapUtil.readBitMap(InitUtil.getInitUtil().context, imagePath));
            }
            else
            {
                if (resIds.length > 0)
                {
                    for (int i = 0; i < resIds.length; i++)
                    {
                        bitmaps.add(BitmapUtil.readBitMap(InitUtil.getInitUtil().context, resIds[i]));
                    }
                }
            }
        }
    }

    private void deleteBitmaps()
    {
        if (bitmaps != null)
        {
            LogInfomation.d(TAG, "recycleBitMap!!!bitmaps:" + bitmaps + "  bitmaps.size:" + bitmaps.size());

            for (int i = 0; i < bitmaps.size(); i++)
            {
                Bitmap map = bitmaps.get(i);
                if (map != null && !map.isRecycled())
                {
                    map.recycle();
                }
            }
            bitmaps.clear();
            bitmaps = null;
        }
    }

    private final int normalPosition = 0;
    private final int pressPosition = 1;
    private final int enablePosition = 2;
    private final int cornerRadiusPosition = 3;
    private final int marginPosition = 4;

    public Bitmap getBitmap(Class<? extends Object> beloneName, View useView)
    {
        createBitmaps();
        useList.put(useControlKey(beloneName, useView), useView);
        if (bitmaps != null && bitmaps.size() > 0)
        {
            return bitmaps.get(0);
        }
        return null;
    }

    public Drawable getDrawable(Class<? extends Object> beloneName, View useView)
    {
        createBitmaps();
        useList.put(useControlKey(beloneName, useView), useView);
        LogInfomation.d(TAG, "res:" + Arrays.toString(resIds) + "     listSize:" + useList.size());
        // useList.add(bActitivity); //要修改
        int cornerRadius = 0;
        int margin = 0;
        Bitmap normalBitmap = null;
        Bitmap pressBitmap = null;
        Bitmap enableBitmap = null;
        if (bitmaps == null)
        {
            return null;
        }
        normalBitmap = bitmaps.get(normalPosition);
        int switchCase = bitmaps.size() - 1;
        switch (switchCase)
        {
            case pressPosition:
                pressBitmap = bitmaps.get(pressPosition);
                break;
            case marginPosition:
                margin = resIds[marginPosition];
            case cornerRadiusPosition:
                cornerRadius = resIds[cornerRadiusPosition];
            case enablePosition:
                pressBitmap = bitmaps.get(pressPosition);
                enableBitmap = bitmaps.get(enablePosition);
                break;
            default:
                break;
        }
        return getCheckDrawable(normalBitmap, pressBitmap, enableBitmap, margin, cornerRadius);
    }

    private String useControlKey(Class<? extends Object> beloneUi, View useView)
    {
        String useControlKey = beloneUiKeyName(beloneUi) + "_" + useView.hashCode();
        LogInfomation.d(TAG, "useControlKey:" + useControlKey);
        return useControlKey;
    }

    private String beloneUiKeyName(Class<? extends Object> beloneUi)
    {
        return beloneUi.getName();

    }

    private Drawable getCheckDrawable(Bitmap normal, Bitmap press, Bitmap disableId, int margin, int cornerRadius)
    {
        Drawable normalD = getRelaDrawable(cornerRadius, normal, margin);
        Drawable pressD = getRelaDrawable(cornerRadius, press, margin);

        Drawable disable = getRelaDrawable(cornerRadius, disableId, margin);

        if (normalD != null)
        {
            if (pressD != null)
            {
                StateListDrawable bg = new StateListDrawable();
                bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressD);
                bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, pressD);
                bg.addState(new int[] { android.R.attr.state_enabled }, normalD);
                bg.addState(new int[] { android.R.attr.state_focused }, pressD);

                if (disable != null)
                {
                    bg.addState(new int[] {}, disable);
                }
                else
                {
                    bg.addState(new int[] {}, normalD);
                }
                return bg;
            }
            else
            {
                return normalD;
            }
        }
        else
        {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private Drawable getRelaDrawable(int cornerRadius, Bitmap bitmap, int margin)
    {
        if (bitmap == null)
        {
            return null;
        }
        Drawable imageDrawable = null;
        if (cornerRadius == 0)
        {
            byte[] chunk = bitmap.getNinePatchChunk();
            if (chunk != null && NinePatch.isNinePatchChunk(chunk))
            {
                imageDrawable = new NinePatchDrawable(InitUtil.getInitUtil().context.getResources(), bitmap, chunk, NinePatchChunk.deserialize(chunk).mPaddings, null);
            }
            else
            {
                imageDrawable = new BitmapDrawable(bitmap);
            }
        }
        else
        {
            imageDrawable = new RoundedDrawable(bitmap, cornerRadius, margin);
        }
        return imageDrawable;
    }

    static class NinePatchChunk
    {
        public static final int NO_COLOR = 0x00000001;
        public static final int TRANSPARENT_COLOR = 0x00000000;

        public final Rect mPaddings = new Rect();

        public int mDivX[];
        public int mDivY[];
        public int mColor[];

        private static void readIntArray(final int[] data, final ByteBuffer buffer)
        {
            for (int i = 0, n = data.length; i < n; ++i)
            {
                data[i] = buffer.getInt();
            }
        }

        private static void checkDivCount(final int length)
        {
            if (length == 0 || (length & 0x01) != 0)
            {
                throw new RuntimeException("invalid nine-patch: " + length);
            }
        }

        public static NinePatchChunk deserialize(final byte[] data)
        {
            final ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(ByteOrder.nativeOrder());

            if (byteBuffer.get() == 0)
            {
                return null; // is not serialized
            }

            final NinePatchChunk chunk = new NinePatchChunk();
            chunk.mDivX = new int[byteBuffer.get()];
            chunk.mDivY = new int[byteBuffer.get()];
            chunk.mColor = new int[byteBuffer.get()];

            checkDivCount(chunk.mDivX.length);
            checkDivCount(chunk.mDivY.length);

            // skip 8 bytes
            byteBuffer.getInt();
            byteBuffer.getInt();

            chunk.mPaddings.left = byteBuffer.getInt();
            chunk.mPaddings.right = byteBuffer.getInt();
            chunk.mPaddings.top = byteBuffer.getInt();
            chunk.mPaddings.bottom = byteBuffer.getInt();

            // skip 4 bytes
            byteBuffer.getInt();

            readIntArray(chunk.mDivX, byteBuffer);
            readIntArray(chunk.mDivY, byteBuffer);
            readIntArray(chunk.mColor, byteBuffer);

            return chunk;
        }
    }

    /**
     * 销毁 key值对应的viewlist
     * 
     * @param bActitivity
     * @return true 代表被delete了
     */
    @SuppressWarnings("deprecation")
    public boolean destoryDrawable(Class<? extends Object> beloneName)
    {

        Set<String> keySet = useList.keySet();

        ArrayList<String> removeKeyList = new ArrayList<String>();
        for (String key : keySet)
        {
            String belonePre = beloneUiKeyName(beloneName);
            if (key.startsWith(belonePre))
            {
                removeKeyList.add(key);
                View view = useList.get(key);
                LogInfomation.d(TAG, "removeKey:" + key + "  control:" + view);

                if (view != null)
                {
                    view.setBackgroundDrawable(null);

                    if (view instanceof ImageView)
                    {
                        ((ImageView) view).setImageDrawable(null);
                    }
                }
            }
        }
        for (int i = 0; i < removeKeyList.size(); i++)
        {
            useList.remove(removeKeyList.get(i));
        }

        LogInfomation.d(TAG, "useListSize:" + useList.size());
        if (useList.size() <= 0)
        {
            deleteBitmaps();
            return true;
        }
        return false;
    }

}
