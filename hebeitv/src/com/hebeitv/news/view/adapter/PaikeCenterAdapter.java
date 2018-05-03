package com.hebeitv.news.view.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MrYang.zhuoyu.adapter.FrameAdapter;
import com.MrYang.zhuoyu.handle.ViewHolder;
import com.MrYang.zhuoyu.utils.DensityUtil;
import com.MrYang.zhuoyu.utils.ImageLoadUtil;
import com.MrYang.zhuoyu.utils.LogInfomation;
import com.hebeitv.news.R;
import com.hebeitv.news.bean.PaikeCenterBean;

public class PaikeCenterAdapter extends FrameAdapter<PaikeCenterBean>
{

    public PaikeCenterAdapter(List<PaikeCenterBean> list)
    {
        super(list, DensityUtil.dip2px(72));

    }

    @Override
    public View getItemView(int arg0, View arg1, ViewGroup arg2)
    {
        arg1 = setConvertView_Holder(arg1, R.layout.item_paik_center);

        PaikeCenterBean pcb = getItem(arg0);
        ViewHolder holder = (ViewHolder) arg1.getTag();
        com.android.volley.toolbox.NetworkImageView iv = holder.getView(R.id.item_paike_center_img);
        LogInfomation.d("paikecenterAdapter", "iv:" + iv + "   pcb.getpPath():" + pcb.getpPath());
        ImageLoadUtil.loadImage(getContext(), iv, pcb.getpPath());

        TextView tv = holder.getView(R.id.item_paike_center_text);
        tv.setText(pcb.getTitle());
        arg1.setTag(R.id.tag_first, pcb);
        return arg1;
    }
}
