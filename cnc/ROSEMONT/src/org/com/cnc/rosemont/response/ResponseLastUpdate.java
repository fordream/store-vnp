package org.com.cnc.rosemont.response;

import org.com.cnc.common.android.response.CommonResponse;
import org.com.cnc.common.android.service.CommonService;
import org.com.cnc.rosemont.request.RequestLastUpdate;
import org.json.JSONObject;

import android.util.Log;

public class ResponseLastUpdate extends CommonResponse {
	private String latUpdate;

	public String getLatUpdate() {
		return latUpdate;
	}

	public void setLatUpdate(String latUpdate) {
		this.latUpdate = latUpdate;
	}

	public void getData() {
		JSONObject jsonArray = CommonService.getJSON(new RequestLastUpdate());
		if (jsonArray != null) {
			String date = CommonService.getString(jsonArray, "date_modified");
			setLatUpdate(date);
		}
	}
}
