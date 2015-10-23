package org.com.cnc.rosemont.response;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.response.CommonResponse;
import org.com.cnc.common.android.service.CommonService;
import org.com.cnc.rosemont.database.table.RosemontTable;
import org.com.cnc.rosemont.request.RequestData;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseData extends CommonResponse {
	private RosemontTable table = new RosemontTable();

	public void getData() {
		JSONArray json = CommonService.getJSONArray(new RequestData());
		setStatus(json == null ? "false" : "true");
		setMessage("Connect time out!");

		if (json != null) {
			JSONObject jsonObject = null;
			int index = 0;
			while ((jsonObject = getJsonObject(json, index)) != null) {
				List<String> lRows = new ArrayList<String>();
				for (int i = 0; i < table.sizeOfColumn(); i++) {
					String nameColumn = table.getColumnName(i);
					String txtData = CommonService.getString(jsonObject,
							nameColumn);
					lRows.add(txtData);
				}

				table.addRow(lRows);
				index++;
			}
		}
	}

	public void getData(String data) {
		try {
			JSONArray json = new JSONArray(data);
			setStatus(json == null ? "false" : "true");
			setMessage("Connect time out!");

			if (json != null) {
				JSONObject jsonObject = null;
				int index = 0;
				while ((jsonObject = getJsonObject(json, index)) != null) {
					List<String> lRows = new ArrayList<String>();
					for (int i = 0; i < table.sizeOfColumn(); i++) {
						String nameColumn = table.getColumnName(i);
						String txtData = CommonService.getString(jsonObject,
								nameColumn);
						lRows.add(txtData);
					}

					table.addRow(lRows);
					index++;
				}
			}
		} catch (Exception e) {
		}
	}

	public RosemontTable getTable() {
		return table;
	}

	public void setTable(RosemontTable table) {
		this.table = table;
	}

	private JSONObject getJsonObject(JSONArray jsonArray, int index) {
		try {
			return jsonArray.getJSONObject(index);
		} catch (Exception e) {
			return null;
		}
	}
}