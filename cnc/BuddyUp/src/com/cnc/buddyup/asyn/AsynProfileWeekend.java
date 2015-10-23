package com.cnc.buddyup.asyn;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.RequestProfileWeekend;
import com.cnc.buddyup.request.RequestSportName;
import com.cnc.buddyup.response.ResponseProfileWeekend;
import com.cnc.buddyup.response.ResponseSportName;
import com.cnc.buddyup.response.item.SportName;
import com.cnc.buddyup.views.ProfileSportView;

public class AsynProfileWeekend extends AsyncTask {
	private ProfileSportView profileView;
	private ResponseProfileWeekend response;

	public AsynProfileWeekend(ProfileSportView profileView) {
		this.profileView = profileView;
	}

	protected String doInBackground(String... params) {
		profileView.showLoadingView(true);
		RequestProfileWeekend request = new RequestProfileWeekend(Common.id,
				Common.token);
		response = ResponseProfileWeekend.getData(request);
		if ("0".equals(response.getStatus())) {
			RequestSportName requestSportName = new RequestSportName();
			ResponseSportName sportName = ResponseSportName
					.getData(requestSportName);
			if ("0".equals(sportName.getStatus())) {
				for (int i = 0; i < sportName.getlSkillLevels().size(); i++) {
					SportName sportName2 = sportName.getlSkillLevels().get(i);
					if (Common.compare(response.getSport1().getName(),
							sportName2.getName())) {
						response.getSport1().setIdSport(sportName2.getId());
					}

					if (Common.compare(response.getSport2().getName(),
							sportName2.getName())) {
						response.getSport2().setIdSport(sportName2.getId());
					}

					if (Common.compare(response.getSport3().getName(),
							sportName2.getName())) {
						response.getSport3().setIdSport(sportName2.getId());
					}

				}
			}
		}
		profileView.showLoadingView(false);

		return null;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		profileView.update(response);
	}
}
