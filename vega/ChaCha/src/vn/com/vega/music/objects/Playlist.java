package vn.com.vega.music.objects;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

public class Playlist {

	private static final String LOG_TAG = Const.LOG_PREF
			+ Playlist.class.getSimpleName();

	public static final int TYPE_DOWNLOADED = -3;
	public static final int TYPE_LOCAL_SONGS = -2;
	public static final int TYPE_NOW_PLAYING = -1;
	public static final int TYPE_USER_CREATED = 0;
	public static final int TYPE_FAVORITE = 1;
	public static final int TYPE_INBOX = 2;
	public static final int TYPE_FAVOURITE_EX = 3;

	public int id;
	public int serverId;
	public int type;
	public String lastUpdate;
	public String userMsisdn;
	public String title;
	public boolean waitDownload;

	// chacha
	public int total_song = 0;
	public String userName;
	public int viewCount = 0;
	public String userThumb;
	public ArrayList<String> thumbnails = new ArrayList<String>();
	protected ArrayList<Song> songList = new ArrayList<Song>();

	public Playlist(String user_msisd, String title, int type) {
		this.userMsisdn = user_msisd;
		this.title = title;
		this.type = type;
	}

	public Playlist() {
	}

	public String getTitle() {
		return title;
	}

	public int count() {
		return songList.size();
	}

	public Song getSongAt(int index) {
		if ((index < 0) || (index >= songList.size())) {
			VegaLog.e(LOG_TAG, "getSongAt index " + index + " out of bound");
			return null;
		}
		return songList.get(index);
	}

	public List<Song> getSongList() {
		return songList;
	}
	
	public void setSongList(List<Song> songs){
		songList.clear();
		for(Song song :songs){
			songList.add(song);
		}
	}

	public int countSongWaitToDownload() {
		int counter = 0;
		for (Song song : songList) {
			if (!song.isAvailableLocally() && song.isWaitToDownload()) {
				counter++;
			}
		}
		return counter;

	}

	public Song nextSongWaitToDownload(int skipSongId) {
		for (Song song : songList) {
			if (!song.isAvailableLocally() && song.isWaitToDownload()
					&& (song.id != skipSongId)) {
				return song;
			}
		}
		return null;
	}

	public boolean isAllSongCached() {
		if (songList.size() == 0) {
			return false;
		}
		for (Song song : songList) {
			if (!song.isAvailableLocally()) {
				return false;
			}
		}
		return true;
	}

	public boolean isAllSongCachedOrWaitDownload() {
		for (Song song : songList) {
			if (!song.isAvailableLocally() && !song.isWaitToDownload()) {
				return false;
			}
		}
		return true;
	}

	public int countSongCached() {
		int count = 0;
		for (Song song : songList) {
			if (song.isAvailableLocally()) {
				count++;
			}
		}
		return count;
	}

	public void add(Song s) {
		// TODO Auto-generated method stub
		songList.add(s);
	}

	public void addAll(List<Song> songs) {
		if (songs != null) {
			songList.addAll(songs);
		}
	}

	public void remove(Song s) {
		// TODO Auto-generated method stub
		songList.remove(s);
	}

	public void clearSongs() {
		songList.clear();
	}

	public boolean onSyncCopyInfoFrom(Playlist other) {
		if (id != other.id) {
			return false;
		}

		serverId = other.serverId;
		type = other.type;
		lastUpdate = other.lastUpdate;
		userMsisdn = other.userMsisdn;
		title = other.title;

		// Don't copy waitDownload

		return true;
	}
}
