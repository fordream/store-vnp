package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.JsonVideo;
import vn.com.vega.music.clientserver.JsonWatchUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.gui.ContextMenuExListener;
import vn.com.vega.music.view.gui.ViewFlipperChangeAnimation;
import vn.com.vega.music.view.holder.MyPlaylistStack;
import vn.com.vega.music.view.holder.TabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class LocalMusicActivity extends AbstractSongAcitivity implements Const, ContextMenuExListener {
	private static final String LOG_TAG = Const.LOG_PREF
			+ LocalMusicActivity.class.getSimpleName();

	/**
	 * View
	 */
	private TextView dockinaTitle;
	private RadioGroup tabs;
	private ListView myList;
	private View helpLayout;
	private TextView helpText, titleText;
	private TextView subText;
	private Button playAllBtn, clearBtn;
	private Button tabSongBtn, tabArtistBtn, tabGenreBtn, backBtn, editExBtn;
	private Button cancelBtn;
	private ViewFlipper flipper;
	private Context context;
	private EditText searchEdt;
	private RelativeLayout searchLayout;

	// Song Adapter
	private SongListAdapter songListAdapter;
	private FeaturedListAdapter artistListAdapter;
	private FeaturedListAdapter genreListAdapter;

	protected int tabsIndex = TAB_SONG;

	private DataStore dataStore;
	private ProgressDialog mProgressDialog;
	private MyPlaylistStack activityStack;
	private List<Object> mObjs;

	private int currTab = SONG_TAB;
	private boolean isfirstTime = true;

	// private boolean isDownloadedMusic = false;
	private int playlist_type;

	protected static final int SONG_TAB = 0;
	protected static final int ARTIST_TAB = 1;
	protected static final int GENRE_TAB = 2;

	public static final int TYPE_DOWNLOADED = 1;
	public static final int TYPE_LOCAL = 2;
	public static final int TYPE_FAVOURITE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_local_music);
		dataStore = DataStore.getInstance();

		Bundle bd = this.getIntent().getExtras();
		// isDownloadedMusic = (boolean)
		// bd.getBoolean(Const.BUNDLE_DOWNLOADED_MUSIC);
		playlist_type = (int) bd.getInt(Const.BUNDLE_PLAYLIST_TYPE);

		activityStack = (MyPlaylistStack) getParent();
		dockinaTitle = (TextView) findViewById(R.id.my_playlist_detail_numofsong_txt);
		// tabs = (RadioGroup) findViewById(R.id.local_music_tabs);
		myList = (ListView) findViewById(R.id.local_music_listview);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		myList.addHeaderView(footerView);
		myList.addFooterView(footerView);
		myList.setFastScrollEnabled(true);
		helpLayout = findViewById(R.id.local_music_empty_layout);
		helpText = (TextView) findViewById(R.id.local_music_empty_txt);
		titleText = (TextView) findViewById(R.id.local_music_title_txt);
		editExBtn = (Button) findViewById(R.id.btn_edit_ex);
		editExBtn.setOnClickListener(onEditExBtnListener);

		if (playlist_type == TYPE_DOWNLOADED)
			titleText.setText(getString(R.string.downloaded_music));
		else if (playlist_type == TYPE_LOCAL)
			titleText.setText(getString(R.string.local_music));
		else
			titleText.setText(getString(R.string.favourite_music));

		if (playlist_type == TYPE_LOCAL)
			editExBtn.setVisibility(Button.GONE);
		else
			editExBtn.setVisibility(Button.VISIBLE);

		// subText = (TextView) findViewById(R.id.local_music_subtitle);

		// playAllBtn = (Button)
		// findViewById(R.id.my_playlist_detail_playall_btn);
		// searchBtn = (Button) findViewById(R.id.search_btn);
		searchEdt = (EditText) findViewById(R.id.search_edt);
		cancelBtn = (Button) findViewById(R.id.my_playlist_detail_cancel_search_btn);
		backBtn = (Button) findViewById(R.id.local_music_back_btn);
		clearBtn = (Button) findViewById(R.id.local_music_search_clear_btn);
		clearBtn.setOnClickListener(onClearBtnListener);
		backBtn.setOnClickListener(onBackBtnListener);

		tabSongBtn = (Button) findViewById(R.id.tab_song_btn);
		tabArtistBtn = (Button) findViewById(R.id.tab_artist_btn);
		tabGenreBtn = (Button) findViewById(R.id.tab_genre_btn);

		searchLayout = (RelativeLayout) findViewById(R.id.search_layout);

		tabSongBtn.setOnClickListener(onTabSongListener);
		tabArtistBtn.setOnClickListener(onTabArtistListener);
		tabGenreBtn.setOnClickListener(onTabGenreListener);

		// flipper = (ViewFlipper) findViewById(R.id.local_music_flipper);

		myList.setOnItemClickListener(onItemClickListener);

		// tabs.setOnCheckedChangeListener(onCheckedChangeListener);

		// search while typing
		searchEdt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do search here
				String _keyword = searchEdt.getText().toString().trim()
						.toLowerCase();
				if (_keyword.equals("")) {
					if (mObjs != null) {
						songListAdapter.setData(mObjs);
						songListAdapter.notifyDataSetChanged();
					}
				} else {
					List<Object> objs = new ArrayList<Object>();
					objs.clear();
					for (Object obj : mObjs) {
						if (obj instanceof Song) {
							Song song = (Song) obj;
							if (song.title.trim().toLowerCase()
									.contains(_keyword)) {
								objs.add(obj);
							}
						}
					}
					songListAdapter.setData(objs);
					songListAdapter.notifyDataSetChanged();
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

		registerForContextMenu(myList);
		// reg with ContextMenuEx
		ContextMenuEx.regContextMenuExListener(this);

		initData();

		currTab = getSelectedTab();
		if (currTab == SONG_TAB)
			showSongTab();
		else if (currTab == ARTIST_TAB)
			showArtistTab();
		else
			showGenreTab();

	}


	@Override
	protected void onPause() {
		super.onPause();
		if (songListAdapter != null) {
			songListAdapter.onPause();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// do stuffs
		if (dataStore == null)
			dataStore = DataStore.getInstance();
		if (isfirstTime)
			isfirstTime = false;
		else {
			if (currTab == SONG_TAB && songListAdapter != null) {
				initData();
				songListAdapter.setData(mObjs);
				songListAdapter.onResume();
				tabChanged();
				songListAdapter.notifyDataSetChanged();
			}

		}

	}

	public void setSelectedTab(int value) {

		SharedPreferences sp = this.getSharedPreferences("SELECTED_TAB",
				Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("SELECTED_TAB", value);
		editor.commit();

	}

	public int getSelectedTab() {

		SharedPreferences sp = null;
		sp = getSharedPreferences("SELECTED_TAB", Activity.MODE_PRIVATE);

		return sp.getInt("SELECTED_TAB", SONG_TAB);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if ((playlist_type == TYPE_DOWNLOADED || playlist_type == TYPE_FAVOURITE)
				&& currTab == SONG_TAB && songListAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songListAdapter.getItem(info.position - 1);
			ContextMenuEx contextMenu = new ContextMenuEx(false, false);
			contextMenu.showOptionalDialog(song, getParent(), this.getParent(),
					Const.TYPE_PLAYLIST_STACK);
		}
	}

	private void initData() {
		mObjs = new ArrayList<Object>();

		if (playlist_type == TYPE_DOWNLOADED)
			mObjs.addAll(dataStore.getListDownloadedSongs());
		else if (playlist_type == TYPE_LOCAL)
			mObjs.addAll(dataStore.loadAllLocalSong());
		else
			mObjs.addAll(dataStore.getSpecialPlaylistByType(
					Playlist.TYPE_FAVORITE).getSongList());

	}

	private void tabChanged() {
		if (currTab == SONG_TAB) {
			if (songListAdapter != null) {
				tabSongBtn.setText(String.format(
						getResources().getString(R.string.tab_song),
						songListAdapter.getCount()));
			}

			else
				tabSongBtn.setText(String.format(
						getResources().getString(R.string.tab_song), 0));

			tabSongBtn.setTextColor(Color.parseColor("#ffffff"));
			tabArtistBtn.setTextColor(Color.parseColor("#999999"));
			tabGenreBtn.setTextColor(Color.parseColor("#999999"));
			tabSongBtn.setBackgroundResource(R.drawable.bg_segment_selected_1);
			tabArtistBtn.setBackgroundResource(R.drawable.selector_tab_album);
			tabGenreBtn.setBackgroundResource(R.drawable.selector_tab_video);

			searchLayout.setVisibility(RelativeLayout.VISIBLE);
		} else if (currTab == ARTIST_TAB) {
			if (artistListAdapter != null)
				tabArtistBtn.setText(String.format(
						getResources().getString(R.string.tab_artist),
						artistListAdapter.getCount()));
			else
				tabArtistBtn.setText(String.format(
						getResources().getString(R.string.tab_artist), 0));
			tabSongBtn.setBackgroundResource(R.drawable.selector_tab_song);
			tabArtistBtn
					.setBackgroundResource(R.drawable.bg_segment_selected_2);
			tabGenreBtn.setBackgroundResource(R.drawable.selector_tab_video);
			tabSongBtn.setTextColor(Color.parseColor("#999999"));
			tabArtistBtn.setTextColor(Color.parseColor("#ffffff"));
			tabGenreBtn.setTextColor(Color.parseColor("#999999"));

			searchLayout.setVisibility(RelativeLayout.GONE);
		} else if (currTab == GENRE_TAB) {
			if (genreListAdapter != null)
				tabGenreBtn.setText(String.format(
						getResources().getString(R.string.tab_genre),
						genreListAdapter.getCount()));
			else
				tabGenreBtn.setText(String.format(
						getResources().getString(R.string.tab_genre), 0));
			tabSongBtn.setBackgroundResource(R.drawable.selector_tab_song);
			tabArtistBtn.setBackgroundResource(R.drawable.selector_tab_album);
			tabGenreBtn.setBackgroundResource(R.drawable.bg_segment_selected_3);
			tabSongBtn.setTextColor(Color.parseColor("#999999"));
			tabArtistBtn.setTextColor(Color.parseColor("#999999"));
			tabGenreBtn.setTextColor(Color.parseColor("#ffffff"));
			searchLayout.setVisibility(RelativeLayout.GONE);
		}
	}

	private void showSongTab() {

		if (mObjs.size() > 0)
			helpLayout.setVisibility(LinearLayout.GONE);
		else
			helpLayout.setVisibility(LinearLayout.VISIBLE);
		if (songListAdapter == null) {
			if (playlist_type == TYPE_LOCAL)
				songListAdapter = new SongListAdapter(LocalMusicActivity.this,
						mObjs, true, LocalMusicActivity.class.getName());
			else
				songListAdapter = new SongListAdapter(LocalMusicActivity.this,
						mObjs, false, LocalMusicActivity.class.getName());
		}
		myList.setAdapter(songListAdapter);
		currTab = SONG_TAB;
		setSelectedTab(SONG_TAB);
		tabChanged();
		myList.setFastScrollEnabled(true);
		// showTotalFound(songListAdapter.getCount(), SONG_TAB);
		// subText.setText(songListAdapter.getCount() + " bài nhạc");
	}

	private void showArtistTab() {
		if (artistListAdapter == null) {
			List<Object> objs = new ArrayList<Object>();

			if (playlist_type == TYPE_DOWNLOADED)
				objs.addAll(dataStore.loadArtistOfDownloadedMusic());
			else if (playlist_type == TYPE_LOCAL)
				objs.addAll(dataStore.loadArtist());
			else
				objs.addAll(dataStore.loadArtistOfFavouriteMusic());

			if (objs.size() > 0)
				helpLayout.setVisibility(LinearLayout.GONE);
			else
				helpLayout.setVisibility(LinearLayout.VISIBLE);

			artistListAdapter = new FeaturedListAdapter(
					LocalMusicActivity.this, objs, true);
		}
		myList.setAdapter(artistListAdapter);
		currTab = ARTIST_TAB;
		setSelectedTab(ARTIST_TAB);
		tabChanged();
		// myList.setFastScrollEnabled(false);
		// showTotalFound(artistListAdapter.getCount(), ARTIST_TAB);
	}

	private void showGenreTab() {
		if (genreListAdapter == null) {
			List<Object> objs = new ArrayList<Object>();
			if (playlist_type == TYPE_DOWNLOADED)
				objs.addAll(dataStore.loadGenreOfDownloadedMusic());
			else if (playlist_type == TYPE_LOCAL)
				objs.addAll(dataStore.loadGenre());
			else
				objs.addAll(dataStore.loadGenreOfFavouriteMusic());
			if (objs.size() > 0)
				helpLayout.setVisibility(LinearLayout.GONE);
			else
				helpLayout.setVisibility(LinearLayout.VISIBLE);
			if (playlist_type == TYPE_LOCAL)
				genreListAdapter = new FeaturedListAdapter(
						LocalMusicActivity.this, objs, true);
			else
				genreListAdapter = new FeaturedListAdapter(
						LocalMusicActivity.this, objs, false);
		}
		myList.setAdapter(genreListAdapter);
		currTab = GENRE_TAB;
		setSelectedTab(GENRE_TAB);
		tabChanged();
		// myList.setFastScrollEnabled(false);
		// showTotalFound(genreListAdapter.getCount(), GENRE_TAB);
	}

	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.my_playlist_detail_cancel_search_btn:
					flipper.setInAnimation(ViewFlipperChangeAnimation
							.inFromRightAnimation());
					flipper.setOutAnimation(ViewFlipperChangeAnimation
							.outToLeftAnimation());
					flipper.showPrevious();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(searchEdt.getWindowToken(), 0);
					songListAdapter.setData(mObjs);
					songListAdapter.notifyDataSetChanged();
					break;
				case R.id.my_playlist_detail_search_btn:
					flipper.setInAnimation(ViewFlipperChangeAnimation
							.inFromRightAnimation());
					flipper.setOutAnimation(ViewFlipperChangeAnimation
							.outToLeftAnimation());
					flipper.showNext();
					break;

				default:
					break;
				}
			}
		});
	}// end of setOnclick

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
		}
	};

	OnClickListener onClearBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			searchEdt.setText("");
		}
	};

	OnClickListener onBackBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			Object obj = adapter.getItemAtPosition(position);
			if (obj instanceof Song) {
				NowPlayingList.init(NowPlayingList.TYPE_LOCAL, position
						- myList.getHeaderViewsCount(),
						songListAdapter.getSongList());
				Intent i = new Intent().setClass(getApplicationContext(),
						NowPlayingActivity.class).setFlags(
						Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			} else if (obj instanceof Artist) {
				Artist artist = (Artist) obj;
				// List<Song> songs =
				// dataStore.loadLocalSongByArtist(Integer.toString(artist.id));
				Bundle bundle = new Bundle();
				// bundle.putBoolean(Const.BUNDLE_DOWNLOADED_MUSIC,
				// isDownloadedMusic);
				bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE, playlist_type);
				Session.putSharedObject(bundle, LocalMusicActivity.this, artist);
				// bundle.putParcelable(OBJECT, artist)

				Intent intent = new Intent(LocalMusicActivity.this,
						LocalMusicDetailActivity.class);
				intent.putExtras(bundle);
				activityStack.startChildActivity(
						LocalMusicDetailActivity.class.getName(), intent);

			} else if (obj instanceof Genre) {
				Genre genre = (Genre) obj;
				// List<Song> songs =
				// dataStore.loadLocalSongByArtist(Integer.toString(artist.id));
				Bundle bundle = new Bundle();
				// bundle.putBoolean(Const.BUNDLE_DOWNLOADED_MUSIC,
				// isDownloadedMusic);
				bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE, playlist_type);
				Session.putSharedObject(bundle, LocalMusicActivity.this, genre);

				Intent intent = new Intent(LocalMusicActivity.this,
						LocalMusicDetailActivity.class);
				intent.putExtras(bundle);
				activityStack.startChildActivity(
						LocalMusicDetailActivity.class.getName(), intent);

			}

		}
	};

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putInt(Const.SELECTED_TAB, currTab);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		currTab = savedInstanceState.getInt(Const.SELECTED_TAB);
	}

	private void showTotalFound(int total, int type) {
		if (type == SONG_TAB)
			dockinaTitle.setText(String.format(
					getResources().getString(R.string.dockina_song), total));
		else if (type == ARTIST_TAB)
			dockinaTitle.setText(String.format(
					getResources().getString(R.string.dockina_artist), total));
		else if (type == GENRE_TAB)
			dockinaTitle.setText(String.format(
					getResources().getString(R.string.dockina_genre), total));
	}

	private void changeButtonVisible(int type) {
		if (type == SONG_TAB) {
			// if(mObjs.size() > 20){
			// searchBtn.setVisibility(Button.VISIBLE);
			// }
			// else{
			// searchBtn.setVisibility(Button.GONE);
			// }
			playAllBtn.setVisibility(Button.VISIBLE);
			cancelBtn.setVisibility(Button.VISIBLE);
		} else {
			playAllBtn.setVisibility(Button.INVISIBLE);
			// searchBtn.setVisibility(Button.INVISIBLE);
			cancelBtn.setVisibility(Button.INVISIBLE);
		}
	}

	OnClickListener onTabSongListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currTab = SONG_TAB;
			setSelectedTab(SONG_TAB);
			// myList.setFastScrollEnabled(true);
			if (songListAdapter != null) {
				if (songListAdapter.getCount() > 0) {
					helpLayout.setVisibility(View.GONE);
					// dockinaTitle.setVisibility(TextView.VISIBLE);
					// showTotalFound(songListAdapter.getCount(), currTab);
				} else {
					helpLayout.setVisibility(View.VISIBLE);
					helpText.setText(R.string.no_local_song);
					// dockinaTitle.setVisibility(TextView.INVISIBLE);
				}
				myList.setAdapter(songListAdapter);
				tabChanged();
			} else {
				showSongTab();
			}
		}
	};

	OnClickListener onTabArtistListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			currTab = ARTIST_TAB;
			setSelectedTab(ARTIST_TAB);
			// myList.setFastScrollEnabled(false);
			// changeButtonVisible(currTab);
			if (artistListAdapter != null) {
				if (artistListAdapter.getCount() > 0) {
					helpLayout.setVisibility(View.GONE);
					// dockinaTitle.setVisibility(TextView.VISIBLE);
					// showTotalFound(artistListAdapter.getCount(), currTab);
				} else {
					helpLayout.setVisibility(View.VISIBLE);
					helpText.setText(R.string.no_local_artist);
					// dockinaTitle.setVisibility(TextView.INVISIBLE);
				}
				myList.setAdapter(artistListAdapter);
				tabChanged();
			} else {
				showArtistTab();
			}

		}
	};

	OnClickListener onEditExBtnListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			int playlistId;
			if (playlist_type == TYPE_FAVOURITE) {
				playlistId = dataStore
						.getSpecialPlaylistByType(Playlist.TYPE_FAVORITE).id;
			} else
				playlistId = -72;
			bundle.putInt(Const.BUNDLE_PLAYLIST_ID, playlistId);
			bundle.putInt(Const.BUNDLE_PLAYLIST_TYPE, playlist_type);
			Intent i = new Intent(getParent(), MyPlaylistEditorActivity.class);
			i.putExtras(bundle);
			startActivity(i);
		}
	};

	OnClickListener onTabGenreListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currTab = GENRE_TAB;
			setSelectedTab(GENRE_TAB);
			// myList.setFastScrollEnabled(false);
			// changeButtonVisible(currTab);
			if (genreListAdapter != null) {
				if (genreListAdapter.getCount() > 0) {
					helpLayout.setVisibility(View.GONE);
					// dockinaTitle.setVisibility(TextView.VISIBLE);
					// showTotalFound(genreListAdapter.getCount(), currTab);
				} else {
					helpLayout.setVisibility(View.VISIBLE);
					helpText.setText(R.string.no_local_genre);
					// dockinaTitle.setVisibility(TextView.INVISIBLE);
				}

				myList.setAdapter(genreListAdapter);
				tabChanged();
			} else {
				showGenreTab();
			}
		}
	};

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onThumbnailClickListener(View v) {
		// TODO Auto-generated method stub
		Song song = (Song) v.getTag();
		songListAdapter.download(song);
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onUpdateUI() {
		// TODO Auto-generated method stub
		if (currTab == SONG_TAB && songListAdapter != null) {
			initData();
			songListAdapter.setData(mObjs);
			songListAdapter.onResume();
			tabChanged();
			songListAdapter.notifyDataSetChanged();
		}
	}
}
