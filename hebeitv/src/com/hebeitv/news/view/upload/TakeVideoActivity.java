package com.hebeitv.news.view.upload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.MrYang.zhuoyu.other.SirPair;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;
import com.hebeitv.news.frame.Global_hebei;
import com.hebeitv.news.view.control.MovieRecorderView;
import com.hebeitv.news.view.control.MovieRecorderView.OnRecordFinishListener;

public class TakeVideoActivity extends FrameActivity
{

    @ResetControl(id = R.id.recorder_time)
    private View recorder_time;

    @ResetControl(id = R.id.movieRecorderView)
    private MovieRecorderView mRecorderView;

    @ResetControl(id = R.id.shoot_button)
    private Button mShootBtn;
    private boolean isFinish = true;

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        setCenterContent(R.layout.take_photo);
        recorder_time.clearAnimation();
        recorder_time.setVisibility(View.INVISIBLE);
        mShootBtn.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    recorder_time.setVisibility(View.VISIBLE);
                    recorder_time.startAnimation(getTimeAnimation());
                    mRecorderView.record(new OnRecordFinishListener()
                    {

                        @Override
                        public void onRecordFinish()
                        {
                            handler.sendEmptyMessage(1);

                        }
                    });
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE)// 检测移动
                {

                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (mRecorderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else
                    {
                        if (mRecorderView.getVecordFile() != null) mRecorderView.getVecordFile().delete();
                        mRecorderView.stop();
                        Toast.makeText(TakeVideoActivity.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
                    }
                    recorder_time.clearAnimation();
                    recorder_time.setVisibility(View.GONE);
                }
                return true;
            }

            private Animation getTimeAnimation()
            {
                TranslateAnimation sa = new TranslateAnimation(0, DensityUtil.widthPixels, 0, 0);
                sa.setDuration(7500);
                sa.setInterpolator(new LinearInterpolator());
                sa.setRepeatMode(Animation.REVERSE);
                sa.setFillAfter(true);
                return sa;
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        isFinish = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        isFinish = false;
        mRecorderView.stop();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (recorder_time != null)
            {
                recorder_time.clearAnimation();
                recorder_time.setVisibility(View.GONE);
            }
            finishActivity();
        }
    };

    @SuppressWarnings("unchecked")
    private void finishActivity()
    {
        if (isFinish)
        {
            mRecorderView.stop();
            startActivity(UploadPhotoActivity.class, new SirPair<String, String>(Global_hebei.VIDEO_FILE_KEY, mRecorderView.getVecordFile().toString()));
            finish();
        }
    }

    /**
     * 录制完成回调
     * 
     * @author liuyinjun
     * 
     * @date 2015-2-9
     */
    public interface OnShootCompletionListener
    {
        public void OnShootSuccess(String path, int second);

        public void OnShootFailure();
    }
}
