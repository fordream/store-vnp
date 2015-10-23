package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.ServerSessionInvalidListener;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.database.PlaylistStatusListener;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.DownloadStatusListener;
import vn.com.vega.music.downloadmanager.EventNotifier;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.syncmanager.SyncStatusListener;
import vn.com.vega.music.syncmanager.SynchronizeManager;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.MyPlaylistAdapter;
import vn.com.vega.music.view.gui.FacebookGmailListener;
import vn.com.vega.music.view.holder.MyPlaylistStack;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyPlaylistActivity extends AbstractSongAcitivity implements
		DownloadStatusListener, SyncStatusListener, View.OnClickListener {

	//

	public static final int ONLINE_PLAYLIST = -1;
	public static final int DOWNLOADED_PLAYLIST = 1; // 0
	public static final int LOCAL_PLAYLIST = 2; // 1
	public static final int FAVOURITE_PLAYLIST = 3;// 2
	public static final int NORMAL_PLAYLIST = 4;// 3

	public static final int VIEW_LIST_PLAYLIST = 0;
	public static final int VIEW_SELECT_TO_DOWNLOAD = 1;
	public static final int VIEW_DOWNLOADING_SONG = 2;

	public static final String CLASS_NAME = MyPlaylistActivity.class.getName();

	private int myState = VIEW_LIST_PLAYLIST;
	private List<Playlist> selectedItemList = new ArrayList<Playlist>();
	static DataStore dataStore = null;
	public static boolean justCancelDownload = false;
	private boolean isFirst;

	private Context context = this;
	private Activity activity;

	private ListView selectorListView = null;
	private MyPlaylistAdapter selectorAdapter = null;
	private ListView mainListView = null;
	private MyPlaylistAdapter mainAdapter = null;
	private ListView downloadingListView = null;
	private MyPlaylistAdapter downloadingAdapter = null;
	private Button startDownloadBtn; // download button in selector popup
	private Button closeDialogBtn;
	private Dialog selectorDlg;
	private static ProgressDialog syncInProgressDlg;
	private LinearLayout noPlaylist2Download, noSongDownloading;

	private TextView fixed_title, fixed_info;
	private ProgressBar fixed_download_bar;

	private ImageView downloadingImg;

	private ArrayList<Object> mData;

	private boolean networkConnected = true;
	private MyPlaylistStack activityStack;

	private ImageView downloadImg;

	private RelativeLayout fixedDownloadingRow;

	@Override
	protected void onPause() {
		super.onPause();
		// unreg listeners
		SynchronizeManager.removeSyncStatusListener(CLASS_NAME);
		DownloadManager.removeDownloadStatusListener(CLASS_NAME);

	}

	private void showDownloadingImg() {
		if (downloadingImg != null)
			downloadingImg.setVisibility(ImageView.VISIBLE);
	}

	private void hideDownloadingImg() {
		if (downloadingImg != null)
			downloadingImg.setVisibility(ImageView.GONE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// reg listeners
		if (!dataStore.isInOfflineMode()) {
			SynchronizeManager.addSyncStatusListener(CLASS_NAME, this);
			DownloadManager.removeDownloadStatusListener(CLASS_NAME);
			DownloadManager.addDownloadStatusListener(CLASS_NAME, this);

			if (!SynchronizeManager.isRunning()) {
				SynchronizeManager.startSyncThread();
			}
		}

		if (myState == VIEW_LIST_PLAYLIST) {
			// start download if there are songs to doanload and download thread
			// is not running

			if (!dataStore.isInOfflineMode()) {
				if ((dataStore.getSongDownloadPool().size() > 0)
						&& !DownloadManager.isRunning()) {
					DownloadManager.startDownload();
					showTotalDownloadPercent(0, dataStore.getSongDownloadPool()
							.size());
					showDownloadingImg();
				} else {
					if (DownloadManager.isRunning()) {
						int all = DownloadManager.getCountAllSong();
						int dled = DownloadManager.getCountDownloadedSong();
						showTotalDownloadPercent(dled, all);
						showDownloadingImg();
					} else {
						hideTotalDownloadPercent();
						hideDownloadingImg();
					}
				}
			}

			// refreshData();

			if (!isFirst) {
				refreshData();
			}
			isFirst = false;
		}
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onResume();
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (syncInProgressDlg != null && syncInProgressDlg.isShowing())
				syncInProgressDlg.dismiss();
			initializeComponentView();
		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// remove default title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_myplaylist);
		context = this;
		activity = this;
		isFirst = true;
		activityStack = (MyPlaylistStack) getParent();
		dataStore = DataStore.getInstance();

		// DownloadManager.removeDownloadStatusListener(CLASS_NAME);
		// DownloadManager.addDownloadStatusListener(CLASS_NAME, this);

		networkConnected = NetworkUtility.getNetworkStatus() == NetworkUtility.CONNECTION_TYPE_OFF ? false
				: true;

		syncInProgressDlg = new ProgressDialog(getParent());
		syncInProgressDlg
				.setMessage(getString(R.string.my_playlist_in_sync_progress));
		syncInProgressDlg.setCancelable(true);
		syncInProgressDlg.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				mData = getDataList();
				mHandler.sendEmptyMessage(0);
			}
		}).start();

		// initializeComponentView();
	}

	@Override
	public void onClick(View v) {
		int all;
		int dled;
		switch (v.getId()) {

		case R.id.my_playlist_region_botton:
		case R.id.my_playlist_total_progress_txt:
		case R.id.my_playlist_total_progress_bar:
			showDownloadingSongList();
			break;

		case R.id.my_playlist_download_btn:

			if (dataStore.isInOfflineMode())
				askGoToOnline();
			else {
				if (networkConnected) {
					selectedItemList.clear(); // clear data before showing
					showPlaylistToDownloadSelector(); // show popup
				} else
					Toast.makeText(getParent(), "Không có kết nối mạng",
							Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.my_playlist_download_selector_download_btn:
			selectorDlg.dismiss();
			myState = VIEW_LIST_PLAYLIST;

			for (Playlist playlist : selectedItemList) {
				dataStore.addPlaylistToDownloadPool(playlist.id);
				dataStore.addSongsToSongDownloadPool(playlist.getSongList());
			}
			justCancelDownload = false;
			DownloadManager.removeDownloadStatusListener(CLASS_NAME);
			DownloadManager.addDownloadStatusListener(CLASS_NAME, this);
			DownloadManager.startDownload(); // start new thread download
			int poolSize = dataStore.getSongDownloadPool().size();
			DownloadManager.setCountAllSong(poolSize);
			dled = DownloadManager.getCountDownloadedSong();
			showTotalDownloadPercent(dled, (poolSize + dled));
			refreshData();
			break;

		case R.id.my_playlist_downloading_cancel_download_btn:
			selectorDlg.dismiss();
			myState = VIEW_LIST_PLAYLIST;
			justCancelDownload = true;
			DownloadManager.stopDownload(); // stop download
			// clear download pool
			dataStore.clearPlaylistDownloadPool();
			dataStore.clearSongDownloadPool();
			hideTotalDownloadPercent();
			refreshData();
			break;

		case R.id.my_playlist_download_selector_close_btn:
			selectorDlg.dismiss();
			myState = VIEW_LIST_PLAYLIST;
			if (!DownloadManager.isRunning()) {
				hideTotalDownloadPercent();
			} else {
				all = DownloadManager.getCountAllSong();
				dled = DownloadManager.getCountDownloadedSong();
				showTotalDownloadPercent(dled, all);
			}
			refreshData();
			break;

		case R.id.my_playlist_downloading_close_btn:
			selectorDlg.dismiss();
			myState = VIEW_LIST_PLAYLIST;
			if (!DownloadManager.isRunning()) {
				hideTotalDownloadPercent();
			} else {
				all = DownloadManager.getCountAllSong();
				dled = DownloadManager.getCountDownloadedSong();
				showTotalDownloadPercent(dled, all);
			}
			refreshData();
			break;

		case R.id.common_message_dialog_close_btn:
			selectorDlg.dismiss();
			myState = VIEW_LIST_PLAYLIST;
			refreshData();
			break;

		default:
			break;
		}
	}

	private TextView totalDownloadTxt;
	private ProgressBar totalDownloadBar;
	private RelativeLayout totalDownloadRegion;

	private void showTotalDownloadPercent(int dlded, int total) {
		if (totalDownloadRegion == null) {
			totalDownloadTxt = (TextView) findViewById(R.id.my_playlist_total_progress_txt);
			totalDownloadBar = (ProgressBar) findViewById(R.id.my_playlist_total_progress_bar);
			totalDownloadRegion = (RelativeLayout) findViewById(R.id.my_playlist_region_botton);
			totalDownloadRegion.setOnClickListener(this);
			totalDownloadTxt.setOnClickListener(this);
			totalDownloadBar.setOnClickListener(this);
		}
		// show common status
		totalDownloadRegion.setVisibility(RelativeLayout.GONE);

		if (total > 0) {
			totalDownloadBar.setProgress(dlded * 100 / total);
			totalDownloadTxt.setText("" + dlded + "/" + total);
		}
	}

	private void hideTotalDownloadPercent() {
		if (totalDownloadRegion != null) {
			totalDownloadRegion.setVisibility(RelativeLayout.GONE);
		}
	}

	private void hideFixedDownloadingRow() {
		if (fixedDownloadingRow != null)
			fixedDownloadingRow.setVisibility(RelativeLayout.GONE);
	}

	private void showFixedDownloadingRow() {
		if (fixedDownloadingRow != null)
			fixedDownloadingRow.setVisibility(RelativeLayout.VISIBLE);
	}

	private void initializeComponentView() {
		// init controls on view
		Button downloadBtn = (Button) findViewById(R.id.my_playlist_download_btn);
		downloadingImg = (ImageView) findViewById(R.id.downloading_img);
		downloadingImg.setOnClickListener(onDownloadingImgListener);
		downloadBtn.setOnClickListener(this);

		myState = VIEW_LIST_PLAYLIST;
		mainListView = (ListView) findViewById(R.id.my_playlist_listview);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		View headerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		mainListView.addHeaderView(headerView);
		mainListView.addFooterView(footerView);
		mainListView.setFastScrollEnabled(true);
		// mainAdapter = new MyPlaylistAdapter(context, dataStore, false,
		// getDataList());
		mainAdapter = new MyPlaylistAdapter(context, dataStore, false, mData);
		// mainListView.addFooterView(new View(context));
		mainListView.setAdapter(mainAdapter);
		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do stuffs. show playlist detail activity

				if (position == LOCAL_PLAYLIST) {
					Bundle bundle = new Bundle();
					// bundle.putBoolean(Const.BUNDLE_DOWNLOADED_MUSIC, false);
					bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE,
							LocalMusicActivity.TYPE_LOCAL);
					Intent intent = new Intent(getParent(),
							LocalMusicActivity.class);
					intent.putExtras(bundle);
					activityStack.startChildActivity(
							LocalMusicActivity.class.getName(), intent);
				} else if (position == DOWNLOADED_PLAYLIST) {
					Bundle bundle = new Bundle();
					// bundle.putBoolean(Const.BUNDLE_DOWNLOADED_MUSIC, true);
					bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE,
							LocalMusicActivity.TYPE_DOWNLOADED);
					Intent intent = new Intent(getParent(),
							LocalMusicActivity.class);
					intent.putExtras(bundle);
					activityStack.startChildActivity(
							LocalMusicActivity.class.getName(), intent);
				} else if (position == FAVOURITE_PLAYLIST) {
					Bundle bundle = new Bundle();
					// bundle.putBoolean(Const.BUNDLE_DOWNLOADED_MUSIC, true);
					bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE,
							LocalMusicActivity.TYPE_FAVOURITE);
					Intent intent = new Intent(getParent(),
							LocalMusicActivity.class);
					intent.putExtras(bundle);
					activityStack.startChildActivity(
							LocalMusicActivity.class.getName(), intent);
				} else {
					Object obj = parent.getItemAtPosition(position);
					Playlist playlist = (Playlist) obj;
					if (playlist.type != Const.FIRST_SECTION_HEADER
							&& playlist.type != Const.SECOND_SECTION_HEADER) {
						Bundle bundle = new Bundle();
						bundle.putInt(Const.BUNDLE_PLAYLIST_ID, playlist.id);
						if (playlist.type == Playlist.TYPE_FAVOURITE_EX) {
							Session.putSharedObject(bundle,
									MyPlaylistActivity.this, playlist);
							bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE,
									MyPlaylistActivity.ONLINE_PLAYLIST);
						}

						else
							bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE, position);
						Intent i = new Intent(getParent(),
								MyPlaylistDetailActivity.class);
						i.putExtras(bundle);
						activityStack.startChildActivity(
								Const.MY_PLAYLIST_DETAIL, i);
					}

				}
			}
		});
		mainListView.setOnScrollListener(mainAdapter);
	}

	OnClickListener onDownloadingImgListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDownloadingSongList();
		}
	};

	// when users click on checkbox

	// ===== create menu =====///
	@Override
	public boolean onCreateOptionsMenu(Menu mMenu) {
		return false;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	// show when click on download button
	public void showPlaylistToDownloadSelector() {
		myState = VIEW_SELECT_TO_DOWNLOAD;
		selectorDlg = new Dialog(getParent());
		selectorDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		selectorDlg.setCancelable(false);
		selectorDlg.setContentView(R.layout.layout_dialog_selector_download);
		selectorListView = (ListView) selectorDlg
				.findViewById(R.id.my_playlist_download_selector_listview);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_footer_playlist_selector, null,
						false);
		selectorListView.addFooterView(footerView);
		// selectorListView.addFooterView(footerView);
		startDownloadBtn = (Button) selectorDlg
				.findViewById(R.id.my_playlist_download_selector_download_btn);
		closeDialogBtn = (Button) selectorDlg
				.findViewById(R.id.my_playlist_download_selector_close_btn);
		noPlaylist2Download = (LinearLayout) selectorDlg
				.findViewById(R.id.my_playlist_download_selector_empty);
		startDownloadBtn.setEnabled(false);
		startDownloadBtn.setTextColor(Color.parseColor("#666666"));
		startDownloadBtn.setOnClickListener(this);
		closeDialogBtn.setOnClickListener(this);
		selectorAdapter = new MyPlaylistAdapter(context, dataStore, true,
				getDataList());
		selectorListView.setAdapter(selectorAdapter);
		if (selectorAdapter.getCount() <= 0) {
			noPlaylist2Download.setVisibility(View.VISIBLE);
			startDownloadBtn.setTextColor(Color.parseColor("#666666"));
		} else {
			noPlaylist2Download.setVisibility(View.GONE);
		}
		selectorDlg.show();
		selectorListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
				// checkboxClickHandler(view);
			}
		});
	}

	public void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
		builder.setMessage(R.string.offline_mode_msg)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
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

	//
	public void showDownloadingSongList() {
		myState = VIEW_DOWNLOADING_SONG;
		selectorDlg = new Dialog(getParent());
		selectorDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		selectorDlg.setCancelable(false);
		selectorDlg.setContentView(R.layout.layout_dialog_downloading);
		noSongDownloading = (LinearLayout) selectorDlg
				.findViewById(R.id.my_playlist_downloading_empty);
		downloadingListView = (ListView) selectorDlg
				.findViewById(R.id.my_playlist_downloading_listview);
		fixedDownloadingRow = (RelativeLayout) selectorDlg
				.findViewById(R.id.fixed_row);
		fixed_title = (TextView) selectorDlg
				.findViewById(R.id.fixed_row_first_txt);
		fixed_info = (TextView) selectorDlg
				.findViewById(R.id.fixed_row_second_txt);
		fixed_download_bar = (ProgressBar) selectorDlg
				.findViewById(R.id.fixed_row_download_bar);

		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_footer_song_downloading, null,
						false);
		// downloadingListView.addHeaderView(footerView);
		downloadingListView.addFooterView(footerView);
		closeDialogBtn = (Button) selectorDlg
				.findViewById(R.id.my_playlist_downloading_close_btn);
		Button _stopDownloadBtn = (Button) selectorDlg
				.findViewById(R.id.my_playlist_downloading_cancel_download_btn);
		_stopDownloadBtn.setOnClickListener(this);
		_stopDownloadBtn.setEnabled(true);
		_stopDownloadBtn.setTextColor(Color.parseColor("#000000"));
		closeDialogBtn.setOnClickListener(this);
		downloadingAdapter = new MyPlaylistAdapter(context, dataStore, false,
				getDataList());
		downloadingListView.setAdapter(downloadingAdapter);
		if (downloadingAdapter.getCount() <= 0) {
			noSongDownloading.setVisibility(View.VISIBLE);
		} else {
			noSongDownloading.setVisibility(View.GONE);
		}
		selectorDlg.show();
		downloadingListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
			}
		});
		downloadingListView.setOnScrollListener(downloadingAdapter);
		if (downloadingAdapter.getCount() > 12)
			showFixedDownloadingRow();
		else
			hideFixedDownloadingRow();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private MyPlaylistAdapter getCurrentAdapter() {
		switch (myState) {
		case VIEW_LIST_PLAYLIST:
			return mainAdapter;

		case VIEW_SELECT_TO_DOWNLOAD: {
			return selectorAdapter;
		}

		case VIEW_DOWNLOADING_SONG:
			return downloadingAdapter;
		}
		return null;
	}

	public class PlaylistComparator implements Comparator<Object> {
		public int compare(Object oo1, Object oo2) {
			if ((oo1 instanceof Playlist) && (oo2 instanceof Playlist)) {
				Playlist o1 = (Playlist) oo1;
				Playlist o2 = (Playlist) oo2;
				if (o1.type == o2.type) {
					return 0;
				}

				// Downloaded first
				if (o1.type == Playlist.TYPE_DOWNLOADED) {
					return -1;
				} else if (o2.type == Playlist.TYPE_DOWNLOADED) {
					return 1;
				}

				// Then local song
				if (o1.type == Playlist.TYPE_LOCAL_SONGS) {
					return -1;
				} else if (o2.type == Playlist.TYPE_LOCAL_SONGS) {
					return 1;
				}

				/*
				 * later
				 */

				/*
				 * 
				 * // Then inbox if (o1.type == Playlist.TYPE_INBOX) { return
				 * -1; } else if (o2.type == Playlist.TYPE_INBOX) { return 1; }
				 */

				// Then favorite
				if (o1.type == Playlist.TYPE_FAVORITE) {
					return -1;
				} else if (o2.type == Playlist.TYPE_FAVORITE) {
					return 1;
				}

				// Then user created
				if (o1.type == Playlist.TYPE_USER_CREATED) {
					return -1;
				} else if (o2.type == Playlist.TYPE_USER_CREATED) {
					return 1;
				}

				// Then user liked
				if (o1.type == Playlist.TYPE_FAVOURITE_EX) {
					return -1;
				} else if (o2.type == Playlist.TYPE_FAVOURITE_EX) {
					return 1;
				}
			}

			return 0;
		}

		public boolean equals(Object obj) {
			if (obj instanceof PlaylistComparator) {
				return true;
			}
			return false;
		}
	}

	private List<Playlist> getAllPlaylist(boolean selection) {
		ArrayList<Playlist> result = new ArrayList<Playlist>();

		result.addAll(dataStore.getListPlaylist());

		if (selection) {
			Playlist pl = new Playlist();
			pl.type = Playlist.TYPE_DOWNLOADED;
			pl.addAll(dataStore.getListDownloadedSongs());
			result.add(pl);

			Playlist pl2 = new Playlist();
			pl2.type = Playlist.TYPE_LOCAL_SONGS;
			pl2.addAll(dataStore.loadAllLocalSong());
			result.add(pl2);
		}

		return result;
	}

	public ArrayList<Object> getDataList() {
		ArrayList<Object> result = new ArrayList<Object>();
		if (myState == VIEW_LIST_PLAYLIST) {
			result.addAll(getAllPlaylist(true));
			Collections.sort(result, new PlaylistComparator());

		} else if (myState == VIEW_SELECT_TO_DOWNLOAD) {
			List<Playlist> list = getAllPlaylist(false);
			for (Playlist playlist : list) {
				if ((playlist.count() > 0)
						&& !playlist.isAllSongCachedOrWaitDownload()) {
					result.add(playlist);
				}
			}
			Collections.sort(result, new PlaylistComparator());

		} else if (myState == VIEW_DOWNLOADING_SONG) {
			List<Song> downloadingSongs = dataStore.getSongDownloadPool();
			result.addAll(downloadingSongs);
		}
		return result;
	}

	public void refreshData() {
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.refreshData(getDataList());
		}
	}

	protected void checkShowFirstSync() {
		if (!dataStore.isAlreadySyncOne()) {
			if (syncInProgressDlg == null) {
				syncInProgressDlg = new ProgressDialog(getParent());
				syncInProgressDlg
						.setMessage(getString(R.string.my_playlist_in_sync_progress));
				syncInProgressDlg.setCancelable(true);
				syncInProgressDlg.show();
			} else {
				if (!syncInProgressDlg.isShowing())
					syncInProgressDlg.show();
			}
		}
	}

	@Override
	public void onSyncStart() {
		checkShowFirstSync();
	}

	@Override
	public void onSyncDone() {
		if (syncInProgressDlg != null && syncInProgressDlg.isShowing()) {
			syncInProgressDlg.dismiss();
		}
		refreshData();
	}

	@Override
	public void onPlaylistDownloadDone(Playlist pl) {
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onPlaylistDownloadDone(pl);
			mpa.notifyDataSetChanged();
		}
	}

	@Override
	public void onPlaylistDownloadStart(Playlist playlist) {
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onPlaylistDownloadStart(playlist);
			mpa.notifyDataSetChanged();

		}
	}

	@Override
	public void onPlaylistDownloadProgress(Playlist pl, int totalSong,
			int downloadedSong) {
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onPlaylistDownloadProgress(pl, totalSong, downloadedSong);
			mpa.notifyDataSetChanged();
		}
	}

	@Override
	public void onSongDownloadStart(Song song) {
		if (myState == VIEW_DOWNLOADING_SONG) {
			fixed_title.setText(song.title);

			fixed_info.setText("Đang kết nối");
			fixed_download_bar.setVisibility(ProgressBar.GONE);
		}
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onSongDownloadStart(song);
		}
	}

	@Override
	public void onSongDownloadDone(Song sg) {
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onSongDownloadDone(sg);
			List<Object> objs = getDataList();

			mpa.refreshData(objs); // Refresh data to remove download
									// song from downloading screen
			if (objs.size() > 0) {
				noSongDownloading.setVisibility(LinearLayout.GONE);
				if (objs.size() > 12)
					showFixedDownloadingRow();
				else
					hideFixedDownloadingRow();
			} else {
				noSongDownloading.setVisibility(LinearLayout.VISIBLE);
				Button stopDownloadBtn = (Button) selectorDlg
						.findViewById(R.id.my_playlist_downloading_cancel_download_btn);
				stopDownloadBtn.setTextColor(Color.parseColor("#666666"));
				stopDownloadBtn.setEnabled(false);
			}
		}
	}

	@Override
	public void onSongDownloadProgress(Song sg, int total, int downloaded) {

		if (myState == VIEW_DOWNLOADING_SONG) {
			// showFixedDownloadingRow();
			fixed_download_bar.setVisibility(ProgressBar.VISIBLE);
			fixed_title.setText(sg.title);
			int status = (int) ((downloaded * 100) / total);
			fixed_info.setText("(" + status + "%) " + sg.artist_name);
			fixed_download_bar.setProgress(status);
		}

		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onSongDownloadProgress(sg, total, downloaded);
		}
	}

	@Override
	public void onSongDownloadError(Song sg, int errorCode) {
		if (errorCode == EventNotifier.ERROR_NONE) {
			return;
		}
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onSongDownloadError(sg, errorCode);
		}
		if (errorCode == EventNotifier.ERROR_SAVING_FAILED) {
			Toast.makeText(getParent(),
					R.string.my_playlist_download_error_diskfull,
					Toast.LENGTH_LONG).show();
		} else if (errorCode != EventNotifier.ERROR_POSTPONED) {
			Toast.makeText(getParent(),
					R.string.my_playlist_download_error_network,
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onDownloadStart(int total) {
		if (myState == VIEW_LIST_PLAYLIST) {
			showTotalDownloadPercent(0, total);
			showDownloadingImg();
		}
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onDownloadStart(total);
		}
	}

	@Override
	public void onDownloadProgress(int totalSong, int downloadedSong) {
		if (myState == VIEW_LIST_PLAYLIST) {
			showTotalDownloadPercent(downloadedSong, totalSong);
		}
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onDownloadProgress(totalSong, downloadedSong);
		}
	}

	@Override
	public void onDownloadStopped() {
		hideTotalDownloadPercent(); // hide bottom region
		hideDownloadingImg();
		hideFixedDownloadingRow();
		MyPlaylistAdapter mpa = getCurrentAdapter();
		if (mpa != null) {
			mpa.onDownloadStopped();
		}

		if (DownloadManager.isLastSongError()) {
			Toast.makeText(getParent(), R.string.my_playlist_download_stopped,
					Toast.LENGTH_SHORT).show();
		} else {
			if ((dataStore.getSongDownloadPool().size() > 0)
					|| MyPlaylistActivity.justCancelDownload) {
				// Toast.makeText(getParent(),
				// R.string.my_playlist_download_stopped,
				// Toast.LENGTH_SHORT).show();
			} else {
				// Toast.makeText(getParent(),
				// R.string.my_playlist_download_completed,
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onDownloadInOfflineMode() {
		// TODO Auto-generated method stub
		Toast.makeText(getParent(), "Đang ở chế độ offline", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onDownloadVia3gDisable() {
		// TODO Auto-generated method stub
		Toast.makeText(getParent(), "Không cho phép tải nhạc qua 3G",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onThumbnailClickListener(View v) {
		// TODO Auto-generated method stub
		if (myState == VIEW_DOWNLOADING_SONG) {
			Song song = (Song) v.getTag();
			if (song.isWaitToDownload()) {
				if (dataStore.removeSongFromSongDownloadPool(song.id)) {
					MyPlaylistAdapter mpa = getCurrentAdapter();
					if (mpa != null) {
						mpa.refreshData(getDataList());
					}
				}
			}
		}
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		Playlist playlist = (Playlist) v.getTag();
		CheckBox checkbox = (CheckBox) v;
		if (checkbox.isChecked() && playlist.getSongList().size() > 0
				&& !playlist.isAllSongCached()) {
			selectedItemList.add(playlist);
			startDownloadBtn.setEnabled(true);
			startDownloadBtn.setTextColor(Color.parseColor("#000000"));
		} else if (!checkbox.isChecked()) {
			if (selectedItemList.contains(playlist))
				selectedItemList.remove(playlist);
			if (selectedItemList.size() == 0) {
				startDownloadBtn.setEnabled(false);
				startDownloadBtn.setTextColor(Color.parseColor("#666666"));
			}

		}
	}

}