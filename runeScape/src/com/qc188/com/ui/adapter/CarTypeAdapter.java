package com.qc188.com.ui.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qc188.com.R;
import com.qc188.com.bean.CarTypeItemBean;
import com.qc188.com.framwork.FrameAdapter;
import com.qc188.com.ui.CarTypeActivity;

public class CarTypeAdapter extends FrameAdapter implements OnClickListener {

	private CarTypeActivity ctActivity;

	public CarTypeAdapter(Context context, CarTypeActivity ctActivity) {
		super(context);
		this.ctActivity = ctActivity;
	}

	private List<CarTypeItemBean> list;

	public void setItemList(List<CarTypeItemBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = setConvertView_Holder(convertView, R.layout.item_car_type_main, false);

		CarTypeItemBean carItem = list.get(position);

		TextView tv_item_carType_TitleName = (TextView) convertView.findViewById(R.id.tv_item_carType_TitleName);
		TextView tv_item_carType_saleNumber = (TextView) convertView.findViewById(R.id.tv_item_carType_saleNumber);
		TextView tv_item_carType_index = (TextView) convertView.findViewById(R.id.tv_item_carType_index);
		TextView tv_item_carType_phone = (TextView) convertView.findViewById(R.id.tv_item_carType_phone);
		TextView tv_item_carType_saleTime = (TextView) convertView.findViewById(R.id.tv_item_carType_saleTime);
		TextView tv_item_carType_saleArea = (TextView) convertView.findViewById(R.id.tv_item_carType_saleArea);

		ImageView tv_item_carType_sales = (ImageView) convertView.findViewById(R.id.tv_item_carType_sales);

		tv_item_carType_TitleName.setText(carItem.getSale());
		tv_item_carType_saleNumber.setText(carItem.getPrice() + "万");
		tv_item_carType_index.setText(carItem.getAddressName());
		String saleArea = carItem.getSale_area();
		tv_item_carType_phone.setTag((carItem.getPhone()));
		tv_item_carType_phone.setOnClickListener(this);
		if (TextUtils.isEmpty(saleArea)) {
			tv_item_carType_saleArea.setVisibility(View.INVISIBLE);

		} else {
			tv_item_carType_saleArea.setText(saleArea);

			tv_item_carType_saleArea.setVisibility(View.VISIBLE);
		}

		String serviceTime = carItem.getService_time();
		if (TextUtils.isEmpty(serviceTime)) {
			tv_item_carType_saleArea.setVisibility(View.INVISIBLE);

		} else {
			tv_item_carType_saleArea.setVisibility(View.VISIBLE);
			tv_item_carType_saleTime.setText(serviceTime);

		}
		tv_item_carType_phone.setTag(carItem.getPhone());

		if (carItem.getPromotion() != null && carItem.getPromotion().equals("True")) {
			tv_item_carType_sales.setVisibility(View.VISIBLE);
		} else {// 01-14 21:06:41.053: D/UniversalEngineImpl(29723):

			tv_item_carType_sales.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	// 01-14 20:52:16.250: D/CarTypeActivity(28855): result:CarTypeTitleBean
	// [structure=SUV, drive=前置后驱, drive_gearbox=5挡手动, guild_sale=8.98万,
	// the_lowest=6.98万] second:[CarTypeItemBean [addressName=北京市北五环立汤路165号,
	// sale=北京京通硕达汽车销售有限公司,
	// phone=8.98, sale_area=全国, service_time=24小时, promotion=True],
	// CarTypeItemBean [addressName=, sale=北京通立来汽车销售有限公司, phone=8.98,
	// sale_area=全国, service_time=24小时, promotion=True], CarTypeItemBean
	// [addressName=北京市朝阳区东五环五方桥东南侧,
	// sale=北京旺立达商贸有限公司, phone=8.98, sale_area=全国, service_time=24小时,
	// promotion=True]]

	@Override
	public void onClick(View v) {
		Object obj = v.getTag();
		String[] phones = null;
		String phone = null;
		if (obj instanceof String) {
			phone = ((String) obj).trim();

			if (phone.contains("/")) {
				phones = phone.split("/");
			}
		}

		if (phone == null && (phones == null || phones.length < 0)) {
			return;
		}
		if (phones != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ctActivity);
			ListView lv = new ListView(ctActivity);
			lv.setAdapter(new ArrayAdapter<String>(ctActivity, android.R.layout.simple_list_item_1, phones));
			builder.setView(lv);
			builder.create();
			lv.setTag(builder.show());
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					AlertDialog adl = (AlertDialog) parent.getTag();
					if (adl != null && adl.isShowing()) {
						adl.cancel();
					}
					TextView tv = (TextView) view;
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv.getText().toString()));
					ctActivity.startActivity(intent);
				}
			});
		} else {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
			ctActivity.startActivity(intent);
		}
	}
}
