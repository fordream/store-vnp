package com.vnp.core.base.callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.vnp.core.base.service.RestClient;

public abstract class ResClientCallBack extends CallBack {
	private Map<String, String> maps = new HashMap<String, String>();

	public final void addParam(String name, String value) {
		maps.put(name, value);
	}

	public RestClient.RequestMethod getMedthod() {
		return RestClient.RequestMethod.GET;
	}

	public abstract String getUrl();

	@Override
	public Object execute() {
		RestClient client = new RestClient(getUrl());
		// client.addHeader(name, value);
		Set<String> set = maps.keySet();
		for (String key : set) {
			client.addParam(key, maps.get(key));
		}
		try {
			client.execute(getMedthod());
		} catch (Exception e) {
		}

		return client;
	}
}