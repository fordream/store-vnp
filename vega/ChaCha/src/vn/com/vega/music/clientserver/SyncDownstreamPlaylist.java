package vn.com.vega.music.clientserver;

import java.util.ArrayList;

import vn.com.vega.music.objects.Playlist;

public class SyncDownstreamPlaylist {
	public int iServerId;
	public int iType;
	public String sName;
	public ArrayList<Integer> aSongListIds;

	public SyncDownstreamPlaylist() {
		iServerId = -1;
		iType = Playlist.TYPE_USER_CREATED;
		sName = "";
		aSongListIds = new ArrayList<Integer>();
	}
}
