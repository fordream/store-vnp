package com.cnc.buddyup.asyn;

import com.cnc.buddyup.request.RequestUpdateOptionInformation;
import com.cnc.buddyup.response.ResponseUpdateOptionInformation;
import com.cnc.buddyup.views.profile.ProfileOptionInformationView;

public class AsynUpdateProfileOptionInformation extends AsyncTask {
	private ProfileOptionInformationView profileView;
	private ResponseUpdateOptionInformation response;
	private RequestUpdateOptionInformation request;
	public AsynUpdateProfileOptionInformation(ProfileOptionInformationView profileView,RequestUpdateOptionInformation request) {
		this.profileView = profileView;
		this.request = request;
	}

	protected String doInBackground(String... params) {
		profileView.showLoading(true);
		response = ResponseUpdateOptionInformation.getData(request);
		profileView.showLoading(false);
		return super.doInBackground(params);
	}

	protected void onPostExecute(String result) {
		profileView.update(response);
		super.onPostExecute(result);
	}
}
