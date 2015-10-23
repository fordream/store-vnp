package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Random;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonListenUrl;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.MediaService;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.player.OnMusicPlayerListener;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.ConvertFormat;
import vn.com.vega.music.view.adapter.NowPlayingSongListAdapter;
import vn.com.vega.music.view.gui.ContextMenuEx;
import vn.com.vega.music.view.gui.SimpleGestureFilter;
import vn.com.vega.music.view.gui.SimpleGestureFilter.SimpleGestureListener;
import vn.com.vega.music.view.gui.ViewFlipperChangeAnimation;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.commonsware.cwac.tlv.TouchListView;

public class NowPlayingActivity extends Activity implements SimpleGestureListener {
	private static final int TYPE_CURRENT = 0;
	private static final int TYPE_NEXT = 1;
	private static final int TYPE_PREVIOUS = 2;
	private static final int TYPE_RANDOM = 3;

	private static final int CONTROL_BUTTON_REPEAT = 0;
	private static final int CONTROL_BUTTON_PLAY = 1;
	private static final int CONTROL_BUTTON_SHUFFLE = 2;

	private static final int MODE_REPEAT_OFF = 0;
	private static final int MODE_REPEAT_ONE = 1;
	private static final int MODE_REPEAT_ALL = 2;

	private static final int MODE_SHUFFLE_OFF = 0;
	private static final int MODE_SHUFFLE_ON = 1;

	private static final int DEL_LIST_SUCCESS = 0;
	private static final int DEL_LIST_FAILED = 1;

	private MusicPlayer mMusicPlayer = null;
	private TouchListView mListView;
	private Button mListViewBtn, mInfoBtn, mBackBtn;
	private ImageButton mRepeatBtn, mShuffleBtn, mNextBtn, mPreviousBtn, mHideBtn/*, mListViewBtn*/, mPlayPauseBtn/*, mInfoBtn*/;
	private ImageView mSongThumbnailPrev, mSongThumbnailNext;
	private ImageView mSongThumbnailCurrent;
	private TextView mTitleSong, mTitleArtist/*, mSongTitle, mSongArtistAndAlbum*/, mSongDuration, mSongElapsedTime/*, mSongCountLabel*/;
	private Button mLeftButton, mCenterButton, mRightButton;
	private RelativeLayout mRelayLayoutBottomBar;
	private NowPlayingSongListAdapter mListAdapter;
	private SeekBar mSeekBar;
	private int mCurrentPosOfSeek;
	private ProgressDialog mLoadSongDialog;
	private SimpleGestureFilter mGestureDetector;
	private ViewFlipper mFlipperSong, mFlipperControlList, mFlipperInfo;
	private ListView mInfoListView;
	private Context mContext;
	private Activity mActivity;
	private static int mModeRepeat;
	private static int mModeShuffle;
	private ImageLoader mImageLoader;
	private Uri mSongUri;
	private MyOnMusicPlayerListener myOnMusicPlayerListener;
	private boolean result;
	private boolean isLoadingData = false;
	private int randomSongIndex;
	private DataStore dataStore = null;
	private boolean isEditingFlag = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Setup Window view
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_nowplaying);
		mContext = this;
		mActivity = this;
		mImageLoader = ImageLoader.getInstance(mContext);
		dataStore = DataStore.getInstance();

		_generateRandomSongIndex();

		// Draw View
		_initializingComponentView();

		// Create media player and play
		_createMusicPlayer();

		if (NowPlayingList.getPlayFlag() == true) {
			NowPlayingList.setPlayFlag(false);

			_initMusicPlayer();

			_playSong(TYPE_CURRENT);

		} else {
			_updateView();
		}
	

		/* Register exception handler. We will remove it when testing finished */
		// ExceptionHandler.register(this);
		overridePendingTransition(
				R.anim.slide_bottom_to_top, R.anim.hold);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mLoadSongDialog.dismiss();
			switch (msg.what) {
			case DEL_LIST_SUCCESS:
				// Back to home screen
				onBackPressed();
				break;

			case DEL_LIST_FAILED:
				Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_clear_playlist_error), Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};	

	@Override
	protected void onResume() {
		super.onResume();
		if (mListAdapter != null)
			mListAdapter.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mListAdapter != null)
			mListAdapter.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mMusicPlayer.setOnMusicPlayerListener(null);
	}

	private void _initializingComponentView() {
		mTitleSong = (TextView) findViewById(R.id.title_song_txt);
		mTitleArtist = (TextView) findViewById(R.id.title_artist_txt);
		mTitleSong.setText("");
		mTitleArtist.setText("");
		mShuffleBtn = (ImageButton) findViewById(R.id.now_playing_shuffle_btn);
		mRepeatBtn = (ImageButton) findViewById(R.id.now_playing_repeat_btn);
		mNextBtn = (ImageButton) findViewById(R.id.now_playing_next_btn);
		mPreviousBtn = (ImageButton) findViewById(R.id.now_playing_previous_btn);
		mPlayPauseBtn = (ImageButton) findViewById(R.id.now_playing_play_pause_btn);
		mListViewBtn = (Button) findViewById(R.id.now_playing_button_list);
		mInfoBtn = (Button) findViewById(R.id.now_playing_button_info);
		mBackBtn = (Button) findViewById(R.id.back_btn);
		//mSongTitle = (TextView) findViewById(R.id.now_playing_song_title);
		//mSongArtistAndAlbum = (TextView) findViewById(R.id.now_playing_song_artist_and_album);
		//mSongCountLabel = (TextView) findViewById(R.id.now_playing_song_count);
		mSongElapsedTime = (TextView) findViewById(R.id.now_playing_elapsed_time);
		mSongDuration = (TextView) findViewById(R.id.now_playing_duration);
		mSeekBar = (SeekBar) findViewById(R.id.now_playing_seekbar);
		//mHideBtn = (ImageButton) findViewById(R.id.now_playing_button_hide);
		mFlipperSong = (ViewFlipper) findViewById(R.id.flipper_song);
		mFlipperControlList = (ViewFlipper) findViewById(R.id.flipper_control_list);
		mFlipperInfo = (ViewFlipper) findViewById(R.id.flipper_info);
		mInfoListView = (ListView) findViewById(R.id.info_listview);
		mGestureDetector = new SimpleGestureFilter(mFlipperSong, this);
		mSongThumbnailCurrent = (ImageView) findViewById(R.id.now_playing_song_thumbnail_current);
		mSongThumbnailPrev = (ImageView) findViewById(R.id.now_playing_song_thumbnail_previous);
		mSongThumbnailNext = (ImageView) findViewById(R.id.now_playing_song_thumbnail_next);
		mLeftButton = (Button) findViewById(R.id.nowplaying_left_playlist_btn);
		mCenterButton = (Button) findViewById(R.id.nowplaying_center_playlist_btn);
		mRightButton = (Button) findViewById(R.id.nowplaying_right_playlist_btn);
		mRelayLayoutBottomBar = (RelativeLayout) findViewById(R.id.nowplaying_bottom_bar);
		if (NowPlayingList.getListType() == NowPlayingList.TYPE_LOCAL) {
			mRelayLayoutBottomBar.setVisibility(View.GONE);
		}
		setOnClick(mShuffleBtn);
		setOnClick(mPreviousBtn);
		setOnClick(mPlayPauseBtn);
		setOnClick(mNextBtn);
		setOnClick(mSeekBar);
		setOnClick(mRepeatBtn);
		setOnClick(mListViewBtn);
		//setOnClick(mHideBtn);
		setOnClick(mInfoBtn);
		setOnClick(mBackBtn);
		setOnClick(mLeftButton);
		setOnClick(mCenterButton);
		setOnClick(mRightButton);
		//setOnLongClickListener(mFlipperControlList);

		// Setup ListView
		mListView = (TouchListView) findViewById(R.id.now_playing_list_view_song);
		// View emptyView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		// mListView.addHeaderView(emptyView);
		// mListView.addFooterView(emptyView);

		mListAdapter = new NowPlayingSongListAdapter(this, mListView);

		mListView.setAdapter(mListAdapter);
		
		// on short click
		NowPlayingListOnItemClickListener _doSelectNowPlayingSongItem = new NowPlayingListOnItemClickListener();
		mListView.setOnItemClickListener(_doSelectNowPlayingSongItem);
		
		// on long click
		registerForContextMenu(mListView);

		// Initializing
		_updateTitle();

	}

	private boolean _playSong(int _type) {
		if (_type == TYPE_NEXT || _type == TYPE_PREVIOUS) {
			if (NowPlayingList.getCount() <= 1) {
				return false;
			}
		}
		
		result = true;
		// Stop current Song
		mMusicPlayer.reset();

		switch (_type) {
		case TYPE_CURRENT:
			break;
		case TYPE_NEXT:			
			// set next song for list
			if (mModeShuffle == MODE_SHUFFLE_ON) {
				_updatePlayingSongIndex(TYPE_RANDOM);
			} else {
				_updatePlayingSongIndex(TYPE_NEXT);
			}
			mFlipperSong.setInAnimation(ViewFlipperChangeAnimation.inFromRightAnimation());
			mFlipperSong.setOutAnimation(ViewFlipperChangeAnimation.outToLeftAnimation());
			mFlipperSong.showNext();
			break;
		case TYPE_PREVIOUS:
			// set next song for list
			if (mModeShuffle == MODE_SHUFFLE_ON) {
				_updatePlayingSongIndex(TYPE_RANDOM);
			} else {
				_updatePlayingSongIndex(TYPE_PREVIOUS);
			}
			mFlipperSong.setInAnimation(ViewFlipperChangeAnimation.inFromLeftAnimation());
			mFlipperSong.setOutAnimation(ViewFlipperChangeAnimation.outToRightAnimation());
			mFlipperSong.showPrevious();
			break;
		default:
			break;
		}

		// Update title first
		_updateTitle();

		if (!_songIsAvailableToPlayInOffline()) {
			_showErrorPlayOfflineDialog();
			_updateView();
			return false;
		}
		// LOADING DATA ...
		if (NowPlayingList.getListType() == NowPlayingList.TYPE_NORMAL) {
			mLoadSongDialog = ProgressDialog.show(this, "", getString(R.string.nowplaying_loading_data), true);
		}
		new Thread() {
			@Override
			public void run() {

				// LOADING DATA - BEGIN ----
				// Load path of song
				result = true;
				mSongUri = null;
				boolean _result = _loadSongUri();

				// In case of the path is invalid, return error
				if (_result == false) {
					result = false;
				}
				// LOADING DATA - END -----
				// Play Media
				if (result == true) {
					mMusicPlayer.playNextSong(mSongUri);
				}
				// Update View
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						_updateView();
						if (NowPlayingList.getListType() == NowPlayingList.TYPE_NORMAL) {
							mLoadSongDialog.dismiss();
						}
						if (result == false) {
							_loadUriError();
						}
					}
				});

			}
		}.start();

		return result;
	}

	private boolean _loadSongUri() {
		isLoadingData = true;
		String _path = null;
		boolean _result = true;
		do {
			int _playingSongIndex = NowPlayingList.getSongCurrentIndex();
			Song _song = NowPlayingList.getSongAtIndex(_playingSongIndex);

			if (_song == null) {
				_result = false;
				break;
			}

			if (_song.isLocalSong()) {
				_path = _song.cached_file_path;
			} else {
				if (_song.isAvailableLocally()) {
					// Get stored song path
					_path = _song.cached_file_path;
				} else {
					// Get song path from server
					String audioProfileId = dataStore.getConfig(Const.MUSIC_QUALITY_ID);
					JsonListenUrl _jresult = JsonListenUrl.loadListenUrl(_song.id, audioProfileId);
					if (_jresult.getErrorCode() == 0) {
						_path = _jresult.listenUrl;
					} else {
						_result = false;
						break;
					}
				}
			}
			if (_path != null) {
				mSongUri = Uri.parse(_path);
			} else {
				_result = false;
			}
		} while (false);

		isLoadingData = false;
		return _result;
	}

	private void _createMusicPlayer() {
		MusicPlayer _mp = MusicPlayer.getInstance();
		// Create MusicPlayer and play song
		if (_mp == null) {
			mMusicPlayer = new MusicPlayer(mContext.getApplicationContext());
		} else
			mMusicPlayer = _mp;

		mMusicPlayer.setOnMusicPlayerListener(null);

		myOnMusicPlayerListener = new MyOnMusicPlayerListener();

		mMusicPlayer.setOnMusicPlayerListener(myOnMusicPlayerListener);
	}

	private void _initMusicPlayer() {
		if (mMusicPlayer != null) {
			mMusicPlayer.reset();
		}
	}

	private void _generateRandomSongIndex() {
		int _totalSongs = NowPlayingList.getCount();
		int _playingSongIndex = NowPlayingList.getSongCurrentIndex();
		if (_totalSongs > 1) {
			Random _rd = new Random();
			randomSongIndex = _playingSongIndex;
			while (randomSongIndex == _playingSongIndex) {
				randomSongIndex = _rd.nextInt(_totalSongs);
			}
			;
		}
	}

	private void _updatePlayingSongIndex(int type) {
		int _maxIndex = NowPlayingList.getCount() - 1;
		int _playingSongIndex = NowPlayingList.getSongCurrentIndex();
		switch (type) {
		case TYPE_NEXT:
			if (_playingSongIndex == _maxIndex)
				_playingSongIndex = 0;
			else
				_playingSongIndex += 1;
			break;
		case TYPE_PREVIOUS:
			if (_playingSongIndex == 0)
				_playingSongIndex = _maxIndex;
			else
				_playingSongIndex -= 1;
			break;
		case TYPE_RANDOM:
			_playingSongIndex = randomSongIndex;
			break;
		default:
			break;
		}

		// Update
		NowPlayingList.setSongCurrentIndex(_playingSongIndex);

		// Update title song in notification status
		int currentIndex = NowPlayingList.getSongCurrentIndex();
		Song _song = NowPlayingList.getSongAtIndex(currentIndex);
		if(_song != null)
			MediaService.mTitleofSong = _song.title;

		_generateRandomSongIndex();
	}

	private void _updateTitle() {

		int currentIndex = NowPlayingList.getSongCurrentIndex();
		Song _song = NowPlayingList.getSongAtIndex(currentIndex);
		if(_song != null){
			MediaService.mTitleofSong = _song.title;
			if (_song.artist_name.equals("")) {
				mTitleSong.setText(_song.title);
				mTitleArtist.setVisibility(View.GONE);
			} else {
				mTitleSong.setText(_song.title);
				mTitleArtist.setText(_song.artist_name);
				mTitleArtist.setVisibility(View.VISIBLE);
			}
			/*
			if (_song.album_title.equals("")) {
				mSongArtistAndAlbum.setText(_song.artist_name);
			} else {
				mSongArtistAndAlbum.setText(_song.album_title + " - " + _song.artist_name);
			}			
			mSongTitle.setText(_song.title);
			mSongCountLabel.setText((currentIndex + 1) + "/" + NowPlayingList.getCount());
			*/
		}
	}

	private void _updateThumbnail() {
		int currentIndex = NowPlayingList.getSongCurrentIndex();
		Song _song = NowPlayingList.getSongAtIndex(currentIndex);

		ImageView curr = null; // current displayed ImageView
		ImageView next = null; // next displayed ImageView
		ImageView prev = null; // previous displayed ImageView
		int currIndex = mFlipperSong.getDisplayedChild();
		switch (currIndex) {
		case 0:
			curr = mSongThumbnailCurrent;
			next = mSongThumbnailNext;
			prev = mSongThumbnailPrev;
			break;

		case 1:
			curr = mSongThumbnailNext;
			next = mSongThumbnailPrev;
			prev = mSongThumbnailCurrent;
			break;

		case 2:
			curr = mSongThumbnailPrev;
			next = mSongThumbnailCurrent;
			prev = mSongThumbnailNext;
			break;
		}
		if(_song != null)
			mImageLoader.DisplayImage(_song.imageUrl, this, curr, R.drawable.ic_song_default);
		if (currentIndex > 0) {
			Song _songPrev = null;
			if (mModeShuffle == MODE_SHUFFLE_ON) {
				_songPrev = NowPlayingList.getSongAtIndex(randomSongIndex);
			} else {
				_songPrev = NowPlayingList.getSongAtIndex(currentIndex - 1);
			}
			if(_songPrev != null)
				mImageLoader.DisplayImage(_songPrev.imageUrl, this, prev, R.drawable.ic_song_default);
		}
		if (currentIndex < NowPlayingList.getCount() - 1) {
			Song _songNext = null;
			if (mModeShuffle == MODE_SHUFFLE_ON) {
				_songNext = NowPlayingList.getSongAtIndex(randomSongIndex);
			} else {
				_songNext = NowPlayingList.getSongAtIndex(currentIndex + 1);
			}
			if(_songNext != null)
				mImageLoader.DisplayImage(_songNext.imageUrl, this, next, R.drawable.ic_song_default);
		}
	}

	private void _updateSeekBarProgress() {

		int _duration = mMusicPlayer.getDurationTime();
		mSeekBar.setMax(_duration);
		int _playedSeconds = mMusicPlayer.getPlayedSeconds();
		int _bufferedSeconds = mMusicPlayer.getBufferedSeconds();
		mSeekBar.setProgress(_playedSeconds);
		// mSeekBar.setSecondaryProgress(_bufferedSeconds);

		mSongElapsedTime.setText(ConvertFormat.intToStringTimeFormat(_playedSeconds));
		mSongDuration.setText(ConvertFormat.createHMFormatfromM(_duration * 1000));
	}

	private void _updateView() {

		// UPDATE TITLE
		_updateTitle();

		// UPDATE THUMBNAIL
		_updateThumbnail();

		// UPDATE SEEKBAR
		_updateSeekBarProgress();

		// UPDATE CONTROL BUTTON
		_drawControlButtons();

		// UPDATE LISTVIEW
		mListAdapter.notifyDataSetChanged();
	}

	private void _drawControlButtons() {
		// PLAY-PAUSE
		if (MusicPlayer.isMusicPlaying()) {
			mPlayPauseBtn.setSelected(true);
		} else {
			mPlayPauseBtn.setSelected(false);
		}
		// SHUFFLE
		if (mModeShuffle == MODE_SHUFFLE_OFF) {
			mShuffleBtn.setSelected(false);
		} else if (mModeShuffle == MODE_SHUFFLE_ON) {
			mShuffleBtn.setSelected(true);
		} else {

		}
		// REPEAT
		if (mModeRepeat == MODE_REPEAT_OFF) {
			mRepeatBtn.setSelected(false);
		} else if (mModeRepeat == MODE_REPEAT_ALL) {
			mRepeatBtn.setSelected(true);
		} else {

		}
//		if (mModeRepeat == MODE_REPEAT_OFF) {
//			mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat_off);
//		} else if (mModeRepeat == MODE_REPEAT_ONE) {
//			mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat);
//		} else if (mModeRepeat == MODE_REPEAT_ALL) {
//			mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat);
//		}
//		// NEXT
//		if (NowPlayingList.getCount() <= 1)
//			mNextBtn.setBackgroundResource(R.drawable.ic_next_off);
//		else
//			mNextBtn.setBackgroundResource(R.drawable.ic_next);
//		// PREVIOUS
//		if (NowPlayingList.getCount() <= 1)
//			mPreviousBtn.setBackgroundResource(R.drawable.ic_previous_off);
//		else
//			mPreviousBtn.setBackgroundResource(R.drawable.ic_previous);

	}

	private void _updateControlButtonState(int _buttonType) {

		switch (_buttonType) {
		case CONTROL_BUTTON_PLAY:
			if (MusicPlayer.isMusicPlaying()) {
				mPlayPauseBtn.setSelected(true);
			} else {
				mPlayPauseBtn.setSelected(false);
			}
			break;
		case CONTROL_BUTTON_SHUFFLE:

			if (mModeShuffle == MODE_SHUFFLE_OFF) {
				mShuffleBtn.setSelected(true);
				mModeShuffle = MODE_SHUFFLE_ON;
			} else if (mModeShuffle == MODE_SHUFFLE_ON) {
				mShuffleBtn.setSelected(false);
				mModeShuffle = MODE_SHUFFLE_OFF;
			} else {

			}
			_updateThumbnail();
			break;
		case CONTROL_BUTTON_REPEAT:
			if (mModeRepeat == MODE_SHUFFLE_OFF) {
				mRepeatBtn.setSelected(true);
				mModeRepeat = MODE_REPEAT_ALL;
			} else if (mModeRepeat == MODE_REPEAT_ALL) {
				mRepeatBtn.setSelected(false);
				mModeRepeat = MODE_REPEAT_OFF;
			} else {

			}
//			if (mModeRepeat == MODE_REPEAT_OFF) {
//				mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat);
//				mModeRepeat = MODE_REPEAT_ONE;
//			} else if (mModeRepeat == MODE_REPEAT_ONE) {
//				mModeRepeat = MODE_REPEAT_ALL;
//				mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat);
//			} else if (mModeRepeat == MODE_REPEAT_ALL) {
//				mModeRepeat = MODE_REPEAT_OFF;
//				mRepeatBtn.setBackgroundResource(R.drawable.ic_repeat_off);
//			}
			break;
		default:
			break;

		}
	}

	class MyOnMusicPlayerListener implements OnMusicPlayerListener {

		@Override
		public void OnSongIsFinishPlayed() {
			boolean _result = true;
			// reset music player
			mMusicPlayer.reset();
			_updateControlButtonState(CONTROL_BUTTON_PLAY);

			if (NowPlayingList.getCount() == 1) {
				switch (mModeRepeat) {
				case MODE_REPEAT_ALL:
				case MODE_REPEAT_ONE:
					// Replay
					mMusicPlayer.playNextSong(mSongUri);
					_updateView();
					break;
				case MODE_REPEAT_OFF:
					// Stop here
					break;
				default:
					break;
				}
			} else if (NowPlayingList.getCount() > 1) {
				switch (mModeRepeat) {
				case MODE_REPEAT_ALL:

					if (mModeShuffle == MODE_SHUFFLE_ON) {
						// Next to random song
						_updatePlayingSongIndex(TYPE_RANDOM);
					} else {
						// Next song
						_updatePlayingSongIndex(TYPE_NEXT);
					}
					// Check available to Play.ex: offline mode
					if (!_songIsAvailableToPlayInOffline()) {
						_showErrorPlayOfflineDialog();
						break;
					}
					_result = _loadSongUri();

					if (_result == false) {
						_loadUriError();
					} else {
						mMusicPlayer.playNextSong(mSongUri);
						// _updateView();
					}
					break;
				case MODE_REPEAT_ONE:
					// Replay
					mMusicPlayer.playNextSong(mSongUri);
					_updateView();
					break;
				case MODE_REPEAT_OFF:
					if (mModeShuffle == MODE_SHUFFLE_ON) {
						// Next to random song
						_updatePlayingSongIndex(TYPE_RANDOM);
						// Check available to Play.ex: offline mode
						if (!_songIsAvailableToPlayInOffline()) {
							_showErrorPlayOfflineDialog();
							break;
						}

						_result = _loadSongUri();

						if (_result == false) {
							_loadUriError();
						} else {
							mMusicPlayer.playNextSong(mSongUri);
							// _updateView();
						}
					} else if (mModeShuffle == MODE_SHUFFLE_OFF) {
						int _maxIndex = NowPlayingList.getCount() - 1;
						int _playingSongIndex = NowPlayingList.getSongCurrentIndex();
						if (_playingSongIndex == _maxIndex) {
							// is last song of list : Stop here

						} else {
							// is not last song of list : Next song
							_updatePlayingSongIndex(TYPE_NEXT);
							// Check available to Play.ex: offline mode
							if (!_songIsAvailableToPlayInOffline()) {
								_showErrorPlayOfflineDialog();
								break;
							}
							_result = _loadSongUri();

							if (_result == false) {
								_loadUriError();
							} else {
								mMusicPlayer.playNextSong(mSongUri);
							}
						}
					}
					break;
				default:
					break;

				}
			}
			_updateView();
		}

		@Override
		public void OnSongProgressSecondUpdate() {
			int _playedSeconds = mMusicPlayer.getPlayedSeconds();
			// Update view: seek bar progress
			mSeekBar.setProgress(_playedSeconds);

			mSongElapsedTime.setText(ConvertFormat.intToStringTimeFormat(_playedSeconds));

		}

		@Override
		public void OnSongBufferingUpdate() {
			// PLAYING SONG ONLINE - Update view: seek bar secondary
			// progress(buffering)
			// mSeekBar.setSecondaryProgress(mMusicPlayer.getBufferedSeconds());
		}
	}

	class NowPlayingListOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			if (isLoadingData == true) {
				return;
			}

			NowPlayingList.setSongCurrentIndex(position);
			// Update title song in notification status
			int currentIndex = NowPlayingList.getSongCurrentIndex();
			Song _song = NowPlayingList.getSongAtIndex(currentIndex);
			MediaService.mTitleofSong = _song.title;

			_playSong(TYPE_CURRENT);
		}
	}
	
	public void onBackButtonClick() {
		// close Playlist screen
		if (mFlipperControlList.getDisplayedChild() != 0) {
			mFlipperControlList.setInAnimation(ViewFlipperChangeAnimation.inFromLeftAnimation());
			mFlipperControlList.setOutAnimation(ViewFlipperChangeAnimation.outToRightAnimation());
			mFlipperControlList.showNext();
			return;
		}
		
		// close Info screen
		if (mFlipperInfo.getDisplayedChild() != 0) {
			mFlipperInfo.setInAnimation(ViewFlipperChangeAnimation.inFromLeftAnimation());
			mFlipperInfo.setOutAnimation(ViewFlipperChangeAnimation.outToRightAnimation());
			mFlipperInfo.showNext();
			return;
		}
		
		// close all
		onBackPressed();
		overridePendingTransition(R.anim.hold_with_alpha, R.anim.slide_top_to_bottom);
	}

	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (isLoadingData == true) {
					return;
				}
				switch (v.getId()) {
				case R.id.now_playing_previous_btn:
					_playSong(TYPE_PREVIOUS);
					break;

				case R.id.now_playing_play_pause_btn:
					if (MusicPlayer.isMusicPlaying()) {
						mMusicPlayer.pause();
						_updateControlButtonState(CONTROL_BUTTON_PLAY);
					} else {
						if (!_songIsAvailableToPlayInOffline()) {
							_showErrorPlayOfflineDialog();
							_updateView();
							break;
						}
						// in case of last song and our song auto stop song at
						// time finish. we will replay the song
						// if user press play button again
						if (mMusicPlayer.getDurationTime() == mMusicPlayer.getPlayedSeconds()) {
							_playSong(TYPE_CURRENT);

						} else {
							mMusicPlayer.start();
							_updateControlButtonState(CONTROL_BUTTON_PLAY);
						}
					}
					break;

				case R.id.now_playing_next_btn:
					_playSong(TYPE_NEXT);
					break;
				case R.id.now_playing_shuffle_btn:
					_updateControlButtonState(CONTROL_BUTTON_SHUFFLE);
					break;
				case R.id.now_playing_repeat_btn:
					_updateControlButtonState(CONTROL_BUTTON_REPEAT);
					break;
//				case R.id.now_playing_button_hide:
//					onBackPressed();
//					overridePendingTransition(R.anim.hold_with_alpha, R.anim.slide_top_to_bottom);
//					break;
				case R.id.now_playing_button_list:
					mListAdapter.notifyDataSetChanged();
					mFlipperControlList.setInAnimation(ViewFlipperChangeAnimation.inFromRightAnimation());
					mFlipperControlList.setOutAnimation(ViewFlipperChangeAnimation.outToLeftAnimation());
					mFlipperControlList.showNext();

					boolean _selected = mListViewBtn.isSelected();
					mListViewBtn.setSelected(!_selected);
					break;
				case R.id.now_playing_button_info:
					// popup
					Song _song = NowPlayingList.getSongAtIndex(NowPlayingList.getSongCurrentIndex());
					ContextMenuEx _contextMenuEx = new ContextMenuEx(false, false);
					_contextMenuEx.showOptionalDialog(_song, mContext, mActivity, Const.TYPE_NOW_PLAYING_STACK);
					
					// fipper
					// _contextMenuEx.setInfoListView(_song, mContext,
					// mInfoListView);
					// mFlipperInfo.setInAnimation(ViewFlipperChangeAnimation.inFromRightAnimation());
					// mFlipperInfo.setOutAnimation(ViewFlipperChangeAnimation.outToLeftAnimation());
					// mFlipperInfo.showNext();

					break;
				case R.id.back_btn:
					onBackButtonClick();
					break;
				case R.id.nowplaying_left_playlist_btn:
					leftButtonHandler();
					break;
				case R.id.nowplaying_center_playlist_btn:
					centerButtonHandler();
					break;
				case R.id.nowplaying_right_playlist_btn:
					rightButtonHandler();
					break;
					
				default:
					break;
				}
			}
		});

		if (v.getId() == R.id.now_playing_seekbar) {
			v.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
						mCurrentPosOfSeek = mSeekBar.getProgress();
						if (_checkCurrentSongIsOnline()) {
							// PLAYING SONG ONLINE (use buffer)
							int _bufferedSeconds = mMusicPlayer.getBufferedSeconds();
							if (mCurrentPosOfSeek > _bufferedSeconds) {
								mSeekBar.setProgress(_bufferedSeconds);
								mMusicPlayer.seekToTime(_bufferedSeconds);
							} else {
								mMusicPlayer.seekToTime(mCurrentPosOfSeek);
							}
						} else {
							// PLAYING SONG OFFLINE/LOCAL (buffer no need)
							mMusicPlayer.seekToTime(mCurrentPosOfSeek);
						}
						break;

					default:
						break;
					}
					return false;
				}
			});
		}

	}// end of setOnclick
	
	// when long-click on item of listview. This will show context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.now_playing_list_view_song) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Song song = NowPlayingList.getSongAtIndex(info.position);
			
			// show context menu
			ContextMenuEx _contextMenu = new ContextMenuEx(false, false);
			_contextMenu.showOptionalDialog(song, mContext, mActivity,
					Const.TYPE_NOW_PLAYING_STACK);
		}
	}
	
	public void deleteHandler(View v) {		
		// Song _song = (Song) v.getTag();
		int position = ((Integer) v.getTag()).intValue();
		Song _song = NowPlayingList.getSongAtIndex(position);
		Song _currentSongPlaying = NowPlayingList.getSongAtIndex(NowPlayingList.getSongCurrentIndex());
		if (_song.id == _currentSongPlaying.id) {
			// ERROR
			// In case of: remove playing song, error
			Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_remove_playing_song_error), Toast.LENGTH_SHORT).show();
			return;
		}

		Integer _statusSong = mListAdapter.hashSongProgress.get(_song.id);
		if (_statusSong == null) {
		} else if (_statusSong == NowPlayingSongListAdapter.WAITING_VALUE) {
			dataStore.removeSongFromSongDownloadPool(_song.id);
			mListAdapter.hashSongProgress.remove(_song.id);
		} else if (_statusSong >= NowPlayingSongListAdapter.CONNECTING_VALUE && _statusSong <= NowPlayingSongListAdapter.DOWNLOADED_VALUE) {
			// ERROR
			Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_remove_downloading_song_error), Toast.LENGTH_SHORT).show();
			return;
		}

		boolean result = NowPlayingList.removeSong(_song);
		if (result) {
			int current = NowPlayingList.getSongCurrentIndex();
			if (position < current)
				NowPlayingList.setSongCurrentIndex(current - 1);
			mListAdapter.iDeletingPosition = -1;
			if (NowPlayingList.isEmpty()) {
				// In case of: now playing is empty. Finish!
				// Back to home screen
				onBackPressed();
				return;
			}
			// SUCCESS
			// Refresh list view			
			mListAdapter.notifyDataSetChanged();
			Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_remove_song_success), Toast.LENGTH_SHORT).show();
			// mSongCountLabel.setText((NowPlayingList.getSongCurrentIndex() + 1) + "/" + NowPlayingList.getCount());
		} else {
			// ERROR
			// In case of: remove error
			Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_remove_song_error), Toast.LENGTH_SHORT).show();
		}
	}

	public void thumbnailClickHandler(View v) {
		if (NowPlayingList.getListType() == NowPlayingList.TYPE_LOCAL || isLoadingData == true) {
			return;
		}
		if (isEditingFlag) {
			deleteHandler(v);
			
			/* 
			 * disabled second Delete button
			 * 
			int position = ((Integer) v.getTag()).intValue();
			if (mListAdapter.iDeletingPosition == position) {
				mListAdapter.iDeletingPosition = -1;
			} else {
				mListAdapter.iDeletingPosition = position;
			}
			mListAdapter.notifyDataSetChanged();
			*/
		} else {

			// Song _song = (Song) v.getTag();
			int position = ((Integer) v.getTag()).intValue();
			Song _song = NowPlayingList.getSongAtIndex(position);
			mListAdapter.download(_song);
			// if (_song.isWaitToDownload()) {
			// dataStore.removeSongFromSongDownloadPool(_song.id);
			// // to show normal thumbnail
			// mListAdapter.hashSongProgress.remove(_song.id);
			// // refresh listview
			// mListAdapter.notifyDataSetChanged();
			// } else {
			// if (DownloadManager.isRunning()) {
			// if (dataStore.addSongToSongDownloadPool(_song.id)) {
			// // indicate this song is waiting to download
			// mListAdapter.hashSongProgress.put(_song.id,
			// NowPlayingSongListAdapter.WAITING_VALUE);
			// // refresh listview
			// mListAdapter.notifyDataSetChanged();
			// }
			// } else {
			// // to show connecting message
			// mListAdapter.hashSongProgress.put(_song.id,
			// NowPlayingSongListAdapter.CONNECTING_VALUE);
			// // refresh listview
			// mListAdapter.notifyDataSetChanged();
			// // for simple i should clear all download pool before start
			// dataStore.clearSongDownloadPool();
			// dataStore.clearPlaylistDownloadPool();
			// // add song to song download pool
			// dataStore.addSongToSongDownloadPool(_song.id);
			// // start download thread
			// DownloadManager.startDownload();
			// }
			// }
		}
	}

	private void setOnLongClickListener(View v) {
		v.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (isLoadingData == true) {
					return false;
				}
				switch (v.getId()) {
				case R.id.flipper_control_list:
					// open Song detail info pop-up
					Song _song = NowPlayingList.getSongAtIndex(NowPlayingList.getSongCurrentIndex());
					ContextMenuEx _contextMenuEx = new ContextMenuEx(false, false);
					_contextMenuEx.showOptionalDialog(_song, mContext, mActivity, Const.TYPE_NOW_PLAYING_STACK);
					break;

				default:
					return false;
				}
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackButtonClick();
			return true;
			// startActivity(new Intent(MusicPlayer.this, TabHolder.class));
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.mGestureDetector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		if (isLoadingData == true) {
			return;
		}

		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT:
			// Move to previous song
			_playSong(TYPE_PREVIOUS);
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			// Move to next song
			_playSong(TYPE_NEXT);
			break;
		default:
			break;
		}
	}
	
	// onClick: clear Playlist
	public void leftButtonHandler() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.nowplaying_clear_playlist_confirm).setCancelable(false).setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// stop playing
				mMusicPlayer.pause();
				
				// clear playlist
				mLoadSongDialog = new ProgressDialog(mContext);
				mLoadSongDialog.setMessage(getString(R.string.nowplayling_clear_list));
				mLoadSongDialog.setCancelable(false);
				mLoadSongDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						boolean result = NowPlayingList.clear();
						if (result) {
							mHandler.sendEmptyMessage(DEL_LIST_SUCCESS);

						} else {
							mHandler.sendEmptyMessage(DEL_LIST_FAILED);
						}
					}
				}).start();
			}
		}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// onClick: save Playlist
	public void centerButtonHandler() {
		// SAVE PLAYLIST
		ArrayList<Song> songList = NowPlayingList.getNormalSongList();
		if (songList.get(0).isLocalSong()) {
			// not support save Local playlist
			AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
			alertDialog.setTitle(getText(R.string.dialog_notification));
			alertDialog.setMessage(getText(R.string.nowplaying_save_playlist_not_support_local_playlist));
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			alertDialog.show();
		} else {
			// pass to MyPlaylistSelectorActivity
			Bundle bundle = new Bundle();
			Session.putSharedObject(bundle, NowPlayingActivity.this, songList);
			Intent i = new Intent(mContext, MyPlaylistSelectorActivity.class);
			i.putExtras(bundle);
			startActivity(i);
		}
	}

	private void _loadUriError() {
		Toast.makeText(getApplicationContext(), getString(R.string.nowplaying_song_path_error_msg), Toast.LENGTH_SHORT).show();
	}

	// onClick: edit playlist
	public void rightButtonHandler() {
		if (isEditingFlag) {
			isEditingFlag = false;
			mLeftButton.setVisibility(View.VISIBLE);
			mCenterButton.setVisibility(View.VISIBLE);
			mRightButton.setBackgroundResource(R.drawable.selector_button_nowplaying_list_edit);
			mListAdapter.mIsEditing = false;
			mListAdapter.iDeletingPosition = -1;
			mListAdapter.notifyDataSetChanged();
		} else {
			isEditingFlag = true;
			mLeftButton.setVisibility(View.INVISIBLE);
			mCenterButton.setVisibility(View.INVISIBLE);
			mRightButton.setBackgroundResource(R.drawable.selector_button_nowplaying_list_save);
			mListAdapter.mIsEditing = true;
			mListAdapter.notifyDataSetChanged();
		}

	}

	public void editPlaylist(View view) {

	}

	public boolean _checkCurrentSongIsOnline() {
		Song _song = NowPlayingList.getSongAtIndex(NowPlayingList.getSongCurrentIndex());
		if (_song.isLocalSong() == true || _song.isAvailableLocally() == true) {
			return false;
		}
		return true;
	}

	private void _showErrorPlayOfflineDialog() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.nowplaying_offline_song_is_unavailable).setCancelable(false).setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
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

	
	private boolean _songIsAvailableToPlayInOffline() {
		boolean result = true;
		DataStore datastore = DataStore.getInstance();
		if (datastore.isInOfflineMode() && (NowPlayingList.getListType() == NowPlayingList.TYPE_NORMAL)) {
			result = NowPlayingList.getSongAtIndex(NowPlayingList.getSongCurrentIndex()).isAvailableLocally();
		}
		return result;
	}
}
