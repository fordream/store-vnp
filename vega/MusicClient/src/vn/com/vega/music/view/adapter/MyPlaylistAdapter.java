package vn.com.vega.music.view.adapter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.DownloadStatusListener;
import vn.com.vega.music.downloadmanager.EventNotifier;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.AbstractSongAcitivity;
import vn.com.vega.music.view.gui.ItemViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.BaseAdapter;

public class MyPlaylistAdapter extends BaseAdapter implements DownloadStatusListener, OnScrollListener {

	public static final String LOG_TAG = Const.LOG_PREF + MyPlaylistAdapter.class.getSimpleName();

	protected Hashtable<Integer, Integer> mHashProgress = new Hashtable<Integer, Integer>();
	protected Hashtable<Integer, Integer> mHashPosition = new Hashtable<Integer, Integer>();
	protected List<Object> mItemList = new ArrayList<Object>();
	protected Hashtable<Integer, Object> mItemHash = new Hashtable<Integer, Object>();

	protected boolean selectionMode = false;
	protected DataStore dataStore;
	private LayoutInflater mInflater;
	protected Context mContext;

	protected Integer firstVisibleItem;
	protected Integer lastVisibleItem;
	protected boolean isScrolling = false;

	protected int firstSectionHeaderPos;
	protected int secondSectionHeaderPos;

	protected boolean needSectionHeader = false;
	
	protected static final int FIRST_HEADER_INDEX = 3;
	protected static final int FIRST_HEADER_INDEX_EX = 2;
	protected static final int ERROR_HEADER_INDEX = -1;

	public MyPlaylistAdapter(Context context, DataStore store, boolean _selection, List<Object> list) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		dataStore = store;
		selectionMode = _selection;
		Object testObj = null;
		if (list != null && list.size() > 0)
			testObj = list.get(0);

		if (!selectionMode && testObj != null && testObj instanceof Playlist) {
			needSectionHeader = true;
		}

//		if (needSectionHeader) {
//			getSectionHeaderPosition(list);
//		}

		refreshData(list);
	}

	private boolean hasFavouritePlaylist(List<Object> objs){
		boolean result = false;
		for(Object obj : objs){
			if(obj instanceof Playlist){
				Playlist playlist = (Playlist) obj;
				if(playlist.type == Playlist.TYPE_FAVORITE){
					result = true;
					break;
				}				
			}
		}
		return result;
	}
	
	protected void getSectionHeaderPosition(List<Object> objs) {
		boolean flag = hasFavouritePlaylist(objs);
		int user_created_count = 0;
		int fav_count = 0;
		
		for(Object obj : objs){
			if(obj instanceof Playlist){
				Playlist playlist = (Playlist) obj;
				if(playlist.type == Playlist.TYPE_USER_CREATED)
					user_created_count ++;
				else if(playlist.type == Playlist.TYPE_FAVOURITE_EX)
					fav_count ++;
			}
		}
		
		
		if(flag){
			if(user_created_count == 0)
				firstSectionHeaderPos = ERROR_HEADER_INDEX;
			else
				firstSectionHeaderPos = FIRST_HEADER_INDEX;

		}
		else{
			if(user_created_count == 0)
				firstSectionHeaderPos = ERROR_HEADER_INDEX;
			else
				firstSectionHeaderPos = FIRST_HEADER_INDEX_EX;
			
		}
		
		
		
		if(fav_count == 0)
			secondSectionHeaderPos = ERROR_HEADER_INDEX;
		else
			if(firstSectionHeaderPos != ERROR_HEADER_INDEX)
				secondSectionHeaderPos = firstSectionHeaderPos + user_created_count + 1; // need to replace
			else
				if(flag)
					secondSectionHeaderPos = FIRST_HEADER_INDEX;
				else
					secondSectionHeaderPos = FIRST_HEADER_INDEX_EX;
	}

	protected List<Object> updateListObjs(List<Object> list) {

		if(firstSectionHeaderPos != ERROR_HEADER_INDEX){
			Playlist dumbPlaylist1 = new Playlist();
			dumbPlaylist1.type = Const.FIRST_SECTION_HEADER;
			dumbPlaylist1.title = "Playlist tự tạo";
			list.add(firstSectionHeaderPos, dumbPlaylist1);
		}
		
		if(secondSectionHeaderPos != ERROR_HEADER_INDEX){
			Playlist dumbPlaylist2 = new Playlist();
			dumbPlaylist2.type = Const.SECOND_SECTION_HEADER;
			dumbPlaylist2.title = "Playlist yêu thích";
			list.add(secondSectionHeaderPos, dumbPlaylist2);
		}	

		return list;
	}

	public void refreshData(List<Object> list) {	
		if(needSectionHeader){
			getSectionHeaderPosition(list);
			list = updateListObjs(list);
		}		
		mItemList.clear();
		mItemList.addAll(list);
		mItemHash.clear();
		mHashPosition.clear();
		mHashProgress.clear();

		Playlist dldPlaylist = DownloadManager.getCurrentDownloadPlaylist();
		Song dldSong = DownloadManager.getCurrentDownloadSong();
		for (Object obj : mItemList) {
			if (obj instanceof Song) {
				Song song = (Song) obj;
				mItemHash.put(song.id, song);
				if (song.isWaitToDownload()) {
					if ((dldSong != null) && (song.id == dldSong.id)) {
						mHashProgress.put(song.id, DownloadManager.getCurrentDownloadSongPercent());
					}
					// else {
					// mHashProgress.put(song.id, ItemViewHolder.WAITING_VALUE);
					// }
				}
			} else if (obj instanceof Playlist) {
				Playlist playlist = (Playlist) obj;
				mItemHash.put(playlist.id, playlist);
				if ((dldPlaylist != null) && (playlist.id == dldPlaylist.id)) {
					mHashProgress.put(playlist.id, DownloadManager.getCurrentDownloadPlaylistPercent());
				}
			}
		}

		notifyDataSetChanged();
	}

	/**
	 * The number of items in the list is determined by the number of speeches
	 * in our array.
	 * 
	 * @see android.widget.ListAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return mItemList.size();
	}

	/**
	 * Since the data comes from an array, just returning the index is sufficent
	 * to get at the data. If we were using a more complex data structure, we
	 * would return whatever object represents one row in the list.
	 * 
	 * @see android.widget.ListAdapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {

		if ((position >= 0) && (position < mItemList.size())) {
			return mItemList.get(position);
		}
		return null; // should never reach

	}

	/**
	 * Use the array index as a unique id.
	 * 
	 * @see android.widget.ListAdapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	public void onScroll(AbsListView view, int _firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// remember these value to determine whether i should refresh
		// listview or not
		firstVisibleItem = _firstVisibleItem;
		lastVisibleItem = _firstVisibleItem + visibleItemCount;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == 0) {
			isScrolling = false;
		} else
			isScrolling = true;
	}
	
	public void onPause() {
		DownloadManager.removeDownloadStatusListener(MyPlaylistAdapter.class
				.getName());
	}

	public void onResume() {
		DownloadManager.addDownloadStatusListener(
				MyPlaylistAdapter.class.getName(), this);
		notifyDataSetChanged();
	}

	public void onDownloading() {
		DownloadManager.removeDownloadStatusListener(MyPlaylistAdapter.class
				.getName());

		DownloadManager.addDownloadStatusListener(
				MyPlaylistAdapter.class.getName(), this);
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
	private void setOnCheckboxClick(View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mContext instanceof AbstractSongAcitivity) {
					AbstractSongAcitivity thumClick = (AbstractSongAcitivity) mContext;
					thumClick.onCheckboxClickListener(v);
					
				}
			}
		});
	}


	/**
	 * Make a view to hold each row.
	 * 
	 * @see android.widget.ListAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder _holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.view_listview_row_myplaylist, null);
			_holder = new ItemViewHolder(convertView);
			convertView.setTag(_holder);
		} else {
			_holder = (ItemViewHolder) convertView.getTag();
		}

		Object object = getItem(position);
		if (object == null) {
			return null;
		}

		if (object instanceof Song) {
			
//			if(position == 0)
//				convertView.setBackgroundResource(R.drawable.selector_listview_first);
//			else if(position == (getCount() - 1))
//				convertView.setBackgroundResource(R.drawable.selector_listview_last);
//			else
//				convertView.setBackgroundResource(R.drawable.selector_listview_normal);
			
			RelativeLayout firstRegion = (RelativeLayout) convertView.findViewById(R.id.my_playlist_row_region_first);
			RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp1.leftMargin = 0;
			firstRegion.setLayoutParams(lp1);
			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			ImageView arrow = (ImageView) convertView.findViewById(R.id.my_playlist_row_arrow_img);
			lp2.rightMargin = 15;
			lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp2.addRule(RelativeLayout.CENTER_VERTICAL);
			arrow.setLayoutParams(lp2);
			
			convertView.setBackgroundResource(R.drawable.selector_listview_dialog);
			
			
			
			Song _currSong = (Song) object;
			Integer _status = mHashProgress.get(_currSong.id);
			mHashPosition.put(_currSong.id, position);
			_holder.setupFor(_currSong, selectionMode, _status);
			
			_holder.thumbnailImg.setTag(_currSong);
			setOnThumbClick(_holder.thumbnailImg);

		} else if (object instanceof Playlist) {
			if(needSectionHeader){
				if (position == 0)
					convertView.setBackgroundResource(R.drawable.selector_listview_first);
				else if (position == 1 && firstSectionHeaderPos == FIRST_HEADER_INDEX_EX)
					convertView.setBackgroundResource(R.drawable.selector_listview_last);
				else if(position == 2 && firstSectionHeaderPos == FIRST_HEADER_INDEX)
					convertView.setBackgroundResource(R.drawable.selector_listview_last);
				else if(position == firstSectionHeaderPos){
					convertView.setBackgroundResource(R.drawable.bg_section_header_ex);
				}		
				else if(position == secondSectionHeaderPos)
					convertView.setBackgroundResource(R.drawable.bg_section_header);
				else if (position == (getCount() - 1))
					convertView.setBackgroundResource(R.drawable.selector_listview_last);
				else
					convertView.setBackgroundResource(R.drawable.selector_listview_normal);
				//convertView.setBackgroundResource(R.drawable.selector_listview_dialog);

			}
			else{
				
				RelativeLayout firstRegion = (RelativeLayout) convertView.findViewById(R.id.my_playlist_row_region_first);
				RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				lp1.leftMargin = 0;
				firstRegion.setLayoutParams(lp1);
				RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				ImageView arrow = (ImageView) convertView.findViewById(R.id.my_playlist_row_arrow_img);
				lp2.rightMargin = 15;
				lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp2.addRule(RelativeLayout.CENTER_VERTICAL);
				arrow.setLayoutParams(lp2);
				
				
				convertView.setBackgroundResource(R.drawable.selector_listview_dialog);
				
				
				
//				if(position == 0)
//					convertView.setBackgroundResource(R.drawable.selector_listview_first);
//				else if(position == (getCount() - 1))
//					convertView.setBackgroundResource(R.drawable.selector_listview_last);
//				else
//					convertView.setBackgroundResource(R.drawable.selector_listview_normal);
				
				
				
				
			}
			Playlist _currPlaylist = (Playlist) object;
			Integer _status = mHashProgress.get(_currPlaylist.id);
			mHashPosition.put(_currPlaylist.id, position);
			_holder.setupFor(object, selectionMode, _status);
			
			_holder.checkbox.setTag(_currPlaylist);
			setOnCheckboxClick(_holder.checkbox);
			
		}

		return convertView;
	}

	@Override
	public void onPlaylistDownloadDone(Playlist pl) {
		Object cached = mItemHash.get(pl.id);
		if (cached instanceof Playlist) {
			mHashProgress.remove(pl.id);
		}
	}

	@Override
	public void onPlaylistDownloadStart(Playlist playlist) {
		onPlaylistDownloadProgress(playlist, 1, 0);
	}

	@Override
	public void onPlaylistDownloadProgress(Playlist pl, int totalSong, int downloadedSong) {
		if (!isScrolling) {
			Object cached = mItemHash.get(pl.id);
			if (cached instanceof Playlist) {
				int _currStatus = (int) ((downloadedSong * 100) / totalSong);
				mHashProgress.put(pl.id, _currStatus);

				Integer _position = mHashPosition.get(pl.id);
				if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
					notifyDataSetChanged();
				}
			}
		}
	}

	@Override
	public void onSongDownloadStart(Song song) {
		onSongDownloadProgress(song, 1, 0);
	}

	@Override
	public void onSongDownloadDone(Song sg) {
		Object cached = mItemHash.get(sg.id);
		if (cached instanceof Song) {
			mHashProgress.remove(sg.id);
			notifyDataSetChanged();
		}
	}

	@Override
	public void onSongDownloadProgress(Song sg, int total, int downloaded) {
		if (!isScrolling) {
			Object cached = mItemHash.get(sg.id);
			if (cached instanceof Song) {
				int _currStatus = (int) ((downloaded * 100) / total);
				mHashProgress.put(sg.id, _currStatus);

				Integer _position = mHashPosition.get(sg.id);
				if (_position >= firstVisibleItem && _position <= lastVisibleItem) {
					notifyDataSetChanged();
				}
			}
		}
	}
	
	@Override
	public void onSongDownloadError(Song sg, int errorCode) {
		if (errorCode == EventNotifier.ERROR_NONE) {
			return;
		}

		Object cached = mItemHash.get(sg.id);
		if (cached instanceof Song) {
			if (errorCode == EventNotifier.ERROR_POSTPONED) {
				mHashProgress.put(sg.id, ItemViewHolder.WAITING_VALUE);
			} else {
				mHashProgress.remove(sg.id);
			}
			if (!isScrolling) {
				notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onDownloadStart(int total) {
		
	}

	@Override
	public void onDownloadProgress(int totalSong, int downloadedSong) {
	}

	@Override
	public void onDownloadStopped() {
		mHashProgress.clear();
		notifyDataSetChanged();
	}

	@Override
	public void onDownloadInOfflineMode() {
		// TODO Auto-generated method stub
		//Toast.makeText(mContext, "Đang ở chế độ offline", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDownloadVia3gDisable() {
		// TODO Auto-generated method stub
		//Toast.makeText(mContext, "Không cho phép tải nhạc qua 3G", Toast.LENGTH_SHORT).show();
	}
}
