package cn.tlrfid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.tlrfid.R;

/**
 * 主界面的跳转方案.
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.main_bt_clickToProgress).setOnClickListener(this);
		findViewById(R.id.main_bt_editorCurriculumVitae).setOnClickListener(this);
		findViewById(R.id.main_bt_showCurriculumVitae).setOnClickListener(this);
		findViewById(R.id.main_bt_toMain).setOnClickListener(this);
		findViewById(R.id.main_bt_showLogin).setOnClickListener(this);
		
		findViewById(R.id.main_bt_showMaintain).setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_bt_toMain:
			toActivity(MainViewPagerActivity.class);
			break;
		// 去进度管理 progressCheckActivity
		case R.id.main_bt_clickToProgress:
			toActivity(ProgressQueryActivity.class);
			break;
		// 去编辑履历
		case R.id.main_bt_editorCurriculumVitae:
			toActivity(CurriculumVitae_Create_Activity.class);
			break;
		// 去显示履历一览
		case R.id.main_bt_showCurriculumVitae:
			
			toActivity(CurriculumVitae_Query_Activity.class);
			break;
		// 登录界面
		case R.id.main_bt_showLogin:
			toActivity(LoginActivity.class);
			
			break;
		
		case R.id.main_bt_showMaintain:
			toActivity(MaintainActivity.class);
			
		default:
			break;
		}
	}
	
	/**
	 * 跳转到activity
	 * 
	 * @param clazz
	 */
	private void toActivity(Class<? extends Activity> clazz) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), clazz);
		startActivity(intent);
	}
	
}