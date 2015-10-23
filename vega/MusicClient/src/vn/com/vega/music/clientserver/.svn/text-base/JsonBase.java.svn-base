/**
 * 
 */
package vn.com.vega.music.clientserver;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Song;

/**
 * @author Hoai Ngo
 * 
 */
public class JsonBase {

	private static ServerSessionInvalidListener listener;

	public static final int SUCCESS = 0;
	public static final int ERROR_PARSER = -1001;
	public static final int ERROR_NETWORK = -1002;
	

	// error code
	public static final int ERROR_USER_NOT_AUTHENTICATED = 401;
	public static final int ERROR_POWER_EXPIRE = 402;
	public static final int ERROR_APP_NOT_VALIDATED = 403;

	protected int errorCode;
	protected String errorMessage;
	protected String preferredPackageCode; 
	public int totalFound;

	public boolean isSuccess() {
		return (errorCode == SUCCESS);
	}

	public boolean isParsingError() {
		return (errorCode == ERROR_PARSER);
	}

	public boolean isNetworkError() {
		return (errorCode == ERROR_NETWORK);
	}

	public boolean isServerSessionInvalid() {
		return (errorCode == ERROR_APP_NOT_VALIDATED) || (errorCode == ERROR_USER_NOT_AUTHENTICATED);
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	protected void setParsingError() {
		errorCode = ERROR_PARSER;
		errorMessage = "Parse Error";
	}

	protected void setNetworkError() {
		errorCode = ERROR_NETWORK;
		errorMessage = "Không thể kết nối đến máy chủ";
	}

	public static void setServerSessionInvalidListener(ServerSessionInvalidListener _listener) {
		listener = _listener;
	}

	public static void clearServerSessionInvalidListener() {
		listener = null;
	}

	protected JSONObject requestAndParseBasicJson(String url) {
		try {
			String input = NetworkUtility.doGetUrlSync(url).getResponseText();
			if (input == null || input.trim().length() == 0) {
				setNetworkError();
				return null;
			}

			JSONObject json = new JSONObject(input);
			parseBasicJson(json);

			// JSONObject data = json.getJSONObject("data");

			return json;
		} catch (Exception ex) {
			// setParsingError();
		}
		return null;
	}
	
	/*
	 * 
	 * Test
	 * 
	 */
	
	protected JSONObject testRequestAndParseBasicJson(String url) {
		try {
			//String input = NetworkUtility.doGetUrlSync(url).getResponseText();
			String input = "{\"error\":402,\"error_message\":\"Goi cuoc da het quyen download, xin vui long chuyen sang goi cuoc moi\",\"data\":[]}";
			if (input == null || input.trim().length() == 0) {
				setNetworkError();
				return null;
			}

			JSONObject json = new JSONObject(input);
			parseBasicJson(json);

			// JSONObject data = json.getJSONObject("data");

			return json;
		} catch (Exception ex) {
			// setParsingError();
		}
		return null;
	}

	protected JSONObject postAndParseBasicJson(String url, String content) {
		try {
			String input = NetworkUtility.doPostUrlSync(url, content).getResponseText();
			if (input == null || input.trim().length() == 0) {
				setNetworkError();
				return null;
			}

			JSONObject json = new JSONObject(input);
			parseBasicJson(json);

			return json;
		} catch (Exception ex) {
			// setParsingError();
		}
		return null;
	}
	
	protected JSONObject postAndParseBasicJson(String url, Bitmap content) {
		try {
			//String input = NetworkUtility.doPostUrlAsync(url, content, null).getResponseText();
			String input = NetworkUtility.doPostUrlSync(url, content).getResponseText();
			if (input == null || input.trim().length() == 0) {
				setNetworkError();
				return null;
			}

			JSONObject json = new JSONObject(input);
			parseBasicJson(json);

			return json;
		} catch (Exception ex) {
			// setParsingError();
		}
		return null;
	}


	// protected boolean parseBasicJson(JSONArray jsonArray) {
	//
	// errorCode = requireInt(jsonArray, 1);
	// if (listener != null) {
	// if (errorCode == ERROR_USER_NOT_AUTHENTICATED) {
	// listener.onLoginAgain();
	// } else if (errorCode == ERROR_APP_NOT_VALIDATED) {
	// listener.onValidateAgain();
	// }
	// }
	//
	// errorMessage = getString(jsonArray, 2, false);
	// totalFound = getInt(jsonArray, "total", false);
	// return (errorCode == 0);
	// }

	protected boolean parseBasicJson(JSONObject json) {
		errorCode = requireInt(json, "error");
		preferredPackageCode = getString(json, "preferred_package_code", false);
		errorMessage = getString(json, "error_message", false);
		totalFound = getInt(json, "total", false);
		
		/*
		DataStore dataStore = DataStore.getInstance();
		if(dataStore.getConfig("TEST_402") != null && dataStore.getConfig("TEST_402").equals("402"))
			errorCode = ERROR_POWER_EXPIRE;
		
		*/
		
		try{
			if (listener != null) {
				if (errorCode == ERROR_USER_NOT_AUTHENTICATED) {
					listener.onLoginAgain();
				} else if (errorCode == ERROR_APP_NOT_VALIDATED) {
					listener.onValidateAgain();
				}
				else if(errorCode == ERROR_POWER_EXPIRE){
					listener.onPowerExpire(preferredPackageCode, errorMessage);
				}
			}
		}
		catch (Throwable e) {
			// TODO: handle exception		
			Log.d("402", e.getMessage(), e);
			
		}
		
		
		
		return (errorCode == 0);
	}

	// -------------------------------------------------------------------
	// Song related

	protected Song parseSong(JSONObject obj) {
		Song s = new Song(Song.SONG_TYPE_SERVER);
		try {
			s.id = obj.getInt("id");
			s.title = obj.getString("title");
			s.duration = obj.getInt("duration");
			s.artist_id = getInt(obj, "artist_id", false);
			s.artist_name = obj.getString("artist_name");
			s.imageUrl = obj.getString("artist_image_url_large");
			s.genre_name = getString(obj, "category_name", false);
			s.viewCount = getInt(obj, "view_count", false);
		} catch (Throwable t) {
			setParsingError();
		}

		s.album_id = getInt(obj, "album_id", false);
		s.album_title = getString(obj, "album_name", false);
		return s;
	}

	// -------------------------------------------------------------------
	// Basic stuff

	protected int requireInt(JSONObject json, String key) {
		return getInt(json, key, true);
	}

	protected int requireInt(JSONArray json, int index) {
		return getInt(json, index, true);
	}

	protected int getInt(JSONObject json, String key, boolean require) {
		try {
			return json.getInt(key);
		} catch (Throwable t) {
			if (require) {
				setParsingError();
			}
			return 0;
		}
	}

	protected int getInt(JSONArray json, int index, boolean require) {
		try {
			return json.getInt(index);
		} catch (Throwable t) {
			if (require) {
				setParsingError();
			}
			return 0;
		}
	}

	protected String requireString(JSONObject json, String key) {
		return getString(json, key, true);
	}

	protected String getString(JSONObject json, String key, boolean require) {
		try {
			return json.getString(key);
		} catch (Throwable t) {
			if (require) {
				setParsingError();
			}
			return "";
		}
	}

	protected String getString(JSONArray json, int index, boolean require) {
		try {
			return json.getString(index);
		} catch (Throwable t) {
			if (require) {
				setParsingError();
			}
			return "";
		}
	}

	protected boolean requireBoolean(JSONObject json, String key) {
		return getBoolean(json, key, true);
	}

	protected boolean getBoolean(JSONObject json, String key, boolean require) {
		try {
			return json.getBoolean(key);
		} catch (Throwable t) {
			if (require) {
				setParsingError();
			}
			return false;
		}
	}
}
