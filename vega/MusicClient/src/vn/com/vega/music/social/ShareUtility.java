package vn.com.vega.music.social;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonPlaylist;
import vn.com.vega.music.clientserver.JsonSong;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.FindFriendActivity;
import vn.com.vega.music.view.FriendActivity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ShareUtility {
	private Context mContext;
	private Activity mActivity;
	private boolean isSong = true;
	private int playlistId;
	private int songId;
	private ProgressDialog pd;
	private String mShareLink;
	private String mShareMsg;

	private static final int VALUE_FACEBOOK = 0;
	private static final int VALUE_FRIEND = 1;
	protected static final int SHARE_SUCCESS = 2;
	protected static final int SHARE_FAILED = 3;

	public static final int TYPE_SONG = 0;
	public static final int TYPE_PLAYLIST = 1;

	public ShareUtility(Context _context, Activity _activity, boolean _isSong) {
		mContext = _context;
		mActivity = _activity;
		isSong = _isSong;

	}

	public void setPlaylistId(int _playlistId) {
		playlistId = _playlistId;
	}

	public void setSongId(int _songId) {
		songId = _songId;
	}

	// handle init data result
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			switch (msg.what) {
			case SHARE_FAILED:
				Toast.makeText(mContext, "Không lấy được thông tin",
						Toast.LENGTH_SHORT).show();
				break;
			case SHARE_SUCCESS:
				FacebookUtility fbu = new FacebookUtility(mContext, mActivity);
				fbu.loginAndPostToWall(mShareMsg, mShareLink);
				break;
			}
		}
	};

	private void sharePlaylist() {
		if (NetworkUtility.hasNetworkConnection()) {
			// show progress dialog while load data from database
			pd = ProgressDialog.show(mContext, "", "Đang lấy thông tin...",
					true);

			Thread dataInitializationThread = new Thread() {
				public void run() {
					JsonPlaylist jso = JsonPlaylist.sharePlaylist(playlistId);
					if (jso.isSuccess()) {
						mShareLink = jso.shareLink;
						mShareMsg = jso.shareMsg;
						mHandler.sendEmptyMessage(SHARE_SUCCESS);
					} else {
						mHandler.sendEmptyMessage(SHARE_FAILED);
					}

				}
			};
			dataInitializationThread.start();
		} else {
			Toast.makeText(mContext,
					mContext.getString(R.string.no_network_connection),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void shareSong() {
		if (NetworkUtility.hasNetworkConnection()) {
			// show progress dialog while load data from database
			pd = ProgressDialog.show(mContext, "", "Đang lấy thông tin...",
					true);

			Thread dataInitializationThread = new Thread() {
				public void run() {
					JsonSong jso = JsonSong.shareSong(songId);
					if (jso.isSuccess()) {
						mShareLink = jso.shareLink;
						mShareMsg = jso.shareMsg;
						mHandler.sendEmptyMessage(SHARE_SUCCESS);
					} else {
						mHandler.sendEmptyMessage(SHARE_FAILED);
					}

				}
			};
			dataInitializationThread.start();
		} else {
			Toast.makeText(mContext,
					mContext.getString(R.string.no_network_connection),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void showShareChooser() {
		final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_context_menu);
		dialog.setCancelable(true);
		ListView _listview = (ListView) dialog
				.findViewById(R.id.common_context_menu_listview);
		Button _closeBtn = (Button) dialog
				.findViewById(R.id.common_context_menu_close_btn);
		_closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		DialogAdapter _myAdapter = new DialogAdapter(mContext);
		_listview.setAdapter(_myAdapter);

		TextView _title = (TextView) dialog
				.findViewById(R.id.common_context_menu_title_txt);
		_title.setText("Chia sẻ qua");
		dialog.show();
		_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
				if (position == 0) {
					dialog.dismiss();
					if (isSong)
						shareSong();
					else
						sharePlaylist();
				} else if (position == 1) {
					dialog.dismiss();
					Bundle bundle = new Bundle();
					if (isSong) {
						bundle.putInt(Const.KEY_SHARE_TYPE, TYPE_SONG);
						bundle.putInt(Const.KEY_SHARE_VALUE, songId);
					}

					else {
						bundle.putInt(Const.KEY_SHARE_TYPE, TYPE_PLAYLIST);
						bundle.putInt(Const.KEY_SHARE_VALUE, playlistId);
					}

					bundle.putBoolean(Const.KEY_IS_SHARING, true);
					Intent i = new Intent(mContext, FriendActivity.class);
					i.putExtras(bundle);
					mContext.startActivity(i);

				}
			}
		});
	}

	private class DialogAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		class ViewHolder {
			TextView first_text;
			ImageView image;
			ImageView arrow;

		}

		public DialogAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.view_listview_row_context_menu, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_thumbnail_img);
				holder.arrow = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_arrow_img);
				holder.first_text = (TextView) convertView
						.findViewById(R.id.common_context_menu_row_first_txt);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setVisibility(ImageView.VISIBLE);
			holder.arrow.setVisibility(ImageView.VISIBLE);
			holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow);

			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp2.rightMargin = 20;
			lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp2.addRule(RelativeLayout.CENTER_VERTICAL);
			holder.arrow.setLayoutParams(lp2);

			if (position == 0) {
				holder.image.setBackgroundResource(R.drawable.ic_facebook);
				holder.first_text.setText("Facebook");
			} else if (position == 1) {
				holder.image.setBackgroundResource(R.drawable.ic_friend);
				holder.first_text.setText("Friend");
			}

			return convertView;
		}
	}

}
