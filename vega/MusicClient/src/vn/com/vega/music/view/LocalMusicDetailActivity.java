package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Genre;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.gui.ViewFlipperChangeAnimation;
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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class LocalMusicDetailActivity extends AbstractSongAcitivity implements Const {
	private static final String LOG_TAG = Const.LOG_PREF + LocalMusicDetailActivity.class.getSimpleName();

	private TextView mTitle, mainTitle;
	private TextView mInfo;
	private ListView mListView;
	private RelativeLayout emptyLayout, searchLayout;
	private LinearLayout headerLayout;
	private Button backBtn, clearBtn;

	private ImageView thumbnail;

	private ViewFlipper flipper;
	private Context context;
	private EditText searchEdt;

	private ProgressDialog mProgressDialog;

	private SongListAdapter songAdapter;

	private DataStore dataStore;
	private List<Song> songs;
	private String title = "";
	private boolean isArtist = true;

	private boolean offlineMode = false;
	
	//private boolean isDownloadedMusic = false;
	private int playlist_type;

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_local_music_detail);
		dataStore = DataStore.getInstance();
		final Bundle bd = this.getIntent().getExtras();
		context = this;
		//isDownloadedMusic = bd.getBoolean(Const.BUNDLE_DOWNLOADED_MUSIC);
		playlist_type = (int) bd.getInt(Const.BUNDLE_PLAYLIST_TYPE);
		
		mListView = (ListView) findViewById(R.id.local_music_detail_listview);
		thumbnail = (ImageView) findViewById(R.id.local_music_detail_icon);
		headerLayout = (LinearLayout)findViewById(R.id.region_header);
		mListView.setFastScrollEnabled(true);
		View footerView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		mListView.addHeaderView(footerView);
		mListView.addFooterView(footerView);
		hideHeaderLayout();
		mProgressDialog = new ProgressDialog(getParent());
		mProgressDialog.setMessage(getString(R.string.loading));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Object mObj = Session.getSharedObject(bd);

				if (mObj instanceof Artist) {
					Artist artist = (Artist) mObj;
					isArtist = true;
					if(playlist_type == LocalMusicActivity.TYPE_DOWNLOADED)
						songs = dataStore.loadDownloadedSongByArtist(artist.id);
					else if(playlist_type == LocalMusicActivity.TYPE_LOCAL)
						songs = dataStore.loadLocalSongByArtist(Integer.toString(artist.id));
					else
						songs = dataStore.loadFavouriteSongByArtist(artist.id);
					title = artist.name;
				} else if (mObj instanceof Genre) {
					Genre genre = (Genre) mObj;
					isArtist = false;
					if(playlist_type == LocalMusicActivity.TYPE_DOWNLOADED)
						songs = dataStore.loadDownloadedSongByGenre(genre.name);
					else if(playlist_type == LocalMusicActivity.TYPE_LOCAL)
						songs = dataStore.loadSongByGenre(Integer.toString(genre.id));
					else
						songs = dataStore.loadFavouriteSongByGenre(genre.name);
					title = genre.name;
				}
				mHandler.sendEmptyMessage(0);
			}
		}).start();
		
		registerForContextMenu(mListView);
	}
	
	private void hideHeaderLayout(){
		if(headerLayout != null){
			headerLayout.setVisibility(LinearLayout.GONE);
		}
	}
	
	private void showHeaderLayout(){
		if(headerLayout != null){
			headerLayout.setVisibility(LinearLayout.VISIBLE);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if ((playlist_type == LocalMusicActivity.TYPE_DOWNLOADED || playlist_type == LocalMusicActivity.TYPE_FAVOURITE)
				&& songAdapter != null) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songAdapter.getItem(info.position -1);
			ContextMenuEx contextMenu = new ContextMenuEx(false, false);
			contextMenu.showOptionalDialog(song, getParent(), this.getParent(),
					Const.TYPE_PLAYLIST_STACK);
		}
	}
	
	private void initComponentView() {
		mProgressDialog.dismiss();
		showHeaderLayout();
		emptyLayout = (RelativeLayout) findViewById(R.id.empty_layout);
		searchLayout = (RelativeLayout) findViewById(R.id.local_music_detail_search_layout);
		mainTitle = (TextView) findViewById(R.id.local_music_detail_main_title);
		mTitle = (TextView) findViewById(R.id.local_music_detail_title);
		mInfo = (TextView) findViewById(R.id.local_music_detail_info);
		clearBtn = (Button) findViewById(R.id.local_music_detail_search_clear_btn);
		clearBtn.setOnClickListener(onClearBtnListener);
		if(isArtist){
			mainTitle.setText("Nghệ sĩ");
			thumbnail.setImageResource(R.drawable.ic_artist_default);
		}
			
		else{
			mainTitle.setText("Thể loại");
			thumbnail.setImageResource(R.drawable.ic_song_default);
		}
			
		
		if (!title.trim().equals(""))
			mTitle.setText(title);
		else {
			if (isArtist)
				mTitle.setText("Unknown");
			else
				mTitle.setText("Unknown");
		}
		mInfo.setText(songs.size() + " bài hát");

		mListView.setOnItemClickListener(onItemClickListener);

		//playAllBtn = (Button) findViewById(R.id.my_playlist_detail_playall_btn);
		//searchBtn = (Button) findViewById(R.id.my_playlist_detail_search_btn);
		searchEdt = (EditText) findViewById(R.id.local_music_detail_search_edt);
		backBtn = (Button) findViewById(R.id.local_music_detail_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);
		//cancelBtn = (Button) findViewById(R.id.my_playlist_detail_cancel_search_btn);
		//flipper = (ViewFlipper) findViewById(R.id.local_music_detail_flipper);

		//setOnClick(playAllBtn);
		//setOnClick(searchBtn);
		//setOnClick(cancelBtn);

		// search while typing
		searchEdt.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// do search here
				String _keyword = searchEdt.getText().toString().trim().toLowerCase();
				List<Object> mObjs = new ArrayList<Object>();
				mObjs.addAll(songs);
				if (_keyword.equals("")) {
					songAdapter.setData(mObjs);
					songAdapter.notifyDataSetChanged();
				} else {
					List<Object> objs = new ArrayList<Object>();
					objs.clear();
					for (Object obj : mObjs) {
						if (obj instanceof Song) {
							Song song = (Song) obj;
							if (song.title.trim().toLowerCase().contains(_keyword)) {
								objs.add(obj);
							}
						}
					}
					songAdapter.setData(objs);
					songAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// do stuffs
			}

			@Override
			public void afterTextChanged(Editable s) {
				// do stuffs
			}
		});
		

		if (songs.size() > 20)
			searchLayout.setVisibility(RelativeLayout.VISIBLE);
		else
			searchLayout.setVisibility(RelativeLayout.GONE);

		List<Object> objs = new ArrayList<Object>();
		objs.addAll(songs);
		if(playlist_type == LocalMusicActivity.TYPE_LOCAL)
			songAdapter = new SongListAdapter(LocalMusicDetailActivity.this, objs, true, LocalMusicDetailActivity.class.getName());
		else
			songAdapter = new SongListAdapter(LocalMusicDetailActivity.this, objs, false, LocalMusicDetailActivity.class.getName());
		mListView.setAdapter(songAdapter);
	}

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
	
	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.my_playlist_detail_cancel_search_btn:
					flipper.setInAnimation(ViewFlipperChangeAnimation.inFromRightAnimation());
					flipper.setOutAnimation(ViewFlipperChangeAnimation.outToLeftAnimation());
					flipper.showPrevious();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(searchEdt.getWindowToken(), 0);
					List<Object> objs = new ArrayList<Object>();
					objs.addAll(songs);
					songAdapter.setData(objs);
					songAdapter.notifyDataSetChanged();
					break;
				case R.id.my_playlist_detail_search_btn:
					flipper.setInAnimation(ViewFlipperChangeAnimation.inFromRightAnimation());
					flipper.setOutAnimation(ViewFlipperChangeAnimation.outToLeftAnimation());
					flipper.showNext();
					break;
//				case R.id.my_playlist_detail_playall_btn:
//					// start player activity
//					Intent _i = new Intent();
//					NowPlayingList.init(NowPlayingList.TYPE_LOCAL, 0, (ArrayList<Song>) songs);
//					_i.setClass(getApplicationContext(), NowPlayingActivity.class);
//					startActivity(_i);
//					break;
				default:
					break;
				}
			}
		});
	}// end of setOnclick

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			if (adapter.getItemAtPosition(position) instanceof Song) {
				NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position-mListView.getHeaderViewsCount(), songAdapter.getSongList());
				startActivity(new Intent().setClass(getApplicationContext(), NowPlayingActivity.class));
			}
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			initComponentView();
		}
	};

	@Override
	public void onThumbnailClickListener(View v) {
		// TODO Auto-generated method stub
		Song song = (Song) v.getTag();
		songAdapter.download(song);
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}

}
