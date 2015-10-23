package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonArtist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.holder.FeatureTabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AlbumInfoActivity extends AbstractSongAcitivity implements Const {
	private static final String LOG_TAG = Const.LOG_PREF + AlbumInfoActivity.class.getSimpleName();

	private ImageView albumImage;
	private TextView albumTitle;
	private TextView albumInfo;
	private TextView dockina;
	private ListView albumListView;
	private Button listenBtn, downloadBtn;
	private RelativeLayout emptyLayout;
	private Button backBtn;

	private LinearLayout artistInfoLayout;

	private Context mContext;

	private ProgressDialog mProgressDialog;

	private SongListAdapter songAdapter;

	private ImageLoader imageLoader;
	private Album album;

	private boolean notInGroup = false;
	private DataStore dataStore;

	private boolean reload = false;
	private boolean offlineMode = false;

	protected static final String DOWNLOAD_START = "Tải về";
	protected static final String DOWNLOAD_CANCEL = "Hủy tải";
	protected static final int GO_TO_PLAYER = 9;

	private FeatureTabHolder musicDiscoveryStack;

	@Override
	protected void onResume() {
		super.onResume();
		if (songAdapter != null) {
			songAdapter.onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (songAdapter != null) {
			songAdapter.onPause();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_album_detail);

		musicDiscoveryStack = (FeatureTabHolder) getParent();

		imageLoader = ImageLoader.getInstance(this);
		dataStore = DataStore.getInstance();

		mContext = this;

		Bundle extra = getIntent().getExtras();

		album = (Album) Session.getSharedObject(extra);
		notInGroup = getIntent().getExtras().getBoolean(BUNDLE_NOT_IN_GROUP);

		albumListView = (ListView) findViewById(R.id.feature_detail_listview);
		// View headerView =
		// ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_feature_album_header,
		// null, false);
		// albumListView.addHeaderView(headerView);

		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		albumListView.addHeaderView(footerView);
		albumListView.addFooterView(footerView);

		emptyLayout = (RelativeLayout) findViewById(R.id.empty_layout);
		// headerTitle = (TextView) findViewById(R.id.album_title_txt);
		albumImage = (ImageView) findViewById(R.id.feature_album_detail_icon);
		albumTitle = (TextView) findViewById(R.id.feature_album_detail_title);
		albumInfo = (TextView) findViewById(R.id.feature_album_detail_info);
		listenBtn = (Button) findViewById(R.id.feature_album_listen_btn);
		artistInfoLayout = (LinearLayout) findViewById(R.id.artist_info_btn);
		downloadBtn = (Button) findViewById(R.id.feature_album_download_btn);
		dockina = (TextView) findViewById(R.id.feature_album_dockina_title);
		backBtn = (Button) findViewById(R.id.album_detail_back_btn);
		albumImage.setTag(album.coverUrl);
		imageLoader.DisplayImage(album.coverUrl, this, albumImage, ImageLoader.TYPE_ALBUM);
		albumTitle.setText(album.title);
		albumInfo.setText("bởi " + album.artistName);
		listenBtn.setOnClickListener(onListenAlbumListener);
		downloadBtn.setText(DOWNLOAD_START);
		downloadBtn.setOnClickListener(onDownloadListener);
		albumListView.setOnScrollListener(onListScrollListener);
		albumListView.setOnItemClickListener(onItemClickListener);
		artistInfoLayout.setOnClickListener(onArtistInfoListener);
		backBtn.setOnClickListener(onBackListener);
		registerForContextMenu(albumListView);

		new AlbumTask().execute(0);
	}

	OnClickListener onBackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (songAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songAdapter.getItem(info.position - 1);
			ContextMenuEx contextMenu = new ContextMenuEx();

			contextMenu.showOptionalDialog(song, getParent(), ContextMenuEx.TYPE_NORMAL);

			// if (notInGroup)
			// contextMenu.showOptionalDialog(song, AlbumInfoActivity.this,
			// ContextMenuEx.TYPE_NORMAL);
			// else
			// contextMenu.showOptionalDialog(song, getParent(),
			// ContextMenuEx.TYPE_NORMAL);

		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SWITCH_ONLINE_MODE:
			return new AlertDialog.Builder(getParent()).setMessage(R.string.offline_mode_msg).setCancelable(false)
					.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dataStore.setOfflineModeStatus(false);
						}
					}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					}).create();

		default:
			break;
		}

		return null;
	};

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			if (adapter.getItemAtPosition(position) instanceof Song) {
				NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position - albumListView.getHeaderViewsCount(), songAdapter.getSongList());
				startActivity(new Intent().setClass(getApplicationContext(), NowPlayingActivity.class));
			}
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			switch (msg.what) {
			case Const.INITIALIZE_FAILURE:

				break;
			case Const.INITIALIZE_SUCCESS:
				Bundle b = new Bundle();
				Session.putSharedObject(b, AlbumInfoActivity.this, mArtist);

				b.putBoolean(ArtistInfoActivity.BUNDLE_NOT_IN_GROUP, false);

				Intent artistInfoIntent = new Intent(getParent(), ArtistInfoActivity.class);
				artistInfoIntent.putExtras(b);
				musicDiscoveryStack.startChildActivity(ArtistInfoActivity.class.getName(), artistInfoIntent);

				// if (notInGroup) {
				// b.putBoolean(ArtistInfoActivity.BUNDLE_NOT_IN_GROUP, true);
				// mContext.startActivity(new Intent(mContext,
				// ArtistInfoActivity.class).putExtras(b));
				// } else {
				// b.putBoolean(ArtistInfoActivity.BUNDLE_NOT_IN_GROUP, false);
				// getParent().startActivity(new Intent(getParent(),
				// ArtistInfoActivity.class).putExtras(b));
				// }

				break;
			case GO_TO_PLAYER:
				Intent i = new Intent();
				i.setClass(getApplicationContext(), NowPlayingActivity.class);
				startActivity(i);
				break;
			default:
				break;
			}

		}
	};

	OnClickListener onListenAlbumListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (notInGroup)
				mProgressDialog = new ProgressDialog(AlbumInfoActivity.this);
			else
				mProgressDialog = new ProgressDialog(AlbumInfoActivity.this.getParent());

			mProgressDialog.setMessage(getString(R.string.loading));
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					ArrayList<Song> objs = songAdapter.getSongList();
					NowPlayingList.init(NowPlayingList.TYPE_NORMAL, 0, objs);

					mHandler.sendEmptyMessage(GO_TO_PLAYER);
				}
			}).start();
		}
	};

	public void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder;
		if (notInGroup)
			builder = new AlertDialog.Builder(mContext);
		else
			builder = new AlertDialog.Builder(getParent());
		builder.setMessage(R.string.offline_mode_msg).setCancelable(false)
				.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dataStore.setOfflineModeStatus(false);
					}
				}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// old code
	private Artist mArtist;

	public void goArtistInfo(View view) {

	}

	OnClickListener onArtistInfoListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (album == null)
				return;

			if (dataStore.isInOfflineMode()) {
				askGoToOnline();
			} else {
				if (NetworkUtility.hasNetworkConnection()) {
					// show progress dialog while load data from database
					if (notInGroup)
						mProgressDialog = ProgressDialog.show(mContext, "", mContext.getString(R.string.loading), true);
					else
						mProgressDialog = ProgressDialog.show(getParent(), "", getParent().getString(R.string.loading), true);
					Thread dataInitializationThread = new Thread() {
						public void run() {
							JsonArtist jar = JsonArtist.loadArtistInfo(album.artistId);
							if (jar.isSuccess() && jar.artists != null && jar.artists.size() > 0) {
								mArtist = jar.artists.get(0);
								mHandler.sendEmptyMessage(Const.INITIALIZE_SUCCESS);
							} else
								mHandler.sendEmptyMessage(Const.INITIALIZE_FAILURE);
						}
					};
					dataInitializationThread.start();
				} else {
					if (notInGroup)
						Toast.makeText(mContext, mContext.getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getParent(), getParent().getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

	OnClickListener onDownloadListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			offlineMode = dataStore.isInOfflineMode();
			if (!offlineMode) {
				if (downloadBtn.getText().toString().equalsIgnoreCase(DOWNLOAD_START)) {
					downloadBtn.setText(DOWNLOAD_CANCEL);
					songAdapter.downloadAll();
				} else {
					downloadBtn.setText(DOWNLOAD_START);
					songAdapter.cancelDownload();
				}

			} else {
				showDialog(DIALOG_SWITCH_ONLINE_MODE);
			}

			// Hello, please use this function to remove all song in this list
			// from download pool
			// songAdapter.removeAllAddedSongFromDownloadPool();
		}
	};

	OnScrollListener onListScrollListener = new OnScrollListener() {
		boolean endListReacher = false;

		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			endListReacher = (firstVisibleItem + visibleItemCount == totalItemCount) ? true : false;
			SongListAdapter.setVisibleItems(firstVisibleItem, firstVisibleItem + visibleItemCount);
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == 0) {
				SongListAdapter.setScrolling(false);
				if (endListReacher)
					endListReacher = false;
			} else
				SongListAdapter.setScrolling(true);
		}
	};

	public void onThumbnailClickListener(View v) {
		Song song = (Song) v.getTag();
		songAdapter.download(song);
	}

	// -------------------------------------
	// AsyncTask to load song list
	// -------------------------------------
	private class AlbumTask extends AsyncTask<Integer, Integer, List<Object>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected List<Object> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			List<Object> objs = new ArrayList<Object>();
			if (NetworkUtility.hasNetworkConnection()) {
				if (songAdapter == null || reload) {
					JsonSong jos = JsonSong.loadSongListByAlbumId(album.id);
					if (jos.isSuccess())
						objs.addAll(jos.songs);
					else
						Log.e(LOG_TAG, jos.getErrorMessage());
				}
			}
			return objs;
		}

		@Override
		protected void onPostExecute(List<Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.size() > 0) {
				dockina.setVisibility(TextView.VISIBLE);
				dockina.setText(String.format(getResources().getString(R.string.dockina_song), result.size()));
				downloadBtn.setVisibility(Button.VISIBLE);
				listenBtn.setVisibility(Button.VISIBLE);
				emptyLayout.setVisibility(RelativeLayout.INVISIBLE);
				songAdapter = new SongListAdapter(AlbumInfoActivity.this, result, false);
				albumListView.setAdapter(songAdapter);
			} else {
				dockina.setVisibility(TextView.INVISIBLE);
				downloadBtn.setVisibility(Button.INVISIBLE);
				listenBtn.setVisibility(Button.INVISIBLE);
				emptyLayout.setVisibility(RelativeLayout.VISIBLE);
			}

		}
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}
}
