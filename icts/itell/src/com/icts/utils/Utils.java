package com.icts.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.icts.app.ItelApplication;
import com.icts.itel.R;
import com.icts.object.CompanyObject;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.object.ObjFriend;
import com.icts.object.StampCatObject;
import com.icts.object.StampObject;
import com.icts.object.User;
import com.icts.viewcustom.Map;

public class Utils {
	private final static String AUTHEN_USER = "itell";
	private final static String AUTHEN_PASS = "itell@2012";
	private final static boolean IS_PRODUCTION = false;
	
	public static final String urlRegisterToPush = "http://49.212.140.145/itell/setting/register_device_push";
	public static final String urlUnregisterToPush = "http://49.212.140.145/itell/setting/unregister_device_push";
	
	private static String urlDeleteImage = "http://49.212.140.145/itell/setting/delete_img";
	// Map

	// Add para user_id, uuid, longtitude, latitude
	// district, city, timestamp
	private static String urlPostLocation = "http://49.212.140.145/itell/map/post_position?";

	// API get friends around user
	// Add para user_id, uuid, longtitude, latitude, max_matches, distance
	private static String urlGetUserProfiles = "http://49.212.140.145/itell/profile/get_profile_multi?";
		
	// API get friends around user
	// Add para user_id, uuid, longtitude, latitude, max_matches, distance
	private static String urlGetMapFriends = "http://49.212.140.145/itell/map/get_map_friend";

	// API get others around user
	// Add para user_id, longtitude, latitude, max_matches, distance
	private static String urlGetMapOthers = "http://49.212.140.145/itell/map/get_map_other";

	private static String urlGetMapCompany = "http://49.212.140.145/itell/map/get_map_company";
	private static String urlUpdateInfo = "http://49.212.140.145/itell/user/update_user_info";
	private static String urlAllowSearch = "http://49.212.140.145/itell/setting/allow_search";
	private static String urlDenySearch = "http://49.212.140.145/itell/setting/deny_search";

	private static String urlBlock = "http://49.212.140.145/itell/profile/block";
	private static String urlUnBlock = "http://49.212.140.145/itell/profile/unblock";

	private static String urlAccept = "http://49.212.140.145/itell/friend/accept_invite";
	private static String urlDeny = "http://49.212.140.145/itell/friend/deny_invite";
	private static String urlRequest = "http://49.212.140.145/itell/profile/send_invite";

	private static String urlRestrict = "http://49.212.140.145/itell/profile/restrict_pub";
	private static String urlPub = "http://49.212.140.145/itell/profile/allow_pub";

	// Friends
	/**
	 * http://49.212.140.145/itell/friend/get_list_friend?user_id=&uuid=
	 */
	private static String urlGetfriendlist = "http://49.212.140.145/itell/friend/get_list_friend?";

	/**
	 * http://49.212.140.145/itell/chat/get_server?user_id=11&uuid=
	 * ccf4766507a1b47bbc321fa995687d6a
	 */
	private static String urlGetServerInfo = "http://49.212.140.145/itell/chat/get_server?";

	private static String urlCheckUser = "http://49.212.140.145/itell/user/check_user?";

	// Stamp post
	private static String urlFreeStamp = "http://49.212.140.145/itell/chat/get_list_free_stamp";
	private static String urlUserStamp = "http://49.212.140.145/itell/chat/get_list_user_stamp";

	/**
	 * Check whether a network connection is available or not
	 * 
	 * @param ac
	 * @return false if fail;
	 *         <p>
	 *         true if success
	 */
	public static boolean hasNetwork(Activity ac) {
		ConnectivityManager connMgr = (ConnectivityManager) ac
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * { user_id: user_id, uuid: uuid longitude: longitude, latitude: latitude,
	 * strict: string, city: string, street: string, time_stamp: curr_time }
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static boolean postLocation(final String user_id, final String uuid,
			double lat, double lng, String strict, String city, String street)
			throws UnsupportedEncodingException {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(urlPostLocation);
			final long timestamp = getTimeStamp();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
			nameValuePairs.add(new BasicNameValuePair("uuid", uuid));
			nameValuePairs.add(new BasicNameValuePair("longitude", String
					.valueOf(lng)));
			nameValuePairs.add(new BasicNameValuePair("latitude", String
					.valueOf(lat)));
			if (strict == null) {
				nameValuePairs.add(new BasicNameValuePair("strict", ""));
			} else {
				nameValuePairs.add(new BasicNameValuePair("strict", strict));
			}
			if (city == null) {
				nameValuePairs.add(new BasicNameValuePair("city", ""));
			} else {
				nameValuePairs.add(new BasicNameValuePair("city", city));
			}

			if (street == null) {
				nameValuePairs.add(new BasicNameValuePair("street", ""));
			} else {
				nameValuePairs.add(new BasicNameValuePair("street", street));
			}
			nameValuePairs.add(new BasicNameValuePair("time_stamp", String
					.valueOf(timestamp)));
			httppost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					HTTP.UTF_8, false));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
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
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();

				boolean success = getDataFromPostLocation(sb.toString());
				System.out.println("reponse: "
						+ response.getStatusLine().getStatusCode()
						+ " content: " + sb.toString());
				return success;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

	/**
	 * parse json data
	 * 
	 * @param data
	 * @return true if success
	 */
	private static boolean getDataFromPostLocation(String data) {

		try {
			JSONObject json = new JSONObject(data);
			boolean retval = json.getBoolean("retval");
			if (retval) {
				return true;
			} else {
				getFailInfo(json);
				return false;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get info if fail
	 * 
	 * @param json
	 */
	private static void getFailInfo(JSONObject json) {
		try {
			ItelApplication.err_code = json.getInt("error_code");
			ItelApplication.err_message = json.getString("error_msg");
			ItelApplication.timeStamp = json.getLong("time_stamp");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get current time that converted to milisecond
	 * 
	 * @return
	 */
	public static long getTimeStamp() {
		Calendar c = Calendar.getInstance(Locale.JAPAN);
		return c.getTimeInMillis();
	}

	/**
	 * Show error dialog
	 * 
	 * @param context
	 * @param title
	 */
	public static void showDialogErorr(Context context, String title) {
		AlertDialog.Builder builderMessageNotSupport = new AlertDialog.Builder(
				context);
		builderMessageNotSupport.setMessage(title).setNegativeButton("OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog messageDialogNotSupport = builderMessageNotSupport.create();
		messageDialogNotSupport.show();
	}

	/**
	 * Get address from latitude and longitude
	 * 
	 * @param lat
	 * @param lng
	 * @param context
	 * @return
	 */
	public static String[] geocoder(double lat, double lng, Context context) {
		Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = geoCoder.getFromLocation(lat, lng, 1);
			if (addresses.size() > 0) {
				Address address = addresses.get(0);
				String[] info = Utils.getAddressInfo(address);
				/*
				 * String add = ""; for (int i=0;
				 * i<addresses.get(0).getMaxAddressLineIndex(); i++) add +=
				 * addresses.get(0).getAddressLine(i) + " ";
				 * Log.i("NDT","address "+add); }
				 */

				return info;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get stamp from server. If updated = 0 get all stamp
	 * 
	 * @param user_id
	 * @param uuid
	 * @param updated
	 * @param free
	 * @return list stamp object
	 */
	public static ArrayList<StampObject> getStamp(String user_id, String uuid,
			long updated, boolean free) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("updated", String
					.valueOf(updated)));
			String url = urlFreeStamp;
			if (!free) {
				url = urlUserStamp;
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					ArrayList<StampObject> arr = getStampData(sb.toString(),
							free);
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Get info of a user: street, district, city, country E.g:
	 * Address[addressLines=[0:"40 Triều Khúc",1:"Thanh Xuân Nam",
	 * 2:"Thanh Xuân", 3:"Hanoi",4:"Vietnam"], feature=40,admin=Hanoi,
	 * sub-admin=Thanh Xuân,locality=null, thoroughfare=Triều
	 * Khúc,postalCode=null,
	 * countryCode=VN,countryName=Vietnam,hasLatitude=true,
	 * latitude=20.9858535,hasLongitude
	 * =true,longitude=105.7986295,phone=null,url=null,extras=null]
	 * 
	 * @param address
	 * @return street, district, city, country
	 */
	public static String[] getAddressInfo(Address address) {
		String[] info = new String[4];
		info[0] = address.getThoroughfare();
		info[1] = address.getSubAdminArea();
		info[2] = address.getAdminArea();
		info[3] = address.getCountryName();
		return info;
	}

	// Map
	/**
	 * Get friends around 2km
	 * 
	 * @param user_id
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static ArrayList<FriendObject> getMapFriends(final String user_id,
			final String uuid, double lat, double lng, int page) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
			params.add(new BasicNameValuePair("longitude", String.valueOf(lng)));
			params.add(new BasicNameValuePair("max_matches", String
					.valueOf(Constant.MAX_MATCHES)));
			params.add(new BasicNameValuePair("distance", String
					.valueOf(Constant.MAP_DISTANCE)));
			// params.add(new BasicNameValuePair("page", String.valueOf(page)));
			HttpPost httpPost = new HttpPost(urlGetMapFriends);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					ArrayList<FriendObject> arr = getMapFriendsData(sb
							.toString());
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Get others (not friends) around MAP_DISTANCE
	 * 
	 * @param user_id
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static ArrayList<FriendObject> getMapOthers(String user_id,
			String uuid, double lat, double lng, int page) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
			params.add(new BasicNameValuePair("longitude", String.valueOf(lng)));
			params.add(new BasicNameValuePair("max_matches", String
					.valueOf(Constant.MAX_MATCHES)));
			params.add(new BasicNameValuePair("distance", String
					.valueOf(Constant.MAP_DISTANCE)));
			// params.add(new BasicNameValuePair("page", String.valueOf(page)));

			HttpPost httpPost = new HttpPost(urlGetMapOthers);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					ArrayList<FriendObject> arr = getMapOtherUserData(sb
							.toString());
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Get others (not friends) around MAP_DISTANCE:
	 * <p>
	 * E.g: "id":"5506","name":"BOZU","address":"","longitude":"105.811579",
	 * "latitude":"21.010634",
	 * "mobile_num":"694016568","email":"","avatar":"","desc"
	 * :"","distance":1604.0933837891s
	 * 
	 * @param user_id
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static ArrayList<CompanyObject> getMapCompany(String user_id,
			String uuid, double lat, double lng, int page) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
			params.add(new BasicNameValuePair("longitude", String.valueOf(lng)));
			params.add(new BasicNameValuePair("max_matches", String
					.valueOf(Constant.MAX_MATCHES)));
			params.add(new BasicNameValuePair("distance", String
					.valueOf(Constant.MAP_DISTANCE)));
			// params.add(new BasicNameValuePair("page", String.valueOf(page)));

			HttpPost httpPost = new HttpPost(urlGetMapCompany);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					ArrayList<CompanyObject> arr = getMapCompanyUserData(sb
							.toString());
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * Get data of friends
	 * "id":"25","nick":"tuhucon","avatar":"","longitude":"105.798802",
	 * "latitude":"20.986245","gender":false,"itell":"",
	 * "itell_start":"0000-00-00 00:00:00","distance":0
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<FriendObject> getMapFriendsData(String data) {
		try {
			ArrayList<FriendObject> arr = new ArrayList<FriendObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.user_id = jsonParentObj.getInt("user_id");
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			// int totalResults = jsonParentObj.getInt("total_found");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				Map.totalFriends = 0;
				Map.currentPageFriends = 0;
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}
			Map.totalFriends = jsonParentObj.getInt("total_found");
			Map.currentPageFriends = jsonParentObj.getInt("current_page");

			JSONArray jsonAr;
			int l;
			JSONObject jsonObj;
			// Get friends
			if (jsonParentObj.has("friend_data")) {
				jsonAr = jsonParentObj.getJSONArray("friend_data");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						FriendObject friend = new FriendObject();
						friend.setFriend(true);
						friend.setFriendID(jsonObj.getInt("id"));
						friend.setFriendNick(jsonObj.getString("nick"));
						friend.setFriendAva(jsonObj.getString("avatar"));
						/*
						 * friend.setFriendAva(
						 * "http://avatar.hq-picture.com/avatars/" +
						 * "img34/benfica_avatar_picture_95165.png");
						 */
						friend.setFriendLong(jsonObj.getDouble("longitude"));
						friend.setFriendLat(jsonObj.getDouble("latitude"));
						friend.setMale(!jsonObj.getBoolean("gender"));
						friend.setFriendiTell(jsonObj.getString("itell"));
						friend.setFriendStatTime(jsonObj
								.getString("itell_start"));
						friend.distance = jsonObj.getDouble("distance");
						
						//address
						String street = null;
						String district = null;
						String city = null;
						if (jsonObj.has("street")) {
							street = jsonObj.getString("street");
						}
						if (jsonObj.has("strict")) {
							district = jsonObj.getString("strict");
						}
						if (jsonObj.has("city")) {
							city = jsonObj.getString("city");
						}
						String address = getAddress(city, street, district);
						friend.setFriendAddress(address);
						
						arr.add(friend);
					}
				}
			}

			if (jsonParentObj.has("other_data")) {
				// Get Other
				jsonAr = jsonParentObj.getJSONArray("other_data");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						FriendObject friend = new FriendObject();
						friend.setFriend(false);
						friend.setFriendID(jsonObj.getInt("user_id"));
						friend.setFriendNick(jsonObj.getString("user_name"));
						friend.setFriendAva(jsonObj.getString("user_avatar"));
						/*
						 * friend.setFriendAva(
						 * "http://avatar.hq-picture.com/avatars/" +
						 * "img34/benfica_avatar_picture_95165.png");
						 */
						friend.setFriendLong(jsonObj.getLong("user_longitude"));
						friend.setFriendLat(jsonObj.getLong("user_latitude"));
						if (jsonObj.getInt("user_gender") == 0) {
							friend.setMale(true);
						} else {
							friend.setMale(false);
						}
						friend.setFriendiTell(jsonObj.getString("user_itell"));
						friend.setFriendStatTime(jsonObj
								.getLong("user_itell_start_time"));
						
						String street = null;
						String district = null;
						String city = null;
						if (jsonObj.has("street")) {
							street = jsonObj.getString("street");
						}
						if (jsonObj.has("strict")) {
							district = jsonObj.getString("strict");
						}
						if (jsonObj.has("city")) {
							city = jsonObj.getString("city");
						}
						String address = getAddress(city, street, district);
						friend.setFriendAddress(address);
						// Log.i("NDT", "friend_id "
						// +jsonObj.getInt("friend_id"));
						// Log.i("NDT", "friend_nick "
						// +jsonObj.getString("friend_nick"));
						arr.add(friend);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get data of other users (not friends)
	 * "id":"25","nick":"tuhucon","avatar":"","longitude":"105.798802",
	 * "latitude":"20.986245","gender":false,"itell":"","itell_start":
	 * "0000-00-00 00:00:00","distance":0
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<FriendObject> getMapOtherUserData(String data) {
		try {
			ArrayList<FriendObject> arr = new ArrayList<FriendObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.user_id = jsonParentObj.getInt("user_id");
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			// int totalResults = jsonParentObj.getInt("total_found");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				Map.totalOther = 0;
				Map.currentPageOther = 0;
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}
			Map.totalOther = jsonParentObj.getInt("total_found");
			Map.currentPageOther = jsonParentObj.getInt("current_page");

			JSONArray jsonAr;
			int l;
			JSONObject jsonObj;
			// Get friends
			if (jsonParentObj.has("friend_data")) {
				jsonAr = jsonParentObj.getJSONArray("friend_data");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						FriendObject friend = new FriendObject();
						friend.setFriend(false);
						friend.setFriendID(jsonObj.getInt("id"));
						friend.setFriendNick(jsonObj.getString("nick"));
						friend.setFriendAva(jsonObj.getString("avatar"));
						/*
						 * friend.setFriendAva(
						 * "http://avatar.hq-picture.com/avatars/" +
						 * "img34/benfica_avatar_picture_95165.png");
						 */
						friend.setFriendLong(jsonObj.getDouble("longitude"));
						friend.setFriendLat(jsonObj.getDouble("latitude"));
						friend.setMale(!jsonObj.getBoolean("gender"));
						if (jsonObj.has("itell")) {
							friend.setFriendiTell(jsonObj.getString("itell"));
						}
						friend.setFriendStatTime(jsonObj
								.getString("itell_start"));
						friend.distance = jsonObj.getDouble("distance");
						
						//address
						String street = null;
						String district = null;
						String city = null;
						if (jsonObj.has("street")) {
							if (!jsonObj.isNull("street")){
								street = jsonObj.optString("street");
							}
						}
						if (jsonObj.has("strict")) {
							if (!jsonObj.isNull("strict")){
								district = jsonObj.optString("strict");
							}
						}
						if (jsonObj.has("city")) {
							if (!jsonObj.isNull("city")){
								city = jsonObj.optString("city");
							}
						}
						String address = getAddress(city, street, district);
						friend.setFriendAddress(address);
						// Log.i("NDT", "friend_id "
						// +jsonObj.getInt("friend_id"));
						// Log.i("NDT", "friend_nick "
						// +jsonObj.getString("friend_nick"));
						arr.add(friend);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * parse to object
	 * 
	 * @param data
	 *            : {"retval":true,"user_id":"11","time_stamp":1348067960,
	 *            "stamp_list":[{"id":"1","code":":-==", "image":
	 *            "http:\/\/icons.iconarchive.com\/icons\/iconscity\/flags\/256\/vietnam-icon.png"}]
	 *            }
	 * @param free
	 * @return
	 */
	public static ArrayList<StampObject> getStampData(String data, boolean free) {
		try {
			ArrayList<StampObject> arr = new ArrayList<StampObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.user_id = jsonParentObj.getInt("user_id");
			ItelApplication.timeStamp = jsonParentObj.getLong("time_stamp");

			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}

			JSONArray jsonAr;
			int l;
			JSONObject jsonObj;
			// Get friends
			if (jsonParentObj.has("stamp_list")) {
				jsonAr = jsonParentObj.getJSONArray("stamp_list");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						StampObject stamp = new StampObject();
						stamp.setId(jsonObj.getString("id"));
						stamp.setCode(jsonObj.getString("code"));
						stamp.setImageLink(jsonObj.getString("image"));
						stamp.setFree(free);
						arr.add(stamp);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get data of other users (not friends) E.g:
	 * "id":"5506","name":"BOZU","address"
	 * :"","longitude":"105.811579","latitude":"21.010634",
	 * "mobile_num":"694016568"
	 * ,"email":"","avatar":"","desc":"","distance":1604.0933837891s
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<CompanyObject> getMapCompanyUserData(String data) {
		try {
			int in = data.indexOf("{");
			ArrayList<CompanyObject> arr = new ArrayList<CompanyObject>();
			JSONObject jsonParentObj = new JSONObject(data.substring(in));
			ItelApplication.user_id = jsonParentObj.getInt("user_id");
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");

			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				Map.totalCom = 0;
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}
			Map.totalCom = jsonParentObj.getInt("total_found");
			Map.currentPageCom = jsonParentObj.getInt("current_page");

			JSONArray jsonAr;
			int l;
			JSONObject jsonObj;
			// Get friends
			if (jsonParentObj.has("company_data")) {
				jsonAr = jsonParentObj.getJSONArray("company_data");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						CompanyObject com = new CompanyObject();

						com.setmUserId(String.valueOf(jsonObj.getInt("id")));
						com.setmName(jsonObj.getString("name"));
						com.setmAvatar(jsonObj.getString("avatar"));
						/*
						 * com.setmAvatar("http://avatar.hq-picture.com/avatars/"
						 * + "img34/benfica_avatar_picture_95165.png");
						 */
						com.setmLong(jsonObj.getDouble("longitude"));
						com.setmLat(jsonObj.getDouble("latitude"));
						com.setmDesc(jsonObj.getString("desc"));
						com.setmDistance(jsonObj.getDouble("distance"));
						com.setmAddress(jsonObj.getString("address"));
						arr.add(com);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Friend
	/**
	 * send invite user
	 * 
	 * @param urlGet
	 * @param user_id
	 *            : 11
	 * @param uuid
	 *            : e.g:ccf4766507a1b47bbc321fa995687d6a
	 */
	public static ArrayList<FriendObject> getFriendsList(final int user_id,
			final String uuid, double lat, double lng) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", String
					.valueOf(user_id)));
			params.add(new BasicNameValuePair("uuid", uuid));
			String query = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(new URI(urlGetfriendlist + query));
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					// get friends array
					ArrayList<FriendObject> arr = getFriends(sb.toString(),
							lat, lng);
					Log.i("NDT", "reponse: "
							+ response.getStatusLine().getStatusCode()
							+ " content: " + sb.toString());
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static boolean updateInfo(String user_id, String uuid, String info) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("update_info", info));

			HttpPost httpPost = new HttpPost(urlUpdateInfo);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean allowSearch(String user_id, String uuid,
			boolean isAllow) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			String url = urlAllowSearch;
			if (!isAllow) {
				url = urlDenySearch;
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean restrictPub(String user_id, String uuid,
			String friendId, boolean restrict) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("friend_id", friendId));
			String url = urlRestrict;
			if (!restrict) {
				url = urlPub;
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}

	
	public static String requestFriend(String user_id,String uuid,String friendId){
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("friend_id", friendId));
			String url = urlRequest;
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					String c = getInviteID(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;
	}

	public static boolean deleteImage(String user_id, String uuid, int imgId) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("image_id", String.valueOf(imgId)));
			String url = urlDeleteImage;
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean deleteAvatar(String user_id, String uuid) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			String url = Constant.DELETE_AVATAR;
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean block(String user_id, String uuid, String friendId,
			boolean block) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("friend_id", friendId));
			String url = urlBlock;
			if (!block) {
				url = urlUnBlock;
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return false;
	}
	public static boolean acceptFriend(String user_id,String uuid,String inviteId,boolean accept){
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("invite_id", inviteId));
			String url = urlAccept;
			if (!accept){
				url = urlDeny;
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER,
							AUTHEN_PASS), "UTF-8", false));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httpPost);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean c = getUpdateReturn(sb.toString());
					return c;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {}
		return false;
	}
	
	private static boolean getUpdateReturn(String data){

		try {
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static FriendObject convertUser2Friend(User user){
		FriendObject friend = new FriendObject();
		friend.setFriendID(user.getUserId());
		friend.setFriendStatTime(user.getStatusUpdateTime());
		friend.setMale(user.isMale());
		friend.setFriend(user.isFriend());
		friend.setFriendLong(user.longitude);
		friend.setFriendLat(user.latitude);
		friend.setFriendAddress(user.getAddress());
		friend.setFriendAva(user.getImageUrl());
		friend.setFriendiTell(user.getStatus());
		friend.setFriendNick(user.getUserNick());
		return friend;
	}
	
	/**
	 * {"retval":true,"invite_data":{"user_id":"13","friend_id":"12","status":1,
	 * "modified":"2012-10-01 16:22:44","created":"2012-10-01 16:22:44","id":"58"},"timestamp":1349076164}
	 * @param data
	 * @return
	 */
	private static String getInviteID(String data){
		try {
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval){
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj.getString("error_msg");
				return null;
			}
			JSONObject json = jsonParentObj.getJSONObject("invite_data");
			
			return json.getString("id");
		}
		catch(Exception e){
			return null;
		}
		
	}

	/**
	 * @param data
	 *            : {"retval":true,"user_id":"11","friend_data":
	 *            [{"friend_id":"12"
	 *            ,"friend_nick":"khoi ki","friend_avatar":"","restrict_pub"
	 *            :false},
	 *            {"friend_id":"14","friend_nick":"maunb","friend_avatar"
	 *            :"","restrict_pub":false},
	 *            {"friend_id":"13","friend_nick":"hieu xit"
	 *            ,"friend_avatar":"","restrict_pub":false}],
	 *            "timestamp":1345947286}
	 */
	private static ArrayList<FriendObject> getFriends(String data, double lat,
			double lng) {
		try {
			ArrayList<FriendObject> arr = new ArrayList<FriendObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.user_id = jsonParentObj.getInt("user_id");
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}
			Log.i("NDT", "user_id " + ItelApplication.user_id);

			// Get friends
			JSONArray jsonAr = jsonParentObj.getJSONArray("friend_data");
			JSONObject jsonObj;
			FriendObject friend;
			int l;
			if (jsonAr != null) {
				l = jsonAr.length();
				for (int i = 0; i < l; i++) {
					jsonObj = jsonAr.optJSONObject(i);
					friend = new FriendObject();
					friend.setFriend(true);
					friend.setFriendID(jsonObj.getInt("friend_id"));
					friend.setFriendNick(jsonObj.getString("friend_nick"));
					friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
					friend.setFriendAva(jsonObj.getString("friend_avatar"));
					friend.setFriendiTell("friend status" + i);
					friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
							+ "img34/benfica_avatar_picture_95165.png");
					friend.setFriendLat(lat + 0.001 * (i + 1));
					friend.setFriendLong(lng + 0.001 * (i + 1));
					friend.setFriendStatTime(Calendar.getInstance(Locale.JAPAN)
							.getTimeInMillis());
					Log.i("NDT", "friend_id " + jsonObj.getInt("friend_id"));
					Log.i("NDT",
							"friend_nick " + jsonObj.getString("friend_nick"));
					arr.add(friend);
				}
			}

			if (jsonParentObj.has("other_data")) {
				// Get Other
				jsonAr = jsonParentObj.getJSONArray("other_data");
				if (jsonAr != null) {
					l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						friend = new FriendObject();
						friend.setFriend(false);
						friend.setFriendID(jsonObj.getInt("friend_id"));
						friend.setFriendNick(jsonObj.getString("friend_nick"));
						friend.setRestrict_pub(jsonObj
								.getBoolean("restrict_pub"));
						friend.setFriendiTell("friend_avatar");
						friend.setFriendAva(jsonObj.getString("friend_avatar"));
						//friend.setFriendAva("http://avatar.hq-picture.com/avatars/" +
						//		"img34/benfica_avatar_picture_95165.png");
						friend.setFriendLat(lat+0.001*(i+1));
						friend.setFriendLong(lng+0.001*(i+1));
						Log.i("NDT", "friend_id " +jsonObj.getInt("friend_id"));
						Log.i("NDT", "friend_nick " +jsonObj.getString("friend_nick"));

						arr.add(friend);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean getServerInfo(int user_id, String uuid) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", String
					.valueOf(user_id)));
			params.add(new BasicNameValuePair("uuid", String.valueOf(uuid)));

			String query = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(new URI(urlGetServerInfo + query));
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					boolean success = splitData(sb.toString());

					// if (BuildConfig.DEBUG){
					// System.out.println("reponse: "
					// + response.getStatusLine().getStatusCode()
					// + " content: " + sb.toString());
					// }
					return success;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public static User getCheckUser(String uuid) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uuid", uuid));

			String query = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(new URI(urlCheckUser + query));
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					"UTF-8", false));
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					User user = getUser1(sb.toString());

					// if (BuildConfig.DEBUG){
					// System.out.println("reponse: "
					// + response.getStatusLine().getStatusCode()
					// + " content: " + sb.toString());
					// }
					return user;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private static User getUser1(String data) throws JSONException {

		User user = new User();
		JSONObject jsonObject = new JSONObject(data);
		ItelApplication.timeStamp = jsonObject.getLong("timestamp");
		user.setmTimeCurrent(ItelApplication.timeStamp);
		JSONObject jsonObjectUserData = jsonObject.getJSONObject("user_data");
		JSONObject jsonObjectUser = jsonObjectUserData.getJSONObject("User");
		user.setName(jsonObjectUser.getString("name"));
		user.setImageUrl(jsonObjectUser.getString("avatar"));
		user.setUserNick(jsonObjectUser.getString("nick"));
		user.setUserId(jsonObjectUser.getString("id"));
		user.setMale(jsonObjectUser.getBoolean("gender"));
		user.itellPolicy = jsonObjectUser.getInt("itell_policy");
		user.setName(jsonObjectUser.getString("name"));
		Date dateBirth = Utils.convertStringToDate(
				jsonObjectUser.getString("birth"), "yyyy-MM-dd HH:mm:ss");
		if (dateBirth != null) {
			user.setBirthday(dateBirth.getTime());
		}
		Date dateStart = Utils.convertStringToDate(
				jsonObjectUser.getString("itell_start"), "yyyy-MM-dd HH:mm:ss");
		if (dateStart != null) {
			user.setStatusUpdateTime(dateStart.getTime());
		}

		return user;
	}

	/**
	 * Get port, address from server
	 * 
	 * @param data
	 * @return
	 */
	private static boolean splitData(String data) {
		try {
			JSONObject jsonParentObj = new JSONObject(data);
			ItelApplication.timeStamp = jsonParentObj.getLong("timestamp");
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				ItelApplication.serverInfo = null;
				ItelApplication.port = -1;
				ItelApplication.server = null;
				return false;
			}
			Log.i("NDT", "user_id " + ItelApplication.user_id);

			// Get server info
			String server = jsonParentObj.getString("server");
			Log.i("NDT", "server " + server);
			if (server == null) {
				ItelApplication.serverInfo = null;
				ItelApplication.port = -1;
				ItelApplication.server = null;
				return false;
			}

			// Success
			ItelApplication.server = server;
			String[] s = server.split(":");
			ItelApplication.port = Integer.parseInt(s[1]);
			ItelApplication.serverInfo = s[0].trim();
			Log.i("NDT", "data: port " + ItelApplication.port + " serverip "
					+ ItelApplication.serverInfo);
			return true;
		} catch (JSONException e) {
			ItelApplication.serverInfo = null;
			ItelApplication.port = -1;
			ItelApplication.server = null;
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get message from server
	 * 
	 * @param data
	 *            {"receiver":"11","sender":"12","msg":"kaka123"}
	 * @return Message object
	 */
	public static MessageObject getMessage(String data) {
		try {
			JSONObject json = new JSONObject(data);
			MessageObject mesObj = getMessage(json);
			return mesObj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get message from server
	 * 
	 * @param data
	 *            {"receiver":"11","sender":"12","msg":"kaka123"}
	 * @return Message object
	 */
	public static MessageObject getMessage(JSONObject json) {
		try {
			String receiver = json.getString("receiver");
			String sender = json.getString("sender");
			String message = json.getString("msg");
			MessageObject mesObj = new MessageObject(receiver, sender, message);
			mesObj.setStamp(ImageCache.isStamp(message));
			
			if (json.has("timestamp")){
				mesObj.setTimestamp(json.getLong("timestamp"));
			} else {
				long time = Utils.getTimeStamp();
				mesObj.setTimestamp(time);
			}
			return mesObj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get message from server (backup)
	 * 
	 * @param data
	 *            {"receiver":"11","sender":"12","msg":"kaka123"}
	 * @return Message object
	 */
	public static MessageObject getMessage1(JSONObject json) {
		try {
			String receiver = json.getString("receiver");
			String sender = json.getString("sender");
			String message = json.getString("msg");
			MessageObject mesObj = new MessageObject(receiver, sender, message);

			String s = ImageCache.subMessage(message);
			if (s!=null){
				mesObj.setStamp(true);
				mesObj.setMessage(s);
			}
			else {
				mesObj.setStamp(false);
			}
			
			if (json.has("timestamp")){
				mesObj.setTimestamp(json.getLong("timestamp"));
			} else {
				long time = Utils.getTimeStamp();
				mesObj.setTimestamp(time);
			}
			return mesObj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param data
	 * @return
	 * @throws JSONException
	 *             Phan tich json du lieu tra ve tu server
	 */

	public ObjFriend getFriend(String data) throws JSONException {
		ObjFriend friends = new ObjFriend();
		JSONObject jsonObject = new JSONObject(data);
		friends.setmIdFriend(jsonObject.getInt("user_id"));
		JSONObject jsonObjectData = jsonObject.getJSONObject("user_data");
		friends.setmBadge(jsonObjectData.getInt("badge"));
		friends.setRestricPublic(jsonObjectData.getBoolean("restric_public"));
		friends.setFriend(jsonObjectData.getBoolean("friend"));
		friends.setMale(jsonObjectData.getBoolean("user_gender"));

		return friends;
	}

	/**
	 * Get code
	 * 
	 */
	public String getCode(String data) throws JSONException {
		JSONObject jsonObject = new JSONObject(data);
		boolean retval = jsonObject.getBoolean("retval");
		String time = jsonObject.getString("timestamp");
		if (retval) {
			return time;
		} else
			return null;

	}

	/**
	 * Get info User [
	 * "{\"retval\":true,\"user_info\":{\"id\":11,\"mobile_num\":\"0923456789\",
	 * \
	 * "uuid\":\"ccf4766507a1b47bbc321fa995687d6a\",\"nick\":\"trungnd\",\"avatar\":\"\",\"gender\":0
	 * , \"itell\":\"i need money!!!!!!!!\
	 * ",\"itell_policy\":1,\"itell_start\":\"2012-08-30T17:29:45.000Z\", \
	 * "itell_status\":1,\"longitude\":\"\",\"latitude\":\"\",\"position_time\":null,\"can_search\":1,\"status\":1
	 * , \
	 * "last_login\":null,\"created\":\"2012-08-24T17:17:51.000Z\",\"modified\":\"2012-08-30T17:29:45.000Z\"}}"
	 * ]
	 */
	public static User getUser(JSONObject json) {
		User user = new User();
		try {
			boolean retval = json.getBoolean("retval");
			if (!retval)
				return null;

			JSONObject jsonChild = json.getJSONObject("user_info");
			user.setUserId(String.valueOf(jsonChild.getInt("id")));
			user.setPhone(jsonChild.getString("mobile_num"));
			user.uuid = jsonChild.getString("uuid");
			user.setUserNick(jsonChild.getString("nick"));
			user.setImageUrl(jsonChild.getString("avatar"));
			user.setMale(jsonChild.getInt("gender"));
			user.setStatus(jsonChild.getString("itell"));
			user.itellPolicy = jsonChild.getInt("itell_policy");
			user.canSearch = jsonChild.getInt("can_search") == 0 ? false : true;
			user.latitude = jsonChild.getDouble("latitude");
			user.longitude = jsonChild.getDouble("longitude");
			return user;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Get info User [
	 * "{\"retval\":true,\"user_info\":{\"id\":11,\"mobile_num\":\"0923456789\",
	 * \
	 * "uuid\":\"ccf4766507a1b47bbc321fa995687d6a\",\"nick\":\"trungnd\",\"avatar\":\"\",\"gender\":0
	 * , \"itell\":\"i need money!!!!!!!!\
	 * ",\"itell_policy\":1,\"itell_start\":\"2012-08-30T17:29:45.000Z\", \
	 * "itell_status\":1,\"longitude\":\"\",\"latitude\":\"\",\"position_time\":null,\"can_search\":1,\"status\":1
	 * , \
	 * "last_login\":null,\"created\":\"2012-08-24T17:17:51.000Z\",\"modified\":\"2012-08-30T17:29:45.000Z\"}}"
	 * ]
	 */
	public static User getUser(String data) throws JSONException {
		JSONObject json = new JSONObject(data);
		return getUser(json);
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static void sortArrayList(ArrayList<MessageObject> ar) {
		Collections.sort(ar);
	}

	public static String convertToString(long time) {
		Calendar c = Calendar.getInstance(Locale.JAPAN);
		c.setTimeInMillis(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
		Date date = c.getTime();
		return dateFormat.format(date);
	}

	// 2012年9月21日 10:00
	public static String convertToString2(long time) {
		Calendar c = Calendar.getInstance(Locale.JAPAN);
		c.setTimeInMillis(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 hh:mm");
		Date date = c.getTime();
		return dateFormat.format(date);
	}

	// 2012年9月21日 10:00
	public static String convertToString2(long time, String format) {
		Calendar c = Calendar.getInstance(Locale.JAPAN);
		c.setTimeInMillis(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 hh:mm");
		Date date = c.getTime();
		return dateFormat.format(date);
	}

	/**
	 * Convert date string to date using format (yyyy-MM-dd HH:mm:ss) in Japan
	 * locale
	 * 
	 * @param date
	 * @param format
	 *            : e.g:Fri, 23 Sep 2010 11:47:13 -0500
	 * @return
	 */
	public static Date convertStringToDate2(String date, String format) {
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.JAPAN);
		Date convertedDate;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
		return convertedDate;
	}

	public static Date convertStringToDate(String date, String format) {
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);//, Locale.JAPAN
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9:00"));
		Date convertedDate;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
		return convertedDate;
	}

	/**
	 * Convert drawable to bitmap
	 * 
	 * @param d
	 * @return
	 */
	public static Bitmap convert2bitmap(Drawable d) {
		try {
			if (d == null) {
				return null;
			}
			Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
			return bitmap;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Check itell updated time is invalid or not
	 * 
	 * @param time
	 * @return
	 */
	public static boolean checkTimeValid(long time) {
		if (time <= 0) {
			return false;
		}
		long current = Calendar.getInstance(Locale.JAPAN).getTimeInMillis();
		if (current < time) {
			return false;
		}
		long dis = (current - time) / 60000;
		if (dis >= Constant.THREE_HOUR) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Hide soft keyboard of android
	 * 
	 * @param activity
	 * @param view
	 */
	public static void hideSoftKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
	}
	
	/**
	 * Show soft keyboard of android
	 * 
	 * @param activity
	 * @param view
	 */
	public static void showSoftKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static void animation(View view, long duration) {
		ScaleAnimation scale = new ScaleAnimation(1.0f, 1.0f, 0.1f, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0.5f);
		scale.setDuration(duration);
		scale.setFillEnabled(true);
		scale.setFillAfter(true);
		view.setAnimation(scale);
	}


	public static long calculate(long timeserver,long time,long timepass){
		long t = timeserver-time;
		long remain=0;
		if (t>=Constant.THREE_HOUR_MILIS){
			remain = 0;
		}
		else {
			t = t -timepass;
			remain = t<=0?Constant.THREE_HOUR_MILIS:Constant.THREE_HOUR_MILIS-t;
		}
		return remain;
	}
	public static boolean checkValidateTime(User user, FriendObject friend){
		long timeFriend  = friend.getFriendStatTime();
		long i = ItelApplication.count.getTimeRemain();
		/*if (i>0){
			i = Utils.calculate(ItelApplication.count.getTimeServer(), timeFriend, 
								ItelApplication.count.getTimePass());
		}*/
		if (i==0){
			return false;
		}
		return true;
	}

	/**
	 * Convert string (format yyyy/mm/dd) to calendar
	 * year have to add 1900
	 * @param s
	 * @return
	 */
	public static Calendar convertStringtoCalendar(String s){
		if ((s==null)||s.trim().equalsIgnoreCase(""))
			return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try{
			date = dateFormat.parse(s);
			Calendar c = Calendar.getInstance();
			c.set(date.getYear()+1900, date.getMonth(), date.getDate());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			return c;
		}
		catch (Exception e) {
		}
		return null;
	}
	
	public static String getAddress(String city,String street,String district){
		String address = "";
		boolean c = false;
		if (street!=null&&(!street.equals(""))&&(!street.equalsIgnoreCase("null"))){
			c = true;
			address +=street;
		}
		if (district!=null&&(!district.equals(""))&&(!district.equalsIgnoreCase("null"))){
			c = true;
			address =address+" "+district; 
		}
		if (city!=null&&(!city.equals(""))&&(!city.equalsIgnoreCase("null"))){
			c = true;
			address =address+" "+city;
		}
		if (!c){
			return null;
		}
		return address;
	}
	public static void showMessage(Context context,String message){
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	

	public static void i(String tag, String message) {
		if (IS_PRODUCTION)
			return;
		Log.i(tag, message);
	}

	public static void d(String tag, String message) {
		if (IS_PRODUCTION)
			return;
		Log.d(tag, message);
	}

	public static void e(String tag, String message) {
		if (IS_PRODUCTION)
			return;
		Log.e(tag, message);
	}

	public static  void httppost(String url, HttpCallback callback, String... params) {
		if (params.length % 2 != 0)
			throw new IllegalArgumentException("number of params must be even");
		HttpClient httpclient = new DefaultHttpClient();
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (int i = 0; i < params.length / 2; i++) {
				nvps.add(new BasicNameValuePair(params[2 * i],
						params[2 * i + 1]));
			}

			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					HTTP.UTF_8, false));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpclient.execute(httpPost);
			d("hieuth", "http post: "+params[0]);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					String result = convertStreamToString(instream);
					instream.close();
					if (callback!=null){
						callback.run(result);
					}
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}

	private static String convertStreamToString(InputStream is) {
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
				sb.append(line);
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return sb.toString();
	}
	
	public interface HttpCallback{
		public void run(String str);
	}
	
	
	 public static Bitmap scaleImage(Bitmap bmp,Activity ac){
	    	int width = ac.getWindowManager().getDefaultDisplay().getWidth();
	    	int hScreen = ac.getWindowManager().getDefaultDisplay().getHeight();
	    	
	    	double d = (double)bmp.getHeight()/bmp.getWidth();
	    	double ratio = (double)hScreen/width; //((double)2)/3;
	    	if (d>ratio){
	    		bmp = Bitmap.createScaledBitmap(bmp, width, (int)(width*d), true);
	    		int h = bmp.getHeight();
	    		int height =(int) (width*ratio);//2*width/3;
	    		int delta = h - height;
	    		bmp = Bitmap.createBitmap(bmp, 0, delta/2, width, height);
	    	}
	    	else{
	    		bmp = Bitmap.createScaledBitmap(bmp,(int) (2*width/(3*d)), (int)(width*2/3), true);
	    		int height =(int) (ratio*width);//2*width/3;
	    		int w = bmp.getWidth();
	    		int delta = w - width;
	    		bmp = Bitmap.createBitmap(bmp, delta/2, 0, width, height);
	    	}
	    	return bmp;
	    	
	    }
	
	/**
	 * Check size of bitmap is more than 2M. If true, compress it. 
	 * After that, convert bitmap to byte array.
	 * @param bitmap
	 * @return
	 */
	public static byte[] reduceSize(Bitmap bitmap) {
		byte[] byteArray = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		//convert image to byte array
		if (bitmap != null) {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
			//Check size>=2M
			while (outputStream.size() >= 2*1024*1024) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
			}
			
			byteArray = outputStream.toByteArray();
		}
		return byteArray;
	}
	
	public static int convertDip2Pixel(float v,Context context){
		// returns the number of pixels for 123.4dip
		/*int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
		                     (float) 123.4, context.getResources().getDisplayMetrics());*/
		int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                v, context.getResources().getDisplayMetrics());
		return value;
	}
	
	/**
	 * 
	 * @param userID
	 * @param uuid
	 * @param usersID user ids list, get from array json [11,12,13]
	 * @return
	 */
	public static ArrayList<FriendObject> getMultiProfile(String userID,String uuid,String userIDs){
		HttpClient httpclient = new DefaultHttpClient();

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", userID));
			params.add(new BasicNameValuePair("uuid", uuid));
			params.add(new BasicNameValuePair("friend_ids", userIDs));
			String query = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(new URI(urlGetUserProfiles+ query));
			httpGet.addHeader(BasicScheme.authenticate(
					new UsernamePasswordCredentials(AUTHEN_USER, AUTHEN_PASS),
					HTTP.UTF_8, false));
			HttpResponse response = httpclient.execute(httpGet);
			InputStream is = null;
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				// read response data
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, HTTP.ISO_8859_1), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					// get friends array
					ArrayList<FriendObject> arr = geProfileData(sb.toString());
					return arr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean validateDate(String s){
		//String s = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		//String regEx ="^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d{2}$";
		//String regEx ="(19|20)\\d{2}$[- /.]^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])";
		String regEx = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		Matcher matcherObj = Pattern.compile(regEx).matcher(s);
		if (matcherObj.matches()){
            return true;
        }
        else{
            return false;
        }
	}
	/**
	 * @param data
	 * "retval":true,"user_data":[{"User":{"id":"11","nick":"nickey","mobile_num":"46456456456",
	 * "avatar":"","gender":false,"itell":"2222","itell_policy":"1","itell_start":"2012-10-07 18:58:16",
	 * "can_search":true,"hide_age":false,"longitude":"105.843002","latitude":"21.007000",
	 * "street":"","strict":"","city":"","befriend":false,"invite":false}}
	 */
	private static ArrayList<FriendObject> geProfileData(String data) {
		try {
			ArrayList<FriendObject> arr = new ArrayList<FriendObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			boolean retval = jsonParentObj.getBoolean("retval");
			if (!retval) {
				ItelApplication.err_code = jsonParentObj.getInt("error_code");
				ItelApplication.err_message = jsonParentObj
						.getString("error_msg");
				return null;
			}

			// Get friends
			JSONArray jsonAr = jsonParentObj.getJSONArray("user_data");
			JSONObject jsonObj1;
			FriendObject friend;
			int l;
			if (jsonAr != null) {
				l = jsonAr.length();
				for (int i = 0; i < l; i++) {
					jsonObj1 = jsonAr.optJSONObject(i);
					JSONObject jsonObj = jsonObj1.getJSONObject("User");
					friend = new FriendObject();
					friend.setFriend(true);
					friend.setFriendID(jsonObj.getInt("id"));
					friend.setFriendNick(jsonObj.getString("nick"));
					friend.setFriendAva(jsonObj.getString("avatar"));
					friend.setFriendiTell(jsonObj.getString("itell"));
					friend.setFriendLong(jsonObj.getDouble("longitude"));
					friend.setFriendLat(jsonObj.getDouble("latitude"));
					friend.setMale(!jsonObj.getBoolean("gender"));
					friend.setFriendStatTime(jsonObj
							.getString("itell_start"));
					//address
					String street = null;
					String district = null;
					String city = null;
					if (jsonObj.has("street")) {
						street = jsonObj.optString("street");
					}
					if (jsonObj.has("strict")) {
						district = jsonObj.optString("strict");
					}
					if (jsonObj.has("city")) {
						city = jsonObj.optString("city");
					}
					String address = getAddress(city, street, district);
					friend.setFriendAddress(address);
					
					arr.add(friend);
				}
			}

			return arr;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get resource from drawable name
	 * @param context
	 * @param drawableName
	 * @return
	 */
	public static int getIDResource(Context context,String drawableName ){
		return context.getResources().getIdentifier(drawableName , "drawable", context.getPackageName());
	}
	
	/**
	 * Get resource from asset
	 * @param nameFile
	 */
	public static ArrayList<StampObject> loadResource(String nameFile,Context ctx){
		
		// read response data
		try {
			InputStream is = ctx.getResources().getAssets().open(nameFile);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, HTTP.ISO_8859_1), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			String data = sb.toString();
			return getStamp(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ArrayList<StampObject> getStamp(String data){
		try {
			ArrayList<StampObject> arr = new ArrayList<StampObject>();
			JSONObject jsonParentObj = new JSONObject(data);
			JSONObject jsonObj;
			// Get friends
			if (jsonParentObj.has("stamps")) {
				JSONArray jsonAr = jsonParentObj.getJSONArray("stamps");
				if (jsonAr != null) {
					int l = jsonAr.length();
					for (int i = 0; i < l; i++) {
						jsonObj = jsonAr.optJSONObject(i);
						StampObject stamp = new StampObject();
						
						stamp.setId(jsonObj.getString("id"));
						stamp.setCode(jsonObj.getString("code"));
						stamp.setFree(jsonObj.getBoolean("free"));
						stamp.setFromNative(jsonObj.getBoolean("inClient"));
						stamp.setImageLink(jsonObj.getString("path"));
						ImageCache.putStampInNewCache(stamp);
						arr.add(stamp);
					}
				}
			}
			return arr;
		}
		catch(JSONException e){}
		return null;
	}
	
	/**
	 * Get resource from asset
	 * @param nameFile
	 */
	public static ArrayList<StampCatObject> loadResource1(String nameFile,Context ctx){
		
		// read response data
		try {
			InputStream is = ctx.getResources().getAssets().open(nameFile);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, HTTP.ISO_8859_1), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			String data = sb.toString();
			return getCatStamp(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ArrayList<StampCatObject> getCatStamp(String data){
		try {
			JSONObject jsonSuperParentObj = new JSONObject(data);
			JSONObject jsonObj;
			int number = jsonSuperParentObj.getInt("number");
			ArrayList<StampCatObject> arr = new ArrayList<StampCatObject>(number);
			if (jsonSuperParentObj.has("cats")) {
				JSONArray jsonParentArr = jsonSuperParentObj.getJSONArray("cats");
				int length = jsonParentArr.length();
				for (int n = 0;n<length;n++){
					JSONObject jsonParentObj = jsonParentArr.optJSONObject(n);
					StampCatObject cat = new StampCatObject();
					cat.setId(jsonParentObj.getString("id"));
					cat.setFree(jsonParentObj.getBoolean("free"));
					cat.setFromNative(jsonParentObj.getBoolean("from_client"));
					cat.setImageActive(jsonParentObj.getString("image_active"));
					cat.setImageInactive(jsonParentObj.getString("image_inactive"));
					cat.setNumberStamp(jsonParentObj.getInt("number_stamp"));
					// Get stamps
					if (jsonParentObj.has("stamps")) {
						JSONArray jsonAr = jsonParentObj.getJSONArray("stamps");
						if (jsonAr != null) {
							int l = jsonAr.length();
							ArrayList<StampObject> stampArr = new ArrayList<StampObject>(l);
							for (int i = 0; i < l; i++) {
								jsonObj = jsonAr.optJSONObject(i);
								StampObject stamp = new StampObject();
								stamp.setId(jsonObj.getString("id"));
								stamp.setCatId(cat.getId());
								stamp.setCode(jsonObj.getString("code"));
								stamp.setFree(cat.isFree());
								stamp.setFromNative(cat.isFromNative());
								stamp.setImageLink(jsonObj.getString("image"));
								ImageCache.putStampInNewCache(stamp);
								stampArr.add(stamp);
							}
							cat.setArrStamps(stampArr);
						}
					}
					arr.add(cat);
					ImageCache.putCategoryStampInCache(cat);
				}
			}
			return arr;
		}
		catch(JSONException e){}
		return null;
	}
	
	/**
	 * Get image from resource based on gender
	 * @param isMale
	 * @param context
	 * @return
	 */
	public static Bitmap getAvatarImage(boolean isMale,Context context){
		Bitmap b = null;
		if (isMale) {
			Drawable d = context.getResources().getDrawable(
					R.drawable.avatar_male_default);
			
			b = Utils.convert2bitmap(d);
		}
		else {
			Drawable d = context.getResources().getDrawable(
					R.drawable.avatar_female_default);
			b = Utils.convert2bitmap(d);
		}
		if (b==null){
			Drawable draw = context.getResources().getDrawable(R.drawable.icon);
			b = Utils.convert2bitmap(draw);
		}
		return b;
	}
}
