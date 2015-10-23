package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;

import com.cnc.maispreco.views.WebViewController;

public class WebAsyn extends AsynTask {
	private String url ;
	public WebAsyn(MaisprecoScreen maisprecoScreen,String url) {
		super(maisprecoScreen);
		this.url = url;
	}

	protected void onPostExecute(String result) {

		WebViewController viewWeb = new WebViewController(maisprecoScreen);
		viewWeb.loadUrl(url);
		maisprecoScreen.addlView(viewWeb);
	}

	protected String doInBackground(String... arg0) {
		return null;
	}
}
