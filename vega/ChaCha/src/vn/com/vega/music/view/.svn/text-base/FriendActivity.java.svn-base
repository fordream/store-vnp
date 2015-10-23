package vn.com.vega.music.view;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FriendListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FriendActivity extends Activity {

	private static final String LOG_TAG = Const.LOG_PREF
			+ FriendActivity.class.getSimpleName();

	private Context mContext;
	private Button btn_Back;
	private Button btn_Addfriend;
	private Button btn_Clear;
	private EditText txt_find_friend;
	private ListView lst_friend;
	private RelativeLayout search_layout;
	private RelativeLayout loading_panel;
	private ImageView divider;
	private RadioGroup tab_interested_fan;
	private ImageView empty_icon;
	private TextView empty_label;
	private RelativeLayout empty_layout;
	private RelativeLayout find_user_panel;

	private FriendListAdapter friend_adapter;
	private FriendListAdapter fan_adapter;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (NetworkUtility.hasNetworkConnection())
			new FriendTask().execute(Const.TYPE_FRIEND_IDOL_LISTING, 0);
		else
			showDisconnectedView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_setting_friend);
		mContext = this;
		btn_Back = (Button) findViewById(R.id.btn_Back);
		btn_Addfriend = (Button) findViewById(R.id.btn_Addfriend);
		btn_Clear = (Button) findViewById(R.id.btn_Clear);
		txt_find_friend = (EditText) findViewById(R.id.txt_find_friend);
		lst_friend = (ListView) findViewById(R.id.lst_friend);
		lst_friend.setTextFilterEnabled(true);
		search_layout = (RelativeLayout) findViewById(R.id.search_layout);
		divider = (ImageView) findViewById(R.id.img_divider);
		tab_interested_fan = (RadioGroup) findViewById(R.id.tab_interested_friend);
		loading_panel = (RelativeLayout) findViewById(R.id.loading_panel);
		empty_icon = (ImageView) findViewById(R.id.empty_icon);
		empty_label = (TextView) findViewById(R.id.empty_label);
		empty_layout = (RelativeLayout) findViewById(R.id.empty_layout);
		find_user_panel = (RelativeLayout) findViewById(R.id.find_user_panel);
		find_user_panel.setVisibility(View.GONE);
		empty_layout.setVisibility(View.GONE);
		loading_panel.setVisibility(View.GONE);
		btn_Back.setOnClickListener(onbacklistenner);
		btn_Addfriend.setOnClickListener(onaddfriendlistenner);
		btn_Clear.setOnClickListener(onclearlistenner);
		tab_interested_fan
				.setOnCheckedChangeListener(ontabcheckedchangelistener);
		txt_find_friend.setOnKeyListener(onSearchListenner);
		empty_layout.setVisibility(View.GONE);
		LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING);
		lst_friend.setOnItemClickListener(onlistFriendListener);
	}

	protected void LoadListFriend(int TYPE) {
		if (NetworkUtility.hasNetworkConnection())
			new FriendTask().execute(TYPE, 0);
		else {
			showDisconnectedView();
		}
	}

	OnClickListener onbacklistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnItemClickListener onlistFriendListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, String.valueOf(position),
					Toast.LENGTH_SHORT).show();
		}

	};

	OnClickListener onaddfriendlistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, FindFriendActivity.class);
			startActivity(intent);
		}
	};

	OnClickListener onclearlistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			txt_find_friend.setText("");
			switch (tab_interested_fan.getCheckedRadioButtonId()) {
			case R.id.btn_Interested:
				LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING);
				break;
			case R.id.btn_Fan:
				LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING);
				break;
			}
		}
	};

	OnCheckedChangeListener ontabcheckedchangelistener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.btn_Interested:
				LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING);
				break;
			case R.id.btn_Fan:
				LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING);
				break;
			default:
				break;
			}
		}
	};

	OnKeyListener onSearchListenner = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (event.getAction() != KeyEvent.ACTION_DOWN)
				return true;
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				doSearch();
				return true;
			}
			return false;
		}
	};

	private void doSearch() {
		if (NetworkUtility.hasNetworkConnection()) {
			switch (tab_interested_fan.getCheckedRadioButtonId()) {
			case R.id.btn_Interested:
				new SearchTask().execute(Const.TYPE_FRIEND_IDOL_SEARCHING, 0);
				break;
			case R.id.btn_Fan:
				new SearchTask().execute(Const.TYPE_FRIEND_FAN_SEARCHING, 0);
				break;

			default:
				break;
			}
		} else {
			showDisconnectedView();
		}
	}

	private void showLoaddingPanel() {
		lst_friend.setVisibility(View.GONE);
		divider.setVisibility(View.GONE);
		search_layout.setVisibility(View.GONE);
		loading_panel.setVisibility(View.VISIBLE);
	}

	private void showListView() {
		lst_friend.setVisibility(View.VISIBLE);
		divider.setVisibility(View.VISIBLE);
		search_layout.setVisibility(View.VISIBLE);
		loading_panel.setVisibility(View.GONE);
	}

	private void showDisconnectedView() {
		// TODO Auto-generated method stub
		empty_icon.setBackgroundResource(R.drawable.ic_disconnected);
		empty_label.setText(R.string.search_disconnected_msg);
		empty_layout.setVisibility(View.VISIBLE);
		lst_friend.setAdapter(null);
	}

	private void showEmptyListView() {
		// TODO Auto-generated method stub
		empty_icon.setBackgroundResource(R.drawable.ic_search_large);
		empty_label.setText(R.string.setting_friend_have_no_friend);
		empty_layout.setVisibility(View.VISIBLE);
	}

	private class FriendTask extends
			AsyncTask<Integer, Integer, ArrayList<Friend>> {

		@Override
		protected void onPostExecute(ArrayList<Friend> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				if (result.size() > 0) {
					showListView();
					setListAdapter(result);
					empty_layout.setVisibility(View.GONE);
				} else {
					showEmptyListView();
				}
				loading_panel.setVisibility(View.GONE);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			friend_adapter = null;
			fan_adapter = null;
			showLoaddingPanel();
		}

		@Override
		protected ArrayList<Friend> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			ArrayList<Friend> arr = new ArrayList<Friend>();
			JsonFriends json;
			json = JsonFriends.getFriendsList(params[0], null, 0);
			if (json.isSuccess())
				arr.addAll(json.array_friend);
			else
				Log.e(LOG_TAG, json.getErrorMessage());
			return arr;
		}

	}

	private class SearchTask extends
			AsyncTask<Integer, Integer, ArrayList<Friend>> {

		@Override
		protected ArrayList<Friend> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			ArrayList<Friend> arr = new ArrayList<Friend>();
			JsonFriends json;
			String keyword = txt_find_friend.getText().toString();
			json = JsonFriends.getFriendsList(params[0], keyword, 0);
			if (json.isSuccess())
				arr.addAll(json.array_friend);
			else
				Log.e(LOG_TAG, json.getErrorMessage());
			return arr;
		}

		@Override
		protected void onPostExecute(ArrayList<Friend> result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.size() > 0) {
					showListView();
					setListAdapter(result);
					empty_layout.setVisibility(View.GONE);
				} else {
					showEmptyListView();
				}
				loading_panel.setVisibility(View.GONE);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			friend_adapter = null;
			fan_adapter = null;
			showLoaddingPanel();
		}

	}

	protected void setListAdapter(ArrayList<Friend> objs) {
		switch (tab_interested_fan.getCheckedRadioButtonId()) {
		case R.id.btn_Interested:
			if (friend_adapter == null) {
				friend_adapter = new FriendListAdapter(FriendActivity.this,
						R.layout.view_listview_row_setting_friend, objs,
						View.GONE, View.GONE);
				lst_friend.setAdapter(friend_adapter);
			} else {
				friend_adapter.notifyListObjectChanged(objs);
			}
			break;
		case R.id.btn_Fan:
			if (fan_adapter == null) {
				fan_adapter = new FriendListAdapter(FriendActivity.this,
						R.layout.view_listview_row_setting_friend, objs,
						View.GONE, View.GONE);
				lst_friend.setAdapter(fan_adapter);
			} else {
				fan_adapter.notifyListObjectChanged(objs);
			}
			break;

		default:
			break;
		}

	}

}
