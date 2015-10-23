package vn.com.vega.music.view;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonNotification;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.objects.Notification;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class NotifyConfigActivity extends Activity {
	
	private Button backBtn;
	private Context mContext;
	private CheckBox newPlaylist_chk, likePlaylist_chk, serverUpdate_chk, sharing_chk, artistUpdate_chk;
	private DataStore dataStore;
	private ProgressDialog pd;
	private Notification notif;
	
	private static final int SEND_SUCCESS = 0;
	private static final int SEND_FAILED = 1;
	private static final int GET_SUCCESS = 2;
	private static final int GET_FAILED = 3;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		dataStore = DataStore.getInstance();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_notify_config);
		
		initView();
	}

	private void initView(){
		backBtn = (Button)findViewById(R.id.acc_setting_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);
		newPlaylist_chk = (CheckBox)findViewById(R.id.first_chk);
		likePlaylist_chk = (CheckBox)findViewById(R.id.second_chk);
		serverUpdate_chk = (CheckBox)findViewById(R.id.third_chk);
		sharing_chk = (CheckBox)findViewById(R.id.fourth_chk);
		artistUpdate_chk = (CheckBox)findViewById(R.id.fifth_chk);
		
		newPlaylist_chk.setOnClickListener(onNewPlaylistChkListener);
		likePlaylist_chk.setOnClickListener(onLikePlaylistChkListener);
		serverUpdate_chk.setOnClickListener(onServerUpdateChkListener);
		sharing_chk.setOnClickListener(onSharingChkListener);
		artistUpdate_chk.setOnClickListener(onArtistChkListener);
		
		
		
		getFromServer();
		
	}
	
	private void updateView(){
		if(notif != null){
			newPlaylist_chk.setChecked(notif.email_on_new_pl);
			likePlaylist_chk.setChecked(notif.email_on_like_pl);
			serverUpdate_chk.setChecked(notif.email_on_server_news);
			sharing_chk.setChecked(notif.sms_on_shared_to_me);
			artistUpdate_chk.setChecked(notif.sms_on_artist_updated);
		}
	}
	
	OnClickListener onNewPlaylistChkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean flag = newPlaylist_chk.isChecked();
			if (flag) {
				dataStore.setNotifyWhenNewPlaylistMode(true);

			} else {
				dataStore.setNotifyWhenNewPlaylistMode(false);
			}
		}
	};
	OnClickListener onLikePlaylistChkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean flag = likePlaylist_chk.isChecked();
			if (flag) {
				dataStore.setNotifyWhenLikePlaylistMode(true);

			} else {
				dataStore.setNotifyWhenLikePlaylistMode(false);
			}
		}
	};
	OnClickListener onServerUpdateChkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dataStore.setNotifyWhenServerUpdateMode(serverUpdate_chk.isChecked());
		}
	};
	OnClickListener onSharingChkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean flag = sharing_chk.isChecked();
			if (flag) {
				dataStore.setNotifyWhenSharingMode(true);

			} else {
				dataStore.setNotifyWhenSharingMode(false);
			}
		}
	};
	OnClickListener onArtistChkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			boolean flag = artistUpdate_chk.isChecked();
			if (flag) {
				dataStore.setNotifyWhenArtistUpdateMode(true);

			} else {
				dataStore.setNotifyWhenArtistUpdateMode(false);
			}
		}
	};
	
	
	OnClickListener onBackBtnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//send to server here
			sendToServer();
			//onBackPressed();
		}
	};
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	       
	    	//send to server here
	    	sendToServer();
	    	
	    }
	    return true;
	}
	
	private int convertBoolToInt(boolean value){
		if(value)
			return 1;
		else
			return 0;
	}
	
	private void getFromServer(){
		pd = ProgressDialog.show(mContext, "", getString(R.string.loading), true);

		Thread dataInitializationThread = new Thread() {
			public void run() {
				JsonNotification jau = JsonNotification.getNotificationConfig();
				if (jau.isSuccess()) {
					notif = jau.notif;
					mHandler.sendEmptyMessage(GET_SUCCESS);
				} else
					mHandler.sendEmptyMessage(GET_FAILED);
			}
		};
		dataInitializationThread.start();
	}
	
	private void sendToServer(){
		pd = ProgressDialog.show(mContext, "", getString(R.string.loading), true);

		Thread dataInitializationThread = new Thread() {
			public void run() {
				JsonNotification jau = JsonNotification.updateNotificationConfig(convertBoolToInt(dataStore.isAllowingNotify_NewPlaylist()), convertBoolToInt(dataStore.isAllowingNotify_LikePlaylist()), 
						convertBoolToInt(dataStore.isAllowingNotify_ServerUpdate()), convertBoolToInt(dataStore.isAllowingNotify_Sharing()), convertBoolToInt(dataStore.isAllowingNotify_ArtistUpdate()));
				if (jau.isSuccess()) {
					mHandler.sendEmptyMessage(SEND_SUCCESS);
				} else
					mHandler.sendEmptyMessage(SEND_FAILED);
			}
		};
		dataInitializationThread.start();
	}
	// handle init data result
		Handler mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				pd.dismiss();
				switch (msg.what) {
				case SEND_SUCCESS:
					Toast.makeText(mContext, "Đã lưu thay đổi", Toast.LENGTH_SHORT).show();
					onBackPressed();
					break;
				case SEND_FAILED:
					// do stuffs
					Toast.makeText(mContext, "Chưa lưu thay đổi", Toast.LENGTH_SHORT).show();
					onBackPressed();
					break;
				case GET_SUCCESS:
					updateView();
					break;
				case GET_FAILED:
					newPlaylist_chk.setChecked(dataStore.isAllowingNotify_NewPlaylist());
					likePlaylist_chk.setChecked(dataStore.isAllowingNotify_LikePlaylist());
					serverUpdate_chk.setChecked(dataStore.isAllowingNotify_ServerUpdate());
					sharing_chk.setChecked(dataStore.isAllowingNotify_Sharing());
					artistUpdate_chk.setChecked(dataStore.isAllowingNotify_ArtistUpdate());
					break;
				}
			}
		};
	
}
