package com.hebeitv.news.view.control;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.hebeitv.news.R;

public class ImageDownloadView extends NetworkImageView
{

    public ImageDownloadView(Context context)
    {
        super(context);
        init();
    }

    public ImageDownloadView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public ImageDownloadView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {

        setDefaultImageResId(R.drawable.no_pic);
        setErrorImageResId(R.drawable.no_pic);
    }

}
