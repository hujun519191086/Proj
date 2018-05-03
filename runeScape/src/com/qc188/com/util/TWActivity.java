package com.qc188.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qc188.com.R;
import com.qc188.com.ui.MainActivity.AnimCommon;

public class TWActivity extends Activity {
	ProgressDialog progressDialog;

	public void showProgressDialogWithTitle(String info) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(info);
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	public void hideProgressDialog() {
		progressDialog.dismiss();
	}

	public void showAlertWithMessage(String msg) {
		new AlertDialog.Builder(this)
				.setMessage(msg)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// continue with delete
							}
						}).show();
	}

	public void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void setTitle(int resId) {
		setTitle(getString(resId));
	}

	public void visibleTitleBack(OnClickListener listener) {
		Button bt = (Button) findViewById(R.id.btnBack);
		bt.setOnClickListener(listener);
		bt.setVisibility(View.VISIBLE);
	}

	public void setTitle(String str) {
		TextView titleLabel = (TextView) findViewById(R.id.tvTitle);
		titleLabel.setText(str);
	}

	/*
	 * public void showBackButton(){ ImageView backIcon = (ImageView)
	 * findViewById(R.id.backIcon); Button btnBack = (Button)
	 * findViewById(R.id.btnBack); backIcon.setVisibility(View.VISIBLE);
	 * btnBack.setVisibility(View.VISIBLE);
	 * 
	 * btnBack.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) { finish(); } }); }
	 */

	public void toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
}
