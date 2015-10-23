package org.com.vnp.tinhca.activity;

import org.com.cnc.common.android.CommonView;
import org.com.cnc.common.android.activity.Activity;
import org.com.vnp.tinhca.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener {

	private Button btnMore;

	private Button btnSetting;

	private Button btnPlay;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		btnMore = (Button) findViewById(R.id.btnMore);
		btnPlay = getButton(R.id.btnPlay);
		btnSetting = getButton(R.id.btnSetting);

		btnMore.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
		btnSetting.setOnClickListener(this);

	}

	
	public void onClick(View v) {
		if(v == btnMore){
			CommonView.showMarketPublish(this, "vuong van truong");
		}
	}
}
