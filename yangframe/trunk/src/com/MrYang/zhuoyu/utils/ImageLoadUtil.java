package com.MrYang.zhuoyu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * 图片下载器
 * 
 * @author mryang
 * 
 */
public class ImageLoadUtil
{

    private static LruCache<String, Bitmap> lruCache;
    private static ImageLoader imageLoader;

    /**
     * 默认下载配置。
     * 
     * @param url
     *            路径
     * @param view
     *            要设置的imageview
     * @param defaultPic
     *            未下载完毕时的图片
     */
    public static void loadImageFromDefault(String url, ImageView view, int defaultPic)
    {
        // DisplayImageOptions diOptions = new DisplayImageOptions.Builder().showImageOnLoading(defaultPic).showImageForEmptyUri(defaultPic).showImageOnFail(defaultPic).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
        // ImageLoader.getInstance().displayImage(url, view, diOptions);

        // RequestQueue mRequestQueue = Volley.newRequestQueue(null);
        // final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);
        // ImageCache imageCache = new ImageCache()
        // {
        // @Override
        // public void putBitmap(String key, Bitmap value)
        // {
        // mImageCache.put(key, value);
        // }
        //
        // @Override
        // public Bitmap getBitmap(String key)
        // {
        // return mImageCache.get(key);
        // }
        // };
        // ImageLoader mImageLoader = new ImageLoader(mRequestQueue, imageCache);
        // // imageView是一个ImageView实例
        // // ImageLoader.getImageListener的第二个参数是默认的图片resource id
        // // 第三个参数是请求失败时候的资源id，可以指定为0
        // ImageListener listener = ImageLoader.getImageListener(view, defaultPic, defaultPic);
        // mImageLoader.get(url, listener);
    }

    public static void loadImage(Context context, NetworkImageView iv, String path)
    {
        // String imageUrl = "http://d.hiphotos.baidu.com/pic/w%3D310/sign=a00aca825aafa40f3cc6c8dc9b65038c/060828381f30e9246b63e1814c086e061c95f7bf.jpg";
        if (TextUtils.isEmpty(path)|| !path.startsWith("http"))
        {
            return;
        }
        if (lruCache == null)
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int mCacheSize = maxMemory / 8;
            lruCache = new LruCache<String, Bitmap>(mCacheSize);
            ImageCache imageCache = new ImageCache()
            {
                @Override
                public void putBitmap(String key, Bitmap value)
                {
                    lruCache.put(key, value);
                }

                @Override
                public Bitmap getBitmap(String key)
                {
                    return lruCache.get(key);
                }
            };
            imageLoader = new ImageLoader(requestQueue, imageCache);
        }

        iv.setTag("url");
        iv.setImageUrl(path, imageLoader);
    }

}
