package vn.vvn.bibook.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Environment;
import android.util.Log;

public class RestClient {
	private static File extStorageDirectory = Environment.getDataDirectory();
	private static String RESULT = "result";
	public static String results;
	public static int TIMEOUT = 120000;

	private static String convertStreamToString(InputStream is)
			throws UnsupportedEncodingException {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		// returnValueList list;
		// returnValue value=null;
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

	/*
	 * This is a test function which will connects to a given rest service and
	 * prints it's response to Android Log with labels "Praeda".
	 */
	public static JSONArray connect(String url) {

		// HttpClient httpclient = new DefaultHttpClient();
		// String results;
		// Prepare a request object
		HttpGet httpget = null;

		// Execute the request
		HttpResponse response;
		try {
			for (int i = 0; i < 3; i++) {
				HttpParams params = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, TIMEOUT);
				HttpProtocolParams.setContentCharset(params, "UTF-8");
				httpget = new HttpGet(new URL(url).toURI());
				HttpClient httpclient = new DefaultHttpClient(params);
				response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// A Simple JSON Response Read
					InputStream instream = entity.getContent();

					results = convertStreamToString(instream);
					Log.d("haipn", "results.lenght = " + results.length());
					int index1 = results.indexOf('[');
					int index2 = results.indexOf('{');

					String json = results.substring(index1 > index2 ? index2
							: index1);

					Log.d("haipn", "json lenght: " + json.length());
					Log.d("haipn", "json :" + json);
					// JSONObject json = new JSONObject(RESULT);
					JSONArray list = new JSONArray(json);

					// JSONArray list = ob.getJSONArray(RESULT);
					instream.close();
					return list;
				}
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			// e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
