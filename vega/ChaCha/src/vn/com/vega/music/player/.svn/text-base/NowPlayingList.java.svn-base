package vn.com.vega.music.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.util.Log;

public class NowPlayingList implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_LOCAL = 1;
	private static final String LOG_TAG = Const.LOG_PREF + DataStore.class.getSimpleName();
	private static int listType = TYPE_NORMAL;
	private static int songCurrentIndex = 0;
	private static ArrayList<Song> songArrayList = new ArrayList<Song>();
	public static boolean isNewList = false;

	public static void init(int _type, int _songCurrentIndex, ArrayList<Song> _songArrayList) {
		listType = _type;
		songCurrentIndex = _songCurrentIndex;

		if (_songArrayList == null || _songArrayList.isEmpty()) {
			return;
		} else {
			if (!songArrayList.isEmpty())
				songArrayList.clear();
		}

		for (Song _song : _songArrayList) {
			songArrayList.add(_song);
		}
		saveNowPlayingList();

		isNewList = true;
	}

	public static void reset() {
		listType = TYPE_NORMAL;
		songCurrentIndex = 0;
		if (!songArrayList.isEmpty())
			songArrayList.clear();
		songArrayList = new ArrayList<Song>();
		isNewList = false;
	}

	public static void setPlayFlag(boolean _playFlag) {
		isNewList = _playFlag;
	};

	public static boolean getPlayFlag() {
		return isNewList;
	};

	public NowPlayingList() {
		songArrayList = new ArrayList<Song>();
	};

	public static int getSongCurrentIndex() {
		return songCurrentIndex;
	}

	public static int setSongCurrentIndex(int _index) {
		return songCurrentIndex = _index;
	}

	public static boolean addSong(Song _song) {
		boolean result = true;
		if (listType == TYPE_NORMAL) {
			DataStore dataStore = DataStore.getInstance();
			Playlist nowPlayingList = dataStore.getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);
			// create now playing list
			if (nowPlayingList == null) {
				nowPlayingList = new Playlist();
				nowPlayingList.type = Playlist.TYPE_NOW_PLAYING;
				nowPlayingList.title = "Now Playing List";
				dataStore.addPlaylist(nowPlayingList, false);
			}
			result = dataStore.addSongToPlaylist(nowPlayingList.id, _song.id);
			if (result == true) {
				songArrayList.add(_song);
			}
		} else {
			result = false;
		}
		return result;
	}

	public static boolean removeSong(Song _song) {
		boolean result = true;
		if (listType == TYPE_NORMAL) {
			DataStore dataStore = DataStore.getInstance();
			Playlist nowPlayingList = dataStore.getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);
			if (nowPlayingList != null) {
				List<Song> nowPlayingListSongs = nowPlayingList.getSongList();
				int total = nowPlayingListSongs.size();
				if (total > 0) {
					result = dataStore.removeSongFromPlaylist(nowPlayingList.id, _song.id);
				} else {
					result = false;
				}
				if (result == true) {
					songArrayList.remove(_song);
				}
			}
		} else {
			result = false;
		}
		return result;
	}
	
	public static boolean reorderSong(int from, int to) {		
		if (listType != TYPE_NORMAL)
			return false;
		
		// reorder
		Song s = getSongAtIndex(from);
		songArrayList.remove(from);
		songArrayList.add(to, s);
		saveNowPlayingList();
		
		// update current song index
		if (from == songCurrentIndex)
			songCurrentIndex = to;
		else if (from < songCurrentIndex && songCurrentIndex <= to)
			songCurrentIndex--;
		else if (from > songCurrentIndex && songCurrentIndex >= to)
			songCurrentIndex++;
		
		return true;
	}

	public static int getListType() {
		return listType;
	}

	public static ArrayList<Song> getNormalSongList() {
		return songArrayList;
	}

	public static int getCount() {
		return songArrayList.size();
	}

	public static Song getSongAtIndex(int _position) {
		Song _song = null;
		if (!songArrayList.isEmpty()) {
			_song = songArrayList.get(_position);
		}
		return _song;
	}

	public static Song getPlaylingSong() {
		Song _song = null;
		if (!songArrayList.isEmpty()) {
			_song = songArrayList.get(songCurrentIndex);
		}
		return _song;
	}
	
	public static boolean clear() {
		boolean result = true;
		DataStore dataStore = DataStore.getInstance();
		Playlist nowPlayingList = dataStore.getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);
		// create now playing list
		if (nowPlayingList == null) {
			return false;
		}
		List<Song> nowPlayingListSongs = nowPlayingList.getSongList();
		int total = nowPlayingListSongs.size();
		if (total > 0) {
			// Clear old list song
			int[] songIds = new int[total];
			int i = 0;
			for (Song _song : nowPlayingListSongs) {
				songIds[i] = _song.id;
				i++;
			}
			result = dataStore.removeListSongFromPlaylist(nowPlayingList.id, songIds);
		}
		if (result) {
			songArrayList.clear();
		}
		return result;
	}

	public static boolean isEmpty() {
		return songArrayList.isEmpty();
	}

	public static boolean isExist(int _songId) {
		boolean result = false;
		for (Song _song : songArrayList) {
			if (_songId == _song.id) {
				result = true;
				break;
			}
		}
		return result;
	}

	// save the current state : current song,current position
	private static void saveNowPlayingList() {
		new Thread() {
			@Override
			public void run() {
				DataStore dataStore = DataStore.getInstance();
				Playlist nowPlayingList = dataStore.getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);
				// create now playing list
				if (nowPlayingList == null) {
					nowPlayingList = new Playlist();
					nowPlayingList.type = Playlist.TYPE_NOW_PLAYING;
					nowPlayingList.title = "Now Playing List";
					dataStore.addPlaylist(nowPlayingList, false);
				}
				List<Song> nowPlayingListSongs = nowPlayingList.getSongList();

				int total = nowPlayingListSongs.size();
				if (total > 0) {
					// Clear old list song
					int[] songIds = new int[total];
					int i = 0;
					for (Song _song : nowPlayingListSongs) {
						songIds[i] = _song.id;
						i++;
					}

					dataStore.removeListSongFromPlaylist(nowPlayingList.id, songIds);

				} else {
					Log.d(LOG_TAG, "saveNowPlayingList nowPlayingListSongs: empty");
				}
				if (songArrayList.size() > 0 && listType == TYPE_NORMAL) {
					dataStore.addListSongToPlaylist(nowPlayingList.id, songArrayList, false);
				}
			}
		}.start();

	}

	// save the current state : current song,current position
	public static boolean loadNowPlayingList() {
		boolean result = true;
		DataStore dataStore = DataStore.getInstance();
		Playlist nowPlayingList = dataStore.getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);

		// load saved play list from DB
		if (nowPlayingList != null) {
			ArrayList<Song> _songArrayList = (ArrayList<Song>) nowPlayingList.getSongList();
			if (!_songArrayList.isEmpty()) {
				NowPlayingList.init(NowPlayingList.TYPE_NORMAL, 0, _songArrayList);
			} else {
				Log.d(LOG_TAG, "loadNowPlayingList() _songArrayList isEmpty");
				result = false;
			}
		} else {
			Log.d(LOG_TAG, "loadNowPlayingList() nowPlayingList is null");
			result = false;
		}
		// Load default: LOCAL SONG
		if (result == false) {
			ArrayList<Song> _localSongList = (ArrayList<Song>) dataStore.loadAllLocalSong();
			if (_localSongList != null) {
				NowPlayingList.init(NowPlayingList.TYPE_LOCAL, 0, _localSongList);
				result = true;
			}
		}

		return result;
	}
}
