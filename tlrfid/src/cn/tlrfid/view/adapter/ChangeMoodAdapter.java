package cn.tlrfid.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import cn.tlrfid.R;

public class ChangeMoodAdapter extends PagerAdapter {
	private Context context;
	private ArrayList<View> adapterList;
	
	private static String[] str = { "我是进度管理", "我是质量/安全", "我是设备管理" };
	
	public ChangeMoodAdapter(Context context) {
		this.context = context;
		adapterList = new ArrayList<View>();
		
		for (int i = 0; i < str.length; i++) {
			// TextView tv = new TextView(context);
			// tv.setGravity(Gravity.CENTER);
			// tv.setTextSize(20);
			// tv.setText(str[i]);
			// tv.setTextColor(Color.WHITE);
			
			RelativeLayout mRelativeLayout = (RelativeLayout) View.inflate(context, R.layout.main_view_pager_inner,
					null);
			
			adapterList.add(mRelativeLayout);
		}
	}
	
	public int getCurrentItem() {
		return str.length * 300;
	}
	
	@Override
	public int getCount() {
		return Integer.MAX_VALUE - 2;
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// container.removeViewAt(getPosition(position));
		// container.removeView(adapterList.get(getPosition(position)));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = getPosition(position);
		container.removeView(adapterList.get(position));
		container.addView(adapterList.get(position));
		return adapterList.get(position);
	}
	
	public int getPosition(int position) {
		return position % str.length;
	}
	
}
