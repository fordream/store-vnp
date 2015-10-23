package com.cnc.maispreco.views;

import com.cnc.maispreco.soap.data.Product;

import android.content.Context;

public class HomeViewControl extends CommonHomeViewControl {
	private String id1 = null;

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public HomeViewControl(Context context, Product product,String id1) {
		super(context, product);
		this.id1  = id1;
	}
}