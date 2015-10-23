package com.cnc.buddyup.asyn;

import com.cnc.buddyup.request.RequestProfileDelete;
import com.cnc.buddyup.response.ResponseProfileDelete;
import com.cnc.buddyup.views.profile.ProfileView;

public class AsynProfileDelete extends AsyncTask {
	private RequestProfileDelete request;
	private ProfileView profileView;
	private ResponseProfileDelete response ;

	public AsynProfileDelete(RequestProfileDelete request, ProfileView profileView) {
		this.request = request;
		this.profileView = profileView;
	}

	protected String doInBackground(String... params) {
		profileView.showLoading(true);
		response = ResponseProfileDelete.getResponseProfileGet(request);
		profileView.showLoading(false);
		return super.doInBackground(params);
	}
	protected void onPostExecute(String result) {
		profileView.update(response);
		super.onPostExecute(result);
	}
}
