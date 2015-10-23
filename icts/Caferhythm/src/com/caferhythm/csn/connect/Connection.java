package com.caferhythm.csn.connect;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Connection {
	public static final String BASE_URL = "http://joseiapi0001.murashiki.com";
	private static AsyncHttpClient client = new AsyncHttpClient();
	public static void get(String url,RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(10000);
		Log.i("test",""+getAbsoluteUrl(url));
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(10000);
		Log.i("test",""+getAbsoluteUrl(url));
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
	
}
