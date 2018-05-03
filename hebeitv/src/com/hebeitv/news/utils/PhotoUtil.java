package com.hebeitv.news.utils;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class PhotoUtil
{
    public static final int TAKE_PHOTO_REQUEST_CODE = 1000;
    public static final int ON_SYSTEM_PIC_REQUEST_CODE = 1001;
    public static final String ON_MY_POSITION_RESULT_INTENT_KEY = "positionIntentKey";
    public static ArrayList<String> photoList = new ArrayList<String>();

    public static void takePhoto(Activity m_activity)
    {
        takePhoto(m_activity, 0);
    }

    /**
     * 照相.
     * 
     * @param m_activity
     * @param position
     *            位置
     */
    public static void takePhoto(Activity m_activity, int position)
    {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
        {
            File dir = new File(Environment.getExternalStorageDirectory() + "/cache");
            if (!dir.exists()) dir.mkdirs();

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(dir, System.currentTimeMillis() + "");// localTempImgDir和localTempImageFileName是自己定义的名字
            photoList.add(f.getPath());
            Uri u = Uri.fromFile(f);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            intent.putExtra(ON_MY_POSITION_RESULT_INTENT_KEY, position);
            m_activity.startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
        }
    }

    public static String getNearPhotoPath()
    {
        if (photoList.size() > 0)
        {
            return photoList.get(photoList.size() - 1);
        }
        return null;
    }

    public static Bitmap getFrameAtTime(String path)
    {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        Bitmap bitmap = mmr.getFrameAtTime();
        mmr.release();
        return bitmap;
    }

    public static void toSystemPic(Activity activity)
    {
        toSystemPic(activity, 0);
    }

    public static void toSystemPic(Activity activity,int imagePosition)
    {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
        i.putExtra(ON_MY_POSITION_RESULT_INTENT_KEY, imagePosition);
        activity.startActivityForResult(i, ON_SYSTEM_PIC_REQUEST_CODE);
    }
}
