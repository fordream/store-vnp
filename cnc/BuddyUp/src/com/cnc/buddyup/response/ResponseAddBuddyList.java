package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestAddbuddyList;
import com.cnc.buddyup.sign.paracelable.Country;

public class ResponseAddBuddyList extends Response {
	private List<Country> lCountries = new ArrayList<Country>();

	public List<Country> getlCountries() {
		return lCountries;
	}

	public void setlCountries(List<Country> lCountries) {
		this.lCountries = lCountries;
	}

	public static ResponseAddBuddyList getResponseAddBuddyList(
			RequestAddbuddyList request) {
		ResponseAddBuddyList response = new ResponseAddBuddyList();
		JSONObject object = JSonCommon.getJSONfromURLPOSTResponseAddBuddyList(request);
		
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
		}
		
		return response;
	}
}