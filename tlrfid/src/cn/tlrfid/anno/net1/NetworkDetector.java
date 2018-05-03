package cn.tlrfid.anno.net1;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

public class NetworkDetector {

	/**
	 * 检测当前网络是否可用
	 * 
	 * @param act
	 *            Current Activity
	 * @return true : 可用 <br>
	 *         false : 不可用
	 */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		if (null == manager) {
			return false;
		}
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (null == networkInfo || !networkInfo.isAvailable()) {
			return false;
		}

		return true;
	}
	
}
