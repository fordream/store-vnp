package vn.com.vega.music.clientserver;

import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.vega.music.objects.Video;

public class JsonVideo extends JsonBase {

	public ArrayList<Video> videos;

	protected JsonVideo() {
	}

	public static JsonVideo loadVideoListByArtist(int artistId, int offset) {
		String url = ClientServer.getInstance().getUrlListVideoByArtist(artistId, offset);
		return loadVideoList(url);
	}

	public static JsonVideo search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchVideo(keyword, offset);
		return loadVideoList(url);
	}
	
	
	public static JsonVideo loadBillboardVideo() {
		String url = ClientServer.getInstance().getUrlBillboardVideo();
		return loadVideoList(url);
	}

	public static JsonVideo loadTopVideoList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopVideo(offset);
		return loadVideoList(url);
	}

	protected static JsonVideo loadVideoList(String url) {
		JsonVideo result = new JsonVideo();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			result.parseVideo(json);
		}
		return result;
	}

	/**
	 * parse video json from server return: JSONVideo object
	 */
	protected void parseVideo(JSONObject json) {
		try {
			// channel category
			ArrayList<Video> detailVideos = new ArrayList<Video>();
			JSONArray aData = json.getJSONArray("data");
			for (int i = 0; i < aData.length(); i++) {
				JSONObject o = aData.getJSONObject(i);
				Video v = new Video();
				v.id = String.valueOf(o.getInt("id"));
				v.title = o.getString("title");
				v.thumbnail_large = o.getString("thumbnail_large");
				v.thumbnail_small = o.getString("thumbnail_large");
				v.duration = o.getString("duration");
				// v.streamingUrl = o.getString("url_streaming");
				v.price = o.getString("price");
				detailVideos.add(v);
			}
			videos = detailVideos;
		} catch (Exception ex) {
			setParsingError();
		}
	}
}
