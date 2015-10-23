package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonArtist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.JsonVideo;
import vn.com.vega.music.clientserver.JsonWatchUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.adapter.base.AutoLoadingAdapter;
import vn.com.vega.music.view.adapter.base.AutoLoadingListener;
import vn.com.vega.music.view.custom.SegmentedControlButton;
import vn.com.vega.music.view.custom.SegmentedRadioGroup;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.holder.FeatureTabHolder;
import vn.com.vega.music.view.holder.MyPlaylistStack;
import vn.com.vega.music.view.holder.SearchTabHolder;
import android.app.Activity;
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
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ArtistInfoActivity extends AbstractSongAcitivity implements Const, AutoLoadingListener, OnClickListener {
	private static final String LOG_TAG = Const.LOG_PREF + ArtistInfoActivity.class.getSimpleName();
	/**
	 * View
	 */
	private ImageView artistImage;
	private TextView artistName, tvFollow, tvFan;
	private SegmentedRadioGroup tabs;
	private ViewFlipper contentView;
	private ProgressBar progressbar;
	private ListView artistList;
	private View helpLayout;
	private TextView helpText;
	private TextView followText;
	private ProgressDialog mProgressDialog;
	private Button btBack;
	private View btFollow;

	// new for chacha
	private SegmentedControlButton songTab, albumTab, videoTab;

	// ImageLoader
	private ImageLoader imageLoader;

	// Song Adapter
	private SongListAdapter songListAdapter;
	private FeaturedListAdapter albumListAdapter;
	private FeaturedListAdapter videoListAdapter;

	private boolean subcrible = false;

	protected int tabsIndex = TAB_SONG;
	protected int totalSongFound = 0;
	protected int totalAlbumFound = 0;
	protected int totalVideoFound = 0;
	private boolean notInGroup = false;

	private DataStore dataStore;
	
	private int stackType;

	private Artist artist;
	private ArtistTask artistTask;

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
		setContentView(R.layout.layout_activity_artist_detail);
		dataStore = DataStore.getInstance();

		Bundle b = getIntent().getExtras();
		artist = (Artist) Session.getSharedObject(b);
		stackType = b.getInt(Const.BUNDLE_STACK_TYPE);
		notInGroup = b.getBoolean(BUNDLE_NOT_IN_GROUP);
		imageLoader = ImageLoader.getInstance(this);
		artistImage = (ImageView) findViewById(R.id.feature_artist_detail_icon);
		progressbar = (ProgressBar) findViewById(R.id.artist_progressbar);
		contentView = (ViewFlipper) findViewById(R.id.artist_view_flipper);
		artistName = (TextView) findViewById(R.id.feature_artist_detail_title);
		artistList = (ListView) findViewById(R.id.artist_listview);
		
		helpLayout = findViewById(R.id.help_layout);
		helpLayout.setVisibility(View.GONE);
		
		helpText = (TextView) findViewById(R.id.search_help_label);
		tvFollow = (TextView) findViewById(R.id.tvFollow);
		tvFan = (TextView) findViewById(R.id.feature_artist_detail_info);
		btBack = (Button) findViewById(R.id.btArtirstBack);
		btFollow = findViewById(R.id.btFollow);

		tabs = (SegmentedRadioGroup) findViewById(R.id.artist_detail_tabs);
		songTab = (SegmentedControlButton) findViewById(R.id.artist_tab_song);
		albumTab = (SegmentedControlButton) findViewById(R.id.artist_tab_album);
		videoTab = (SegmentedControlButton) findViewById(R.id.artist_tab_video);

		tabs.setOnCheckedChangeListener(onCheckedChangeListener);
		contentView.setVisibility(View.GONE);
		progressbar.setVisibility(View.VISIBLE);
		imageLoader.DisplayImage(artist.imageUrl, this, artistImage, ImageLoader.TYPE_ARTIST);
		artistName.setText(artist.name);

		btBack.setOnClickListener(this);
		btFollow.setOnClickListener(this);

		songTab.setText(String.format(getResources().getString(R.string.tab_song), artist.songCount));
		albumTab.setText(String.format(getString(R.string.tab_album), artist.albumCount));
		videoTab.setText(String.format(getResources().getString(R.string.tab_video), artist.videoCount));
		tvFan.setText(String.format(getString(R.string.format_fan), artist.fanCount));
		artistList.setOnItemClickListener(onItemClickListener);
		if (subcrible) {
			tvFollow.setText(R.string.format_unfollow);
		} else {
			tvFollow.setText(R.string.format_follow);
		}
		registerForContextMenu(artistList);

		artistTask = new ArtistTask();
		artistTask.execute(0);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (songListAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songListAdapter.getItem(info.position);
			ContextMenuEx contextMenu = new ContextMenuEx(false, true);
			if (stackType == Const.TYPE_NOW_PLAYING_STACK)
				contextMenu.showOptionalDialog(song, ArtistInfoActivity.this, this, stackType);
			else
				contextMenu.showOptionalDialog(song, ArtistInfoActivity.this.getParent(), this.getParent(), stackType);
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

	OnClickListener onListenAll = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ArrayList<Song> songs = songListAdapter.getSongList();
			NowPlayingList.init(NowPlayingList.TYPE_NORMAL, 0, songs);

			Intent i = new Intent();
			i.setClass(getApplicationContext(), NowPlayingActivity.class);
			startActivity(i);
		}
	};

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

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
		}
	};

	public void onFollowClick(View view) {
		SpannableString content = new SpannableString("Đăng ký nhận tin");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		followText.setText(content);
		if (stackType == Const.TYPE_NOW_PLAYING_STACK)
			mProgressDialog = new ProgressDialog(this);
		else
			mProgressDialog = new ProgressDialog(this.getParent());
		mProgressDialog.setMessage(getString(R.string.loading));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// follow here
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			Object obj = adapter.getItemAtPosition(position);
			if (obj instanceof Song) {
				NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position - artistList.getHeaderViewsCount(), songListAdapter.getSongList());
				Intent i = new Intent().setClass(getApplicationContext(), NowPlayingActivity.class).setFlags(
						Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			} else if (obj instanceof Album) {
				if (dataStore.isInOfflineMode()) {
					showDialog(DIALOG_SWITCH_ONLINE_MODE);
				} else {
					Album album = (Album) obj;
					Bundle bundle = new Bundle();
					Session.putSharedObject(bundle, ArtistInfoActivity.this, album);
					bundle.putBoolean(BUNDLE_NOT_IN_GROUP, true);
					bundle.putInt(Const.BUNDLE_STACK_TYPE, stackType);
					bundle.putBoolean(Const.BUNDLE_IS_FROM_ARTIST, true);
					switch (stackType) {
					case Const.TYPE_NOW_PLAYING_STACK:
						startActivity(new Intent(ArtistInfoActivity.this, AlbumInfoActivity.class).putExtras(bundle));
						break;
					case Const.TYPE_FEATURE_STACK:
						FeatureTabHolder featureTabHolder = (FeatureTabHolder) getParent();
						featureTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(ArtistInfoActivity.this,
										AlbumInfoActivity.class).putExtras(bundle));
						break;
					case Const.TYPE_SEARCH_STACK:
						SearchTabHolder searchTabHolder = (SearchTabHolder) getParent();
						searchTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(ArtistInfoActivity.this,
										AlbumInfoActivity.class).putExtras(bundle));
						break;

					case Const.TYPE_PLAYLIST_STACK:
						MyPlaylistStack playlistTabHolder = (MyPlaylistStack) getParent();
						playlistTabHolder.startChildActivity(
								AlbumInfoActivity.class.getName()
										+ "_context_menu", new Intent(ArtistInfoActivity.this,
										AlbumInfoActivity.class).putExtras(bundle));
						break;

					default:
						break;
					}
					//startActivity(new Intent(ArtistInfoActivity.this, AlbumInfoActivity.class).putExtras(bundle));
				}
			} else if (obj instanceof Video) {
				if (dataStore.isInOfflineMode()) {
					showDialog(DIALOG_SWITCH_ONLINE_MODE);
				} else {
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
			case R.id.artist_tab_song:
				if (songListAdapter != null) {
					if (songListAdapter.getCount() > 0) {
						helpLayout.setVisibility(View.GONE);
					} else {
						helpLayout.setVisibility(View.GONE);
						helpText.setText(R.string.no_song);
					}
					setAdapter(songListAdapter);
				} else {
					setAdapter(null);
					new ArtistTask().execute(TYPE_FEATURED_SONGS, 0);
				}
				break;
			case R.id.artist_tab_album:
				if (albumListAdapter != null) {
					
					if (albumListAdapter.getCount() > 0) {
						helpLayout.setVisibility(View.GONE);
					} else {
						helpLayout.setVisibility(View.GONE);
						helpText.setText(R.string.no_album);
					}
					
					setAdapter(albumListAdapter);
				} else {
					setAdapter(null);
					new ArtistTask().execute(TYPE_FEATURED_ALBUMS, 0);
				}
				break;
			case R.id.artist_tab_video:
				if (videoListAdapter != null) {
					if (videoListAdapter.getCount() > 0) {
						helpLayout.setVisibility(View.GONE);
					} else {
						helpLayout.setVisibility(View.GONE);
						helpText.setText(R.string.no_video);
					}
					setAdapter(null);
					setAdapter(videoListAdapter);
				} else {
					new ArtistTask().execute(TYPE_FEATURED_VIDEOS, 0);
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
	class ArtistTask extends AsyncTask<Integer, Integer, List<Object>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressbar.setVisibility(View.VISIBLE);
		}

		@Override
		protected List<Object> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			if (!NetworkUtility.hasNetworkConnection())
				return null;

			int tabId = tabs.getCheckedRadioButtonId();
			int index = params[0];

			if (tabId == R.id.artist_tab_song) {
				List<Object> songs = new ArrayList<Object>();
				if (songListAdapter == null) {
					JsonSong jos = JsonSong.loadSongListByArtistId(artist.id, index);
					if (jos.isSuccess()) {
						songs.addAll(jos.songs);
						totalSongFound = jos.totalFound;
					} else {
						Log.e(LOG_TAG, jos.getErrorMessage());
					}
				} else {
					index = songListAdapter.getCount();
					JsonSong jos = JsonSong.loadSongListByArtistId(artist.id, index);
					if (jos.isSuccess()) {
						songs.addAll(jos.songs);
						totalSongFound = jos.totalFound;
					} else {
						Log.e(LOG_TAG, jos.getErrorMessage());
					}
				}
				return songs;
			} else if (tabId == R.id.artist_tab_album) {
				List<Object> albums = new ArrayList<Object>();
				if (albumListAdapter == null) {
					JsonAlbum jal = JsonAlbum.loadAlbummListByArtist(artist.id, index);
					if (jal.isSuccess()) {
						albums.addAll(jal.albums);
						totalAlbumFound = jal.totalFound;
					} else {
						Log.e(LOG_TAG, jal.getErrorMessage());
					}
				} else {
					index = albumListAdapter.getCount();
					JsonAlbum jal = JsonAlbum.loadAlbummListByArtist(artist.id, index);
					if (jal.isSuccess()) {
						albums.addAll(jal.albums);
						totalAlbumFound = jal.totalFound;
					} else {
						Log.e(LOG_TAG, jal.getErrorMessage());
					}
				}
				return albums;
			} else if (tabId == R.id.artist_tab_video) {
				List<Object> videos = new ArrayList<Object>();
				if (videoListAdapter == null) {
					JsonVideo jav = JsonVideo.loadVideoListByArtist(artist.id, index);
					if (jav.isSuccess()) {
						videos.addAll(jav.videos);
						totalVideoFound = jav.totalFound;
					} else {
						Log.e(LOG_TAG, jav.getErrorMessage());
					}
				} else {
					index = videoListAdapter.getCount();
					JsonVideo jav = JsonVideo.loadVideoListByArtist(artist.id, index);
					if (jav.isSuccess()) {
						videos.addAll(jav.videos);
						totalVideoFound = jav.totalFound;
					} else {
						Log.e(LOG_TAG, jav.getErrorMessage());
					}
				}
				return videos;
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressbar.setVisibility(View.GONE);
			contentView.setVisibility(View.VISIBLE);
			if (result == null) {
				result = new ArrayList<Object>();
			}
			setListAdapter(result);
		}
	}

	private void setListAdapter(List<Object> objs) {
		switch (tabs.getCheckedRadioButtonId()) {
		case R.id.artist_tab_song:
			if (objs.size() > 0) {
				helpLayout.setVisibility(View.GONE);
			} else {
				helpText.setText(R.string.no_song);
				helpLayout.setVisibility(View.GONE);
			}

			if (songListAdapter == null) {
				songListAdapter = new SongListAdapter(ArtistInfoActivity.this, objs, false, ArtistInfoActivity.class.getName());
				setAdapter(songListAdapter);
				// artistList.setAdapter(songListAdapter);
			} else {
				songListAdapter.notifyListObjectChanged(objs);
			}
			break;
		case R.id.artist_tab_album:
			if (objs.size() > 0) {
				helpLayout.setVisibility(View.GONE);
			} else {
				helpText.setText(R.string.no_album);
				helpLayout.setVisibility(View.GONE);
			}

			if (albumListAdapter == null) {
				albumListAdapter = new FeaturedListAdapter(ArtistInfoActivity.this, objs, false);
				setAdapter(albumListAdapter);
				// artistList.setAdapter(albumListAdapter);
			} else {
				albumListAdapter.notifyListObjectChanged(objs);
			}
			break;
		case R.id.artist_tab_video:
			if (objs.size() > 0) {
				helpLayout.setVisibility(View.GONE);
			} else {
				helpText.setText(R.string.no_video);
				helpLayout.setVisibility(View.GONE);
			}

			if (videoListAdapter == null) {
				videoListAdapter = new FeaturedListAdapter(ArtistInfoActivity.this, objs, false);
				// artistList.setAdapter(videoListAdapter);
				setAdapter(videoListAdapter);
			} else {
				videoListAdapter.notifyListObjectChanged(objs);
			}
			break;
		default:
			break;
		}
	}

	private void setAdapter(BaseAdapter adapter) {
		if (adapter != null) {
			artistList.setVisibility(View.VISIBLE);
			// artistList.setAdapter(adapter);
			artistList.setAdapter(new AutoLoadingAdapter(adapter, this));
		} else {
			artistList.setAdapter(null);
		}
	}

	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return ArtistInfoActivity.this;
	}

	@Override
	public List<Object> getMoreData() {
		// TODO Auto-generated method stub
		return new ArtistTask().doInBackground(0);
	}

	private class FollowTask extends AsyncTask<Integer, Integer, JSONObject> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return JsonArtist.follow(artist.id);
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					if (result.getInt("error") == 0) {
						subcrible = true;
						tvFollow.setText(R.string.format_unfollow);
						Toast.makeText(getApplicationContext(), getString(R.string.follow_success_msg), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private class UnFollowTask extends AsyncTask<Integer, Integer, JSONObject> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			return JsonArtist.unFollow(artist.id);
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					if (result.getInt("error") == 0) {
						tvFollow.setText(R.string.format_follow);
						subcrible = false;
						Toast.makeText(getApplicationContext(), getString(R.string.unfollor_sucess_msg), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btArtirstBack:
			finish();
			break;
		case R.id.btFollow:
			if (!subcrible) {
				new FollowTask().execute(0);
			} else {
				new UnFollowTask().execute(0);
			}

		default:
			break;
		}
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}
}
