package vn.com.vega.music.downloadmanager;

import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;

public interface DownloadStatusListener {
	public void onPlaylistDownloadStart(Playlist pl);

	public void onPlaylistDownloadDone(Playlist pl);

	public void onPlaylistDownloadProgress(Playlist pl, int totalSong, int downloadedSong);

	public void onDownloadProgress(int totalSong, int downloadedSong);

	public void onSongDownloadStart(Song sg);

	public void onSongDownloadDone(Song sg);

	public void onSongDownloadProgress(Song sg, int total, int downloaded);

	public void onSongDownloadError(Song sg, int errorCode);

	public void onDownloadStart(int total);

	public void onDownloadStopped();
	
	public void onDownloadInOfflineMode();
	
	public void onDownloadVia3gDisable();
}
