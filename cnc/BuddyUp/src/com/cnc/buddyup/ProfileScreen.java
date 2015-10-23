package com.cnc.buddyup;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.views.ProfileSportView;
import com.cnc.buddyup.views.profile.ProfileOptionInformationView;
import com.cnc.buddyup.views.profile.ProfileView;

public class ProfileScreen extends Activity {
	private int index = 1;
	protected LinearLayout llContent;
	protected Button commonbtnBack;
	protected Button commonBtnAdd;
	protected TextView commonETHearder;
	private ProfileView profileView;
	private ProfileOptionInformationView optionInformationView;
	private ProfileSportView sportView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		profileView = new ProfileView(this);
		setContentView(profileView);
	}

	protected void addView(View view, boolean isClear) {
		if (view != null) {
			if (isClear) {
				llContent.removeAllViews();
			}
			llContent.addView(view);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == Common.REQUEST_CODE_0) {
			Country country = (Country) data.getExtras().getParcelable(
					Common.ARG0);
			profileView.updateContry(country);
		} else if (resultCode == RESULT_OK
				&& requestCode == Common.REQUEST_CODE_1) {
			profileView.updateAge(data.getExtras().getString(Common.ARG0));
		} else if (resultCode == RESULT_OK
				&& requestCode == Common.REQUEST_CODE_2) {
			optionInformationView.updateSex(data.getExtras().getString(
					Common.ARG1));
		} else if (resultCode == RESULT_OK
				&& requestCode == Common.REQUEST_CODE_3) {
			String id = data.getExtras().getString(Common.ARG0);
			String name = data.getExtras().getString(Common.ARG1);
			String idView = data.getExtras().getString(Common.ARG2);
			sportView.updateSkillLevel(id, name, idView);
		}else if (resultCode == RESULT_OK
				&& requestCode == Common.REQUEST_CODE_4) {
			String id = data.getExtras().getString(Common.ARG0);
			String name = data.getExtras().getString(Common.ARG1);
			String idView = data.getExtras().getString(Common.ARG2);
			sportView.updateSportName(id, name, idView);
		}else if (resultCode == RESULT_OK
				&& requestCode == Common.REQUEST_CODE_5) {
			String id = data.getExtras().getString(Common.ARG0);
			String name = data.getExtras().getString(Common.ARG1);
			String idView = data.getExtras().getString(Common.ARG2);
			sportView.updatePhiloshophy(id, name, idView);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (index == 2) {
				addBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void addOptionInformation() {
		index = 2;
		optionInformationView = new ProfileOptionInformationView(this);
		setContentView(optionInformationView);
	}

	public void addBack() {
		index = 1;
		setContentView(profileView);
	}

	public void addSportInformation() {
		index = 2;
		sportView = new ProfileSportView(this);
		setContentView(sportView);
	}
}