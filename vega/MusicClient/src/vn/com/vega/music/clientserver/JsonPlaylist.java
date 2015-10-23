package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;

public class JsonPlaylist extends JsonBase {

	public ArrayList<Playlist> playlists;
	public String shareMsg;
	public String shareLink;

	protected JsonPlaylist() {
	}

	public static JsonPlaylist loadTopPlaylistList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopPlaylist(offset);
		return loadListPlaylist(url);
	}

	public static JsonPlaylist likePlaylist(int playlistId) {

		String url = ClientServer.getInstance().getUrlLikePlaylist(playlistId);
		JsonPlaylist result = new JsonPlaylist();
		result.requestAndParseBasicJson(url);

		return result;

	}

	public static JsonPlaylist unlikePlaylist(int playlistId) {

		String url = ClientServer.getInstance()
				.getUrlUnLikePlaylist(playlistId);
		JsonPlaylist result = new JsonPlaylist();
		result.requestAndParseBasicJson(url);

		return result;

	}

	public static JsonPlaylist sharePlaylist(int playlistId) {
		String url = ClientServer.getInstance().getUrlSharePlaylist(playlistId);
		return loadShareContent(url);
	}

	protected static JsonPlaylist loadShareContent(String url) {
		JsonPlaylist result = new JsonPlaylist();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {

				JSONObject aData = json.getJSONObject("data");

				result.shareMsg = aData.getString("message");
				result.shareLink = aData.getString("link");

			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}

	public static JsonPlaylist search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchPlaylist(keyword,
				offset);
		return loadListPlaylist(url);
	}

	protected static JsonPlaylist loadListPlaylist(String url) {
		JsonPlaylist result = new JsonPlaylist();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				result.parsePlaylistList(json);
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}

	/**
	 * parse video json from server return: JSONVideo object
	 */
	protected void parsePlaylistList(JSONObject json) {
		try {
			// channel category
			ArrayList<Playlist> list = new ArrayList<Playlist>();
			JSONArray aData = json.getJSONArray("data");
			for (int i = 0; i < aData.length(); i++) {
				JSONObject o = aData.getJSONObject(i);
				list.add(parsePlaylist(o));
			}
			playlists = list;
		} catch (Exception ex) {
			setParsingError();
		}
	}

	/**
	 * Update playlist object???
	 * 
	 * @param jo
	 * @return
	 * @throws JSONException
	 */
	protected Playlist parsePlaylist(JSONObject jo) throws JSONException {
		Log.e("JsonPlaylist", jo.toString());
		try {
			Playlist p = new Playlist();
			p.serverId = jo.getInt("id");
			p.type = jo.getInt("type");
			p.title = getString(jo, "name", false); // jo.getString("name");
			p.userMsisdn = getString(jo, "creator_msisdn", false); // jo.getString("creator_msisdn");
			p.userName = getString(jo, "creator_name", false); // jo.getString("creator_name");
			p.viewCount = getInt(jo, "view_count", false); // jo.getInt("view_count");
			p.userThumb = getString(jo, "creator_avatar_url", false);
			p.total_song = getInt(jo, "total_song", false);
			JSONArray jsonThumbs = new JSONArray();
			try {
				jsonThumbs = jo.getJSONArray("thumbnails");
			} catch (Exception e) {
				// TODO: handle exception
			}

			ArrayList<String> thumbs = new ArrayList<String>();
			for (int i = 0; i < jsonThumbs.length(); i++) {
				thumbs.add(jsonThumbs.get(i).toString());
			}
			ArrayList<String> temps = new ArrayList<String>();
			temps.add("");
			temps.add("");
			temps.add("");
			temps.add("");
			if (thumbs.size() < 4) {
				thumbs.addAll(temps);
			}
			p.thumbnails.addAll(thumbs);
			if(p.title.equals("") || p.title.equalsIgnoreCase("null"))
				p.title = "Unknown";
			return p;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("parsePlaylist", e.getMessage());
		}
		return null;
	}
}
