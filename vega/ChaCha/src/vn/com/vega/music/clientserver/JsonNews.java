package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vega.music.objects.NewsEntry;

public class JsonNews extends JsonBase {

	public ArrayList<NewsEntry> newsEntries;

	protected JsonNews() {
	}
	
	public static JsonNews loadNewsList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopNews(offset);
		JsonNews result = new JsonNews();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				ArrayList<NewsEntry> list = new ArrayList<NewsEntry>();
				JSONArray aData = json.getJSONArray("data");
				for (int i = 0; i < aData.length(); i++) {
					JSONObject o = aData.getJSONObject(i);
					list.add(result.parseNews(o));
				}
				result.newsEntries = list;
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}
	
	protected NewsEntry parseNews(JSONObject jo) throws JSONException {
		NewsEntry n = new NewsEntry();
		n.id = jo.getInt("id");
		n.title = jo.getString("title");
		n.wapUrl = jo.getString("url");
		n.thumbUrl = jo.getString("image_url");
		n.datetime = jo.getString("datetime");
		return n;
	}
}