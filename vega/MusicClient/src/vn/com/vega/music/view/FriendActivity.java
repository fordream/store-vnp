package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Comparator;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.social.ShareUtility;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FriendListAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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
	private static ProgressDialog progress;

	private static FriendListAdapter friend_adapter;
	private static FriendListAdapter fan_adapter;
	private static FriendListAdapter friend_search_adapter;
	private static FriendListAdapter fan_search_adapter;

	private static Boolean KEY_BACK_FROM_PROFILE;
	private static Boolean KEY_BACK_FROM_SHARE = false;
	private static Boolean KEY_IS_SHARING = false;
	private static Boolean KEY_IS_SHARE_SONG = true;
	private static Boolean KEY_IS_LOADING = false;
	private static Boolean KEY_IS_SEARCHING = false;
	private static int TOTAL_ITEM_FOUND = 0;
	private static int SHARING_OBJECT_ID = 0;
	private static String ERR_MSG = "";
	private static DataStore datastore;
	private static String MSISDN;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * Get Extra values
		 */
		Bundle b = getIntent().getExtras();
		if (b != null) {
			KEY_IS_SHARING = b.getBoolean(Const.KEY_IS_SHARING, false);
			KEY_IS_SHARE_SONG = b.getInt(Const.KEY_SHARE_TYPE,
					ShareUtility.TYPE_SONG) == ShareUtility.TYPE_SONG;
			SHARING_OBJECT_ID = b.getInt(Const.KEY_SHARE_VALUE, 0);
			if (KEY_IS_SHARING) {
				btn_Addfriend
				.setBackgroundResource(R.drawable.selector_button_share);
				btn_Addfriend.setText("Chia sẻ");
			}
		}
		if (KEY_IS_SHARING || KEY_BACK_FROM_SHARE
				|| !MSISDN.equals(datastore.getMsisdn())) {
			friend_adapter = null;
			fan_adapter = null;
		}
		if (KEY_BACK_FROM_PROFILE || !MSISDN.equals(datastore.getMsisdn())) {
			switch (tab_interested_fan.getCheckedRadioButtonId()) {
			case R.id.btn_Interested:
				if(KEY_IS_SEARCHING) {
					friend_search_adapter = null;
					LoadListFriend(Const.TYPE_FRIEND_IDOL_SEARCHING, 0, false);
				}
				else {
					friend_adapter = null;
					LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING, 0, false);
				}
				break;
			case R.id.btn_Fan:
				if(KEY_IS_SEARCHING) {
					fan_search_adapter = null;
					LoadListFriend(Const.TYPE_FRIEND_FAN_SEARCHING, 0, false);
				}
				else {
					fan_adapter = null;
					LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING, 0, false);
				}
				break;
			}
		} else
			LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING, 0, false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_setting_friend);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		datastore = DataStore.getInstance();
		MSISDN = datastore.getMsisdn();
		/**
		 * Init view
		 */
		KEY_IS_SHARING = false;
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
		progress = new ProgressDialog(mContext);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Đang tải dữ liệu....");
		/**
		 * Set events and default visible
		 */
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
		lst_friend.setOnItemClickListener(onlistFriendListener);
		KEY_BACK_FROM_PROFILE = false;
		lst_friend.setOnScrollListener(onlstFriendScrollListener);
	}

	protected void LoadListFriend(int TYPE, int offset, Boolean loadmore) {
		switch (TYPE) {
		case Const.TYPE_FRIEND_IDOL_LISTING:
			if (friend_adapter == null || loadmore)
				if (NetworkUtility.hasNetworkConnection())
					new FriendTask().execute(TYPE, offset);
				else {
					showDisconnectedView();
				}
			else {
				lst_friend.setAdapter(friend_adapter);
				friend_adapter.notifyDataSetChanged();
				showListView();
			}
			break;
		case Const.TYPE_FRIEND_FAN_LISTING:
			if (fan_adapter == null || loadmore)
				if (NetworkUtility.hasNetworkConnection())
					new FriendTask().execute(TYPE, offset);
				else {
					showDisconnectedView();
				}
			else {
				lst_friend.setAdapter(fan_adapter);
				fan_adapter.notifyDataSetChanged();
				showListView();
			}
			break;
		default:
			if (NetworkUtility.hasNetworkConnection())
				new FriendTask().execute(TYPE, offset);
			else {
				showDisconnectedView();
			}
			break;
		}
	}

	OnScrollListener onlstFriendScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if (totalItemCount > 0 && totalItemCount < TOTAL_ITEM_FOUND - 1) {
				Boolean endOfList = false;
				endOfList = (visibleItemCount + firstVisibleItem == totalItemCount) ? true
						: false;
				if (endOfList && !KEY_IS_LOADING) {
					KEY_IS_LOADING = true;
					switch (tab_interested_fan.getCheckedRadioButtonId()) {
					case R.id.btn_Interested:
						if(KEY_IS_SEARCHING)
							LoadListFriend(Const.TYPE_FRIEND_IDOL_SEARCHING, totalItemCount + 1, true);
						else
							LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING,
									totalItemCount + 1, true);
						break;
					case R.id.btn_Fan:
						if(KEY_IS_SEARCHING)
							LoadListFriend(Const.TYPE_FRIEND_FAN_SEARCHING, totalItemCount + 1, true);
						else
							LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING,
									totalItemCount + 1, true);
						break;
					}
				}
			}
		}
	};

	OnClickListener onbacklistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			KEY_IS_SEARCHING = false;
			KEY_BACK_FROM_PROFILE = false;
			onBackPressed();
		}
	};

	OnItemClickListener onlistFriendListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			// TODO Auto-generated method stub

			if(KEY_IS_SHARING){

			} else {
				Intent i = new Intent(mContext, FriendProfileActivity.class);
				Friend f = new Friend();
				switch (tab_interested_fan.getCheckedRadioButtonId()) {
				case R.id.btn_Interested:
					if(KEY_IS_SEARCHING)
						f = friend_search_adapter.getArray_friend().get(position);
					else
						f = friend_adapter.getArray_friend().get(position);
					break;
				case R.id.btn_Fan:
					if(KEY_IS_SEARCHING)
						f = fan_search_adapter.getArray_friend().get(position);
					else
						f = fan_adapter.getArray_friend().get(position);
					break;
				}
				Bundle b = new Bundle();
				b.putSerializable("friend", (Friend) f);
				i.putExtras(b);
				startActivity(i);
				KEY_BACK_FROM_PROFILE = true;
			}
		}

	};

	OnClickListener onaddfriendlistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (KEY_IS_SHARING) {
				String friendList = "";
				for (int i = 0; i < lst_friend.getCount(); i++) {
					Friend f = (Friend) lst_friend.getItemAtPosition(i);
					if (f.isChecked()) {
						friendList += String.valueOf(f.getFriend_id()) + "|";
					}
				}
				friendList = friendList.substring(0, friendList.length() - 1);
				if (friendList.length() > 0) {
					if (JsonFriends.ShareWithFriend(KEY_IS_SHARE_SONG,
							SHARING_OBJECT_ID, friendList)) {
						Toast.makeText(mContext, R.string.share_success,
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext, R.string.share_fail,
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(mContext, "Bạn chưa chọn ai để chia sẻ",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Intent intent = new Intent(mContext, FindFriendActivity.class);
				startActivity(intent);
			}
		}
	};

	OnClickListener onclearlistenner = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!txt_find_friend.getText().toString().equals("")) {
				txt_find_friend.setText("");
				KEY_IS_SEARCHING = false;
				switch (tab_interested_fan.getCheckedRadioButtonId()) {
				case R.id.btn_Interested:
					LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING, 0, false);
					break;
				case R.id.btn_Fan:
					LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING, 0, false);
					break;
				}
			}
		}
	};

	OnCheckedChangeListener ontabcheckedchangelistener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.btn_Interested:
				LoadListFriend(Const.TYPE_FRIEND_IDOL_LISTING, 0, false);
				break;
			case R.id.btn_Fan:
				LoadListFriend(Const.TYPE_FRIEND_FAN_LISTING, 0, false);
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
			KEY_IS_SEARCHING = true;
			friend_search_adapter = null;
			fan_search_adapter = null;
			switch (tab_interested_fan.getCheckedRadioButtonId()) {
			case R.id.btn_Interested:
				new FriendTask().execute(Const.TYPE_FRIEND_IDOL_SEARCHING, 0);
				break;
			case R.id.btn_Fan:
				new FriendTask().execute(Const.TYPE_FRIEND_FAN_SEARCHING, 0);
				break;
			default:
				break;
			}
		} else {
			showDisconnectedView();
		}
	}

	private void showListView() {
		lst_friend.setVisibility(View.VISIBLE);
		divider.setVisibility(View.VISIBLE);
		search_layout.setVisibility(View.VISIBLE);
		loading_panel.setVisibility(View.GONE);
		empty_layout.setVisibility(View.GONE);
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
		lst_friend.setVisibility(View.GONE);
	}

	private class FriendTask extends
	AsyncTask<Integer, Integer, ArrayList<Friend>> {

		@Override
		protected void onPostExecute(ArrayList<Friend> result) {
			// TODO Auto-generated method stub
			progress.dismiss();
			super.onPostExecute(result);
			if (ERR_MSG.equals("")) {
				if (result.size() > 0) {
					showListView();
					switch (tab_interested_fan.getCheckedRadioButtonId()) {
					case R.id.btn_Interested:
						if (KEY_IS_SEARCHING)
							friend_search_adapter = setListAdapter(result,
									friend_search_adapter);
						else
							friend_adapter = setListAdapter(result,
									friend_adapter);
						break;
					case R.id.btn_Fan:
						if (KEY_IS_SEARCHING)
							fan_search_adapter = setListAdapter(result,
									fan_search_adapter);
						else
							fan_adapter = setListAdapter(result, fan_adapter);
						break;
					}
					empty_layout.setVisibility(View.GONE);
				} else {
					showEmptyListView();
				}
			} else {
				Toast.makeText(mContext, ERR_MSG, Toast.LENGTH_SHORT).show();
				showEmptyListView();
			}
			KEY_IS_LOADING = false;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// friend_adapter = null; fan_adapter = null;

			progress.show();
			// showLoaddingPanel();
			ERR_MSG = "";
		}

		@Override
		protected ArrayList<Friend> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			ArrayList<Friend> arr = new ArrayList<Friend>();
			JsonFriends json;
			String key_word = null;
			if (KEY_IS_SEARCHING)
				key_word = txt_find_friend.getText().toString();
			json = JsonFriends.getFriendsList(params[0], key_word, params[1]);
			if (json.getErrorCode() == 0) {
				if (json.isSuccess()) {
					arr.addAll(json.array_friend);
					TOTAL_ITEM_FOUND = json.totalFound;
				} else {
					Log.e(LOG_TAG, json.getErrorMessage());
				}
			} else {
				ERR_MSG = json.getErrorMessage();
			}
			return arr;
		}

	}

	protected FriendListAdapter setListAdapter(ArrayList<Friend> objs,
			FriendListAdapter adapter) {
		if (adapter == null) {
			if (KEY_IS_SHARING) {
				KEY_BACK_FROM_SHARE = true;
				adapter = new FriendListAdapter(FriendActivity.this,
						R.layout.view_listview_row_setting_friend, objs,
						Const.FRIEND_MODE_SHARE);
			} else {
				KEY_BACK_FROM_SHARE = false;
				adapter = new FriendListAdapter(FriendActivity.this,
						R.layout.view_listview_row_setting_friend, objs,
						Const.FRIEND_MODE_DEFAULT);
			}
			lst_friend.setAdapter(adapter);
		} else {
			adapter.notifyListObjectChanged(objs);
		}
		adapter.sort(new Comparator<Friend>() {

			@Override
			public int compare(Friend lhs, Friend rhs) {
				// TODO Auto-generated method stub
				return lhs.friend_name.compareToIgnoreCase(rhs.friend_name);
			}
		});
		return adapter;
	}

}
