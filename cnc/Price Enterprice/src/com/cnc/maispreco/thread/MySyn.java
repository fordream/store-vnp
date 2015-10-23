package com.cnc.maispreco.thread;

import java.util.List;

import org.com.cnc.common.database.DBAdapterPage;
import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.LoginResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cnc.maispreco.soap.data.Locality;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.ConfigurationView;

public class MySyn extends AsyncTask<String, String, String> {
	private Handler handler;
	Context context;

	public MySyn(Handler handler, Context context) {
		super();
		this.handler = handler;
		this.context = context;
	}

	protected String doInBackground(String... params) {
		int count = 0;
		LoginResponse loginResponse = new LoginResponse();
		while (count < 3) {
			LoginRequest loginRequest = new LoginRequest("" + Common.latitude,
					"" + Common.longitude);
			loginResponse = LoginResponse.getLoginResponse(loginRequest);

			if (loginResponse != null) {
				Common.tooken = loginResponse.getToken();
			}

			count++;
			if ("0".equals(loginResponse.getStatusCode())) {
				break;
			}
		}

		Message message = new Message();
		message.what = Common.MESSAGE_WHAT_1;
		Bundle data = new Bundle();
		data.putString("arg0", loginResponse.getMessageBoxTitle());
		data.putString("arg1", loginResponse.getMessageBoxContent());
		message.setData(data);
		handler.sendMessage(message);
		ConfigurationView.page = new DBAdapterPage(context).getPage();

		Search search = new Search();
		search.setToken(Common.tooken);
		search.setSearchType("1");
		List<Locality> alLocality = Locality.getLLocality(search);
		Common.LOCALID = new DBAdapterPage(context).getLocationId();
		
		if (Common.LOCALID == null || "".equals(Common.LOCALID)){
			for (int i = 0; i < alLocality.size(); i++) {
				Locality locality = alLocality.get(i);
				if ("true".equals(locality.get(Locality.DEFAULTLOCALE))) {
					Common.LOCALID = locality.get(Locality.ID);
					new DBAdapterPage(context).updateLocation(Common.LOCALID);
					break;
				}
			}
		}
		MaisprecoScreen.addLocality(alLocality);
		return null;
	}

	protected void onPostExecute(String result) {

	}

}