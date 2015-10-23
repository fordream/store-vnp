package vn.com.vega.music.clientserver;

import org.json.JSONObject;

public class JsonWatchUrl extends JsonBase {

	public String streamingUrl = "";

	protected JsonWatchUrl() {
		
	}

	public static JsonWatchUrl loadWatchUrl(int videoId) {
		String url = ClientServer.getInstance().getUrlWatchByVideoId(videoId);
		JsonWatchUrl result = new JsonWatchUrl();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				JSONObject obj = json.getJSONArray("data").getJSONObject(0);
				result.streamingUrl = obj.getString("streaming_url");
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}
}
