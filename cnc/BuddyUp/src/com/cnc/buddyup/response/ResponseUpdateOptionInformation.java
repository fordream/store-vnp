package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestUpdateOptionInformation;

public class ResponseUpdateOptionInformation extends Response {
	public static ResponseUpdateOptionInformation getData(
			RequestUpdateOptionInformation request) {
		ResponseUpdateOptionInformation response = new ResponseUpdateOptionInformation();
		// String url = request.getUrl().replace("{0}", Common.token);
		JSONObject object = JSonCommon.UpdateOptionProfile(request);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
		}
		return response;
		
	}
}