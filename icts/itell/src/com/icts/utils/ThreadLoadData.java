package com.icts.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 
 * 
 * @author Luongdt Class thread load data from server Truoc khi load show dialog
 *         loading khi load xong day du lieu len cho thread truyen vao
 */

public class ThreadLoadData extends Thread {
	Handler mHandler;
	final static int STATE_DONE = 0;
	final static int STATE_RUNNING = 1;
	int mState;
	int total;
	private String mURL;
	private String mData;
	private JSONObject mJsonObject;
	private boolean exeption = true;
	private ProgressDialog progressDialogLoading;
	private Context context;
	private static String userAuthen = "itell";
	private static String passAuthen = "itell@2012";
	private List<NameValuePair> mNameValuePairs;

	public ThreadLoadData(Handler h, String url, Context context,
			List<NameValuePair> nameValuePairs) {
		mHandler = h;
		mURL = url;
		this.context = context;
		mNameValuePairs = nameValuePairs;
		Log.e("Loading", "LoadingLoadingLoading");
		progressDialogLoading = new ProgressDialog(this.context);
		progressDialogLoading.setMessage("Loading...");
		progressDialogLoading.show();

	}

	public void run() {
		mState = STATE_RUNNING;
		total = 0;
		Log.d("welcome", "running");
		if (mState == STATE_RUNNING) {
			total++;
			try {
				Log.e("", "url=============" + mURL);
				if (mNameValuePairs == null)
					mData = getJSONfromURL(mURL);
				else
					mData = postData(mURL, mNameValuePairs);
				// Log.e("", "data==1=" + mData);
			} catch (TimeoutException e) {
				exeption = false;
				e.printStackTrace();
			} catch (IllegalStateException e) {
				exeption = false;
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				exeption = false;
				e.printStackTrace();
			} catch (IOException e) {
				exeption = false;
				e.printStackTrace();
			} catch (URISyntaxException e) {
				exeption = false;
				e.printStackTrace();
			}
			Message msg = mHandler.obtainMessage();
			Bundle b = new Bundle();
			b.putInt("total", total);
			Log.e("", "data==" + mData);
			b.putString("data", mData);
			b.putBoolean("exeption", exeption);
			msg.setData(b);
			msg.what = 1;
			mHandler.sendMessage(msg);
			progressDialogLoading.dismiss();

		}
	}

	// load special list
	/*
	 * sets the current state for the thread, used to stop the thread
	 */
	public String getData() {
		return mData;
	}

	public boolean getExeption() {
		return exeption;
	}

	public void setState(int state) {
		mState = state;
	}

	/**
	 * 
	 * 
	 * @param url
	 * @return
	 * @throws TimeoutException
	 * @throws IllegalStateException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * 
	 *             ham connect vs server de load data
	 */

	public static String connect(String url) throws TimeoutException,
			IllegalStateException, ClientProtocolException, IOException,
			URISyntaxException {

		HttpGet httpget = null;
		HttpResponse response;
		HttpParams params = new BasicHttpParams();
		// HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		HttpConnectionParams.setConnectionTimeout(params, 5000);

		httpget = new HttpGet(new URL(url).toURI());

		// httpget.addHeader("Accept-Encoding", "gzip");
		HttpClient httpclient = new DefaultHttpClient(params);
		// httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
		// config.USER_AGENT_NAME);
		response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			// A Simple JSON Response Read
			InputStream instream = entity.getContent();
			// instream = new GZIPInputStream(instream);
			return convertStreamToString(instream);
		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param is
	 * @return Convert byte sang String data lay tu server
	 */

	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * jsonObject get
	 * 
	 * @param url
	 * @return
	 */
	public String getJSONfromURL(String url) throws TimeoutException,
			IllegalStateException, ClientProtocolException, IOException,
			URISyntaxException {
		// initialize
		InputStream is = null;
		String result = "";
		JSONObject jObject = null;
		// try {
		HttpParams httpParams = new BasicHttpParams();
		// HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		// use and pp
		HttpGet httpGet = new HttpGet(url);
		// add autheticate if co'
		httpGet.addHeader(BasicScheme.authenticate(
				new UsernamePasswordCredentials(userAuthen, passAuthen),
				"UTF-8", false));
		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}
		// } catch (Exception e) {
		// }
		// convert response to string
		// try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		result = sb.toString();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// try parse the string to a JSON object
		// try {
		// jObject = new JSONObject(result);
		// } catch (JSONException e) {
		// }
		return result;
	}

	StringBuilder sb = new StringBuilder();

	/*
	 * POST
	 */
	public String postData(String url, List<NameValuePair> nameValuePairs) {
		// Looper.prepare();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(userAuthen, passAuthen),
					"UTF-8", false));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
			// read response data
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);

				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				System.out.println("reponse: "
						+ response.getStatusLine().getStatusCode()
						+ " content: " + sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e("", "qqqqqqqqqqqqqqq");
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		// Looper.loop();
		return sb.toString();
	}

}
