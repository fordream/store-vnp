package com.cnc.buddyup.response;

import org.com.cnc.common.android.response.CommonResponse;
import org.com.cnc.common.android.service.CommonService;
import org.json.JSONObject;

import com.cnc.buddyup.request.RequestProfileWeekend;
import com.cnc.buddyup.response.item.ItemWeekend;

public class ResponseProfileWeekend extends CommonResponse {
	private ItemWeekend sport1;
	private ItemWeekend sport2;
	private ItemWeekend sport3;

	public ItemWeekend getSport1() {
		return sport1;
	}

	public void setSport1(ItemWeekend sport1) {
		this.sport1 = sport1;
	}

	public ItemWeekend getSport2() {
		return sport2;
	}

	public void setSport2(ItemWeekend sport2) {
		this.sport2 = sport2;
	}

	public ItemWeekend getSport3() {
		return sport3;
	}

	public void setSport3(ItemWeekend sport3) {
		this.sport3 = sport3;
	}

	public static ResponseProfileWeekend getData(RequestProfileWeekend request) {
		ResponseProfileWeekend response = new ResponseProfileWeekend();
		// TODO
		// JSONObject json = CommonService.getJSON(request);
		try {
			JSONObject json = new JSONObject("{}");
			if (json != null) {
				response.setError(CommonService.getString(json, "Error"));
				response.setStatus(CommonService.getString(json, "Status"));
				response.setMessage(CommonService.getString(json, "Message"));
				response.setSport1(ItemWeekend.getData(getJson(json, "item1")));
				response.setSport2(ItemWeekend.getData(getJson(json, "item2")));
				response.setSport3(ItemWeekend.getData(getJson(json, "item3")));
			}
		} catch (Exception exception) {
		}

		return response;
	}

	private static JSONObject getJson(JSONObject jsonObject, String key) {
		return CommonService.getJSONObjectByKey(jsonObject, key);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getStatus()).append("\n");
		builder.append(getMessage()).append("\n");
		builder.append(getSport1().toString()).append("\n");
		builder.append(getSport2().toString()).append("\n");
		builder.append(getSport3().toString()).append("\n");
		return builder.toString();
	}

}
