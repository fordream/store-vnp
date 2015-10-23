package com.cnc.buddyup.asyn;

import com.cnc.buddyup.request.RequestCountryList;
import com.cnc.buddyup.request.RequestProfileGet;
import com.cnc.buddyup.response.ResponseCountryList;
import com.cnc.buddyup.response.ResponseProfileGet;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.views.profile.ProfileView;

public class AsynProfileGet extends AsyncTask {
	private RequestProfileGet request;
	private ProfileView profileView;
	private ResponseProfileGet response;
	private String nameCountry = "";
	public AsynProfileGet(RequestProfileGet request, ProfileView profileView) {
		this.request = request;
		this.profileView = profileView;
	}

	protected String doInBackground(String... params) {
		profileView.showLoading(true);
		response = ResponseProfileGet.getResponseProfileGet(request);
		if(response.getIdCountry() != null){
			RequestCountryList countryList = new RequestCountryList();
			ResponseCountryList countryList2 = ResponseCountryList.getResponseCountryList(countryList);
			for(int i = 0; i < countryList2.getlCountries().size() ; i ++){
				Country country = countryList2.getlCountries().get(i);
				if(response.getIdCountry().equals(country.getId())){
					nameCountry = country.getName();
				}
			}
		}
		profileView.showLoading(false);
		
		return null;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		profileView.update(response,nameCountry);
	}
}
