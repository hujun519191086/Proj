package cn.tlrfid.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.PersonBean;
import cn.tlrfid.framework.FrameAdapter;

public class ProjectPersonAdapter extends FrameAdapter {
	private List<PersonBean> beanList;
	
	public ProjectPersonAdapter(Context context, AbsListView alv, List<PersonBean> beanList) {
		super(context, alv, 0);
		this.beanList = beanList;
		if (beanList == null) {
			beanList = new ArrayList<PersonBean>();
		}
	}
	
	@Override
	public int getCount() {
		return beanList.size();
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.project_message_content, false);
		convertView.setBackgroundColor(Color.WHITE);
		ViewHolder vHolder = (ViewHolder) convertView.getTag();
		PersonBean pb = beanList.get(position);
		vHolder.progressmanager_groupName.setText(pb.getGroup().getGroupName());
		vHolder.progressmanager_personName.setText(pb.getPersonName());
		vHolder.progressmanager_personTagId.setText(pb.getPersonTagId());
		return convertView;
	}
	
	private static class ViewHolder extends BaseViewHolder {
		TextView progressmanager_groupName;
		TextView progressmanager_personName;
		TextView progressmanager_personTagId;
	}
	
}
