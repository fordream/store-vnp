package org.com.cnc.rosemont.views.widgets;

import org.com.cnc.rosemont.R;

import android.R.dimen;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DialogOkNo extends Dialog implements
		android.view.View.OnClickListener {
	private boolean isOk = false;

	// filed
	private TextView tvMessage;
	private View lnOk;
	private View lnNo;

	public DialogOkNo(Context arg0, String message) {
		super(arg0, R.style.Theme_Dialog_Translucent);
		config(arg0, message);
	}

	public DialogOkNo(Context arg0, int res) {
		super(arg0, R.style.Theme_Dialog_Translucent);
		String message = getContext().getResources().getString(res);
		config(arg0, message);
	}

	// public DialogOkNo(Context arg0, int res, String url) {
	// super(arg0, android.R.style.Theme_Translucent_NoTitleBar);
	// context = arg0;
	// String message = getContext().getResources().getString(res);
	// config(arg0, message, url);
	// }

	// private void config(Context context, String message, final String url) {
	// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// setContentView(R.layout.dlok_no);
	//
	// tvMessage = (TextView) findViewById(R.id.textView1);
	// tvMessage.setText(message);
	//
	// lnOk = findViewById(R.id.LinearLayout06);
	// lnOk.setOnClickListener(new View.OnClickListener() {
	// public void onClick(View v) {
	// ((Activity) DialogOkNo.this.context).finish();
	// CommonView.callWeb(getContext(), url);
	// }
	// });
	//
	// lnNo = findViewById(R.id.LinearLayout11);
	// lnNo.setOnClickListener(this);
	// }

	private void config(Context context, String message) {
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dlok_no);

		tvMessage = (TextView) findViewById(R.id.textView1);
		tvMessage.setText(message);

		lnOk = findViewById(R.id.LinearLayout06);
		lnOk.setOnClickListener(this);

		lnNo = findViewById(R.id.LinearLayout11);
		lnNo.setOnClickListener(this);
	}

	public void onClick(View view) {
		if (view == lnOk) {
			isOk = true;
			dismiss();
		} else if (view == lnNo) {
			isOk = false;
			dismiss();
		}
	}

	public boolean isOk() {
		return isOk;
	}
	// public void addAction(View.OnClickListener onClickListener) {
	// //lnOk.setOnClickListener(onClickListener);
	// //return this;
	// }
}