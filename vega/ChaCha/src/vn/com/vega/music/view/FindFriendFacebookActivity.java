package vn.com.vega.music.view;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.FacebookFriend;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FriendListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FindFriendFacebookActivity extends Activity {

	private static ArrayList<FacebookFriend> arr_facebook;
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_find_friend_facebook);
		mContext = this;
		InitView();
		Bundle b = getIntent().getExtras();
		TYPE = b.getInt("TYPE", 6);
		switch (TYPE) {
		case Const.TYPE_FRIEND_FACEBOOK:
			new FindFriendFacebookTask().execute(Const.TYPE_FRIEND_FACEBOOK, 0);
			break;
		case Const.TYPE_FRIEND_GMAIL:
			new FindFriendFacebookTask().execute(Const.TYPE_FRIEND_GMAIL, 0);
			break;
		default:
			new FindFriendFacebookTask().execute(Const.TYPE_FRIEND_FACEBOOK, 0);
			break;
		}

	}

	protected void InitView() {
		inviteBtn = (Button) findViewById(R.id.btn_Invite);
		non_chacha_memer = (TextView) findViewById(R.id.find_friend_FB_lbl_not_member_friend);
		chacha_member = (TextView)findViewById(R.id.find_friend_FB_lbl_chacha_member);
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
			Log.d("Find friend FB activity", "Start new activity with data");
			Intent intent = new Intent(mContext, InviteFriendActivity.class);
			intent.putParcelableArrayListExtra("invitelist", arr_facebook);
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
			String access_token = "";
			if (NetworkUtility.hasNetworkConnection()) {
				switch (params[0]) {
				case Const.TYPE_FRIEND_FACEBOOK:
					access_token = datastore.getFbAccessToken();
					if (!access_token.equals("")) {
						json = JsonFriends.getInviteList(params[0],
								access_token);
						return json;
					}
					break;
				/*case Const.TYPE_FRIEND_GMAIL:
					access_token = datastore.getGmAccessToken();
					if (!access_token.equals("")) {
						json = JsonFriends.getInviteList(params[0],
								access_token);
						if (json.isSuccess()) {
							arr_friend.addAll(json.array_gmail);
						}
					}
					break;*/
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
			if(result.array_friend != null && result.array_facebook != null) {
				arr_facebook = result.array_facebook;
				non_chacha_memer.setText("Có " + arr_facebook.size()
						+ " bạn chưa đăng ký ChaCha");
				if (result.array_friend.size() > 0) {
					chacha_member.setText("Thành viên ChaCha (" + result.array_friend.size() + ")");
					setListAdapter(result.array_friend);
					listview_panel.setVisibility(View.VISIBLE);
					message_panel.setVisibility(View.GONE);
				} else
					showEmptyView();
			}
			else {
				showDisconnectView();
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
					R.layout.view_listview_row_setting_friend, objs, View.GONE,
					View.VISIBLE);
			lst_Friend.setAdapter(friend_adapter);
		} else {
			friend_adapter.notifyListObjectChanged(objs);
		}
	}

}
