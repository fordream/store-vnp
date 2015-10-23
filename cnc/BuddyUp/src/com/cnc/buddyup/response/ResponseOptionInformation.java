package com.cnc.buddyup.response;

import org.json.JSONObject;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestOptionInformation;

public class ResponseOptionInformation extends Response {
	private String sex;
	private String description;
	private String comments;
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public static ResponseOptionInformation getResponseAddBuddyList(
			RequestOptionInformation request) {
		ResponseOptionInformation response = new ResponseOptionInformation();
		String url = request.getUrl().replace("{0}", Common.token);
		JSONObject object = JSonCommon.getJSONfromURLGET(url);
		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			response.setSex(JSonCommon.getString(object, "Sex"));
			response.setComments(JSonCommon.getString(object, "Comments"));
			response.setDescription(JSonCommon.getString(object, "Description"));
		}
		return response;
	}
}