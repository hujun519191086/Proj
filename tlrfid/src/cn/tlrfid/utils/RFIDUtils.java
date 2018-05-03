package cn.tlrfid.utils;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import cn.tlrfid.engine.RFIDReadEngine;

import com.cetc7.UHFReader.UHFReaderClass;

/**
 * 读卡写卡
 * 
 * @author dao
 * 
 */
public class RFIDUtils {
	
	/** RFID 主类 */
	private UHFReaderClass RFID;
	/** 模式(0:主从模式 1：触发模式) */
	private int RFIDMode = 0;
	/** 硬件是否连接 */
	private boolean bConnected = false;
	
	private boolean bSetMode = false;
	
	/** RFID模块工作在触发模式或者主从模式下，触发以后需要对扫描到的数据的进行监听 */
	// private UHFReaderClass.OnEPCsListener listen;
	
	private static RFIDUtils mRFIDUtil = null;
	
	private Handler mHandler;
	private Handler mHandler2;
	
	private RFIDReadEngine mRFIDRead;
	
	private Context context;
	
	private byte[] data = null;
	
	private RFIDUtils() {
	}
	
	private ProgressDialog mProgress;
	
	private RFIDUtils(Context context) {
		RFID = UHFReaderClass.GetUHFReader();
		this.context = context;
		mProgress=new ProgressDialog(context);
		mProgress.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		// mHandler = new Handler() {
		// @SuppressWarnings("unchecked")
		// @Override
		// public void handleMessage(Message msg) {
		// super.handleMessage(msg);
		// switch (msg.what) {
		// case 0:
		// AlertDialogUtil.dissmissLoading();
		// ArrayList<String> mEpcList = (ArrayList<String>) msg.obj;
		// mRFIDRead.readRFID(mEpcList);
		// closeConnect();
		// break;
		// }
		//
		// }
		// };
		// listen = new UHFReaderClass.OnEPCsListener() {
		// // 对接收到EPC数据的处理
		// // 由于onEPCsRecv是被封装在UHFReaderClass类的非UI线程调用的，
		// // 要实现把数据实时刷到界面，需通过Handle交回给UI线程处理。
		// public void onEPCsRecv(ArrayList<String> EPCList) {
		// mHandler.sendMessage(Message.obtain(mHandler, 0, EPCList));
		// }
		// };
		
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
			}
		};
		mHandler2 = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
			}
		};
		
	}
	
	public void setRFAttenuation(int mRFAttenuation) {
		RFID.SetRFAttenuation(mRFAttenuation);
	}
	
	public void setRFIDReadListener(RFIDReadEngine mRFIDRead) {
		this.mRFIDRead = mRFIDRead;
	}
	
	public static RFIDUtils getInstance(Context context) {
		if (mRFIDUtil == null) {
			synchronized (RFIDUtils.class) {
				if (mRFIDUtil == null) {
					mRFIDUtil = new RFIDUtils(context);
				}
			}
		}
		return mRFIDUtil;
	}
	
	public void startReadEPCs(final UHFReaderClass.OnEPCsListener listen) {
//		AlertDialogUtil.showLoading(context, "扫描中...");
		// 打开连接
		int stateCon = RFID.Connect();
		if (stateCon != 0) {
			//SystemNotification.showToast(context, "连接失败...");
			return;
		}
		
		// 设置模式
		int stateMode = RFID.SetReaderMode(2);
		if (stateMode != 0) {
			//SystemNotification.showToast(context, "连接失败...");
			return;
		}
		
		int stateLis = RFID.TirggerStart(listen);
		if (stateLis != 0) {
			//SystemNotification.showToast(context, "设置触发模式失败...");
		}
	}
	
	public void stopReadEPCs() {
//		AlertDialogUtil.dissmissLoading();
		int stateStop = RFID.TriggerStop();
		if (stateStop != 0) {
			//SystemNotification.showToast(context, "触发停止失败...");
			return;
		}
		
		int stateDis = RFID.DisConnect();
		if (stateDis != 0) {
			//SystemNotification.showToast(context, "断开连接失败...");
		}
	}
	
	public void readEPCs(final UHFReaderClass.OnEPCsListener listen) {
		
		AlertDialogUtil.getInstance(context).alert(context, "提示", "请将卡紧贴在本设备背部感应区,否则不能读取到卡中数据...", "确定",
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
						new AsyncTask<Void, Void, Integer>() {
							
							@Override
							protected Integer doInBackground(Void... params) {
								
								openConnect();
								if (!bConnected) {
									return null;
								}
								
								setMode();
								
								if (!bSetMode) {
									return null;
								} else {
									bSetMode = false;
								}
								
								int state = RFID.ReadEPCs(listen);
								return state;
								
								// ArrayList<String> mList = RFID.ReadEPCs();
								
								// return mList;
							}
							
							@Override
							protected void onPreExecute() {
								super.onPreExecute();
								AlertDialogUtil.showLoading(context, "扫描中...");
							}
							
							@Override
							protected void onPostExecute(Integer result) {
								super.onPostExecute(result);
								if (result == 0) {
								} else {
									AlertDialogUtil.getInstance(context).alert(context, "提示", "读取失败,请重新扫描...", "确定",
											null);
								}
								// if (result == null || result.size() == 0) {
								// AlertDialogUtil.getInstance(context).alert(context, "提示", "连接失败或者未扫描到卡...", "确定",
								// null);
								// }else{
								// mRFIDRead.readRFID(result);
								// }
								AlertDialogUtil.dissmissLoading();
								closeConnect();
							}
							
						}.execute((Void) null);
						
					}
				}, "取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		
	}
	
	/**
	 * 打开连接
	 */
	public void openConnect() {
		int count = 3;
		int stateConnect = -1;
		while (count > 0) {
			count--;
			stateConnect = RFID.Connect();
			if (stateConnect == 0) {
				bConnected = true;
				count = 0;
			}
		}
	}
	
	/**
	 * 关闭连接
	 */
	public void closeConnect() {
		int count = 3;
		int stateConnect = -1;
		while (count > 0) {
			count--;
			stateConnect = RFID.DisConnect();
			if (stateConnect == 0) {
				bConnected = false;
				count = 0;
			}
		}
	}
	
	/**
	 * 设置读取模式
	 */
	public void setMode() {
		int count = 3;
		int stateMode = -1;
		// 设置模式为触发模式
		while (count > 0) {
			stateMode = RFID.SetReaderMode(2);
			if (stateMode == 0) {
				bSetMode = true;
				count = 0;
			}
		}
	}
	
	public byte[] epc;
	public byte[] mByte;
	
	/**
	 * 读取用户数据(最好在子线程中调用)
	 */
	public byte[] read6CUserData() {
		
		epc = null;
		mByte = null;
		
		AlertDialogUtil.getInstance(context).alert(context, "提示", "请将卡放置在本设备背部感应区...", "确定",
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						openConnect();
						alertConnectedDialog();
						epc = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
						mByte = RFID.Read6CUserData(epc, 0, 2);
						closeConnect();
						
					}
				}, "取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		
		return mByte;
		
		// new AsyncTask<Void, Void, byte[]>() {
		//
		// @Override
		// protected byte[] doInBackground(Void... params) {
		// return RFID.Read6CUserData(epc, 0, 2);
		// }
		//
		// protected void onPostExecute(byte[] result) {
		// AlertDialogUtil.dissmissLoading();
		// data = result;
		// };
		//
		// }.execute((Void) null);
	}
	
	/**
	 * 写用户数据(最好在子线程中调用)
	 * 
	 * @return 0:成功，其它为失败
	 */
	public int write6CUserData() {
		openConnect();
		alertConnectedDialog();
		byte[] pw = new byte[] { 0, 0, 0, 0 };
		byte[] epc = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int state = RFID.Write6CUserData(epc, pw, 0, 2, data);
		closeConnect();
		return state;
	}
	
	public void alertConnectedDialog() {
		if (!bConnected) {
			AlertDialogUtil.getInstance(context).alert(context, "提示", "连接失败,请重新扫描...", "确定", null);
		}
	}
}
