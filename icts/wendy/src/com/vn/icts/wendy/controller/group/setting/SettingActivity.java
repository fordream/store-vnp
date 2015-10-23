/**
 * 
 */
package com.vn.icts.wendy.controller.group.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ToggleButton;

import com.ict.library.activity.BaseActivity;
import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Setting;
import com.vn.icts.wendy.view.TopBarView;
import com.vn.icts.wendy.view.dialog.DateDialog;

/**
 * @author tvuong1pc
 * 
 */
public class SettingActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener, OnEditorActionListener {
	private Setting setting;

	//
	private ToggleButton setting_toggleButtonPush;
	private ToggleButton setting_toggleButtonFace;
	private ToggleButton setting_toggleButtonTwitter;

	private TextView setting_tvUserName;
	private TextView setting_tvGender;
	private TextView setting_tvBirth;

	private EditText setting_etUserName;

	private LinearLayout setting_birth;
	private LinearLayout setting_gender;
	private LinearLayout setting_username;
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			setting = new Setting();
			update();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		TopBarView barView = getView(R.id.topBarViewSetting);
		barView.showRip();
		barView.setTitle(R.string.setting);

		setting = new Setting();

		setting_toggleButtonPush = getView(R.id.setting_toggleButtonPush);
		setting_toggleButtonFace = getView(R.id.setting_toggleButtonFace);
		setting_toggleButtonTwitter = getView(R.id.setting_toggleButtonTwitter);

		setting_tvUserName = getView(R.id.setting_tvUserName);

		setting_etUserName = getView(R.id.setting_etUserName);

		setting_tvGender = getView(R.id.setting_tvGender);
		setting_tvBirth = getView(R.id.setting_tvBirth);

		setting_birth = getView(R.id.setting_birth);
		setting_username = getView(R.id.setting_username);
		setting_gender = getView(R.id.setting_gender);
		update();

		//
		setting_etUserName.setVisibility(View.GONE);

		// add Avtion
		setting_birth.setOnClickListener(this);
		setting_username.setOnClickListener(this);
		setting_gender.setOnClickListener(this);
		setting_toggleButtonPush.setOnCheckedChangeListener(this);
		setting_toggleButtonFace.setOnCheckedChangeListener(this);
		setting_toggleButtonTwitter.setOnCheckedChangeListener(this);

		setting_etUserName.setImeOptions(EditorInfo.IME_ACTION_DONE);

		setting_etUserName.setOnEditorActionListener(this);
		
		registerReceiver(broadcastReceiver, new IntentFilter("SettingActivity"));
	}

	private void update() {
		setting_tvUserName.setText(setting.getUserName() + "   ");
		setting_etUserName.setText(setting.getUserName());

		setting_tvGender
				.setText((setting.isMale() ? "Male" : "Female") + "   ");
		setting_tvBirth.setText(setting.getDateOfBirth() + "   ");

		setting_toggleButtonPush.setChecked(setting.isPush());
		setting_toggleButtonFace.setChecked(setting.isConnectFace());
		setting_toggleButtonTwitter.setChecked(setting.isConnectTwiiter());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		setting.setConnectFace(setting_toggleButtonFace.isChecked());
		setting.setConnectTwiiter(setting_toggleButtonTwitter.isChecked());
		setting.setPush(setting_toggleButtonPush.isChecked());
		setting.save();

		if (buttonView == setting_toggleButtonPush) {
			if (setting_toggleButtonPush.isChecked()) {
				// open for push
			} else {
				// close for push
			}
		} else if (buttonView == setting_toggleButtonTwitter) {
			if (setting_toggleButtonTwitter.isChecked()) {
			} else {
			}
		} else if (buttonView == setting_toggleButtonFace) {
			if (setting_toggleButtonFace.isChecked()) {
			} else {
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view == setting_birth) {
			// show dialog settings
			final DateDialog dateActivity = new DateDialog(getParent());
			dateActivity.setOnClickOk(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dateActivity.dismiss();
					int year = dateActivity.getYear();
					int month = dateActivity.getMonth();
					int day = dateActivity.getDay();
					String date = (day < 10 ? "0" : "") + day;
					date += (month < 10 ? "-0" : "-") + month;
					date += "-" + year;
					setting.setDateOfBirth(date);
					setting.save();
					update();
				}
			});
			dateActivity.show();
		} else if (setting_gender == view) {
			((BaseGroupActivity)getParent()).addView("SettingGenderActivity", SettingGenderActivity.class, null);
		} else if (view == setting_username) {
			setting_tvUserName.setVisibility(View.GONE);
			setting_etUserName.setText(setting.getUserName());

			setting_etUserName.setVisibility(View.VISIBLE);
			setting_etUserName.requestFocus();

			// show keyboard

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(setting_etUserName,
					InputMethodManager.SHOW_IMPLICIT);

		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH
				|| actionId == EditorInfo.IME_ACTION_DONE
				|| event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
			setting.setUserName(setting_etUserName.getText().toString());
			setting.save();
			update();
			setting_etUserName.setVisibility(View.GONE);
			setting_tvUserName.setVisibility(View.VISIBLE);
		}
		return false;
	}
}
