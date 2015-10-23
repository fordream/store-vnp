package com.cnc.buddyup.asyn;

import com.cnc.buddyup.request.RequestOptionInformation;
import com.cnc.buddyup.response.ResponseOptionInformation;
import com.cnc.buddyup.views.profile.ProfileOptionInformationView;

public class AsynProfileOptionInformation extends AsyncTask {
	private ProfileOptionInformationView profileView;
	private ResponseOptionInformation response;

	public AsynProfileOptionInformation(ProfileOptionInformationView profileView) {
		this.profileView = profileView;
	}

	protected String doInBackground(String... params) {
		profileView.showLoading(true);
		RequestOptionInformation request = new RequestOptionInformation();
		response = ResponseOptionInformation.getResponseAddBuddyList(request);
		profileView.showLoading(false);
		return super.doInBackground(params);
	}

	protected void onPostExecute(String result) {
		profileView.update(response);
		super.onPostExecute(result);
	}
}
