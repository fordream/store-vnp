package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.common.TrackerGoogle;

public class TrackerAsyn extends AsynTask {
	private String url;

	public TrackerAsyn(MaisprecoScreen maisprecoScreen,String url) {
		super(maisprecoScreen);
		this.url = url;
	}

	protected String doInBackground(String... params) {
		TrackerGoogle.homeTracker(maisprecoScreen, url);
		return null;
	}
}
