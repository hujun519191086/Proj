package cn.tlrfid.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.view.CurriculumVitae_Create_Activity;
import cn.tlrfid.view.QualityCreateActivity;
import cn.tlrfid.view.SafetyCreateActivity;

public class ScanAdapter extends FrameAdapter {
	private Context mContext;
	
	private List<ProjectCardBean> list;
	
	@SuppressWarnings("unchecked")
	public ScanAdapter(Context context, List<ProjectCardBean> list) {
		super(context, null, 0);
		this.mContext = context;
		this.list = list;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
	public View getItemView(final int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.item_scan, null);
		
		TextView tv_str = (TextView) view.findViewById(R.id.tv_str);
		
		tv_str.setText(list.get(position).getName());
		
		ImageButton ib_item_delete = (ImageButton) view.findViewById(R.id.ib_item_delete);
		
		ib_item_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (context instanceof CurriculumVitae_Create_Activity) {
					
					if (((CurriculumVitae_Create_Activity) context).state == 1) {
						((CurriculumVitae_Create_Activity) context).list_project.remove(position);
						
						if (((CurriculumVitae_Create_Activity) context).list_project.size() != 0) {
							((CurriculumVitae_Create_Activity) context).tv_project
									.setText(((CurriculumVitae_Create_Activity) context).list_project.get(
											((CurriculumVitae_Create_Activity) context).list_project.size() - 1)
											.getName());
						} else {
							((CurriculumVitae_Create_Activity) context).list_project.clear();
							((CurriculumVitae_Create_Activity) context).tv_project.setText("");
						}
						
					}
					
					ScanAdapter.this.notifyDataSetChanged();
				}
				if (context instanceof QualityCreateActivity) {
					
					if (((QualityCreateActivity) context).state == 1) {
						((QualityCreateActivity) context).list_project.remove(position);
						
						if (((QualityCreateActivity) context).list_project.size() != 0) {
							((QualityCreateActivity) context).tv_project
									.setText(((QualityCreateActivity) context).list_project.get(
											((QualityCreateActivity) context).list_project.size() - 1).getName());
						} else {
							((QualityCreateActivity) context).list_project.clear();
							((QualityCreateActivity) context).tv_project.setText("");
						}
						
					}
					
					ScanAdapter.this.notifyDataSetChanged();
				}
				if (context instanceof SafetyCreateActivity) {
					
					if (((SafetyCreateActivity) context).state == 1) {
						((SafetyCreateActivity) context).list_project.remove(position);
						
						if (((SafetyCreateActivity) context).list_project.size() != 0) {
							((SafetyCreateActivity) context).tv_project
									.setText(((SafetyCreateActivity) context).list_project.get(
											((SafetyCreateActivity) context).list_project.size() - 1).getName());
						} else {
							((SafetyCreateActivity) context).list_project.clear();
							((SafetyCreateActivity) context).tv_project.setText("");
						}
						
					}
					
					ScanAdapter.this.notifyDataSetChanged();
					
				}
			}
		});
		return view;
	}
}
