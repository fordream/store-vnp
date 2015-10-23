package com.icts.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.icts.app.ItelApplication;
import com.icts.object.FriendObject;
import com.icts.object.ObjFriend;
import com.icts.object.ObjFriendInvite;
import com.icts.object.User;
import com.icts.utils.CheckWifi;
import com.icts.utils.ThreadLoadData;
import com.icts.utils.Utils;

public class JsonAnalysis {
	private Context mContext;
	private CheckWifi mCheckWifi = new CheckWifi();

	private final static String AUTHEN_USER = "itell";
	private final static String AUTHEN_PASS = "itell@2012";

	/**
	 * Add user_id para e.g:
	 * http://49.212.140.145/itell/profile/get_profile?user_id=12
	 */
	private String urlJsonUser = "http://49.212.140.145/itell/profile/get_profile?user_id=12";
	private String urlInviteFriend = "http://49.212.140.145/itell/profile/send_invite";
	private String urlDeleteFriend = "http://49.212.140.145/itell/profile/delete_friend";
	/**
	 * http://49.212.140.145/itell/friend/get_list_friend?user_id=&uuid=
	 */
	private static String urlGetfriendlist = "http://49.212.140.145/itell/friend/get_list_friend?";

	// Map
	/**
	 * Add para user_id, longtitude, latitude district city timestamp
	 */
	private String urlPostLocation = "http://49.212.140.145/itell/map/post_position?";

	/**
	 * Add para user_id, longtitude, latitude, timestamp
	 */
	private static String urlGetMapData = "http://49.212.140.145/itell/map/get_map_data?";

	public JsonAnalysis(Context context) {
		mContext = context;
	}

	/**
	 * 
	 * 
	 * @param url
	 * @param handler
	 *            Ham thuc hien load data, truoc khi load co check internet
	 */
	//
	public void executeLoadData(String url, Handler handler, Context context,
			List<NameValuePair> nameValuePairs) {
		Log.e("", "11111111111111");
		if (mCheckWifi.check(context) == -1) {
			Utils.showDialogErorr(context, "NETWORK ERROR");
		} else {
			ThreadLoadData thread = new ThreadLoadData(handler, url, context,
					nameValuePairs);
			thread.start();
		}

	}

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
		// Thread t = new Thread() {
		// public void run() {
		// Looper.prepare();
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
		// Looper.loop();
		// }
		// };
		// t.start();
	}

	public static ArrayList<FriendObject> getMapData(String user_id,
			double lat, double lng) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			long time = getTimeStamp();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", user_id));
			params.add(new BasicNameValuePair("latitude", String.valueOf(lat)));
			params.add(new BasicNameValuePair("longitude", String.valueOf(lng)));
			params.add(new BasicNameValuePair("timestamp", String.valueOf(time)));

			String query = URLEncodedUtils.format(params, "utf-8");
			HttpGet httpGet = new HttpGet(new URI(urlGetMapData + query));
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
					ArrayList<FriendObject> arr = getDataUser(sb.toString());
					System.out.println("reponse: "
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

	public static ArrayList<FriendObject> getDataUser(String data) {
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
					friend.setFriendID(jsonObj.getInt("user_id"));
					friend.setFriendNick(jsonObj.getString("user_name"));
					friend.setFriendAva(jsonObj.getString("user_avatar"));
					friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
							+ "img34/benfica_avatar_picture_95165.png");
					friend.setFriendLong(jsonObj.getDouble("user_longitude"));
					friend.setFriendLat(jsonObj.getDouble("user_latitude"));
					if (jsonObj.getInt("user_gender") == 0) {
						friend.setMale(true);
					} else {
						friend.setMale(false);
					}
					friend.setFriendiTell(jsonObj.getString("user_itell"));
					friend.setFriendStatTime(jsonObj
							.getLong("user_itell_start_time"));
					Log.i("NDT", "friend_id " + jsonObj.getInt("friend_id"));
					Log.i("NDT",
							"friend_nick " + jsonObj.getString("friend_nick"));
					arr.add(friend);
				}
			}

			// Get Other
			jsonAr = jsonParentObj.getJSONArray("other_data");
			if (jsonAr != null) {
				l = jsonAr.length();
				for (int i = 0; i < l; i++) {
					jsonObj = jsonAr.optJSONObject(i);
					friend = new FriendObject();
					friend.setFriend(false);
					friend.setFriendID(jsonObj.getInt("user_id"));
					friend.setFriendNick(jsonObj.getString("user_name"));
					friend.setFriendAva(jsonObj.getString("user_avatar"));
					friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
							+ "img34/benfica_avatar_picture_95165.png");
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
					Log.i("NDT", "friend_id " + jsonObj.getInt("friend_id"));
					Log.i("NDT",
							"friend_nick " + jsonObj.getString("friend_nick"));
					arr.add(friend);
				}
			}
			return arr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static long getTimeStamp() {
		Calendar c = Calendar.getInstance(Locale.JAPAN);
		return c.getTimeInMillis();
	}

	/**
	 * 
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
					friend.setFriendiTell("friend_avatar");
					friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
							+ "img34/benfica_avatar_picture_95165.png");
					friend.setFriendLat(lat + 0.001 * (i + 1));
					friend.setFriendLong(lng + 0.001 * (i + 1));
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
						friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
								+ "img34/benfica_avatar_picture_95165.png");
						friend.setFriendLat(lat + 0.001 * (i + 1));
						friend.setFriendLong(lng + 0.001 * (i + 1));
						Log.i("NDT", "friend_id " + jsonObj.getInt("friend_id"));
						Log.i("NDT",
								"friend_nick "
										+ jsonObj.getString("friend_nick"));
						arr.add(friend);
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
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
	 * 
	 * Get code
	 * 
	 */
	public String getCode(String data) throws JSONException {
		JSONObject jsonObject = new JSONObject(data);
		boolean retval = jsonObject.getBoolean("retval");
		String time = jsonObject.getString("timestamp");
		ItelApplication.timeStamp = Long.parseLong(time);
		if (retval) {
			return time;
		} else
			return null;

	}

	/*
	 * 
	 * Get info User
	 */
	public User getUser(String data) throws JSONException {
		User user = new User();
		JSONObject jsonObject = new JSONObject(data);
		user.setmTimeCurrent(jsonObject.getLong("timestamp"));
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
			Log.e("", "time start ==" + (dateStart.getTime()));
			user.setStatusUpdateTime(dateStart.getTime());
		}

		return user;
	}

	public ArrayList<FriendObject> getListFriend(String data)
			throws JSONException {
		ArrayList<FriendObject> arrayList = new ArrayList<FriendObject>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("friend_data");
		for (int i = 0; i < jsonArray.length(); ++i) {
			Log.e("", "ii==" + i);
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			FriendObject friend = new FriendObject();
			friend.setFriend(true);
			friend.setFriendID(jsonObj.getInt("friend_id"));
			friend.setFriendNick(jsonObj.getString("friend_nick"));
			friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
			friend.setFriendAva(jsonObj.getString("friend_avatar"));
		//	friend.setFriendiTell("friend_avatar");
		//	friend.setFriendAva("http://avatar.hq-picture.com/avatars/"
		//					+ "img34/benfica_avatar_picture_95165.png");
			arrayList.add(friend);
		}
		return arrayList;
	}

	public ArrayList<FriendObject> getListFriendSearch(String data)
			throws JSONException {
		ArrayList<FriendObject> arrayList = new ArrayList<FriendObject>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("friend_data");
		for (int i = 0; i < jsonArray.length(); ++i) {
			Log.e("", "ii==" + i);
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			FriendObject friend = new FriendObject();
			friend.setFriend(true);
			friend.setFriendID(jsonObj.getInt("id"));
			friend.setFriendNick(jsonObj.getString("nick"));
			// friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
			// friend.setFriendAva(jsonObj.getString("friend_avatar"));
			// friend.setFriendiTell("friend_avatar");
			friend.setFriendAva(jsonObj.getString("avatar"));
			arrayList.add(friend);
		}
		return arrayList;
	}

	public ArrayList<FriendObject> getListFriendFromContact(String data)
			throws JSONException {
		ArrayList<FriendObject> arrayList = new ArrayList<FriendObject>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("contacts");
		for (int i = 0; i < jsonArray.length(); ++i) {
			Log.e("", "ii==" + i);
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			FriendObject friend = new FriendObject();
			friend.setFriend(true);
			friend.setFriendID(jsonObj.getInt("id"));
			friend.setFriendNick(jsonObj.getString("nick"));
			// friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
			// friend.setFriendAva(jsonObj.getString("friend_avatar"));
			// friend.setFriendiTell("friend_avatar");
			friend.setFriendAva(jsonObj.getString("avatar"));
			arrayList.add(friend);
		}
		return arrayList;
	}

	public ArrayList<FriendObject> getListFriendInvite(String data)
			throws JSONException {
		ArrayList<FriendObject> arrayList = new ArrayList<FriendObject>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("invite_data");
		for (int i = 0; i < jsonArray.length(); ++i) {
			Log.e("", "ii==" + i);
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			FriendObject friend = new FriendObject();
			// friend.setFriend(true);
			// friend.setFriendID(jsonObj.getInt("friend_id"));
			// friend.setFriendNick(jsonObj.getString("friend_nick"));
			// friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
			// friend.setFriendAva(jsonObj.getString("friend_avatar"));
			// friend.setFriendiTell("friend_avatar");
			// friend.setFriendAva(jsonObj.getString("avatar"));
			// arrayList.add(friend);
		}
		return arrayList;
	}

	public ArrayList<ObjFriendInvite> getListFriendInviteUser(String data)
			throws JSONException {
		ArrayList<ObjFriendInvite> arrayList = new ArrayList<ObjFriendInvite>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("invite_data");
		for (int i = 0; i < jsonArray.length(); ++i) {
			Log.e("", "ii==" + i);
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			ObjFriendInvite friend = new ObjFriendInvite();
			friend.setmFriendID(jsonObj.getString("friend_id"));
			friend.setmFriendNick(jsonObj.getString("friend_nick"));
			// friend.setRestrict_pub(jsonObj.getBoolean("restrict_pub"));
			friend.setmFriendAvatar(jsonObj.getString("friend_avatar"));
			friend.setmInviteId(jsonObj.getString("invite_id"));
			arrayList.add(friend);
		}
		return arrayList;
	}
	
	
	
	

	public ArrayList<FriendObject> getListIsFriendContact(String data,
			ArrayList<FriendObject> arrayList) throws JSONException {
		// ArrayList<FriendObject> arrayList = new ArrayList<FriendObject>();
		JSONObject jsonObject = new JSONObject(data);
		JSONArray jsonArray = jsonObject.getJSONArray("contacts");
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			FriendObject contact = new FriendObject();
			contact.setFriendID(jsonObj.getInt("id"));
			contact.setFriendNick(jsonObj.getString("name"));
			contact.setmNumberMobile(jsonObj.getString("mobile_num"));
			contact.setmMail(jsonObj.getString("email"));
			// contact.setFriendAva(jsonObj.getString("avatar"));
			arrayList.add(contact);
		}

		return arrayList;

	}

	public String getCodeReturn(String data) throws JSONException {
		JSONObject jsonObject = new JSONObject(data);
		boolean flag = jsonObject.getBoolean("retval");
		String s = jsonObject.getString("email");
		if (flag)
			return s;
		else
			return "";
	}
	
	public String getCodeReturnInvite(String data) throws JSONException {
		JSONObject jsonObject = new JSONObject(data);
		boolean flag = jsonObject.getBoolean("retval");
		String s = jsonObject.getString("friend_id");
		if (flag)
			return s;
		else
			return "";
	}

}
