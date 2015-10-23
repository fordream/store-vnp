package vn.com.vega.music.view;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.clientserver.JsonBase;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.downloadmanager.DownloadManager;
import vn.com.vega.music.player.MusicPlayer;
import vn.com.vega.music.syncmanager.SynchronizeManager;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AccSettingActivity extends Activity {

	private Button backBtn, closeBtn, saveBtn;
	private EditText oldPassEdt, newPassEdt, newPassConfirmEdt;
	private RelativeLayout userInfoLayout, accConnectingLayout, nofifyConfigLayout, unsubcribeLayout, changePassLayout;
	private Context mContext;
	private ProgressDialog mProgressDialog;
	private JsonAuth unsubscribe;
	protected static final int UNSUBCRIBE_FAILED = 0;
	protected static final int UNSUBCRIBE_SUCCESS = 1;
	protected static final int CHANGE_PASS_FAILED = 2;
	protected static final int CHANGE_PASS_SUCCESS = 3;
	private DataStore dataStore;
	protected Dialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		dataStore = DataStore.getInstance();
		
		dataStore.getMsisdn();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_account_setting);

		initView();
	}

	private void initView() {
		backBtn = (Button) findViewById(R.id.acc_setting_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);
		userInfoLayout = (RelativeLayout) findViewById(R.id.user_info_layout);
		accConnectingLayout = (RelativeLayout) findViewById(R.id.connecting_layout);
		userInfoLayout.setOnClickListener(onUserInfoLayoutListener);
		accConnectingLayout.setOnClickListener(onConnectingLayoutListener);
		nofifyConfigLayout = (RelativeLayout) findViewById(R.id.notification_setting_layout);
		nofifyConfigLayout.setOnClickListener(onNotificationLayoutListener);
		unsubcribeLayout = (RelativeLayout) findViewById(R.id.unsubcribe_layout);
		unsubcribeLayout.setOnClickListener(onUnsubcribeLayoutListener);
		changePassLayout = (RelativeLayout) findViewById(R.id.change_pass_layout);
		changePassLayout.setOnClickListener(onChangePassLayoutListener);
	}

	OnClickListener onChangePassLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog = new Dialog(mContext);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.layout_dialog_change_pass);
			oldPassEdt = (EditText) dialog.findViewById(R.id.old_pass_edt);
			newPassEdt = (EditText) dialog.findViewById(R.id.new_pass_edt_1);
			newPassConfirmEdt = (EditText) dialog.findViewById(R.id.new_pass_edt_2);
			closeBtn = (Button) dialog.findViewById(R.id.change_pass_close_btn);
			saveBtn = (Button) dialog.findViewById(R.id.change_pass_save_btn);
			setOnClick(closeBtn);
			setOnClick(saveBtn);
			dialog.show();
		}
	};

	private void changePassword() {
		mProgressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true);

		Thread dataInitializationThread = new Thread() {
			public void run() {
				JsonAuth jau = JsonAuth.changePassword(dataStore.getMsisdn(), oldPassEdt.getText().toString(), newPassEdt.getText().toString());
				if(jau.isSuccess()){
					mHandler.sendEmptyMessage(CHANGE_PASS_SUCCESS);
				}
				else
					mHandler.sendEmptyMessage(CHANGE_PASS_FAILED);
				
			}
		};
		dataInitializationThread.start();
	}
	
	private void setOnClick(final View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.change_pass_close_btn:
					dialog.dismiss();
					break;
				case R.id.change_pass_save_btn:
					if (!oldPassEdt.getText().toString().trim().equals(dataStore.getSavedPassword())) {
						Toast.makeText(mContext, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
					} else if (!newPassEdt.getText().toString().equals(newPassConfirmEdt.getText().toString())) {
						Toast.makeText(mContext, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();

					} else {
						changePassword();
					}
					break;

				default:
					break;
				}
			}
		});
	}// end of setOnclick

	OnClickListener onUnsubcribeLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			unsubscribe();
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mProgressDialog != null && mProgressDialog.isShowing())
				mProgressDialog.dismiss();
			switch (msg.what) {
			case UNSUBCRIBE_FAILED:
				if (!unsubscribe.getErrorMessage().trim().equals(""))
					showAlert(unsubscribe.getErrorMessage());
				break;
			case UNSUBCRIBE_SUCCESS:
				// showAlert(getString(R.string.setting_unsubcribe_success));
				restartApp(getString(R.string.setting_unsubcribe_success));
				break;
			case CHANGE_PASS_FAILED:
				Toast.makeText(mContext, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
				break;
			case CHANGE_PASS_SUCCESS:
				if(dialog != null)
					dialog.dismiss();
				Toast.makeText(mContext, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
				break;

			}
		}
	};

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
				bundle.putString(Const.KEY_PASSWORD, dataStore.getSavedPassword());

				// clear old login info
				dataStore.setMsisdn("");
				dataStore.setPassword("");

				bundle.putBoolean(Const.BUNDLE_IS_LOGOUT, true);
				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.putExtras(bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

	private void unsubscribe() {
		// show Confirm dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage(R.string.setting_confirm_unsubscribe).setCancelable(false).setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				mProgressDialog = new ProgressDialog(mContext);
				mProgressDialog.setMessage(getString(R.string.setting_unsubcribe));
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
		}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	OnClickListener onBackBtnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnClickListener onUserInfoLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, UserInfoActivity.class);
			startActivity(intent);
			//Toast.makeText(mContext, "Tính năng đang trong quá trình cập nhật", Toast.LENGTH_SHORT).show();
		}
	};

	OnClickListener onNotificationLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, NotifyConfigActivity.class);
			startActivity(intent);
		}
	};

	OnClickListener onConnectingLayoutListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContext, AccConnectingActivity.class);
			startActivity(intent);
		}
	};
}
