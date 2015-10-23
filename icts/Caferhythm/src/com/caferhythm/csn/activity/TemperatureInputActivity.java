package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.ErrorJSON;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TemperatureInputActivity extends Activity {
	// view on screen
	private EditText edt;

	private String headString;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temperature_input_screen);
		edt = (EditText) findViewById(R.id.et_temperature);
		edt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(arg0.length()>=1){
					if(arg0.charAt(arg0.length()-1)=='.'){
						//edt.setMa
						int maxLength = arg0.length()+1;
						InputFilter[] FilterArray = new InputFilter[1];
						FilterArray[0] = new InputFilter.LengthFilter(maxLength);
						edt.setFilters(FilterArray);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		final SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat s2;
		Resources r = getResources();
		pd = new ProgressDialog(this);
		pd.setMessage("Loading...");
		s2 = new SimpleDateFormat("yyyy" + r.getString(R.string.year) + "MM"
				+ r.getString(R.string.month) + "dd"
				+ r.getString(R.string.day));
		try {
			((TextView) (findViewById(R.id.tv_temperature_head))).setText(s2
					.format(s1.parse(getIntent().getStringExtra("headname"))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (getIntent().getStringExtra("taion") != null) {
			edt.setText(getIntent().getStringExtra("taion"));
			if (edt.getText() != null)
				edt.setSelection(edt.getText().length());
		}
		headString = getIntent().getStringExtra("headname");
		findViewById(R.id.bt_temperature_save).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (edt.getText().toString().length() > 0) {
							uploadTemperature(headString, edt.getText()
									.toString());
							if (pd != null && !pd.isShowing())
								pd.show();
							// finish();
						}
					}
				});
		findViewById(R.id.bt_temperature_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		findViewById(R.id.tv_temp_01).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), HelpContent02Activity.class));
			}
		});
		findViewById(R.id.tv_temp_02).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), HelpContent02Activity.class));
			}
		});
	}

	private void uploadTemperature(String date, String temper) {
		RequestParams params = new RequestParams();
		params.put("date", date);
		params.put("temperature", temper);
		Log.i("test", "params" + params);
		Connection.post(API.API_S006 + FlashScreenActivity.token, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.i("test", "" + arg0);
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}
						try {
							JSONObject j = new JSONObject(arg0);
							ErrorJSON e = JsonPaser.getErrorJSON(j);
							if (!e.getCode().equals("1003")) {
								finish();
							} else {
								Toast.makeText(getApplicationContext(),
										getResources().getString(R.string.ms_input_invalid), Toast.LENGTH_LONG)
										.show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
					}
				});
	}
}
