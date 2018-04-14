package com.MrYang.zhuoyu.view;

import com.MrYang.zhuoyu.utils.LogInfomation;

import android.os.Bundle;

public class AActivity extends BaseActivity
{

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        super.onCreateView(savedInstanceState);

        LogInfomation.e(TAG, "skjafdkjsdkf");
    }
    // protected static final String TAG = "MainActivity";
    //
    // @ResetControl(id = R.id.tv, drawable = { R.drawable.bg_skyyard_shuzhidiban }, clickThis = true)
    // private TextView helloWord;
    //
    // protected void onCreateView(android.os.Bundle savedInstanceState)
    // {
    // setContentView(R.layout.activity_main);
    //
    // drawableList.add(R.drawable.acm_sign_content_bg);
    // drawableList.add(R.drawable.bg_chuangjian_kongxinyuan);
    // drawableList.add(R.drawable.bg_chuangjian_liangxian);
    // drawableList.add(R.drawable.bg_chuangjian_shixinyuan);
    // drawableList.add(R.drawable.bg_common_anchormsgback);
    // drawableList.add(R.drawable.bg_common_bottomverticalline);
    // drawableList.add(R.drawable.bg_common_headblank);
    // drawableList.add(R.drawable.bg_common_pink);
    // drawableList.add(R.drawable.bg_common_pink_revert);
    // drawableList.add(R.drawable.bg_common_redmark);
    // drawableList.add(R.drawable.bg_common_shuruback);
    // drawableList.add(R.drawable.bg_common_shuruback);
    // drawableList.add(R.drawable.bg_common_shurukuang);
    // drawableList.add(R.drawable.bg_common_xinxiback_press);
    // drawableList.add(R.drawable.bg_gift_titleback);
    // drawableList.add(R.drawable.bg_guizu_shenlanback);
    // drawableList.add(R.drawable.bg_common_xinxipinkback_press);
    // drawableList.add(R.drawable.bg_contact_mytalk);
    // drawableList.add(R.drawable.bg_contact_mytalk_part);
    // drawableList.add(R.drawable.bg_contact_othertalk);
    // drawableList.add(R.drawable.bg_contact_othertalk_part);
    // drawableList.add(R.drawable.bg_gift_sanjiao);
    // drawableList.add(R.drawable.bg_gift_titleback);
    // drawableList.add(R.drawable.bg_gift_titleback);
    // drawableList.add(R.drawable.bg_houyuantuan_jianjie);
    // drawableList.add(R.drawable.bg_huiliu_blue);
    // drawableList.add(R.drawable.bg_huiliu_chongwuer);
    // drawableList.add(R.drawable.bg_skyyard_jindudikuang);
    // drawableList.add(R.drawable.bg_skyyard_num4);
    // drawableList.add(R.drawable.bg_skyyard_num6);
    //
    // for (int i = 0; i < drawableList.size(); i++)
    // {
    // int id = getResources().getIdentifier("view" + (i + 1), "id", getPackageName());
    // LogInfomation.d("MainActivity", drawableList.get(i) + "");
    // View view = findById(id);
    // LogInfomation.d("control", "MainActivity:  findById(id):" + view);
    // view.setBackgroundDrawable(M.getManger().getDrawable(this.getClass(), view, drawableList.get(i)));
    // }
    // // lv.setAdapter(new MyBaseAdapter());
    // }
    //
    // @Override
    // protected void init()
    // {
    //
    // }
    //
    // @Override
    // public void onViewClick(View v)
    // {
    // setIntentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    // startActivity(SecondeActivity.class);
    // }
    //
    // private ArrayList<Integer> drawableList = new ArrayList<Integer>();
    //
    // private class MyBaseAdapter extends FrameAdapter<Integer>
    // {
    // public MyBaseAdapter()
    // {
    // // super(AutoSetValues.setListValues(new ArrayList<String>(), String.class, 50), 10);
    // super(drawableList, 100);
    //
    // }
    //
    //
    // @Override
    // public View getItemView(int position, View convertView, ViewGroup parent)
    // {
    // convertView = setConvertView_Holder(convertView, R.layout.list_item);
    //
    // ViewHolder viewHolder = (ViewHolder) convertView.getTag();
    //
    // // ImageLoadUtil.aa(getApplicationContext(), niv, "drawable://" + getItem(position));
    //
    // return convertView;
    // }
    // }

}
