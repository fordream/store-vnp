package vn.com.vega.music.view;

import java.util.ArrayList;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonPackage;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.database.SharedProperties;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.utils.Util;
import vn.com.vega.music.view.holder.TabHolder;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Package;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final String NEED_AUTHENTICATION = "Need Authentication";

	private static final int LOGIN_SUCCESS = 0;
	private static final int LOGIN_FAILED = -1;
	private static final int AUTH_FAILED = -2;
	private static final int NO_NETWORK = -3;

	private static final int GET_PACKAGE_SUCCESS = 1;
	private static final int GET_PACKAGE_FAILED = 2;
	private static final int SUBSCRIBE_SUCCESS = 3;
	private static final int SUBSCRIBE_FAILED = 4;

	private static final int SEND_SMS = -4;

	private String SMS_TO = "1226";
	private String SMS_BODY = "MK";

	private Context mContext;
	EditText usernameField;
	EditText passwordField;
	private Dialog dialog;
	private Dialog subDialog;

	private Button loginBtn, getPassBtn, registerBtn;

	private View footerView;

	String username = "";
	String password = "";

	String oldUsername = "";
	String oldPassword = "";

	boolean needAuthentication = false;
	boolean isLogout = false;

	private Package selectedPackage;

	private boolean isViettel = true;

	private ArrayList<Package> packages = null;

	private String registerMessage = "";

	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NO_NETWORK:
				showAlert(getString(R.string.no_network_connection));
				break;
			case LOGIN_SUCCESS:
				// open Music page
				Intent intent = new Intent(getApplicationContext(),
						TabHolder.class);
				startActivity(intent);
				finish();
				break;
			case LOGIN_FAILED:
				// forgetPass.setVisibility(TextView.VISIBLE);
				showAlert(getString(R.string.login_screen_error_login_unsuccessfully));
				break;

			case AUTH_FAILED:
				showAlert(getString(R.string.login_screen_error_authenticate_unsuccessfully));
				break;
			case GET_PACKAGE_FAILED:
				mProgressDialog.dismiss();
				showAlert(getString(R.string.login_screen_no_package));
				break;
			case SUBSCRIBE_FAILED:
				mProgressDialog.dismiss();
				if (registerMessage.trim().equals(""))
					showAlert(getString(R.string.login_screen_subscribe_failed));
				else
					showAlert(registerMessage);
				break;
			case SUBSCRIBE_SUCCESS:
				mProgressDialog.dismiss();
				// enter app
				// loginAgain();
				// open Music page
				Intent i = new Intent(getApplicationContext(), TabHolder.class);
				startActivity(i);
				finish();
				break;
			case SEND_SMS:
				mProgressDialog.dismiss();
				break;
			case GET_PACKAGE_SUCCESS:
				mProgressDialog.dismiss();
				if (packages != null) {
					if (packages.size() == 1) {
						selectedPackage = packages.get(0);
						if (selectedPackage != null)
							showSMSDialog();
					} else if (packages.size() > 1) {
						showPackageDialog();
					}
				}
				break;
			}
		}
	};

	private void loginAgain() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog
				.setMessage(getString(R.string.login_screen_login_in_progress));
		mProgressDialog.setCancelable(false);

		// authenticate & login again
		WelcomeActivity ws = new WelcomeActivity();
		ws.setContext(mContext);
		ws.enableThreadMode();
		Thread thread = new Thread(ws);
		mProgressDialog.show();
		thread.start();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		oldUsername = "";
		oldPassword = "";

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			needAuthentication = bundle.getBoolean(NEED_AUTHENTICATION);
			oldUsername = (bundle.getString(Const.KEY_MSISDN) != null ? bundle
					.getString(Const.KEY_MSISDN) : "");
			oldPassword = (bundle.getString(Const.KEY_PASSWORD) != null ? bundle
					.getString(Const.KEY_PASSWORD) : "");
			isLogout = bundle.getBoolean(Const.BUNDLE_IS_LOGOUT);
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_login);
		mContext = this;

		loginBtn = (Button) findViewById(R.id.login_screen_login_btn);
		getPassBtn = (Button) findViewById(R.id.login_screen_get_pass_btn);
		registerBtn = (Button) findViewById(R.id.login_screen_register_btn);

		loginBtn.setOnClickListener(onLoginBtnListener);
		getPassBtn.setOnClickListener(onGetPassBtnListener);
		registerBtn.setOnClickListener(onRegisterBtnListener);

		usernameField = (EditText) findViewById(R.id.login_screen_username_edt);
		passwordField = (EditText) findViewById(R.id.login_screen_password_edt);

		footerView = LayoutInflater.from(LoginActivity.this).inflate(
				R.layout.view_listview_normal_footer, null);
		DataStore dataStore = DataStore.getInstance();
		if (oldUsername.equals(""))
			oldUsername = dataStore.getMsisdn();
		if (oldPassword.equals(""))
			oldPassword = dataStore.getSavedPassword();
		usernameField.setText(oldUsername);
		passwordField.setText(oldPassword);

		// For development only
		// usernameField.setText("client_dev");
		// passwordField.setText("123456");

		if (isLogout) {
			usernameField.setText("");
			passwordField.setText("");
		}

	}

	OnClickListener onLoginBtnListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			doLogin(null);
		}
	};

	OnClickListener onGetPassBtnListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			doGetPass(null);
		}
	};

	OnClickListener onRegisterBtnListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			doRegister(null);
		}
	};

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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

	private boolean checkLoginInfo(String username, String password) {
		if (username == null || username.equalsIgnoreCase("")) {
			showAlert(getString(R.string.login_screen_error_username_null));
			return false;
		}

		if (password == null || password.equalsIgnoreCase("")) {
			showAlert(getString(R.string.login_screen_error_password_null));
			return false;
		}

		if (username.trim().length() == 0 || password.trim().length() == 0) {
			showAlert(getString(R.string.login_screen_error_login_unsuccessfully));
			return false;
		}

		return true;
	}

	public void doLogin(View view) {
		username = usernameField.getText().toString();
		password = passwordField.getText().toString();

		if (!checkLoginInfo(username, password)) {
			return;
		}

		Thread doLoginThread = new Thread() {
			public void run() {
				boolean authen = authenticateApplication();
				if (authen) {
					JsonAuth login = JsonAuth.login(username, password);

					mProgressDialog.dismiss();
					if (login.isNetworkError()) {
						mHandler.sendEmptyMessage(NO_NETWORK);
						return;
					}
					if (login.isSuccess()) {
						// save Login info
						DataStore dataStore = DataStore.getInstance();
						dataStore.setMsisdn(username);
						dataStore.setPassword(password);
						dataStore.updateAccountConfig(login.hashAccount);
						if (!username.equals(oldUsername)) {
							// mContext.deleteDatabase(DatabaseManager.DATABASE_NAME);
							DataStore.reinit(mContext);
						}
						mHandler.sendEmptyMessage(LOGIN_SUCCESS);
					} else {
						mHandler.sendEmptyMessage(LOGIN_FAILED);
					}
				} else {
					mHandler.sendEmptyMessage(LOGIN_FAILED);
				}
				
			}
		};

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog
				.setMessage(getString(R.string.login_screen_login_in_progress));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		doLoginThread.start();
	}

	private boolean authenticateApplication() {

		// Request random key
		JsonAuth ja = JsonAuth.requestAuthKey();

		if (ja.isNetworkError())
			return false;

		if (!ja.isSuccess()) {

			return false;
		}

		// validate
		JsonAuth validate = JsonAuth.validate(ja.randomKey);

		if (validate.isNetworkError()) {
			return false;
		}

		if (!validate.isSuccess()) {
			return false;
		}

		// authen success

		// save extra data here
		DataStore dataStore = DataStore.getInstance();
		dataStore.setConfig(Const.KEY_SMS_SERVER, validate.mService.sms_server);
		dataStore.setConfig(Const.KEY_SMS_CONTENT,
				validate.mService.sms_content);

		Util mUtil = new Util(mContext);
		mUtil.setSharedPre(Const.KEY_SMS_SERVER, validate.mService.sms_server);
		mUtil.setSharedPre(Const.KEY_SMS_CONTENT, validate.mService.sms_content);
		mUtil.setSharedPre(Const.KEY_ASK_RATE,
				Integer.toString(validate.mService.ask_rate));
		// mUtil.setSharedPre(Const.KEY_ASK_RATE, "1");
		mUtil.setSharedPre(Const.KEY_WARNING_MSG,
				validate.mService.warning_message);
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
		return true;
	}

	public void doGetPass(View view) {
		// SharedProperties mUtil = new SharedProperties(mContext);
		// SMS_TO = mUtil.getSharedPre(Const.KEY_SMS_SERVER);
		// SMS_BODY = mUtil.getSharedPre(Const.KEY_SMS_CONTENT);
		DataStore dataStore = DataStore.getInstance();
		SMS_TO = dataStore.getConfig(Const.KEY_SMS_CONTENT);
		SMS_BODY = dataStore.getConfig(Const.KEY_SMS_SERVER);
		if (!SMS_TO.trim().equals("") && !SMS_BODY.trim().equals("")) {
			// send message here
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle("Quên mật khẩu")
					.setMessage(
							"Bạn có muốn gửi tin nhắn với nội dung " + SMS_BODY
									+ " tới tổng đài " + SMS_TO
									+ " để lấy lại mật khẩu không ?")
					.setCancelable(false)
					.setPositiveButton(
							mContext.getString(R.string.confirm_yes),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
									sendSMS(SMS_TO, SMS_BODY);
								}
							})
					.setNegativeButton(mContext.getString(R.string.confirm_no),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();

								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			showAlert("Chức năng này đang được cập nhật");
		}
	}

	public void doRegister(View view) {
		// get list of package
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog
				.setMessage(getString(R.string.login_screen_load_package));
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				JsonPackage jsonPackage = JsonPackage.loadPackageList();
				if (!jsonPackage.isSuccess()) {
					// failed
					mHandler.sendEmptyMessage(GET_PACKAGE_FAILED);
				} else {
					// success
					packages = jsonPackage.packages;
					if (packages != null)
						mHandler.sendEmptyMessage(GET_PACKAGE_SUCCESS);
					else
						mHandler.sendEmptyMessage(GET_PACKAGE_FAILED);
				}
			}
		}).start();
	}

	private void showSMSDialog() {

		isViettel = NetworkUtility.getIsViettel();

		subDialog = new Dialog(mContext);
		subDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		subDialog.setContentView(R.layout.layout_dialog_sms);
		subDialog.setCancelable(true);
		Button okBtn = (Button) subDialog.findViewById(R.id.sms_dialog_ok_btn);
		Button cancelBtn = (Button) subDialog
				.findViewById(R.id.sms_dialog_cancel_btn);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// send message here
				subDialog.dismiss();
				mProgressDialog = new ProgressDialog(mContext);
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
							JsonAuth json = JsonAuth.identify();
							if (json.isSuccess()) {
								registerMessage = jsonAuth.getErrorMessage();
								mHandler.sendEmptyMessage(SUBSCRIBE_FAILED);
							} else {
								sendSMS(selectedPackage.smsServer,
										selectedPackage.smsContent);
								mHandler.sendEmptyMessage(SEND_SMS);
							}

						} else {
							// success

							if (jsonAuth.username != null)
								dataStore.setMsisdn(jsonAuth.phoneNumber);
							if (jsonAuth.password != null)
								dataStore.setPassword(jsonAuth.password);
							if (selectedPackage.price != null)
								dataStore.setPrice(selectedPackage.price);
							mHandler.sendEmptyMessage(SUBSCRIBE_SUCCESS);
						}

					}
				}).start();
				if (dialog != null)
					dialog.dismiss();
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subDialog.dismiss();
				if (dialog != null)
					dialog.show();
			}
		});

		TextView packageDes = (TextView) subDialog
				.findViewById(R.id.sms_first_txt);
		TextView smsContent = (TextView) subDialog
				.findViewById(R.id.sms_second_txt);

		packageDes.setText(selectedPackage.description);
		smsContent.setText("Giá cước dịch vụ là " + selectedPackage.price
				+ " .Bạn có muốn đăng ký dịch vụ không ?");
		subDialog.show();
	}

	private void sendSMS(String phoneNumber, String message) {

		// call message composer

		Uri uri = Uri.parse("smsto:" + phoneNumber);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", message);
		startActivity(it);
	}

	public void showPackageDialog() {
		dialog = new Dialog(mContext);
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

		_listview.addFooterView(footerView);

		TextView _title = (TextView) dialog
				.findViewById(R.id.common_context_menu_title_txt);
		_title.setText("Các gói dịch vụ");
		dialog.show();
		_listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// do something
				selectedPackage = (Package) parent.getItemAtPosition(position);
				if (selectedPackage != null) {
					DataStore dataStore = DataStore.getInstance();
					if (dataStore.getConfig(Const.KEY_PACKAGE_CODE).equals(
							selectedPackage.code)) {
						Toast.makeText(mContext, "Bạn đã đăng ký gói cước này",
								Toast.LENGTH_SHORT).show();
					} else {
						showSMSDialog();
					}
				}
			}
		});
	}

	// -------------------------
	// BEGIN-----------------------

	private class DialogAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		class ViewHolder {
			TextView first_text;
			TextView second_text;
			ImageView image;
			ImageView arrow;

		}

		public DialogAdapter(Context context) {
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
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

	// END-----------------------

}
