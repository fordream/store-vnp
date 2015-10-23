package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Comparator;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FriendListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindFriendFacebookActivity extends Activity {

	private static Context mContext;

	private static int TYPE;
	private DataStore datastore;
	private Button inviteBtn;
	private Button backBtn;
	private TextView non_chacha_memer;
	private TextView chacha_member;
	private ListView lst_Friend;
	private FriendListAdapter friend_adapter;
	private LinearLayout listview_panel;
	private RelativeLayout loading_panel;
	private RelativeLayout invite_panel;
	private RelativeLayout message_panel;
	private TextView message_content;
	private ImageView message_icon;
	private static int number_unregister = -1;
	private static int number_register = -1;
	private static String access_token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_find_friend_facebook);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		InitView();
		Bundle b = getIntent().getExtras();
		TYPE = b.getInt("TYPE", 6);
		access_token = datastore.getFbAccessToken();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (number_unregister < 0) {
			switch (TYPE) {
			case Const.TYPE_FRIEND_FACEBOOK:
				new FindFriendFacebookTask().execute(
						Const.TYPE_FRIEND_FACEBOOK, 0);
				break;
			case Const.TYPE_FRIEND_GMAIL:
				new FindFriendFacebookTask()
						.execute(Const.TYPE_FRIEND_GMAIL, 0);
				break;
			default:
				new FindFriendFacebookTask().execute(
						Const.TYPE_FRIEND_FACEBOOK, 0);
				break;
			}
		} else {
			inviteBtn.setEnabled(number_unregister > 0);
			non_chacha_memer.setText("Có " + number_unregister
					+ " bạn chưa đăng ký ChaCha");
			if (number_register > 0) {
				chacha_member.setText("Thành viên ChaCha (" + number_register
						+ ")");
				if (friend_adapter != null) {
					lst_Friend.setAdapter(friend_adapter);
					friend_adapter.notifyDataSetChanged();
				}
			} else {
				showEmptyView();
				message_panel.setVisibility(View.GONE);
			}
		}
	}

	protected void InitView() {
		inviteBtn = (Button) findViewById(R.id.btn_Invite);
		non_chacha_memer = (TextView) findViewById(R.id.find_friend_FB_lbl_not_member_friend);
		chacha_member = (TextView) findViewById(R.id.find_friend_FB_lbl_chacha_member);
		backBtn = (Button) findViewById(R.id.find_friend_FB_btn_Back);
		listview_panel = (LinearLayout) findViewById(R.id.find_friend_FB_listview_panel);
		loading_panel = (RelativeLayout) findViewById(R.id.find_friend_FB_loading_panel);
		invite_panel = (RelativeLayout) findViewById(R.id.find_friend_FB_invite_panel);
		message_panel = (RelativeLayout) findViewById(R.id.find_friend_FB_message_panel);
		message_content = (TextView) findViewById(R.id.message_content);
		message_icon = (ImageView) findViewById(R.id.message_icon);
		lst_Friend = (ListView) findViewById(R.id.find_friend_FB_lstFriend);
		datastore = DataStore.getInstance();

		/**
		 * Add events listener
		 */
		inviteBtn.setOnClickListener(onInviteListener);
		backBtn.setOnClickListener(onBackListener);
	}

	OnClickListener onInviteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, InviteFriendActivity.class);
			Bundle b = new Bundle();
			b.putInt("TOTAL_FOUND", number_unregister);
			intent.putExtras(b);
			startActivity(intent);
		}
	};

	OnClickListener onBackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	protected void showDisconnectView() {
		invite_panel.setVisibility(View.GONE);
		message_panel.setVisibility(View.VISIBLE);
		listview_panel.setVisibility(View.GONE);
		message_content.setText("Không thể kết nối đến Facebook");
		message_icon.setBackgroundResource(R.drawable.ic_disconnected);
	}

	protected void showEmptyView() {
		invite_panel.setVisibility(View.VISIBLE);
		listview_panel.setVisibility(View.GONE);
	}

	private class FindFriendFacebookTask extends
			AsyncTask<Integer, Integer, JsonFriends> {

		@Override
		protected JsonFriends doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			JsonFriends json = null;
			if (NetworkUtility.hasNetworkConnection()) {
				switch (params[0]) {
				case Const.TYPE_FRIEND_FACEBOOK:
					if (!access_token.equals("")) {
						json = JsonFriends.getFriendOnFacebook(params[0],
								access_token);
						return json;
					}
					break;
				}
			} else {
				showDisconnectView();
			}
			return null;
		}

		@Override
		protected void onPostExecute(JsonFriends result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loading_panel.setVisibility(View.GONE);
			invite_panel.setVisibility(View.VISIBLE);
			if (result.array_friend != null) {
				inviteBtn.setEnabled(result.unregister_friend_count > 0);
				number_unregister = result.unregister_friend_count;
				number_register = result.register_friend_count;
				non_chacha_memer.setText("Có " + number_unregister
						+ " bạn chưa đăng ký ChaCha");
				if (result.array_friend.size() > 0) {
					chacha_member.setText("Thành viên ChaCha ("
							+ number_register + ")");
					setListAdapter(result.array_friend);
					listview_panel.setVisibility(View.VISIBLE);
					message_panel.setVisibility(View.GONE);
				} else {
					showEmptyView();
				}
			} else {
				// showDisconnectView();
				onBackPressed();
				Toast.makeText(mContext, result.getErrorMessage(),
						Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			listview_panel.setVisibility(View.GONE);
			invite_panel.setVisibility(View.GONE);
			loading_panel.setVisibility(View.VISIBLE);
			message_panel.setVisibility(View.GONE);
		}

	}

	protected void setListAdapter(ArrayList<Friend> objs) {
		if (friend_adapter == null) {
			friend_adapter = new FriendListAdapter(
					FindFriendFacebookActivity.this,
					R.layout.view_listview_row_setting_friend, objs,
					Const.FRIEND_MODE_INVITE_FRIEND);
			lst_Friend.setAdapter(friend_adapter);
		} else {
			friend_adapter.notifyListObjectChanged(objs);
		}
		friend_adapter.sort(new Comparator<Friend>() {

			@Override
			public int compare(Friend lhs, Friend rhs) {
				// TODO Auto-generated method stub
				return lhs.friend_name.compareToIgnoreCase(rhs.friend_name);
			}
		});
	}

}
