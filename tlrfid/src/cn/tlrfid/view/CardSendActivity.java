package cn.tlrfid.view;

import android.view.View;
import cn.tlrfid.R;
import cn.tlrfid.framework.BaseActivity;

public class CardSendActivity extends BaseActivity {
	
	@Override
	public void onClick(View v) {
		
	}
	
	@Override
	public void init() {
		title_bar_content.setText("工程发卡");
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.card_send);
	}
	
}
