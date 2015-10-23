package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestProfileUpdate;

public class ResponseProfileUpdate extends Response {
	
	public static ResponseProfileUpdate getResponseProfileGet(
			RequestProfileUpdate request) {
		ResponseProfileUpdate response = new ResponseProfileUpdate();
	
		JSONObject object = JSonCommon.updateProfile(request);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
		}
		return response;
	}
}
