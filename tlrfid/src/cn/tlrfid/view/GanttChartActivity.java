package cn.tlrfid.view;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.engine.ProjectScheduleEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.framework.SerializPair;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.control.GanttView;
import cn.tlrfid.view.control.HorListView;
import cn.tlrfid.view.control.VerticalListView;

public class GanttChartActivity extends BaseActivity implements OnItemClickListener {
	public static int VERTICAL_LISTVIEW_HEIGH = R.dimen.ganttchart_vertical_height;
	public static int VERTICAL_LISTVIEW_WIDTH = R.dimen.ganttchart_back_button_width;
	public static int HORIZONTAL_LISTVIEW_HEIGHT = R.dimen.ganttchart_back_button_height;
	public static int WIDTHOFFSET = 120;
	public static int DAY_WIDTHOFFSET = 120 / 30;
	
	public static final int HORIZONTAL_MARGIN_RIGHT = 1;
	
	public static final int TOUCH_MODULE_LEFT = 1;
	public static final int TOUCH_MODULE_RIGHT = 2;
	public static final int TOUCH_MODULE_TOP = 3;
	public static int TOUCH_MODULE = 0;
	
	@ViewById
	private VerticalListView vlv_gantt_left;
	@ViewById
	private GanttView gv_gantt_right;
	
	private void findID(GanttMaxMinMothText gmmmb) {
		vlv_gantt_left.setScrollViewLisener(gv_gantt_right.getVerticalScrollViewListener(), psBeanList);
		gv_gantt_right.setVerticalScrollViewLisener(vlv_gantt_left.getVerticalScrollViewListener(), gmmmb);
		
		HorListView hlv_gantt_top = (HorListView) findViewById(R.id.hlv_gantt_top);
		hlv_gantt_top.initWidth(gv_gantt_right.getWidthPX(), gmmmb);
		
		gv_gantt_right.setHorScrollViewLisener(hlv_gantt_top.gethorScrollViewListener());
		hlv_gantt_top.setHorScrollViewLisener(gv_gantt_right.gethorScrollViewListener());
		
		gv_gantt_right.setOnItemclickListener(this);
	}
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		setActionBarTitle("横道图");
		SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> psPair = (SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>) getIntent()
				.getSerializableExtra("loginResult");
		if (psPair == null) {
			new LoadProjectScheduleAsync(GanttChartActivity.this, "正在加载横道图...")
					.execute(ConstantValues.LOAD_PROGRESS_QUERY + "?" + ConstantValues.PROJECT_CODE + "="
							+ Config_values.PROJECT_CODE);
		} else {
			preperData(psPair);
		}
		
	}
	
	private void preperData(SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> psBeanList) {
		GanttMaxMinMothText gmmmb = psBeanList.first;
		this.psBeanList = psBeanList.second;
		gv_gantt_right.setGanttMaxMinMothBean(gmmmb);
		gv_gantt_right.setGanttChartList(psBeanList.second);
		
		// 衔接两面接口
		findID(gmmmb);
	}
	
	public interface ScrollViewListener {
		public void onScrollChanged(FrameLayout scrollView, int x, int y, int oldx, int oldy);
		
	}
	
	private ArrayList<ProjectScheduleBean> psBeanList;
	
	@Override
	public void onClick(View v) {
	}
	
	@Override
	public void onCreateView() {
		VERTICAL_LISTVIEW_HEIGH = getApplicationContext().getResources().getDimensionPixelSize(
				R.dimen.ganttchart_vertical_height);
		VERTICAL_LISTVIEW_WIDTH = getApplicationContext().getResources().getDimensionPixelSize(
				R.dimen.ganttchart_back_button_width);
		HORIZONTAL_LISTVIEW_HEIGHT = getApplicationContext().getResources().getDimensionPixelSize(
				R.dimen.ganttchart_back_button_height);
		setContentView(R.layout.ganttchart);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GanttMaxMinMothText gmmmt = (GanttMaxMinMothText) view.getTag(R.id.tag_second_id);
		int out = gmmmt.getOutOfDay();
		AlertDialogUtil.getInstance(getApplicationContext()).alert(
				this,
				gmmmt.getProjectName(),
				"预计工期:" + gmmmt.getStartTime() + "日  到:" + gmmmt.getEndTime() + "日    完成度:" + gmmmt.getFinishRate()
						+ (out <= 0 ? "%" : ("%  \n超出时间:" + gmmmt.getOutOfDay() + "天")));
	}
	
	private class LoadProjectScheduleAsync extends
			NetConnectionAsync<String, Void, SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>> {
		
		public LoadProjectScheduleAsync(BaseActivity activity, String dialogString) {
			super(activity, dialogString);
		}
		
		@Override
		protected SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> doInBackground(String... params) {
			
			ProjectScheduleEngine pse = InstanceFactory.getEngine(ProjectScheduleEngine.class);
			
			return pse.getProjectSchedule(params[0], ProjectScheduleBean.class);
			
		}
		
		@Override
		protected void onNetResult(SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> result) {
			if (result == null) {
				SystemNotification.showToast(getApplicationContext(), "网络错误!");
			} else {
				preperData(result);
			}
		}
		
	}
	
}
