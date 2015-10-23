package vn.com.vega.music.clientserver;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonDownloadUrl extends JsonBase {

	public String downloadUrl = "";

	public static JsonDownloadUrl loadDownloadUrl(int songId,
			String audioProfile) {
		String url = ClientServer.getInstance().getUrlDownloadBySongId(songId,
				audioProfile);

		JsonDownloadUrl result = new JsonDownloadUrl();
		JSONObject json = result.requestAndParseBasicJson(url);
		// JSONObject json = result.testRequestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {

				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject obj = jsonArray.getJSONObject(0);

				// JSONObject obj = json.getJSONObject("data");
				// result.downloadUrl = obj.getString("download_url");
				result.downloadUrl = obj.getString("streaming_url"); // changed
																		// for
																		// chacha
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}
}
