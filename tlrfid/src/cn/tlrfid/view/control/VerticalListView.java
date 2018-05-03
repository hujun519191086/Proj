package cn.tlrfid.view.control;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.view.GanttChartActivity;
import cn.tlrfid.view.GanttChartActivity.ScrollViewListener;

public class VerticalListView extends ScrollView {
	public static int FIX_HEIGHT_VALUES = 60;
	
	public VerticalListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public VerticalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public VerticalListView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		verticalList = new ListView(getContext());
		verticalList.setDividerHeight(1);
		verticalList.setDivider(new BitmapDrawable(BitmapUtil.createBitmapForColor(0xFFFFFFFF, 1, 1)));
		rl_verticalRela = new RelativeLayout(getContext());
		addView(rl_verticalRela);
		rl_verticalRela.addView(verticalList);
	}
	
	private int height;
	private ListView verticalList;
	private RelativeLayout rl_verticalRela;
	
	public void setAdapter(ListAdapter adapter) {
		height = GanttChartActivity.VERTICAL_LISTVIEW_HEIGH * adapter.getCount();
		verticalList.setAdapter(adapter);
		
		ViewGroup.LayoutParams params = rl_verticalRela.getLayoutParams();
		params.height = height + FIX_HEIGHT_VALUES;
		params.width = -1;
		rl_verticalRela.setLayoutParams(params);
		Log.d("setAdapter", "height:" + height);
		params = verticalList.getLayoutParams();
		params.height = height + FIX_HEIGHT_VALUES;
		
		verticalList.setLayoutParams(params);
		
		// Utility.setListViewHeightBasedOnChildren(verticalList);
	}
	
	private static class ViewHolder extends BaseViewHolder {
		TextView item_ganttChart_vertical;
	}
	
	private ScrollViewListener scrollViewListener = null;
	
	public void setScrollViewLisener(ScrollViewListener scrollViewListener, ArrayList<ProjectScheduleBean> psBeanList) {
		this.scrollViewListener = scrollViewListener;
		setAdapter(new MyBaseAdapter(verticalList, psBeanList));
	}
	
	private class MyBaseAdapter extends FrameAdapter {
		public MyBaseAdapter(AbsListView alv, ArrayList<ProjectScheduleBean> psBeanList) {
			super(getContext(), alv, 0);
			setItemHeightInPX(GanttChartActivity.VERTICAL_LISTVIEW_HEIGH);
			this.psBeanList = psBeanList;
		}
		
		private ArrayList<ProjectScheduleBean> psBeanList;
		
		public int getCount() {
			if (psBeanList == null) {
				return 0;
			}
			return psBeanList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_vertical_gantt);
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.item_ganttChart_vertical.setText(psBeanList.get(position).getName());
			return convertView;
		}
	}
	
	public ScrollViewListener getVerticalScrollViewListener() {
		return new ScrollViewListener() {
			public void onScrollChanged(FrameLayout scrollView, int x, int y, int oldx, int oldy) {
				
				VerticalListView.this.scrollTo(0, scrollView.getScrollY());
			}
		};
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		GanttChartActivity.TOUCH_MODULE = GanttChartActivity.TOUCH_MODULE_LEFT;
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollViewListener != null && GanttChartActivity.TOUCH_MODULE == GanttChartActivity.TOUCH_MODULE_LEFT) {
			scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
		Log.d("onScrollChanged", "l:" + l + "     t:" + t + "     oldl:" + oldl + "     oldt:" + oldt);
	}
	
}
