package cn.tlrfid.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.SafetyItemBean;

public class PollingResultAdapter extends BaseAdapter {
	private Context mContext;
	
	public  ArrayList<SafetyItemBean> mList;
	
	public PollingResultAdapter(Context context, ArrayList<SafetyItemBean> list) {
		
		this.mContext = context;
		this.mList = list;
		
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_polling_create, null);
		
		TextView item_polling_create_name = (TextView) view.findViewById(R.id.item_polling_create_name);
		
		final CheckBox item_polling_create_isqualify = (CheckBox) view.findViewById(R.id.item_polling_create_isqualify);
		
		final CheckBox item_polling_create_ismistake = (CheckBox) view.findViewById(R.id.item_polling_create_ismistake);
		
		if (TextUtils.isEmpty(mList.get(position).getTitle())) {
			item_polling_create_name.setText(mList.get(position).getTitle());
		} else {
			item_polling_create_name.setText(mList.get(position).getTitle() + "[" + mList.get(position).getLevelName()
					+ "]");
		}
		item_polling_create_isqualify.setChecked(mList.get(position).isQualify());
		item_polling_create_ismistake.setChecked(mList.get(position).isMistake());
		
		item_polling_create_isqualify.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if (isChecked) {
					
					if(mList.get(position).isMistake()){
						mList.get(position).setMistake(!isChecked);
						item_polling_create_ismistake.setChecked(!isChecked);
					}
				}
				mList.get(position).setQualify(isChecked);
				mChangeData.change(mList);
			}
		});
		
		item_polling_create_ismistake.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(isChecked){
					
					if(mList.get(position).isQualify()){
						mList.get(position).setQualify(!isChecked);
						item_polling_create_isqualify.setChecked(!isChecked);
					}
				}
				mList.get(position).setMistake(isChecked);
				mChangeData.change(mList);
				
			}
		});
		
		return view;
	}
	
	public interface ChangeData {
		
		public void change(ArrayList<SafetyItemBean> mList);
	}
	
	public ChangeData mChangeData;
	
	public void setDataChangeListener(ChangeData mChangeData) {
		this.mChangeData = mChangeData;
	}
}
