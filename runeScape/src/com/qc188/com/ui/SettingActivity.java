package com.qc188.com.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.qc188.com.R;
import com.qc188.com.framwork.BaseActivity;
import com.qc188.com.util.CacheUtil;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.OpenUrl;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private SharedPreferences sp;
	private boolean isNoPic;
	private Button bt_noPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		Button bt_clear_cache = (Button) findViewById(R.id.bt_clear_cache);
		Button bt_goto_mainURL = (Button) findViewById(R.id.bt_goto_mainURL);
		bt_noPic = (Button) findViewById(R.id.bt_noPic);
		sp = getSharedPreferences("prop", Context.MODE_PRIVATE);
		isNoPic = sp.getBoolean("noPic", false);
        setTitleContent("更多");
		setButtonPic();
		bt_clear_cache.setOnClickListener(this);
		bt_goto_mainURL.setOnClickListener(this);
		bt_noPic.setOnClickListener(this);

		findById(R.id.bt_compairCar).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_clear_cache:

			if (CacheUtil.deleteChacheSize()) {
                    Toast.makeText(getApplicationContext(), "清除成功!!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bt_goto_mainURL:
                new OpenUrl(this, ConstantValues.MOBILE_URL);
			break;
		case R.id.bt_noPic:
			isNoPic = !isNoPic;
			sp.edit().putBoolean("noPic", isNoPic).commit();
			setButtonPic();
			break;

		case R.id.bt_compairCar:
			startActivity(CompairSelelctActivity.class);
			break;
		}
	}

	private void setButtonPic() {
		ConstantValues.NO_PIC = isNoPic;
		if (isNoPic) {
            bt_noPic.setText("无图模式[开:读取本地(如果有)]");
		} else {

            bt_noPic.setText("无图模式[关]");
		}
	}

}
