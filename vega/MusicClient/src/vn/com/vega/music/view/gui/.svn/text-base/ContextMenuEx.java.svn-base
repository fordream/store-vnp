package vn.com.vega.music.view.gui;

import java.util.ArrayList;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonArtist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.social.FacebookUtility;
import vn.com.vega.music.social.ShareUtility;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.AlbumInfoActivity;
import vn.com.vega.music.view.ArtistInfoActivity;
import vn.com.vega.music.view.MyPlaylistSelectorActivity;
import vn.com.vega.music.view.holder.FeatureTabHolder;
import vn.com.vega.music.view.holder.MyPlaylistStack;
import vn.com.vega.music.view.holder.SearchTabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContextMenuEx {
	protected Dialog dialog;
	protected Context context;
	protected Activity activity;
	protected DataStore dataStore;
	protected static final String UNLIKE = "Đã bỏ thích";
	protected static final String LIKE = "Đã thêm vào nhạc yêu thích";
	protected static final String ADD_SUCCESS = "Đã thêm vào danh sách đang nghe";
	protected static final String ADD_FAILED = "Thêm không thành công";
	protected static final String LOCAL_SONG_MSG = "Đây là nhạc có sẵn trong máy";
	protected static final String NO_INFO_MSG = "Không lấy được thông tin";
	protected static final String LOADING_MSG = "Loading, please wait...";
	protected static final String DOWNLOADING_MSG = "Bài hát đang được tải về";
	protected static final String WAITING_DOWNLOAD_MSG = "Bài hát đang được đợi để tải về";
	protected static final String ERROR_MSG = "Không thành công";
	protected static final String DOWNLOAD_ERROR_MSG = "Không tải được bài hát";
	protected static final String DOWNLOADED_MSG = "Bài hát đã được tải về";
	public static final int OPTION_ALBUM = 0;
	public static final int OPTION_ARTIST = 1;
	protected static final int SHARE_SUCCESS = 2;
	protected static final int SHARE_FAILED = 3;
	protected int mType;
	protected int mOption;
	protected Song mSong;
	protected Album mAlbum;
	protected String mShareMsg;
	protected String mShareLink;
	protected Artist mArtist;
	protected boolean mIsInNowplaying = false;
	private ProgressDialog pd;
	private static ContextMenuExListener listener;
	
	private boolean inAlbumActivity = false;
	private boolean inArtistActivity = false;

	private void downloadSingleSong(Song song) {
		dataStore.addSong(song);

		// song is waiting to download, remove it
		if (song.isWaitToDownload()) {
			dataStore.removeSongFromSongDownloadPool(song.id);
			Toast.makeText(context, DOWNLOADED_MSG, Toast.LENGTH_SHORT).show();
		} else {
			if (DownloadManager.isRunning()) {
				if (dataStore.addSongToSongDownloadPool(song.id)) {
					// indicate this song is waiting to download
					Toast.makeText(context, WAITING_DOWNLOAD_MSG,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, DOWNLOAD_ERROR_MSG,
							Toast.LENGTH_SHORT).show();
				}
			} else {
				// for simple i should clear all download pool before start
				dataStore.clearSongDownloadPool();
				dataStore.clearPlaylistDownloadPool();
				// add song to song download pool
				dataStore.addSongToSongDownloadPool(song.id);
				// start download thread
				DownloadManager.startDownload();
				Toast.makeText(context, DOWNLOADING_MSG, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// do something
			if (mSong.isLocalSong()) {
				Toast.makeText(context, LOCAL_SONG_MSG, Toast.LENGTH_SHORT)
						.show();
			} else {
				if (dialog != null)
					dialog.dismiss();
				switch (position) {
				case 0:
					// add to nowplaying
					if (mType == Const.TYPE_NOW_PLAYING_STACK) {
						downloadSingleSong(mSong);

					} else {
						if (!mIsInNowplaying) {

							// add song first
							if (dataStore.addSong(mSong)) {
								if (NowPlayingList.addSong(mSong))
									Toast.makeText(context, ADD_SUCCESS,
											Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(context, ADD_FAILED,
											Toast.LENGTH_SHORT).show();
							} else
								Toast.makeText(context, ADD_FAILED,
										Toast.LENGTH_SHORT).show();

						}
					}
					break;
				case 1:
					Playlist _favPlaylist = dataStore
							.getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
					if (_favPlaylist == null) {
						Toast.makeText(context, ERROR_MSG, Toast.LENGTH_SHORT)
								.show();
					} else {
						if (_favPlaylist.getSongList().contains(mSong) || checkForSure(_favPlaylist, mSong)) {
							dataStore.removeSongFromPlaylist(_favPlaylist.id,
									mSong.id);
							Toast.makeText(context, UNLIKE, Toast.LENGTH_SHORT)
									.show();
						} else {
							// add song first
							if (dataStore.addSong(mSong)) {
								if (dataStore.addSongToPlaylist(
										_favPlaylist.id, mSong.id))
									Toast.makeText(context, LIKE,
											Toast.LENGTH_SHORT).show();
								else
									Toast.makeText(context, ERROR_MSG,
											Toast.LENGTH_SHORT).show();
							} else
								Toast.makeText(context, ERROR_MSG,
										Toast.LENGTH_SHORT).show();
						}
						if (listener != null)
							listener.onUpdateUI();
					}
					break;
				case 2:
					// add to playlist
					Bundle bundle = new Bundle();
					ArrayList<Song> _songs = new ArrayList<Song>();
					_songs.add(mSong);
					Session.putSharedObject(bundle, ContextMenuEx.this, _songs);
					Intent i = new Intent(context,
							MyPlaylistSelectorActivity.class);
					i.putExtras(bundle);
					context.startActivity(i);
					break;
				case 3:
					ShareUtility shareUtis = new ShareUtility(context,
							activity, true);
					shareUtis.setSongId(mSong.id);
					shareUtis.showShareChooser();

					break;
				case 4:
					// artist's info
					if (mSong.artist_id > 0)
						mOption = OPTION_ARTIST;
					else if (mSong.artist_id <= 0 && mSong.album_id > 0)
						mOption = OPTION_ALBUM;
					// new ContextMenuExTask().execute(0);
					if(!inAlbumActivity)
						initializeData();
					break;
				case 5:
					// album's info
					mOption = OPTION_ALBUM;
					// new ContextMenuExTask().execute(0);
					if(!inArtistActivity)
						initializeData();
					break;
				default:
					break;
				}
			}

		}
	};

	public ContextMenuEx(boolean _inAlbumActivity, boolean _inArtistActivity) {
		inAlbumActivity = _inAlbumActivity;
		inArtistActivity = _inArtistActivity;
		dataStore = DataStore.getInstance();
	}

	public static void regContextMenuExListener(ContextMenuExListener _listener) {
		listener = _listener;
	}

	public void showOptionalDialog(final Song _song, Context _context,
			Activity _activity, int _type) {

		if (_song.isLocalSong())
			return;
		activity = _activity;
		context = _context;
		mType = _type;
		dialog = new Dialog(context);
		mSong = _song;
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_context_menu);
		ListView _listview = (ListView) dialog
				.findViewById(R.id.common_context_menu_listview);
		Button _closeBtn = (Button) dialog
				.findViewById(R.id.common_context_menu_close_btn);
		DialogAdapter _myAdapter = new DialogAdapter(context, mSong);
		_listview.setAdapter(_myAdapter);
		setOnClick(_closeBtn);
		TextView _title = (TextView) dialog
				.findViewById(R.id.common_context_menu_title_txt);
		_title.setText(mSong.title);
		dialog.show();
		_listview.setOnItemClickListener(onItemClickListener);
	}

	public void setInfoListView(final Song _song, Context _context,
			ListView _listview) {
		if (_song.isLocalSong())
			return;

		context = _context;
		dialog = null;
		mSong = _song;

		DialogAdapter _myAdapter = new DialogAdapter(context, mSong);
		_listview.setAdapter(_myAdapter);
		_listview.setOnItemClickListener(onItemClickListener);

	}

	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.common_context_menu_close_btn:
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
					break;
				default:
					break;
				}
			}
		});
	}// end of setOnclick

	// -------------------------
	// BEGIN-----------------------

	private class DialogAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Song _currSong = null;
		private Playlist _favPlaylist = null;

		class ViewHolder {
			TextView first_text;
			// TextView second_text;
			ImageView image;
			ImageView arrow;

		}

		public DialogAdapter(Context context, Song song) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			_currSong = song;
			mIsInNowplaying = NowPlayingList.isExist(_currSong.id);
			_favPlaylist = dataStore
					.getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
		}

		@Override
		public int getCount() {
			if (_currSong.artist_id > 0 && _currSong.album_id > 0)
				return 6;
			else if (_currSong.artist_id > 0 || _currSong.album_id > 0)
				return 5;
			else
				return 4;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.view_listview_row_context_menu, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_thumbnail_img);
				holder.arrow = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_arrow_img);
				holder.first_text = (TextView) convertView
						.findViewById(R.id.common_context_menu_row_first_txt);
				// holder.second_text = (TextView) convertView
				// .findViewById(R.id.common_context_menu_row_second_txt);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// if (position == 0)
			// convertView.setBackgroundResource(R.drawable.selector_listview_first);
			// else if (position > 0 && position < (getCount() - 1))
			// convertView.setBackgroundResource(R.drawable.selector_listview_normal);
			// else if (position == (getCount() - 1))
			// convertView.setBackgroundResource(R.drawable.selector_listview_last);

			convertView
					.setBackgroundResource(R.drawable.selector_listview_dialog);

			holder.image.setVisibility(ImageView.VISIBLE);

			switch (position) {
			case 0: // add to nowplaylist
				holder.arrow.setVisibility(ImageView.GONE);
				if (mType == Const.TYPE_NOW_PLAYING_STACK) {
					if (_currSong.isAvailableLocally()) {
						holder.first_text.setText("Bài hát đã được tải về");
					} else {
						holder.first_text
								.setText(R.string.common_context_menu_download_txt);
					}
					holder.image
							.setBackgroundResource(R.drawable.ic_dialog_download);

				} else {
					holder.image
							.setBackgroundResource(R.drawable.ic_dialog_add_to_playlist);
					if (!mIsInNowplaying) {
						holder.first_text
								.setText(R.string.common_context_menu_add_to_nowplaying);
					} else {
						holder.first_text
								.setText("Đang ở trong danh sách nghe");
					}
				}

				break;
			case 1:
				holder.arrow.setVisibility(ImageView.GONE);
				if (_favPlaylist == null) {
					holder.first_text
							.setText(R.string.common_context_menu_like);
					holder.image
							.setBackgroundResource(R.drawable.ic_dialog_like);
				} else {
					if (!checkForSure(_favPlaylist, _currSong)) {
						holder.first_text
								.setText(R.string.common_context_menu_like);
						holder.image
								.setBackgroundResource(R.drawable.ic_dialog_like);
					} else {
						holder.first_text
								.setText(R.string.common_context_menu_unlike);
						holder.image
								.setBackgroundResource(R.drawable.ic_dialog_unlike);
					}
				}
				break;
			case 2:
				holder.arrow.setVisibility(ImageView.GONE);
				holder.first_text
						.setText(R.string.common_context_menu_add_to_playlist);
				holder.image
						.setBackgroundResource(R.drawable.ic_dialog_add_to_playlist);
				break;
			case 3:
				holder.arrow.setVisibility(ImageView.GONE);
				holder.first_text.setText(R.string.common_context_menu_share);
				holder.image.setBackgroundResource(R.drawable.ic_dialog_share);
				break;
			case 4:
				holder.arrow.setVisibility(ImageView.VISIBLE);
				// holder.first_text.setText(R.string.common_context_menu_artist_info);
				if (_currSong.artist_id > 0) {
					holder.first_text.setText(_currSong.artist_name);
					holder.image
							.setBackgroundResource(R.drawable.ic_dialog_artist_info);
				} else if (_currSong.artist_id <= 0 && _currSong.album_id > 0) {
					holder.first_text.setText(_currSong.album_title);
					holder.image
							.setBackgroundResource(R.drawable.ic_dialog_album_info);
				}
				break;
			case 5:
				holder.arrow.setVisibility(ImageView.VISIBLE);
				// holder.first_text.setText(R.string.common_context_menu_album_info);
				holder.first_text.setText(_currSong.album_title);
				holder.image
						.setBackgroundResource(R.drawable.ic_dialog_album_info);
				break;
			default:
				break;
			}

			return convertView;

		}

	}

	// END-----------------------
	
	private boolean checkForSure(Playlist pl, Song s){
		boolean result = false;
		for(Song song : pl.getSongList()){
			if(song.id == s.id){
				result = true;
				break;
			}
		}
		return result;
	}

	private void showAlert(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(R.string.dialog_notification);
		alertDialog.setMessage(message);
		alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();
	}

	private void shareSong() {
		if (NetworkUtility.hasNetworkConnection()) {
			// show progress dialog while load data from database
			pd = ProgressDialog
					.show(context, "", "Đang lấy thông tin...", true);

			Thread dataInitializationThread = new Thread() {
				public void run() {
					JsonSong jso = JsonSong.shareSong(mSong.id);
					if (jso.isSuccess()) {
						mShareLink = jso.shareLink;
						mShareMsg = jso.shareMsg;
						mHandler.sendEmptyMessage(SHARE_SUCCESS);
					} else {
						// mShareLink =
						// "http://mp3.zing.vn/bai-hat/Dem-Cho-Vo-Le-Hieu/ZWZBEW7A.html";
						// mShareMsg = "Đêm chơ vơ - Lê Hiếu";
						// mHandler.sendEmptyMessage(SHARE_SUCCESS);
						mHandler.sendEmptyMessage(SHARE_FAILED);
					}

				}
			};
			dataInitializationThread.start();
		} else {
			Toast.makeText(context,
					context.getString(R.string.no_network_connection),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initializeData() {
		if (dataStore.isInOfflineMode()) {
			askGoToOnline();
		} else {
			if (NetworkUtility.hasNetworkConnection()) {
				// show progress dialog while load data from database
				pd = ProgressDialog.show(context, "", LOADING_MSG, true);

				Thread dataInitializationThread = new Thread() {
					public void run() {
						if (mOption == OPTION_ALBUM) {
							JsonAlbum jal = JsonAlbum
									.loadAlbumInfo(mSong.album_id);
							if (jal.isSuccess() && jal.albums != null
									&& jal.albums.size() > 0) {
								mAlbum = jal.albums.get(0);
								mHandler.sendEmptyMessage(Const.INITIALIZE_SUCCESS);
							} else
								mHandler.sendEmptyMessage(Const.INITIALIZE_FAILURE);
						} else { // mOption == OPTION_ARTIST
							JsonArtist jar = JsonArtist
									.loadArtistInfo(mSong.artist_id);
							if (jar.isSuccess() && jar.artists != null
									&& jar.artists.size() > 0) {
								mArtist = jar.artists.get(0);
								mHandler.sendEmptyMessage(Const.INITIALIZE_SUCCESS);
							} else
								mHandler.sendEmptyMessage(Const.INITIALIZE_FAILURE);
						}
					}
				};
				dataInitializationThread.start();
			} else {
				Toast.makeText(context,
						context.getString(R.string.no_network_connection),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.offline_mode_msg)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dataStore.setOfflineModeStatus(false);
							}
						})
				.setNegativeButton(R.string.confirm_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// handle init data result
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			switch (msg.what) {
			case Const.INITIALIZE_SUCCESS:
				// draw view
				Bundle b;
				if (mOption == OPTION_ALBUM) {
					b = new Bundle();
					Session.putSharedObject(b, ContextMenuEx.this, mAlbum);
					b.putBoolean(AlbumInfoActivity.BUNDLE_NOT_IN_GROUP, true);
					b.putInt(Const.BUNDLE_STACK_TYPE, mType);
					switch (mType) {
					case Const.TYPE_NOW_PLAYING_STACK:
						context.startActivity(new Intent(context,
								AlbumInfoActivity.class).putExtras(b));
						break;
					case Const.TYPE_FEATURE_STACK:
						FeatureTabHolder featureTabHolder = (FeatureTabHolder) activity;
						featureTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
										AlbumInfoActivity.class).putExtras(b));
						break;
					case Const.TYPE_SEARCH_STACK:
						SearchTabHolder searchTabHolder = (SearchTabHolder) activity;
						searchTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
										AlbumInfoActivity.class).putExtras(b));
						break;

					case Const.TYPE_PLAYLIST_STACK:
						MyPlaylistStack playlistTabHolder = (MyPlaylistStack) activity;
						playlistTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
										AlbumInfoActivity.class).putExtras(b));
						break;

					default:
						break;
					}

				} else {
					b = new Bundle();
					Session.putSharedObject(b, ContextMenuEx.this, mArtist);
					b.putBoolean(ArtistInfoActivity.BUNDLE_NOT_IN_GROUP, true);
					b.putInt(Const.BUNDLE_STACK_TYPE, mType);
					switch (mType) {
					case Const.TYPE_NOW_PLAYING_STACK:
						context.startActivity(new Intent(context,
								ArtistInfoActivity.class).putExtras(b));
						break;
					case Const.TYPE_FEATURE_STACK:
						FeatureTabHolder featureTabHolder = (FeatureTabHolder) activity;
						featureTabHolder.startChildActivity(
								ArtistInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
												ArtistInfoActivity.class).putExtras(b));
						break;
					case Const.TYPE_SEARCH_STACK:
						SearchTabHolder searchTabHolder = (SearchTabHolder) activity;
						searchTabHolder.startChildActivity(
								ArtistInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
												ArtistInfoActivity.class).putExtras(b));
						break;

					case Const.TYPE_PLAYLIST_STACK:
						MyPlaylistStack playlistTabHolder = (MyPlaylistStack) activity;
						playlistTabHolder.startChildActivity(
								ArtistInfoActivity.class.getName()
										+ "_context_menu", new Intent(context,
												ArtistInfoActivity.class).putExtras(b));
						break;

					default:
						break;
					}
					
				}
				break;
			case Const.INITIALIZE_FAILURE:
				// do stuffs
				Toast.makeText(context, NO_INFO_MSG, Toast.LENGTH_SHORT).show();
				break;
			case SHARE_FAILED:
				Toast.makeText(context, NO_INFO_MSG, Toast.LENGTH_SHORT).show();
				break;
			case SHARE_SUCCESS:
				FacebookUtility fbu = new FacebookUtility(context, activity);
				fbu.loginAndPostToWall(mShareMsg, mShareLink);
				break;

			}
		}
	};
}
