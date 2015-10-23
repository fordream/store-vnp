package com.cnc.buddyup.request;


import org.com.cnc.common.android.request.CommonRequest;

import com.cnc.buddyup.common.Common;

public class RequestWeekendUpdate extends CommonRequest {
	public RequestWeekendUpdate() {
		setUrl("http://buddyup.com/api/profile/Updatesportactivity.aspx");
		setToken(Common.token);
	}

	public void setDataSport(int sportIndex, String sportId, String skillId,
			String philosophyValue, boolean[][] check) {
		addParameter("sport_" + sportIndex, sportId);
		addParameter("skill_" + sportIndex, skillId);
		addParameter("philosophy_" + sportIndex, philosophyValue);
		for(int i = 1; i < 9; i ++){
			for(int j = 1; j < 6; j ++){
				
			}
		}
	}
}
