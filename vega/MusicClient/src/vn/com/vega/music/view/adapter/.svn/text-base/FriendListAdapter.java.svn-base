package vn.com.vega.music.view.adapter;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendListAdapter extends ArrayAdapter<Friend> {

	private ArrayList<Friend> array_friend;

	public ArrayList<Friend> getArray_friend() {
		return array_friend;
	}

	private static final int SHOW = View.VISIBLE;
	private static final int HIDE = View.GONE;

	private Activity mContext;
	private ImageLoader mImageLoader;
	private static int mMode;

	public FriendListAdapter(Activity activity, int textViewResourceId,
			ArrayList<Friend> objects, int MODE) {
		super(activity.getApplicationContext(), textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.mContext = activity;
		this.array_friend = objects;
		mImageLoader = ImageLoader
				.getInstance(activity.getApplicationContext());
		mMode = MODE;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View friend_view = convertView;
		final Friend currentFriend;
		ViewHolder holder;

		if (friend_view == null) {
			LayoutInflater li = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			friend_view = li.inflate(R.layout.view_listview_row_setting_friend,
					null);
			holder = new ViewHolder();
			holder.friend_avatar = (ImageView) friend_view
					.findViewById(R.id.friend_avatar);
			holder.friend_name = (TextView) friend_view
					.findViewById(R.id.friend_name);
			holder.friend_playlist = (TextView) friend_view
					.findViewById(R.id.friend_playlist);
			holder.find_user_subscribed = (Button) friend_view
					.findViewById(R.id.btn_Subscribed);
			holder.find_friend_facebook = (Button) friend_view
					.findViewById(R.id.btn_Subscribed_below);
			holder.chk_select = (CheckBox) friend_view
					.findViewById(R.id.chkSelect);
			friend_view.setTag(holder);
		} else
			holder = (ViewHolder) friend_view.getTag();
		if (mMode == Const.FRIEND_MODE_INVITE_FRIEND) {
			if (position == (GetNumberOfFriend() - 1))
				friend_view
						.setBackgroundResource(R.drawable.bg_listview_find_friend_facebook_last);
			else
				friend_view
						.setBackgroundResource(R.drawable.bg_listview_find_friend_facebook);
		} else {
			if (position == 0) {
				friend_view
						.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_first);
			} else if (position == (GetNumberOfFriend() - 1))
				friend_view
						.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_last);
			else
				friend_view
						.setBackgroundResource(R.drawable.selector_setting_friend_listview_row_middle);
		}
		currentFriend = array_friend.get(position);
		if (currentFriend != null) {
			holder.friend_name.setText(currentFriend.friend_name);
			holder.friend_playlist.setText(currentFriend.friend_playlist.size()
					+ " playlist");
			mImageLoader.DisplayImage(currentFriend.friend_avatar, mContext,
					holder.friend_avatar, ImageLoader.TYPE_ARTIST);
			holder.chk_select
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							currentFriend.setChecked(isChecked);
						}
					});
			/**
			 * set button subcribse visible by mode
			 */
			switch (mMode) {
			case Const.FRIEND_MODE_DEFAULT:
				holder.find_user_subscribed.setVisibility(HIDE);
				holder.find_friend_facebook.setVisibility(HIDE);
				holder.chk_select.setVisibility(HIDE);
				break;
			case Const.FRIEND_MODE_FIND_USER:
				if (currentFriend.getmy_idol() == 1) {
					holder.find_user_subscribed.setVisibility(HIDE);
				} else {
					holder.find_user_subscribed.setVisibility(SHOW);
				}
				holder.find_friend_facebook.setVisibility(HIDE);
				holder.chk_select.setVisibility(HIDE);
				break;
			case Const.FRIEND_MODE_INVITE_FRIEND:
				if (currentFriend.getmy_idol() == 1) {
					holder.find_friend_facebook.setVisibility(HIDE);
				} else {
					holder.find_friend_facebook.setVisibility(SHOW);
				}
				holder.find_user_subscribed.setVisibility(HIDE);
				holder.chk_select.setVisibility(HIDE);
				break;
			case Const.FRIEND_MODE_SHARE:
				holder.find_user_subscribed.setVisibility(HIDE);
				holder.find_friend_facebook.setVisibility(HIDE);
				holder.chk_select.setVisibility(SHOW);
				break;
			}
			holder.find_user_subscribed.setTag(currentFriend.friend_id);
			holder.find_user_subscribed
					.setOnClickListener(onSubscriebdListener);
			holder.find_friend_facebook
					.setOnClickListener(onSubscriebdListener);
			holder.find_friend_facebook.setTag(currentFriend.friend_id);
		}
		return friend_view;
	}

	public int GetNumberOfFriend() {
		return array_friend.size();
	}

	public void notifyListObjectChanged(ArrayList<Friend> objs) {
		if (objs != null) {
			array_friend.addAll(objs);
			notifyDataSetChanged();
		}
	}

	public OnClickListener onSubscriebdListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String friendID = v.getTag().toString();
			JsonFriends.subscribed(friendID);
			v.setVisibility(HIDE);
			Toast.makeText(mContext.getApplicationContext(), "Đăng ký quan tâm thành công", Toast.LENGTH_SHORT);
		}
	};

	public void OnResume() {
		notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView friend_avatar;
		TextView friend_name;
		TextView friend_playlist;
		Button find_user_subscribed;
		Button find_friend_facebook;
		CheckBox chk_select;
	}

}
