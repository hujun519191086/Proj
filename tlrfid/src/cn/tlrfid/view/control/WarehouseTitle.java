package cn.tlrfid.view.control;

import java.util.ArrayList;
import java.util.List;

import cn.tlrfid.R;
import cn.tlrfid.bean.warehouse.AssetsCategoryBean;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.LogUtil;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WarehouseTitle extends LinearLayout {
	
	protected static final String TAG = "WarehouseTitle";
	
	public WarehouseTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public WarehouseTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public WarehouseTitle(Context context) {
		super(context);
		init();
	}
	
	private LayoutParams params;
	private LayoutParams lineParams;
	private List<TextView> contentList;
	
	private void init() {
		setOrientation(HORIZONTAL);
		
		params = new LayoutParams(DensityUtil.dip2px(100), -1);
		lineParams = new LayoutParams(DensityUtil.dip2px(1), -1);
	}
	
	public void setTitle(List<AssetsCategoryBean> list, int position) {
		if (list == null) {
			return;
		}
		removeAllViews();
		contentList = new ArrayList<TextView>();
		for (int i = 0; i < list.size(); i++) {
			AssetsCategoryBean acb = list.get(i);
			TextView tv = new TextView(getContext());
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(18);
			tv.setText(acb.getName());
			tv.setTag(acb.getValue());
			tv.setTag(R.id.tag_second_id, i);
			tv.setLayoutParams(params);
			tv.setOnClickListener(oclisener);
			addView(tv);
			contentList.add(tv);
			if (i < list.size() - 1) {
				tv = new TextView(getContext());
				tv.setBackgroundColor(0xFF67B3D9);
				tv.setLayoutParams(lineParams);
				addView(tv);
			}
		}
		changeBackground(position);
	}
	
	private OnClickListener oclisener;
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		this.oclisener = l;
	}
	
	public void changeBackground(int position) {
		if (contentList == null) {
			return;
		}
		for (int i = 0; i < contentList.size(); i++) {
			TextView tempTv = contentList.get(i);
			if (i == position) {
				tempTv.setBackgroundColor(0xFF1D90C9);
				tempTv.setTextColor(Color.WHITE);
				tempTv.setClickable(false);
			} else {
				tempTv.setClickable(true);
				tempTv.setTextColor(Color.BLACK);
				tempTv.setBackgroundColor(0x00000000);
				
			}
		}
	}
	
}
