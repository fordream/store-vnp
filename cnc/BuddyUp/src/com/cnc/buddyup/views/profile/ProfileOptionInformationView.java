package com.cnc.buddyup.views.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnc.buddyup.ProfileScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.asyn.AsynProfileOptionInformation;
import com.cnc.buddyup.asyn.AsynUpdateProfileOptionInformation;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.request.RequestUpdateOptionInformation;
import com.cnc.buddyup.response.ResponseOptionInformation;
import com.cnc.buddyup.response.ResponseUpdateOptionInformation;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.views.LinearLayout;
import com.cnc.buddyup.wheel.SexWheelActivity;

public class ProfileOptionInformationView extends LinearLayout {
	private LoadingView loadingView;

	public class Field {
		public TextView fprofileoptioninformationSPSex;
		public EditText fprofileoptioninformationETDescription;
		public EditText fprofileoptioninformationETComments;
		public Button fprofileoptioninformationBtnSubmit;
	}

	public class Data {
		public Country dcountry = new Country("id1", "Fmale");
	}

	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ProfileOptionInformationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.profile_option_information);
	}

	public ProfileOptionInformationView(Context context) {
		super(context);
		config(R.layout.profile_option_information);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		field.fprofileoptioninformationBtnSubmit = getButton(R.id.profileoptioninformationBtnSubmit);
		field.fprofileoptioninformationETComments = getEditText(R.id.profileoptioninformationETComments);
		field.fprofileoptioninformationETDescription = getEditText(R.id.profileoptioninformationETDescription);
		field.fprofileoptioninformationSPSex = getTextView(R.id.profileoptioninformationSPSex);
		loadingView = (LoadingView) findViewById(R.id.loadingView1);
		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						((ProfileScreen) getContext()).addBack();
					}
				});

		field.fprofileoptioninformationSPSex
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(getContext(),
								SexWheelActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString(Common.ARG0,
								field.fprofileoptioninformationSPSex.getText()
										.toString());
						intent.putExtras(bundle);
						((ProfileScreen) getContext()).startActivityForResult(
								intent, Common.REQUEST_CODE_2);
					}
				});

		new AsynProfileOptionInformation(this).execute("");
		field.fprofileoptioninformationBtnSubmit
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						RequestUpdateOptionInformation request;
						request = new RequestUpdateOptionInformation();
						request.setSex(field.fprofileoptioninformationSPSex.getText().toString());
						request.setComments(field.fprofileoptioninformationETComments.getText().toString());
						request.setDescription(field.fprofileoptioninformationETDescription.getText().toString());
						
						new AsynUpdateProfileOptionInformation(ProfileOptionInformationView.this, request).execute("");
					}
				});
	}

	public void hiddenAllKey() {
		hiddenKeyboard(getContext(),
				getEditText(R.id.profileoptioninformationETComments));
		hiddenKeyboard(getContext(),
				getEditText(R.id.profileoptioninformationETDescription));
	}

	public void updateSex(String string) {
		field.fprofileoptioninformationSPSex.setText(string);
	}

	public void showLoading(final boolean b) {
		loadingView.post(new Runnable() {
			public void run() {
				loadingView.setVisibility(b ? VISIBLE : GONE);
			}
		});
	}

	public void update(ResponseOptionInformation response) {
		if ("0".equals(response.getStatus())) {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
			field.fprofileoptioninformationSPSex.setText(response.getSex());
			field.fprofileoptioninformationETComments.setText(response
					.getComments());
			field.fprofileoptioninformationETDescription.setText(response
					.getDescription());
		} else {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		}
	}

	private String getString(int res) {
		return getResources().getString(res);
	}

	public void update(ResponseUpdateOptionInformation response) {
		if ("0".equals(response.getStatus())) {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		} else {
			Common.builder(getContext(), getString(R.string.message1),
					response.getMessage());
		}

	}
}