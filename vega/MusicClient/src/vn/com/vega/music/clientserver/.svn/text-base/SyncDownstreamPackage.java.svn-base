package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;

public class SyncDownstreamPackage {

	public ArrayList<SyncDownstreamPlaylist> aAddedPlaylists;
	public ArrayList<SyncDownstreamPlaylist> aUpdatedPlaylists;
	public ArrayList<Integer> aDeletedPlaylistIds;
	//public int iLastVersion;
	public String sLastVersion;
	public ArrayList<SyncIdRegisterResult> aIdRegisterResults;
	public ArrayList<Song> aNewSongs;
	public ArrayList<Playlist> aFavPlaylist;

	public SyncDownstreamPackage() {
		aAddedPlaylists = new ArrayList<SyncDownstreamPlaylist>();
		aUpdatedPlaylists = new ArrayList<SyncDownstreamPlaylist>();
		aDeletedPlaylistIds = new ArrayList<Integer>();
		//iLastVersion = 0;
		sLastVersion = "";
		aIdRegisterResults = new ArrayList<SyncIdRegisterResult>();
		aNewSongs = new ArrayList<Song>();
		aFavPlaylist = new ArrayList<Playlist>();
	}
}
