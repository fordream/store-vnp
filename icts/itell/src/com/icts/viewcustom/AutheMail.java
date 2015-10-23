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

public class AutheMail {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button btnOk;
	private EditText mEditText1;
	private EditText mEditText2;
	private Register registerAc;
	private int mode = 0;
	private String mNumber;
	private String mCheck;
	private String autheni;
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);
	private String mTimeStam;
	private String email;

	public AutheMail(Context context, Register register, String net,
			String _email) {
		mContext = context;
		registerAc = register;
		mCheck = net;
		email = _email;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_mail_authe, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		btnOk = (Button) mView.findViewById(R.id.regis_iput_authe_btnOk);
		mEditText1 = (EditText) mView
				.findViewById(R.id.register_iput_mail_editText1);
		mEditText2 = (EditText) mView
				.findViewById(R.id.register_iput_mail_editText2);

	}

	public void initData() {

	}

	// //////////////
	// ////////mode == 0 send code register

	public void setEvent() {
		btnOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				String url = registerAc.getString(R.string.url_authen);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs
						.add(new BasicNameValuePair("mobile_num", mCheck));// isp
				nameValuePairs.add(new BasicNameValuePair("email", email));// isp
				nameValuePairs.add(new BasicNameValuePair("authen_code",
						mEditText2.getText().toString()));// isp
				nameValuePairs.add(new BasicNameValuePair("isp", "4"));
				mJsonAlalysis.executeLoadData(url, handlerSendAuthen,
						v.getContext(), nameValuePairs);

			}
		});
	}

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	/*
	 * 
	 * Handler doi load data tu server
	 */

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerSendAuthen = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {
					try {
						if (mJsonAlalysis.getCode(data) != null) {
							DoneRegister doneRegister = new DoneRegister(
									mContext, registerAc, 0,
									mJsonAlalysis.getCode(data), mEditText2
											.getText().toString(), mCheck, 4,
									email);
							registerAc.changeViewLL(doneRegister.getmView());
						} else {

						}

					} catch (JSONException e) {
						// Utils.showDialogServerError(json.mContext);
						e.printStackTrace();
					}
				} else {
					// Utils.showDialogServerError(json.mContext);
				}
			}
		}
	};

}
