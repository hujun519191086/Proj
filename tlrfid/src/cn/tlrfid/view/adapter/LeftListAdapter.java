package cn.tlrfid.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.framework.FrameAdapter;

public class LeftListAdapter extends FrameAdapter {
	private String[] list;
	
	public LeftListAdapter(Context context, AbsListView alv, String[] list) {
		super(context, alv, 0);
		setItemHeightInDP(45);
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.length;
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		convertView = setConvertView_Holder(convertView, null, R.layout.item_left_list);
		TextView tv = (TextView) convertView;
		tv.setText("" + list[position]);
		if (position != 0) {
			convertView.setBackgroundResource(R.drawable.left_list_click_selector);
		}
		return convertView;
	}
}
