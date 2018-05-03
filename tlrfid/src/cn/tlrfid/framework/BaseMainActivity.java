package cn.tlrfid.framework;

import cn.tlrfid.bean.ControlHolder;
import cn.tlrfid.view.async.LoadProjectManagerAsync;

public abstract class BaseMainActivity extends BaseActivity {
	protected ControlHolder cholder;

	/**
	 * 加载主界面的数据
	 */
	public void loadMainInfo() {
		loadMainInfo(true);
	}

	/**
	 * 加载主界面数据
	 * 
	 * @param openAsync
	 *            是否开启自动asyn并且赋值. false为不开启
	 */
	public void loadMainInfo(boolean openAsync) {
		cholder = new ControlHolder();
		cholder.init(getWindow().getDecorView());
		if (openAsync) {
			new LoadProjectManagerAsync(this, cholder).execute(ConstantValues.PROJECT_CODE + "=" + Config_values.PROJECT_CODE);
		}
	}
}
