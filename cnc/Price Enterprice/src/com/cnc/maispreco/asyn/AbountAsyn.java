package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;

import android.view.View;

import com.cnc.maispreco.views.AboutViewControl;

public class AbountAsyn extends AsynTask {

	public AbountAsyn(MaisprecoScreen maisprecoScreen) {
		super(maisprecoScreen);
	}

	protected void onPostExecute(String result) {
		View _view = new AboutViewControl(maisprecoScreen);
		maisprecoScreen.addlView(_view);
	}

	protected String doInBackground(String... params) {
		return null;
	}
}
