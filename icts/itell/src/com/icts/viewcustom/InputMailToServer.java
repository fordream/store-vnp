package com.icts.viewcustom;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icts.itel.R;
import com.icts.itel.Register;
import com.icts.json.JsonAnalysis;

public class InputMailToServer {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button btnSendMail;
	private EditText mEdtMail;
	private EditText mEdtNumber;
	private Register registerAc;
	// /
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);

	public InputMailToServer(Context context, Register register) {
		mContext = context;
		registerAc = register;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_iput_mailtoserver,
				null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		btnSendMail = (Button) mView.findViewById(R.id.regis_iput_btnOk);
		mEdtMail = (EditText) mView.findViewById(R.id.register_iput_editText);
		mEdtNumber = (EditText) mView
				.findViewById(R.id.register_iput_editTextNumber);

	}

	public void initData() {

	}

	// //////////////
	// ////////mode == 0 send code register

	public void setEvent() {

		btnSendMail.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String url = "http://49.212.140.145/itell/regist/get_code_email";
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("email", mEdtMail
						.getText().toString()));// isp

				mJsonAlalysis.executeLoadData(url, handlerRegister,
						v.getContext(), nameValuePairs);

				autheMail = new AutheMail(v.getContext(), registerAc,
						mEdtNumber.getText().toString(), mEdtMail.getText()
								.toString());

			}
		});
	}

	AutheMail autheMail;

	public View getmView() {
		return mView;
	}

	public void setmView(View mView) {
		this.mView = mView;
	}

	final Handler handlerRegister = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						if (mJsonAlalysis.getCode(data) != null) {
							registerAc.changeViewLL(autheMail.getmView());
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
