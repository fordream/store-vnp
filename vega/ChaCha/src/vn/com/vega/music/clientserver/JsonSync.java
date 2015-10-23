package vn.com.vega.music.clientserver;

import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;

public class JsonSync extends JsonBase {

	public SyncDownstreamPackage aDownstreamPackage = new SyncDownstreamPackage();

	public static JsonSync doSync(SyncUpstreamPackage sup) {
		String url = ClientServer.getInstance().getUrlSync();
		JsonSync result = new JsonSync();
		JSONObject json = result.postAndParseBasicJson(url, sup.toJsonString());
		if (result.isSuccess()) {
			result.parseSyncDownstreamPackageJson(json);
		}
		return result;
	}

	protected void parseSyncDownstreamPackageJson(JSONObject json) {
		try {
			// Last version aka last update
			//aDownstreamPackage.iLastVersion = json.getInt("last_update");
			//aDownstreamPackage.iLastVersion = getInt(json, "last_update", false);
			aDownstreamPackage.sLastVersion = getString(json, "last_update", false);

			// Updated playlists
			JSONArray updated = json.getJSONArray("updated");
			for (int i = 0; i < updated.length(); i++) {
				JSONObject jsonPlaylist = updated.getJSONObject(i);
				SyncDownstreamPlaylist sdp = parseSyncDownstreamPlaylist(jsonPlaylist);
				aDownstreamPackage.aUpdatedPlaylists.add(sdp);
			}

			// Added playlists
			JSONArray added = json.getJSONArray("added");
			for (int i = 0; i < added.length(); i++) {
				JSONObject jsonPlaylist = added.getJSONObject(i);
				SyncDownstreamPlaylist sdp = parseSyncDownstreamPlaylist(jsonPlaylist);
				aDownstreamPackage.aAddedPlaylists.add(sdp);
			}

			// Deleted playlists
			JSONArray deleted = json.getJSONArray("deleted");
			for (int i = 0; i < deleted.length(); i++) {
				aDownstreamPackage.aDeletedPlaylistIds.add(new Integer(deleted.getInt(i)));
			}

			// Register Ids
			JSONArray regIds = json.getJSONArray("regids");
			for (int i = 0; i < regIds.length(); i++) {
				SyncIdRegisterResult sirr = parseSyncIdRegisterResult(regIds.getJSONObject(i));
				aDownstreamPackage.aIdRegisterResults.add(sirr);
			}

			// New songs
			JSONArray newSong = json.getJSONArray("sync_song");
			for (int i = 0; i < newSong.length(); i++) {
				Song song = parseSong(newSong.getJSONObject(i));
				aDownstreamPackage.aNewSongs.add(song);
			}
			
			// Favourite playlist
			JSONArray favPlaylist = json.getJSONArray("favourite_playlist");
			for (int i = 0; i < favPlaylist.length(); i++) {
				//Playlist playlist = favPlaylist.
				//aDownstreamPackage.aNewSongs.add(song);
			}
			
			// Follow/Unfollw artist
			
			
		} catch (Throwable t) {
			setParsingError();
		}
	}

	protected SyncDownstreamPlaylist parseSyncDownstreamPlaylist(JSONObject json) {
		SyncDownstreamPlaylist playlist = new SyncDownstreamPlaylist();
		try {
			playlist.iServerId = json.getInt("id");
			playlist.sName = json.getString("name");
			playlist.iType = json.getInt("type");
			JSONArray songList = json.getJSONArray("song_list");
			for (int i = 0; i < songList.length(); i++) {
				playlist.aSongListIds.add(new Integer(songList.getInt(i)));
			}
		} catch (Throwable t) {
			setParsingError();
		}

		return playlist;
	}

	protected SyncIdRegisterResult parseSyncIdRegisterResult(JSONObject json) {
		SyncIdRegisterResult sirr = new SyncIdRegisterResult();
		sirr.iSubmitId = requireInt(json, "submitted");
		sirr.iRegisteredId = requireInt(json, "registered");
		return sirr;
	}
}
