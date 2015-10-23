package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.common.Session;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonNews;
import vn.com.vega.music.clientserver.JsonPlaylist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.JsonVideo;
import vn.com.vega.music.clientserver.JsonWatchUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.NewsEntry;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturePlaylistAdapter;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.adapter.base.AutoLoadingAdapter;
import vn.com.vega.music.view.adapter.base.AutoLoadingListener;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.holder.FeatureTabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

public class FeatureHomeActivity extends AbstractSongAcitivity implements Const, AutoLoadingListener {
	private static final String LOG_TAG = Const.LOG_PREF + FeatureHomeActivity.class.getSimpleName();
	private static final int[] icons = new int[] { R.drawable.ic_listview_music, R.drawable.ic_listview_video, R.drawable.ic_listview_album,
			R.drawable.ic_listview_playlist, R.drawable.ic_listview_news, R.drawable.ic_listview_billboard };

	private static final int[] titles = new int[] { R.string.feature_song_title, R.string.feature_video_title, R.string.feature_album_title,
			R.string.feature_playlist_title, /*R.string.feature_news_title, */R.string.feature_billboard_title };

	/* View */
	private TextView mTitleBar, mButtonTitle;
	private Button mLoadAgain;

	private ListView homeListView;
	private PullToRefreshListView featureListView;

	private View mLoadingView;
	private ViewFlipper mContentFlipper;
	private WebView webView;

	private Animation pushLeftIn, pushLeftOut, pushRightIn, pushRightOut;

	private int featureType = TYPE_FEATURED_NONE;
	private boolean scrollable = true;
	private int totalFound = 0;
	private boolean reloadList = false, offlineMode = false, networkConnected = false;

	private DataStore dataStore;
	private SongListAdapter songListAdapter;
	private FeaturedListAdapter videoAdapter, newsAdapter, albumAdapter;

	private FeaturePlaylistAdapter playlistAdapter;
	private FeatureTabHolder musicDiscoveryStack;

	private Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_feature);
		dataStore = DataStore.getInstance();

		musicDiscoveryStack = (FeatureTabHolder) getParent();

		View headerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		mContentFlipper = (ViewFlipper) findViewById(R.id.feature_flipper);
		mTitleBar = (TextView) findViewById(R.id.feature_title_txt);

		homeListView = (ListView) findViewById(R.id.feature_home_listview);
		homeListView.addHeaderView(headerView);
		homeListView.addFooterView(footerView);

		mLoadingView = findViewById(R.id.view_feature_loading);

		mLoadingView.setVisibility(View.GONE);

		mLoadAgain = (Button) findViewById(R.id.feature_help_btn);
		mLoadAgain.setVisibility(View.GONE);
		mLoadAgain.setOnClickListener(onLoadAgain);

		mButtonTitle = (TextView) findViewById(R.id.featureBtnTitle);
		mButtonTitle.setVisibility(View.GONE);

		backBtn = (Button) findViewById(R.id.layout_activity_feature_back_btn);
		backBtn.setVisibility(Button.GONE);
		backBtn.setOnClickListener(onBackBtnClick);

		featureListView = (PullToRefreshListView) findViewById(R.id.feature_list);
		// featureListView.setOnRefreshListener(this);
		featureListView.addHeaderView(headerView);
		featureListView.addFooterView(footerView);

		webView = (WebView) findViewById(R.id.webview);

		// Animation
		pushLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		pushLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		pushRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in);
		pushRightOut = AnimationUtils.loadAnimation(this, R.anim.push_right_out);

		homeListView.setAdapter(new FeatureHomeAdapter());
		homeListView.setOnItemClickListener(onFeatureHomeItemClick);

		featureListView.setOnItemClickListener(onFeatureListItemClick);
		featureListView.setOnScrollListener(onFeatureScrollListener);		

		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new VegaWebViewClient());

		registerForContextMenu(featureListView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (songListAdapter != null) {
			songListAdapter.onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (songListAdapter != null) {
			songListAdapter.onPause();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mContentFlipper.getDisplayedChild() > 0)
				showPrevious();
			else
				getParent().finish();
			return true;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (featureType == R.drawable.ic_listview_music && songListAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songListAdapter.getItem(info.position - 1);
			ContextMenuEx contextMenu = new ContextMenuEx();
			contextMenu.showOptionalDialog(song, getParent(), ContextMenuEx.TYPE_NORMAL);
		}
	}

	public void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
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

	OnClickListener onBackBtnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// mHelpView.setVisibility(View.GONE);
			showPrevious();
		}
	};

	OnClickListener onLoadAgain = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new FeatureLoadTask().execute(0);
		}
	};

	OnScrollListener onFeatureScrollListener = new OnScrollListener() {
		boolean endListReacher = false;

		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			endListReacher = (firstVisibleItem + visibleItemCount == totalItemCount) ? true : false;
			SongListAdapter.setVisibleItems(firstVisibleItem, firstVisibleItem + visibleItemCount);
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == 0)
				SongListAdapter.setScrolling(false);
			else
				SongListAdapter.setScrolling(true);
			if (scrollState == 0 && endListReacher && scrollable && networkConnected && featureListView.getAdapter().getCount() < LIST_LIMITED - 20
					&& !dataStore.isInOfflineMode()) {
				new FeatureLoadTask().execute(0);
			}
		}
	};

	OnItemClickListener onFeatureHomeItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			offlineMode = dataStore.isInOfflineMode();
			networkConnected = NetworkUtility.getNetworkStatus() == NetworkUtility.CONNECTION_TYPE_OFF ? false : true;

			if (offlineMode) {
				askGoToOnline();

			} else {
				if (networkConnected) {

					featureType = icons[position - 1];
					if (featureType != R.drawable.ic_listview_billboard) {
						mTitleBar.setText(titles[position - 1]);

					}

					featureListView.setVisibility(View.GONE);

					switch (featureType) {

					case R.drawable.ic_listview_music:
						showNext();
						if (songListAdapter != null && songListAdapter.getCount() > 0) {
							setAdapter(songListAdapter);

						} else if (songListAdapter != null && songListAdapter.getCount() == 0) {
						} else {
							setAdapter(null);
							new FeatureLoadTask().execute(0);
						}
						break;
					case R.drawable.ic_listview_video:
						showNext();
						if (videoAdapter != null && videoAdapter.getCount() > 0) {
							setAdapter(videoAdapter);
						} else if (videoAdapter != null && videoAdapter.getCount() == 0) {
							// showPrevious();
						} else {
							setAdapter(null);
							new FeatureLoadTask().execute(0);
						}
						break;
					case R.drawable.ic_listview_album:
						showNext();
						if (albumAdapter != null && albumAdapter.getCount() > 0) {
							setAdapter(albumAdapter);
						} else if (albumAdapter != null && albumAdapter.getCount() == 0) {
							showPrevious();
						} else {
							setAdapter(null);
							new FeatureLoadTask().execute(0);
						}
						break;
					case R.drawable.ic_listview_playlist:
						showNext();
						if (playlistAdapter != null && playlistAdapter.getCount() > 0) {
							setAdapter(playlistAdapter);
						} else if (playlistAdapter != null && playlistAdapter.getCount() == 0) {
							// showPrevious();
						} else {
							setAdapter(null);
							new FeatureLoadTask().execute(0);
						}
						break;
					case R.drawable.ic_listview_news:
						showNext();
						if (newsAdapter != null && newsAdapter.getCount() > 0) {
							setAdapter(newsAdapter);
						} else if (newsAdapter != null && newsAdapter.getCount() == 0) {
							// showPrevious();
						} else {
							setAdapter(null);
							new FeatureLoadTask().execute(0);
						}
						break;
					case R.drawable.ic_listview_billboard:
						Intent billboardIntent = new Intent(getParent(), BillboardInfoActivity.class);
						musicDiscoveryStack.startChildActivity(BillboardInfoActivity.class.getName(), billboardIntent);
					}
				} else {
					Toast.makeText(getParent(), "Không có kết nối mạng", Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	OnItemClickListener onFeatureListItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			offlineMode = dataStore.isInOfflineMode();
			if (offlineMode) {
				askGoToOnline();
			} else {
				if (NetworkUtility.hasNetworkConnection()) {
					Object obj = adapter.getItemAtPosition(position);
					scrollable = true;

					Bundle b;
					if (obj instanceof Artist) {
						// TODO: Do something with Artist
						Artist artist = (Artist) obj;
						b = new Bundle();
						Session.putSharedObject(b, FeatureHomeActivity.this, artist);
						musicDiscoveryStack.startChildActivity(ArtistInfoActivity.class.getName(),
								new Intent(getParent(), ArtistInfoActivity.class).putExtras(b));

					} else if (obj instanceof Video) {
						// TODO: Do something with Video
						openVideo((Video) obj);

					} else if (obj instanceof Album) {
						// TODO: Do something with Album
						Album album = (Album) obj;
						b = new Bundle();
						Session.putSharedObject(b, FeatureHomeActivity.this, album);
						musicDiscoveryStack.startChildActivity(AlbumInfoActivity.class.getName(),
								new Intent(getParent(), AlbumInfoActivity.class).putExtras(b));

					} else if (obj instanceof Playlist) {
						// TODO: Do something with Playlist
						Playlist playlist = (Playlist) obj;
						b = new Bundle();
						Session.putSharedObject(b, FeatureHomeActivity.this, playlist);
						b.putInt(Const.BUNDLE_PLAYLIST_TYPE, MyPlaylistActivity.ONLINE_PLAYLIST);
						musicDiscoveryStack.startChildActivity(MyPlaylistDetailActivity.class.getName(), new Intent(getParent(),
								MyPlaylistDetailActivity.class).putExtras(b));

					} else if (obj instanceof NewsEntry) {
						// TODO: Do something with NewsEntry
						showNext();
						openUrl((NewsEntry) obj);

					} else if (obj instanceof Song) {
						// TODO: Open music player
						NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position - featureListView.getHeaderViewsCount(),
								songListAdapter.getSongList());
						startActivity(new Intent().setClass(getApplicationContext(), NowPlayingActivity.class));
					}
				} else {
					Toast.makeText(getParent(), getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show();
				}

			}
		}

	};

	@Override
	public void onThumbnailClickListener(View view) {
		Song song = (Song) view.getTag();
		songListAdapter.download(song);
	}

	private void openVideo(Video video) {
		// call clientserver module to get streaming url
		JsonWatchUrl jsonWatchUrl = JsonWatchUrl.loadWatchUrl(Integer.parseInt(video.id));
		if (!jsonWatchUrl.isSuccess()) {
			// do stuffs
			Toast.makeText(getParent(), "Đường dẫn bị lỗi", Toast.LENGTH_SHORT).show();
		} else {
			/**
			 * Tell music service pause if playing
			 */
			Intent i = new Intent("com.android.music.musicservicecommand");
			i.putExtra("command", "pause");
			sendBroadcast(i);

			Log.d(LOG_TAG, "play video: " + jsonWatchUrl.streamingUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse(jsonWatchUrl.streamingUrl), "video/*");
			startActivity(intent);
		}
	}

	private void setAdapter(BaseAdapter adapter) {
		if (adapter != null) {
			featureListView.setVisibility(View.VISIBLE);
			// featureListView.setAdapter(adapter);
			featureListView.setAdapter(new AutoLoadingAdapter(adapter, this));
			if (featureListView.getOnRefreshListener() == null) {
				featureListView.setOnRefreshListener(new OnRefreshListener() {			
					@Override
					public void onRefresh() {
						reloadList = true;
						new FeatureLoadTask().execute(0);				
					}
				});
			}
		}
	}

	private void setListAdapter(final List<Object> objs) {
		switch (featureType) {

		case R.drawable.ic_listview_music:
			if (songListAdapter == null) {
				songListAdapter = new SongListAdapter(FeatureHomeActivity.this, objs, false);
				setAdapter(songListAdapter);
			} else {
				songListAdapter.notifyListObjectChanged(objs);
				if (totalFound == songListAdapter.getCount()) {
					scrollable = false;
				}
			}

			break;
		case R.drawable.ic_listview_video:
			if (videoAdapter == null) {
				videoAdapter = new FeaturedListAdapter(FeatureHomeActivity.this, objs, false);
				setAdapter(videoAdapter);
			} else {
				videoAdapter.notifyListObjectChanged(objs);
				if (videoAdapter.getCount() == totalFound) {
					scrollable = false;
				}
			}

			break;
		case R.drawable.ic_listview_album:
			if (albumAdapter == null) {
				albumAdapter = new FeaturedListAdapter(FeatureHomeActivity.this, objs, false);
				setAdapter(albumAdapter);
			} else {
				albumAdapter.notifyListObjectChanged(objs);
				if (albumAdapter.getCount() == totalFound) {
					scrollable = false;
				}
			}

			break;
		case R.drawable.ic_listview_playlist:
			if (playlistAdapter == null) {
				playlistAdapter = new FeaturePlaylistAdapter(FeatureHomeActivity.this, objs, false);
				setAdapter(playlistAdapter);
			} else {
				playlistAdapter.notifyListObjectChanged(objs);
				if (playlistAdapter.getCount() == totalFound) {
					scrollable = false;
				}
			}

			break;
		case R.drawable.ic_listview_news:
			if (newsAdapter == null) {
				newsAdapter = new FeaturedListAdapter(FeatureHomeActivity.this, objs, false);
				setAdapter(newsAdapter);
			} else {
				newsAdapter.notifyListObjectChanged(objs);
				if (newsAdapter.getCount() == totalFound) {
					scrollable = false;
				}
			}

			break;
		default:
			break;
		}
	}

	private void openUrl(NewsEntry entry) {
		Log.e(LOG_TAG, entry.wapUrl);
		webView.loadUrl(entry.wapUrl);
	}

	private void showNext() {
		mContentFlipper.setInAnimation(pushLeftIn);
		mContentFlipper.setOutAnimation(pushLeftOut);
		mContentFlipper.setFlipInterval(8000);
		mContentFlipper.showNext();

		backBtn.setVisibility(Button.VISIBLE);
		mButtonTitle.setVisibility(View.VISIBLE);
	}

	private void showPrevious() {
		mContentFlipper.setInAnimation(pushRightIn);
		mContentFlipper.setOutAnimation(pushRightOut);
		mContentFlipper.setFlipInterval(8000);
		mContentFlipper.showPrevious();

		if (mContentFlipper.getDisplayedChild() == 0) {
			mTitleBar.setText(R.string.feature_title_txt);
			mButtonTitle.setVisibility(View.INVISIBLE);
		}

		backBtn.setVisibility(Button.GONE);
	}

	// -------------------------------------
	// Adapter for feature home list
	// -------------------------------------

	class FeatureHomeAdapter extends BaseAdapter {

		public FeatureHomeAdapter() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.view_listview_row_feature_home, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView.findViewById(R.id.feature_home_item_icon);
				holder.title = (TextView) convertView.findViewById(R.id.feature_home_item_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(titles[position]);
			holder.icon.setBackgroundResource(icons[position]);

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView title;
		}
	}

	// -------------------------------------
	// AsyncTask to load feature object
	// -------------------------------------
	class FeatureLoadTask extends AsyncTask<Integer, Integer, List<Object>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!reloadList && mLoadingView.getVisibility() != View.VISIBLE) {
				mLoadingView.setVisibility(View.VISIBLE);
			}
			// mHelpView.setVisibility(View.GONE);
		}

		@Override
		protected List<Object> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			List<Object> objs = new ArrayList<Object>();
			if (!NetworkUtility.hasNetworkConnection())
				return objs;
			try {
				switch (featureType) {
				case R.drawable.ic_listview_music: {
					if (songListAdapter == null || reloadList) {
						JsonSong js = JsonSong.loadTopSongList(params[0]);
						if (js.isSuccess()) {
							objs.addAll(js.songs);
							totalFound = js.totalFound;
						}
					} else {
						JsonSong js = JsonSong.loadTopSongList(songListAdapter.getCount());
						if (js.isSuccess()) {
							objs.addAll(js.songs);
							totalFound = js.totalFound;
						}
					}
					break;
				}

				case R.drawable.ic_listview_video: {
					if (videoAdapter == null || reloadList) {
						JsonVideo jav = JsonVideo.loadTopVideoList(params[0]);
						if (jav.isSuccess()) {
							objs.addAll(jav.videos);
							totalFound = jav.totalFound;
						}
					} else {
						JsonVideo jav = JsonVideo.loadTopVideoList(videoAdapter.getCount());
						if (jav.isSuccess()) {
							objs.addAll(jav.videos);
							totalFound = jav.totalFound;
						}
					}
					break;
				}

				case R.drawable.ic_listview_album:
					if (albumAdapter == null || reloadList) {
						JsonAlbum jal = JsonAlbum.loadNewAlbummList(params[0]);
						if (jal.isSuccess()) {
							objs.addAll(jal.albums);
							totalFound = jal.totalFound;
						}
					} else {
						JsonAlbum jal = JsonAlbum.loadNewAlbummList(albumAdapter.getCount());
						if (jal.isSuccess()) {
							objs.addAll(jal.albums);
							totalFound = jal.totalFound;
						}
					}
					break;
				case R.drawable.ic_listview_playlist:
					if (playlistAdapter == null || reloadList) {
						JsonPlaylist jap = JsonPlaylist.loadTopPlaylistList(params[0]);
						if (jap.isSuccess()) {
							objs.addAll(jap.playlists);
							totalFound = jap.totalFound;
						}
					} else {
						JsonPlaylist jap = JsonPlaylist.loadTopPlaylistList(playlistAdapter.getCount());
						if (jap.isSuccess()) {
							objs.addAll(jap.playlists);
							totalFound = jap.totalFound;
						}
					}
					break;
				case R.drawable.ic_listview_news:
					if (newsAdapter == null || reloadList) {
						JsonNews jsn = JsonNews.loadNewsList(params[0]);
						if (jsn.isSuccess()) {
							objs.addAll(jsn.newsEntries);
							totalFound = jsn.totalFound;
						}
					} else {
						JsonNews jsn = JsonNews.loadNewsList(newsAdapter.getCount());
						if (jsn.isSuccess()) {
							objs.addAll(jsn.newsEntries);
						}
					}
					break;
				}
				return objs;
			} catch (Exception e) {
				// TODO: handle exception
				return objs;
			}
		}

		@Override
		protected void onPostExecute(List<Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mLoadingView.getVisibility() != View.INVISIBLE) {
				mLoadingView.setVisibility(View.INVISIBLE);
			}

//			if (result == null || result.size() == 0) {
//				// mHelpView.setVisibility(View.VISIBLE);
//				mLoadAgain.setVisibility(View.VISIBLE);
//			}

			setListAdapter(result);
			
			Time now = new Time();
			now.setToNow();
			String updated = getString(R.string.pull_to_refresh_last_updated) + ": " + now.format("%H:%M %d-%m-%Y");
			featureListView.onRefreshComplete(updated);
		}
	}

	private class VegaWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return FeatureHomeActivity.this;
	}

	@Override
	public List<Object> getMoreData() {
		// TODO Auto-generated method stub
		return new FeatureLoadTask().doInBackground(0);
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}

}
