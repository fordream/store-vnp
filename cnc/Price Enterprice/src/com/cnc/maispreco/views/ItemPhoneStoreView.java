package com.cnc.maispreco.views;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemPhoneStoreView extends LinearLayout implements OnClickListener {
	private TextView tvPhone;
	private Button btnEdit;
	private Button btnCall;

	public ItemPhoneStoreView(Context context) {
		super(context);
		config();
	}

	public ItemPhoneStoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		LayoutInflater li;
		String serviceStr = Context.LAYOUT_INFLATER_SERVICE;
		li = (LayoutInflater) getContext().getSystemService(serviceStr);
		li.inflate(R.layout.item_store_phone, this);
		tvPhone = (TextView) findViewById(R.id.textView1);
		btnCall = (Button) findViewById(R.id.button1);
		btnEdit = (Button) findViewById(R.id.button2);

		btnCall.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
	}

	public void onClick(View v) {
		if (v == btnCall) {
			MaisprecoScreen maisprecoScreen = (MaisprecoScreen)getContext();
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			String url = "tel:" + tvPhone.getText().toString();
			callIntent.setData(Uri.parse(url));
			maisprecoScreen.setStop(false);
			maisprecoScreen.startActivityForResult(callIntent,1000);
		} else if (v == btnEdit) {

		}
	}

	public void addData(String string) {
		tvPhone.setText(string);
	}
}
