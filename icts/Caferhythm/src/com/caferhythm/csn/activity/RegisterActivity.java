package com.caferhythm.csn.activity;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.json.JsonPaser;
import com.caferhythm.csn.utils.CheckConnection;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisterActivity extends Activity {
	// view on screen
	private Button agreeBt;
	private EditText userNameEditText;
	private EditText emailEditText;
	private TextView register3TV;
	private TextView register5TV;

	private ProgressDialog pd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CheckConnection c = new CheckConnection(this);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.register_screen);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		pd = new ProgressDialog(this);
		if (!c.haveNetworkConnection()) {
			Toast toast = Toast.makeText(this, getResources().getString(R.string.ms_no_internet),
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		getToken(FlashScreenActivity.uuidString);
		userNameEditText = (EditText) findViewById(R.id.edt_register_user_name);
		emailEditText = (EditText) findViewById(R.id.edt_register_email);
		register3TV = (TextView) findViewById(R.id.tv_register_03);
		Pattern patternUseInfo = Pattern.compile(getResources().getString(
				R.string.use_info));
		Pattern patternPrivacyPolicy = Pattern.compile(getResources()
				.getString(R.string.privacy_policy));
		register5TV = (TextView) findViewById(R.id.tv_register_05);
		Linkify.addLinks(register5TV, patternUseInfo, "caferhythm.use_info://");
		Linkify.addLinks(register5TV, patternPrivacyPolicy,
				"caferhythm.privacy_policy://");
		register5TV.setLinkTextColor(getResources().getColorStateList(R.color.gray));
		agreeBt = (Button) findViewById(R.id.bt_register_agree);
		agreeBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = userNameEditText.getText().toString();
				String email = emailEditText.getText().toString();
				if (name.length() > 16) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_username_long), Toast.LENGTH_SHORT).show();
					return;
				}
				if (name.length() < 4) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_username_short), Toast.LENGTH_SHORT).show();
					return;
				}
				Pattern p = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]" + "+(?:\\.[a-z0-9!#$%&'*"
						+ "+/=?^_`{|}~-]+)*");
				if(!p.matcher(name.trim()).matches()){
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_name_format), Toast.LENGTH_SHORT).show();
					return;
				}
				if(email.length()>50){
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_email_too_long), Toast.LENGTH_SHORT).show();
					return;
				}
				if (!validateEmail(email)) {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_email_invalid), Toast.LENGTH_SHORT).show();
					return;
				}
				doRegister(name, email, FlashScreenActivity.token);
				if (pd == null) {
					pd.setTitle("connecting to server...");
					pd.show();
				}
				// startActivity(new Intent(getApplicationContext(),
				// Entry02Activity.class));
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}

	private String getToken(String uuid) {
		String result = "";
		Connection.get(API.API_GET_TOKEN + uuid, null,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						FlashScreenActivity.token = JsonPaser
								.PaserAccesstoken(arg0);
						Log.i("test", "reslult:----:" + arg0);
						Log.i("test", "reslult:----:"
								+ FlashScreenActivity.token);
						agreeBt.setClickable(true);
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Log.i("test", "reslult failure:" + arg1);
					}
				});
		return result;
	}

	private void doRegister(final String name, String email, String token) {
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("mailaddress", email);
		Connection.post(API.API_USER_REGISTER + token, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.i("test", "return---" + arg0);
						if (JsonPaser.paserRegisterResult(arg0).equals(name)) {
							SharedPreferences sp = getApplication().getSharedPreferences(Configure.SHARED_PREFERENCES,
											Activity.MODE_PRIVATE);
							sp.edit().putString(Configure.TOKEN,FlashScreenActivity.token).commit();
							sp.edit().putString(Configure.EMAIL,emailEditText.getText().toString()).commit();
							if (pd != null && pd.isShowing())
								pd.dismiss();
							Intent i = new Intent(getApplicationContext(),Seiri01Activity.class);
							i.putExtra("first", "0");
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
						}

					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
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
