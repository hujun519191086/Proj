package cn.tlrfid.view.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.Point;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.LogUtil;

@SuppressLint("UseSparseArrays")
public class TreeAdapter extends BaseExpandableListAdapter {

	private static final String TAG = "TreeAdapter";

	private Context context;

	HashMap<Integer, Point> maps;
	ArrayList<Integer> keys;

	@SuppressLint("UseSparseArrays")
	public TreeAdapter(Context context,
			ArrayList<ProjectScheduleBean> psBeanList) {
		this.context = context;

		maps = new HashMap<Integer, Point>();
		keys = new ArrayList<Integer>();
		for (int i = 0; i < psBeanList.size(); i++) {
			ProjectScheduleBean psb = psBeanList.get(i);
			int deepLevel = psb.getType();
			if (deepLevel < 1) {
				Point temppb = new Point();
				temppb.psBean = psb;
				maps.put(psb.getId(), temppb);
				keys.add(psb.getId());
			}
		}

		for (int i = 0; i < psBeanList.size(); i++) {
			ProjectScheduleBean psb = psBeanList.get(i);

			int deepLevel = psb.getType();
			Point pb = new Point();

			pb.psBean = psb;
			if (deepLevel == 1) {

				Point parentPb = maps.get(pb.psBean.getParentId());
				if (parentPb == null) {
					Collection<Point> collections = maps.values();

					for (Point point : collections) {
						if (point.hasChild()) {
							Point tempParentPb = point.getChilds().get(
									pb.psBean.getParentId());

							if (tempParentPb == null) {
								continue;
							}
							tempParentPb.setChilds(pb);
						}
					}
					continue;
				}
				parentPb.setChilds(pb);

			}
		}

		LogUtil.d(TAG, "maps:" + maps);
	}

	public TreeAdapter(Context context, Point parentpoint) {
		this.context = context;

		maps = new HashMap<Integer, Point>();
		keys = new ArrayList<Integer>();

		maps.put(parentpoint.psBean.getId(), parentpoint);

		level1Color = level1Color + parentpoint.deep * 16;

		keys.add(parentpoint.psBean.getId());
	}

	@Override
	public int getGroupCount() {

		if (maps == null) {
			return 0;
		}
		return maps.keySet().size();

	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return maps.get(keys.get(groupPosition)).childCount();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return maps.get(keys.get(groupPosition));
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		Point point = (Point) getGroup(groupPosition);

		return point.getChilds().get(point.getKeys().get(childPosition));
	}

	@Override
	public long getGroupId(int groupPosition) {
		Point point = (Point) getGroup(groupPosition);
		return point.psBean.getId();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		Point point = (Point) getChild(groupPosition, childPosition);
		return point.psBean.getId();
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	private int level1Color = 0xFFCCCCCC;

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View view = View.inflate(context, R.layout.item_progressquery, null);

		Point point = (Point) getGroup(groupPosition);

		view.setBackgroundColor(level1Color);
		LogUtil.d(TAG, "grounp:" + point);
		setData(view, point.psBean, true, groupPosition);
		return view;
	}

	private int itemChange = 2;

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		Point point = (Point) getChild(groupPosition, childPosition);
		ExpandableListView elv = new ExpandableListView(context);

		elv.setBackgroundColor(Color.BLACK);
		elv.setLayoutParams(new AbsListView.LayoutParams(-1, DensityUtil
				.dip2px(40)));

		elv.setAdapter(new TreeAdapter(context, point));

		elv.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				int childCount = parent.getExpandableListAdapter()
						.getChildrenCount(groupPosition);

				if (!parent.isGroupExpanded(groupPosition)) {
					parent.setLayoutParams(new AbsListView.LayoutParams(-1,
							DensityUtil.dip2px(40 + childCount * 40)));

				} else {
					parent.setLayoutParams(new AbsListView.LayoutParams(-1,
							DensityUtil.dip2px(40)));

				}
				return false;
			}
		});
		// if (childPosition % itemChange == 0) {
		// view.setBackgroundColor(0xFFF0F8FF);
		// } else {
		// view.setBackgroundColor(0xFFDCEEF8);
		// }
		// // itemChange = !itemChange;
		// //
		// view.setBackgroundResource(R.drawable.progressquery_item_level2_background);
		// setData(view, point.psBean, false, childPosition);
		// LogUtil.d(TAG, "child:" + point.psBean.getType());
		return elv;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	private int textSize = 15;

	private void setData(View view, ProjectScheduleBean pqb, boolean isGroup,
			int position) {
		view.setLayoutParams(new AbsListView.LayoutParams(-1, DensityUtil
				.dip2px(40)));
		TextView progressquery_tv_plan = (TextView) view
				.findViewById(R.id.progressquery_tv_plan);
		TextView progressquery_tv_planType = (TextView) view
				.findViewById(R.id.progressquery_tv_planType);
		TextView progressquery_tv_startTime = (TextView) view
				.findViewById(R.id.progressquery_tv_startTime);
		TextView progressquery_tv_endTime = (TextView) view
				.findViewById(R.id.progressquery_tv_endTime);
		TextView progressquery_tv_limitTime = (TextView) view
				.findViewById(R.id.progressquery_tv_limitTime);
		TextView progressquery_tv_completeProgress = (TextView) view
				.findViewById(R.id.progressquery_tv_completeProgress);
		TextView progressquery_tv_details = (TextView) view
				.findViewById(R.id.progressquery_tv_details);
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
