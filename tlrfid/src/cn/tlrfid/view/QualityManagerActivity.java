package cn.tlrfid.view;

import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import cn.tlrfid.R;
import cn.tlrfid.anno.net1.HttpRequest;
import cn.tlrfid.anno.net1.HttpRequest.HttpRequestCallBack;
import cn.tlrfid.bean.QualityRecordBean;
import cn.tlrfid.bean.SelfCurriculumViateQueryBean;
import cn.tlrfid.framework.BaseMainActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.StringUtils;
import cn.tlrfid.utils.SystemNotification;

public class QualityManagerActivity extends BaseMainActivity {
	
	@Override
	public void onCreateView() {
		setContentViewNoActionBar(R.layout.qualitymanager);
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
		setLeftListType(LeftListEnum.QUALITY_MANAGER, new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				switch (position) {
				case 1:
					// 新建质量检查
					startActivity(QualityCreateActivity.class);
					break;
				case 2:
					// 质量管理一览
					getProjectRecod();
					break;
				case 3:// 待整改质量检查记录
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
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseQualityCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(QualityManagerActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(QualityCheckedActivity.class, "data", currVitae);
				
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(QualityManagerActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.QUALITY_NEED_CHECKED, mHashMap);
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
				SelfCurriculumViateQueryBean currVitae = StringUtils.parseQualityCurriculum(message);
				if (currVitae.state != 1) {
					SystemNotification.showToast(QualityManagerActivity.this, currVitae.errMessage);
					return;
				}
				
				startActivity(QualityManagerProject.class, "data", currVitae);
			}
			
			@Override
			public void start() {
				AlertDialogUtil.showLoading(QualityManagerActivity.this, "加载中...");
			}
			
			@Override
			public void error(String message) {
				AlertDialogUtil.dissmissLoading();
			}
			
			@Override
			public void cancel() {
			}
		}, ConstantValues.QUERY_CHECK_RECORD, mHashMap);
		mRequest.execute();
		
	}
	
}
