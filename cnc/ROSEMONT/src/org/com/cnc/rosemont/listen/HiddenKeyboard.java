package org.com.cnc.rosemont.listen;

import org.com.cnc.common.android.CommonView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class HiddenKeyboard implements OnClickListener {
	private Activity activity;

	public HiddenKeyboard(Activity activity) {
		this.activity = activity;
	}

	public void onClick(View v) {
		CommonView.hiddenKeyBoard(activity);
	}

}
