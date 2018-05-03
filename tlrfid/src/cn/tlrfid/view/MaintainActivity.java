package cn.tlrfid.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.tlrfid.R;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.utils.BitmapUtil;

public class MaintainActivity extends BaseActivity implements OnClickListener {
	
	private ImageView maintain_iv; // 显示的图片
	
	private void initView() {
		
		maintain_iv = (ImageView) findViewById(R.id.maintain_iv);
		
		Button maintain_bt_openCamera = (Button) findViewById(R.id.maintain_bt_openCamera);
		
		Button maintain_bt_checkbox = (Button) findViewById(R.id.maintain_bt_choose);
		
		maintain_bt_openCamera.setOnClickListener(this);
		
		maintain_bt_checkbox.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.maintain_bt_choose:
			AlertDialog.Builder build = new Builder(MaintainActivity.this);
			
			build.setTitle("请选择养护项");
			
			String[] items = new String[] { "asd", "ret", "rty", "ytu" };
			
			boolean[] checks = new boolean[] { false, false, false, false };
			
			build.setMultiChoiceItems(items, checks, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// TODO Auto-generated method stub
					
				}
			});
			
			build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
			build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			
			Dialog dialog = build.create();
			
			dialog.show();
			
			break;
		
		case R.id.maintain_bt_openCamera:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 0);
			break;
		
		default:
			
			break;
		
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// 返回是得到bitmap更改图片
		if (resultCode == Activity.RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap bitmap = (Bitmap) extras.get("data");
			/*
			 * Toast toast=Toast.makeText(this, "相片已保存在:SDcard/DCIM/Camera目录中", Toast.LENGTH_LONG);
			 * toast.setGravity(Gravity.BOTTOM, 0, 0); toast.show();
			 */
			Bitmap cornerBitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 3);
			maintain_iv.setBackground(new BitmapDrawable(getResources(), cornerBitmap));
			
			Toast.makeText(getApplicationContext(), "拍照成功！！！", 0).show();
		}
	}
	
	@Override
	public void init() {
		Bitmap bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.item_curriculum_vitae_query_default_pic);
		
		Bitmap cornerBitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 3);
		maintain_iv.setBackground(new BitmapDrawable(getResources(), cornerBitmap));
		
		title_bar_content.setText("养护");
		
	}
	
	@Override
	public void onCreateView() {
		setContentView(R.layout.maintain);
		
		initView();
		
	}
	
}
