package vn.com.vega.music.view;

import vn.com.vega.chacha.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginGmailActivity extends Activity {

	private static final String FACEBOOK = "Facebook";
	private static final String GMAIL = "Gmail";

	private Button backBtn;
	private Button clearUsernameBtn;
	private Button clearPasswordBtn;
	private EditText usernametxt;
	private EditText passwordtxt;
	private TextView titleLbl;
	private String LOGINTYPE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_login_gmail);
		Intent i = this.getIntent();
		LOGINTYPE = i.getStringExtra("caller");
		InitView();
	}

	protected void InitView() {
		backBtn = (Button) findViewById(R.id.btn_Back);
		clearUsernameBtn = (Button) findViewById(R.id.btn_clear_username);
		clearPasswordBtn = (Button) findViewById(R.id.btn_clear_password);
		usernametxt = (EditText) findViewById(R.id.txt_username);
		passwordtxt = (EditText) findViewById(R.id.txt_password);
		titleLbl = (TextView) findViewById(R.id.lbl_logintitle);

		if (LOGINTYPE == GMAIL)
			titleLbl.setText(R.string.find_friend_login_to_gmail);
		else if(LOGINTYPE == FACEBOOK)
			titleLbl.setText(R.string.find_friend_login_to_facebook);

		clearUsernameBtn.setOnClickListener(onClearUsernameListener);
		clearPasswordBtn.setOnClickListener(onClearPasswordListener);
		backBtn.setOnClickListener(onBackListener);
	}

	OnClickListener onBackListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onBackPressed();
		}
	};

	OnClickListener onClearUsernameListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			usernametxt.setText("");
		}
	};

	OnClickListener onClearPasswordListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			passwordtxt.setText("");
		}
	};

}
