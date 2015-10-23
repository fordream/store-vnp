package vn.com.vega.music.view;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.ClientServer;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonBase;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.syncmanager.SynchronizeManager;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.Util;
import vn.com.vega.music.view.holder.TabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements Runnable {

	public static final int HANDLER_MSG_WAIT = 1;
	public static final int HANDLER_QUESTION_OFFLINE = 2;

	private static final int NOT_AUTHENTICATED = 11;
	private static final int NO_INTERNET = 12;
	private static final int SERVER_ERROR = 15;
	private static final int ASK_OFFLINE = 13;
	private static final int NOT_REGISTERED_MSG = 10;
	private static final int SUCCESS = 14;
	private static final int SHOW_FIRST_PROGRESS = 16;
	private static final int SHOW_SECOND_PROGRESS = 17;
	private static final int SHOW_THIRD_PROGRESS = 18;
	private static final int SHOW_FOURTH_PROGRESS = 19;

	private static final int AUTHEN_SERVER_ERROR = 0;
	private static final int AUTHEN_FAILED = 1;
	private static final int AUTHEN_SUCCESS = 2;

	private Context mContext;
	private Thread mBckThread;
	private int network;
	private DataStore dataStore;
	private ProgressBar mProgressBar;

	private boolean threadModeEnabled = false;

	private String authenMessage = "";
	private String registerMessage = "";

	
	private void gotoOfflineMode(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(msg).setCancelable(false).setPositiveButton(mContext.getString(R.string.confirm_yes), new DialogInterface.OnClickListener() {

			// Yes, i wanna go to offline mode
			public void onClick(DialogInterface dialog, int id) {
				// store shared preference Offline Mode
				try {
					DataStore dataStore = DataStore.getInstance();
					dataStore.setOfflineModeStatus(true);

					// show Music page
					dialog.dismiss();
					Intent intent = new Intent(mContext, TabHolder.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}).setNegativeButton(mContext.getString(R.string.confirm_no), new DialogInterface.OnClickListener() {

			// No , i don't wanna go to offline mode ------> finish app
			public void onClick(DialogInterface dialog, int id) {
				try {
					dialog.dismiss();
					Intent intent = new Intent(mContext, TabHolder.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Intent i = new Intent(Const.ACTION_DIMISS_DIALOG);
			mContext.sendBroadcast(i);

			switch (msg.what) {
			case ASK_OFFLINE:
				gotoOfflineMode(getString(R.string.setting_question_switch_to_offline));
				break;
			case NO_INTERNET:
				// show message then exit app
				showAlert(getString(R.string.no_network_connection));
				break;
			case SERVER_ERROR:
				//showAlert(getString(R.string.login_screen_error_auto_login_unsuccessfully));
				gotoOfflineMode(getString(R.string.server_error_go_to_offline));
				break;
			case NOT_AUTHENTICATED:
				// show message then exit app
				if (authenMessage.trim().equals("")) {
					//showAlert(getString(R.string.login_screen_error_authenticate_unsuccessfully));
					gotoOfflineMode(getString(R.string.not_authenticated_go_to_offline));
				} else {
					//showAlert(authenMessage);
					gotoOfflineMode(authenMessage);
				}
				break;
			case NOT_REGISTERED_MSG:
				Toast.makeText(mContext, "Bạn chưa đăng ký dịch vụ", Toast.LENGTH_LONG).show();
				// for sure
				JsonBase.clearServerSessionInvalidListener();
				// Stop download thread
				DownloadManager.stopDownload();
				// Stop sync thread
				SynchronizeManager.stopSyncThread();
				// Stop MusicPlayer
				MusicPlayer.stopMusicPlayer();

				// show Login page with register button. Will show list
				// of packages when clicking on register button

				String username = dataStore.getMsisdn();
				String password = dataStore.getSavedPassword();
				if (username == null)
					username = "";
				if (password == null)
					password = "";
				Bundle bundle = new Bundle();
				bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
				bundle.putBoolean(Const.KEY_NOT_REGISTERED, true);
				bundle.putString(Const.KEY_MSISDN, username);
				bundle.putString(Const.KEY_PASSWORD, password);
				Intent intent = new Intent(mContext, StartPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
				finish();

				break;
			case SHOW_FIRST_PROGRESS:
				if(mProgressBar != null)
					mProgressBar.setProgress(25);
				break;
			case SHOW_SECOND_PROGRESS:
				if(mProgressBar != null)
					mProgressBar.setProgress(50);
				break;
			case SHOW_THIRD_PROGRESS:
				if(mProgressBar != null)
					mProgressBar.setProgress(75);
				break;
			case SHOW_FOURTH_PROGRESS:
				if(mProgressBar != null)
					mProgressBar.setProgress(100);
				break;
			case SUCCESS:
				if (!threadModeEnabled) {
					Intent intent1 = new Intent(mContext, TabHolder.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mContext.startActivity(intent1);
				} else {
					Intent intent1 = new Intent(Const.ACTION_UPDATE_SETTING_SCREEN);
					mContext.sendBroadcast(intent1);
				}
				finish();
				break;
			}
		}
	};

	/**
	 * When in thread mode, use this to set context for starting another
	 * activity
	 * 
	 * @param context
	 *            External context used for starting another activity
	 */
	public void setContext(Context context) {
		if (context == null)
			mContext = getApplicationContext();
		else
			mContext = context;
	}

	/**
	 * Use this activity as a background thread without UI
	 */
	public void enableThreadMode() {
		threadModeEnabled = true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		setContentView(R.layout.layout_activity_splash);
		mContext = this;

		mProgressBar = (ProgressBar) findViewById(R.id.splash_progress_bar);

		// Init some core module here
		NetworkUtility.registerNetworkNotification(this);

		mBckThread = new Thread(this);
		mBckThread.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// Destroy
		NetworkUtility.unregisterNetworkNotification(this);
	}

	@SuppressWarnings("unused")
	private void sendMessage(int stringID, int messageType) {
		Message msg = new Message();
		msg.obj = mContext.getResources().getString(stringID);
		msg.what = messageType;
		mHandler.sendMessage(msg);
	}

	private int authenticateApplication() {

		// Request random key
		JsonAuth ja = JsonAuth.requestAuthKey();

		if (ja.isNetworkError())
			return AUTHEN_SERVER_ERROR;

		if (!ja.isSuccess()) {
			// Service unavailable
			authenMessage = ja.getErrorMessage();
			return AUTHEN_FAILED;
		}

		// validate
		JsonAuth validate = JsonAuth.validate(ja.randomKey);

		if (validate.isNetworkError()) {
			return AUTHEN_SERVER_ERROR;
		}

		if (!validate.isSuccess()) {
			// Auth error
			authenMessage = validate.getErrorMessage();
			return AUTHEN_FAILED;
		}

		// authen success

		// save extra data here
		Util mUtil = new Util(mContext);
		mUtil.setSharedPre(Const.KEY_SMS_SERVER, validate.mService.sms_server);
		mUtil.setSharedPre(Const.KEY_SMS_CONTENT, validate.mService.sms_content);
		mUtil.setSharedPre(Const.KEY_ASK_RATE, Integer.toString(validate.mService.ask_rate));
		// mUtil.setSharedPre(Const.KEY_ASK_RATE, "1");
		mUtil.setSharedPre(Const.KEY_WARNING_MSG, validate.mService.warning_message);
		String temp = mUtil.getSharedPre(Const.KEY_LOGIN_COUNT);
		int curr_count = 0;
		try {
			if (temp.trim().equals(""))
				curr_count = 0;
			else
				curr_count = Integer.parseInt(temp);
		} catch (Exception e) {
			// TODO: handle exception
			curr_count = 0;
		}

		mUtil.setSharedPre(Const.KEY_LOGIN_COUNT, "" + (curr_count + 1));
		return AUTHEN_SUCCESS;
	}

	private void showAlert(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.setTitle(getText(R.string.dialog_notification));
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);
		alertDialog.setButton("Đóng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// exit app
				finish();
			}
		});
		alertDialog.show();
	}

	private void checkHavingPassword(String username, String password) {
		// Yes, there is password in database
		if (!password.trim().equalsIgnoreCase("")) {
			// try auto login
			JsonAuth login = JsonAuth.login(username, password);

			// success ?

			// oh no, failed
			if (!login.isSuccess()) {

				// for sure
				JsonBase.clearServerSessionInvalidListener();
				// Stop download thread
				DownloadManager.stopDownload();
				// Stop sync thread
				SynchronizeManager.stopSyncThread();
				// Stop MusicPlayer
				MusicPlayer.stopMusicPlayer();

				// show Login page
				Bundle bundle = new Bundle();
				bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
				bundle.putString(Const.KEY_MSISDN, username);
				bundle.putString(Const.KEY_PASSWORD, password);
				Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
				mContext.startActivity(intent);
				finish();
				// oh yeah, success
			} else {
				// Auto login success, record user
				if (login.username != null) {
					dataStore.setMsisdn(login.username);
				}

				mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
				// show Music page
				mHandler.sendEmptyMessage(SUCCESS);

			}
		}
		// No, there is no password
		else {
			// for sure
			JsonBase.clearServerSessionInvalidListener();
			// Stop download thread
			DownloadManager.stopDownload();
			// Stop sync thread
			SynchronizeManager.stopSyncThread();
			// Stop MusicPlayer
			MusicPlayer.stopMusicPlayer();

			mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

			// show Login page with empty username and
			// password
			Intent intent = new Intent(mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mContext.startActivity(intent);
			finish();
		}
	}

	public void run() {
		// Init application modules
		if (!threadModeEnabled)
			DataStore.init(this);
		dataStore = DataStore.getInstance();

		// Startup flow
		network = NetworkUtility.getNetworkStatus();
		boolean offlineModeEnabled = dataStore.isInOfflineMode();

		// Get previously saved Login info
		// has msisdn ?

		String username = dataStore.getMsisdn();
		String password = dataStore.getSavedPassword();

		int authenticated;

		// yes, has msisdn !
		if (username != null && !username.trim().equalsIgnoreCase("")) {
			// offline mode ?

			// No, online mode
			if (!offlineModeEnabled) {

				// no network connection
				if (network == NetworkUtility.CONNECTION_TYPE_OFF) {
					// ask offline. Do you wanna go to offline mode ?
					mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
					mHandler.sendEmptyMessage(ASK_OFFLINE);
				}
				// has network connection
				else {
					// show progress
					mHandler.sendEmptyMessage(SHOW_FIRST_PROGRESS);

					// authenticate first
					authenticated = authenticateApplication();

					mHandler.sendEmptyMessage(SHOW_SECOND_PROGRESS);

					if (authenticated == AUTHEN_SUCCESS) {

						JsonAuth identify = JsonAuth.identify();

						mHandler.sendEmptyMessage(SHOW_THIRD_PROGRESS);

						// Network error ?

						// shit, network is suck
						if (identify.isNetworkError()) {

							// has pass ?
							checkHavingPassword(username, password);

							mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

						}
						// ok, network is nice
						else {

							// detected ?

							if (identify.isSuccess()) {

								// Is registered ?

								// No, not registered
								if (identify.packageCode.equalsIgnoreCase("")) {
									// show message then go to Register page
									registerMessage = identify.getErrorMessage();

									mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

									mHandler.sendEmptyMessage(NOT_REGISTERED_MSG);

									// Yes, registered already !!
								} else {
									// save Login info
									dataStore.setMsisdn(identify.username);
									dataStore.setPassword(identify.password);
									dataStore.updateAccountConfig(identify.hashAccount);

									mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

									// show Music page
									mHandler.sendEmptyMessage(SUCCESS);
								}
							}

							else {
								// has pass ?
								checkHavingPassword(username, password);

								mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
							}
						}

					} else {

						mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

						mHandler.sendEmptyMessage(ASK_OFFLINE);

					}
				}

				// Yes, is offline mode ! -------> enter app !
			} else {
				// show Music page
				mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

				mHandler.sendEmptyMessage(SUCCESS);
			}

			// No, there is no msisdn
		} else {

			// Network type ?

			if (network == NetworkUtility.CONNECTION_TYPE_OFF) {
				// mHandler.sendEmptyMessage(NO_INTERNET);
				mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

				mHandler.sendEmptyMessage(ASK_OFFLINE);
			} else {

				// show progress
				mHandler.sendEmptyMessage(SHOW_FIRST_PROGRESS);

				// authenticate first
				authenticated = authenticateApplication();

				mHandler.sendEmptyMessage(SHOW_SECOND_PROGRESS);

				if (authenticated == AUTHEN_SUCCESS) {
					// dectect msisdn
					JsonAuth identify = JsonAuth.identify();

					mHandler.sendEmptyMessage(SHOW_THIRD_PROGRESS);

					// Network error ?

					// Oh man, network is suck :(
					if (identify.isNetworkError()) {

						mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

						mHandler.sendEmptyMessage(ASK_OFFLINE);

					}
					// okie, network is good
					else {

						if (identify.isSuccess()) {

							// Is registered ?

							// No, not registered
							if (identify.packageCode.trim().equalsIgnoreCase("")) {
								// register flow
								registerMessage = identify.getErrorMessage();

								mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

								mHandler.sendEmptyMessage(NOT_REGISTERED_MSG);

								// Yes, registered already !!
							} else {
								// save Login info
								dataStore.setMsisdn(identify.username);
								dataStore.setPassword(identify.password);
								dataStore.updateAccountConfig(identify.hashAccount);

								mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);

								// show Music page
								mHandler.sendEmptyMessage(SUCCESS);
							}
						} else {

							// for sure
							JsonBase.clearServerSessionInvalidListener();
							// Stop download thread
							DownloadManager.stopDownload();
							// Stop sync thread
							SynchronizeManager.stopSyncThread();
							// Stop MusicPlayer
							MusicPlayer.stopMusicPlayer();

							// show Login page with empty username and password
							Bundle bundle = new Bundle();
							bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
							Intent intent = new Intent(mContext, StartPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(bundle);
							mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
							mContext.startActivity(intent);
							finish();

						}
					}
				} else {

					if (authenticated == AUTHEN_SERVER_ERROR) {
						mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
						mHandler.sendEmptyMessage(SERVER_ERROR);
					} else if (authenticated == AUTHEN_FAILED) {
						mHandler.sendEmptyMessage(SHOW_FOURTH_PROGRESS);
						mHandler.sendEmptyMessage(NOT_AUTHENTICATED);
					}

				}
			}

		}
	}
}
