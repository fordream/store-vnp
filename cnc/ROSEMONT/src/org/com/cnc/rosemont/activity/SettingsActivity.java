package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.activity.CommonActivity;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.layout;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.database.DBAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.database.table.Config;
import org.com.cnc.rosemont.views.AnimationSlide;
import org.com.cnc.rosemont.views.HeaderView;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SettingsActivity extends CommonActivity implements
		OnCheckedChangeListener {
	Config config = new Config();
	private AnimationSlide animationSlide = new AnimationSlide();
	private boolean isFirst = true;
	private RadioButton radioStartUp;
	private RadioButton radio3G;
	private TextView tvAppversion;
	private TextView tvLastUpdate;
	ViewFlipper flipper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.tran1, R.anim.transitionfromleft);
		setContentView(layout.settings);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(false, false);
		headerView.setType(HeaderView.TYPE_SETTINGS);
		flipper = (ViewFlipper) findViewById(R.id.flipper);

		radioStartUp = (RadioButton) findViewById(R.id.RadioButton02);
		radio3G = (RadioButton) findViewById(R.id.radio0);
		radioStartUp.setOnCheckedChangeListener(this);
		radio3G.setOnCheckedChangeListener(this);
		tvAppversion = (TextView) findViewById(R.id.textView1);
		tvLastUpdate = (TextView) findViewById(R.id.TextView01);
		updateData();
	}

	private void updateData() {
		config = new DBAdapter(this).getConfig();
		radioStartUp.setChecked("true".equals(config.getRemovethestartup()));
		radio3G.setChecked("true".equals(config.getWifiupdate()));
		tvAppversion.setText(config.getAppversion());
		tvLastUpdate.setText(config.getLastupdateSetting());
		isFirst = false;
	}

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (!isFirst) {
			config.setRemovethestartup(radioStartUp.isChecked() + "");
			config.setWifiupdate(radio3G.isChecked() + "");
			new DBAdapter(this).update(config);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		DataStore.getInstance().setConfig(Conts.ISCALCULATOR, false);
		DataStore.getInstance().setConfig(Conts.TAB_ID, 3);
		DataStore.getInstance().setConfig(Conts.BACKFROMCALCULATOR, false);
	}
}
