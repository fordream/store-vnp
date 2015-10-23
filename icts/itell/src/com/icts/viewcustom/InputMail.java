package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import com.icts.itel.R;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icts.itel.MainActivity;
import com.icts.itel.Register;
import com.icts.json.JsonAnalysis;

public class InputMail {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button btnAuthe;
	private Button btnSendMail;

	private Register registerAc;
	private int mode = 0;
	private String mNumber;
	private int style = 0;
	private int mCheck = 1;
	private String autheni;
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);
	private String mTimeStam;

	public InputMail(Context context, Register register, int net) {
		mContext = context;
		registerAc = register;
		mCheck = net;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_iput_mail, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		btnSendMail = (Button) mView.findViewById(R.id.regis_iput_mail_getCode);
		btnAuthe = (Button) mView.findViewById(R.id.regis_iput_mail_btnOk);

	}

	public void initData() {

	}

	// //////////////
	// ////////mode == 0 send code register

	public void setEvent() {
		btnAuthe.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				AutheMail autheMail = new AutheMail(v.getContext(), registerAc,
						"","");
				registerAc.changeViewLL(autheMail.getmView());
			}
		});
		btnSendMail.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				InputMailToServer inputMailToServer = new InputMailToServer(v
						.getContext(), registerAc);
				registerAc.changeViewLL(inputMailToServer.getmView());
				// Intent i = new Intent(Intent.ACTION_SEND);
				// i.setType("message/rfc822");
				// i.putExtra(Intent.EXTRA_EMAIL,
				// new String[] { "itel.csn2012@gmail.com" });
				// i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
				// i.putExtra(Intent.EXTRA_TEXT, "body of email");
				// try {
				// registerAc.startActivity(Intent.createChooser(i,
				// "Send mail..."));
				// } catch (android.content.ActivityNotFoundException ex) {
				// // Toast.makeText(registerAc.this,
				// // "There are no email clients installed.",
				// // Toast.LENGTH_SHORT).show();
				// }

			}
		});
	}

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

}
