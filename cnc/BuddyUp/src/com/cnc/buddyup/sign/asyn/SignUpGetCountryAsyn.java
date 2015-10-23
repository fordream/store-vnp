package com.cnc.buddyup.sign.asyn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cnc.buddyup.SignUpScreen;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.RequestCountryList;
import com.cnc.buddyup.response.ResponseCountryList;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.sign.paracelable.CountryParcacelable;

public class SignUpGetCountryAsyn extends AsyncTask<String, String, String> {
	protected static final CharSequence TITLE = "Loadding";
	protected static final CharSequence MESSAGE = "Load Countries ...";
	private Handler handler;
	private SignUpScreen context;
	private boolean isClose = false;

	public void isClose() {
		isClose = false;
	}

	public SignUpGetCountryAsyn(SignUpScreen context, Handler handler) {
		this.handler = handler;
		this.context = context;
	}


	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				if (!isClose) {
					context.showDialog(true);
				}
			}
		});

		RequestCountryList request = new RequestCountryList();
		ResponseCountryList response = ResponseCountryList
				.getResponseCountryList(request);
		handler.post(new Runnable() {
			public void run() {
				if (!isClose) {
					context.showDialog(false);
				}
			}
		});
		if (!isClose) {
			handler.sendMessage(createCountryList(response));
		}
		return null;
	}

	private Message createCountryList(ResponseCountryList response) {
		Message message = new Message();
		message.what = Common.MESSAGE_WHAT_1;
		CountryParcacelable parcacelable = new CountryParcacelable();
		for (int i = 0; i < response.getlCountries().size(); i++) {
			Country country = response.getlCountries().get(i);
			parcacelable.add(country.getId(), country.getName());
		}
		Bundle data = new Bundle();
		data.putParcelable(Common.ARG0, parcacelable);
		message.setData(data);
		return message;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
