package vn.com.vega.music.view;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonUnregisterFriend;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.UnregisteredFriend;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.adapter.InviteFriendAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class InviteFriendActivity extends Activity {

	private static final String LOG_TAG = Const.LOG_PREF
			+ InviteFriendActivity.class.getSimpleName();
	private static final int HIDE = View.GONE;
	// private static final int SHOW = View.VISIBLE;
	private static Boolean isLoading = false;
	private static int totalFound = 0;
	private static int offset = 0;
	private static String error_message;
	private static String access_token;
	private static Facebook mFacebook;
	private static InviteFriendAdapter fb_adaper;
	private static Context mContext;
	private DataStore datastore;
	private ProgressDialog progress;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Bundle b = getIntent().getExtras();
		if(b != null) {
			totalFound = b.getInt("TOTAL_FOUND");
		}
		if(!access_token.equals("")) {
			if(!access_token.equals(datastore.getFbAccessToken())) {
				fb_adaper = null;
				access_token = datastore.getFbAccessToken();
			}
			if(fb_adaper == null) {
				mFacebook = new Facebook(Const.FACEBOOK_APP_ID);
				mFacebook.setAccessToken(access_token);
				new InviteTask().execute(Const.TYPE_FRIEND_FACEBOOK, 0);
			} else {
				listFriend.setAdapter(fb_adaper);
				fb_adaper.notifyDataSetChanged();
			}
			
		}
	}

	private TextView titleLbl;
	private Button inviteBtn;
	private RelativeLayout findUserPanel;
	private RadioGroup tabGroup;
	private ListView listFriend;
	private EditText findFriendtxt;
	private Button ClearBtn;
	private Button backBtn;
	private RelativeLayout EmptyLayout;
	private RelativeLayout searchLayout;
	private ImageView emptyIcon;
	private TextView emptyMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_setting_friend);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		datastore = DataStore.getInstance();
		InitView();
		access_token = datastore.getFbAccessToken();
	}

	protected void InitView() {
		titleLbl = (TextView) findViewById(R.id.feature_title_txt);
		inviteBtn = (Button) findViewById(R.id.btn_Addfriend);
		findUserPanel = (RelativeLayout) findViewById(R.id.find_user_panel);
		tabGroup = (RadioGroup) findViewById(R.id.tab_interested_friend);
		listFriend = (ListView) findViewById(R.id.lst_friend);
		findFriendtxt = (EditText) findViewById(R.id.txt_find_friend);
		ClearBtn = (Button) findViewById(R.id.btn_Clear);
		backBtn = (Button) findViewById(R.id.btn_Back);
		EmptyLayout = (RelativeLayout) findViewById(R.id.empty_layout);
		emptyIcon = (ImageView) findViewById(R.id.empty_icon);
		emptyMessage = (TextView) findViewById(R.id.empty_label);
		searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
		progress = new ProgressDialog(mContext);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Đang tải danh sách bạn bè....");
		// set visiblity
		EmptyLayout.setVisibility(HIDE);
		tabGroup.setVisibility(HIDE);
		findUserPanel.setVisibility(HIDE);
		emptyIcon.setVisibility(HIDE);
		emptyMessage.setVisibility(HIDE);
		searchLayout.setVisibility(HIDE);

		// set events
		backBtn.setOnClickListener(onBackListener);
		ClearBtn.setOnClickListener(onClearListener);
		inviteBtn.setOnClickListener(onInviteListener);
		titleLbl.setText("Mời bạn bè");
		inviteBtn
				.setBackgroundResource(R.drawable.selector_buton_invite_friend);
		inviteBtn.setText("Mời");
		listFriend.setOnScrollListener(onlistFriendScroll);
	}
	
	OnScrollListener onlistFriendScroll = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if(totalItemCount > 0 && totalItemCount < totalFound) {
				if(!isLoading && visibleItemCount + firstVisibleItem == totalItemCount) {
					new InviteTask().execute(Const.TYPE_FRIEND_FACEBOOK, offset);
					isLoading = true;
				}
			}
		}
	};

	android.view.View.OnClickListener onInviteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String facebooklist = "";
			for (int i = 0; i < fb_adaper.getCount(); i++) {
				if (fb_adaper.getItem(i).getSelected()) {
					String facebookID = fb_adaper.getItem(i).getId();
					facebooklist += facebookID + ",";
				}
			}
			if (facebooklist.length() > 0) {
				facebooklist.substring(0, facebooklist.length() - 1);
				SendRequest("Mời bạn tham gia Chacha", facebooklist);
			} else
				SendRequest("Mời bạn tham gia Chacha", null);
			// PostToWall("Tham gia chacha ngay - Rạp hát di động trên dế yêu T_T");
		}
	};

	public void SendRequest(String message, String to) {
		Bundle params = new Bundle();
		params.putString("message", message);
		if (to != null) {
			params.putString("to", to);
		}
		if (mFacebook.isSessionValid()) {
			mFacebook.dialog(mContext, "apprequests", params,
					new AppRequestsListener());
		} else {
			Toast.makeText(mContext, "Chưa đăng nhập Facebook",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void PostToWall(String message) {
		Bundle params = new Bundle();
		params.putString("message", message);
		params.putString("name", "Chacha - Rạp hát di động");
		params.putString(
				"description",
				"Kho nhạc chất lượng cao, cập nhật liên tục; Dễ dàng chia sẻ qua Facebook, Email, SMS; Đồng bộ kho nhạc về điện thoại để nghe Offline; Hỗ trợ tải nhanh, thuận tiện");
		params.putString("link", "http://beta.chacha.vn/");

		if (mFacebook.isSessionValid()) {
			mFacebook.dialog(mContext, "feed", params,
					new UpdateStatusListener());
		} else {
			Toast.makeText(mContext, "Chưa đăng nhập Facebook",
					Toast.LENGTH_SHORT).show();
		}
	}

	OnClickListener onBackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnClickListener onClearListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			findFriendtxt.setText("");
		}
	};

	OnKeyListener onSearchFieldListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() != KeyEvent.ACTION_DOWN)
				return true;
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				// do something here
				return true;
			}
			return false;
		}
	};

	/*
	 * callback for the apprequests dialog which sends an app request to user's
	 * friends.
	 */
	public class AppRequestsListener implements
			com.facebook.android.Facebook.DialogListener {
		@Override
		public void onComplete(Bundle values) {
			String request = values.getString("request");
			if(request != null){
				Toast toast = Toast.makeText(getApplicationContext(),
						"Gửi lời mời thành công", Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(getApplicationContext(),
					"Facebook Error: " + error.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

		@Override
		public void onCancel() {
			
			 Toast toast = Toast.makeText(getApplicationContext(),
			 "App request cancelled", Toast.LENGTH_SHORT); toast.show();
			 
		}

		@Override
		public void onError(DialogError e) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Facebook Error: " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public class UpdateStatusListener implements
			com.facebook.android.Facebook.DialogListener {
		@Override
		public void onComplete(Bundle values) {
			final String postId = values.getString("post_id");
			if (postId != null) {
				Toast toast = Toast.makeText(mContext,
						"Viết lên tường thành công!", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast.makeText(mContext,
						"Không có thông báo nào được viết lên tường!",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(mContext, "Facebook Error: " + error.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast toast = Toast.makeText(mContext, "Update status cancelled",
					Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		public void onError(DialogError e) {
			// TODO Auto-generated method stub

		}
	}

	private class InviteTask extends
			AsyncTask<Integer, Integer, ArrayList<UnregisteredFriend>> {

		@Override
		protected void onPostExecute(ArrayList<UnregisteredFriend> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(error_message.equals("")) {
				if (result != null && result.size() > 0) {
					setListAdapter(result);
				}
			}
			else {
				Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
			}
			progress.dismiss();
			isLoading = false;
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
			progress.show();
			error_message = "";
		}

		@Override
		protected ArrayList<UnregisteredFriend> doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			ArrayList<UnregisteredFriend> arr = new ArrayList<UnregisteredFriend>();
			JsonUnregisterFriend json;
			json = JsonUnregisterFriend.GetFBFriendList(params[0], access_token, params[1]);
			if(json.getErrorCode() != 0) {
				error_message = json.getErrorMessage();
			}
			if (json.isSuccess() && json.friendlist != null) {
				arr.addAll(json.friendlist);
				offset = json.offset;
			} else {
				Log.e(LOG_TAG, json.getErrorMessage());
			}
			return arr;
		}

	}

	protected void setListAdapter(ArrayList<UnregisteredFriend> objs) {
		if (fb_adaper == null) {
			fb_adaper = new InviteFriendAdapter(mContext,
					R.layout.view_listview_row_invite_friend, objs);
			listFriend.setAdapter(fb_adaper);
		} else
			fb_adaper.notifyListObjectChanged(objs);
	}

}
