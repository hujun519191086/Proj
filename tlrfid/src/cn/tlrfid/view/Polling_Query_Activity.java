package cn.tlrfid.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.CurriculumVitae_Query_Activity.ProjectViewHolder;
import cn.tlrfid.view.CurriculumVitae_Query_Activity.ProjectsAdapter;

public class Polling_Query_Activity extends BaseActivity implements OnItemClickListener {
	@ViewById_Clickthis
	private Button polling_query_tv_click_to_scan;
	
	@ViewById
	private GridView polling_query_gv_show;
	@ViewById
	private EditText polling_query_about;
	@ViewById_Clickthis
	private ImageView polling_query_arrow;
	private PollingQueryVitaeAdapter adapter;
	private List<String> subProjectList;
	
	private ProjectsAdapter mProjectAdapter;
	private PopupWindow popupWindow;
	
	private void initData() {
		adapter = new PollingQueryVitaeAdapter(getApplicationContext(), polling_query_gv_show,
				R.dimen.gridview_item_hight);
		polling_query_gv_show.setAdapter(adapter);
		
		title_bar_content.setText("巡检一览");
	}
	
	private void setListener() {
		polling_query_gv_show.setOnItemClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.polling_query_tv_click_to_scan:
			// 扫描设备
			break;
		case R.id.polling_query_arrow:
			// 弹出选择对话框
			showSelectNumberDialog();
			break;
		}
	}
	
	@SuppressWarnings("deprecation")
	private void showSelectNumberDialog() {
		subProjectList = getProjects();
		
		ListView lv = new ListView(this);
		lv.setBackgroundResource(R.drawable.icon_spinner_listview_background);
		// 隐藏滚动条
		lv.setVerticalScrollBarEnabled(false);
		// 让listView没有分割线
		lv.setDividerHeight(0);
		lv.setDivider(null);
		lv.setOnItemClickListener(this);
		
		mProjectAdapter = new ProjectsAdapter(getApplicationContext(), lv, R.dimen.listview_item_hight);
		lv.setAdapter(mProjectAdapter);
		
		// SystemNotification.showPopupWindow(this, lv, polling_query_about, polling_query_about.getWidth(), 150, 0, 0,
		// this);
		
		popupWindow = new PopupWindow(lv, polling_query_about.getWidth(), 300);
		// 设置点击外部可以被关闭
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		
		// 设置popupWindow可以得到焦点
		popupWindow.setFocusable(true);
		
		popupWindow.showAsDropDown(polling_query_about, 0, 0); // 显示
	}
	
	class ProjectsAdapter extends FrameAdapter {
		
		public ProjectsAdapter(Context context, AbsListView alv) {
			super(context, alv, 0);
		}
		
		public ProjectsAdapter(Context context, AbsListView alv, int resId) {
			super(context, alv, resId);
			
		}
		
		@Override
		public int getCount() {
			return subProjectList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return subProjectList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			convertView = setConvertView_Holder(convertView, ProjectViewHolder.class, R.layout.item_spinner_names,
					false);
			ProjectViewHolder holder = (ProjectViewHolder) convertView.getTag();
			holder.tv_name.setText(subProjectList.get(position));
			holder.ib_delete.setTag(position);
			holder.ib_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					subProjectList.remove(index);
					mProjectAdapter.notifyDataSetChanged();
					
					if (subProjectList.size() == 0) {
						popupWindow.dismiss();
					}
					
				}
			});
			return convertView;
		}
		
	}
	
	private static class ProjectViewHolder extends BaseViewHolder {
		public TextView tv_name;
		public ImageView ib_delete;
	}
	
	private List<String> getProjects() {
		List<String> mProjects = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			mProjects.add("土建工程");
		}
		return mProjects;
	}
	
	private class PollingQueryVitaeAdapter extends FrameAdapter {
		
		public PollingQueryVitaeAdapter(Context context, AbsListView alv) {
			super(context, alv, 0);
		}
		
		public PollingQueryVitaeAdapter(Context context, AbsListView alv, int resId) {
			super(context, alv, resId);
			
		}
		
		@Override
		public int getCount() {
			return 10;
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_curriculum_vitae_query);
			return convertView;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
	}
	
	private static class ViewHolder extends BaseViewHolder {
		// public ImageView item_curriculum_vitae_query_iv_show;
		// public TextView item_curriculum_vitae_query_tv_show;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent instanceof ListView) {
			polling_query_about.setText(subProjectList.get(position));
			popupWindow.dismiss();
		} else {
			SystemNotification.showToast(this, "GridView点击了...");
		}
	}
	
	@Override
	public void init() {
		setListener();
		initData();
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.polling_query);
	}
}
