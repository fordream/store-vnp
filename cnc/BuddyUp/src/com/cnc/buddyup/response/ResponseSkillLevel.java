package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestSkillLevel;
import com.cnc.buddyup.response.item.SkillLevel;

public class ResponseSkillLevel extends Response {
	private List<SkillLevel> lSkillLevels = new ArrayList<SkillLevel>();

	public List<SkillLevel> getlSkillLevels() {
		return lSkillLevels;
	}

	public void setlSkillLevels(List<SkillLevel> lSkillLevels) {
		this.lSkillLevels = lSkillLevels;
	}

	public static ResponseSkillLevel getData(RequestSkillLevel request) {
		ResponseSkillLevel response = new ResponseSkillLevel();
		String url = request.getUrl().replace("{0}", Common.token);

		JSONObject object = JSonCommon.getJSONfromURLGET(url);

		if (object != null) {
			response.setError(JSonCommon.getString(object, "Error"));
			response.setStatus(JSonCommon.getString(object, "Status"));
			response.setMessage(JSonCommon.getString(object, "Message"));
			try {
				JSONObject object2 = object.getJSONObject("items");
				if (object2 != null) {
					int index = 1;
					boolean check = true;
					while (check) {
						String key = "item" + index;
						JSONObject object3 = object2.getJSONObject(key);
						if (object3 == null) {
							check = false;
						} else {
							String id = JSonCommon.getString(object3, "id");
							String name = JSonCommon.getString(object3, "name");
							SkillLevel skillLevel = new SkillLevel();
							skillLevel.setId(id);
							skillLevel.setName(name);
							response.getlSkillLevels().add(skillLevel);
						}
						index++;
					}
				}
			} catch (JSONException e) {
			}
		}

		return response;
	}
}