package com.cnc.buddyup.views.profile;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.buddyup.LoginScreen;
import com.cnc.buddyup.ProfileScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.asyn.AsynProfileDelete;
import com.cnc.buddyup.asyn.AsynProfileGet;
import com.cnc.buddyup.asyn.AsynProfileUpdate;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.request.RequestProfileDelete;
import com.cnc.buddyup.request.RequestProfileGet;
import com.cnc.buddyup.request.RequestProfileUpdate;
import com.cnc.buddyup.response.ResponseProfileDelete;
import com.cnc.buddyup.response.ResponseProfileGet;
import com.cnc.buddyup.response.ResponseProfileUpdate;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.wheel.AgeWheelActivity;
import com.cnc.buddyup.wheel.CountryWheelNameActivity;

public class ProfileView extends LinearLayout {
	// public class Field {
	public TextView fprofileTVName;
	public EditText fprofileEtFirstName;
	public EditText fprofileEtEmail;
	public TextView fprofileSpAge;
	public EditText fprofileEtPostCode;
	public TextView fprofileSpContry;
	public Button fprofilebtnSports;
	public Button fprofileBtnorther;
	public Button fprofileBtnUpdate;
	public Button fprofileBtnDelete;
	private EditText fprofilePassword;
	private EditText fprofileRePassword;
	private LoadingView floadingView1;
	private String id = "";
	public ProfileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.profile);
	}

	public ProfileView(Context context) {
		super(context);
		config(R.layout.profile);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);
		fprofilePassword = (EditText) findViewById(R.id.EditText02);
		fprofileRePassword = (EditText) findViewById(R.id.EditText01);
		fprofileBtnDelete = (Button) findViewById(R.id.profileBtnDelete);
		fprofileBtnDelete.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				String title = getResources().getString(R.string.message1);
				String message = getResources().getString(
						R.string.do_you_want_delete_profile);
				Builder builder = Common.createBuilder(getContext(), title,
						message);
				builder.setPositiveButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								RequestProfileDelete request = new RequestProfileDelete();
								request.setToken(Common.token);
								new AsynProfileDelete(request, ProfileView.this)
										.execute("");

							}
						});
				builder.show();

			}
		});

		fprofileBtnorther = (Button) findViewById(R.id.profileBtnorther);
		fprofileBtnorther.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				((ProfileScreen) getContext()).addOptionInformation();

			}
		});

		fprofilebtnSports = (Button) findViewById(R.id.profilebtnSports);
		fprofilebtnSports.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((ProfileScreen) getContext()).addSportInformation();
			}
		});

		fprofileBtnUpdate = (Button) findViewById(R.id.profileBtnUpdate);
		fprofileBtnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RequestProfileUpdate request = new RequestProfileUpdate();
				request.setAge(fprofileSpAge.getText().toString());
				request.setCountry(fprofileSpContry.getText().toString());
				request.setCountry(id);

				request.setEmail(fprofileEtEmail.getText().toString());
				request.setFirstname(fprofileEtFirstName.getText().toString());
				request.setNewsletter("");
				request.setReuserpassword(fprofileRePassword.getText()
						.toString());
				request.setSport1("9");
				request.setSport2("10");
				request.setSport3("11");
				request.setToken(Common.token);
				request.setUsername(fprofileTVName.getText().toString());
				request.setUserpassword(fprofilePassword.getText().toString());

				request.setZip(fprofileEtPostCode.getText().toString());
				new AsynProfileUpdate(request, ProfileView.this).execute("");
			}
		});
		fprofileEtEmail = (EditText) findViewById(R.id.profileEtEmail);
		fprofileEtFirstName = (EditText) findViewById(R.id.profileEtFirstName);
		fprofileEtPostCode = (EditText) findViewById(R.id.profileEtPostCode);
		fprofileSpAge = (TextView) findViewById(R.id.profileSpAge);
		fprofileSpAge.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), AgeWheelActivity.class);
				Bundle data = new Bundle();
				data.putString(Common.ARG1, fprofileSpAge.getText().toString());
				intent.putExtras(data);
				((Activity) getContext()).startActivityForResult(intent,
						Common.REQUEST_CODE_1);
			}
		});

		fprofileSpContry = (TextView) findViewById(R.id.profileSpContry);
		fprofileSpContry.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getContext(),
						CountryWheelNameActivity.class);
				Bundle data = new Bundle();
				data.putString(Common.ARG1, fprofileSpContry.getText()
						.toString());
				intent.putExtras(data);
				((Activity) getContext()).startActivityForResult(intent,
						Common.REQUEST_CODE_0);
			}
		});

		fprofileTVName = (TextView) findViewById(R.id.profileTVName);
		floadingView1 = (LoadingView) findViewById(R.id.loadingView1);
		updateData();
	}

	public void updateData() {
		RequestProfileGet request = new RequestProfileGet();
		request.setToken(Common.token);
		new AsynProfileGet(request, this).execute("");
	}

	public void hiddenAllKey() {
	}

	public void showLoading(final boolean b) {
		Runnable runnable = new Runnable() {
			public void run() {
				floadingView1.setVisibility(b ? VISIBLE : GONE);
			}
		};
		floadingView1.post(runnable);
	}

	public void update(ResponseProfileGet response,String nameCountry) {
		if ("0".equals(response.getStatus())) {
			fprofileEtPostCode.setText(response.getZip());
			fprofileEtEmail.setText(response.getEmail());
			fprofileEtFirstName.setText(response.getFirstName());
			fprofileTVName.setText(response.getUserName());
			fprofileSpAge.setText(response.getAge());
			fprofileSpContry.setText(response.getCountry());
			fprofilePassword.setText(response.getPassword());
			fprofileRePassword.setText(response.getPassword());
			fprofileSpContry.setText(nameCountry);
			id = response.getIdCountry();
		} else {
			String message = response.getMessage();
			if (message == null) {
				message = "Fail";
			}
			Common.builder(getContext(), getString(R.string.error), message);
		}
	}

	public void update(ResponseProfileDelete response) {
		if ("0".equals(response.getStatus())) {
			Common.builder(getContext(),getString(R.string.message1), response.getMessage());
			((Activity) getContext()).finish();
			Intent intent = new Intent(getContext(), LoginScreen.class);
			((Activity) getContext()).startActivity(intent);
		} else {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		}
	}

	public void updateContry(Country country) {
		fprofileSpContry.setText(country.getName());
	}

	public void updateAge(String string) {
		fprofileSpAge.setText(string);
	}

	public void update(ResponseProfileUpdate response) {
		if ("0".equals(response.getStatus())) {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		} else {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		}
	}

	private String getString(int res) {
		return getResources().getString(res);
	}
}