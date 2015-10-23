package vn.com.vega.music.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import vn.com.vega.chacha.R;
import vn.com.vega.music.database.DataStore;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class AccConnectingActivity extends Activity {

	private Button backBtn, connect2FacebookBtn, connect2GmailBtn;
	private  Facebook mFacebook;
	private  Handler mHandler = new Handler();
	private  Context mContext;
	public static final String APP_ID = "170971199667899";
	private  AsyncFacebookRunner mAsyncRunner;
	private  Activity mActivity = this;
	
	private boolean isFacebook = true;

	private static final String[] PERMISSIONS = new String[] { "publish_stream", "read_stream", "offline_access" };

	private static final String AUTH_TOKEN_TYPE = "cp";

	private static final int REQUEST_AUTHENTICATE = 0;

	private static final String PREF = "GmailAccount";

	private static final int DIALOG_ACCOUNTS = 0;

	private String authToken;

	
	private DataStore datastore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mActivity = this;
		datastore = DataStore.getInstance();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_connect_to);

		initView();
	}
	


	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// Log.d("FB Sample App", "onActivityResult(): " + requestCode);
	// mFacebook.authorizeCallback(requestCode, resultCode, data);
	// }

	private void initView() {
		backBtn = (Button) findViewById(R.id.connecting_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);
		connect2FacebookBtn = (Button) findViewById(R.id.connect_to_facebook_btn);
		connect2GmailBtn = (Button) findViewById(R.id.connect_to_gmail_btn);
		connect2FacebookBtn.setOnClickListener(onFacebookListener);
		connect2GmailBtn.setOnClickListener(onGmailListener);
	}

	OnClickListener onBackBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnClickListener onFacebookListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			isFacebook = true;
			
			// Make sure the app client_app has been set
			if (APP_ID == null) {
				Util.showAlert(mContext, "Warning", "Facebook Applicaton ID must be set...");
			}
			
			
			
			// Initialize the Facebook session
			mFacebook = new Facebook(APP_ID);
			mAsyncRunner = new AsyncFacebookRunner(mFacebook);
			
			
			/*
			// TODO Auto-generated method stub
			// Toggle the button state.
			// If coming from login transition to logout.
			if (mFacebook.isSessionValid()) {
				AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFacebook);
				asyncRunner.logout(getBaseContext(), new LogoutRequestListener());
			} else {
				// Toggle the button state.
				// If coming from logout transition to login (authorize).
				mFacebook.authorize(mActivity, PERMISSIONS, new LoginDialogListener());
			}
			*/
			
			AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFacebook);
			asyncRunner.logout(getBaseContext(), new LogoutRequestListener());
			
			mFacebook.authorize(mActivity, PERMISSIONS, new LoginDialogListener());
			
			
			//mFacebook.authorize(mActivity, PERMISSIONS, new LoginDialogListener());
		}
	};
	

	OnClickListener onGmailListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			isFacebook = false;
			gotAccount(false);
		}
	};
	
	
	// ///////////////////////////////////////////////////////
	// Login / Logout Listeners
	// ///////////////////////////////////////////////////////

	/**
	 * Listener for login dialog completion status
	 */
	private final class LoginDialogListener implements com.facebook.android.Facebook.DialogListener {

		/**
		 * Called when the dialog has completed successfully
		 */
		public void onComplete(Bundle values) {
			// Process onComplete
			Log.d("FB Sample App", "LoginDialogListener.onComplete()");
			// Dispatch on its own thread
			mHandler.post(new Runnable() {
				public void run() {

					// Toast.makeText(mContext,
					// "Liên kết tới Facebook thành công",
					// Toast.LENGTH_SHORT).show();

					// mText.setText("Facebook login successful. Press Menu...");

					String access_token = mFacebook.getAccessToken();
					
					
					datastore.setFbAccessToken(access_token);
					
	
					
					//Long access_expire = mFacebook.getAccessExpires();

					//Toast.makeText(mContext, "Access token is: " + access_token, Toast.LENGTH_SHORT).show();
					
					//loadFriendOnFacebook(access_token);
					
				}
			});
		}

		/**
         *
         */
		public void onFacebookError(FacebookError error) {
			// Process error
			Log.d("FB Sample App", "LoginDialogListener.onFacebookError()");
		}

		/**
         *
         */
		public void onError(DialogError error) {
			// Process error message
			Log.d("FB Sample App", "LoginDialogListener.onError()");
		}

		/**
         *
         */
		public void onCancel() {
			// Process cancel message
			Log.d("FB Sample App", "LoginDialogListener.onCancel()");
		}
	}

	/**
	 * Listener for logout status message
	 */
	private class LogoutRequestListener implements RequestListener {

		/** Called when the request completes w/o error */
		public void onComplete(String response) {

			// Only the original owner thread can touch its views
			AccConnectingActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					// mText.setText("Thanks for using FB Sample App. Bye bye...");
					// friends.clear();
					// friendsArrayAdapter.notifyDataSetChanged();
				}
			});

			// Dispatch on its own thread
			mHandler.post(new Runnable() {
				public void run() {
				}
			});
		}

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e, Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub

		}

	}

	/*
	 * Gmail
	 */

	@Override
	protected Dialog onCreateDialog(int id) {

		try {
			final AccountManager manager = AccountManager.get(this);
			final Account[] accounts = manager.getAccountsByType("com.google");
			final int size = accounts.length;
			if(size > 0){
				switch (id) {
				case DIALOG_ACCOUNTS:
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setTitle("Select a Google account");

					String[] names = new String[size + 1];
					for (int i = 0; i < size; i++) {
						names[i] = accounts[i].name;
					}
					names[size] = "Sử dụng tài khoản khác";
					builder.setItems(names, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if(which == size){
								Intent intent = new Intent("android.settings.SYNC_SETTINGS");
								startActivity(intent);
							}
							else
								gotAccount(manager, accounts[which]);
						}
					});

					return builder.create();
				}
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("Thông báo").setMessage(R.string.no_gmail_account).setCancelable(false).setPositiveButton(R.string.confirm_continue, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent("android.settings.SYNC_SETTINGS");
						startActivity(intent);
					}
				});
				return builder.create();
			}
			
		} catch (Exception e) {
			// TODO: handle exception

		}
		return null;

	}

	private void gotAccount(boolean tokenExpired) {
		SharedPreferences settings = getSharedPreferences(PREF, 0);
		String accountName = settings.getString("accountName", null);
		AccountManager manager = AccountManager.get(this);
		Account[] accounts = manager.getAccountsByType("com.google");
		if (accountName != null) {
			int size = accounts.length;
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Account account = accounts[i];
					if (accountName.equals(account.name)) {
						if (tokenExpired) {
							manager.invalidateAuthToken("com.google", this.authToken);
						}
						gotAccount(manager, account);
						return;
					} else {

					}
				}
			} else {
				noGmailAccount();
				return;
			}

		}
		if (accounts.length > 0)
			showDialog(DIALOG_ACCOUNTS);
		else
			noGmailAccount();
	}

	private void noGmailAccount() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Thông báo").setMessage(R.string.no_gmail_account).setCancelable(false).setPositiveButton(R.string.confirm_continue, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent("android.settings.SYNC_SETTINGS");
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void gotAccount(final AccountManager manager, final Account account) {
		SharedPreferences settings = getSharedPreferences(PREF, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("accountName", account.name);
		editor.commit();

		// try {
		// final Bundle bundle = manager.getAuthToken(account, AUTH_TOKEN_TYPE,
		// true, null, null).getResult();
		// if (bundle.containsKey(AccountManager.KEY_AUTHTOKEN)) {
		// authenticatedClientLogin(bundle.getString(AccountManager.KEY_AUTHTOKEN));
		// }
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// String s = "error";
		// }

		new Thread() {
			@Override
			public void run() {
				try {
					final Bundle bundle = manager.getAuthToken(account, AUTH_TOKEN_TYPE, true, null, null).getResult();
					runOnUiThread(new Runnable() {
						public void run() {
							try {
								if (bundle.containsKey(AccountManager.KEY_INTENT)) {
									Intent intent = bundle.getParcelable(AccountManager.KEY_INTENT);
									int flags = intent.getFlags();
									flags &= ~Intent.FLAG_ACTIVITY_NEW_TASK;
									intent.setFlags(flags);
									startActivityForResult(intent, REQUEST_AUTHENTICATE);
								} else if (bundle.containsKey(AccountManager.KEY_AUTHTOKEN)) {
									authenticatedClientLogin(bundle.getString(AccountManager.KEY_AUTHTOKEN));
								}
							} catch (Exception e) {
								handleException(e);
							}
						}
					});
				} catch (Exception e) {
					handleException(e);
				}
			}
		}.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(isFacebook){
			mFacebook.authorizeCallback(requestCode, resultCode, data);
		}
		else{
			switch (requestCode) {
			case REQUEST_AUTHENTICATE:
				if (resultCode == RESULT_OK) {
					gotAccount(false);
				} else {
					showDialog(DIALOG_ACCOUNTS);
				}
				break;
			}
		}
		
	}

	private void authenticatedClientLogin(String authToken) {
		this.authToken = authToken;

		Toast.makeText(this, "Access token is: " + authToken, Toast.LENGTH_SHORT).show();

		// ((GoogleHeaders) transport.defaultHeaders).setGoogleLogin(authToken);
		// authenticated();
	}

	void handleException(Exception e) {
		Toast.makeText(mContext, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
	}

}