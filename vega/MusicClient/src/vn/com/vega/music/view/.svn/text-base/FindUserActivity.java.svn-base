package vn.com.vega.music.view;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.FriendListAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindUserActivity extends Activity {

	private static final String LOG_TAG = Const.LOG_PREF
			+ FriendActivity.class.getSimpleName();

	private RelativeLayout find_user_panel;
	private RadioGroup tab_interested_friend;
	private RelativeLayout search_layout;
	private Button btn_Addfriend;
	private Button btn_Back;
	private Button btn_FindUser;
	private Button btn_Clear;
	private ListView list_user;
	private EditText txt_find_user;
	private TextView title_view;
	private RelativeLayout loading_panel;
	private FriendListAdapter user_adapter;
	private RelativeLayout empty_layout;
	private ImageView empty_icon;
	private TextView empty_label;
	private static int TOTAL_FOUND = 0;
	private static Boolean IS_LOADING = false;
	private static String keyword = "";
	private static ProgressDialog dialog;
	private static Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.layout_activity_setting_friend);
		InitControl();
	}

	private void InitControl() {
		find_user_panel = (RelativeLayout) findViewById(R.id.find_user_panel);
		tab_interested_friend = (RadioGroup) findViewById(R.id.tab_interested_friend);
		search_layout = (RelativeLayout) findViewById(R.id.search_layout);
		btn_Addfriend = (Button) findViewById(R.id.btn_Addfriend);
		btn_Back = (Button) findViewById(R.id.btn_Back);
		list_user = (ListView) findViewById(R.id.lst_friend);
		txt_find_user = (EditText) findViewById(R.id.txt_find_user);
		btn_FindUser = (Button) findViewById(R.id.btn_find_user);
		title_view = (TextView) findViewById(R.id.feature_title_txt);
		loading_panel = (RelativeLayout) findViewById(R.id.loading_panel);
		btn_Clear = (Button) findViewById(R.id.btn_Clear_find_user);
		empty_layout = (RelativeLayout) findViewById(R.id.empty_layout);
		empty_icon = (ImageView)findViewById(R.id.empty_icon);
		empty_label = (TextView)findViewById(R.id.empty_label);
		
		dialog = new ProgressDialog(mContext);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Đang tải dữ liệu");
		
		title_view.setText(R.string.find_friend);
		find_user_panel.setVisibility(View.VISIBLE);
		tab_interested_friend.setVisibility(View.GONE);
		search_layout.setVisibility(View.GONE);
		btn_Addfriend.setVisibility(View.GONE);
		list_user.setVisibility(View.GONE);
		btn_Back.setOnClickListener(onbackListener);
		btn_FindUser.setOnClickListener(onFindListener);
		btn_Clear.setOnClickListener(onClearListener);
		txt_find_user.setOnKeyListener(ontxtFindListener);
		list_user.setOnScrollListener(onlistscroListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	protected void showEmptyLayout() {
		empty_icon.setBackgroundResource(R.drawable.ic_search_large);
		empty_label.setText(R.string.setting_friend_have_no_friend);
		empty_layout.setVisibility(View.VISIBLE);
		list_user.setVisibility(View.GONE);
	}
	
	OnScrollListener onlistscroListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if(totalItemCount > 0 & totalItemCount < TOTAL_FOUND - 1){
				if(!IS_LOADING && firstVisibleItem + visibleItemCount == totalItemCount){
					IS_LOADING = true;
					new FindUserTask().execute(totalItemCount + 1);
				}
			}
		}
	};

	OnClickListener onbackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	};

	OnClickListener onClearListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			txt_find_user.setText("");
			list_user.setVisibility(View.GONE);
			empty_layout.setVisibility(View.GONE);
		}
	};

	protected void doSearch() {
		if(NetworkUtility.hasNetworkConnection()) {
			keyword = txt_find_user.getText().toString();
			user_adapter = null;
			if (keyword.length() > 0) {
				new FindUserTask().execute(0);
		}
		
		}else {
			Toast.makeText(getApplicationContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
		}
	}

	OnClickListener onFindListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			doSearch();
		}
	};

	OnKeyListener ontxtFindListener = new OnKeyListener() {

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

	protected void SetListAdapter(ArrayList<Friend> objs) {
		if (user_adapter == null) {
			user_adapter = new FriendListAdapter(FindUserActivity.this,
					R.layout.view_listview_row_setting_friend, objs, Const.FRIEND_MODE_FIND_USER);
			list_user.setAdapter(user_adapter);
		} else
			user_adapter.notifyListObjectChanged(objs);
	}

	private class FindUserTask extends
			AsyncTask<Integer, Integer, ArrayList<Friend>> {

		@Override
		protected ArrayList<Friend> doInBackground(Integer... params) {
			ArrayList<Friend> users = new ArrayList<Friend>();
			JsonFriends json = JsonFriends.getFriendsList(
					Const.TYPE_FRIEND_CHACHA, keyword, params[0]);
			if (json.isSuccess()) {
				users.addAll(json.array_friend);
				TOTAL_FOUND = json.totalFound;
				return users;
			} else
				Log.e(LOG_TAG, json.getErrorMessage());
			return users;
		}

		@Override
		protected void onPostExecute(ArrayList<Friend> result) {
			super.onPostExecute(result);
			loading_panel.setVisibility(View.GONE);
			if (result != null) {
				if (result.size() > 0) {
					list_user.setVisibility(View.VISIBLE);
					SetListAdapter(result);
				} else
					showEmptyLayout();
			}
			IS_LOADING = false;
			dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

	}

}
