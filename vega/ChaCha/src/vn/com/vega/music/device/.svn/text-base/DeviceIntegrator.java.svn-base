package vn.com.vega.music.device;

/**
 * @author khainv
 * @since  09/2010
 */
import java.util.ArrayList;
import java.util.List;

import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.util.Log;

public class DeviceIntegrator {
	private static final String LOG_TAG = Const.LOG_PREF + DeviceIntegrator.class.getSimpleName();

	private Context mContext;

	public DeviceIntegrator(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public List<Genre> searchForGenre() {
		List<Genre> entries = new ArrayList<Genre>();
		final String[] cursor_cols = { MediaStore.Audio.Genres._ID, MediaStore.Audio.Genres.NAME};
		Cursor c = null;
		ContentResolver resolver = null;

		if (!isExternalMounted()) {
			Log.e(LOG_TAG, "SD card is rejected, please insert sdcard");
		} else {
			try {
				resolver = mContext.getContentResolver();
				c = resolver.query(MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, cursor_cols, null, null, MediaStore.Audio.Genres.NAME + " ASC");
				if (c.moveToFirst()) {
					do {
						Genre s = new Genre();
						s.id = Integer.parseInt(c.getString(0));
						s.name = c.getString(1);
						//s.songCount = c.getString(2);
						entries.add(s);
					} while (c.moveToNext());
				}
				return entries;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return entries;
	}

	public List<Artist> searchForArtist() {
		List<Artist> entries = new ArrayList<Artist>();
		final String[] cursor_cols = { MediaStore.Audio.Artists._ID, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists.ARTIST_KEY, MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS};
		Cursor c = null;
		ContentResolver resolver = null;
		
		/*
		FileStorage fs = new FileStorage();
		String offlineRoot = null;
		try {
			offlineRoot = fs.getCacheDirRoot();
		} catch (Exception e) {
		}
		*/
		
		if (!isExternalMounted()) {
			Log.e(LOG_TAG, "SD card is rejected, please insert sdcard");
		} else {
			try {
				resolver = mContext.getContentResolver();
				c = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, cursor_cols, null, null, MediaStore.Audio.Artists.ARTIST + " ASC");
				if (c.moveToFirst()) {
					do {
						Artist s = new Artist();
						s.id = Integer.parseInt(c.getString(0));
						s.name = c.getString(1);
						s.songCount = Integer.parseInt(c.getString(4));
						s.albumCount = Integer.parseInt(c.getString(3));
						entries.add(s);
						/*
						String path = c.getString(2);
						if ((offlineRoot == null) || !path.startsWith(offlineRoot)) {
							
						}
						*/
					} while (c.moveToNext());
				}
				return entries;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return entries;
	}

	public List<Song> searchForSongByArtist(String artist_id) {
		List<Song> entries = new ArrayList<Song>();
		Cursor c = null;
		ContentResolver resolver = null;

		FileStorage fs = new FileStorage();
		String offlineRoot = null;
		try {
			offlineRoot = fs.getCacheDirRoot();
		} catch (Exception e) {
		}

		if (!isExternalMounted()) {
			Log.e(LOG_TAG, "SD card is rejected, please insert sdcard");
		} else {
			try {
				resolver = mContext.getContentResolver();
				c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id3Tags, MediaStore.Audio.Media.ARTIST_ID + "=" + artist_id, null, MediaStore.Audio.Media.TITLE + " ASC");
				if (c.moveToFirst()) {
					do {
						String path = c.getString(2);
						if ((offlineRoot == null) || !path.startsWith(offlineRoot)) {
							Song s = new Song(Song.SONG_TYPE_LOCAL);
							s.title = c.getString(1);
							s.artist_name = c.getString(3);
							s.cached_file_path = c.getString(2);
							s.album_title = c.getString(4);
							s.duration = c.getInt(5);
							entries.add(s);
						}
					} while (c.moveToNext());
				}
				return entries;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return entries;
	}

	public List<Song> searchForSongByGenre(String genre_id) {
		Long genreId = Long.valueOf(genre_id);
		List<Song> entries = new ArrayList<Song>();
		final String[] cursor_cols = {MediaStore.Audio.Genres.Members._ID, MediaStore.Audio.Genres.Members.TITLE, MediaStore.Audio.Genres.Members.DATA, MediaStore.Audio.Genres.Members.ARTIST, MediaStore.Audio.Genres.Members.ALBUM, MediaStore.Audio.Genres.Members.DURATION};
		Cursor c = null;
		ContentResolver resolver = null;

		FileStorage fs = new FileStorage();
		String offlineRoot = null;
		try {
			offlineRoot = fs.getCacheDirRoot();
		} catch (Exception e) {
		}

		if (!isExternalMounted()) {
			Log.e(LOG_TAG, "SD card is rejected, please insert sdcard");
		} else {
			try {
				resolver = mContext.getContentResolver();
				c = resolver.query(MediaStore.Audio.Genres.Members.getContentUri("external", genreId), cursor_cols, null, null, MediaStore.Audio.Genres.Members.TITLE + " ASC");
				if (c.moveToFirst()) {
					do {
						String path = c.getString(2);
						if ((offlineRoot == null) || !path.startsWith(offlineRoot)) {
							Song s = new Song(Song.SONG_TYPE_LOCAL);
							s.title = c.getString(1);
							s.artist_name = c.getString(3);
							s.cached_file_path = c.getString(2);
							s.album_title = c.getString(4);
							s.duration = c.getInt(5);
							entries.add(s);
						}
					} while (c.moveToNext());
				}
				return entries;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return entries;
	}

	public List<Song> searchForLocalMusic() {
		// TODO: Get list song from external storage
		List<Song> entries = new ArrayList<Song>();
		Cursor c = null;
		ContentResolver resolver = null;

		FileStorage fs = new FileStorage();
		String offlineRoot = null;
		try {
			offlineRoot = fs.getCacheDirRoot();
		} catch (Exception e) {
		}

		if (!isExternalMounted()) {
			Log.e(LOG_TAG, "SD card is rejected, please insert sdcard");
		} else {
			try {
				resolver = mContext.getContentResolver();
				c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id3Tags, null, null, null);
				if (c.moveToFirst()) {
					do {
						String path = c.getString(2);
						if ((offlineRoot == null) || !path.startsWith(offlineRoot)) {
							Song s = new Song(Song.SONG_TYPE_LOCAL);
							s.title = c.getString(1);
							s.artist_name = c.getString(3);
							s.cached_file_path = c.getString(2);
							s.album_title = c.getString(4);
							s.duration = c.getInt(5);

							entries.add(s);
						}
					} while (c.moveToNext());
				}
				return entries;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		return entries;
	}

	private static final String[] id3Tags = new String[] { android.provider.MediaStore.Audio.Media._ID, android.provider.MediaStore.Audio.Media.TITLE, android.provider.MediaStore.Audio.Media.DATA,
			android.provider.MediaStore.Audio.Media.ARTIST, android.provider.MediaStore.Audio.Media.ALBUM, android.provider.MediaStore.Audio.Media.DURATION };

	private static boolean isExternalMounted() {
		// TODO: Check if external storage is mounted
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
			return true;
		else
			return false;
	}
}