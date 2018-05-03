package com.qc188.com.util;

import java.io.File;

public class CacheUtil {
	public static boolean deleteChacheSize() {
		File file = new File(ConstantValues.FILE_PATH);

		if (file != null && file.exists() && file.isDirectory()) {

			for (File item : file.listFiles()) {

				item.delete();
			}
			file.delete();
		}
		ConstantValues.homeUrlPreference.edit().clear().commit();
		return true;
	}

}
