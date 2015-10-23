package com.cnc.maispreco.listenner;

import org.com.cnc.maispreco.MaisprecoScreen;

import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.maispreco.soap.data.Store;
import com.cnc.maispreco.views.StoreViewControl;

public class OnCLickLinerStore implements OnClickListener {
	private StoreViewControl storeViewControl;

	public OnCLickLinerStore(StoreViewControl storeViewControl) {
		this.storeViewControl = storeViewControl;

	}

	public void onClick(View v) {
		Store store = storeViewControl.getStore();
		MaisprecoScreen mais = (MaisprecoScreen) storeViewControl.getContext();
		mais.addAddressViewControl(store);

	}

}