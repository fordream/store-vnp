package org.com.cnc.rosemont.views.widgets;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.activity.commom.CommonApp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DialogOk extends Dialog implements
		android.view.View.OnClickListener {

	// filed
	private TextView tvMessage;
	private View lnOk;
	private OkClickListener okClickListener;

	public DialogOk(Context arg0, String message) {
		super(arg0, R.style.Theme_Dialog_Translucent);
		config(arg0, message);
	}

	public DialogOk(Context arg0, int res) {
		super(arg0, R.style.Theme_Dialog_Translucent);
		String message = getContext().getResources().getString(res);
		config(arg0, message);
	}

	public void setOkClickListener(OkClickListener okClickListener) {
		this.okClickListener = okClickListener;
	}

	private void config(Context context, String message) {
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dlok);

		tvMessage = (TextView) findViewById(R.id.textView1);
		tvMessage.setText(message);

		lnOk = findViewById(R.id.lnOk);
		lnOk.setOnClickListener(this);

	}

	public void onClick(View view) {
		if (view == lnOk) {
			dismiss();
			CommonApp.isShowFirst = false;
			if(okClickListener !=null){
				okClickListener.performOkClick();
			}
		}
	}

	@Override
	public void show() {
		super.show();
		CommonApp.isShowFirst = true;
	}

	@Override
	public void dismiss() {
		super.dismiss();

	}

	public interface OkClickListener {
		public void performOkClick();
	}
}