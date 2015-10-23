package vn.com.vega.music.objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Song implements Serializable {

	public static final int SONG_TYPE_SERVER = 0;
	public static final int SONG_TYPE_LOCAL = 1;

	public int id = 0;
	public String title = "";
	public int duration = 0;
	public int artist_id = 0;
	public String artist_name = "";
	public int album_id = 0;
	public String album_title = "";
	public String cached_file_path = "";
	public int wait_download = 0;
	public String imageUrl = "";
	
	public int genre_id = 0;
	public String genre_name = "";
	
	// chacha
	public int viewCount = 0;

	protected int type;
	
	public Song() {
	}
	
	public Song(int type) {
		this.type = type;
	}

	public Song(Song song) {
		id = song.id;
		title = song.title;
		duration = song.duration;
		artist_id = song.artist_id;
		artist_name = song.artist_name;
		album_id = song.album_id;
		album_title = song.album_title;
		cached_file_path = song.cached_file_path;
		wait_download = song.wait_download;
		type = song.type;
		imageUrl = song.imageUrl;
		genre_id = song.genre_id;
		genre_name = song.genre_name;
		
		viewCount = song.viewCount;
	}

	public boolean isSameIdDiffInfo(Song song) {
		if (id != song.id) {
			return false;
		}

		// public int id = 0;
		// public String title = "";
		// public int duration = 0;
		if (!song.title.equals(title) || (song.duration != duration)) {
			return true;
		}

		// public int artist_id = 0;
		// public String artist_name = "";
		if ((song.artist_id != artist_id) || (!song.artist_name.equals(artist_name))) {
			return true;
		}

		// public int album_id = 0;
		// public String album_title = "";
		if ((song.album_id != album_id) || (!song.album_title.equals(album_title))) {
			return true;
		}

		return false;
	}

	public void updateDiffFrom(Song song) {
		title = song.title;
		artist_id = song.artist_id;
		artist_name = song.artist_name;
		genre_id = song.genre_id;
		genre_name = song.genre_name;
		album_id = song.album_id;
		album_title = song.album_title;
	}

	public boolean isLocalSong() {
		return (this.type == SONG_TYPE_LOCAL);
	}

	public boolean isAvailableLocally() {
		if ((cached_file_path != null) && (cached_file_path.trim().length() > 0)) {
			return true;
		}
		return false;
	}

	public boolean isWaitToDownload() {
		return !isAvailableLocally() && (wait_download != 0);
	}
}
