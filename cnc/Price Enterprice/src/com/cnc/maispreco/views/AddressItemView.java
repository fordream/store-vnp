package com.cnc.maispreco.views;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;

import com.cnc.maispreco.soap.data.Address;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressItemView extends LinearLayout {
	private TextView tVAddress;
	private TextView tVNumberCall;
	private Button button001;
	private String numberPhone;

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
		if (this.numberPhone == null) {
			button001.setVisibility(GONE);
		} else if ("".equals(this.numberPhone.trim())) {
			button001.setVisibility(GONE);
		}
	}

	public AddressItemView(Context context) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_address, this, true);

		tVAddress = (TextView) findViewById(R.id.tVAddress);
		tVNumberCall = (TextView) findViewById(R.id.tVNumberCall);
		button001 = (Button) findViewById(R.id.button001);
		button001.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MaisprecoScreen maisprecoScreen = (MaisprecoScreen)getContext();
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + numberPhone));
				maisprecoScreen.setStop(false);
				maisprecoScreen.startActivityForResult(callIntent,1000);
			}
		});
	}

	public TextView gettVAddress() {
		return tVAddress;
	}

	public void settVAddress(TextView tVAddress) {
		this.tVAddress = tVAddress;
	}

	public TextView gettVNumberCall() {
		return tVNumberCall;
	}

	public void settVNumberCall(TextView tVNumberCall) {
		this.tVNumberCall = tVNumberCall;
	}

	public void setData(Address address) {
		if (address != null) {
			setNumberPhone(address.get(Address.PHONE));
			tVAddress.setText(address.get(Address.INFO));
		}
	}
}