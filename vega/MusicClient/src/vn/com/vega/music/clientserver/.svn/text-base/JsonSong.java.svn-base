package vn.com.vega.music.clientserver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import vn.com.vega.music.objects.Song;

public class JsonSong extends JsonBase {

	public List<Song> songs;
	public String shareMsg;
	public String shareLink;

	protected JsonSong() {
	}

	public static JsonSong loadSongListByAlbumId(int albumId) {
		String url = ClientServer.getInstance().getUrlListSongByAlbum(albumId);
		return loadSongList(url);
	}

	public static JsonSong loadSongListByPlaylistId(int playlistId) {
		String url = ClientServer.getInstance().getUrlListSongByPlaylist(
				playlistId);
		return loadSongList(url);
	}

	public static JsonSong loadSongListByArtistId(int artistId, int offset) {
		String url = ClientServer.getInstance().getUrlListSongByArtist(
				artistId, offset);
		return loadSongList(url);
	}

	public static JsonSong loadBillboardSong() {
		String url = ClientServer.getInstance().getUrlBillboardSong();
		return loadSongList(url);
	}

	public static JsonSong loadTopSongList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopSong(offset);
		return loadSongList(url);
	}

	public static JsonSong search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchSong(keyword,
				offset);
		return loadSongList(url);
	}

	public static JsonSong loadSongInfo(int songId) {
		String url = ClientServer.getInstance().getUrlSongInfo(songId);
		return loadSongList(url);
	}

	public static JsonSong shareSong(int songId) {
		String url = ClientServer.getInstance().getUrlShareSong(songId);
		return loadShareContent(url);
	}

	protected static JsonSong loadShareContent(String url) {
		JsonSong result = new JsonSong();
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

	protected static JsonSong loadSongList(String url) {
		JsonSong result = new JsonSong();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				result.parseSongList(json);
			} catch (Exception ex) {
				result.setParsingError();
			}
		}
		return result;
	}

	public void parseSongList(JSONObject json) throws JSONException {
		// channel category
		ArrayList<Song> detailSongs = new ArrayList<Song>();
		JSONArray aData = json.getJSONArray("data");
		for (int i = 0; i < aData.length(); i++) {
			JSONObject obj = aData.getJSONObject(i);
			Song v = parseSong(obj);
			detailSongs.add(v);
		}
		songs = detailSongs;
	}

	/**
	 * 
	 * Music quality list
	 * */
	public static JsonSong loadMusicQualityList() {
		JsonSong result = new JsonSong();
		String url = ClientServer.getInstance().getUrlListAudioProfile();
		Log.i("URL", url);
		JSONObject json = result.requestAndParseBasicJson(url);

		if (result.isSuccess()) {
			try {
				result.parseMusicQualityList(json);
			} catch (Exception ex) {
				result.setParsingError();
			}
		}

		return result;
	}

	public void parseMusicQualityList(JSONObject json) throws JSONException {
		// channel category
		ArrayList<Song> detailSongs = new ArrayList<Song>();
		JSONArray aData = json.getJSONArray("data");
		for (int i = 0; i < aData.length(); i++) {
			JSONObject obj = aData.getJSONObject(i);
			Song v = parseMusicQuality(obj);
			detailSongs.add(v);
		}
		songs = detailSongs;
	}

	private Song parseMusicQuality(JSONObject obj) {
		Song s = new Song(Song.SONG_TYPE_SERVER);
		try {
			s.id = obj.getInt("id");
			s.title = obj.getString("name");
		} catch (Throwable t) {
			setParsingError();
		}
		return s;
	}

}
