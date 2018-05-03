package cn.tlrfid.view.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.LogUtil;

public class ProgressQueryContentAdapter extends BaseExpandableListAdapter {
	
	private static final String TAG = "ProgressQueryContentAdapter";
	private Context context;
	
	private HashMap<Integer, Pair<ProjectScheduleBean, ArrayList<ProjectScheduleBean>>> lList;
	private ArrayList<Integer> keyList;
	private Pair<Integer, ArrayList<Integer>> allCount;
	int nextPostion = -1;
	
	@SuppressLint("UseSparseArrays")
	public ProgressQueryContentAdapter(Context context, ArrayList<ProjectScheduleBean> psBeanList) {
		this.context = context;
		
		int groupCount = 0;
		int childCount = 0;
		ArrayList<Integer> childCounts = new ArrayList<Integer>();
		
		keyList = new ArrayList<Integer>();
		lList = new HashMap<Integer, Pair<ProjectScheduleBean, ArrayList<ProjectScheduleBean>>>();
		for (int i = 0; i < psBeanList.size(); i++) {
			ProjectScheduleBean psb = psBeanList.get(i);
			int type = psb.getType();
			LogUtil.d(TAG, " psb:" + psb);
			if (type == 0) {
				ArrayList<ProjectScheduleBean> list = new ArrayList<ProjectScheduleBean>();
				lList.put(psb.getId(), new Pair<ProjectScheduleBean, ArrayList<ProjectScheduleBean>>(psb, list));
				groupCount++;
				
				if (nextPostion >= 0) {
					childCounts.add(childCount);
					childCount = 0;
				}
				keyList.add(psb.getId());
				nextPostion++;
			} else {
				int parentId = psb.getParentId();
				Pair<ProjectScheduleBean, ArrayList<ProjectScheduleBean>> pair = lList.get(parentId);
				if (pair == null) {
					Set<Integer> sets = lList.keySet();
					overSearchFatcher: for (Integer set : sets) {
						Pair<ProjectScheduleBean, ArrayList<ProjectScheduleBean>> p = lList.get(set);
						ArrayList<ProjectScheduleBean> secondList = p.second;
						for (int j = 0; j < secondList.size(); j++) {
							ProjectScheduleBean psBean = secondList.get(j);
							if (parentId == psBean.getId()) {
								secondList.add(psb);
								break overSearchFatcher;
							}
						}
						
					}
				} else {
					pair.second.add(psb);
				}
				childCount++;
			}
		}
		
		childCounts.add(childCount);
		
		allCount = new Pair<Integer, ArrayList<Integer>>(groupCount, childCounts);
		
		LogUtil.i(TAG, "childCount:" + childCount + "     groupCount: " + groupCount);
		Set<Integer> set = lList.keySet();
		for (Integer id : set) {
			LogUtil.d(TAG, "set:" + lList.get(id).second);
		}
		
	}
	
	@Override
	public int getGroupCount() {
		return allCount.first;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return allCount.second.get(groupPosition);
	}
	
	@Override
	public ProjectScheduleBean getGroup(int groupPosition) {
		return lList.get(keyList.get(groupPosition)).first;
	}
	
	@Override
	public ProjectScheduleBean getChild(int groupPosition, int childPosition) {
		return lList.get(keyList.get(groupPosition)).second.get(childPosition);
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_progressquery, null);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1, DensityUtil.getDimensPx(context,
				R.dimen.progressquery_item_content_height));
		view.setLayoutParams(params);
		
		view.setBackgroundColor(0xFFCCCCCC);
		setData(view, getGroup(groupPosition), true, groupPosition);
		return view;
	}
	
	private int itemChange = 2;
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_progressquery, null);
		if (childPosition % itemChange == 0) {
			view.setBackgroundColor(0xFFF0F8FF);
		} else {
			view.setBackgroundColor(0xFFDCEEF8);
		}
		// itemChange = !itemChange;
		// view.setBackgroundResource(R.drawable.progressquery_item_level2_background);
		setData(view, getChild(groupPosition, childPosition), false, childPosition);
		return view;
	}
	
	private int textSize = 15;
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	private void setData(View view, ProjectScheduleBean pqb, boolean isGroup, int position) {
		view.setLayoutParams(new AbsListView.LayoutParams(-1, context.getResources().getDimensionPixelSize(
				R.dimen.progressquery_itemHeight)));
		TextView progressquery_tv_plan = (TextView) view.findViewById(R.id.progressquery_tv_plan);
		TextView progressquery_tv_planType = (TextView) view.findViewById(R.id.progressquery_tv_planType);
		TextView progressquery_tv_startTime = (TextView) view.findViewById(R.id.progressquery_tv_startTime);
		TextView progressquery_tv_endTime = (TextView) view.findViewById(R.id.progressquery_tv_endTime);
		TextView progressquery_tv_limitTime = (TextView) view.findViewById(R.id.progressquery_tv_limitTime);
		TextView progressquery_tv_completeProgress = (TextView) view
				.findViewById(R.id.progressquery_tv_completeProgress);
		TextView progressquery_tv_details = (TextView) view.findViewById(R.id.progressquery_tv_details);
		String tempStr = "";
		while (tempStr.length() < pqb.getType()) {
			tempStr += "  ";
		}
		progressquery_tv_plan.setText(tempStr + pqb.getName());
		
		if (isGroup) {
			
			int childCount = getChildrenCount(position);
			progressquery_tv_planType.setText(childCount + "");
			if (childCount > 0 ? true : false) {
				progressquery_tv_planType.setTextColor(Color.RED);
			} else {
				progressquery_tv_planType.setTextColor(Color.BLACK);
			}
		} else {
			
			progressquery_tv_planType.setText("");
		}
		progressquery_tv_startTime.setText(pqb.getBeginTime());
		progressquery_tv_endTime.setText(pqb.getEndTime());
		progressquery_tv_limitTime.setText(pqb.getPeriod() + "天");
		progressquery_tv_completeProgress.setText(pqb.getFinishRate() + "%");
		progressquery_tv_details.setText(pqb.getRemark());
		
		if (!isGroup) {
			progressquery_tv_plan.setTextSize(textSize);
			progressquery_tv_planType.setTextSize(textSize);
			progressquery_tv_startTime.setTextSize(textSize);
			progressquery_tv_endTime.setTextSize(textSize);
			progressquery_tv_limitTime.setTextSize(textSize);
			progressquery_tv_completeProgress.setTextSize(textSize);
			progressquery_tv_details.setTextSize(textSize);
		}
	}
}
