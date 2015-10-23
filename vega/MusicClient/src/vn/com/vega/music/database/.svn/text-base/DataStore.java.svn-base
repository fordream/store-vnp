package vn.com.vega.music.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.SyncDownstreamPackage;
import vn.com.vega.music.clientserver.SyncDownstreamPlaylist;
import vn.com.vega.music.clientserver.SyncIdRegisterResult;
import vn.com.vega.music.clientserver.SyncUpstreamPackage;
import vn.com.vega.music.device.DeviceIntegrator;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.CacheEntry;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.content.Context;
import android.util.Log;

/**
 * 
 * @author khainv
 * 
 */
public class DataStore implements Const {

	private static final String LOG_TAG = Const.LOG_PREF
			+ DataStore.class.getSimpleName();

	protected static DataStore _sharedInstance = null;
	protected static Object _syncObject = new Object();
	protected static Object _staticObject = new Object();

	private Context mContext;

	private static DatabaseManager mDbManager;

	public static DataStore getInstance() {
		synchronized (_staticObject) {
			return _sharedInstance;
		}
	}

	public static void init(Context context) {
		synchronized (_staticObject) {
			if (_sharedInstance == null) {
				_sharedInstance = new DataStore(context.getApplicationContext());
				_sharedInstance.initInternal();
			}
		}
	}

	public static void reinit(Context context) {
		synchronized (_staticObject) {
			if (_sharedInstance != null) {
				_sharedInstance.reinitInternal();
			}
		}
	}

	public static void uninit() {
		synchronized (_staticObject) {
			if (_sharedInstance != null) {
				_sharedInstance.release();
				_sharedInstance = null;
			}
		}
	}

	/**
	 * Hiding constructor
	 */
	private DataStore(Context context) {
		mContext = context;
		mDbManager = DatabaseManager.getWritableInstance(mContext);
	}

	/**
	 * Release all internal objects
	 */
	private void release() {
		if ((mDbManager != null) && mDbManager.isOpen()) {
			mDbManager.close();
		}
		mDbManager = null;
		iDBOpenCount = 0;
	}

	private int iDBOpenCount = 0;

	private void checkOpenDatabase() {
		if (mDbManager == null) {
			mDbManager = DatabaseManager.getWritableInstance(mContext);
		}
		if (!mDbManager.isOpen()) {
			mDbManager.open();
		}
		iDBOpenCount++;
	}

	private void checkCloseDatabase() {
		iDBOpenCount--;
		if ((iDBOpenCount == 0) && mDbManager.isOpen()) {
			mDbManager.close();
		}
	}

	private void initInternal() {
		this.checkOpenDatabase();
		this.initUsersAndSettings();
		this.initSong();
		this.initPlaylist();
		this.initSync();
		this.checkCloseDatabase();
	}

	private void reinitInternal() {
		this.checkOpenDatabase();
		this.reinitUsersAndSettings();
		this.initSync();
		this.reinitPlaylist();
		this.checkCloseDatabase();
	}

	/****************************************************************************************
	 * 
	 * NOTIFICATION
	 * 
	 ****************************************************************************************/

	/**
	 * Listener pools
	 */
	protected Hashtable<Integer, ArrayList<SongStatusListener>> hashSongStatusListeners = new Hashtable<Integer, ArrayList<SongStatusListener>>();
	protected Hashtable<Integer, ArrayList<PlaylistStatusListener>> hashPlaylistStatusListeners = new Hashtable<Integer, ArrayList<PlaylistStatusListener>>();

	/**
	 * Add listener to song status change events.
	 * 
	 * @param ssl
	 *            : listener
	 * @param songid
	 *            : id of song to listen, 0 if listen to all song
	 */
	public boolean addSongChangeListener(SongStatusListener ssl, int songId) {
		if (songId < 0) {
			return false;
		} else {
			Integer id = new Integer(songId);
			ArrayList<SongStatusListener> pool = hashSongStatusListeners
					.get(id);
			if (pool == null) {
				pool = new ArrayList<SongStatusListener>();
				hashSongStatusListeners.put(id, pool);
			}
			if (!pool.contains(ssl)) {
				pool.add(ssl);
			}
			return true;
		}
	}

	/**
	 * Add listener to playlist status change events.
	 * 
	 * @param psl
	 *            : listener
	 * @param playlistId
	 *            : id of playlist to listen, 0 if listen to all playlist
	 */
	public boolean addPlaylistChangeListener(PlaylistStatusListener psl,
			int playlistId) {
		if (playlistId < 0) {
			return false;
		} else {
			Integer id = new Integer(playlistId);
			ArrayList<PlaylistStatusListener> pool = hashPlaylistStatusListeners
					.get(id);
			if (pool == null) {
				pool = new ArrayList<PlaylistStatusListener>();
				hashPlaylistStatusListeners.put(id, pool);
			}
			if (!pool.contains(psl)) {
				pool.add(psl);
			}
			return true;
		}
	}

	/****************************************************************************************
	 * 
	 * PLAYLIST
	 * 
	 ****************************************************************************************/

	/**
	 * Caching playlists
	 */
	protected Hashtable<Integer, Playlist> hashPlaylists = new Hashtable<Integer, Playlist>();

	/**
	 * Get list of all playlist including user created, inbox and favorite
	 * playlist
	 * 
	 * @return List of Playlist object
	 */

	public List<Playlist> getListPlaylist() {
		ArrayList<Playlist> result = new ArrayList<Playlist>();
		ArrayList<Playlist> temp = new ArrayList<Playlist>();
		synchronized (this) {
			try {
				for (Playlist playlist : hashPlaylists.values()) {

					// Hoai Ngo: 2011-10-19 Hey, look at the function
					// description,
					// only
					// return user-created, inbox, fav and local (I guess?)
					/*
					 * if (playlist.type == Playlist.TYPE_FAVORITE ||
					 * playlist.type == Playlist.TYPE_INBOX || playlist.type ==
					 * Playlist.TYPE_LOCAL_SONGS || playlist.type ==
					 * Playlist.TYPE_USER_CREATED) { result.add(playlist); }
					 */
					if (playlist.type == Playlist.TYPE_INBOX
							|| playlist.type == Playlist.TYPE_FAVOURITE_EX
							|| playlist.type == Playlist.TYPE_LOCAL_SONGS
							|| playlist.type == Playlist.TYPE_USER_CREATED) {
						result.add(playlist);
					} else if (playlist.type == Playlist.TYPE_FAVORITE) {
						temp.add(playlist);
					}
				}
				if (temp.size() > 1) {
					Playlist firstPlaylist = temp.get(0);
					for (int i = 1; i < temp.size(); i++) {
						Playlist playlist = temp.get(i);
						firstPlaylist.addAll(playlist.getSongList());
					}
					result.add(firstPlaylist);
				} else if (temp.size() == 1 && temp.get(0) != null)
					result.add(temp.get(0));
				else {
					Playlist favPlaylist = new Playlist();
					favPlaylist.type = Playlist.TYPE_FAVORITE;
					favPlaylist.title = "Favourite playlist";
					result.add(favPlaylist);
					addPlaylist(favPlaylist, false);
				}

			} catch (Exception e) {
				// TODO: handle exception
				return result;
			}
		}
		return result;
	}

	/**
	 * Get a playlist based on it's id
	 * 
	 * @param playlistId
	 * @return
	 */
	public Playlist getPlaylistByID(int playlistId) {
		// Retrieve playlist from cache
		if (playlistId < 0) {
			Log.e(LOG_TAG, "playlist id must be non-negative number");
			return null;
		}
		synchronized (this) {
			return hashPlaylists.get(playlistId);
		}
	}

	public boolean checkOnlinePlaylist(int serverId) {
		boolean result = false;

		for (Playlist pl : hashPlaylists.values()) {
			if (pl.type == Playlist.TYPE_FAVOURITE_EX
					&& pl.serverId == serverId) {
				result = true;
				break;
			}
		}

		return result;
	}

	public List<Playlist> getUserCreatedPlaylistList() {
		ArrayList<Playlist> result = new ArrayList<Playlist>();

		synchronized (this) {
			for (Playlist playlist : hashPlaylists.values()) {
				if (playlist.type == Playlist.TYPE_USER_CREATED) {
					result.add(playlist);
				}
			}
		}
		return result;
	}

	/**
	 * Get a special playlist: inbox, favorite, now playing and local
	 * 
	 * @param type
	 *            - type of playlist
	 * @return
	 */
	public Playlist getSpecialPlaylistByType(int type) {
		// Hoai Ngo: 2011-10-19, we already have list of all playlist in memory
		// (in hash)!!!
		synchronized (this) {
			if (type == Playlist.TYPE_FAVORITE || type == Playlist.TYPE_INBOX
					|| type == Playlist.TYPE_LOCAL_SONGS
					|| type == Playlist.TYPE_NOW_PLAYING) {
				for (Playlist playlist : hashPlaylists.values()) {
					if (playlist.type == type) {
						return playlist;
					}
				}
			}
		}
		return null; // Ops, playlist not available

	}

	/*-----------------------------------------------------------------------------------
	 * Add playlist
	 -----------------------------------------------------------------------------------*/
	/**
	 * Add a list of playlist into database
	 * 
	 * @param playlists
	 * @return
	 */
	public boolean addPlaylist(List<Playlist> playlists) {
		if (playlists == null) {
			Log.e(LOG_TAG, "try to pass null list playlist");
			return false;
		}

		for (Playlist p : playlists) {
			addPlaylist(p);
		}
		return true;
	}

	/**
	 * User add a playlist After adding playlist into database & cache, it will
	 * send notification to listener >>>>>>> .r556
	 * 
	 * @param playlist
	 * @return
	 */
	public boolean addPlaylist(Playlist p) {
		return addPlaylist(p, true);
	}

	/**
	 * Actual addPlaylist implementor
	 * 
	 * @param p
	 * @param addChangeRecord
	 *            - create a change record or not
	 * @return
	 */
	public boolean addPlaylist(Playlist p, boolean addChangeRecord) {
		if (p == null) {
			Log.e("DataStore", "trying insert null playlist object");
			return false;
		}
		p.userMsisdn = mMsisdnStr;

		Playlist duplicated = null;
		// Check duplicate for special playlist
		if (p.type == Playlist.TYPE_FAVORITE) {
			synchronized (this) {
				for (Playlist old : hashPlaylists.values()) {
					if (old.type == p.type) {
						duplicated = old;
						break;
					}
				}
			}
		}
		// check duplicate for normal playlist
		if (p.type != Playlist.TYPE_FAVORITE) {
			synchronized (this) {
				for (Playlist old : hashPlaylists.values()) {
					if (old.serverId > 0 && old.serverId == p.serverId) {
						duplicated = old;
						break;
					}
				}
			}
		}
		// If duplicated with existing playlist, update to existing playlist
		if (duplicated != null) {
			Log.d("DataStore", "Duplicate playlist with type "
					+ duplicated.type);

			checkOpenDatabase();

			List<Song> songList = p.getSongList();
			// duplicated.clearSongs();

			if (duplicated.type == Playlist.TYPE_FAVORITE) {
				// duplicated.clearSongs();
				ArrayList<Song> toAddSong = new ArrayList<Song>();
				ArrayList<Song> toDelSong = new ArrayList<Song>();
				List<Song> cachedList = duplicated.getSongList();
				toDelSong.addAll(cachedList);
				for (Song song : songList) {

					boolean found = false;
					for (Song s : cachedList) {
						if (s.id == song.id) {
							toDelSong.remove(s);
							found = true;
							break;
						}
					}
					if (!found) {
						toAddSong.add(song);
					}
				}

				for (int i = 0; i < toAddSong.size(); i++) {
					Song song = toAddSong.get(i);
					if (mDbManager.addSongToPlaylist(song.id, duplicated.id,
							duplicated.count())) {
						duplicated.add(song);
					} else {
						Log.e(LOG_TAG, "Failed onSync adding song " + song.id
								+ " to playlist id " + duplicated.id);
					}
				}

				for (int i = 0; i < toDelSong.size(); i++) {
					Song song = toDelSong.get(i);
					if (mDbManager.removeSongFromPlaylist(song.id,
							duplicated.id)) {
						duplicated.remove(song);
					} else {
						Log.e(LOG_TAG, "Failed onSync removing song " + song.id
								+ " from playlist id " + duplicated.id);
					}
				}

			} else
				for (Song song : songList) {
					addSongToPlaylist(duplicated.id, song, addChangeRecord);
				}
			p.id = duplicated.id;
			if (duplicated.serverId > 0) {
				p.serverId = duplicated.serverId;
			} else {
				duplicated.serverId = p.serverId;
				synchronized (this) {
					mDbManager.updatePlaylistInfo(duplicated);
				}
			}

			checkCloseDatabase();
			return true;
		}

		boolean result = false;
		checkOpenDatabase();
		synchronized (this) {

			int id = mDbManager.addPlaylist(p);
			if (id >= 0) {
				p.id = id; // get playlist id(local id)
				// add all song in playlist to database if exits
				List<Song> songList = p.getSongList();
				for (int i = 0; i < songList.size(); i++) {
					Song song = songList.get(i);
					if (!hashSong.containsKey(song.id)) {
						if (mDbManager.addSong(song)) {
							hashSong.put(song.id, song);
						}
					}
					if (!mDbManager.addSongToPlaylist(song.id, p.id, i)) {
						// Error
						Log.e("DataStore", "insert song " + song.id
								+ " to playlist '" + p.title
								+ "' not successfull");
					}
				}

				hashPlaylists.put(p.id, p); // update to hash playlist
				result = true;

				// Create change record
				if (addChangeRecord) {
					CacheEntry ce = CacheEntry.createAddPlaylistCacheEntry(p);
					if (ce != null)
						mDbManager.addCached(ce);
				}

			} else {
				Log.e("DataStore", "insert Playlist '" + p.title
						+ "' not successfull");
			}
		}
		checkCloseDatabase();

		// notify here
		if (result) {
			if (listener != null)
				listener.onPlaylistAdded(null);
		}

		return result;
	}

	private static PlaylistStatusListener listener;

	public static void setPlaylistStatusListener(
			PlaylistStatusListener _listener) {
		listener = _listener;
	}

	public static void clearPlaylistStatusListener() {
		listener = null;
	}

	/*-----------------------------------------------------------------------------------
	 * Remove playlist
	 -----------------------------------------------------------------------------------*/
	/**
	 * User delete a playlist After deleting playlist from database & cache, it
	 * will send notification to listener
	 * 
	 * @param playlistId
	 * @return
	 */
	public boolean removePlaylist(int playlistId) {
		return removePlaylist(playlistId, true);
	}

	/**
	 * Actual removePlaylist impelemntor
	 * 
	 * @param playlistId
	 * @param addChangeRecord
	 *            - add change record or not
	 * @return
	 */
	public boolean removePlaylist(int playlistId, boolean addChangeRecord) {
		boolean result = true;
		Playlist playlist = null;
		synchronized (this) {
			playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG, "Deleting an non exist playlist id = "
						+ playlistId);
				return false;
			}

			checkOpenDatabase();
			// delete from playlist and songs_playlist table also from hash
			if (mDbManager.removePlaylist(playlistId)) {
				hashPlaylists.remove(playlistId);

				// Add change record
				if (addChangeRecord) {
					CacheEntry ce = mDbManager
							.getCacheEntryByPlaylistId(playlistId);
					if (ce != null) {
						ce.convertToDeleteType();
						mDbManager.updateCachedInfo(ce);
					} else {
						ce = CacheEntry
								.createDeletePlaylistCacheEntry(playlist);
						if (ce != null)
							mDbManager.addCached(ce);
					}
				}

			} else {
				result = false;
				Log.e(LOG_TAG, "Failed deleting playlist id = " + playlistId);
			}
		}

		// removeSong will use synchronized, so don't put into synchronized
		// block
		if (result) {
			// delete all song of playlist if it doesn't retain by another
			// project
			for (Song song : playlist.getSongList()) {
				removeSong(song.id);
			}
		}
		checkCloseDatabase();
		return result;
	}

	/*-----------------------------------------------------------------------------------
	 * Rename playlist
	 -----------------------------------------------------------------------------------*/
	/**
	 * User update a playlist including change name, change song list After
	 * updating playlist to database & cache, it will send notification to
	 * listener
	 * 
	 * @param updated
	 * @return
	 */
	public Playlist renamePlaylist(int playlistId, String title) {
		return renamePlaylist(playlistId, title, true);
	}

	/**
	 * Actual rename playlist implementor
	 * 
	 * @param playlistId
	 * @param title
	 * @param addChangeRecord
	 * @return
	 */
	protected Playlist renamePlaylist(int playlistId, String title,
			boolean addChangeRecord) {
		Playlist playlist = null;
		synchronized (this) {
			// Validate playlist id
			playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG, "Editing playlist with invalid playlist id = "
						+ playlistId);
				return null;
			}

			// If title null, rename not success and return current object
			if ((title == null) || (title.trim().length() == 0)) {
				Log.e(LOG_TAG, "Editing playlist with null or empty title");
				return playlist;
			}

			checkOpenDatabase();
			if (mDbManager.updatePlaylistTitle(playlistId, title)) {
				playlist.title = title;

				// Add change record
				if (addChangeRecord) {
					CacheEntry ce = mDbManager
							.getCacheEntryByPlaylistId(playlistId);
					if (ce != null) {
						ce.playlistTitle = title;
						mDbManager.updateCachedInfo(ce);
					} else {
						ce = CacheEntry.createUpdatePlaylistCacheEntry(
								playlist, null, null);
						if (ce != null)
							mDbManager.addCached(ce);
					}
				}

			} else {
				Log.e(LOG_TAG, "Failed renaming playlist id = " + playlistId);
			}
		}
		checkCloseDatabase();
		return playlist;
	}

	/*-----------------------------------------------------------------------------------
	 * Add song into playlist
	 -----------------------------------------------------------------------------------*/
	/**
	 * 
	 * @param playlistId
	 * @param songId
	 * @return
	 */
	public boolean addSongToPlaylist(int playlistId, int songId) {
		Song song = null;

		synchronized (this) {
			song = hashSong.get(songId);
			if (song == null) {
				return false;
			}
		}
		return addSongToPlaylist(playlistId, song);
	}

	public boolean addListSongToPlaylist(int playlistId, List<Song> songs,
			boolean addChangeRecord) {
		if (songs == null) {
			Log.e(LOG_TAG, "try to pass null list<song> object");
			return false;
		}

		boolean result = true;
		checkOpenDatabase();

		for (Song song : songs) {
			addSong(song);
			result = addSongToPlaylist(playlistId, song, addChangeRecord);
			if (!result) {
				break;
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * 
	 * @param song
	 * @param playlistId
	 * @return
	 */
	public boolean addSongToPlaylist(int playlistId, Song song) {
		return addSongToPlaylist(playlistId, song, true);
	}

	/**
	 * Actual add song to playlist implementor
	 * 
	 * @param playlistId
	 * @param song
	 * @param addChangeRecord
	 * @return
	 */
	public boolean addSongToPlaylist(int playlistId, Song song,
			boolean addChangeRecord) {
		// Do this to make sure
		addSong(song);

		boolean result = true;
		synchronized (this) {
			Playlist playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG, "Adding song to non-exist playlist. id = "
						+ playlistId);
				return false;
			}

			if (song == null) {
				Log.e(LOG_TAG, "Adding a null song to playlist");
				return false;
			}

			checkOpenDatabase();
			if (mDbManager.addSongToPlaylist(song.id, playlist.id, playlist
					.getSongList().size())) {
				playlist.add(song);

				// Add change record
				if (addChangeRecord) {
					CacheEntry ce = mDbManager
							.getCacheEntryByPlaylistId(playlistId);
					if (ce != null) {
						ce.addSong(song);
						mDbManager.updateCachedInfo(ce);
					} else {
						ce = CacheEntry.createUpdatePlaylistCacheEntry(
								playlist, null, null);
						if (ce != null) {
							ce.addSong(song);
							mDbManager.addCached(ce);
						}

					}
				}
			} else {
				result = false;
				Log.e(LOG_TAG, "Failed adding song " + song.id
						+ " to playlist " + playlistId);
			}
		}
		checkCloseDatabase();
		return result;
	}

	/*-----------------------------------------------------------------------------------
	 * Remove song from playlist
	 -----------------------------------------------------------------------------------*/
	/**
	 * @param playlistId
	 * @param listSongId
	 */
	public boolean removeListSongFromPlaylist(int playlistId, int[] songIds) {
		if (songIds == null || songIds.length == 0) {
			Log.e(LOG_TAG, "Failed removing from playlist " + playlistId
					+ " because putting null song ids list");
			return false;
		}

		boolean result = true;
		checkOpenDatabase();
		for (int songId : songIds) {
			if (!removeSongFromPlaylist(playlistId, songId)) {
				result = false;
			}
		}
		checkCloseDatabase();
		return result;
	}

	public boolean removeAllSongFromPlaylist(int playlistId) {
		boolean result = true;
		checkOpenDatabase();

		Playlist pl = getPlaylistByID(playlistId);
		List<Song> songs = new ArrayList<Song>();
		for (Song song : pl.getSongList()) {
			songs.add(song);
		}
		for (Song song : songs) {
			if (!removeSongFromPlaylist(playlistId, song.id, false)) {
				result = false;
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * 
	 * @param playlistId
	 * @param songId
	 */
	public boolean removeSongFromPlaylist(int playlistId, int songId) {
		return removeSongFromPlaylist(playlistId, songId, true);
	}

	/**
	 * Actual remove song from playlist implementor
	 * 
	 * @param playlistId
	 * @param songId
	 * @param addChangeRecord
	 * @return
	 */
	protected boolean removeSongFromPlaylist(int playlistId, int songId,
			boolean addChangeRecord) {
		boolean result = true;
		synchronized (this) {
			Playlist playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG,
						"playlist id, song id must be non-negative number");
				return false;
			}

			Song cached = null;
			for (Song song : playlist.getSongList()) {
				if (song.id == songId) {
					cached = song;
					break;
				}
			}
			if (cached == null) {
				Log.e(LOG_TAG, "Failed removing song " + songId
						+ " from playlist " + playlistId
						+ " because song does not belong to this playlist");
				return false;
			}

			checkOpenDatabase();
			if (mDbManager.removeSongFromPlaylist(songId, playlistId)) {
				playlist.remove(cached);

				// Add change record
				if (addChangeRecord) {
					CacheEntry ce = mDbManager
							.getCacheEntryByPlaylistId(playlistId);
					if (ce != null) {
						ce.removeSong(cached);
						mDbManager.updateCachedInfo(ce);
					} else {
						ce = CacheEntry.createUpdatePlaylistCacheEntry(
								playlist, null, null);
						if (ce != null) {
							ce.removeSong(cached);
							mDbManager.addCached(ce);
						}
					}
				}

			} else {
				Log.e(LOG_TAG, "Failed removing song " + songId
						+ " from playlist " + playlistId);
				result = false;
			}
		}
		if (result) {
			removeSong(songId); // This function will use synchronized, so don't
								// put into synchronized block
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Init playlists
	 */
	protected void initPlaylist() {
		checkOpenDatabase();
		synchronized (this) {
			List<Playlist> listPlaylists = mDbManager
					.getListPlaylist(mMsisdnStr);
			for (Playlist p : listPlaylists) {

				// Load song list
				int[] songids = mDbManager.getSongIdsByPlaylistId(p.id);
				if (songids != null) {
					for (int i = 0; i < songids.length; i++) {
						Song cached = hashSong.get(songids[i]);
						if (cached != null) {
							p.add(cached);
						}
					}
				}

				hashPlaylists.put(p.id, p); // Add to hash
			}
		}
		checkCloseDatabase();
	}

	/**
	 * Reinit playlists
	 */
	protected void reinitPlaylist() {
		checkOpenDatabase();
		synchronized (this) {
			hashPlaylists.clear();
			List<Playlist> listPlaylists = mDbManager
					.getListPlaylist(mMsisdnStr);
			for (Playlist p : listPlaylists) {

				// Load song list
				int[] songids = mDbManager.getSongIdsByPlaylistId(p.id);
				if (songids != null) {
					for (int i = 0; i < songids.length; i++) {
						Song cached = hashSong.get(songids[i]);
						if (cached != null) {
							p.add(cached);
						}
					}
				}

				hashPlaylists.put(p.id, p); // Add to hash
			}
		}
		checkCloseDatabase();
	}

	public void initNowPlayingList() {

		// Init Now Playing list
		Playlist playlist = getSpecialPlaylistByType(Playlist.TYPE_NOW_PLAYING);
		if (playlist == null) {
			Playlist nowPlayingList = new Playlist();
			nowPlayingList.type = Playlist.TYPE_NOW_PLAYING;
			nowPlayingList.title = "Now Playing List";
			addPlaylist(nowPlayingList);
		}

	}

	/****************************************************************************************
	 * 
	 * LOCAL SONGS
	 * 
	 ****************************************************************************************/

	protected List<Song> localSong = null;

	/**
	 * Get list of all local song, it would block current thread if needed
	 * 
	 * @return
	 */
	public List<Song> loadAllLocalSong() {
		if (localSong == null) {
			DeviceIntegrator di = new DeviceIntegrator(mContext);
			localSong = di.searchForLocalMusic();
		}
		return localSong;
	}

	public List<Song> loadLocalSongByArtist(String artist_id) {
		DeviceIntegrator di = new DeviceIntegrator(mContext);
		return di.searchForSongByArtist(artist_id);
	}

	public List<Song> loadDownloadedSongByArtist(int artist_id) {

		List<Song> results = new ArrayList<Song>();

		List<Song> songs = getListDownloadedSongs();
		for (Song song : songs) {
			if (song.artist_id == artist_id)
				results.add(song);
		}

		return results;
	}

	public List<Song> loadDownloadedSongByGenre(String genre_name) {

		List<Song> results = new ArrayList<Song>();

		List<Song> songs = getListDownloadedSongs();
		for (Song song : songs) {
			if (song.genre_name.equalsIgnoreCase(genre_name))
				results.add(song);
		}

		return results;
	}

	public List<Song> loadFavouriteSongByGenre(String genre_name) {

		List<Song> results = new ArrayList<Song>();

		Playlist pl = getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
		List<Song> songs = pl.getSongList();
		for (Song song : songs) {
			if (song.genre_name.equalsIgnoreCase(genre_name))
				results.add(song);
		}

		return results;
	}

	public List<Song> loadFavouriteSongByArtist(int artist_id) {

		List<Song> results = new ArrayList<Song>();
		Playlist pl = getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
		List<Song> songs = pl.getSongList();
		for (Song song : songs) {
			if (song.artist_id == artist_id)
				results.add(song);
		}

		return results;
	}

	public List<Song> loadSongByGenre(String genre_id) {
		DeviceIntegrator di = new DeviceIntegrator(mContext);
		return di.searchForSongByGenre(genre_id);
	}

	public List<Genre> loadGenre() {
		DeviceIntegrator di = new DeviceIntegrator(mContext);
		return di.searchForGenre();
	}

	public List<Artist> loadArtist() {
		DeviceIntegrator di = new DeviceIntegrator(mContext);
		return di.searchForArtist();
	}

	public List<Genre> loadGenreOfDownloadedMusic() {
		List<Genre> genres = new ArrayList<Genre>();
		List<Song> songs = getListDownloadedSongs();
		for (Song song : songs) {
			checkAndExtractGenre(genres, song);
		}

		return genres;
	}

	public List<Genre> loadGenreOfFavouriteMusic() {
		List<Genre> genres = new ArrayList<Genre>();
		Playlist pl = getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
		List<Song> songs = pl.getSongList();
		for (Song song : songs) {
			checkAndExtractGenre(genres, song);
		}

		return genres;
	}

	public List<Artist> loadArtistOfDownloadedMusic() {

		List<Artist> artists = new ArrayList<Artist>();

		List<Song> songs = getListDownloadedSongs();
		for (Song song : songs) {
			checkAndExtractArtist(artists, song);
		}

		return artists;
	}

	private void checkAndExtractArtist(List<Artist> artists, Song song) {
		if (artists.size() == 0) {
			Artist artist = new Artist();
			artist.id = song.artist_id;
			artist.name = song.artist_name;
			artist.songCount = 1;
			artists.add(artist);
		} else {

			boolean isExist = false;

			for (Artist artist : artists) {
				if (artist.id == song.artist_id) {
					isExist = true;
					artist.songCount = artist.songCount + 1;
					break;
				}

			}

			if (!isExist) {
				Artist artist = new Artist();
				artist.id = song.artist_id;
				artist.name = song.artist_name;
				artist.songCount = 1;
				artists.add(artist);
			}
		}
	}

	private void checkAndExtractGenre(List<Genre> genres, Song song) {
		if (genres.size() == 0) {
			Genre genre = new Genre();
			genre.name = song.genre_name;
			genre.songCount = 1;
			genres.add(genre);
		} else {

			boolean isExist = false;

			for (Genre genre : genres) {
				if (genre.name.equalsIgnoreCase(song.genre_name)) {
					isExist = true;
					genre.songCount = genre.songCount + 1;
					break;
				}

			}

			if (!isExist) {
				Genre genre = new Genre();
				genre.name = song.genre_name;
				genre.songCount = 1;
				genres.add(genre);
			}
		}
	}

	public List<Artist> loadArtistOfFavouriteMusic() {

		List<Artist> artists = new ArrayList<Artist>();
		Playlist pl = getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
		List<Song> songs = pl.getSongList();
		for (Song song : songs) {
			checkAndExtractArtist(artists, song);
		}

		return artists;
	}

	/****************************************************************************************
	 * 
	 * SONG
	 * 
	 ****************************************************************************************/

	/**
	 * Caching songs
	 */
	protected Hashtable<Integer, Song> hashSong = new Hashtable<Integer, Song>();

	/**
	 * Get full info of a song
	 * 
	 * @param songId
	 * @return Song object
	 */
	public Song getSongById(int songId) {
		if (songId < 0) {
			Log.e(LOG_TAG, "song id must be non-negative number");
			return null;
		}
		synchronized (this) {
			return hashSong.get(songId);
		}
	}

	/**
	 * Add a song into database & cache After adding song, it will send
	 * notification to listener
	 * 
	 * @param oSong
	 * @return
	 */
	public boolean addSong(Song s) {
		if (s == null) {
			Log.e(LOG_TAG, "Adding null song");
			return false;
		}
		Log.v(LOG_TAG, "Adding song " + s.id + ": " + s.title);

		boolean result = true;
		synchronized (this) {
			// Hoai Ngo: 2011-10-19, do check in cached information first
			// Check in hash
			Song cached = hashSong.get(s.id);
			if ((cached != null) && (!cached.isSameIdDiffInfo(s))) {
				Log.v(LOG_TAG, "Adding an already exist song");
				return true; // Already available with the same information,
								// don't
								// need to update
			}

			checkOpenDatabase();
			if (cached != null) {
				if (mDbManager.updateSongInfo(s)) {
					cached.updateDiffFrom(s);
				} else {
					Log.e(LOG_TAG, "Failed updating song " + s.id);
					result = false;
				}
			} else {
				if (mDbManager.addSong(s)) {
					hashSong.put(s.id, s);
				} else {
					Log.e(LOG_TAG, "Failed adding song " + s.id);
					result = false;
				}
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Check if song available in hash
	 * 
	 * @param s
	 * @return
	 */
	public boolean containSong(Song s) {
		return hashSong.contains(s);
	}

	public boolean addListSong(List<Song> songs) {
		if (songs == null) {
			Log.e(LOG_TAG, "Calling addListSong with null parameter");
			return false;
		}
		for (Song song : songs) {
			addSong(song);
		}
		return true;
	}

	/**
	 * Remove a song from database & cache After remove song, it will send
	 * notification to listener
	 * 
	 * IMPORTANCE: before removing song, it has to check if any other playlist
	 * is using this song. If there is any other playlist using this song, it
	 * should not remove song and return false
	 * 
	 * @param songId
	 * @return
	 */
	public boolean removeSong(int songId) {
		boolean result = true;
		synchronized (this) {
			Song cached = hashSong.get(songId);
			if (cached == null) {
				Log.e(LOG_TAG, "Removing song does not exist in hash");
				return false;
			}

			checkOpenDatabase();

			if (mDbManager.isRetainByAnotherPlaylist(songId)) {
				result = false;
				Log.v(LOG_TAG,
						"Removing a song existing in another playlist sid = "
								+ songId);
			} else {
				if (mDbManager.removeSong(songId) > 0) {
					hashSong.remove(songId);
				} else {
					result = false;
					Log.e(LOG_TAG, "Failed removing song " + songId);
				}
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Init songs
	 */
	protected void initSong() {
		checkOpenDatabase();
		synchronized (this) {
			List<Song> listSong = mDbManager.getListSong();
			for (Song s : listSong) {
				hashSong.put(s.id, s);
			}
		}
		checkCloseDatabase();
	}

	/****************************************************************************************
	 * 
	 * SONG & PLAYLIST DOWNLOAD
	 * 
	 ****************************************************************************************/

	/**
	 * Get list of playlist in download pool
	 */
	public List<Playlist> getPlaylistDownloadPool() {
		List<Playlist> entries = new ArrayList<Playlist>();
		synchronized (this) {
			for (Playlist playlist : hashPlaylists.values()) {
				if (playlist.waitDownload) {
					entries.add(playlist);
				}
			}
		}
		return entries;
	}

	/**
	 * Add a playlist into download pool
	 * 
	 * @param playlistId
	 * @return
	 */
	public boolean addPlaylistToDownloadPool(int playlistId) {
		Playlist playlist = null;
		synchronized (this) {
			playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG,
						"Adding non-exist playlist to download pool, pid = "
								+ playlistId);
				return false;
			}

			if (playlist.waitDownload) {
				Log.d(LOG_TAG,
						"Adding playlist to download pool which is already in pool, pid = "
								+ playlistId);
				return true;
			}

			checkOpenDatabase();
			if (mDbManager.markPlaylistBeDownload(playlistId)) {
				playlist.waitDownload = true;
			}
		}

		// Move them out of sync block to reduce work load
		if (!playlist.waitDownload) {
			Log.e(LOG_TAG,
					"Failed marking playlist as wait download to database, pid = "
							+ playlistId);
		}
		checkCloseDatabase();
		return playlist.waitDownload;
	}

	/**
	 * Remove a playlist from download pool
	 * 
	 * @param playlistId
	 * @return
	 */
	public boolean removePlaylistFromDownloadPool(int playlistId) {
		Playlist playlist = null;
		synchronized (this) {
			playlist = hashPlaylists.get(playlistId);
			if (playlist == null) {
				Log.e(LOG_TAG,
						"Removing non-exist playlist from download pool, pid = "
								+ playlistId);
				return false;
			}

			if (!playlist.waitDownload) {
				Log.d(LOG_TAG,
						"Removing playlist from download pool which is NOT in pool, pid = "
								+ playlistId);
				return true;
			}

			checkOpenDatabase();
			if (mDbManager.unmarkPlaylistBeDownload(playlistId)) {
				playlist.waitDownload = false;
			}
		}

		// Move them out of sync block to reduce work load
		if (playlist.waitDownload) {
			Log.e(LOG_TAG,
					"Failed un-marking playlist as wait download to database, pid = "
							+ playlistId);
		}
		checkCloseDatabase();
		return !playlist.waitDownload;
	}

	public void clearPlaylistDownloadPool() {
		synchronized (this) {
			for (Integer key : hashPlaylists.keySet()) {
				Playlist _playlist = hashPlaylists.get(key);
				if (_playlist.waitDownload) {
					removePlaylistFromDownloadPool(_playlist.id);
				}
			}
		}
	}

	// get all songs that downloaded/cached
	public List<Song> getListDownloadedSongs() {
		ArrayList<Song> result = new ArrayList<Song>();
		synchronized (this) {
			for (Integer key : hashSong.keySet()) {
				if (hashSong.get(key).isAvailableLocally() || isAvailableAtLocal(hashSong.get(key).id)) {
					result.add(hashSong.get(key));
				}
			}
		}

		int usedSize = (int) FileStorage.getUsedSize();

		if (result.size() == 0 && usedSize != 0) {
			reinitSong();
			synchronized (this) {
				for (Integer key : hashSong.keySet()) {
					if (hashSong.get(key).isAvailableLocally() || isAvailableAtLocal(hashSong.get(key).id)) {
						result.add(hashSong.get(key));
					}
				}
			}
		}

		return result;
	}

	/*
	 * reinit song
	 */

	protected void reinitSong() {
		checkOpenDatabase();
		synchronized (this) {
			hashSong.clear();
			List<Song> listSong = mDbManager.getListSong();
			for (Song s : listSong) {
				hashSong.put(s.id, s);
			}
		}
		checkCloseDatabase();
	}

	/**
	 * Mark a song status to be download
	 * 
	 * @param songId
	 * @return
	 */
	public boolean addSongToSongDownloadPool(int songId) {
		boolean result = true;
		synchronized (this) {
			Song cached = hashSong.get(songId);
			if (cached == null) {
				Log.e(LOG_TAG,
						"Adding non-exist song into download pool. Sid = "
								+ songId + ". Inserting...");

				return false;
			}

			if (cached.isWaitToDownload()) {
				Log.v(LOG_TAG, "Adding a song already in download pool. Sid = "
						+ songId);
				return true;
			}

			checkOpenDatabase();
			if (mDbManager.markSongBeDownload(songId)) {
				cached.wait_download = 1;
			} else {
				Log.e(LOG_TAG, "Failed adding song into download pool. Sid = "
						+ songId);
				result = false;
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Mark list of song status to be download
	 * 
	 * @param songList
	 * @return
	 */
	public int addSongsToSongDownloadPool(List<Song> songs) {
		int count = 0;
		if (songs == null) {
			return 0;
		}
		checkOpenDatabase();
		for (int i = 0; i < songs.size(); i++) {
			if (addSongToSongDownloadPool(songs.get(i).id)) {
				count++;
			}
		}
		checkCloseDatabase();
		return count;
	}

	// /**
	// * Mark list of song status to be download
	// *
	// * @param songList
	// * @return
	// */
	// public int addSongsToSongDownloadPool(int[] songIds) {
	// int count = 0;
	// if (songIds == null) {
	// return 0;
	// }
	// checkOpenDatabase();
	// for (int i = 0; i < songIds.length; i++) {
	// if (addSongToSongDownloadPool(songIds[i])) {
	// count++;
	// }
	// }
	// checkCloseDatabase();
	// return count;
	// }

	/**
	 * Unmark a song from wait download
	 * 
	 * @param songList
	 * @return
	 */
	public boolean removeSongFromSongDownloadPool(int songId) {
		boolean result = true;
		synchronized (this) {
			Song cached = hashSong.get(songId);
			if (cached == null) {
				Log.e(LOG_TAG,
						"Removing non-exist song into download pool. Sid = "
								+ songId);
				return false;
			}

			if (!cached.isWaitToDownload()) {
				Log.v(LOG_TAG, "Remove a song NOT in download pool. Sid = "
						+ songId);
				return true;
			}

			checkOpenDatabase();
			if (mDbManager.unMarkSongBeDownload(songId)) {
				cached.wait_download = 0;
			} else {
				Log.e(LOG_TAG,
						"Failed removing song from download pool. Sid = "
								+ songId);
				result = false;
			}
		}
		checkCloseDatabase();
		return result;
	}

	public int removeSongsFromSongDownloadPool(List<Song> songs) {
		int count = 0;
		if (songs == null) {
			return 0;
		}
		checkOpenDatabase();
		for (int i = 0; i < songs.size(); i++) {
			if (removeSongFromSongDownloadPool(songs.get(i).id)) {
				count++;
			}
		}
		checkCloseDatabase();
		return count;
	}

	// public int removeSongsFromSongDownloadPool(int[] songIds) {
	// int count = 0;
	// if (songIds == null) {
	// return 0;
	// }
	// checkOpenDatabase();
	// for (int i = 0; i < songIds.length; i++) {
	// if (removeSongFromSongDownloadPool(songIds[i])) {
	// count++;
	// }
	// }
	// checkCloseDatabase();
	// return count;
	// }

	public boolean clearSongDownloadPool() {
		boolean result = true;
		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.clearSongDownloadPool()) {
				for (Song song : hashSong.values()) {
					song.wait_download = 0;
				}
			} else {
				result = false;
				Log.e(LOG_TAG, "Failed clearing download pool");
			}
		}
		checkCloseDatabase();
		return result;
	}

	public List<Song> getSongDownloadPool() {
		List<Song> entries = new ArrayList<Song>();
		synchronized (this) {
			for (Song song : hashSong.values()) {
				if (song.isWaitToDownload()) {
					entries.add(song);
				}
			}
		}
		return entries;
	}

	public boolean isAvailableAtLocal(int songId) {
		boolean result = false;
		Song cached = hashSong.get(songId);
		if (cached == null) {
			result = false;
		} else {
			if ((cached.cached_file_path != null)
					&& (cached.cached_file_path.trim().length() > 0)) {
				result = true;
			} else
				result = false;
		}
		return result;
	}

	/**
	 * Set downloaded file path to a song It also mark this song status as
	 * downloaded
	 * 
	 * @param songId
	 * @param path
	 * @return
	 */
	public boolean setSongCachedPath(int songId, String path) {
		boolean result = true;
		synchronized (this) {
			Song cached = hashSong.get(songId);
			if (cached == null) {
				Log.e(LOG_TAG, "Setting cached path to non-exist song. Sid = "
						+ songId);
				return false;
			}

			checkOpenDatabase();
			if (mDbManager.setSongCachedPath(songId, path)) {
				cached.cached_file_path = path;
				cached.wait_download = 0;
			} else {
				Log.e(LOG_TAG, "Failed setting cached path to song Sid = "
						+ songId);
				result = false;
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Clear downloaded file path of this song It should also mark this song
	 * status as not downloaded
	 * 
	 * @param songId
	 * @return
	 */
	public boolean clearSongCachedPath(int songId) {
		boolean result = true;
		synchronized (this) {
			Song cached = hashSong.get(songId);
			if (cached == null) {
				Log.e(LOG_TAG,
						"Clearing cached path from non-exist song. Sid = "
								+ songId);
				return false;
			}

			if (!cached.isAvailableLocally()) {
				Log.v(LOG_TAG,
						"Clearing cached path from NON-CACHED song. Sid = "
								+ songId);
				return true;
			}

			checkOpenDatabase();
			if (mDbManager.setClearSongCachedPath(songId)) {
				cached.cached_file_path = "";
			} else {
				Log.e(LOG_TAG, "Failed clearing cached path to song Sid = "
						+ songId);
			}
		}
		checkCloseDatabase();
		return result;
	}

	public boolean clearAllSongCachedPath() {
		boolean result = true;
		synchronized (this) {
			for (Song cached : hashSong.values()) {
				if (cached == null) {
					Log.e(LOG_TAG,
							"Clearing cached path from non-exist song. Sid = ");
					continue;
				}

				if (!cached.isAvailableLocally()) {
					Log.v(LOG_TAG,
							"Clearing cached path from NON-CACHED song. Sid = "
									+ cached.id);
					continue;
				}

				checkOpenDatabase();
				if (mDbManager.setClearSongCachedPath(cached.id)) {
					cached.cached_file_path = "";
				} else {
					Log.e(LOG_TAG, "Failed clearing cached path to song Sid = "
							+ cached.id);
				}
			}
		}
		checkCloseDatabase();
		return result;
	}

	public boolean maskClearAllSongCachedPath() {
		boolean result = true;
		synchronized (this) {
			for (Song cached : hashSong.values()) {
				if (cached == null) {
					Log.e(LOG_TAG,
							"mask Clearing cached path from non-exist song. Sid = ");
					continue;
				}

				if (!cached.isAvailableLocally()) {
					Log.v(LOG_TAG,
							"mask Clearing cached path from NON-CACHED song. Sid = "
									+ cached.id);
					continue;
				}

				cached.cached_file_path = "";
			}
		}
		checkCloseDatabase();
		return result;
	}

	/**
	 * Internal init
	 */
	protected void initSongDownloadPool() {
	}

	/****************************************************************************************
	 * 
	 * SYNC
	 * 
	 * @author tuanhd
	 ****************************************************************************************/

	// protected int iLastVersion = 0;
	protected String sLastVersion = "0000-00-00 00:00:00";
	protected boolean hasNewData;
	protected boolean alreadySyncOne = false;

	/**
	 * Generate SyncUpstreamPackage for sync process
	 */
	public SyncUpstreamPackage generateUpstreamPackage() {
		SyncUpstreamPackage syncUpstreamPackage = new SyncUpstreamPackage();
		try {
			checkOpenDatabase();
			synchronized (this) {
				syncUpstreamPackage.cacheEntries.addAll(mDbManager
						.getListCached(mMsisdnStr));
				// mDbManager.removeChangeCaches();
				mDbManager.removeChangeCaches(mMsisdnStr);
			}
		} catch (Exception e) {
			Log.e(LOG_TAG,
					"Error generating upstream package: " + e.getMessage());
		} finally {
			checkCloseDatabase();
		}

		// syncUpstreamPackage.iCurrentVersion = iLastVersion;
		syncUpstreamPackage.sCurrentVersion = sLastVersion;
		if (syncUpstreamPackage.cacheEntries.size() == 0) {
			return syncUpstreamPackage;
		}

		for (CacheEntry cacheEntry : syncUpstreamPackage.cacheEntries) {
			switch (cacheEntry.getType()) {
			case CacheEntry.ACTION_ADD:
				syncUpstreamPackage.aAddedPlaylists.add(cacheEntry
						.toUpstreamPlaylist());
				break;

			case CacheEntry.ACTION_UPDATE:
				syncUpstreamPackage.aUpdatedPlaylists.add(cacheEntry
						.toUpstreamPlaylist());
				break;

			case CacheEntry.ACTION_DELETE:
				syncUpstreamPackage.aDeletedPlaylistIds.add(cacheEntry
						.getServerId());
				break;
			}
		}

		return syncUpstreamPackage;
	}

	public void revertSync(SyncUpstreamPackage sup) {
		// Do revert on sync failed

		// Create hash first
		Hashtable<Integer, CacheEntry> oldEntries = new Hashtable<Integer, CacheEntry>();
		for (CacheEntry entry : sup.cacheEntries) {
			oldEntries.put(entry.getLocalId(), entry);
		}
		checkOpenDatabase();
		synchronized (this) {
			List<CacheEntry> newEntries = mDbManager.getListCached(mMsisdnStr);
			for (CacheEntry newEntry : newEntries) {
				CacheEntry oldEntry = oldEntries.get(newEntry.getLocalId());
				if (oldEntry != null) {
					newEntry.mergeWithOldEntry(oldEntry);
					mDbManager.updateCachedInfo(newEntry);
					oldEntries.remove(oldEntry.getLocalId());
				}
			}

			Collection<CacheEntry> remainEntries = oldEntries.values();
			for (CacheEntry entry : remainEntries) {
				mDbManager.addCached(entry);
			}
		}
		checkCloseDatabase();
	}

	public boolean hasNewDataFromServer() {
		synchronized (this) {
			return hasNewData;
		}
	}

	public boolean isAlreadySyncOne() {
		synchronized (this) {
			return alreadySyncOne;
		}
	}

	/**
	 * Merge result from server to database & cache It should also clear mark of
	 * submitted item (playlists, songs) It should send notification to playlist
	 * & song listeners
	 * 
	 * @param downPackage
	 * @return
	 */
	public boolean mergeDownstreamPackage(SyncDownstreamPackage downPackage) {
		try {
			// Step checking
			// if (downPackage.iLastVersion <= iLastVersion) {
			if (downPackage.sLastVersion.compareToIgnoreCase(sLastVersion) < 0) { // <=
				hasNewData = false;
				return false;
			}
			// has new data
			alreadySyncOne = true;
			hasNewData = true;
			// Step prepare :
			checkOpenDatabase();
			// Step prepare :
			// updateLastVersion(downPackage.iLastVersion);
			updateLastVersion(downPackage.sLastVersion);
			// Step 1: insert new songs first
			if (downPackage.aNewSongs.size() > 0) {
				addListSong(downPackage.aNewSongs);
			}

			// Step 2: delete playlists
			ArrayList<Integer> aDeletedPlaylists = downPackage.aDeletedPlaylistIds;
			if (aDeletedPlaylists.size() > 0) {
				for (Integer id : aDeletedPlaylists) {
					Log.v(LOG_TAG, "OnSync Removing playlist " + id);
					// this is server id.
					removePlaylist(getLocalId(id), false);
				}
			}

			// Step 3: add new playlists
			ArrayList<SyncDownstreamPlaylist> aAddedPlaylists = downPackage.aAddedPlaylists;
			if (aAddedPlaylists.size() > 0) {
				for (SyncDownstreamPlaylist syncDownPlaylist : aAddedPlaylists) {
					// Playlist pl = convertSyncToPlaylist(syncDownPlaylist,
					// downPackage.iLastVersion);
					Playlist pl = convertSyncToPlaylist(syncDownPlaylist,
							downPackage.sLastVersion);
					Log.v(LOG_TAG, "OnSync Adding playlist " + pl.id + ": "
							+ pl.title);
					addPlaylist(pl, false); // Calling add playlist without
											// create change record
				}
			}
			
			ArrayList<Playlist> aFavPlaylists = downPackage.aFavPlaylist;
			if (aFavPlaylists.size() > 0) {
				for (Playlist favPlaylist : aFavPlaylists) {				
					addPlaylist(favPlaylist, false); // Calling add playlist without
											// create change record
				}
			}

			// Step 5: register playlist id
			ArrayList<SyncIdRegisterResult> syncIdRegs = downPackage.aIdRegisterResults;
			if (syncIdRegs.size() > 0) {
				for (SyncIdRegisterResult syncIdReg : syncIdRegs) {
					Log.v(LOG_TAG, "OnSync registering " + syncIdReg.iSubmitId
							+ " -> " + syncIdReg.iRegisteredId);
					onSyncRegisterPlaylist(syncIdReg);
				}
			}

			// Step 4: update playlists
			ArrayList<SyncDownstreamPlaylist> aUpdatedPlaylists = downPackage.aUpdatedPlaylists;
			if (aUpdatedPlaylists.size() > 0) {
				for (SyncDownstreamPlaylist syncDownPlaylist : aUpdatedPlaylists) {
					Log.v(LOG_TAG, "OnSync Updating playlist sid="
							+ syncDownPlaylist.iServerId + ": "
							+ syncDownPlaylist.sName);
					// onSyncUpdatePlaylist(syncDownPlaylist,
					// downPackage.iLastVersion);
					onSyncUpdatePlaylist(syncDownPlaylist,
							downPackage.sLastVersion);
				}
			}

			// Step 5: done!
			return true;
		} catch (Exception e) {
			Log.d(LOG_TAG,
					"MergeData: some errors occurred while merging data from server");
			return false;
		} finally {
			checkCloseDatabase();
		}
	}

	public int getLocalId(int serverId) {
		int iResult = -1;
		for (Playlist playlist : hashPlaylists.values()) {
			if (playlist.serverId == serverId) {
				iResult = playlist.id;
				return iResult;
			}
		}
		return iResult;
	}

	protected boolean onSyncRegisterPlaylist(SyncIdRegisterResult sirr) {
		boolean result = true;
		synchronized (this) {
			Playlist cached = hashPlaylists.get(sirr.iSubmitId);
			if ((cached == null) || (cached.serverId > 0)) {
				Log.e(LOG_TAG,
						"Found playlist register result with submit id unknown or already registered. "
								+ sirr.iSubmitId + " -> " + sirr.iRegisteredId);
				return false;
			}

			checkOpenDatabase();
			if (mDbManager.regIdForNewPlaylist(sirr.iSubmitId,
					sirr.iRegisteredId)) {
				cached.serverId = sirr.iRegisteredId;
			} else {
				Log.e(LOG_TAG, "Failed registering new playlist. "
						+ sirr.iSubmitId + " -> " + sirr.iRegisteredId);
				result = false;
			}
			checkCloseDatabase();
		}
		return result;
	}

	// protected boolean onSyncUpdatePlaylist(SyncDownstreamPlaylist sdp, int
	// version) {
	protected boolean onSyncUpdatePlaylist(SyncDownstreamPlaylist sdp,
			String version) {
		Playlist pl = convertSyncToPlaylist(sdp, version);
		Playlist cached = null;

		synchronized (this) {
			// Search for cached playlist
			for (Playlist playlist : hashPlaylists.values()) {
				if (playlist.serverId == pl.serverId) {
					cached = playlist;
					break;
				}
			}

			// Only update if found
			if (cached != null) {

				// Merge with new cache entry in database if found
				CacheEntry cacheEntry = mDbManager
						.getCacheEntryByPlaylistId(cached.id);
				if (cacheEntry != null) {
					for (Integer songId : cacheEntry.getAddedSongs()) {
						Song song = hashSong.get(songId);
						if (song != null) {
							pl.add(song);
						}
					}
					for (Integer songId : cacheEntry.getDeletedSongs()) {
						Song song = hashSong.get(songId);
						if (song != null) {
							pl.remove(song);
						}
					}
				}

				ArrayList<Song> toAddSong = new ArrayList<Song>();
				ArrayList<Song> toDelSong = new ArrayList<Song>();
				List<Song> cachedList = cached.getSongList();
				List<Song> newList = pl.getSongList();
				toDelSong.addAll(cachedList);
				for (Song song : newList) {

					boolean found = false;
					for (Song s : cachedList) {
						if (s.id == song.id) {
							toDelSong.remove(s);
							found = true;
							break;
						}
					}

					if (!found) {
						toAddSong.add(song);
					}

					// if (cachedList.contains(song)) {
					// // Exist in both two list
					// toDelSong.remove(song);
					// } else {
					// // Exist in new list only
					// toAddSong.add(song);
					// }
				}

				checkOpenDatabase();
				// Update playlist now
				pl.id = cached.id;
				if (mDbManager.updatePlaylistInfo(pl) != DatabaseManager.INVALID) {
					cached.onSyncCopyInfoFrom(pl);
				} else {
					Log.e(LOG_TAG, "Failed onSync update playlist id = "
							+ pl.id);
				}

				int bid = cached.count();
				for (int i = 0; i < toAddSong.size(); i++) {
					Song song = toAddSong.get(i);
					if (mDbManager.addSongToPlaylist(song.id, pl.id, bid + i)) {
						cached.add(song);
					} else {
						Log.e(LOG_TAG, "Failed onSync adding song " + song.id
								+ " to playlist id " + pl.id);
					}
				}

				for (int i = 0; i < toDelSong.size(); i++) {
					Song song = toDelSong.get(i);
					if (mDbManager.removeSongFromPlaylist(song.id, pl.id)) {
						cached.remove(song);
					} else {
						Log.e(LOG_TAG, "Failed onSync removing song " + song.id
								+ " from playlist id " + pl.id);
					}
				}
			}
		}

		// Not found to update?, add it
		if (cached == null) {
			return addPlaylist(pl, false); // Calling add without create change
											// record
		}
		checkCloseDatabase();
		return true;
	}

	// protected Playlist convertSyncToPlaylist(SyncDownstreamPlaylist sdp, int
	// version) {
	protected Playlist convertSyncToPlaylist(SyncDownstreamPlaylist sdp,
			String version) {
		Playlist playlist = new Playlist();
		playlist.lastUpdate = version;
		playlist.serverId = sdp.iServerId;
		playlist.userMsisdn = mMsisdnStr;
		playlist.type = sdp.iType;
		playlist.title = sdp.sName;
		// begin add song to playlist
		for (Integer songId : sdp.aSongListIds) {
			Song _song = getSongById(songId);

			// Song is missing
			if (_song == null) {
				JsonSong js = JsonSong.loadSongInfo(songId);
				if (js.isSuccess() && js.songs.size() > 0) {
					_song = js.songs.get(0);
				}
			}

			// Add song if available
			if (_song != null) {
				playlist.add(_song);
			}
		}
		return playlist;
	}

	// Set last update version
	// protected boolean updateLastVersion(int currVersion) {
	protected boolean updateLastVersion(String currVersion) {
		// if (currVersion < 0) {
		if (currVersion == null || currVersion.equalsIgnoreCase("")) {
			currVersion = "";
		}
		// iLastVersion = currVersion;
		sLastVersion = currVersion;
		Log.d(LOG_TAG, "Latest version" + currVersion);

		try {
			checkOpenDatabase();
			synchronized (this) {
				return mDbManager.updateConfigInfo(Const.CURRENT_VERSION, ""
						+ currVersion) == DatabaseManager.INVALID;
			}
		} catch (Exception e) {
			return false;
		} finally {
			checkCloseDatabase();
		}
	}

	/**
	 * Init sync
	 */
	protected void initSync() {
		checkOpenDatabase();
		synchronized (this) {
			try {
				// iLastVersion =
				// Integer.parseInt(mDbManager.getConfig(Const.CURRENT_VERSION));
				sLastVersion = mDbManager.getConfig(Const.CURRENT_VERSION);

				if (sLastVersion.trim().equals(""))
					sLastVersion = "0000-00-00 00:00:00";

			} catch (Throwable t) {
				// iLastVersion = 0;
				sLastVersion = "0000-00-00 00:00:00";
			}
		}
		checkCloseDatabase();
	}

	/****************************************************************************************
	 * 
	 * USERS & SETTINGS
	 * 
	 ****************************************************************************************/

	protected Hashtable<String, String> hashConfig = new Hashtable<String, String>();
	protected String mMsisdnStr = "";
	protected boolean mIsOfflineMode = false;
	protected boolean mIsDownloadVia3gMode = true;
	protected boolean mIsNotifiedWhenNewPlaylist = true;
	protected boolean mIsNotifiedWhenLikePlaylist = true;
	protected boolean mIsNotifiedWhenNewUpdateFromServer = true;
	protected boolean mIsNotifiedWhenSharing = true;
	protected boolean mIsNotifiedWhenNewUpdateFromArtist = true;
	protected String mPasswordStr = "";
	protected int mMemoryLimit = 100;
	protected String mPrice = "";

	protected String fb_access_token = "";
	protected String fb_access_expire = "";
	protected String gm_access_token = "";
	protected String gm_access_expire = "";

	/**
	 * Init users & settings
	 */
	protected void initUsersAndSettings() {
		checkOpenDatabase();
		mMsisdnStr = "";
		mPasswordStr = "";
		mIsOfflineMode = false;
		mIsDownloadVia3gMode = true;
		mIsNotifiedWhenNewPlaylist = true;
		mIsNotifiedWhenLikePlaylist = true;
		mIsNotifiedWhenNewUpdateFromArtist = true;
		mIsNotifiedWhenNewUpdateFromServer = true;
		mIsNotifiedWhenSharing = true;
		mPrice = "";
		synchronized (this) {
			try {
				mMsisdnStr = mDbManager.getConfig(Const.KEY_MSISDN);
				mPasswordStr = mDbManager.getConfig(Const.KEY_PASSWORD);
				mPrice = mDbManager.getConfig(Const.KEY_PRICE);

				hashConfig.put(KEY_LOGIN_NAME,
						mDbManager.getConfig(KEY_LOGIN_NAME));
				hashConfig.put(KEY_PACKAGE_CODE,
						mDbManager.getConfig(KEY_PACKAGE_CODE));
				hashConfig.put(KEY_BEGIN_DATE,
						mDbManager.getConfig(KEY_BEGIN_DATE));
				hashConfig
						.put(KEY_END_DATE, mDbManager.getConfig(KEY_END_DATE));
				hashConfig.put(KEY_ACCOUNT_INFO,
						mDbManager.getConfig(KEY_ACCOUNT_INFO));
				hashConfig.put(KEY_AVATAR, mDbManager.getConfig(KEY_AVATAR));
				hashConfig.put(KEY_FULL_NAME,
						mDbManager.getConfig(KEY_FULL_NAME));
				hashConfig.put(KEY_PHONE_NUMBER,
						mDbManager.getConfig(KEY_PHONE_NUMBER));
				hashConfig.put(KEY_PHONE_NUMBER_EDITABLE,
						mDbManager.getConfig(KEY_PHONE_NUMBER_EDITABLE));
				hashConfig.put(KEY_DOB, mDbManager.getConfig(KEY_DOB));
				hashConfig.put(KEY_GENDER, mDbManager.getConfig(KEY_GENDER));
				hashConfig.put(KEY_ADDRESS, mDbManager.getConfig(KEY_ADDRESS));
				hashConfig.put(KEY_EMAIL, mDbManager.getConfig(KEY_EMAIL));
				hashConfig.put(KEY_EMAIL_EDITABLE,
						mDbManager.getConfig(KEY_EMAIL_EDITABLE));
				
				//music quality
				hashConfig.put(Const.MUSIC_QUALITY_ID,
						mDbManager.getConfig(Const.MUSIC_QUALITY_ID));
				hashConfig.put(Const.MUSIC_QUALITY_VALUE,
						mDbManager.getConfig(Const.MUSIC_QUALITY_VALUE));
				

				String offline = mDbManager.getConfig(Const.OFFLINE_MODE);
				if ((offline != null)
						&& offline.equalsIgnoreCase(Const.VALUE_OFFLINE)) {
					mIsOfflineMode = true;
				}
				String flag_download_via_3g = mDbManager
						.getConfig(Const.KEY_DOWNLOAD_VIA_3G);
				if (flag_download_via_3g != null
						&& flag_download_via_3g
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsDownloadVia3gMode = false;
				}
				String flag_notified_new_playlist = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_NEW_PLAYLIST);
				if (flag_notified_new_playlist != null
						&& flag_notified_new_playlist
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewPlaylist = false;
				}
				String flag_notified_like_playlist = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_LIKE_PLAYLIST);
				if (flag_notified_like_playlist != null
						&& flag_notified_like_playlist
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenLikePlaylist = false;
				}
				String flag_notified_server_update = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_SERVER_UPDATE);
				if (flag_notified_server_update != null
						&& flag_notified_server_update
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewUpdateFromServer = false;
				}
				String flag_notified_sharing = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_SHARING);
				if (flag_notified_sharing != null
						&& flag_notified_sharing
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenSharing = false;
				}
				String flag_notified_artist_update = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_ARTIST_UPDATE);
				if (flag_notified_artist_update != null
						&& flag_notified_artist_update
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewUpdateFromArtist = false;
				}
				String limit = mDbManager.getConfig(Const.MEMORY_LIMIT);
				if (limit != null && limit.trim().length() > 0) {
					int temp = Integer.valueOf(limit).intValue();
					if (temp >= 0 && temp <= 100)
						mMemoryLimit = temp;
				}
			} catch (Throwable t) {
				Log.e(LOG_TAG,
						"Throwable loading user & setting, msg = "
								+ t.getMessage());
			}
		}
		checkCloseDatabase();
	}

	/**
	 * Reinit users & settings
	 * 
	 * @return
	 */
	protected void reinitUsersAndSettings() {
		checkOpenDatabase();
		mIsOfflineMode = false;
		mIsDownloadVia3gMode = true;
		mIsNotifiedWhenNewPlaylist = true;
		mIsNotifiedWhenLikePlaylist = true;
		mIsNotifiedWhenNewUpdateFromArtist = true;
		mIsNotifiedWhenNewUpdateFromServer = true;
		mIsNotifiedWhenSharing = true;
		mMemoryLimit = 100;
		mPrice = "";
		synchronized (this) {
			try {
				mPrice = mDbManager.getConfig(Const.KEY_PRICE);
				String offline = mDbManager.getConfig(Const.OFFLINE_MODE);
				if ((offline != null)
						&& offline.equalsIgnoreCase(Const.VALUE_OFFLINE)) {
					mIsOfflineMode = true;
				}
				String flag_download_via_3g = mDbManager
						.getConfig(Const.KEY_DOWNLOAD_VIA_3G);
				if (flag_download_via_3g != null
						&& flag_download_via_3g
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsDownloadVia3gMode = false;
				}
				String flag_notified_new_playlist = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_NEW_PLAYLIST);
				if (flag_notified_new_playlist != null
						&& flag_notified_new_playlist
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewPlaylist = false;
				}
				String flag_notified_like_playlist = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_LIKE_PLAYLIST);
				if (flag_notified_like_playlist != null
						&& flag_notified_like_playlist
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenLikePlaylist = false;
				}
				String flag_notified_server_update = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_SERVER_UPDATE);
				if (flag_notified_server_update != null
						&& flag_notified_server_update
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewUpdateFromServer = false;
				}
				String flag_notified_sharing = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_SHARING);
				if (flag_notified_sharing != null
						&& flag_notified_sharing
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenSharing = false;
				}
				String flag_notified_artist_update = mDbManager
						.getConfig(Const.KEY_NOTIFY_WHEN_ARTIST_UPDATE);
				if (flag_notified_artist_update != null
						&& flag_notified_artist_update
								.equalsIgnoreCase(Const.VALUE_OFF)) {
					mIsNotifiedWhenNewUpdateFromArtist = false;
				}
				String limit = mDbManager.getConfig(Const.MEMORY_LIMIT);
				if (limit != null && limit.trim().length() > 0) {
					int temp = Integer.valueOf(limit).intValue();
					if (temp >= 0 && temp <= 100)
						mMemoryLimit = temp;
				}

			} catch (Throwable t) {
				Log.e(LOG_TAG,
						"Throwable loading user & setting, msg = "
								+ t.getMessage());
			}
		}
		checkCloseDatabase();
	}

	// update hash account
	public void updateAccountConfig(Hashtable<String, String> hashAccount) {
		setConfig(KEY_LOGIN_NAME, hashAccount.get(KEY_LOGIN_NAME));
		setConfig(KEY_PACKAGE_CODE, hashAccount.get(KEY_PACKAGE_CODE));
		setConfig(KEY_BEGIN_DATE, hashAccount.get(KEY_BEGIN_DATE));
		setConfig(KEY_END_DATE, hashAccount.get(KEY_END_DATE));
		setConfig(KEY_ACCOUNT_INFO, hashAccount.get(KEY_ACCOUNT_INFO));
		setConfig(KEY_AVATAR, hashAccount.get(KEY_AVATAR));
		setConfig(KEY_FULL_NAME, hashAccount.get(KEY_FULL_NAME));
		setConfig(KEY_PHONE_NUMBER, hashAccount.get(KEY_PHONE_NUMBER));
		setConfig(KEY_PHONE_NUMBER_EDITABLE,
				hashAccount.get(KEY_PHONE_NUMBER_EDITABLE));
		setConfig(KEY_DOB, hashAccount.get(KEY_DOB));
		setConfig(KEY_GENDER, hashAccount.get(KEY_GENDER));
		setConfig(KEY_ADDRESS, hashAccount.get(KEY_ADDRESS));
		setConfig(KEY_EMAIL, hashAccount.get(KEY_EMAIL));
		setConfig(KEY_EMAIL_EDITABLE, hashAccount.get(KEY_EMAIL_EDITABLE));

		// music quality
		setConfig(Const.MUSIC_QUALITY_ID, hashAccount.get(MUSIC_QUALITY_ID));
		setConfig(Const.MUSIC_QUALITY_VALUE, hashAccount.get(MUSIC_QUALITY_VALUE));
	}

	// get config
	public String getConfig(String key) {
		return hashConfig.get(key);
	}

	// set config
	public boolean setConfig(String key, String value) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(key, value) != DatabaseManager.INVALID) {
			hashConfig.put(key, value);
		} else {
			result = false;
			Log.e(LOG_TAG, String.format("Failed saving %s to database", key));
		}
		checkCloseDatabase();
		return result;
	}

	public boolean setPrice(String _price) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(Const.KEY_PRICE, _price) != DatabaseManager.INVALID) {
			mPrice = _price;
		} else {
			result = false;
			Log.e(LOG_TAG, "Failed saving price to database");
		}
		checkCloseDatabase();
		return result;
	}

	public String getSavedPrice() {
		return mPrice;
	}

	public String getMsisdn() {
		return mMsisdnStr;
	}

	public String getFbAccessToken() {
		checkOpenDatabase();
		String access_token = mDbManager.getConfig(Const.KEY_FB_ACCESS_TOKEN);
		checkCloseDatabase();
		return access_token;
	}

	public String getGmAccessToken() {
		checkOpenDatabase();
		String access_token = mDbManager.getConfig(Const.KEY_GM_ACCESS_TOKEN);
		checkCloseDatabase();
		return access_token;
	}

	public String getFbAccessExpire() {
		checkOpenDatabase();
		String access_expire = mDbManager.getConfig(Const.KEY_FB_ACCESS_EXPIRE);
		checkCloseDatabase();
		return access_expire;
	}

	public String getGmAccessExpire() {
		checkOpenDatabase();
		String access_expire = mDbManager.getConfig(Const.KEY_GM_ACCESS_EXPIRE);
		checkCloseDatabase();
		return access_expire;
	}

	public boolean setFbAccessToken(String access_token) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager
				.updateConfigInfo(Const.KEY_FB_ACCESS_TOKEN, access_token) != DatabaseManager.INVALID) {
			fb_access_token = access_token;
		} else {
			result = false;
			// Log.e(LOG_TAG, "Failed saving msisdn to database");
		}
		checkCloseDatabase();
		return result;
	}

	public boolean setGmAccessToken(String access_token) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager
				.updateConfigInfo(Const.KEY_GM_ACCESS_TOKEN, access_token) != DatabaseManager.INVALID) {
			gm_access_token = access_token;
		} else {
			result = false;
			// Log.e(LOG_TAG, "Failed saving msisdn to database");
		}
		checkCloseDatabase();
		return result;
	}

	public boolean setFbAccessExpire(String access_expire) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(Const.KEY_FB_ACCESS_EXPIRE,
				access_expire) != DatabaseManager.INVALID) {
			fb_access_expire = access_expire;
		} else {
			result = false;
			// Log.e(LOG_TAG, "Failed saving msisdn to database");
		}
		checkCloseDatabase();
		return result;
	}

	public boolean setGmAccessExpire(String access_expire) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(Const.KEY_GM_ACCESS_EXPIRE,
				access_expire) != DatabaseManager.INVALID) {
			fb_access_expire = access_expire;
		} else {
			result = false;
			// Log.e(LOG_TAG, "Failed saving msisdn to database");
		}
		checkCloseDatabase();
		return result;
	}

	// set msisdn
	public boolean setMsisdn(String _msisdn) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(Const.KEY_MSISDN, _msisdn) != DatabaseManager.INVALID) {
			mMsisdnStr = _msisdn;
		} else {
			result = false;
			Log.e(LOG_TAG, "Failed saving msisdn to database");
		}
		checkCloseDatabase();
		return result;
	}

	public String getSavedPassword() {
		return mPasswordStr;
	}

	// set msisdn
	public boolean setPassword(String _pass) {
		boolean result = true;
		checkOpenDatabase();
		if (mDbManager.updateConfigInfo(Const.KEY_PASSWORD, _pass) != DatabaseManager.INVALID) {
			mPasswordStr = _pass;
		} else {
			result = false;
			Log.e(LOG_TAG, "Failed saving password to database");
		}
		checkCloseDatabase();
		return result;
	}

	public boolean isInOfflineMode() {
		return mIsOfflineMode;
	}

	public boolean isAllowingDownloadVia3g() {
		return mIsDownloadVia3gMode;
	}

	public boolean isAllowingNotify_NewPlaylist() {
		return mIsNotifiedWhenNewPlaylist;
	}

	public boolean isAllowingNotify_LikePlaylist() {
		return mIsNotifiedWhenLikePlaylist;
	}

	public boolean isAllowingNotify_ServerUpdate() {
		return mIsNotifiedWhenNewUpdateFromServer;
	}

	public boolean isAllowingNotify_ArtistUpdate() {
		return mIsNotifiedWhenNewUpdateFromArtist;
	}

	public boolean isAllowingNotify_Sharing() {
		return mIsNotifiedWhenSharing;
	}

	// set offline mode status
	public boolean setOfflineModeStatus(boolean offline) {
		String mode = offline ? Const.VALUE_OFFLINE : Const.VALUE_ONLINE;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(Const.OFFLINE_MODE, mode) != DatabaseManager.INVALID) {
				mIsOfflineMode = offline;
			} else {
				Log.e(LOG_TAG, "Failed saving offline mode to database");
			}
		}
		checkCloseDatabase();

		// Auto start download manager
		if (!mIsOfflineMode) {
			DownloadManager.startDownload();
		}

		return mIsOfflineMode;
	}

	public boolean setDownloadVia3gModeStatus(boolean flag) {
		String mode = flag ? Const.VALUE_OFF : Const.VALUE_ON;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(Const.KEY_DOWNLOAD_VIA_3G, mode) != DatabaseManager.INVALID) {
				mIsDownloadVia3gMode = flag;
			} else {
				Log.e(LOG_TAG, "Failed saving KEY_DOWNLOAD_VIA_3G to database");
			}
		}
		checkCloseDatabase();

		return mIsDownloadVia3gMode;
	}

	public boolean setNotifyWhenNewPlaylistMode(boolean flag) {
		String mode = flag ? Const.VALUE_ON : Const.VALUE_OFF;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(Const.KEY_NOTIFY_WHEN_NEW_PLAYLIST,
					mode) != DatabaseManager.INVALID) {
				mIsNotifiedWhenNewPlaylist = flag;
			} else {
				Log.e(LOG_TAG,
						"Failed saving KEY_NOTIFY_WHEN_NEW_PLAYLIST to database");
			}
		}
		checkCloseDatabase();

		return mIsNotifiedWhenNewPlaylist;
	}

	public boolean setNotifyWhenLikePlaylistMode(boolean flag) {
		String mode = flag ? Const.VALUE_ON : Const.VALUE_OFF;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(
					Const.KEY_NOTIFY_WHEN_LIKE_PLAYLIST, mode) != DatabaseManager.INVALID) {
				mIsNotifiedWhenLikePlaylist = flag;
			} else {
				Log.e(LOG_TAG,
						"Failed saving KEY_NOTIFY_WHEN_LIKE_PLAYLIST to database");
			}
		}
		checkCloseDatabase();

		return mIsNotifiedWhenLikePlaylist;
	}

	public boolean setNotifyWhenSharingMode(boolean flag) {
		String mode = flag ? Const.VALUE_ON : Const.VALUE_OFF;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager
					.updateConfigInfo(Const.KEY_NOTIFY_WHEN_SHARING, mode) != DatabaseManager.INVALID) {
				mIsNotifiedWhenSharing = flag;
			} else {
				Log.e(LOG_TAG,
						"Failed saving KEY_NOTIFY_WHEN_SHARING to database");
			}
		}
		checkCloseDatabase();

		return mIsNotifiedWhenSharing;
	}

	public boolean setNotifyWhenServerUpdateMode(boolean flag) {
		String mode = flag ? Const.VALUE_ON : Const.VALUE_OFF;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(
					Const.KEY_NOTIFY_WHEN_SERVER_UPDATE, mode) != DatabaseManager.INVALID) {
				mIsNotifiedWhenNewUpdateFromServer = flag;
			} else {
				Log.e(LOG_TAG,
						"Failed saving KEY_NOTIFY_WHEN_SERVER_UPDATE to database");
			}
		}
		checkCloseDatabase();

		return mIsNotifiedWhenNewUpdateFromServer;
	}

	public boolean setNotifyWhenArtistUpdateMode(boolean flag) {
		String mode = flag ? Const.VALUE_ON : Const.VALUE_OFF;

		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(
					Const.KEY_NOTIFY_WHEN_ARTIST_UPDATE, mode) != DatabaseManager.INVALID) {
				mIsNotifiedWhenNewUpdateFromArtist = flag;
			} else {
				Log.e(LOG_TAG,
						"Failed saving KEY_NOTIFY_WHEN_ARTIST_UPDATE to database");
			}
		}
		checkCloseDatabase();

		return mIsNotifiedWhenNewUpdateFromArtist;
	}

	// get Memory Usage Limit (MB)
	public int getMemoryLimitInMB() {
		int used = (int) (FileStorage.getUsedSize() / FileStorage.MBYTE);
		int free = (int) (FileStorage.getAvaiableVolume() / FileStorage.MBYTE);
		int maxUsed = used + free;
		int limit = mMemoryLimit * maxUsed / 100;
		return limit;
	}

	// get Memory Usage Limit (%)
	public int getMemoryLimit() {
		return mMemoryLimit;
	}

	// set Memory Usage Limit (%)
	public boolean setMemoryLimit(int percentage) {
		boolean result = true;
		checkOpenDatabase();
		synchronized (this) {
			if (mDbManager.updateConfigInfo(Const.MEMORY_LIMIT,
					String.valueOf(percentage)) != DatabaseManager.INVALID) {
				mMemoryLimit = percentage;
			} else {
				result = false;
				Log.e(LOG_TAG, "Failed saving memory limit to database");
			}
		}
		checkCloseDatabase();
		return result;
	}

}