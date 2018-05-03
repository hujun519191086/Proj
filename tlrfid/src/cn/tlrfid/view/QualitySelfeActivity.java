package cn.tlrfid.view;

import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.tlrfid.R;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.CurriculumVitaeBean;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
import cn.tlrfid.bean.ControlHolder;
import cn.tlrfid.bean.ProjectInfoBean;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.framework.BaseMainActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.view.async.LoadProjectManagerAsync;

public class QualitySelfeActivity extends BaseMainActivity {
	
	@Override
	public void onClick(View v) {
	}
	
	@Override
	public void init() {
		setLeftListType(LeftListEnum.SAFE, new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					break;
				case 1:// 新建安全检查
					startActivity(SafetyCreateActivity.class);
					break;
				case 2:// 安全检查一览
						// startActivity(SelfManagerProject.class);
					getProjectRecod();
					break;
				case 3:// 待整改安全检查记录
					getNeedCheckedRecode();
					break;
				}
			}
		});
		
		loadMainInfo();
	}
	
	protected void getNeedCheckedRecode() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "9");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseSelfCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(QualitySelfeActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(SelfCheckedActivity.class, "data", currVitae);
				
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(QualitySelfeActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.CHECK_RECODE, mHashMap);
		mRequest.execute();
	}
	
	protected void getProjectRecod() {
		HashMap<String, String> mHashMap = new HashMap<String, String>();
		mHashMap.put("projectCode", Config_values.PROJECT_CODE);
		mHashMap.put("pageSize", "9");
		HttpRequest mRequest = new HttpRequest(this, new HttpRequestCallBack() {
			
			@Override
			public void success(String message) {
				AlertDialogUtil.dissmissLoading();
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseSelfCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(QualitySelfeActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(SelfManagerProject.class, "data", currVitae);
				
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(QualitySelfeActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.SELF_CURRICULUM_VITATE_QUERY, mHashMap);
		mRequest.execute();
		
	}
	
	@Override
	public void onCreateView() {
		setContentViewNoActionBar(R.layout.qualityself);
		
	}
	
}
