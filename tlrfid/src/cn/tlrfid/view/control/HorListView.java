package cn.tlrfid.view.control;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.view.GanttChartActivity;
import cn.tlrfid.view.GanttChartActivity.ScrollViewListener;

public class HorListView extends HorizontalScrollView {
	
	public HorListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public HorListView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		init();
	}
	
	public HorListView(Context context) {
		super(context);
		init();
	}
	
	private LinearLayout ganttContent_ll;
	
	private void init() {
		ganttContent_ll = new LinearLayout(getContext());
		ganttContent_ll.setOrientation(LinearLayout.HORIZONTAL);
		addView(ganttContent_ll);
		
	}
	
	private int width = 0;
	private GanttMaxMinMothText gmmmtext;
	
	public void initWidth(int width, GanttMaxMinMothText gmmmtext) {
		this.gmmmtext = gmmmtext;
		this.width = width;
		ViewGroup.LayoutParams params = ganttContent_ll.getLayoutParams();
		params.width = width;
		params.height = GanttChartActivity.HORIZONTAL_LISTVIEW_HEIGHT;
		ganttContent_ll.setId(R.id.gantt_content_title_id);
		ganttContent_ll.setLayoutParams(params);
		initTitleContent();
	}
	
	private void initTitleContent() {
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(GanttChartActivity.WIDTHOFFSET, -1);
		params.rightMargin = 1;
		int startMoth = gmmmtext.getStartMoth();
		for (int i = 0, temp = width / GanttChartActivity.WIDTHOFFSET; i < temp; i++) {
			View view = View.inflate(getContext(), R.layout.item_hor_gantt, null);
			
			TextView tvTextView = (TextView) view.findViewById(R.id.item_ganttChart_hor);
			int moth = (startMoth + i) % 12;
			if (moth == 0) {
				moth = 12;
			}
			tvTextView.setText(moth + "月份");
			view.setLayoutParams(params);
			ganttContent_ll.addView(view);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		GanttChartActivity.TOUCH_MODULE = GanttChartActivity.TOUCH_MODULE_TOP;
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		if (horScrollViewListener != null && GanttChartActivity.TOUCH_MODULE == GanttChartActivity.TOUCH_MODULE_TOP) {
			horScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}
	
	private ScrollViewListener horScrollViewListener = null;
	
	public ScrollViewListener gethorScrollViewListener() {
		return new ScrollViewListener() {
			public void onScrollChanged(FrameLayout scrollView, int x, int y, int oldx, int oldy) {
				HorListView.this.scrollTo(scrollView.getScrollX(), 0);
			}
		};
	}
	
	public void setHorScrollViewLisener(ScrollViewListener horScrollViewListener) {
		this.horScrollViewListener = horScrollViewListener;
		
	}
	
}
