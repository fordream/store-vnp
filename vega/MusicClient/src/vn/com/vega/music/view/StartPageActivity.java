package vn.com.vega.music.view;

import java.util.ArrayList;
import java.util.Calendar;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonPackage;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.network.NetworkUtility;
import vn.com.vega.music.objects.Package;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.holder.TabHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StartPageActivity extends Activity {

	private Button regBtn, loginBtn;
	private Context mContext;
	private static final int GET_PACKAGE_SUCCESS = 1;
	private static final int GET_PACKAGE_FAILED = 2;
	private static final int SUBSCRIBE_SUCCESS = 3;
	private static final int SUBSCRIBE_FAILED = 4;
	private static final int SEND_SMS = -4;

	private static final int SEND_SMS_SUCCESS = 0;

	private ArrayList<Package> packages = null;
	private String registerMessage = "";
	private Package selectedPackage;
	private Dialog dialog;
	private Dialog subDialog;
	private View footerView;

	private boolean wantRegister = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_start_page);
		mContext = this;
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			wantRegister = bundle.getBoolean(Const.KEY_NOT_REGISTERED);
		}

		initView();

		if (wantRegister)
			doRegister();
	}

	private void initView() {
		regBtn = (Button) findViewById(R.id.start_page_reg_btn);
		regBtn.setOnClickListener(onRegisterListener);
		loginBtn = (Button) findViewById(R.id.start_page_login_btn);
		loginBtn.setOnClickListener(onLoginListener);

	}

	OnClickListener onLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// show Login page
			Bundle bundle = new Bundle();
			bundle.putBoolean(LoginActivity.NEED_AUTHENTICATION, false);
			Intent intent = new Intent(mContext, LoginActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}
	};

	OnClickListener onRegisterListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doRegister();
		}
	};

	public void doRegister() {
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

	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

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

	private void sendSMS(String phoneNumber, String message) {

		// call message composer

		Uri uri = Uri.parse("smsto:" + phoneNumber);
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);
		it.putExtra("sms_body", message);
		startActivity(it);
	}

	private void showSMSDialog() {

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
							// failed
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
}
