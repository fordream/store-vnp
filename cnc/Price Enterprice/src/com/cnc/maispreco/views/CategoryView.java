package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryView extends LinearLayout {
	private TextView tVCatagoryName;

	public CategoryView(Context context) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_category, this, true);
		tVCatagoryName = (TextView) findViewById(R.id.tVCatagoryName);
	}

	public void setData(String text) {
		tVCatagoryName.setText(text);
	}

	// public TextView gettVCatagoryName() {
	// return tVCatagoryName;
	// }
	//
	// public void settVCatagoryName(TextView tVCatagoryName) {
	// this.tVCatagoryName = tVCatagoryName;
	// }
}
