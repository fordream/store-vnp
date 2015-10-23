package vn.com.vega.music.objects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.VegaLog;

@SuppressWarnings("serial")
public class DatabasePlaylist extends Playlist {

	private static final String LOG_TAG = Const.LOG_PREF + Playlist.class.getSimpleName();

	protected Hashtable<Integer, Song> hashSongs = new Hashtable<Integer, Song>();

	public DatabasePlaylist(String user_msisd, String _title, int _type) {
		super(user_msisd, _title, _type);
	}

	/**
	 * Set song list on start up
	 * 
	 * @param list
	 * @return
	 */
	public boolean onStartupSetSongList(List<Song> list) {
		ArrayList<Song> listSongEntry = new ArrayList<Song>();
		Hashtable<Integer, Song> hashSongEntry = new Hashtable<Integer, Song>();
		for (Song song : list) {
			if (hashSongEntry.containsKey(song.id)) {
				return false;
			}
			hashSongEntry.put(song.id, song);
			listSongEntry.add(song);
		}
		songList = listSongEntry;
		hashSongs = hashSongEntry;
		return true;
	}

	/**
	 * @param song
	 */
	public boolean appendSong(Song song) {
		if (song == null) {
			VegaLog.e(LOG_TAG, "Input null");
			return false;
		}
		if (hashSongs.containsKey(song.id)) {
			VegaLog.v(LOG_TAG, "Adding an already exist song into playlist");
			return false;
		}
		hashSongs.put(song.id, song);
		songList.add(song);
		return true;
	}

	/**
	 * @param songId
	 */
	public boolean removeSong(int songId) {
		Song cached = hashSongs.get(songId);
		if (cached == null) {
			VegaLog.v(LOG_TAG, "Deleting an non-exist song from playlist");
			return false;
		}
		songList.remove(cached);
		hashSongs.remove(songId);
		return true;
	}

	/**
	 * @param title
	 */
	public void setTitle(String _title) {
		this.title = _title;
	}
}
