package com.cnc.buddyup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cnc.buddyup.asyn.SignUpAsyn;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.request.RequestRegister;
import com.cnc.buddyup.sign.asyn.SignUpGetCountryAsyn;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.sign.paracelable.CountryParcacelable;
import com.cnc.buddyup.wheel.CountryWheelActivity;
// Item
@SuppressWarnings("unused")
public class SignUpScreen extends Activity implements OnClickListener {

	private Button btnBack;
	private Button btnSign;

	private CountryParcacelable countryParcacelable = null;
	private Country country = new Country(null, "None");
	private SignUpGetCountryAsyn signUpGetCountryAsyn;
	private SignUpAsyn signUpAsyn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_temp);
		loadingView = (LoadingView) findViewById(R.id.loadingView1);

		getButton(R.id.signUpbtnSubmit).setOnClickListener(this);
		getTextView(R.id.SignUpspCountry).setOnClickListener(this);
		getButton(R.id.button1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callBack();

			}
		});
		configCountry();
		signUpGetCountryAsyn = new SignUpGetCountryAsyn(this, handler);
		signUpGetCountryAsyn.execute("");
	}

	protected void onDestroy() {
		signUpGetCountryAsyn.isClose();
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			callBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void callBack() {
		if (signUpAsyn != null && signUpAsyn.isRun()) {
			return;
		}
		finish();
	}

	private void configCountry() {
		getTextView(R.id.SignUpspCountry).setText(country.getName());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Common.REQUEST_CODE_0 && resultCode == RESULT_OK) {
			country = data.getExtras().getParcelable(Common.ARG0);
			configCountry();
		}
	}

	public void onClick(View arg0) {
		hiddenAllKey();
		if (arg0.getId() == R.id.SignUpspCountry) {
			Intent intent = new Intent(this, CountryWheelActivity.class);
			Bundle data = new Bundle();
			data.putParcelable(Common.ARG0, countryParcacelable);
			data.putString(Common.ARG1, country.getId());
			intent.putExtras(data);
			if (countryParcacelable.getLCountries().size() > 0)
				startActivityForResult(intent, Common.REQUEST_CODE_0);
			return;
		}
		RequestRegister request = new RequestRegister();

		request.setCountry(country.getId());
		request.setEmail(getText(getEditText(R.id.signUpEtEmail)));
		request.setEmailcheck(getText(getEditText(R.id.etEmailcheck)));
		request.setFirstname(getText(getEditText(R.id.SignUprtFirstName)));
		request.setNewsletter(getCheckBox(R.id.signUpCBPlease).isChecked() + "");
		request.setUsername(getText(getEditText(R.id.SignUpetUserName)));
		request.setUserpassword(getText(getEditText(R.id.etUserpassword)));
		request.setZip(getText(getEditText(R.id.SignUpetPostCode)));
		// SignUpRequest request = new SignUpRequest();
		// request.setEmail(getText(getEditText(R.id.signUpEtEmail)));
		// request.setFirstName(getText(getEditText(R.id.SignUprtFirstName)));
		// request.setNewsletter(getCheckBox(R.id.signUpCBPlease).isChecked());
		// request.setPostCode(getText(getEditText(R.id.SignUpetPostCode)));
		// request.setUserName(getText(getEditText(R.id.SignUpetUserName)));
		// request.setCountry(getText(getTextView(R.id.SignUpspCountry)));
		signUpAsyn = new SignUpAsyn(handler, this);
		signUpAsyn.isRun(true);
		signUpAsyn.execute(request);
	}

	private void hiddenAllKey() {
		hiddenKeyboard(this, getEditText(R.id.signUpEtEmail));
		hiddenKeyboard(this, getEditText(R.id.SignUpetPostCode));
		hiddenKeyboard(this, getEditText(R.id.SignUpetUserName));
		hiddenKeyboard(this, getEditText(R.id.SignUprtFirstName));
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_0) {
				finish();
			} else if (msg.what == Common.MESSAGE_WHAT_1) {
				countryParcacelable = msg.getData().getParcelable(Common.ARG0);
				if (countryParcacelable.getLCountries().size() > 0) {
					// country = countryParcacelable.getLCountries().get(0);
					for (int i = 0; i < countryParcacelable.getLCountries()
							.size(); i++) {
						Country country = countryParcacelable.getLCountries()
								.get(i);
						if ("9".equals(country.getId())) {
							SignUpScreen.this.country = country;
						}
					}
					configCountry();
				}
			}
		}
	};
}