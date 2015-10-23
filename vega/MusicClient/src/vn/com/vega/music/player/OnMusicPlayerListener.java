package vn.com.vega.music.player;

public interface OnMusicPlayerListener {
	public void OnSongIsFinishPlayed();

	public void OnSongProgressSecondUpdate();

	public void OnSongBufferingUpdate();
}
