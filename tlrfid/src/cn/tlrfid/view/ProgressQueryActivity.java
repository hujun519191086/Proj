package cn.tlrfid.view;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.GanttMaxMinMothText;
import cn.tlrfid.bean.ProjectScheduleBean;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.SerializPair;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.view.adapter.ProgressQueryContentAdapter;
import cn.tlrfid.view.adapter.TreeAdapter;

/**
 * 进度查看界面
 * 
 * @author 杨煜
 * 
 */
public class ProgressQueryActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "ProgressQueryActivity";
	@ViewById
	private ExpandableListView progressquery_elv_content;

	@Override
	public void onClick(View v) {
		@SuppressWarnings("unchecked")
		SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> s = (SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>) v.getTag();
		startActivity(GanttChartActivity.class, "loginResult", s);
	}

	@ViewById_Clickthis
	private View progressquery_bt_showGantt;

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>> psBeanList = (SerializPair<GanttMaxMinMothText, ArrayList<ProjectScheduleBean>>) getIntent()
				.getSerializableExtra("projectSchedule");
		title_bar_content.setText(psBeanList.first.getProjectTitle());
		LogUtil.d(TAG, "psBean:" + psBeanList);
		progressquery_bt_showGantt.setTag(psBeanList);
		progressquery_elv_content.setAdapter(new TreeAdapter(getApplicationContext(), psBeanList.second));
		progressquery_elv_content.expandGroup(0);
		progressquery_elv_content.setGroupIndicator(null);
	}

	@Override
	public void onCreateView() {
		setContentView(R.layout.progressquery);

	}
}
