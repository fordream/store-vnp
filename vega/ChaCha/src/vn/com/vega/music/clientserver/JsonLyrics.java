package vn.com.vega.music.clientserver;

import org.json.JSONObject;

import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

public class JsonLyrics extends JsonBase {
	
	private static final String LOG_TAG = Const.LOG_PREF + JsonLyrics.class.getSimpleName();

	public String lyrics;

	public JsonLyrics() {
	}

	public static JsonLyrics loadLyrics(String url) {
		VegaLog.d(LOG_TAG, url);
		JsonLyrics result = new JsonLyrics();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONObject obj = json.getJSONObject("data");
				result.lyrics = obj.getString("lyrics");
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}
}
