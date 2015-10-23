package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestRegister;

public class ResponseRegister extends Response {

	public static ResponseRegister getResponseRegister(
			RequestRegister request) {
		ResponseRegister response = new ResponseRegister();
		JSONObject object = JSonCommon.getJSONfromURLPOSTRegister(request.getUrl(),request);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
		}
		return response;
	}
}
