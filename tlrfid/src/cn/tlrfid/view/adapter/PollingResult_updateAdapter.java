package cn.tlrfid.view.adapter;

import java.util.ArrayList;

import cn.tlrfid.R;
import cn.tlrfid.bean.PollingResult;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class PollingResult_updateAdapter extends BaseAdapter {
	private Context mContext;
	
	private ArrayList<PollingResult> mList;
	
	public PollingResult_updateAdapter(Context context, ArrayList<PollingResult> list) {
		this.mContext = context;
		this.mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_polling_update, null);
		
		TextView item_polling_create_name = (TextView) view.findViewById(R.id.item_polling_create_name);
		
		CheckBox item_polling_create_isqualify = (CheckBox) view.findViewById(R.id.item_polling_create_isqualify);
		
		CheckBox item_polling_create_ismistake = (CheckBox) view.findViewById(R.id.item_polling_create_ismistake);
		
		TextView item_polling_create_reason = (TextView) view.findViewById(R.id.item_polling_create_reason);
		
		CheckBox item_polling_create_isok = (CheckBox) view.findViewById(R.id.item_polling_create_isok);
		
		item_polling_create_name.setText(mList.get(position).getName());
		
		item_polling_create_isqualify.setChecked(mList.get(position).isQualify());
		
		item_polling_create_ismistake.setChecked(mList.get(position).isMistake());
		
		item_polling_create_reason.setText(mList.get(position).getReason());
		
		item_polling_create_isok.setChecked(mList.get(position).isOk());
		
		return view;
	}
}
