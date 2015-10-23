package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import com.commonsware.cwac.tlv.TouchListView;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.holder.TabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyPlaylistEditorActivity extends Activity {

	private Context context;
	private int playlistId;
	private DataStore dataStore = null;
	private int playlistType;
	private static EfficientAdapter adapter;

	private static ListView listview = null;
	private LinearLayout saveLayout, deleteLayout;
	private Button backBtn, clearBtn;
	private EditText playlistNameEdt;
	private Playlist currPlaylist;
	private LinearLayout emptyList;

	private boolean isReordered = false;

	private ProgressDialog mProgressDialog;

	private static final int NAME_EMPTY = 0;
	private static final int NAME_EXIST = 1;
	private static final int SUCCESS = 2;
	private static final int DELETE_FAILED = 3;
	
	private int numOfDownloadFailed = 0;

	private ImageView thumbnailImg;

	private List<Song> mRemainSongs = new ArrayList<Song>();
	private List<Song> mSelectedSongs = new ArrayList<Song>();

	@Override
	protected void onResume() {
		super.onResume();
		// do stuffs
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// remove default title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_myplaylist_editor);
		context = this;
		// init data
		dataStore = DataStore.getInstance();
		Bundle bd = this.getIntent().getExtras();
		playlistType = (int) bd.getInt(Const.BUNDLE_PLAYLIST_TYPE);
		if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
			for (Song song : dataStore.getListDownloadedSongs()) {
				mRemainSongs.add(song);
			}
		} else {
			playlistId = (int) bd.getInt(Const.BUNDLE_PLAYLIST_ID);
			currPlaylist = dataStore.getPlaylistByID(playlistId);
			for (Song song : currPlaylist.getSongList()) {
				mRemainSongs.add(song);
			}
		}
		// draw view
		initializeComponentView();

	}

	private Handler mDownloadedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			switch (msg.what) {
			case SettingActivity.CLEAR_CACHE_SUCCESS:
				onBackPressed();
				break;

			case SettingActivity.CLEAR_CACHE_FAILED:
				Toast.makeText(context, getString(R.string.delete_fail),
						Toast.LENGTH_SHORT).show();
				onBackPressed();
				break;
			}
		}
	};

	private Handler mFavouriteHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			onBackPressed();
		}
	};

	private Handler mNormalHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			// Intent intent = new Intent(context,
			// TabHolder.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			// context.startActivity(intent);

			onBackPressed();
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			switch (msg.what) {
			case NAME_EMPTY:
				Toast.makeText(context, "Tên danh sách rỗng",
						Toast.LENGTH_SHORT).show();
				break;

			case NAME_EXIST:
				Toast.makeText(context, "Tên danh sách đã tồn tại",
						Toast.LENGTH_SHORT).show();
				break;
			case SUCCESS:
				onBackPressed();
				break;
			case DELETE_FAILED:
				Toast.makeText(context, "Lỗi xóa nhạc, " + numOfDownloadFailed + " bài được xóa",
						Toast.LENGTH_SHORT).show();
				onBackPressed();
				break;
			}
		}
	};

	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {

				case R.id.editor_clear_btn:
					playlistNameEdt.setText("");
					break;

				case R.id.playlist_editor_back_btn:
					// back to previous screen without change
					onBackPressed();
					break;

				case R.id.delete_layout:
					switch (playlistType) {
					case MyPlaylistActivity.DOWNLOADED_PLAYLIST:
						showConfirmDialog(getString(R.string.common_confirm_dialog_downloaded_msg_txt));
						break;
					case MyPlaylistActivity.FAVOURITE_PLAYLIST:
						showConfirmDialog(getString(R.string.common_confirm_dialog_favourite_msg_txt));
						break;
					default:
						showConfirmDialog(getString(R.string.common_confirm_dialog_normal_msg_txt));
						break;
					}
					break;

				case R.id.save_layout:

					mProgressDialog = new ProgressDialog(context);
					mProgressDialog.setMessage("Đang cập nhật...");
					mProgressDialog.setCancelable(false);
					mProgressDialog.show();
					new Thread(new Runnable() {
						@Override
						public void run() {
							// save change and back to previous screen
							if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
								// delete donwloaded songs
								if (mSelectedSongs.size() > 0) {
									for (Song song : mSelectedSongs) {
										if (FileStorage
												.removeSongCache(song.id))
											dataStore
													.clearSongCachedPath(song.id);
										else
											numOfDownloadFailed++;
									}
								}
								if (numOfDownloadFailed > 0) {
									mHandler.sendEmptyMessage(DELETE_FAILED);
								}
								else
									mHandler.sendEmptyMessage(SUCCESS);
							} else {
								if (playlistType != MyPlaylistActivity.FAVOURITE_PLAYLIST) {
									String _newName = playlistNameEdt.getText()
											.toString().toLowerCase().trim();
									if (_newName.equals("")) {
										mHandler.sendEmptyMessage(NAME_EMPTY);
									} else {
										if (!_newName.equals(currPlaylist.title
												.toLowerCase().trim())) {
											List<Playlist> _playlists = dataStore
													.getListPlaylist();
											boolean _nameIsExist = false;
											for (Playlist _playlist : _playlists) {
												if (_playlist.title
														.toLowerCase().trim()
														.equals(_newName)) {
													_nameIsExist = true;
													break;
												}
											}
											if (_nameIsExist)
												mHandler.sendEmptyMessage(NAME_EXIST);
											else {
												dataStore.renamePlaylist(
														playlistId, _newName);
												if (mSelectedSongs.size() > 0)
													for (Song song : mSelectedSongs) {
														dataStore
																.removeSongFromPlaylist(
																		playlistId,
																		song.id);

													}
												if (isReordered)
													refreshSongList(playlistId,
															mRemainSongs);
												mHandler.sendEmptyMessage(SUCCESS);
											}

										} else {
											if (mSelectedSongs.size() > 0)
												for (Song song : mSelectedSongs) {
													dataStore
															.removeSongFromPlaylist(
																	playlistId,
																	song.id);
													// dataStore.removeListSongFromPlaylist(playlistId,
													// songIds)
												}
											if (isReordered)
												refreshSongList(playlistId,
														mRemainSongs);
											mHandler.sendEmptyMessage(SUCCESS);
										}

									}
								} else {
									if (mSelectedSongs.size() > 0)
										for (Song song : mSelectedSongs) {
											dataStore.removeSongFromPlaylist(
													playlistId, song.id);
										}
									if (isReordered)
										refreshSongList(playlistId,
												mRemainSongs);
									mHandler.sendEmptyMessage(SUCCESS);
								}

							}
						}
					}).start();
					break;
				default:
					break;
				}
			}
		});
	}// end of setOnclick

	private void refreshSongList(int playlistId, List<Song> songs) {
		dataStore.removeAllSongFromPlaylist(playlistId);

		dataStore.addListSongToPlaylist(playlistId, songs, false);
	}

	// confirm when users wanna delete this playlist
	public void showConfirmDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Xóa danh sách")
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(context.getString(R.string.confirm_yes),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
									mProgressDialog = new ProgressDialog(
											context);
									mProgressDialog
											.setMessage(getString(R.string.setting_clear_memory_in_progress));
									mProgressDialog.setCancelable(true);
									mProgressDialog.show();
									new Thread(new Runnable() {
										@Override
										public void run() {
											int result = FileStorage
													.removeAllSongCache();
											if (result < 0) {
												// failed
												mDownloadedHandler
														.sendEmptyMessage(SettingActivity.CLEAR_CACHE_FAILED);
											} else {
												// success
												dataStore
														.clearAllSongCachedPath();
												mDownloadedHandler
														.sendEmptyMessage(SettingActivity.CLEAR_CACHE_SUCCESS);
											}
										}
									}).start();
								} else if (playlistType == MyPlaylistActivity.FAVOURITE_PLAYLIST) {
									mProgressDialog = new ProgressDialog(
											context);
									mProgressDialog
											.setMessage(getString(R.string.my_playlist_detail_delete_all_favourite_playlist));
									mProgressDialog.setCancelable(true);
									mProgressDialog.show();
									new Thread(new Runnable() {
										@Override
										public void run() {
											Playlist _favPlaylist = dataStore
													.getSpecialPlaylistByType(Playlist.TYPE_FAVORITE);
											_favPlaylist.clearSongs();
											mFavouriteHandler
													.sendEmptyMessage(0);
										}
									}).start();
								} else {

									mProgressDialog = new ProgressDialog(
											context);
									mProgressDialog
											.setMessage(getString(R.string.my_playlist_detail_delete_normal_playlist));
									mProgressDialog.setCancelable(true);
									mProgressDialog.show();
									new Thread(new Runnable() {
										@Override
										public void run() {
											dataStore
													.removePlaylist(playlistId);
											mNormalHandler.sendEmptyMessage(0);

										}
									}).start();

								}
							}
						})
				.setNegativeButton(context.getString(R.string.confirm_no),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();

							}
						});
		AlertDialog alert = builder.create();
		alert.show();

	}

	// UI
	private void initializeComponentView() {
		// init controls on view
		saveLayout = (LinearLayout) findViewById(R.id.save_layout);
		deleteLayout = (LinearLayout) findViewById(R.id.delete_layout);
		backBtn = (Button) findViewById(R.id.playlist_editor_back_btn);
		clearBtn = (Button) findViewById(R.id.editor_clear_btn);

		// cancelBtn = (Button)
		// findViewById(R.id.my_playlist_editor_cancel_btn);
		playlistNameEdt = (EditText) findViewById(R.id.my_playlist_editor_name_edt);
		playlistNameEdt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (currPlaylist != null) {
					if (s.toString().toLowerCase()
							.equals(currPlaylist.title.toLowerCase())
							&& mSelectedSongs.size() == 0) {
						// saveLayout.setEnabled(false);
						disableSaveLayout();
					} else
						// saveLayout.setEnabled(true);
						enableSaveLayout();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do stuffs
			}

			@Override
			public void afterTextChanged(Editable s) {
				// do stuffs
			}
		});
		emptyList = (LinearLayout) findViewById(R.id.my_playlist_editor_empty);

		disableSaveLayout();

		if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
			playlistNameEdt.setText("Nhạc đã tải về máy");
			playlistNameEdt.setEnabled(false);
			clearBtn.setEnabled(false);
			playlistNameEdt.setClickable(false);
			playlistNameEdt.setFocusable(false);
		} else if (playlistType == MyPlaylistActivity.FAVOURITE_PLAYLIST) {
			playlistNameEdt.setText("Nhạc yêu thích");
			playlistNameEdt.setEnabled(false);
			clearBtn.setEnabled(false);
			playlistNameEdt.setClickable(false);
			playlistNameEdt.setFocusable(false);
		} else {
			playlistNameEdt.setText(currPlaylist.title);
			playlistNameEdt.setEnabled(true);
			clearBtn.setEnabled(true);
			playlistNameEdt.setFocusable(true);
		}
		setOnClick(deleteLayout);
		setOnClick(saveLayout);
		setOnClick(backBtn);
		setOnClick(clearBtn);
		listview = (ListView) findViewById(R.id.my_playlist_editor_listview);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		View headerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		// listview.addHeaderView(headerView);
		// listview.addFooterView(footerView);
		adapter = new EfficientAdapter(context);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do nothing
			}
		});
		return;
	}

	OnClickListener onThumbnailImgListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			thumbnailClickHandler(null);
		}
	};

	private void enableSaveLayout() {
		saveLayout.setEnabled(true);
		saveLayout.setBackgroundResource(R.drawable.selector_button_save);
	}

	private void disableSaveLayout() {
		saveLayout.setEnabled(false);
		saveLayout.setBackgroundResource(R.drawable.bg_button_toolbar_disable);
	}

	// BEGIN-----------------------
	private class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		class ViewHolder {
			TextView firstTxt;
			ImageView thumbnailImg;
			TextView secondTxt;
			ImageView arrow;
			ImageView grabber;
		}

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);

			if (listview instanceof TouchListView) {
				TouchListView tlv = (TouchListView) listview;
				tlv.setDropListener(onDrop);
				tlv.setRemoveListener(onRemove);
			}

		}

		private TouchListView.DropListener onDrop = new TouchListView.DropListener() {
			@Override
			public void drop(int from, int to) {
				if (from == to) {
					isReordered = false;
					return;
				}

				if (reorderSong(from, to)) {
					isReordered = true;
					enableSaveLayout();
					notifyDataSetChanged();
				}

				// else
				// Toast.makeText(mContext,
				// mContext.getString(R.string.nowplaying_reorder_song_error),
				// Toast.LENGTH_SHORT).show();
			}
		};

		private TouchListView.RemoveListener onRemove = new TouchListView.RemoveListener() {
			@Override
			public void remove(int which) {
				// if
				// (NowPlayingList.removeSong(NowPlayingList.getSongAtIndex(which)))
				// notifyDataSetChanged();
				// else
				// Toast.makeText(mContext,
				// mContext.getString(R.string.nowplaying_remove_song_error),
				// Toast.LENGTH_SHORT).show();
			}
		};

		/**
		 * The number of items in the list is determined by the number of
		 * speeches in our array.
		 * 
		 * @see android.widget.ListAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return mRemainSongs.size();
		}

		/**
		 * Since the data comes from an array, just returning the index is
		 * sufficent to get at the data. If we were using a more complex data
		 * structure, we would return whatever object represents one row in the
		 * list.
		 * 
		 * @see android.widget.ListAdapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return mRemainSongs.get(position);
		}

		/**
		 * Use the array index as a unique id.
		 * 
		 * @see android.widget.ListAdapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder _holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.view_listview_row_myplaylist, null);
				_holder = new ViewHolder();
				_holder.firstTxt = (TextView) convertView
						.findViewById(R.id.my_playlist_row_first_txt);
				_holder.secondTxt = (TextView) convertView
						.findViewById(R.id.my_playlist_row_second_txt);
				_holder.thumbnailImg = (ImageView) convertView
						.findViewById(R.id.my_playlist_row_thumbnail_img);
				_holder.arrow = (ImageView) convertView
						.findViewById(R.id.my_playlist_row_arrow_img);
				_holder.grabber = (ImageView) convertView
						.findViewById(R.id.my_playlist_row_grabber);
				convertView.setTag(_holder);
			} else {
				_holder = (ViewHolder) convertView.getTag();
			}
			if (position == 0)
				convertView
						.setBackgroundResource(R.drawable.selector_listview_first);
			else if (position > 0 && position < (getCount() - 1))
				convertView
						.setBackgroundResource(R.drawable.selector_listview_normal);
			else if (position == (getCount() - 1))
				convertView
						.setBackgroundResource(R.drawable.selector_listview_last);
			emptyList.setVisibility(LinearLayout.GONE);
			Song _currSong = mRemainSongs.get(position);

			_holder.arrow.setVisibility(ImageView.GONE);
			_holder.grabber.setVisibility(ImageView.VISIBLE);

			_holder.firstTxt.setText((position + 1) + ". " + _currSong.title);
			_holder.thumbnailImg.setVisibility(ImageView.VISIBLE);
			_holder.thumbnailImg
					.setBackgroundResource(R.drawable.selector_button_listview_delete);
			_holder.thumbnailImg.setTag(_currSong);
			_holder.thumbnailImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					thumbnailClickHandler(_holder.thumbnailImg);
				}
			});
			_holder.secondTxt.setVisibility(TextView.VISIBLE);
			_holder.secondTxt.setText(_currSong.artist_name);
			return convertView;

		}

	}

	// ------------------------- EfficientAdapter Class
	// END-----------------------

	private Song getSongAtIndex(int _position) {
		Song _song = null;
		if (!mRemainSongs.isEmpty()) {
			_song = mRemainSongs.get(_position);
		}
		return _song;
	}

	private boolean reorderSong(int from, int to) {

		// reorder
		Song s = getSongAtIndex(from);
		if (s != null) {
			mRemainSongs.remove(from);
			mRemainSongs.add(to, s);
			// saveMyList();
		}

		return true;
	}

	// when users click on delete button on each row

	public void thumbnailClickHandler(View v) {
		Song song = (Song) v.getTag();
		// delete song here. NOTE : do not make change to database before users
		// click on save button
		// Step 1: refresh current playlist that is data binding to listview
		mRemainSongs.remove(song);
		mSelectedSongs.add(song);
		// Step 2: refresh listview
		adapter.notifyDataSetChanged();
		// Step 3: enable save button
		if (mSelectedSongs.size() > 0)
			// saveLayout.setEnabled(true);
			enableSaveLayout();

	}

}
