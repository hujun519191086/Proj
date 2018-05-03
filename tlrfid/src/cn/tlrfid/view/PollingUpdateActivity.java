package cn.tlrfid.view;

import java.util.ArrayList;

import android.view.View;
import android.widget.ListView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.bean.PollingResult;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.view.adapter.PollingResultAdapter;
import cn.tlrfid.view.adapter.PollingResult_updateAdapter;

public class PollingUpdateActivity extends BaseActivity {
	
	@ViewById
	private ListView polling_lv;
	
	private ArrayList<PollingResult> list;
	
	private PollingResult pr;
	
	@Override
	public void onClick(View arg0) {
		
	}
	
	@Override
	public void init() {
		list = new ArrayList<PollingResult>();
		
		pr = new PollingResult("1.人员配置", true, false, "", false);
		list.add(pr);
		pr = new PollingResult("2.专项活动", true, false, "", false);
		list.add(pr);
		pr = new PollingResult("3.原料质量", false, true, "强条", true);
		list.add(pr);
		pr = new PollingResult("4.施工记录", true, false, "", false);
		list.add(pr);
		pr = new PollingResult("5.实体质量", true, false, "", false);
		list.add(pr);
		pr = new PollingResult("6.强条工艺标准", true, false, "", false);
		list.add(pr);
		
		PollingResult_updateAdapter adapter = new PollingResult_updateAdapter(this, list);
		
		polling_lv.setAdapter(adapter);
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.polling_update);
		
		title_bar_content.setText("违规管理");
		
	}
	
}
