package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestCountryList;
import com.cnc.buddyup.sign.paracelable.Country;

public class ResponseCountryList extends Response {
	private List<Country> lCountries = new ArrayList<Country>();

	public List<Country> getlCountries() {
		return lCountries;
	}

	public void setlCountries(List<Country> lCountries) {
		this.lCountries = lCountries;
	}

	public static ResponseCountryList getResponseCountryList(
			RequestCountryList request) {
		ResponseCountryList response = new ResponseCountryList();
		JSONObject object = JSonCommon.getJSONfromURLGET(request.getUrl());
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			JSONArray array;
			try {
				array = object.getJSONArray("items");
				for (int i = 0; i < array.length(); i++) {
					JSONObject object2 = array.getJSONObject(i);
					String id = JSonCommon.getString(object2, "id");
					String name = JSonCommon.getString(object2, "name");
					Country country = new Country(id, name);
					response.getlCountries().add(country);
				}
			} catch (JSONException e) {
			}

		}
		return response;
	}
}