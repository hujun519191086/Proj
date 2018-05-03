package com.qc188.com.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.KouBeiBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.util.DensityUtil;

public class BrandDetailKouBeiAdapter extends FrameAdapter {

	private List<KouBeiBean> list;
	private Context context;
	private String fromWhere, whereName;

    public BrandDetailKouBeiAdapter(Context context, List<KouBeiBean> list, String fromWhere, String whereName)
    {
		this.list = list;
		this.context = context;
		this.fromWhere = fromWhere;
		this.whereName = whereName;
		setItemHeigth(DensityUtil.dip2px(40));
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView tv = new TextView(context);

		setLayoutParams(tv);
		list.get(position).setFromWhere(fromWhere);
		list.get(position).setWhereName(whereName);

		tv.setText(list.get(position).getReputName());

		tv.setTag(R.id.tag_first, list.get(position));
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setPadding(DensityUtil.dip2px(10), 0, 0, 0);
		tv.setSingleLine();
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(17);

		return tv;
	}

}
