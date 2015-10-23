package vn.com.vega.music.clientserver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vega.music.objects.Song;

public class JsonSong extends JsonBase {

	public List<Song> songs;

	protected JsonSong() {
	}
	
	public static JsonSong loadSongListByAlbumId(int albumId) {
		String url = ClientServer.getInstance().getUrlListSongByAlbum(albumId);
		return loadSongList(url);
	}
	
	public static JsonSong loadSongListByPlaylistId(int playlistId) {
		String url = ClientServer.getInstance().getUrlListSongByPlaylist(playlistId);
		return loadSongList(url);
	}
	
	public static JsonSong loadSongListByArtistId(int artistId, int offset) {
		String url = ClientServer.getInstance().getUrlListSongByArtist(artistId, offset);
		return loadSongList(url);
	}
	
	public static JsonSong loadBillboardSong(){
		String url = ClientServer.getInstance().getUrlBillboardSong();
		return loadSongList(url);
	}
	
	public static JsonSong loadTopSongList(int offset) {
		String url = ClientServer.getInstance().getUrlListTopSong(offset);
		return loadSongList(url);
	}
	
	public static JsonSong search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchSong(keyword, offset);
		return loadSongList(url);
	}
	
	public static JsonSong loadSongInfo(int songId) {
		String url = ClientServer.getInstance().getUrlSongInfo(songId);
		return loadSongList(url);		
	}
	
	public static JsonSong shareSong(int songId) {
		String url = ClientServer.getInstance().getUrlShareSong(songId);
		JsonSong result = new JsonSong();
		JSONObject json = result.requestAndParseBasicJson(url);
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

}
