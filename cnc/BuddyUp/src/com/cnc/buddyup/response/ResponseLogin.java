package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestLogin;

public class ResponseLogin extends Response {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static ResponseLogin getResponseRegister(RequestLogin request) {
		ResponseLogin response = new ResponseLogin();
		String url = request.getUrl();
		url = url.replace("{0}", request.getUsername());
		url = url.replace("{1}", request.getPassword());
		
		JSONObject object = JSonCommon.getJSONfromURLGET(url);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			response.setToken(JSonCommon.getString(object, "Token"));
			response.setId(JSonCommon.getString(object, "UserID"));
		}else{
		}
		
		return response;
	}
}
