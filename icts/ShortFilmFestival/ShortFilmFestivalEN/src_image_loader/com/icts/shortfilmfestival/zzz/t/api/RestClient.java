package com.icts.shortfilmfestival.zzz.t.api;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RestClient {

	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	private static final int TIME_OUT = 10 * 1000;

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "="
							+ URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			HttpGet request = new HttpGet(url + combinedParams);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			this.executeRequest(request, url);
			break;
		}
		case POST: {
			HttpPost request = new HttpPost(url);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			this.executeRequest(request, url);
			break;
		}
		case PUT: {
			HttpPut request = new HttpPut(url);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}
			this.executeRequest(request, url);
			break;

		}
		case DELETE: {
			HttpDelete request = new HttpDelete(url);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			this.executeRequest(request, url);
			break;

		}
		}
	}

	private void executeRequest(HttpUriRequest request, String url) {

		int timeout = TIME_OUT;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
		HttpConnectionParams.setSoTimeout(httpParameters, timeout);

		HttpClient client = new DefaultHttpClient(httpParameters);

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				response = EntityUtils.toString(entity);
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
		}
	}

	public void executeUploadFile() throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpPut request = new HttpPut(url);
		MultipartEntity partEntity = new MultipartEntity();

		HttpResponse httpResponse;
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		for (NameValuePair p : params) {
			if (p.getName().equals("user[avatar]")) {
				partEntity.addPart(p.getName(),
						new FileBody(new File(p.getValue()), "image/jpeg"));
			} else if (p.getName().equals("file")) {
				partEntity.addPart(p.getName(),
						new FileBody(new File(p.getValue()), "image/jpeg"));
			} else if (p.getName().equals("user[cover]")) {
				partEntity.addPart(p.getName(),
						new FileBody(new File(p.getValue()), "image/jpeg"));
			} else if (p.getName().equals("post[url]")) {
				partEntity.addPart(p.getName(),
						new FileBody(new File(p.getValue()), "image/jpeg"));
			} else {
				partEntity.addPart(p.getName(), new StringBody(p.getValue()));
			}
		}

		request.setEntity(partEntity);
		try {

			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				response = EntityUtils.toString(entity);
			}

		} catch (ClientProtocolException e) {
			Log.e("ERRRRO", "x", e);
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			Log.e("ERRRRO", "x", e);
		}

	}
}