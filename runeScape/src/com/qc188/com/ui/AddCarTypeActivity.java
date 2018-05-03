package com.qc188.com.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.qc188.com.R;
import com.qc188.com.bean.SearchContentBean;
import com.qc188.com.engine.BrandDetailEngine;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.framwork.BaseAsync;
import com.qc188.com.ui.control.BrandDetailContent;
import com.qc188.com.ui.manager.CompairManager;
import com.qc188.com.util.ConstantValues.STATUS;
import com.qc188.com.util.DataPackageUtil;
import com.qc188.com.util.InstanceFactory;
import com.qc188.com.util.LogUtil;

/**
 * 对比中,添加车型.中车系界面
 * 
 * @author jieranyishen
 * 
 */
public class AddCarTypeActivity extends BaseActivity {

	private BrandDetailContent bdc_addCarType_Content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_car_type);
		bdc_addCarType_Content = findById(R.id.bdc_addCarType_Content);
		SearchContentBean scBean = (SearchContentBean) getIntent().getSerializableExtra("carBrand");
		DataPackageUtil.getPackage().put(scBean.getCar_name());
		setTitleContent(scBean.getCar_name());
		new BrandDetialMsgGetAsync(this).execute(scBean.getCar_id() + "");
	}

	        /**
     * 获取汽车详情async
     * 
     * @author mryang
     * 
     */
	private class BrandDetialMsgGetAsync extends BaseAsync<String, Integer> implements OnItemClickListener {

		private static final String TAG = "BrandDetialMsgGetAsync";
		private BrandDetailEngine bdEngine;

		public BrandDetialMsgGetAsync(Activity activity) {
			super(activity);
			setCheckedLink(false);
		}

		@Override
		public void clear() {

		}

		@Override
		public void onPost(Integer result) {

			if (result != STATUS.SUCCESS) {
				out(result);
				return;
			}
			LogUtil.d(TAG,
					"bdEngine.getBrandDetailBean():" + bdEngine.getBrandDetailBean() + "  bdEngine.getBrandEngineBean():" + bdEngine.getBrandEngineBean());

			bdc_addCarType_Content.setOnItemClickListener(this);
            bdc_addCarType_Content.addList(bdEngine.getBrandEngineBean(), CompairManager.getManger().getSharedMap(), true, AddCarTypeActivity.this);
		}

		@Override
		protected Integer doInBackground(String... params) {

			bdEngine = InstanceFactory.getInstances(BrandDetailEngine.class);
			return bdEngine.getJsonToMatchBean(Integer.valueOf(params[0]));
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String key = (String) arg1.getTag(R.id.tag_first);
			String value = (String) arg1.getTag(R.id.tag_second);
			LogUtil.d("CarType", "key:" + key + "  value:" + value);
			CompairManager.getManger().putCompair(key, value);
			finish();
		}

	}

}
