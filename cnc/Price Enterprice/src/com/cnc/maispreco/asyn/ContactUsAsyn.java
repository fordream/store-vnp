package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;

import android.view.View;

import com.cnc.maispreco.views.ContactUsViewControl;

public class ContactUsAsyn extends AsynTask {

	public ContactUsAsyn(MaisprecoScreen maisprecoScreen) {
		super(maisprecoScreen);
	}

	protected String doInBackground(String... params) {
		return null;
	}

	protected void onPostExecute(String result) {
		View _view = new ContactUsViewControl(maisprecoScreen);
		maisprecoScreen.addlView(_view);
	}
}
