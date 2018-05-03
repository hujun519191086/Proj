package cn.tlrfid.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class TextFormatUtil {
	
	public static void changeTextColor(TextView tv, int startIndex, int endIndex, int color) {
		SpannableStringBuilder style = new SpannableStringBuilder(tv.getText());
		style.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(style);
	}
}
