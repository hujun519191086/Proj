package cn.tlrfid.view;

import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.anno.net1.NetworkDetector;
import cn.tlrfid.bean.CurriculumVitaeBean;
import cn.tlrfid.bean.ProjectInfoBean;
import cn.tlrfid.framework.BaseMainActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.async.LoadProjectManagerAsync;
import cn.tlrfid.view.async.LoadProjectScheduleAsync;

/**
 * 工程管理模块
 * 
 * @author Administrator
 * 
 */
public class ProjectManageActivity extends BaseMainActivity {
	
	private static final String TAG = "ProjectManageActivity";
	@ViewById_Clickthis
	private TextView header_tv_selfe_text;
	@ViewById_Clickthis
	private TextView header_tv_hard_text;
	
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.header_tv_selfe_text:
		// startActivity(QualitySelfeActivity.class);
		// break;
		}
	}
	
	ProjectInfoBean pib;
	
	@ViewById
	private LinearLayout projectmessage_default_view;
	@ViewById
	private RelativeLayout card_send_view;
	
	@Override
	public void init() {
		setLeftListType(LeftListEnum.PROJECT_MANAGER, new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!NetworkDetector.isNetworkAvailable(ProjectManageActivity.this)) {
					SystemNotification.showToast(ProjectManageActivity.this, "网络不可用，请检查网络！");
					return;
				}
				switch (position) {
				case 1:// 进度管理
					new LoadProjectScheduleAsync(ProjectManageActivity.this, "正在加载一级网络计划...", projectInfoTitle)
							.execute(ConstantValues.LOAD_PROGRESS_QUERY + "?" + ConstantValues.PROJECT_CODE + "="
									+ Config_values.PROJECT_CODE);
					break;//
				case 2:// 进度管理列表,横道图
					startActivity(GanttChartActivity.class);
					break;
				case 3:// 新增履历
					startActivity(CurriculumVitae_Create_Activity.class);
					break;
				case 4:// 履历一览
					getProjectRecod();
					break;
				case 5:// 工程发卡
					startActivity(CardSendActivity.class);
					break;
				}
			}
		});
		
		pib = (ProjectInfoBean) getIntent().getSerializableExtra("loginResult");
		loadMainInfo(false);
		if (pib != null) {
			cholder.preperData(getApplicationContext(), pib);
			projectInfoTitle = pib.getName();
		} else {
			new LoadProjectManagerAsync(this, cholder) {
				public void onNetResultSuccess(ProjectInfoBean result) {
					projectInfoTitle = result.getName();
				}
			}.execute(ConstantValues.PROJECT_CODE + "=" + Config_values.PROJECT_CODE);
		}
	}
	
	protected void getProjectRecod() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "9");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				CurriculumVitaeBean currVitae = StringUtils.parseCUrriculumInfo(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(ProjectManageActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(CurriculumVitae_Query_Activity.class, "data", currVitae);
				
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(ProjectManageActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.CURRICULUM_VITAE_QUERY, mHashMap);
		mRequest.execute();
		
	}
	
	private String projectInfoTitle = "";
	
	@Override
	public void onCreateView() {
		setContentViewNoActionBar(R.layout.projectmanager);
	}
	
}
