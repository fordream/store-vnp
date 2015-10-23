package com.icts.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.icts.itel.R;

import android.app.Activity;
import android.provider.Settings.Secure;

public class Constant {
	public static final String INVITE = "invite";
	public static final String API_MAP_KEY = "0jVjLo-RWwGlWNl72HroGg2QKZTz7uSU6dQXfuw";
	public static final int[] ALL_ITELL_BUTTON_IMAGES = new int[]{
		R.drawable.menu_itell_button_1,R.drawable.menu_itell_button_2,R.drawable.menu_itell_button_3,R.drawable.menu_itell_button_4,
		R.drawable.menu_itell_button_5,R.drawable.menu_itell_button_6,R.drawable.menu_itell_button_7,R.drawable.menu_itell_button_8,
		R.drawable.menu_itell_button_9,R.drawable.menu_itell_button_10,R.drawable.menu_itell_button_11,R.drawable.menu_itell_button_12,
		R.drawable.menu_itell_button_13,R.drawable.menu_itell_button_14,R.drawable.menu_itell_button_15,R.drawable.menu_itell_button_16,
		R.drawable.menu_itell_button_17,R.drawable.menu_itell_button_18,R.drawable.menu_itell_button_19,R.drawable.menu_itell_button_20,
		R.drawable.menu_itell_button_21,R.drawable.menu_itell_button_22,R.drawable.menu_itell_button_23,R.drawable.menu_itell_button_24,
		R.drawable.menu_itell_button_25,R.drawable.menu_itell_button_26,R.drawable.menu_itell_button_27,R.drawable.menu_itell_button_28,
		R.drawable.menu_itell_button_29,R.drawable.menu_itell_button_30
	}; 
	
	public static final int[] FRIEND_ITELL_BUTTON_IMAGES = new int[]{
		R.drawable.menu_itell_button_friend0,R.drawable.menu_itell_button_friend1,R.drawable.menu_itell_button_friend2,R.drawable.menu_itell_button_friend3,R.drawable.menu_itell_button_friend4,
		R.drawable.menu_itell_button_friend5,R.drawable.menu_itell_button_friend6,R.drawable.menu_itell_button_friend7,R.drawable.menu_itell_button_friend8,
		R.drawable.menu_itell_button_friend9,R.drawable.menu_itell_button_friend10,R.drawable.menu_itell_button_friend11,R.drawable.menu_itell_button_friend12,
		R.drawable.menu_itell_button_friend14,R.drawable.menu_itell_button_friend15,R.drawable.menu_itell_button_friend16,
		R.drawable.menu_itell_button_friend17,R.drawable.menu_itell_button_friend18,R.drawable.menu_itell_button_friend19,R.drawable.menu_itell_button_friend20,
		R.drawable.menu_itell_button_friend21,R.drawable.menu_itell_button_friend22,R.drawable.menu_itell_button_friend23,R.drawable.menu_itell_button_friend24,
		R.drawable.menu_itell_button_friend25,R.drawable.menu_itell_button_friend26,R.drawable.menu_itell_button_friend27,R.drawable.menu_itell_button_friend28,
		R.drawable.menu_itell_button_friend29,R.drawable.menu_itell_button_friend30
	}; 
	public static final int[] OTHER_ITELL_BUTTON_IMAGES = new int[]{
		R.drawable.menu_itell_button_other1,R.drawable.menu_itell_button_other2,R.drawable.menu_itell_button_other3,R.drawable.menu_itell_button_other4,
		R.drawable.menu_itell_button_other5,R.drawable.menu_itell_button_other6,R.drawable.menu_itell_button_other7,R.drawable.menu_itell_button_other8,
		R.drawable.menu_itell_button_other9,R.drawable.menu_itell_button_other10,R.drawable.menu_itell_button_other11,R.drawable.menu_itell_button_other12,
		R.drawable.menu_itell_button_other13,R.drawable.menu_itell_button_other14,R.drawable.menu_itell_button_other15,R.drawable.menu_itell_button_other16,
		R.drawable.menu_itell_button_other17,R.drawable.menu_itell_button_other18,R.drawable.menu_itell_button_other19,R.drawable.menu_itell_button_other20,
		R.drawable.menu_itell_button_other21,R.drawable.menu_itell_button_other22,R.drawable.menu_itell_button_other23,R.drawable.menu_itell_button_other24,
		R.drawable.menu_itell_button_other25,R.drawable.menu_itell_button_other26,R.drawable.menu_itell_button_other27,R.drawable.menu_itell_button_other28,
		R.drawable.menu_itell_button_other29,R.drawable.menu_itell_button_other30
	}; 
	//CHAT STAMP
	public static final int NUMBER_STAMP_IN_PAGE = 6;
	public static final String STAMP_TAG = "@@!!";
	public static final int CHECK_SUCCESS = 0;
	public static final int CHECK_NETWORK_ERROR = 2;
	public static final int CHECK_DATA_INVALID = 3;
	public static final int CHECK_USER_NOT_FOUND = 4;
	public static final String CHAT_VIEW = "Chat";
	public static final String LIST_VIEW = "List";
	public static final String PROFILE_VIEW = "Profile";
	public static final String MAP_VIEW = "Map";
	public static final String SETTING_VIEW = "Setting";
	public static final String FRIEND_VIEW = "Friend";
	public static final String ITELL_VIEW = "Itell";
	public static final int THREE_HOUR = 180;
	public static final int THREE_HOUR_MILIS = 10800000;
	public static enum ViewMode {REGISTER,TOP,CHAT,CHAT_LIST,MAP,MAP_LIST,FRIEND,ITELL,SETTING,PROFILE,NULL};
	
	// ButtonItell
	public static final long INTERVAL_ITELL_BUTTON = 3000;
	
	//Map
	public static final int MAX_MATCHES = 100;
	public static final int MAP_DISTANCE = 10000;
	
	//Gender
	public static final int GENDER_MALE = 0;
	public static final int GENDER_FEMALE = 1;
	
	//Policy
	public static final int POLICY_ALL = 1;
	public static final int POLICY_FRIEND = 2;
	public static final int POLICY_OTHER = 3;
	
	public static final int IO_BUFFER_SIZE = 8 * 1024;
    public final static int MAX_EXEC_THREAD = 5;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static String ANDROID_ID;
    // setting
	public final static String ITELL_USERNAME_AUTHEN = "itell";
	public final static String ITELL_PASS_AUTHEN = "itell@2012";
	public final static int REQUEST_PHOTO_SELECT = 3;
	public final static int REQUEST_CAMERA_TAKE = 4;
	
	/**
	 * user_id=12&uuid=26234213123d6f182a46e615ac0d8a55&friend_id=12
	 */
	public static String SETTING_GET_PROFILE_NEW = "http://49.212.140.145/itell/profile/get_profile?" +
			"user_id=%s&uuid=%s&friend_id=%s";
	
	public static String SETTING_GET_PROFILE = "http://49.212.140.145/itell/profile/get_profile?user_id=";
	public final static String DOMAIN = "http://49.212.140.145/itell/";
	public final static String SETTING_SEND_INVITE_FRIEND = "http://49.212.140.145/itell/profile/send_invite";
	public final static String SETTING_DELETE_FRIEND = "http://49.212.140.145/itell/profile/delete_friend";
	public final static String SETTING_UPLOAD_AVATAR = "http://49.212.140.145/itell/setting/upload_avatar";
//	http://49.212.140.145/itell/setting/get_album?user_id=12&uuid=26234213123d6f182a46e615ac0d8a55
	public final static String  GET_ALBUM= "http://49.212.140.145/itell/setting/get_album?user_id=";
	public final static String DELETE_AVATAR = DOMAIN + "setting/delete_avatar";
	public final static String BLOCK_LIST = DOMAIN + "setting/get_block_list?user_id=";
	public final static String SETTING_UPLOAD_IMAGE = "http://49.212.140.145/itell/setting/upload_img";
	public final static String uuid = "&uuid=";
	
	// setting for GCM
	public final static String SENDER_ID = "974054509921"; // Account of clich.ink url = https://code.google.com/apis/console/?pli=1#project:251723413194
	public final static String PUSH_MESSAGE = "message";
	public final static String PUSH_ID = "id";
	//setting
    private static String getAndroidid(Activity ac) {
        String s = Secure.getString(ac.getContentResolver(), Secure.ANDROID_ID);
        return s;
    }

    /**
     * Get MD5 of string
     * 
     * @param timestamp
     * @return
     */
    public static String md5Digest(final String timestamp, Activity ac) {
        try {
            // get android id
            ANDROID_ID = getAndroidid(ac);
            String text = ANDROID_ID + timestamp.trim();
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(text.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            int messageDigestLenght = messageDigest.length;
            for (int i = 0; i < messageDigestLenght; i++) {
                String hashedData = Integer
                        .toHexString(0xFF & messageDigest[i]);
                while (hashedData.length() < 2) {
                    hashedData = "0" + hashedData;
                }
                hexString.append(hashedData);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ""; // if text is null then return nothing
    }
    /**
   	 * jsonObject get
   	 * 
   	 * @param url
   	 * @return
   	 */
   	public static JSONObject getJSONfromURL(String url) {
   		// initialize
   		InputStream is = null;
   		String result = "";
   		JSONObject jObject = null;
   		try {
   			HttpParams httpParams = new BasicHttpParams();
   			// HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
   			HttpClient httpClient = new DefaultHttpClient(httpParams);
   			// use and pp
   			HttpGet httpGet = new HttpGet(url);
   			// add autheticate if co'
   			httpGet.addHeader(BasicScheme.authenticate(
   					new UsernamePasswordCredentials(ITELL_USERNAME_AUTHEN, ITELL_PASS_AUTHEN),
   					"UTF-8", false));
   			HttpResponse response = httpClient.execute(httpGet);
   			if (response.getStatusLine().getStatusCode() == 200) {
   				HttpEntity entity = response.getEntity();
   				is = entity.getContent();
   			}
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		// convert response to string
   		try {
   			BufferedReader reader = new BufferedReader(new InputStreamReader(
   					is, "iso-8859-1"), 8);
   			StringBuilder sb = new StringBuilder();
   			String line = null;
   			while ((line = reader.readLine()) != null) {
   				sb.append(line + "\n");
   			}
   			is.close();
   			result = sb.toString();
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
   		// try parse the string to a JSON object
   		try {
   			jObject = new JSONObject(result);
   		} catch (JSONException e) {
   		}
   		return jObject;
   	}
}
