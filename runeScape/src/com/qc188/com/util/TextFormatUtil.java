package com.qc188.com.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class TextFormatUtil {

	public static void changeTextColor(TextView tv, int startIndex,
			int endIndex, int color) {

		if (endIndex == -1) {
			endIndex = tv.getText().length();
		}
		SpannableStringBuilder style = new SpannableStringBuilder(tv.getText());
		style.setSpan(new ForegroundColorSpan(color), startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(style);
	}
}
