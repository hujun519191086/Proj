package cn.tlrfid.view;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.bean.warehouse.DevicePagerBean;
import cn.tlrfid.engine.WarehouseEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.adapter.WarehouseContentAdapter;
import cn.tlrfid.view.control.PullToRefreshView;
import cn.tlrfid.view.control.PullToRefreshView.OnFooterRefreshListener;
import cn.tlrfid.view.control.PullToRefreshView.OnHeaderRefreshListener;
import cn.tlrfid.view.control.WarehouseTitle;

public class WarehouseManagerActivity extends BaseActivity implements OnItemClickListener {
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
		nowPosition = 0;
		nowPage = 1;
		setLeftListType(LeftListEnum.WAREHOUSE, new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				switch (position) {
				case 1:
					startActivity(Maintain_Device_Activity.class);
					break;
				case 2:
					startActivity(MaintenanceReminders.class);
					break;
				}
			}
		});
		
		prv_warehouse_refrshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				prv_warehouse_refrshView.onHeaderRefreshComplete();
			}
		});
		new GetAssentAsync(this).execute(ConstantValues.WAREHOUSE_MANAGER_URL);
		wt_warehouse_title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String value = (String) v.getTag();
				nowPosition = (Integer) v.getTag(R.id.tag_second_id);
				nowPage = 1;
				new GetAssentAsync(WarehouseManagerActivity.this).execute(ConstantValues.WAREHOUSE_MANAGER_URL
						+ "?assetsCategory=" + value);
			}
		});
		
		lv_warehouse_list.setOnItemClickListener(this);
	}
	
	private int nowPosition = 0;
	
	@Override
	public void onCreateView() {
		setContentViewNoActionBar(R.layout.warehouse_manager);
	}
	
	private int nowPage = 1;
	
	private static final String TAG = "WarehouseManagerActivity";
	@ViewById
	private WarehouseTitle wt_warehouse_title;
	@ViewById
	private ListView lv_warehouse_list;
	@ViewById
	private PullToRefreshView prv_warehouse_refrshView;
	private WarehouseContentAdapter wcAdapter;
	
	private class GetAssentAsync extends NetConnectionAsync<String, Void, String> {
		private WarehouseEngine we;
		
		public GetAssentAsync(BaseActivity activity) {
			super(activity, "正在获取数据...", false);
			AlertDialogUtil.showLoading(activity, "正在获取数据...", true);
		}
		
		@Override
		protected String doInBackground(String... params) {
			we = InstanceFactory.getEngine(WarehouseEngine.class);
			// UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
			// DeviceDetailBean ddb = ue.getUrlBean(
			// "http://192.168.1.101:8080/spms/service/queryByTagCode.action?tagCode=FD0000000000000000000011",
			// DeviceDetailBean.class);
			// LogUtil.d(TAG, "ddb:" + ddb);
			
			// "http://192.168.1.101:8080/spms/service/queryByTagCode.action?tagCode=FE0000000000000000000044"
			
			return we.initConnectionJson(params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (!TextUtils.isEmpty(result)) {
				SystemNotification.showToast(getApplicationContext(), result);
				return;
			}
			AlertDialogUtil.dissmissLoading();
			wt_warehouse_title.setTitle(we.getAssetsList(), nowPosition);
			if (wcAdapter == null) {
				wcAdapter = new WarehouseContentAdapter(getApplicationContext());
				lv_warehouse_list.setAdapter(wcAdapter);
			}
			final DevicePagerBean dpBean = we.getDevicePager();
			if (prv_warehouse_refrshView.mFooterState == PullToRefreshView.REFRESHING) {
				prv_warehouse_refrshView.onFooterRefreshComplete();
				wcAdapter.addList(dpBean);
			} else {
				wcAdapter.changeList(dpBean);
			}
			prv_warehouse_refrshView.setOnFooterRefreshListener(new OnFooterRefreshListener() {
				
				@Override
				public void onFooterRefresh(PullToRefreshView view) {
					LogUtil.d(
							TAG,
							"dpBean:" + dpBean.getPage() + "   " + dpBean.getPageSize() + "    "
									+ dpBean.getTotalPage() + "      " + dpBean.getTotalCount());
					if (dpBean.getPage() < dpBean.getTotalPage()) {
						new GetAssentAsync(WarehouseManagerActivity.this).execute(ConstantValues.WAREHOUSE_MANAGER_URL
								+ "?page=" + (dpBean.getPage() + 1));
					} else {
						prv_warehouse_refrshView.onFooterRefreshComplete();
						SystemNotification.showToast(getApplicationContext(), "没有更多数据!");
					}
				}
			});
			
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		String assentCode = (String) view.getTag(R.id.tag_search_tagCode);
		startActivity(Maintain_Device_Activity.class, "assentCode", assentCode);
	}
	
}
