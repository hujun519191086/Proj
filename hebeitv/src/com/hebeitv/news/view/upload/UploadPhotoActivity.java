package com.hebeitv.news.view.upload;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.control.RoundImageDrawable;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.Global_hebei;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.module.ModuleManager;
import com.hebeitv.news.net.OnPostCallBack;
import com.hebeitv.news.utils.PhotoUtil;
import com.hebeitv.news.utils.WindowUtil;

public class UploadPhotoActivity extends FrameActivity implements OnGlobalLayoutListener
{

    @ResetControl(id = R.id.gv_moment_videos)
    private GridView momentVideoView;

    @ResetControl(id = R.id.iv_moment_bigPic)
    private ImageView bigPic;
    @ResetControl(id = R.id.ed_moment_think)
    private EditText et;

    private ArrayList<String> picMapList;
    private boolean isPhoto = false;
    private int maxPhotoSize = 4;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("", true, false);
        setCenterContent(R.layout.upload);
    }

    @Override
    protected void init()
    {
        picMapList = new ArrayList<String>();
        setCommonTopBack();
        setCommonTopRightText("发送").setOnClickListener(new UpLoadClick(et, picMapList, this));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String videopath = (String) bundle.get(Global_hebei.VIDEO_FILE_KEY);

        LogInfomation.d(TAG, videopath);
        isPhoto = !videopath.endsWith(".mp4");
        picMapList.add(videopath);

        momentVideoView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public static String moreImageValue = "moreClick";
    private UploadItemAdapter adapter;

    @SuppressWarnings("deprecation")
    @Override
    public void onGlobalLayout()
    {
        momentVideoView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        int width = momentVideoView.getWidth();
        int columnCount = 5;

        int columWidth = (int) ((float) width - (DensityUtil.dip2px(5) * (columnCount - 1))) / columnCount;

        if (isPhoto && picMapList.size() < maxPhotoSize)
        {
            picMapList.add(moreImageValue);
        }
        adapter = new UploadItemAdapter(picMapList, columWidth);
        momentVideoView.setAdapter(adapter);
    }

    private int jumpOutNumber = 0;

    @Override
    public void onViewClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_moment_bigPic:
                v.setOnClickListener(null);
                v.setVisibility(View.GONE);
                break;

            default:
                break;
        }
        Object tag = v.getTag(R.id.adapter_tag_1);
        if (tag != null)
        {
            if (tag instanceof Integer)// 进入adapter点击事件
            {
                String url = (String) v.getTag(R.id.adapter_tag_2);
                Integer itemNumber = (Integer) tag;
                if (v.getId() == R.id.iv_upload_delete_image)// 删除
                {
                    picMapList.remove(itemNumber.intValue());

                    if (picMapList.size() <= 0 || !picMapList.get(picMapList.size() - 1).equals(moreImageValue))
                    {
                        picMapList.add(moreImageValue);
                    }
                    if (adapter != null)
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
                else if (url.equals(moreImageValue))
                {
                    WindowUtil.ins().alertTakephoto(this, false, itemNumber);
                    jumpOutNumber = itemNumber;
                }
                else
                {
                    bigPic.setVisibility(View.VISIBLE);
                    bigPic.setBackgroundColor(Color.BLACK);
                    bigPic.setImageBitmap(ImageManager.getManger().getBitmap(this.getClass(), bigPic, url));
                    bigPic.setOnClickListener(this);

                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PhotoUtil.TAKE_PHOTO_REQUEST_CODE)// 照相返回
            {
                String nearPath = PhotoUtil.getNearPhotoPath();
                if (TextUtils.isEmpty(nearPath))
                {
                    WindowUtil.showToast(this, "查找照片路径失败!");
                }
                else
                {
                    addImagePath(nearPath);

                }

            }
            else if (requestCode == PhotoUtil.ON_SYSTEM_PIC_REQUEST_CODE)
            {
                String[] proj = { MediaStore.Images.Media.DATA };

                @SuppressWarnings("deprecation")
                Cursor cursor = managedQuery(data.getData(), proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                addImagePath(path);

            }
        }
    }

    private void addImagePath(String path)
    {

        if (picMapList.contains(path))
        {
            WindowUtil.showToast(this, "这张图片已被选中!");
            return;

        }
        if (picMapList.size() < maxPhotoSize && jumpOutNumber == picMapList.size() - 1)
        {
            picMapList.add(jumpOutNumber, path);
        }
        else
        {
            picMapList.remove(jumpOutNumber);
            picMapList.add(jumpOutNumber, path);
        }

        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    private class UploadItemAdapter extends FrameAdapter<String>
    {

        public UploadItemAdapter(ArrayList<String> picMapList, int itemHeight)
        {
            super(picMapList, itemHeight);
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getItemView(int arg0, View arg1, ViewGroup arg2)
        {
            LogInfomation.d(TAG, "contentView:" + arg1);
            arg1 = setConvertView_Holder(arg1, R.layout.item_upload_image);
            ViewHolder holder = (ViewHolder) arg1.getTag();
            LogInfomation.d(TAG, "holder:" + holder + "  position:" + arg0);
            ImageView iv = holder.getView(R.id.iv_upload_image);
            LogInfomation.d(TAG, "iv:" + iv);
            iv.setScaleType(ScaleType.FIT_XY);
            String imageUrl = getItem(arg0);

            ImageView delete = holder.getView(R.id.iv_upload_delete_image);
            if (isPhoto)
            {

                if (imageUrl.equals(moreImageValue))
                {
                    iv.setImageDrawable(getDrawable(iv, R.drawable.btn_add));
                    delete.setVisibility(View.GONE);
                    delete.setOnClickListener(null);
                }
                else
                {
                    iv.setImageDrawable(new RoundImageDrawable(ImageManager.getManger().getBitmap(UploadPhotoActivity.class, iv, imageUrl), 10));
                    delete.setTag(R.id.adapter_tag_1, arg0);
                    delete.setTag(R.id.adapter_tag_2, imageUrl);
                    delete.setVisibility(View.VISIBLE);
                    delete.setOnClickListener(UploadPhotoActivity.this);
                    delete.setBackgroundDrawable(getDrawable(delete, R.drawable._delete));
                }
                iv.setTag(R.id.adapter_tag_1, arg0);
                iv.setTag(R.id.adapter_tag_2, imageUrl);
                iv.setOnClickListener(UploadPhotoActivity.this);
            }
            else
            {
                delete.setVisibility(View.GONE);
                delete.setOnClickListener(null);
                iv.setImageBitmap(PhotoUtil.getFrameAtTime(imageUrl));
            }

            return arg1;
        }
    }

    private class UpLoadClick implements OnClickListener
    {
        EditText title;
        ArrayList<String> filePaths;
        OnPostCallBack[] backs;

        public UpLoadClick(EditText title, ArrayList<String> filePaths, OnPostCallBack... backs)
        {

            this.title = title;
            this.filePaths = filePaths;
            this.backs = backs;
        }

        @Override
        public void onClick(View v)
        {
            IMessage message = ModuleManager.getManager().getModule(IMessage.class);
            message.uploadFile(title.getText().toString(), filePaths, backs);
        }
    }

    @Override
    public void onSuccess(int messageId, String resultContent)
    {
        super.onSuccess(messageId, resultContent);
        finish();
    }
}
