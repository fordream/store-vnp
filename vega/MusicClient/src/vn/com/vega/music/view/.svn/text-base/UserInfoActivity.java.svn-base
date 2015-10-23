package vn.com.vega.music.view;

import java.util.Calendar;

import vn.com.vega.chacha.R;
import vn.com.vega.music.clientserver.JsonAuth;
import vn.com.vega.music.database.DataStore;
import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class UserInfoActivity extends Activity implements Const {

	private Context mContext;
	private Button backBtn;
	static final int DATE_DIALOG_ID = 0;
	private int mYear;
	private int mMonth;
	private int mDay;
	private boolean mPhoneEditable, mEmailEditable;
	private DataStore dataStore;
	private Button ApplyChange;
	private EditText edtPhone, edtName, edtEmail, edtAddress, edtBirth,
			edtGender;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_activity_user_info);
		mContext = this;

		edtPhone = (EditText) findViewById(R.id.phone_edt);
		edtName = (EditText) findViewById(R.id.name_edt);
		edtEmail = (EditText) findViewById(R.id.email_edt);
		edtAddress = (EditText) findViewById(R.id.address_edt);
		edtBirth = (EditText) findViewById(R.id.dob_edt);
		edtGender = (EditText) findViewById(R.id.gender_edt);
		ApplyChange = (Button) findViewById(R.id.user_info_apply_btn);
		ApplyChange.setOnClickListener(onApplyChangeListener);
		initView();
	}

	OnClickListener onApplyChangeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String phone = mPhoneEditable ? edtPhone.getText().toString()
					: "";
			final String name = edtName.getText().toString();
			final String email = mEmailEditable ? edtEmail.getText().toString()
					: "";
			final String dob = String.format("%04d-%02d-%02d", mYear, mMonth,
					mDay);
			final int gender = GenderToInt(edtGender.getText().toString());
			final String address = edtAddress.getText().toString();

			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setMessage(getString(R.string.loading));
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {

					JsonAuth update = JsonAuth.updateAccount(name, phone,
							email, dob, gender, address);
					if (update.isSuccess()) {
						mHandler.sendEmptyMessage(MSG_SUCCESS);
						dataStore.setConfig(KEY_DOB, dob);
						dataStore.setConfig(KEY_FULL_NAME, name);
						dataStore.setConfig(KEY_ADDRESS, address);
						dataStore.setConfig(KEY_GENDER, String.valueOf(gender));
						if (mPhoneEditable)
							dataStore.setConfig(KEY_PHONE_NUMBER, phone);
						if (mEmailEditable)
							dataStore.setConfig(KEY_EMAIL, email);
					} else
						mHandler.sendEmptyMessage(MSG_FAILED);
				}
			}).start();
		}
	};

	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mProgressDialog != null && mProgressDialog.isShowing())
				mProgressDialog.dismiss();
			switch (msg.what) {
			case MSG_SUCCESS:
				showAlert(getString(R.string.setting_account_update_success));
				break;
			case MSG_FAILED:
				showAlert(getString(R.string.setting_account_update_failed));
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

	private int GenderToInt(String str) {
		if (str.equals("Nam"))
			return 1;
		else if (str.equals("Nữ"))
			return 2;
		return 0;
	}

	private String GenderToString(int i) {
		if (i == 2)
			return "Nam";
		else if (i == 2)
			return "Nữ";
		return "Chưa biết";
	}

	private void GetDob(String dob) {
		if (dob != null && !dob.equals("")) {
			String[] date = dob.split("-");
			mYear = Integer.parseInt(date[0]);
			mMonth = Integer.parseInt(date[1]);
			mDay = Integer.parseInt(date[2]);
		} else {
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH) + 1;
			mDay = c.get(Calendar.DAY_OF_MONTH);
		}
	}

	private void initView() {
		backBtn = (Button) findViewById(R.id.user_info_back_btn);
		backBtn.setOnClickListener(onBackBtnListener);
		dataStore = DataStore.getInstance();
		// phone
		mPhoneEditable = dataStore.getConfig(KEY_PHONE_NUMBER_EDITABLE).equals(
				"true");
		edtPhone.setText(dataStore.getConfig(KEY_PHONE_NUMBER));
		edtPhone.setEnabled(mPhoneEditable);
		edtPhone.setClickable(mPhoneEditable);

		// name
		edtName.setText(dataStore.getConfig(KEY_FULL_NAME));

		// email
		mEmailEditable = dataStore.getConfig(KEY_EMAIL_EDITABLE).equals("true");
		edtEmail.setText(dataStore.getConfig(KEY_EMAIL));
		edtEmail.setEnabled(mEmailEditable);
		edtEmail.setClickable(mEmailEditable);

		// birthday
		edtBirth.setOnClickListener(onBirthListener);
		GetDob(dataStore.getConfig(KEY_DOB));
		updateDisplay();

		// gender
		edtGender.setOnClickListener(onGenderListener);
		String gender = dataStore.getConfig(KEY_GENDER).equals("") ? "0"
				: dataStore.getConfig(KEY_GENDER);
		edtGender.setText(GenderToString(Integer.valueOf(gender)));

		// address
		edtAddress.setText(dataStore.getConfig(KEY_ADDRESS));
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear,
					mMonth - 1, mDay);
		}
		return null;
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear + 1;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	// updates the date we display in the TextView
	private void updateDisplay() {
		edtBirth.setText(new StringBuilder().append(FormatTime(mDay))
				.append("/").append(FormatTime(mMonth)).append("/")
				.append(mYear));
	}

	private String FormatTime(int d) {
		if (d < 10)
			return (new StringBuilder().append("0").append(d)).toString();
		return (new StringBuilder().append(d)).toString();
	}

	OnClickListener onBirthListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showDialog(DATE_DIALOG_ID);
		}
	};

	OnClickListener onGenderListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showSelectGender();
		}
	};

	private void showSelectGender() {
		final CharSequence[] genders = { "Nam", "Nữ" };
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Chọn giới tính");
		builder.setSingleChoiceItems(genders, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						CharSequence selected = genders[which];
						edtGender.setText(selected);
						dialog.dismiss();
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

}
