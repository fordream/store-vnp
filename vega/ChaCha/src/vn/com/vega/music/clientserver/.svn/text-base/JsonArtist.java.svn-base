package vn.com.vega.music.clientserver;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.utils.VegaLog;

public class JsonArtist extends JsonBase {

	private static final String LOG_TAG = JsonArtist.class.getSimpleName();

	public ArrayList<Artist> artists;

	protected JsonArtist() {
	}

	public static JsonArtist loadTopArtistList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopArtist(offset);
		return loadArtistList(url);
	}

	public static JsonArtist search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchArtist(keyword, offset);
		return loadArtistList(url);
	}

	public static JsonArtist loadArtistInfo(int artistId) {
		String url = ClientServer.getInstance().getUrlArtistInfo(artistId);
		return loadArtistList(url);
	}

	public static JSONObject follow(int artistId) {
		String url = ClientServer.getInstance().getUrlFollowArtist(artistId);
		return register(url);
	}

	public static JSONObject unFollow(int artistId) {
		String url = ClientServer.getInstance().getUrlUnFollowArtist(artistId);
		return register(url);
	}

	protected static JSONObject register(String url) {
		JsonArtist result = new JsonArtist();
		return result.requestAndParseBasicJson(url);
	}

	protected static JSONObject unRegister(String url) {
		JsonArtist result = new JsonArtist();
		return result.requestAndParseBasicJson(url);
	}

	protected static JsonArtist loadArtistList(String url) {
		JsonArtist result = new JsonArtist();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				ArrayList<Artist> detailArtists = new ArrayList<Artist>();
				JSONArray aData = json.getJSONArray("data");
				for (int i = 0; i < aData.length(); i++) {
					JSONObject obj = aData.getJSONObject(i);
					Artist v = new Artist();
					v.id = obj.getInt("id");
					v.name = obj.getString("name");
					v.imageUrl = obj.getString("image_url");
					v.albumCount = obj.getInt("album_count");
					v.songCount = obj.getInt("song_count");
					v.videoCount = obj.getInt("video_count");
					v.fanCount = obj.getInt("fan_count");

					// v.follow = obj.getInt("follow");

					detailArtists.add(v);
				}
				// add to list
				result.artists = detailArtists;
			} catch (Throwable t) {
				VegaLog.d(LOG_TAG, "Parsing list artist list failed");
				result.setParsingError();
			}
		}
		return result;
	}
}
