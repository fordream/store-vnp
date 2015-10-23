package com.gmail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class GmailGender {
	private static final String GMAIL_STORE = "GMAIL_STORE";
	private Context context;
	private ProgressDialog progressDialog;
	private SharedPreferences preferences;
	private final static int TIMEOUT = 3000;
	private CallBack callBack;

	public GmailGender(Context context) {
		this.context = context;
		preferences = this.context.getSharedPreferences(GMAIL_STORE, 0);
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	@SuppressLint("NewApi")
	public void authent(String user, String pass) {
		progressDialog = ProgressDialog.show(context, null, "login...");
		new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... params) {
				String userName = params[0];
				String pass = params[1];
				try {
					String authKey = getGoogleAuthKey(userName, pass);
					String googleToken = getGoogleToken(authKey);
					String userInfor = getUserInfo(authKey);
					String IdUser = getGoogleUserID(userInfor);

					preferences.edit().putString("user", userName).commit();
					preferences.edit().putString("pass", pass).commit();
					preferences.edit().putString("authKey", authKey).commit();
					preferences.edit().putString("googleToken", googleToken).commit();
					preferences.edit().putString("userInfor", userInfor).commit();
					preferences.edit().putString("IdUser", IdUser).commit();
				} catch (Exception e) {
					return e.getMessage();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (result != null) {
					callBack.error(result);
				} else {
					callBack.sucess();
				}
				
				progressDialog.dismiss();
				progressDialog = null;
			};
		}.execute(user, pass);
	}

	public String getUser() {
		return preferences.getString("user", "");
	}

	public String getPass() {
		return preferences.getString("pass", "");
	}

	public String getAuthKey() {
		return preferences.getString("authKey", "");
	}

	public String getGoogleToken() {
		return preferences.getString("googleToken", "");
	}

	public String getUserInforr() {
		return preferences.getString("userInfor", "");
	}

	public String getIdUser() {
		return preferences.getString("IdUser", "");
	}

	public void relogin() {

	}

	public void clear() {
		preferences.edit().clear().commit();
	}

	// -----------------------------
	private static final String _AUTHPARAMS = "GoogleLogin auth=";
	private static final String _GOOGLE_LOGIN_URL = "https://www.google.com/accounts/ClientLogin";
	private static final String _READER_BASE_URL = "http://www.google.com/reader/";
	private static final String _API_URL = _READER_BASE_URL + "api/0/";
	private static final String _TOKEN_URL = _API_URL + "token";
	private static final String _USER_INFO_URL = _API_URL + "user-info";
	private static final String _USER_LABEL = "user/-/label/";
	private static final String _TAG_LIST_URL = _API_URL + "tag/list";
	private static final String _EDIT_TAG_URL = _API_URL + "tag/edit";
	private static final String _RENAME_TAG_URL = _API_URL + "rename-tag";
	private static final String _DISABLE_TAG_URL = _API_URL + "disable-tag";
	private static final String _SUBSCRIPTION_URL = _API_URL
			+ "subscription/edit";
	private static final String _SUBSCRIPTION_LIST_URL = _API_URL
			+ "subscription/list";

	private String getGoogleAuthKey(String _USERNAME, String _PASSWORD)
			throws UnsupportedEncodingException, IOException {
		Document doc = Jsoup
				.connect(_GOOGLE_LOGIN_URL)
				.data("accountType", "GOOGLE", "Email", _USERNAME, "Passwd",
						_PASSWORD, "service", "reader", "source",
						"&lt;your app name&gt;")
				.userAgent("&lt;your app name&gt;").timeout(TIMEOUT).post();

		// RETRIEVES THE RESPONSE TEXT inc SID and AUTH. We only want the AUTH
		// key.
		String _AUTHKEY = doc
				.body()
				.text()
				.substring(doc.body().text().indexOf("Auth="),
						doc.body().text().length());
		_AUTHKEY = _AUTHKEY.replace("Auth=", "");
		return _AUTHKEY;
	}

	private String getGoogleToken(String authKey)
			throws UnsupportedEncodingException, IOException {
		Document doc = Jsoup.connect(_TOKEN_URL)
				.header("Authorization", _AUTHPARAMS + authKey)
				.userAgent("&lt;your app name").timeout(TIMEOUT).get();
		// RETRIEVES THE RESPONSE TOKEN
		String _TOKEN = doc.body().text();
		return _TOKEN;
	}

	private String getUserInfo(String authKey)
			throws UnsupportedEncodingException, IOException {
		Document doc = Jsoup.connect(_USER_INFO_URL)
				.header("Authorization", _AUTHPARAMS + authKey)
				.userAgent("&lt;your app name&gt;").timeout(TIMEOUT).get();

		// RETRIEVES THE RESPONSE USERINFO
		String _USERINFO = doc.body().text();
		return _USERINFO;
	}

	private String getGoogleUserID(String userInfor)
			throws UnsupportedEncodingException, IOException {
		/*
		 * USERINFO RETURNED LOOKS LIKE {"userId":"14577161871823252783",
		 * "userName"
		 * :"&lt;username&gt;","userProfileId":"&lt;21 numeric numbers",
		 * "userEmail":"&lt;username&gt;@gmail.com", "isBloggerUser":true,
		 * "signupTimeSec":1159535065}
		 */
		String _USERINFO = userInfor;
		String _USERID = (String) _USERINFO.subSequence(11, 31);
		return _USERID;
	}

	//
	// public String getGoogleToken(String _USERNAME, String _PASSWORD)
	// throws UnsupportedEncodingException, IOException {
	// Document doc = Jsoup
	// .connect(_TOKEN_URL)
	// .header("Authorization",
	// _AUTHPARAMS + getGoogleAuthKey(_USERNAME, _PASSWORD))
	// .userAgent("&lt;your app name").timeout(4000).get();
	// // RETRIEVES THE RESPONSE TOKEN
	// String _TOKEN = doc.body().text();
	// return _TOKEN;
	// }

	// public String getUserInfo(String _USERNAME, String _PASSWORD)
	// throws UnsupportedEncodingException, IOException {
	// Document doc = Jsoup
	// .connect(_USER_INFO_URL)
	// .header("Authorization",
	// _AUTHPARAMS + getGoogleAuthKey(_USERNAME, _PASSWORD))
	// .userAgent("&lt;your app name&gt;").timeout(4000).get();
	//
	// // RETRIEVES THE RESPONSE USERINFO
	// String _USERINFO = doc.body().text();
	// return _USERINFO;
	// }

	// public String getGoogleUserID(String _USERNAME, String _PASSWORD)
	// throws UnsupportedEncodingException, IOException {
	// /*
	// * USERINFO RETURNED LOOKS LIKE {"userId":"14577161871823252783",
	// * "userName"
	// * :"&lt;username&gt;","userProfileId":"&lt;21 numeric numbers",
	// * "userEmail":"&lt;username&gt;@gmail.com", "isBloggerUser":true,
	// * "signupTimeSec":1159535065}
	// */
	// String _USERINFO = getUserInfo(_USERNAME, _PASSWORD);
	// String _USERID = (String) _USERINFO.subSequence(11, 31);
	// return _USERID;
	// }
	//
	// private String[] getTagList(String _USERNAME, String _PASSWORD) {
	// ArrayList<String> _TAGTITLE_ARRAYLIST = new ArrayList<String>();
	// String _TAG_LABEL = null;
	// try {
	// _TAG_LABEL = "user/" + getGoogleUserID(_USERNAME, _PASSWORD)
	// + "/label/";
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// Document doc = null;
	// try {
	// doc = Jsoup
	// .connect(_TAG_LIST_URL)
	// .header("Authorization",
	// _AUTHPARAMS
	// + getGoogleAuthKey(_USERNAME, _PASSWORD))
	// .userAgent("&lt;your app name&gt;").timeout(6000).get();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// Elements links = doc.select("string");
	// for (Element link : links) {
	// String tagAttrib = link.attr("name");
	// String tagText = link.text();
	// // if(Func_Strings.FindWordInString(tagText, _TAG_LABEL)) {
	// // _TAGTITLE_ARRAYLIST.add(tagText.substring(32));
	// // }
	// }
	//
	// String[] _TAGTITLE_ARRAY = new String[_TAGTITLE_ARRAYLIST.size()];
	// _TAGTITLE_ARRAYLIST.toArray(_TAGTITLE_ARRAY);
	// return _TAGTITLE_ARRAY;
	// }
	//
	// private String[] getSubList(String _USERNAME, String _PASSWORD)
	// throws UnsupportedEncodingException, IOException {
	// ArrayList<String> _SUBTITLE_ARRAYLIST = new ArrayList<String>();
	//
	// Document doc = Jsoup
	// .connect(_SUBSCRIPTION_LIST_URL)
	// .header("Authorization",
	// _AUTHPARAMS + getGoogleAuthKey(_USERNAME, _PASSWORD))
	// .userAgent("&lt;your app name&gt;").timeout(5000).get();
	//
	// Elements links = doc.select("string");
	// for (Element link : links) {
	// String tagAttrib = link.attr("name");
	// String tagText = link.text();
	// _SUBTITLE_ARRAYLIST.add(tagText);
	// }
	//
	// String[] _SUBTITLE_ARRAY = new String[_SUBTITLE_ARRAYLIST.size()];
	// _SUBTITLE_ARRAYLIST.toArray(_SUBTITLE_ARRAY);
	// return _SUBTITLE_ARRAY;
	// }

	public interface CallBack {
		public void error(String message);

		public void sucess();
	}

}