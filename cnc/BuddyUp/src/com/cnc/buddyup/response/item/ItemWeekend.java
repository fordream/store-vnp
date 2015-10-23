package com.cnc.buddyup.response.item;

import org.com.cnc.common.android.service.CommonService;
import org.json.JSONObject;

public class ItemWeekend {
	public static final int SIZE_X = 7;
	public static final int SIZE_Y = 5;
	private String name;
	private String idSport;
	private String skilllevel;
	private String philosophy;
	private String schedule[][] = new String[SIZE_X][SIZE_Y];

	public String getSchedule(int x, int y) {
		return schedule[x][y];
	}

	public void addSchedule(int x, int y, String value) {
		schedule[x][y] = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdSport() {
		return idSport;
	}

	public void setIdSport(String idSport) {
		this.idSport = idSport;
	}

	public String getSkilllevel() {
		return skilllevel;
	}

	public void setSkilllevel(String skilllevel) {
		this.skilllevel = skilllevel;
	}

	public String getPhilosophy() {
		return philosophy;
	}

	public void setPhilosophy(String philosophy) {
		this.philosophy = philosophy;
	}

	public static ItemWeekend getData(JSONObject json) {
		ItemWeekend weekend = new ItemWeekend();
		if (json != null) {
			weekend.setName(CommonService.getString(json, "name"));
			weekend.setSkilllevel(CommonService.getString(json, "skilllevel"));
			weekend.setPhilosophy(CommonService.getString(json, "philosophy"));
			JSONObject shcedule = getJson(json, "schedule");

			if (shcedule != null) {

				for (int i = 0; i < SIZE_X; i++) {
					JSONObject day = getJson(shcedule, "day" + (i + 1));
					if (day != null) {
						for (int j = 0; j < SIZE_Y; j++) {
							String value = CommonService.getString(day, "check"
									+ (j + 1));
							weekend.addSchedule(i, j, value);
						}
					}
				}
			}
		}
		return weekend;
	}

	private static JSONObject getJson(JSONObject jsonObject, String key) {
		return CommonService.getJSONObjectByKey(jsonObject, key);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id" + idSport).append("\n");
		builder.append("name" + name).append("\n");
		builder.append("skilllevel" + skilllevel).append("\n");
		for (int i = 0; i < SIZE_X; i++) {
			builder.append("day ").append("\n");
			for (int j = 0; j < SIZE_Y; j++) {
				builder.append(schedule[i][j]).append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
