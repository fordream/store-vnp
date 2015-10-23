package com.icts.shortfilmfestival.api;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import com.icts.shortfilmfestival.utils.HttpManager;
import com.icts.shortfilmfestival.utils.IOUtilities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class SSSFApi {

	private static final String TAG = "LOG_SSSFApi";

	/** ConnectivityManager . */
	public static ConnectivityManager mConnectivityManager;

	/** json Data . */
	public static String jsonData;

	/** Get Connectivity . */
	public static ConnectivityManager getConnectivityManager() {
		return mConnectivityManager;
	}

	/** Set Connectivity . */
	public static void setConnectivityManager(
			ConnectivityManager pConnectivityManager) {
		mConnectivityManager = pConnectivityManager;
	}

	/**
	 * Set Data Json
	 * 
	 * @param jsons
	 */
	public static void setJson(String jsons) {
		jsonData = jsons;
		Log.d(TAG, "Data is SET for JSONObject");
	}

	/** get Data Json . */
	public static String getJson() {
		return jsonData;
	}

	/** Process request Url . */
	public static String getAllNews(String url, String param, String lang) {
		String dataAll = "";
		// url += (param + "&lang=" + lang);
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {

			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/** Process request Url . */
	public static String getAllSchedule(String url, String param, String lang) {
		String dataAll = "";
		// url += (param + "&lang=" + lang);
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {

			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	public static String getDetailNews(String url, String param, int newsId,
			String lang) {
		String dataAll = "";
		url += (param + "&id=" + newsId + "&lang=" + lang);
		// url =
		// "http://web.icts.vn/shorts/api/detail.php?type=ssff&id=1328305197&lang=en";
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {
			// insert url and parameters;
			// String pid = "";
			// if(!SystemResource.PidPreferences.getPid().equals("")){
			// pid = "&pid=" + SystemResource.PidPreferences.getPid();
			// }
			// Log.d(TAG, "Plain URL: " + url + param+pid);
			// String encodedURL = url + encodeByPopGate(param+pid);
			// Log.d(TAG, "Encoded URL: " + encodedURL);
			// Get Url Encode with PopGate
			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;

				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/**
	 * This method get number like news
	 * 
	 * @param url
	 * @return
	 */
	public static String getNumLikeTwitter(String url) {
		String dataAll = "";
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {
			// insert url and parameters;
			// String pid = "";
			// if(!SystemResource.PidPreferences.getPid().equals("")){
			// pid = "&pid=" + SystemResource.PidPreferences.getPid();
			// }
			// Log.d(TAG, "Plain URL: " + url + param+pid);
			// String encodedURL = url + encodeByPopGate(param+pid);
			// Log.d(TAG, "Encoded URL: " + encodedURL);
			// Get Url Encode with PopGate
			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/**
	 * This method get number like facebook
	 * 
	 * @param url
	 * @return
	 */
	public static String getNumLikeFaceBook(String url) {
		String dataAll = "";
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {
			// insert url and parameters;
			// String pid = "";
			// if(!SystemResource.PidPreferences.getPid().equals("")){
			// pid = "&pid=" + SystemResource.PidPreferences.getPid();
			// }
			// Log.d(TAG, "Plain URL: " + url + param+pid);
			// String encodedURL = url + encodeByPopGate(param+pid);
			// Log.d(TAG, "Encoded URL: " + encodedURL);
			// Get Url Encode with PopGate
			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/** Process request Url . */
	public static String getAllYoutube(String url) {
		String dataAll = "";
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {
			// insert url and parameters;
			// String pid = "";
			// if(!SystemResource.PidPreferences.getPid().equals("")){
			// pid = "&pid=" + SystemResource.PidPreferences.getPid();
			// }
			// Log.d(TAG, "Plain URL: " + url + param+pid);
			// String encodedURL = url + encodeByPopGate(param+pid);
			// Log.d(TAG, "Encoded URL: " + encodedURL);
			// Get Url Encode with PopGate
			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/** Process request Url . */
	public static String getAllPhoto(String url) {
		String dataAll = "";
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {
			// insert url and parameters;
			// String pid = "";
			// if(!SystemResource.PidPreferences.getPid().equals("")){
			// pid = "&pid=" + SystemResource.PidPreferences.getPid();
			// }
			// Log.d(TAG, "Plain URL: " + url + param+pid);
			// String encodedURL = url + encodeByPopGate(param+pid);
			// Log.d(TAG, "Encoded URL: " + encodedURL);
			// Get Url Encode with PopGate
			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}

	/** Process request Url . */
	public static String getAllScheduleDetail(String url) {
		String dataAll = "";
		// url += (param + "&lang=" + lang);
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {

			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}
	
	/** Process request Url . */
	public static String getAllVenuesLink(String url) {
		String dataAll = "";
		// url += (param + "&lang=" + lang);
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {

			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}
	
	/** Process request Url . */
	public static String getYoutubeTopNews(String url) {
		String dataAll = "";
		// url += (param + "&lang=" + lang);
		Log.d(TAG, "URL REQUEST:" + url);
		// Exception Networking on Device
		NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		} else {
			// show Error networkinfo
			dataAll = "ERROR" + "Cannot NetWork";
			return dataAll;
		}

		HttpEntity entity = null;
		try {

			HttpGet httpGet = new HttpGet(url);
			Log.d(TAG, "URL REQUEST:" + url);
			// Execute utl
			HttpResponse response = HttpManager.execute(httpGet);
			Log.d(TAG, response.getStatusLine().getStatusCode()
					+ "-------------------");
			// Response Ok
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
				InputStream in = null;
				BufferedOutputStream out = null;
				try {
					in = entity.getContent();
					final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					out = new BufferedOutputStream(dataStream,
							IOUtilities.IO_BUFFER_SIZE);
					IOUtilities.copy(in, out);
					out.flush();
					final String data = dataStream.toString();
					dataAll += data;
					Log.d(TAG, "Result of URL :" + data);
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOEXCEPTION:" + e);

				} finally {
					IOUtilities.closeStream(in);
					IOUtilities.closeStream(out);
				}
			} else {
				// show Error status with status!=200
				dataAll = "ERROR";
				switch (response.getStatusLine().getStatusCode()) {
				case 400:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_400);
					break;
				case 401:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_401);
					break;
				case 403:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_403);
					break;
				case 404:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_404);
					break;
				case 408:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_408);
					break;
				case 500:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_500);
					break;
				case 501:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_501);
					break;
				default:
					// dataAll
					// +=SystemResource.getStringFromXml(R.string.error_exception);
					break;
				}
				Log.d(TAG, "Status result from url is :"
						+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			// show Error
			dataAll = "ERROR";
			Log.d(TAG, " Exception:" + e);
		} finally {
			if (entity != null) {
				try {
					entity.consumeContent();
				} catch (IOException e) {
					// show Error
					dataAll = "ERROR";
					Log.d(TAG, "IOException error: " + e);
				}
			}
		}
		return dataAll;
	}
}
