package vn.com.vega.music.database;

/**
 * @author khainv
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.List;

import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.CacheEntry;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager extends AbstractDatabaseManager {
	public static final String LOG_TAG = Const.LOG_PREF + DatabaseManager.class.getSimpleName();

	/* ----------------------------------------------------------------------- */
	/* Static fields */
	/* ----------------------------------------------------------------------- */
	public static final String DATABASE_NAME = "vega_imuzik.db";
	public static final int DATABASE_VERSION = 1;

	// Fields
	public static final String FIELD_SONG_ID = "song_id";
	public static final String FIELD_SONG_TITLE = "song_title";
	public static final String FIELD_SONG_CACHED = "cached_file_path";
	public static final String FIELD_SONG_WAIT_DOWNLOAD = "wait_download";

	public static final String FIELD_ARTIST_ID = "artist_id";
	public static final String FIELD_ARTIST_NAME = "artist_name";
	public static final String FIELD_IMAGE_URL = "image_url";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_ALBUM_COUNT = "album_count";
	public static final String FIELD_SONG_COUNT = "song_count";
	public static final String FIELD_ARTIST_WAIT_DOWNLOAD = "wait_download";
	
	public static final String FIELD_GENRE_ID = "genre_id";
	public static final String FIELD_GENRE_NAME = "genre_name";

	public static final String FIELD_ALBUM_ID = "album_id";
	public static final String FIELD_ALBUM_TITLE = "album_title";
	public static final String FIELD_ALBUM_WAIT_DOWNLOAD = "wait_download";

	public static final String FIELD_CONFIG_KEY = "skey";
	public static final String FIELD_CONFIG_VALUE = "svalue";
	public static final String FIELD_CONFIG_MSISDN = "msisdn";

	public static final String FIELD_PLAYLIST_ID = "playlist_id";
	public static final String FIELD_PLAYLIST_TITLE = "playlist_title";
	public static final String FIELD_SERVER_ID = "server_id";
	public static final String FIELD_USER_MSISDN = "user_msisdn";
	public static final String FIELD_PLAYLIST_TYPE = "type";
	public static final String FIELD_LAST_UPDATE = "last_update";
	public static final String FIELD_PLAYLIST_WAIT_DOWNLOAD = "wait_download";

	public static final String FIELD_DELETED_SONG_ID = "deleted_song_id";
	public static final String FIELD_ADDED_SONG_ID = "added_song_id";
	public static final String FIELD_SYNC_ACTION = "sync_action";
	public static final String FIELD_SONG_ORDER = "song_order";
	private static final String FIELD_SONG_DURATION = "duration";

	// Tables
	public static final String TABLE_SONGS = "songs";
	public static final String TABLE_ARTISTS = "artists";
	public static final String TABLE_ALBUMS = "albums";
	public static final String TABLE_GENRES = "genres";
	public static final String TABLE_PLAYLIST = "playlists";
	public static final String TABLE_CONFIGS = "configs";

	public static final String TABLE_SONGS_ALBUMS = "songs_albums";
	public static final String TABLE_SONGS_PLAYLISTS = "songs_playlists";
	public static final String TABLE_CHANGE_CACHE = "change_cache";

	private static DatabaseManager writeAdapter;

	protected DatabaseManager(Context ctx, boolean allowWrite) {
		super(ctx, allowWrite);
		// TODO Auto-generated constructor stub
	}

	public static DatabaseManager getWritableInstance(Context ctx) {
		// TODO: get writable database
		if (writeAdapter == null)
			writeAdapter = new DatabaseManager(ctx, true);
		return writeAdapter;
	}

	public static DatabaseManager getReadableInstance(Context ctx) {
		// TODO: Get readable database
		return new DatabaseManager(ctx, false);
	}

	@Override
	public void onCreateDatabase(SQLiteDatabase db) {
		// TODO: Create database and index tables
		super.onCreateDatabase(db);
	}

	/* ----------------------------------------------------------------------- */
	/* Abstracts methods */
	/* ----------------------------------------------------------------------- */
	@Override
	public String getDatabaseName() {
		return DATABASE_NAME;
	}

	@Override
	public int getDatabaseVersion() {
		return DATABASE_VERSION;
	}

	@Override
	protected String[] getSQLCreateTables() {
		String sqlSongs = getSQLCreateTable(TABLE_SONGS, new SQLPair(FIELD_SONG_ID, "INTEGER PRIMARY KEY"), new SQLPair(FIELD_ARTIST_ID, "INTEGER NULL"), new SQLPair(FIELD_ARTIST_NAME,
				"TEXT NOT NULL"), new SQLPair(FIELD_ALBUM_ID, "INTEGER NULL"), new SQLPair(FIELD_ALBUM_TITLE, "TEXT NULL"), new SQLPair(FIELD_SONG_TITLE, "TEXT NOT NULL"), new SQLPair(
				FIELD_SONG_DURATION, "INTEGER NOT NULL"), new SQLPair(FIELD_SONG_CACHED, "TEXT NULL"), new SQLPair(FIELD_SONG_WAIT_DOWNLOAD, "INTEGER NULL"), new SQLPair(FIELD_GENRE_NAME, "TEXT NOT NULL"));

		String sqlArtists = getSQLCreateTable(TABLE_ARTISTS, new SQLPair(FIELD_ARTIST_ID, "INTEGER PRIMARY KEY"), new SQLPair(FIELD_ARTIST_NAME, "TEXT NOT NULL"), new SQLPair(FIELD_IMAGE_URL,
				"TEXT NULL"), new SQLPair(FIELD_DESCRIPTION, "TEXT NULL"), new SQLPair(FIELD_ALBUM_COUNT, "INTEGER DEFAULT 0"), new SQLPair(FIELD_SONG_COUNT, "INTEGER DEFAULT 0"), new SQLPair(
				FIELD_ARTIST_WAIT_DOWNLOAD, "INTEGER NULL"));
		
		String sqlGenres = getSQLCreateTable(TABLE_GENRES, new SQLPair(FIELD_GENRE_ID, "INTEGER PRIMARY KEY"), new SQLPair(FIELD_GENRE_NAME, "TEXT NOT NULL"), new SQLPair(FIELD_DESCRIPTION, "TEXT NULL"), new SQLPair(FIELD_SONG_COUNT, "INTEGER DEFAULT 0"));

		String sqlAlbums = getSQLCreateTable(TABLE_ALBUMS, new SQLPair(FIELD_ALBUM_ID, "INTEGER PRIMARY KEY"), new SQLPair(FIELD_ALBUM_TITLE, "TEXT NOT NULL"), new SQLPair(FIELD_IMAGE_URL,
				"TEXT NULL"), new SQLPair(FIELD_DESCRIPTION, "TEXT NULL"), new SQLPair(FIELD_SONG_COUNT, "INTEGER DEFAULT 0"), new SQLPair(FIELD_ARTIST_ID, "INTEGER NOT NULL"), new SQLPair(
				FIELD_ARTIST_NAME, "TEXT NOT NULL"), new SQLPair(FIELD_ALBUM_WAIT_DOWNLOAD, "INTEGER NULL"));

		String sqlPlaylists = getSQLCreateTable(TABLE_PLAYLIST, new SQLPair(FIELD_PLAYLIST_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"), new SQLPair(FIELD_SERVER_ID, "INTEGER DEFAULT 0"), new SQLPair(
				FIELD_USER_MSISDN, "TEXT NULL"), new SQLPair(FIELD_PLAYLIST_TITLE, "TEXT NOT NULL"), new SQLPair(FIELD_PLAYLIST_TYPE, "INTEGER NULL"), new SQLPair(FIELD_LAST_UPDATE, "INTEGER NULL"),
				new SQLPair(FIELD_PLAYLIST_WAIT_DOWNLOAD, "INTEGER NULL"), new SQLPair(FIELD_SONG_COUNT, "INTEGER DEFAULT 0"), new SQLPair(FIELD_IMAGE_URL, "TEXT NULL"));

		String sqlConfigs = getSQLCreateTable(TABLE_CONFIGS, new SQLPair(FIELD_CONFIG_KEY, "TEXT NOT NULL"), new SQLPair(FIELD_CONFIG_VALUE, "TEXT NOT NULL"), new SQLPair(FIELD_CONFIG_MSISDN,
				"TEXT NULL"));

		String sqlCaches = getSQLCreateTable(TABLE_CHANGE_CACHE, new SQLPair(FIELD_PLAYLIST_ID, "INTEGER PRIMARY KEY"), new SQLPair(FIELD_USER_MSISDN, "TEXT NULL"), new SQLPair(FIELD_SERVER_ID,
				"INTEGER NULL"), new SQLPair(FIELD_PLAYLIST_TYPE, "INTEGER NULL"), new SQLPair(FIELD_SYNC_ACTION, "INTEGER NULL"), new SQLPair(FIELD_PLAYLIST_TITLE, "TEXT NOT NULL"), new SQLPair(
				FIELD_DELETED_SONG_ID, "TEXT NULL"), new SQLPair(FIELD_ADDED_SONG_ID, "TEXT NULL"));

		String sqlSongAlbum = getSQLCreateTable(TABLE_SONGS_ALBUMS, "PRIMARY KEY(" + FIELD_ALBUM_ID + "," + FIELD_SONG_ID + ")", new SQLPair(FIELD_ALBUM_ID, "INTEGER NOT NULL"), new SQLPair(
				FIELD_SONG_ID, "INTEGER NOT NULL"), new SQLPair(FIELD_SONG_ORDER, "INTEGER NOT NULL"));

		String sqlSongPlaylist = getSQLCreateTable(TABLE_SONGS_PLAYLISTS, "PRIMARY KEY (" + FIELD_PLAYLIST_ID + "," + FIELD_SONG_ID + ")", new SQLPair(FIELD_PLAYLIST_ID, "INTEGER NOT NULL"),
				new SQLPair(FIELD_SONG_ID, "INTEGER NOT NULL"), new SQLPair(FIELD_SONG_ORDER, "INTEGER NOT NULL"));

		return new String[] { sqlSongs, sqlArtists, sqlAlbums, sqlPlaylists, sqlSongAlbum, sqlSongPlaylist, sqlConfigs, sqlCaches, sqlGenres };
	}

	@Override
	protected String[] getTableNames() {
		return new String[] { TABLE_SONGS, TABLE_ARTISTS, TABLE_ALBUMS, TABLE_PLAYLIST, TABLE_SONGS_PLAYLISTS, TABLE_SONGS_ALBUMS, TABLE_CHANGE_CACHE, TABLE_GENRES };
	}

	/* ----------------------------------------------------------------------- */
	/* Instance methods */
	/* ----------------------------------------------------------------------- */
	@Override
	public synchronized void execSQL(String sql) {
		// TODO Auto-generated method stub
		super.execSQL(sql);
	}

	/**********************************************************************************************
	 * 
	 * Album & Artist & Genre
	 * 
	 **********************************************************************************************/

	public int addArtist(Artist a) {
		return insert(TABLE_ARTISTS, new String[] { FIELD_ALBUM_ID, FIELD_ARTIST_NAME, FIELD_DESCRIPTION, FIELD_IMAGE_URL, FIELD_ALBUM_COUNT, FIELD_SONG_COUNT }, new Object[] { a.id, a.name,
				a.description, a.imageUrl, a.albumCount, a.songCount });
	}

	public int addGenre(Genre g) {
		return insert(TABLE_GENRES, new String[] { FIELD_GENRE_ID, FIELD_GENRE_NAME, FIELD_DESCRIPTION, FIELD_SONG_COUNT }, new Object[] { g.id, g.name,
				g.description, g.songCount });
	}
	
	// private int insertNewArtist(int id, String name, String description,
	// String image_url, int album_count,
	// int song_count) {
	// // TODO Auto-generated method stub
	// return insert(TABLE_ARTISTS, new String[] { FIELD_ALBUM_ID,
	// FIELD_ARTIST_NAME, FIELD_DESCRIPTION,
	// FIELD_IMAGE_URL, FIELD_ALBUM_COUNT, FIELD_SONG_COUNT }, new Object[] {
	// id, name, description,
	// image_url, album_count, song_count });
	// }

	public int addAlbum(Album a) {
		return insert(TABLE_ALBUMS, new String[] { FIELD_ALBUM_ID, FIELD_ALBUM_TITLE, FIELD_DESCRIPTION, FIELD_IMAGE_URL, FIELD_ARTIST_ID, FIELD_ARTIST_NAME, FIELD_SONG_COUNT }, new Object[] { a.id,
				a.title, a.description, a.coverUrl, a.artistId, a.artistName, a.songCount });
	}

	// private int insertNewAlbum(int id, String title, String description,
	// String coverUrl, int artistId,
	// String artistName, int songCount) {
	// // TODO Auto-generated method stub
	// return insert(TABLE_ALBUMS, new String[] { FIELD_ALBUM_ID,
	// FIELD_ALBUM_TITLE, FIELD_DESCRIPTION,
	// FIELD_IMAGE_URL, FIELD_ARTIST_ID, FIELD_ARTIST_NAME, FIELD_SONG_COUNT },
	// new Object[] { id, title,
	// description, coverUrl, artistId, artistName, songCount });
	// }

	/**********************************************************************************************
	 * 
	 * CONFIGS & SETTINGS
	 * 
	 **********************************************************************************************/

	public int addConfig(String key, String value) {
		return updateConfigInfo(key, value);
	}

	public String getConfig(String key) {
		Cursor c = null;
		String result = "";
		try {
			if(key != Const.KEY_MSISDN && key != Const.KEY_PASSWORD){
				String msisdn = getConfig(Const.KEY_MSISDN);
				c = db.query(TABLE_CONFIGS, new String[] { FIELD_CONFIG_VALUE }, FIELD_CONFIG_KEY + "='" + key + "'" + " AND " + FIELD_CONFIG_MSISDN + "='" + msisdn + "'" , null, null, null, null);
			}
			else{
				c = db.query(TABLE_CONFIGS, new String[] { FIELD_CONFIG_VALUE }, FIELD_CONFIG_KEY + "='" + key + "'" , null, null, null, null);
			}
			
			if (c.moveToFirst()) {
				result = c.getString(0);
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return result;
	}

	public int updateConfigInfo(String key, String value) {
		int result = INVALID;
		try {
			
			if(key != Const.KEY_MSISDN && key != Const.KEY_PASSWORD){
				String msisdn = getConfig(Const.KEY_MSISDN);
				result = update(TABLE_CONFIGS, new String[] { FIELD_CONFIG_VALUE }, new Object[] { value }, FIELD_CONFIG_KEY + " = '" + key + "'" + " AND " + FIELD_CONFIG_MSISDN + "='" + msisdn + "'",
						null);
				if (result <= 0)
					return insert(TABLE_CONFIGS, new String[] { FIELD_CONFIG_KEY, FIELD_CONFIG_VALUE, FIELD_CONFIG_MSISDN }, new Object[] { key, value, msisdn });
			}
			else{
				result = update(TABLE_CONFIGS, new String[] { FIELD_CONFIG_VALUE }, new Object[] { value }, FIELD_CONFIG_KEY + " = '" + key + "'",
						null);
				if (result <= 0)
					return insert(TABLE_CONFIGS, new String[] { FIELD_CONFIG_KEY, FIELD_CONFIG_VALUE, FIELD_CONFIG_MSISDN }, new Object[] { key, value, "" });
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**********************************************************************************************
	 * 
	 * CACHE ENTRIES
	 * 
	 **********************************************************************************************/

	public int addCached(CacheEntry cache) {
		return insert(
				TABLE_CHANGE_CACHE,
				new String[] { FIELD_PLAYLIST_ID, FIELD_USER_MSISDN, FIELD_SERVER_ID, FIELD_PLAYLIST_TYPE, FIELD_SYNC_ACTION, FIELD_PLAYLIST_TITLE, FIELD_ADDED_SONG_ID, FIELD_DELETED_SONG_ID },
				new Object[] { cache.getLocalId(), cache.getMsisdn(), cache.getServerId(), cache.playlistType, cache.getType(), cache.playlistTitle, cache.getAddedSongsAsString(),
						cache.getDeletedSongsAsString() });
	}

	public CacheEntry getCacheEntryByPlaylistId(int pid) {
		Cursor c = null;
		try {
			c = db.query(TABLE_CHANGE_CACHE, new String[] { FIELD_PLAYLIST_ID, FIELD_USER_MSISDN, FIELD_SERVER_ID, FIELD_PLAYLIST_TYPE, FIELD_PLAYLIST_TITLE, FIELD_ADDED_SONG_ID,
					FIELD_DELETED_SONG_ID, FIELD_SYNC_ACTION }, FIELD_PLAYLIST_ID + "=" + pid, null, null, null, null);
			if (c.moveToFirst()) {
				int localId = c.getInt(0);
				String msisdn = c.getString(1);
				int serverId = c.getInt(2);
				int playlistType = c.getInt(3);
				String playlistTitle = c.getString(4);
				String addedSongIds = c.getString(5);
				String deletedSongIds = c.getString(6);
				int actionType = c.getInt(7);

				return new CacheEntry(actionType, msisdn, serverId, localId, playlistType, playlistTitle, addedSongIds, deletedSongIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return null;
	}

	public int updateCachedInfo(CacheEntry ce) {
		try {
			return update(TABLE_CHANGE_CACHE, new String[] { FIELD_SERVER_ID, FIELD_PLAYLIST_TITLE, FIELD_PLAYLIST_TYPE, FIELD_ADDED_SONG_ID, FIELD_DELETED_SONG_ID, FIELD_SYNC_ACTION }, new Object[] {
					ce.getServerId(), ce.playlistTitle, ce.playlistType, ce.getAddedSongsAsString(), ce.getDeletedSongsAsString(), ce.getType() }, FIELD_PLAYLIST_ID + "=" + ce.getLocalId(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	public int removeChangeCaches() {
		try {
			return delete(TABLE_CHANGE_CACHE, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	public int removeChangeCaches(String _msisdn) {
		try {
			return delete(TABLE_CHANGE_CACHE, FIELD_USER_MSISDN + "='" + _msisdn + "'", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return INVALID;
	}

	public ArrayList<CacheEntry> getListCached(String msisdn) {
		Cursor c = null;
		ArrayList<CacheEntry> entries = new ArrayList<CacheEntry>();
		try {
			c = db.query(TABLE_CHANGE_CACHE, new String[] { FIELD_PLAYLIST_ID, FIELD_USER_MSISDN, FIELD_SERVER_ID, FIELD_PLAYLIST_TYPE, FIELD_PLAYLIST_TITLE, FIELD_ADDED_SONG_ID,
					FIELD_DELETED_SONG_ID, FIELD_SYNC_ACTION }, FIELD_USER_MSISDN + "='" + msisdn + "'", null, null, null, null);
			if (c.moveToFirst()) {
				do {
					int localId = c.getInt(0);
					int serverId = c.getInt(2);
					int playlistType = c.getInt(3);
					String playlistTitle = c.getString(4);
					String addedSongIds = c.getString(5);
					String deletedSongIds = c.getString(6);
					int actionType = c.getInt(7);

					CacheEntry ce = new CacheEntry(actionType, msisdn, serverId, localId, playlistType, playlistTitle, addedSongIds, deletedSongIds);

					entries.add(ce);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return entries;
	}

	/**********************************************************************************************
	 * 
	 * PLAYLISTS
	 * 
	 **********************************************************************************************/
	public List<Playlist> getListPlaylist(String msisdn) {
		// TODO: get all playlist from database
		Cursor c = null;
		List<Playlist> entries = new ArrayList<Playlist>();
		try {
			c = db.query(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_ID, FIELD_LAST_UPDATE, FIELD_SERVER_ID, FIELD_PLAYLIST_TYPE, FIELD_USER_MSISDN, FIELD_PLAYLIST_TITLE,
					FIELD_PLAYLIST_WAIT_DOWNLOAD, FIELD_SONG_COUNT, FIELD_IMAGE_URL }, FIELD_USER_MSISDN + "='" + msisdn + "'", null, null, null, null);
			if (c.moveToFirst()) {
				do {
					Playlist p = new Playlist();
					p.id = c.getInt(0);
					//p.lastUpdate = c.getInt(1);
					p.lastUpdate = c.getString(1);
					p.serverId = c.getInt(2);
					p.type = c.getInt(3);
					p.userMsisdn = c.getString(4);
					p.title = c.getString(5);
					p.waitDownload = (c.getInt(6) == 1);
					p.total_song  = c.getInt(7);
					p.userThumb = c.getString(8);
					entries.add(p);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return entries;
	}

	public int addPlaylist(Playlist p) {
		return insert(TABLE_PLAYLIST, new String[] { FIELD_LAST_UPDATE, FIELD_SERVER_ID, FIELD_PLAYLIST_TYPE, FIELD_USER_MSISDN, FIELD_PLAYLIST_TITLE, FIELD_PLAYLIST_WAIT_DOWNLOAD, FIELD_SONG_COUNT, FIELD_IMAGE_URL }, new Object[] {
				p.lastUpdate, p.serverId, p.type, p.userMsisdn, p.title, (p.waitDownload ? 1 : 0), p.total_song, p.userThumb });
	}

	public boolean removePlaylist(int playlistId) {
		try {
			// remove all song of playlist from songs table
			int[] songIds = getSongIdsByPlaylistId(playlistId);
			if (songIds != null && songIds.length > 0) {
				for (int i = 0; i < songIds.length; i++) {
					removeSong(songIds[i]);
				}
			}

			// remove from songs_playlists table
			delete(TABLE_SONGS_PLAYLISTS, FIELD_PLAYLIST_ID + "=" + playlistId, null);

			// remove from playlists table
			return delete(TABLE_PLAYLIST, FIELD_PLAYLIST_ID + "=" + playlistId, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean regIdForNewPlaylist(int localId, int serverId) {
		boolean bResult = false;
		try {
			int r = update(TABLE_PLAYLIST, new String[] { FIELD_SERVER_ID }, new Object[] { serverId }, FIELD_PLAYLIST_ID + "=" + localId, null);
			if (r != -1)
				bResult = true;
			else
				bResult = false;
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(LOG_TAG, "Database error: cannot register id for new playlist");
			bResult = false;
		}
		return bResult;
	}

	public int updatePlaylistInfo(Playlist p) {
		// TODO: update playlist info
		try {
			return update(TABLE_PLAYLIST, new String[] { FIELD_LAST_UPDATE, FIELD_SERVER_ID, FIELD_PLAYLIST_TITLE, FIELD_PLAYLIST_TYPE, FIELD_USER_MSISDN, FIELD_PLAYLIST_WAIT_DOWNLOAD },
					new Object[] { p.lastUpdate, p.serverId, p.title, p.type, p.userMsisdn, p.waitDownload }, FIELD_PLAYLIST_ID + "=" + p.id, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return INVALID;
	}

	// public int updatePlaylistInfoByServerId(Playlist p) {
	// // TODO: update playlist info
	// try {
	// return update(TABLE_PLAYLIST, new String[] { FIELD_LAST_UPDATE,
	// FIELD_PLAYLIST_TITLE, FIELD_PLAYLIST_TYPE,
	// FIELD_USER_MSISDN, FIELD_PLAYLIST_WAIT_DOWNLOAD }, new Object[] {
	// p.lastUpdate, p.title, p.type,
	// p.userMsisdn, (p.waitDownload ? 1 : 0) }, FIELD_SERVER_ID + "=" +
	// p.serverId, null);
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// return INVALID;
	// }
	//
	public boolean updatePlaylistTitle(int playlistId, String title) {
		// TODO Auto-generated method stub
		try {
			if (update(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_TITLE }, new Object[] { title }, FIELD_PLAYLIST_ID + "=" + playlistId, null) > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	// private int insertNewPlaylist(int lastUpdate, int playlistId, int type,
	// String userMsisdn, String title, int wait_download) {
	// // TODO Auto-generated method stub
	// return insert(TABLE_PLAYLIST, new String[] { FIELD_LAST_UPDATE,
	// FIELD_PLAYLIST_ID, FIELD_PLAYLIST_TYPE, FIELD_USER_MSISDN,
	// FIELD_PLAYLIST_TITLE, FIELD_PLAYLIST_WAIT_DOWNLOAD }, new Object[] {
	// lastUpdate, playlistId,
	// type, userMsisdn, title, wait_download });
	// }

	// Hoai Ngo: 2011-10-19, don't need this
	// public int[] getPlaylistIdsByType(int type) {
	// // TODO: get all playlist from database
	// Cursor c = null;
	// int[] result = new int[] {};
	// int index = 0;
	// try {
	// c = db.query(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_ID },
	// FIELD_PLAYLIST_TYPE + "=" + type, null, null, null, null);
	// if (c.moveToFirst()) {
	// result = new int[c.getCount()];
	// do {
	// result[index] = c.getInt(0);
	// index++;
	// } while (c.moveToNext());
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// } finally {
	// if (c != null) {
	// c.close();
	// }
	// }
	// return result;
	// }

	public boolean addSongToPlaylist(int songId, int playlistId, int order) {
		// TODO add song to playlist
		return insert(TABLE_SONGS_PLAYLISTS, new String[] { FIELD_SONG_ID, FIELD_PLAYLIST_ID, FIELD_SONG_ORDER }, new Object[] { songId, playlistId, order }) > 0 ? true : false;
	}

	public int[] getSongIdsByPlaylistId(int playlistId) {
		// TODO: get list of song id by playlist id
		int[] entries = null;
		Cursor c = null;
		try {
			c = db.query(TABLE_SONGS_PLAYLISTS, new String[] { FIELD_SONG_ID }, FIELD_PLAYLIST_ID + "=" + playlistId, null, null, null, null);
			if (c.moveToFirst()) {
				int index = 0;
				int row = c.getCount();
				entries = new int[row];
				do {
					entries[index] = c.getInt(0);
					index++;
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return entries;
	}

	public boolean removeSongFromPlaylist(int songId, int playlistId) {
		// TODO: remove song from playlist
		try {
			return delete(TABLE_SONGS_PLAYLISTS, FIELD_PLAYLIST_ID + "=" + playlistId + " AND " + FIELD_SONG_ID + "=" + songId, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public int removeAllSongsInPlaylist(int serverId) {
		int iResult = -1;
		try {
			iResult = delete(TABLE_SONGS_PLAYLISTS, FIELD_SERVER_ID + "=" + serverId, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(LOG_TAG, "Database error: some errors occurred while remove all songs in playlist");
			iResult = -1;
		}
		return iResult;
	}

	public boolean isRetainByAnotherPlaylist(int songId) {
		// TODO Auto-generated method stub
		Cursor c = null;
		try {
			c = db.query(TABLE_SONGS_PLAYLISTS, new String[] { FIELD_PLAYLIST_ID, FIELD_SONG_ID }, FIELD_SONG_ID + "=" + songId, null, null, null, null);
			if (c.moveToFirst()) {
				return c.getCount() > 1 ? true : false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**********************************************************************************************
	 * 
	 * SONGS
	 * 
	 **********************************************************************************************/

	public List<Song> getListSong() {
		// TODO: get all song in database
		Cursor c = null;
		List<Song> entries = new ArrayList<Song>();
		try {
			c = db.query(TABLE_SONGS, new String[] { FIELD_SONG_ID, FIELD_ALBUM_ID, FIELD_ALBUM_TITLE, FIELD_ARTIST_ID, FIELD_ARTIST_NAME, FIELD_SONG_CACHED, FIELD_SONG_DURATION, FIELD_SONG_TITLE,
					FIELD_SONG_WAIT_DOWNLOAD, FIELD_GENRE_NAME }, null, null, null, null, null);
			if (c.moveToFirst()) {
				do {
					Song s = new Song();
					s.id = c.getInt(0);
					s.album_id = c.getInt(1);
					s.album_title = c.getString(2);
					s.artist_id = c.getInt(3);
					s.artist_name = c.getString(4);
					s.cached_file_path = c.getString(5);
					s.duration = c.getInt(6);
					s.title = c.getString(7);
					s.wait_download = c.getInt(8);
					s.genre_name = c.getString(9);
					entries.add(s);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return entries;
	}

	public boolean addSong(Song entry) {
		return insert(TABLE_SONGS, new String[] { FIELD_SONG_ID, FIELD_ALBUM_ID, FIELD_ALBUM_TITLE, FIELD_ARTIST_ID, FIELD_ARTIST_NAME, FIELD_SONG_CACHED, FIELD_SONG_DURATION, FIELD_SONG_TITLE,
				FIELD_SONG_WAIT_DOWNLOAD, FIELD_GENRE_NAME }, new Object[] { entry.id, entry.album_id, entry.album_title, entry.artist_id, entry.artist_name, entry.cached_file_path, entry.duration, entry.title,
				entry.wait_download, entry.genre_name }) != INVALID;
	}

	public boolean updateSongInfo(Song s) {
		try {
			return update(TABLE_SONGS, new String[] { FIELD_SONG_TITLE, FIELD_SONG_WAIT_DOWNLOAD, FIELD_SONG_CACHED }, new Object[] { s.title, s.wait_download, s.cached_file_path }, FIELD_SONG_ID
					+ "=" + s.id, null) != INVALID;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public int removeSong(int songId) {
		// TODO: remove song by song id
		try {
			return delete(TABLE_SONGS, FIELD_SONG_ID + "=" + songId, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return INVALID;
	}

	// Hoai Ngo: 2011-10-19, don't need this
	// public int[] getSongDownloadPoolIds() {
	// // TODO Auto-generated method stub
	// Cursor c = null;
	// int[] ids = null;
	// try {
	// c = db.query(TABLE_SONGS, new String[] { FIELD_SONG_ID },
	// FIELD_SONG_WAIT_DOWNLOAD + "=" + 1, null, null,
	// null, null);
	// if (c.moveToFirst()) {
	// int i = 0;
	// ids = new int[c.getCount()];
	// do {
	// ids[i] = c.getInt(0);
	// i++;
	// } while (c.moveToNext());
	// }
	// return ids;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// } finally {
	// if (c != null) {
	// c.close();
	// }
	// }
	// return null;
	// }

	// public int[] getPlaylistDownloadPoolIds() {
	// // TODO Auto-generated method stub
	// Cursor c = null;
	// int[] ids = null;
	// try {
	// c = db.query(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_ID },
	// FIELD_PLAYLIST_WAIT_DOWNLOAD + "=" + 1, null, null, null, null);
	// if (c.moveToFirst()) {
	// int i = 0;
	// ids = new int[c.getCount()];
	// do {
	// ids[i] = c.getInt(0);
	// i++;
	// } while (c.moveToNext());
	// }
	// return ids;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// } finally {
	// if (c != null) {
	// c.close();
	// }
	// }
	// return null;
	// }

	// private int insertNewSong(int song_id, int album_id, String album_title,
	// int artist_id, String artist_name, String cached_file_path,
	// int duration, String title, int wait_download) {
	// // TODO Auto-generated method stub
	// return insert(TABLE_SONGS, new String[] { FIELD_SONG_ID,
	// FIELD_ALBUM_ID, FIELD_ALBUM_TITLE, FIELD_ARTIST_ID,
	// FIELD_ARTIST_NAME, FIELD_SONG_CACHED, FIELD_SONG_DURATION,
	// FIELD_SONG_TITLE, FIELD_SONG_WAIT_DOWNLOAD }, new Object[] {
	// song_id, album_id, album_title, artist_id, artist_name,
	// cached_file_path, duration, title, wait_download });
	// }

	/**********************************************************************************************
	 * 
	 * SONGS CACHING
	 * 
	 **********************************************************************************************/

	public boolean markSongBeDownload(int songId) {
		try {
			return update(TABLE_SONGS, new String[] { FIELD_SONG_WAIT_DOWNLOAD }, new Object[] { 1 }, FIELD_SONG_ID + "=" + songId, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean unMarkSongBeDownload(int songId) {
		try {
			return update(TABLE_SONGS, new String[] { FIELD_SONG_WAIT_DOWNLOAD }, new Object[] { 0 }, FIELD_SONG_ID + "=" + songId, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean clearSongDownloadPool() {
		// TODO Auto-generated method stub
		try {
			return update(TABLE_SONGS, new String[] { FIELD_SONG_WAIT_DOWNLOAD }, new Object[] { 0 }, null, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean setSongCachedPath(int songId, String path) {
		try {
			// Hoai Ngo: 2011-10-19, update song cached path and update wait
			// download too
			return update(TABLE_SONGS, new String[] { FIELD_SONG_CACHED, FIELD_SONG_WAIT_DOWNLOAD }, new Object[] { path, 0 }, FIELD_SONG_ID + "=" + songId, null) > 0 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean setClearSongCachedPath(int songId) {
		try {
			return update(TABLE_SONGS, new String[] { FIELD_SONG_CACHED }, new Object[] { "" }, FIELD_SONG_ID + "=" + songId, null) > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// public boolean clearPlaylistDownloadPool() {
	// // TODO Auto-generated method stub
	// try {
	// return update(TABLE_PLAYLIST,
	// new String[] { FIELD_PLAYLIST_WAIT_DOWNLOAD },
	// new Object[] { 0 }, null, null) > 0 ? true : false;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// return false;
	// }

	public boolean markPlaylistBeDownload(int playlistId) {
		try {
			return update(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_WAIT_DOWNLOAD }, new Object[] { 1 }, FIELD_PLAYLIST_ID + "=" + playlistId, null) > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean unmarkPlaylistBeDownload(int playlistId) {
		try {
			return update(TABLE_PLAYLIST, new String[] { FIELD_PLAYLIST_WAIT_DOWNLOAD }, new Object[] { 0 }, FIELD_PLAYLIST_ID + "=" + playlistId, null) > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
