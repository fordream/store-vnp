package com.cnc.buddyup.request;

import org.com.cnc.common.android.request.CommonRequest;


public class RequestProfileWeekend extends CommonRequest {
	public RequestProfileWeekend(String id, String token) {
		String url = "http://buddyup.com/api/profile/listsscheduleweek/id/{0}/token/{1}.aspx";
		url = url.replace("{0}", id);
		url = url.replace("{1}", token);
		setUrl(url);
		setGet(true);
		setToken(token);
		setTimeout(15000);
	}
}
