package com.hebeitv.news.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.TextView;
import android.widget.Toast;

import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.InitUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.TVApplication;
import com.hebeitv.news.view.upload.TakeVideoActivity;

public class WindowUtil
{
    public static final String TAG = WindowUtil.class.getSimpleName();

    private static WindowUtil pwu = new WindowUtil();

    private WindowUtil()
    {

    }

    public static WindowUtil ins()
    {
        return pwu;
    }

    ArrayList<AlertDialog> dialogList = new ArrayList<AlertDialog>();
    private int itemPosition = 0;

    public void alertTakephoto(FrameActivity acti, boolean showVideo, int imagePosition)
    {
        itemPosition = imagePosition;
        AlertDialog dialog = new AlertDialog.Builder(acti).setCancelable(true).create();
        dialogList.add(dialog);
        View contentView = LayoutInflater.from(InitUtil.getInitUtil().context).inflate(R.layout.tak_photo_pop, null);

        if (!showVideo)
        {
            contentView.findViewById(R.id.tv_take_media).setVisibility(View.GONE);
        }
        else
        {
            contentView.findViewById(R.id.tv_take_media).setOnClickListener(new OnAlertControlClick(dialog, acti));
        }
        contentView.findViewById(R.id.tv_take_photo).setOnClickListener(new OnAlertControlClick(dialog, acti));
        contentView.findViewById(R.id.tv_take_inPictures).setOnClickListener(new OnAlertControlClick(dialog, acti));
        contentView.findViewById(R.id.tv_take_cancel).setOnClickListener(new OnAlertControlClick(dialog, acti));
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.anchorTast_pop_anim);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.widthPixels;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setContentView(contentView, lp);
    }

    public void alertTakephoto(FrameActivity acti)
    {
        alertTakephoto(acti, true, 0);
    }

    private class OnAlertControlClick implements OnClickListener
    {

        private AlertDialog m_dialog;
        private FrameActivity m_activity;

        public OnAlertControlClick(AlertDialog dialog, FrameActivity activity)
        {
            m_dialog = dialog;
            m_activity = activity;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_take_media:
                    m_activity.startActivity(TakeVideoActivity.class);
                    break;
                case R.id.tv_take_photo:
                    PhotoUtil.takePhoto(m_activity, itemPosition);
                    break;
                case R.id.tv_take_inPictures:
                    PhotoUtil.toSystemPic(m_activity, itemPosition);
                    break;
                case R.id.tv_take_cancel:

                    break;

                default:
                    break;
            }
            if (m_dialog != null && m_dialog.isShowing())
            {
                m_dialog.dismiss();
            }
        }
    }

    public static void showToast(Activity activity, String content)
    {
        LogInfomation.i(TAG, "content:" + content);
        Toast toast = new Toast(activity);

        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);

        View view = View.inflate(activity, R.layout.toast, null);
        TextView tv_toast_content = (TextView) view.findViewById(R.id.tv_toast_content);
        tv_toast_content.setText(content);
        toast.setView(view);
        toast.show();

        // Toast.makeText(activity, content, Toast.LENGTH_SHORT).setView(R.layout.toast);
    }

    private ArrayList<AlertDialog> loadingAlertList = new ArrayList<AlertDialog>();

    public void showLoading()
    {
        LogInfomation.i("WindowUtil", "WindowUtil showLoadingInitUtil.getInitUtil():" + InitUtil.getInitUtil() + "     InitUtil.getInitUtil().context:" + ((TVApplication) InitUtil.getInitUtil().context).getWorkingActivity());
        AlertDialog loadingAlert = new AlertDialog.Builder(((TVApplication) InitUtil.getInitUtil().context).getWorkingActivity(), R.style.DialogStyle).create();
        loadingAlertList.add(loadingAlert);
        loadingAlert.setCancelable(false);
        try
        {
            loadingAlert.show();

        }
        catch (BadTokenException e)
        {
            LogInfomation.e(TAG, "e:" + e);
        }
        loadingAlert.setContentView(R.layout.loading_progress);
    }

    public void dissmissLoading()
    {
        if (loadingAlertList.size() > 0)
        {
            AlertDialog dialog = loadingAlertList.remove(0);
            if (dialog != null && dialog.isShowing())
            {
                LogInfomation.d(TAG, "WindowUtil  dissmissLoading");
                dialog.dismiss();
            }
        }

    }
}
