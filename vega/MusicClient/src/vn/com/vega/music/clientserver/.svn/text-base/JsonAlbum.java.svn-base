package vn.com.vega.music.clientserver;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.vega.music.objects.Album;
import vn.com.vega.music.utils.VegaLog;

public class JsonAlbum extends JsonBase {

	private static final String LOG_TAG = JsonAlbum.class.getSimpleName();
	
	public ArrayList<Album> albums;

	protected JsonAlbum() {
	}

	public static JsonAlbum loadAlbummListByArtist(int artistId, int offset) {
		String url = ClientServer.getInstance().getUrlListAlbumByArtist(artistId, offset);
		return loadAlbumList(url);
	}

	public static JsonAlbum loadBillboardAlbum(){
		String url = ClientServer.getInstance().getUrlBillboardAlbum();
		return loadAlbumList(url);
	}
	
	public static JsonAlbum loadNewAlbummList(int offset) {
		String url = ClientServer.getInstance().getUrlListNewAlbum(offset);
		return loadAlbumList(url);
	}

	public static JsonAlbum search(String keyword, int offset) {
		String url = ClientServer.getInstance().getUrlSearchAlbum(keyword, offset);
		return loadAlbumList(url);
	}

	public static JsonAlbum loadAlbumInfo(int albumId) {
		String url = ClientServer.getInstance().getUrlAlbumInfo(albumId);
		return loadAlbumList(url);
	}

	protected static JsonAlbum loadAlbumList(String url) {
		JsonAlbum result = new JsonAlbum();
		JSONObject json = result.requestAndParseBasicJson(url);
		if (result.isSuccess()) {
			try {
				ArrayList<Album> detailAlbums = new ArrayList<Album>();
				JSONArray aData = json.getJSONArray("data");
				for (int i = 0; i < aData.length(); i++) {
					JSONObject obj = aData.getJSONObject(i);
					Album v = new Album();
					v.id = obj.getInt("id");
					v.title = obj.getString("title");
					v.coverUrl = obj.getString("cover_url");
					v.artistId = obj.getInt("artist_id");
					v.artistName = obj.getString("artist_name");
					v.songCount = obj.getInt("song_count");
	
					detailAlbums.add(v);
				}
				result.albums = detailAlbums;
			} catch (Throwable t) {
				VegaLog.d(LOG_TAG, "Parsing album list failed");
				result.setParsingError();
			}
		}
		return result;
	}
}
