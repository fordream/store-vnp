package com.cnc.maispreco.adpters;

import java.util.List;

import org.com.cnc.maispreco.R;

import com.cnc.maispreco.views.AddressChoiseitemView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class AddressChoiseApater extends ArrayAdapter<String> {
	List<String> list;
	int resource;


	public AddressChoiseApater(Context context, List<String> objects) {
		super(context, R.layout.item_choise_address, objects);

		resource = R.layout.item_choise_address;
		list = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;

		if (workView == null) {
			workView = new AddressChoiseitemView(getContext());
		}

		final String  content = list.get(position);
		AddressChoiseitemView view = ((AddressChoiseitemView) workView);
		if (content != null) {
			view.gettVContent().setText(content);
		}

		return workView;
	}
}