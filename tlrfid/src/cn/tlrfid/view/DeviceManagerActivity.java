package cn.tlrfid.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.tlrfid.R;
import cn.tlrfid.framework.BaseActivity;

public class DeviceManagerActivity extends BaseActivity {
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
//		setLeftListType(LeftListEnum.DEVICE_MANAGER, new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				switch (position) {
//				case 1:
//					startActivity(MaintainActivity.class);
//					break;
//				case 2:
//					startActivity(CurriculumVitae_Query_Activity.class);
//					break;
//				case 3:
//					startActivity(CurriculumVitae_Update_Activity.class);
//					break;
//				default:
//					break;
//				}
//			}
//		});
	}
	
	@Override
	public void onCreateView() {
		setContentViewNoActionBar(R.layout.devicemanager);
	}
	
}
