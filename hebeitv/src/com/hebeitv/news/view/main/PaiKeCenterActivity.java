package com.hebeitv.news.view.main;

import java.util.ArrayList;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.PaikeCenterBean;
import com.hebeitv.news.control.RefreshLayout;
import com.hebeitv.news.control.RefreshLayout.OnLoadListener;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.Global_hebei;
import com.hebeitv.news.module.IMessage;
import com.hebeitv.news.utils.PhotoUtil;
import com.hebeitv.news.utils.WindowUtil;
import com.hebeitv.news.view.WebActivity;
import com.hebeitv.news.view.adapter.PaikeCenterAdapter;
import com.hebeitv.news.view.upload.UploadPhotoActivity;

/**
 * 拍客中心
 * 
 * @author jieranyishen
 * 
 */
public class PaiKeCenterActivity extends FrameActivity implements OnItemClickListener, OnRefreshListener, OnLoadListener
{

    @ResetControl(id = R.id.lv_paike_center)
    private ListView lv_paike_center;
    @ResetControl(id = R.id.rl_paike_center_refresh)
    private RefreshLayout refresh;
    private PaikeCenterAdapter paikeAdapter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        includeTop("河北电视台新闻中心", false, true);
        includeBottom(2, CenterActivity.class, NativeShowingActivity.class, PaiKeCenterActivity.class);
        setCenterContent(R.layout.main_paike_center);
        refresh.setOnRefreshListener(this);
        refresh.setOnLoadListener(this, null);
    }

    private int nowPage = -1;
    private boolean isReload = false;

    @Override
    protected void init()
    {
        setCommonTopPicture();
        paikeAdapter = new PaikeCenterAdapter(null);
        lv_paike_center.setAdapter(paikeAdapter);
        lv_paike_center.setOnItemClickListener(this);
        onLoad();
        LogInfomation.d(TAG, "log test");
    }

    @SuppressWarnings("unchecked")
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

                    startActivity(UploadPhotoActivity.class, new SirPair<String, String>(Global_hebei.VIDEO_FILE_KEY, nearPath));
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

                startActivity(UploadPhotoActivity.class, new SirPair<String, String>(Global_hebei.VIDEO_FILE_KEY, path));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        PaikeCenterBean pcb = (PaikeCenterBean) arg1.getTag(R.id.tag_first);
        startActivity(WebActivity.class, new SirPair<String, String>("url", pcb.getpPath()));
    }

    @Override
    public <T> void onSuccess(int messageId, T bean)
    {
        refresh.setRefreshing(false);
        refresh.setLoading(false);
        LogInfomation.d(TAG, "paikeCenterdataList:" + bean);
        @SuppressWarnings("unchecked")
        ArrayList<PaikeCenterBean> paikeCenterList = (ArrayList<PaikeCenterBean>) bean;

        if (isReload)
        {
            nowPage = 0;
            paikeAdapter.setList(paikeCenterList);
        }
        else
        {
            nowPage++;
            paikeAdapter.addList(paikeCenterList);

        }
    }

    @Override
    public void onFault(int messageId, int errorCode, Header[] headers, String reslutContent, Exception e)
    {
        refresh.setRefreshing(false);
        refresh.setLoading(false);
        super.onFault(messageId, errorCode, headers, reslutContent, e);
    }

    @Override
    public void onRefresh()
    {
        isReload = true;
        IMessage message = getModule(IMessage.class);
        message.loadPaikeCenter(1, 0 + "", this);
    }

    @Override
    public void onLoad()
    {
        isReload = false;
        IMessage message = getModule(IMessage.class);
        message.loadPaikeCenter(1, (nowPage + 1) + "", this);
    }
}
