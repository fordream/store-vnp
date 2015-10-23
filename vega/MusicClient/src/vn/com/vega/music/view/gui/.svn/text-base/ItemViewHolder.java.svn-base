package vn.com.vega.music.view.gui;

import vn.com.vega.chacha.R;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ItemViewHolder {

	public static final int CONNECTING_VALUE = 0;
	public static final int DOWNLOADED_VALUE = 100;
	public static final int WAITING_VALUE = 101;

	private TextView firstTxt;
	private TextView secondTxt;
	public ImageView thumbnailImg;
	private ImageView statusImg;
	private ProgressBar downloadBar;
	public CheckBox checkbox;
	private ImageView arrow;

	private View ownerView;

	public ItemViewHolder(View view) {
		ownerView = view;
		firstTxt = (TextView) ownerView.findViewById(R.id.my_playlist_row_first_txt);
		secondTxt = (TextView) ownerView.findViewById(R.id.my_playlist_row_second_txt);
		thumbnailImg = (ImageView) ownerView.findViewById(R.id.my_playlist_row_thumbnail_img);
		statusImg = (ImageView) ownerView.findViewById(R.id.my_playlist_row_status_img);
		downloadBar = (ProgressBar) ownerView.findViewById(R.id.my_playlist_row_download_bar);
		checkbox = (CheckBox) ownerView.findViewById(R.id.my_playlist_row_checkbox);
		arrow = (ImageView) ownerView.findViewById(R.id.my_playlist_row_arrow_img);
	}

	public void setupFor(Object object, boolean isSelectMode, Integer _status) {
		if (object instanceof Song) {
			Song _currSong = (Song) object;
			setupSong(_currSong, _status);

		} else if (object instanceof Playlist) {
			Playlist _currPlaylist = (Playlist) object;
			if (isSelectMode) {
				setupSelectPlaylist(_currPlaylist);
			} else {
				setupPlaylist(_currPlaylist, _status);
			}
		}
	}

	protected void setupSong(Song _currSong, Integer _status) {
		
		downloadBar.setBackgroundResource(R.drawable.drawable_seekbar_blue_ex);
		
		firstTxt.setText(_currSong.title);
		statusImg.setVisibility(ImageView.GONE);
		thumbnailImg.setVisibility(ImageView.VISIBLE);
		secondTxt.setVisibility(TextView.VISIBLE);
		secondTxt.setText(_currSong.artist_name);
		thumbnailImg.setTag(_currSong);

		if (_currSong.isAvailableLocally()) {
			thumbnailImg.setBackgroundResource(R.drawable.ic_listview_downloaded);
			thumbnailImg.setEnabled(false);
			downloadBar.setVisibility(ProgressBar.GONE);

		} else {
			if (_status == null) {
				downloadBar.setVisibility(ProgressBar.GONE);
				thumbnailImg.setBackgroundResource(R.drawable.selector_button_listview_delete_ex);
				thumbnailImg.setEnabled(true);
			} else {
				thumbnailImg.setBackgroundResource(R.drawable.ic_dialog_downloading);
				thumbnailImg.setEnabled(false);

				if (_status == ItemViewHolder.CONNECTING_VALUE) {
					downloadBar.setVisibility(ProgressBar.GONE);
					secondTxt.setText(R.string.my_playlist_download_connecting);

				} else if (_status == ItemViewHolder.WAITING_VALUE) {
					downloadBar.setVisibility(ProgressBar.GONE);
					secondTxt.setText(_currSong.artist_name);

				} else if (_status > ItemViewHolder.CONNECTING_VALUE && _status < ItemViewHolder.DOWNLOADED_VALUE) {
					downloadBar.setVisibility(ProgressBar.VISIBLE);
					downloadBar.setProgress(_status);
					secondTxt.setText("(" + _status + "%) " + _currSong.artist_name);
				}
			}
		}
	}

	protected void setupSelectPlaylist(Playlist playlist) {
		thumbnailImg.setVisibility(ImageView.GONE);
		downloadBar.setVisibility(ProgressBar.GONE);
		checkbox.setVisibility(CheckBox.VISIBLE);

		secondTxt.setVisibility(TextView.VISIBLE);
		int totalSongLeft = playlist.count() - playlist.countSongCached() - playlist.countSongWaitToDownload();
		secondTxt.setText("Có " + totalSongLeft + Const.SONG_SUFFIX + " chưa tải về");
		checkbox.setTag(playlist);
		if (playlist.type == Playlist.TYPE_FAVORITE)
			firstTxt.setText(R.string.my_playlist_favourite_music);
		else
			firstTxt.setText(playlist.title);
		if (playlist.getSongList().size() == 0) {
			statusImg.setVisibility(ImageView.GONE);
			checkbox.setEnabled(false);
		} else if (playlist.isAllSongCached()) {
			statusImg.setVisibility(ImageView.VISIBLE);
			statusImg.setBackgroundResource(R.drawable.ic_listview_downloaded_music_ex);
			checkbox.setEnabled(false);
		} else {
			statusImg.setVisibility(ImageView.GONE);
			checkbox.setEnabled(true);
		}
	}

	protected void setupPlaylist(Playlist playlist, Integer _status) {
		checkbox.setVisibility(CheckBox.GONE);
		thumbnailImg.setVisibility(ImageView.VISIBLE);
		secondTxt.setVisibility(TextView.VISIBLE);
		arrow.setVisibility(ImageView.VISIBLE);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 0, 0, 0);
		firstTxt.setLayoutParams(layoutParams);
		firstTxt.setTextColor(Color.parseColor("#333333"));
		if ((playlist.type == Playlist.TYPE_DOWNLOADED) || (playlist.type == Playlist.TYPE_LOCAL_SONGS)) {
			statusImg.setVisibility(ImageView.GONE);
			downloadBar.setVisibility(ProgressBar.GONE);
			secondTxt.setText("" + playlist.count() + Const.SONG_SUFFIX);

			if (playlist.type == Playlist.TYPE_DOWNLOADED) {
				firstTxt.setText(R.string.my_playlist_downloaded_music);
				thumbnailImg.setBackgroundResource(R.drawable.ic_listview_downloaded_music);
			} else if (playlist.type == Playlist.TYPE_LOCAL_SONGS) {
				firstTxt.setText(R.string.my_playlist_local_music);
				thumbnailImg.setBackgroundResource(R.drawable.ic_listview_local);
			}
		} else if (playlist.type == Const.FIRST_SECTION_HEADER || playlist.type == Const.SECOND_SECTION_HEADER) {
			checkbox.setVisibility(CheckBox.GONE);
			thumbnailImg.setVisibility(ImageView.GONE);
			arrow.setVisibility(ImageView.GONE);
			secondTxt.setVisibility(TextView.GONE);
			statusImg.setVisibility(TextView.GONE);
			downloadBar.setVisibility(ProgressBar.GONE);
			firstTxt.setTextSize(17);
			firstTxt.setTextColor(Color.parseColor("#000000"));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 10, 0, 0);
			if(playlist.type == Const.FIRST_SECTION_HEADER)
				firstTxt.setLayoutParams(params);
			firstTxt.setText(playlist.title);

		} else {
			if (playlist.type == Playlist.TYPE_FAVORITE) {
				firstTxt.setText(R.string.my_playlist_favourite_music);
				thumbnailImg.setBackgroundResource(R.drawable.ic_listview_favourite);
			} else {
				firstTxt.setText(playlist.title);
				thumbnailImg.setBackgroundResource(R.drawable.ic_listview_playlist);
			}

			if (playlist.isAllSongCached()) {
				downloadBar.setVisibility(ProgressBar.GONE);
				statusImg.setVisibility(ImageView.VISIBLE);
				statusImg.setBackgroundResource(R.drawable.ic_listview_downloaded_music_ex);
				secondTxt.setText("" + playlist.count() + Const.SONG_SUFFIX);
			} else {
				if (_status == null) {
					downloadBar.setVisibility(ProgressBar.GONE);
					statusImg.setVisibility(ImageView.GONE);
					if(playlist.type == Playlist.TYPE_FAVOURITE_EX)
						secondTxt.setText("" + playlist.total_song + Const.SONG_SUFFIX);
					else
						secondTxt.setText("" + playlist.getSongList().size() + Const.SONG_SUFFIX);
				} else {
					statusImg.setVisibility(ImageView.VISIBLE);
					statusImg.setBackgroundResource(R.drawable.ic_listview_downloading_ex);

					if (_status == ItemViewHolder.CONNECTING_VALUE) {
						downloadBar.setVisibility(ProgressBar.GONE);
						secondTxt.setText(R.string.my_playlist_download_connecting);
					} else if (_status == ItemViewHolder.WAITING_VALUE) {
						downloadBar.setVisibility(ProgressBar.GONE);
						secondTxt.setText(R.string.my_playlist_download_waiting);
					} else if (_status > ItemViewHolder.CONNECTING_VALUE && _status < ItemViewHolder.DOWNLOADED_VALUE) {
						downloadBar.setVisibility(ProgressBar.VISIBLE);
						downloadBar.setProgress(_status);
						secondTxt.setText("(" + _status + "%) còn " + playlist.countSongWaitToDownload() + Const.SONG_SUFFIX);
					}
				}
			}
		}
	}
}
