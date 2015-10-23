package vn.com.vega.music.view.holder;

import java.util.ArrayList;

import com.google.android.c2dm.C2DMessaging;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.ClientServer;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonBase;
import vn.com.vega.music.clientserver.JsonPackage;
import vn.com.vega.music.clientserver.ServerSessionInvalidListener;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.network.NetworkStatusListener;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Package;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.syncmanager.SynchronizeManager;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.LoginActivity;
import vn.com.vega.music.view.MyPlaylistActivity;
import vn.com.vega.music.view.NowPlayingActivity;
import vn.com.vega.music.view.SettingActivity;
import vn.com.vega.music.view.WelcomeActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class TabHolder extends TabActivity implements NetworkStatusListener,
		OnClickListener, ServerSessionInvalidListener {
	private final String TAB_MUSIC = "music";

	private final String TAB_SEARCH = "search";
	private final String TAB_PLAYER = "player";
	private final String TAB_MY_PLAYLIST = "my playlist";
	private final String TAB_SETTING = "setting";

	private static final String C2DM_GMAIL = "chachaclient@gmail.com";

	private ProgressDialog mProgressDialog = null;

	private static LinearLayout tabControls;
	private Context _context;

	private static final int SUBSCRIBE_SUCCESS = 0;

	private static final int SEND_SMS = 1;

	private static final int SHOW_MSG_DIALOG = 2;
	private static final int SHOW_PACKAGE_DIALOG = 3;
	private static final int SHIT_ERROR = 4;
	private static final int LOGOUT_SUCCESS = 5;

	private String mMessage;
	private ArrayList<Package> mPackages;

	private boolean isFirstTime = true;
	private boolean expireDialogIsShowing = false;

	private BroadcastReceiver mRececiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equalsIgnoreCase(Const.ACTION_DIMISS_DIALOG)) {
				// dismiss progress dialog
				if (mProgressDialog != null)
					mProgressDialog.dismiss();
			}

		}
	};

	private ArrayList<MyTabHolder> mTabHolders = new ArrayList<TabHolder.MyTabHolder>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_context = this;
		setTheme(R.style.theme_no_title_bar);
		setContentView(R.layout.layout_activity_tab_holder);
		tabControls = (LinearLayout) findViewById(R.id.tab_holder_bottom_region);
		final TabHost _tabHost = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec musicTabSpec = _tabHost.newTabSpec(TAB_MUSIC);
		TabSpec searchTabSpec = _tabHost.newTabSpec(TAB_SEARCH);
		// TabSpec playTabSpec = _tabHost.newTabSpec(TAB_PLAY);
		TabSpec myPlaylistTabSpec = _tabHost.newTabSpec(TAB_MY_PLAYLIST);
		TabSpec settingTabSpec = _tabHost.newTabSpec(TAB_SETTING);
		musicTabSpec.setIndicator(TAB_MUSIC,
				getResources().getDrawable(R.drawable.ic_app));
		searchTabSpec.setIndicator(TAB_SEARCH,
				getResources().getDrawable(R.drawable.ic_app));
		// playTabSpec.setIndicator(TAB_PLAY,
		// getResources().getDrawable(R.drawable.icon));
		myPlaylistTabSpec.setIndicator(TAB_MY_PLAYLIST, getResources()
				.getDrawable(R.drawable.ic_app));
		settingTabSpec.setIndicator(TAB_SETTING,
				getResources().getDrawable(R.drawable.ic_app));

		musicTabSpec.setContent(new Intent(_context, FeatureTabHolder.class));
		searchTabSpec.setContent(new Intent(_context, SearchTabHolder.class));
		// playTabSpec.setContent(new Intent(_context,
		// NowPlayingActivity.class));
		myPlaylistTabSpec
				.setContent(new Intent(_context, MyPlaylistStack.class));
		settingTabSpec.setContent(new Intent(_context, SettingActivity.class));

		/* Add tabSpec to the TabHost to display. */
		_tabHost.addTab(musicTabSpec);
		_tabHost.addTab(searchTabSpec);
		_tabHost.addTab(myPlaylistTabSpec);
		_tabHost.addTab(settingTabSpec);

		addTab(TAB_MUSIC, R.id.tab_holder_music_btn, R.id.holder_music,
				R.id.tab_holder_music_txt);
		addTab(TAB_SEARCH, R.id.tab_holder_search_btn, R.id.holder_search,
				R.id.tab_holder_search_txt);
		addTab(TAB_PLAYER, R.id.tab_holder_play_btn, R.id.holder_play, -1);
		addTab(TAB_MY_PLAYLIST, R.id.tab_holder_my_playlist_btn,
				R.id.holder_playlist, R.id.tab_holder_my_playlist_txt);
		addTab(TAB_SETTING, R.id.tab_holder_setting_btn, R.id.holder_personal,
				R.id.tab_holder_personal_txt);

		// Set music tab is default
		MyTabHolder musictab = mTabHolders.get(0);
		musictab.background.setSelected(true);
		musictab.button.setSelected(true);
		if (musictab.title != null)
			musictab.title.setTextColor(Color.parseColor("#ffffff"));
		getTabHost().setCurrentTabByTag(musictab.tab_id);

		// begin sync thread
		SynchronizeManager.startSyncThread();

		// c2dm
		C2DMessaging.register(this, C2DM_GMAIL);

		// start receiving network status
		NetworkUtility.registerNetworkNotification(this);
		// add network status listener
		NetworkUtility
				.addNetworkStatusListener(TabHolder.class.getName(), this);
		// register receiver
		_context.registerReceiver(mRececiver, new IntentFilter(
				Const.ACTION_DIMISS_DIALOG));

		JsonBase.setServerSessionInvalidListener(this);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// remove network status listener
		NetworkUtility.removeNetworkStatusListener("TabActivity");
		// stop receiving network status
		NetworkUtility.unregisterNetworkNotification(this);
		// unregister receiver
		_context.unregisterReceiver(mRececiver);
	}

	private static class MyTabHolder {
		public String tab_id;

		public Button button;
		public LinearLayout background;

		public TextView title;
	}

	private void addTab(String tabId, int buttonId, int backgroundId, int textId) {
		MyTabHolder mth = new MyTabHolder();
		mth.tab_id = tabId;
		mth.button = (Button) findViewById(buttonId);
		if (textId != -1)
			mth.title = (TextView) findViewById(textId);
		mth.button.setOnClickListener(this);
		mth.background = (LinearLayout) findViewById(backgroundId);
		mth.background.setOnClickListener(this);
		mTabHolders.add(mth);
	}

	@Override
	public void onClick(View v) {
		String currentTag = getTabHost().getCurrentTabTag();
		int viewId = v.getId();
		for (MyTabHolder tab : mTabHolders) {
			if ((tab.button.getId() == viewId)
					|| (tab.background.getId() == viewId)) {
				if ((currentTag == null) || !currentTag.equals(tab.tab_id)) {
					if (tab.tab_id.equals(TAB_PLAYER)) {
						// Special code for player tab

						if (!NowPlayingList.isEmpty()) {
							Intent _i = new Intent(getApplicationContext(),
									NowPlayingActivity.class)
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
											| Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(_i);
							// overridePendingTransition(
							// R.anim.slide_bottom_to_top, R.anim.hold);
						} else {
							if (NowPlayingList.loadNowPlayingList()) {
								Intent _i = new Intent(getApplicationContext(),
										NowPlayingActivity.class)
										.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
												| Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(_i);
								// overridePendingTransition(
								// R.anim.slide_bottom_to_top, R.anim.hold);
							}
						}

					} else {
						// reset all other tabs
						for (MyTabHolder otab : mTabHolders) {
							if (otab != tab) {
								otab.background.setSelected(false);
								otab.button.setSelected(false);
								if (otab.title != null)
									otab.title.setTextColor(Color
											.parseColor("#808080"));
							}
						}

						tab.background.setSelected(true);
						tab.button.setSelected(true);
						if (tab.title != null)
							tab.title.setTextColor(Color.parseColor("#ffffff"));
						getTabHost().setCurrentTabByTag(tab.tab_id);
					}
				}
				break;
			}
		}
	}

	public static void setControlsVisibility(boolean status) {
		if (status)
			tabControls.setVisibility(LinearLayout.VISIBLE);
		else
			tabControls.setVisibility(LinearLayout.GONE);
	}

	private void loginAgain() {

		synchronized (TabHolder.class) {
			if ((WelcomeActivity.mLock != null)
					&& WelcomeActivity.mLock.isRunning()) {
				return; // It's aready running
			}
			// for sure
			DownloadManager.stopDownload();

			// WelcomeActivity ws = new WelcomeActivity();
			WelcomeActivity.mLock = new WelcomeActivity();

			WelcomeActivity.mLock.setContext(_context);
			WelcomeActivity.mLock.enableThreadMode();
			Thread thread = new Thread(WelcomeActivity.mLock);
			thread.start();
		}

	}

	@Override
	public void onNetworkUnavailable() {

	}

	@Override
	public void onNetworkAvailable() {
		if (NetworkUtility.hasNetworkConnection())
			loginAgain();
	}

	@Override
	public void onNetworkChange() {

		// loginAgain();

		/*
		 * NetworkUtility.removeAllNetworkStatusListener();
		 * 
		 * int network = NetworkUtility.getNetworkStatus();
		 * 
		 * if (network == NetworkUtility.CONNECTION_TYPE_3G) { // Wifi -> 3G
		 * loginAgain(); }
		 * 
		 * else { // 3G -> Wifi
		 * 
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { JsonAuth.logout();
		 * mHandler.sendEmptyMessage(LOGOUT_SUCCESS); } }).start();
		 * 
		 * }
		 */
	}

	@Override
	public void onLoginAgain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValidateAgain() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onPowerExpire(final String preferred_package_code,
			final String msg) {
		// TODO Auto-generated method stub
		if (!expireDialogIsShowing) {
			expireDialogIsShowing = true;

			new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						mMessage = msg;

						// Stop download
						DownloadManager.stopDownload();
						// Stop MusicPlayer
						Song playingSong = NowPlayingList.getPlaylingSong();

						if (playingSong != null
								&& !playingSong.isAvailableLocally()
								&& playingSong.isLocalSong())
							MusicPlayer.stopMusicPlayer();

						// get list of package
						ArrayList<Package> packages = new ArrayList<Package>();
						ArrayList<Package> availablePackages = new ArrayList<Package>();
						DataStore dataStore = DataStore.getInstance();
						String currPackageCode = dataStore
								.getConfig(Const.KEY_PACKAGE_CODE);
						JsonPackage jsonPackage = JsonPackage.loadPackageList();
						packages = jsonPackage.packages;
						for (Package myPackage : packages) {
							if (!myPackage.code
									.equalsIgnoreCase(currPackageCode)) {
								availablePackages.add(myPackage);
							}
						}

						if (preferred_package_code.equals("")) {
							if (availablePackages.size() == 0) {
								// show message and logout
								// showPowerExpireMsg(msg);
								mHandler.sendEmptyMessage(SHOW_MSG_DIALOG);
							} else {
								// show message and available package
								// showPowerExpireMsg(msg, availablePackages);
								mPackages = availablePackages;
								mHandler.sendEmptyMessage(SHOW_PACKAGE_DIALOG);
							}
						} else {
							// show message and preferred package
							if (availablePackages.size() == 0) {
								// create default package to show
								Package myPackage = new Package();
								myPackage.name = "CHACHA CLOUD";
								myPackage.code = preferred_package_code;
								myPackage.price = "";
								availablePackages.add(myPackage);
								// showPowerExpireMsg(msg, availablePackages);
								mPackages = availablePackages;
								mHandler.sendEmptyMessage(SHOW_PACKAGE_DIALOG);
							} else {
								// get full package's info to show
								ArrayList<Package> temp = new ArrayList<Package>();
								boolean isExist = false;
								for (Package myPackage : availablePackages) {
									if (myPackage.code
											.equalsIgnoreCase(preferred_package_code)) {
										// myPackage
										temp.add(myPackage);
										isExist = true;
										break;
									}
								}
								if (!isExist) {
									// create default package to show
									Package myPackage = new Package();
									myPackage.name = "CHACHA CLOUD";
									myPackage.code = preferred_package_code;
									myPackage.price = "";
									temp.add(myPackage);
									// myPackage
								}

								// showPowerExpireMsg(msg, temp);
								mPackages = temp;
								mHandler.sendEmptyMessage(SHOW_PACKAGE_DIALOG);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						// Log.d("402", e.getMessage());
						mHandler.sendEmptyMessage(SHIT_ERROR);

					}

				}
			}).start();

		}

	}

	private void showPowerExpireMsg(String msg,
			final ArrayList<Package> packages) {

		expireDialogIsShowing = true;

		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(_context);
			builder.setTitle("Thông báo")
					.setMessage(msg)
					.setCancelable(false)
					.setNegativeButton(
							_context.getString(R.string.change_package_ok),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
									showPackageDialog(packages);
								}
							})
					.setPositiveButton(
							_context.getString(R.string.change_package_cancel),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
									expireDialogIsShowing = false;
									/*
									 * Intent intent = new Intent(_context,
									 * LoginActivity.class)
									 * .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									 * ); _context.startActivity(intent);
									 * finish();
									 */

								}
							});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("402", e.getMessage());
			expireDialogIsShowing = false;
		}

	}

	private void showPowerExpireMsg(String msg) {

		expireDialogIsShowing = true;

		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(_context);
			builder.setMessage(msg)
					.setCancelable(false)
					.setNegativeButton(
							_context.getString(R.string.change_package_cancel),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									/*
									 * Intent intent = new Intent(_context,
									 * LoginActivity.class)
									 * .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									 * ); _context.startActivity(intent);
									 * finish();
									 */
									dialog.dismiss();

									expireDialogIsShowing = false;

								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("402", e.getMessage());
			expireDialogIsShowing = false;
		}

	}

	private void sendSMS(String phoneNumber, String message) {

		// call message composer

		Uri uri = Uri.parse("smsto:" + phoneNumber);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", message);
		startActivityForResult(it, SEND_SMS);
	}

	private class DialogAdapterEx extends BaseAdapter {
		private LayoutInflater mInflater;
		private ArrayList<Package> packages;

		class ViewHolder {
			TextView first_text;
			TextView second_text;
			ImageView image;
			ImageView arrow;

		}

		public DialogAdapterEx(Context context, ArrayList<Package> _packages) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			packages = _packages;
		}

		@Override
		public int getCount() {
			return packages.size();
		}

		@Override
		public Object getItem(int position) {
			return packages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.view_listview_row_context_menu, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_thumbnail_img);
				holder.arrow = (ImageView) convertView
						.findViewById(R.id.common_context_menu_row_arrow_img);
				holder.first_text = (TextView) convertView
						.findViewById(R.id.common_context_menu_row_first_txt);
				holder.second_text = (TextView) convertView
						.findViewById(R.id.common_context_menu_row_second_txt);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Package currPackage = packages.get(position);
			// holder.third_text.setVisibility(TextView.VISIBLE);
			holder.image.setVisibility(ImageView.VISIBLE);
			holder.image.setBackgroundResource(R.drawable.ic_package);
			holder.arrow.setVisibility(ImageView.VISIBLE);
			holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow);
			holder.second_text.setVisibility(TextView.VISIBLE);
			holder.first_text.setText(currPackage.name);
			holder.second_text.setText(currPackage.price);

			return convertView;
		}

	}

	private Dialog dialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case SUBSCRIBE_SUCCESS:
					mProgressDialog.dismiss();
					Toast.makeText(_context,
							"Bạn đã đăng ký thành công gói dịch vụ mới",
							Toast.LENGTH_SHORT).show();
					expireDialogIsShowing = false;
					break;
				case SEND_SMS:
					mProgressDialog.dismiss();
					expireDialogIsShowing = false;
					break;
				case SHOW_MSG_DIALOG:
					showPowerExpireMsg(mMessage);
					break;
				case SHOW_PACKAGE_DIALOG:
					showPowerExpireMsg(mMessage, mPackages);
					break;
				case SHIT_ERROR:

					expireDialogIsShowing = false;
					break;
				case LOGOUT_SUCCESS:

					JsonBase.clearServerSessionInvalidListener();
					// Stop download thread
					DownloadManager.stopDownload();
					// Stop sync thread
					SynchronizeManager.stopSyncThread();
					// Stop MusicPlayer
					MusicPlayer.stopMusicPlayer();
					// logout
					JsonAuth.logout();
					Bundle bundle = new Bundle();
					bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, true);
					Intent login = new Intent(_context, LoginActivity.class);
					login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_SINGLE_TOP);
					login.putExtras(bundle);
					startActivity(login);
					finish();
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("402", e.getMessage());
				expireDialogIsShowing = false;
			}

		}
	};

	public void showPackageDialog(ArrayList<Package> packages) {
		dialog = new Dialog(_context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_context_menu);
		dialog.setCancelable(true);
		ListView _listview = (ListView) dialog
				.findViewById(R.id.common_context_menu_listview);
		Button _closeBtn = (Button) dialog
				.findViewById(R.id.common_context_menu_close_btn);
		_closeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				expireDialogIsShowing = false;

			}
		});
		DialogAdapterEx _myAdapter = new DialogAdapterEx(_context, packages);
		_listview.setAdapter(_myAdapter);

		TextView _title = (TextView) dialog
				.findViewById(R.id.common_context_menu_title_txt);
		_title.setText("Các gói dịch vụ mới");
		dialog.show();
		_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Package selectedPackage = (Package) parent
						.getItemAtPosition(position);
				dialog.dismiss();
				mProgressDialog = new ProgressDialog(_context);
				mProgressDialog
						.setMessage(getString(R.string.login_screen_registering));
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						DataStore dataStore = DataStore.getInstance();
						JsonAuth jsonAuth = JsonAuth.subscribe(
								selectedPackage.code, dataStore
										.getConfig(Const.KEY_TEMP_PHONE_NUMBER));
						if (!jsonAuth.isSuccess()) {

							sendSMS(selectedPackage.smsServer,
									selectedPackage.smsContent);
							mHandler.sendEmptyMessage(SEND_SMS);
						} else {
							// success

							dataStore.updateAccountConfig(jsonAuth.hashAccount);
							mHandler.sendEmptyMessage(SUBSCRIBE_SUCCESS);
						}

					}
				}).start();
			}
		});
	}

}
