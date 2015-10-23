package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestProfileDelete;

public class ResponseProfileDelete extends Response {
	
	public static ResponseProfileDelete getResponseProfileGet(
			RequestProfileDelete request) {
		ResponseProfileDelete response = new ResponseProfileDelete();
		String url = request.getUrl();
		url = url.replace("{0}", request.getToken());
	
		JSONObject object = JSonCommon.getJSONfromURLGET(url);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
		}
		return response;
	}
}
