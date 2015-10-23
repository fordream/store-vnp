package vn.com.vega.music.clientserver;


import org.json.JSONArray;
import org.json.JSONObject;

public class JsonListenUrl extends JsonBase {
	public String listenUrl = "";
	
	public static JsonListenUrl loadListenUrl(int songId, String audioProfileId) {
		String url = ClientServer.getInstance().getUrlListenBySongId(songId, audioProfileId);
		
		JsonListenUrl result = new JsonListenUrl();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				
				JSONArray jsonArray = json.getJSONArray("data");
				JSONObject obj = jsonArray.getJSONObject(0);
				
				//JSONObject obj = json.getJSONObject("data");
				result.listenUrl = obj.getString("streaming_url");
				
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}
}
