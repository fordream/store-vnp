package com.caferhythm.csn.activity;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MailAdressSettingActivity extends BaseActivityWithHeadtab {
	// view on screen
	private TextView mailSettingTV02;
	private EditText mailSetingET01;
	private EditText mailSetingET02;
	private Button changeBT;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentTab(getResources().getString(R.string.mail_setting),
				R.layout.mail_setting_screen);
		super.onCreate(savedInstanceState);
		genView();
	}

	private void genView() {
		mailSettingTV02 = (TextView) findViewById(R.id.tv_mail_setting_02);
		mailSetingET01 = (EditText) findViewById(R.id.et_mail_setting_01);
		mailSetingET02 = (EditText) findViewById(R.id.et_mail_setting_02);
		changeBT = (Button) findViewById(R.id.bt_mail_setting_change);
		pd = new ProgressDialog(this);
		pd.setMessage("Changing...");
		mailSettingTV02.setText(getSharedPreferences(
				Configure.SHARED_PREFERENCES, MODE_PRIVATE).getString(
				Configure.EMAIL, ""));
		changeBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!validateEmail(mailSetingET01.getText().toString())) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_email_invalid), Toast.LENGTH_LONG).show();
					return;
				}
				if (!mailSetingET01.getText().toString().trim()
						.equals(mailSetingET02.getText().toString().trim())) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.ms_email_not_match),
							Toast.LENGTH_LONG).show();
					return;

				}

				changeEmail(mailSetingET02.getText().toString());
				if(pd!=null&&!pd.isShowing())
					pd.show();
			}
		});
	}

	private void changeEmail(final String s1) {
		RequestParams params = new RequestParams();
		params.put("token", FlashScreenActivity.token);
		params.put("mailaddress", s1);
		Connection.post(API.API_S00201, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				JSONObject j;
				try {
					j = new JSONObject(arg0);

					if (!JsonPaser.getErrorJSON(j).isNoError()){
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.ms_email_not_changed), Toast.LENGTH_LONG).show();
					}
					SharedPreferences sp = getApplication()
							.getSharedPreferences(Configure.SHARED_PREFERENCES,
									Activity.MODE_PRIVATE);
					sp.edit()
							.putString(Configure.EMAIL,
									mailSetingET01.getText().toString().trim())
							.commit();
					if(pd!=null &&pd.isShowing())
						pd.dismiss();
					
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_email_success_changed), Toast.LENGTH_LONG).show();
					mailSettingTV02.setText(s1);
					
					mailSetingET01.setText("");
					mailSetingET02.setText("");
					getWindow().setSoftInputMode(
						      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.ms_email_not_changed), Toast.LENGTH_LONG).show();
			}
		});
	}

	private static final Pattern patten = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]" + "+(?:\\.[a-z0-9!#$%&'*"
					+ "+/=?^_`{|}~-]+)*"
					+ "@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
					+ "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	public boolean validateEmail(String email) {

		if (!patten.matcher(email.trim()).matches()) {
			return false;
		} else {
			return true;
		}

	}
}
