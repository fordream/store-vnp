package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAlbum;
import vn.com.vega.music.clientserver.JsonArtist;
import vn.com.vega.music.clientserver.JsonPlaylist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.clientserver.JsonVideo;
import vn.com.vega.music.clientserver.JsonWatchUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Album;
import vn.com.vega.music.objects.Artist;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.objects.Video;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FeaturePlaylistAdapter;
import vn.com.vega.music.view.adapter.FeaturedListAdapter;
import vn.com.vega.music.view.adapter.SongListAdapter;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.holder.SearchTabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SearchActivity extends AbstractSongAcitivity implements Const {
	private static final String LOG_TAG = Const.LOG_PREF + SearchActivity.class.getSimpleName();

	private EditText searchEdittex;
	private Button cancelButton, clearBtn, helpBtn;
	private View helpLayout, spinningLayout;
	private TextView helpText;

	private ImageView helpImg;

	private RadioGroup tabs;
	private ListView listView;

	// private ProgressBar progressbar;

	// private ViewFlipper topbarViewFlipper;
	// private Button selector;

	private RadioButton tabSong;
	private RadioButton tabVideo;
	private RadioButton tabAlbum;
	private RadioButton tabArtist;
	private RadioButton tabPlaylist;

	private Animation pushLeftIn, pushLeftOut;

	private int resultFound = 0;
	private int songFlag = SCROLLABLE_VALUE;
	private int albumFlag = SCROLLABLE_VALUE;
	private int artistFlag = SCROLLABLE_VALUE;
	private int playlistFlag = SCROLLABLE_VALUE;
	private int videoFlag = SCROLLABLE_VALUE;
	private boolean offlineMode = false;

	private String keySearch = "";

	private static final int SEARCHED_VALUE = 0;
	private static final int SCROLLABLE_VALUE = 1;
	private static final int UNSCROLLABLE_VALUE = 2;

	private DataStore dataStore;

	private FeaturedListAdapter albumAdapter, artistAdapter, videoAdapter;

	private FeaturePlaylistAdapter playlistAdapter;

	private SongListAdapter songListAdapter;

	private SearchTabHolder searchTabHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_search);

		dataStore = DataStore.getInstance();
		searchTabHolder = (SearchTabHolder) getParent();
		helpText = (TextView) findViewById(R.id.search_help_label);
		searchEdittex = (EditText) findViewById(R.id.search_edt);
		cancelButton = (Button) findViewById(R.id.cancel_btn);
		clearBtn = (Button) findViewById(R.id.search_clear_btn);
		tabs = (RadioGroup) findViewById(R.id.search_tabs);
		listView = (ListView) findViewById(R.id.search_listview);

		View emptyView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		listView.addHeaderView(emptyView);
		listView.addFooterView(emptyView);

		// progressbar = (ProgressBar) findViewById(R.id.search_progressbar);
		spinningLayout = findViewById(R.id.search_spinning);

		helpLayout = findViewById(R.id.help_layout);
		helpImg = (ImageView) findViewById(R.id.search_help_icon);
		helpBtn = (Button) findViewById(R.id.search_help_btn);

		tabSong = (RadioButton) findViewById(R.id.search_tab_songs);
		tabAlbum = (RadioButton) findViewById(R.id.search_tab_album);
		tabArtist = (RadioButton) findViewById(R.id.search_tab_artist);
		tabPlaylist = (RadioButton) findViewById(R.id.search_tab_playlist);
		tabVideo = (RadioButton) findViewById(R.id.search_tab_videos);

		// topbarViewFlipper = (ViewFlipper) findViewById(R.id.search_topbar);
		// selector = (Button) findViewById(R.id.search_topbar_selector);

		// Animation
		pushLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		pushLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);

		// selector.setOnClickListener(onTopbarSelect);

		// progressbar.setVisibility(View.INVISIBLE);
		hideProgressBar();

		cancelButton.setOnClickListener(onCancelButtonListener);
		tabs.setOnCheckedChangeListener(onCheckedChangeListener);
		listView.setOnScrollListener(onListScrollListener);
		listView.setOnItemClickListener(onItemClickListener);
		helpBtn.setOnClickListener(onHelpBtnListener);
		// searchEdittex.setOnFocusChangeListener(onFocusChangeListener);
		searchEdittex.setOnKeyListener(onSearchEnterListener);
		clearBtn.setOnClickListener(onClearBtnListener);

		// disable tab controls
		// disableTabControls();
		registerForContextMenu(listView);

	}

	private void disableTabControls() {
		tabSong.setEnabled(false);
		tabAlbum.setEnabled(false);
		tabArtist.setEnabled(false);
		tabPlaylist.setEnabled(false);
		tabVideo.setEnabled(false);
	}

	private void enableTabControls() {
		tabSong.setEnabled(true);
		tabAlbum.setEnabled(true);
		tabArtist.setEnabled(true);
		tabPlaylist.setEnabled(true);
		tabVideo.setEnabled(true);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// switch (tabs.getCheckedRadioButtonId()) {
		// case R.id.search_tab_songs:
		// if(songListAdapter == null || songListAdapter.getCount() == 0)
		// showSoftKeyboard();
		// else
		// hideSoftKeyboard();
		// break;
		// case R.id.search_tab_album:
		// if(albumAdapter == null || albumAdapter.getCount() == 0)
		// showSoftKeyboard();
		// else
		// hideSoftKeyboard();
		// break;
		// case R.id.search_tab_artist:
		// if(artistAdapter == null || artistAdapter.getCount() == 0)
		// showSoftKeyboard();
		// else
		// hideSoftKeyboard();
		// break;
		// case R.id.search_tab_videos:
		// if(videoAdapter == null || videoAdapter.getCount() == 0)
		// showSoftKeyboard();
		// else
		// hideSoftKeyboard();
		// break;
		// case R.id.search_tab_playlist:
		// if(playlistAdapter == null || playlistAdapter.getCount() == 0)
		// showSoftKeyboard();
		// else
		// hideSoftKeyboard();
		// break;
		// default:
		// break;
		// }
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (songListAdapter != null && tabs.getCheckedRadioButtonId() == R.id.search_tab_songs) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = (Song) songListAdapter.getItem(info.position - 1);
			ContextMenuEx contextMenu = new ContextMenuEx();
			contextMenu.showOptionalDialog(song, getParent(), ContextMenuEx.TYPE_NORMAL);
		}
	};

	public void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
		builder.setMessage(R.string.offline_mode_msg).setCancelable(false)
				.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
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

	OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			// Log.e(TAG, "Focus changed");
			if (hasFocus)
				showSoftKeyboard();
		}
	};

	OnClickListener onTopbarSelect = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			offlineMode = dataStore.isInOfflineMode();
			if (offlineMode) {
				askGoToOnline();
			} else {
				showNext();
				showSoftKeyboard();
				helpText.setText("");
				// enable tab controls
				enableTabControls();
			}
		}
	};

	OnClickListener onClearBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			searchEdittex.setText("");
		}
	};

	OnClickListener onCancelButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// showPrevious();
			// hideSoftKeyboard();
			// listView.setAdapter(null);
			// helpText.setText(R.string.search_help_label);
			// helpLayout.setVisibility(View.VISIBLE);
			// // disable tab controls
			// disableTabControls();
			doSearch();

		}
	};

	private void showDefaultEmplyListView() {
		helpImg.setBackgroundResource(R.drawable.ic_search_large);
		helpText.setText(R.string.search_help_label);
		helpBtn.setText(R.string.retry);

		helpImg.setVisibility(ImageView.VISIBLE);
		helpText.setVisibility(TextView.VISIBLE);
		helpBtn.setVisibility(Button.GONE);

		listView.setAdapter(null);
		helpLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void showOfflineModeView() {
		helpImg.setBackgroundResource(R.drawable.ic_search_large);
		helpText.setText(R.string.search_offline_msg);
		helpBtn.setText("Go online");

		helpImg.setVisibility(ImageView.VISIBLE);
		helpText.setVisibility(TextView.VISIBLE);
		helpBtn.setVisibility(Button.VISIBLE);

		listView.setAdapter(null);
		helpLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void showEmptyKeyView() {
		helpImg.setBackgroundResource(R.drawable.ic_search_large);
		helpText.setText(R.string.search_edittext_empty);

		helpImg.setVisibility(ImageView.VISIBLE);
		helpText.setVisibility(TextView.VISIBLE);
		helpBtn.setVisibility(Button.GONE);

		listView.setAdapter(null);
		helpLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void showDisconnectedView() {
		helpImg.setBackgroundResource(R.drawable.ic_disconnected);
		helpText.setText(R.string.search_disconnected_msg);
		helpBtn.setText("Go online");

		helpImg.setVisibility(ImageView.VISIBLE);
		helpText.setVisibility(TextView.VISIBLE);
		helpBtn.setVisibility(Button.VISIBLE);

		listView.setAdapter(null);
		helpLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void showEmptyListView() {
		helpImg.setBackgroundResource(R.drawable.ic_search_large);
		helpText.setText(R.string.search_no_result);
		helpBtn.setText(R.string.retry);

		helpImg.setVisibility(ImageView.VISIBLE);
		helpText.setVisibility(TextView.VISIBLE);
		helpBtn.setVisibility(Button.VISIBLE);

		listView.setAdapter(null);
		helpLayout.setVisibility(RelativeLayout.VISIBLE);
	}

	private void doSearch() {
		if (dataStore.isInOfflineMode()) {
			// askGoToOnline();
			showOfflineModeView();
		} else {
			if (NetworkUtility.hasNetworkConnection()) {
				keySearch = searchEdittex.getText().toString();
				if (keySearch == null || keySearch.trim().equals(""))
					// showToast(getString(R.string.search_edittext_empty));
					showEmptyKeyView();
				else {
					helpLayout.setVisibility(View.GONE);
					switch (tabs.getCheckedRadioButtonId()) {
					case R.id.search_tab_songs:
						songListAdapter = null;
						break;
					case R.id.search_tab_album:
						albumAdapter = null;
						break;
					case R.id.search_tab_artist:
						artistAdapter = null;
						break;
					case R.id.search_tab_playlist:
						playlistAdapter = null;
						break;
					default:
						break;
					}
					new SearchTask().execute(0);
				}
			} else {
				showDisconnectedView();
				// Toast.makeText(getParent(),
				// getString(R.string.no_network_connection),
				// Toast.LENGTH_SHORT).show();
			}

		}
	}

	OnKeyListener onSearchEnterListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() != KeyEvent.ACTION_DOWN)
				return true;
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				doSearch();
				return true;
			}
			return false;
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
			} else
				SongListAdapter.setScrolling(true);

			switch (tabs.getCheckedRadioButtonId()) {
			case R.id.search_tab_songs:
				if (scrollState == 0 && endListReacher && (songFlag == SCROLLABLE_VALUE) && !dataStore.isInOfflineMode()
						&& NetworkUtility.hasNetworkConnection() && listView.getAdapter().getCount() < LIST_LIMITED - 20) {
					new SearchTask().execute(0);
				}
				break;
			case R.id.search_tab_album:
				if (scrollState == 0 && endListReacher && (albumFlag == SCROLLABLE_VALUE) && !dataStore.isInOfflineMode()
						&& NetworkUtility.hasNetworkConnection() && listView.getAdapter().getCount() < LIST_LIMITED - 20) {
					new SearchTask().execute(0);
				}
				break;
			case R.id.search_tab_artist:
				if (scrollState == 0 && endListReacher && (artistFlag == SCROLLABLE_VALUE) && !dataStore.isInOfflineMode()
						&& NetworkUtility.hasNetworkConnection() && listView.getAdapter().getCount() < LIST_LIMITED - 20) {
					new SearchTask().execute(0);
				}
				break;
			case R.id.search_tab_playlist:
				if (scrollState == 0 && endListReacher && (playlistFlag == SCROLLABLE_VALUE) && !dataStore.isInOfflineMode()
						&& NetworkUtility.hasNetworkConnection() && listView.getAdapter().getCount() < LIST_LIMITED - 20) {
					new SearchTask().execute(0);
				}
				break;
			case R.id.search_tab_videos:
				if (scrollState == 0 && endListReacher && (videoFlag == SCROLLABLE_VALUE) && !dataStore.isInOfflineMode()
						&& NetworkUtility.hasNetworkConnection() && listView.getAdapter().getCount() < LIST_LIMITED - 20) {
					new SearchTask().execute(0);
				}
				break;
			default:
				break;
			}
		}
	};

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			// TODO Auto-generated method stub
			Bundle b;
			Object obj = adapter.getItemAtPosition(position);
			if (obj instanceof Song) {
				NowPlayingList.init(NowPlayingList.TYPE_NORMAL, position - listView.getHeaderViewsCount(), songListAdapter.getSongList());
				Intent intent = new Intent();
				intent.setClass(getParent(), NowPlayingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.hold);

			} else if (obj instanceof Album) {
				Album album = (Album) obj;
				b = new Bundle();
				Session.putSharedObject(b, SearchActivity.this, album);

				searchTabHolder.startChildActivity(AlbumInfoActivity.class.getName(),
						new Intent(SearchActivity.this, AlbumInfoActivity.class).putExtras(b));

			} else if (obj instanceof Artist) {
				Artist artist = (Artist) obj;
				b = new Bundle();
				Session.putSharedObject(b, SearchActivity.this, artist);
				searchTabHolder.startChildActivity(ArtistInfoActivity.class.getName(),
						new Intent(SearchActivity.this, ArtistInfoActivity.class).putExtras(b));

			} else if (obj instanceof Playlist) {
				Playlist playlist = (Playlist) obj;
				b = new Bundle();
				Session.putSharedObject(b, SearchActivity.this, playlist);
				b.putInt(Const.BUNDLE_PLAYLIST_TYPE, MyPlaylistActivity.ONLINE_PLAYLIST);
				searchTabHolder.startChildActivity(MyPlaylistDetailActivity.class.getName(), new Intent(SearchActivity.this,
						MyPlaylistDetailActivity.class).putExtras(b));

			} else if (obj instanceof Video) {
				//openVideo((Video) obj);
				Toast.makeText(getParent(), "Chức năng này đang được cập nhật", Toast.LENGTH_SHORT).show();
			}
		}

	};

	private void openVideo(Video video) {
		try {
			// call clientserver module to get streaming url
			JsonWatchUrl jsonWatchUrl = JsonWatchUrl.loadWatchUrl(Integer.parseInt(video.id));
			if (!jsonWatchUrl.isSuccess()) {
				// do stuffs
				Toast.makeText(getParent(), "Đường dẫn bị lỗi", Toast.LENGTH_SHORT).show();
			} else {

				MusicPlayer musicPlayer = MusicPlayer.getInstance();
				if (musicPlayer != null && musicPlayer.isPlaying())
					musicPlayer.pause();

				/**
				 * Tell music service pause if playing
				 */

				Intent i = new Intent("com.android.music.musicservicecommand");
				i.putExtra("command", "pause");
				sendBroadcast(i);

				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(jsonWatchUrl.streamingUrl), "video/*");
				startActivity(intent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getParent(), "Đường dẫn bị lỗi", Toast.LENGTH_SHORT).show();
		}

	}

	OnClickListener onHelpBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dataStore.setOfflineModeStatus(false);
			doSearch();
		}
	};

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.search_tab_songs:
				if (songListAdapter == null || songListAdapter.getCount() == 0) {
					showDefaultEmplyListView();
				} else {
					helpLayout.setVisibility(View.GONE);
					listView.setAdapter(songListAdapter);
				}
				break;
			case R.id.search_tab_album:
				if (albumAdapter == null || albumAdapter.getCount() == 0) {
					showDefaultEmplyListView();
				} else {
					helpLayout.setVisibility(View.GONE);
					listView.setAdapter(albumAdapter);
				}

				break;
			case R.id.search_tab_artist:
				if (artistAdapter == null || artistAdapter.getCount() == 0) {
					showDefaultEmplyListView();
				} else {
					helpLayout.setVisibility(View.GONE);
					listView.setAdapter(artistAdapter);
				}

				break;
			case R.id.search_tab_playlist:
				if (playlistAdapter == null || playlistAdapter.getCount() == 0) {
					showDefaultEmplyListView();
				} else {
					helpLayout.setVisibility(View.GONE);
					listView.setAdapter(playlistAdapter);
				}

				break;
			case R.id.search_tab_videos:
				if (videoAdapter == null || videoAdapter.getCount() == 0) {
					showDefaultEmplyListView();
				} else {
					helpLayout.setVisibility(View.GONE);
					listView.setAdapter(videoAdapter);
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

	private void setListAdapter(final List<Object> objects) {
		switch (tabs.getCheckedRadioButtonId()) {
		case R.id.search_tab_songs:
			if (resultFound > 0) {
				if (songListAdapter == null) {
					songFlag = SCROLLABLE_VALUE;
					songListAdapter = new SongListAdapter(SearchActivity.this, objects, false);
					listView.setAdapter(songListAdapter);

				} else {
					songListAdapter.notifyListObjectChanged(objects);
					if (resultFound == songListAdapter.getCount()) {
						songFlag = UNSCROLLABLE_VALUE;
					} else
						songFlag = SCROLLABLE_VALUE;
				}
			} else {
				songListAdapter = null;
				showEmptyListView();
				songFlag = SEARCHED_VALUE;
			}
			break;
		case R.id.search_tab_album:
			if (resultFound > 0) {
				if (albumAdapter == null) {
					albumFlag = SCROLLABLE_VALUE;
					albumAdapter = new FeaturedListAdapter(SearchActivity.this, objects, false);
					listView.setAdapter(albumAdapter);
				} else {
					albumAdapter.notifyListObjectChanged(objects);
					if (resultFound == albumAdapter.getCount()) {
						albumFlag = UNSCROLLABLE_VALUE;

					} else
						albumFlag = SCROLLABLE_VALUE;
				}
			} else {
				albumAdapter = null;
				// listView.setAdapter(null);
				// helpLayout.setVisibility(View.VISIBLE);
				showEmptyListView();
				albumFlag = SEARCHED_VALUE;

			}
			break;
		case R.id.search_tab_playlist:
			if (resultFound > 0) {
				if (playlistAdapter == null) {
					playlistFlag = SCROLLABLE_VALUE;
					playlistAdapter = new FeaturePlaylistAdapter(SearchActivity.this, objects, false);
					listView.setAdapter(playlistAdapter);
				} else {
					playlistAdapter.notifyListObjectChanged(objects);
					if (resultFound == playlistAdapter.getCount()) {
						playlistFlag = UNSCROLLABLE_VALUE;
					} else
						playlistFlag = SCROLLABLE_VALUE;
				}
			} else {
				playlistAdapter = null;
				// listView.setAdapter(null);
				// helpLayout.setVisibility(View.VISIBLE);
				showEmptyListView();
				playlistFlag = SEARCHED_VALUE;

			}

			break;
		case R.id.search_tab_videos:
			if (resultFound > 0) {
				if (videoAdapter == null) {
					videoFlag = SCROLLABLE_VALUE;
					videoAdapter = new FeaturedListAdapter(SearchActivity.this, objects, false);
					listView.setAdapter(videoAdapter);
				} else {
					videoAdapter.notifyListObjectChanged(objects);
					if (resultFound == videoAdapter.getCount()) {
						videoFlag = UNSCROLLABLE_VALUE;
					} else
						videoFlag = SCROLLABLE_VALUE;
				}
			} else {
				videoAdapter = null;
				// listView.setAdapter(null);
				// helpLayout.setVisibility(View.VISIBLE);
				showEmptyListView();
				videoFlag = SEARCHED_VALUE;

			}

			break;
		case R.id.search_tab_artist:
			if (resultFound > 0) {
				if (artistAdapter == null) {
					artistFlag = SCROLLABLE_VALUE;
					artistAdapter = new FeaturedListAdapter(SearchActivity.this, objects, false);
					listView.setAdapter(artistAdapter);
				} else {
					artistAdapter.notifyListObjectChanged(objects);
					if (resultFound == artistAdapter.getCount()) {
						artistFlag = UNSCROLLABLE_VALUE;

					} else
						artistFlag = SCROLLABLE_VALUE;
				}
			} else {
				artistAdapter = null;
				// listView.setAdapter(null);
				// helpLayout.setVisibility(View.VISIBLE);
				showEmptyListView();
				artistFlag = SEARCHED_VALUE;

			}

			break;
		default:
			break;
		}
	}

	private void showPrevious() {
		// topbarViewFlipper.setInAnimation(pushLeftIn);
		// topbarViewFlipper.setOutAnimation(pushLeftOut);
		// topbarViewFlipper.setFlipInterval(5000);
		// topbarViewFlipper.showPrevious();
	}

	private void showNext() {
		// topbarViewFlipper.setInAnimation(pushLeftIn);
		// topbarViewFlipper.setOutAnimation(pushLeftOut);
		// topbarViewFlipper.setFlipInterval(5000);
		// topbarViewFlipper.showNext();
	}

	private void showToast(String msg) {
		Toast.makeText(SearchActivity.this, msg, Toast.LENGTH_LONG).show();
	}

	private void showProgressBar() {

		// listView.setVisibility(ListView.GONE);

		if (spinningLayout.getVisibility() != View.VISIBLE) {
			spinningLayout.setVisibility(View.VISIBLE);
		}
	}

	private void hideProgressBar() {

		// listView.setVisibility(ListView.VISIBLE);

		if (spinningLayout.getVisibility() != View.GONE) {
			spinningLayout.setVisibility(View.GONE);
		}
	}

	private void hideSoftKeyboard() {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchEdittex.getWindowToken(), 0);
		listView.requestFocus();
	}

	private void showSoftKeyboard() {

		searchEdittex.requestFocus();
		InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboard.showSoftInput(searchEdittex, 0);
	}

	// -------------------------------------
	// AsyncTask to load search result list
	// -------------------------------------
	private class SearchTask extends AsyncTask<Integer, Integer, List<Object>> {
		@SuppressWarnings("unused")
		private String errorMsg = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			disableTabControls();
			errorMsg = "";
			hideSoftKeyboard();
			showProgressBar();
		}

		@Override
		protected List<Object> doInBackground(Integer... args) {
			// TODO Auto-generated method stub
			List<Object> objs = new ArrayList<Object>();
			if (!NetworkUtility.hasNetworkConnection())
				return objs;
			switch (tabs.getCheckedRadioButtonId()) {
			case R.id.search_tab_songs:
				if (songListAdapter == null) {
					JsonSong jos = JsonSong.search(keySearch, 0);
					if (jos.isSuccess()) {
						objs.addAll(jos.songs);
						resultFound = jos.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = jos.songs.size();
					} else
						errorMsg = jos.getErrorMessage();
				} else {
					JsonSong jos = JsonSong.search(keySearch, songListAdapter.getCount());
					if (jos.isSuccess()) {
						objs.addAll(jos.songs);
						resultFound = jos.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = songListAdapter.getCount();
					} else
						errorMsg = jos.getErrorMessage();
				}

				break;
			case R.id.search_tab_album:
				if (albumAdapter == null) {
					JsonAlbum jal = JsonAlbum.search(keySearch, 0);
					if (jal.isSuccess()) {
						objs.addAll(jal.albums);
						resultFound = jal.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = jal.albums.size();
					} else
						errorMsg = jal.getErrorMessage();
				} else {
					JsonAlbum jal = JsonAlbum.search(keySearch, albumAdapter.getCount());
					if (jal.isSuccess()) {
						objs.addAll(jal.albums);
						resultFound = jal.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = albumAdapter.getCount();
					} else
						errorMsg = jal.getErrorMessage();
				}

				break;
			case R.id.search_tab_playlist:
				if (playlistAdapter == null) {
					JsonPlaylist jpl = JsonPlaylist.search(keySearch, 0);
					if (jpl.isSuccess()) {
						objs.addAll(jpl.playlists);
						resultFound = jpl.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = jpl.playlists.size();
					} else
						errorMsg = jpl.getErrorMessage();
				} else {
					JsonPlaylist jpl = JsonPlaylist.search(keySearch, playlistAdapter.getCount());
					if (jpl.isSuccess()) {
						objs.addAll(jpl.playlists);
						resultFound = jpl.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = playlistAdapter.getCount();

					} else
						errorMsg = jpl.getErrorMessage();
				}

				break;
			case R.id.search_tab_artist:
				if (artistAdapter == null) {
					JsonArtist jar = JsonArtist.search(keySearch, 0);
					if (jar.isSuccess()) {
						objs.addAll(jar.artists);
						resultFound = jar.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = jar.artists.size();

					} else
						errorMsg = jar.getErrorMessage();
				} else {
					JsonArtist jar = JsonArtist.search(keySearch, artistAdapter.getCount());
					if (jar.isSuccess()) {
						objs.addAll(jar.artists);
						resultFound = jar.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = artistAdapter.getCount();
					} else
						errorMsg = jar.getErrorMessage();
				}
				break;
			case R.id.search_tab_videos:
				if (videoAdapter == null) {
					JsonVideo jpl = JsonVideo.search(keySearch, 0);
					if (jpl.isSuccess()) {
						objs.addAll(jpl.videos);
						resultFound = jpl.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = jpl.videos.size();
					} else
						errorMsg = jpl.getErrorMessage();
				} else {
					JsonVideo jpl = JsonVideo.search(keySearch, videoAdapter.getCount());
					if (jpl.isSuccess()) {
						objs.addAll(jpl.videos);
						resultFound = jpl.totalFound;
						// chacha's api do not return total result
						if (resultFound == 0)
							resultFound = videoAdapter.getCount();

					} else
						errorMsg = jpl.getErrorMessage();
				}

				break;

			default:
				break;
			}

			return objs;
		}

		@Override
		protected void onPostExecute(List<Object> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			helpLayout.setVisibility(View.GONE);
			hideProgressBar();
			enableTabControls();
			setListAdapter(result);
		}
	}

	@Override
	public void onCheckboxClickListener(View v) {
		// TODO Auto-generated method stub
		
	}

}
