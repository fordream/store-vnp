package com.cnc.maispreco.adpters;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Address;
import com.cnc.maispreco.views.AddressItemView;

public class AddressApater extends ArrayAdapter<Address> {
	List<Address> lAddress;
	int resource;
	
	
	
	Context context;
	public static final int ESTADO = 1;
	public static final int CIDADE = 2;
	public static final int BAIRRO = 31;
	int index = ESTADO;

	public void setIndex(int index) {
		this.index = index;
	}

	public AddressApater(Context context, ArrayList<Address> objects) {
		super(context, R.layout.item_address, objects);
		this.context = context;
		resource = R.layout.item_address;
		lAddress = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new AddressItemView(getContext());
		}

		final Address address = lAddress.get(position);
		AddressItemView view = ((AddressItemView) workView);
		if (address != null) {
			TextView tVAddress = ((AddressItemView) workView).gettVAddress();
			TextView imgBtnCall = ((AddressItemView) workView)
					.gettVNumberCall();
			tVAddress.setText(address.get(Address.INFO));
			view.setNumberPhone(address.get(Address.PHONE));
			imgBtnCall.setText(address.get(Address.PHONE));

		}

		return workView;
	}
}