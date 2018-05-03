package cn.tlrfid.view.async;

import cn.tlrfid.engine.UniversalEngine;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.NetConnectionAsync;
import cn.tlrfid.utils.CardNetConnection;
import cn.tlrfid.utils.ReadCardSche;
import cn.tlrfid.utils.SystemNotification;

public class SendCardAsync extends NetConnectionAsync<Object, Void, BaseBean> {
	private CardNetConnection cnc;
	private String carNumber;
	
	public SendCardAsync(BaseActivity activity, CardNetConnection cnc) {
		super(activity, "", false);
		context = activity.getApplicationContext();
		this.cnc = cnc;
	}
	
	@Override
	protected BaseBean doInBackground(Object... params) {
		UniversalEngine ue = InstanceFactory.getEngine(UniversalEngine.class);
		this.carNumber = params[2].toString();
		return ue.getUrlBean(params[0].toString() + params[2].toString(), (Class<? extends BaseBean>) params[1]);
	}
	
	@Override
	protected void onNetResult(BaseBean result) {
		if (result != null) {
			ReadCardSche.getInstance().changeKeyState(result.getInTag());
			cnc.onCardReadOne(result);
			cnc.onCardRead();
			return;
		}
		
		SystemNotification.showToast(context, "数据库无此数据:" + carNumber);
		
	}
}
