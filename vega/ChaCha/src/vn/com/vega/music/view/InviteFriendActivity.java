package vn.com.vega.music.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.FacebookFriend;
import vn.com.vega.music.view.adapter.InviteFriendAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

public class InviteFriendActivity extends Activity {

	public static final String APP_ID = "170971199667899";
	private static final int HIDE = View.GONE;
	// private static final int SHOW = View.VISIBLE;

	private static Facebook mFacebook;
	private static AsyncFacebookRunner mAsyncRunner;

	public static ArrayList<FacebookFriend> fb_friend = new ArrayList<FacebookFriend>();
	private static InviteFriendAdapter fb_adaper;
	private static Context mContext;
	private DataStore datastore;

	private TextView titleLbl;
	private Button inviteBtn;
	private RelativeLayout findUserPanel;
	private RadioGroup tabGroup;
	private ListView listFriend;
	private EditText findFriendtxt;
	private Button ClearBtn;
	private Button backBtn;
	private RelativeLayout EmptyLayout;
	private ImageView emptyIcon;
	private TextView emptyMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_setting_friend);
		mContext = getApplicationContext();
		Log.d("Invite friend activity", "Get data from other activity");
		fb_friend = getIntent().getParcelableArrayListExtra("invitelist");
		datastore = DataStore.getInstance();
		InitView();
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

		// set visiblity
		EmptyLayout.setVisibility(HIDE);
		tabGroup.setVisibility(HIDE);
		findUserPanel.setVisibility(HIDE);
		emptyIcon.setVisibility(HIDE);
		emptyMessage.setVisibility(HIDE);

		// set events
		backBtn.setOnClickListener(onBackListener);
		ClearBtn.setOnClickListener(onClearListener);
		inviteBtn.setOnClickListener(onInviteListener);

		// set value
		String access_token = datastore.getFbAccessToken();
		mFacebook = new Facebook(APP_ID);
		mFacebook.setAccessToken(access_token);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		titleLbl.setText("Mời bạn bè");
		inviteBtn
				.setBackgroundResource(R.drawable.selector_buton_invite_friend);
		inviteBtn.setText("Mời");
		if (fb_adaper == null) {
			fb_adaper = new InviteFriendAdapter(mContext,
					R.layout.view_listview_row_invite_friend, fb_friend);
			listFriend.setAdapter(fb_adaper);
		} else
			fb_adaper.notifyDataSetChanged();
	}

	android.view.View.OnClickListener onInviteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for (int i = 0; i < fb_friend.size(); i++) {
				if (fb_friend.get(i).getIsChecked()) {
					String facebookID = fb_friend.get(i).getFacebookID();
					PostToWall(facebookID, "[Android test] - Mang cả rạp hát về dế yêu của bạn");
				}
			}
		}
	};
	
	/*private class InviteTask extends AsyncTask<String, Integer, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			for (int i = 0; i < fb_friend.size(); i++) {
				if (fb_friend.get(i).getIsChecked()) {
					String facebookID = fb_friend.get(i).getFacebookID();
					PostToWall(facebookID, "[Android test] - Mang cả rạp hát về dế yêu của bạn");
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(mContext, "Mời bạn bè hoàn tất", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}*/

	public void PostToWall(String friendID, String message) {
		Bundle params = new Bundle();
		params.putString("message", message);
		params.putString("name", "Chacha - Rạp hát di động");
		params.putString("description", "Kho nhạc chất lượng cao, cập nhật liên tục; Dễ dàng chia sẻ qua Facebook, Email, SMS; Đồng bộ kho nhạc về điện thoại để nghe Offline; Hỗ trợ tải nhanh, thuận tiện");
		params.putString("link", "http://beta.chacha.vn/");

		if (mFacebook.isSessionValid()) {
			mAsyncRunner.request(((friendID == null) ? "me" : friendID)
					+ "/feed", params, "POST", new WallPostRequestListener(),
					null);
			/*mAsyncRunner.request("me/feed", params, "POST", new WallPostRequestListener(),
					null);*/
		} else {
			// Toggle the button state.
			// If coming from logout transition to login (authorize).
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

	public class WallPostRequestListener implements RequestListener {

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Mời thành công", Toast.LENGTH_SHORT)
					.show();
			Log.d("Invite Friend Facebook response : ", response);
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			Toast.makeText(mContext, "Lỗi kết nối với Facebook", Toast.LENGTH_SHORT)
			.show();
		}

	}

}
