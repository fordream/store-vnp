package com.cnc.buddyup.asyn;

import com.cnc.buddyup.request.RequestProfileUpdate;
import com.cnc.buddyup.response.ResponseProfileUpdate;
import com.cnc.buddyup.views.profile.ProfileView;

public class AsynProfileUpdate extends AsyncTask {
	private RequestProfileUpdate request;
	private ProfileView profileView;
	private ResponseProfileUpdate response;

	public AsynProfileUpdate(RequestProfileUpdate request,
			ProfileView profileView) {
		this.request = request;
		this.profileView = profileView;
	}

	protected String doInBackground(String... params) {
		profileView.showLoading(true);
		response = ResponseProfileUpdate.getResponseProfileGet(request);
		profileView.showLoading(false);
		return super.doInBackground(params);
	}

	protected void onPostExecute(String result) {
		profileView.update(response);
		super.onPostExecute(result);
	}
}
