/**
 * 
 */
package com.vn.icts.wendy.controller.group.setting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ict.library.activity.BaseActivity;
import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Setting;
import com.vn.icts.wendy.view.TopBarView;

/**
 * @author tvuong1pc
 * 
 */
public class SettingGenderActivity extends BaseActivity implements
		OnClickListener {
	private TopBarView setting_gender_topBarView;
	private RadioGroup settingGender_radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_gender);
		setting_gender_topBarView = getView(R.id.setting_gender_topBarView);
		setting_gender_topBarView.showRip();
		setting_gender_topBarView.setTitle(R.string.setting);
		settingGender_radioGroup = getView(R.id.settingGender_radioGroup);

		Setting setting = new Setting();
		if (setting.isMale()) {
			((RadioButton) settingGender_radioGroup.getChildAt(0))
					.setChecked(true);
		} else {
			((RadioButton) settingGender_radioGroup.getChildAt(1))
					.setChecked(true);
		}

		setting_gender_topBarView.setOnClickRight(this);
	}

	@Override
	public void onClick(View v) {
		Setting setting = new Setting();
		setting.setMale(((RadioButton) settingGender_radioGroup.getChildAt(0))
				.isChecked());
		setting.save();
		((BaseGroupActivity) getParent())
				.onBackPressed(null, "SettingActivity");
	}
}
