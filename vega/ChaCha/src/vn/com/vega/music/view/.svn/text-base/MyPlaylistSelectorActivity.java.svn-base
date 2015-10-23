package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.common.Session;
import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.Playlist;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyPlaylistSelectorActivity extends Activity {

	Context context;
	LayoutInflater inflater;
	static DataStore dataStore = null;
	private static EfficientAdapter adapter;
	private static ListView listview = null;
	private Button closeDialogBtn, backBtn, clearBtn;
	// private Button createNewPlaylistBtn;
	private LinearLayout createNewPlaylistLayout;
	private Button okDialogBtn;
	private ArrayList<Song> songs = new ArrayList<Song>();
	private EditText editText;
	protected Dialog dialog;
	private ProgressDialog mProgressDialog;

	protected static final int ADD_SUCCESS = 0;
	protected static final int ADD_FAIL = 1;

	@Override
	protected void onResume() {
		super.onResume();
		// do stuffs
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// remove default title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_myplaylist_selector);
		context = this;
		// init data
		dataStore = DataStore.getInstance();
		// get data pass through intent
		Bundle bd = this.getIntent().getExtras();
		songs = (ArrayList<Song>) Session.getSharedObject(bd);
		// draw view
		initializeComponentView();
	}

	// when click on thumbnail
	public void thumbnailClickHandler(View v) {

	}

	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.my_playlist_selector_new_playlist_clear_btn:
					editText.setText("");
					break;
				case R.id.playlist_selector_back_btn:
					// back to previous screen without change
					onBackPressed();
					break;
				case R.id.my_playlist_selector_new_playlist_layout:
					// show dialog allowing user enter new playlist
					showNewPlaylistDialog();
					break;
				case R.id.my_playlist_selector_new_playlist_create_btn:
					// add new playlist
					String _playlistName = editText.getText().toString().toLowerCase().trim();
					if (_playlistName.equals("")) {
						Toast.makeText(context, R.string.my_playlist_selector_warning_msg_txt, Toast.LENGTH_SHORT).show();
					} else {
						boolean _nameIsExist = false;
						List<Playlist> _playlists = dataStore.getListPlaylist();
						for (Playlist _playlist : _playlists) {
							if (_playlist.title != null && _playlist.title.toLowerCase().trim().equals(_playlistName)) {
								_nameIsExist = true;
								break;
							}
						}
						if (_nameIsExist) {
							Toast.makeText(context, "Tên đã tồn tại", Toast.LENGTH_SHORT).show();
						} else {
							dialog.cancel();
							Playlist _newPlaylist = new Playlist();
							_newPlaylist.title = _playlistName;
							dataStore.addPlaylist(_newPlaylist);
							adapter.refreshData();
							adapter.notifyDataSetChanged();
						}
					}
					break;
				case R.id.my_playlist_selector_new_playlist_close_btn:
					if (dialog.isShowing()) {
						dialog.cancel();
					}
					break;
				default:
					break;
				}
			}
		});
	}// end of setOnclick

	public void showNewPlaylistDialog() {
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_new_playlist);
		editText = (EditText) dialog.findViewById(R.id.my_playlist_selector_new_playlist_edt);
		okDialogBtn = (Button) dialog.findViewById(R.id.my_playlist_selector_new_playlist_create_btn);
		closeDialogBtn = (Button) dialog.findViewById(R.id.my_playlist_selector_new_playlist_close_btn);
		clearBtn = (Button) dialog.findViewById(R.id.my_playlist_selector_new_playlist_clear_btn);
		setOnClick(okDialogBtn);
		setOnClick(closeDialogBtn);
		setOnClick(clearBtn);
		dialog.show();
	}

	// UI
	private void initializeComponentView() {
		// init controls on view
		createNewPlaylistLayout = (LinearLayout) findViewById(R.id.my_playlist_selector_new_playlist_layout);
		backBtn = (Button) findViewById(R.id.playlist_selector_back_btn);
		setOnClick(createNewPlaylistLayout);
		setOnClick(backBtn);
		listview = (ListView) findViewById(R.id.my_playlist_selector_listview);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_listview_empty_footer, null, false);
		listview.addHeaderView(footerView);
		listview.addFooterView(footerView);
		adapter = new EfficientAdapter(context);
		listview.setAdapter(adapter);
		if (adapter.getCount() == 0)
			showNewPlaylistDialog();
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// do stuffs.
				// add song to playlist and back to previous screen
				Object obj = parent.getItemAtPosition(position);
				final Playlist playlist = (Playlist) obj;

				mProgressDialog = new ProgressDialog(context);
				mProgressDialog.setMessage(getString(R.string.my_playlist_selector_adding));
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						int i = 0;
						if (songs.size() > 0) {
							for (Song song : songs) {
								// add song first
								dataStore.addSong(song);
								if (dataStore.addSongToPlaylist(playlist.id, song.id))
									i++;
							}
						}
						// mHandler.sendEmptyMessage(ADD_SUCCESS);

						if (i > 0)
							mHandler.sendEmptyMessage(ADD_SUCCESS);
						else
							mHandler.sendEmptyMessage(ADD_FAIL);

					}
				}).start();

			}
		});
		return;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgressDialog.dismiss();
			switch (msg.what) {
			case ADD_FAIL:
				adapter.refreshData();
				adapter.notifyDataSetChanged();
				Toast.makeText(context, "Thêm bài hát không thành công", Toast.LENGTH_SHORT).show();
				break;

			case ADD_SUCCESS:
				adapter.refreshData();
				adapter.notifyDataSetChanged();
				Toast.makeText(context, "Thêm bài hát thành công", Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};

	// BEGIN-----------------------
	private class EfficientAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private List<Playlist> mData = null;

		class ViewHolder {
			TextView firstTxt;
			TextView secondTxt;
			ImageView thumbnailImg;
			ImageView statusImg;
			ImageView arrow;
		}

		public EfficientAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			mData = dataStore.getUserCreatedPlaylistList();
		}

		public void refreshData() {
			mData = dataStore.getUserCreatedPlaylistList();
		}

		/**
		 * The number of items in the list is determined by the number of
		 * speeches in our array.
		 * 
		 * @see android.widget.ListAdapter#getCount()
		 */
		@Override
		public int getCount() {
			return mData.size();
		}

		/**
		 * Since the data comes from an array, just returning the index is
		 * sufficent to get at the data. If we were using a more complex data
		 * structure, we would return whatever object represents one row in the
		 * list.
		 * 
		 * @see android.widget.ListAdapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			return mData.get(position);
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

		/**
		 * Make a view to hold each row.
		 * 
		 * @see android.widget.ListAdapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder _holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.view_listview_row_myplaylist, null);// later
				_holder = new ViewHolder();
				_holder.firstTxt = (TextView) convertView.findViewById(R.id.my_playlist_row_first_txt);
				_holder.secondTxt = (TextView) convertView.findViewById(R.id.my_playlist_row_second_txt);
				_holder.thumbnailImg = (ImageView) convertView.findViewById(R.id.my_playlist_row_thumbnail_img);
				_holder.statusImg = (ImageView) convertView.findViewById(R.id.my_playlist_row_status_img);
				_holder.arrow = (ImageView) convertView.findViewById(R.id.my_playlist_row_arrow_img);
				convertView.setTag(_holder);
			} else {
				_holder = (ViewHolder) convertView.getTag();
			}

			if (position == 0)
				convertView.setBackgroundResource(R.drawable.selector_listview_first);
			else if (position > 0 && position < (getCount() - 1))
				convertView.setBackgroundResource(R.drawable.selector_listview_normal);
			else if (position == (getCount() - 1))
				convertView.setBackgroundResource(R.drawable.selector_listview_last);

			Playlist _currPlaylist = mData.get(position);
			if (songs.size() == 1) {
				List<Song> _songs = _currPlaylist.getSongList();
				Song _song = songs.get(0);
				boolean bResult = true;
				for (Song song : _songs) {
					if (song.id == _song.id) {
						_holder.arrow.setBackgroundResource(R.drawable.ic_dialog_checked);
						_holder.arrow.setVisibility(ImageView.VISIBLE);
						bResult = false;
						break;
					}
				}
				if (bResult){
					_holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow_ex);
					_holder.arrow.setVisibility(ImageView.VISIBLE);
				}
					
			} else {
				_holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow_ex);
				_holder.arrow.setVisibility(ImageView.VISIBLE);
			}
			_holder.firstTxt.setText((position + 1) + ". " + _currPlaylist.title);
			_holder.secondTxt.setVisibility(TextView.VISIBLE);
			_holder.secondTxt.setText(_currPlaylist.getSongList().size() + Const.SONG_SUFFIX);
			if (_currPlaylist.isAllSongCached()) {
				_holder.statusImg.setVisibility(ImageView.VISIBLE);
				_holder.statusImg.setBackgroundResource(R.drawable.ic_listview_downloaded_music_ex);
			} else {
				_holder.statusImg.setVisibility(ImageView.GONE);
			}
			_holder.thumbnailImg.setVisibility(ImageView.VISIBLE);
			_holder.thumbnailImg.setBackgroundResource(R.drawable.ic_listview_playlist);
			return convertView;

		}
	}
	// ------------------------- EfficientAdapter Class
	// END-----------------------
}
