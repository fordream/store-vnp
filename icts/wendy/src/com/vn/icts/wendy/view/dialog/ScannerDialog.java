package com.vn.icts.wendy.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.vn.icts.wendy.R;

public class ScannerDialog extends Dialog implements
		android.view.View.OnClickListener {
	public static final int TYPE_PRESENT = 1;
	public static final int TYPE_COUPON = 2;
	public static final int TYPE_YES_NO = 3;
	private int type = 1;

	public ScannerDialog(Context context, int type) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		setCancelable(false);
		this.type = type;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_scanner_layout);

		if (type == TYPE_PRESENT) {
			findViewById(R.id.dialog_scanner_img_present).setVisibility(
					View.VISIBLE);
		} else if (type == TYPE_COUPON) {
			findViewById(R.id.dialog_scanner_img_cupon).setVisibility(
					View.VISIBLE);
		} else {
			findViewById(R.id.dialog_scanner_yes_no)
					.setVisibility(View.VISIBLE);
		}

		findViewById(R.id.dialog_scanner_btn_yes).setOnClickListener(this);
		findViewById(R.id.dialog_scanner_btn_no).setOnClickListener(this);
		findViewById(R.id.dialog_scanner_main).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_scanner_btn_yes:
			dismiss();
			break;
		case R.id.dialog_scanner_btn_no:
			dismiss();
			break;
		case R.id.dialog_scanner_main:
			if (type != TYPE_YES_NO) {
				dismiss();
			}
			break;
		default:
			break;
		}

	}
}