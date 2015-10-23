package vn.com.vega.music.clientserver;

import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

import vn.com.vega.music.clientserver.ClientServer;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.MyService;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

public class JsonAuth extends JsonBase implements Const {

	private static final String LOG_TAG = Const.LOG_PREF
			+ JsonAuth.class.getSimpleName();

	public String randomKey = "";
	public String username;
	public String password;
	public String phoneNumber;
	public String packageCode;
	public String accountInfo;

	public String beginDate;
	public String endDate;
	
	public Hashtable<String, String> hashAccount = new Hashtable<String, String>();

	public MyService mService = new MyService();

	public static JsonAuth requestAuthKey() {
		String url = ClientServer.getInstance().getUrlAuthRequestRandomKey();
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject data = jsonArray.getJSONObject(0);
				result.randomKey = data.getString("randomkey");

			} catch (Exception ex) {
				VegaLog.d(LOG_TAG, "Parsing AuthKey message failed");
				result.setParsingError();
			}
		}
		return result;
	}

	public static JsonAuth logout() {
		String url = ClientServer.getInstance().getUrlAuthLogout();
		JsonAuth result = new JsonAuth();
		result.requestAndParseBasicJson(url);
		return result;
	}

	public static JsonAuth validate(String randomKey) {
		String hash = ClientServer.getInstance().calcAuthHash(randomKey);
		String url = ClientServer.getInstance().getUrlAuthValidate(hash);
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject data = jsonArray.getJSONObject(0);
				result.mService = new MyService();
				result.mService.sms_content = result.getString(data, "recover_password_sms_server", false);
				result.mService.sms_server = result.getString(data, "recover_password_sms_content", false);
				result.mService.ask_rate = result.getInt(data, "ask_rate", false);
				result.mService.ask_rate_count = result.getInt(data, "ask_rate_count", false);
				result.mService.ask_rate_message = result.getString(data, "ask_rate_message", false);
				result.mService.warning_message = result.getString(data, "warning_message", false);
			} catch (Throwable t) {
				//Do nothing here, please don't mark as parse error
			}
		}
		return result;
	}

	public static JsonAuth submitKey(String randomKey) {
		String submitJson = "{\"session_key\":\""
				+ ClientServer.getInstance().calcAuthHash(randomKey) + "\"}";
		String url = ClientServer.getInstance().getUrlAuthSubmitHash();
		JsonAuth result = new JsonAuth();
		result.postAndParseBasicJson(url, submitJson);
		return result;
	}

	public static JsonAuth login(String username, String pass) {
		String url = ClientServer.getInstance().getUrlAuthLogin(username, pass);
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		result.parseAccount(json);
		return result;
	}
	
	
	public static JsonAuth changePassword(String username, String oldpass, String newpass) {
		String url = ClientServer.getInstance().getUrlChangePassword(username, oldpass, newpass);
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		return result;
	}


	private void parseAccount(JSONObject json) {
		if (isSuccess()) {
			try {
				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject data = jsonArray.getJSONObject(0);

				username = getString(data, KEY_LOGIN_NAME, false);
				packageCode = getString(data, KEY_PACKAGE_CODE, false);
				beginDate = getString(data, KEY_BEGIN_DATE, false);
				endDate = getString(data, KEY_END_DATE, false);
				phoneNumber = getString(data, "phone_number", false);
				
				hashAccount.put(KEY_LOGIN_NAME, getString(data, KEY_LOGIN_NAME, false));
				hashAccount.put(KEY_PACKAGE_CODE, getString(data, KEY_PACKAGE_CODE, false));
				hashAccount.put(KEY_BEGIN_DATE, getString(data, KEY_BEGIN_DATE, false));
				hashAccount.put(KEY_END_DATE, getString(data, KEY_END_DATE, false));
				hashAccount.put(KEY_ACCOUNT_INFO, getString(data, KEY_ACCOUNT_INFO, false));
				hashAccount.put(KEY_AVATAR, getString(data, KEY_AVATAR, false));
				hashAccount.put(KEY_FULL_NAME, getString(data, KEY_FULL_NAME, false));
				hashAccount.put(KEY_PHONE_NUMBER, getString(data, KEY_PHONE_NUMBER, false));
				hashAccount.put(KEY_PHONE_NUMBER_EDITABLE, String.valueOf(getBoolean(data, KEY_PHONE_NUMBER_EDITABLE, false)));
				hashAccount.put(KEY_DOB, getString(data, KEY_DOB, false));
				hashAccount.put(KEY_GENDER, String.valueOf(getInt(data, KEY_GENDER, false)));
				hashAccount.put(KEY_ADDRESS, getString(data, KEY_ADDRESS, false));
				hashAccount.put(KEY_EMAIL, getString(data, KEY_EMAIL, false));
				hashAccount.put(KEY_EMAIL_EDITABLE, String.valueOf(getBoolean(data, KEY_EMAIL_EDITABLE, false)));

				String ai = getString(data, "account_info", false);
				if (ai != null) {
					accountInfo = ai.replace("&lt;", "<").replace("&gt;", ">");
				} else {
					accountInfo = "";
				}
			} catch (Exception ex) {
				VegaLog.d(LOG_TAG, "Parsing account info failed");
				setParsingError();
			}
		}
	}

	public static JsonAuth uploadAvatar(Bitmap avatar) {
		String url = ClientServer.getInstance().getUrlUploadAvatar();
		JsonAuth result = new JsonAuth();
		result.postAndParseBasicJson(url, avatar);
		if (result.isSuccess()) {
			// what the hell here ???
		}
		return result;
	}
	
	public static JsonAuth identify() {
		String url = ClientServer.getInstance().getUrlAuthIdentify();
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		result.parseAccount(json);
		return result;
	}

	public static JsonAuth subscribe(String packageID, String phoneNumber) {
		String url = ClientServer.getInstance().getUrlAuthSubscribe(packageID, phoneNumber);
		JsonAuth result = new JsonAuth();
		JSONObject json = result.requestAndParseBasicJson(url);
		result.parseAccount(json);
		return result;
	}

	public static JsonAuth unsubscribe() {
		String url = ClientServer.getInstance().getUrlAuthUnsubscribe();
		JsonAuth result = new JsonAuth();
		result.requestAndParseBasicJson(url);
		return result;
	}
	
	public static JsonAuth updateAccount(String name, String phone, String email, String dob, int gender, String address) {
		String url = ClientServer.getInstance().getUrlAuthUpdateAccount(name, phone, email, dob, gender, address);
		JsonAuth result = new JsonAuth();
		result.requestAndParseBasicJson(url);
		return result;
	}
}