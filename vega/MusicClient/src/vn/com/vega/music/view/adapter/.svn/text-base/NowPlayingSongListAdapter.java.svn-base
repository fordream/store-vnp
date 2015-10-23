package vn.com.vega.music.view.adapter;

import java.util.Hashtable;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.DownloadStatusListener;
import vn.com.vega.music.downloadmanager.EventNotifier;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.MyPlaylistActivity;
import vn.com.vega.music.view.NowPlayingActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.tlv.TouchListView;

public class NowPlayingSongListAdapter extends BaseAdapter implements DownloadStatusListener {
	public static final int CONNECTING_VALUE = 0;
	public static final int DOWNLOADED_VALUE = 100;
	public static final int WAITING_VALUE = 101;
	protected static final String LOG_TAG = Const.LOG_PREF + NowPlayingSongListAdapter.class.getSimpleName();

	private LayoutInflater mInflate;
	private Context mContext;
	private ListView mListView;
	protected Integer mFirstVisibleItem;
	protected Integer mLastVisibleItem;
	private DataStore mDataStore = null;
	private boolean mIsScrolling = false;
	public boolean mIsEditing = false;
	public int iDeletingPosition = -1;

	public Hashtable<Integer, Integer> hashSongProgress = new Hashtable<Integer, Integer>();
	protected Hashtable<Integer, Integer> hashSongPosition = new Hashtable<Integer, Integer>();

	public NowPlayingSongListAdapter(Context context, ListView _lv) {
		mInflate = LayoutInflater.from(context);
		mContext = context;
		mListView = _lv;
		mListView.setOnScrollListener(new OnScrollListener() {
			public void onScroll(AbsListView view, int _mFirstVisibleItem, int visibleItemCount, int totalItemCount) {
				mFirstVisibleItem = _mFirstVisibleItem;
				mLastVisibleItem = _mFirstVisibleItem + visibleItemCount;
			}

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == 0) {
					mIsScrolling = false;
				} else
					mIsScrolling = true;
			}
		});
		if (mListView instanceof TouchListView) {
			TouchListView tlv = (TouchListView)mListView;
			tlv.setDropListener(onDrop);
			tlv.setRemoveListener(onRemove);
		}

		mDataStore = DataStore.getInstance();

		// Load song which is waiting down load
		List<Song> waitingSongs = mDataStore.getSongDownloadPool();
		for (Song song : waitingSongs) {
			hashSongProgress.put(song.id, WAITING_VALUE);
		}
	}
	
	private TouchListView.DropListener onDrop = new TouchListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from == to)
				return;
			
			if (NowPlayingList.reorderSong(from, to))
				notifyDataSetChanged();
			else
				Toast.makeText(mContext, mContext.getString(R.string.nowplaying_reorder_song_error), Toast.LENGTH_SHORT).show();
		}
	};
	
	private TouchListView.RemoveListener onRemove = new TouchListView.RemoveListener() {
		@Override
		public void remove(int which) {
			if (NowPlayingList.removeSong(NowPlayingList.getSongAtIndex(which)))
				notifyDataSetChanged();
			else
				Toast.makeText(mContext, mContext.getString(R.string.nowplaying_remove_song_error), Toast.LENGTH_SHORT).show();
		}
	};

	public void onPause() {
		DownloadManager.removeDownloadStatusListener(LOG_TAG);
	}

	public void onResume() {
		DownloadManager.addDownloadStatusListener(LOG_TAG, this);
		notifyDataSetChanged();
	}

	protected void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.offline_mode_msg).setCancelable(false).setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				mDataStore.setOfflineModeStatus(false);
			}
		}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void download(Song song) {
		if (mDataStore.isInOfflineMode())
			askGoToOnline();
		else {
			DownloadManager.removeDownloadStatusListener(LOG_TAG);

			DownloadManager.addDownloadStatusListener(LOG_TAG, this);

			mDataStore.addSong(song);

			// song is waiting to download, remove it
			if (song.isWaitToDownload()) {
				mDataStore.removeSongFromSongDownloadPool(song.id);
				// to show normal thumbnail
				hashSongProgress.remove(song.id);
				// refresh listview
				notifyDataSetChanged();
				// song is normal state
			} else {
				if (DownloadManager.isRunning()) {
					if (mDataStore.addSongToSongDownloadPool(song.id)) {
						// indicate this song is waiting to download
						hashSongProgress.put(song.id, WAITING_VALUE);
						// refresh listview
						notifyDataSetChanged();
					}
				} else {
					// to show connecting message
					hashSongProgress.put(song.id, CONNECTING_VALUE);
					// refresh listview
					notifyDataSetChanged();
					// for simple i should clear all download pool before start
					mDataStore.clearSongDownloadPool();
					mDataStore.clearPlaylistDownloadPool();
					// add song to song download pool
					mDataStore.addSongToSongDownloadPool(song.id);
					// start download thread
					DownloadManager.startDownload();
				}
			}
		}
	}

	@Override
	public int getCount() {
		return NowPlayingList.getCount();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
//		View mHeader;
//		View mContent;
//		View mFooter;
		TextView mTittleText;
		TextView mSecondTxt;
		ImageView mPlayingSongImage;
		ImageView mThumbnailImg;
		ProgressBar mDownloadBar;
		ImageView mDetailImg;
		ImageView mGrabberImg;
		ImageView mDeleteImg;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder _itemView;

		if (convertView == null) {
			convertView = mInflate.inflate(R.layout.view_listview_row_nowplaying, null);
//			convertView = mInflate.inflate(R.layout.view_listview_row_nowplaying_with_header_footer, null);

			_itemView = new ViewHolder();
//			_itemView.mHeader = convertView.findViewById(R.id.row_header);
//			_itemView.mContent = convertView.findViewById(R.id.row_content);
//			_itemView.mFooter = convertView.findViewById(R.id.row_footer);
			_itemView.mTittleText = (TextView) convertView.findViewById(R.id.list_song_item_song_title);
			_itemView.mSecondTxt = (TextView) convertView.findViewById(R.id.list_song_item_song_artist);
			_itemView.mPlayingSongImage = (ImageView) convertView.findViewById(R.id.list_song_item_is_playing);
			_itemView.mThumbnailImg = (ImageView) convertView.findViewById(R.id.list_song_item_cached_img);
			_itemView.mDownloadBar = (ProgressBar) convertView.findViewById(R.id.list_song_item_download_bar);
			_itemView.mDetailImg = (ImageView) convertView.findViewById(R.id.list_song_item_detail);
			_itemView.mGrabberImg = (ImageView) convertView.findViewById(R.id.list_song_item_grabber);
			_itemView.mDeleteImg = (ImageView) convertView.findViewById(R.id.list_song_item_delete);
			setOnClick(_itemView.mThumbnailImg);
			setOnClick(_itemView.mDeleteImg);
			convertView.setTag(_itemView);
		} else {
			_itemView = (ViewHolder) convertView.getTag();
		}
		
		if (position == 0)
			convertView.setBackgroundResource(R.drawable.selector_listview_row_first);
		else if (position == (getCount() - 1))
			convertView.setBackgroundResource(R.drawable.selector_listview_row_last);
		else
			convertView.setBackgroundResource(R.drawable.selector_listview_row_middle);
		
//		if (position == 0) {
//			_itemView.mContent.setBackgroundResource(R.drawable.selector_listview_row_first);
//			_itemView.mHeader.setVisibility(View.VISIBLE);
//			_itemView.mFooter.setVisibility(View.GONE);
//		} else if (position == (getCount() - 1)) {
//			_itemView.mContent.setBackgroundResource(R.drawable.selector_listview_row_last);
//			_itemView.mHeader.setVisibility(View.GONE);
//			_itemView.mFooter.setVisibility(View.VISIBLE);
//		} else {
//			_itemView.mContent.setBackgroundResource(R.drawable.selector_listview_row_middle);
//			_itemView.mHeader.setVisibility(View.GONE);
//			_itemView.mFooter.setVisibility(View.GONE);
//		}

		Song _song = NowPlayingList.getSongAtIndex(position);
		if(_song == null){
			return convertView;
		}
		_itemView.mTittleText.setText(_song.title);
		_itemView.mSecondTxt.setText(_song.artist_name);
		_itemView.mDownloadBar.setVisibility(ProgressBar.GONE);

		if (_song.isLocalSong()) {
			_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_default_music);

		} else {
			// Pass song to Activity
			_itemView.mThumbnailImg.setTag(new Integer(position));
			_itemView.mDeleteImg.setTag(new Integer(position));			
			hashSongPosition.put(_song.id, position);

			if (_song.isAvailableLocally()) {
				_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_downloaded);
			} else {
				Integer _status = hashSongProgress.get(_song.id);

				if (_status == null) {
					// IDLE STATUS
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_not_downloaded);
					_itemView.mThumbnailImg.setEnabled(true);
					_itemView.mDownloadBar.setVisibility(ProgressBar.GONE);
					_itemView.mSecondTxt.setVisibility(TextView.VISIBLE);
					_itemView.mSecondTxt.setText(_song.artist_name);
				} else if (_status == CONNECTING_VALUE) {
					// CONNECTING STATUS
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_downloading);
					_itemView.mThumbnailImg.setEnabled(false);
					_itemView.mSecondTxt.setVisibility(TextView.VISIBLE);
					_itemView.mSecondTxt.setText(String.format(mContext.getString(R.string.connecting), _song.artist_name));

				} else if (_status == WAITING_VALUE) {
					// WAITING STATUS
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_downloading);
					_itemView.mThumbnailImg.setEnabled(true);
					_itemView.mSecondTxt.setVisibility(TextView.VISIBLE);
					_itemView.mSecondTxt.setText(String.format(mContext.getString(R.string.download_waiting), _song.artist_name));
				} else if (_status >= CONNECTING_VALUE && _status <= DOWNLOADED_VALUE) {
					// DOWNLOADING STATUS
					_itemView.mDownloadBar.setVisibility(ProgressBar.VISIBLE);
					_itemView.mDownloadBar.setProgress(_status);
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_listview_downloading);
					_itemView.mThumbnailImg.setEnabled(false);
					_itemView.mSecondTxt.setVisibility(TextView.VISIBLE);
					_itemView.mSecondTxt.setText(" ("+ _status +"%) - " + _song.artist_name);
				}
			}
			if (mIsEditing) {
				if (position != iDeletingPosition) {
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_delete_horizontal);
					_itemView.mThumbnailImg.setEnabled(true);
					_itemView.mDetailImg.setVisibility(ImageView.GONE);
					_itemView.mGrabberImg.setVisibility(ImageView.VISIBLE);
					_itemView.mDeleteImg.setVisibility(ImageView.GONE);
				}
				else {
					_itemView.mThumbnailImg.setImageResource(R.drawable.ic_delete_vertical);
					_itemView.mThumbnailImg.setEnabled(true);
					_itemView.mDetailImg.setVisibility(ImageView.GONE);
					_itemView.mGrabberImg.setVisibility(ImageView.GONE);
					_itemView.mDeleteImg.setVisibility(ImageView.VISIBLE);
				}				 
			}
			else {
				_itemView.mDetailImg.setVisibility(ImageView.VISIBLE);
				_itemView.mGrabberImg.setVisibility(ImageView.GONE); 
				_itemView.mDeleteImg.setVisibility(ImageView.GONE);
			}
		}

		// Visible/Invisible Playing image
		if (position == NowPlayingList.getSongCurrentIndex()) {
			_itemView.mThumbnailImg.setVisibility(ImageView.GONE);
			_itemView.mPlayingSongImage.setVisibility(ImageView.VISIBLE);
		}
		else {
			_itemView.mThumbnailImg.setVisibility(ImageView.VISIBLE);
			_itemView.mPlayingSongImage.setVisibility(ImageView.GONE);
		}

		return convertView;
	}

	private void setOnClick(View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContext instanceof NowPlayingActivity) {
					NowPlayingActivity nowPlayingActivity = (NowPlayingActivity) mContext;
					switch (v.getId()) {
					case R.id.list_song_item_cached_img:
						nowPlayingActivity.thumbnailClickHandler(v);
						break;
					case R.id.list_song_item_delete:
						nowPlayingActivity.deleteHandler(v);
						break;
					}
				}				
			}
		});
	}

	@Override
	public void onPlaylistDownloadStart(Playlist pl) {
	}

	@Override
	public void onPlaylistDownloadDone(Playlist pl) {
	}

	@Override
	public void onPlaylistDownloadProgress(Playlist pl, int totalSong, int downloadedSong) {
	}

	@Override
	public void onDownloadProgress(int totalSong, int downloadedSong) {
	}

	public void onSongDownloadStart(Song sg) {
	}

	@Override
	public void onSongDownloadDone(Song sg) {
		hashSongProgress.remove(sg.id);
		// Refresh list view
		notifyDataSetChanged();
	}

	@Override
	public void onSongDownloadProgress(Song sg, int total, int downloaded) {
		if (!mIsScrolling) {
			Integer _position = hashSongPosition.get(sg.id);
			if (_position >= mFirstVisibleItem && _position <= mLastVisibleItem) {
				int _currStatus = (int) ((downloaded * 100) / total);
				hashSongProgress.put(sg.id, _currStatus);
				// Refresh list view
				notifyDataSetChanged();
			}
		}
	}

	public void onDownloadStart(int total) {
	}

	@Override
	public void onDownloadStopped() {

		hashSongProgress.clear();
		// refresh to clean listview
		notifyDataSetChanged();

		if (DownloadManager.isLastSongError()) {
			Toast.makeText(mContext, R.string.my_playlist_download_stopped, Toast.LENGTH_SHORT).show();
		} else {
			if ((mDataStore.getSongDownloadPool().size() > 0) || MyPlaylistActivity.justCancelDownload) {
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
	public void onSongDownloadError(Song sg, int errorCode) {
		if (errorCode == EventNotifier.ERROR_NONE) {
			return;
		}

		if (errorCode == EventNotifier.ERROR_POSTPONED) {
			hashSongProgress.put(sg.id, WAITING_VALUE);
		} else {
			hashSongProgress.remove(sg.id);
		}
		// Refresh list view
		notifyDataSetChanged();

		if (errorCode == EventNotifier.ERROR_SAVING_FAILED) {
			Toast.makeText(mContext, R.string.my_playlist_download_error_diskfull, Toast.LENGTH_SHORT).show();
		} else if (errorCode != EventNotifier.ERROR_POSTPONED) {
			Toast.makeText(mContext, R.string.my_playlist_download_error_network, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onDownloadInOfflineMode() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Đang ở chế độ offline", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDownloadVia3gDisable() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Không cho phép tải nhạc qua 3G", Toast.LENGTH_SHORT).show();
	}

}
