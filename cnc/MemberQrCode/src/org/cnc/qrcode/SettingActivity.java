package org.cnc.qrcode;

import org.cnc.qrcode.common.Common;
import org.cnc.qrcode.database.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends Activity {
	private DBAdapter dbAdapter;
	public static String phone = null;
	private EditText yPhone;

	public void onCreate(Bundle objQandAValid) {
		super.onCreate(objQandAValid);
		setContentView(R.layout.settings);
		findViewById(R.id.btnOK).setOnClickListener(okClick);
		findViewById(R.id.btnRequestToGetQRImage).setOnClickListener(
				requestClick);
		findViewById(R.id.btnRequestToGetQRImage).setVisibility(View.GONE);
		phone = null;
		yPhone = (EditText) findViewById(R.id.edit_yourphone);
		config();

	}

	private void config() {
		try {
			dbAdapter = new DBAdapter(this);
			dbAdapter.open();
			yPhone.setText(dbAdapter.getUersName());
		} catch (Exception e) {
		}
	}

	public Button.OnClickListener okClick = new Button.OnClickListener() {
		public void onClick(View v) {
			phone = yPhone.getText().toString();
			if (Common.isNullOrBlank(phone)) {
				String title = getResources().getString(R.string.error_trans);
				String messge = getResources().getString(
						R.string.key_not_blank_trans);
				showDialogCancel(title, messge);
				return;
			}

			Intent i = getIntent();
			i.putExtra("youPhone", yPhone.getText());

			dbAdapter.updateStatus(1, phone, phone);

			GlobalActivity.questionContent = phone;
			setResult(RESULT_OK, i);
			finish();
		}
	};

	public Button.OnClickListener requestClick = new Button.OnClickListener() {
		public void onClick(View v) {
			phone = yPhone.getText().toString();
			if (Common.isNullOrBlank(phone)) {
				String title = getResources().getString(R.string.error_trans);
				String messge = getResources().getString(
						R.string.key_not_blank_trans);
				showDialogCancel(title, messge);
				return;
			}

			GlobalActivity.questionContent = phone;
			try {
				dbAdapter.updateStatus(1, phone, phone);
			} catch (Exception e) {
			}

			Intent intent = new Intent(SettingActivity.this,
					ViewImageActivity.class);
			Bundle data = new Bundle();
			data.putString(Common.ARG0, phone);
			intent.putExtras(data);
			startActivity(intent);
		}
	};

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String url = msg.getData().getString(Common.ARG0);
			if (msg.what == Common.MESSAGE_WHAT_1) {
				if (!Common.isNullOrBlank(url)) {
					Intent iShowImageQR = new Intent(getBaseContext(),
							ShowWebActivity.class);
					iShowImageQR.putExtra("msgAns", url);
					startActivityForResult(iShowImageQR, Common.REQUEST_0);
				} else {
					String title = getResources().getString(
							R.string.error_trans);
					String messge = getResources().getString(
							R.string.download_fail_trans);
					showDialogCancel(title, messge);
				}
			} else if (msg.what == Common.MESSAGE_WHAT_0) {
				// String message = "Incorrect key. Try agian!";
				String title = getResources().getString(R.string.error_trans);
				String messge = getResources().getString(
						R.string.incorrect_key_try_agian_trans);
				showDialogCancel(title, messge);
			} else {
				String title = getResources().getString(R.string.error_trans);
				String messge = getResources().getString(
						R.string.download_fail_trans);
				showDialogCancel(title, messge);
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String title = getResources().getString(R.string.error_trans);
			String messge = getResources().getString(
					R.string.download_fail_trans);
			showDialogCancel(title, messge);
		}
	}

	private void showDialogCancel(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});

		builder.show();
	}

}
