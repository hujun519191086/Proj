package cn.tlrfid.view;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.bean.warehouse.DeviceHelpBean;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.engine.WarehouseEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.adapter.WarehouseContentAdapter;
import cn.tlrfid.view.adapter.WarehouseSafeAdapter;
import cn.tlrfid.view.control.WarehouseTitle;

public class MaintenanceReminders extends BaseActivity implements OnItemClickListener {
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
		nowPosition = 0;
		setActionBarTitle("养护提醒");
		new GetAssentAsync(this).execute();
		// wt_warehouse_title.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// String value = (String) v.getTag();
		// nowPosition = (Integer) v.getTag(R.id.tag_second_id);
		// new GetAssentAsync(MaintenanceReminders.this).execute(ConstantValues.WAREHOUSE_MANAGER_URL
		// + "?assetsCategory=" + value);
		// }
		// });
		
		lv_warehouse_list.setOnItemClickListener(this);
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.maintenance_reminder);
	}
	
	private int nowPosition = 0;
	@ViewById
	private WarehouseTitle wt_warehouse_title;
	@ViewById
	private ListView lv_warehouse_list;
	private WarehouseSafeAdapter wcAdapter;
	private List<DeviceHelpBean> dhbList;
	
	private class GetAssentAsync extends NetConnectionAsync<Void, Void, List<DeviceHelpBean>> {
		private UniversalEngine ue;
		
		public GetAssentAsync(BaseActivity activity) {
			super(activity, "正在获取数据...", false);
			AlertDialogUtil.showLoading(activity, "正在获取数据...", true);
		}
		
		@Override
		protected List<DeviceHelpBean> doInBackground(Void... params) {
			ue = InstanceFactory.getEngine(UniversalEngine.class);
			dhbList = ue.getUrlBean(ConstantValues.MAINTENANCE_REMINDER, DeviceHelpBean.class);
			return dhbList;
		}
		
		@Override
		protected void onPostExecute(List<DeviceHelpBean> result) {
			AlertDialogUtil.dissmissLoading();
			if (result == null || result.size() == 0) {
				SystemNotification.showToast(getApplicationContext(), "获取服务器数据失败..");
				return;
			}
			if (wcAdapter == null) {
				wcAdapter = new WarehouseSafeAdapter(getApplicationContext());
			}
			lv_warehouse_list.setAdapter(wcAdapter);
			wcAdapter.changeList(result);
			
		}
	}
	
	private class MaintenAdapter extends FrameAdapter {
		
		public MaintenAdapter(Context context) {
			super(context);
		}
		
		@Override
		public int getCount() {
			return dhbList == null ? 0 : dhbList.size();
		}
		
		@Override
		public View getItemView(int position, View convertView, ViewGroup parent) {
			return null;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
	
}
