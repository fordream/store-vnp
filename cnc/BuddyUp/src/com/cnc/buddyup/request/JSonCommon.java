package com.cnc.buddyup.request;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.cnc.buddyup.common.Common;

public class JSonCommon {

	public static JSONObject getJSONfromURLPOST(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result1 = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result1);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

	public static JSONObject getJSONfromURLGET(String url) {
		InputStream is = null;
		JSONObject jArray = null;

		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);

			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpGet httppost = new HttpGet(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			String result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

	public static JSONObject getJSONfromURLPOSTRegister(String url,
			RequestRegister register) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs
					.add(new BasicNameValuePair("Zip", register.getZip()));
			nameValuePairs.add(new BasicNameValuePair("Country", register
					.getCountry()));
			nameValuePairs.add(new BasicNameValuePair("username", register
					.getUsername()));
			nameValuePairs.add(new BasicNameValuePair("email", register
					.getEmail()));
			nameValuePairs.add(new BasicNameValuePair("emailcheck", register
					.getEmailcheck()));
			nameValuePairs.add(new BasicNameValuePair("newsletter", register
					.getNewsletter()));
			nameValuePairs.add(new BasicNameValuePair("firstname", register
					.getFirstname()));
			nameValuePairs.add(new BasicNameValuePair("userpassword", register
					.getUserpassword()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result1 = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result1);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

	public static String getString(JSONObject object, String string) {
		try {
			return object.getString(string);
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONObject getJSONfromURLPOSTResponseAddBuddyList(
			RequestAddbuddyList request) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			String url = request.getUrl().replace("{0}", Common.token);
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", request
					.getUsername()));
			nameValuePairs.add(new BasicNameValuePair("description", request
					.getDescription()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result1 = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result1);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

	public static JSONObject updateProfile(RequestProfileUpdate request) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			String url = request.getUrl();
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("Zip", URLEncoder
					.encode(request.getZip())));
			nameValuePairs.add(new BasicNameValuePair("Country", URLEncoder
					.encode(request.getCountry())));
			nameValuePairs.add(new BasicNameValuePair("username", URLEncoder
					.encode(request.getUsername())));
			nameValuePairs.add(new BasicNameValuePair("email", request
					.getEmail()));
			nameValuePairs.add(new BasicNameValuePair("newsletter", URLEncoder
					.encode(request.getNewsletter())));
			nameValuePairs.add(new BasicNameValuePair("firstname", URLEncoder
					.encode(request.getFirstname())));
			nameValuePairs.add(new BasicNameValuePair("userpassword",
					URLEncoder.encode(request.getUserpassword())));
			nameValuePairs.add(new BasicNameValuePair("reuserpassword",
					URLEncoder.encode(request.getReuserpassword())));
			nameValuePairs.add(new BasicNameValuePair("token", Common.token));
			nameValuePairs.add(new BasicNameValuePair("age", URLEncoder
					.encode(request.getAge())));
			nameValuePairs.add(new BasicNameValuePair("sport1", request
					.getSport1()));
			nameValuePairs.add(new BasicNameValuePair("sport2", request
					.getSport2()));
			nameValuePairs.add(new BasicNameValuePair("sport3", request
					.getSport3()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result1 = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result1);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

	public static JSONObject UpdateOptionProfile(
			RequestUpdateOptionInformation request) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters,
					Common.TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, Common.TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);
			String url = request.getUrl();
			HttpPost httppost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("Token", URLEncoder
					.encode(Common.token)));
			nameValuePairs.add(new BasicNameValuePair("Sex", URLEncoder
					.encode(request.getSex())));
			nameValuePairs.add(new BasicNameValuePair("Comments", URLEncoder
					.encode(request.getComments())));
			nameValuePairs.add(new BasicNameValuePair("Description", URLEncoder
					.encode(request.getDescription())));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			InputStreamReader input = new InputStreamReader(is, "iso-8859-1");
			BufferedReader reader = new BufferedReader(input, 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			jArray = new JSONObject(result);
		} catch (Exception e) {
			String result1 = "{\"Status\":\"false\", \"Message\":\"Connect time out!\",\"Error\":\"Connect time out!\"}";
			try {
				return new JSONObject(result1);
			} catch (JSONException e1) {
			}
		}

		return jArray;
	}

}
