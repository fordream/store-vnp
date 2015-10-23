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

public class DoneRegister {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button btnOk;
	private ImageView txtConfirm;
	private TextView mTv3;
	private Register registerAc;
	private int mode = 0;
	private String mNumber;
	private int mCheck = 1;
	private String autheni;
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);
	private String mTimeStam;
	private int mIps;
	private String mMail;

	public DoneRegister(Context context, Register register, int net, String time) {
		mContext = context;
		registerAc = register;
		mCheck = net;
		mTimeStam = time;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_done, null);

		initView();
		initData();
		setEvent();
	}

	public DoneRegister(Context context, Register register, int net,
			String time, String authe, String number, int ips, String email) {
		mContext = context;
		registerAc = register;
		mCheck = net;
		mNumber = number;
		autheni = authe;
		mIps = ips;
		mTimeStam = time;
		mMail = email;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_done, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		btnOk = (Button) mView.findViewById(R.id.regis_iput_btnOk);
		txtConfirm = (ImageView) mView.findViewById(R.id.regis_done_imgV);
		mTv3 = (TextView) mView.findViewById(R.id.rg_done_txt);

	}

	public void initData() {

		if (mCheck != 1) {
			txtConfirm.setImageResource(R.drawable.first_2_img);
		} else {
			mTv3.setVisibility(View.VISIBLE);
			txtConfirm.setImageResource(R.drawable.sussss);
		}
		// txtConfirm.setText("Dang ky hoan tat");

	}

	// //////////////
	// ////////mode == 0 send code register

	public void setEvent() {
		btnOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (mCheck != 1) {

					UserName name = new UserName(v.getContext(), registerAc,
							mTimeStam, autheni, mNumber, mIps, mMail);
					registerAc.changeViewLL(name.getmView());
				} else {
					Intent in = new Intent(v.getContext(), MainActivity.class);
					registerAc.startActivity(in);
					registerAc.finish();

				}

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
