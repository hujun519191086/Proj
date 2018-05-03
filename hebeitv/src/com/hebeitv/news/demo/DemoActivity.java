package com.hebeitv.news.demo;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MrYang.zhuoyu.Manager.ImageManager;
import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.MrYang.zhuoyu.view.BaseActivity;
import com.MrYang.zhuoyu.view.SecondeActivity;
import com.MrYang.zhuoyu.view.anno.ResetControl;
import com.hebeitv.news.R;
import com.hebeitv.news.frame.FrameActivity;

public class DemoActivity extends FrameActivity
{

    protected static final String TAG = "MainActivity";

    @ResetControl(id = R.id.tv, drawable = { R.drawable.ic_launcher }, clickThis = true)
    private TextView helloWord;

    @SuppressWarnings("deprecation")
    protected void onCreateView(android.os.Bundle savedInstanceState)
    {
//        setContentView(R.layout.activity_main);
//
//        drawableList.add(R.drawable.ic_launcher);
//
//        for (int i = 0; i < drawableList.size(); i++)
//        {
//            int id = getResources().getIdentifier("view" + (i + 1), "id", getPackageName());
//            LogInfomation.d("MainActivity", drawableList.get(i) + "");
//            View view = findById(id);
//            LogInfomation.d("control", "MainActivity:  findById(id):" + view);
//            view.setBackgroundDrawable(ImageManager.getManger().getDrawable(this.getClass(), view, drawableList.get(i)));
//        }
        // lv.setAdapter(new MyBaseAdapter());
    }

    @Override
    protected void init()
    {

    }

    @Override
    public void onViewClick(View v)
    {
        setIntentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(SecondeActivity.class);
    }

    private ArrayList<Integer> drawableList = new ArrayList<Integer>();

    private class MyBaseAdapter extends FrameAdapter<Integer>
    {
        public MyBaseAdapter()
        {
            // super(AutoSetValues.setListValues(new ArrayList<String>(), String.class, 50), 10);
            super(drawableList, 100);

        }

        @Override
        public View getItemView(int position, View convertView, ViewGroup parent)
        {

            // ImageLoadUtil.aa(getApplicationContext(), niv, "drawable://" + getItem(position));

            return convertView;
        }
    }
}
