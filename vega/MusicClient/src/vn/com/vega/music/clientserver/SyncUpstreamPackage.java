package vn.com.vega.music.clientserver;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.vega.music.objects.CacheEntry;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

public class SyncUpstreamPackage {
	
	private static final String LOG_TAG = Const.LOG_PREF + SyncUpstreamPackage.class.getSimpleName();

	public ArrayList<SyncUpstreamPlaylist> aAddedPlaylists;
	public ArrayList<SyncUpstreamPlaylist> aUpdatedPlaylists;
	public ArrayList<Integer> aDeletedPlaylistIds;
	public ArrayList<CacheEntry> cacheEntries;
	//public int iCurrentVersion;
	public String sCurrentVersion;

	public SyncUpstreamPackage() {
		aAddedPlaylists = new ArrayList<SyncUpstreamPlaylist>();
		aUpdatedPlaylists = new ArrayList<SyncUpstreamPlaylist>();
		aDeletedPlaylistIds = new ArrayList<Integer>();
		cacheEntries = new ArrayList<CacheEntry>();
		//iCurrentVersion = 0;
		sCurrentVersion = "";
	}

	/**
	 * toJsonString Create json string represent of this package
	 * 
	 * @return String
	 */

	public String toJsonString() {
		JSONObject json = new JSONObject();
		try {
			//json.put("last_update", iCurrentVersion);
			json.put("last_update", sCurrentVersion);
			JSONArray jDArray = new JSONArray(); // deleted
			JSONArray jAArray = new JSONArray(); // added
			JSONArray jUArray = new JSONArray(); // updated

			// deleted
			for (int i = 0; i < aDeletedPlaylistIds.size(); i++) {
				jDArray.put(aDeletedPlaylistIds.get(i));
			}

			// added
			for (int i = 0; i < aAddedPlaylists.size(); i++) {
				JSONObject jaPlaylist = new JSONObject();
				jaPlaylist.put("id", aAddedPlaylists.get(i).iLocalId);
				jaPlaylist.put("name", URLEncoder.encode(aAddedPlaylists.get(i).sName));
				jaPlaylist.put("type", aAddedPlaylists.get(i).iType);
				JSONArray subArr = new JSONArray();
				ArrayList<Integer> aAddedSongListIds = aAddedPlaylists.get(i).aAddedSongListIds;
				for (int j = 0; j < aAddedSongListIds.size(); j++) {
					subArr.put(aAddedSongListIds.get(j));
				}
				jaPlaylist.put("song_list", subArr);
				jAArray.put(jaPlaylist);
			}

			// updated
			for (int i = 0; i < aUpdatedPlaylists.size(); i++) {
				JSONObject juPlaylist = new JSONObject();
				juPlaylist.put("id", aUpdatedPlaylists.get(i).iServerId);
				juPlaylist.put("name", URLEncoder.encode(aUpdatedPlaylists.get(i).sName));
				juPlaylist.put("type", aUpdatedPlaylists.get(i).iType);
				JSONArray subAddedArr = new JSONArray();
				JSONArray subDeletedArr = new JSONArray();
				ArrayList<Integer> aAddedSongListIds = aUpdatedPlaylists.get(i).aAddedSongListIds;
				ArrayList<Integer> aDeletedSongListIds = aUpdatedPlaylists.get(i).aDeletedSongListIds;
				for (int j = 0; j < aAddedSongListIds.size(); j++) {
					subAddedArr.put(aAddedSongListIds.get(j));
				}
				juPlaylist.put("added_song_list", subAddedArr);
				for (int j = 0; j < aDeletedSongListIds.size(); j++) {
					subDeletedArr.put(aDeletedSongListIds.get(j));
				}
				juPlaylist.put("deleted_song_list", subDeletedArr);
				jUArray.put(juPlaylist);
			}

			json.put("added", jAArray);
			json.put("updated", jUArray);
			json.put("deleted", jDArray);

			String jsonString = json.toString();
			VegaLog.d(LOG_TAG, "Post = " + jsonString);
			return jsonString;

		} catch (Exception e) {
			return "";
		}
	}
}
