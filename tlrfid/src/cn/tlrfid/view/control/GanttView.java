package cn.tlrfid.view.control;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.FrameAdapter.BaseViewHolder;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.view.GanttChartActivity;
import cn.tlrfid.view.GanttChartActivity.ScrollViewListener;

public class GanttView extends HorizontalScrollView {
	
	private static final String TAG = "GanttView";
	
	public GanttView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public GanttView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public GanttView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		ganttContent_sv = new ScrollView(getContext()) {
			@Override
			protected void onScrollChanged(int l, int t, int oldl, int oldt) {
				super.onScrollChanged(l, t, oldl, oldt);
				if (verticalScrollViewListener != null
						&& GanttChartActivity.TOUCH_MODULE == GanttChartActivity.TOUCH_MODULE_RIGHT) {
					verticalScrollViewListener.onScrollChanged(ganttContent_sv, l, t, oldl, oldt);
				}
				Log.d("onScrollChanged", "l:" + l + "     t:" + t + "     oldl:" + oldl + "     oldt:" + oldt);
			}
			
			@Override
			public boolean onTouchEvent(MotionEvent ev) {
				GanttChartActivity.TOUCH_MODULE = GanttChartActivity.TOUCH_MODULE_RIGHT;
				return super.onTouchEvent(ev);
			}
		};
		addView(ganttContent_sv);
		
		ganttContent_rl = new RelativeLayout(getContext());
		ganttContent_sv.addView(ganttContent_rl);
		
		ganttContent_lv = new ListView(getContext());
		ganttContent_rl.addView(ganttContent_lv);
		
	}
	
	public int getWidthPX() {
		return width;
	}
	
	public void setOnItemclickListener(OnItemClickListener oiClick) {
		if (ganttContent_lv != null) {
			ganttContent_lv.setOnItemClickListener(oiClick);
		}
	}
	
	private ArrayList<GanttMaxMinMothText> maxMinMothList;
	private int width = 0;
	private int height = 0;
	private int mothCount = 0;
	private ListView ganttContent_lv;
	private RelativeLayout ganttContent_rl;
	private ScrollView ganttContent_sv;
	
	public void setGanttMaxMinMothBean(GanttMaxMinMothText gmmmBean) {
		int offset = gmmmBean.getMothCount();
		width = (GanttChartActivity.WIDTHOFFSET + GanttChartActivity.HORIZONTAL_MARGIN_RIGHT) * offset
				- GanttChartActivity.HORIZONTAL_MARGIN_RIGHT;
		mothCount = width / (GanttChartActivity.WIDTHOFFSET + GanttChartActivity.HORIZONTAL_MARGIN_RIGHT);
		LogUtil.d(TAG, "width" + width);
		startGantt = gmmmBean;
	}
	
	public void setGanttChartList(ArrayList<ProjectScheduleBean> ganttBeanList) {
		this.maxMinMothList = new ArrayList<GanttMaxMinMothText>();
		if (ganttBeanList != null) {
			GanttMaxMinMothText gmmmtxt;
			for (int i = 0; i < ganttBeanList.size(); i++) {
				ProjectScheduleBean psb = ganttBeanList.get(i);
				gmmmtxt = new GanttMaxMinMothText(psb.getBeginTime(), psb.getEndTime());
				gmmmtxt.setOutofWidth(psb.getOutOfDay());
				gmmmtxt.setProgress(psb.getFinishRate());
				gmmmtxt.setProjectName(psb.getName());
				maxMinMothList.add(gmmmtxt);
			}
		}
		height = GanttChartActivity.VERTICAL_LISTVIEW_HEIGH * ganttBeanList.size();
		Log.d("WH", "width:" + width + "      height:" + height + "   ganttBeanList:" + ganttBeanList.size());
		//
		setAdapter(new MyBaseAdapter(getContext(), ganttContent_lv));
	}
	
	public void setAdapter(ListAdapter adapter) {
		ViewGroup.LayoutParams params = ganttContent_rl.getLayoutParams();
		params.height = height + VerticalListView.FIX_HEIGHT_VALUES;
		params.width = width;
		ganttContent_rl.setLayoutParams(params);
		
		RelativeLayout.LayoutParams p = (android.widget.RelativeLayout.LayoutParams) ganttContent_lv.getLayoutParams();
		p.height = height + VerticalListView.FIX_HEIGHT_VALUES;
		p.width = width;
		ganttContent_lv.setLayoutParams(p);
		ganttContent_lv.setAdapter(adapter);
		ganttContent_lv.setDivider(BitmapUtil.createColorDrawabe(0xFFFFFFFF));
		ganttContent_lv.setDividerHeight(1);
		
	}
	
	private class MyBaseAdapter extends FrameAdapter {
		
		public MyBaseAdapter(Context context, AbsListView alv) {
			super(context, alv, 0);
			setItemHeightInPX(GanttChartActivity.VERTICAL_LISTVIEW_HEIGH);
		}
		
		@Override
		public int getCount() {
			return maxMinMothList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return maxMinMothList.get(position);
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_ganttchart);
			ViewHolder vHolder = (ViewHolder) convertView.getTag();
			
			GanttMaxMinMothText gmmmt = maxMinMothList.get(position);
			int leftMargin = 0;
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(gmmmt.getWidth(), -1);
			params.leftMargin = leftMargin = gmmmt.getMarginLeft(startGantt.getStartTime())+3;
			vHolder.ganttChart_item_max_width.setLayoutParams(params);
			
			params = new FrameLayout.LayoutParams(gmmmt.getProgress(), -1);
			params.leftMargin = leftMargin;
			vHolder.ganttChart_item_now_width.setLayoutParams(params);
			
			for (int i = 0; i < mothCount; i++) {
				View view = View.inflate(getContext(), R.layout.px_1_textview, null);
				RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(1, -1);
				p.leftMargin = (i + 1) * (GanttChartActivity.WIDTHOFFSET + GanttChartActivity.HORIZONTAL_MARGIN_RIGHT)-2;
				view.setLayoutParams(p);
				vHolder.ganttChart_item_bakckground.addView(view);
			}
			
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(gmmmt.getOutofWidth(),
					DensityUtil.dip2px(17));
			p.addRule(RelativeLayout.RIGHT_OF, R.id.ganttchart_fl_content);
			p.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			vHolder.ganttChart_item_outofDay.setLayoutParams(p);
			convertView.setTag(R.id.tag_second_id, gmmmt);
			// params.leftMargin =
			//
			// TextView tv = new TextView(getContext());
			// tv.setLayoutParams(params);
			// tv.setText("position:" + position + "position:" + position + "position:" + position + "position:"
			// + position + "position:" + position + "position:" + position + "position:" + position + "position:"
			// + position + "position:" + position);
			// return tv;
			return convertView;
		}
	}
	
	private static class ViewHolder extends BaseViewHolder {
		FrameLayout ganttChart_item_progress;
		TextView ganttChart_item_max_width;
		TextView ganttChart_item_now_width;
		TextView ganttChart_item_outofDay;
		
		RelativeLayout ganttChart_item_bakckground;
		
	}
	
	public ScrollViewListener getVerticalScrollViewListener() {
		return new ScrollViewListener() {
			public void onScrollChanged(FrameLayout scrollView, int x, int y, int oldx, int oldy) {
				ganttContent_sv.scrollTo(0, scrollView.getScrollY());
			}
		};
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		GanttChartActivity.TOUCH_MODULE = GanttChartActivity.TOUCH_MODULE_RIGHT;
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		
		if (horScrollViewListener != null && GanttChartActivity.TOUCH_MODULE == GanttChartActivity.TOUCH_MODULE_RIGHT) {
			horScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
		Log.d("onScrollChanged", "l:" + l + "     t:" + t + "     oldl:" + oldl + "     oldt:" + oldt);
	}
	
	private ScrollViewListener verticalScrollViewListener = null;
	private ScrollViewListener horScrollViewListener = null;
	private GanttMaxMinMothText startGantt;
	
	public void setVerticalScrollViewLisener(ScrollViewListener scrollViewListener, GanttMaxMinMothText gmmmb) {
		this.verticalScrollViewListener = scrollViewListener;
		
	}
	
	public void setHorScrollViewLisener(ScrollViewListener scrollViewListener) {
		this.horScrollViewListener = scrollViewListener;
	}
	
	public ScrollViewListener gethorScrollViewListener() {
		return new ScrollViewListener() {
			public void onScrollChanged(FrameLayout scrollView, int x, int y, int oldx, int oldy) {
				scrollTo(scrollView.getScrollX(), 0);
			}
		};
	}
	
}
