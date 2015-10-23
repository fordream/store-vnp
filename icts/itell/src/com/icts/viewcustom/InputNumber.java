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

public class InputNumber {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private Button btnOk;
	private EditText mEditText;
	private ImageView txtConfirm;
	private TextView mTv1;
	private TextView mTv2;
	private TextView mTv3;
	private TextView mTv4;
	private Register registerAc;
	private int mode = 0;
	private String mNumber;
	private int style = 0;
	private int mCheck = 1;
	private String autheni;
	JsonAnalysis mJsonAlalysis = new JsonAnalysis(mContext);
	private String mTimeStam;

	public InputNumber(Context context, Register register, int style, int net) {
		mContext = context;
		this.style = style;
		registerAc = register;
		mCheck = net;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_iput_number, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		btnOk = (Button) mView.findViewById(R.id.regis_iput_btnOk);
		mEditText = (EditText) mView.findViewById(R.id.register_iput_editText);
		txtConfirm = (ImageView) mView.findViewById(R.id.regis_input_txtConfirm);
		mTv1 = (TextView) mView.findViewById(R.id.register_tv1);
		mTv2 = (TextView) mView.findViewById(R.id.register_tv2);
		mTv3 = (TextView) mView.findViewById(R.id.register_tv3);
		mTv4 = (TextView) mView.findViewById(R.id.register_tv4);

	}

	public void initData() {
		if (style == 0) {

		} else {
			if(mCheck != 1){
			txtConfirm.setVisibility(View.VISIBLE);
			mEditText.setVisibility(View.INVISIBLE);
			}else{
				txtConfirm.setVisibility(View.VISIBLE);
				txtConfirm.setImageResource(R.drawable.sussss);
				
				mEditText.setVisibility(View.INVISIBLE);
			}
			//txtConfirm.setText("Dang ky hoan tat");
		}

	}

	// //////////////
	// ////////mode == 0 send code register

	public void setEvent() {
		btnOk.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (style == 0) {
					if (mode == 0) {
						Log.e("", "aaaaaaaaaaaa");

						String url = registerAc.getString(R.string.url_getcode);
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								2);
						nameValuePairs.add(new BasicNameValuePair("mobile_num",
								mEditText.getText().toString()));// isp
						nameValuePairs.add(new BasicNameValuePair("isp",
								Integer.toString(mCheck)));
						mJsonAlalysis.executeLoadData(url, handlerLoadData,
								v.getContext(), nameValuePairs);

					} else {
						if (mode == 1) {
							String url = registerAc
									.getString(R.string.url_authen);
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
									2);
							nameValuePairs.add(new BasicNameValuePair(
									"mobile_num", mNumber));// isp authen_code
							nameValuePairs.add(new BasicNameValuePair("isp",
									Integer.toString(mCheck)));
							autheni = mEditText.getText().toString();
							nameValuePairs.add(new BasicNameValuePair(
									"authen_code", mEditText.getText()
											.toString()));
							mJsonAlalysis.executeLoadData(url,
									handlerSendAuthen, v.getContext(),
									nameValuePairs);
							Log.e("", "aaaaaaqqaaaaaa");

						} else {
							UserName name = new UserName(v.getContext(),
									registerAc, mTimeStam,autheni,mNumber,mCheck,"");
							registerAc.changeViewLL(name.getmView());
						}
					}
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

	/*
	 * 
	 * Handler doi load data tu server
	 */

	final Handler handlerLoadData = new Handler() {
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			String data = msg.getData().getString("data");
			boolean exeption = msg.getData().getBoolean("exeption");
			if (total >= 0) {
				if (exeption) {

					try {
						if (mJsonAlalysis.getCode(data) != null) {
							++mode;
							mNumber = mEditText.getText().toString();
							mTv2.setText("SMS で届いた確認コードを入力してください");
							
							mTv1.setVisibility(View.GONE);
							mTv3.setVisibility(View.GONE);
							mTv4.setVisibility(View.GONE);
							mEditText.setText("101");
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
							++mode;
							btnOk.setWidth(90);
							mTv2.setText("確認が完了しました");
							btnOk.setText("プロフィール登録へ");
							txtConfirm.setVisibility(View.VISIBLE);
							mEditText.setVisibility(View.INVISIBLE);
							Log.e("", "hereeeeeeeeeeee");
							mTimeStam = mJsonAlalysis.getCode(data);
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
