package cn.tlrfid.framework;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import cn.tlrfid.factory.AdapterFactory;
import cn.tlrfid.factory.InstanceFactory;
import cn.tlrfid.utils.DensityUtil;
import cn.tlrfid.utils.LogUtil;

public abstract class FrameAdapter extends BaseAdapter {
	
	private static final String TAG = "FrameAdapter";
	protected Context context;
	protected AbsListView alv;
	
	// private HashMap<Integer, WeakHashMap<Integer, Drawable>> bitHashMap;
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	protected int itemHeight;
	
	protected static final int NO_STATE_PIC = 0;
	
	public View getItemView(int position) {
		return null;
	}
	
	public int readResourceDimens(int id) {
		return context.getResources().getDimensionPixelSize(id);
	}
	
	/**
	 * 正在显示条目的view
	 * 
	 * @param position
	 * @return
	 */
	
	public Object getList() {
		return null;
	}
	
	/**
	 * 设置item的params高度. 高度数量如果为0,默认为60
	 * 
	 * @param view
	 * @return
	 */
	public View setitemAbsParams(View view) {
		int heigth = itemHeight;
		if (heigth == 0) {
			heigth = 100;
		}
		view.setLayoutParams(new AbsListView.LayoutParams(-1, heigth));
		
		return view;
	}
	
	public int getItemHeigth() {
		int heigth = itemHeight;
		if (heigth == 0) {
			heigth = 100;
		}
		LogUtil.d(TAG, "itemheigth:" + itemHeight);
		return heigth;
	}
	
	protected void setItemHeightInDP(int dpValue) {
		itemHeight = DensityUtil.dip2px(dpValue);
	}
	
	protected void setItemHeightInPX(int pxValues) {
		itemHeight = pxValues;
	}
	
	public FrameAdapter(Context context) {
		this.context = context;
	}
	
	/**
	 * /** 构造函数解释
	 * 
	 * @param context
	 * @param alv 该 listview lisview
	 * @param resId item的高度, 如果不需要,可以传入默认0
	 */
	public FrameAdapter(Context context, AbsListView alv, int resId) {
		this.alv = alv;
		this.context = context;
		LogUtil.d(TAG, "context:" + context);
		// if (alv instanceof PullToRefreshListView)
		// {
		//
		// }
		// else
		// {
		
		// if (alv != null) {
		// alv.setOnScrollListener(this);
		// }
		// }
		
		/**
		 * 清空item
		 */
		if (resId == 0) {
			return;
		}
		itemHeight = context.getResources().getDimensionPixelSize(resId);
		
		LogUtil.d(TAG, " dimens:" + itemHeight);
	}
	
	/**
	 * 自动填充 view 将viewholder放在 view里面.
	 * 
	 * @param convertView
	 * @param holder
	 * @param layoutId
	 * @return
	 */
	public View setConvertView_Holder(View convertView, Class<? extends BaseViewHolder> holder, int layoutId) {
		return setConvertView_Holder(convertView, holder, layoutId, true);
	}
	
	/**
	 * 免去viewholder的各种查找烦恼.
	 * 
	 * @param convertView 参数内的convertView
	 * @param holder 定义的viewholder,继承自BaseViewHolder,成员变量必须为public
	 * @param layoutId 需要充入的布局int
	 * @param setheigth 是否设置高度为构造函数内传入的高度! false为不设置
	 * @return 已处理完成的view
	 */
	public View setConvertView_Holder(View convertView, Class<? extends BaseViewHolder> holder, int layoutId,
			boolean setheigth) {
		if (convertView != null) {
			return convertView;
		}
		LogUtil.d(TAG, "   holder class:" + holder);
		BaseViewHolder vHolder = null;
		if (holder != null) {
			vHolder = InstanceFactory.getInstances(holder);
		}
		convertView = View.inflate(context, layoutId, null);
		if (setheigth) {
			setitemAbsParams(convertView);
		}
		LogUtil.d(TAG, "converView:" + convertView + "   holder:" + vHolder);
		if (vHolder != null) {
			convertView = AdapterFactory.putHolderToView(context, convertView, vHolder);
		}
		return convertView;
	}
	
	public static class BaseViewHolder {
		int position;
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public AbsListView getListView() {
		return alv;
	}
	
	public void smoothScrollToLast() {
		alv.smoothScrollToPosition(getCount() + 1);
	}
	
	public void setSelectionToBottom() {
		alv.setSelection(getCount() + 1);
	}
	
	protected boolean isFirstEnter = true;
	
	protected OnItemSelectedLisener oisListener;
	protected int pageSetIndex = -1;
	
	public void setSelection(int index, OnItemSelectedLisener oisListener) {
		this.oisListener = oisListener;
		this.pageSetIndex = index;
	}
	
	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = getItemView(position, convertView, parent);
		if (oisListener != null && position == pageSetIndex) {
			oisListener.onItemSelected(convertView);
		}
		return convertView;
	}
	
	public abstract View getItemView(int position, View convertView, ViewGroup parent);
	
	public interface OnItemSelectedLisener {
		public void onItemSelected(View view);
	}
	
	public int getViewHeight() {
		return 0;
	}
	
}
