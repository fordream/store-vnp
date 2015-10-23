package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;

import android.view.View;

import com.cnc.maispreco.views.HelpViewControl;

public class HelpAsyn extends AsynTask {

	public HelpAsyn(MaisprecoScreen maisprecoScreen) {
		super(maisprecoScreen);
	}

	protected void onPostExecute(String result) {
		View _view = new HelpViewControl(maisprecoScreen);
		maisprecoScreen.addlView(_view);
	}

	protected String doInBackground(String... params) {
		return null;
	}
}
