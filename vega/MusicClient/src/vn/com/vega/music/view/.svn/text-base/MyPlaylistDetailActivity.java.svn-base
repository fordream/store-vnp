package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import vn.com.vega.chacha.R;
import vn.com.vega.common.Session;
import vn.com.vega.music.clientserver.JsonPlaylist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.DownloadStatusListener;
import vn.com.vega.music.downloadmanager.EventNotifier;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.social.FacebookUtility;
import vn.com.vega.music.social.ShareUtility;
import vn.com.vega.music.syncmanager.SyncStatusListener;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.custom.LoaddingMore;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.gui.ContextMenuExListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class MyPlaylistDetailActivity extends Activity implements
		DownloadStatusListener, SyncStatusListener {

	protected static final String LOG_TAG = Const.LOG_PREF
			+ MyPlaylistDetailActivity.class.getName();

	private int playlistType;
	private int playlistId;

	private String mShareMsg;
	private String mShareLink;

	private String savedName = "";
	private String savedCreator = "";
	private ImageLoader mImageLoader;
	private ImageView creatorImg;

	private ImageView downloadImg;

	private Playlist currPlaylist = null;
	private List<Song> songs = null;
	private List<Song> songsClone = new ArrayList<Song>();
	private MyPlaylistDetailActivity myPlaylistDetailActivity;

	private Context context;
	private Activity activity;
	// private EditText searchEdt;
	private DataStore dataStore = null;
	private LinearLayout noSongLayout, likeLayout, editLayout, shareLayout,
			editAndShareLayout;
	private RelativeLayout headerLayout, dividerLayout;
	private TextView titleTxt, creatorTxt, likeTxt;
	private TextView noSongTxt;
	private ListView lv;
	private EfficientAdapter adapter;
	private Dialog dialog;
	private ProgressDialog pd;
	private Button backBtn;

	private boolean isFirstTime = true;
	private boolean isScrolling = false;
	protected static final int CONNECTING_VALUE = 0;
	protected static final int DOWNLOADED_VALUE = 100;
	protected static final int WAITING_VALUE = 101;

	protected static final int SHARE_SUCCESS = 5;
	protected static final int SHARE_FAILED = 6;
	protected static final int INIT_SUCCESS = 4;
	protected static final int RESUME_SUCCESS = 7;

	// store download progress
	protected Hashtable<Integer, Integer> hashSongProgress = new Hashtable<Integer, Integer>();
	// store song position
	protected Hashtable<Integer, Integer> hashSongPosition = new Hashtable<Integer, Integer>();

	protected Integer firstVisibleItem;
	protected Integer lastVisibleItem;

	// truongvv
	// private LoaddingMore loadMoreProgress;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_myplaylist_detail);
		context = this;
		activity = this;
		// is first time
		isFirstTime = true;
		mImageLoader = ImageLoader.getInstance(this);
		myPlaylistDetailActivity = this;

		// init data
		dataStore = DataStore.getInstance();
		// get data pass through intent
		Bundle bd = this.getIntent().getExtras();
		playlistType = (int) bd.getInt(Const.BUNDLE_PLAYLIST_TYPE);
		playlistId = (int) bd.getInt(Const.BUNDLE_PLAYLIST_ID);
		
		// init some data
		hideHeaderLayout();

		// truongvv
		String message = getString(R.string.loading);
		pd = ProgressDialog.show(getParent(), "", message, true);
		// loadMoreProgress = (LoaddingMore) findViewById(R.id.loaddingMore1);
		// loadMoreProgress.setText(R.string.loading);

		Thread dataInitializationThread = new Thread() {
			public void run() {

				setDataView();
				mHandler.sendEmptyMessage(INIT_SUCCESS);
			}
		};
		dataInitializationThread.start();

	}

	private void showHeaderLayout() {
		if (headerLayout != null)
			headerLayout.setVisibility(RelativeLayout.VISIBLE);
		if (dividerLayout != null)
			dividerLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void hideHeaderLayout() {
		if (headerLayout != null) {
			headerLayout.setVisibility(RelativeLayout.GONE);
		}
		if (dividerLayout != null)
			dividerLayout.setVisibility(RelativeLayout.GONE);
	}

	private void setDataView() {
		switch (playlistType) {
		case MyPlaylistActivity.DOWNLOADED_PLAYLIST:
			songs = dataStore.getListDownloadedSongs();
			break;
		case MyPlaylistActivity.LOCAL_PLAYLIST:
			songs = dataStore.loadAllLocalSong();
			break;
		case MyPlaylistActivity.ONLINE_PLAYLIST: {
			Playlist received = (Playlist) Session.getSharedObject(getIntent()
					.getExtras());
			if (received != null) {
				currPlaylist = received;
			}
			if (currPlaylist == null)
				songs = new ArrayList<Song>();
			else {
				JsonSong jso = JsonSong
						.loadSongListByPlaylistId(currPlaylist.serverId);
				if (jso.isSuccess()) {
					songs = jso.songs;
					currPlaylist.setSongList(songs);
				} else {
					songs = new ArrayList<Song>();
				}
			}

			break;
		}

		default:
			currPlaylist = dataStore.getPlaylistByID(playlistId);
			if (currPlaylist == null)
				songs = new ArrayList<Song>();
			else {

				songs = currPlaylist.getSongList();
				savedCreator = currPlaylist.userMsisdn;
				savedName = currPlaylist.title;
			}
			break;
		}
		if (playlistType >= MyPlaylistActivity.FAVOURITE_PLAYLIST) {
			List<Song> waitingSongs = dataStore.getSongDownloadPool();
			for (Song song : waitingSongs) {
				hashSongProgress.put(song.id, WAITING_VALUE);
			}
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		// Unregister download listener
		DownloadManager.removeDownloadStatusListener(LOG_TAG);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// do stuffs
		if (dataStore == null)
			dataStore = DataStore.getInstance();

		// reg to listen download status
		// i clear before adding new because DownloadManager sometimes does
		// not fire download status if there are many listeners at the same time
		DownloadManager.addDownloadStatusListener(LOG_TAG,
				myPlaylistDetailActivity);
		if (isFirstTime)
			isFirstTime = false;
		else if (adapter != null) {
			if(pd != null && !pd.isShowing())
				pd = ProgressDialog.show(getParent(), "", getString(R.string.loading), true);
			Thread dataInitializationThread = new Thread() {
				public void run() {

					setDataView();
					mHandler.sendEmptyMessage(RESUME_SUCCESS);
				}
			};
			dataInitializationThread.start();
			
		}

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putInt(Const.BUNDLE_PLAYLIST_ID, playlistId);
		savedInstanceState.putInt(Const.BUNDLE_PLAYLIST_TYPE, playlistType);

		savedInstanceState.putString("saved_name", currPlaylist.title);
		savedInstanceState.putString("saved_creator", currPlaylist.userMsisdn);

		// etc.
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		playlistId = savedInstanceState.getInt(Const.BUNDLE_PLAYLIST_ID);
		playlistType = savedInstanceState.getInt(Const.BUNDLE_PLAYLIST_TYPE);

		savedName = savedInstanceState.getString("saved_name");
		savedCreator = savedInstanceState.getString("saved_creator");

	}

	private void initializeComponentView() {

		// setup listview
		lv = (ListView) findViewById(R.id.my_playlist_detail_listview);
		View headerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		lv.addHeaderView(headerView);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		lv.addFooterView(footerView);

		noSongLayout = (LinearLayout) findViewById(R.id.my_playlist_detail_empty);
		likeLayout = (LinearLayout) findViewById(R.id.like_layout);
		editLayout = (LinearLayout) findViewById(R.id.edit_layout);
		shareLayout = (LinearLayout) findViewById(R.id.share_layout);
		likeTxt = (TextView) findViewById(R.id.like_layout_txt);
		editLayout.setOnClickListener(onEditLayoutListener);
		shareLayout.setOnClickListener(onShareLayoutListener);
		likeLayout.setOnClickListener(onLikeLayoutListener);
		creatorImg = (ImageView) findViewById(R.id.creator_icon);

		editAndShareLayout = (LinearLayout) findViewById(R.id.edit_and_share_layout);
		headerLayout = (RelativeLayout) findViewById(R.id.header_region);
		dividerLayout = (RelativeLayout) findViewById(R.id.my_playlist_detail_region_second);
		noSongTxt = (TextView) findViewById(R.id.my_playlist_detail_empty_txt);
		titleTxt = (TextView) findViewById(R.id.playlist_title);
		creatorTxt = (TextView) findViewById(R.id.creator_title);

		backBtn = (Button) findViewById(R.id.playlist_detail_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);

		setHeaderView();

		adapter = new EfficientAdapter(this);
		// lv.addFooterView(footerView);
		lv.setAdapter(adapter);
		lv.setFastScrollEnabled(true);
		// for long-click on each row of listview
		// if not is local music
		if (playlistType != MyPlaylistActivity.LOCAL_PLAYLIST)
			registerForContextMenu(lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
				// start player activity
				Intent _i = new Intent();
				ArrayList<Song> _songArrayList = new ArrayList<Song>();
				if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
					_songArrayList = (ArrayList<Song>) dataStore
							.getListDownloadedSongs();
					NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position,
							_songArrayList);
				} else if (playlistType == MyPlaylistActivity.LOCAL_PLAYLIST) {
					_songArrayList = (ArrayList<Song>) dataStore
							.loadAllLocalSong();
					NowPlayingList.init(NowPlayingList.TYPE_LOCAL,
							position - 1, _songArrayList);
				} else if (playlistType == MyPlaylistActivity.ONLINE_PLAYLIST) {
					if(currPlaylist != null){
						_songArrayList = (ArrayList<Song>) currPlaylist.getSongList();
						NowPlayingList.init(NowPlayingList.TYPE_NORMAL,
								position - lv.getHeaderViewsCount(), _songArrayList);
					}
				} else {

					// bi null dataStore.getPlaylistByID(playlistId)
					_songArrayList = (ArrayList<Song>) dataStore
							.getPlaylistByID(playlistId).getSongList();
					NowPlayingList.init(NowPlayingList.TYPE_NORMAL,
							position - 1, _songArrayList);
				}

				_i.setClass(getApplicationContext(), NowPlayingActivity.class);

				startActivity(_i);
			}
		});

		lv.setOnScrollListener(new OnScrollListener() {
			public void onScroll(AbsListView view, int _firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				// remember these value to determine whether i should refresh
				// listview or not
				firstVisibleItem = _firstVisibleItem;
				lastVisibleItem = _firstVisibleItem + visibleItemCount;
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == 0) {
					isScrolling = false;
				} else
					isScrolling = true;
			}
		});
	}

	OnClickListener onDownloadImgListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			thumbnailClickHandler(null);
		}
	};

	OnClickListener onEditLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// if (songs.size() > 0) {
			Bundle bundle = new Bundle();
			bundle.putInt(Const.BUNDLE_PLAYLIST_ID, playlistId);
			bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE, playlistType);
			Intent i = new Intent(getParent(), MyPlaylistEditorActivity.class);
			i.putExtras(bundle);
			startActivity(i);
			// }
		}
		
	};

	OnClickListener onShareLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			ShareUtility shareUtis = new ShareUtility(getParent(),
					activity.getParent(), false);
			shareUtis.setPlaylistId(playlistId);
			shareUtis.showShareChooser();
		}
	};

	private void sharePlaylist() {
		if (NetworkUtility.hasNetworkConnection()) {
			// show progress dialog while load data from database
			pd = ProgressDialog.show(getParent(), "", "Đang lấy thông tin...",
					true);
			// loadMoreProgress.setVisibility(View.VISIBLE);
			// loadMoreProgress.setText(R.string.loading);
			Thread dataInitializationThread = new Thread() {
				public void run() {
					JsonPlaylist jso = JsonPlaylist.sharePlaylist(playlistId);
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

	private boolean onlinePlaylistIsCached(int serverId) {
		boolean result = false;
		result = dataStore.checkOnlinePlaylist(serverId);
		return result;
	}

	// handle init data result
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// truongvv
			pd.dismiss();
			// loadMoreProgress.setVisibility(View.GONE);
			switch (msg.what) {
			case RESUME_SUCCESS:
				setHeaderView();
				adapter.refreshData();
				adapter.notifyDataSetChanged();
				break;
			case INIT_SUCCESS:
				// draw view
				initializeComponentView();
				showHeaderLayout();
				break;
			case 0:
				likeTxt.setText("Bỏ thích");
				currPlaylist.type = Playlist.TYPE_FAVOURITE_EX;
				dataStore.addPlaylist(currPlaylist, false);
				break;
			case 1:
				likeTxt.setText("Thích");
				Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				likeTxt.setText("Thích");
				dataStore.removePlaylist(
						dataStore.getLocalId(currPlaylist.serverId), false);
				break;
			case 3:
				likeTxt.setText("Bỏ thích");
				Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT)
						.show();
				break;
			case SHARE_FAILED:
				Toast.makeText(context, "Không lấy được thông tin",
						Toast.LENGTH_SHORT).show();
				break;
			case SHARE_SUCCESS:
				FacebookUtility fbu = new FacebookUtility(getParent(),
						activity.getParent());
				fbu.loginAndPostToWall(mShareMsg, mShareLink);
				break;
			}
		}
	};

	OnClickListener onLikeLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (currPlaylist != null) {
				if (onlinePlaylistIsCached(currPlaylist.serverId)) {
					// truongvv
					// loadMoreProgress.setVisibility(View.VISIBLE);
					// loadMoreProgress.setText(R.string.loading);
					pd = ProgressDialog.show(getParent(), "",
							getString(R.string.loading), true);

					Thread dataInitializationThread = new Thread() {
						public void run() {

							JsonPlaylist jpl = JsonPlaylist
									.unlikePlaylist(currPlaylist.serverId);
							if (jpl.isSuccess())
								mHandler.sendEmptyMessage(2);
							else
								mHandler.sendEmptyMessage(3);
						}
					};
					dataInitializationThread.start();
				} else {
					// loadMoreProgress.setVisibility(View.VISIBLE);
					// loadMoreProgress.setText(R.string.loading);
					pd = ProgressDialog.show(getParent(), "",
							getString(R.string.loading), true);

					Thread dataInitializationThread = new Thread() {
						public void run() {

							JsonPlaylist jpl = JsonPlaylist
									.likePlaylist(currPlaylist.serverId);
							if (jpl.isSuccess())
								mHandler.sendEmptyMessage(0);
							else
								mHandler.sendEmptyMessage(1);
						}
					};
					dataInitializationThread.start();
				}
			}
		}
	};

	OnClickListener onBackBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	private void setHeaderView() {
		// there is no search button in normal playlist
		// numOfSongTxt.setText(Const.SONG_PREFIX + songs.size() +
		// Const.SONG_SUFFIX);
		switch (playlistType) {
		case MyPlaylistActivity.ONLINE_PLAYLIST:

			TextView headerText = (TextView) findViewById(R.id.my_playlist_detail_title_txt);
			headerText.setText("Playlist đề cử");
			titleTxt.setText(currPlaylist.title);
			if (dataStore.getConfig(Const.KEY_FULL_NAME).equals(""))
				creatorTxt.setText(dataStore.getMsisdn());
			else
				creatorTxt.setText(dataStore.getConfig(Const.KEY_FULL_NAME));
			likeLayout.setVisibility(LinearLayout.VISIBLE);
			editAndShareLayout.setVisibility(LinearLayout.GONE);

			if (onlinePlaylistIsCached(currPlaylist.serverId)) {
				likeTxt.setText("Bỏ thích");
			} else
				likeTxt.setText("Thích");
			if (currPlaylist.userThumb != null
					&& !currPlaylist.userThumb.equals(""))
				mImageLoader.DisplayImage(currPlaylist.userThumb, this,
						creatorImg, ImageLoader.TYPE_PLAYLIST);

			break;
		case MyPlaylistActivity.DOWNLOADED_PLAYLIST:

			titleTxt.setText(R.string.my_playlist_downloaded_music);
			if (dataStore.getConfig(Const.KEY_FULL_NAME).equals(""))
				creatorTxt.setText(dataStore.getMsisdn());
			else
				creatorTxt.setText(dataStore.getConfig(Const.KEY_FULL_NAME));
			likeLayout.setVisibility(LinearLayout.GONE);
			shareLayout.setVisibility(LinearLayout.GONE);
			editLayout.setVisibility(LinearLayout.VISIBLE);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 0, 0);
			editLayout.setLayoutParams(lp);
			break;
		case MyPlaylistActivity.LOCAL_PLAYLIST:

			titleTxt.setText(R.string.my_playlist_local_music);
			break;
		case MyPlaylistActivity.FAVOURITE_PLAYLIST:

			titleTxt.setText(R.string.my_playlist_favourite_music);
			if (dataStore.getConfig(Const.KEY_FULL_NAME).equals(""))
				creatorTxt.setText(dataStore.getMsisdn());
			else
				creatorTxt.setText(dataStore.getConfig(Const.KEY_FULL_NAME));
			likeLayout.setVisibility(LinearLayout.GONE);
			editAndShareLayout.setVisibility(LinearLayout.VISIBLE);
			break;
		default:

			if (currPlaylist != null) {

				FileStorage fileStorage = new FileStorage();

				Bitmap myBitmap = BitmapFactory.decodeFile(fileStorage
						.getAvatarCachePath());
				if (myBitmap != null) {

					creatorImg.setImageBitmap(myBitmap);
				} else {
					mImageLoader.DisplayImage(
							dataStore.getConfig(Const.KEY_AVATAR), this,
							creatorImg, R.drawable.ic_user_default);
				}

				titleTxt.setText(currPlaylist.title);
				if (dataStore.getConfig(Const.KEY_FULL_NAME).equals(""))
					creatorTxt.setText(dataStore.getMsisdn());
				else
					creatorTxt
							.setText(dataStore.getConfig(Const.KEY_FULL_NAME));
				if (currPlaylist.userMsisdn.trim().equalsIgnoreCase(
						dataStore.getMsisdn().trim())) {
					likeLayout.setVisibility(LinearLayout.GONE);
					editAndShareLayout.setVisibility(LinearLayout.VISIBLE);
				} else {
					likeLayout.setVisibility(LinearLayout.VISIBLE);
					editAndShareLayout.setVisibility(LinearLayout.GONE);
				}
			} else {
				titleTxt.setText(savedName);
				creatorTxt.setText(savedCreator);
				editAndShareLayout.setVisibility(LinearLayout.GONE);

			}
			break;
		}
	}

	// when long-click on item of listview. This will show context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.my_playlist_detail_listview) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			// Song song = currPlaylist.getSongAt(info.position);
			Song song = songs.get(info.position - 1);
			// show context menu
			ContextMenuEx _contextMenu = new ContextMenuEx(false, false);
			_contextMenu.showOptionalDialog(song, getParent(),
					activity.getParent(), Const.TYPE_PLAYLIST_STACK);
		}
	}

	// when click on thumbnail
	public void thumbnailClickHandler(View v) {
		if (dataStore.isInOfflineMode())
			askGoToOnline();
		else {
			if (NetworkUtility.hasNetworkConnection()) {
				DownloadManager.removeDownloadStatusListener(LOG_TAG);
				DownloadManager.addDownloadStatusListener(LOG_TAG,
						myPlaylistDetailActivity);
				Song song = (Song) v.getTag();
				dataStore.addSong(song);
				if (song.isWaitToDownload()) {
					dataStore.removeSongFromSongDownloadPool(song.id);
					// to show normal thumbnail
					hashSongProgress.remove(song.id);
					// refresh listview
					adapter.notifyDataSetChanged();
				} else {
					if (DownloadManager.isRunning()) {
						if (dataStore.addSongToSongDownloadPool(song.id)) {
							// indicate this song is waiting to download
							hashSongProgress.put(song.id, WAITING_VALUE);
							// refresh listview
							adapter.notifyDataSetChanged();
						}
					} else {
						// to show connecting message
						hashSongProgress.put(song.id, CONNECTING_VALUE);
						// for simple i should clear all download pool before
						// start
						dataStore.clearSongDownloadPool();
						dataStore.clearPlaylistDownloadPool();
						// add song to song download pool
						dataStore.addSongToSongDownloadPool(song.id);
						// start download thread
						DownloadManager.startDownload();
						// refresh listview
						adapter.notifyDataSetChanged();
					}
				}
			} else {
				Toast.makeText(getParent(), "Không có kết nối mạng",
						Toast.LENGTH_LONG).show();
			}
		}
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

	public class customComparator implements Comparator<Song> {
		public int compare(Song object1, Song object2) {
			return object1.title.toUpperCase().trim()
					.compareTo(object2.title.toUpperCase().trim());
			// return 0;
		}
	}

	// because of allowing change song's order so i remove this
	private class EfficientAdapter extends BaseAdapter implements
			SectionIndexer {
		// private class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		HashMap<String, Integer> azIndexer;
		String[] sections;

		class ViewHolder {
			TextView firstTxt;
			TextView secondTxt;
			ImageView thumbnailImg;
			ProgressBar downloadBar;
			TextView thirdTxt;
		}

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			refreshData();
		}

		public void refreshData() {
			Song downloading = DownloadManager.getCurrentDownloadSong();
			if (downloading != null) {
				hashSongProgress.put(downloading.id,
						DownloadManager.getCurrentDownloadSongPercent());
			}

			/*
			 * switch (playlistType) { case
			 * MyPlaylistActivity.DOWNLOADED_PLAYLIST: songs =
			 * dataStore.getListDownloadedSongs(); break; case
			 * MyPlaylistActivity.LOCAL_PLAYLIST: songs =
			 * dataStore.loadAllLocalSong(); break; case
			 * MyPlaylistActivity.ONLINE_PLAYLIST: currPlaylist = (Playlist)
			 * Session .getSharedObject(getIntent().getExtras()); if
			 * (currPlaylist == null) songs = new ArrayList<Song>(); else songs
			 * = currPlaylist.getSongList(); break; default: currPlaylist =
			 * dataStore.getPlaylistByID(playlistId); if (currPlaylist == null)
			 * songs = new ArrayList<Song>(); else songs =
			 * currPlaylist.getSongList(); break; }
			 */

			if (songs.size() == 0) {
				noSongLayout.setVisibility(LinearLayout.VISIBLE);
				switch (playlistType) {
				case MyPlaylistActivity.ONLINE_PLAYLIST:
					noSongTxt
							.setText(R.string.my_playlist_detail_online_empty_msg);
					break;
				case MyPlaylistActivity.DOWNLOADED_PLAYLIST:
					noSongTxt
							.setText(R.string.my_playlist_detail_downloaded_empty_msg);
					editAndShareLayout.setVisibility(LinearLayout.GONE);
					break;
				case MyPlaylistActivity.LOCAL_PLAYLIST:
					noSongTxt
							.setText(R.string.my_playlist_detail_local_empty_msg);
					break;
				case MyPlaylistActivity.FAVOURITE_PLAYLIST:
					noSongTxt
							.setText(R.string.my_playlist_detail_favourite_empty_msg);
					editAndShareLayout.setVisibility(LinearLayout.GONE);
					break;
				default:
					if (currPlaylist != null)
						noSongTxt
								.setText(R.string.my_playlist_detail_normal_empty_msg);
					else {
						hideHeaderLayout();
						noSongTxt.setText(R.string.delete_playlist_success_msg);
					}

					likeLayout.setVisibility(LinearLayout.GONE);
					editLayout.setVisibility(LinearLayout.VISIBLE);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					lp.setMargins(0, 0, 0, 0);
					editLayout.setLayoutParams(lp);
					shareLayout.setVisibility(LinearLayout.GONE);
					break;
				}
			} else {
				/*
				 * because of allowing change song's order so i remove this
				 */

				// Collections.sort(songs, new customComparator());
				azIndexer = new HashMap<String, Integer>();
				int size = songs.size();
				// clear clone
				songsClone.clear();
				for (int i = 0; i < size; i++) {
					// copy
					songsClone.add(songs.get(i));
					String element = songs.get(i).title.trim();
					// We store the first letter of the word, and its index.
					azIndexer.put(element.substring(0, 1).toUpperCase(), i);
				}
				Set<String> keys = azIndexer.keySet(); // set of letters

				Iterator<String> it = keys.iterator();
				ArrayList<String> keyList = new ArrayList<String>();

				while (it.hasNext()) {
					String key = it.next();
					keyList.add(key);
				}
				Collections.sort(keyList);// sort the keylist
				sections = new String[keyList.size()]; // simple conversion to
														// array
				for (int i = 0; i < keyList.size(); i++) {
					sections[i] = keyList.get(i);
				}

			}
		}

		@Override
		public int getCount() {
			return songs.size();
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
			final ViewHolder _holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.view_listview_row_myplaylist, null);
				_holder = new ViewHolder();
				_holder.downloadBar = (ProgressBar) convertView
						.findViewById(R.id.my_playlist_row_download_bar);
				_holder.thumbnailImg = (ImageView) convertView
						.findViewById(R.id.my_playlist_row_thumbnail_img);
				_holder.firstTxt = (TextView) convertView
						.findViewById(R.id.my_playlist_row_first_txt);
				_holder.secondTxt = (TextView) convertView
						.findViewById(R.id.my_playlist_row_second_txt);
				_holder.thirdTxt = (TextView) convertView
						.findViewById(R.id.my_playlist_row_third_txt);
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

			Song _currSong = songs.get(position);
			if (_currSong.artist_name == null
					|| _currSong.artist_name.trim().equals("")) {
				_currSong.artist_name = "chưa cập nhật";
			}

			_holder.thumbnailImg.setTag(_currSong);
			_holder.thumbnailImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					thumbnailClickHandler(_holder.thumbnailImg);
				}
			});

			noSongLayout.setVisibility(LinearLayout.GONE);
			_holder.thumbnailImg.setVisibility(ImageView.VISIBLE);
			if (playlistType == MyPlaylistActivity.LOCAL_PLAYLIST) {
				_holder.firstTxt.setText((position + 1) + ". "
						+ _currSong.title);
				_holder.secondTxt.setVisibility(TextView.VISIBLE);
				_holder.secondTxt.setText(_currSong.artist_name);
				_holder.downloadBar.setVisibility(ProgressBar.GONE);
				_holder.thumbnailImg
						.setBackgroundResource(R.drawable.ic_listview_default_music);
				_holder.thumbnailImg.setEnabled(false);
			} else if (playlistType == MyPlaylistActivity.DOWNLOADED_PLAYLIST) {
				_holder.firstTxt.setText((position + 1) + ". "
						+ _currSong.title);
				_holder.secondTxt.setVisibility(TextView.VISIBLE);
				_holder.secondTxt.setText(_currSong.artist_name);
				_holder.thumbnailImg
						.setBackgroundResource(R.drawable.ic_listview_downloaded);
				_holder.thumbnailImg.setEnabled(false);
				_holder.downloadBar.setVisibility(ProgressBar.GONE);
			} else { // favourite and user created playlist
				_holder.downloadBar.setVisibility(ProgressBar.GONE);
				_holder.firstTxt.setText((position + 1) + ". "
						+ _currSong.title);

				if (playlistType == MyPlaylistActivity.ONLINE_PLAYLIST) {
					_holder.secondTxt.setTextColor(Color.parseColor("#333333"));
					_holder.thirdTxt.setVisibility(TextView.VISIBLE);
					_holder.thirdTxt.setText("- " + _currSong.viewCount
							+ " lượt nghe");
				}

				hashSongPosition.put(_currSong.id, position);
				if (_currSong.isAvailableLocally()
						|| dataStore.isAvailableAtLocal(_currSong.id)) {
					// thumbnail with downloaded symbol
					_holder.thumbnailImg
							.setBackgroundResource(R.drawable.ic_listview_downloaded);
					_holder.thumbnailImg.setEnabled(false);
					_holder.secondTxt.setVisibility(TextView.VISIBLE);
					_holder.secondTxt.setText(_currSong.artist_name);
					_holder.downloadBar.setVisibility(ProgressBar.GONE);
				} else {
					// thumbnail can click to download this song
					Integer _status = hashSongProgress.get(_currSong.id);
					// idle status
					if (_status == null) {
						_holder.thumbnailImg
								.setBackgroundResource(R.drawable.ic_listview_not_downloaded);
						_holder.thumbnailImg.setEnabled(true);
						_holder.secondTxt.setVisibility(TextView.VISIBLE);
						_holder.secondTxt.setText(_currSong.artist_name);
						_holder.downloadBar.setVisibility(ProgressBar.GONE);
						// connecting status
					} else if (_status == CONNECTING_VALUE) {
						_holder.thumbnailImg
								.setBackgroundResource(R.drawable.ic_listview_downloading);
						_holder.thumbnailImg.setEnabled(false);
						_holder.secondTxt.setVisibility(TextView.VISIBLE);
						_holder.secondTxt.setText(R.string.connecting);
						// waiting status
					} else if (_status == WAITING_VALUE) {
						_holder.thumbnailImg
								.setBackgroundResource(R.drawable.ic_listview_downloading);
						_holder.thumbnailImg.setEnabled(true);
						_holder.secondTxt.setVisibility(TextView.VISIBLE);
						_holder.secondTxt.setText(_currSong.artist_name + " - "
								+ getString(R.string.download_waiting));
						// downloading status
					} else if (_status > CONNECTING_VALUE
							&& _status < DOWNLOADED_VALUE) {
						_holder.downloadBar.setVisibility(ProgressBar.VISIBLE);
						_holder.downloadBar.setProgress(_status);
						_holder.thumbnailImg
								.setBackgroundResource(R.drawable.ic_listview_downloading);
						_holder.thumbnailImg.setEnabled(false);
						_holder.secondTxt.setVisibility(TextView.VISIBLE);
						_holder.secondTxt.setText("(" + _status + "%) - "
								+ _currSong.artist_name);
					}
				}
			}

			return convertView;
		}

		/*
		 * because of allowing change song's order so i remove this
		 */

		@Override
		public int getPositionForSection(int section) {
			// TODO Auto-generated method stub
			String letter = sections[section];
			return azIndexer.get(letter);
		}

		@Override
		public int getSectionForPosition(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object[] getSections() {
			// TODO Auto-generated method stub
			return sections;
		}

	}

	@Override
	public void onSyncStart() {
	}

	@Override
	public void onSyncDone() {
	}

	public void onPlaylistDownloadStart(Playlist playlist) {
	}

	@Override
	public void onPlaylistDownloadDone(Playlist pl) {
		// NO NEED to implement
	}

	@Override
	public void onPlaylistDownloadProgress(Playlist pl, int totalSong,
			int downloadedSong) {
		// ignore
	}

	public void onSongDownloadStart(Song song) {
		if (playlistType >= MyPlaylistActivity.FAVOURITE_PLAYLIST
				&& !isScrolling) {
			Integer _position = hashSongPosition.get(song.id);
			if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
				hashSongProgress.put(song.id, 0);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onSongDownloadDone(Song sg) {
		if (playlistType >= MyPlaylistActivity.FAVOURITE_PLAYLIST) {
			// to show downloaded thumbnail
			hashSongProgress.remove(sg.id);
			// refresh listview
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onSongDownloadProgress(Song sg, int total, int downloaded) {
		if ((playlistType == MyPlaylistActivity.ONLINE_PLAYLIST || playlistType >= MyPlaylistActivity.FAVOURITE_PLAYLIST)
				&& !isScrolling) {
			Integer _position = hashSongPosition.get(sg.id);
			if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
				int _currStatus = (int) ((downloaded * 100) / total);
				hashSongProgress.put(sg.id, _currStatus);
				adapter.notifyDataSetChanged();
			}
		}
	}

	public void onSongDownloadError(Song sg, int errorCode) {
		if (errorCode == EventNotifier.ERROR_NONE) {
			return;
		}
		if (playlistType >= MyPlaylistActivity.FAVOURITE_PLAYLIST) {
			if (errorCode == EventNotifier.ERROR_POSTPONED) {
				hashSongProgress.put(sg.id, WAITING_VALUE);
			} else {
				hashSongProgress.remove(sg.id);
			}
			// refresh listview
			adapter.notifyDataSetChanged();
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

	public void onDownloadStart(int total) {
	}

	@Override
	public void onDownloadProgress(int totalSong, int downloadedSong) {
	}

	@Override
	public void onDownloadStopped() {
		hashSongProgress.clear();
		// refresh to clean listview
		adapter.notifyDataSetChanged();

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
	protected void onDestroy() {
		super.onDestroy();
		hashSongPosition.clear();
		hashSongProgress.clear();
		songs = null;
		songsClone = null;
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

}
