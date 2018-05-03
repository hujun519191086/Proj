package cn.tlrfid.view;

import java.util.HashMap;

import com.cetc7.UHFReader.UHFReaderClass;

import cn.tlrfid.R;
import cn.tlrfid.bean.PersonCardBean;
import cn.tlrfid.bean.ProjectCardBean;
import cn.tlrfid.framework.BaseBean;
import cn.tlrfid.framework.FrameAdapter;
import cn.tlrfid.utils.CardNetConnection;
import cn.tlrfid.utils.RFIDUtils;
import cn.tlrfid.utils.ReadCardSche;
import cn.tlrfid.utils.SystemNotification;
import cn.tlrfid.view.CurriculumVitae_Query_Activity.MyAdapter;
import cn.tlrfid.view.adapter.ScanIvAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MyAlertDialogShow implements OnItemClickListener {
	
	private Context context;
	private View view;
	private BaseAdapter adapter;
	private AlertDialog dialog;
	private String message;
	private UHFReaderClass.OnEPCsListener listen;
	private boolean isScaning = false;
	
	public MyAlertDialogShow(Context context, View view, BaseAdapter adapter, String message,
			UHFReaderClass.OnEPCsListener listen) {
		this.context = context;
		this.view = view;
		this.adapter = adapter;
		this.message = message;
		this.listen = listen;
	}
	
	public void show() {
		AlertDialog.Builder mBuilder = new Builder(context);
		ListView mListView = (ListView) view.findViewById(R.id.listView);
		mListView.setAdapter(adapter);
		Button startBtn = (Button) view.findViewById(R.id.start);
		// Button stopBtn = (Button) view.findViewById(R.id.stop);
		Button exitBtn = (Button) view.findViewById(R.id.exit);
		mListView.setOnItemClickListener(this);
		ReadCardSche.getInstance().clearMap();
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// SystemNotification.showToast(context, "开始");
				if (!isScaning) {
					RFIDUtils.getInstance(context).startReadEPCs(listen);
					isScaning = true;
					dialog.setTitle("正在扫描中....");
				}
				
			}
		});
		
		/*
		 * stopBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // SystemNotification.showToast(context, "停止"); if (isScaning) {
		 * isScaning = false; RFIDUtils.getInstance(context).stopReadEPCs(); dialog.setTitle("已经停止扫描"); } } });
		 */
		
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// SystemNotification.showToast(context, "退出");
				logOutRFID();
			}
		});
		dialog = mBuilder.create();
		dialog.setTitle(message);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				logOutRFID();
			}
		});
		dialog.show();
	}
	
	public void logOutRFID() {
		if (isScaning) {
			isScaning = false;
			RFIDUtils.getInstance(context).stopReadEPCs();
		}
		if (context instanceof CurriculumVitae_Query_Activity || context instanceof SelfManagerProject
				|| context instanceof SelfCheckedActivity || context instanceof QualityManagerProject
				|| context instanceof QualityCheckedActivity) {
			((MyAdapter) adapter).clearList();
		}
		onClickOut();
		ReadCardSche.getInstance().clearMap();
		ScanIvAdapter.AnimateFirstDisplayListener.displayedImages.clear();
		dialog.dismiss();
	}
	
	/**
	 * 点击退出的时候的操作
	 */
	public void onClickOut() {
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		logOutRFID();
	}
	
}
