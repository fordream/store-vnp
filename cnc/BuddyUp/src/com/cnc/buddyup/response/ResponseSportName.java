package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestSportName;
import com.cnc.buddyup.response.item.SportName;

public class ResponseSportName extends Response {
	private List<SportName> lSkillLevels = new ArrayList<SportName>();

	public List<SportName> getlSkillLevels() {
		return lSkillLevels;
	}

	public void setlSkillLevels(List<SportName> lSkillLevels) {
		this.lSkillLevels = lSkillLevels;
	}

	public static ResponseSportName getData(RequestSportName request) {
		ResponseSportName response = new ResponseSportName();
		String url = request.getUrl().replace("{0}", Common.token);

		JSONObject object = JSonCommon.getJSONfromURLGET(url);

		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			try {
				JSONArray object2 = object.getJSONArray("items");
				if (object2 != null) {
					for(int i = 0; i < object2.length(); i ++){
						JSONObject object3 = object2.getJSONObject(i);
						if (object3 == null) {
						} else {
							String id = JSonCommon.getString(object3, "id");
							String name = JSonCommon.getString(object3, "name");
							SportName skillLevel = new SportName();
							skillLevel.setId(id);
							skillLevel.setName(name);
							skillLevel.setIcon(JSonCommon.getString(object3,
									"icon"));
							response.getlSkillLevels().add(skillLevel);
						}
					}
				}
			} catch (JSONException e) {
			}
		}

		return response;
	}
}