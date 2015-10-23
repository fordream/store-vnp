package vn.com.vega.music.view;

import vn.com.vega.chacha.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendProfileActivity extends Activity {
	
	private TextView lblTitle;
	private TextView lblFriendName;
	private TextView lblFriendInfo;
	private LinearLayout btnSubscribe;
	private ImageView ico_button;
	private TextView lblSubscribe;
	private ImageView imgAvatar;
	private ListView playlists;
	private RelativeLayout empty_layout;
	private ImageView empty_icon;
	private TextView empty_message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_album_detail);
	}
	
	protected void InitView() {
		lblTitle = (TextView)findViewById(R.id.album_title_txt);
		lblFriendName = (TextView)findViewById(R.id.feature_album_detail_title);
		lblFriendInfo = (TextView)findViewById(R.id.feature_album_detail_info);
		btnSubscribe = (LinearLayout)findViewById(R.id.artist_info_btn);
		ico_button = (ImageView)findViewById(R.id.imgArtise);
		lblSubscribe = (TextView)findViewById(R.id.artise_detail);
		imgAvatar = (ImageView)findViewById(R.id.feature_album_detail_icon);
		playlists = (ListView)findViewById(R.id.feature_detail_listview);
		empty_layout = (RelativeLayout)findViewById(R.id.empty_layout);
		empty_icon = (ImageView)findViewById(R.id.empty_icon);
		empty_message = (TextView)findViewById(R.id.empty_label);
		
		//set default values
		lblTitle.setText("Profiles");
		btnSubscribe.setBackgroundResource(R.drawable.selector_button_subscribe_friend);
		ico_button.setVisibility(View.GONE);
		lblSubscribe.setText("+ Quan tâm");
	}
}
