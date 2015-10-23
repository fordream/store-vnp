package vn.com.vega.music.view.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.DownloadStatusListener;
import vn.com.vega.music.downloadmanager.EventNotifier;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.AbstractSongAcitivity;
import vn.com.vega.music.view.FeatureHomeActivity;
import vn.com.vega.music.view.NowPlayingActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author khainv
 * @since 10/2011
 */
public class SongListAdapter extends BaseAdapter implements DownloadStatusListener, Const, SectionIndexer {

	protected Hashtable<Integer, Integer> hashSongProgress = new Hashtable<Integer, Integer>();
	protected Hashtable<Integer, Integer> hashSongPosition = new Hashtable<Integer, Integer>();

	private Activity mContext;
	private LayoutInflater mInflater;
	private List<Object> mListObject = new ArrayList<Object>();

	protected static Integer firstVisibleItem;
	protected static Integer lastVisibleItem;
	protected static boolean isScrolling = false;

	private SongListAdapter songListAdapter;
	private DataStore dataStore;

	private boolean isLocalMusic = false;

	HashMap<String, Integer> azIndexer;
	String[] sections;

	public class customComparator implements Comparator<Object> {
		public int compare(Object object1, Object object2) {
			if (object1 instanceof Song && object2 instanceof Song) {
				Song song1 = (Song) object1;
				Song song2 = (Song) object2;
				return song1.title.toUpperCase().trim().compareTo(song2.title.toUpperCase().trim());
			}
			return 0;
		}
	}

	public SongListAdapter(Activity context, List<Object> objects, boolean _isLocalMusic) {
		mContext = context;
		isLocalMusic = _isLocalMusic;
		dataStore = DataStore.getInstance();
		songListAdapter = this;
		mInflater = LayoutInflater.from(context);
		if (isLocalMusic) {
			mListObject.clear();
			mListObject.addAll(objects);
			if (mListObject.size() > 0) {
				Object obj = mListObject.get(0);
				if (obj instanceof Song)
					indexData();
			}

		} else {
			DownloadManager.addDownloadStatusListener(SongListAdapter.class.getName(), songListAdapter);

			if (objects != null) {
				for (Object object : objects) {
					if (object instanceof Song) {
						Song cached = dataStore.getSongById(((Song) object).id);
						if (cached != null) {
							mListObject.add(cached);
							continue;
						}
					}
					mListObject.add(object);
				}
			}
		}

	}

	public void setData(List<Object> objs) {
		mListObject = objs;
	}

	public void indexData() {
		//Collections.sort(mListObject, new customComparator());
		azIndexer = new HashMap<String, Integer>();
		int size = mListObject.size();
		for (int i = 0; i < size; i++) {
			Object obj = mListObject.get(i);
			if (obj instanceof Song) {
				Song song = (Song) obj;
				String element = song.title.trim();
				// We store the first letter of the word, and its index.
				azIndexer.put(element.substring(0, 1).toUpperCase(), i);
			}

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

	@Override
	public int getCount() {
		return mListObject.size();
	}

	@Override
	public Object getItem(int position) {
		return mListObject.get(position);
	}

	public void setIsLocalMusic(boolean value) {
		isLocalMusic = value;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		Object obj = mListObject.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.view_listview_row_feature_song, null);

			holder = new ViewHolder();
			holder.thumb = (ImageButton) convertView.findViewById(R.id.songitem_icon);
			holder.title = (TextView) convertView.findViewById(R.id.songitem_title);
			holder.info = (TextView) convertView.findViewById(R.id.songitem_by);
			holder.view = (TextView) convertView.findViewById(R.id.songitem_view);
			holder.progressbar = (ProgressBar) convertView.findViewById(R.id.download_progressbar);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == 0)
			convertView.setBackgroundResource(R.drawable.selector_listview_row_first);
		else if (position == (getCount() - 1))
			convertView.setBackgroundResource(R.drawable.selector_listview_row_last);
		else
			convertView.setBackgroundResource(R.drawable.selector_listview_row_middle);

		if (obj instanceof Song) {
			Song song = (Song) obj;

			holder.title.setText((position + 1) + ". " + song.title);
			holder.thumb.setTag(song);
			setOnThumbClick(holder.thumb);
			hashSongPosition.put(song.id, position);

			if (isLocalMusic) {
				holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloaded);
				holder.thumb.setEnabled(false);

				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				holder.thumb.setLayoutParams(params);

				holder.info.setVisibility(TextView.VISIBLE);
				holder.view.setVisibility(View.GONE);
				holder.info.setText(String.format(mContext.getString(R.string.format_by), song.artist_name));

				holder.progressbar.setVisibility(ProgressBar.GONE);
			} else if (song.isAvailableLocally() || dataStore.isAvailableAtLocal(song.id)) {
				// thumbnail with downloaded symbol
				holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloaded);
				holder.thumb.setEnabled(false);

				holder.info.setVisibility(TextView.VISIBLE);
				holder.view.setVisibility(View.GONE);
				holder.view.setText(String.format(mContext.getString(R.string.format_view_count), song.viewCount));
				holder.info.setText(String.format(mContext.getString(R.string.format_by), song.artist_name));

				holder.progressbar.setVisibility(ProgressBar.GONE);
			} else {
				Integer progress = hashSongProgress.get(song.id);
				if (progress == null) {
					if (song.isWaitToDownload()) {
						holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloading);
						holder.thumb.setEnabled(true);

						holder.info.setVisibility(TextView.VISIBLE);
						holder.view.setVisibility(View.INVISIBLE);
						holder.info.setText(song.artist_name + " - Đang đợi tải");

						holder.progressbar.setVisibility(ProgressBar.GONE);
					} else {
						holder.thumb.setBackgroundResource(R.drawable.ic_listview_not_downloaded);
						holder.thumb.setEnabled(true);

						holder.info.setVisibility(TextView.VISIBLE);
						holder.view.setVisibility(View.VISIBLE);
						holder.view.setText(String.format(mContext.getString(R.string.format_view_count), song.viewCount));
						holder.info.setText(String.format(mContext.getString(R.string.format_by), song.artist_name));

						holder.progressbar.setVisibility(ProgressBar.GONE);
					}
					// connecting status
				} else if (progress == CONNECTING_VALUE) {
					holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloading);
					holder.thumb.setEnabled(false);

					holder.info.setVisibility(TextView.VISIBLE);
					holder.view.setVisibility(View.INVISIBLE);
					holder.info.setText(R.string.connecting);

					holder.progressbar.setVisibility(ProgressBar.GONE);
					// waiting status
				} else if (progress == WAITING_VALUE) {
					holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloading);
					holder.thumb.setEnabled(true);

					holder.info.setVisibility(TextView.VISIBLE);
					holder.view.setVisibility(View.INVISIBLE);
					holder.info.setText(song.artist_name + " - Đang đợi tải");

					holder.progressbar.setVisibility(ProgressBar.GONE);
				} else if (progress > CONNECTING_VALUE && progress < DOWNLOADED_VALUE) {
					holder.progressbar.setVisibility(ProgressBar.VISIBLE);
					holder.progressbar.setProgress(progress);
					holder.thumb.setBackgroundResource(R.drawable.ic_listview_downloading);
					holder.thumb.setEnabled(false);

					holder.info.setVisibility(TextView.VISIBLE);
					holder.view.setVisibility(View.INVISIBLE);
					holder.info.setText("(" + progress + " %) - " + song.artist_name);
				}
			}
		}
		return convertView;
	}

	protected void askGoToOnline() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.offline_mode).setMessage(R.string.offline_mode_msg).setCancelable(false)
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

	public void download(Song song) {
		if (NetworkUtility.hasNetworkConnection()) {
			DownloadManager.removeDownloadStatusListener(SongListAdapter.class.getName());

			DownloadManager.addDownloadStatusListener(SongListAdapter.class.getName(), songListAdapter);

			dataStore.addSong(song);

			// song is waiting to download, remove it
			if (song.isWaitToDownload()) {
				dataStore.removeSongFromSongDownloadPool(song.id);
				// to show normal thumbnail
				hashSongProgress.remove(song.id);
				// refresh listview
				notifyDataSetChanged();
				// song is normal state
			} else {
				if (DownloadManager.isRunning()) {
					if (dataStore.addSongToSongDownloadPool(song.id)) {
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
					dataStore.clearSongDownloadPool();
					dataStore.clearPlaylistDownloadPool();
					// add song to song download pool
					dataStore.addSongToSongDownloadPool(song.id);
					// start download thread
					DownloadManager.startDownload();
				}
			}
		} else {
			Toast.makeText(mContext.getApplicationContext(), "Không có kết nối mạng", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * addedToDownloadPool Use to record which song is added to download pool by
	 * this adapter Remember, song may be added by this adapter, may be added by
	 * another
	 */
	private Hashtable<Integer, Integer> addedToDownloadPool = new Hashtable<Integer, Integer>();

	/**
	 * This function is used to remove all song from download which we added to
	 * download pool last time
	 * 
	 * HEY, don't touch the download manager, it will automatically stop if
	 * download pool is empty
	 * 
	 * @author Ngo Tri Hoai
	 */
	public void removeAllAddedSongFromDownloadPool() {
		List<Song> songList = getSongList();
		if (songList == null) {
			return;
		}

		boolean removed = false;
		for (Song song : songList) {
			if (addedToDownloadPool.containsKey(song.id)) {
				removed = true;
				addedToDownloadPool.remove(song.id);
				dataStore.removeSongFromSongDownloadPool(song.id);
			}
		}

		if (removed) {
			notifyDataSetChanged();
		}
	}

	public void onPause() {
		DownloadManager.removeDownloadStatusListener(SongListAdapter.class.getName());
	}

	public void onResume() {
		DownloadManager.addDownloadStatusListener(SongListAdapter.class.getName(), songListAdapter);
		notifyDataSetChanged();
	}

	/**
	 * This function is used to add all song into download pool if needed and
	 * remember which song is added, so we can remove them later
	 * 
	 * HEY, remember to start downloadmanager if it's not running !
	 * 
	 * @author Ngo Tri Hoai
	 */
	public void downloadAll() {
		if (NetworkUtility.hasNetworkConnection()) {
			DownloadManager.removeDownloadStatusListener(SongListAdapter.class.getName());

			DownloadManager.addDownloadStatusListener(SongListAdapter.class.getName(), songListAdapter);

			List<Song> songList = getSongList();
			if (songList == null) {
				return;
			}

			boolean added = false;
			for (Song song : songList) {
				dataStore.addSong(song);
				Song cached = dataStore.getSongById(song.id);
				if (!cached.isAvailableLocally() || !cached.isWaitToDownload()) {
					added = true;
					dataStore.addSongToSongDownloadPool(song.id);
					hashSongProgress.put(song.id, WAITING_VALUE);
					addedToDownloadPool.put(song.id, song.id);
				}
			}

			if (added) {
				notifyDataSetChanged();
				if (!DownloadManager.isRunning())
					DownloadManager.startDownload();
			}
		} else {
			Toast.makeText(mContext.getApplicationContext(), "Không có kết nối mạng", Toast.LENGTH_LONG).show();
		}

	}

	public void cancelDownload() {
		DownloadManager.removeDownloadStatusListener(SongListAdapter.class.getName());
		hashSongProgress.clear();
		if (dataStore.removeSongsFromSongDownloadPool(getSongList()) > 0)
			notifyDataSetInvalidated();
	}

	class ViewHolder {
		ImageButton thumb;
		TextView title;
		TextView info, view;
		ProgressBar progressbar;
	}

	public void notifyListObjectChanged(List<Object> objectsChanged) {
		if (objectsChanged != null) {
			mListObject.addAll(objectsChanged);
			notifyDataSetChanged();
		}
	}

	public ArrayList<Song> getSongList() {
		ArrayList<Song> result = new ArrayList<Song>();
		for (Object obj : mListObject) {
			if (obj instanceof Song) {
				result.add((Song) obj);
			}
		}
		return result;
	}

	public void onSongDownloadStart(Song song) {

		if (!isScrolling) {
			Integer _position = hashSongPosition.get(song.id);
			if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
				hashSongProgress.put(song.id, CONNECTING_VALUE);
				notifyDataSetChanged();
			}
		}
	}

	private void setOnThumbClick(View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mContext instanceof AbstractSongAcitivity) {
					AbstractSongAcitivity thumClick = (AbstractSongAcitivity) mContext;
					thumClick.onThumbnailClickListener(v);
				}
			}
		});
	}

	@Override
	public void onSongDownloadDone(Song song) {
		hashSongProgress.remove(song.id);
		notifyDataSetChanged();
	}

	
	@Override
	public void onSongDownloadProgress(Song sg, int total, int downloaded) {

		if (!isScrolling) {
			Integer _position = hashSongPosition.get(sg.id);
			if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
				int _currStatus = (int) ((downloaded * 100) / total);
				hashSongProgress.put(sg.id, _currStatus);
				notifyDataSetChanged();
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
		notifyDataSetChanged();

		if (errorCode == EventNotifier.ERROR_SAVING_FAILED) {
			Toast.makeText(mContext, R.string.my_playlist_download_error_diskfull, Toast.LENGTH_LONG).show();
		} else if (errorCode != EventNotifier.ERROR_POSTPONED) {
			Toast.makeText(mContext, R.string.my_playlist_download_error_network, Toast.LENGTH_LONG).show();
		}

	}

	public void onPlaylistDownloadStart(Playlist playlist) {
	}

	@Override
	public void onPlaylistDownloadDone(Playlist pl) {
	}

	@Override
	public void onPlaylistDownloadProgress(Playlist pl, int totalSong, int downloadedSong) {
	}

	public void onDownloadStart(int total) {
	}

	@Override
	public void onDownloadProgress(int totalSong, int downloadedSong) {
	}

	@Override
	public void onDownloadStopped() {
		hashSongProgress.clear();
		notifyDataSetChanged();

		if (DownloadManager.isLastSongError()) {
			Toast.makeText(mContext, R.string.my_playlist_download_stopped, Toast.LENGTH_SHORT).show();
		} else {
			if ((dataStore.getSongDownloadPool().size() > 0)) {
				// Toast.makeText(mContext,
				// R.string.my_playlist_download_stopped,
				// Toast.LENGTH_SHORT).show();
			} else {
				// Toast.makeText(mContext,
				// R.string.my_playlist_download_completed,
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

	public static void setVisibleItems(int _firstVisibleItem, int _lastVisibleItem) {
		firstVisibleItem = _firstVisibleItem;
		lastVisibleItem = _lastVisibleItem;
	}

	public static void setScrolling(boolean _isScrolling) {
		isScrolling = _isScrolling;
	}

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

	@Override
	public void onDownloadInOfflineMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDownloadVia3gDisable() {
		// TODO Auto-generated method stub

	}

}
