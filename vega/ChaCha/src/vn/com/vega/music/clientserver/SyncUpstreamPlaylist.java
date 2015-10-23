package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import vn.com.vega.music.objects.Playlist;

public class SyncUpstreamPlaylist {

	public int iLocalId;
	public int iServerId;
	public int iType;
	public String sName;
	public ArrayList<Integer> aAddedSongListIds;
	public ArrayList<Integer> aDeletedSongListIds;

	public SyncUpstreamPlaylist() {
		iLocalId = -1;
		iServerId = -1;
		iType = Playlist.TYPE_USER_CREATED;
		sName = "";
		aAddedSongListIds = new ArrayList<Integer>();
		aDeletedSongListIds = new ArrayList<Integer>();
	}
}
