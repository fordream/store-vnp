package org.com.cnc.rosemont.response;

import org.com.cnc.common.android.response.CommonResponse;
import org.com.cnc.common.android.service.CommonService;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.request.RequestUpdateData;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseUpdateData extends CommonResponse {
	private String latUpdate;

	public String getLatUpdate() {
		return latUpdate;
	}

	public void setLatUpdate(String latUpdate) {
		this.latUpdate = latUpdate;
	}

	public void getData(RequestUpdateData data, RosemontTable rosemontDelete,
			RosemontTable rosemontUpdate) {
		JSONObject json = CommonService.getJSON(data);
		if (json != null) {
			try {
				JSONArray arrayDelete = json.getJSONArray("modifileParent");
				JSONArray arrayUpdate = json.getJSONArray("all");

				ResponseData responseData = new ResponseData();
				responseData.getData(arrayDelete + "");
				rosemontDelete.addAll(responseData.getTable());
				
				ResponseData responseData1 = new ResponseData();
				responseData1.getData(arrayUpdate + "");
				rosemontUpdate.addAll(responseData1.getTable());
				

			} catch (Exception e) {
			}

		}
	}
}
