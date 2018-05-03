package cn.tlrfid.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.ConstructionPeson;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;

public class CreateAdapter extends FrameAdapter {
	private ArrayList<PersonCardBean> mList;
	
	public CreateAdapter(Context context, AbsListView alv, ArrayList<PersonCardBean> list) {
		super(context, alv, 0);
		this.mList = list;
	}
	
	public int getCount() {
		return mList.size();
	}
	
	public void notifyList(ArrayList<PersonCardBean> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_curriculum_vitae_create, false);
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.item_create_name.setText(mList.get(position).getPersonName());
		
		holder.item_create_style.setText(mList.get(position).getGroup());
		return convertView;
	}
	
	private static class ViewHolder extends BaseViewHolder {
		TextView item_create_name;
		TextView item_create_style;
	}
}
