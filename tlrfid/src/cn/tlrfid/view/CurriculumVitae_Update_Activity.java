package cn.tlrfid.view;

import android.app.Activity;
import android.app.ActivityManager;
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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import cn.tlrfid.R;
import cn.tlrfid.anno.ViewById;
import cn.tlrfid.framework.BaseActivity;
import cn.tlrfid.utils.BitmapUtil;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.SystemNotification;

/**
 * 新建履历
 * 
 * @author sk
 * 
 */
public class CurriculumVitae_Update_Activity extends BaseActivity {
	
	@ViewById
	private ImageView curriculumvitae_iv;
	
	@ViewById
	private ImageButton watermark_iv;
	
	@ViewById
	private Button bt_quanzhu;
	
	private WindowManager wm;
	
	private ActivityManager am;
	
	public void onCreateView() {
		setContentView(R.layout.curriculumvitae_update);
		
	};
	
	public void toOpen(View v) {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
		
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
			curriculumvitae_iv.setBackground(new BitmapDrawable(getResources(), cornerBitmap));
			
			SystemNotification.showToast(getApplicationContext(), "拍照成功！！！");
			
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.bt_quanzhu:
			
			watermark_iv.setVisibility(View.VISIBLE);
			
			FrameLayout.LayoutParams fLParams = (LayoutParams) watermark_iv.getLayoutParams();
			fLParams.gravity = Gravity.LEFT | Gravity.TOP;
			
			watermark_iv.setLayoutParams(fLParams);
			break;
		
		}
		
	}
	
	@Override
	public void init() {
		
		Bitmap bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.item_curriculum_vitae_query_default_pic);
		
		Bitmap cornerBitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 3);
		
		curriculumvitae_iv.setBackground(new BitmapDrawable(getResources(), cornerBitmap));
		
		bt_quanzhu.setOnClickListener(this);
		
		title_bar_content.setText("修改履历");
		
		watermark_iv.setOnTouchListener(new OnTouchListener() {
			int x;
			int y;
			private FrameLayout.LayoutParams params;
			
			@Override
			public boolean onTouch(View v, final MotionEvent event) {
				
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					
					x = (int) event.getRawX();
					y = (int) event.getRawY();
					
					break;
				case MotionEvent.ACTION_UP:
					Toast.makeText(getApplicationContext(), "当前坐标：" + params.leftMargin + "-" + params.topMargin, 0)
							.show();
					
					;
					
					AlertDialog.Builder builder = new Builder(CurriculumVitae_Update_Activity.this);
					
					builder.setTitle("确定圈住吗？");
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// int h = DensityUtil.getStatusBarHeight(CurriculumVitae_Update_Activity.this);
							
							watermark_iv.setVisibility(View.GONE);
							
							Bitmap src = BitmapUtil.drawableToBitmap(curriculumvitae_iv.getBackground());
							
							Bitmap watermark = BitmapFactory.decodeResource(getResources(), R.drawable.quanzhu);
							
							Bitmap bitmap = BitmapUtil.doodle(curriculumvitae_iv, src, watermark, params.leftMargin,
									params.topMargin);
							
							curriculumvitae_iv.setBackground(new BitmapDrawable(getResources(), bitmap));
							
						}
					});
					
					Dialog dialog = builder.create();
					
					dialog.show();
					
					break;
				
				case MotionEvent.ACTION_MOVE:
					
					params = (LayoutParams) v.getLayoutParams();
					
					int newX = (int) event.getRawX();
					
					int newY = (int) event.getRawY();
					
					int dx = newX - x;
					int dy = newY - y;
					
					params.leftMargin += dx;
					params.topMargin += dy;
					
					if (params.leftMargin <= 0) {
						params.leftMargin = 0;
					}
					if (params.leftMargin >= (curriculumvitae_iv.getWidth() - watermark_iv.getWidth())) {
						params.leftMargin = curriculumvitae_iv.getWidth() - watermark_iv.getWidth();
					}
					if (params.topMargin <= 0) {
						params.topMargin = 0;
					}
					if (params.topMargin >= (curriculumvitae_iv.getHeight() - watermark_iv.getHeight())) {
						params.topMargin = curriculumvitae_iv.getHeight() - watermark_iv.getHeight();
					}
					
					System.out.println(params.leftMargin + "-" + params.topMargin);
					v.setLayoutParams(params);
					// watermark_iv.invalidate();
					// 重新初始化手指
					x = (int) event.getRawX();
					y = (int) event.getRawY();
					
					break;
				
				}
				
				return true;
			}
		});
		
	}
}
