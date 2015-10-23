package com.cnc.buddyup.response;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.request.JSonCommon;
import com.cnc.buddyup.request.RequestPhiloshophy;
import com.cnc.buddyup.response.item.SkillLevel;

public class ResponsePhiloshophy extends Response {
	private List<SkillLevel> lSkillLevels = new ArrayList<SkillLevel>();

	public List<SkillLevel> getlSkillLevels() {
		return lSkillLevels;
	}

	public void setlSkillLevels(List<SkillLevel> lSkillLevels) {
		this.lSkillLevels = lSkillLevels;
	}

	public static ResponsePhiloshophy getData(RequestPhiloshophy request) {
		ResponsePhiloshophy response = new ResponsePhiloshophy();
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

//	public List<String> getListPhiloshophy() {
//		List<String> lAges = new ArrayList<String>();
//		if (data != null) {
//			StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
//			while (stringTokenizer.hasMoreTokens()) {
//				String da = stringTokenizer.nextToken();
//				lAges.add(da);
//			}
//		}
//		return lAges;
//	}
}