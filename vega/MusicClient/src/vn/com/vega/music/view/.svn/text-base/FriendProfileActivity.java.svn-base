package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.List;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonFriends;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.objects.Friend;
import vn.com.vega.music.view.adapter.FeaturePlaylistAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendProfileActivity extends Activity {

	private TextView lblTitle;
	private Button backBtn;
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
	private static Friend friend;
	private ImageLoader mImageLoader;
	private LinearLayout loadingPanel;
	private static FeaturePlaylistAdapter playlist_adapter;
	private static List<Object> playlist;

	private static final String SUBSCRIBSE = "+ Quan tâm";
	private static final String UNSUBSCRIBSE = "- Quan tâm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_album_detail);
		mImageLoader = ImageLoader.getInstance(this.getApplicationContext());
		friend = new Friend();
		Bundle b = new Bundle();
		b = getIntent().getExtras();
		friend = (Friend) b.getSerializable("friend");
		InitView();
	}

	protected void InitView() {
		lblTitle = (TextView) findViewById(R.id.album_title_txt);
		backBtn = (Button) findViewById(R.id.album_detail_back_btn);
		lblFriendName = (TextView) findViewById(R.id.feature_album_detail_title);
		lblFriendInfo = (TextView) findViewById(R.id.feature_album_detail_info);
		btnSubscribe = (LinearLayout) findViewById(R.id.artist_info_btn);
		ico_button = (ImageView) findViewById(R.id.imgArtise);
		lblSubscribe = (TextView) findViewById(R.id.artise_detail);
		imgAvatar = (ImageView) findViewById(R.id.feature_album_detail_icon);
		playlists = (ListView) findViewById(R.id.feature_detail_listview);
		empty_layout = (RelativeLayout) findViewById(R.id.empty_layout);
		empty_icon = (ImageView) findViewById(R.id.empty_icon);
		empty_message = (TextView) findViewById(R.id.empty_label);
		loadingPanel = (LinearLayout)findViewById(R.id.feature_detail_progressbar);
		playlist = new ArrayList<Object>();
		// set default values
		lblTitle.setText("Profiles");
		btnSubscribe
				.setBackgroundResource(R.drawable.selector_button_subscribe_friend);
		ico_button.setVisibility(View.GONE);
		if (friend.getmy_idol() == 1) {
			lblSubscribe.setText(UNSUBSCRIBSE);
		} else {
			lblSubscribe.setText(SUBSCRIBSE);
		}
		lblSubscribe.setPadding(8, 0, 0, 0);
		backBtn.setOnClickListener(onBackListener);
		lblFriendInfo.setMaxWidth(20);
		btnSubscribe.setOnClickListener(onSubcribseListener);
		View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.view_listview_empty_footer, null, false);
		playlists.addHeaderView(footerView);
		playlists.addFooterView(footerView);
		loadingPanel.setVisibility(View.GONE);
		lblFriendName.setText(friend.getFriend_name());
		lblFriendInfo.setText(friend.getFriend_info());
		lblFriendInfo.setHorizontallyScrolling(true);
		lblFriendInfo.setEllipsize(TruncateAt.MARQUEE);
		lblFriendInfo.setSingleLine(true);
		lblFriendInfo.setSelected(true);
		mImageLoader.DisplayImage(friend.getFriend_avatar(),
				FriendProfileActivity.this, imgAvatar, ImageLoader.TYPE_ARTIST);
		playlist.addAll(friend.getFriend_playlist());
		playlist_adapter = new FeaturePlaylistAdapter(
				FriendProfileActivity.this, playlist, false);
		playlists.setAdapter(playlist_adapter);
	}

	protected void showEmptyLayout() {
		empty_icon.setVisibility(View.VISIBLE);
		empty_message.setVisibility(View.VISIBLE);
		empty_layout.setVisibility(View.VISIBLE);
	}

	OnClickListener onBackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnClickListener onSubcribseListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String text = lblSubscribe.getText().toString();
			if (text.equals(SUBSCRIBSE)) {
				JsonFriends.subscribed(String.valueOf(friend.getFriend_id()));
				lblSubscribe.setText(UNSUBSCRIBSE);
				friend.setmy_idol(1);
			} else {
				JsonFriends.unsubscribed(String.valueOf(friend.getFriend_id()));
				lblSubscribe.setText(SUBSCRIBSE);
				friend.setmy_idol(0);
			}
		}
	};
}
