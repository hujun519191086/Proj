package cn.tlrfid.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.anno.ViewById_Clickthis;
import cn.tlrfid.bean.ProjectInfoBean;
import cn.tlrfid.engine.ProjectInfoEngine;
import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.Config_values;
import cn.tlrfid.framework.ConstantValues;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.utils.AlertDialogUtil;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.utils.LogUtil;
import cn.tlrfid.utils.SystemNotification;

public class LoginActivity extends BaseActivity {
	
	private static final String TAG = "LoginActivity";
	@ViewById
	private ImageView login_iv_show;
	@ViewById
	private EditText login_et_user;
	@ViewById
	private EditText login_et_password;
	
	@ViewById_Clickthis
	private ImageButton login_ib_setting;
	
	@ViewById_Clickthis
	private Button login_clickToLogin;
	private Dialog dialog;
	private SharedPreferences sp; // 设置的sp
	@ViewById_Clickthis
	private CheckBox check_box;
	
	private void initData() {
		
		// ConstantValues.initClientPre(sp.getString("ip", "http://182.92.76.78:8080/spms"));
		// ConstantValues.initClientPre(sp.getString("ip", "http://192.168.1.105:8080/spms"));
		// ConstantValues.initClientPre("http://192.168.1.105:8080/spms");
		
		Bitmap mBitmap = BitmapUtil.getBitmap(this, R.drawable.item_curriculum_vitae_query_default_pic);
		Bitmap zoomBitmap = BitmapUtil.zoomBitmap(mBitmap, 80, 80);
		BitmapDrawable drawable = new BitmapDrawable(getResources(), zoomBitmap);
		LogUtil.d(TAG, "login_iv_show:" + login_iv_show);
		login_iv_show.setBackground(drawable);
		
		String name = sp.getString("login_name", Config_values.USERNAME);
		String pwd = sp.getString("pwd", Config_values.PASSWORD);
		
		login_et_user.setText(name);
		login_et_password.setText(pwd);
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.login_clickToLogin:
			new LoginAsync(this).execute(ConstantValues.LOGIN_NAME + "=" + Config_values.USERNAME,
					ConstantValues.LOGIN_PASSWORD + "=" + Config_values.PASSWORD, ConstantValues.PROJECT_CODE + "="
							+ Config_values.PROJECT_CODE);
			// startActivity(MaintainActivity.class);
			
			break;
		case R.id.login_ib_setting:
			
			AlertDialog.Builder builder = new Builder(this);
			
			View view = View.inflate(this, R.layout.login_dialog, null);
			
			final EditText et_project_no = (EditText) view.findViewById(R.id.et_project_no);
			final EditText et_ip = (EditText) view.findViewById(R.id.et_ip);
			Button bt_dialog_cancel = (Button) view.findViewById(R.id.bt_dialog_cancel);
			Button bt_dialog_ok = (Button) view.findViewById(R.id.bt_dialog_ok);
			
			bt_dialog_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
			
			bt_dialog_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String project_no = et_project_no.getText().toString().trim();
					String ip = et_ip.getText().toString().trim();
					
					Editor edit = sp.edit();
					
					if (!TextUtils.isEmpty(ip)) {
						
						ConstantValues.initClientPre(ip);
						LogUtil.d(TAG, "ip:" + ip + "   constvalues:" + ConstantValues.LOGIN_URL);
						edit.putString("ip", ip);
					}
					if (!TextUtils.isEmpty(project_no)) {
						Config_values.initProjectCode(project_no);
						edit.putString("project_no", project_no);
						Config_values.PROJECT_CODE = project_no;
					}
					
					edit.commit();
					
					dialog.dismiss();
				}
			});
			
			builder.setView(view);
			
			builder.setTitle("设置");
			dialog = builder.create();
			
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
			
			dialog.show();
			
		default:
			break;
		}
	}
	
	@Override
	public void init() {
		
		sp = getSharedPreferences("login_info", MODE_PRIVATE);
		
		initData();
		AlertDialogUtil.getInstance(getApplicationContext());
		base_title_layout.setVisibility(View.GONE);
		
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.login);
	}
	
	private class LoginAsync extends NetConnectionAsync<String, Void, ProjectInfoBean> {
		
		public LoginAsync(BaseActivity activity) {
			super(activity, "正在登陆");
		}
		
		@Override
		protected ProjectInfoBean doInBackground(String... params) {
			String url = ConstantValues.LOGIN_URL + "?" + params[0];
			for (int i = 1; i < params.length; i++) {
				url = url + "&" + params[i];
			}
			
			LogUtil.d(TAG, "logurl:" + url);
			ProjectInfoEngine pie = InstanceFactory.getEngine(ProjectInfoEngine.class);
			LogUtil.d(TAG, "url:" + url);
			return pie.getProjectInfo(url, ProjectInfoBean.class);
		}
		
		@Override
		protected void onNetResult(ProjectInfoBean result) {
			LogUtil.d(TAG, "result:" + result);
			if (result != null) {
				startActivity(ProjectManageActivity.class, "loginResult", result);
				// 登录成功
				if (check_box.isChecked()) {
					String loginName = login_et_user.getText().toString().trim();
					String passWd = login_et_password.getText().toString().trim();
					Editor edit = sp.edit();
					edit.putString("login_name", loginName);
					edit.putString("pwd", passWd);
					edit.commit();
					System.out.println("projectCode:" + Config_values.PROJECT_CODE);
				}
				finish();
			} else {
				SystemNotification.showToast(getApplicationContext(), "网络异常!!");
			}
		}
	}
}
