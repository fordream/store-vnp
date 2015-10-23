package vn.com.vega.music.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonBase;
import vn.com.vega.music.clientserver.ServerSessionInvalidListener;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.database.SharedProperties;
import vn.com.vega.music.device.FileStorage;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.downloadmanager.ImageLoader;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Song;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.player.NowPlayingList;
import vn.com.vega.music.syncmanager.SynchronizeManager;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SettingActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener, Const, ServerSessionInvalidListener {
	protected static final int CLEAR_CACHE_SUCCESS = 0;
	protected static final int CLEAR_CACHE_FAILED = -1;

	protected static final int GET_ACC_INFO_SUCCESS = 1;
	protected static final int GET_ACC_INFO_FAILED = 2;

	protected static final int UNSUBCRIBE_FAILED = 4;
	protected static final int UNSUBCRIBE_SUCCESS = 5;

	protected static final int REGISTERED = 6;
	protected static final int NOT_REGISTERED = 7;
	protected static final int LOGOUT_SUCCESS = 11;

	protected static final int UNLOGGED = 8;

	protected static final int VALUE_CAMERA = 0;
	protected static final int VALUE_GALLERY = 1;

	private ImageLoader mImageLoader;

	private JsonAuth identify;
	private JsonAuth unsubscribe;

	private TextView txtFullName;
	private TextView txtPhoneNumber;
	private ImageView imgAvatar;

	private Context mContext;
	private CheckBox offlineCheckBox, via3gCheckBox;
	private SeekBar totalMemorySeekBar;
	private TextView currentMemoryTextView, usedMemoryText;
	private TextView totalMemoryPercentTextView;
	private TextView totalMemoryTextView;
	private TextView totalMemoryMaxTextView;
	private TextView packageTextView;
	private TextView priceTextView;
	private int totalMemorySeekBarMinProgress;
	private RelativeLayout logoutLayout, infoLayout, friendLayout,
			clearDownloadedMusicLayout;
	private DataStore dataStore;
	private RelativeLayout logoutLayoutBorder;

	private Button uploadAvatarBtn;

	private BroadcastReceiver settingChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equalsIgnoreCase(Const.ACTION_UPDATE_SETTING_SCREEN)) {

			}
		}
	};

	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mProgressDialog != null && mProgressDialog.isShowing())
				mProgressDialog.dismiss();
			switch (msg.what) {
			case CLEAR_CACHE_SUCCESS:
				showAlert(getString(R.string.setting_clear_memory_done));

				updateUI();
				break;

			case CLEAR_CACHE_FAILED:
				showAlert(getString(R.string.setting_clear_memory_error));
				break;
			case GET_ACC_INFO_FAILED:
				if (!identify.getErrorMessage().trim().equals("")
						&& !identify.getErrorMessage().trim()
								.equalsIgnoreCase("null"))
					showAlert(identify.getErrorMessage());
				break;
			case GET_ACC_INFO_SUCCESS:
				if (!identify.accountInfo.trim().equals("")
						&& !identify.accountInfo.trim()
								.equalsIgnoreCase("null"))
					showAlert(identify.accountInfo);
				break;
			case UNSUBCRIBE_FAILED:
				if (!unsubscribe.getErrorMessage().trim().equals(""))
					showAlert(unsubscribe.getErrorMessage());
				break;
			case UNSUBCRIBE_SUCCESS:
				// showAlert(getString(R.string.setting_unsubcribe_success));
				restartApp(getString(R.string.setting_unsubcribe_success));
				break;
			case REGISTERED:
				unsubscribe();
				break;
			case NOT_REGISTERED:
				// if (ClientServer.isUsingAPI_WithDomain()) //I don't
				// understand why we need to check this
				showAlert(getString(R.string.setting_not_registered));
				break;
			case UNLOGGED:
				JsonBase.clearServerSessionInvalidListener();
				// Stop download thread
				DownloadManager.stopDownload();
				// Stop sync thread
				SynchronizeManager.stopSyncThread();
				// Stop MusicPlayer
				MusicPlayer.stopMusicPlayer();
				Toast.makeText(mContext,
						"Timeout, ứng dụng cần phải đăng nhập lại",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext, LoginActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(intent);
				finish();
				break;
			case LOGOUT_SUCCESS:
				JsonBase.clearServerSessionInvalidListener();
				// Stop download thread
				DownloadManager.stopDownload();
				// Stop sync thread
				SynchronizeManager.stopSyncThread();
				// Stop MusicPlayer
				MusicPlayer.stopMusicPlayer();

				// clear database
				// /////////mContext.deleteDatabase(DatabaseManager.DATABASE_NAME);
				// reinit datastore
				// /////////DataStore.reinit(mContext);

				// request login again
				Bundle bundle = new Bundle();
				bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
				bundle.putString(Const.KEY_MSISDN, dataStore.getMsisdn());
				bundle.putString(Const.KEY_PASSWORD,
						dataStore.getSavedPassword());

				// clear old login info
				dataStore.setMsisdn("");
				dataStore.setPassword("");

				bundle.putBoolean(Const.BUNDLE_IS_LOGOUT, true);
				Intent login = new Intent(mContext, LoginActivity.class);
				login.putExtras(bundle);
				login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(login);
				finish();
				break;
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_setting);
		mContext = this;
		dataStore = DataStore.getInstance();
		mImageLoader = ImageLoader.getInstance(mContext);

		// connect Views
		usedMemoryText = (TextView) findViewById(R.id.setting_screen_used_memory_txt);
		offlineCheckBox = (CheckBox) findViewById(R.id.setting_screen_offline_checkBox);
		offlineCheckBox.setOnClickListener(onOfflineCheckBoxListener);
		via3gCheckBox = (CheckBox) findViewById(R.id.checkbox_download_via_3g);
		via3gCheckBox.setOnClickListener(onVia3gListener);
		totalMemorySeekBar = (SeekBar) findViewById(R.id.setting_screen_total_memory_seekbar);
		// currentMemoryTextView = (TextView)
		// findViewById(R.id.setting_screen_current_memory_txt);
		totalMemoryPercentTextView = (TextView) findViewById(R.id.setting_screen_total_memory_percent_txt);
		totalMemoryTextView = (TextView) findViewById(R.id.setting_screen_total_memory_txt);
		totalMemoryMaxTextView = (TextView) findViewById(R.id.setting_screen_total_memory_max_txt);
		packageTextView = (TextView) findViewById(R.id.setting_screen_package_txt);
		// priceTextView = (TextView)
		// findViewById(R.id.setting_screen_price_txt);
		logoutLayout = (RelativeLayout) findViewById(R.id.setting_screen_logout);
		infoLayout = (RelativeLayout) findViewById(R.id.account_info_layout);
		friendLayout = (RelativeLayout) findViewById(R.id.friend_layout);
		clearDownloadedMusicLayout = (RelativeLayout) findViewById(R.id.clear_downloaded_music_layout);
		logoutLayout.setOnClickListener(onLogoutListener);
		infoLayout.setOnClickListener(onInfoListener);
		friendLayout.setOnClickListener(onFriendListener);
		clearDownloadedMusicLayout.setOnClickListener(onClearListener);
		// logoutLayoutBorder = (RelativeLayout)
		// findViewById(R.id.setting_screen_logout_border);

		txtFullName = (TextView) findViewById(R.id.account_info_full_name);
		txtPhoneNumber = (TextView) findViewById(R.id.account_info_phone_number);
		imgAvatar = (ImageView) findViewById(R.id.account_info_avatar);

		uploadAvatarBtn = (Button) findViewById(R.id.upload_avatar_btn);
		uploadAvatarBtn.setOnClickListener(onUploadAvatarBtnListener);

		// update Views' status
		updateUI();

		// set Listener
		totalMemorySeekBar.setOnSeekBarChangeListener(this);
		mContext.registerReceiver(settingChangedReceiver, new IntentFilter(
				Const.ACTION_UPDATE_SETTING_SCREEN));

		showRateDialog();

		JsonBase.setServerSessionInvalidListener(this);
	}

	OnClickListener onUploadAvatarBtnListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			// startActivityForResult(intent, 0);
			showPhotoChooser();
		}
	};

	private void uploadAvatarToServer(Bitmap avatar) {
		Toast.makeText(mContext, "Hình ảnh đang được tải lên",
				Toast.LENGTH_SHORT).show();
		JsonAuth.uploadAvatar(avatar);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == VALUE_CAMERA) {
			if (data != null) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				photo = Bitmap.createScaledBitmap(photo, 80, 80, false);
				//imgAvatar.setBackgroundResource(0);
				imgAvatar.setImageBitmap(photo);
				

				uploadAvatarToServer(photo);
			} else {

			}
		} else if (requestCode == VALUE_GALLERY) {
			if (data != null) {
				Uri chosenImageUri = data.getData();
				try {
					Bitmap mBitmap = Media.getBitmap(this.getContentResolver(),
							chosenImageUri);
					//ImageView avatar = (ImageView)findViewById(R.id.account_info_avatar);
					imgAvatar.setImageBitmap(mBitmap);
					//imgAvatar.refreshDrawableState();

					uploadAvatarToServer(mBitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {

			}
		}
	}

	public void showPhotoChooser() {
		final Dialog dialog = new Dialog(mContext);
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
			}
		});
		DialogAdapter _myAdapter = new DialogAdapter(mContext);
		_listview.setAdapter(_myAdapter);

		TextView _title = (TextView) dialog
				.findViewById(R.id.common_context_menu_title_txt);
		_title.setText("Chọn ảnh");
		dialog.show();
		_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
				if (position == 0) {
					dialog.dismiss();
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					startActivityForResult(intent, VALUE_CAMERA);
				} else if (position == 1) {
					dialog.dismiss();
					Intent photoPickerIntent = new Intent(
							Intent.ACTION_GET_CONTENT);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, VALUE_GALLERY);

				}
			}
		});
	}

	private class DialogAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		class ViewHolder {
			TextView first_text;
			ImageView image;
			ImageView arrow;

		}

		public DialogAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
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
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setVisibility(ImageView.VISIBLE);
			holder.arrow.setVisibility(ImageView.VISIBLE);
			holder.arrow.setBackgroundResource(R.drawable.ic_listview_arrow);

			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			lp2.rightMargin = 20;
			lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp2.addRule(RelativeLayout.CENTER_VERTICAL);
			holder.arrow.setLayoutParams(lp2);

			if (position == 0) {
				holder.image.setBackgroundResource(R.drawable.ic_camera);
				holder.first_text.setText("Chụp từ camera");
			} else if (position == 1) {
				holder.image.setBackgroundResource(R.drawable.ic_gallery);
				holder.first_text.setText("Lấy ảnh có sẵn");
			}

			return convertView;
		}
	}

	OnClickListener onOfflineCheckBoxListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			offlineModeChanged(null);
		}
	};

	OnClickListener onInfoListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, AccSettingActivity.class);
			startActivity(intent);
		}
	};

	OnClickListener onFriendListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(NetworkUtility.hasNetworkConnection()) {
				Intent intent = new Intent(mContext, FriendActivity.class);
				startActivity(intent);
			}
			else {
				Toast.makeText(mContext, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
			}
		}
	};

	OnClickListener onClearListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			clearCache(null);
		}
	};

	OnClickListener onLogoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			logout(null);
		}
	};

	OnClickListener onVia3gListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			downloadVia3gModeChanged();
		}
	};

	@Override
	public void onResume() {
		super.onResume();

		// update Views' status
		int used = (int) (FileStorage.getUsedSize() / FileStorage.MBYTE);
		if(usedMemoryText != null)
			usedMemoryText.setText(getStringOfSize(used));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		mContext.unregisterReceiver(settingChangedReceiver);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG_LOGOUT:
			return new AlertDialog.Builder(getParent())
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.app_name)
					.setMessage(R.string.logout_msg)
					.setPositiveButton(R.string.confirm_yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									getParent().finish();
								}
							})
					.setNegativeButton(R.string.confirm_no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// do nothing
								}
							}).create();
		}
		return null;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// showDialog(DIALOG_LOGOUT);
			finish();
		}
		return true;
	}

	private String getStringOfSize(int sizeInMB) {
		if (sizeInMB < 1024)
			return sizeInMB + " MB";

		int sizeInGB = sizeInMB / 1024;
		return sizeInGB + " GB";
	}

	private void updateUI() {
		// get Shared Preference File
		DataStore dataStore = DataStore.getInstance();

		// Offline Mode
		Boolean offlineModeEnabled = dataStore.isInOfflineMode();
		offlineCheckBox.setChecked(offlineModeEnabled);

		// Get Memory Info
		int used = (int) (FileStorage.getUsedSize() / FileStorage.MBYTE);
		int free = (int) (FileStorage.getAvaiableVolume() / FileStorage.MBYTE);
		int maxUsed = used + free;

		// Total Memory (maximum memory amount allowed)
		totalMemorySeekBar.setMax(100);
		int progress = dataStore.getMemoryLimit();
		totalMemorySeekBar.setProgress(progress);
		totalMemoryPercentTextView.setText(progress + " %");
		totalMemoryMaxTextView.setText(getStringOfSize(maxUsed));
		int limit = maxUsed * progress / 100;
		totalMemoryTextView.setText(getStringOfSize(limit));
		usedMemoryText.setText(getStringOfSize(used));
		// Current Memory (memory in use)
		// Hoai Ngo: temporary comment it out to fix crash
		// currentMemoryTextView.setText(getStringOfSize(used));
		// totalMemorySeekBarMinProgress = used * 100 / maxUsed;
		if (maxUsed != 0)
			totalMemorySeekBarMinProgress = used * 100 / maxUsed;
		else
			totalMemorySeekBarMinProgress = 0;

		if (maxUsed != 0)
			if (used * 100 % maxUsed > 0)
				totalMemorySeekBarMinProgress += 1;

		// Account Information
		txtFullName.setText(dataStore.getConfig(KEY_FULL_NAME));
		txtPhoneNumber.setText(dataStore.getConfig(KEY_PHONE_NUMBER));
		mImageLoader.DisplayImage(dataStore.getConfig(KEY_AVATAR), this,
				imgAvatar, R.drawable.ic_user_default);

		// Package Information
		String phoneNumber = dataStore.getMsisdn();
		if (phoneNumber.trim().equals("")) {
			TelephonyManager tMgr = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tMgr != null) {
				if (tMgr.getLine1Number() != null
						&& !tMgr.getLine1Number().trim().equals(""))
					phoneNumber = tMgr.getLine1Number();
			}
		}

	}

	private void restartApp(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.setTitle(getText(R.string.dialog_notification));
		alertDialog.setMessage(message);
		alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				JsonBase.clearServerSessionInvalidListener();
				// Stop download thread
				DownloadManager.stopDownload();
				// Stop sync thread
				SynchronizeManager.stopSyncThread();
				// Stop MusicPlayer
				MusicPlayer.stopMusicPlayer();

				Bundle bundle = new Bundle();
				bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
				bundle.putString(Const.KEY_MSISDN, dataStore.getMsisdn());
				bundle.putString(Const.KEY_PASSWORD,
						dataStore.getSavedPassword());

				// clear old login info
				dataStore.setMsisdn("");
				dataStore.setPassword("");

				bundle.putBoolean(Const.BUNDLE_IS_LOGOUT, true);
				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
			}
		});
		alertDialog.show();
	}

	private void showAlert(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.setTitle(getText(R.string.dialog_notification));
		alertDialog.setMessage(message);
		alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();
	}

	public void showAccountInfo(View view) {

		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(getString(R.string.setting_get_acc_info));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				identify = JsonAuth.identify();
				if (!identify.isSuccess()) {
					mHandler.sendEmptyMessage(GET_ACC_INFO_FAILED);
				} else {
					mHandler.sendEmptyMessage(GET_ACC_INFO_SUCCESS);
				}
			}
		}).start();

	}

	public void unsubscribe(View view) {
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(getString(R.string.setting_get_acc_info));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				identify = JsonAuth.identify();
				if (identify.isSuccess()
						&& !identify.packageCode.trim().equalsIgnoreCase("")) {
					mHandler.sendEmptyMessage(REGISTERED);
				} else {
					mHandler.sendEmptyMessage(NOT_REGISTERED);
				}
			}
		}).start();
	}

	private void unsubscribe() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.setting_confirm_unsubscribe)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								mProgressDialog = new ProgressDialog(mContext);
								mProgressDialog
										.setMessage(getString(R.string.setting_unsubcribe));
								mProgressDialog.setCancelable(false);
								mProgressDialog.show();
								new Thread(new Runnable() {
									@Override
									public void run() {
										unsubscribe = JsonAuth.unsubscribe();
										if (!unsubscribe.isSuccess()) {
											mHandler.sendEmptyMessage(UNSUBCRIBE_FAILED);
										} else {
											mHandler.sendEmptyMessage(UNSUBCRIBE_SUCCESS);
										}
									}
								}).start();

							}
						})
				.setNegativeButton(R.string.confirm_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void logout(View view) {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.setting_confirm_logout)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();

								// call logout API first to remove section
								mProgressDialog = new ProgressDialog(mContext);
								mProgressDialog.setMessage("Đang đăng xuất...");
								mProgressDialog.setCancelable(false);
								mProgressDialog.show();
								new Thread(new Runnable() {
									@Override
									public void run() {
										JsonAuth logout = JsonAuth.logout();
										mHandler.sendEmptyMessage(LOGOUT_SUCCESS);
									}
								}).start();

							}
						})
				.setNegativeButton(R.string.confirm_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void showRateDialog() {
		final SharedProperties mUtil = new SharedProperties(this);
		int ask_rate;
		try {
			ask_rate = Integer.parseInt(mUtil.getSharedPre(Const.KEY_ASK_RATE));
		} catch (Exception e) {
			// TODO: handle exception
			ask_rate = 0;
		}
		int curr_count;
		try {
			curr_count = Integer.parseInt(mUtil
					.getSharedPre(Const.KEY_LOGIN_COUNT));
		} catch (Exception e) {
			// TODO: handle exception
			curr_count = 0;
		}

		if (ask_rate != 0 && curr_count >= 10) {
			// show Confirm dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage("Bạn có muốn đánh giá ứng dụng không ?")
					.setCancelable(true)
					.setPositiveButton(R.string.confirm_yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mUtil.setSharedPre(Const.KEY_ASK_RATE, "0");
									mUtil.setSharedPre(Const.KEY_LOGIN_COUNT,
											"0");
									Intent goToMarket = null;
									goToMarket = new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id=vn.com.vega.music"));
									startActivity(goToMarket);
								}
							})
					.setNegativeButton(R.string.confirm_no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	public void downloadVia3gModeChanged() {
		// store shared preference Offline Mode

		boolean flag = via3gCheckBox.isChecked();
		if (!flag) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(
					"Nếu bạn tắt tính năng này quá trình tải nhạc sử dụng mạng 3G sẽ bị dừng")
					.setCancelable(false)
					.setPositiveButton(R.string.confirm_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									if(NetworkUtility.getNetworkStatus() == NetworkUtility.CONNECTION_TYPE_3G)
										DownloadManager.stopDownload();
									dataStore.setDownloadVia3gModeStatus(false);
									dialog.dismiss();
								}
							})
					.setNegativeButton(R.string.confirm_no,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									via3gCheckBox.setChecked(true);
									dialog.dismiss();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();

		} else {
			dataStore.setDownloadVia3gModeStatus(true);
		}

	}

	public void offlineModeChanged(View view) {
		// store shared preference Offline Mode

		boolean offline = offlineCheckBox.isChecked();
		if (offline) {
			// stop download and sync thread
			SynchronizeManager.stopSyncThread();
			DownloadManager.stopDownload();

			Song song = NowPlayingList.getSongAtIndex(NowPlayingList
					.getSongCurrentIndex());
			if (song != null && !song.isAvailableLocally()) {
				if (MusicPlayer.isMusicPlaying()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							mContext);
					builder.setMessage(
							R.string.setting_confirm_stop_online_song)
							.setCancelable(false)
							.setPositiveButton(R.string.confirm_ok,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											MusicPlayer.stopMusicPlayer();
											dataStore
													.setOfflineModeStatus(true);
											dialog.dismiss();
										}
									})
							.setNegativeButton(R.string.confirm_no,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											offlineCheckBox.setChecked(false);
											dialog.dismiss();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();
				} else {
					MusicPlayer.stopMusicPlayer();
					dataStore.setOfflineModeStatus(true);
				}
			} else {
				dataStore.setOfflineModeStatus(true);
			}
		} else {
			dataStore.setOfflineModeStatus(false);
		}

	}

	public void clearCache(View view) {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.setting_confirm_clear_memory)
				.setCancelable(false)
				.setPositiveButton(R.string.confirm_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// clear all songs cached
								mProgressDialog = new ProgressDialog(mContext);
								mProgressDialog
										.setMessage(getString(R.string.setting_clear_memory_in_progress));
								mProgressDialog.setCancelable(false);
								mProgressDialog.show();
								new Thread(new Runnable() {
									@Override
									public void run() {
										int result = FileStorage
												.removeAllSongCache();
										if (result < 0) {
											// failed
											mHandler.sendEmptyMessage(CLEAR_CACHE_FAILED);
										} else {
											// success
											dataStore.clearAllSongCachedPath();
											mHandler.sendEmptyMessage(CLEAR_CACHE_SUCCESS);
										}
									}
								}).start();

							}
						})
				.setNegativeButton(R.string.confirm_no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress < totalMemorySeekBarMinProgress) {
			totalMemorySeekBar.setProgress(totalMemorySeekBarMinProgress);
		} else {
			// update UI
			totalMemoryPercentTextView.setText(progress + " %");
			updateMemoryView(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	private void updateMemoryView(int progress) {
		// update UI
		int used = (int) (FileStorage.getUsedSize() / FileStorage.MBYTE);
		int free = (int) (FileStorage.getAvaiableVolume() / FileStorage.MBYTE);
		int maxUsed = used + free;

		// String ss = getStringOfSize(used);

		// currentMemoryTextView.setText(getStringOfSize(used));
		if (maxUsed != 0) {
			totalMemorySeekBarMinProgress = used * 100 / maxUsed;
		} else
			totalMemorySeekBarMinProgress = 0;
		if (maxUsed != 0)
			if (used * 100 % maxUsed > 0)
				totalMemorySeekBarMinProgress += 1;
		totalMemoryMaxTextView.setText(getStringOfSize(maxUsed));
		int limit = maxUsed * progress / 100;
		totalMemoryTextView.setText(getStringOfSize(limit));
		usedMemoryText.setText(getStringOfSize(used));
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		updateMemoryView(progress);
		// store preference
		DataStore dataStore = DataStore.getInstance();
		dataStore.setMemoryLimit(progress);
	}

	@Override
	public void onValidateAgain() {
		onLoginAgain();
	}

	@Override
	public void onLoginAgain() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DataStore dataStore = DataStore.getInstance();
				int network = NetworkUtility.getNetworkStatus();
				if (network == NetworkUtility.CONNECTION_TYPE_3G) {
					JsonAuth identify = JsonAuth.identify();
					if (identify.isNetworkError())
						return;
					if (identify.isSuccess())
						return;
					else {
						mHandler.sendEmptyMessage(UNLOGGED);
					}

				} else if (network == NetworkUtility.CONNECTION_TYPE_WIFI) {
					String username = dataStore.getMsisdn();
					String password = dataStore.getSavedPassword();
					JsonAuth login = JsonAuth.login(username, password);
					if (login.isNetworkError()) {
						return;
					}
					if (login.isSuccess()) {
						return;
					} else {
						mHandler.sendEmptyMessage(UNLOGGED);
					}
				}
			}
		}).start();
	}

	@Override
	public void onPowerExpire(String preferred_package_code, String msg) {
		// TODO Auto-generated method stub
		
	}
}
