package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.common.android.activity.CommonActivity;
import org.com.cnc.rosemont.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class CloseDialogActivity extends CommonActivity implements
		OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogclose);
		getLinearLayout(R.id.llOk).setOnClickListener(this);
		getLinearLayout(R.id.llCancel).setOnClickListener(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.llOk) {

				setResult(RESULT_OK);
				String url = "http://www.yellowcard.gov.uk";
				CommonView.callWeb(this, url);
		}

		finish();
	}
}
