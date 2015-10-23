package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONObject;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestAge;
import com.cnc.buddyup.response.item.Age;

public class ResponseAge extends Response {
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static ResponseAge getData(RequestAge request) {
		ResponseAge response = new ResponseAge();
		String url = request.getUrl().replace("{0}", Common.token);

		JSONObject object = JSonCommon.getJSONfromURLGET(url);

		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			response.setData(JSonCommon.getString(object, "Items"));
		}

		return response;
	}

	public List<Age> getListAge() {
		List<Age> lAges = new ArrayList<Age>();
		if(data!= null){
			StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
			while(stringTokenizer.hasMoreTokens()){
				String da = stringTokenizer.nextToken();
				Age age = new Age();
				age.setId(da);
				age.setValue(da);
				lAges.add(age);
			}
		}
		return lAges;
	}
}