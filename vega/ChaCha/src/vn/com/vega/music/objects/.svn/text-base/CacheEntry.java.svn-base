package vn.com.vega.music.objects;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.music.clientserver.SyncUpstreamPlaylist;
import vn.com.vega.music.utils.VegaLog;

public class CacheEntry {
	
	private static final String LOG_TAG = CacheEntry.class.getSimpleName();

	public static final int ACTION_ADD = 0;
	public static final int ACTION_DELETE = 1;
	public static final int ACTION_UPDATE = 2;

	private int actionType;
	private ArrayList<Integer> addedSongIds = new ArrayList<Integer>();
	private ArrayList<Integer> deletedSongIds = new ArrayList<Integer>();
	private String userMsisdn;
	private int serverId;
	private int localId;

	public int playlistType;
	public String playlistTitle;

	public CacheEntry(int type, String msisdn, int server, int local, int pType, String title, String added, String deleted) {
		actionType = type;
		userMsisdn = msisdn;
		serverId = server;
		localId = local;
		playlistType = pType;
		playlistTitle = title;
		addedSongIds.addAll(convertStringToList(added));
		deletedSongIds.addAll(convertStringToList(deleted));
	}

	/***********************************************************************************
	 * 
	 * STATIC FUNCTIONS
	 * 
	 ***********************************************************************************/
	public static CacheEntry createAddPlaylistCacheEntry(Playlist pl) {
		if (pl.serverId > 0) {
			// Error
			return null;
		}

		CacheEntry ce = new CacheEntry(ACTION_ADD, pl.userMsisdn, pl.serverId, pl.id, pl.type, pl.title, null, null);
		ce.addSongList(pl.getSongList());
		return ce;
	}

	public static CacheEntry createUpdatePlaylistCacheEntry(Playlist pl, List<Song> addedSong, List<Song> deletedSong) {
		if (pl.serverId <= 0) {
			// Error
			return null;
		}

		CacheEntry ce = new CacheEntry(ACTION_UPDATE, pl.userMsisdn, pl.serverId, pl.id, pl.type, pl.title, null, null);
		if (addedSong != null) {
			ce.addSongList(addedSong);
		}
		if (deletedSong != null) {
			ce.removeSongList(deletedSong);
		}
		return ce;
	}

	public static CacheEntry createDeletePlaylistCacheEntry(Playlist pl) {
		if (pl.serverId <= 0) {
			// Error
			return null;
		}

		CacheEntry ce = new CacheEntry(ACTION_DELETE, pl.userMsisdn, pl.serverId, pl.id, pl.type, pl.title, null, null);
		return ce;
	}

	/***********************************************************************************
	 * 
	 * CLASS FUNCTIONS
	 * 
	 ***********************************************************************************/

	public void convertToDeleteType() {
		actionType = ACTION_DELETE;
	}

	public void updateInfo(Playlist pl) {
		// If action is delete, do nothing
		if (actionType == ACTION_DELETE) {
			return;
		}

		// Invalid input
		if (pl.id != localId) {
			return;
		}

		playlistType = pl.type;
		playlistTitle = pl.title;
	}

	public void addSongList(List<Song> songList) {
		for (Song song : songList) {
			addSong(song);
		}
	}

	public void addSong(Song song) {
		// If action is delete, do nothing
		Integer value = new Integer(song.id);
		if (actionType == ACTION_DELETE) {
			return;
		}

		// If song is already in list, do nothing
		if (addedSongIds.contains(value)) {
			return;
		}

		if (deletedSongIds.contains(value)) {
			deletedSongIds.remove(value);
		}
		addedSongIds.add(value);
	}

	public void removeSongList(List<Song> songList) {
		for (Song song : songList) {
			removeSong(song);
		}
	}

	public void removeSong(Song song) {
		// If action is delete or add, do nothing
		Integer value = new Integer(song.id);
		if (actionType != ACTION_UPDATE) {
			return;
		}

		// If song is already in list, do nothing
		if (deletedSongIds.contains(value)) {
			return;
		}

		if (addedSongIds.contains(value)) {

			addedSongIds.remove(value);
		}
		deletedSongIds.add(value);

	}

	public String getMsisdn() {
		return userMsisdn;
	}

	public int getType() {
		return actionType;
	}

	public int getLocalId() {
		return localId;
	}

	public int getServerId() {
		return serverId;
	}

	public List<Integer> getDeletedSongs() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.addAll(deletedSongIds);
		return list;
	}

	public String getDeletedSongsAsString() {
		return convertListToString(deletedSongIds);
	}

	public List<Integer> getAddedSongs() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.addAll(addedSongIds);
		return list;
	}

	public String getAddedSongsAsString() {
		return convertListToString(addedSongIds);
	}

	public SyncUpstreamPlaylist toUpstreamPlaylist() {
		SyncUpstreamPlaylist aUpdatedPlaylist = new SyncUpstreamPlaylist();
		aUpdatedPlaylist.iLocalId = localId;
		aUpdatedPlaylist.iServerId = serverId;
		aUpdatedPlaylist.iType = playlistType;
		aUpdatedPlaylist.sName = playlistTitle;
		aUpdatedPlaylist.aAddedSongListIds.addAll(addedSongIds);
		aUpdatedPlaylist.aDeletedSongListIds.addAll(deletedSongIds);
		return aUpdatedPlaylist;
	}

	public boolean mergeWithOldEntry(CacheEntry oldEntry) {
		if (oldEntry.localId != localId) {
			return false;
		}

		if (actionType == ACTION_DELETE) {
			return true;
		}

		if ((oldEntry.actionType == ACTION_ADD) && (actionType == ACTION_UPDATE)) {
			actionType = ACTION_ADD;
		}

		if ((oldEntry.actionType == ACTION_UPDATE) && (actionType == ACTION_ADD)) {
			// Crazy here
			VegaLog.e(LOG_TAG, "Crazy thing here, new cache is add type while old cache is update!!!");
			return false;
		}

		ArrayList<Integer> mergeAdded = new ArrayList<Integer>();
		mergeAdded.addAll(oldEntry.addedSongIds);
		mergeAdded.addAll(addedSongIds);
		addedSongIds = mergeAdded;

		ArrayList<Integer> mergeDeleted = new ArrayList<Integer>();
		mergeDeleted.addAll(oldEntry.deletedSongIds);
		mergeDeleted.addAll(deletedSongIds);
		deletedSongIds = mergeDeleted;
		return true;
	}

	/***********************************************************************************
	 * 
	 * INTERNAL FUNCTIONS
	 * 
	 ***********************************************************************************/

	protected List<Integer> convertStringToList(String string) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if ((string != null) && (string.trim().length() > 0)) {
			String[] med = string.trim().split(",");
			for (int i = 0; i < med.length; i++) {
				try {
					result.add(Integer.parseInt(med[i]));
				} catch (Throwable t) {
				}
			}
		}
		return result;
	}

	protected String convertListToString(List<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for (Integer id : list) {
			if (sb.length() == 0) {
				sb.append("" + id);
			} else {
				sb.append("," + id);
			}
		}
		return sb.toString();
	}
}
