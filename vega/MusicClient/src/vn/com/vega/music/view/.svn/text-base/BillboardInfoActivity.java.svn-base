package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.common.Session;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.JsonVideo;
import vn.com.vega.music.clientserver.JsonWatchUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.custom.SegmentedRadioGroup;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.holder.FeatureTabHolder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

public class BillboardInfoActivity extends AbstractSongAcitivity implements Const {
	private static final String LOG_TAG = Const.LOG_PREF + BillboardInfoActivity.class.getSimpleName();
	/**
	 * View
	 */
	private SegmentedRadioGroup mTabView;

	private View mBillboardLoadView;
	private View mHelpView;

	private PullToRefreshListView mBillboardList;

	private TextView helpText;
	private TextView dateText;
	private Button btnBillboardBack;
	private ProgressDialog mProgressDialog;

	// Song Adapter
	private SongListAdapter songListAdapter;
	private FeaturedListAdapter albumListAdapter;
	private FeaturedListAdapter videoListAdapter;

	protected int mTabViewIndex = TAB_SONG;
	protected int totalSongFound = 0;
	protected int totalAlbumFound = 0;
	protected int totalVideoFound = 0;

	private DataStore dataStore;
	private BillboardTask billboardTask;
	private FeatureTabHolder mFeatureTabHolder;

	@Override
	protected void onPause() {
		super.onPause();
		if (songListAdapter != null)
			songListAdapter.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (songListAdapter != null) {
			songListAdapter.onResume();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_billboard_detail);
		dataStore = DataStore.getInstance();
		mFeatureTabHolder = (FeatureTabHolder) getParent();

		mBillboardLoadView = findViewById(R.id.view_billboard_load);
		mTabView = (SegmentedRadioGroup) findViewById(R.id.feature_billboard_detail_tabs);

		mBillboardList = (PullToRefreshListView) findViewById(R.id.billboard_listview);
		mHelpView = findViewById(R.id.view_billboard_help);

		helpText = (TextView) findViewById(R.id.search_help_label);
		btnBillboardBack = (Button) findViewById(R.id.btnBillboardBack);

		View emptyView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);

		mBillboardLoadView.setVisibility(View.VISIBLE);

		mBillboardList.addHeaderView(emptyView);
		mBillboardList.addFooterView(emptyView);
		mBillboardList.setOnItemClickListener(onItemClickListener);
		mBillboardList.setOnScrollListener(onListScrollListener);
		btnBillboardBack.setOnClickListener(onBackClickListener);

		mTabView.setOnCheckedChangeListener(onCheckedChangeListener);
		registerForContextMenu(mBillboardList);

		billboardTask = new BillboardTask();
		billboardTask.execute(TYPE_FEATURED_SONGS, 0);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (songListAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songListAdapter.getItem(info.position - mBillboardList.getHeaderViewsCount());
			ContextMenuEx contextMenu = new ContextMenuEx(false, false);
			contextMenu.showOptionalDialog(song, getParent(), this.getParent(), Const.TYPE_FEATURE_STACK);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SWITCH_ONLINE_MODE:
			return new AlertDialog.Builder(getParent()).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.offline_mode)
					.setMessage(R.string.offline_mode_msg).setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dataStore.setOfflineModeStatus(false);
						}
					}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// do nothing
						}
					}).create();
		}
		return null;
	};

	OnScrollListener onListScrollListener = new OnScrollListener() {

		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == 0)
				SongListAdapter.setScrolling(false);
			else
				SongListAdapter.setScrolling(true);
		}
	};
	
	private JsonWatchUrl jsonWatchUrl;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 0:
				mProgressDialog.dismiss();
				Toast.makeText(getParent(), "Đường dẫn bị lỗi",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				mProgressDialog.dismiss();
				try {
					MusicPlayer musicPlayer = MusicPlayer.getInstance();
					if (musicPlayer != null && musicPlayer.isPlaying())
						musicPlayer.pause();

					/**
					 * Tell music service pause if playing
					 */

					Intent i = new Intent(
							"com.android.music.musicservicecommand");
					i.putExtra("command", "pause");
					sendBroadcast(i);

					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse(jsonWatchUrl.streamingUrl),
							"video/*");
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getParent(), "Đường dẫn bị lỗi",
							Toast.LENGTH_SHORT).show();
				}

				break;

			}
		}
	};

	private void openVideo(final Video video) {
		
		try {

			mProgressDialog = new ProgressDialog(getParent());
			mProgressDialog.setMessage(getString(R.string.loading));
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					jsonWatchUrl = JsonWatchUrl.loadWatchUrl(Integer
							.parseInt(video.id));
					if (!jsonWatchUrl.isSuccess()) {
						mHandler.sendEmptyMessage(0);

					} else {
						mHandler.sendEmptyMessage(1);
					}

				}
			}).start();

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getParent(), "Đường dẫn bị lỗi", Toast.LENGTH_SHORT)
					.show();
		}
		
	}

	OnClickListener onBackClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};


	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			if (dataStore.isInOfflineMode()) {
				showDialog(DIALOG_SWITCH_ONLINE_MODE);
			} else {
				Object obj = adapter.getItemAtPosition(position);
				if (obj instanceof Song) {
					NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position - mBillboardList.getHeaderViewsCount(), songListAdapter.getSongList());
					Intent i = new Intent().setClass(getApplicationContext(), NowPlayingActivity.class).setFlags(
							Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

					startActivity(i);
				} else if (obj instanceof Album) {
					Album album = (Album) obj;
					Bundle bundle = new Bundle();
					Session.putSharedObject(bundle, BillboardInfoActivity.this, album);
					bundle.putBoolean(BUNDLE_NOT_IN_GROUP, true);
					bundle.putInt(Const.BUNDLE_STACK_TYPE, Const.TYPE_FEATURE_STACK);

					Intent albumIntent = new Intent(BillboardInfoActivity.this, AlbumInfoActivity.class);
					albumIntent.putExtras(bundle);
					;
					mFeatureTabHolder.startChildActivity(AlbumInfoActivity.class.getName(), albumIntent);

				} else if (obj instanceof Video) {
					openVideo((Video) obj);
				}
			}
		}
	};

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.billboard_tab_song:
				if (songListAdapter != null) {
					if (songListAdapter.getCount() > 0) {
						mHelpView.setVisibility(View.GONE);
					} else {
						mHelpView.setVisibility(View.VISIBLE);
						helpText.setText(R.string.no_song);
					}
					mBillboardList.setAdapter(songListAdapter);
				} else {
					new BillboardTask().execute(TYPE_FEATURED_SONGS, 0);
				}
				break;
			case R.id.billboard_tab_album:
				if (albumListAdapter != null) {
					if (albumListAdapter.getCount() > 0) {
						mHelpView.setVisibility(View.GONE);
					} else {
						mHelpView.setVisibility(View.VISIBLE);
						helpText.setText(R.string.no_album);
					}
					mBillboardList.setAdapter(albumListAdapter);
				} else {
					new BillboardTask().execute(TYPE_FEATURED_ALBUMS, 0);
				}
				break;
			case R.id.billboard_tab_video:
				if (videoListAdapter != null) {
					if (videoListAdapter.getCount() > 0) {
						mHelpView.setVisibility(View.GONE);
					} else {
						mHelpView.setVisibility(View.VISIBLE);
						helpText.setText(R.string.no_video);
					}
					mBillboardList.setAdapter(videoListAdapter);
				} else {
					new BillboardTask().execute(TYPE_FEATURED_VIDEOS, 0);
				}

				break;
			default:
				break;
			}

		}
	};

	public void onThumbnailClickListener(View view) {
		// TODO: Handle thumbnail click event
		Song song = (Song) view.getTag();
		songListAdapter.download(song);
	}

	// //////////////////////////////////////////////////////////////////////////
	// AsyncTask to load artist's object
	// //////////////////////////////////////////////////////////////////////////
	private class BillboardTask extends AsyncTask<Integer, Integer, List<Object>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mBillboardLoadView.setVisibility(View.VISIBLE);
			mBillboardList.setVisibility(View.GONE);
			mHelpView.setVisibility(View.GONE);
		}

		@Override
		protected List<Object> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			if (params[0] == TYPE_FEATURED_SONGS) {
				List<Object> songs = new ArrayList<Object>();
				JsonSong jos = JsonSong.loadBillboardSong();
				if (jos.isSuccess()) {
					songs.addAll(jos.songs);
					totalSongFound = jos.totalFound;
				} else {
					Log.e(LOG_TAG, jos.getErrorMessage());
				}
				return songs;
			} else if (params[0] == TYPE_FEATURED_ALBUMS) {
				List<Object> albums = new ArrayList<Object>();
				JsonAlbum jal = JsonAlbum.loadBillboardAlbum();
				if (jal.isSuccess()) {
					albums.addAll(jal.albums);
					totalAlbumFound = jal.totalFound;
				} else {
					Log.e(LOG_TAG, jal.getErrorMessage());
				}
				return albums;
			} else if (params[0] == TYPE_FEATURED_VIDEOS) {
				List<Object> videos = new ArrayList<Object>();
				JsonVideo jav = JsonVideo.loadBillboardVideo();
				if (jav.isSuccess()) {
					videos.addAll(jav.videos);
					totalVideoFound = jav.totalFound;
				} else {
					Log.e(LOG_TAG, jav.getErrorMessage());
				}
				return videos;
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mBillboardLoadView.setVisibility(View.GONE);
			mBillboardList.setVisibility(View.VISIBLE);
			if (result != null) {
				setListAdapter(result);
				
				Time now = new Time();
				now.setToNow();
				String updated = getString(R.string.pull_to_refresh_last_updated) + ": " + now.format("%H:%M %d-%m-%Y");
				mBillboardList.onRefreshComplete(updated);
			}
		}
	}

	private void setListAdapter(List<Object> objs) {

		// need implement again
		int type = TYPE_FEATURED_NONE;

		switch (mTabView.getCheckedRadioButtonId()) {
		case R.id.billboard_tab_song:
			// if (objs.size() > 0) {
			// mHelpView.setVisibility(View.GONE);
			// } else {
			// helpText.setText(R.string.no_song);
			// mHelpView.setVisibility(View.VISIBLE);
			// }
			type = TYPE_FEATURED_SONGS;
			if (songListAdapter == null) {
				songListAdapter = new SongListAdapter(BillboardInfoActivity.this, objs, false, BillboardInfoActivity.class.getName());
				mBillboardList.setAdapter(songListAdapter);
			} else {
				songListAdapter.notifyListObjectChanged(objs);
			}
			break;
		case R.id.billboard_tab_album:
			// if (objs.size() > 0) {
			// mHelpView.setVisibility(View.GONE);
			// } else {
			// helpText.setText(R.string.no_album);
			// mHelpView.setVisibility(View.VISIBLE);
			// }
			type = TYPE_FEATURED_ALBUMS;
			if (albumListAdapter == null) {
				albumListAdapter = new FeaturedListAdapter(BillboardInfoActivity.this, objs, false);
				mBillboardList.setAdapter(albumListAdapter);
			} else {
				albumListAdapter.notifyListObjectChanged(objs);
			}
			break;
		case R.id.billboard_tab_video:
			// if (objs.size() > 0) {
			// mHelpView.setVisibility(View.GONE);
			// } else {
			// helpText.setText(R.string.no_video);
			// mHelpView.setVisibility(View.VISIBLE);
			// }
			type = TYPE_FEATURED_VIDEOS;
			if (videoListAdapter == null) {
				videoListAdapter = new FeaturedListAdapter(BillboardInfoActivity.this, objs, false);
				mBillboardList.setAdapter(videoListAdapter);
			} else {
				videoListAdapter.notifyListObjectChanged(objs);
			}
			break;
		default:
			break;
		}
		
		if (type != TYPE_FEATURED_NONE) {
			final int param = type;
			mBillboardList.setOnRefreshListener(new OnRefreshListener() {			
				@Override
				public void onRefresh() {
					new BillboardTask().execute(param, 0);				
				}
			});
		}

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			return true;
		}
		return true;
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}

}
