package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressChoiseitemView extends LinearLayout {

	private TextView tVContent;

	public AddressChoiseitemView(Context context) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_choise_address, this, true);
		configure();
	}

	private void configure() {
		tVContent = (TextView) findViewById(R.id.textView1);
	}

	public TextView gettVContent() {
		return tVContent;
	}

	public void settVContent(TextView tVContent) {
		this.tVContent = tVContent;
	}

}