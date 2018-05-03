package com.qc188.com.util;

import android.content.Context;

public class Utils {

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static boolean emailValidate(String email) {
		if (email == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}
	}

	public static boolean isbnValidate(String isbn) {
		if (Utils.isNumeric(isbn)) {
			if (isbn.length() == 10 || isbn.length() == 13) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
