package com.qc188.com.ui.adapter;

import java.util.ArrayList;
import java.util.TreeMap;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.SortBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.interf.SideBarClickLisener;
import com.qc188.com.ui.control.SideBar;
import com.qc188.com.util.ConstantValues;
import com.qc188.com.util.DensityUtil;
import com.qc188.com.util.ImageLoadUtil;
import com.qc188.com.util.LogUtil;

public class SortAdapter extends FrameAdapter {

	private static final String TAG = "SortAdapter";
	ArrayList<SortBean> mList;
	SideBar sb;
	private TreeMap<Integer, Integer> word_map;
	private TreeMap<Integer, Integer> index_map;
	private Context context;
	// private AsyncImageTask imageTask = new AsyncImageTask();

	private int selectPosition = -1;

	public void setSelectPosition(int selectPosition) {
		this.selectPosition = selectPosition;
	}

	public int getSelectPosition() {
		return selectPosition;
	}

	public SortAdapter(Context context, SideBar sb, ArrayList<SortBean> mList, final ListView lv) {
		this.mList = mList;
		this.context = context;
		this.sb = sb;
		setItemHeigth(DensityUtil.dip2px(73));
		word_map = new TreeMap<Integer, Integer>();
		index_map = new TreeMap<Integer, Integer>();

		for (int i = 0; i < mList.size(); i++) {
			if (!lastType.equals(mList.get(i).getType_id())) {
				lastType = mList.get(i).getType_id();
				this.mList.add(i, null);
				index_map.put(word_positionIndex, i);
				word_map.put(i, word_positionIndex++);
			}
		}
		lastType = "";
		sb.setTouchListener(new SideBarClickLisener() {
			public void OnSide(View v, int position) {
				if (position < word_positionIndex) {
					if (lv != null && index_map != null && index_map.get(position) != null)
						lv.setSelection(index_map.get(position));
				}
			}
		});
	}

	public int getWordCount() {
		LogUtil.d(TAG, "getcount" + word_positionIndex);
		return word_positionIndex;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(position);
	}

	private String lastType = "";
	private int word_positionIndex = 0;
	private boolean rollOver = false;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SortBean sb = mList.get(position);
		if (sb == null) {
			int value = word_map.get(position);
			TextView tv = new TextView(context);
			tv.setTextSize(17);
			tv.setTextColor(context.getResources().getColor(R.color.search_abcColor));
			tv.setPadding(DensityUtil.dip2px(18), DensityUtil.dip2px(3), 0, DensityUtil.dip2px(3));
			tv.setBackgroundColor(0xDCDCDC);
			tv.setText(mList.get(position + 1).getType_id());
			tv.setBackgroundColor(context.getResources().getColor(R.color.search_abcbackgroundColor));
			return tv;
		}
		ViewHolder holder = null;
		if (convertView == null || convertView instanceof TextView) {
			convertView = View.inflate(context, R.layout.sort_listview_item, null);
			holder = new ViewHolder();
			holder.iv_sort_item_image = (ImageView) convertView.findViewById(R.id.iv_sort_item_image);
			holder.iv_sort_item_image.setTag(position);
			holder.tv_sort_item_text = (TextView) convertView.findViewById(R.id.tv_sort_item_text);
			convertView.setTag(holder);
			setLayoutParams(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LogUtil.d(TAG, "brand:" + sb.getCar_brand() + "url:" + sb.getBrand_url() + "  position:" + position);
		convertView.setTag(R.id.tag_first, sb.getBrand_id());
		convertView.setTag(R.id.tag_second, sb.getCar_brand());
		holder.tv_sort_item_text.setText(sb.getCar_brand());

		ImageLoadUtil.loadImageFromDefault(sb.getBrand_url(), holder.iv_sort_item_image, R.drawable.load_c);
		// Drawable drawable = imageTask.loadImage(holder.iv_sort_item_image,
		// sb.getBrand_url(), DensityUtil.dip2px(30), DensityUtil.dip2px(30),
		// new ImageCallback() {
		// @Override
		// public void imageLoaded(Drawable image, Object id) {
		// if (image != null) {
		// ((ImageView) id).setBackgroundDrawable(image);
		// // ImageView mainBg = (ImageView) lv
		// // .findViewWithTag(id);
		// // if (mainBg != null) {
		// // putBitmap(id + "", BitmapUtils
		// // .convertDrawable2BitmapByCanvas(image));
		// // mainBg.setImageDrawable(image);
		// // }
		// }
		// }
		// });
		// if (drawable != null) {
		// holder.iv_sort_item_image.setImageDrawable(drawable);// 13698409915
		// } else {
		// holder.iv_sort_item_image.setBackgroundResource(R.drawable.load_c);
		// }

		if (selectPosition == position) {
			convertView.setBackgroundColor(ConstantValues.SEARCH_SELECT_COLOR);
            convertView.findViewById(R.id.view_sort_item_select_left).setVisibility(View.VISIBLE);
		} else {
            convertView.findViewById(R.id.view_sort_item_select_left).setVisibility(View.GONE);
			convertView.setBackgroundColor(Color.WHITE);
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_sort_item_image;
		TextView tv_sort_item_text;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
