package cn.tlrfid.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.tlrfid.R;
import cn.tlrfid.bean.warehouse.DevicePagerBean;
import cn.tlrfid.bean.warehouse.DevicePagerItemBean;
import cn.tlrfid.framework.FrameAdapter;

public class WarehouseContentAdapter extends FrameAdapter {
	
	public WarehouseContentAdapter(Context context) {
		super(context, null, 0);
		setItemHeightInPX(40);
	}
	
	private List<DevicePagerItemBean> list;
	
	public void changeList(DevicePagerBean dbPbean) {
		this.list = dbPbean.getItems();
		notifyDataSetChanged();
	}
	
	public void addList(DevicePagerBean dbPbean) {
		list.addAll(dbPbean.getItems());
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}
	
	private static class ViewHolder extends BaseViewHolder {
		TextView tv_item_warehorse_assetsCode;
		TextView tv_item_warehorse_assetNameName;
		TextView tv_item_warehorse_factoryName;
		TextView tv_item_warehorse_modelName;
		TextView tv_item_warehorse_personName;
		TextView tv_item_warehorse_conserveTypeName;
	}
	
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		
		boolean isFirst = convertView == null ? true : false;
		convertView = setConvertView_Holder(convertView, ViewHolder.class, R.layout.item_warehouse);
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if (isFirst) {
			holder.tv_item_warehorse_assetsCode.setBackgroundColor(0x00000000);
			holder.tv_item_warehorse_assetNameName.setBackgroundColor(0x00000000);
			holder.tv_item_warehorse_factoryName.setBackgroundColor(0x00000000);
			holder.tv_item_warehorse_modelName.setBackgroundColor(0x00000000);
			holder.tv_item_warehorse_personName.setBackgroundColor(0x00000000);
			holder.tv_item_warehorse_conserveTypeName.setBackgroundColor(0x00000000);
			
			holder.tv_item_warehorse_assetNameName.setGravity(Gravity.CENTER_VERTICAL);
			holder.tv_item_warehorse_factoryName.setGravity(Gravity.CENTER_VERTICAL);
			holder.tv_item_warehorse_modelName.setGravity(Gravity.CENTER_VERTICAL);
			holder.tv_item_warehorse_personName.setGravity(Gravity.CENTER_VERTICAL);
			holder.tv_item_warehorse_conserveTypeName.setGravity(Gravity.CENTER_VERTICAL);
		}
		if (position % 2 == 0) {
			convertView.setBackgroundColor(0xFFF2F2F2);
		} else {
			convertView.setBackgroundColor(0x00000000);
			
		}
		preperData(holder, list.get(position));
		convertView.setTag(R.id.tag_search_tagCode, list.get(position).getAssetsCode());
		return convertView;
	}
	
	private void preperData(ViewHolder holder, DevicePagerItemBean dpiBean) {
		holder.tv_item_warehorse_assetsCode.setText(dpiBean.getAssetsCode());
		holder.tv_item_warehorse_assetNameName.setText(dpiBean.getAssetNameName());
		holder.tv_item_warehorse_factoryName.setText(dpiBean.getFactoryName());
		holder.tv_item_warehorse_modelName.setText(dpiBean.getModelName());
		holder.tv_item_warehorse_personName.setText(dpiBean.getPersonName());
		holder.tv_item_warehorse_conserveTypeName.setText(dpiBean.getConserveTypeName());
	}
}
