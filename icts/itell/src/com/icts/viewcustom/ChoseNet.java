package com.icts.viewcustom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icts.itel.R;
import com.icts.itel.Register;

public class ChoseNet {
	private View mView = null;
	private LayoutInflater layoutInflater;
	private Context mContext;
	private TextView mTxtCoin;
	private Register registerAc;
	private Button mBtnAu;
	private Button mBtnDocomo;
	private Button mBtnSoftBank;

	public ChoseNet(Context context, Register register) {
		mContext = context;
		registerAc = register;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layoutInflater.inflate(R.layout.register_chosenet, null);

		initView();
		initData();
		setEvent();
	}

	public void initView() {
		mBtnAu = (Button) mView.findViewById(R.id.register_chosenet_rdioAu);
		mBtnDocomo = (Button) mView
				.findViewById(R.id.register_chosenet_rdioDoco);
		mBtnSoftBank = (Button) mView
				.findViewById(R.id.register_chosenet_rdioSoftbank);

	}

	public void initData() {

	}

	public void setEvent() {

		mBtnAu.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				InputMail input = new InputMail(v.getContext(), registerAc, 4);
				registerAc.changeViewLL(input.getmView());
			}
		});
		mBtnDocomo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				InputMail input = new InputMail(v.getContext(), registerAc, 4);
				registerAc.changeViewLL(input.getmView());
			}
		});
		mBtnSoftBank.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				InputNumber input = new InputNumber(v.getContext(), registerAc,
						0, 1);
				registerAc.changeViewLL(input.getmView());
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
